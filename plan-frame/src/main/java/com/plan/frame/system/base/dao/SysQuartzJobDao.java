package com.plan.frame.system.base.dao;

import com.plan.frame.mybatis.MyBatisTwoDao;
import com.plan.frame.system.base.entity.SysQuartzJob;

import java.util.List;

@MyBatisTwoDao
public interface SysQuartzJobDao {
    void insert(SysQuartzJob entity);

    void insertBatch(List<SysQuartzJob> entities);

    void insertSelective(SysQuartzJob entity);

    int update(SysQuartzJob entity);

    int updateSelective(SysQuartzJob entity);

    int deleteByPrimaryKey(String id);

    SysQuartzJob selectByPrimaryKey(String id);

    List<SysQuartzJob> selectByEntitySelective(SysQuartzJob entity);
}