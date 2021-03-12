package com.plan.demo.base.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @table tb_bespeak - 
 * @time 2021-03-12 15:29:32
 */
public class TbBespeak {
    /**
     * 主键id，对应表字段为：tb_bespeak.id
     */
    @ApiModelProperty("主键id")
    private String id;

    /**
     * 预约编号，对应表字段为：tb_bespeak.bespeak_no
     */
    @ApiModelProperty("预约编号")
    private String bespeakNo;

    /**
     * 预约起点，对应表字段为：tb_bespeak.bespeak_start_location
     */
    @ApiModelProperty("预约起点")
    private String bespeakStartLocation;

    /**
     * 预约起点，对应表字段为：tb_bespeak.bespeak_end_location
     */
    @ApiModelProperty("预约起点")
    private String bespeakEndLocation;

    /**
     * 预约起点经度，对应表字段为：tb_bespeak.bespeak_start_lon
     */
    @ApiModelProperty("预约起点经度")
    private String bespeakStartLon;

    /**
     * 预约起点纬度，对应表字段为：tb_bespeak.bespeak_start_lat
     */
    @ApiModelProperty("预约起点纬度")
    private String bespeakStartLat;

    /**
     * 预约结束点经度，对应表字段为：tb_bespeak.bespeak_end_lon
     */
    @ApiModelProperty("预约结束点经度")
    private String bespeakEndLon;

    /**
     * 预约结束纬度，对应表字段为：tb_bespeak.bespeak_end_lat
     */
    @ApiModelProperty("预约结束纬度")
    private String bespeakEndLat;

    /**
     * 下单时间，对应表字段为：tb_bespeak.order_time
     */
    @ApiModelProperty("下单时间")
    private Date orderTime;

    /**
     * 创建时间，对应表字段为：tb_bespeak.create_time
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间，对应表字段为：tb_bespeak.update_time
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBespeakNo() {
        return bespeakNo;
    }

    public void setBespeakNo(String bespeakNo) {
        this.bespeakNo = bespeakNo;
    }

    public String getBespeakStartLocation() {
        return bespeakStartLocation;
    }

    public void setBespeakStartLocation(String bespeakStartLocation) {
        this.bespeakStartLocation = bespeakStartLocation;
    }

    public String getBespeakEndLocation() {
        return bespeakEndLocation;
    }

    public void setBespeakEndLocation(String bespeakEndLocation) {
        this.bespeakEndLocation = bespeakEndLocation;
    }

    public String getBespeakStartLon() {
        return bespeakStartLon;
    }

    public void setBespeakStartLon(String bespeakStartLon) {
        this.bespeakStartLon = bespeakStartLon;
    }

    public String getBespeakStartLat() {
        return bespeakStartLat;
    }

    public void setBespeakStartLat(String bespeakStartLat) {
        this.bespeakStartLat = bespeakStartLat;
    }

    public String getBespeakEndLon() {
        return bespeakEndLon;
    }

    public void setBespeakEndLon(String bespeakEndLon) {
        this.bespeakEndLon = bespeakEndLon;
    }

    public String getBespeakEndLat() {
        return bespeakEndLat;
    }

    public void setBespeakEndLat(String bespeakEndLat) {
        this.bespeakEndLat = bespeakEndLat;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
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