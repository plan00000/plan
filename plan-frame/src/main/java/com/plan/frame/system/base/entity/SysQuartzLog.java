package com.plan.frame.system.base.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @table SYS_QUARTZ_LOG - 调度任务日志
 * @time 2020-06-04 16:01:45
 */
public class SysQuartzLog {
    /**
     * null，对应表字段为：SYS_QUARTZ_LOG.ID
     */
    @ApiModelProperty("null")
    private String id;

    /**
     * 任务ID,关联JOB表主键，对应表字段为：SYS_QUARTZ_LOG.JOB_ID
     */
    @ApiModelProperty("任务ID,关联JOB表主键")
    private String jobId;

    /**
     * Bean名称，对应表字段为：SYS_QUARTZ_LOG.BEAN_NAME
     */
    @ApiModelProperty("Bean名称")
    private String beanName;

    /**
     * cron表达式，对应表字段为：SYS_QUARTZ_LOG.CRON_EXPRESSION
     */
    @ApiModelProperty("cron表达式")
    private String cronExpression;

    /**
     * 异常详细，对应表字段为：SYS_QUARTZ_LOG.EXCEPTION_DETAIL
     */
    @ApiModelProperty("异常详细")
    private String exceptionDetail;

    /**
     * 是否成功，对应表字段为：SYS_QUARTZ_LOG.IS_SUCCESS
     */
    @ApiModelProperty("是否成功")
    private Byte isSuccess;

    /**
     * 任务名称，对应表字段为：SYS_QUARTZ_LOG.JOB_NAME
     */
    @ApiModelProperty("任务名称")
    private String jobName;

    /**
     * 方法名称，对应表字段为：SYS_QUARTZ_LOG.METHOD_NAME
     */
    @ApiModelProperty("方法名称")
    private String methodName;

    /**
     * 参数，对应表字段为：SYS_QUARTZ_LOG.PARAMS
     */
    @ApiModelProperty("参数")
    private String params;

    /**
     * 耗时（毫秒），对应表字段为：SYS_QUARTZ_LOG.TIME
     */
    @ApiModelProperty("耗时（毫秒）")
    private Long time;

    /**
     * 创建时间，对应表字段为：SYS_QUARTZ_LOG.CREATE_TIME
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间，对应表字段为：SYS_QUARTZ_LOG.UPDATE_TIME
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 创建人，对应表字段为：SYS_QUARTZ_LOG.CREATE_USER
     */
    @ApiModelProperty("创建人")
    private String createUser;

    /**
     * 更新人，对应表字段为：SYS_QUARTZ_LOG.UPDATE_USER
     */
    @ApiModelProperty("更新人")
    private String updateUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
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

    public String getExceptionDetail() {
        return exceptionDetail;
    }

    public void setExceptionDetail(String exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }

    public Byte getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Byte isSuccess) {
        this.isSuccess = isSuccess;
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
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