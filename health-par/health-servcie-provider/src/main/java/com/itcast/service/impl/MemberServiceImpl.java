package com.itcast.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.mapper.MemberMapper;
import com.itcast.pojo.Member;
import com.itcast.service.MemberService;
import com.itcast.utils.HealthPageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/16 19:28
 * @description ：
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private MemberMapper memberMapper;


    /**
     * 添加会员
     * @param member
     */
    @Override
    public void add(Member member) {
        memberMapper.add(member);
    }

    /**
     * 根据手机号查询会员
     * @param phoneNumber
     * @return
     */
    @Override
    public Member findByPhoneNumber(String phoneNumber) {
        return memberMapper.findByTelephone(phoneNumber);
    }

    /**
     * 根据月份查询数量
     * @param month
     * @return
     */
    @Override
    public List<Integer> findCountByMonth(List<String> month) {
        List<Integer> list = new ArrayList();

        for (String m : month) {
            m = m + ".31";
            Integer count = memberMapper.findCountByDate(m);
            list.add(count);
        }


        return list;
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPageByCondition(QueryPageBean queryPageBean) {
        queryPageBean = HealthPageUtils.handleQuery(queryPageBean);
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Member> page = memberMapper.findMemberByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }


}
