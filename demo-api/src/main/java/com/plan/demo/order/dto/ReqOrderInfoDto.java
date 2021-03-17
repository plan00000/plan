package com.plan.demo.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/3/16 0016.
 */
@Setter
@Getter
@ApiModel(description = "乘客订单详情")
public class ReqOrderInfoDto {
    @ApiModelProperty("订单表id")
    private String id;
}
