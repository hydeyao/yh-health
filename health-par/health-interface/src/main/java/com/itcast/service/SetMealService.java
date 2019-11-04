package com.itcast.service;

import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author ：yh
 * @version :1.0.1
 * @date ：Created in 2019/10/11 11:10
 * @description ：套餐服务
 */
public interface SetMealService {

    /**
     * 新建套餐
     * @param setmeal
     * @param checkGroupIds
     */
    void addSetMeal(Setmeal setmeal,Integer[] checkGroupIds);

    /**
     * 设置套餐和检查组联系
     * @param setMealId
     * @param checkGroupIds
     */
    void setRelation(Integer setMealId,Integer[] checkGroupIds);

    /**
     * 条件分页
     * @param queryPageBean
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 删除套餐
     * @param id
     * @param part
     */
    void delMeal(Integer id,String part);

    /**
     * 根据id返回setMeal视图
     * @param id
     * @return
     */
    Setmeal setMealView(Integer id);

    /**
     * 更新套餐信息
     * @param setmeal
     * @param checkGroupIds
     */
    void updateMeal(Setmeal setmeal,Integer[] checkGroupIds);

    /**
     * 根据id查找套餐关联的检查组项
     * @param setMealId
     * @return
     */
    List<Integer> findRelationCount(Integer setMealId);

    /**
     * 查询所有
     * @return
     */
    List<Setmeal> findAll();


    /**
     * 根据id查询该套餐及所有子项
     * @param id
     * @return
     */
    Setmeal findAllInfoById(Integer id);


    /**
     * 查找套餐信息及对应的数量
     * @return
     */
    Map<String,Object> findSetMealCount() throws Exception;

    /**
     * 获取套餐数量及对应销售额
     * @return
     */
    Map<String,Object> findCountWithPrice();


}
