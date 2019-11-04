package com.itcast.service;

import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/14 14:18
 * @description ：
 */
public interface OrderSettingService {

    /**
     * 添加预约信息
     *
     * @param orderSettingList
     */
    void add(List<OrderSetting> orderSettingList);


    /**
     * 根据日期按月份查找
     *
     * @param dateStr
     * @return
     */
    List<Map> findByMonth(String dateStr);


    /**
     * 修改当前日期的预约人数
     *
     * @param orderSetting
     */
    void updateByOrderDate(OrderSetting orderSetting);


    /**
     * 查找预约设置列表
     * @param queryPageBean
     * @return
     */
    List<OrderSetting> findOrderSettingList(QueryPageBean queryPageBean);

    /**
     * 总数
     * @return
     */
    Long findOrderSettingCount();

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    PageResult findPageByCondition(QueryPageBean queryPageBean);


}
