package com.plan.frame.system.base.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @table SYS_QUARTZ_JOB - 调度任务
 * @time 2020-06-04 15:49:29
 */
public class SysQuartzJob {
    /**
     * null，对应表字段为：SYS_QUARTZ_JOB.ID
     */
    @ApiModelProperty("null")
    private String id;

    /**
     * Spring Bean名称，对应表字段为：SYS_QUARTZ_JOB.BEAN_NAME
     */
    @ApiModelProperty("Spring Bean名称")
    private String beanName;

    /**
     * cron 表达式，对应表字段为：SYS_QUARTZ_JOB.CRON_EXPRESSION
     */
    @ApiModelProperty("cron 表达式")
    private String cronExpression;

    /**
     * 状态：1暂停、0启用，对应表字段为：SYS_QUARTZ_JOB.IS_PAUSE
     */
    @ApiModelProperty("状态：1暂停、0启用")
    private Byte isPause;

    /**
     * 任务名称，对应表字段为：SYS_QUARTZ_JOB.JOB_NAME
     */
    @ApiModelProperty("任务名称")
    private String jobName;

    /**
     * 方法名称，对应表字段为：SYS_QUARTZ_JOB.METHOD_NAME
     */
    @ApiModelProperty("方法名称")
    private String methodName;

    /**
     * 参数，对应表字段为：SYS_QUARTZ_JOB.PARAMS
     */
    @ApiModelProperty("参数")
    private String params;

    /**
     * 备注，对应表字段为：SYS_QUARTZ_JOB.REMARK
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 创建时间，对应表字段为：SYS_QUARTZ_JOB.CREATE_TIME
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 创建或更新日期，对应表字段为：SYS_QUARTZ_JOB.UPDATE_TIME
     */
    @ApiModelProperty("创建或更新日期")
    private Date updateTime;

    /**
     * 创建人，对应表字段为：SYS_QUARTZ_JOB.CREATE_USER
     */
    @ApiModelProperty("创建人")
    private String createUser;

    /**
     * 更新人，对应表字段为：SYS_QUARTZ_JOB.UPDATE_USER
     */
    @ApiModelProperty("更新人")
    private String updateUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Byte getIsPause() {
        return isPause;
    }

    public void setIsPause(Byte isPause) {
        this.isPause = isPause;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}