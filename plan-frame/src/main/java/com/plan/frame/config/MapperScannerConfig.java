package com.plan.frame.config;

import com.plan.frame.mybatis.MyBatisPrimaryDao;
import com.plan.frame.mybatis.MyBatisTwoDao;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: linzhihua
 * @Description: 数据源注解
 * @Date: Created in 2019/8/22 8:57
 * @Modified By:
 */
@Configuration
public class MapperScannerConfig {
    /**
     * 主数据源注解
     * @return
     */
    @Bean
    public MapperScannerConfigurer primaryMapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.plan");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("primarySqlSessionFactory");
        mapperScannerConfigurer.setAnnotationClass(MyBatisPrimaryDao.class);
        return mapperScannerConfigurer;
    }

    /**
     * 第二数据源注解
     * @return
     */
    @Bean
    public MapperScannerConfigurer twoMapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.plan");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("twoSqlSessionFactory");
        mapperScannerConfigurer.setAnnotationClass(MyBatisTwoDao.class);
        return mapperScannerConfigurer;
    }



}
