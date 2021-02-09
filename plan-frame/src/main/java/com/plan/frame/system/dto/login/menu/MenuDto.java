package com.plan.frame.system.dto.login.menu;

import com.plan.frame.system.dto.login.ButtonDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by xieyanling on 2020/6/5.
 */
@ApiModel(description = "菜单信息对象")
public class MenuDto {
    @ApiModelProperty("菜单ID")
    private String menuId;

    @ApiModelProperty("菜单编号")
    private String menuCode;

    @ApiModelProperty("菜单URL")
    private String menuUrl;

    @ApiModelProperty("完整URL")
    private String fullUrl;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("菜单图标")
    private String menuIcon;

    @ApiModelProperty("菜单序号")
    private String menuOrder;

    @ApiModelProperty("父菜单ID")
    private String parentId;

    @ApiModelProperty("组件名称")
    private String moduleName;

    @ApiModelProperty("组件路径")
    private String moduleUrl;

    @ApiModelProperty("按钮列表")
    private List<ButtonDto> buttonList;

    @ApiModelProperty("子菜单列表")
    private List<MenuDto> subMenuList;



    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
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

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }

    public List<MenuDto> getSubMenuList() {
        return subMenuList;
    }

    public void setSubMenuList(List<MenuDto> subMenuList) {
        this.subMenuList = subMenuList;
    }

    public List<ButtonDto> getButtonList() {
        return buttonList;
    }

    public void setButtonList(List<ButtonDto> buttonList) {
        this.buttonList = buttonList;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }
}
