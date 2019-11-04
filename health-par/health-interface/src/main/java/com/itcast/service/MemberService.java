package com.itcast.service;

import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.pojo.Member;

import java.util.List;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/16 19:27
 * @description ：
 */
public interface MemberService {

    /**
     * 添加会员
     * @param member
     */
    void add(Member member);

    /**
     * 根据手机号查询会员
     * @param phoneNumber
     * @return
     */
    Member findByPhoneNumber(String phoneNumber);

    /**
     * 根据月份查询数量
     * @param month
     * @return
     */
    List<Integer> findCountByMonth(List<String> month);


    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    PageResult findPageByCondition(QueryPageBean queryPageBean);








}
