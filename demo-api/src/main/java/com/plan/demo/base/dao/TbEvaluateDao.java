package com.plan.demo.base.dao;

import com.plan.demo.base.entity.TbEvaluate;
import com.plan.frame.mybatis.MyBatisPrimaryDao;
import java.util.List;

@MyBatisPrimaryDao
public interface TbEvaluateDao {
    void insert(TbEvaluate entity);

    void insertBatch(List<TbEvaluate> entities);

    void insertSelective(TbEvaluate entity);

    int update(TbEvaluate entity);

    int updateSelective(TbEvaluate entity);

    int deleteByPrimaryKey(String id);

    TbEvaluate selectByPrimaryKey(String id);

    List<TbEvaluate> selectByEntitySelective(TbEvaluate entity);
}