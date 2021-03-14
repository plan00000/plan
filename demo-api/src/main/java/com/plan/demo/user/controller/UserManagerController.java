package com.plan.demo.user.controller;

import com.plan.demo.user.dto.ReqMobileCodeDto;
import com.plan.demo.user.dto.ResMobileCodeDto;
import com.plan.demo.user.dto.ResTokenDto;
import com.plan.demo.user.service.UserManagerService;
import com.plan.frame.entity.Result;
import com.plan.frame.exception.BaseException;
import com.plan.frame.exception.SystemException;
import com.plan.frame.helper.ResultHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;


/**
 * @Author: linzhihua
 * @Description:
 * @Date: Created in 2021/2/23 9:02
 * @Modified By:
 */
@Controller
@RequestMapping("/login")
public class UserManagerController {
    @Autowired
    private UserManagerService userManagerService;

    @ApiOperation(value = "获取手机验证码")
    @RequestMapping(value = "/getMobileCode",method = RequestMethod.POST)
    public Result<ResMobileCodeDto>  getMobileCode(ReqMobileCodeDto reqMobileCodeDto)throws RuntimeException{
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
    @ApiIgnore
    @ApiOperation(value = "登录成功后,系统获取token")
    @RequestMapping(value = "/getToken",method = RequestMethod.GET)
    public Result<ResTokenDto>  getToken(ReqMobileCodeDto reqMobileCodeDto)throws RuntimeException{
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

}