package com.plan.frame.filter;

import com.plan.frame.entity.Result;
import com.plan.frame.helper.ResultHelper;
import com.plan.frame.helper.ThreadLocalHelper;
import com.plan.frame.token.JwtToken;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Created by linzhihua
 * @Description 自定义一个Filter，用来拦截所有的请求判断是否携带Token
 * isAccessAllowed()判断是否携带了有效的JwtToken
 * onAccessDenied()是没有携带JwtToken的时候进行账号密码登录，登录成功允许访问，登录失败拒绝访问
 * @ClassName JwtFilter
 * @Date 2020/6/18 10:45
 */
public class JwtFilter extends AccessControlFilter{
    private Logger logger = Logger.getLogger(this.getClass());


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
//        logger.warn("isAccessAllowed方法被调用");
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//        logger.warn("onAccessDenied 方法被调用");
        //这个地方和前端约定，要求前端将jwtToken放在请求的Header部分
        //所以以后发起请求的时候就需要在Header中放一个Authorization，值就是对应的Token
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = httpServletRequest.getHeader("token");
        logger.info("请求的 Header 中藏有 jwtToken {}"+jwt);

        JwtToken jwtToken = new JwtToken(jwt);

        /*
         * 下面就是固定写法
         * */
        try {
            // 委托 realm 进行登录认证
            //所以这个地方最终还是调用JwtRealm进行的认证
            //也就是subject.login(token)
            getSubject(request, response).login(jwtToken);
            //认证成功后，把token放入线程变量里
            ThreadLocalHelper.setToken(jwt);

        } catch (DisabledAccountException e) {
            logger.error(e.getMessage());
            onLoginFail(response,e.getMessage());
            //调用下面的方法向客户端返回错误信息
            return false;
        }

        return true;
        //执行方法中没有抛出异常就表示登录成功
    }

    //登录失败时默认返回 401 状态码
    private void onLoginFail(ServletResponse response,String errorMsg) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Result result = ResultHelper.error(errorMsg);
        httpResponse.getWriter().write(result.toString());
    }
}
