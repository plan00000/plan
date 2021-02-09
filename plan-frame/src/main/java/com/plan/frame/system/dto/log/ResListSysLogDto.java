package com.plan.frame.system.dto.log;

import com.plan.frame.entity.PageinationDto;
import com.plan.frame.system.base.entity.SysOperateLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Created by shenyiyi
 * @Description
 * @ClassName
 * @Create in 2020/4/13 14:05
 */
@Setter
@Getter
@ApiModel(description = "查询日志信息-应答对象")
public class ResListSysLogDto extends PageinationDto {
    @ApiModelProperty("日志信息列表")
    private List<SysOperateLog> sysLogList;


}
