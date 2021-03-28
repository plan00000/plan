package com.plan.demo.order.dto;

import com.plan.frame.entity.DateConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wpr on 2021/3/16 0016.
 */
@Setter
@Getter
@ApiModel(description = "乘客订单详情返回dto")
public class ResPassengerOrderInfoDto {
    @ApiModelProperty("订单表id")
    private long id;

    @ApiModelProperty("下单时间")
    @DateConvert(format = "yyyy-MM-dd HH:mm:ss")
    private String orderTime;

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

    @ApiModelProperty("订单状态:0-已取消，1-派单中，2-司机已接单，3-行程开始，4-行程已完成,5-订单超时")
    private String orderStatus;

    @ApiModelProperty("订单起点纬度")
    private ResPassengerOrderInfoDriverDto resPassengerOrderInfoDriverDto;
}
