package com.plan.frame.system.controller;

import com.plan.frame.config.OperateLog;
import com.plan.frame.entity.Result;
import com.plan.frame.exception.BaseException;
import com.plan.frame.exception.SystemException;
import com.plan.frame.helper.ResultHelper;
import com.plan.frame.system.dto.log.ReqListSysLogDto;
import com.plan.frame.system.dto.log.ResListSysLogDto;
import com.plan.frame.system.service.OperateLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @Author
 * @Description: 日志管理
 * @Date 2020-4-14
 */
@Api(tags="93-日志管理接口")
@RestController
@RequestMapping(value = "/sysLog")
public class SysLogController {
    @Resource
    private OperateLogService operateLogService;


    @OperateLog
    @ApiOperation(value = "分页查询日志信息")
    @RequestMapping(value = "/listSysLog", method = RequestMethod.POST)
    @ResponseBody
    public Result<ResListSysLogDto> listSysLog(@RequestBody ReqListSysLogDto req) {
        try {
            ResListSysLogDto res = operateLogService.listSysOperateLog(req);
            return ResultHelper.success(res);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("查询出错", e, "请联系管理员！");
            }
        }
    }

    @ApiOperation(value = "测试")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Result<ResListSysLogDto> test() {
        try {
            ReqListSysLogDto req = new ReqListSysLogDto();
            req.setPageination(new Pageination());

            ResListSysLogDto res = operateLogService.listSysOperateLog(req);
            return ResultHelper.success(res);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("查询出错", e, "请联系管理员！");
            }
        }
    }

}
