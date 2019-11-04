package com.itcast.service;

import com.itcast.entity.PageResult;
import com.itcast.pojo.CheckGroup;

import java.util.List;

/**
 * @author ：yh
 * @date ：Created in 2019/10/9 10:07
 * @description ：CheckGroupService
 */
public interface CheckGroupService {

    /**
     * 新增检查组
     * @param checkGroup
     * @param checkItemIds
     */
    void addCheckGroup(CheckGroup checkGroup,Integer[] checkItemIds);

    /**
     * 设置检查组和检查项关系
     * @param checkGroupId
     * @param checkItemIds
     */
    void setCheckGroupCheckItem(Integer checkGroupId,Integer[] checkItemIds);

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult pageQuery(Integer currentPage,Integer pageSize,String queryString);

    /**
     * 根据checkGroupId查询
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 根据CheckGroupId查询与CheckItem的关系
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdCheckItemId(Integer id);

    /**
     * 更新checkGroup 并且更新和checkItem关系
     * @param checkGroup
     * @param checkItemIds
     */
    void update(CheckGroup checkGroup,Integer[] checkItemIds);

    /**
     * 根据id删除checkGroup并删除关系
     *
     * @param id
     */
    void delCheckGroup(Integer id);

    /**
     * 查询所有
     * @return
     */
    List<CheckGroup> findAll();

}
