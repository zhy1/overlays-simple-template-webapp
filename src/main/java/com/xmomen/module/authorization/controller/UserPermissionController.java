package com.xmomen.module.authorization.controller;

import com.xmomen.framework.mybatis.page.Page;
import com.xmomen.framework.web.exceptions.ArgumentValidException;
//import com.xmomen.module.logger.Log;
import com.xmomen.module.authorization.model.UserPermissionCreate;
import com.xmomen.module.authorization.model.UserPermissionQuery;
import com.xmomen.module.authorization.model.UserPermissionUpdate;
import com.xmomen.module.authorization.model.UserPermissionModel;
import com.xmomen.module.authorization.service.UserPermissionService;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author  tanxinzheng
 * @date    2016-10-17 0:59:11
 * @version 1.0.0
 */
@RestController
public class UserPermissionController {

    @Autowired
    UserPermissionService userPermissionService;

    /**
     * 用户权限列表
     * @param   limit           每页结果数
     * @param   offset          页码
     * @param   id              主键
     * @param   ids             主键数组
     * @param   excludeIds      不包含主键数组
     * @param   keyword         关键字
     * @return  Page<UserPermissionModel> 用户权限领域分页对象
     */
    @RequestMapping(value = "/userPermission", method = RequestMethod.GET)
    //@Log(actionName = "查询用户权限列表")
    public Page<UserPermissionModel> getUserPermissionList(@RequestParam(value = "limit") Integer limit,
                                  @RequestParam(value = "offset") Integer offset,
                                  @RequestParam(value = "id", required = false) String id,
                                  @RequestParam(value = "ids", required = false) String[] ids,
                                  @RequestParam(value = "excludeIds", required = false) String[] excludeIds,
                                  @RequestParam(value = "keyword", required = false) String keyword){
        UserPermissionQuery userPermissionQuery = new UserPermissionQuery();
        userPermissionQuery.setId(id);
        userPermissionQuery.setExcludeIds(excludeIds);
        userPermissionQuery.setIds(ids);
        userPermissionQuery.setKeyword(keyword);
        return userPermissionService.getUserPermissionModelPage(limit, offset, userPermissionQuery);
    }

    /**
     * 查询单个用户权限
     * @param   id  主键
     * @return  UserPermissionModel   用户权限领域对象
     */
    @RequestMapping(value = "/userPermission/{id}", method = RequestMethod.GET)
    //@Log(actionName = "查询用户权限")
    public UserPermissionModel getUserPermissionById(@PathVariable(value = "id") String id){
        return userPermissionService.getOneUserPermissionModel(id);
    }

    /**
     * 新增用户权限
     * @param   UserPermissionCreate  新增对象参数
     * @param   bindingResult   参数校验结果
     * @return  UserPermissionModel   用户权限领域对象
     */
    @RequestMapping(value = "/userPermission", method = RequestMethod.POST)
    //@Log(actionName = "新增用户权限")
    public UserPermissionModel createUserPermission(@RequestBody @Valid UserPermissionCreate userPermissionCreate, BindingResult bindingResult) throws ArgumentValidException {
        if(bindingResult != null && bindingResult.hasErrors()){
            throw new ArgumentValidException(bindingResult);
        }
        return userPermissionService.createUserPermission(userPermissionCreate);
    }

    /**
     * 更新用户权限
     * @param id                            主键
     * @param UserPermissionUpdate 更新对象参数
     * @param bindingResult                 参数校验结果
     * @throws ArgumentValidException       参数校验异常类
     */
    @RequestMapping(value = "/userPermission/{id}", method = RequestMethod.PUT)
    //@Log(actionName = "更新用户权限")
    public void updateUserPermission(@PathVariable(value = "id") String id,
                           @RequestBody @Valid UserPermissionUpdate userPermissionUpdate, BindingResult bindingResult) throws ArgumentValidException {
        if(bindingResult != null && bindingResult.hasErrors()){
            throw new ArgumentValidException(bindingResult);
        }
        userPermissionService.updateUserPermission(userPermissionUpdate);
    }

    /**
     *  删除用户权限
     * @param id    主键
     */
    @RequestMapping(value = "/userPermission/{id}", method = RequestMethod.DELETE)
    //@Log(actionName = "删除单个用户权限")
    public void deleteUserPermission(@PathVariable(value = "id") String id){
        userPermissionService.deleteUserPermission(id);
    }

    /**
     *  删除用户权限
     * @param ids    主键
     */
    @RequestMapping(value = "/userPermission", method = RequestMethod.DELETE)
    //@Log(actionName = "批量删除用户权限")
    public void deleteUserPermissions(@RequestParam(value = "ids") String[] ids){
        userPermissionService.deleteUserPermission(ids);
    }

    /**
    * 导出
    * @param id
    * @param ids
    * @param excludeIds
    * @param keyword
    * @param modelMap
    * @return
    */
    @RequestMapping(value="/userPermission/report", method = RequestMethod.GET)
    public ModelAndView exportUserPermission(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "ids", required = false) String[] ids,
            @RequestParam(value = "excludeIds", required = false) String[] excludeIds,
            @RequestParam(value = "keyword", required = false) String keyword,
            ModelMap modelMap) {
        UserPermissionQuery userPermissionQuery = new UserPermissionQuery();
        userPermissionQuery.setId(id);
        userPermissionQuery.setExcludeIds(excludeIds);
        userPermissionQuery.setIds(ids);
        userPermissionQuery.setKeyword(keyword);
        List<UserPermissionModel> userPermissionList = userPermissionService.getUserPermissionModelList(userPermissionQuery);
        modelMap.put(NormalExcelConstants.FILE_NAME, "用户权限信息");
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams());
        modelMap.put(NormalExcelConstants.CLASS, UserPermissionModel.class);
        modelMap.put(NormalExcelConstants.DATA_LIST, userPermissionList);
        return new ModelAndView(NormalExcelConstants.JEECG_EXCEL_VIEW);
    }


}
