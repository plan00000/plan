package com.plan.frame.util;

import io.swagger.models.auth.In;

import java.math.BigDecimal;

/**
 * 数字工具类
 * Created by xieyanling on 2020/7/15.
 */
public class DigitalUtil {

    /**
     * 保留N位小数
     * @param d
     * @param scale 小数位数
     * @return
     */
    public static double decimalFormat(double d, int scale){
        double f = new BigDecimal(d).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f;
    }

    /**
     * 计算同比
     * @return
     */
    public static Double calcYoy(Integer curNum, Integer lastNum, int scale){
        if(curNum != null && lastNum != null && lastNum != 0){
           Double yoy = decimalFormat((curNum - lastNum)* 100 / lastNum.doubleValue(), scale);
            return yoy;
        }
        return null;
    }

    public static void main(String[] args) {
        Double a = calcYoy(null,1,1);
        System.out.println(a);
    }
}
