package com.itcast.mapper;

import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/21 14:58
 * @description ：
 */
public interface MenuMapper {

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
     * 条件查询菜单信息
     * @param name
     * @return
     */
    Page<Map> findMenuByCondition(String name);
}
