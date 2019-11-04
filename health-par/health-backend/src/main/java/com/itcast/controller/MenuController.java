package com.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.entity.Result;
import com.itcast.service.MenuService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.itcast.constant.MessageConstant.GET_MENU_FAIL;
import static com.itcast.constant.MessageConstant.GET_MENU_SUCCESS;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/21 15:54
 * @description ：
 */
@RequestMapping("menu")
@RestController("menuController")
public class MenuController {

    @Reference
    private MenuService menuService;

    /**
     * 获取菜单列表
     * @return
     */
    @RequestMapping("findMenuList")
    public Result findMenuList() {

        try {
            List<Map> menuList = menuService.findMenuList();
            return new Result(true, "获取菜单成功", menuList);
        } catch (Exception e) {
            return new Result(false, "获取菜单失败");
        }

    }

    /**
     * 根据用户获取对应菜单
     * @param userName
     * @return
     */
    @RequestMapping("findMenuByUser")
    public Result findMenuByUser(String userName) {

        try {
            List<Map> mapList = menuService.findMenuListByUser(userName);
            return new Result(true, GET_MENU_SUCCESS, mapList);
        } catch (Exception e) {
            return new Result(false, GET_MENU_FAIL);
        }
    }

    /**
     * 获取菜单信息
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findMenuPage")
    public PageResult findMenuPage(@RequestBody QueryPageBean queryPageBean) {
        return menuService.findMenuByCondition(queryPageBean);
    }


}
