package com.plan.demo.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/3/14 0014.
 */
@Setter
@Getter
@ApiModel(description = "获取用户信息")
public class ResPassengerDto {
    @ApiModelProperty("用户id")
    private long id;
    @ApiModelProperty("用户名称")
    private String userName;
    @ApiModelProperty("手机号码")
    private String mobileno;
    @ApiModelProperty("用户目前位置")
    private String location;
    @ApiModelProperty("用户所在位置经纬度--纬度")
    private String lat;
    @ApiModelProperty("用户所在位置经纬度--经度")
    private String lon;
}
