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
@ApiModel(description = "查询-请求对象")
public class ReqListJobsDto  extends PageinationDto {

    @ApiModelProperty("任务名称,模糊查询")
    private String jobName;

    @ApiModelProperty("Spring Bean名称")
    private String beanName;

}
