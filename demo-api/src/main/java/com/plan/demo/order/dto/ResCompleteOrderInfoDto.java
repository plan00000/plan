package com.plan.demo.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/3/27 0027.
 */
@Setter
@Getter
@ApiModel(description = "获取司机或乘客完成订单详细信息dto")
public class ResCompleteOrderInfoDto {
    @ApiModelProperty("订单表id")
    private long id;
    @ApiModelProperty("订单类型:0-包车，1-拼车")
    private String orderTypeInfo;
    @ApiModelProperty("下单时间")
    private String orderTime;

    @ApiModelProperty("订单起点")
    private String orderStartAddress;

    @ApiModelProperty("订单结束点")
    private String orderEndAddress;

    @ApiModelProperty("订单起点经度")
    private String orderStartLon;

    @ApiModelProperty("订单起点纬度")
    private String orderStartLat;

    @ApiModelProperty("订单结束点经度")
    private String orderEndLon;

    @ApiModelProperty("订单起点纬度")
    private String orderEndLat;

    @ApiModelProperty("订单评价星级：几颗星最高5个星")
    private String orderStar;

    @ApiModelProperty("订单服务评价(111111-车内整洁，认路准，驾驶平稳，态度好，有礼貌，服务周到)")
    private String orderService;

    @ApiModelProperty("订单评价")
    private String orderEvaluate;
}
