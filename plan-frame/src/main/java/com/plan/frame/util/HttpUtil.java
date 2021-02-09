package com.plan.frame.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	public static final String GET_METHOD = "GET";
	public static final String POST_METHOD = "POST";

	// 使用httpclient2.0
	public  static String post(String url, Map<String,String> params,String requestBody) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		try {
			if(CommonUtil.isNotEmpty(params)) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					postMethod.addParameter(entry.getKey(), entry.getValue());
				}
			}
		    
		    if(requestBody != null && !"".equals(requestBody)){
		    	RequestEntity se = new StringRequestEntity(requestBody, "application/json", "UTF-8");
		    	postMethod.setRequestEntity(se);
		    }
			// 设置header
			postMethod.setRequestHeader("Content-Type", "application/json");
			int statusCode = httpClient.executeMethod(postMethod);
			byte[] responseBody = postMethod.getResponseBody();
			String info = new String(responseBody, "UTF-8");
			logger.debug(statusCode + ":" + info);
			return info;
		} catch (Exception e) {
			logger.error("http post请求失败", e);
		} finally {
			postMethod.releaseConnection();
		}
		return null;
	}
	
		// 使用httpclient2.0
		public  static String get(String url,Map<String,String> params) {
			return request(url, params, GET_METHOD);
		}
		public  static String post(String url,Map<String,String> params) {
			return request(url, params, POST_METHOD);
		}
		
		public  static String request(String url,Map<String,String> params,String methodType) {
			HttpClient httpClient = new HttpClient();
			StringBuffer query=new StringBuffer();
			HttpMethod method=null;
			if(url != null && !"".equals(url)){
				try {
					if(params!=null){
						if(url.contains("?")){
							for (Map.Entry<String,String> entry : params.entrySet()) {
								query.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(),"UTF-8"));
							}
							url=url+query.toString();
						}else{
							query.append("?");
							for (Map.Entry<String,String> entry : params.entrySet()) {
								query.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(),"UTF-8")).append("&");
							}
							url=url+query.substring(0, query.length()-1);
							
						}
					}
					if(POST_METHOD.equals(methodType)){
						method = new PostMethod(url);
					}else{
						method = new GetMethod(url);
					}
				
					int statusCode = httpClient.executeMethod(method);
					byte[] responseBody = method.getResponseBody();
					String info = new String(responseBody, "UTF-8");
					logger.debug(statusCode + ":" + info);
					return info;
				} catch (Exception e) {
					logger.error("http post请求失败", e);
				} finally {
					if(method!=null){
						method.releaseConnection();
					}
				}
			}
			return null;
		}

	public  static String post(String url, Map<String,String> params,String requestBody,String authorization) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		try {
			if(CommonUtil.isNotEmpty(params)) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					postMethod.addParameter(entry.getKey(), entry.getValue());
				}
			}

			if(requestBody != null && !"".equals(requestBody)){
				RequestEntity se = new StringRequestEntity(requestBody, "application/json", "UTF-8");
				postMethod.setRequestEntity(se);
			}
			// 设置header
			postMethod.setRequestHeader("Content-Type", "application/json");
			postMethod.setRequestHeader("Authorization", authorization);
			int statusCode = httpClient.executeMethod(postMethod);
			byte[] responseBody = postMethod.getResponseBody();
			String info = new String(responseBody, "UTF-8");
			logger.debug(statusCode + ":" + info);
			return info;
		} catch (Exception e) {
			logger.error("http post请求失败", e);
		} finally {
			postMethod.releaseConnection();
		}
		return null;
	}

	// 使用httpclient2.0
	public static String post1(String url, String params,String authorization) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		//postMethod.addParameter("params", params);
		try {
			RequestEntity requestEntity = new StringRequestEntity(params, "text/xml", "utf-8");
			postMethod.setRequestEntity(requestEntity);
			postMethod.setRequestHeader("Accept", "application/json");
			postMethod.setRequestHeader("Content-Type", "application/json;charset=utf-8");
			postMethod.setRequestHeader("Authorization", authorization);
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				return statusCode + "";
			}
			byte[] responseBody = postMethod.getResponseBody();
			String info = new String(responseBody, "UTF-8");
			System.out.println(info);
			return info;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return null;
	}

	public  static String post3(String url, Map<String,String> params,String requestBody,String authorization) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		try {
			for (Map.Entry<String,String> entry : params.entrySet()) {
				postMethod.addParameter(entry.getKey(),  entry.getValue());
			}

			if(requestBody != null && !"".equals(requestBody)){
				RequestEntity se = new StringRequestEntity(requestBody, "application/json", "UTF-8");
				postMethod.setRequestEntity(se);
			}
			// 设置header
			postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			postMethod.setRequestHeader("Authorization", authorization);
			int statusCode = httpClient.executeMethod(postMethod);
			byte[] responseBody = postMethod.getResponseBody();
			String info = new String(responseBody, "UTF-8");
			logger.debug(statusCode + ":" + info);
			return info;
		} catch (Exception e) {
			logger.error("http post请求失败", e);
		} finally {
			postMethod.releaseConnection();
		}
		return null;
	}

	public  static String get3(String url, Map<String,String> params,String requestBody,String authorization) {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		try {
			// 设置header
			getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			getMethod.setRequestHeader("Authorization", authorization);
			int statusCode = httpClient.executeMethod(getMethod);
			byte[] responseBody = getMethod.getResponseBody();
			String info = new String(responseBody, "UTF-8");
			logger.debug(statusCode + ":" + info);
			return info;
		} catch (Exception e) {
			logger.error("http post请求失败", e);
		} finally {
			getMethod.releaseConnection();
		}
		return null;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {

	}
}
