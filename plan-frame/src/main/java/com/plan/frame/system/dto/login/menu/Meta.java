package com.plan.frame.system.dto.login.menu;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lisongtai on 2020/7/1.
 */
public class Meta {
    @ApiModelProperty("菜单名称")
    private String title;

    @ApiModelProperty("菜单图标")
    private String icon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
