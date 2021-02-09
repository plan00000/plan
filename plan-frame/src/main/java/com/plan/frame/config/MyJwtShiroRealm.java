package com.plan.frame.config;


import com.plan.frame.token.JwtToken;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;


import javax.annotation.Resource;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName MyShiroRealm
 * @Date 2020/4/20 10:06
 */

public class MyJwtShiroRealm extends AuthorizingRealm {

    /**
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    /**
     * 认证
     *
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

        String jwt = (String) authcToken.getPrincipal();
        if (CommonUtil.isEmpty(jwt)) {
            throw new DisabledAccountException("jwtToken 不允许为空");
        }
        //校验token
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.checkToken(jwt);
        //判断一下单点登录是否有进行登出操作：修改与20201010，修改原因
        /*Assertion assertion = AssertionHolder.getAssertion();
        if(CommonUtil.isEmpty(assertion)){
            throw new DisabledAccountException("请重新登录");
        }*/

        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                jwt, // 用户名
                jwt, // 密码
                ByteSource.Util.bytes(jwt),//
                this.getName() // realm name
        );

        return authenticationInfo;

    }
}
