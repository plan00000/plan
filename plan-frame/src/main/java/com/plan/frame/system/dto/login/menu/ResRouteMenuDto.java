package com.plan.frame.system.dto.login.menu;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Created by linzhihua
 * @Description：前段获取路由菜单
 * @ClassName ResRouteMenuDto
 * @Date 2020/7/1 15:58
 */
public class ResRouteMenuDto {
    @ApiModelProperty("菜单信息列表")
    private List<RouteMenu> routeMenuList;

    public List<RouteMenu> getRouteMenuList() {
        return routeMenuList;
    }

    public void setRouteMenuList(List<RouteMenu> routeMenuList) {
        this.routeMenuList = routeMenuList;
    }
}
