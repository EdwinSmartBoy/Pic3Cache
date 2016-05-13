package com.example.edwin.pic3cache.Pic3CacheUtils;

import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * 对文件进行MD5加密的工具类
 */
public class Md5Util {

	/*base64 sha-1 AES*/

	/**
	 * 提取文件特征码
	 * @param path
	 * @return
	 */
	public static String encodeFile(String path){
		
		try {
			FileInputStream in = new FileInputStream(path);
			MessageDigest digester = MessageDigest.getInstance("MD5");
			  byte[] bytes = new byte[8192];
			  int byteCount;
			  while ((byteCount = in.read(bytes)) > 0) {
			    digester.update(bytes, 0, byteCount);
			  }
			  byte[] digest = digester.digest();
			  
			  StringBuffer stringBuffer = new StringBuffer();
			  for (byte b : digest) {
//				  123------9 , a, b, c, ...f
				String s = Integer.toHexString(b&0xff);
				if(s.length() == 1){
					s="0"+s;
				}
				stringBuffer.append(s);
			  }
			  return stringBuffer.toString();
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 对字符串进行MD5加密
	 * @param str
	 * @return
	 */
	public static String encodeStr(String str){
		try {
			MessageDigest digester = MessageDigest.getInstance("MD5");
			   digester.update(str.getBytes(), 0, str.length());
			  byte[] digest = digester.digest();
			  
			  StringBuffer stringBuffer = new StringBuffer();
			  for (byte b : digest) {
//				  123------9 , a, b, c, ...f
				String s = Integer.toHexString(b&0xff);
				if(s.length() == 1){
					s="0"+s;
				}
				stringBuffer.append(s);
			  }
			  return stringBuffer.toString();
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
