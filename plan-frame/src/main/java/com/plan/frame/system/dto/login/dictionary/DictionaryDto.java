package com.plan.frame.system.dto.login.dictionary;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Created by ljw
 * @Description
 * @ClassName DictionDto
 * @Date 2020/6/15 14:30
 */
@Getter
@Setter
public class DictionaryDto {
    @ApiModelProperty(value = "字典id")
    private String id;
    @ApiModelProperty(value = "字典编码")
    private String code;
    @ApiModelProperty(value = "字典名称")
    private String name;


}
