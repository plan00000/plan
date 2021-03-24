package com.plan.demo.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wpr on 2021/3/17 0017.
 */
@Getter
@Setter
@ApiModel(description = "获取乘客实时订单dto")
public class ReqOrderRealTypeDto {
    @ApiModelProperty("用户id")
    private long userId;
    @ApiModelProperty("订单种类：0-实时单，1-预约单")
    private String orderRealType;
}
