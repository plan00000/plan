package com.plan.demo.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ljw on 2021/4/14 0014.
 */
@Getter
@Setter
public class ResContactPersonDto {
    @ApiModelProperty("是否已添加紧急联系人:0-未添加，1-已添加")
    private String hasFlag;
    @ApiModelProperty("紧急联系人")
    private String contactName;
    @ApiModelProperty("紧急联系人电话")
    private String contactPhone;
}
