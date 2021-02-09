package com.plan.frame.system.controller;

import com.plan.frame.config.OperateLog;
import com.plan.frame.entity.Result;
import com.plan.frame.exception.BaseException;
import com.plan.frame.exception.SystemException;
import com.plan.frame.helper.ResultHelper;
import com.plan.frame.helper.ThreadLocalHelper;
import com.plan.frame.system.dto.login.ResLoginDto;
import com.plan.frame.system.dto.login.dept.ReqDeptDto;
import com.plan.frame.system.dto.login.dept.ResDeptDto;
import com.plan.frame.system.dto.login.dictionary.ReqDictionaryDto;
import com.plan.frame.system.dto.login.dictionary.ResDictinaryDto;
import com.plan.frame.system.dto.login.menu.ReqFindMenuTreeDto;
import com.plan.frame.system.dto.login.menu.ResFindMenuTreeDto;
import com.plan.frame.system.dto.login.menu.ResRouteMenuDto;
import com.plan.frame.system.dto.login.userInfo.ReqUpdatePswDto;
import com.plan.frame.system.dto.login.userInfo.ResPermissionDto;
import com.plan.frame.system.dto.login.userInfo.UserInfoDto;
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
@Api(tags = "91-系统登录")
@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private LoginService loginService;

    @Value("${spring.menhuurl}")
    private String menhuurl;

    @Value("${spring.menhuurl2}")
    private String menhuurl2;

    /**
     * @Description:登录
     * @param:
     * @return:
     * @throws: RuntimeException
     */
//    @ApiOperation(value = "单点回调地址")
    @ApiIgnore
    @RequestMapping(value = "/checkAccount", method = RequestMethod.GET)
    public Result checkAccount(String toredirect, HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
        try {
            loginService.checkAccount(toredirect,request,response);
            return ResultHelper.success();
        } catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("登录失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:账号登录
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "单点登录成功后,系统获取token")
    @RequestMapping(value = "/loginAccount",method = RequestMethod.GET)
    public Result<ResLoginDto>  loginAccount(HttpServletRequest request)throws RuntimeException{
        try {
            ResLoginDto resLoginDto = loginService.loginAccount(request);
            return ResultHelper.success(resLoginDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("登录校验失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * 根据 用户id、子系统名称 获取相对应的菜单，权限
     * @param
     * @return PageResult
     */
    @ApiOperation(value = "获取用户权限")
    @RequestMapping(value = "/getPermission", method = RequestMethod.POST)
    @ResponseBody
    public Result<ResPermissionDto> getPermission() {
        try {
            ResPermissionDto resPermissionDto = loginService.getPermissionDto();
            return ResultHelper.success(resPermissionDto);
        } catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取权限成功", e, "请联系管理员！");
            }
        }
    }


    /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     *
     * @return
     */
    @ApiIgnore
    @RequestMapping(value = "/unAuth",method = RequestMethod.POST)
    @ResponseBody
    public Result<String>  unAuth() {
        try {
            return ResultHelper.faile(Result.Status.AUTH,"未登录");
        } catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取验证码出错", e, "请联系管理员！");
            }
        }
    }

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

    @RequestMapping(value = "/getLoginUsername", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getUsername(HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        try {
            UserInfoDto user = ThreadLocalHelper.getUser();
            return ResultHelper.success("获取用户名称成功",user);
        } catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取用户名称失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * 获取当前用户登录信息
     * @return
     */
    @ApiIgnore
    @RequestMapping(value="/getCurrentUser", method = RequestMethod.POST)
    public Result<UserInfoDto> getCurrentUser() {
        try {
            UserInfoDto userInfoDto = loginService.getCurrentUser();
            return ResultHelper.success(userInfoDto);
        } catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取当前用户信息成功", e, "请联系管理员！");
            }
        }
    }


    /**
     * @Description:
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "获取菜单树", extensions = @Extension(properties = @ExtensionProperty(name = "userVisable", value = "false")))
    @RequestMapping(value = "/findMenuTree",method = RequestMethod.POST)
    public Result<ResFindMenuTreeDto>  findMenuTree(@RequestBody ReqFindMenuTreeDto req)throws RuntimeException{
        try {
            UserInfoDto user = ThreadLocalHelper.getUser();
            String userName = user.getUserName();
            ResFindMenuTreeDto res = loginService.findMenuTree(userName, req);
            return ResultHelper.success(res);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取子菜单列表出错", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "获取菜单路由树")
    @RequestMapping(value = "/getRouteMenu",method = RequestMethod.POST)
    public Result<ResRouteMenuDto>  getRouteMenuTree(@RequestBody ReqFindMenuTreeDto req )throws RuntimeException{
        try {
            ResRouteMenuDto resRouteMenuDto = loginService.getRouteMenuTree(req);
            return ResultHelper.success(resRouteMenuDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取菜单路由树失败", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:获取字典信息
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "获取字典编码")
    @RequestMapping(value = "/findDicByBianMa",method = RequestMethod.POST)
    public Result<ResDictinaryDto>  findDicByBianMa(@RequestBody ReqDictionaryDto reqDictionaryDto)throws RuntimeException{
        try {
            ResDictinaryDto resDictinaryDto = loginService.findDicByBianMa(reqDictionaryDto);
            return ResultHelper.success(resDictinaryDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取菜单编码", e, "请联系管理员！");
            }
        }
    }

    /**
     * @Description:获取机构信息
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "获取机构信息")
    @RequestMapping(value = "/findDeptByIdOrNo",method = RequestMethod.POST)
    public Result<ResDeptDto>  getDeptForIdOrNo(@RequestBody ReqDeptDto reqDeptDto)throws RuntimeException{
        try {
            ResDeptDto resDeptDto = loginService.getDeptForIdOrNo(reqDeptDto);
            return ResultHelper.success(resDeptDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("获取机构信息", e, "请联系管理员！");
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

    /**
     * @Description:
     * @param
     * @throws RuntimeException
     */
    @ApiOperation(value = "判断是否有按钮权限")
    @RequestMapping(value = "/isHasButtonAuth",method = RequestMethod.POST)
    public Result<Boolean>  isHasButtonAuth(@ApiParam(value = "用户名", required = true) @RequestParam("userName") @NotNull String userName,
                                                      @ApiParam(value = "按钮编号", required = true) @RequestParam("buttonNo") @NotNull String buttonNo)throws RuntimeException{
        try {
            Boolean flag = loginService.isHasButtonAuth(userName, buttonNo);
            return ResultHelper.success(flag);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("判断是否有按钮权限出错", e, "请联系管理员！");
            }
        }
    }
}