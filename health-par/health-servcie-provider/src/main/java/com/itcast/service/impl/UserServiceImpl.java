package com.itcast.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.mapper.UserMapper;
import com.itcast.pojo.User;
import com.itcast.service.UserService;
import com.itcast.utils.HealthPageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/20 10:10
 * @description ：
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findAllDetailByUserName(String userName) {
        return userMapper.findAllByUserName(userName);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        queryPageBean = HealthPageUtils.handleQuery(queryPageBean);
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<User> page = userMapper.findAllByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }
}
