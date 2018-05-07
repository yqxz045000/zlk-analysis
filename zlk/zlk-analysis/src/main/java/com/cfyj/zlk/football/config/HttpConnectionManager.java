package com.cfyj.zlk.football.config;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

public class HttpConnectionManager {

	public static PoolingHttpClientConnectionManager cm = null;

	public static RequestConfig defaultRequestConfig = null;

	/**
	 * 最大连接数
	 */
	private final static int maxTotal = 800;
	// 如果你的客户端连接的目标服务器只有一个，那么大可设置最大route连接数和最大连接池连接数相同，以便高效利用连接池中创建的连接。
	/**
	 * 每个路由最大连接数
	 */
	private final static int maxPerRoute = 80;

	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 8000;
	/**
	 * 读取超时时间
	 */
	public final static int READ_TIMEOUT = 20000;
	/**
	 * 获取连接的最大等待时间
	 */
	public final static int WAIT_TIMEOUT = 60000;

	static {
		//初始化manager
		LayeredConnectionSocketFactory sslsf = null;
		try {
			sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();
		cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		cm.setMaxTotal(maxTotal);
		cm.setDefaultMaxPerRoute(maxPerRoute);
	}

	static {
		//init 请求配置
		defaultRequestConfig = RequestConfig.custom().setSocketTimeout(READ_TIMEOUT)//设置读取超时
				.setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(WAIT_TIMEOUT).build();
		/**
		 *.setStaleConnectionCheckEnabled(true) 在提交请求之前 测试连接是否可用    setConnectTimeout  连接超时，setSocketTimeout 读取超时，setConnectionRequestTimeout 从连接池获取连接超时 
		 */		
	}

	public synchronized static CloseableHttpClient getHttpClient() {
		// 请求重试处理
		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= 2) {// 如果已经重试了2次，就放弃
					return false;
				}
				if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
					return true;
				}
				if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
					return false;
				}
				if (exception instanceof InterruptedIOException) {// 超时
					return false;
				}
				if (exception instanceof UnknownHostException) {// 目标服务器不可达
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
					return false;
				}
				if (exception instanceof SSLException) {// SSL握手异常
					return false;
				}

				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				// 如果请求是幂等的，就再次尝试
				if (!(request instanceof HttpEntityEnclosingRequest)) {
					return true;
				}
				return false;
			}
		};

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
				.setRetryHandler(httpRequestRetryHandler).setDefaultRequestConfig(defaultRequestConfig).build();

		return httpClient;
	}
}