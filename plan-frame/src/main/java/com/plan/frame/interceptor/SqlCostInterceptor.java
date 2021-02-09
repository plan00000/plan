package com.plan.frame.interceptor;

/**
 * Created by 林志华 on 2020/1/19.
 */

import com.plan.frame.helper.BeanHelper;
import com.plan.frame.util.DateUtil;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.defaults.DefaultSqlSession.StrictMap;
import org.apache.log4j.Logger;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Statement;
import java.util.*;

/**
 * @Author: linzhihua
 * @Description: Sql执行时间记录拦截器
 * @Date: Created in 2019/8/22 6:57
 * @Modified By:
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})
public class SqlCostInterceptor implements Interceptor {
    private static Logger logger = Logger.getLogger(SqlCostInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();

        long startTime = System.currentTimeMillis();
        StatementHandler statementHandler = (StatementHandler) target;
        boolean isError = false;
        String errMsg = "";
        try {
            try {
                return invocation.proceed();
            } catch (Throwable t) {
                isError = true;
                errMsg = t.getMessage();
                throw (Throwable) t;
            }
        } finally {
            //是否打印
            if(Boolean.getBoolean("isDevelop")) {
                long endTime = System.currentTimeMillis();
                long sqlCost = endTime - startTime;
//            sqlCost = 2322;
                if (sqlCost > 2000 || Boolean.getBoolean("isDevelop") && sqlCost > 20) {
                    if (!logger.isDebugEnabled()) {
                        BoundSql boundSql = statementHandler.getBoundSql();
                        String sql = boundSql.getSql();
                        Object parameterObject = boundSql.getParameterObject();
                        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();

                        // 格式化Sql语句，去除换行符，替换参数
                        sql = formatSql(sql, parameterObject, parameterMappingList);

                        logger.warn(" " + "SQL：" + sql);
                    }
                    logger.warn(" " + "执行耗时：" + sqlCost + "ms");
                } else {
                    BoundSql boundSql = statementHandler.getBoundSql();
                    String sql = boundSql.getSql();
                    Object parameterObject = boundSql.getParameterObject();
                    List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();

                    // 格式化Sql语句，去除换行符，替换参数
                    sql = formatSql(sql, parameterObject, parameterMappingList);
                    if (isError) {
                        logger.error(" " + errMsg + "  SQL：" + sql);
                    } else {
                        logger.info(" " + "SQL：" + sql);
                    }
                    logger.info(" " + "执行耗时：" + sqlCost + "ms");

                }
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    @SuppressWarnings("unchecked")
    private String formatSql(String sql, //sql
                             Object parameterObject,//参数对象
                             List<ParameterMapping> parameterMappingList//参数映射
    ) {
        // 输入sql字符串空判断
        if (sql == null || sql.length() == 0) {
            return "";
        }

        // 美化sql
        sql = beautifySql(sql);

        // 不传参数的场景，直接把Sql美化一下返回出去
        if (parameterObject == null || parameterMappingList == null || parameterMappingList.size() == 0) {
            return sql;
        }

        // 定义一个没有替换过占位符的sql，用于出异常时返回
        String sqlWithoutReplacePlaceholder = sql;

        try {
            if (parameterMappingList != null) {                 //参数不能为空
                Class<?> parameterObjectClass = parameterObject.getClass();//取参数对象

                // 如果参数是StrictMap且Value类型为Collection，获取key="list"的属性，这里主要是为了处理<foreach>循环时传入List这种参数的占位符替换
                // 例如select * from xxx where id in <foreach collection="list">...</foreach>
                if (isStrictMap(parameterObjectClass)) {
                    StrictMap<Collection<?>> strictMap = (StrictMap<Collection<?>>) parameterObject;

                    if (isList(strictMap.get("list").getClass())) {
                        sql = handleListParameter(sql, strictMap.get("list"));
                    }
                } else if (isMap(parameterObjectClass)) {
                    // 如果参数是Map则直接强转，通过map.get(key)方法获取真正的属性值
                    // 这里主要是为了处理<insert>、<delete>、<update>、<select>时传入parameterType为map的场景
                    Map<?, ?> paramMap = (Map<?, ?>) parameterObject;
                    sql = handleMapParameter(sql, paramMap, parameterMappingList);
                } else {
                    // 通用场景，比如传的是一个自定义的对象或者八种基本数据类型之一或者String
                    sql = handleCommonParameter(sql, parameterMappingList, parameterObjectClass, parameterObject);
                }
            }
        } catch (Exception e) {
            // 占位符替换过程中出现异常，则返回没有替换过占位符但是格式美化过的sql，这样至少保证sql语句比BoundSql中的sql更好看
            return sqlWithoutReplacePlaceholder;
        }

        return sql;
    }

    /**
     * 美化Sql
     */
    private String beautifySql(String sql) {
        // sql = sql.replace("\n", "").replace("\t", "").replace("  ", " ").replace("( ", "(").replace(" )", ")").replace(" ,", ",");
        sql = sql.replaceAll("[\\s\n ]+", " ");
        return sql;
    }

    /**
     * 处理参数为List的场景
     */
    private String handleListParameter(String sql, Collection<?> col) {
        if (col != null && col.size() != 0) {
            for (Object obj : col) {
                String value = "";
                Class<?> objClass = obj.getClass();

                // 只处理基本数据类型、基本数据类型的包装类、String这三种
                // 如果是复合类型也是可以的，不过复杂点且这种场景较少，写代码的时候要判断一下要拿到的是复合类型中的哪个属性
                if (isPrimitiveOrPrimitiveWrapper(objClass)) {
                    value = obj.toString();
                } else if (objClass.isAssignableFrom(String.class)) {
                    value = "'" + obj.toString() + "'";
                } else if (objClass.getClass().isAssignableFrom(Date.class)) {
                    value = "to_date('" + DateUtil.date2Str((Date) obj) + "','yyyy-mm-dd hh24:mi:ss')";
                }

                sql = sql.replaceFirst("\\?", value);
            }
        }

        return sql;
    }

    /**
     * 处理参数为Map的场景
     */
    private String handleMapParameter(String sql, Map<?, ?> paramMap, List<ParameterMapping> parameterMappingList) {
        for (ParameterMapping parameterMapping : parameterMappingList) {
            String propertyName = parameterMapping.getProperty();
            String[] params = propertyName.split("\\.");
            List list = Arrays.asList(params);
            List<String> paramNames = new ArrayList<String>(list);
            Object propertyValue = this.deepGetProperty(paramMap, paramNames);
            if (propertyValue != null) {
                if (propertyValue.getClass().isAssignableFrom(String.class)) {
                    propertyValue = "'" + propertyValue + "'";
                } else if (propertyValue.getClass().isAssignableFrom(Date.class)) {
                    propertyValue = "to_date('" + DateUtil.date2Str((Date) propertyValue) + "','yyyy-mm-dd hh24:mi:ss')";
                }

                sql = sql.replaceFirst("\\?", propertyValue.toString());
            }
        }

        return sql;
    }

    /**
     * 深度取对象值,只取第一个返回,如果,数组,长度,还是一样,不管
     *
     * @param paramMap
     * @param params
     * @return
     */
    private Object deepGetProperty(Map<?, ?> paramMap, List<String> params) {
        if (params.size() > 0) {
            String propertyName = params.get(0);
            Object propertyValue = paramMap.get(propertyName);
            params.remove(0);
            if (params.size() > 0) {
                if (isMap(propertyValue.getClass())) {
                    return this.deepGetProperty((Map<?, ?>) propertyValue, params);
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    BeanHelper.beanToMap(map, propertyValue);
                    return this.deepGetProperty(map, params);
                }
            } else {
                return propertyValue;
            }
        }
        return null;
    }

    /**
     * 处理通用的场景
     */
    private String handleCommonParameter(String sql, List<ParameterMapping> parameterMappingList, Class<?> parameterObjectClass,
                                         Object parameterObject) throws Exception {
        for (ParameterMapping parameterMapping : parameterMappingList) {
            String propertyValue = null;
            // 基本数据类型或者基本数据类型的包装类，直接toString即可获取其真正的参数值，其余直接取paramterMapping中的property属性即可
            if (isPrimitiveOrPrimitiveWrapper(parameterObjectClass)) {
                propertyValue = parameterObject.toString();
            } else {
                String propertyName = parameterMapping.getProperty();
                Field[] fields = parameterObjectClass.getDeclaredFields();
                Field field = parameterObjectClass.getDeclaredField(propertyName);
                // 要获取Field中的属性值，这里必须将私有属性的accessible设置为true
                field.setAccessible(true);
                Object propertyValue2 = field.get(parameterObject);
                if (parameterMapping.getJavaType().isAssignableFrom(String.class)) {
                    propertyValue = "'" + propertyValue2 + "'";
                } else if (propertyValue2 != null && propertyValue2 instanceof Date) {
                    propertyValue = "to_date('" + DateUtil.date2Str((Date) propertyValue2) + "','yyyy-mm-dd hh24:mi:ss')";
                } else if (propertyValue2 != null && propertyValue2 instanceof StringReader) {
                    propertyValue = propertyValue2.toString();
                } else {
                    propertyValue = String.valueOf(propertyValue2);
                }
            }

            sql = sql.replaceFirst("\\?", propertyValue.replaceAll("\\$", "#"));
        }

        return sql;
    }

    /**
     * 是否基本数据类型或者基本数据类型的包装类
     */
    private boolean isPrimitiveOrPrimitiveWrapper(Class<?> parameterObjectClass) {
        return parameterObjectClass.isPrimitive() ||
                (parameterObjectClass.isAssignableFrom(Byte.class) || parameterObjectClass.isAssignableFrom(Short.class) ||
                        parameterObjectClass.isAssignableFrom(Integer.class) || parameterObjectClass.isAssignableFrom(Long.class) ||
                        parameterObjectClass.isAssignableFrom(Double.class) || parameterObjectClass.isAssignableFrom(Float.class) ||
                        parameterObjectClass.isAssignableFrom(Character.class) || parameterObjectClass.isAssignableFrom(Boolean.class)) ||
                parameterObjectClass.isAssignableFrom(String.class) || parameterObjectClass.isAssignableFrom(BigDecimal.class);
    }

    /**
     * 是否DefaultSqlSession的内部类StrictMap
     */
    private boolean isStrictMap(Class<?> parameterObjectClass) {
        return parameterObjectClass.isAssignableFrom(StrictMap.class);
    }

    /**
     * 是否List的实现类
     */
    private boolean isList(Class<?> clazz) {
        Class<?>[] interfaceClasses = clazz.getInterfaces();
        for (Class<?> interfaceClass : interfaceClasses) {
            if (interfaceClass.isAssignableFrom(List.class)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 是否Map的实现类
     */
    private boolean isMap(Class<?> parameterObjectClass) {
        String name = parameterObjectClass.getName();
        if (parameterObjectClass.getName().equals("org.apache.ibatis.binding.MapperMethod$ParamMap")) return true;
        Class<?>[] interfaceClasses = parameterObjectClass.getInterfaces();

        for (Class<?> interfaceClass : interfaceClasses) {
            if (interfaceClass.isAssignableFrom(Map.class)) {
                return true;
            }
        }
        return false;
    }

}
