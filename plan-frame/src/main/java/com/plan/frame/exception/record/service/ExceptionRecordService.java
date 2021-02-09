package com.plan.frame.exception.record.service;

import com.plan.frame.entity.Pageination;
import com.plan.frame.entity.ValueObject;
import com.plan.frame.exception.BaseException;
import com.plan.frame.exception.record.dao.ExceptionRecordDao;
import com.plan.frame.exception.record.dao.SysExceptionDao;
import com.plan.frame.exception.record.dto.ReqExceptionRecordDto;
import com.plan.frame.exception.record.dto.ResExceptionRecordDto;
import com.plan.frame.exception.record.entity.SysException;
import com.plan.frame.helper.BeanHelper;
import com.plan.frame.helper.PageinationHelper;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.ThrowableUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Created by linzhihua
 * @Description 异常信息记录service
 * @ClassName ExceptionRecordService
 * @Date 2020/4/3 9:14
 */
@Service
public class ExceptionRecordService {
    private static final Logger log = Logger.getLogger(ExceptionRecordService.class);
    @Resource
    private SysExceptionDao sysExceptionDao;
    @Resource
    private ExceptionRecordDao exceptionRecordDao;

    /**
     * 异常信息查询
     * @param reqExceptionRecordDto
     * @return
     * @throws Exception
     */
    public ResExceptionRecordDto exceptionRecordList(ReqExceptionRecordDto reqExceptionRecordDto) throws Exception{
        //分页开始
        Pageination pageination = PageinationHelper.start(reqExceptionRecordDto);
        List<SysException> sysExceptionList = exceptionRecordDao.exceptionRecordList(reqExceptionRecordDto);
        ResExceptionRecordDto resExceptionRecordDto = new ResExceptionRecordDto();
        resExceptionRecordDto.setSysExceptionList(sysExceptionList);
        //分页处理
        PageinationHelper.install(pageination,sysExceptionList,resExceptionRecordDto);
        return resExceptionRecordDto;
    }

    /**
     *测试使用
     * @param reqExceptionRecordDto
     * @return
     * @throws Exception
     */
    public ResExceptionRecordDto testVo(ReqExceptionRecordDto reqExceptionRecordDto) throws Exception{
        //分页开始
        Pageination pageination = PageinationHelper.start(reqExceptionRecordDto);
        long startTime = System.currentTimeMillis();
        List<ValueObject> voList = exceptionRecordDao.testVo(reqExceptionRecordDto);
        for(ValueObject valueObject:voList){
            valueObject.put("transactionId","01");
        }
        long endTime = System.currentTimeMillis();
        long sqlCost = endTime - startTime;
        System.out.println("数据库查询耗时："+sqlCost+"ms");
        List<SysException> sysExceptionList = BeanHelper.voListToBeanList(voList,SysException.class);
        long zzTime = System.currentTimeMillis();
        long zzCost = zzTime-endTime ;
        System.out.println("组装"+voList.size()+"耗时："+zzCost+"ms");
        ResExceptionRecordDto resExceptionRecordDto = new ResExceptionRecordDto();
        resExceptionRecordDto.setSysExceptionList(sysExceptionList);
        //分页处理
        PageinationHelper.install(pageination,voList,resExceptionRecordDto);

        //事务一致性测试
        SysException sysException = new SysException();


        return resExceptionRecordDto;
    }

    /**
     *异常信息记录
     * @param ex
     * @param url
     * @return
     * @throws Exception
     */
    public String createExceptionRecord(Throwable ex, String url){
        SysException sysException = new SysException();
        sysException.setExceptionId(CommonUtil.getUUID());
        try {
            if (ex instanceof BaseException) {
                String resultErrMsg = ((BaseException) ex).getResultErrMsg();
                int length = resultErrMsg.length();
                if(length>200){
                    resultErrMsg = resultErrMsg.substring(0,200);
                }
                sysException.setExceptionMsg(resultErrMsg);
                sysException.setExceptionInfo(ThrowableUtil.getCause(ex.getCause()));
                sysException.setExceptionUri(url);
                sysException.setTransactionId(((BaseException) ex).getTransactionId());
                sysException.setCreateTime(new Date());
                //新增
                sysExceptionDao.insert(sysException);
                ReqExceptionRecordDto reqExceptionRecordDto = new ReqExceptionRecordDto();

            }
        }catch (Exception e){
            log.error("异常信息记录失败，请联系管理员处理",e);
        }
        return  sysException.getExceptionId();
    }


}
