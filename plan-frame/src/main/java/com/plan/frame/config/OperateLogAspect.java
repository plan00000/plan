package com.plan.frame.config;

import com.plan.frame.helper.ThreadLocalHelper;
import com.plan.frame.system.base.entity.SysOperateLog;
import com.plan.frame.system.dto.login.userInfo.UserInfoDto;
import com.plan.frame.system.service.OperateLogService;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.IpAndAddrUtil;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 操作业务日志切面
 * @author xieyanling
 * @date 2020/7/23
 */
@Aspect
@Component
@Order(4)
public class OperateLogAspect {
    @Autowired(required = false)
    HttpServletRequest request;

    @Autowired
    private OperateLogService operateLogService;

    @Pointcut("execution(* com..*.*Controller.*(..))")
    public void operateLogPointCutCondition1() {
    }
    @Pointcut("@annotation(com.plan.frame.config.OperateLog)")
    public void operateLogPointCutCondition2() {
    }


    @Pointcut("operateLogPointCutCondition1() && operateLogPointCutCondition2()")
    public void operateLogPointCut() {
    }

    @Around("operateLogPointCut() && @annotation(apiOperation)")
    public Object  around(ProceedingJoinPoint point, ApiOperation apiOperation) throws Throwable {
        String userName = getCurrentUserName(request);
        if(CommonUtil.isNotEmpty(userName)){
            SysOperateLog log = new SysOperateLog();
            log.setLogId(CommonUtil.getUUID());
            log.setOperateTime(new Date());
            log.setOperateIp(IpAndAddrUtil.getIp(request));
            log.setOperator(userName);
            log.setContent(apiOperation.value());
            operateLogService.createSysOperateLog(log);
        }
        Object result = point.proceed();// 执行方法
        return result;
    }

    public String getCurrentUserName(HttpServletRequest request) {
        try {
            UserInfoDto user = ThreadLocalHelper.getUser();
            return user == null ? "" : user.getName();
        }catch (Exception e){
            return  null;
        }
    }
}
