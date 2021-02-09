package com.plan.frame.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Created by linzhihua
 * @Description: 日期格式注解转换
 * @ClassName DateConvert
 * @Date 2020/7/6 10:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface DateConvert {
    String value() default "";
    String format() default "";
}
