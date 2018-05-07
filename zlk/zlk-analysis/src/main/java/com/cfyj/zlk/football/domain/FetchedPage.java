package com.cfyj.zlk.football.domain;

import org.dom4j.Document;

/**
 * 爬虫抓取页面返回状态
 * @author lilei
 *
 */

public class FetchedPage  {
	private String url;
	private String content;
	private int statusCode;
	private String remark;
	private Document document;
	private org.w3c.dom.Document xpathDocument;
	public FetchedPage(){
		
	}
	public FetchedPage(String url, String content, int statusCode){
		this.url = url;
		this.content = content;
		this.statusCode = statusCode;
		
	}
	public FetchedPage(String url, Document document, int statusCode){
		this.url = url;
		this.document = document;
		this.statusCode = statusCode;
		
	}
	
	public FetchedPage(String url, org.w3c.dom.Document xpathDocument, int statusCode){
		this.url = url;
		this.xpathDocument = xpathDocument;
		this.statusCode = statusCode;
		
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	public int getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public org.w3c.dom.Document getXpathDocument() {
		return xpathDocument;
	}
	public void setXpathDocument(org.w3c.dom.Document xpathDocument) {
		this.xpathDocument = xpathDocument;
	}
	
}

