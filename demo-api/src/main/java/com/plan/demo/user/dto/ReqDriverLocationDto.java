package com.plan.demo.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/3/16 0016.
 */
@Setter
@Getter
@ApiModel(description = "修改司机所在位置请求dto")
public class ReqDriverLocationDto {
    @ApiModelProperty("司机id")
    private String id;
    @ApiModelProperty("用户目前位置")
    private String location;
    @ApiModelProperty("用户所在位置经纬度--纬度")
    private String lat;
    @ApiModelProperty("用户所在位置经纬度--经度")
    private String lon;
}
