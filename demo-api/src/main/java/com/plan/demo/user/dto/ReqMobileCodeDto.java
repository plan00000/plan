package com.plan.demo.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/3/13 0013.
 */
@Setter
@Getter
@ApiModel(description = "获取手机验证码")
public class ReqMobileCodeDto {
    @ApiModelProperty("手机号")
    private String mobileno;
    @ApiModelProperty("用户类型:0-乘客，1-司机")
    private String userType;
    @ApiModelProperty("验证码（获取token时必填）")
    private String code;

}
