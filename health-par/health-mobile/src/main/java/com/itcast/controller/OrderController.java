package com.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.entity.Result;
import com.itcast.service.OrderService;
import com.itcast.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

import static com.itcast.constant.MessageConstant.*;
import static com.itcast.constant.RedisMessageConstant.SENDTYPE_ORDER;
import static com.itcast.pojo.Order.ORDERTYPE_WEIXIN;
import static com.itcast.utils.SMSUtils.ORDER_NOTICE;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/16 14:53
 * @description ：
 */
@RequestMapping("order")
@RestController("orderController")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 提交预约
     * @param map
     * @return
     */
    @RequestMapping("submitOrder")
    public Result submitOrder(@RequestBody Map map){

        String validateCode = (String) map.get("validateCode");
        String phoneNumber = (String) map.get("telephone");
        String codeInRedis = jedisPool.getResource().get(phoneNumber+SENDTYPE_ORDER);
        String orderDate = (String) map.get("orderDate");

        //校验验证码
        if (validateCode!=null&&codeInRedis!=null&&validateCode.equals(codeInRedis)){
            Result result = null;
            try {
                map.put("orderType",ORDERTYPE_WEIXIN);
                result = orderService.order(map);

                //成功发送预约成功短信
                if (result.isFlag()){
                    SMSUtils.sendShortMessage(ORDER_NOTICE,phoneNumber,orderDate);
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false,"预约失败,请稍后重试");
            }finally {
                jedisPool.getResource().del(phoneNumber+SENDTYPE_ORDER);
            }
        }else {
            return new Result(false,VALIDATECODE_ERROR);
        }

    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping("findById")
    public Result findById(Integer id){
        try {
            Map map = orderService.findById(id);
            return new Result(true,QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            return new Result(false,QUERY_ORDER_FAIL);
        }
    }

}
