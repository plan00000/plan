package com.plan.frame.helper;

import com.plan.frame.entity.Pageination;
import com.plan.frame.exception.SystemException;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.StringUtil;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Created by linzhihua
 * @Description 分页器帮助类
 * @ClassName PaginationHelper
 * @Date 2020/4/09 22:49
 */
public class PageinationHelper {
    /**
     * 分页器开始
     * @param sourceEntity
     * @return
     * @throws Exception
     */
    public static Pageination start(Object sourceEntity)throws Exception{
        Pageination pageination =null;
        BeanInfo sourceBeanInfo = Introspector.getBeanInfo(sourceEntity.getClass());
        PropertyDescriptor[] sourcePropertyDescriptors = sourceBeanInfo.getPropertyDescriptors();
        for (PropertyDescriptor sourceProperty : sourcePropertyDescriptors) {
            String propertyName = sourceProperty.getName();
            if(StringUtil.equalsString(propertyName,"pageination")){
                Method getter = sourceProperty.getReadMethod();
                Object sourcePropertyValue = getter.invoke(sourceEntity);
                if(CommonUtil.isEmpty(sourcePropertyValue)){
                    throw new SystemException("分页器分页出错","请求dto分页器pageination对象为空","请排查请求dto分页器实体数据完整性");
                }
                pageination = (Pageination) sourcePropertyValue;
                pageination.startPage();
                break;
            }
        }
        if(CommonUtil.isEmpty(pageination)){
            throw new SystemException("分页器分页出错","请求dto参数未包含pageination参数","请排查请求dto实体");
        }
        return pageination;
    }

    /**
     * 分页操作方法
     * @param pageination 分页器
     * @param resultList 进行分页的sql查询返回list
     * @param targetEntity 返回实体类dto（包含有分页器实体的dto）
     * @param <T>
     * @throws Exception
     */
    public static <T> void install(Pageination pageination, List<T> resultList,Object targetEntity)throws Exception{

        pageination.endPage(resultList);
        BeanInfo targetBeanInfo = Introspector.getBeanInfo(targetEntity.getClass());
        PropertyDescriptor[] targetPropertyDescriptors = targetBeanInfo.getPropertyDescriptors();
        Boolean checkTargetPropert = true;
        for (PropertyDescriptor targetProperty : targetPropertyDescriptors) {
            String propertyName = targetProperty.getName();
            if(StringUtil.equalsString(propertyName,"pageination")){
                checkTargetPropert = false;
                Method setter = targetProperty.getWriteMethod();
                setter.invoke(targetEntity,pageination);
                break;
            }
        }

        if(checkTargetPropert){
            throw new SystemException("分页器分页出错","返回dto参数未包含pageination参数","请排查返回dto实体");
        }

    }
}
