package com.plan.frame.system.base.dao;

import com.plan.frame.mybatis.MyBatisTwoDao;
import com.plan.frame.system.base.entity.SysFileInfo;

import java.util.List;

@MyBatisTwoDao
public interface SysFileInfoDao {
    void insert(SysFileInfo entity);

    void insertBatch(List<SysFileInfo> entities);

    void insertSelective(SysFileInfo entity);

    int update(SysFileInfo entity);

    int updateSelective(SysFileInfo entity);

    int deleteByPrimaryKey(String fileId);

    SysFileInfo selectByPrimaryKey(String fileId);

    List<SysFileInfo> selectByEntitySelective(SysFileInfo entity);
}