package com.plan.frame.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @Created by linzhihua
 * @Description 工具类-字符串操作帮助类
 * @ClassName StringUtil
 * @Date 2020/5/11 8:42
 */
public class StringUtil {

    /**
     * 判断两个String是否相等,如果是字符,比较是否相同,如果是数字,比较是否相等
     *
     * @param aStr String
     * @param bStr String
     * @return boolean
     */
    public static boolean equalsString(String aStr, String bStr) {
        boolean isSame = false;
        aStr = toString(aStr);
        bStr = toString(bStr);
        if ((CommonUtil.isEmpty(aStr)) && CommonUtil.isEmpty(bStr)) {
            isSame = true;
        } else if ((CommonUtil.isEmpty(aStr)) || (CommonUtil.isEmpty(bStr))) {
            isSame = false;
        } else if (aStr.equals(bStr)) {
            isSame = true;
        }
        return isSame;
    }

    /**
     * Convert string to string after trim
     */
    public static String toString(String sValue) {
        return CommonUtil.isNull(sValue) ? "" : sValue.trim();
    }

    /**
     * 用默认的分隔符(,)将字符串转换为字符串数组
     * @param str	字符串
     * @return
     */
    public static String[] str2StrArray(String str){
        return str2StrArray(str,",\\s*");
    }

    /**
     * 字符串转换为字符串数组
     * @param str 字符串
     * @param splitRegex 分隔符
     * @return
     */
    public static String[] str2StrArray(String str,String splitRegex){
        if(CommonUtil.isEmpty(str)){
            return null;
        }
        return str.split(splitRegex);
    }

    /**
     * 将输入流转换字符串
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static String readInputStreamToString(InputStream is, String charsetName)
            throws IOException {
        InputStreamReader isr = new InputStreamReader(is, charsetName);
        return readerToString(isr);
    }

    /**
     * 将输入流转换字符串
     *
     * @param isr
     * @return
     * @throws IOException
     */
    public static String readerToString(Reader isr)
            throws IOException {
        StringBuffer strBF = new StringBuffer();
        char[] readbf = new char[8192];
        int readSize = 0;
        try {
            while ((readSize = isr.read(readbf)) > 0) {
                strBF.append(readbf, 0, readSize);
            }
        } finally {
            if (isr != null) {
                isr.close();
            }
        }
        return strBF.toString();
    }

    /**
     * NULL字符串转换，参数对象为null时，返回空字符串
     *
     * @param o 转换原对象
     * @return 字符串
     */
    public static String nvl(Object o) {
        if (o == null) {
            return "";
        }
        return o.toString().trim();
    }

}
