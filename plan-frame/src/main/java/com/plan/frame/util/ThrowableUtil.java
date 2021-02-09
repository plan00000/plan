package com.plan.frame.util;


/**
 * 异常堆栈工具类
 * Created by lzh on 2017/5/18 0018.
 */
public class ThrowableUtil {

    public static String getCause(Throwable throwable){
        String msg = "";
        if(CommonUtil.isNotEmpty(throwable)) {
            Throwable t = throwable;
            Throwable cause = throwable.getCause();
            if (cause != null) {
                t = cause;
            }
            if (t != null) {
                msg = t.getLocalizedMessage();
                if (CommonUtil.isEmpty(msg)) {
                    msg = t.getClass().getName();
                } else {
                    msg = msg + " " + t.getClass().getName();
                }
                if (t == cause) {
                    msg += throwable.getMessage();
                }
                StringBuffer stackTraceBf = new StringBuffer();
                for (StackTraceElement traceElement : t.getStackTrace()) {
                    stackTraceBf.append("\tat " + traceElement + "\n");
                }
                msg = msg + "\tat\n" + stackTraceBf.toString();
            }
        }
        return msg;
    }
}
