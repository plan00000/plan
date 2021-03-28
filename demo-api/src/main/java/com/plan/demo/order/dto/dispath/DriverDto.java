package com.plan.demo.order.dto.dispath;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: ljw
 * @Description:
 * @Date: Created in 2021/3/26 14:34
 * @Modified By:
 */
@Getter
@Setter
public class DriverDto {

    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("手机号码")
    private String mobileno;

    @ApiModelProperty("司机状态（0-下班，1-上班）")
    private String driverStatus;

    @ApiModelProperty("司机评分级：几颗星最高5个星1代表一个星")
    private String driverStar;

    @ApiModelProperty("用户目前位置")
    private String location;

    @ApiModelProperty("用户所在位置经纬度--纬度")
    private String lat;

    @ApiModelProperty("用户所在位置经纬度--经度")
    private String lon;

}
