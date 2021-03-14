package com.plan.demo.user.dto;

import com.plan.frame.entity.PageinationDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Administrator on 2021/3/14 0014.
 */
@Setter
@Getter
@ApiModel(description = "获取线路列表")
public class ResLineResultDto extends PageinationDto{
    @ApiModelProperty("线路类表")
    List<ResLineDto> resLineDtoList ;
}
