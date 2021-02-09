package com.plan.frame.interceptor;

import com.plan.frame.system.base.entity.SysQuartzJob;
import com.plan.frame.system.service.quartz.QuartzJobRunner;
import com.plan.frame.system.service.quartz.Task;
import com.plan.frame.util.DateUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 对定时任务进行扫描
 */
//@Component
public class TaskListenerProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        if (methods != null) {
            for (Method method : methods) {
                Task task = AnnotationUtils.findAnnotation(method, Task.class);
                // process
                if (task != null) {
                    SysQuartzJob job =new SysQuartzJob();
                    boolean b = task.run();
                    job.setIsPause(b ? (byte) 0: (byte) 1);
                    job.setMethodName(method.getName());
                    job.setBeanName(beanName);
                    job.setCronExpression(task.corn());
                    job.setParams(task.params());
                    //设置默认名称
                    if(StringUtils.isEmpty(task.name())){
                        job.setJobName(beanName+"_"+method.getName()+"任务");
                    }else{
                        job.setJobName(task.name());
                    }
                    job.setRemark("系统自动扫描新增");
                    job.setCreateUser("system");
                    job.setCreateTime(DateUtil.getCurDate());
                    QuartzJobRunner.INIT_TASK_MAP.put(beanName+"_"+method.getName(),job);
                }

            }
        }
        return bean;
    }
}
