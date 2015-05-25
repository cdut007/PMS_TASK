package com.jameschen.comm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static String inputStream2String(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}
	public static String replaceBlank(String str) {
	    String dest = "";
	    if (str!=null) {
	        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
	        Matcher m = p.matcher(str);
	        dest = m.replaceAll("");
	    }
	    return dest;
	}
}
