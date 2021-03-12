package com.plan.demo.base.dao;

import com.plan.demo.base.entity.TbDriver;
import com.plan.frame.mybatis.MyBatisPrimaryDao;

import java.util.List;

@MyBatisPrimaryDao
public interface TbDriverDao {
    void insert(TbDriver entity);

    void insertBatch(List<TbDriver> entities);

    void insertSelective(TbDriver entity);

    int deleteById(String id);

    int update(TbDriver entity);

    int updateSelective(TbDriver entity);

    TbDriver selectById(String id);
}