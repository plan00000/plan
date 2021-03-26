package com.plan.demo.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/3/26 0026.
 */
@Getter
@Setter
@ApiModel(description = "乘客订单评价")
public class ReqOrderEvaluationDto {
    @ApiModelProperty("订单表id")
    private long id;

    @ApiModelProperty("订单评价星级：几颗星最高5个星")
    private String orderStar;

    @ApiModelProperty("订单服务评价(111111-车内整洁，认路准，驾驶平稳，态度好，有礼貌，服务周到)")
    private String orderService;

    @ApiModelProperty("订单评价")
    private String orderEvaluate;
}
