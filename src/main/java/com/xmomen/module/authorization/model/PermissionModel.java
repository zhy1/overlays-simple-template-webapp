package com.xmomen.module.authorization.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xmomen.module.authorization.entity.Permission;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author  tanxinzheng
 * @date    2016-10-23 12:15:20
 * @version 1.0.0
 */
@ExcelTarget(value = "PermissionModel")
public @Data class PermissionModel implements Serializable {

    /** 主键 */
    private String id;
    /** 权限代码 */
    @Excel(name = "权限代码")
    private String permissionCode;
    /** 权限名称 */
    @Excel(name = "权限名称")
    private String permissionName;
    /** 权限描述 */
    @Excel(name = "权限描述")
    private String description;
    /** 激活 */
    @Excel(name = "激活")
    private Boolean active;
    /** 创建时间 */
    @Excel(name = "创建时间")
    private Date createDate;
    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;
    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;
    @JsonIgnore
    public Permission getEntity(){
        Permission permission = new Permission();
        BeanUtils.copyProperties(this, permission);
        return permission;
    }


}
