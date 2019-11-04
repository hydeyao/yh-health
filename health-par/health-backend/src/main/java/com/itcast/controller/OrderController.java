package com.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/23 17:06
 * @description ：
 */
@RequestMapping("order")
@RestController("orderController")
public class OrderController {

    @Reference
    private OrderService orderService;

    /**
     * 预约分页
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findAllOrder")
    public PageResult findAllOrder(@RequestBody QueryPageBean queryPageBean) {
        return orderService.findOrderAll(queryPageBean);
    }


}
