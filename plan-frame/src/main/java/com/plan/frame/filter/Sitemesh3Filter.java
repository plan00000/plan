package com.plan.frame.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.context.annotation.Configuration;

import javax.servlet.annotation.WebFilter;

/**
 * Created by Administrator on 2021/2/25 0025.
 */
@Configuration
@WebFilter(filterName="Sitemesh3Filter",urlPatterns="/*")
public class Sitemesh3Filter extends ConfigurableSiteMeshFilter {
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/*", "/layout/default_admin_main")
                .addExcludedPath("/static/**");//白名单,静态资源
//                .addTagRuleBundle(new CustomTagRuleBundle());
    }
}
