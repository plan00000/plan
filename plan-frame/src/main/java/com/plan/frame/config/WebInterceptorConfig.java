package com.plan.frame.config;

import com.plan.frame.interceptor.ThreadLocalCleanInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Created by linzhihua
 * @Description 系统拦截器配置
 * @ClassName WebInterceptorConfig
 * @Date 2020/6/23 18:18
 */
@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {
    /**
     * 添加线程清理拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
        registry.addInterceptor(new ThreadLocalCleanInterceptor()).addPathPatterns("/**");
    }

}
