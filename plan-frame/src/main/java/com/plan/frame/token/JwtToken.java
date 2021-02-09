package com.plan.frame.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName JwtToken
 * @Date 2020/6/18 10:55
 */
public class JwtToken implements AuthenticationToken{

    private String jwt;
    public JwtToken(String jwt){
        this.jwt = jwt;
    }
    @Override
    public Object getPrincipal() {
        return jwt;
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }
}
