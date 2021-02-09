package com.plan.frame.exception.record.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class SysException {
    /**
     *  主键ID,所属表字段为SYS_EXCEPTION.EXCEPTION_ID
     *
     * @mbggenerated
     */
    @ApiModelProperty("主键ID")
    private String exceptionId;

    /**
     *  异常信息,所属表字段为SYS_EXCEPTION.EXCEPTION_MSG
     *
     * @mbggenerated
     */
    @ApiModelProperty("异常信息")
    private String exceptionMsg;

    /**
     *  调用路径,所属表字段为SYS_EXCEPTION.EXCEPTION_URI
     *
     * @mbggenerated
     */
    @ApiModelProperty("调用路径")
    private String exceptionUri;

    /**
     *  关联案件id,所属表字段为SYS_EXCEPTION.TRANSACTION_ID
     *
     * @mbggenerated
     */
    @ApiModelProperty("关联案件id")
    private String transactionId;

    /**
     *  创建时间,所属表字段为SYS_EXCEPTION.CREATE_TIME
     *
     * @mbggenerated
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     *  更新时间,所属表字段为SYS_EXCEPTION.UPDATE_TIME
     *
     * @mbggenerated
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     *  创建用户,所属表字段为SYS_EXCEPTION.CREATE_USER
     *
     * @mbggenerated
     */
    @ApiModelProperty("创建用户")
    private String createUser;

    /**
     *  更新用户,所属表字段为SYS_EXCEPTION.UPDATE_USER
     *
     * @mbggenerated
     */
    @ApiModelProperty("更新用户")
    private String updateUser;

    /**
     *  异常堆栈信息,所属表字段为SYS_EXCEPTION.EXCEPTION_INFO
     *
     * @mbggenerated
     */
    @ApiModelProperty("异常堆栈信息")
    private String exceptionInfo;

    /**
     * 获取 主键ID 字段:SYS_EXCEPTION.EXCEPTION_ID
     *
     * @return SYS_EXCEPTION.EXCEPTION_ID, 主键ID
     *
     * @mbggenerated
     */
    @ApiModelProperty("主键ID")
    public String getExceptionId() {
        return exceptionId;
    }

    /**
     * 设置 主键ID 字段:SYS_EXCEPTION.EXCEPTION_ID
     *
     * @param exceptionId SYS_EXCEPTION.EXCEPTION_ID, 主键ID
     *
     * @mbggenerated
     */
    @ApiModelProperty("主键ID")
    public void setExceptionId(String exceptionId) {
        this.exceptionId = exceptionId == null ? null : exceptionId.trim();
    }

    /**
     * 获取 异常信息 字段:SYS_EXCEPTION.EXCEPTION_MSG
     *
     * @return SYS_EXCEPTION.EXCEPTION_MSG, 异常信息
     *
     * @mbggenerated
     */
    @ApiModelProperty("异常信息")
    public String getExceptionMsg() {
        return exceptionMsg;
    }

    /**
     * 设置 异常信息 字段:SYS_EXCEPTION.EXCEPTION_MSG
     *
     * @param exceptionMsg SYS_EXCEPTION.EXCEPTION_MSG, 异常信息
     *
     * @mbggenerated
     */
    @ApiModelProperty("异常信息")
    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg == null ? null : exceptionMsg.trim();
    }

    /**
     * 获取 调用路径 字段:SYS_EXCEPTION.EXCEPTION_URI
     *
     * @return SYS_EXCEPTION.EXCEPTION_URI, 调用路径
     *
     * @mbggenerated
     */
    @ApiModelProperty("调用路径")
    public String getExceptionUri() {
        return exceptionUri;
    }

    /**
     * 设置 调用路径 字段:SYS_EXCEPTION.EXCEPTION_URI
     *
     * @param exceptionUri SYS_EXCEPTION.EXCEPTION_URI, 调用路径
     *
     * @mbggenerated
     */
    @ApiModelProperty("调用路径")
    public void setExceptionUri(String exceptionUri) {
        this.exceptionUri = exceptionUri == null ? null : exceptionUri.trim();
    }

    /**
     * 获取 关联案件id 字段:SYS_EXCEPTION.TRANSACTION_ID
     *
     * @return SYS_EXCEPTION.TRANSACTION_ID, 关联案件id
     *
     * @mbggenerated
     */
    @ApiModelProperty("关联案件id")
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * 设置 关联案件id 字段:SYS_EXCEPTION.TRANSACTION_ID
     *
     * @param transactionId SYS_EXCEPTION.TRANSACTION_ID, 关联案件id
     *
     * @mbggenerated
     */
    @ApiModelProperty("关联案件id")
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    /**
     * 获取 创建时间 字段:SYS_EXCEPTION.CREATE_TIME
     *
     * @return SYS_EXCEPTION.CREATE_TIME, 创建时间
     *
     * @mbggenerated
     */
    @ApiModelProperty("创建时间")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置 创建时间 字段:SYS_EXCEPTION.CREATE_TIME
     *
     * @param createTime SYS_EXCEPTION.CREATE_TIME, 创建时间
     *
     * @mbggenerated
     */
    @ApiModelProperty("创建时间")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 更新时间 字段:SYS_EXCEPTION.UPDATE_TIME
     *
     * @return SYS_EXCEPTION.UPDATE_TIME, 更新时间
     *
     * @mbggenerated
     */
    @ApiModelProperty("更新时间")
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置 更新时间 字段:SYS_EXCEPTION.UPDATE_TIME
     *
     * @param updateTime SYS_EXCEPTION.UPDATE_TIME, 更新时间
     *
     * @mbggenerated
     */
    @ApiModelProperty("更新时间")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 创建用户 字段:SYS_EXCEPTION.CREATE_USER
     *
     * @return SYS_EXCEPTION.CREATE_USER, 创建用户
     *
     * @mbggenerated
     */
    @ApiModelProperty("创建用户")
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置 创建用户 字段:SYS_EXCEPTION.CREATE_USER
     *
     * @param createUser SYS_EXCEPTION.CREATE_USER, 创建用户
     *
     * @mbggenerated
     */
    @ApiModelProperty("创建用户")
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 获取 更新用户 字段:SYS_EXCEPTION.UPDATE_USER
     *
     * @return SYS_EXCEPTION.UPDATE_USER, 更新用户
     *
     * @mbggenerated
     */
    @ApiModelProperty("更新用户")
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置 更新用户 字段:SYS_EXCEPTION.UPDATE_USER
     *
     * @param updateUser SYS_EXCEPTION.UPDATE_USER, 更新用户
     *
     * @mbggenerated
     */
    @ApiModelProperty("更新用户")
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * 获取 异常堆栈信息 字段:SYS_EXCEPTION.EXCEPTION_INFO
     *
     * @return SYS_EXCEPTION.EXCEPTION_INFO, 异常堆栈信息
     *
     * @mbggenerated
     */
    @ApiModelProperty("异常堆栈信息")
    public String getExceptionInfo() {
        return exceptionInfo;
    }

    /**
     * 设置 异常堆栈信息 字段:SYS_EXCEPTION.EXCEPTION_INFO
     *
     * @param exceptionInfo SYS_EXCEPTION.EXCEPTION_INFO, 异常堆栈信息
     *
     * @mbggenerated
     */
    @ApiModelProperty("异常堆栈信息")
    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo == null ? null : exceptionInfo.trim();
    }
}