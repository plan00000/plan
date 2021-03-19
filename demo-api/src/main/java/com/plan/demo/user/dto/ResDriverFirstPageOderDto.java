package com.plan.demo.user.dto;

import com.plan.frame.entity.DictConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: ljw
 * @Description:司机首页返回dto
 * @Date: Created in 2021/3/19 16:21
 * @Modified By:
 */
@Getter
@Setter
@ApiModel(description = "司机首页返回dto")
public class ResDriverFirstPageOderDto {

    @ApiModelProperty("乘客id")
    private String orderId;

    @ApiModelProperty("订单类型:0-包车，1-拼车")
    private String orderType;

    @ApiModelProperty("订单种类：0-实时单，1-预约单")
    @DictConvert(dictType = "orderRealType")
    private String orderRealType;

    @ApiModelProperty("列表订单展示信息")
    private String showOrderInfo;



}
