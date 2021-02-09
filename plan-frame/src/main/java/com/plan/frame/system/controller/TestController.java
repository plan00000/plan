package com.plan.frame.system.controller;

import com.plan.frame.config.OperateLog;
import com.plan.frame.entity.Result;
import com.plan.frame.helper.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xieyanling
 * @date 2020/7/23
 */
@Api(tags = "96-test")
@RestController
@RequestMapping("/test")
public class TestController {
    @OperateLog
    @ApiOperation("新增信息")
    @PostMapping(value = "/add")
    public Result<String> add(){
        return ResultHelper.success();
    }


    @OperateLog
    @ApiOperation("修改信息")
    @PostMapping(value = "/update")
    public Result<String> update(){
        return ResultHelper.success();
    }

    @ApiOperation("查询信息")
    @PostMapping(value = "/list")
    public Result<String> list(){
        return ResultHelper.success();
    }
}
