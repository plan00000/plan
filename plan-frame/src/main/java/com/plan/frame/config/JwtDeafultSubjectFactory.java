package com.plan.frame.config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * @Created by linzhihua
 * @Description  已实现无状态的web，使用不到shiro的session功和http request的sessioin的功能，关闭session，调用getSession()会抛出异常
 * 当用启用或停用session时，ShiroConfigBean类SecurityManager 也要一并处理
 * @ClassName JwtDeafultSubjectFactory
 * @Date 2020/6/18 10:38
 */
public class JwtDeafultSubjectFactory extends DefaultWebSubjectFactory{
    @Override
    public Subject createSubject(SubjectContext context) {
        //注释掉代表-不创建session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
