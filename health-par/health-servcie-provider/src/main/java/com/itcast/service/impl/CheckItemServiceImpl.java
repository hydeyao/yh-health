package com.itcast.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.entity.PageResult;
import com.itcast.mapper.CheckItemMapper;
import com.itcast.pojo.CheckItem;
import com.itcast.service.CheckItemService;
import com.itcast.utils.HealthPageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/9 11:49
 * @description ：
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;

    /**
     * 添加
     *
     * @param checkItem
     * @throws RuntimeException
     */
    @Override
    public void add(CheckItem checkItem) throws RuntimeException {
        Integer codeCount = checkItemMapper.findCodeCount(checkItem.getCode());
        Integer nameCount = checkItemMapper.findNameCount(checkItem.getName());
        if (codeCount > 0) {
            throw new RuntimeException("添加失败,项目编码重复");
        }
        if (nameCount > 0) {
            throw new RuntimeException("添加失败,项目名称重复");
        }

        checkItemMapper.add(checkItem);
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
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {

        currentPage= HealthPageUtils.judgeCurrentPage(currentPage);
        pageSize=HealthPageUtils.judgePagesize(pageSize);

        if (queryString != null && queryString.length() > 0) {
            queryString = queryString.trim();
            queryString = queryString = "%" + queryString + "%";

        }
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckItem> page = checkItemMapper.findByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());

    }

    /**
     * 由id删除
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        Long count = checkItemMapper.findCountByCheckItemId(id);
        if (count > 0) {
            throw new RuntimeException("当前项被引用，不可删除");
        }
        checkItemMapper.deleteById(id);
    }

    /**
     * 由id查询
     *
     * @param id
     * @return
     */
    @Override
    public CheckItem findByItem(Integer id) {
        return checkItemMapper.findById(id);
    }

    /**
     * 修改 已弃用
     *
     * @param checkItem
     * @throws RuntimeException
     */
    @Override
    public void edit(CheckItem checkItem) throws RuntimeException {

        checkItemMapper.update(checkItem);
    }

    /**
     * 修改
     *
     * @param checkItem
     */
    @Override
    public void editPro(CheckItem checkItem) throws RuntimeException {
        CheckItem item = checkItemMapper.findById(checkItem.getId());
        if (!item.getName().equals(checkItem.getName())) {
            Integer nameCount = checkItemMapper.findNameCount(checkItem.getName());
            if (nameCount > 0) {
                throw new RuntimeException("修改失败,项目名称重复");
            }
        }

        if (!item.getCode().equals(checkItem.getCode())) {
            Integer codeCount = checkItemMapper.findCodeCount(checkItem.getCode());
            if (codeCount > 0) {
                throw new RuntimeException("修改失败,项目编码重复");
            }
        }

        checkItemMapper.update(checkItem);

    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemMapper.findAll();
    }


}
