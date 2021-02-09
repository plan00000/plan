package com.plan.frame.system.base.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @table SYS_OPERATE_LOG - 系统操作业务日志
 * @time 2020-07-23 18:00:49
 */
public class SysOperateLog {
    /**
     * 主键，对应表字段为：SYS_OPERATE_LOG.LOG_ID
     */
    @ApiModelProperty("主键")
    private String logId;

    /**
     * 操作IP，对应表字段为：SYS_OPERATE_LOG.OPERATE__IP
     */
    @ApiModelProperty("操作IP")
    private String operateIp;

    /**
     * 操作人，对应表字段为：SYS_OPERATE_LOG.OPERATOR
     */
    @ApiModelProperty("操作人")
    private String operator;

    /**
     * 操作时间，对应表字段为：SYS_OPERATE_LOG.OPERATE_TIME
     */
    @ApiModelProperty("操作时间")
    private Date operateTime;

    /**
     * 操作内容，对应表字段为：SYS_OPERATE_LOG.CONTENT
     */
    @ApiModelProperty("操作内容")
    private String content;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}