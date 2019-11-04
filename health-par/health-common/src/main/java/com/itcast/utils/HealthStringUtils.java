package com.itcast.utils;

/**
 * @author ：yh
 * @version :1.0.1
 * @date ：Created in 2019/10/10 10:35
 * @description ：
 */
public class HealthStringUtils {

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.trim().length()==0){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

}
