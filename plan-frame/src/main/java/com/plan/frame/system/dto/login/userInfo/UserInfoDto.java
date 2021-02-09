package com.plan.frame.system.dto.login.userInfo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Created by linzhihua
 * @Description 用户信息
 * @ClassName UserInfo
 * @Date 2020/6/15 18:11
 */
public class UserInfoDto {
    @ApiModelProperty("部门编码")
    private String deptId;
    @ApiModelProperty("部门名称")
    private String deptName;
//    @ApiModelProperty("用户密码")
//    private String password;
    @ApiModelProperty("用户电话")
    private String phone;
    @ApiModelProperty("上次登录ip")
    private String ip;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("角色编号")
    private String roleId;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("最后一次登录的时间")
    private String lastLogin;
    @ApiModelProperty("电子邮箱")
    private String email;
    @ApiModelProperty("用户名称")
    private String name;

    @ApiModelProperty("上次修改密码时间")
    private String lastUpdatePass;

    @ApiModelProperty("密码已过期，需提示更新")
    private boolean passwordExpired;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastUpdatePass() {
        return lastUpdatePass;
    }

    public void setLastUpdatePass(String lastUpdatePass) {
        this.lastUpdatePass = lastUpdatePass;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }
}
