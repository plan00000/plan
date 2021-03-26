package com.plan.demo.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/3/25 0025.
 */
@Getter
@Setter
public class ResDriverInfoDto {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("性别：1-男生，2-女生")
    private String sex;

    @ApiModelProperty("年龄")
    private String age;

    @ApiModelProperty("工龄")
    private String workAge;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("手机号码")
    private String mobileno;

    @ApiModelProperty("状态 0-禁用,1-启用,2-删除")
    private String state;

    @ApiModelProperty("司机状态（0-下班，1-上班）")
    private String driverStatus;

    @ApiModelProperty("用户目前位置")
    private String location;

    @ApiModelProperty("用户所在位置经纬度--纬度")
    private String lat;

    @ApiModelProperty("用户所在位置经纬度--经度")
    private String lon;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("车牌号")
    private String carNo;

    @ApiModelProperty("车辆颜色")
    private String carColor;

    /**
     * 车辆品牌，对应表字段为：tb_driver.car_mark
     */
    @ApiModelProperty("车辆品牌")
    private String carMark;

    @ApiModelProperty("司机评分级：几颗星最高5个星1代表一个星")
    private String driverStar;

    @ApiModelProperty("驾驶证号")
    private String driverNo;
}
