package com.plan.demo.order.dao;

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
    List<ValueObject> findNowOrderList(ValueObject valueObject);
}
