package com.plan.demo.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/3/20 0020.
 */
@Getter
@Setter
@ApiModel(description = "司机登录请求dto")
public class ReqDriverLoginDto {
    @ApiModelProperty("手机号")
    private String mobileno;

    @ApiModelProperty("密码")
    private String password;

}
