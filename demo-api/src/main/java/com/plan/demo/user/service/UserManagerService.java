package com.plan.demo.user.service;

import com.plan.demo.user.dto.ReqMobileCodeDto;
import com.plan.demo.user.dto.ResTokenDto;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.DateUtil;
import com.plan.frame.util.JwtUtil;
import com.plan.frame.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator 2021/3/14 0014
 * @version V1.0.0
 * @description 用户管理service
 */
@Service
public class UserManagerService {


    //token过期时长，目前设置成一个小时
    @Value("${token_exp}")
    private String tokenExp ;
    @Autowired
    private RedisUtils redisUtil;

    /**
     * 用户登录-生成token
     * @param reqMobileCodeDto
     * @return
     * @throws Exception
     */
    public ResTokenDto getToken(ReqMobileCodeDto reqMobileCodeDto)throws Exception{
        ResTokenDto resTokenDto = new ResTokenDto();

        //生成token
        //token过期时间
        if(CommonUtil.isEmpty(tokenExp)){
            tokenExp = "60";
        }
        //由于修改配置过于麻烦所有临时修改此处1.8*60 约等于 2*50
        Long tokenExpTime = 2*50*1000*Long.parseLong(tokenExp);
        Long tokenDate = System.currentTimeMillis()+tokenExpTime;
        String token = JwtUtil.getToken(reqMobileCodeDto.getMobileno(),tokenDate);
        resTokenDto.setToken(token);
        resTokenDto.setTokenDate(DateUtil.date2Str(new Date(tokenDate),"yyyy-MM-dd HH:mm:ss"));
        //对token进行加密
        String key = DigestUtils.md5DigestAsHex(token.getBytes());
        //放入缓存
        redisUtil.set(key,"",3L, TimeUnit.HOURS);

        return resTokenDto;
    }
}
