package com.plan.frame.system.dao;


import com.plan.frame.mybatis.MyBatisTwoDao;
import com.plan.frame.system.base.entity.SysQuartzLog;
import com.plan.frame.system.dto.quartz.ReqListLogsDto;

import java.util.List;

/**
 * Created by huangdongliang on 2020/4/18.
 */
@MyBatisTwoDao
public interface SysQuartzLogExtDao {

    /**
     *查询job日志列表
     * @param reqDto
     * @return
     */
    List<SysQuartzLog> listLogs(ReqListLogsDto reqDto);



}
