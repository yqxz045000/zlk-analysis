package com.cfyj.zlk.football.data.parser;

import com.cfyj.zlk.football.domain.FetchedPage;

public interface BaseParser<T> {
	
	public T parse(FetchedPage resp,Object param)throws Exception;
	
}
