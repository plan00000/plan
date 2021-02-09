package com.plan.frame.config;

import com.plan.frame.filter.JwtFilter;
import com.plan.frame.util.CommonUtil;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * @Author linzhihua
 * @Description: Shiro bean配置
 * @Date 2019-02-18
 * @version v1.0
 */
@Configuration
public class ShiroConfigBean {
    @Value("${shiro.ignore.url}")
    private String shiroIgnoreUrl;
    /**
     * 告诉shiro不要使用默认的DefaultSubject创建对象
     * @return
     */
    @Bean
    public SubjectFactory subjectFactory(){
        return new JwtDeafultSubjectFactory();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String,Filter> filterMap = new HashMap<>();
        filterMap.put("jwt",new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);


        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        filterChainDefinitionMap.put("/logout", "logout");
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");

        //从配置文件读取不验证访问url
        if(CommonUtil.isNotEmpty(shiroIgnoreUrl)){
            String[] ignoreUrls = shiroIgnoreUrl.split(",");
            List<String > ignoreUrlList = Arrays.asList(ignoreUrls);
            if(CommonUtil.isNotEmpty(ignoreUrlList)){
                for(String ignoreUrl:ignoreUrlList){
                    filterChainDefinitionMap.put(ignoreUrl,"anon");
                }
            }
        }
        //swagger
        filterChainDefinitionMap.put("/swagger-ui.html*","anon");
        filterChainDefinitionMap.put("/navigation/**","anon");
        filterChainDefinitionMap.put("/doc.html*","anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/language/getLanguagelist", "anon");
        filterChainDefinitionMap.put("/document/**", "anon");
        filterChainDefinitionMap.put("/homepage/**", "anon");
        filterChainDefinitionMap.put("/navcationlink/listnavlink", "anon");
        filterChainDefinitionMap.put("/navcationlink/listlinks", "anon");
        filterChainDefinitionMap.put("/language/getChinesslist", "anon");
        filterChainDefinitionMap.put("/language/getEnglishlist", "anon");

        filterChainDefinitionMap.put("/**", "jwt");
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/login/unAuth");
        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /*
	 * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	 * 所以我们需要修改下doGetAuthenticationInfo中的代码; )
	 */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("SHA-1");// 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1);// 散列的次数，比如散列两次，相当于md5(md5(""));
        return hashedCredentialsMatcher;
    }
    @Bean
    public MyJwtShiroRealm myShiroRealm() {
        MyJwtShiroRealm myJwtShiroRealm = new MyJwtShiroRealm();
//        myJwtShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myJwtShiroRealm;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        //启用或停用session开始 -jwtDefaultSubjectFactory类里的也要一并处理
        //注释掉代表-关闭shiroDao功能
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        //注释掉代表-关闭session功能
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        //启用或停用session结束
        securityManager.setSubjectFactory(subjectFactory());

        return securityManager;
    }


    /**
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
