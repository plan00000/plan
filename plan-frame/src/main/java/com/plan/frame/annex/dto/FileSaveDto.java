package com.plan.frame.annex.dto;


import io.swagger.annotations.ApiModelProperty;

/**
 * @Created by linzhihua
 * @Description 附件保存
 * @ClassName ReqFileUploadDto
 * @Date 2019/7/5 10:18
 */
public class FileSaveDto {
    /**
     * 附件主题
     */
    @ApiModelProperty(value = "附件主题")
    private String fileTitle;
    /**
     * 关联id
     */
    @ApiModelProperty(value = "关联id")
    private String relId;
    /**
     * 附件名称
     */
    @ApiModelProperty(value = "附件名称")
    private String annexName;
    /**
     * 附件编号
     */
    @ApiModelProperty(value = "附件编号")
    private String annexNo;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer orderNo;
    /**
     * 附件路径
     */
    @ApiModelProperty(value = "附件路径")
    private String annexDir;
    @ApiModelProperty(value="返回附件主键Id标志位")
    private String backAnnexIdFlag;

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getAnnexName() {
        return annexName;
    }

    public void setAnnexName(String annexName) {
        this.annexName = annexName;
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

    public String getAnnexDir() {
        return annexDir;
    }

    public void setAnnexDir(String annexDir) {
        this.annexDir = annexDir;
    }

    public String getBackAnnexIdFlag() {
        return backAnnexIdFlag;
    }

    public void setBackAnnexIdFlag(String backAnnexIdFlag) {
        this.backAnnexIdFlag = backAnnexIdFlag;
    }
}
