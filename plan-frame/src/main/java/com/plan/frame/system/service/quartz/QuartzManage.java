package com.plan.frame.system.service.quartz;

import com.plan.frame.exception.SystemException;
import com.plan.frame.system.base.constant.SysCodeConstant;
import com.plan.frame.system.base.entity.SysQuartzJob;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author jie
 * @date 2019-01-07
 */

@Component
public class QuartzManage {

    private static final String JOB_NAME = "TASK_";

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    public void addJob(SysQuartzJob quartzJob){
        try {
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ExecutionJob.class).
                    withIdentity(JOB_NAME + quartzJob.getId()).build();
            //jobDetail.getJobDataMap().put(QuartzJob.JOB_KEY, quartzJob);
            //通过触发器名和cron 表达式创建 Trigger
            Trigger cronTrigger = newTrigger()
                    .withIdentity(JOB_NAME + quartzJob.getId())
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()))
                    .build();

            cronTrigger.getJobDataMap().put(SysCodeConstant.JOB_KEY, quartzJob);

            //重置启动时间
            ((CronTriggerImpl)cronTrigger).setStartTime(new Date());

            //执行定时任务
            scheduler.scheduleJob(jobDetail,cronTrigger);

            // 暂停任务
            if (quartzJob.getIsPause() == 1) {
                pauseJob(quartzJob);
            }
        } catch (Exception e){
            //log.error("创建定时任务失败", e);
            throw new SystemException("创建定时任务失败",e,"请检查程序");
        }
    }

    /**
     * 更新job cron表达式
     * @param quartzJob
     * @throws SchedulerException
     */
    public void updateJobCron(SysQuartzJob quartzJob){
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + quartzJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if(trigger == null){
                addJob(quartzJob);
                trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            }
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //重置启动时间
            ((CronTriggerImpl)trigger).setStartTime(new Date());
            trigger.getJobDataMap().put(SysCodeConstant.JOB_KEY,quartzJob);

            scheduler.rescheduleJob(triggerKey, trigger);
            // 暂停任务
            if (quartzJob.getIsPause() == 1) {
                pauseJob(quartzJob);
            }
        } catch (Exception e){
            // log.error("更新定时任务失败", e);
            throw new SystemException("更新定时任务失败",e,"请检查程序");
        }

    }

    /**
     * 删除一个job
     * @param quartzJob
     * @throws SchedulerException
     */
    public void deleteJob(SysQuartzJob quartzJob){
        try {
            JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
            scheduler.deleteJob(jobKey);
        } catch (Exception e){
            //log.error("删除定时任务失败", e);
            throw new SystemException("删除定时任务失败",e,"请检查程序");
        }
    }

    /**
     * 恢复一个job
     * @param quartzJob
     * @throws SchedulerException
     */
    public void resumeJob(SysQuartzJob quartzJob){
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + quartzJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if(trigger == null)
                addJob(quartzJob);
            JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
            scheduler.resumeJob(jobKey);
        } catch (Exception e){
            //log.error("恢复定时任务失败", e);
            throw new SystemException("恢复定时任务失败",e,"请检查程序");
        }
    }

    /**
     * 立即执行job
     * @param quartzJob
     * @throws SchedulerException
     */
    public void runAJobNow(SysQuartzJob quartzJob){
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + quartzJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if(trigger == null)
                addJob(quartzJob);
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(SysCodeConstant.JOB_KEY, quartzJob);
            JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
            scheduler.triggerJob(jobKey,dataMap);
        } catch (Exception e){
            //log.error("定时任务执行失败", e);
            throw new SystemException("定时任务执行失败",e,"请检查程序");
        }
    }

    /**
     * 暂停一个job
     * @param quartzJob
     * @throws SchedulerException
     */
    public void pauseJob(SysQuartzJob quartzJob){
        try {
            JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
            scheduler.pauseJob(jobKey);
        } catch (Exception e){
            //log.error("定时任务暂停失败", e);
            //throw new BadRequestException("定时任务暂停失败");
            throw new SystemException("定时任务暂停失败",e,"请检查程序");
        }
    }
}
