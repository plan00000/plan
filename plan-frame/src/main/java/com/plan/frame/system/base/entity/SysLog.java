package com.plan.frame.system.base.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @table SYS_LOG - 日志表
 * @time 2020-07-03 17:43:20
 */
public class SysLog {
    /**
     * 日志ID，对应表字段为：SYS_LOG.LOG_ID
     */
    @ApiModelProperty("日志ID")
    private String logId;

    /**
     * 操作IP，对应表字段为：SYS_LOG.REQ_IP
     */
    @ApiModelProperty("操作IP")
    private String reqIp;

    /**
     * 操作人，对应表字段为：SYS_LOG.OPERATOR
     */
    @ApiModelProperty("操作人")
    private String operator;

    /**
     * 操作时间，对应表字段为：SYS_LOG.OPERATE_TIME
     */
    @ApiModelProperty("操作时间")
    private Date operateTime;

    /**
     * 操作类型，对应表字段为：SYS_LOG.OPERATE_TYPE
     */
    @ApiModelProperty("操作类型")
    private String operateType;

    /**
     * 日志标题，对应表字段为：SYS_LOG.LOG_TITLE
     */
    @ApiModelProperty("日志标题")
    private String logTitle;

    /**
     * 创建时间，对应表字段为：SYS_LOG.CREATE_TIME
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 创建人，对应表字段为：SYS_LOG.CREATE_USER
     */
    @ApiModelProperty("创建人")
    private String createUser;

    /**
     * 更新时间，对应表字段为：SYS_LOG.UPDATE_TIME
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 更新人，对应表字段为：SYS_LOG.UPDATE_USER
     */
    @ApiModelProperty("更新人")
    private String updateUser;

    /**
     * 日志类型(info,error)，对应表字段为：SYS_LOG.LOG_TYPE
     */
    @ApiModelProperty("日志类型(info,error)")
    private String logType;

    /**
     * 请求URL，对应表字段为：SYS_LOG.REQ_URL
     */
    @ApiModelProperty("请求URL")
    private String reqUrl;

    /**
     * 请求参数，对应表字段为：SYS_LOG.REQ_PARAMS
     */
    @ApiModelProperty("请求参数")
    private String reqParams;

    /**
     * 异常信息，对应表字段为：SYS_LOG.ERROR_MSG
     */
    @ApiModelProperty("异常信息")
    private String errorMsg;

    /**
     * 请求类型(POST,GET)，对应表字段为：SYS_LOG.REQ_METHOD
     */
    @ApiModelProperty("请求类型(POST,GET)")
    private String reqMethod;

    /**
     * 耗时（ms），对应表字段为：SYS_LOG.TIMEOUT
     */
    @ApiModelProperty("耗时（ms）")
    private Long timeout;

    /**
     * 浏览器，对应表字段为：SYS_LOG.BROWSER
     */
    @ApiModelProperty("浏览器")
    private String browser;

    /**
     * 操作系统，对应表字段为：SYS_LOG.OSNAME
     */
    @ApiModelProperty("操作系统")
    private String osname;

    /**
     * 用户是否可见，1-是，0-否，对应表字段为：SYS_LOG.VISIABLE
     */
    @ApiModelProperty("用户是否可见，1-是，0-否")
    private String visiable;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getReqIp() {
        return reqIp;
    }

    public void setReqIp(String reqIp) {
        this.reqIp = reqIp;
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

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getLogTitle() {
        return logTitle;
    }

    public void setLogTitle(String logTitle) {
        this.logTitle = logTitle;
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

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getReqParams() {
        return reqParams;
    }

    public void setReqParams(String reqParams) {
        this.reqParams = reqParams;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getReqMethod() {
        return reqMethod;
    }

    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
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

    public String getVisiable() {
        return visiable;
    }

    public void setVisiable(String visiable) {
        this.visiable = visiable;
    }
}