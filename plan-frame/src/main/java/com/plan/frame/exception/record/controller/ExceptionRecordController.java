package com.plan.frame.exception.record.controller;

import com.plan.frame.entity.Result;
import com.plan.frame.exception.BaseException;
import com.plan.frame.exception.SystemException;
import com.plan.frame.exception.record.dto.ReqExceptionRecordDto;
import com.plan.frame.exception.record.dto.ResExceptionRecordDto;
import com.plan.frame.exception.record.service.ExceptionRecordService;
import com.plan.frame.helper.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Created by linzhihua
 * @Description 异常信息记录查询controller
 * @ClassName ExceptionRecordController
 * @Date 2020/4/3 9:13
 */
@Api(tags="95-查询异常记录信息")
@RequestMapping("/record")
@RestController
public class ExceptionRecordController {
    @Resource
    private ExceptionRecordService exceptionRecordService;

    /**
     * 列表条件查询
     * @param
     * @return
     * @throws RuntimeException
     */
    @ApiOperation(value = "获取异常信息列表")
    @RequestMapping(value = "/exceptionRecordList",method = RequestMethod.POST)
    public Result<ResExceptionRecordDto> exceptionRecordList(@RequestBody ReqExceptionRecordDto reqExceptionRecordDto)throws RuntimeException{
        try {
            ResExceptionRecordDto resExceptionRecordDto = exceptionRecordService.exceptionRecordList(reqExceptionRecordDto);
            return ResultHelper.success(resExceptionRecordDto);
        }catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new SystemException("查询出错", e, "请联系管理员！");
            }
        }
    }
    /**
     * 列表条件查询
     * @param
     * @return
     * @throws RuntimeException
     */
    @ApiOperation(value = "获取异常信息列表")
    @RequestMapping(value = "/voTest",method = RequestMethod.POST)
    public Result<ResExceptionRecordDto> voTest(@RequestBody ReqExceptionRecordDto reqExceptionRecordDto)throws Exception{

        ResExceptionRecordDto resExceptionRecordDto = exceptionRecordService.testVo(reqExceptionRecordDto);
        return ResultHelper.success(resExceptionRecordDto);

    }


}
