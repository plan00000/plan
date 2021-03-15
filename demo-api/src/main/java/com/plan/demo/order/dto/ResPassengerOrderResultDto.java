package com.plan.demo.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Administrator on 2021/3/16 0016.
 */
@Setter
@Getter
@ApiModel(description = "订单列表返回dto")
public class ResPassengerOrderResultDto {
    @ApiModelProperty("乘客订单列表")
    List<ResPassengerOrderDto> resPassengerOrderDtoList;
}
