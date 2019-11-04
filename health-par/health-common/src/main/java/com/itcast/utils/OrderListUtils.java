package com.itcast.utils;

import com.itcast.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：yh
 * @version :1.0.1
 * @date ：Created in 2019/10/14 11:22
 * @description ：OrderSettingList转换工具类
 */
public class OrderListUtils {

    /**
     * List<String[] 转换 List<OrderSetting>
     *
     * @param stringList
     * @return List<OrderSetting>
     */
    public static List<OrderSetting> conventTo(List<String[]> stringList) {
        List<OrderSetting> orderSettingList = new ArrayList<>();

        for (String[] strings : stringList) {
            String OrderDate = strings[0];
            String number = strings[1];
            OrderSetting orderSetting = new OrderSetting(new Date(OrderDate), Integer.parseInt(number));
            orderSettingList.add(orderSetting);
        }

        return orderSettingList;
    }

}
