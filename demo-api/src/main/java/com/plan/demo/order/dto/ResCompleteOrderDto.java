package com.plan.demo.order.dto;

import com.plan.frame.entity.DateConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by ljw on 2021/3/20 0020.
 */
@Getter
@Setter
@ApiModel(description = "获取司机或乘客完成订单列表详细信息dto")
public class ResCompleteOrderDto {
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
}
