package com.plan.frame.system.service;

import com.plan.frame.system.base.dao.SysRequestLogDao;
import com.plan.frame.system.base.entity.SysRequestLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName SysRequestLogService
 * @Date 2020/7/24 9:32
 */
@Service
public class SysRequestLogService {
    @Resource
    private SysRequestLogDao sysRequestLogDao;

    /**
     *
     * @param sysRequestLog
     */
    public void insertSysRequestLog(SysRequestLog sysRequestLog){
        sysRequestLogDao.insert(sysRequestLog);
    }

    public void updateSysRequestLogByPk(SysRequestLog sysRequestLog){
        sysRequestLogDao.updateSelective(sysRequestLog);
    }
}
