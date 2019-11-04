package com.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.constant.MessageConstant;
import com.itcast.entity.Result;
import com.itcast.service.MemberService;
import com.itcast.service.ReportService;
import com.itcast.service.SetMealService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.itcast.constant.MessageConstant.*;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/20 20:32
 * @description ：
 */
@RequestMapping("report")
@RestController("reportController")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetMealService setMealService;

    @Reference
    private ReportService reportService;

    @RequestMapping("getMemberReport")
    public Result getMemberReport() {

        Map<String, Object> resultMap = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");

            List<String> monthList = new ArrayList<>();

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -12);
            for (int i = 0; i < 12; i++) {
                calendar.add(Calendar.MONTH, 1);
                monthList.add(sdf.format(calendar.getTime()));
            }

            List<Integer> countList = memberService.findCountByMonth(monthList);

            resultMap = new HashMap<>();
            resultMap.put("months", monthList);
            resultMap.put("memberCount", countList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, GET_MEMBER_NUMBER_REPORT_FAIL);
        }

        return new Result(true, GET_MEMBER_NUMBER_REPORT_SUCCESS, resultMap);
    }


    @RequestMapping("getSetmealReport")
    public Result getSetmealReport() {

        try {
            Map<String, Object> map = setMealService.findSetMealCount();
            return new Result(true, GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
        } catch (Exception e) {
            return new Result(false, GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    @RequestMapping("getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> map = reportService.getBusinessReport();
            return new Result(true, GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            return new Result(false, GET_BUSINESS_REPORT_FAIL);
        }
    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping("exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            Map<String, Object> result = reportService.getBusinessReport();
            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            String filePath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
            XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File(filePath)));
            XSSFSheet sheet = excel.getSheetAt(0);

            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);
            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);
            row.getCell(7).setCellValue(totalMember);

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);
            row.getCell(7).setCellValue(thisMonthNewMember);

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);
            row.getCell(7).setCellValue(todayVisitsNumber);

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);
            row.getCell(7).setCellValue(thisWeekVisitsNumber);

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);
            row.getCell(7).setCellValue(thisMonthVisitsNumber);

            int rowNum = 12;
            for (Map map : hotSetmeal) {
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum++);
                row.getCell(4).setCellValue(name);
                row.getCell(5).setCellValue(setmeal_count);
                row.getCell(6).setCellValue(proportion.doubleValue());
            }

            //使用输出流进行表格下载,基于浏览器作为客户端下载
            OutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            excel.write(out);

            out.flush();
            out.close();
            excel.close();
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

        return new Result(true, "导出成功");
    }

    @RequestMapping("getReportSetMeal")
    public Result getReportSetMeal(){

        try {
            Map<String, Object> map = setMealService.findCountWithPrice();
            return new Result(true, GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
        } catch (Exception e) {
            return new Result(false, GET_SETMEAL_COUNT_REPORT_FAIL);
        }

    }

}
