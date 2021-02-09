package com.plan.frame.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Created by linzhihua
 * @Description:字典转换注解，需要字典翻译在字段上添加该注解
 * <备注：>被注解的字段要定义成String类型</>
 * 目前支持BeanHelper.voToBean(),BeanHelper.voListToBeanList(),BeanHelper.copyBeanValue();
 * @ClassName DictConvert
 * @Date 2020/7/2 10:26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface DictConvert {
    String value() default "";
    //字典代码类型
    String dictType() default "";
    //存于缓存bean名称
    String beanName() default "";
    //默认显示值
    String defalutValue() default "";
}
