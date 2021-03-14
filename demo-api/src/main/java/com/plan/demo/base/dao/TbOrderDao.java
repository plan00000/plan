package com.plan.demo.base.dao;

import com.plan.demo.base.entity.TbOrder;
import com.xmgps.tocc.frame.mybatis.MyBatisPrimaryDao;
import java.util.List;

@MyBatisPrimaryDao
public interface TbOrderDao {
    void insert(TbOrder entity);

    void insertBatch(List<TbOrder> entities);

    void insertSelective(TbOrder entity);

    int deleteById(String id);

    int update(TbOrder entity);

    int updateSelective(TbOrder entity);

    TbOrder selectById(String id);
}