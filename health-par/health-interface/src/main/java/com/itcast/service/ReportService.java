package com.itcast.service;

import java.util.Map;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/21 9:37
 * @description ：
 */
public interface ReportService {

    /**
     * 获取运营统计数据
     * @return
     */
    Map<String,Object> getBusinessReport() throws Exception;
}
