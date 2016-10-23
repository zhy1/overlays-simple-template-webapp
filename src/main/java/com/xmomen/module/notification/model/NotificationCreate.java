package com.xmomen.module.notification.model;

import com.xmomen.module.notification.entity.Notification;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author  tanxinzheng
 * @date    2016-10-22 23:34:58
 * @version 1.0.0
 */
public @Data class NotificationCreate implements Serializable {

    /** 主键 */
    private String id;
    /** 发送者主键 */
    private String senderId;
    /** 接收者主键 */
    private String receiverId;
    /** 内容主键 */
    private String textId;
    /** 已读 */
    private Boolean readed;
    /** 状态 */
    private String status;

    public Notification getEntity(){
        Notification notification = new Notification();
        BeanUtils.copyProperties(this, notification);
        return notification;
    }
}
