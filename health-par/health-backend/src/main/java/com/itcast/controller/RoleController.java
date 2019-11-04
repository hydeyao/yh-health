package com.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/22 15:49
 * @description ：
 */
@RequestMapping("role")
@RestController("roleController")
public class RoleController {

    @Reference
    private RoleService roleService;

    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return roleService.findPage(queryPageBean);
    }

}
