package com.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.service.PermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/22 14:57
 * @description ：
 */
@RequestMapping("permission")
@RestController("permissionController")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = permissionService.findPage(queryPageBean);
        return pageResult;
    }

}
