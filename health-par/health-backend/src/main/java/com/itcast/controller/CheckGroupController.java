package com.itcast.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.entity.PageResult;
import com.itcast.entity.QueryPageBean;
import com.itcast.entity.Result;
import com.itcast.pojo.CheckGroup;
import com.itcast.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.itcast.constant.MessageConstant.*;

/**
 * @author ：yh
 * @date ：Created in 2019/10/8 19:53
 * @description ：CheckGroupController
 * @Version :1.0.1
 */
@RestController("checkGroupController")
@RequestMapping("group")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 新增检查组
     *
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @RequestMapping("addCheckGroup")
    public Result addCheckGroup(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.addCheckGroup(checkGroup, checkitemIds);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, ADD_CHECKGROUP_FAIL);
        }
        return new Result(true, ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkGroupService.pageQuery(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
        return pageResult;
    }

    /**
     * 根据checkGroupId查询检查组
     *
     * @param id
     * @return
     */
    @RequestMapping("findById")
    public Result findById(Integer id) {
        CheckGroup checkGroup = checkGroupService.findById(id);
        if (checkGroup != null) {
            return new Result(true, QUERY_CHECKGROUP_SUCCESS, checkGroup);
        }
        return new Result(false, QUERY_CHECKGROUP_FAIL);

    }

    /**
     * 根据checkGroupId查询与检查项的关系
     *
     * @param id
     * @return
     */
    @RequestMapping("findRelation")
    public Result findRelation(Integer id) {
        try {
            List<Integer> checkList = checkGroupService.findCheckGroupIdCheckItemId(id);
            return new Result(true, QUERY_CHECKITEM_SUCCESS, checkList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, QUERY_CHECKITEM_FAIL);
        }

    }

    /**
     * 修改检查组
     *
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @RequestMapping("updateCheckGroup")
    public Result updateCheckGroup(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.update(checkGroup, checkitemIds);
            return new Result(true, EDIT_CHECKGROUP_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, EDIT_CHECKGROUP_FAIL);
        }
    }

    /**
     * 删除检查组
     *
     * @param id
     * @return
     */
    @RequestMapping("delCheckGroup")
    public Result delCheckGroup(Integer id) {
        try {
            checkGroupService.delCheckGroup(id);
            return new Result(true, DELETE_CHECKGROUP_SUCCESS);
        } catch (RuntimeException e){
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            return new Result(false, DELETE_CHECKGROUP_FAIL);
        }
    }

    /**
     * 查询所有检查项
     *
     * @return
     */
    @RequestMapping("findAll")
    public Result findAll() {
        List<CheckGroup> checkGroups = checkGroupService.findAll();
        if (checkGroups.size() > 0 && checkGroups != null) {
            return new Result(true, QUERY_CHECKGROUP_SUCCESS, checkGroups);
        }
        return new Result(false, QUERY_CHECKGROUP_FAIL);
    }


}
