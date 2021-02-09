package com.plan.frame.system.dto.quartz;

import com.plan.frame.entity.PageinationDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by huangdongliang on 2020/4/18.
 */

@Setter
@Getter
@ApiModel(description = "任务日志查询-请求对象")
public class ReqListLogsDto extends PageinationDto {

    @ApiModelProperty("任务ID,关联JOB表主键")
    private String jobId;

    @ApiModelProperty("是否成功,0-否，1-是")
    private Boolean isSuccess;

    @ApiModelProperty("执行时间开始")
    private  String startTime;

    @ApiModelProperty("执行时间开始结束")
    private  String endTime;

}
