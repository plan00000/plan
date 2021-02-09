package com.plan.frame.system.dto.login.dept;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName ReqDeptDto
 * @Date 2020/6/19 16:06
 */
public class ReqDeptDto {
    @ApiModelProperty(value = "条件类型：1-根据父机构单位id查询子机构，2-根据父机构单位编号查询子机构")
    private String type;
    @ApiModelProperty(value = "条件内容")
    private String content;
    @ApiModelProperty(value = "是否递归查询：0-否，1-是")
    private String hasChildren;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(String hasChildren) {
        this.hasChildren = hasChildren;
    }
}
