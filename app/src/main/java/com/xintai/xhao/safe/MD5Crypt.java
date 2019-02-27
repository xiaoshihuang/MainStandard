package com.xintai.xhao.safe;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.text.TextUtils;
import android.util.Log;
/**
 * MD5摘要算法（通过摘要的数据，不能得到原始数据），而不是MD5加密
 * http://blog.csdn.net/wf1154439/article/details/44619535
 * 1.虽然说MD5加密本身是不可逆的，但并不是不可破译的，破解机制采用穷举法，就是我们平时说的跑字典，
 * 防止自己的MD5值被穷举出来可以通过使用长且复杂的密码或者进行多次MD5。
 * 2.一个原始数据，只对应一个md5值，但是一个md5值，可能对应多个原始数据。
 * 3.加强破解难度方法：--你破解获取到的是一组数据，但是你这组数据，就不一定是正确的原始数据，
 * 因为，我可以在客户端对原始，进行修改后（包含加盐），在进行（若干次）加密（即你破解的得到的'密码'）；
 * 
 * 4.App开发经常用到MD5加密算法的有：
 * 1.）在登录时，保护用户信息，对用户密码加密，即使系统管理者也不会知道用户的登录密码；
 * 2.）如果不限制同一账号尝试登录次数，那么md5加密将毫无意义，因为穷举密码的难度比穷举密码MD5值难度小多了，别人直接穷举你密码了；
 * 3.）可以用于判断传输的数据是否被篡改：
 * 客户端，对参数进行md5加密，然后对md5值进行公钥加密；
 * 服务器端，对参数进行md5加密，私钥解密获取客户端传递过来的md5值，然后进行对比，看是否一致，就可以判断参数是否被修改过；
 * md5使用场景
 * https://blog.csdn.net/oYiMiYangGuang123/article/details/81773912
 * @author xionghao
 */
public class MD5Crypt {
	/**
	 * 补充知识点：
	 * bit位，一个二进制数据0或1;
	 * byte字节，存储空间的基本计量单位，1 byte = 8 bit;
	 * 1 字母 = 1 byte = 8 bit;
	 * 1 汉字 = 2 byte = 16 bit;
	 *  A>.汉字输入状态下，默认为全角输入方式；
	 *  B>.英文输入状态下，默认为半角输入方式；
	 *  C>.全角输入方式下，标点符号占2字节；
	 *  D>.半角输入方式下，标点符号占1字节；
	 *  一个八进制对应3个二进制，2的n次，(1,2,4,8)；
	 *  八进制的7转换成二进制是111，
	 *  八进制的5转换成二进制是101，
	 *  一个十六进制对应4个二进制，2的n次，(1,2,4,8)；
	 *  十六进制15转换成二进制1111
	 *  十六进制11转换成二进制1011
	 * @param string
	 * @return
	 */
	
	/**
	 * 计算字符串的MD5值
	 * @param string 原料
	 * @return 长度为32的字符串，如果需要长度为16的字符串，截取中间的即可
	 */
	 public static String md5(String string) {
	        if (TextUtils.isEmpty(string)) {
	            return "";
	        }
	        MessageDigest md5 = null;
	        try {
	            md5 = MessageDigest.getInstance("MD5");//调用jdk自带的MD5加密算法进行加密;
	          //无论字符串多长，加密后，md5的长度，默认为128bit，也就是128个0和1的二进制串。
	          //将二进制转成了16进制，每4个bit表示一个16进制,所以128/4 = 32 ;
	            byte[] bytes = md5.digest(string.getBytes());//bytes.length=16,16*8=128bit
	            StringBuffer result = new StringBuffer();
	          //result = new String(bytes);//二进制直接转换String，得到的是乱码
	            for (byte b : bytes) {
//	            	b & 0xff将byte转换成int
//	            	Integer.toHexString()将十进制转换成十六进制，39->27,正数除以2取余
	                String temp = Integer.toHexString(b & 0xff);//Oct八进制，Dec十进制，Hex十六进制
	                if (temp.length() == 1) {
	                    temp = "0" + temp;
	                }
//	                result += temp;
					result.append(temp);
	            }
	            return result.toString();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        return "";
	    }
	 
	 	/**
	 	 * 计算文件的 MD5 值
	 	 * @param file 文件
	 	 * @return
	 	 */
	    public static String md5(File file) {
	        if (file == null || !file.isFile() || !file.exists()) {
	            return "";
	        }
	        FileInputStream in = null;
	        String result = "";
	        byte buffer[] = new byte[8192];
	        int len;
	        try {
	            MessageDigest md5 = MessageDigest.getInstance("MD5");
	            in = new FileInputStream(file);
	            while ((len = in.read(buffer)) != -1) {
	                md5.update(buffer, 0, len);
	            }
	            byte[] bytes = md5.digest();

	            for (byte b : bytes) {
	                String temp = Integer.toHexString(b & 0xff);
	                if (temp.length() == 1) {
	                    temp = "0" + temp;
	                }
	                result += temp;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	            if(null!=in){
	                try {
	                    in.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        return result;
	    }
	    
	   
	   /**
	    * md5加盐多次加密 ，增加被破解的难度；
	    * @param string 密码
	    * @param salt 盐
	    * @param times 加密次数
	    * @return
	    */
	    public static String md5(String string, String salt, int times) {
	        if (TextUtils.isEmpty(string)) {
	            return "";
	        }
	        String md5 = md5(string+salt);
	        for (int i = 0; i < times - 1; i++) {
	            md5 = md5(md5);
	        }
	        return md5;
	    }

}
