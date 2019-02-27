package com.xintai.xhao.safe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.util.Base64;
import android.util.Log;

/**
 * 1.ASCII（American Standard Code for Information Interchange，美国信息交换标准代码）,前127被iso(国际标准化组织)，定为国际标准
 * 2.在计算机中任何数据都是按ascii码存储的，而ascii码的128～255之间的值是不可见字符。
 * 而在网络上交换数据时，比如说从A地传到B地，往往要经过多个路由设备，由于不同的设备对字符的处理方式有一些不同，
 * 这样那些不可见字符就有可能被处理错误，这是不利于传输的。
 * 所以就先把数据先做一个Base64编码，统统变成可见字符，这样出错的可能性就大降低了。
 * 3.标准Base64只有64个字符（英文大小写52个、数字0-9和+、/）以及用作后缀等号；
 * 
 * @author xionghao
 *
 */
public class Bas64Crypt {
	
	/**
	 * 无论是编码还是解码都会有一个参数Flags，Android提供了以下几种
    DEFAULT 这个参数是默认，使用默认的方法来加密
    NO_PADDING 这个参数是略去加密字符串最后的”=”
    NO_WRAP 这个参数意思是略去所有的换行符（设置后CRLF就没用了）
    CRLF 这个参数看起来比较眼熟，它就是Win风格的换行符，意思就是使用CR LF这一对作为一行的结尾而不是Unix风格的LF
    URL_SAFE 这个参数意思是加密时不使用对URL和文件名有特殊意义的字符来作为加密字符，具体就是以-和_取代+和/
	 */
	
	/**
	 * 对字符串进行Base64编码
	 * @param initStr 转码前字符串
	 * @return
	 */
	public static String encodeString(String initStr){
		String encodedString = Base64.encodeToString(initStr.getBytes(), Base64.DEFAULT);
		return encodedString;
	}
	
	/**
	 * 对字符串进行Base64解码
	 * @param encodedString 转码后字符串
	 * @return
	 */
	public static String decodeString(String encodedString){
		String initStr = new String(Base64.decode(encodedString.getBytes(), Base64.DEFAULT));
		return initStr;
	}
	
	/**
	 * 对文件进行Base64编码，需求少见，待随机应变；
	 * @param file 转码前文件
	 * @return
	 */
	public static String encodeFile(File file){
		String encodedString = "" ;
//		File file = new File("/storage/emulated/0/pimsecure_debug.txt");
		FileInputStream inputFile = null;
		try {
		    inputFile = new FileInputStream(file);
		    byte[] buffer = new byte[(int) file.length()];
		    inputFile.read(buffer);
		    inputFile.close();
		    encodedString = Base64.encodeToString(buffer, Base64.DEFAULT);
		    Log.i("xhao", "Base64---->" + encodedString);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return encodedString;
	}
	
	/**
	 * dui文件进行Base64解码，需求少见，待随机应变；
	 * @param desFile 转码后文件
	 * @return
	 */
	public static void decodeFile(File desFile){
		String encodedString = "" ;
//		File desFile = new File("/storage/emulated/0/pimsecure_debug_1.txt");
		FileOutputStream  fos = null;
		try {
		    byte[] decodeBytes = Base64.decode(encodedString.getBytes(), Base64.DEFAULT);
		    fos = new FileOutputStream(desFile);
		    fos.write(decodeBytes);
		    fos.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	

}
