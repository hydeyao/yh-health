package com.itcast.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.mapper.OrderSettingMapper;
import com.itcast.pojo.OrderSetting;
import com.itcast.service.OrderSettingService;
import com.itcast.utils.HealthPageUtils;
import com.itcast.utils.HealthStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：yh
 * @version :1.0.1
 * @date ：Created in 2019/10/14 14:18
 * @description ：OrderSettingService
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    /**
     * 添加预约信息
     * @param orderSettingList
     */
    @Override
    public void add(List<OrderSetting> orderSettingList) {
        for (OrderSetting orderSetting : orderSettingList) {
            Date orderDate = orderSetting.getOrderDate();
            Integer countByDate = orderSettingMapper.findCountByDate(orderDate);
            if (countByDate>0){
                //如果日期存在,则修改该日期对应的人数
                orderSettingMapper.editOrderSetting(orderSetting);
            }else {
                //日期不存在,则添加
                orderSettingMapper.add(orderSetting);
            }
        }
    }

    /**
     * 根据日期按月份查找
     * @param dateStr
     * @return
     */
    @Override
    public List<Map> findByMonth(String dateStr) {
        String startDate = dateStr + "-01";
        String endDate = dateStr + "-31";

        Map map = new HashMap();
        map.put("startDate",startDate);
        map.put("endDate",endDate);

        List<Map> mapList = orderSettingMapper.findByDateWithMonth(map);
        return mapList;
    }

    /**
     * 修改当前日期的预约人数
     * @param orderSetting
     */
    @Override
    public void updateByOrderDate(OrderSetting orderSetting) {
        Integer count = orderSettingMapper.findCountByDate(orderSetting.getOrderDate());
        if (count>0){
            orderSettingMapper.editOrderSetting(orderSetting);
        }else {
            orderSettingMapper.add(orderSetting);
        }

    }

    /**
     * 日期模糊查询列表
     * @return
     */
    @Override
    public List<OrderSetting> findOrderSettingList(QueryPageBean queryPageBean) {

        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();

        currentPage = HealthPageUtils.judgeCurrentPage(currentPage);
        pageSize = HealthPageUtils.judgePagesize(pageSize);

        PageHelper.startPage(currentPage,pageSize);

        List<OrderSetting> list = orderSettingMapper.findOrderSettingList();

        return list;
    }

    @Override
    public Long findOrderSettingCount() {
        return orderSettingMapper.findOrderSettingCount();
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPageByCondition(QueryPageBean queryPageBean) {

        Integer currentPage = HealthPageUtils.judgeCurrentPage(queryPageBean.getCurrentPage());
        Integer pageSize = HealthPageUtils.judgePagesize(queryPageBean.getPageSize());

        String queryString = queryPageBean.getQueryString();

        if (HealthStringUtils.isNotEmpty(queryString)){
            queryString = HealthPageUtils.fuzzyQueryString(queryString);
        }

        PageHelper.startPage(currentPage,pageSize);
        Page<OrderSetting> page = orderSettingMapper.findByDateCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
}
