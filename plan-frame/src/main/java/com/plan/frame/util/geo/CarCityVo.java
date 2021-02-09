package com.plan.frame.util.geo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xieyanling
 * @date 2020/11/4
 */
@Getter
@Setter
public class CarCityVo {
    // 车牌前缀，如“闽D”
    private String code;

    // 归属地省份
    private String province;

    // 归属地城市
    private String city;

    // 归属地省份编号
    private String pcode;

}
