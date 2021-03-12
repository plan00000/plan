package com.plan.demo.base.dao;

import com.plan.demo.base.entity.TbBespeak;
import com.plan.frame.mybatis.MyBatisPrimaryDao;

import java.util.List;

@MyBatisPrimaryDao
public interface TbBespeakDao {
    void insert(TbBespeak entity);

    void insertBatch(List<TbBespeak> entities);

    void insertSelective(TbBespeak entity);

    int deleteById(String id);

    int update(TbBespeak entity);

    int updateSelective(TbBespeak entity);

    TbBespeak selectById(String id);
}