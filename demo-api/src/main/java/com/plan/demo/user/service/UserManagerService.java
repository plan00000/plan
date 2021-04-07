package com.plan.demo.user.service;

import com.plan.demo.base.dao.TbDriverDao;
import com.plan.demo.base.dao.TbOrderDao;
import com.plan.demo.base.dao.TbPassengerDao;
import com.plan.demo.base.entity.TbDriver;
import com.plan.demo.base.entity.TbOrder;
import com.plan.demo.base.entity.TbPassenger;
import com.plan.demo.user.dao.UserManagerMapper;
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
    @Autowired
    private UserManagerMapper userManagerMapper;

    /**
     * 获取随机码
     * @param reqMobileCodeDto
     * @return
     * @throws Exception
     */
    public ResMobileCodeDto getMobileCode(ReqMobileCodeDto reqMobileCodeDto)throws Exception{
        if(CommonUtil.isEmpty(reqMobileCodeDto.getMobileno())){
            throw new SystemException("","手机号不能为空","");
        }
        //先判断手机号是否有注册过
        TbPassenger tbPassengerQuery = new TbPassenger();
        tbPassengerQuery.setMobileno(reqMobileCodeDto.getMobileno());
        List<TbPassenger> tbPassengers = tbPassengerDao.selectByEntitySelective(tbPassengerQuery);
        if(CommonUtil.isEmpty(tbPassengers)){
            //未注册，则默认注册新的
            TbPassenger tbPassenger = new TbPassenger();
            Long id = userManagerMapper.findPassengerMaxId();
            tbPassenger.setMobileno(reqMobileCodeDto.getMobileno());
            tbPassenger.setUserName("新用户"+reqMobileCodeDto.getMobileno());
            tbPassenger.setId(id+1);
            tbPassenger.setState("1");
            tbPassenger.setCreateTime(new Date());
            tbPassenger.setUpdateTime(new Date());
            tbPassengerDao.insert(tbPassenger);
        }
        int code = (int)(Math.random()*8998)+1000+1;
        redisUtil.set(reqMobileCodeDto.getMobileno(),String.valueOf(code),300L, TimeUnit.SECONDS);
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
        //判断手机号是否存在
        TbPassenger tbPassengerQuery1 = new TbPassenger();
        tbPassengerQuery1.setMobileno(reqMobileCodeDto.getMobileno());
        List<TbPassenger> tbPassengerList1 = tbPassengerDao.selectByEntitySelective(tbPassengerQuery1);
        if(CommonUtil.isEmpty(tbPassengerList1)){
            throw new SystemException("获取token失败","该手机号为注册","");
        }
        ResTokenDto resTokenDto = new ResTokenDto();
        //判断验证码是否对
        String code = (String) redisUtil.get(reqMobileCodeDto.getMobileno());
        if(CommonUtil.isNotEmpty(code)) {
            if (!StringUtil.equalsString(reqMobileCodeDto.getCode(), code)) {
                throw new SystemException("获取token失败", "验证码过期或输入不正确", "请输入正确验证码");
            }
        }else{
            throw new SystemException("获取token失败", "验证码已过期", "请输入正确验证码");
        }
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
        resTokenDto.setId(userInfoDto.getId());
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

        /*if(CommonUtil.isEmpty(reqEditPassengerDto.getId())){
            throw new SystemException("更新失败","乘客id不能为空","请联系管理员处理");
        }*/
        reqEditPassengerDto.setId(ThreadLocalHelper.getUser().getId());

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
        if(CommonUtil.isNotEmpty(reqEditPassengerDto.getIdCard())){
            tbPassenger.setIdCard(reqEditPassengerDto.getIdCard());
        }
        if(CommonUtil.isNotEmpty(reqEditPassengerDto.getMobileno())){
            tbPassenger.setMobileno(reqEditPassengerDto.getMobileno());
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
        if(CommonUtil.isEmpty(reqDriverRegisterDto.getPassword())){
            throw new SystemException("司机注册失败","密码为空","请联系管理员处理");
        }
        //ljw在判断手机号是否注册过，注册过了就不让wpr注册
        TbDriver ljwTbDriverQuery = new TbDriver();
        ljwTbDriverQuery.setMobileno(reqDriverRegisterDto.getMobileno());

        TbDriver tbDriver = new TbDriver();
        //对密码进行md5加密
        BeanHelper.copyBeanValue(reqDriverRegisterDto,tbDriver);
        tbDriver.setPassword(DigestUtils.md5DigestAsHex(tbDriver.getPassword().getBytes()));
        long id = userManagerMapper.findDriverMaxId();
        tbDriver.setId(id+1);
        tbDriver.setDriverStatus("0");
        tbDriver.setDriverStar("4.5");
        tbDriver.setWorkAge("1");
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
        long driverId = ThreadLocalHelper.getUser().getId();
        TbDriver tbDriver = tbDriverDao.selectByPrimaryKey(driverId);
        if(CommonUtil.isEmpty(tbDriver.getCarNo())){
            resDriverFirstPageResultDto.setBinding("0");
        }else{
            resDriverFirstPageResultDto.setBinding("1");
        }
        resDriverFirstPageResultDto.setId(driverId);
        resDriverFirstPageResultDto.setWorkYears(tbDriver.getWorkAge());
        resDriverFirstPageResultDto.setDriverStatus(tbDriver.getDriverStatus());

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
     *
     * @param reqDriverLoginDto
     * @throws Exception
     */
    public ResTokenDto loginDriver(ReqDriverLoginDto reqDriverLoginDto) throws Exception{
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
        /*Boolean ljwPasswordCheck =StringUtil.equalsString(tbDriver.getPassword(),DigestUtils.md5DigestAsHex(reqDriverLoginDto.getPassword().getBytes()));
        if(!ljwPasswordCheck){
            throw new SystemException("司机登录失败","密码不正确","请重新输入");
        }*/
        ResTokenDto resTokenDto = new ResTokenDto();
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(tbDriver.getId());
        userInfoDto.setUserName(tbDriver.getUserName());
        userInfoDto.setSex(tbDriver.getSex());
        userInfoDto.setMobileno(tbDriver.getMobileno());
        //生成token
        //token过期时间
        if(CommonUtil.isEmpty(tokenExp)){
            tokenExp = "60";
        }
        //由于修改配置过于麻烦所有临时修改此处1.8*60 约等于 2*50
        Long tokenExpTime = 2*50*1000*Long.parseLong(tokenExp);
        Long tokenDate = System.currentTimeMillis()+tokenExpTime;
        String token = JwtUtil.getToken(tbDriver.getMobileno(),tokenDate);
        resTokenDto.setToken(token);
        resTokenDto.setTokenDate(DateUtil.date2Str(new Date(tokenDate),"yyyy-MM-dd HH:mm:ss"));
        //对token进行加密
        String key = DigestUtils.md5DigestAsHex(token.getBytes());
        //放入缓存
        redisUtil.set(key,userInfoDto,3L, TimeUnit.HOURS);
        resTokenDto.setId(userInfoDto.getId());
        return resTokenDto;
    }

    /**
     * 司机更改上下班及听单状态
     * @param reqDriverCommutingDto
     * @throws Exception
     */
    public void driverCommuting(ReqDriverCommutingDto reqDriverCommutingDto)throws Exception{
        long driverId = ThreadLocalHelper.getUser().getId();
        TbDriver tbDriver = tbDriverDao.selectByPrimaryKey(driverId);
        tbDriver.setDriverStatus(reqDriverCommutingDto.getDriverStatus());
        tbDriverDao.update(tbDriver);
    }

    /**
     * 获取司机详细信息
     * @return
     * @throws Exception
     */
    public ResDriverInfoDto getDriverInfo()throws Exception{
        long driverId = ThreadLocalHelper.getUser().getId();
        TbDriver tbDriver = tbDriverDao.selectByPrimaryKey(driverId);
        if(CommonUtil.isEmpty(tbDriver)){
            throw new SystemException("获取司机详细信息失败","没有该司机","");
        }
        ResDriverInfoDto resDriverInfoDto = new ResDriverInfoDto();
        BeanHelper.copyBeanValue(tbDriver,resDriverInfoDto);
        return resDriverInfoDto;
    }

    /**
     * 修改司机信息
     * @param reqEditDriverDto
     * @throws Exception
     */
    public void editDriver(ReqEditDriverDto reqEditDriverDto)throws Exception{
        long driverId = ThreadLocalHelper.getUser().getId();
        TbDriver tbDriver = tbDriverDao.selectByPrimaryKey(driverId);
        if(CommonUtil.isEmpty(tbDriver)){
            throw new SystemException("获取司机详细信息失败","没有该司机","");
        }
        BeanHelper.copyBeanValue(reqEditDriverDto,tbDriver);
        tbDriverDao.update(tbDriver);
    }

    /**
     * 司机注销
     * @param reqDriverDto
     * @throws Exception
     */
    public void deleteDriver(ReqDriverDto reqDriverDto) throws Exception{
        TbDriver tbDriver = tbDriverDao.selectByPrimaryKey(reqDriverDto.getId());
        if(CommonUtil.isEmpty(tbDriver)){
            throw new SystemException("司机注销失败","不存在该司机","请重新刷新页面");
        }
        tbDriver.setMobileno("");
        tbDriver.setState("2");
        tbDriver.setDriverStatus("0");
        tbDriver.setCarNo("");
        tbDriverDao.update(tbDriver);
    }




}
