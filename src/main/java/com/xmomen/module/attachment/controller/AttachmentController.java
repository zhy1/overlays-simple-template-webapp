package com.xmomen.module.attachment.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.xmomen.framework.mybatis.page.Page;
import com.xmomen.framework.logger.ActionLog;
import com.xmomen.framework.web.controller.BaseRestController;
import com.xmomen.module.attachment.model.AttachmentQuery;
import com.xmomen.module.attachment.model.AttachmentModel;
import com.xmomen.module.attachment.service.AttachmentService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang3.StringUtils;
import javax.validation.Valid;
import java.util.List;

/**
 * @author  tanxinzheng
 * @date    2017-8-6 15:56:07
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/attachment")
public class AttachmentController extends BaseRestController {

    public static final String PERMISSION_ATTACHMENT_CREATE = "attachment:create";
    public static final String PERMISSION_ATTACHMENT_DELETE = "attachment:delete";
    public static final String PERMISSION_ATTACHMENT_UPDATE = "attachment:update";
    public static final String PERMISSION_ATTACHMENT_VIEW   = "attachment:view";

    @Autowired
    AttachmentService attachmentService;

    /**
     * 附件列表
     * @param   attachmentQuery    附件查询参数对象
     * @return  Page<AttachmentModel> 附件领域分页对象
     */
    @ApiOperation(value = "查询附件列表")
    @ActionLog(actionName = "查询附件列表")
    @RequiresPermissions(value = {PERMISSION_ATTACHMENT_VIEW})
    @RequestMapping(method = RequestMethod.GET)
    public Page<AttachmentModel> getAttachmentList(AttachmentQuery attachmentQuery){
        if(attachmentQuery.isPaging()){
            return attachmentService.getAttachmentModelPage(attachmentQuery);
        }
        List<AttachmentModel> attachmentList = attachmentService.getAttachmentModelList(attachmentQuery);
        return new Page(attachmentList);
    }

    /**
     * 查询单个附件
     * @param   id  主键
     * @return  AttachmentModel   附件领域对象
     */
    @ApiOperation(value = "查询附件")
    @ActionLog(actionName = "查询附件")
    @RequiresPermissions(value = {PERMISSION_ATTACHMENT_VIEW})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AttachmentModel getAttachmentById(@PathVariable(value = "id") String id){
        return attachmentService.getOneAttachmentModel(id);
    }

    /**
     * 新增附件
     * @param   attachmentModel  新增对象参数
     * @return  AttachmentModel   附件领域对象
     */
    @ApiOperation(value = "新增附件")
    @ActionLog(actionName = "新增附件")
    @RequiresPermissions(value = {PERMISSION_ATTACHMENT_CREATE})
    @RequestMapping(method = RequestMethod.POST)
    public AttachmentModel createAttachment(@RequestBody @Valid AttachmentModel attachmentModel) {
        return attachmentService.createAttachment(attachmentModel);
    }

    /**
     * 更新附件
     * @param id    主键
     * @param attachmentModel  更新对象参数
     * @return  AttachmentModel   附件领域对象
     */
    @ApiOperation(value = "更新附件")
    @ActionLog(actionName = "更新附件")
    @RequiresPermissions(value = {PERMISSION_ATTACHMENT_UPDATE})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public AttachmentModel updateAttachment(@PathVariable(value = "id") String id,
                           @RequestBody @Valid AttachmentModel attachmentModel){
        if(StringUtils.isNotBlank(id)){
            attachmentModel.setId(id);
        }
        attachmentService.updateAttachment(attachmentModel);
        return attachmentService.getOneAttachmentModel(id);
    }

    /**
     *  删除附件
     * @param id    主键
     */
    @ApiOperation(value = "删除单个附件")
    @ActionLog(actionName = "删除单个附件")
    @RequiresPermissions(value = {PERMISSION_ATTACHMENT_DELETE})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteAttachment(@PathVariable(value = "id") String id){
        attachmentService.deleteAttachment(id);
    }

    /**
     *  删除附件
     * @param attachmentQuery    查询参数对象
     */
    @ApiOperation(value = "批量删除附件")
    @ActionLog(actionName = "批量删除附件")
    @RequiresPermissions(value = {PERMISSION_ATTACHMENT_DELETE})
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAttachments(AttachmentQuery attachmentQuery){
        attachmentService.deleteAttachment(attachmentQuery.getIds());
    }


}
