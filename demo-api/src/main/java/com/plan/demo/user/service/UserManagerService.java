package com.plan.demo.user.service;

import com.plan.demo.base.dao.TbDriverDao;
import com.plan.demo.base.dao.TbPassengerDao;
import com.plan.demo.base.entity.TbDriver;
import com.plan.demo.base.entity.TbPassenger;
import com.plan.demo.user.dto.*;
import com.plan.frame.exception.SystemException;
import com.plan.frame.helper.BeanHelper;
import com.plan.frame.helper.ThreadLocalHelper;
import com.plan.frame.system.dto.login.userInfo.UserInfoDto;
import com.plan.frame.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
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

    @Autowired
    private TbPassengerDao tbPassengerDao;
    @Autowired
    private TbDriverDao tbDriverDao;

    /**
     * 用户登录-生成token
     * @param reqMobileCodeDto
     * @return
     * @throws Exception
     */
    public ResTokenDto getToken(ReqMobileCodeDto reqMobileCodeDto)throws Exception{
        ResTokenDto resTokenDto = new ResTokenDto();
        //判断是乘客还是司机
        UserInfoDto userInfoDto = new UserInfoDto();
        if(StringUtil.equalsString(reqMobileCodeDto.getUserType(),"0")) {
            TbPassenger tbPassengerQuery = new TbPassenger();
            tbPassengerQuery.setMobileno(reqMobileCodeDto.getMobileno());
            List<TbPassenger> tbPassengerList = tbPassengerDao.selectByEntitySelective(tbPassengerQuery);
            if (CommonUtil.isNotEmpty(tbPassengerList)) {
                TbPassenger tbPassenger = tbPassengerList.get(0);
                userInfoDto.setId(tbPassenger.getId());
                userInfoDto.setMobileno(tbPassenger.getMobileno());
                userInfoDto.setUserName(tbPassenger.getUserName());
                userInfoDto.setSex(tbPassenger.getSex());
                userInfoDto.setUserType("0");
            }
        }else{//乘客
            TbDriver tbDriverQuery = new TbDriver();
            tbDriverQuery.setMobileno(reqMobileCodeDto.getMobileno());
            List<TbDriver> tbDriverList = tbDriverDao.selectByEntitySelective(tbDriverQuery);
            if(CommonUtil.isNotEmpty(tbDriverList)){
                TbDriver tbDriver = tbDriverList.get(0);
                userInfoDto.setId(tbDriver.getId());
                userInfoDto.setMobileno(tbDriver.getMobileno());
                userInfoDto.setSex(tbDriver.getSex());
                userInfoDto.setUserName(tbDriver.getUserName());
                userInfoDto.setUserType("1");
            }
        }
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
        redisUtil.set(key,userInfoDto,3L, TimeUnit.HOURS);
        return resTokenDto;
    }

    /**
     * 获取乘客信息
     * @return
     */
    public ResPassengerDto getPassengerInfo(){
        ResPassengerDto resPassengerDto = new ResPassengerDto();
        UserInfoDto userInfoDto = ThreadLocalHelper.getUser();
        if(CommonUtil.isEmpty(userInfoDto)){
            throw new SystemException("获取用户信息失败","该用户未登录","请进行登录");
        }
        TbPassenger tbPassengerQuery = new TbPassenger();
        tbPassengerQuery.setMobileno(userInfoDto.getMobileno());
        List<TbPassenger> tbPassengerList = tbPassengerDao.selectByEntitySelective(tbPassengerQuery);
        if (CommonUtil.isNotEmpty(tbPassengerList)) {
            TbPassenger tbPassenger = tbPassengerList.get(0);
            BeanHelper.copyBeanValue(tbPassenger,resPassengerDto);
        }
        return resPassengerDto;
    }

    /**
     * 编辑用户
     * @param reqEditPassengerDto
     * @throws Exception
     */
    public void editPassengerInfo(ReqEditPassengerDto reqEditPassengerDto)throws Exception{
        if(CommonUtil.isEmpty(reqEditPassengerDto.getId())){
            throw new SystemException("更新失败","乘客id不能为空","请联系管理员处理");
        }
        TbPassenger tbPassenger = tbPassengerDao.selectByPrimaryKey(reqEditPassengerDto.getId());
        if(CommonUtil.isEmpty(tbPassenger)){
            throw new SystemException("更新失败","不存在该乘客信息","请刷新页面");
        }
        if(CommonUtil.isNotEmpty(reqEditPassengerDto.getSex())) {
            tbPassenger.setSex(reqEditPassengerDto.getSex());
        }
        if(CommonUtil.isNotEmpty(reqEditPassengerDto.getUserName())) {
            tbPassenger.setUserName(reqEditPassengerDto.getUserName());
        }
        if(CommonUtil.isNotEmpty(reqEditPassengerDto.getLocation())){
            tbPassenger.setLocation(reqEditPassengerDto.getLocation());
        }
        if(CommonUtil.isNotEmpty(reqEditPassengerDto.getLat())){
            tbPassenger.setLat(reqEditPassengerDto.getLat());
        }
        if(CommonUtil.isNotEmpty(reqEditPassengerDto.getLon())){
            tbPassenger.setLon(reqEditPassengerDto.getLon());
        }
        tbPassengerDao.update(tbPassenger);

    }

    /**
     * 更新司机位置信息
     * @param reqDriverLocationDto
     * @throws Exception
     */
    public void editDriverLocation(ReqDriverLocationDto reqDriverLocationDto)throws Exception{
        //如果app没有传id则从线程变量里拿
        if(CommonUtil.isEmpty(reqDriverLocationDto.getId())){
            reqDriverLocationDto.setId(ThreadLocalHelper.getUser().getId());
        }
        TbDriver tbDriver = tbDriverDao.selectByPrimaryKey(reqDriverLocationDto.getId());
        if(CommonUtil.isEmpty(tbDriver)){
            throw new SystemException("修改司机位置信息失败","不存在该司机","请联系管理员处理");
        }
        tbDriver.setLocation(reqDriverLocationDto.getLocation());
        tbDriver.setLat(reqDriverLocationDto.getLat());
        tbDriver.setLon(reqDriverLocationDto.getLon());
        tbDriverDao.update(tbDriver);
    }
}
