package com.cfyj.zlk.football.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfyj.zlk.football.data.dao.OuOddsMapper;
import com.cfyj.zlk.football.entity.Odds;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OuOddsService {

	@Autowired
	private OuOddsMapper ouOddsMapper;
	
	public void saveOrUpdate(Odds odds) {
		Odds odds_db = ouOddsMapper.findByQtidAndCompanyId(odds.getQtid(),odds.getCompanyId());
		if(odds_db!=null) {
			if(!odds_db.getAllOdds().equals(odds.getAllOdds())) {
				odds.setId(odds_db.getId());
				ouOddsMapper.updateById(odds);
			}
			
		}else {	
			ouOddsMapper.insert(odds.getQtid(),odds.getCompanyId(),odds.getOldOdds(),odds.getNewOdds(),odds.getAllOdds(),odds.getType(),odds.getCreateTime(),odds.getUpdateTime());
		}
	}

}
