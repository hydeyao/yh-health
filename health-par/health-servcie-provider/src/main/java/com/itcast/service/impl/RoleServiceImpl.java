package com.itcast.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.mapper.RoleMapper;
import com.itcast.pojo.Role;
import com.itcast.service.RoleService;
import com.itcast.utils.HealthPageUtils;
import com.itcast.utils.HealthStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/22 15:18
 * @description ：
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 角色管理条件分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        Integer pagesize = HealthPageUtils.judgePagesize(queryPageBean.getPageSize());
        Integer currentPage = HealthPageUtils.judgeCurrentPage(queryPageBean.getCurrentPage());

        String queryString = queryPageBean.getQueryString();
        if (HealthStringUtils.isNotEmpty(queryString)){
        queryString = HealthPageUtils.fuzzyQueryString(queryString);
        }
        PageHelper.startPage(currentPage,pagesize);
        Page<Role> page = roleMapper.findPageByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
}
