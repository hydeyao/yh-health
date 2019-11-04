package com.itcast.mapper;

import com.github.pagehelper.Page;
import com.itcast.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/16 9:56
 * @description ：
 */
public interface OrderMapper {

    /**
     * 动态条件查询
     *
     * @param order
     * @return
     */
    List<Order> findByCondition(Order order);

    /**
     * 添加预约信息
     *
     * @param order
     */
    void add(Order order);


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Order findById(Integer id);

    /**
     * 通过id查询详细
     *
     * @param id
     * @return
     */
    Map findById4Detail(Integer id);

    /**
     * 根据当前时间查询预约数
     * @param dateStr
     * @return
     */
    Integer findCountThisDate(String dateStr);

    /**
     * 查找指定日期后预约数
     * @param dateStr
     * @return
     */
    Integer findCountAfterDate(String dateStr);

    /**
     * 指定日期的到诊数
     * @param dateStr
     * @return
     */
    Integer findVisitedCountByDate(String dateStr);

    /**
     * 指定日期后到诊数
     * @param dateStr
     * @return
     */
    Integer findVisitedCountAfterDate(String dateStr);


    /**
     * 查找各套餐占比
     * @return
     */
    List<Map> findHotSetMeal();

    /**
     * 查询预约列表
     * @return
     */
    List<Map> findOrderList();


    /**
     * 查询预约详细
     * @param queryStr
     * @return
     */
    Page<Map> findOrderAll(String queryStr);





}
