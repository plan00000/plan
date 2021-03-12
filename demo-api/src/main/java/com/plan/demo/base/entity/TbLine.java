package com.plan.demo.base.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @table tb_line - 
 * @time 2021-03-12 15:29:32
 */
public class TbLine {
    /**
     * 主键id，对应表字段为：tb_line.id
     */
    @ApiModelProperty("主键id")
    private String id;

    /**
     * 起点，对应表字段为：tb_line.start_address
     */
    @ApiModelProperty("起点")
    private String startAddress;

    /**
     * 终点，对应表字段为：tb_line.end_address
     */
    @ApiModelProperty("终点")
    private String endAddress;

    /**
     * 起点经度，对应表字段为：tb_line.start_lon
     */
    @ApiModelProperty("起点经度")
    private String startLon;

    /**
     * 起点纬度，对应表字段为：tb_line.start_lat
     */
    @ApiModelProperty("起点纬度")
    private String startLat;

    /**
     * 终点经度，对应表字段为：tb_line.end_lon
     */
    @ApiModelProperty("终点经度")
    private String endLon;

    /**
     * 终点纬度，对应表字段为：tb_line.end_lat
     */
    @ApiModelProperty("终点纬度")
    private String endLat;

    /**
     * 创建时间，对应表字段为：tb_line.create_time
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间，对应表字段为：tb_line.update_time
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getStartLon() {
        return startLon;
    }

    public void setStartLon(String startLon) {
        this.startLon = startLon;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getEndLon() {
        return endLon;
    }

    public void setEndLon(String endLon) {
        this.endLon = endLon;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
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
}