package com.itcast.service;

import com.itcast.entity.PageResult;
import com.itcast.pojo.CheckItem;

import java.util.List;

/**
 * @author ：yh
 * @date ：Created in 2019/10/9 10:07
 * @description ：CheckItemService
 */
public interface CheckItemService {

    /**
     * 添加
     * @param checkItem
     * @throws Exception
     */
    void add(CheckItem checkItem) throws Exception;

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    CheckItem findByItem(Integer id);

    /**
     * 修改 已用editPro代替
     * @param checkItem
     */
    @Deprecated
    void edit(CheckItem checkItem);

    /**
     * 修改
     * @param checkItem
     */
    void editPro(CheckItem checkItem);

    /**
     * 查询所有
     * @return
     */
    List<CheckItem> findAll();

}
