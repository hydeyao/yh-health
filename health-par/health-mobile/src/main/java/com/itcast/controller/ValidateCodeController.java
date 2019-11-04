package com.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itcast.entity.Result;
import com.itcast.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import static com.itcast.constant.MessageConstant.SEND_VALIDATECODE_FAIL;
import static com.itcast.constant.MessageConstant.SEND_VALIDATECODE_SUCCESS;
import static com.itcast.constant.RedisMessageConstant.SENDTYPE_LOGIN;
import static com.itcast.constant.RedisMessageConstant.SENDTYPE_ORDER;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/15 18:44
 * @description ：
 */
@RequestMapping("validate")
@RestController("validateCodeController")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private ValidateCodeService validateCodeService;

    /**
     * 发送预约验证码
     *
     * @param phoneNumber
     * @return
     */
    @RequestMapping("send4Order")
    public Result send4Order(String phoneNumber) {
        try {
            validateCodeService.sendValidateCode(phoneNumber, 4, SENDTYPE_ORDER, 300);
            return new Result(true, SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            return new Result(false, SEND_VALIDATECODE_FAIL);
        }
    }

    /**
     * 发送登录验证码
     *
     * @param telephone
     * @return
     */
    @RequestMapping("send4Login")
    public Result send4Login(String telephone) {
        try {
            validateCodeService.sendValidateCode(telephone, 6, SENDTYPE_LOGIN, 300);
            return new Result(true, SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            return new Result(false, SEND_VALIDATECODE_FAIL);
        }
    }


}
