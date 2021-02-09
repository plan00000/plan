package com.plan.frame.system.dao;

import com.plan.frame.mybatis.MyBatisTwoDao;
import com.plan.frame.system.base.entity.SysOperateLog;
import com.plan.frame.system.dto.log.ReqListSysLogDto;

import java.util.List;

/**
 * Created by xieyanling on 2020/4/14.
 */
@MyBatisTwoDao
public interface SysOperateLogExtDao {
    List<SysOperateLog> listOperateLog(ReqListSysLogDto req);
}