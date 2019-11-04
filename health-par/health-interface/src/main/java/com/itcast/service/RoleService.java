package com.itcast.service;

import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/22 15:18
 * @description ：
 */
public interface RoleService {

    /**
     * 角色管理条件分页查询
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);
}
