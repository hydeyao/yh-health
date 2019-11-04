package com.itcast.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.mapper.MenuMapper;
import com.itcast.service.MenuService;
import com.itcast.utils.HealthPageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/21 15:05
 * @description ：
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 查询菜单列表
     *
     * @return
     */
    @Override
    public List<Map> findMenuList() {
        List<Map> menuList = menuMapper.findMenuList();

        return findByParentId(menuList, null);
    }

    /**
     * 根据用户查询菜单
     *
     * @param userName
     * @return
     */
    @Override
    public List<Map> findMenuListByUser(String userName) {
        List<Map> menuListByUser = menuMapper.findMenuListByUser(userName);

        for (Map map : menuListByUser) {
            if (map == null) {
                return menuListByUser;
            }
        }

        return findByParentId(menuListByUser, null);
    }

    /**
     * 分页查询菜单信息
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findMenuByCondition(QueryPageBean queryPageBean) {
        queryPageBean = HealthPageUtils.handleQuery(queryPageBean);
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Map> page = menuMapper.findMenuByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 根据parentId 查询菜单
     *
     * @param menuList
     * @param parentId
     * @return
     */
    private List<Map> findByParentId(List<Map> menuList, Integer parentId) {

        List<Map> result = new ArrayList<>();

        for (Map map : menuList) {

            Integer pid = (Integer) map.get("pid");
            if (pid == null) {
                pid = 0;
            }

            if (parentId == null) {
                parentId = 0;
            }

            if (parentId.equals(pid)) {
                map.put("children", findByParentId(menuList, (Integer) map.get("id")));
                result.add(map);
            }
        }

        return result;
    }

}
