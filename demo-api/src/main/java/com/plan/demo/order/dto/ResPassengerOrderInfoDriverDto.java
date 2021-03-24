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
@ApiModel(description = "乘客订单信息-司机相关信息dto")
public class ResPassengerOrderInfoDriverDto {
    @ApiModelProperty("司机id")
    private long id;
    @ApiModelProperty("手机号码")
    private String mobileno;
    @ApiModelProperty("车牌号")
    private String carNo;
    @ApiModelProperty("车身颜色")
    private String carColor;
    @ApiModelProperty("车辆品牌")
    private String carMark;
    @ApiModelProperty("司机评分级：几颗星最高5个星1代表一个星")
    private String driveStar;
}
