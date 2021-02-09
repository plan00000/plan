package com.plan.frame.system.service;

import com.plan.frame.entity.Pageination;
import com.plan.frame.helper.PageinationHelper;
import com.plan.frame.system.base.dao.SysQuartzJobDao;
import com.plan.frame.system.base.entity.SysQuartzJob;
import com.plan.frame.system.dao.SysQuartzJobExtDao;
import com.plan.frame.system.dto.quartz.ReqListJobsDto;
import com.plan.frame.system.dto.quartz.ResListJobsDto;
import com.plan.frame.system.service.quartz.QuartzManage;
import com.plan.frame.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangdongliang on 2020/4/18.
 */
@Service
public class SysQuartzJobService {

    @Autowired
    private SysQuartzJobDao sysQuartzJobDao ;
    @Autowired
    private SysQuartzJobExtDao sysOuartzJobExtDao ;


    @Autowired
    private QuartzManage quartzManage;

    public List<SysQuartzJob> listJobsByIsPause(Boolean isPause){
        return sysOuartzJobExtDao.listJobsByIsPause(isPause);
    }
    public List<SysQuartzJob> listAllJobs(){
        return sysOuartzJobExtDao.listAllJobs();
    }

    public  void  updateIsPause(String id,Boolean isPause){
        SysQuartzJob job =new SysQuartzJob();
        job.setIsPause(isPause ? (byte)1 : (byte)0);
        job.setId(id);
        sysOuartzJobExtDao.updateIsPause(job);

    }

    /**
     * 分页查询任务
     * @param reqDto
     */
    public ResListJobsDto listJobsPage(ReqListJobsDto reqDto) throws Exception{

        //分页开始
        Pageination pageination = PageinationHelper.start(reqDto);
        List<SysQuartzJob> list = sysOuartzJobExtDao.listJobs(reqDto);
        ResListJobsDto resDto = new ResListJobsDto();
        resDto.setJobList(list);
        //分页处理
        PageinationHelper.install(pageination,list,resDto);
        return resDto;

    }

    /**
     * 新增任务
     * @param job
     */
    public void  createJob(SysQuartzJob job){
        String  id= CommonUtil.getUUID();
        job.setId(id);
        sysQuartzJobDao.insert(job);
        quartzManage.addJob(job);
    }

    public void  updateJob(SysQuartzJob job){
        sysQuartzJobDao.updateSelective(job);

        quartzManage.updateJobCron(sysQuartzJobDao.selectByPrimaryKey(job.getId()));
    }

    public  void   changeIsPause(String id,Boolean isPause){
        SysQuartzJob quartzJob = sysQuartzJobDao.selectByPrimaryKey(id);
        if (isPause) {
            quartzManage.pauseJob(quartzJob);
            quartzJob.setIsPause((byte)1);
        } else {
            quartzManage.resumeJob(quartzJob);
            quartzJob.setIsPause((byte)1);
        }
        updateIsPause(id,isPause);
    }

    public void execution(String id) {
        SysQuartzJob quartzJob=sysQuartzJobDao.selectByPrimaryKey(id);
        quartzManage.runAJobNow(quartzJob);
    }

    public void deleteJob(String id){
        SysQuartzJob quartzJob=sysQuartzJobDao.selectByPrimaryKey(id);
        quartzManage.deleteJob(quartzJob);
        sysQuartzJobDao.deleteByPrimaryKey(id);
    }





}