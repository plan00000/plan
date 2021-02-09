package com.plan.frame.system.base.dao;


import com.plan.frame.mybatis.MyBatisTwoDao;
import com.plan.frame.system.base.entity.SysAnnex;

import java.util.List;

@MyBatisTwoDao
public interface SysAnnexDao {
    void insert(SysAnnex entity);

    void insertBatch(List<SysAnnex> entities);

    void insertSelective(SysAnnex entity);

    int update(SysAnnex entity);

    int updateSelective(SysAnnex entity);

    int deleteByPrimaryKey(String annexId);

    SysAnnex selectByPrimaryKey(String annexId);

    List<SysAnnex> selectByEntitySelective(SysAnnex entity);
}