package com.xmomen.module.authorization.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xmomen.module.authorization.entity.UserPermission;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;
import org.springframework.beans.BeanUtils;

import java.lang.String;
import java.io.Serializable;

/**
 * @author  tanxinzheng
 * @date    2016-10-20 23:14:13
 * @version 1.0.0
 */
@ExcelTarget(value = "UserPermissionModel")
public @Data class UserPermissionModel implements Serializable {

    /** 主键 */
    private String id;
    /** 用户表ID */
    @Excel(name = "用户表ID")
    private String userId;
    /** 权限表ID */
    @Excel(name = "权限表ID")
    private String permissionId;
    @JsonIgnore
    public UserPermission getEntity(){
        UserPermission userPermission = new UserPermission();
        BeanUtils.copyProperties(this, userPermission);
        return userPermission;
    }


}