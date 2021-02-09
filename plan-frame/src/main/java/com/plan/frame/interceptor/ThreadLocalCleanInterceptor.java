package com.plan.frame.interceptor;

import com.plan.frame.helper.ThreadLocalHelper;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Created by linzhihua
 * @Description线程变量清除拦截器
 * 注意：该拦截器必须从根目录开始拦截/**
 * @ClassName ThreadLocalCleanInterceptor
 * @Date 2020/6/23 16:05
 */
public class ThreadLocalCleanInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalHelper.removeAll();
    }
}
