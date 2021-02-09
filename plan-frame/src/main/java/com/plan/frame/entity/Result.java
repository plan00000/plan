package com.plan.frame.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linzhihua on 2017/1/18.
 */
@ApiModel("统一返回类")
public class Result<T> implements Serializable {

    public static interface Status {

        /**
         * 业务处理成功
         */
        int SUCCESS = 200;

        /**
         * 服务端询问业务人员是否继续操作
         */
        int CONFIRM=202;
        // ========================
        /**
         * 权限异常
         */
        int AUTH = 403;

        /**
         * 业务条件不满足
         */
        int FAILED= 412;
        // ========================
        /**
         * 服务内部错误错误
         */
        int ERROR = 500;
    }
    @ApiModelProperty("操作信息")
    private String message;
    @ApiModelProperty("异常信息")
    private String exception;
    /**
     * 异常编号，每一个异常都需要赋值一个uuid作为唯一编号
     */
    @ApiModelProperty("异常记录id")
    private String exceptionId;
    @ApiModelProperty("返回常量值")
    private int code = 200;
    @ApiModelProperty("返回实体")
    private T result;

    public Result() {
    }

    public Result(int status) {
        this.code = status;
    }

    public Result(T result) {
        this.result = result;
    }

    public Result(int status, T result) {
        this.code = status;
        this.result = result;
    }

    public Result(int status, String message) {
        this.message = message;
        this.code = status;
    }

    public Result(int status, String message, String exception) {
        this.message = message;
        this.code = status;
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(String exceptionId) {
        this.exceptionId = exceptionId;
    }
}
