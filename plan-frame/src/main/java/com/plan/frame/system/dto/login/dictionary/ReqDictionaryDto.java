package com.plan.frame.system.dto.login.dictionary;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName ReqDictionaryDto
 * @Date 2020/6/15 11:19
 */
public class ReqDictionaryDto {
    @ApiModelProperty(value = "字典编码", required=true)
    private String bianMa;
    @ApiModelProperty(value = "是否递归查询，0-否，1-是")
    private String hasChildren;

    public String getBianMa() {
        return bianMa;
    }

    public void setBianMa(String bianMa) {
        this.bianMa = bianMa;
    }

    public String getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(String hasChildren) {
        this.hasChildren = hasChildren;
    }
}
