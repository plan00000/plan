package com.plan.frame.system.base.dao;

import com.plan.frame.mybatis.MyBatisTwoDao;
import com.plan.frame.system.base.entity.SysQuartzLog;

import java.util.List;

@MyBatisTwoDao
public interface SysQuartzLogDao {
    void insert(SysQuartzLog entity);

    void insertBatch(List<SysQuartzLog> entities);

    void insertSelective(SysQuartzLog entity);

    int update(SysQuartzLog entity);

    int updateSelective(SysQuartzLog entity);

    int deleteByPrimaryKey(String id);

    SysQuartzLog selectByPrimaryKey(String id);

    List<SysQuartzLog> selectByEntitySelective(SysQuartzLog entity);
}