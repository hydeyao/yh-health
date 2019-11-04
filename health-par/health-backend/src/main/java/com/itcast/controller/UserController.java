package com.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.entity.Result;
import com.itcast.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.itcast.constant.MessageConstant.GET_USERNAME_FAIL;
import static com.itcast.constant.MessageConstant.GET_USERNAME_SUCCESS;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/20 14:13
 * @description ：
 */
@RequestMapping("/user")
@RestController("userController")
public class UserController {


    @Reference
    private UserService userService;

    /**
     * 获取用户名
     * @return
     */
    @RequestMapping("/getUserName")
    public Result getUserName(){

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true,GET_USERNAME_SUCCESS,user.getUsername());
        } catch (Exception e) {
            return new Result(false,GET_USERNAME_FAIL);
        }

    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
       return userService.findPage(queryPageBean);
    }


}
