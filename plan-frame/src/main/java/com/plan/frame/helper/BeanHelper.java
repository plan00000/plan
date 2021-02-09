package com.plan.frame.helper;

import com.plan.frame.cache.DictinaryCache;
import com.plan.frame.entity.DateConvert;
import com.plan.frame.entity.DictConvert;
import com.plan.frame.entity.ValueObject;
import com.plan.frame.exception.BaseException;
import com.plan.frame.exception.ConditionException;
import com.plan.frame.exception.SystemException;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.DateUtil;
import com.plan.frame.util.SpringContextHolder;
import com.plan.frame.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: linzhihua
 * @Description: bean帮助类
 * @Date: Created in 2020/1/19 17:21
 * @Modified By:
 */
public class BeanHelper {
    private static Logger logger = Logger.getLogger(BeanHelper.class);

    /**
     * 把bean转换成Map格式
     * 支持List递归转换,List转换成转换成Key:list[i].propertyName格式
     * 支持FormatConvert转换格式注解
     *
     * @param map
     * @param bean
     * @return
     */
    public static Map<String, String> beanToMap(Map<String, String> map, Object bean) {
        Object value = null;
        String key = null;
        //判断Map和Bean是否为null
        if (map == null) {
            throw new SystemException("Bean转换成Map失败", "map不能为null", "请检查程序");
        }
        if (bean == null) {
            throw new SystemException("Bean转换成Map失败", "bean不能为null", "请检查程序");
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String propertyName = property.getName();
                key = propertyName;
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Class returnType = getter.getReturnType();

                if (CommonUtil.isPrimitiveOrPrimitiveWrapper(returnType) || Date.class.isAssignableFrom(returnType)) {
//                    map.put(key,formatPropertyValue(bean,propertyName));
                } else if (returnType.isAssignableFrom(List.class)) {
                    List list = (List) getter.invoke(bean);
                    if (CommonUtil.isNotEmpty(list)) {
                        for (int i = 0; i < list.size(); i++) {
                            Object listValue = list.get(i);
                            if (listValue == null) {
                                map.put(key + "[" + i + "]", "");
                            } else if (listValue != null && CommonUtil.isPrimitiveOrPrimitiveWrapper(listValue.getClass())) {
                                map.put(key + "[" + i + "]", listValue.toString());
                            } else {
                                Map<String, String> listBeanMap = beanToMap(map.getClass().newInstance(), list.get(i));
                                Set<Map.Entry<String,String>> listBeanMapEntry =listBeanMap.entrySet();
                                for (Map.Entry<String,String> e : listBeanMapEntry) {
                                    Object listBeanMapValue =e.getValue();
                                    if (listBeanMapValue != null) {
                                        map.put(key + "[" + i + "]." + e.getKey(), listBeanMapValue.toString());
                                    } else {
                                        map.put(key + "[" + i + "]." + e.getKey(), "");
                                    }
                                }
                            }
                        }
                    }
                } else {
                    value = getter.invoke(bean);
                    if (value != null) {
                        Map<String, String> listBeanMap = beanToMap(map.getClass().newInstance(), value);
                        Set<String> listBeanMapKeys = listBeanMap.keySet();
                        for (String listBeanMapKey : listBeanMapKeys) {
                            Object listBeanMapValue = listBeanMap.get(listBeanMapKey);
                            if (listBeanMapValue != null) {
                                map.put(key + "." + listBeanMapKey, listBeanMapValue.toString());
                            } else {
                                map.put(key + "." + listBeanMapKey, "");
                            }
                        }
                    }

                }
            }
            return map;
        } catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("Bean转换成Map异常", e, "请联系管理员");
            }
        }
    }

    /**
     *
     * @param sourceEntity 值来源实体
     * @param targetEntity 被赋值实体
     * @param <T> 默认转换
     */
    public static <T> void copyBeanValue(T sourceEntity, T targetEntity){
        copyBeanValue(sourceEntity,targetEntity,true);
    }

    /**
     * 实体间值的copy
     *
     * @param sourceEntity 值来源实体
     * @param targetEntity 被赋值实体
     * @param isConvert 是否要转换
     * @return
     */
    public static <T> void copyBeanValue(T sourceEntity, T targetEntity,Boolean isConvert) {
        try {
            //值来源bean
            BeanInfo sourceBeanInfo = Introspector.getBeanInfo(sourceEntity.getClass());
            PropertyDescriptor[] sourcePropertyDescs = sourceBeanInfo.getPropertyDescriptors();
            //目标bean
            BeanInfo targetBeanInfo = Introspector.getBeanInfo(targetEntity.getClass());
            PropertyDescriptor[] targetPropertyDescs = targetBeanInfo.getPropertyDescriptors();
            Map<String, String> sourceMap = new HashMap<>();

            Map<String,DictConvert> dictConvertMap = new HashMap<>();
            Map<String, DateConvert> dateConvertMap = new HashMap<>();
            if(isConvert) {//注解进行翻译转换
                Field[] fields = targetEntity.getClass().getDeclaredFields();
                for (Field f : fields) {
                    // 判断字段注解是否存在
                    boolean apiModelPropertyExist = f.isAnnotationPresent(DictConvert.class);
                    if (apiModelPropertyExist) {
                        DictConvert dictConvert = f.getAnnotation(DictConvert.class);
                        dictConvertMap.put(f.getName(), dictConvert);
                    }
                    boolean dateModelPropertyExist = f.isAnnotationPresent(DateConvert.class);
                    if (dateModelPropertyExist) {
                        DateConvert dateConvert = f.getAnnotation(DateConvert.class);
                        dateConvertMap.put(f.getName(), dateConvert);
                    }

                }
            }

            for (PropertyDescriptor sourcePropertyDes : sourcePropertyDescs) {
                String propertyName = sourcePropertyDes.getName();
                Method getter = sourcePropertyDes.getReadMethod();
                Object propertyValue = getter.invoke(sourceEntity);
                if (CommonUtil.isNotEmpty(propertyValue) && !StringUtil.equalsString(propertyName, "class")) {
                    //所有的字段类型都转换为String
                    Class clazz = sourcePropertyDes.getPropertyType();
                    String typeName = clazz.getName();
                    if (typeName.contains("Date")) {
                        sourceMap.put(propertyName, DateUtil.date2Str((Date) propertyValue));
                    } else {
                        sourceMap.put(propertyName, String.valueOf(propertyValue));
                    }
                }
            }
            for (PropertyDescriptor targetPropertyDes : targetPropertyDescs) {
                String targetPropertyName = targetPropertyDes.getName();
                if (CommonUtil.isNotEmpty(sourceMap.get(targetPropertyName))) {
                    Method setter = targetPropertyDes.getWriteMethod();
                    //判断字段的类型
                    Class clazz = targetPropertyDes.getPropertyType();
                    String typeName = clazz.getName();
                    if (typeName.contains("String")) {

                        String sourceValue = sourceMap.get(targetPropertyName);
                        if(isConvert) {//注解进行转义和翻译
                            DictConvert dictConvert = dictConvertMap.get(targetPropertyName);
                            if (CommonUtil.isNotEmpty(dictConvert)) {
                                if (CommonUtil.isEmpty(dictConvert.beanName())) {
                                    if (CommonUtil.isEmpty(dictConvert.dictType())) {
                                        throw new SystemException("字典值翻译异常", targetEntity.getClass().getName() + "." + targetPropertyName
                                                + "属性的dictConvert注解dictType不能为空"
                                                , "请检查程序");
                                    }
                                    String dictCnNameValue = DictinaryCache.getCache().getDictCnName(dictConvert.dictType(), sourceValue);
                                    if (CommonUtil.isEmpty(dictCnNameValue)) {
                                        /*throw new SystemException("字典值翻译异常", targetEntity.getClass().getName() + "." + targetPropertyName
                                                + "=" + sourceValue + " 经过DictConvert注解代码对照转换后的值为空"
                                                , "请检查对照表");*/
                                        dictCnNameValue = "无效字典值-"+sourceValue;
                                    }
                                    sourceValue = dictCnNameValue;
                                } else {
                                    Object o = SpringContextHolder.getBean(dictConvert.beanName());
                                    Class dictBeanClass = o.getClass();
                                    if (CommonUtil.isEmpty(dictBeanClass)) {
                                        throw new SystemException("项目字典翻译异常", "不存在" + dictConvert.beanName() + "的缓存bean", "请检查程序");
                                    }
                                    //获取bean对应的方法

                                    Method beanMethod = dictBeanClass.getDeclaredMethod("getDictName", String.class);
                                    if (CommonUtil.isNotEmpty(beanMethod)) {
                                        String dictCnNameValue = (String) beanMethod.invoke(o, sourceValue);
                                        sourceValue = dictCnNameValue;
                                    }

                                }
                            }
                            DateConvert dateConvert = dateConvertMap.get(targetPropertyName);
                            if (CommonUtil.isNotEmpty(dateConvert)) {
                                sourceValue = DateUtil.dateStr2Str(sourceValue, "yyyy-MM-dd HH:mm:ss", dateConvert.format());
                            }
                        }

                        setter.invoke(targetEntity, sourceValue);
                    } else if (typeName.contains("BigDecimal")) {
                        setter.invoke(targetEntity, (new BigDecimal(sourceMap.get(targetPropertyName))));
                    } else if (typeName.contains("Date")) {
                        String sourceValue = sourceMap.get(targetPropertyName);
                        SimpleDateFormat sf = new SimpleDateFormat(DateUtil.getTimeFormat(sourceValue));
                        setter.invoke(targetEntity, sf.parse(sourceMap.get(targetPropertyName)));
                    } else if (typeName.contains("Integer") || typeName.contains("int")) {
                        setter.invoke(targetEntity, Integer.parseInt(sourceMap.get(targetPropertyName)));
                    } else if (typeName.contains("Long") || typeName.contains("long")) {
                        setter.invoke(targetEntity, Long.valueOf(sourceMap.get(targetPropertyName)));
                    } else if (typeName.contains("Float") || typeName.contains("float")) {
                        setter.invoke(targetEntity, Float.valueOf(sourceMap.get(targetPropertyName)));
                    }else if(typeName.contains("Byte")|| typeName.contains("byte")){
                        setter.invoke(targetEntity,Byte.valueOf(sourceMap.get(targetPropertyName)));
                    }else if(typeName.contains("Double")||typeName.contains("double")){
                        setter.invoke(targetEntity,Double.valueOf(sourceMap.get(targetPropertyName)));
                    }
                }
            }
        } catch (Exception e) {
            throw new SystemException("bean值copy出错", e, "请联系管理员处理");
        }
    }


    /**
     * voList转List<bean>
     * @param sourceVoList
     * @param  targetEntity
     * @param <T>
     */
    public static <T> List<T> voListToBeanList(List<ValueObject> sourceVoList, Class<T> targetEntity) {
        if(CommonUtil.isEmpty(sourceVoList)){
            return null;
        }
        try {
            List<T> targetEntityList = new ArrayList<T>();
            for (ValueObject sourceVo : sourceVoList) {
                T t = targetEntity.newInstance();
                voToBean(sourceVo, t);
                targetEntityList.add(t);
            }
            return targetEntityList;
        } catch (Exception e) {
            throw new SystemException("voList转换成beanList出错", e, "请联系管理员处理");
        }

    }

    /**
     * vo转bean
     *
     * @param sourceVo 数据来源Vo
     * @param targetEntity 目标实体
     * @param <T>
     */
    public static <T> void voToBean(ValueObject sourceVo, T targetEntity) {
        try {
            if(CommonUtil.isNotEmpty(sourceVo)) {

                //目标bean
                BeanInfo targetBeanInfo = null;
                targetBeanInfo = Introspector.getBeanInfo(targetEntity.getClass());
                PropertyDescriptor[] targetPropertyDescs = targetBeanInfo.getPropertyDescriptors();

                Field[] fields = targetEntity.getClass().getDeclaredFields();

                Map<String,DictConvert> dictConvertMap = new HashMap<>();
                Map<String, DateConvert> dateConvertMap = new HashMap<>();
                for (Field f : fields) {
                    // 判断字段注解是否存在
                    boolean apiModelPropertyExist = f.isAnnotationPresent(DictConvert.class);
                    if (apiModelPropertyExist) {
                        DictConvert dictConvert = f.getAnnotation(DictConvert.class);
                        dictConvertMap.put(f.getName(),dictConvert);
                    }
                    boolean dateModelPropertyExist = f.isAnnotationPresent(DateConvert.class);
                    if(dateModelPropertyExist){
                        DateConvert dateConvert = f.getAnnotation(DateConvert.class);
                        dateConvertMap.put(f.getName(),dateConvert);
                    }

                }

                for (PropertyDescriptor targetPropertyDes : targetPropertyDescs) {
                    String targetPropertyName = targetPropertyDes.getName();
                    if (CommonUtil.isNotEmpty(sourceVo.get(targetPropertyName))) {
                        Method setter = targetPropertyDes.getWriteMethod();
                        //判断字段的类型
                        Class clazz = targetPropertyDes.getPropertyType();
                        String typeName = clazz.getName();
                        if (typeName.contains("String")) {
                            String sourceVoValue = sourceVo.getAsString(targetPropertyName);
                            //
                            DictConvert dictConvert = dictConvertMap.get(targetPropertyName);
                            if(CommonUtil.isNotEmpty(dictConvert)) {
                                if (CommonUtil.isEmpty(dictConvert.dictType())) {
                                    throw new SystemException("字典值翻译异常", targetEntity.getClass().getName() + "." + targetPropertyName
                                            + "属性的dictConvert注解dictType不能为空"
                                            , "请检查程序");
                                }
                                String dictCnNameValue = DictinaryCache.getCache().getDictCnName(dictConvert.dictType(), sourceVoValue);
                                if (CommonUtil.isEmpty(dictCnNameValue)) {
                                    /*throw new SystemException("字典值翻译异常", targetEntity.getClass().getName() + "." + targetPropertyName
                                            + "=" + sourceVoValue + " 经过DictConvert注解代码对照转换后的值为空"
                                            , "请检查对照表");*/
                                    dictCnNameValue = "无效字典值-"+sourceVoValue;
                                }
                                sourceVoValue = dictCnNameValue;
                            }

                            DateConvert dateConvert = dateConvertMap.get(targetPropertyName);
                            if(CommonUtil.isNotEmpty(dateConvert)){
                                sourceVoValue = DateUtil.dateStr2Str(sourceVoValue,"yyyy-MM-dd HH:mm:ss",dateConvert.format());
                            }
                            setter.invoke(targetEntity, sourceVoValue);
                        } else if (typeName.contains("BigDecimal")) {
                            setter.invoke(targetEntity, sourceVo.getAsBigDecimal(targetPropertyName));
                        } else if (typeName.contains("Date")) {

                            setter.invoke(targetEntity, sourceVo.getAsDate(targetPropertyName, "yyyy-MM-dd HH:mm:ss"));
                        } else if (typeName.contains("Integer") || typeName.contains("int")) {
                            setter.invoke(targetEntity, sourceVo.getAsInteger(targetPropertyName));
                        } else if (typeName.contains("Long") || typeName.contains("long")) {
                            setter.invoke(targetEntity, sourceVo.getAsLong(targetPropertyName));
                        } else if (typeName.contains("Float") || typeName.contains("float")) {
                            setter.invoke(targetEntity, Float.valueOf(sourceVo.getAsString(targetPropertyName)));
                        }else if(typeName.contains("Double")||typeName.contains("double")){
                            setter.invoke(targetEntity,Double.valueOf(sourceVo.getAsDouble(targetPropertyName)));
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new SystemException("bean值copy出错", e, "请联系管理员处理");
        }catch (InvocationTargetException e){
            throw new SystemException("bean值copy出错", e, "请联系管理员处理");
        }catch (IntrospectionException e){
            throw new SystemException("bean值copy出错",e,"请联系管理员处理");
        }
    }

    /**
     * 数据完整性检查
     * 判断Bean对应的属性是否为空
     * @param bean
     * @param propertyNames
     * @return
     */
    public static void checkPropertyEmpty(Object bean, String ... propertyNames) {
        try {
            List<String> emptyPropertyNames = new ArrayList<String>();
            String emptyProperty = "";
            Class beanClass = bean.getClass();
            Field[] fields = beanClass.getDeclaredFields();
            BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            HashMap<String,PropertyDescriptor> propertyDescriptorHashMap = new HashMap<>();
            for(PropertyDescriptor propertyDescriptor:propertyDescriptors){
                propertyDescriptorHashMap.put(propertyDescriptor.getName(),propertyDescriptor);
            }

            for (String propertyName : propertyNames) {
                boolean find=false;
                for (Field f : fields) {
                    if (StringUtil.equalsString(f.getName(), propertyName)) {
                        Method getter = propertyDescriptorHashMap.get(f.getName()).getReadMethod();
                        PropertyDescriptor p = propertyDescriptorHashMap.get(f.getName());
                        Object propertyValue = getter.invoke(bean);
                        find=true;
                        if(CommonUtil.isEmpty(propertyValue)) {
                            // 判断字段注解是否存在
                            boolean apiModelPropertyExist = f.isAnnotationPresent(ApiModelProperty.class);
                            if (apiModelPropertyExist) {
                                ApiModelProperty apiModelProperty = f.getAnnotation(ApiModelProperty.class);
                                if (CommonUtil.isEmpty(apiModelProperty.value())) {
                                    throw new ConditionException("检查对象属性异常", bean.getClass().getName() + "." + propertyName + "属性apiModelProperty注解值不能为空,为空不能显示属性中文名称", "请增加注解值");
                                }
                                emptyPropertyNames.add(apiModelProperty.value());
                                emptyProperty += apiModelProperty.value() + "、";
                            } else {
                                throw new ConditionException("检查对象属性异常", bean.getClass().getName() + "." + propertyName + "属性没有apiModelProperty注解不能显示属性中文名称", "请增加注解");
                            }
                        }
                        break;
                    }
                }
                if(!find){
                    throw new ConditionException("检查对象属性异常", bean.getClass().getName()+"没有"+propertyName+"属性","请联系管理员");
                }
            }
            if (CommonUtil.isNotEmpty(emptyProperty)) {
                emptyProperty = emptyProperty.substring(0, emptyProperty.length() - 1);//截取最后一个顿号
            }
            if(CommonUtil.isNotEmpty(emptyProperty)){
                throw new ConditionException("数据完整性检查","字段数据："+emptyProperty+"不能为空","请对相应的数据进行完整性检查");
            }
        } catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("检查对象属性异常", e, "请联系管理员");
            }
        }
    }

    /**
     * 设置数据库实体类操作用户信息
     * modifyDate、modifyUserId 每次都设置新值
     * createDate、createUserId 只新增的才会设置
     * @param entity
     * @param <T>
     */
    public static <T> void setUserInfo(T entity,boolean isNew) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(entity.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String propertyName = property.getName();
                Object value = null;
                if (CommonUtil.equalsNumberOrChar("createTime", propertyName)&&isNew) {
                    value = new Date();
                } else if (CommonUtil.equalsNumberOrChar("createUser", propertyName)&&isNew) {
                    if(CommonUtil.isNotEmpty(SecurityUtils.getSubject())&&CommonUtil.isNotEmpty(SecurityUtils.getSubject().getSession())
                            &&CommonUtil.isNotEmpty(SecurityUtils.getSubject().getSession().getId())) {
                        value = SecurityUtils.getSubject().getSession().getId().toString();
                    }
                } else if (CommonUtil.equalsNumberOrChar("updateTime", propertyName)) {
                    value = new Date();
                } else if (CommonUtil.equalsNumberOrChar("updateUser", propertyName)) {
                    if(CommonUtil.isNotEmpty(SecurityUtils.getSubject())&&CommonUtil.isNotEmpty(SecurityUtils.getSubject().getSession())
                            &&CommonUtil.isNotEmpty(SecurityUtils.getSubject().getSession().getId())) {
                        value = SecurityUtils.getSubject().getSession().getId().toString();
                    }
                }
                if (value != null) {
                    Method setter = property.getWriteMethod();
                    setter.invoke(entity, value);
                }
            }
        } catch (Exception e) {
            throw new ConditionException("设置实体Bean用户操作信息失败",e,"请联系管理员");
        }
    }


}
