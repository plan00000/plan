package com.plan.demo.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/3/26 0026.
 */
@Getter
@Setter
@ApiModel(description = "司机请求dto")
public class ReqDriverDto {
    @ApiModelProperty("")
    private Long id;
}
