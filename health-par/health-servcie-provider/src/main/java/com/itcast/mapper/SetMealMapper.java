package com.itcast.mapper;

import com.github.pagehelper.Page;
import com.itcast.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/11 8:26
 * @description ：
 */
public interface SetMealMapper {

    /**
     * 查询所有套餐
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 新建套餐
     * @param setmeal
     */
    void addSetMeal(Setmeal setmeal);

    /**
     * 设置套餐和检查组关系
     * @param map
     */
    void setRelation(Map map);

    /**
     * code唯一性判断
     * @param code
     * @return
     */
    Integer findByCode(String code);

    /**
     * name唯一性判断
     * @param name
     * @return
     */
    Integer findByName(String name);

    /**
     *条件查询
     * @param queryString
     * @return
     */
    Page<Setmeal> findByCondition(String queryString);


    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id删除联系
     * @param id
     */
    void deleteRelation(Integer id);


    /**
     * 根据id查询套餐信息
     * @param id
     * @return
     */
    Setmeal findById(Integer id);


    /**
     * 更新套餐信息
     * @param setmeal
     */
    void updateMeal(Setmeal setmeal);


    /**
     * 根据id查找套餐关联的检查组项
     * @param setMealId
     * @return
     */
    List<Integer> findRelationCount(Integer setMealId);


    /**
     * 根据id查询套餐全部信息
     * @param id
     * @return
     */
    Setmeal findAllById(Integer id);


    /**
     * 查找套餐对应的数量
     * @return
     */
    List<Map> findSetMealCount();


    /**
     * 获取套餐对应数量及价格信息
     * @return
     */
    List<Map> findCountPrice();




}
