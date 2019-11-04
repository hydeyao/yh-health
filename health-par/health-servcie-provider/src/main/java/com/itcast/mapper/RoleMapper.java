package com.itcast.mapper;

import com.github.pagehelper.Page;
import com.itcast.pojo.Role;

import java.util.Set;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/20 8:52
 * @description ：
 */
public interface RoleMapper {


    /**
     * 根据用户id查找对应角色
     * @param userId
     * @return
     */
    Set<Role> findByUserId(Integer userId);

    /**
     * 条件分页
     * @param queryStr
     * @return
     */
    Page<Role> findPageByCondition(String queryStr);
}


