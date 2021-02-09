package com.plan.frame.system.base.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @table SYS_FILE_INFO - 附件表
 * @time 2020-07-24 15:52:13
 */
public class SysFileInfo {
    /**
     * 附件ID，对应表字段为：SYS_FILE_INFO.FILE_ID
     */
    @ApiModelProperty("附件ID")
    private String fileId;

    /**
     * 附件名称，对应表字段为：SYS_FILE_INFO.FILE_NAME
     */
    @ApiModelProperty("附件名称")
    private String fileName;

    /**
     * 附件类型，对应表字段为：SYS_FILE_INFO.FILE_TYPE
     */
    @ApiModelProperty("附件类型")
    private String fileType;

    /**
     * 附件地址，对应表字段为：SYS_FILE_INFO.FILE_DIR
     */
    @ApiModelProperty("附件地址")
    private String fileDir;

    /**
     * 关联ID，对应表字段为：SYS_FILE_INFO.REL_ID
     */
    @ApiModelProperty("关联ID")
    private String relId;

    /**
     * 附件编号，对应表字段为：SYS_FILE_INFO.FILE_NO
     */
    @ApiModelProperty("附件编号")
    private String fileNo;

    /**
     * 附件排序，对应表字段为：SYS_FILE_INFO.ORDER_NO
     */
    @ApiModelProperty("附件排序")
    private Integer orderNo;

    /**
     * 创建人，对应表字段为：SYS_FILE_INFO.CREATE_USER
     */
    @ApiModelProperty("创建人")
    private String createUser;

    /**
     * 更新人，对应表字段为：SYS_FILE_INFO.UPDATE_USER
     */
    @ApiModelProperty("更新人")
    private String updateUser;

    /**
     * 创建时间，对应表字段为：SYS_FILE_INFO.CREATE_TIME
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间，对应表字段为：SYS_FILE_INFO.UPDATE_TIME
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 附件主题，见字典表：安全管理附件主题AQGL_FILE_TITLE，对应表字段为：SYS_FILE_INFO.FILE_TITLE
     */
    @ApiModelProperty("附件主题，见字典表：安全管理附件主题AQGL_FILE_TITLE")
    private String fileTitle;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
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

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }
}