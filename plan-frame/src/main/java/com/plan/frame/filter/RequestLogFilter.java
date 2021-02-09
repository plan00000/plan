package com.plan.frame.filter;

import com.plan.frame.helper.ThreadLocalHelper;
import com.plan.frame.system.base.entity.SysRequestLog;
import com.plan.frame.system.service.SysRequestLogService;
import com.plan.frame.util.CommonUtil;
import com.plan.frame.util.IpAndAddrUtil;
import com.plan.frame.util.SpringContextHolder;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.util.Date;

/**
 * @Created by linzhihua
 * @Description：请求
 * @ClassName RequestLogFilter
 * @Date 2020/7/23 17:59
 */
public class RequestLogFilter implements Filter {
    Logger logger = Logger.getLogger(RequestLogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        BodyReaderHttpServletRequestWrapper requestWrapper = null;
        BodyWriterHttpServletResponseWrapper responseWrapper = null;

        String url = "/";
        String uri = "/";
        String ip = "";
        String method = "";
        String browser = "";
        String osname = "";
        String params = "";
        SysRequestLog sysRequestLog = new SysRequestLog();
        sysRequestLog.setLogId(CommonUtil.getUUID());
        ThreadLocalHelper.setSysLogId(sysRequestLog.getLogId());

        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
            url = httpServletRequest.getRequestURI();
            ip = getIpAddr(httpServletRequest);
            uri = httpServletRequest.getRequestURI();
            method = httpServletRequest.getMethod();
            browser = IpAndAddrUtil.getBrowser(httpServletRequest);
            osname = IpAndAddrUtil.getOsName(httpServletRequest);
            /*if(!(uri.contains("/swagger-ui.html")||uri.contains("/swagger-resources")||uri.contains("/v2")||uri.contains("/webjars"))) {
                String contentType = request.getContentType();
                if (CommonUtil.isNotEmpty(contentType) && contentType.toLowerCase().indexOf("/json") > -1) {
                    requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
                    params = new String(requestWrapper.getInput(), "utf-8");
                    if (logger.isDebugEnabled()) {
                        logger.info(ThreadLocalHelper.getSysLogId() + " 请求报文（" + ((HttpServletRequest) request).getRequestURI() + "）：" + params);
                    }
                    responseWrapper = new BodyWriterHttpServletResponseWrapper((HttpServletResponse) response);
                }
            }*/
        }

        if(!(uri.contains("/swagger-ui.html")||uri.contains("/swagger-resources")||uri.contains("/v2")||uri.contains("/webjars"))) {
            if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
                String contentType = request.getContentType();
                if (CommonUtil.isNotEmpty(contentType) && contentType.toLowerCase().indexOf("/json") > -1) {
                    requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
                    params = new String(requestWrapper.getInput(), "utf-8");
                    if (logger.isDebugEnabled()) {
                        logger.info(ThreadLocalHelper.getSysLogId() + " 请求报文（" + params + "）：" + new String(requestWrapper.getInput(), "utf-8"));
                    }
                    responseWrapper = new BodyWriterHttpServletResponseWrapper((HttpServletResponse) response);
                }
            }
        }

        sysRequestLog.setRepIp(ip);
        sysRequestLog.setUri(uri);
        sysRequestLog.setReqMethod(method);
        sysRequestLog.setBrowser(browser);
        sysRequestLog.setOsname(osname);
        sysRequestLog.setReqParams(params);
        sysRequestLog.setCreateTime(new Date());

        SysRequestLogService sysRequestLogService = SpringContextHolder.getBean("sysRequestLogService");
        sysRequestLogService.insertSysRequestLog(sysRequestLog);


        //记录开始时间
        long startTime = System.currentTimeMillis();
        if (requestWrapper != null && responseWrapper != null) {
            filterChain.doFilter(requestWrapper, responseWrapper);
            if(responseWrapper.isWrapper) {
                byte[] result = responseWrapper.getResult();
                response.getOutputStream().write(result);
                if (logger.isDebugEnabled()) {
                    logger.info(ThreadLocalHelper.getSysLogId() +" 响应报文（" + ((HttpServletRequest) request).getRequestURI() + "）：" + new String(result, "utf-8"));
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }


        long timeConsuming = System.currentTimeMillis() - startTime;
        if (timeConsuming <= 2000) {
            logger.info(ThreadLocalHelper.getSysLogId() + " " + url + " 耗时:" + (System.currentTimeMillis() - startTime) + "ms");
        } else {
            logger.warn(ThreadLocalHelper.getSysLogId() + " " + url + " 耗时:" + (System.currentTimeMillis() - startTime) + "ms");
        }
        SysRequestLog updateSysRequestLog = new SysRequestLog();
        updateSysRequestLog.setLogId(sysRequestLog.getLogId());
        updateSysRequestLog.setLostTime(String.valueOf(timeConsuming));
        sysRequestLogService.updateSysRequestLogByPk(updateSysRequestLog);

    }

    @Override
    public void destroy() {

    }

    /**
     * 封装请求对象
     */
    public static class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private byte[] body = null;
        private ByteArrayInputStream bais = null;

        public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(9632);
            int read = -1;
            InputStream inputStream = request.getInputStream();
            byte[] buf = new byte[9632];
            while ((read = inputStream.read(buf)) >= 0) {
                byteOutputStream.write(buf, 0, read);
            }
            body = byteOutputStream.toByteArray();
            bais = new ByteArrayInputStream(body);
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {

            return new ServletInputStream() {

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return CommonUtil.isNotEmpty(bais);
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return bais.read();
                }
            };
        }

        /**
         * 获取输入字节
         *
         * @return
         */
        public byte[] getInput() {
            return this.body;
        }
    }
    /**
     * 响应封装类
     */
    public static class BodyWriterHttpServletResponseWrapper extends HttpServletResponseWrapper {
        static final long MAX_BUFFER_SIZE=10240000;
        private ByteArrayOutputStream baos = null;
        private PrintWriter printWriter=null;
        /**
         * 输出流是否有封装
         * true:封装输出到缓存
         * false:直接输出
         */
        private boolean isWrapper=true;

        public BodyWriterHttpServletResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            final ServletOutputStream superServletOutputStream = this.getResponse().getOutputStream();
            if(baos==null) {
                baos = new ByteArrayOutputStream(9632);
            }
            if(!isWrapper){
                return superServletOutputStream;
            }else if (CommonUtil.isNotEmpty(getResponse().getContentType())
                    && getResponse().getContentType().toLowerCase().indexOf("/json") > -1) {
                ServletOutputStream servletOutputStream = new ServletOutputStream() {
                    @Override
                    public boolean isReady() {
                        return superServletOutputStream.isReady();
                    }

                    @Override
                    public void setWriteListener(WriteListener writeListener) {

                    }

                    /**
                     * 如果isWrapper=false直接输出，并且不缓存
                     * @param b
                     * @throws IOException
                     */
                    @Override
                    public void write(int b) throws IOException {
                        if(isWrapper) {
                            baos.write(b);
                        }else{
                            superServletOutputStream.write(b);
                        }
                    }
                };
                return servletOutputStream;
            }else {
                isWrapper=false;
                return superServletOutputStream;
            }
        }

        /**
         * 获取Writer
         * <b>注意创建的Writer对象必须要能自动刷新</b>
         * @return
         * @throws IOException
         */
        @Override
        public PrintWriter getWriter() throws IOException {
            this.printWriter=new PrintWriter(this.getOutputStream(),true);
            this.flushBuffer();
            return printWriter;
        }

        public byte[] getResult() {
            return this.baos.toByteArray();
        }
    }

    /**
     * 获取ip
     *
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (CommonUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
