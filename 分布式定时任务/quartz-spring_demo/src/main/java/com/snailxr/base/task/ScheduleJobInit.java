package com.snailxr.base.task;

import javax.annotation.PostConstruct;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snailxr.base.task.service.JobTaskService;


/**
 * 定时任务初始化
 *
 * Created by liyd on 12/19/14.
 */
@Component
public class ScheduleJobInit {

    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleJobInit.class);

    /** 定时任务service */
    @Autowired
    private JobTaskService  jobTaskService;

    /**
     * 项目启动时初始化
     */
    @PostConstruct
    public void init() {
    	LOG.debug("start quartz init");

        if (LOG.isInfoEnabled()) {
            LOG.info("init");
        }

        try {
			jobTaskService.initScheduleJob();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if (LOG.isInfoEnabled()) {
            LOG.info("end");
        }
    }

}
