package com.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itcast.entity.Result;
import com.itcast.pojo.Member;
import com.itcast.service.MemberService;
import com.itcast.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

import static com.itcast.constant.MessageConstant.LOGIN_SUCCESS;
import static com.itcast.constant.MessageConstant.VALIDATECODE_ERROR;
import static com.itcast.constant.RedisMessageConstant.SENDTYPE_LOGIN;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/16 19:55
 * @description ：
 */
@RequestMapping("member")
@RestController("memberController")
public class MemberController {

    @Reference
    private MemberService memberService;

    @Reference
    private ValidateCodeService validateCodeService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("login")
    public Result login(@RequestBody Map map, HttpServletResponse response) {

        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");

        //校验验证码
        if (validateCodeService.checkValidateCode(telephone, validateCode)) {
            Member member = memberService.findByPhoneNumber(telephone);
            if (member == null) {
                member = new Member();
                member.setRegTime(new Date());
                member.setPhoneNumber(telephone);
                memberService.add(member);
            }

            //写入cookie
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 30);
            response.addCookie(cookie);

            //存入redis
            String json = JSON.toJSONString(member);
            jedisPool.getResource().setex("member", 60 * 30, json);
            jedisPool.getResource().del(telephone + SENDTYPE_LOGIN);
            return new Result(true, LOGIN_SUCCESS);

        } else {
            return new Result(false, VALIDATECODE_ERROR);
        }


    }

}
