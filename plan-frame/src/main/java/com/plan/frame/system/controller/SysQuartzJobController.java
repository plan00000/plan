package com.plan.frame.system.controller;

import com.plan.frame.config.OperateLog;
import com.plan.frame.entity.Result;
import com.plan.frame.helper.ResultHelper;
import com.plan.frame.system.base.entity.SysQuartzJob;
import com.plan.frame.system.dto.quartz.ReqListJobsDto;
import com.plan.frame.system.dto.quartz.ReqListLogsDto;
import com.plan.frame.system.dto.quartz.ResListJobsDto;
import com.plan.frame.system.dto.quartz.ResListLogsDto;
import com.plan.frame.system.service.SysQuartzJobService;
import com.plan.frame.system.service.SysQuartzLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


@Api(tags = "94-定时任务管理")
@ApiIgnore
@RestController
@RequestMapping("/job")
public class SysQuartzJobController {

    private static final String ENTITY_NAME = "quartzJob";

    @Autowired
    private SysQuartzJobService quartzJobService;



    @Autowired
    private SysQuartzLogService quartzLogService;

    @OperateLog
    @ApiOperation("查询定时任务")
    @PostMapping(value = "/listJobs")
    public Result<ResListJobsDto> listJobs(@RequestBody ReqListJobsDto reqDto) throws Exception {
        return ResultHelper.success(quartzJobService.listJobsPage(reqDto));
    }

    /**
     * 查询定时任务日志
     * @param reqDto
     * @return
     */
    @ApiOperation("查询定时任务执行日志")
    @PostMapping(value = "/listLogs")
    public  Result<ResListLogsDto> listLogs(@RequestBody ReqListLogsDto reqDto) throws Exception {
        return ResultHelper.success(quartzLogService.listLogsPage(reqDto));
    }

    @OperateLog
    @ApiOperation("新增定时任务")
    @PostMapping(value = "/createJob")
    public Result<String> createJob(@RequestBody SysQuartzJob job){
        quartzJobService.createJob(job);
        return ResultHelper.success();
    }

    @OperateLog
    @ApiOperation("修改定时任务")
    @PostMapping(value = "/updateJob")
    public Result<String> updateJob(@RequestBody SysQuartzJob job){
        quartzJobService.updateJob(job);
        return ResultHelper.success();
    }

    @OperateLog
    @ApiOperation("更改定时任务状态")
    @ApiParam( value = "任务ID", required = true,name = "id")
    @PostMapping(value = "updateIsPause")
    public  Result<String>  updateIsPause(String id,Boolean isPause){
        quartzJobService.changeIsPause(id,isPause);
        return ResultHelper.success();
    }

    @OperateLog
    @ApiOperation("执行定时任务")
    @ApiParam( value = "任务ID", required = true,name = "id")
    @PostMapping(value = "/execution")
    public Result<String>  execution(String id){
        quartzJobService.execution(id);
        return ResultHelper.success();
    }

    @OperateLog
    @ApiOperation("删除定时任务")
    @ApiParam( value = "任务ID", required = true,name = "id")
    @PostMapping(value = "deleteJob")
    public ResponseEntity deleteJob(String id){
        quartzJobService.deleteJob(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
