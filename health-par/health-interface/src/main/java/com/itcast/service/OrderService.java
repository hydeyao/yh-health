package com.itcast.service;

import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.entity.Result;

import java.util.List;
import java.util.Map;

/**
 * @author yh
 * @version 1.0.1
 * @description
 *
 */
public interface OrderService {
    /**
     * 预约服务
     * @param map
     * @return
     * @throws Exception
     */
    Result order(Map map) throws Exception;

    /**
     * 根据id查询
     * @param id
     * @return
     * @throws Exception
     */
    Map findById(Integer id) throws Exception;

    /**
     * 查询预约列表
     * @return
     */
    List<Map> findOrderList();


    /**
     * 分页条件查询预约
     * @param queryPageBean
     * @return
     */
    PageResult findOrderAll(QueryPageBean queryPageBean);




}
