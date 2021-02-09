package com.plan.frame.system.service.quartz;

import com.plan.frame.system.base.entity.SysQuartzJob;
import com.plan.frame.system.service.SysQuartzJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jie
 * @date 2019-01-07
 */
//@Component
public class QuartzJobRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static Map<String,SysQuartzJob> INIT_TASK_MAP=new HashMap<>();

    @Autowired
    private SysQuartzJobService quartzJobService;

    @Autowired
    private QuartzManage quartzManage;

    /**
     * 项目启动时重新激活启用的定时任务
     * @param applicationArguments
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments applicationArguments){
        logger.info("--------------------注入定时任务---------------------");
        //List<SysQuartzJob> quartzJobs = quartzJobService.listJobsByIsPause(false);
        Map<String,SysQuartzJob> task=new HashMap<>(INIT_TASK_MAP);
        List<SysQuartzJob> quartzJobs = quartzJobService.listAllJobs();
        quartzJobs.forEach(quartzJob -> {
            if(quartzJob.getIsPause() == 0){ //启用
                quartzManage.addJob(quartzJob);
            }
            task.remove(quartzJob.getBeanName()+"_"+quartzJob.getMethodName());
        });
        //新增任务
        task.forEach( (key, job) ->{
            quartzJobService.createJob(job);
        });
        logger.info("--------------------定时任务注入完成---------------------");
    }
}
