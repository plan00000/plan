package com.plan.frame.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName CaptchaUsernamePasswordToken
 * @Date 2020/4/20 10:44
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
    /**
     *
     */
    private static final long serialVersionUID = 1012514998904009525L;
    private String captcha; // 验证码
    public CaptchaUsernamePasswordToken(String username, String password, Boolean rememberMe, String captcha) {
        super(username, password, rememberMe);
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public CaptchaUsernamePasswordToken() {
        super();
    }
}
