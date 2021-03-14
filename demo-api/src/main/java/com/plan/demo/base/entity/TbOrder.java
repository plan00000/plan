package com.plan.demo.base.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @table tb_order - 
 * @time 2021-03-14 12:31:06
 */
public class TbOrder {
    /**
     * 订单表id，对应表字段为：tb_order.id
     */
    @ApiModelProperty("订单表id")
    private String id;

    /**
     * 下单时间，对应表字段为：tb_order.order_time
     */
    @ApiModelProperty("下单时间")
    private Short orderTime;

    /**
     * 订单号，对应表字段为：tb_order.order_no
     */
    @ApiModelProperty("订单号")
    private String orderNo;

    /**
     * 订单起点，对应表字段为：tb_order.order_start_address
     */
    @ApiModelProperty("订单起点")
    private String orderStartAddress;

    /**
     * 订单起点经度，对应表字段为：tb_order.order_start_lon
     */
    @ApiModelProperty("订单起点经度")
    private String orderStartLon;

    /**
     * 订单起点纬度，对应表字段为：tb_order.order_start_lat
     */
    @ApiModelProperty("订单起点纬度")
    private String orderStartLat;

    /**
     * 订单结束点，对应表字段为：tb_order.order_end_address
     */
    @ApiModelProperty("订单结束点")
    private String orderEndAddress;

    /**
     * 订单结束点经度，对应表字段为：tb_order.order_end_lon
     */
    @ApiModelProperty("订单结束点经度")
    private String orderEndLon;

    /**
     * 订单起点纬度，对应表字段为：tb_order.order_end_lat
     */
    @ApiModelProperty("订单起点纬度")
    private String orderEndLat;

    /**
     * 司机id，对应表字段为：tb_order.drive_id
     */
    @ApiModelProperty("司机id")
    private String driveId;

    /**
     * 订单类型:0-包车，1-拼车，对应表字段为：tb_order.order_type
     */
    @ApiModelProperty("订单类型:0-包车，1-拼车")
    private String orderType;

    /**
     * 订单种类：0-实时单，1-预约单，对应表字段为：tb_order.order_real_type
     */
    @ApiModelProperty("订单种类：0-实时单，1-预约单")
    private String orderRealType;

    /**
     * 订单状态:0-已取消，1-未开始，2-进行中，3-已完成，对应表字段为：tb_order.order_status
     */
    @ApiModelProperty("订单状态:0-已取消，1-未开始，2-进行中，3-已完成")
    private String orderStatus;

    /**
     * 关联用户id，对应表字段为：tb_order.user_id
     */
    @ApiModelProperty("关联用户id")
    private String userId;

    /**
     * 乘客人数，对应表字段为：tb_order.order_user_num
     */
    @ApiModelProperty("乘客人数")
    private String orderUserNum;

    /**
     * ，对应表字段为：tb_order.line_id
     */
    @ApiModelProperty("")
    private String lineId;

    /**
     * ，对应表字段为：tb_order.remark
     */
    @ApiModelProperty("")
    private String remark;

    /**
     * 取消原因，对应表字段为：tb_order.cancel_reason
     */
    @ApiModelProperty("取消原因")
    private String cancelReason;

    /**
     * 创建时间，对应表字段为：tb_order.create_time
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间，对应表字段为：tb_order.update_time
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Short getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Short orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStartAddress() {
        return orderStartAddress;
    }

    public void setOrderStartAddress(String orderStartAddress) {
        this.orderStartAddress = orderStartAddress;
    }

    public String getOrderStartLon() {
        return orderStartLon;
    }

    public void setOrderStartLon(String orderStartLon) {
        this.orderStartLon = orderStartLon;
    }

    public String getOrderStartLat() {
        return orderStartLat;
    }

    public void setOrderStartLat(String orderStartLat) {
        this.orderStartLat = orderStartLat;
    }

    public String getOrderEndAddress() {
        return orderEndAddress;
    }

    public void setOrderEndAddress(String orderEndAddress) {
        this.orderEndAddress = orderEndAddress;
    }

    public String getOrderEndLon() {
        return orderEndLon;
    }

    public void setOrderEndLon(String orderEndLon) {
        this.orderEndLon = orderEndLon;
    }

    public String getOrderEndLat() {
        return orderEndLat;
    }

    public void setOrderEndLat(String orderEndLat) {
        this.orderEndLat = orderEndLat;
    }

    public String getDriveId() {
        return driveId;
    }

    public void setDriveId(String driveId) {
        this.driveId = driveId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderRealType() {
        return orderRealType;
    }

    public void setOrderRealType(String orderRealType) {
        this.orderRealType = orderRealType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderUserNum() {
        return orderUserNum;
    }

    public void setOrderUserNum(String orderUserNum) {
        this.orderUserNum = orderUserNum;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
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