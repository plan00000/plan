package com.plan.demo.base.dao;

import com.plan.demo.base.entity.TbPassenger;
import com.plan.frame.mybatis.MyBatisPrimaryDao;
import java.util.List;

@MyBatisPrimaryDao
public interface TbPassengerDao {
    void insert(TbPassenger entity);

    void insertBatch(List<TbPassenger> entities);

    void insertSelective(TbPassenger entity);

    int update(TbPassenger entity);

    int updateSelective(TbPassenger entity);

    int deleteByPrimaryKey(Long id);

    TbPassenger selectByPrimaryKey(Long id);

    List<TbPassenger> selectByEntitySelective(TbPassenger entity);
}