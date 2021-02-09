package com.plan.frame.system.service;

import com.plan.frame.entity.Pageination;
import com.plan.frame.helper.PageinationHelper;
import com.plan.frame.system.base.dao.SysQuartzLogDao;
import com.plan.frame.system.base.entity.SysQuartzLog;
import com.plan.frame.system.dao.SysQuartzLogExtDao;
import com.plan.frame.system.dto.quartz.ReqListLogsDto;
import com.plan.frame.system.dto.quartz.ResListLogsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangdongliang on 2020/4/18.
 */
@Service
public class SysQuartzLogService {

    @Autowired
    private SysQuartzLogDao sysQuartzLogDao ;
    @Autowired
   private SysQuartzLogExtDao sysQuartzLogExtDao ;


    public void create(SysQuartzLog log){
         sysQuartzLogDao.insert(log);
    }




    /**
     * 分页查询任务日志
     * @param reqDto
     */
    public ResListLogsDto listLogsPage(ReqListLogsDto reqDto) throws Exception{

        //分页开始
        Pageination pageination = PageinationHelper.start(reqDto);
        List<SysQuartzLog> list = sysQuartzLogExtDao.listLogs(reqDto);
        ResListLogsDto resDto = new ResListLogsDto();
        resDto.setLogList(list);
        //分页处理
        PageinationHelper.install(pageination,list,resDto);
        return resDto;

    }

}