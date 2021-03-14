package com.plan.demo.base.dao;

import com.plan.demo.base.entity.TbBespeak;
import com.plan.frame.mybatis.MyBatisPrimaryDao;
import java.util.List;

@MyBatisPrimaryDao
public interface TbBespeakDao {
    void insert(TbBespeak entity);

    void insertBatch(List<TbBespeak> entities);

    void insertSelective(TbBespeak entity);

    int update(TbBespeak entity);

    int updateSelective(TbBespeak entity);

    int deleteByPrimaryKey(String id);

    TbBespeak selectByPrimaryKey(String id);

    List<TbBespeak> selectByEntitySelective(TbBespeak entity);
}