package com.plan.frame.exception.record.dao;

import com.plan.frame.entity.ValueObject;
import com.plan.frame.exception.record.dto.ReqExceptionRecordDto;
import com.plan.frame.exception.record.entity.SysException;
import com.plan.frame.mybatis.MyBatisTwoDao;

import java.util.List;

/**
 * @Created by linzhihua
 * @Description
 * @ClassName ExceptionRecordMapper
 * @Date 2020/4/3 10:39
 */
@MyBatisTwoDao
public interface ExceptionRecordDao {
    /**
     * 根据条件查询异常信息
     * @param reqExceptionRecordDto
     * @return
     */
    List<SysException> exceptionRecordList(ReqExceptionRecordDto reqExceptionRecordDto);

    List<ValueObject> testVo(ReqExceptionRecordDto reqExceptionRecordDto);
}
