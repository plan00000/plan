package com.plan.frame.exception.record.dto;

import com.plan.frame.entity.Pageination;
import com.plan.frame.exception.record.entity.SysException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName RepExceptionRecordDto
 * @Date 2020/4/3 9:45
 */
@ApiModel("返回实体类")
public class ResExceptionRecordDto {
    @ApiModelProperty("异常信息")
    private List<SysException> sysExceptionList;
    @ApiModelProperty("分页器")
    private Pageination pageination;

    public List<SysException> getSysExceptionList() {
        return sysExceptionList;
    }

    public void setSysExceptionList(List<SysException> sysExceptionList) {
        this.sysExceptionList = sysExceptionList;
    }

    public Pageination getPageination() {
        return pageination;
    }

    public void setPageination(Pageination pageination) {
        this.pageination = pageination;
    }
}
