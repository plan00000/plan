package com.plan.demo.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2021/3/16 0016.
 */
@Setter
@Getter
@ApiModel(description = "线路信息dto")
public class ResOrderLineDto {
    @ApiModelProperty("线路id")
    private long lineId;
    @ApiModelProperty("线路信息")
    private String lineName;

    @ApiModelProperty("起点")
    private String startAddress;

    @ApiModelProperty("终点")
    private String endAddress;

    @ApiModelProperty("起点经度")
    private String startLon;

    @ApiModelProperty("起点纬度")
    private String startLat;

    @ApiModelProperty("终点经度")
    private String endLon;

    @ApiModelProperty("终点纬度")
    private String endLat;
}
