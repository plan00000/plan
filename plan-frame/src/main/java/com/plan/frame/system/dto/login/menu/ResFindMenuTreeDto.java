package com.plan.frame.system.dto.login.menu;

import com.plan.frame.system.dto.login.ButtonDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * Created by xieyanling on 2020/6/5.
 */
@ApiModel(description = "获取菜单树-应答对象")
public class ResFindMenuTreeDto{

    @ApiModelProperty("菜单信息列表")
    private List<MenuDto> menuDtoList;

    @ApiModelProperty("按钮信息列表")
    private List<ButtonDto> buttonDtoList;


    public List<MenuDto> getMenuDtoList() {
        return menuDtoList;
    }

    public void setMenuDtoList(List<MenuDto> menuDtoList) {
        this.menuDtoList = menuDtoList;
    }

    public List<ButtonDto> getButtonDtoList() {

        return buttonDtoList;
    }

    public void setButtonDtoList(List<ButtonDto> buttonDtoList) {
        this.buttonDtoList = buttonDtoList;
    }
}
