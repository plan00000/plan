package com.plan.demo.user.controller;

import com.plan.demo.user.dto.*;
import com.plan.demo.user.service.UserManagerService;
import com.plan.frame.entity.Result;
import com.plan.frame.exception.BaseException;
import com.plan.frame.exception.SystemException;
import com.plan.frame.helper.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;


/**
 * @Author: ljwwpr
 * @Description:
 * @Date: Created in 2021/2/23 9:02
 * @Modified By:
 */
@RestController
@Api(tags = "1-乘客司机管理接口")
@RequestMapping("/login")
public class UserManagerController {
    @Autowired
    private UserManagerService userManagerService;

    @ApiOperation(value = "1-获取手机验证码")
    @RequestMapping(value = "/getMobileCode",method = RequestMethod.POST)
    public Result<ResMobileCodeDto>  getMobileCode(@RequestBody ReqMobileCodeDto reqMobileCodeDto)throws RuntimeException{
        try {
            ResMobileCodeDto resMobileCodeDto = userManagerService.getMobileCode(reqMobileCodeDto);
            return ResultHelper.success(resMobileCodeDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取手机验证码", e, "请联系管理员！");
            }
        }
    }
    /**
     * @Description:账号登录
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "2-乘客获取系统token")
    @RequestMapping(value = "/getToken",method = RequestMethod.POST)
    public Result<ResTokenDto>  getToken(@RequestBody ReqMobileCodeDto reqMobileCodeDto)throws RuntimeException{
        try {
            ResTokenDto resTokenDto = userManagerService.getToken(reqMobileCodeDto);
            return ResultHelper.success(resTokenDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("系统获取token", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:获取乘车人信息
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "3-获取乘车人信息")
    @RequestMapping(value = "/getPassenger",method = RequestMethod.POST)
    public Result<ResPassengerDto> getPassenger()throws RuntimeException{
        try {
            ResPassengerDto  resPassengerDto = userManagerService.getPassengerInfo();
            return ResultHelper.success(resPassengerDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取乘车人信息失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:修改完善乘车人信息
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "3-修改完善乘车人信息及更新乘客实时位置信息")
    @RequestMapping(value = "/editPassengerInfo",method = RequestMethod.POST)
    public Result<String> editPassengerInfo(@RequestBody ReqEditPassengerDto reqEditPassengerDto)throws RuntimeException{
        try {
            userManagerService.editPassengerInfo(reqEditPassengerDto);
            return ResultHelper.success();
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取乘车人信息失败", e, "请联系管理员！");
            }
        }
    }
    /**
     * @Description:更新司机位置信息
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "4-更新司机位置信息")
    @RequestMapping(value = "/editDriverLocation",method = RequestMethod.POST)
    public Result<String> editDriverLocation(@RequestBody ReqDriverLocationDto reqDriverLocationDto)throws RuntimeException{
        try {
            userManagerService.editDriverLocation(reqDriverLocationDto);
            return ResultHelper.success();
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取乘车人信息失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:乘客注销账号
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "5-乘客注销账号")
    @RequestMapping(value = "/dropPassenger",method = RequestMethod.POST)
    public Result<String> dropPassenger(@RequestBody ReqPassengerDto reqPassengerDto)throws RuntimeException{
        try {
            userManagerService.dropPassenger(reqPassengerDto);
            return ResultHelper.success();
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("乘客注销账号失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:司机注册
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "6-司机注册")
    @RequestMapping(value = "/registerDriver",method = RequestMethod.POST)
    public Result<String> registerDriver(@RequestBody ReqDriverRegisterDto reqDriverRegisterDto)throws RuntimeException{
        try {
            userManagerService.registerDriver(reqDriverRegisterDto);
            return ResultHelper.success();
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("乘客注销账号失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:司机账号登录
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "7-司机账号登录")
    @RequestMapping(value = "/loginDriver",method = RequestMethod.POST)
    public Result<ResTokenDto> loginDriver(@RequestBody ReqDriverLoginDto reqDriverLoginDto)throws RuntimeException{
        try {
            ResTokenDto resTokenDto =userManagerService.loginDriver(reqDriverLoginDto);
            return ResultHelper.success(resTokenDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("司机账号登录失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:司机首页信息
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "8-司机首页信息")
    @RequestMapping(value = "/getDriverFirstPageInfo",method = RequestMethod.GET)
    public Result<ResDriverFirstPageResultDto> getDriverFirstPageInfo()throws RuntimeException{
        try {
            ResDriverFirstPageResultDto resDriverFirstPageResultDto=userManagerService.getDriverFirstPageInfo();
            return ResultHelper.success(resDriverFirstPageResultDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("司机首页信息失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:司机上下班
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "9-司机上下班")
    @RequestMapping(value = "/driverCommuting",method = RequestMethod.POST)
    public Result<String> driverCommuting(@RequestBody ReqDriverCommutingDto reqDriverCommutingDto)throws RuntimeException{
        try {
            userManagerService.driverCommuting(reqDriverCommutingDto);
            return ResultHelper.success();
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("司机上下班失败", e, "请联系管理员！");
            }
        }
    }

    /**
     *
     * @param
     * @return
     * @throws RuntimeException
     */
    @ApiOperation(value = "11-获取司机详细信息")
    @RequestMapping(value = "/getDriverInfo",method = RequestMethod.GET)
    public Result<ResDriverInfoDto> getDriverInfo()throws RuntimeException{
        try {
            ResDriverInfoDto resDriverInfoDto =userManagerService.getDriverInfo();
            return ResultHelper.success(resDriverInfoDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取司机详细信息失败", e, "请联系管理员！");
            }
        }
    }

    @ApiOperation(value = "12-修改司机详细信息")
    @RequestMapping(value = "/editDriverInfo",method = RequestMethod.GET)
    public Result<ResDriverInfoDto> editDriverInfo(@RequestBody ReqEditDriverDto reqEditDriverDto)throws RuntimeException{
        try {
            userManagerService.editDriver(reqEditDriverDto);
            return ResultHelper.success();
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("修改司机详细信息失败", e, "请联系管理员！");
            }
        }
    }






}