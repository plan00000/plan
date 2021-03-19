package com.plan.demo.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wpr on 2021/3/19 0019.
 */
@Getter
@Setter
@ApiModel(description = "获取司机订单详细信息dto")
public class ResDriverOrderInfoDto {
    @ApiModelProperty("订单表id")
    private String id;

    @ApiModelProperty("下单时间")
    private Date orderTime;

    @ApiModelProperty("订单起点")
    private String orderStartAddress;

    @ApiModelProperty("订单起点经度")
    private String orderStartLon;

    @ApiModelProperty("订单起点纬度")
    private String orderStartLat;

    @ApiModelProperty("订单结束点")
    private String orderEndAddress;

    @ApiModelProperty("订单结束点经度")
    private String orderEndLon;

    @ApiModelProperty("订单起点纬度")
    private String orderEndLat;

    @ApiModelProperty("关联用户id")
    private String userId;

    @ApiModelProperty("乘客订单号")
    private String mobileNo;

    @ApiModelProperty("用户所在位置经纬度--纬度")
    private String lat;

    @ApiModelProperty("用户所在位置经纬度--经度")
    private String lon;
    @ApiModelProperty("订单类型(例：两人包车)")
    private String orderTypeInfo;
}
