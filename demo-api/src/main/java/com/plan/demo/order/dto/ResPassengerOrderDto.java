package com.plan.demo.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by ljw on 2021/3/16 0016.
 */
@Setter
@Getter
@ApiModel(description = "乘客订单列表信息dto")
public class ResPassengerOrderDto {
    @ApiModelProperty("订单表id")
    private String id;
    @ApiModelProperty("下单时间")
    private Date orderTime;
    @ApiModelProperty("订单类型:0-包车，1-拼车")
    private String orderType;
    @ApiModelProperty("订单种类：0-实时单，1-预约单")
    private String orderRealType;
    @ApiModelProperty("订单状态:0-已取消，1-派单中，2-司机已接单，3-行程开始，4-行程已完成")
    private String orderStatus;
}
