package com.plan.demo.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/3/22 0022.
 */
@Getter
@Setter
public class ResAddOrderDto {
    @ApiModelProperty("id")
    private long id;
}
