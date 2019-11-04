package com.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.service.MemberService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/22 14:25
 * @description ：
 */
@RequestMapping("member")
@RestController("memberController")
public class MemberController {

    @Reference
    private MemberService memberService;

    @RequestMapping("findAll")
    public PageResult findAll(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = memberService.findPageByCondition(queryPageBean);
        return pageResult;
    }

}
