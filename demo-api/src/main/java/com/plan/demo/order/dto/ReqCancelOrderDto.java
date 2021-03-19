package com.plan.demo.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wpr on 2021/3/14 0014.
 */
@Setter
@Getter
@ApiModel(description = "取消订单dto")
public class ReqCancelOrderDto {
    @ApiModelProperty("订单表id")
    private String id;
    @ApiModelProperty("取消原因")
    private String cancelReason;
}
