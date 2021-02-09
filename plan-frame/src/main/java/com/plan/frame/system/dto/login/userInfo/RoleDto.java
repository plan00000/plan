package com.plan.frame.system.dto.login.userInfo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Created by linzhihua
 * @Description: 角色信息
 * @ClassName RoleDto
 * @Date 2020/6/15 19:09
 */
public class RoleDto {
    @ApiModelProperty("权限串")
    private String rights;
    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("增加角色")
    private String addQx;
    @ApiModelProperty("删除角色")
    private String delQx;
    @ApiModelProperty("编辑角色")
    private String editQx;
    @ApiModelProperty("查询角色")
    private String chaQx;

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
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

    public String getAddQx() {
        return addQx;
    }

    public void setAddQx(String addQx) {
        this.addQx = addQx;
    }

    public String getDelQx() {
        return delQx;
    }

    public void setDelQx(String delQx) {
        this.delQx = delQx;
    }

    public String getEditQx() {
        return editQx;
    }

    public void setEditQx(String editQx) {
        this.editQx = editQx;
    }

    public String getChaQx() {
        return chaQx;
    }

    public void setChaQx(String chaQx) {
        this.chaQx = chaQx;
    }
}
