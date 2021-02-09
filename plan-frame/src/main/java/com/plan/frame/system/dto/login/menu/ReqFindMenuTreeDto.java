package com.plan.frame.system.dto.login.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Created by xieyanling
 * @Description
 * @ClassName
 * @Create in 2020/6/5 10:13
 */
@ApiModel(description = "获取菜单树-请求对象")
public class ReqFindMenuTreeDto {
    @ApiModelProperty(value = "菜单编号(空，则获取整个系统菜单）")
    private String menuCode;

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
}
