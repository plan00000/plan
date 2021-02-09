package com.plan.frame.proxy;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: linzhihua
 * @Description: 根据注解类型配置创建动态代理
 * @Date: Created in 2019/8/22 20:36
 * @Modified By:
 */
public class AnnotationAutoProxyCreator extends AbstractAutoProxyCreator implements InitializingBean {
    private List<String> basePackages=null;
    private List<Class> annotationClasses=null;

    /**
     * 设置需要动态代理的包路径，没有设置为所有符合注解的类创建动态代理
     * @param basePackages
     */

    public void setBasePackages(String... basePackages) {
        Assert.notEmpty(basePackages, "'basePackages' 不能为空！");
        this.basePackages=new ArrayList<String>();
        for(String basePackage:basePackages ){
            basePackage= StringUtils.trimWhitespace(basePackage);
            if(StringUtils.isEmpty(basePackage)){
                throw new IllegalArgumentException("Base Package Value 不能为空");
            }
            this.basePackages.add(basePackage+".");//"."作为包结尾
        }
    }

    public void setAnnotationClasses(String... annotationClassNames) {
        Assert.notEmpty(annotationClassNames, "'annotationClassNames' 不能为空！");
        this.annotationClasses=new ArrayList<Class>();
        for(String annotationClassName:annotationClassNames ){
            try {
                annotationClasses.add(Class.forName(StringUtils.trimWhitespace(annotationClassName)));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(annotationClassName+"注解类没有找到");
            }
        }
    }

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class beanClass, String beanName, TargetSource customTargetSource) throws BeansException {

        /**
         * 检查是否需要代理的基本包路径，不符合的不代理
         *
         */
        Class targetClass=beanClass;
        if(basePackages!=null&&basePackages.size()>0) {
            boolean isBasePackage = false;
            for (String basePackage : basePackages) {
                if(targetClass.getName().startsWith(basePackage)) {
                    isBasePackage =true;
                    break;
                }
            }
            if(isBasePackage==false){
                return DO_NOT_PROXY;
            }
        }

        for (Class annotation: this.annotationClasses){
            if(targetClass.isAnnotationPresent(annotation)){
                return PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
            }else{
                if( ClassUtils.isCglibProxyClass(targetClass)){
                    targetClass=targetClass.getSuperclass();
                }else if(Proxy.isProxyClass(targetClass) ) {
                    //不会有
                } else{
                    return DO_NOT_PROXY;
                }
                if(targetClass.isAnnotationPresent(annotation)){
                    return PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
                }
            }
        }
        return DO_NOT_PROXY;
    }


    public void afterPropertiesSet() throws Exception {
        Assert.notEmpty(this.annotationClasses,"Property 'annotationClasses' is required");
    }
}
