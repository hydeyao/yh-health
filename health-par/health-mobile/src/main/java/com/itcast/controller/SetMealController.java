package com.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.entity.Result;
import com.itcast.pojo.Setmeal;
import com.itcast.service.SetMealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.itcast.constant.MessageConstant.*;


/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/15 10:20
 * @description ：
 */
@RequestMapping("setMeal")
@RestController("SetMealController")
public class SetMealController {


    @Reference
    private SetMealService setMealService;

    /**
     * 获取套餐列表
     * @return
     */
    @RequestMapping("getSetmeal")
    public Result getSetmeal(){
        try {
            List<Setmeal> setmealList = setMealService.findAll();
            return new Result(true, QUERY_SETMEALLIST_SUCCESS, setmealList);
        } catch (Exception e) {
            return new Result(false,QUERY_SETMEALLIST_FAIL);
        }
    }

    /**
     * 通过id查询套餐详细
     * @param id
     * @return
     */
    @RequestMapping("findById")
    public Result findById(Integer id){

        try {
            Setmeal setmeal = setMealService.findAllInfoById(id);
            return new Result(true,QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            return new Result(false,QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 套餐视图
     * @param id
     * @return
     */
    @RequestMapping("setMealView")
    public Result setMealView(Integer id){
        try {
            Setmeal setmeal = setMealService.setMealView(id);
            return new Result(true,QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,QUERY_SETMEAL_FAIL);
        }

    }
}
