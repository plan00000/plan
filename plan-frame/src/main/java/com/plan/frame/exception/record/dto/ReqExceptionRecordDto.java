package com.plan.frame.exception.record.dto;

import com.plan.frame.entity.Pageination;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName ReqExceptionRecordDto
 * @Date 2020/4/3 9:18
 */
@ApiModel("异常列表查询")
public class ReqExceptionRecordDto {
    @ApiModelProperty("主键ID")
    private String exceptionId;

    @ApiModelProperty("调用路径")
    private String exceptionUri;

    @ApiModelProperty("查询开始时间")
    private String createTimeStart;

    @ApiModelProperty("查询结束时间")
    private String createTimeEnd;

    @ApiModelProperty("关联案件id")
    private String transactionId;

    /**
     * 分页器
     */
    @ApiModelProperty("分页器")
    private Pageination pageination;

    public String getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(String exceptionId) {
        this.exceptionId = exceptionId;
    }


    public String getExceptionUri() {
        return exceptionUri;
    }

    public void setExceptionUri(String exceptionUri) {
        this.exceptionUri = exceptionUri;
    }

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Pageination getPageination() {
        return pageination;
    }

    public void setPageination(Pageination pageination) {
        this.pageination = pageination;
    }
}
