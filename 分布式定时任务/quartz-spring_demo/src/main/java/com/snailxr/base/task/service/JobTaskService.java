package com.snailxr.base.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.snailxr.base.task.QuartzJobFactory;
import com.snailxr.base.task.QuartzJobFactoryDisallowConcurrentExecution;
import com.snailxr.base.task.dao.ScheduleJobMapper;
import com.snailxr.base.task.domain.ScheduleJob;

/**
 * 
 * @Description: 计划任务管理
 * @author chenjianlin
 * @date 2014年4月25日 下午2:43:54
 */
@Service
public class JobTaskService {
	public final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private ScheduleJobMapper scheduleJobMapper;
	
	
    public void initScheduleJob()  throws SchedulerException {
    	log.debug( ".......................................................................................initScheduleJob");
        List<ScheduleJob> scheduleJobList = getAllTask();
        if (scheduleJobList == null) {
            return;
        }
        for (ScheduleJob job : scheduleJobList) {
        	
        	if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
    			return;
    		}
    		
        	
        	
        	Scheduler scheduler = schedulerFactoryBean.getScheduler();
    		
    		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        	CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);


    		// 不存在，创建一个
    		if (null == trigger) {
    			Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;

    			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();

    			jobDetail.getJobDataMap().put("scheduleJob", job);

    			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

    			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();

    			scheduler.scheduleJob(jobDetail, trigger);
    		} else {
    			// Trigger已存在，那么更新相应的定时设置
    			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

    			// 按新的cronExpression表达式重新构建trigger
    			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

    			// 按新的trigger重新设置job执行
    			scheduler.rescheduleJob(triggerKey, trigger);
    		}
        }
    }
    

	/**
	 * 从数据库中取 区别于getAllJob
	 * 
	 * @return
	 */
	public List<ScheduleJob> getAllTask() {
		return scheduleJobMapper.getAll();
	}

	/**
	 * 添加到数据库中 区别于addJob
	 */
	public void addTask(ScheduleJob job) {
		job.setCreateTime(new Date());
		scheduleJobMapper.insertSelective(job);
	}

	/**
	 * 从数据库中查询job
	 */
	public ScheduleJob getTaskById(Long jobId) {
		return scheduleJobMapper.selectByPrimaryKey(jobId);
	}

	/**
	 * 更改任务状态
	 * 
	 * @throws SchedulerException
	 */
	public void changeStatus(Long jobId, String cmd) throws SchedulerException {
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		if ("stop".equals(cmd)) {
			deleteJob(job);
			job.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
		} else if ("start".equals(cmd)) {
			job.setJobStatus(ScheduleJob.STATUS_RUNNING);
			addJob(job);
		}
		scheduleJobMapper.updateByPrimaryKeySelective(job);
	}

	/**
	 * 更改任务 cron表达式
	 * 
	 * @throws SchedulerException
	 */
	public void updateCron(Long jobId, String cron) throws SchedulerException {
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		job.setCronExpression(cron);
		if (ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			updateJobCron(job);
		}
		scheduleJobMapper.updateByPrimaryKeySelective(job);

	}

	/**
	 * 添加任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void addJob(ScheduleJob job) throws SchedulerException {
		if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			return;
		}
		
		log.debug(JSON.toJSON(job));

		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		log.debug(scheduler + ".......................................................................................add");
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个
		if (null == trigger) {
			Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;

			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();

			jobDetail.getJobDataMap().put("scheduleJob", job);

			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	@PostConstruct
	public void init() throws Exception {

		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		// 这里获取任务信息数据
		List<ScheduleJob> jobList = scheduleJobMapper.getAll();
	
		for (ScheduleJob job : jobList) {
			addJob(job);
		}
	}

	/**
	 * 获取所有计划中的任务列表
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getAllJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleJob job = new ScheduleJob();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}

	/**
	 * 所有正在运行的job
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getRunningJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	/**
	 * 暂停一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 删除一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);

	}

	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	/**
	 * 更新job时间表达式
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

		scheduler.rescheduleJob(triggerKey, trigger);
	}

	public static void main(String[] args) {
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("xxxxx");
	}
}
