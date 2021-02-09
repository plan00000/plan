package com.plan.frame.system.base.dao;

import com.plan.frame.mybatis.MyBatisTwoDao;
import com.plan.frame.system.base.entity.SysLog;

import java.util.List;

@MyBatisTwoDao
public interface SysLogDao {
    void insert(SysLog entity);

    void insertBatch(List<SysLog> entities);

    void insertSelective(SysLog entity);

    int update(SysLog entity);

    int updateSelective(SysLog entity);

    int deleteByPrimaryKey(String logId);

    SysLog selectByPrimaryKey(String logId);

    List<SysLog> selectByEntitySelective(SysLog entity);
}