package com.plan.frame.helper;

import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName GrenEntityMethodHelper
 * @Date 2020/3/30 11:32
 */
public class GrenEntityMethodHelper {

    public static void main(String[] args) {
//        GrenEntityMethodHelper.gernSeter(SysButton.class,"sysButton");
//        GrenEntityMethodHelper.gernGeter(SysButton.class,"sysButton");
    }

    /**
     * 生成set方法
     * @param entityClass 根据实际情况修改
     * @param variableName 根据实际情况修改
     */
    public static void gernSeter(Class entityClass,String variableName) {
        try {
            // 获取所有的字段
            Field[] fields = entityClass.getDeclaredFields();
            System.out.println("======" + entityClass.getName() + " set生成开始=================");
            for (Field f : fields) {
                // 判断字段注解是否存在
                boolean apiModelPropertyExist = f.isAnnotationPresent(ApiModelProperty.class);
                if (apiModelPropertyExist) {
                    ApiModelProperty apiModelProperty = f.getAnnotation(ApiModelProperty.class);
                    // 获取注解值
                    String apiModelPropertyValue = apiModelProperty.value();
                    String fieldName = "set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
                    System.out.println(variableName + "." + fieldName + "( );  //" + apiModelPropertyValue);
                }
            }
            System.out.println();
            System.out.println("======" + entityClass.getName() + " set生成结束=================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成get方法
     * @param entityClass 根据实际情况修改
     * @param variableName 根据实际情况修改
     */
    public static void gernGeter(Class entityClass,String variableName) {

        try {
            // 获取所有的字段
            Field[] fields = entityClass.getDeclaredFields();
            System.out.println("======" + entityClass.getName() + " get生成开始=================");
            for (Field f : fields) {
                // 判断字段注解是否存在
                boolean apiModelPropertyExist = f.isAnnotationPresent(ApiModelProperty.class);
                if (apiModelPropertyExist) {
                    ApiModelProperty apiModelProperty = f.getAnnotation(ApiModelProperty.class);
                    // 获取注解值
                    String apiModelPropertyValue = apiModelProperty.value();
                    String fieldName = "get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
                    System.out.println(variableName + "." + fieldName + "( );  //" + apiModelPropertyValue);
                }
            }
            System.out.println();
            System.out.println("======" + entityClass.getName() + " get生成结束=================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
