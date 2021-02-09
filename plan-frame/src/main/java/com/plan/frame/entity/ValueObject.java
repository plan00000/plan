package com.plan.frame.entity;

import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.DateUtil;
import com.plan.frame.util.TypeCaseUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * map格式的数据对象
 * 该对象用于mybatis多表关联查询时返回获取数据
 * linzhihua
 * 2019-08-25
 */
public class ValueObject<K, V> extends ConcurrentHashMap<K, V> {

    /**
     * 以BigDecimal类型返回键值
     *
     * @param key
     *            键名
     * @return BigDecimal 键值
     */
    public Double getAsDouble(String key) {
        Object obj = TypeCaseUtil.convert(get(key), "Double", null);
        if (obj != null)
            return (Double) obj;
        else
            return null;
    }

    /**
     * 以BigDecimal类型返回键值
     *
     * @param key
     *            键名
     * @return BigDecimal 键值
     */
    public Double getAsDoubleNullToZore(String key) {
        Double asDouble = getAsDouble(key);
        if(asDouble==null){
            return 0d;
        }else{
            return asDouble;
        }
    }

    /**
     * 以BigDecimal类型返回键值
     *
     * @param key
     *            键名
     * @return BigDecimal 键值
     */
    public BigDecimal getAsBigDecimal(String key) {
        Object obj = TypeCaseUtil.convert(get(key), "BigDecimal", null);
        if (obj != null)
            return (BigDecimal) obj;
        else
            return null;
    }

    /**
     * 以BigDecimal类型返回键值
     *
     * @param key
     *            键名
     * @return BigDecimal 键值
     */
    public BigDecimal getAsBigDecimalNullToZore(String key) {
        BigDecimal bigDecimal = getAsBigDecimal(key);
        if(bigDecimal==null){
            return new BigDecimal(0);
        }else{
            return bigDecimal;
        }
    }

    /**
     * 以Date类型返回键值
     *
     * @param key
     *            键名
     * @return Date 键值
     */
    public Date getAsDateTime(String key) {
        Object obj = TypeCaseUtil.convert(get(key), "Date", "yyyy-MM-dd HH:mm:ss");
        if (obj != null) {
            if(obj instanceof Date){
                return (Date) obj;
            }
            return sqlDate2JavaDate((java.sql.Date) obj);

        }
        else
            return null;
    }

    /**
     * 以Date类型返回键值
     *
     * @param key
     *            键名
     * @return Date 键值
     */
    public Date getAsDate(String key) {
        Object obj = TypeCaseUtil.convert(get(key), "Date", "yyyy-MM-dd");
        if (obj != null) {
            Date d1 = sqlDate2JavaDate((java.sql.Date) obj);
            d1= DateUtil.str2Date(DateUtil.date2Str(d1,"yyyy-MM-dd"),"yyyy-MM-dd");
            return d1;
        }
        else
            return null;
    }

    /**
     * 以Date类型返回键值
     *
     * @param key
     *            键名
     * @return Date 键值
     */
    public Date getAsDate(String key, String format) {
        Object obj = TypeCaseUtil.convert(get(key), "Date", format);
        if (obj != null)
            return (Date) obj;
        else
            return null;
    }

    /**
     * 以Integer类型返回键值
     *
     * @param key
     *            键名
     * @return Integer 键值
     */
    public Integer getAsInteger(String key) {
        Object obj = TypeCaseUtil.convert(get(key), "Integer", null);
        if (obj != null)
            return (Integer) obj;
        else
            return null;
    }

    /**
     * 以Integer类型返回键值
     *
     * @param key
     *            键名
     * @return Integer 键值
     */
    public Short getAsShort(String key) {
        Object obj = TypeCaseUtil.convert(get(key), "Short", null);
        if (obj != null)
            return (Short) obj;
        else
            return null;
    }

    /**
     * 以Long类型返回键值
     *
     * @param key
     *            键名
     * @return Long 键值
     */
    public Long getAsLong(String key) {
        Object obj = TypeCaseUtil.convert(get(key), "Long", null);
        if (obj != null)
            return (Long) obj;
        else
            return null;
    }

    /**
     * 以String类型返回键值
     *
     * @param key
     *            键名
     * @return String 键值
     */
    public String getAsString(String key) {
        Object obj = TypeCaseUtil.convert(get(key), "String", null);
        if (obj != null)
            return (String) obj;
        else
            return "";
    }

    /**
     * 以List类型返回键值
     *
     * @param key
     *            键名
     * @return List 键值
     */
    public List getAsList(String key) {
        return (List) get(key);
    }


    /**
     * 以Boolean类型返回键值
     *
     * @param key
     *            键名
     * @return Timestamp 键值
     */
    public Boolean getAsBoolean(String key) {
        Object obj = TypeCaseUtil.convert(get(key), "Boolean", null);
        if (obj != null)
            return (Boolean) obj;
        else
            return null;
    }

    /**
     * key 转换成java Bean驼峰命名规则
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {
        if(key instanceof String){
            String property= CommonUtil.columnToProperty((String)key);
            return super.put((K)property, value);
        }else {
            return super.put(key, value);
        }
    }

    /**
     * java.sql.Date sqlDate转换成Java.util.Date
     *
     * @param sqlDate
     * @return
     */
    private Date sqlDate2JavaDate(java.sql.Date sqlDate){
        return new Date(sqlDate.getTime());
    }
}
