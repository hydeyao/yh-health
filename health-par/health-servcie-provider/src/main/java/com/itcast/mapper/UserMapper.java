package com.itcast.mapper;

import com.github.pagehelper.Page;
import com.itcast.pojo.User;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/19 21:22
 * @description ：
 */
public interface UserMapper {

    /**
     * 根据用户名查找
     *
     * @param userName
     * @return
     */
    User findByUserName(String userName);

    /**
     * 根据用户名查找详细
     *
     * @param userName
     * @return
     */
    User findAllByUserName(String userName);

    /**
     * 条件分页查询用户
     * @param queryStr
     * @return
     */
    Page<User> findAllByCondition(String queryStr);

}
