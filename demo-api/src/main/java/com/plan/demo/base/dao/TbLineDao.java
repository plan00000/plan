package com.plan.demo.base.dao;

import com.plan.demo.base.entity.TbLine;
import com.plan.frame.mybatis.MyBatisPrimaryDao;
import java.util.List;

@MyBatisPrimaryDao
public interface TbLineDao {
    void insert(TbLine entity);

    void insertBatch(List<TbLine> entities);

    void insertSelective(TbLine entity);

    int update(TbLine entity);

    int updateSelective(TbLine entity);

    int deleteByPrimaryKey(Long id);

    TbLine selectByPrimaryKey(Long id);

    List<TbLine> selectByEntitySelective(TbLine entity);
}