package com.itcast.service;

import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/22 14:51
 * @description ：
 */
public interface PermissionService {

    /**
     * 条件分页
     * @param queryPageBean
     * @return
     */
    PageResult findPage(QueryPageBean queryPageBean);

}
