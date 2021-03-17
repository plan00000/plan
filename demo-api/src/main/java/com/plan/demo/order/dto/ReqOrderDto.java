package com.plan.demo.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/3/17 0017.
 */
@Getter
@Setter
@ApiModel(description = "获取订单信息dto")
public class ReqOrderDto {
    @ApiModelProperty("订单表id")
    private String id;
}
