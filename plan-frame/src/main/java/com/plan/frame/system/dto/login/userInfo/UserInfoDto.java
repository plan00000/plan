package com.plan.frame.system.dto.login.userInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Created by linzhihua
 * @Description 用户信息
 * @ClassName UserInfo
 * @Date 2020/6/15 18:11
 */
@Getter
@Setter
public class UserInfoDto {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("性别：1-男生，2-女生")
    private String sex;
    @ApiModelProperty("手机号码")
    private String mobileno;
    @ApiModelProperty("用户类型:0-乘客，1-司机")
    private String userType;

    }
