package com.plan.demo.user.service;

import com.plan.demo.base.dao.TbDriverDao;
import com.plan.demo.base.dao.TbOrderDao;
import com.plan.demo.base.dao.TbPassengerDao;
import com.plan.demo.base.entity.TbDriver;
import com.plan.demo.base.entity.TbOrder;
import com.plan.demo.base.entity.TbPassenger;
import com.plan.demo.user.dto.*;
import com.plan.frame.cache.DictinaryCache;
import com.plan.frame.exception.SystemException;
import com.plan.frame.helper.BeanHelper;
import com.plan.frame.helper.ThreadLocalHelper;
import com.plan.frame.system.dto.login.userInfo.UserInfoDto;
import com.plan.frame.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ljwwpr 2021/3/14 0014
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
    @Autowired
    private TbOrderDao tbOrderDao;

    /**
     * 获取随机码
     * @param reqMobileCodeDto
     * @return
     * @throws Exception
     */
    public ResMobileCodeDto getMobileCode(ReqMobileCodeDto reqMobileCodeDto)throws Exception{
        int code = (int)(Math.random()*8998)+1000+1;
        redisUtil.set(reqMobileCodeDto.getMobileno(),String.valueOf(code),5L, TimeUnit.SECONDS);
        ResMobileCodeDto resMobileCodeDto = new ResMobileCodeDto();
        resMobileCodeDto.setCode(String.valueOf(code));
        return resMobileCodeDto;
    }

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

    /**
     * 乘客注销账号
     * @param reqPassengerDto
     * @throws Exception
     */
    public void dropPassenger(ReqPassengerDto reqPassengerDto)throws Exception{
        TbPassenger tbPassenger = tbPassengerDao.selectByPrimaryKey(reqPassengerDto.getId());
        if(CommonUtil.isEmpty(tbPassenger)){
            throw new SystemException("乘客注销账号","不存在该用户","请联系管理员处理");
        }
        tbPassenger.setState("2");
        tbPassenger.setMobileno("");
        tbPassengerDao.update(tbPassenger);
    }

    /**
     * 司机注册
     * @param reqDriverRegisterDto
     * @throws Exception
     */
    public void registerDriver(ReqDriverRegisterDto reqDriverRegisterDto) throws Exception{
        if(CommonUtil.isNotEmpty(reqDriverRegisterDto.getPassword())){
            throw new SystemException("司机注册失败","密码为空","请联系管理员处理");
        }
        //ljw在判断手机号是否注册过，注册过了就不让wpr注册
        TbDriver ljwTbDriverQuery = new TbDriver();
        ljwTbDriverQuery.setMobileno(reqDriverRegisterDto.getMobileno());

        TbDriver tbDriver = new TbDriver();
        //对密码进行md5加密
        BeanHelper.copyBeanValue(reqDriverRegisterDto,tbDriver);
        tbDriver.setId(CommonUtil.getUUID());
        tbDriver.setPassword(DigestUtils.md5DigestAsHex(tbDriver.getPassword().getBytes()));
        tbDriver.setCreateTime(new Date());
        tbDriver.setUpdateTime(new Date());
        tbDriverDao.insert(tbDriver);
    }

    /**
     * 司机首页信息
     * @return
     * @throws Exception
     */
    public ResDriverFirstPageResultDto getDriverFirstPageInfo()throws Exception{
        ResDriverFirstPageResultDto resDriverFirstPageResultDto = new ResDriverFirstPageResultDto();
        String driverId = ThreadLocalHelper.getUser().getId();
        TbDriver tbDriver = tbDriverDao.selectByPrimaryKey(driverId);
        resDriverFirstPageResultDto.setId(driverId);
        resDriverFirstPageResultDto.setWorkYears(tbDriver.getWorkAge());

        TbOrder tbOrderQuery = new TbOrder();
        tbOrderQuery.setDriveId(driverId);
        tbOrderQuery.setOrderStatus("4");
        List<TbOrder> completeTbOrderList = tbOrderDao.selectByEntitySelective(tbOrderQuery);
        if(CommonUtil.isNotEmpty(completeTbOrderList)){
            int orderNum = completeTbOrderList.size();
            resDriverFirstPageResultDto.setCompleteOrderNum(String.valueOf(orderNum));
        }else{
            resDriverFirstPageResultDto.setCompleteOrderNum("0");
        }
        //未完成订单
        tbOrderQuery.setOrderStatus("2");
        List<TbOrder> ljwTbOrderList = tbOrderDao.selectByEntitySelective(tbOrderQuery);
        List<ResDriverFirstPageOderDto >resDriverFirstPageOderDtoList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(ljwTbOrderList)){
            for(TbOrder tbOrder:ljwTbOrderList){
                ResDriverFirstPageOderDto resDriverFirstPageOderDto = new ResDriverFirstPageOderDto();
                resDriverFirstPageOderDto.setOrderId(tbOrder.getId());
                resDriverFirstPageOderDto.setOrderRealType(tbOrder.getOrderRealType());
                resDriverFirstPageOderDto.setOrderType(DictinaryCache.getCache().getDictCnName("order_type",tbOrder.getOrderType()));
                if(StringUtil.equalsString(tbOrder.getOrderRealType(),"0")){
                    resDriverFirstPageOderDto.setShowOrderInfo("您有一个新的包车订单");
                }else{
                    resDriverFirstPageOderDto.setShowOrderInfo("您有一个新的拼车订单");
                }
                resDriverFirstPageOderDtoList.add(resDriverFirstPageOderDto);
            }
        }
        resDriverFirstPageResultDto.setOrderList(resDriverFirstPageOderDtoList);
        return resDriverFirstPageResultDto;
    }

    /**
     * 司机登录
     * 拉拉拉拉德玛西亚万岁万岁
     *还是去掉不实现，跟乘客端一样采用手机验证码登录，后面要改在说
     * @param reqDriverLoginDto
     * @throws Exception
     */
    public void loginDriver(ReqDriverLoginDto reqDriverLoginDto) throws Exception{
        if(CommonUtil.isEmpty(reqDriverLoginDto.getMobileno())||CommonUtil.isEmpty(reqDriverLoginDto.getPassword())){
            throw new SystemException("司机登录失败","账号或手机不能为空","请检查");
        }
        TbDriver tbDriverQuery = new TbDriver();
        tbDriverQuery.setMobileno(reqDriverLoginDto.getMobileno());
        List<TbDriver> tbDriverList = tbDriverDao.selectByEntitySelective(tbDriverQuery);
        if(CommonUtil.isEmpty(tbDriverList)){
            throw new SystemException("司机登录失败","该账号未注册","请先注册");
        }
        TbDriver tbDriver = tbDriverList.get(0);
        //密码
        Boolean ljwPasswordCheck =StringUtil.equalsString(tbDriver.getPassword(),DigestUtils.md5DigestAsHex(reqDriverLoginDto.getPassword().getBytes()));
        if(!ljwPasswordCheck){
            throw new SystemException("司机登录失败","密码不正确","请重新输入");
        }
    }




}
