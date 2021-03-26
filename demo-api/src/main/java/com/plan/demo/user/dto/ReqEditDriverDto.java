package com.plan.demo.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/3/23 0026.
 */
@Getter
@Setter
@ApiModel(description = "修改司机信息dto")
public class ReqEditDriverDto {
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

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("车牌号")
    private String carNo;

    @ApiModelProperty("车辆颜色")
    private String carColor;

    @ApiModelProperty("车辆品牌")
    private String carMark;

    @ApiModelProperty("驾驶证号")
    private String driverNo;
}
