package com.plan.demo.base.dao;

import com.plan.demo.base.entity.TbDictionary;
import com.plan.frame.mybatis.MyBatisPrimaryDao;

import java.util.List;

@MyBatisPrimaryDao
public interface TbDictionaryDao {
    void insert(TbDictionary entity);

    void insertBatch(List<TbDictionary> entities);

    void insertSelective(TbDictionary entity);

    int deleteById(String id);

    int update(TbDictionary entity);

    int updateSelective(TbDictionary entity);

    TbDictionary selectById(String id);
}