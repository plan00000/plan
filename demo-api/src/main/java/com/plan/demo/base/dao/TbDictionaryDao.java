package com.plan.demo.base.dao;

import com.plan.demo.base.entity.TbDictionary;
import com.plan.frame.mybatis.MyBatisPrimaryDao;
import java.util.List;

@MyBatisPrimaryDao
public interface TbDictionaryDao {
    void insert(TbDictionary entity);

    void insertBatch(List<TbDictionary> entities);

    void insertSelective(TbDictionary entity);

    int update(TbDictionary entity);

    int updateSelective(TbDictionary entity);

    int deleteByPrimaryKey(String id);

    TbDictionary selectByPrimaryKey(String id);

    List<TbDictionary> selectByEntitySelective(TbDictionary entity);
}