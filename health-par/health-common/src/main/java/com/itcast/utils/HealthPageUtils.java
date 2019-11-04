package com.itcast.utils;

import com.itcast.entity.QueryPageBean;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/13 10:13
 * @description ：分页工具类
 */
public class HealthPageUtils {

    /**
     * currentPage容错性判断
     *
     * @param currentPage
     * @return
     */
    public static Integer judgeCurrentPage(Integer currentPage) {
        if (currentPage == null || currentPage.intValue() == 0) {
            currentPage = 1;
        }
        return currentPage;
    }

    /**
     * pageSize容错性判断
     *
     * @param pageSize
     * @return
     */
    public static Integer judgePagesize(Integer pageSize) {
        if (pageSize == null || pageSize.intValue() == 0) {
            pageSize = 10;
        }
        return pageSize;
    }

    /**
     * 生成模糊查询字段
     * @param queryString
     * @return
     */
    public static String fuzzyQueryString(String queryString) {

        //查询字段为日期 生成模糊查询字段
        if (queryString.contains("-")) {
            String[] strings = queryString.split("-");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < strings.length; i++) {
                sb.append("%" + strings[i] + "%");
                sb.append("-");
            }
            queryString = sb.toString();

            queryString = queryString.substring(0,queryString.length()-1);

        } else {
            //查询字段为普通字段
            queryString = "%" + queryString.trim() + "%";
        }

        return queryString;
    }


    /**
     *
     * 处理QueryPageBean
     * @param queryPageBean
     * @return
     */
    public static QueryPageBean handleQuery(QueryPageBean queryPageBean){

        Integer currentPage = judgeCurrentPage(queryPageBean.getCurrentPage());
        Integer pagesize = judgePagesize(queryPageBean.getPageSize());

        String queryString = queryPageBean.getQueryString();

        if (HealthStringUtils.isNotEmpty(queryString)){
            queryString=HealthPageUtils.fuzzyQueryString(queryString);
        }

        QueryPageBean result = new QueryPageBean();
        result.setCurrentPage(currentPage);
        result.setPageSize(pagesize);
        result.setQueryString(queryString);

        return result;
    }

}
