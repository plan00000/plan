package com.plan.frame.system.dto.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by xieyanling on 2020/6/5.
 */
@Setter
@Getter
@ApiModel(description = "按钮信息对象")
public class ButtonDto {
    @ApiModelProperty("按钮ID")
    private String buttonId;

    @ApiModelProperty("按钮备注")
    private String buttonRemark;

    @ApiModelProperty("按钮编号")
    private String buttonNo;

    @ApiModelProperty("按钮序号")
    private String buttonOrder;


    @ApiModelProperty("菜单ID")
    private String menuId;

    @ApiModelProperty("按钮名称")
    private String buttonName;
}
