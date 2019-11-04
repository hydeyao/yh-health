package com.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.entity.Result;
import com.itcast.pojo.Setmeal;
import com.itcast.service.SetMealService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.itcast.constant.MessageConstant.*;

/**
 * @author ：yh
 * @version :1.0.1
 * @date ：Created in 2019/10/11 9:44
 * @description ：套餐Controller
 */
@RequestMapping("setMeal")
@RestController("setMealController")
public class SetMealController {

    @Reference
    private SetMealService setMealService;

    /**
     * 添加
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("addMeal")
    public Result addMeal(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setMealService.addSetMeal(setmeal, checkgroupIds);
            return new Result(true, ADD_SETMEAL_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, ADD_SETMEAL_FAIL);
        }
    }

    /**
     * 条件分页
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = setMealService.pageQuery(queryPageBean);
        return pageResult;
    }

    /**
     * 删除套餐
     *
     * @param id
     * @param part
     * @return
     */
    @RequestMapping("/delMeal")
    public Result delMeal(Integer id, String part) {
        try {
            setMealService.delMeal(id, part);
            return new Result(true, DELETE_MEAL_SUCCESS);
        } catch (Exception e) {
            return new Result(false, DELETE_MEAL_FAIL);
        }

    }

    /**
     * 根据id返回setMeal视图
     *
     * @param id
     * @return
     */
    @RequestMapping("setMealView")
    public Result setMealView(Integer id) {
        Setmeal setmeal = setMealService.setMealView(id);
        if (setmeal != null) {
            return new Result(true, QUERY_SETMEAL_SUCCESS, setmeal);
        }
        return new Result(false, QUERY_CHECKGROUP_FAIL);
    }

    /**
     * 修改套餐
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("updateMeal")
    public Result updateMeal(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setMealService.updateMeal(setmeal, checkgroupIds);
            return new Result(true, "修改套餐信息成功");
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, "修改套餐信息失败,请稍后重试");
        }
    }

    /**
     * 根据id查找套餐关联的检查组项
     *
     * @param setMealId
     * @return
     */
    @RequestMapping("findRelationCount")
    public Result findRelationCount(Integer setMealId) {
        try {
            List<Integer> relationCount = setMealService.findRelationCount(setMealId);
            return new Result(true, QUERY_CHECKGROUP_SUCCESS, relationCount);
        } catch (Exception e) {
            return new Result(false, QUERY_CHECKGROUP_FAIL);
        }
    }

}