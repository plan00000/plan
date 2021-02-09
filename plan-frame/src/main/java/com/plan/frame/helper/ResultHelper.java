package com.plan.frame.helper;


import com.plan.frame.entity.Result;
import com.plan.frame.util.ThrowableUtil;

/**
 * 返回帮助类
 * Created by linzhihua on 2017/4/20 0020.
 */
public class ResultHelper {

    public static Result success(){
        Result result = new Result();
        result.setCode(Result.Status.SUCCESS);
        result.setMessage("操作成功");
        return result;
    }
    public static Result success(String message){
        Result result = new Result();
        result.setCode(Result.Status.SUCCESS);
        result.setMessage(message);
        return result;
    }
    public static Result success(String message, Object o){
        Result result = new Result();
        result.setCode(Result.Status.SUCCESS);
        result.setMessage(message);
        result.setResult(o);
        return result;
    }

    public static Result success(Object o){
        Result result = new Result();
        result.setCode(Result.Status.SUCCESS);
        result.setMessage("操作成功");
        result.setResult(o);
        return result;
    }

    public static Result faile(String message){
        Result result = new Result();
        result.setCode(Result.Status.FAILED);
        result.setMessage(message);
        return result;
    }
    public static Result faile(String message, String exceptionId){
        Result result = new Result();
        result.setCode(Result.Status.FAILED);
        result.setMessage(message);
        result.setExceptionId(exceptionId);
        return result;
    }
    public static Result faile(int code,String message){
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    public static Result error(String message){
        Result result = new Result();
        result.setCode(Result.Status.ERROR);
        result.setMessage(message);
        return result;
    }
    public static Result error(String message, String exceptionId){
        Result result = new Result();
        result.setCode(Result.Status.ERROR);
        result.setMessage(message);
        result.setExceptionId(exceptionId);
        return result;
    }
    public static Result error(String message, String exceptionId, Throwable throwable){
        Result result = new Result();
        result.setCode(Result.Status.ERROR);
        result.setMessage(message);
        result.setExceptionId(exceptionId);
        result.setException(ThrowableUtil.getCause(throwable));
        return result;
    }

    /**
     * 上传文件过大，异常错误说明
     * @param message
     * @param exceptionId
     * @param throwable
     * @return
     */
    public static Result maxUploadSizeError(String message,String exceptionId,Throwable throwable){
        Result result = new Result();
        result.setCode(Result.Status.ERROR);
        result.setMessage("上传文件过大，系统只支持最大为10M的文件");
        result.setExceptionId(exceptionId);
        result.setException(ThrowableUtil.getCause(throwable));
        return result;
    }


}
