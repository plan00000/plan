package com.plan.frame.helper;

import com.plan.frame.entity.ValueObject;
import com.plan.frame.util.CommonUtil;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

/**
 * @Author Huangry
 * @Description: 权限计算
 * @Date 2019-02-18
 */
public class RightsHelper {
    /**
     * 利用BigInteger对权限进行2的权的和计算
     * @param rights int型权限编码数组
     * @return 2的权的和
     */
    public static BigInteger sumRights(int[] rights){
        BigInteger num = new BigInteger("0");
        for(int i=0; i<rights.length; i++){
            num = num.setBit(rights[i]);
        }
        return num;
    }
    /**
     * 利用BigInteger对权限进行2的权的和计算
     * @param rights String型权限编码数组
     * @return 2的权的和
     */
    public static BigInteger sumRights(String[] rights){
        BigInteger num = new BigInteger("0");
        for(int i=0; i<rights.length; i++){
            num = num.setBit(Integer.parseInt(rights[i]));
        }
        return num;
    }

    /**
     * 测试是否具有指定编码的权限
     * @param sum
     * @param targetRights
     * @return
     */
    public static boolean testRights(BigInteger sum,int targetRights){
        return sum.testBit(targetRights);
    }

    /**
     * 测试是否具有指定编码的权限
     * @param sum
     * @param targetRights
     * @return
     */
    public static boolean testRights(String sum,int targetRights){
        if(CommonUtil.isEmpty(sum)) {
            return false;
        }
        return testRights(new BigInteger(sum),targetRights);
    }

    /**
     * 测试是否具有指定编码的权限
     * @param sum
     * @param targetRights
     * @return
     */
    public static boolean testRights(String sum,String targetRights){
        if(CommonUtil.isEmpty(sum)) {
            return false;
        }
        return testRights(new BigInteger(sum),targetRights);
    }

    /**
     * 测试是否具有指定编码的权限
     * @param sum
     * @param targetRights
     * @return
     */
    public static boolean testRights(BigInteger sum,String targetRights){
        return testRights(sum,Integer.parseInt(targetRights));
    }

    /**
     * 菜单权限解析
     * @param allMenuList
     * @param roleRights
     * @return
     */
    public static List<ValueObject> readMenu(List<ValueObject> allMenuList, String roleRights){
        Iterator<ValueObject> menuKey = allMenuList.iterator();
        while (menuKey.hasNext()){
            ValueObject pd = menuKey.next();
            if(RightsHelper.testRights(roleRights, pd.get("menuId").toString())){
                Object subMenu = pd.get("submenu");
                if(null != subMenu){
                    RightsHelper.readMenu((List<ValueObject>)pd.get("submenu"), roleRights);
                }
            } else {
                menuKey.remove();
            }
        }
        return allMenuList;
    }
}
