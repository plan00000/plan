package com.plan.frame.helper;

import com.alibaba.fastjson.JSONObject;
import com.plan.frame.exception.SystemException;
import com.plan.frame.util.StringUtil;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Created by linzhihua
 * @Description 单元测试请求帮助类
 * @ClassName TestHelper
 * @Date 2020/4/13 8:47
 */
public class TestHelper {

    private static final Logger log = Logger.getLogger(TestHelper.class);

    private static final String host= "http://127.0.0.1:8081";
    /**
     * POST提交JSON报文测试服务接口
     *
     * @param uri
     * @param requestObject
     * @return
     */
    public static String post( String uri, Object requestObject) {
        InputStream requestIs = null;
        InputStream responseIs = null;
        OutputStream os = null;
        long startTime=System.currentTimeMillis();

        try {

            URL url = new URL(host + uri);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(300000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);                // 拷贝请求输入流
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.setRequestProperty("Host",url.getHost());
            httpURLConnection.getRequestProperty("Host");

            int readLength = 0;
            byte[] bufferedbyte = new byte[8192];
            String reqStr= JSONObject.toJSONString(requestObject);
            log.debug("Request Host: " + host);
//            System.out.println("Request Host: "+host);
            log.debug("Request : \r\n" + reqStr);
//            System.out.println("Request :\r\n"+reqStr);
            requestIs = new ByteArrayInputStream(reqStr.getBytes("utf-8"));
            os = httpURLConnection.getOutputStream();
            while ((readLength = requestIs.read(bufferedbyte)) > 0) {
                os.write(bufferedbyte, 0, readLength);
            }
            int responseCode = httpURLConnection.getResponseCode();
            log.debug("Http Response Code : " + responseCode);
//            System.out.println("Http Response Code : "+responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                responseIs = httpURLConnection.getInputStream();
            } else {
                responseIs = httpURLConnection.getErrorStream();
            }
            String responseStr= StringUtil.readInputStreamToString(responseIs, "utf-8");

            log.info("Response ：\r\n"+responseStr);
            return responseStr;
        } catch (Exception e) {
            log.error("测试接口失败：", e);
            throw new SystemException("测试接口失败", e,"建议联系管理员处理");
        } finally {
            log.info("耗时 ："+(System.currentTimeMillis()-startTime)+"ms");
            if (os != null) {
                try {
                    os.close();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            if (requestIs != null) {
                try {
                    requestIs.close();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }
}
