package com.jameschen.comm.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class RegexUtils {
    public static boolean isIdOk(String Idstr) {
        //account
        String regex = "[\u4e00-\u9fa5_a-zA-Z0-9_]{2,30}";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(Idstr).matches();
    }

    public static boolean isPasswordOk(String psdstr) {
        //密码： 长度6-20，由除了空格以外的ASCII码组成
        if (TextUtils.isEmpty(psdstr) || psdstr.length() < 6 || psdstr.length() > 20) {
            return false;
        }
        String regex = "^[\\u0000-\\u001F\\u0021-\\u007F]*$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(psdstr).matches();
    }


    public static boolean isEmailOk(String s) {
        String regex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(s).matches();
    }

    public static boolean isAPhoneNumber(String s) {
        //.电话号码： 国家码+ 本地 . 其中本地长度5-12
        String regex = "^\\d{5,12}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(s).matches();
    }

    public static boolean isOTPOK(String otp_code) {
        if (TextUtils.isEmpty(otp_code)) {
            return false;
        }

        return TextUtils.isDigitsOnly(otp_code) && (otp_code.length() == 4);
    }
}
