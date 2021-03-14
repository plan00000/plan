package com.plan.demo.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2021/3/14 0014.
 */
@Setter
@Getter
@ApiModel(description = "线路信息")
public class ResLineDto {
    @ApiModelProperty("线路id")
    private String lineId;
    @ApiModelProperty("线路信息")
    private String lineName;
}
