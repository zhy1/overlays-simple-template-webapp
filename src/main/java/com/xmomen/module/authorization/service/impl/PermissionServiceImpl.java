package com.xmomen.module.authorization.service.impl;

import com.xmomen.framework.mybatis.page.PageInterceptor;
import com.xmomen.module.authorization.model.Permission;
import com.xmomen.module.authorization.mapper.PermissionMapper;
import com.xmomen.module.authorization.model.PermissionModel;
import com.xmomen.module.authorization.model.PermissionQuery;
import com.xmomen.module.authorization.service.PermissionService;
import com.xmomen.framework.mybatis.page.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author  tanxinzheng
 * @date    2017-7-25 1:52:35
 * @version 1.0.0
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    /**
     * 新增权限
     *
     * @param permissionModel 新增权限对象参数
     * @return PermissionModel    权限领域对象
     */
    @Override
    @Transactional
    public PermissionModel createPermission(PermissionModel permissionModel) {
        Permission permission = createPermission(permissionModel.getEntity());
        if(permission != null){
            return getOnePermissionModel(permission.getId());
        }
        return null;
    }

    /**
     * 新增权限实体对象
     *
     * @param permission 新增权限实体对象参数
     * @return Permission 权限实体对象
     */
    @Override
    @Transactional
    public Permission createPermission(Permission permission) {
        permissionMapper.insertSelective(permission);
        return permission;
    }

    /**
    * 批量新增权限
    *
    * @param permissionModels 新增权限对象集合参数
    * @return List<PermissionModel>    权限领域对象集合
    */
    @Override
    @Transactional
    public List<PermissionModel> createPermissions(List<PermissionModel> permissionModels) {
        List<PermissionModel> permissionModelList = null;
        for (PermissionModel permissionModel : permissionModels) {
            permissionModel = createPermission(permissionModel);
            if(permissionModel != null){
                if(permissionModelList == null){
                    permissionModelList = new ArrayList<>();
                }
                permissionModelList.add(permissionModel);
            }
        }
        return permissionModelList;
    }

    /**
    * 更新权限
    *
    * @param permissionModel 更新权限对象参数
    * @param permissionQuery 过滤权限对象参数
    */
    @Override
    @Transactional
    public void updatePermission(PermissionModel permissionModel, PermissionQuery permissionQuery) {
        permissionMapper.updateSelectiveByQuery(permissionModel.getEntity(), permissionQuery);
    }

    /**
     * 更新权限
     *
     * @param permissionModel 更新权限对象参数
     */
    @Override
    @Transactional
    public void updatePermission(PermissionModel permissionModel) {
        updatePermission(permissionModel.getEntity());
    }

    /**
     * 更新权限实体对象
     *
     * @param permission 新增权限实体对象参数
     * @return Permission 权限实体对象
     */
    @Override
    @Transactional
    public void updatePermission(Permission permission) {
        permissionMapper.updateSelective(permission);
    }

    /**
     * 删除权限
     *
     * @param ids 主键数组
     */
    @Override
    @Transactional
    public void deletePermission(String[] ids) {
        permissionMapper.deletesByPrimaryKey(Arrays.asList(ids));
    }

    /**
    * 删除权限
    *
    * @param id 主键
    */
    @Override
    @Transactional
    public void deletePermission(String id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询权限领域分页对象（带参数条件）
     *
     * @param permissionQuery 查询参数
     * @return Page<PermissionModel>   权限参数对象
     */
    @Override
    public Page<PermissionModel> getPermissionModelPage(PermissionQuery permissionQuery) {
        PageInterceptor.startPage(permissionQuery);
        permissionMapper.selectModel(permissionQuery);
        return PageInterceptor.endPage();
    }

    /**
     * 查询权限领域集合对象（带参数条件）
     *
     * @param permissionQuery 查询参数对象
     * @return List<PermissionModel> 权限领域集合对象
     */
    @Override
    public List<PermissionModel> getPermissionModelList(PermissionQuery permissionQuery) {
        return permissionMapper.selectModel(permissionQuery);
    }

    /**
     * 查询权限实体对象
     *
     * @param id 主键
     * @return Permission 权限实体对象
     */
    @Override
    public Permission getOnePermission(String id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据主键查询单个对象
     *
     * @param id 主键
     * @return PermissionModel 权限领域对象
     */
    @Override
    public PermissionModel getOnePermissionModel(String id) {
        return permissionMapper.selectModelByPrimaryKey(id);
    }

    /**
     * 根据查询参数查询单个对象（此方法只用于提供精确查询单个对象，若结果数超过1，则会报错）
     *
     * @param permissionQuery 权限查询参数对象
     * @return PermissionModel 权限领域对象
     */
    @Override
    public PermissionModel getOnePermissionModel(PermissionQuery permissionQuery) throws TooManyResultsException {
        List<PermissionModel> permissionModelList = permissionMapper.selectModel(permissionQuery);
        if(CollectionUtils.isEmpty(permissionModelList)){
            return null;
        }
        if(permissionModelList.size() > 1){
            throw new TooManyResultsException();
        }
        return permissionModelList.get(0);
    }
}
