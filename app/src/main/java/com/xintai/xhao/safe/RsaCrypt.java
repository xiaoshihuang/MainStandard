package com.xintai.xhao.safe;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

import android.util.Base64;
import android.util.Log;

import com.xintai.xhao.utils.MyLog;

/**
 * 非对称加密Rsa;
 * 加密速度慢,只能处理少量数据,优点是公钥即使在不安全的网络上公开,也能保证安全，Rsa,Dsa,Ecc
 * 
 * 非对称加密一般不会单独拿来使用，他并不是为了取代对称加密而出现的，非对称加密速度比对称加密慢很多，极端情况下会慢1000倍，
 * 所以一般不会用来加密大量数据，通常我们经常会将对称加密和非对称加密两种技术联合起来使用，
 * 例如用非对称加密来给称加密里的秘钥进行加密（即秘钥交换）。
 * 
 * 各种加密的实现和用途http://blog.csdn.net/wxx614817/article/details/52606508
 * 非对称加密用途：
 * 1.身份认证
 * 一条加密信息若能用A的公钥能解开，则该信息一定是用A的私钥加密的，该能确定该用户是A。
 * 服务器发出的信息用服务器端的私钥加密，如果客户端能用服务器端的公钥打开，那么信息肯定是服务器端发出的，不可能被冒充；
 * 2.陌生人通信
 * A 和B 两个人互不认识，A 把自己的公钥发给B，B 也把自己的公钥发给A，则双方可以通过对方的公钥加密信息通信。
 * C 虽然也能得到A、B 的公钥，但是他解不开密文。问题是C可以给AB发生紊乱的信息；
 * 3.秘钥交换
 * 
 * @author xionghao
 * 
 */
public class RsaCrypt {

	public static final String RSA = "RSA";// 非对称加密密钥算法
	public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";// 加密填充方式
	public static final int DEFAULT_KEY_SIZE = 2048;// 秘钥默认长度
	public static final byte[] DEFAULT_SPLIT = "#PART#".getBytes(); // 当要加密的内容超过bufferSize，则采用partSplit进行分块加密
	public static final int DEFAULT_BUFFERSIZE = (DEFAULT_KEY_SIZE / 8) - 11;// 当前秘钥支持加密的最大字节数
	
	/**
	 * 实现分段加密：
	 * RSA非对称加密内容长度有限制，1024位key的最多只能加密127位数据，
	 * 否则就会报错(javax.crypto.IllegalBlockSizeException: Data must not be longer than 117 bytes) ，
	 * 　RSA 是常用的非对称加密算法。最近使用时却出现了“不正确的长度”的异常，研究发现是由于待加密的数据超长所致。
	 * RSA 算法规定：待加密的字节数不能超过密钥的长度值除以 8 再减去 11（即：KeySize / 8 - 11），
	 * 而加密后得到密文的字节数，正好是密钥的长度值除以 8（即：KeySize / 8）。
	 */

	/**
	 * 随机生成RSA密钥对
	 * 
	 * @param keyLength
	 *            密钥长度，范围：512～2048 一般1024
	 * @return
	 */
	public static KeyPair generateRSAKeyPair(int keyLength) {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
			kpg.initialize(keyLength);
			return kpg.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 用公钥对字符串进行加密
	 * 
	 * @param data
	 *            原文
	 */
	public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey)
			throws Exception {
		// 得到公钥
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
		KeyFactory kf = KeyFactory.getInstance(RSA);
		PublicKey keyPublic = kf.generatePublic(keySpec);
		// 加密数据
		Cipher cp = Cipher.getInstance(ECB_PKCS1_PADDING);
		cp.init(Cipher.ENCRYPT_MODE, keyPublic);
		return cp.doFinal(data);
	}
	
	/**
	 * 用公钥对字符串进行分段加密，
	 * 对encryptByPublicKey的完善；
     */
    public static byte[] encryptByPublicKeyForSpilt(byte[] data, byte[] publicKey) throws Exception {
        int dataLen = data.length;
        if (dataLen <= DEFAULT_BUFFERSIZE) {
            return encryptByPublicKey(data, publicKey);
        }
        List<Byte> allBytes = new ArrayList<Byte>(2048);
        int bufIndex = 0;
        int subDataLoop = 0;
        byte[] buf = new byte[DEFAULT_BUFFERSIZE];
        for (int i = 0; i < dataLen; i++) {
            buf[bufIndex] = data[i];
            if (++bufIndex == DEFAULT_BUFFERSIZE || i == dataLen - 1) {
                subDataLoop++;
                if (subDataLoop != 1) {
                    for (byte b : DEFAULT_SPLIT) {
                        allBytes.add(b);
                    }
                }
                byte[] encryptBytes = encryptByPublicKey(buf, publicKey);
                for (byte b : encryptBytes) {
                    allBytes.add(b);
                }
                bufIndex = 0;
                if (i == dataLen - 1) {
                    buf = null;
                } else {
                    buf = new byte[Math.min(DEFAULT_BUFFERSIZE, dataLen - i - 1)];
                }
            }
        }
        byte[] bytes = new byte[allBytes.size()];
        {
            int i = 0;
            for (Byte b : allBytes) {
                bytes[i++] = b.byteValue();
            }
        }
        return bytes;
    }

	/**
	 * 私钥加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param privateKey
	 *            密钥
	 * @return byte[] 加密数据
	 */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKey)
			throws Exception {
		// 得到私钥
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory kf = KeyFactory.getInstance(RSA);
		PrivateKey keyPrivate = kf.generatePrivate(keySpec);
		// 数据加密
		Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
		cipher.init(Cipher.ENCRYPT_MODE, keyPrivate);
		return cipher.doFinal(data);
	}
	
	/**
     * 私钥分段加密，
     * 对encryptByPrivateKey的完善；
     * @param data       要加密的原始数据
     * @param privateKey 秘钥
     */
    public static byte[] encryptByPrivateKeyForSpilt(byte[] data, byte[] privateKey) throws Exception {
        int dataLen = data.length;
        if (dataLen <= DEFAULT_BUFFERSIZE) {
            return encryptByPrivateKey(data, privateKey);
        }
        List<Byte> allBytes = new ArrayList<Byte>(2048);
        int bufIndex = 0;
        int subDataLoop = 0;
        byte[] buf = new byte[DEFAULT_BUFFERSIZE];
        for (int i = 0; i < dataLen; i++) {
            buf[bufIndex] = data[i];
            if (++bufIndex == DEFAULT_BUFFERSIZE || i == dataLen - 1) {
                subDataLoop++;
                if (subDataLoop != 1) {
                    for (byte b : DEFAULT_SPLIT) {
                        allBytes.add(b);
                    }
                }
                byte[] encryptBytes = encryptByPrivateKey(buf, privateKey);
                for (byte b : encryptBytes) {
                    allBytes.add(b);
                }
                bufIndex = 0;
                if (i == dataLen - 1) {
                    buf = null;
                } else {
                    buf = new byte[Math.min(DEFAULT_BUFFERSIZE, dataLen - i - 1)];
                }
            }
        }
        byte[] bytes = new byte[allBytes.size()];
        {
            int i = 0;
            for (Byte b : allBytes) {
                bytes[i++] = b.byteValue();
            }
        }
        return bytes;
    }
	

	/**
	 * 公钥解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param publicKey
	 *            密钥
	 * @return byte[] 解密数据
	 */
	public static byte[] decryptByPublicKey(byte[] data, byte[] publicKey)
			throws Exception {
		// 得到公钥
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
		KeyFactory kf = KeyFactory.getInstance(RSA);
		PublicKey keyPublic = kf.generatePublic(keySpec);
		// 数据解密
		Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
		cipher.init(Cipher.DECRYPT_MODE, keyPublic);
		return cipher.doFinal(data);
	}
	
	/**
     * 公钥分段解密，
     * 对decryptByPublicKey的完善；
     * @param encrypted 待解密数据
     * @param publicKey 密钥
     */
    public static byte[] decryptByPublicKeyForSpilt(byte[] encrypted, byte[] publicKey) throws Exception {
        int splitLen = DEFAULT_SPLIT.length;
        if (splitLen <= 0) {
            return decryptByPublicKey(encrypted, publicKey);
        }
        int dataLen = encrypted.length;
        List<Byte> allBytes = new ArrayList<Byte>(1024);
        int latestStartIndex = 0;
        for (int i = 0; i < dataLen; i++) {
            byte bt = encrypted[i];
            boolean isMatchSplit = false;
            if (i == dataLen - 1) {
                // 到data的最后了
                byte[] part = new byte[dataLen - latestStartIndex];
                System.arraycopy(encrypted, latestStartIndex, part, 0, part.length);
                byte[] decryptPart = decryptByPublicKey(part, publicKey);
                for (byte b : decryptPart) {
                    allBytes.add(b);
                }
                latestStartIndex = i + splitLen;
                i = latestStartIndex - 1;
            } else if (bt == DEFAULT_SPLIT[0]) {
                // 这个是以split[0]开头
                if (splitLen > 1) {
                    if (i + splitLen < dataLen) {
                        // 没有超出data的范围
                        for (int j = 1; j < splitLen; j++) {
                            if (DEFAULT_SPLIT[j] != encrypted[i + j]) {
                                break;
                            }
                            if (j == splitLen - 1) {
                                // 验证到split的最后一位，都没有break，则表明已经确认是split段
                                isMatchSplit = true;
                            }
                        }
                    }
                } else {
                    // split只有一位，则已经匹配了
                    isMatchSplit = true;
                }
            }
            if (isMatchSplit) {
                byte[] part = new byte[i - latestStartIndex];
                System.arraycopy(encrypted, latestStartIndex, part, 0, part.length);
                byte[] decryptPart = decryptByPublicKey(part, publicKey);
                for (byte b : decryptPart) {
                    allBytes.add(b);
                }
                latestStartIndex = i + splitLen;
                i = latestStartIndex - 1;
            }
        }
        byte[] bytes = new byte[allBytes.size()];
        {
            int i = 0;
            for (Byte b : allBytes) {
                bytes[i++] = b.byteValue();
            }
        }
        return bytes;
    }

	/**
	 * 使用私钥进行解密
	 */
	public static byte[] decryptByPrivateKey(byte[] encrypted, byte[] privateKey)
			throws Exception {
		// 得到私钥
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory kf = KeyFactory.getInstance(RSA);
		PrivateKey keyPrivate = kf.generatePrivate(keySpec);

		// 解密数据
		Cipher cp = Cipher.getInstance(ECB_PKCS1_PADDING);
		cp.init(Cipher.DECRYPT_MODE, keyPrivate);
		byte[] arr = cp.doFinal(encrypted);
		return arr;
	}
	
	/**
     * 使用私钥分段解密，
     * 对decryptByPrivateKey的完善；
     *
     */
    public static byte[] decryptByPrivateKeyForSpilt(byte[] encrypted, byte[] privateKey) throws Exception {
        int splitLen = DEFAULT_SPLIT.length;
        if (splitLen <= 0) {
            return decryptByPrivateKey(encrypted, privateKey);
        }
        int dataLen = encrypted.length;
        List<Byte> allBytes = new ArrayList<Byte>(1024);
        int latestStartIndex = 0;
        for (int i = 0; i < dataLen; i++) {
            byte bt = encrypted[i];
            boolean isMatchSplit = false;
            if (i == dataLen - 1) {
                // 到data的最后了
                byte[] part = new byte[dataLen - latestStartIndex];
                System.arraycopy(encrypted, latestStartIndex, part, 0, part.length);
                byte[] decryptPart = decryptByPrivateKey(part, privateKey);
                for (byte b : decryptPart) {
                    allBytes.add(b);
                }
                latestStartIndex = i + splitLen;
                i = latestStartIndex - 1;
            } else if (bt == DEFAULT_SPLIT[0]) {
                // 这个是以split[0]开头
                if (splitLen > 1) {
                    if (i + splitLen < dataLen) {
                        // 没有超出data的范围
                        for (int j = 1; j < splitLen; j++) {
                            if (DEFAULT_SPLIT[j] != encrypted[i + j]) {
                                break;
                            }
                            if (j == splitLen - 1) {
                                // 验证到split的最后一位，都没有break，则表明已经确认是split段
                                isMatchSplit = true;
                            }
                        }
                    }
                } else {
                    // split只有一位，则已经匹配了
                    isMatchSplit = true;
                }
            }
            if (isMatchSplit) {
                byte[] part = new byte[i - latestStartIndex];
                System.arraycopy(encrypted, latestStartIndex, part, 0, part.length);
                byte[] decryptPart = decryptByPrivateKey(part, privateKey);
                for (byte b : decryptPart) {
                    allBytes.add(b);
                }
                latestStartIndex = i + splitLen;
                i = latestStartIndex - 1;
            }
        }
        byte[] bytes = new byte[allBytes.size()];
        {
            int i = 0;
            for (Byte b : allBytes) {
                bytes[i++] = b.byteValue();
            }
        }
        return bytes;
    }
    
    //Rsa加密解密测试
    public static  void TestRsa(){
        String jsonData = "xhao2017";
        KeyPair keyPair=RsaCrypt.generateRSAKeyPair(RsaCrypt.DEFAULT_KEY_SIZE);
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();// 公钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();// 私钥
        MyLog.i("公钥："+publicKey+";\n私钥："+privateKey);

        //公钥加密
        byte[] encryptBytes = null;
        try {
            encryptBytes = RsaCrypt.encryptByPublicKeyForSpilt(jsonData.getBytes(),publicKey.getEncoded());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String encryStr=Base64.encodeToString(encryptBytes, Base64.DEFAULT);
        String str =new String(encryptBytes);//二进制直接转换成成String，会乱码
        MyLog.i("Rsa加密后encryStr:"+encryStr);

        //私钥解密
        byte[] decryptBytes = null;
		try {
			decryptBytes = RsaCrypt.decryptByPrivateKeyForSpilt(Base64.decode(encryStr.getBytes(), Base64.DEFAULT),privateKey.getEncoded());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        String decryStr=new String(decryptBytes);
        MyLog.i("Rsa解密后decryStr:"+decryStr);
	}

}
