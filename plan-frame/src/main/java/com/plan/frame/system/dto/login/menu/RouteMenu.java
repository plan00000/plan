package com.plan.frame.system.dto.login.menu;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Created by linzhihua
 * @Description：路由菜单
 * @ClassName RouteMenu
 * @Date 2020/7/1 15:59
 */
public class RouteMenu {
    @ApiModelProperty("菜单ID")
    private String menuId;

    @ApiModelProperty("菜单编号")
    private String name;

    @ApiModelProperty("菜单URL")
    private String path;

    @ApiModelProperty("菜单序号")
    private String menuOrder;

    @ApiModelProperty("父菜单ID")
    private String parentId;

    @ApiModelProperty("组件名称")
    private String moduleName;

    @ApiModelProperty("组件路径")
    private String component;
    @ApiModelProperty("meta")
    private Meta meta;

    @ApiModelProperty("子菜单列表")
    private List<RouteMenu> children;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(String menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public List<RouteMenu> getChildren() {
        return children;
    }

    public void setChildren(List<RouteMenu> children) {
        this.children = children;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
