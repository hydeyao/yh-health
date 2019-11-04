package com.itcast.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.entity.PageResult;
import com.itcast.mapper.CheckGroupMapper;
import com.itcast.pojo.CheckGroup;
import com.itcast.service.CheckGroupService;
import com.itcast.utils.HealthPageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/9 11:49
 * @description ：
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    /**
     * 新增检查组
     *
     * @param checkGroup
     * @param checkItemIds
     */
    @Override
    public void addCheckGroup(CheckGroup checkGroup, Integer[] checkItemIds) throws RuntimeException {
        Integer name_count = checkGroupMapper.findByName(checkGroup.getName());
        Integer code_count = checkGroupMapper.findByCode(checkGroup.getCode());

        if (name_count > 0) {
            throw new RuntimeException("添加失败,检查组名已存在");
        }

        if (code_count > 0) {
            throw new RuntimeException("添加失败,检查组编码已存在");

        }

        checkGroupMapper.addCheckGroup(checkGroup);
        setCheckGroupCheckItem(checkGroup.getId(), checkItemIds);
    }

    /**
     * 设置检查组和检查项关系
     *
     * @param checkGroupId
     * @param checkItemIds
     */
    @Override
    public void setCheckGroupCheckItem(Integer checkGroupId, Integer[] checkItemIds) {

        Map map = new HashMap<String, Integer>();
        for (Integer checkItemId : checkItemIds) {
            map.put("checkitem_id", checkItemId);
            map.put("checkgroup_id", checkGroupId);
            checkGroupMapper.setCheckGroupCheckItem(map);
        }

    }

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {

        currentPage= HealthPageUtils.judgeCurrentPage(currentPage);
        pageSize=HealthPageUtils.judgePagesize(pageSize);

        if (queryString != null && queryString.length() > 0) {
            queryString = queryString.trim();
            queryString = "%" + queryString + "%";
        }

        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupMapper.findByCondition(queryString);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据checkGroupId查询
     *
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupMapper.findById(id);
    }

    /**
     * 根据CheckGroupId查询与CheckItem的关系
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIdCheckItemId(Integer id) {
        return checkGroupMapper.findCheckGroupIdCheckItemId(id);
    }

    /**
     * 更新checkGroup 并且更新和checkItem关系
     *
     * @param checkGroup
     * @param checkItemIds
     */
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkItemIds) throws RuntimeException {
        Integer name_count = checkGroupMapper.findEditByName(checkGroup);
        if (name_count > 0) {
            throw new RuntimeException("修改失败,修改后项目名已存在");
        }
        checkGroupMapper.updateCheckGroup(checkGroup);
        checkGroupMapper.delRelation(checkGroup.getId());
        setCheckGroupCheckItem(checkGroup.getId(), checkItemIds);
    }

    /**
     * 根据id删除checkGroup并删除关系
     *
     * @param id
     */
    @Override
    public void delCheckGroup(Integer id) throws RuntimeException {
        Integer checkGroupIdCount = checkGroupMapper.findCheckGroupIdCount(id);
        if (checkGroupIdCount>0){
            throw new RuntimeException("删除失败,检查组项与套餐项有关联");
        }

        checkGroupMapper.delRelation(id);
        checkGroupMapper.delCheckGroup(id);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {

        return checkGroupMapper.findAll();
    }


}
