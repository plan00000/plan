package com.plan.demo.base.dao;

import com.plan.demo.base.entity.TbEvaluate;
import com.plan.frame.mybatis.MyBatisPrimaryDao;

import java.util.List;

@MyBatisPrimaryDao
public interface TbEvaluateDao {
    void insert(TbEvaluate entity);

    void insertBatch(List<TbEvaluate> entities);

    void insertSelective(TbEvaluate entity);

    int deleteById(String id);

    int update(TbEvaluate entity);

    int updateSelective(TbEvaluate entity);

    TbEvaluate selectById(String id);
}