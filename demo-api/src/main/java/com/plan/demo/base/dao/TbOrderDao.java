package com.plan.demo.base.dao;

import com.plan.demo.base.entity.TbOrder;
import com.plan.frame.mybatis.MyBatisPrimaryDao;
import java.util.List;

@MyBatisPrimaryDao
public interface TbOrderDao {
    long insert(TbOrder entity);

    void insertBatch(List<TbOrder> entities);

    void insertSelective(TbOrder entity);

    int update(TbOrder entity);

    int updateSelective(TbOrder entity);

    int deleteByPrimaryKey(Long id);

    TbOrder selectByPrimaryKey(Long id);

    List<TbOrder> selectByEntitySelective(TbOrder entity);
}