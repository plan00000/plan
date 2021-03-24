package com.plan.demo.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/3/24 0024.
 */
@Getter
@Setter
public class ReqOrderNextStatusDto {
    @ApiModelProperty("订单表id")
    private long id;
    @ApiModelProperty("下一个状态:0-已取消，1-派单中，2-司机已接单，3-行程开始，4-行程已完成，5-订单超时")
    private String status;
}
