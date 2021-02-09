package com.plan.frame.exception;


import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.ThrowableUtil;
import org.springframework.beans.factory.annotation.Value;

/**
 * 自定义异常基类
 * Created by linzhihua on 2017/4/19 0019.
 */
public class BaseException extends RuntimeException {

    @Value("{error.msg.length}")
    private String errorMsgLength;
    /**
     * 异常错误信息
     */
    private String errMsg;
    /**
     * 异常错误原因
     */
    private String errCause;
    /**
     * 异常错误建议
     */
    private String advise;

    /**
     * 组装后的异常错误信息
     */
    private String resultErrMsg;
    /**
     * 案件编号
     */
    private String transactionId;


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getErrCause() {
        return errCause;
    }

    public void setErrCause(String errCause) {
        this.errCause = errCause;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getAdvise() {
        return advise;
    }

    public void setAdvise(String advise) {
        this.advise = advise;
    }

    public String getErrorMsgLength() {
        return errorMsgLength;
    }

    public void setErrorMsgLength(String errorMsgLength) {
        this.errorMsgLength = errorMsgLength;
    }

    public String getResultErrMsg() {
        return resultErrMsg;
    }

    public void setResultErrMsg(String resultErrMsg) {
        this.resultErrMsg = resultErrMsg;
    }

    /**
     * 错误信息+异常+建议
     *
     * @param errMsg
     * @param cause
     * @param advise
     */
    public BaseException(String errMsg, Throwable cause, String advise) {
        super(errMsg, cause);
        this.errMsg = errMsg;
        this.advise = advise;
        this.resultErrMsg = getErrorMsg(errMsg, cause, advise);
    }

    /**
     * 错误信息+错误原因+建议
     *
     * @param errMsg
     * @param errCause
     * @param advise
     */
    public BaseException(String errMsg, String errCause, String advise) {
        super(errMsg + "；原因：" + errCause + "；建议：" + advise);
        this.errMsg = errMsg;
        this.errCause = errCause;
        this.advise = advise;
        this.resultErrMsg = getErrorMsg(errMsg, errCause, advise);

    }

    /**
     * 带错误信息+异常错误原因+建议
     *
     * @param errMsg
     * @param cause
     * @param advise
     * @return
     */
    private String getErrorMsg(String errMsg, Throwable cause, String advise) {
        String msg = "";
        String exMsg = "";
        int explenth = 200;
        exMsg = cause.getMessage();
        if (CommonUtil.isEmpty(exMsg)) {
            String exP = ThrowableUtil.getCause(cause);
            if (CommonUtil.isNotEmpty(exP) && exP.length() > explenth) {
                exMsg = exP.substring(0, explenth) + "...";
            } else {
                exMsg = exP;
            }
        }
        msg = errMsg + "；原因：" + exMsg + "；建议：" + advise;
        return msg;
    }

    /**
     * 带错误信息+错误原因+建议
     *
     * @param errMsg
     * @param errCause
     * @param advise
     * @return
     */
    public String getErrorMsg(String errMsg, String errCause, String advise) {
        String msg = "";
        msg = errMsg + "；原因：" + errCause + "；建议：" + advise;
        return msg;
    }

    public String getMessage() {
        return this.resultErrMsg;
    }
}
