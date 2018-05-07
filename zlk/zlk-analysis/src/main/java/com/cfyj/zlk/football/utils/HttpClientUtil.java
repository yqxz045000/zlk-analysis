package com.cfyj.zlk.football.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cfyj.zlk.football.config.HttpConnectionManager;
import com.cfyj.zlk.football.domain.FetchedPage;
	
						
public class HttpClientUtil {

	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

	public static HttpConnectionManager connManager = new HttpConnectionManager();

	/**
	 * 返回String类型的响应
	 */	
	public static FetchedPage getReturnStr(String url, String charset) throws Exception {
		CloseableHttpClient client = connManager.getHttpClient();
		FetchedPage fetched = new FetchedPage();
		int statusCode = 500;
		HttpGet getHttp = new HttpGet(url);
		setRequestConfig(getHttp, url);
		log.info("请求的url为："+url);
		HttpResponse response = null;
		String respStr = "";
		try {
			response = client.execute(getHttp);
			statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				respStr = EntityUtils.toString(entity, charset);
			}
			fetched.setStatusCode(statusCode);
			fetched.setContent(respStr);
			log.info("响应状态码：" + statusCode);
//			log.info("响应状态码：" + statusCode + ",content:" + respStr);
		} catch(SocketTimeoutException e) {
			log.error("请求超时，请求url：{}",url, e);
			throw new RuntimeException(e);
		}
		catch (Exception e) {
			log.error("请求失败", e);
			throw new RuntimeException(e);
		} finally {
			if (response != null) {
				EntityUtils.consume(response.getEntity()); // 会自动释放连接
			}
		}

		return fetched;
	}

	/**
	 * 返回String类型的响应，用于js的访问
	 */
	public static FetchedPage getReturnStr(String url, String charset,String from) throws Exception {
		CloseableHttpClient client = connManager.getHttpClient();
		FetchedPage fetched = new FetchedPage();
		int statusCode = 500;
		HttpGet getHttp = new HttpGet(url);
		setRequestConfig(getHttp,from);
		log.info("请求的url为："+url);
		HttpResponse response = null;
		String respStr = "";
		try {
			response = client.execute(getHttp);
			statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				respStr = EntityUtils.toString(entity, charset);
			}
			fetched.setStatusCode(statusCode);
			fetched.setContent(respStr);
			log.info("响应状态码：" + statusCode);
//			log.info("响应状态码：" + statusCode + ",content:" + respStr);
		} catch(SocketTimeoutException e) {
			log.error("请求超时，请求url：{}",url, e);
			throw new RuntimeException(e);
		}catch (Exception e) {
			log.error("请求失败", e);
			throw new RuntimeException(e);
		} finally {
			if (response != null) {
				EntityUtils.consume(response.getEntity()); // 会自动释放连接
			}
		}
		return fetched;
	}
	
	/**
	 * 设置请求参数
	 * 
	 * @param url
	 * @param charset
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static FetchedPage getReturnStr(String url, String charset, Map<String, String> param) throws Exception {
		CloseableHttpClient client = connManager.getHttpClient();
		FetchedPage fetched = new FetchedPage();
		int statusCode = 500;

		url = setGRequestParam(url, param);
		log.info("请求的url为："+url);
		HttpGet getHttp = new HttpGet(url);
		setRequestConfig(getHttp, url);

		HttpResponse response = null;
		String respStr = "";
		try {
			response = client.execute(getHttp);
			statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				respStr = EntityUtils.toString(entity, charset);
			}
			if(404==statusCode) {
				respStr = "";
			}
			fetched.setStatusCode(statusCode);
			fetched.setContent(respStr);
			
			log.info("响应状态码：" + statusCode);
//			log.info("响应状态码：" + statusCode + ",content:" + respStr);
		} catch(SocketTimeoutException e) {
			log.error("请求超时，请求url：{}",url, e);
			throw new RuntimeException(e);
		}catch (Exception e) {
			log.error("请求失败", e);
			throw new RuntimeException(e);
		} finally {
			if (response != null) {
				EntityUtils.consume(response.getEntity()); // 会自动释放连接
			}
		}

		return fetched;
	}

	/**
	 * 设置请求参数
	 * 
	 * @param url
	 * @param param
	 */
	public static String setGRequestParam(String url, Map<String, String> param) {
		if (StringUtils.isNotBlank(url) && param != null && param.size() > 0) {
			StringBuilder sb = new StringBuilder(url);
			sb.append("?");
			for (Entry<String, String> entry : param.entrySet()) {
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");			
			}
			url = sb.toString();
//			log.info("拼接get请求参数后的url："+url);
		}
		return url;
	}

	/**
	 * 返回Document类型的响应
	 */
	public static FetchedPage getReturnDoc(String url, String charset) throws Exception {
		CloseableHttpClient client = connManager.getHttpClient();
		FetchedPage fetched = new FetchedPage();
		int statusCode = 500;
		HttpGet getHttp = new HttpGet(url);
		setRequestConfig(getHttp, url);
		HttpResponse response = null;
		String respStr = "";
		Document document;
		try {
			// 获得信息载体
			response = client.execute(getHttp);
			statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			Reader reader = new BufferedReader(new InputStreamReader(is, charset));
			SAXReader saxreader = new SAXReader();
			document = saxreader.read(reader);
			fetched.setDocument(document);
			fetched.setStatusCode(statusCode);
			log.info("响应状态码：" + statusCode);
		}catch(SocketTimeoutException e) {
			log.error("请求超时，请求url：{}",url, e);
			throw new RuntimeException(e);
		} catch (Exception e) {		
			log.error(">> Put back url:{}", url, e);
			throw new RuntimeException(e);
		} finally {
			if (response != null) {
				EntityUtils.consume(response.getEntity()); // 会自动释放连接
			}
		}
		return fetched;
	}

	public static void setRequestConfig(HttpGet getHttp, String url) {
		getHttp.addHeader("Accept-Charset", "GBK,utf-8");
		getHttp.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		getHttp.addHeader("Accept-Language", "zh-CN,zh");
		getHttp.addHeader("Cookie", "");
		getHttp.addHeader("referer", url);
		getHttp.addHeader("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22");

		// Request不会继承客户端级别的请求配置，所以在自定义Request的时候，需要将客户端的默认配置拷贝过去：
		RequestConfig requestConfig = RequestConfig.copy(HttpConnectionManager.defaultRequestConfig).build();// .setProxy(new
																												// HttpHost("myotherproxy",
																												// 8080))
		getHttp.setConfig(requestConfig);

	}

}
