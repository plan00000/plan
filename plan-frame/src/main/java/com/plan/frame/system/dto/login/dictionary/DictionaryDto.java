package com.plan.frame.system.dto.login.dictionary;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName DictionDto
 * @Date 2020/6/15 14:30
 */
public class DictionaryDto {
    @ApiModelProperty(value = "字典id")
    private String zdId;
    @ApiModelProperty(value = "级别")
    private String jb;
    @ApiModelProperty(value = "字典编码")
    private String bianMa;
    @ApiModelProperty(value = "字典名称")
    private String name;
    @ApiModelProperty(value = "排序")
    private String orderBy;
    @ApiModelProperty(value = "父字典id")
    private String parentId;
    @ApiModelProperty(value = "父字典与子字典合并编码")
    private String pBm;
    @ApiModelProperty(value = "创建时间")
    private String sysCreateTime;
    @ApiModelProperty(value = "更新时间")
    private String sysUpdateTime;
    @ApiModelProperty(value = "字典列表")
    private List<DictionaryDto> subDic;

    public String getZdId() {
        return zdId;
    }

    public void setZdId(String zdId) {
        this.zdId = zdId;
    }

    public String getJb() {
        return jb;
    }

    public void setJb(String jb) {
        this.jb = jb;
    }

    public String getBianMa() {
        return bianMa;
    }

    public void setBianMa(String bianMa) {
        this.bianMa = bianMa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getpBm() {
        return pBm;
    }

    public void setpBm(String pBm) {
        this.pBm = pBm;
    }

    public String getSysCreateTime() {
        return sysCreateTime;
    }

    public void setSysCreateTime(String sysCreateTime) {
        this.sysCreateTime = sysCreateTime;
    }

    public String getSysUpdateTime() {
        return sysUpdateTime;
    }

    public void setSysUpdateTime(String sysUpdateTime) {
        this.sysUpdateTime = sysUpdateTime;
    }

    public List<DictionaryDto> getSubDic() {
        return subDic;
    }

    public void setSubDic(List<DictionaryDto> subDic) {
        this.subDic = subDic;
    }
}
