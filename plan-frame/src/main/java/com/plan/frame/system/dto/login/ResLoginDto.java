package com.plan.frame.system.dto.login;

import java.util.Date;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName ResLoginDto
 * @Date 2020/4/20 15:45
 */
public class ResLoginDto {

    private String redirectUrl;
    private String userName;
    private String token;
    private Date lastLogin;
    private String tokenDate;

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getTokenDate() {
        return tokenDate;
    }

    public void setTokenDate(String tokenDate) {
        this.tokenDate = tokenDate;
    }
}
