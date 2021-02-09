package com.plan.frame.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @Author: linzhihua
 * @Description: 工具类
 * @Date: Created in 2019/8/23 8:57
 * @Modified By:
 */
public class CommonUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 判断Object是否为null
     */
    public static boolean isNull(Object oValue) {
        return oValue == null ? true : false;
    }


    /**
     * 判断对象内容是否为空
     * 对象等于null 为空
     * 对象类型支持 数组、字符串、
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else if (obj instanceof String) {
            return ((String) obj).trim().length() == 0;
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        } else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        } else if (obj instanceof Set) {
            return ((Set) obj).isEmpty();
        } else {
            return false;
        }
    }

    /**
     * 判断是否不为空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }


    /**
     * 判断两个String是否相等,如果是字符,比较是否相同,如果是数字,比较是否相等
     *
     * @param aStr String
     * @param bStr String
     * @return boolean
     */
    public static boolean equalsNumberOrChar(String aStr, String bStr) {
        boolean isSame = false;
        aStr = StringUtil.toString(aStr);
        bStr = StringUtil.toString(bStr);
        if ((isEmpty(aStr)) && isEmpty(bStr)) {
            isSame = true;
        } else if ((isEmpty(aStr)) || (isEmpty(bStr))) {
            isSame = false;
        } else if (aStr.equals(bStr)) {
            isSame = true;
        } else if (isNumber(aStr) && isNumber(bStr)) {
            java.math.BigDecimal aNum = new BigDecimal(aStr);
            java.math.BigDecimal bNum = new BigDecimal(bStr);

            if (aNum.compareTo(bNum) == 0) {
                isSame = true;
            }
        }
        return isSame;
    }

    /**
     * 判断两个数字是否相等,可以是Long、long、Float、float、int、Integer、double、Double、Byte、byte
     *
     * @param aNum
     * @param bNum
     * @return boolean
     */
    public static boolean equalsNumber(Number aNum, Number bNum) {
        if (aNum == bNum) {
            return true;
        } else if (aNum == null || bNum == null) {
            return false;
        } else {
            return aNum.equals(bNum);
        }
    }

    /**
     * 判断是否为数字
     */
    public static boolean isNumber(String sValue) {
        if (isEmpty(sValue))
            return false;
        try {
            Double.parseDouble(sValue);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 字段名称转换成驼峰命名
     */
    public static String columnToProperty(String column) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (column == null || column.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!column.contains("_")) {
            // 都是大写字母的时候才转换成驼峰命名法，如果是大小写混写并且没有“_”已经是驼峰命名法不再转换
            if (StringUtil.equalsString(column.toUpperCase(), column)) {
                return column.toLowerCase();
            } else {
                return column;
            }
        } else {
            // 用下划线将原始字符串分割
            String[] columns = column.split("_");
            for (String columnSplit : columns) {
                // 跳过原始字符串中开头、结尾的下换线或双重下划线
                if (columnSplit.isEmpty()) {
                    continue;
                }
                // 处理真正的驼峰片段
                if (result.length() == 0) {
                    // 第一个驼峰片段，全部字母都小写
                    result.append(columnSplit.toLowerCase());
                } else {
                    // 其他的驼峰片段，首字母大写
                    result.append(columnSplit.substring(0, 1).toUpperCase()).append(columnSplit.substring(1).toLowerCase());
                }
            }
            return result.toString();
        }

    }

    /**
     * 驼峰Bean属性转换成列名称
     */
    public static String propertyToColumn(String property) {
        if (isEmpty(property)) {
            return "".toUpperCase();
        }
        char[] propertyArray = property.toCharArray();
        StringBuffer rs = new StringBuffer();
        for (char c : propertyArray) {
            if (c >= 'A' && c <= 'Z') {
//                Character.toUpperCase()
                rs.append('_');
                rs.append(Character.toUpperCase(c));
            } else {
                rs.append(Character.toUpperCase(c));
            }
        }
        return rs.toString();
    }

    /**
     * 是否基本数据类型或者基本数据类型的包装类
     */
    public static boolean isPrimitiveOrPrimitiveWrapper(Class<?> objectClass) {
        return objectClass.isPrimitive() ||
                (objectClass.isAssignableFrom(Byte.class) || objectClass.isAssignableFrom(Short.class) ||
                        objectClass.isAssignableFrom(Integer.class) || objectClass.isAssignableFrom(Long.class) ||
                        objectClass.isAssignableFrom(Double.class) || objectClass.isAssignableFrom(Float.class) ||
                        objectClass.isAssignableFrom(Character.class) || objectClass.isAssignableFrom(Boolean.class)) ||
                objectClass.isAssignableFrom(String.class) || objectClass.isAssignableFrom(BigDecimal.class);
    }

    /**
     * 获取32位随机UUID
     * */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


}
