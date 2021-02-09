package com.plan.frame.system.base.dao;

import com.plan.frame.mybatis.MyBatisTwoDao;
import com.plan.frame.system.base.entity.SysOperateLog;

import java.util.List;

@MyBatisTwoDao
public interface SysOperateLogDao {
    void insert(SysOperateLog entity);

    void insertBatch(List<SysOperateLog> entities);

    void insertSelective(SysOperateLog entity);

    int update(SysOperateLog entity);

    int updateSelective(SysOperateLog entity);

    int deleteByPrimaryKey(String logId);

    SysOperateLog selectByPrimaryKey(String logId);

    List<SysOperateLog> selectByEntitySelective(SysOperateLog entity);
}