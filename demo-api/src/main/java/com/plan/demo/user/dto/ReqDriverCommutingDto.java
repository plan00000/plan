package com.plan.demo.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/3/21 0021.
 */
@Getter
@Setter
@ApiModel(description = "修改司机上下班请求dto")
public class ReqDriverCommutingDto {
//    @ApiModelProperty("司机id")
//    private String id;
    @ApiModelProperty("司机状态（0-下班，1-上班）")
    private String driverStatus;
}
