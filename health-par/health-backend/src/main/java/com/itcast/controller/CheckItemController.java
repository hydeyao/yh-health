package com.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.entity.Result;
import com.itcast.pojo.CheckItem;
import com.itcast.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.itcast.constant.MessageConstant.*;

/**
 * @author ：yh
 * @date ：Created in 2019/10/8 19:53
 * @description ：CheckItemController
 * @Version :1.0.1
 */
@RequestMapping("/check")
@RestController("checkItemController")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 添加CheckItem
     *
     * @param checkItem
     * @return Result
     */
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, ADD_CHECKITEM_FAIL);
        }
        return new Result(true, ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return PageResult
     */
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkItemService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
        return pageResult;
    }


    /**
     * 删除
     *
     * @param id
     * @return Result
     */
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            checkItemService.deleteById(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, DELETE_CHECKITEM_SUCCESS);
    }


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            CheckItem checkItem = checkItemService.findByItem(id);
            return new Result(true, QUERY_CHECKITEM_SUCCESS, checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 修改
     *
     * @param checkItem
     * @return
     */
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.editPro(checkItem);
            return new Result(true, EDIT_CHECKITEM_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, EDIT_CHECKITEM_FAIL);
        }
    }


    /**
     * 查询所有
     * @return
     */
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("/findAll.do")
    public Result findAll() {
        List<CheckItem> checkItemList = checkItemService.findAll();
        if (checkItemList!=null&&checkItemList.size()>0){
            return new Result(true,QUERY_CHECKITEM_SUCCESS,checkItemList);
        }
        return new Result(false,QUERY_CHECKITEM_FAIL);
    }

}
