package com.xmomen.module.scheduler.service.impl;

import com.xmomen.framework.mybatis.page.Page;
import com.xmomen.framework.mybatis.page.PageInterceptor;
import com.xmomen.module.scheduler.mapper.ScheduleTaskMapper;
import com.xmomen.module.scheduler.model.ScheduleTaskModel;
import com.xmomen.module.scheduler.model.ScheduleTaskQuery;
import com.xmomen.module.scheduler.service.ScheduleTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tanxinzheng on 17/8/9.
 */
@Service
public class ScheduleTaskServiceImpl implements ScheduleTaskService {

    @Autowired
    ScheduleTaskMapper scheduleTaskMapper;

    /**
     * 查询调度任务
     * @param scheduleTaskQuery
     * @return
     */
    @Override
    public Page<ScheduleTaskModel> getScheduleTaskPages(ScheduleTaskQuery scheduleTaskQuery) {
        PageInterceptor.startPage(scheduleTaskQuery);
        scheduleTaskMapper.selectModel(scheduleTaskQuery);
        return PageInterceptor.endPage();
    }
}
