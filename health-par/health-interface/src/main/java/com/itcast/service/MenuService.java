package com.itcast.service;

import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;

import java.util.List;
import java.util.Map;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/21 15:05
 * @description ：
 */
public interface MenuService {

    /**
     * 查询菜单列表
     * @return
     */
    List<Map> findMenuList();

    /**
     * 根据用户查询菜单
     * @param userName
     * @return
     */
    List<Map> findMenuListByUser(String userName);

    /**
     * 分页查询菜单信息
     * @param queryPageBean
     * @return
     */
    PageResult findMenuByCondition(QueryPageBean queryPageBean);

}
