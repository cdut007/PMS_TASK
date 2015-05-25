package com.jameschen.comm.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.content.Context;
import android.telephony.TelephonyManager;

public class CrypToCfg {
	private static final int CRYP_BIT = 128; // 128,192,256
	private Context context ;
	
	public CrypToCfg(Context context ) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	/**
	 * 加密
	 * @param content 需要加密的内容
	 * @param password加密密码
	 * @return  加密后字符串
	 */
	public static String encrypt(String content, String password) {
		try {
			byte[] enCodeFormat = null;
			try {
				enCodeFormat = getRawKey(password.getBytes());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return parseByte2HexStr(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param seed  密文密码
	 * @return	密钥
	 * @throws Exception
	 */
	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		// SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
		SecureRandom sr = null;
		if (android.os.Build.VERSION.SDK_INT >= 17) {
			sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
		} else {
			sr = SecureRandom.getInstance("SHA1PRNG");
		}
		sr.setSeed(seed);
		kgen.init(CRYP_BIT, sr); // 256 bits or 128 bits,192bits
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	public static String decrypt(String content){
		return decrypt(content, "123456");
	}
	
	public static String encrypt(String content){
		return encrypt(content, "123456");
	}
	/**
	 * 解密
	 * @param content 待解密内容
	 * @param password 解密密钥
	 * @return 解密后字符串
	 */
	public static String decrypt(String content, String password) {
		if (content == null) {
			return null;
		}
		byte[] buffer = parseHexStr2Byte(content);
		try {
			byte[] enCodeFormat = null;
			try {
				enCodeFormat = getRawKey(password.getBytes());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(buffer);
			return new String(result); // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	

}
