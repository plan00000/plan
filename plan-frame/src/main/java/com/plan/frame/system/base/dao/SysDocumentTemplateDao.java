package com.plan.frame.system.base.dao;

import com.plan.frame.mybatis.MyBatisTwoDao;
import com.plan.frame.system.base.entity.SysDocumentTemplate;

import java.util.List;

@MyBatisTwoDao
public interface SysDocumentTemplateDao {
    void insert(SysDocumentTemplate entity);

    void insertBatch(List<SysDocumentTemplate> entities);

    void insertSelective(SysDocumentTemplate entity);

    int update(SysDocumentTemplate entity);

    int updateSelective(SysDocumentTemplate entity);

    int deleteByPrimaryKey(String id);

    SysDocumentTemplate selectByPrimaryKey(String id);

    List<SysDocumentTemplate> selectByEntitySelective(SysDocumentTemplate entity);
}