package com.plan.frame.config;
/**
 * @Author Huangry
 * @Description: 单点登录配置信息
 * @Date 2019-02-18
 */
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

//@ConfigurationProperties(prefix = "cas")
//@EnableAutoConfiguration
public class CasAutoconfig {
    private String casServerLoginUrl;
    private String serverName;

    /**
     * 不用登录即可直接访问的URL，多个URL用,隔开
     */
    private String casPassUrl;
    private String ignorePattern;

    private boolean renew = false;
    private boolean gateway = false;

    /**
     * ticket校验filter参数
     */
    private String casServerUrlPrefix;
    private boolean useSession = true;
    private boolean redirectAfterValidation = true;
    private boolean exceptionOnValidationFailure = false;

    /**
     * 默认登录时，跳回的URL统一为该URL
     */
    private String serviceUrl;

    /**
     * 静态资源过滤
     */
    private String resource;

    public String getCasPassUrl() {
        return casPassUrl;
    }
    public void setCasPassUrl(String casPassUrl) {
        this.casPassUrl = casPassUrl;
    }

    public String getIgnorePattern() {
        return ignorePattern;
    }
    public void setIgnorePattern(String ignorePattern) {
        this.ignorePattern = ignorePattern;
    }

    public String getCasServerLoginUrl() {
        return casServerLoginUrl;
    }
    public void setCasServerLoginUrl(String casServerLoginUrl) {
        this.casServerLoginUrl = casServerLoginUrl;
    }

    public String getServerName() {
        return serverName;
    }
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getResource() {
        return resource;
    }
    public void setResource(String resource) {
        this.resource = resource;
    }

    public boolean getRenew() {
        return renew;
    }
    public void setRenew(boolean renew) {
        this.renew = renew;
    }

    public boolean getGateway() {
        return gateway;
    }
    public void setGateway(boolean gateway) {
        this.gateway = gateway;
    }

    public String getCasServerUrlPrefix() {
        return casServerUrlPrefix;
    }
    public void setCasServerUrlPrefix(String casServerUrlPrefix) {
        this.casServerUrlPrefix = casServerUrlPrefix;
    }

    public boolean getUseSession() {
        return useSession;
    }
    public void setUseSession(boolean useSession) {
        this.useSession = useSession;
    }

    public boolean getRedirectAfterValidation() {
        return redirectAfterValidation;
    }
    public void setRedirectAfterValidation(boolean redirectAfterValidation) {
        this.redirectAfterValidation = redirectAfterValidation;
    }

    public boolean getExceptionOnValidationFailure() {
        return exceptionOnValidationFailure;
    }
    public void setExceptionOnValidationFailure(boolean exceptionOnValidationFailure) {
        this.exceptionOnValidationFailure = exceptionOnValidationFailure;
    }
}