package com.plan.frame.system.service.quartz;

import com.plan.frame.system.base.constant.SysCodeConstant;
import com.plan.frame.system.base.entity.SysQuartzJob;
import com.plan.frame.system.base.entity.SysQuartzLog;
import com.plan.frame.system.service.SysQuartzJobService;
import com.plan.frame.system.service.SysQuartzLogService;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.SpringContextHolder;
import com.plan.frame.util.ThrowableUtil;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 参考人人开源，https://gitee.com/renrenio/renren-security
 * @author
 * @date 2019-01-07
 */
@Async
public class ExecutionJob extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) {
        SysQuartzJob quartzJob = (SysQuartzJob) context.getMergedJobDataMap().get(SysCodeConstant.JOB_KEY);
        // 获取spring bean
        SysQuartzLogService quartzLogService = SpringContextHolder.getBean("sysQuartzLogService");
        //
       // SpringContextHolder.getApplicationContext().getClassLoader();

        SysQuartzJobService quartzJobService = SpringContextHolder.getBean("sysQuartzJobService");
        QuartzManage quartzManage = SpringContextHolder.getBean("quartzManage");

        SysQuartzLog log = new SysQuartzLog();
        log.setId(CommonUtil.getUUID());
        log.setJobName(quartzJob.getJobName());
        log.setBeanName(quartzJob.getBeanName());
        log.setMethodName(quartzJob.getMethodName());
        log.setParams(quartzJob.getParams());
        long startTime = System.currentTimeMillis();
        log.setCronExpression(quartzJob.getCronExpression());
        try {
            // 执行任务
            logger.info("任务准备执行，任务名称：{}", quartzJob.getJobName());
            QuartzRunnable task = new QuartzRunnable(quartzJob.getBeanName(), quartzJob.getMethodName(),
                    quartzJob.getParams());
            Future<?> future = executorService.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            log.setTime(times);
            // 任务状态
            log.setIsSuccess((byte)1);
            logger.info("任务执行完毕，任务名称：{} 总共耗时：{} 毫秒", quartzJob.getJobName(), times);
        } catch (Exception e) {
            logger.error("任务执行失败，任务名称：{}" + quartzJob.getJobName(), e);
            long times = System.currentTimeMillis() - startTime;
            log.setTime(times);
            // 任务状态 0：成功 1：失败
            log.setIsSuccess((byte)0);
            log.setExceptionDetail(ThrowableUtil.getCause(e));
            //出错就暂停任务
            quartzManage.pauseJob(quartzJob);
            //更新状态,暂停任务
            quartzJobService.updateIsPause(quartzJob.getId(),true);
        } finally {
            log.setJobId(quartzJob.getId());
            log.setCreateTime(new Date());
            log.setCreateUser("Task");
            quartzLogService.create(log);
        }
    }
}
