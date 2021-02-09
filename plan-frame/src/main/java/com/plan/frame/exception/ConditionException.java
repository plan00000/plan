package com.plan.frame.exception;

/**
 * @Author linzhihua
 * @Description 定义异常业务-条件不满足的异常
 * @Date create in 2019/8/25 22:09
 * @Param
 **/

public class ConditionException extends BaseException{

    public ConditionException(String errMsg, Throwable cause, String  advise) {
        super(errMsg, cause,advise);
    }

    public ConditionException(String errMsg, String errCause, String advise) {
        super(errMsg,errCause,advise);
    }
}
