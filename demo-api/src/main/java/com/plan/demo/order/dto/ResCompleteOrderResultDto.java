package com.plan.demo.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by ljw on 2021/3/20 0020.
 */
@Getter
@Setter
@ApiModel(description = "获取乘客或司机完成订单列表返回dto")
public class ResCompleteOrderResultDto {
    @ApiModelProperty("订单列表")
    private List<ResCompleteOrderDto> completeOrderDtoList;
}
