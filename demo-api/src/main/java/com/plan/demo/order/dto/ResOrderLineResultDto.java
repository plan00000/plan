package com.plan.demo.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by ljw on 2021/3/16 0016.
 */
@Setter
@Getter
@ApiModel(description = "线路信息返回dto")
public class ResOrderLineResultDto {
    @ApiModelProperty("线路列表")
    private List<ResOrderLineDto> orderLineList;
}
