package com.plan.demo.base.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @table tb_evaluate - 
 * @time 2021-03-23 19:57:09
 */
public class TbEvaluate {
    /**
     * 主键id，对应表字段为：tb_evaluate.id
     */
    @ApiModelProperty("主键id")
    private Long id;

    /**
     * 用户id，对应表字段为：tb_evaluate.user_id
     */
    @ApiModelProperty("用户id")
    private String userId;

    /**
     * 司机id，对应表字段为：tb_evaluate.driver_id
     */
    @ApiModelProperty("司机id")
    private String driverId;

    /**
     * 订单id，对应表字段为：tb_evaluate.order_id
     */
    @ApiModelProperty("订单id")
    private String orderId;

    /**
     * 订单表id，对应表字段为：tb_evaluate.order_no
     */
    @ApiModelProperty("订单表id")
    private String orderNo;

    /**
     * 订单评价星级：几颗星最高5个星，对应表字段为：tb_evaluate.order_star
     */
    @ApiModelProperty("订单评价星级：几颗星最高5个星")
    private String orderStar;

    /**
     * 订单服务评价(111111-车内整洁，认路准，驾驶平稳，态度好，有礼貌，服务周到)，对应表字段为：tb_evaluate.order_service
     */
    @ApiModelProperty("订单服务评价(111111-车内整洁，认路准，驾驶平稳，态度好，有礼貌，服务周到)")
    private String orderService;

    /**
     * 订单评价，对应表字段为：tb_evaluate.order_evaluate
     */
    @ApiModelProperty("订单评价")
    private String orderEvaluate;

    /**
     * 创建时间，对应表字段为：tb_evaluate.create_time
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间，对应表字段为：tb_evaluate.update_time
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStar() {
        return orderStar;
    }

    public void setOrderStar(String orderStar) {
        this.orderStar = orderStar;
    }

    public String getOrderService() {
        return orderService;
    }

    public void setOrderService(String orderService) {
        this.orderService = orderService;
    }

    public String getOrderEvaluate() {
        return orderEvaluate;
    }

    public void setOrderEvaluate(String orderEvaluate) {
        this.orderEvaluate = orderEvaluate;
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