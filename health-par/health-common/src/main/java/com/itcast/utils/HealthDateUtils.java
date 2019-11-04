package com.itcast.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/14 15:49
 * @description ：日期工具类
 */
public class HealthDateUtils {


    public static Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse(dateString);
        return date;
    }

    public static String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String format = sdf.format(date);
        return format;
    }


}
