package com.plan.frame.helper;

import com.plan.frame.exception.SystemException;
import com.plan.frame.system.dto.login.userInfo.UserInfoDto;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.RedisUtils;
import com.plan.frame.util.SpringContextHolder;
import org.springframework.util.DigestUtils;

/**
 * @Created by linzhihua
 * @Description: 线程变量管理帮助类
 *
 * @ClassName ThreadLocalHelper
 * @Date 2020/6/23 16:59
 */
public class ThreadLocalHelper {
    private static ThreadLocal<String> tokenThreadLocal = new ThreadLocal<String>();
    private static ThreadLocal<UserInfoDto>  userThreadLocal=new ThreadLocal<UserInfoDto>();
    private static ThreadLocal<String> sysLogIdThreadLocal = new ThreadLocal<String>();



    /**
     *获取token信息
     * @return
     */
    public static String getToken(){
        String token = tokenThreadLocal.get();
        return token;
    }
    public static void setToken(String token){
        if(CommonUtil.isEmpty(token)){
            throw  new SystemException("token信息为空","token信息不能为空","请联系管理员");
        }
        tokenThreadLocal.set(token);
    }
    /**
     * 获取用户对象
     * @return user
     */
    public static UserInfoDto getUser(){
        RedisUtils redisUtils = SpringContextHolder.getBean("redisUtils");
        UserInfoDto user=null;
        if(CommonUtil.isNotEmpty(tokenThreadLocal.get())) {
            user = (UserInfoDto) redisUtils.get(DigestUtils.md5DigestAsHex(tokenThreadLocal.get().getBytes()));
        }
        return user;
    }

    /**
     * 设置用户对象
     * 将会话中的用户对象设置到线程变量中
     * @param user
     */
    public static void setUser(UserInfoDto user){
        if(user==null){
            throw  new SystemException("用户对象为空","用户不能为空","请联系管理员");
        }
        userThreadLocal.set(user);
    }

    public static ThreadLocal<String> getSysLogId() {
        return sysLogIdThreadLocal;
    }

    public static void setSysLogId(String sysLogId) {
        if(CommonUtil.isEmpty(sysLogId)){
            throw  new SystemException("sysLogId为空","sysLogId不能为空","请联系管理员");
        }
        sysLogIdThreadLocal.set(sysLogId);
    }

    /**
     * 将用户从线程变量中移除
     * 业务交由完成后，必须保证该方便被调用
     * 该方法没有被调用会造成内存泄露或操作用户串会话
     * 调用代码获取到被移除的对象需要与放入的对象进行比较，
     * 如果对象不相等应该是程序控制移除需要打印出日志方便及时发现错误
     *  @return user
     */
    public static void removeAll(){
        tokenThreadLocal.remove();
        userThreadLocal.remove();
        sysLogIdThreadLocal.remove();
    }

}
