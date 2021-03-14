package com.plan.demo.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2021/3/14 0014.
 */
@Setter
@Getter
@ApiModel(description = "订单新增dto")
public class ReqAddOrderDto {
    @ApiModelProperty("订单起点(地址信息)")
    private String orderStartAddress;

    @ApiModelProperty("订单起点经度")
    private String orderStartLon;

    @ApiModelProperty("订单起点纬度")
    private String orderStartLat;

    @ApiModelProperty("订单结束点(地址信息)")
    private String orderEndAddress;

    @ApiModelProperty("订单结束点经度")
    private String orderEndLon;

    @ApiModelProperty("订单起点纬度")
    private String orderEndLat;

    @ApiModelProperty("订单类型:0-包车，1-拼车")
    private String orderType;

    @ApiModelProperty("订单种类：0-实时单，1-预约单")
    private String orderRealType;

    @ApiModelProperty("关联用户id")
    private String userId;

    @ApiModelProperty("乘客人数")
    private String orderUserNum;

    @ApiModelProperty("所属线路")
    private String lineId;
}
