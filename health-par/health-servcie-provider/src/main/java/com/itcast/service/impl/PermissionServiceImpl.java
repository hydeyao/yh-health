package com.itcast.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.mapper.PermissionMapper;
import com.itcast.pojo.Permission;
import com.itcast.service.PermissionService;
import com.itcast.utils.HealthPageUtils;
import com.itcast.utils.HealthStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/22 14:51
 * @description ：
 */
@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;


    /**
     * 条件分页
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        Integer currentPage = HealthPageUtils.judgeCurrentPage(queryPageBean.getCurrentPage());
        Integer pagesize = HealthPageUtils.judgePagesize(queryPageBean.getPageSize());

        String queryString = queryPageBean.getQueryString();

        if (HealthStringUtils.isNotEmpty(queryString)){
            queryString = "%" + queryString.trim() + "%";
        }

        PageHelper.startPage(currentPage,pagesize);
        Page<Permission> page = permissionMapper.findPageByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
}
