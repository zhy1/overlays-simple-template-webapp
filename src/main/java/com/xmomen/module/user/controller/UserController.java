package com.xmomen.module.user.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.xmomen.framework.mybatis.page.Page;
import com.xmomen.framework.logger.ActionLog;
import com.xmomen.framework.web.controller.BaseRestController;
import com.xmomen.module.authorization.entity.UserGroup;
import com.xmomen.module.authorization.entity.UserPermission;
import com.xmomen.module.authorization.model.GroupModel;
import com.xmomen.module.authorization.model.GroupQuery;
import com.xmomen.module.authorization.model.PermissionModel;
import com.xmomen.module.authorization.model.PermissionQuery;
import com.xmomen.module.authorization.service.GroupService;
import com.xmomen.module.authorization.service.PermissionService;
import com.xmomen.module.authorization.service.UserGroupService;
import com.xmomen.module.authorization.service.UserPermissionService;
import com.xmomen.module.user.model.UserQuery;
import com.xmomen.module.user.model.UserModel;
import com.xmomen.module.user.service.UserService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang3.StringUtils;
import javax.validation.Valid;
import java.util.List;

/**
 * @author  tanxinzheng
 * @date    2017-6-16 22:59:54
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseRestController {

    public static final String PERMISSION_USER_CREATE = "user:create";
    public static final String PERMISSION_USER_DELETE = "user:delete";
    public static final String PERMISSION_USER_UPDATE = "user:update";
    public static final String PERMISSION_USER_VIEW   = "user:view";

    @Autowired
    UserService userService;

    /**
     * 数据字典列表
     * @param   userQuery    数据字典查询参数对象
     * @return  Page<UserModel> 数据字典领域分页对象
     */
    @ApiOperation(value = "查询数据字典列表")
    @ActionLog(actionName = "查询数据字典列表")
    @RequiresPermissions(value = {PERMISSION_USER_VIEW})
    @RequestMapping(method = RequestMethod.GET)
    public Page<UserModel> getUserList(UserQuery userQuery){
        if(userQuery.isPaging()){
            return userService.getUserModelPage(userQuery);
        }
        List<UserModel> userList = userService.getUserModelList(userQuery);
        return new Page(userList);
    }

    /**
     * 查询单个数据字典
     * @param   id  主键
     * @return  UserModel   数据字典领域对象
     */
    @ApiOperation(value = "查询数据字典")
    @ActionLog(actionName = "查询数据字典")
    @RequiresPermissions(value = {PERMISSION_USER_VIEW})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserModel getUserById(@PathVariable(value = "id") String id){
        return userService.getOneUserModel(id);
    }

    /**
     * 新增数据字典
     * @param   userModel  新增对象参数
     * @return  UserModel   数据字典领域对象
     */
    @ApiOperation(value = "新增数据字典")
    @ActionLog(actionName = "新增数据字典")
    @RequiresPermissions(value = {PERMISSION_USER_CREATE})
    @RequestMapping(method = RequestMethod.POST)
    public UserModel createUser(@RequestBody @Valid UserModel userModel) {
        return userService.createUser(userModel);
    }

    /**
     * 更新数据字典
     * @param id    主键
     * @param userModel  更新对象参数
     * @return  UserModel   数据字典领域对象
     */
    @ApiOperation(value = "更新数据字典")
    @ActionLog(actionName = "更新数据字典")
    @RequiresPermissions(value = {PERMISSION_USER_UPDATE})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public UserModel updateUser(@PathVariable(value = "id") String id,
                           @RequestBody @Valid UserModel userModel){
        if(StringUtils.isNotBlank(id)){
            userModel.setId(id);
        }
        userService.updateUser(userModel);
        return userService.getOneUserModel(id);
    }

    /**
     *  删除数据字典
     * @param id    主键
     */
    @ApiOperation(value = "删除单个数据字典")
    @ActionLog(actionName = "删除单个数据字典")
    @RequiresPermissions(value = {PERMISSION_USER_DELETE})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable(value = "id") String id){
        userService.deleteUser(id);
    }

    /**
     *  删除数据字典
     * @param userQuery    查询参数对象
     */
    @ApiOperation(value = "批量删除数据字典")
    @ActionLog(actionName = "批量删除数据字典")
    @RequiresPermissions(value = {PERMISSION_USER_DELETE})
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteUsers(UserQuery userQuery){
        userService.deleteUser(userQuery.getIds());
    }

    @Autowired
    PermissionService permissionService;

    /**
     * 查询用户组权限
     * @param userId    用户主键
     * @param limit     最大页数
     * @param offset    页码
     * @return
     */
    @ActionLog(actionName = "查询用户组所属权限")
    @RequestMapping(value = "/{id}/permission", method = RequestMethod.GET)
    public Page<PermissionModel> getUserPermission(
            @PathVariable(value = "id") String userId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "hasPermission", required = false) Boolean hasPermission,
            @RequestParam(value = "limit") Integer limit,
            @RequestParam(value = "offset") Integer offset){
        PermissionQuery permissionQuery = new PermissionQuery();
        permissionQuery.setUserId(userId);
        permissionQuery.setKeyword(keyword);
        permissionQuery.setHasPermission(hasPermission);
        return permissionService.getPermissionModelPage(limit, offset, permissionQuery);
    }

    @Autowired
    UserPermissionService userPermissionService;

    /**
     * 批量新增组权限
     * @param userId   用户主键
     * @param permissionIds     权限主键集
     * @return List<UserPermission>    用户权限对象集
     */
    @RequestMapping(value = "/{id}/permission", method = RequestMethod.POST)
    public List<UserPermission> createGroupPermission(
            @PathVariable(value = "id") String userId,
            @RequestParam(value = "permissionIds") String[] permissionIds){
        return userPermissionService.createUserPermissions(userId, permissionIds);
    }

    @Autowired
    GroupService groupService;

    /**
     * 查询用户组权限
     * @param userId    用户主键
     * @param keyword   关键字
     * @param hasGroup     是否查询已有组
     * @param limit     每页数量
     * @param offset    页码
     * @return
     */
//    @Log(actionName = "查询用户组所属权限")
    @RequestMapping(value = "/{id}/group", method = RequestMethod.GET)
    public Page<GroupModel> getUserGroup(
            @PathVariable(value = "id") String userId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "hasGroup", required = false) Boolean hasGroup,
            @RequestParam(value = "limit") Integer limit,
            @RequestParam(value = "offset") Integer offset){
        GroupQuery groupQuery = new GroupQuery();
        groupQuery.setUserId(userId);
        groupQuery.setKeyword(keyword);
        groupQuery.setHasGroup(hasGroup);
        return groupService.getGroupModelPage(limit, offset, groupQuery);
    }

    @Autowired
    UserGroupService userGroupService;

    /**
     * 批量新增用户组
     * @param userId   用户主键
     * @param groupIds     组主键集
     * @return List<UserGroup>    用户组对象集
     */
    @RequestMapping(value = "/{id}/group", method = RequestMethod.POST)
    public List<UserGroup> createUserGroup(
            @PathVariable(value = "id") String userId,
            @RequestParam(value = "groupIds") String[] groupIds){
        return userGroupService.createUserGroups(userId, groupIds);
    }

}
