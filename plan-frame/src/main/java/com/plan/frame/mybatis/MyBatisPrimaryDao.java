package com.plan.frame.mybatis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @Author: linzhihua
 * @Description: 第一数据源注解
 * @Date: Created in 2019/8/22 6:57
 * @Modified By:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyBatisPrimaryDao {
}
