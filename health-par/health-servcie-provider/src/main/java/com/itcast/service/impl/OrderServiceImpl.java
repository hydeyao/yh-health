package com.itcast.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.entity.Result;
import com.itcast.mapper.MemberMapper;
import com.itcast.mapper.OrderMapper;
import com.itcast.mapper.OrderSettingMapper;
import com.itcast.pojo.Member;
import com.itcast.pojo.Order;
import com.itcast.pojo.OrderSetting;
import com.itcast.service.OrderService;
import com.itcast.utils.DateUtils;
import com.itcast.utils.HealthPageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.itcast.constant.MessageConstant.*;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/16 10:42
 * @description ：
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderMapper orderMapper;


    /**
     * 预约服务
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Result order(Map map) throws Exception {

        //姓名
        String name = (String) map.get("name");
        //手机号
        String phoneNumber = (String) map.get("telephone");
        //预约日期
        Date orderDate = DateUtils.parseString2Date((String) map.get("orderDate"));
        //套餐id
        Integer setmealId = Integer.parseInt((String) map.get("setmealId"));
        //身份证号
        String idCard = (String) map.get("idCard");
        //性别
        String sex = (String) map.get("sex");
        //预约类型
        String orderType = (String) map.get("orderType");


        //当前日期是否可预约
        OrderSetting orderSetting = orderSettingMapper.findByOrderDate(orderDate);

        //当前日期不可预约
        if (orderSetting == null) {
            //todo 日期不可预约
            return new Result(false, SELECTED_DATE_CANNOT_ORDER);
        }

        //当前日期人数是否可预约
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        // 人数已满
        if (number <= reservations) {
            return new Result(false, ORDER_FULL);
        }

        //判断是否是会员
        Member member = memberMapper.findByTelephone(phoneNumber);
        String idCardFromDb = member.getIdCard();
        if (member != null && idCardFromDb != null) {
            Order order = new Order(member.getId(), orderDate, setmealId);
            List<Order> orderList = orderMapper.findByCondition(order);

            if (orderList != null && orderList.size() > 0) {
                // 重复预约
                return new Result(false, HAS_ORDERED);
            }
        } else {
            member = new Member();
            member.setName(name);
            member.setPhoneNumber(phoneNumber);
            member.setIdCard(idCard);
            member.setSex(sex);
            member.setRegTime(new Date());

            //会员信息不完善,1:修改
            //会员未注册1：添加
            if (idCardFromDb == null) {
                memberMapper.updateByPhoneNumber(member);
            } else {
                memberMapper.add(member);
            }
        }

        //添加预约
        Order order = new Order();
        order.setOrderDate(orderDate);
        order.setSetmealId(setmealId);
        order.setMemberId(member.getId());
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType(orderType);

        orderMapper.add(order);
        //更新预约人数
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingMapper.updateByOrderDate(orderSetting);
        return new Result(true, ORDER_SUCCESS, order);

    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Map findById(Integer id) throws Exception {

        Map orderMap = orderMapper.findById4Detail(id);
        Date date = (Date) orderMap.get("orderDate");
        String orderDate = DateUtils.parseDate2String(date);
        orderMap.put("orderDate", orderDate);
        return orderMap;
    }

    @Override
    public List<Map> findOrderList() {
        return orderMapper.findOrderList();
    }

    /**
     * 条件分页查询预约
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findOrderAll(QueryPageBean queryPageBean) {
        queryPageBean = HealthPageUtils.handleQuery(queryPageBean);
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Map> page = orderMapper.findOrderAll(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

}
