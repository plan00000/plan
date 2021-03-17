package com.plan.frame.system.controller;

import com.plan.frame.config.OperateLog;
import com.plan.frame.entity.Result;
import com.plan.frame.exception.BaseException;
import com.plan.frame.exception.SystemException;
import com.plan.frame.helper.ResultHelper;
import com.plan.frame.system.dto.login.userInfo.ReqUpdatePswDto;
import com.plan.frame.system.service.LoginService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * @Author: linzhihua
 * @Description（类描述）: 登录controller
 * @Date: Created in 2020/4/20 15:42
 * @Modified By:
 */
@ApiIgnore
@Api(tags = "91-系统登录")
@RestController
@RequestMapping("/loginold")
public class LoginController {
    @Resource
    private LoginService loginService;

    @Value("${spring.menhuurl}")
    private String menhuurl;

    @Value("${spring.menhuurl2}")
    private String menhuurl2;
    /**
     * 退出系统
     * @return
     */
    @ApiIgnore
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public Result logout(HttpServletResponse response) throws Exception {
        try {
            loginService.logout(response);
            return ResultHelper.success("退出登录成功");
        } catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取验证码出错", e, "请联系管理员！");
            }
        }
    }


    /**
     * @Description: 修改密码
     * @param
     * @throws RuntimeException
     */
    @OperateLog
    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "/updatePsw",method = RequestMethod.POST)
    public Result<String>  updatePsw(@RequestBody ReqUpdatePswDto req)throws RuntimeException{
        try {
            Result<String> stringResult = loginService.updatePsw(req);
            return stringResult;
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("修改密码出错", e, "请联系管理员！");
            }
        }
    }

}