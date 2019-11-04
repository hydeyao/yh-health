package com.itcast.mapper;

import com.github.pagehelper.Page;
import com.itcast.pojo.CheckItem;

import java.util.List;

/**
 * @author ：yh
 * @date ：Created in 2019/10/9 10:07
 * @description ：CheckItemMapper
 */
public interface CheckItemMapper {

    /**
     * 添加检查项
     *
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 条件查询
     *
     * @param queryString
     * @return
     */
    Page<CheckItem> findByCondition(String queryString);

    /**
     * 根据id删除
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id判断检查项是否重复
     *
     * @param id
     * @return
     */
    Long findCountByCheckItemId(Integer id);

    /**
     * 根据id查询检查项
     *
     * @param id
     * @return
     */
    CheckItem findById(Integer id);

    /**
     * 修改
     *
     * @param checkItem
     */
    void update(CheckItem checkItem);

    /**
     * 根据code判断检查项是否重复
     *
     * @param code
     * @return
     */
    Integer findCodeCount(String code);

    /**
     * 根据name判断检查项是否重复
     *
     * @param name
     * @return
     */
    Integer findNameCount(String name);

    /**
     * 查询所有
     *
     * @return
     */
    List<CheckItem> findAll();


    /**
     * 根据checkGroupId查询checkItem项
     *
     * @param id
     * @return
     */
    List<CheckItem> findAllInfoById(Integer id);
}
