package com.plan.frame.system.dto.log;

import com.plan.frame.entity.PageinationDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Created by xieyanling
 * @Description
 * @ClassName
 * @Create in 2020/4/13 16:13
 */
@Setter
@Getter
@ApiModel(description = "查询日志信息-请求对象")
public class ReqListSysLogDto extends PageinationDto {
    @ApiModelProperty("关键词(IP、操作人、操作内容），模糊查询")
    private String keyword;

    @ApiModelProperty(value = "操作时间范围-开始", example="yyyy-MM-dd HH:mm:ss")
    private String operateTimeStart;

    @ApiModelProperty(value = "操作时间范围-结束", example="yyyy-MM-dd HH:mm:ss")
    private String operateTimeEnd;
}
