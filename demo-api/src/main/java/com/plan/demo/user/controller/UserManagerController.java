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

    @ApiOperation(value = "获取手机验证码")
    @RequestMapping(value = "/getMobileCode",method = RequestMethod.POST)
    public Result<ResMobileCodeDto>  getMobileCode(@RequestBody ReqMobileCodeDto reqMobileCodeDto)throws RuntimeException{
        try {
            ResMobileCodeDto resMobileCodeDto = new ResMobileCodeDto();
            resMobileCodeDto.setCode("123323");
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
    @ApiOperation(value = "登录成功后,系统获取token")
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
    @ApiOperation(value = "获取乘车人信息")
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
    @ApiOperation(value = "修改完善乘车人信息及更新乘客实时位置信息")
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
    @ApiOperation(value = "更新司机位置信息")
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




}