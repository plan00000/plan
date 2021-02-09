package com.plan.frame.system.dto.quartz;

import com.plan.frame.entity.PageinationDto;
import com.plan.frame.system.base.entity.SysQuartzLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Created by shenyiyi
 * @Description
 * @ClassName
 * @Create in 2020/4/14 18:35
 */
@ApiModel(description = "任务查询-应答对象")
public class ResListLogsDto extends PageinationDto {
    @ApiModelProperty("日志列表")
    private List<SysQuartzLog> logList;

    public List<SysQuartzLog> getLogList() {
        return logList;
    }

    public void setLogList(List<SysQuartzLog> logList) {
        this.logList = logList;
    }
}
