package com.plan.demo.order.dao;

import com.plan.demo.order.dto.ReqOrderDto;
import com.plan.demo.order.dto.ReqOrderRealTypeDto;
import com.plan.demo.user.dto.ReqDriverDto;
import com.plan.demo.user.dto.ReqPassengerDto;
import com.plan.frame.entity.ValueObject;
import com.plan.frame.mybatis.MyBatisPrimaryDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator 2021/3/15 0015
 * @version V1.0.0
 * @description 订单dao
 */
@MyBatisPrimaryDao
public interface OrderMapper {
    List<ValueObject> findNowOrderList(ReqOrderRealTypeDto reqOrderRealTypeDto);
    List<ValueObject> findLineList();
    List<ValueObject> findHotListList();
    Long findMaxId();
    List<ValueObject> findFreeDriverList();
    long findTbEvaluateMaxId();
    //获取当前司机订单列表
    List<ValueObject> getNowDriverOrderList(ReqDriverDto reqDriverDto);
    //获取当前乘客订单列表
    List<ValueObject> getNowPassengerOrderList(ReqPassengerDto reqPassengerDto);
}
