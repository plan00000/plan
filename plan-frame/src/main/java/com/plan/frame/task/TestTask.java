package com.plan.frame.task;

import com.plan.frame.system.service.quartz.Task;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 测试用
 */
@Component
public class TestTask {
    private Logger logger = Logger.getLogger(this.getClass());

    @Task(name="测试task3",run = false,corn = "0/10 * * * * ?",params = "我是参数，支持字符串",description = "测试task3,每10秒执行一次")
    public void run3(String str){
        logger.info("执行成功，参数为： "+str );
    }
}
