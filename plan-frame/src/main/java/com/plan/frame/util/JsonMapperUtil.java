package com.plan.frame.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * 基于jackson的操作类
 * Created by xieyanling on 2020/3/19.
 */
public class JsonMapperUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonMapperUtil.class);
    private static JsonMapperUtil defaultMapper = buildNonNullMapper();
    private ObjectMapper OBJECT_MAPPER;

    public JsonMapperUtil(JsonInclude.Include include) {
        OBJECT_MAPPER = new ObjectMapper();
        //设置输出时包含属性的风格
        OBJECT_MAPPER.setSerializationInclusion(include);
        //设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //禁止使用int代表Enum的order()來反序列化Enum,非常危險
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
    }

    public static JsonMapperUtil getDefault() {
        return defaultMapper;
    }

    /**
     * 创建只输出非空属性到Json字符串的Mapper.
     *
     * @return
     */
    public static JsonMapperUtil buildNonNullMapper() {
        return new JsonMapperUtil(JsonInclude.Include.NON_NULL);
    }

    /**
     * 创建输出全部属性到Json字符串的Mapper.
     *
     * @return
     */
    public static JsonMapperUtil buildNormalMapper() {
        return new JsonMapperUtil(JsonInclude.Include.ALWAYS);
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper.
     *
     * @return
     */
    public static JsonMapperUtil buildNonDefaultMapper() {
        return new JsonMapperUtil(JsonInclude.Include.NON_DEFAULT);
    }

    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper.
     *
     * @return
     */
    public static JsonMapperUtil buildNonEmptyMapper() {
        return new JsonMapperUtil(JsonInclude.Include.NON_EMPTY);
    }

    public <T> T fromJson(String json, Class<T> tClass) {
        if (StringUtils.isBlank(json)) {
            logger.warn("json str is empty or null");
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, tClass);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: "
                    + json + " cannot be transformed to Json object");
        }
    }

    /**
     * 如果对象为Null, 返回"null".
     * 如果集合为空集合, 返回"[]".
     *
     * @param value :
     * @return :
     */
    public String toJson(Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The given Json object value: "
                    + value + " cannot be transformed to a String");
        }
    }


    /**
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合.
     * <p>
     * 如需读取集合如List/Map, 且不是List<String>这种简单类型时,先使用函數constructParametricType构造类型.
     *
     * @param jsonString :
     * @param javaType   :
     * @return :
     * @see #constructParametricType(Class, Class...)
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            logger.error("jsonString is empty or null");
            return null;
        }
        try {
            return (T) OBJECT_MAPPER.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public <T> T fromJson(InputStream is, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(is, clazz);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T fromJson(InputStream is, JavaType javaType) {
        try {
            return (T) OBJECT_MAPPER.readValue(is, javaType);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 构造泛型的Type如List<MyBean>, 则调用constructParametricType(ArrayList.class,MyBean.class)
     * Map<String,MyBean>则调用(HashMap.class,String.class, MyBean.class)
     *
     * @param parametrized     :
     * @param parameterClasses :
     * @return :
     */
    public JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    public JavaType constructParametricType(Class<?> parametrized, JavaType javaType) {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(parametrized, javaType);
    }

    public JavaType constructParametricType(Class<?> parametrized, JavaType[] javaTypes) {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(parametrized, javaTypes);
    }

    /**
     * 当JSON里只含有Bean的部分属性时，更新一个已存在Bean，只覆盖该部分的屬性.
     *
     * @param object     :
     * @param jsonString :
     * @return :
     */
    @SuppressWarnings("unchecked")
    public <T> T update(T object, String jsonString) {
        try {
            return (T) OBJECT_MAPPER.readerForUpdating(object).readValue(jsonString);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     *
     * @return :
     */
    public ObjectMapper getMapper() {
        return OBJECT_MAPPER;
    }

    public <T> T clone(T value) {
        return fromJson(toJson(value), (Class<T>) value.getClass());
    }
}
