package com.plan.demo.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wpr on 2021/3/17 0017.
 */
@Setter
@Getter
@ApiModel(description = "乘客请求dto")
public class ReqPassengerDto {
    @ApiModelProperty("乘客id")
    private String id;
}
