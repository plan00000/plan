package com.plan.frame.exception;

/**
 * @Author linzhihua
 * @Description 自定义异常-系统异常
 * @Date create in 2019/8/25 22:15
 * @Param
 **/

public class SystemException extends BaseException{
    public SystemException(String errMsg, Throwable cause, String  advise) {
        super(errMsg, cause,advise);
    }

    public SystemException(String errMsg, String errCause, String advise) {
        super(errMsg,errCause,advise);
    }
}
