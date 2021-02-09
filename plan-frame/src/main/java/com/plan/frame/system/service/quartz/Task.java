package com.plan.frame.system.service.quartz;

import java.lang.annotation.*;

/**
 * Created by huangdongliang on 2020/5/21.
 * 定时任务注解接口
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Task {
    /**
     * 任务名称
     * @return String
     */
    String name() default "";

    /**
     * 定时corn 表达式
     * @return String
     */
    String corn() default "0 0 12 * * ?";

    /**
     * 是否执行，默认true
     * @return boolean
     */
    boolean run() default true;

    /**
     * 执行传入参数
     * @return
     */
    String params() default "";

    /**
     * 任务描述
     * @return
     */
    String description() default "";

}
