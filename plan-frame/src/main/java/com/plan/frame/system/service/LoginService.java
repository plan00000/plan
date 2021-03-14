package com.plan.frame.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.plan.frame.cache.DictinaryCache;
import com.plan.frame.cache.RoleCache;
import com.plan.frame.entity.Result;
import com.plan.frame.exception.ConditionException;
import com.plan.frame.exception.SystemException;
import com.plan.frame.helper.ResultHelper;
import com.plan.frame.helper.ThreadLocalHelper;
import com.plan.frame.system.dto.login.userInfo.ReqUpdatePswDto;
import com.plan.frame.system.dto.login.userInfo.UserInfoDto;
import com.plan.frame.util.*;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.security.PrivateKey;
import java.util.*;


/**
 * @Author: linzhihua
 * @Description（类描述）: 用户登录service
 * @Date: Created in 2020/4/20 15:12
 * @Modified By:
 */
@Service
public class LoginService {

    protected Logger logger = Logger.getLogger(this.getClass());

    private static int PASSWORD_EXPIRED_DAYS = 90;
    //token过期时长，目前设置成一个小时
    @Value("${token_exp}")
    private String tokenExp ;
    /**
     * 是否启用cas单点登录
     * 启用不进行token验证
     * 不启用就进行token验证
     */

    @Value("${spring.menhuurl}")
    private String menHuUrl;

    @Value("${cas.casServerUrlPrefix}")
    private String casServerUrl;

    @Value("${rsa.privateModulus}")
    private String privateModulus;
    @Value("${rsa.privateExponent}")
    private String privateExponent;

    @Autowired
    private RedisUtils redisUtil;


    @Value("${service.permission.url}")
    private String permissionUrl;

    /**
     * 菜单
     */
    @Value("${system-top-menu-code}")
    private String sysMenuCode;

    @Value("${cas.casServerUrlPrefix2}")
    private String casServerUrlPrefix2;


    /**
     * 用户退出登录操作
     * @param response
     * @throws Exception
     */
    public void logout(HttpServletResponse response)throws Exception{
        //redis移除token和用户信息
//        String token = ThreadLocalHelper.getToken();
//        redisUtil.remove(DigestUtils.md5DigestAsHex(token.getBytes()));
        try{
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
        }catch (Exception e){
        }

        response.sendRedirect(casServerUrlPrefix2);
    }


    /**
     * 修改密码
     * @param req
     * @return
     */
    public Result<String> updatePsw(ReqUpdatePswDto req){
        UserInfoDto user = ThreadLocalHelper.getUser();
        String userName = user.getUserName();

        PrivateKey privateKey = RsaUtil.getRSAPrivateKey(privateModulus, privateExponent);
        String oldPassword = RsaUtil.decryptString(privateKey, req.getOldPwd().replace(' ', '+'));
        String password = RsaUtil.decryptString(privateKey, req.getNewPwd().replace(' ', '+'));

        Map<String, String> params=new HashMap<>();
        params.put("username", userName);
        params.put("oldPwd", oldPassword); // 密码为明文
        params.put("newPwd", password);

        Result<String> res = new Result<String>();
        String s = HttpUtil.post(permissionUrl + "/updatePwd", params);
        if(CommonUtil.isEmpty(s)){
            return ResultHelper.faile("修改密码失败");
        }
        JSONObject result = JSONObject.parseObject(s);
        if ("1".equals(result.getString("code"))) {
            return ResultHelper.success("修改密码成功");
        }else{
            return ResultHelper.faile("修改密码失败:" + result.getString("msg"));
        }
    }


}