package com.plan.demo.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/3/18 0018.
 */
@Getter
@Setter
public class ReqDriverRegisterDto {
    @ApiModelProperty("手机号码")
    private String mobileno;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("性别：1-男生，2-女生")
    private String sex;
    @ApiModelProperty("年龄")
    private String age;
    @ApiModelProperty("身份证号")
    private String idCard;
    @ApiModelProperty("车牌号")
    private String carNo;
    @ApiModelProperty("车身颜色")
    private String carColor;
    @ApiModelProperty("车辆品牌")
    private String carMark;
    @ApiModelProperty("驾驶证号")
    private String driverNo;

}
