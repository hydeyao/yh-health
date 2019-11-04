package com.itcast.mapper;

import com.github.pagehelper.Page;
import com.itcast.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/14 11:43
 * @description ：
 */
public interface OrderSettingMapper {

    /**
     * 添加预约选项
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 根据日期判断是否存在
     * @param orderDate
     * @return
     */
    Integer findCountByDate(Date orderDate);

    /**
     * 如果日期存在通过日期修改
     * @param orderSetting
     */
    void editOrderSetting(OrderSetting orderSetting);

    /**
     * 通过日期按月查询
     * @param date
     * @return
     */
    List<Map> findByDateWithMonth(Map date);

    /**
     * 通过预约日期查询
     * @param orderDate
     * @return OrderSetting
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 修改当前日期的预约人数
     * @param orderSetting
     */
    void updateByOrderDate(OrderSetting orderSetting);


    /**
     * 查找预约设置列表
     * @return
     */
    List<OrderSetting> findOrderSettingList();

    /**
     * 总数
     * @return
     */
    Long findOrderSettingCount();


    /**
     * 日期模糊查询
     * @param queryStr
     * @return
     */
    Page<OrderSetting>findByDateCondition(String queryStr);




}
