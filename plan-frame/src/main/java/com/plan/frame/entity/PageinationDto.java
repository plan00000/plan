package com.plan.frame.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 分页类
 * Created by xieyanling on 2020/4/14.
 */
public  class PageinationDto {
    /**
     * 分页器
     */
    @ApiModelProperty("分页器")
    private Pageination pageination;

    public Pageination getPageination() {
        return pageination;
    }

    public void setPageination(Pageination pageination) {
        this.pageination = pageination;
    }
}
