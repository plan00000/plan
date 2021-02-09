package com.plan.frame.system.base.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @table SYS_REQUEST_LOG - 系统操作业务日志
 * @time 2020-07-24 08:45:32
 */
public class SysRequestLog {
    /**
     * 主键，对应表字段为：SYS_REQUEST_LOG.LOG_ID
     */
    @ApiModelProperty("主键")
    private String logId;

    /**
     * 路径，对应表字段为：SYS_REQUEST_LOG.URI
     */
    @ApiModelProperty("路径")
    private String uri;

    /**
     * 请求ip，对应表字段为：SYS_REQUEST_LOG.REP_IP
     */
    @ApiModelProperty("请求ip")
    private String repIp;

    /**
     * 请求参数，对应表字段为：SYS_REQUEST_LOG.REQ_PARAMS
     */
    @ApiModelProperty("请求参数")
    private String reqParams;

    /**
     * 请求方法，对应表字段为：SYS_REQUEST_LOG.REQ_METHOD
     */
    @ApiModelProperty("请求方法")
    private String reqMethod;

    /**
     * 耗时，对应表字段为：SYS_REQUEST_LOG.LOST_TIME
     */
    @ApiModelProperty("耗时")
    private String lostTime;

    /**
     * 浏览器，对应表字段为：SYS_REQUEST_LOG.BROWSER
     */
    @ApiModelProperty("浏览器")
    private String browser;

    /**
     * 操作系统，对应表字段为：SYS_REQUEST_LOG.OSNAME
     */
    @ApiModelProperty("操作系统")
    private String osname;

    /**
     * 创建时间，对应表字段为：SYS_REQUEST_LOG.CREATE_TIME
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 创建人，对应表字段为：SYS_REQUEST_LOG.CREATE_USER
     */
    @ApiModelProperty("创建人")
    private String createUser;

    /**
     * 更新时间，对应表字段为：SYS_REQUEST_LOG.UPDATE_TIME
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 更新人，对应表字段为：SYS_REQUEST_LOG.UPDATE_USER
     */
    @ApiModelProperty("更新人")
    private String updateUser;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRepIp() {
        return repIp;
    }

    public void setRepIp(String repIp) {
        this.repIp = repIp;
    }

    public String getReqParams() {
        return reqParams;
    }

    public void setReqParams(String reqParams) {
        this.reqParams = reqParams;
    }

    public String getReqMethod() {
        return reqMethod;
    }

    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }

    public String getLostTime() {
        return lostTime;
    }

    public void setLostTime(String lostTime) {
        this.lostTime = lostTime;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOsname() {
        return osname;
    }

    public void setOsname(String osname) {
        this.osname = osname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}