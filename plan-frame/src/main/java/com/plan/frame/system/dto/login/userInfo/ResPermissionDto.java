package com.plan.frame.system.dto.login.userInfo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName ResPermissionDto
 * @Date 2020/6/15 17:55
 */
public class ResPermissionDto {
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("用户信息")
    private UserInfoDto userInfo;
    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty("角色信息")
    private RoleDto role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserInfoDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDto userInfo) {
        this.userInfo = userInfo;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }
}
