package com.plan.frame.system.base.dao;

import com.plan.frame.mybatis.MyBatisTwoDao;
import com.plan.frame.system.base.entity.SysRequestLog;

import java.util.List;

@MyBatisTwoDao
public interface SysRequestLogDao {
    void insert(SysRequestLog entity);

    void insertBatch(List<SysRequestLog> entities);

    void insertSelective(SysRequestLog entity);

    int update(SysRequestLog entity);

    int updateSelective(SysRequestLog entity);

    int deleteByPrimaryKey(String logId);

    SysRequestLog selectByPrimaryKey(String logId);

    List<SysRequestLog> selectByEntitySelective(SysRequestLog entity);
}