package com.cfyj.zlk.football.data.spider;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cfyj.zlk.football.constant.CharsetConstant;
import com.cfyj.zlk.football.data.parser.BaseParser;
import com.cfyj.zlk.football.domain.FetchedPage;
import com.cfyj.zlk.football.utils.HttpClientUtil;

		

public abstract class BaseSpider {
	public  Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 直接返回字符串	
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public String spider(String url) throws Exception {
		FetchedPage resp = 	HttpClientUtil.getReturnStr(url, CharsetConstant.DEFAULT_CHARSET);
		return resp.getContent();	
	}
	
	/**
	 * 直接返回字符串
	 * @param url
	 * @return
	 * @throws Exception 
	 * 用于访问无法直接访问的js文件的访问
	 */
	public String spider(String url,String from) throws Exception{
		FetchedPage resp = HttpClientUtil.getReturnStr(url, CharsetConstant.DEFAULT_CHARSET,from);
		return resp.getContent();
	}
	
	/**
	 * 根据指定url获取接入的数据，然后根据传入的parse解析接入的数据，最后返回解析后的数据,默认的编码为uft-8
	 * @param url
	 * @param parse
	 * @return
	 * @throws Exception
	 */
	public <T> T spider(String url,BaseParser<T> parse) throws Exception {
		FetchedPage resp = 	HttpClientUtil.getReturnStr(url, CharsetConstant.DEFAULT_CHARSET);
		 return  parse.parse(resp,null);
	}
	
	/**
	 * 根据指定url获取接入的数据，然后根据传入的parse解析接入的数据，最后返回解析后的数据,默认的编码为uft-8
	 * @param url
	 * @param parse
	 * @return
	 * @throws Exception
	 */
	public <T> T spider(String url,BaseParser<T> parse,String from) throws Exception {
		FetchedPage resp = 	HttpClientUtil.getReturnStr(url, CharsetConstant.DEFAULT_CHARSET,from);
		 return  parse.parse(resp,null);
	}
	
	/**
	 * 根据指定url获取接入的数据，然后根据传入的parse解析接入的数据，最后返回解析后的数据,默认的编码为uft-8
	 * @param url
	 * @param type
	 * @param parse
	 * @return
	 * @throws Exception
	 */
	public <T> T spider(String url,Object p,BaseParser<T> parse) throws Exception {
		FetchedPage resp = 	HttpClientUtil.getReturnStr(url, CharsetConstant.DEFAULT_CHARSET);
		 return  parse.parse(resp,p);
	}
	
	/**
	 * 根据指定url获取接入的数据，然后根据传入的parse解析接入的数据，最后返回解析后的数据,默认的编码为uft-8
	 * 根据传入的map映射请求参数:key=value
	 * @param url
	 * @param type
	 * @param parse
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public <T> T spider(String url,Object p,BaseParser<T> parse,Map<String,String> param) throws Exception {
		FetchedPage resp = HttpClientUtil.getReturnStr(url, CharsetConstant.DEFAULT_CHARSET,param);	
		 return  parse.parse(resp,p);
	}
	
	/**
	 * 根据指定url获取接入的数据，然后根据传入的parse使用指定的charset 解析接入的数据，最后返回解析后的数据
	 * @param url
	 * @param type
	 * @param parse
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public <T> T spider(String url,Object p,BaseParser<T> parse,String charset) throws Exception {
		FetchedPage resp = HttpClientUtil.getReturnStr(url, charset);	
		 return  parse.parse(resp,p);
	}
	
	public abstract void spiderData () throws Exception;

}
