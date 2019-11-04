package com.itcast.mapper;

import com.github.pagehelper.Page;
import com.itcast.pojo.Member;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/16 9:53
 * @description ：
 */
public interface MemberMapper {

    /**
     * 通过手机号查询会员信息
     *
     * @param phoneNumber
     * @return
     */
    Member findByTelephone(String phoneNumber);

    /**
     * 添加会员
     *
     * @param member
     */
    void add(Member member);

    /**
     * 通过phoneNumber更新
     *
     * @param member
     */
    void updateByPhoneNumber(Member member);

    /**
     * 根据日期查询个数
     *
     * @param dateStr
     * @return
     */
    Integer findCountByDate(String dateStr);

    /**
     * 查找指定日期会员数
     *
     * @param dateStr
     * @return
     */
    Integer findCountThisDate(String dateStr);

    /**
     * 查找指定日期后会员数
     *
     * @param dateStr
     * @return
     */
    Integer findCountAfterDate(String dateStr);

    /**
     * 查找会员总数
     *
     * @return
     */
    Integer findAllCount();

    /**
     * 条件查询会员
     * @param queryStr
     * @return
     */
    Page<Member> findMemberByCondition(String queryStr);


}
