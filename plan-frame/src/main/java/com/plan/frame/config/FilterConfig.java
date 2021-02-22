package com.plan.frame.config;

import com.plan.frame.filter.RequestLogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName FilterConfig
 * @Date 2020/7/24 10:01
 */
//@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean registrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new RequestLogFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("reqeustLogFilter");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
