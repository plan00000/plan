package com.plan.demo.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2021/3/13 0013.
 */
@Setter
@Getter
@ApiModel(description = "返回token值")
public class ResTokenDto {
    @ApiModelProperty("手机号验证码")
    private String token;
    @ApiModelProperty("token过期时间")
    private String tokenDate;
}
