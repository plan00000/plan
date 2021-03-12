package com.plan.demo.base.dao;

import com.plan.demo.base.entity.TbPassenger;
import com.plan.frame.mybatis.MyBatisPrimaryDao;

import java.util.List;

@MyBatisPrimaryDao
public interface TbPassengerDao {
    void insert(TbPassenger entity);

    void insertBatch(List<TbPassenger> entities);

    void insertSelective(TbPassenger entity);

    int deleteById(String id);

    int update(TbPassenger entity);

    int updateSelective(TbPassenger entity);

    TbPassenger selectById(String id);
}