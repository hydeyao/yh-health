package com.itcast.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.mapper.SetMealMapper;
import com.itcast.pojo.Setmeal;
import com.itcast.service.SetMealService;
import com.itcast.utils.HealthPageUtils;
import com.itcast.utils.HealthStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.itcast.constant.RedisConstant.SETMEAL_PIC_DB_RESOURCES;

/**
 * @author ：yh
 * @version :1.0.1
 * @date ：Created in 2019/10/11 11:11
 * @description ：套餐服务
 */
@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealMapper setMealMapper;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 新建套餐
     *
     * @param setmeal
     * @param checkGroupIds
     */
    @Override
    public void addSetMeal(Setmeal setmeal, Integer[] checkGroupIds) throws RuntimeException {
        String name = setmeal.getName();
        String code = setmeal.getCode();

        //name,code非空判断
        if (HealthStringUtils.isEmpty(code)) {
            throw new RuntimeException("添加失败,编码不能为空!");
        }
        if (HealthStringUtils.isEmpty(name)) {
            throw new RuntimeException("添加失败,名称不能为空!");
        }

        //name,code唯一性判断
        if (setMealMapper.findByCode(code) > 0) {
            throw new RuntimeException("添加失败,编码已存在!");
        }
        if (setMealMapper.findByName(name) > 0) {
            throw new RuntimeException("添加失败,名称已存在!");
        }


        setMealMapper.addSetMeal(setmeal);
        if (checkGroupIds != null && checkGroupIds.length > 0) {
            setRelation(setmeal.getId(), checkGroupIds);
        }
        add2redis("setMeal/"+setmeal.getImg());

    }

    /**
     * 设置套餐和检查组联系
     *
     * @param setMealId
     * @param checkGroupIds
     */
    @Override
    public void setRelation(Integer setMealId, Integer[] checkGroupIds) {
        Map map = new HashMap<String, Integer>();
        for (Integer checkGroupId : checkGroupIds) {
            map.put("setmeal_id", setMealId);
            map.put("checkgroup_id", checkGroupId);
            setMealMapper.setRelation(map);
        }
    }

    /**
     * 条件分页
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        currentPage= HealthPageUtils.judgeCurrentPage(currentPage);
        pageSize=HealthPageUtils.judgePagesize(pageSize);

        if (HealthStringUtils.isNotEmpty(queryString)) {
            queryString = "%" + queryString + "%";
        }

        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setMealMapper.findByCondition(queryString);

        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 删除套餐
     * @param id
     */
    @Override
    public void delMeal(Integer id,String part) {
        setMealMapper.deleteRelation(id);
        Setmeal setmeal = setMealMapper.findById(id);
        String filePath = "setMeal/"+setmeal.getImg();
        setMealMapper.deleteById(id);
        jedisPool.getResource().srem(SETMEAL_PIC_DB_RESOURCES,filePath);
    }

    /**
     * 根据id返回setMeal视图
     * @param id
     * @return
     */
    @Override
    public Setmeal setMealView(Integer id) {
        Setmeal setmeal = setMealMapper.findById(id);
        return setmeal;
    }

    /**
     * 更新套餐信息
     * @param setmeal
     * @param checkGroupIds
     */
    @Override
    public void updateMeal(Setmeal setmeal, Integer[] checkGroupIds) {
        Integer setMealId = setmeal.getId();

        Setmeal mealFromDB = setMealMapper.findById(setMealId);

        if (!mealFromDB.getName().equals(setmeal.getName())){
            Integer name_count = setMealMapper.findByName(setmeal.getName());
            if (name_count>0){
                throw new RuntimeException("修改失败,套餐名称已存在");
            }
        }

        String setmealImg = setmeal.getImg();
        String mealFromDBImg = mealFromDB.getImg();

        //判断图片信息是否修改,1：更新到redis
        if (!mealFromDBImg.equals(setmealImg)){
            jedisPool.getResource().srem(SETMEAL_PIC_DB_RESOURCES,"setMeal/"+mealFromDBImg);
            add2redis("setMeal/"+setmealImg);
        }

        setMealMapper.updateMeal(setmeal);
        setMealMapper.deleteRelation(setMealId);
        setRelation(setMealId,checkGroupIds);
    }

    /**
     * 根据id查找套餐关联的检查组项
     * @param setMealId
     * @return
     */
    @Override
    public List<Integer> findRelationCount(Integer setMealId) {
        return setMealMapper.findRelationCount(setMealId);
    }

    /**
     * 查询所有套餐
     * @return
     */
    @Override
    public List<Setmeal> findAll() {
        return setMealMapper.findAll();
    }

    /**
     * 根据id查询该套餐及所有子项
     * @param id
     * @return
     */
    @Override
    public Setmeal findAllInfoById(Integer id) {
        return setMealMapper.findAllById(id);
    }

    /**
     * 查找套餐信息及对应的数量
     * @return
     */
    @Override
    public Map<String,Object> findSetMealCount() throws Exception {

        Map<String,Object> resultMap = new HashMap<>();

        List<Map> setMealCount = setMealMapper.findSetMealCount();
        resultMap.put("setmealCount",setMealCount);

        List<String> setmealNames = new ArrayList<>();

        for (Map map : setMealCount) {
            setmealNames.add((String) map.get("name"));
        }

        resultMap.put("setmealNames", setmealNames);
        return resultMap;
    }

    /**
     * 获取套餐数量及对应销售额
     * @return
     */
    @Override
    public Map<String, Object> findCountWithPrice() {
        Map<String,Object> result = new HashMap<>();
        List<String> setmealNames = new ArrayList<>();
        List<Map> setmealCount = new ArrayList<>();
        List<Map> setmealPrice = new ArrayList<>();

        List<Map> countPrice = setMealMapper.findCountPrice();

        for (Map map : countPrice) {
            //封装name
            String name = (String) map.get("name");
            setmealNames.add(name);

            //封装count name
            Map countMap = new HashMap();
            Long count = (Long) map.get("setmeal_count");
            countMap.put("name",name);
            countMap.put("value",count);
            setmealCount.add(countMap);

            //封装 price name
            Map priceMap = new HashMap();
            Double price = (Double) map.get("price");
            priceMap.put("name",name);
            priceMap.put("value",price);
            setmealPrice.add(priceMap);
        }

        result.put("setmealNames",setmealNames);
        result.put("setmealCount",setmealCount);
        result.put("setmealPrice",setmealPrice);

        return result;
    }

    /**
     * 添加到redis
     * @param img
     */
    private void add2redis(String img) {
        jedisPool.getResource().sadd(SETMEAL_PIC_DB_RESOURCES, img);
    }
}
