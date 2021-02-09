package com.plan.frame.system.service;

import com.plan.frame.entity.Pageination;
import com.plan.frame.helper.PageinationHelper;
import com.plan.frame.system.base.dao.SysOperateLogDao;
import com.plan.frame.system.base.entity.SysOperateLog;
import com.plan.frame.system.dao.SysOperateLogExtDao;
import com.plan.frame.system.dto.log.ReqListSysLogDto;
import com.plan.frame.system.dto.log.ResListSysLogDto;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class OperateLogService {
    private static final Logger log = Logger.getLogger(OperateLogService.class);
    @Resource
    private SysOperateLogDao sysOperateLogDao;


    @Resource
    private SysOperateLogExtDao sysOperateLogExtDao;

    public void createSysOperateLog(SysOperateLog sysOperateLog){
        sysOperateLogDao.insert(sysOperateLog);
    }

    public ResListSysLogDto listSysOperateLog(ReqListSysLogDto req) throws Exception {
        //分页开始
        Pageination pageination = PageinationHelper.start(req);
        List<SysOperateLog> dtoList = sysOperateLogExtDao.listOperateLog(req);
        ResListSysLogDto res = new ResListSysLogDto();
        res.setSysLogList(dtoList);
        //分页处理
        PageinationHelper.install(pageination, dtoList, res);
        return res;
    }
}
