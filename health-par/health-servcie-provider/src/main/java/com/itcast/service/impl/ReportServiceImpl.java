package com.itcast.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itcast.entity.Result;
import com.itcast.mapper.MemberMapper;
import com.itcast.mapper.OrderMapper;
import com.itcast.service.ReportService;
import com.itcast.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/21 9:38
 * @description ：
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private OrderMapper orderMapper;



    /**
     * 获取运营统计数据
     * @return
     */
    @Override
    public Map<String, Object> getBusinessReport() throws Exception {


        String today = DateUtils.parseDate2String(DateUtils.getToday());
        //当前星期第一天
        String monday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //当前月第一天
        String firstDayOfMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

        //今日新增会员
        Integer todayNewMember = memberMapper.findCountThisDate(today);
        //本周新增会员
        Integer thisWeekNewMember = memberMapper.findCountAfterDate(monday);
        //本月新增会员
        Integer thisMonthNewMember = memberMapper.findCountAfterDate(firstDayOfMonth);
        //会员总数
        Integer totalMember = memberMapper.findAllCount();

        //今天预约数
        Integer todayOrderNumber = orderMapper.findCountThisDate(today);
        //本周预约数
        Integer thisWeekOrderNumber = orderMapper.findCountAfterDate(monday);
        //本月预约数
        Integer thisMonthOrderNumber = orderMapper.findCountAfterDate(firstDayOfMonth);

        //今日到诊数
        Integer todayVisitsNumber = orderMapper.findVisitedCountByDate(today);
        //本周到诊数
        Integer thisWeekVisitsNumber = orderMapper.findVisitedCountAfterDate(monday);
        //本月到诊数
        Integer thisMonthVisitsNumber = orderMapper.findVisitedCountAfterDate(firstDayOfMonth);

        //套餐信息
        List<Map> hotSetMeal = orderMapper.findHotSetMeal();

        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("reportDate",today);
        resultMap.put("todayNewMember",todayNewMember);
        resultMap.put("thisWeekNewMember",thisWeekNewMember);
        resultMap.put("thisMonthNewMember",thisMonthNewMember);
        resultMap.put("totalMember",totalMember);
        resultMap.put("todayOrderNumber",todayOrderNumber);
        resultMap.put("thisWeekOrderNumber",thisWeekOrderNumber);
        resultMap.put("thisMonthOrderNumber",thisMonthOrderNumber);
        resultMap.put("todayVisitsNumber",todayVisitsNumber);
        resultMap.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        resultMap.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        resultMap.put("hotSetmeal",hotSetMeal);


        return resultMap;
    }




}
