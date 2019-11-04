package com.itcast.service;

import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.pojo.User;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/20 10:08
 * @description ：
 */
public interface UserService {

    /**
     * 根据用户名查询用户角色权限
     * @param userName
     * @return
     */
    User findAllDetailByUserName(String userName);

    /**
     * 分页条件查询用户
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

}
