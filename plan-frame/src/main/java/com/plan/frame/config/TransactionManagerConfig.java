package com.plan.frame.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.plan.frame.proxy.AnnotationAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.UserTransaction;
import java.util.Properties;

/**
 * @Author: linzhihua
 * @Description: 事务控制
 * @Date: Created in 2019/8/22 20:36
 * @Modified By:
 */
@Configuration
public class TransactionManagerConfig {
    /**
     * 分布式事务使用JTA管理，不管有多少个数据源只要配置一个 JtaTransactionManager
     * @return
     */
    @Bean(name="transactionManager")
    public JtaTransactionManager transactionManager(){
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }

    /**
     * 声明式事务配置_开始
     * @return
     */
    @Bean(name="transactionInterceptorStart")
    public TransactionInterceptor transactionInterceptor(){
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManagerBeanName("transactionManager");
        /*PROPAGATION_SUPPORTS: 如果已经存在事务,则加入事务;如果没有事务,则以非事务的方式执行;
        PROPAGATION_MANDATORY: 使用当前事务, 如果没有, 则抛出异常;
        PROPAGATION_REQUIRED: 新建事务,如果当前有事务, 则挂起; P
        ROPAGATION_NOT_SUPPORTED:以非事务的方式执行, 如果当前有事务, 则挂起;
        PROPAGATION_NEVER:以非事务的方式执行, 如果当前有事务,则抛出异常;
        +/-Exception</prop> + 表示异常出现时事物提交 - 表示异常出现时事务回滚 */
        Properties properties = new Properties();
        properties.setProperty("*","PROPAGATION_SUPPORTS,-Exception");
        transactionInterceptor.setTransactionAttributes(properties);
        return transactionInterceptor;
    }

    @Bean
    public AnnotationAutoProxyCreator annotationAutoProxyCreator(){
        AnnotationAutoProxyCreator annotationAutoProxyCreator = new AnnotationAutoProxyCreator();
        annotationAutoProxyCreator.setBasePackages("com.xmgps");
        annotationAutoProxyCreator.setProxyTargetClass(true);
        annotationAutoProxyCreator.setAnnotationClasses("org.springframework.stereotype.Service");
        annotationAutoProxyCreator.setInterceptorNames("transactionInterceptorStart");
        return annotationAutoProxyCreator;
    }

}
