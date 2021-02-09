package com.plan.frame.annex.dao;



import com.plan.frame.annex.dto.FileReqDto;
import com.plan.frame.mybatis.MyBatisTwoDao;
import com.plan.frame.system.base.entity.SysFileInfo;

import java.util.List;

@MyBatisTwoDao
public interface SysFileInfoExtDao {


    List<SysFileInfo> selectByEntitySelective(FileReqDto entity);
}