package com.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.entity.Result;
import com.itcast.pojo.OrderSetting;
import com.itcast.service.OrderSettingService;
import com.itcast.utils.NewPoiUtils;
import com.itcast.utils.OrderListUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.itcast.constant.MessageConstant.*;


/**
 * @author ：yh
 * @version :1.0.1
 * @date ：Created in 2019/10/14 14:38
 * @description ：OrderSettingController
 */
@RestController("orderSettingController")
@RequestMapping("ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * excelFile 上传并解析
     * @param excelFile
     * @return
     */
    @RequestMapping("upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {

        try {
            List<String[]> strings = NewPoiUtils.readExcel(excelFile.getOriginalFilename(), excelFile.getInputStream());
            List<OrderSetting> orderSettingList = OrderListUtils.conventTo(strings);
            orderSettingService.add(orderSettingList);
            return new Result(true,IMPORT_ORDERSETTING_SUCCESS,orderSettingList);
        } catch (Exception e) {
            return new Result(false,IMPORT_ORDERSETTING_FAIL);
        }
    }


    /**
     * 查找当前月的预约信息
     * @param dateStr
     * @return
     */
    @RequestMapping("findByMonth")
    public Result findByMonth(String dateStr){
        try {
            List<Map> mapList = orderSettingService.findByMonth(dateStr);
            return new Result(true,QUERY_ORDER_SUCCESS,mapList);
        } catch (Exception e) {
            return new Result(false,QUERY_ORDER_FAIL);
        }
    }

    /**
     * 修改
     * @param orderSetting
     * @return
     */
    @RequestMapping("update")
    public Result update(@RequestBody OrderSetting orderSetting){

        try {
            orderSettingService.updateByOrderDate(orderSetting);
            return new Result(true,ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            return new Result(false,ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("findAll")
    public PageResult findAll(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = orderSettingService.findPageByCondition(queryPageBean);
        return pageResult;
    }
}
