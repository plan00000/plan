package com.plan.demo.user.dto;

import com.plan.demo.base.entity.TbOrder;
import com.plan.demo.order.dto.ResOrderLineDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by ljw on 2021/4/9 0009.
 */
@Getter
@Setter
public class ResPassengerFirstPageResultDto {
    @ApiModelProperty("是否有订单：0-没有，1-有")
    private String hasOrderFlag;
    @ApiModelProperty("乘客进行中的订单")
    private TbOrder tbOrder;
    @ApiModelProperty("热门线路列表")
    private List<ResOrderLineDto> hotLineList;
}
