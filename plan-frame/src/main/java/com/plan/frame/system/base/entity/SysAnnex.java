package com.plan.frame.system.base.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @table SYS_ANNEX - 附件表
 * @time 2020-06-04 15:29:19
 */
public class SysAnnex {
    /**
     * 主键ID，对应表字段为：SYS_ANNEX.ANNEX_ID
     */
    @ApiModelProperty("主键ID")
    private String annexId;

    /**
     * 附件名称，对应表字段为：SYS_ANNEX.ANNEX_NAME
     */
    @ApiModelProperty("附件名称")
    private String annexName;

    /**
     * 附件类型，对应表字段为：SYS_ANNEX.ANNEX_TYPE
     */
    @ApiModelProperty("附件类型")
    private String annexType;

    /**
     * 附件地址，对应表字段为：SYS_ANNEX.ANNEX_DIR
     */
    @ApiModelProperty("附件地址")
    private String annexDir;

    /**
     * 关联ID，对应表字段为：SYS_ANNEX.REL_ID
     */
    @ApiModelProperty("关联ID")
    private String relId;

    /**
     * 附件编号，对应表字段为：SYS_ANNEX.ANNEX_NO
     */
    @ApiModelProperty("附件编号")
    private String annexNo;

    /**
     * 附件排序，对应表字段为：SYS_ANNEX.ORDER_NO
     */
    @ApiModelProperty("附件排序")
    private Integer orderNo;

    /**
     * 创建人，对应表字段为：SYS_ANNEX.CREATE_USER
     */
    @ApiModelProperty("创建人")
    private String createUser;

    /**
     * 更新人，对应表字段为：SYS_ANNEX.UPDATE_USER
     */
    @ApiModelProperty("更新人")
    private String updateUser;

    /**
     * 创建时间，对应表字段为：SYS_ANNEX.CREATE_TIME
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间，对应表字段为：SYS_ANNEX.UPDATE_TIME
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 附件主题，见字典表附件主题FILE_TITLE，对应表字段为：SYS_ANNEX.FILE_TITLE
     */
    @ApiModelProperty("附件主题，见字典表附件主题FILE_TITLE")
    private String fileTitle;

    public String getAnnexId() {
        return annexId;
    }

    public void setAnnexId(String annexId) {
        this.annexId = annexId;
    }

    public String getAnnexName() {
        return annexName;
    }

    public void setAnnexName(String annexName) {
        this.annexName = annexName;
    }

    public String getAnnexType() {
        return annexType;
    }

    public void setAnnexType(String annexType) {
        this.annexType = annexType;
    }

    public String getAnnexDir() {
        return annexDir;
    }

    public void setAnnexDir(String annexDir) {
        this.annexDir = annexDir;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getAnnexNo() {
        return annexNo;
    }

    public void setAnnexNo(String annexNo) {
        this.annexNo = annexNo;
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