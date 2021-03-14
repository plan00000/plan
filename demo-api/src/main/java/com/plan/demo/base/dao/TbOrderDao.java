package com.plan.demo.base.dao;

import com.plan.demo.base.entity.TbOrder;
import com.plan.frame.mybatis.MyBatisPrimaryDao;
import java.util.List;

@MyBatisPrimaryDao
public interface TbOrderDao {
    void insert(TbOrder entity);

    void insertBatch(List<TbOrder> entities);

    void insertSelective(TbOrder entity);

    int update(TbOrder entity);

    int updateSelective(TbOrder entity);

    int deleteByPrimaryKey(String id);

    TbOrder selectByPrimaryKey(String id);

    List<TbOrder> selectByEntitySelective(TbOrder entity);
}