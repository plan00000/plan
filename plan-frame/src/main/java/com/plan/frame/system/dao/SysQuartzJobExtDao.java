package com.plan.frame.system.dao;


import com.plan.frame.mybatis.MyBatisTwoDao;
import com.plan.frame.system.base.entity.SysQuartzJob;
import com.plan.frame.system.dto.quartz.ReqListJobsDto;

import java.util.List;

/**
 * Created by huangdongliang on 2020/4/18.
 */
@MyBatisTwoDao
public interface SysQuartzJobExtDao {
    /**
     * 根据是否暂停查询job
     * @param isPause
     * @return
     */
    List<SysQuartzJob> listJobsByIsPause(Boolean isPause  );

    /**
     * 更新是否暂停
     * @param SysQuartzJob
     */
    void  updateIsPause(SysQuartzJob job);


    /**
     *查询job列表
     * @param reqDto
     * @return
     */
    List<SysQuartzJob> listJobs(ReqListJobsDto reqDto ); /**
     *查询job列表
     * @param reqDto
     * @return
     */
    List<SysQuartzJob> listAllJobs();



}
