package com.itcast.mapper;

import com.github.pagehelper.Page;
import com.itcast.pojo.Permission;

import java.util.Set;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/20 8:54
 * @description ：
 */
public interface PermissionMapper {

    /**
     * 根据角色id查找对应权限
     * @param roleId
     * @return
     */
    Set<Permission> findByRoleId(Integer roleId);


    /**
     * 条件分页
     * @param queryStr
     * @return
     */
    Page<Permission> findPageByCondition(String queryStr);

}
