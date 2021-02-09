package com.plan.frame.util;


import com.plan.frame.exception.SystemException;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * 类型转换辅助工具类<br>
 *
 * @author linzhihua
 * @since 2019-07-06
 */
public class TypeCaseUtil {

    /**
     * 转换核心实现方法
     *
     * @param obj
     * @param type
     * @param format
     * @return Object
     * @throws SystemException
     */
    public static Object convert(Object obj, String type, String format) throws SystemException {
        Locale locale = new Locale("zh", "CN", "");
        if (obj == null)
            return null;
        if (obj.getClass().getName().equals(type))
            return obj;
        if ("Object".equals(type) || "java.lang.Object".equals(type))
            return obj;
        String fromType = null;
        if (obj instanceof String) {
            fromType = "String";
            String str = (String) obj;
            if ("String".equals(type) || "java.lang.String".equals(type))
                return obj;
            if (str.length() == 0)
                return null;
            if ("Boolean".equals(type) || "java.lang.Boolean".equals(type)) {
                Boolean value = null;
                if (str.equalsIgnoreCase("TRUE"))
                    value = Boolean.TRUE;
                else
                    value = Boolean.FALSE;
                return value;
            }
            if ("Double".equals(type) || "java.lang.Double".equals(type))
                try {
                    Number tempNum = getNf(locale).parse(str);
                    return new Double(tempNum.doubleValue());
                } catch (ParseException e) {
                    throw new SystemException("Could not convert " + str + " to " + type + ": ",
                            e,"请联系管理员处理");
                }
            if ("BigDecimal".equals(type) || "java.math.BigDecimal".equals(type))
                try {
                    BigDecimal retBig = new BigDecimal(str);
                    int iscale = str.indexOf(".");
                    int keylen = str.length();
                    if (iscale > -1) {
                        iscale = keylen - (iscale + 1);
                        return retBig.setScale(iscale, 5);
                    } else {
                        return retBig.setScale(0, 5);
                    }
                } catch (Exception e) {
                    throw new SystemException("Could not convert " + str + " to " + type + ": ",
                            e,"请联系管理员处理");
                }
            if ("Float".equals(type) || "java.lang.Float".equals(type))
                try {
                    Number tempNum = getNf(locale).parse(str);
                    return new Float(tempNum.floatValue());
                } catch (ParseException e) {
                    throw new SystemException("Could not convert " + str + " to " + type + ": ",
                            e,"请联系管理员处理");
                }
            if ("Long".equals(type) || "java.lang.Long".equals(type))
                try {
                    NumberFormat nf = getNf(locale);
                    nf.setMaximumFractionDigits(0);
                    Number tempNum = nf.parse(str);
                    Long tempLong = tempNum.longValue();
                    return tempLong;
                } catch (ParseException e) {
                    throw new SystemException("Could not convert " + str + " to " + type + ": ",
                            e,"请联系管理员处理");
                }
            if ("Integer".equals(type) || "java.lang.Integer".equals(type))
                try {
                    NumberFormat nf = getNf(locale);
                    nf.setMaximumFractionDigits(0);
                    Number tempNum = nf.parse(str);
                    Integer tempInt = tempNum.intValue();
                    return tempInt;
                } catch (ParseException e) {
                    throw new SystemException("Could not convert " + str + " to " + type + ": ",
                            e,"请联系管理员处理");
                }
            if ("Short".equals(type) || "java.lang.Short".equals(type))
                try {
                    NumberFormat nf = getNf(locale);
                    nf.setMaximumFractionDigits(0);
                    Number tempNum = nf.parse(str);
                    return tempNum.shortValue();
                } catch (ParseException e) {
                    throw new SystemException("Could not convert " + str + " to " + type + ": ",
                            e,"请联系管理员处理");
                }
            if ("Date".equals(type) || "java.sql.Date".equals(type)) {
                if (format == null || format.length() == 0)
                    try {
                        return Date.valueOf(str);
                    } catch (Exception e) {
                        try {
                            DateFormat df = null;
                            if (locale != null)
                                df = DateFormat.getDateInstance(3, locale);
                            else
                                df = DateFormat.getDateInstance(3);
                            java.util.Date fieldDate = df.parse(str);
                            return new Date(fieldDate.getTime());
                        } catch (ParseException e1) {
                            throw new SystemException("Could not convert " + str + " to " + type
                                    + ": ", e,"请联系管理员处理");
                        }
                    }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    java.util.Date fieldDate = sdf.parse(str);
                    return new Date(fieldDate.getTime());
                } catch (ParseException e) {
                    throw new SystemException("Could not convert " + str + " to " + type + ": ",
                            e,"请联系管理员处理");
                }
            }
            if ("Timestamp".equals(type) || "java.sql.Timestamp".equals(type)) {
                if (str.length() == 10)
                    str = str + " 00:00:00";
                if (format == null || format.length() == 0)
                    try {
                        return Timestamp.valueOf(str);
                    } catch (Exception e) {
                        try {
                            DateFormat df = null;
                            if (locale != null)
                                df = DateFormat.getDateTimeInstance(3, 3, locale);
                            else
                                df = DateFormat.getDateTimeInstance(3, 3);
                            java.util.Date fieldDate = df.parse(str);
                            return new Timestamp(fieldDate.getTime());
                        } catch (ParseException e1) {
                            throw new SystemException("Could not convert " + str + " to " + type
                                    + ": ", e,"请联系管理员处理");
                        }
                    }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    java.util.Date fieldDate = sdf.parse(str);
                    return new Timestamp(fieldDate.getTime());
                } catch (ParseException e) {
                    throw new SystemException("Could not convert " + str + " to " + type + ": ",
                            e,"请联系管理员处理");
                }
            } else {
                throw new SystemException("没有匹配到对应的对象类型","Conversion from " + fromType + " to " + type
                        + " not currently supported","请联系管理员处理");
            }
        }
        if (obj instanceof BigDecimal) {
            fromType = "BigDecimal";
            BigDecimal bigD = (BigDecimal) obj;
            if ("String".equals(type))
                return getNf(locale).format(bigD.doubleValue());
            if ("BigDecimal".equals(type) || "java.math.BigDecimal".equals(type))
                return obj;
            if ("Double".equals(type))
                return new Double(bigD.doubleValue());
            if ("Float".equals(type))
                return new Float(bigD.floatValue());
            if ("Long".equals(type))
                return Long.valueOf(Math.round(bigD.doubleValue()));
            if ("Integer".equals(type))
                return  Integer.valueOf((int) Math.round(bigD.doubleValue()));
            if ("Short".equals(type))
                return  Short.valueOf((short) Math.round(bigD.doubleValue()));
            else
                throw new SystemException("没有匹配到对应的对象类型","Conversion from " + fromType + " to " + type
                        + " not currently supported","请联系管理员处理");
        }
        if (obj instanceof Double) {
            fromType = "Double";
            Double dbl = (Double) obj;
            if ("String".equals(type) || "java.lang.String".equals(type))
                return getNf(locale).format(dbl.doubleValue());
            if ("Double".equals(type) || "java.lang.Double".equals(type))
                return obj;
            if ("Float".equals(type) || "java.lang.Float".equals(type))
                return new Float(dbl.floatValue());
            if ("Long".equals(type) || "java.lang.Long".equals(type))
                return new Long(Math.round(dbl.doubleValue()));
            if ("Integer".equals(type) || "java.lang.Integer".equals(type))
                return new Integer((int) Math.round(dbl.doubleValue()));
            if ("BigDecimal".equals(type) || "java.math.BigDecimal".equals(type))
                return new BigDecimal(dbl.toString());
            else
                throw new SystemException("没有匹配到对应的对象类型","Conversion from " + fromType + " to " + type
                        + " not currently supported","请联系管理员处理");
        }
        if (obj instanceof Float) {
            fromType = "Float";
            Float flt = (Float) obj;
            if ("String".equals(type))
                return getNf(locale).format(flt.doubleValue());
            if ("BigDecimal".equals(type) || "java.math.BigDecimal".equals(type))
                return new BigDecimal(flt.doubleValue());
            if ("Double".equals(type))
                return new Double(flt.doubleValue());
            if ("Float".equals(type))
                return obj;
            if ("Long".equals(type))
                return new Long(Math.round(flt.doubleValue()));
            if ("Integer".equals(type))
                return new Integer((int) Math.round(flt.doubleValue()));
            else
                throw new SystemException("没有匹配到对应的对象类型","Conversion from " + fromType + " to " + type
                        + " not currently supported","请联系管理员处理");
        }
        if (obj instanceof Long) {
            fromType = "Long";
            Long lng = (Long) obj;
            if ("String".equals(type) || "java.lang.String".equals(type))
                return getNf(locale).format(lng.longValue());
            if ("Double".equals(type) || "java.lang.Double".equals(type))
                return new Double(lng.doubleValue());
            if ("Float".equals(type) || "java.lang.Float".equals(type))
                return new Float(lng.floatValue());
            if ("BigDecimal".equals(type) || "java.math.BigDecimal".equals(type))
                return new BigDecimal(lng.toString());
            if ("Long".equals(type) || "java.lang.Long".equals(type))
                return obj;
            if ("Integer".equals(type) || "java.lang.Integer".equals(type))
                return new Integer(lng.intValue());
            if ("Date".equals(type) || "java.sql.Date".equals(type))
                return new Date(lng.longValue());
            else
                throw new SystemException("没有匹配到对应的对象类型","Conversion from " + fromType + " to " + type
                        + " not currently supported","请联系管理员处理");
        }
        if (obj instanceof Integer) {
            fromType = "Integer";
            Integer intgr = (Integer) obj;
            if ("String".equals(type) || "java.lang.String".equals(type))
                return getNf(locale).format(intgr.longValue());
            if ("Double".equals(type) || "java.lang.Double".equals(type))
                return new Double(intgr.doubleValue());
            if ("Float".equals(type) || "java.lang.Float".equals(type))
                return new Float(intgr.floatValue());
            if ("BigDecimal".equals(type) || "java.math.BigDecimal".equals(type)) {
                String str = intgr.toString();
                BigDecimal retBig = new BigDecimal(intgr.doubleValue());
                int iscale = str.indexOf(".");
                int keylen = str.length();
                if (iscale > -1) {
                    iscale = keylen - (iscale + 1);
                    return retBig.setScale(iscale, 5);
                } else {
                    return retBig.setScale(0, 5);
                }
            }
            if ("Long".equals(type) || "java.lang.Long".equals(type))
                return new Long(intgr.longValue());
            if ("Integer".equals(type) || "java.lang.Integer".equals(type))
                return obj;
            else
                throw new SystemException("没有匹配到对应的对象类型","Conversion from " + fromType + " to " + type
                        + " not currently supported","请联系管理员处理");
        }
        if (obj instanceof Date) {
            fromType = "Date";
            Date dte = (Date) obj;
            if ("String".equals(type) || "java.lang.String".equals(type))
                if (format == null || format.length() == 0) {
                    return dte.toString();
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    return sdf.format(new java.util.Date(dte.getTime()));
                }
            if ("Date".equals(type) || "java.sql.Date".equals(type))
                return obj;
            if ("Time".equals(type) || "java.sql.Time".equals(type))
                throw new SystemException("类型不支持","Conversion from " + fromType + " to " + type
                        + " not currently supported","请联系管理员处理");
            if ("Timestamp".equals(type) || "java.sql.Timestamp".equals(type))
                return new Timestamp(dte.getTime());
            else
                throw new SystemException("类型不支持","Conversion from " + fromType + " to " + type
                        + " not currently supported","请联系管理员处理");
        }
        if (obj instanceof Timestamp) {
            fromType = "Timestamp";
            Timestamp tme = (Timestamp) obj;
            if ("String".equals(type) || "java.lang.String".equals(type))
                if (format == null || format.length() == 0) {
                    return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(tme).toString();
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    return sdf.format(new java.util.Date(tme.getTime()));
                }
            if ("Date".equals(type) || "java.sql.Date".equals(type))
                return new java.util.Date(tme.getTime());
            if ("Time".equals(type) || "java.sql.Time".equals(type))
                return new Time(tme.getTime());
            if ("Timestamp".equals(type) || "java.sql.Timestamp".equals(type))
                return obj;
            else
                throw new SystemException("没有匹配到对应的对象类型","Conversion from " + fromType + " to " + type
                        + " not currently supported","请联系管理员处理");
        }
        if (obj instanceof Boolean) {
            fromType = "Boolean";
            Boolean bol = (Boolean) obj;
            if ("Boolean".equals(type) || "java.lang.Boolean".equals(type))
                return bol;
            if ("String".equals(type) || "java.lang.String".equals(type))
                return bol.toString();
            if ("Integer".equals(type) || "java.lang.Integer".equals(type)) {
                if (bol.booleanValue())
                    return new Integer(1);
                else
                    return new Integer(0);
            } else {
                throw new SystemException("没有匹配到对应的对象类型","Conversion from " + fromType + " to " + type
                        + " not currently supported","请联系管理员处理");
            }
        }
        if(obj instanceof java.util.Date){
            return obj;
        }
        if ("String".equals(type) || "java.lang.String".equals(type))
            return obj.toString();
        else
            throw new SystemException("没有匹配到对应的对象类型","Conversion from " + obj.getClass().getName() + " to "
                    + type + " not currently supported","请联系管理员处理");
    }

    private static NumberFormat getNf(Locale locale) {
        NumberFormat nf = null;
        if (locale == null)
            nf = NumberFormat.getNumberInstance();
        else
            nf = NumberFormat.getNumberInstance(locale);
        nf.setGroupingUsed(false);
        return nf;
    }

    public static Boolean convert2SBoolean(Object obj) throws SystemException {
        return (Boolean) convert(obj, "Boolean", null);
    }

    public static Integer convert2Integer(Object obj) throws SystemException {
        return (Integer) convert(obj, "Integer", null);
    }

    public static String convert2String(Object obj) throws SystemException {
        return (String) convert(obj, "String", null);
    }

    public static String convert2String(Object obj, String defaultValue) throws SystemException {
        Object s = convert(obj, "String", null);
        if (s != null)
            return (String) s;
        else
            return "";
    }

    public static Long convert2Long(Object obj) throws SystemException {
        return (Long) convert(obj, "Long", null);
    }

    public static Double convert2Double(Object obj) throws SystemException {
        return (Double) convert(obj, "Double", null);
    }

    public static BigDecimal convert2BigDecimal(Object obj, int scale) throws SystemException {
        return ((BigDecimal) convert(obj, "BigDecimal", null)).setScale(scale, 5);
    }

    public static Date convert2SqlDate(Object obj, String format) throws SystemException {
        return (Date) convert(obj, "Date", format);
    }

    public static Timestamp convert2Timestamp(Object obj, String format) throws SystemException {
        return (Timestamp) convert(obj, "Timestamp", format);
    }
}
