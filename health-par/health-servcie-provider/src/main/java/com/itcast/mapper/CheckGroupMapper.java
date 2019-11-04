package com.itcast.mapper;

import com.github.pagehelper.Page;
import com.itcast.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

/**
 * @author ：yh
 * @date ：Created in 2019/10/9 10:07
 * @description ：CheckGroupMapper
 */
public interface CheckGroupMapper {

    /**
     * 添加检查组
     *
     * @param checkGroup
     */
    void addCheckGroup(CheckGroup checkGroup);

    /**
     * 设置检查组检查项关系
     *
     * @param map
     */
    void setCheckGroupCheckItem(Map map);

    /**
     * 根据检查组名查询
     *
     * @param name
     * @return
     */
    Integer findByName(String name);

    /**
     * 根据检查组编码
     *
     * @param code
     * @return
     */
    Integer findByCode(String code);

    /**
     * 条件查询
     *
     * @param queryString
     * @return Page
     */
    Page<CheckGroup> findByCondition(String queryString);

    /**
     * 根据id查询t_checkgroup表
     *
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 根据CheckGroupId查询与CheckItem的关系
     *
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdCheckItemId(Integer id);

    /**
     * 修改检查组
     *
     * @param checkGroup
     */
    void updateCheckGroup(CheckGroup checkGroup);

    /**
     * 根据checkGroupId删除关系
     *
     * @param id
     */
    void delRelation(Integer id);

    /**
     * 根据id判断是否重复
     *
     * @param checkGroup
     * @return
     */
    Integer findEditByName(CheckGroup checkGroup);


    /**
     * 根据id删除checkGroup
     *
     * @param id
     */
    void delCheckGroup(Integer id);


    /**
     * 查询所有
     *
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 查找CheckGroupId在是否有关联
     * @param checkGroupId
     * @return
     */
    Integer findCheckGroupIdCount(Integer checkGroupId);


    /**
     * 根据setMealId查询checkGroup项
     * @param id
     * @return
     */
    List<CheckGroup> findAllInfoById(Integer id);


}
