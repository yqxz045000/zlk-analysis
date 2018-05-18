package com.cfyj.zlk.football.data.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfyj.zlk.football.data.dao.StageMapper;
import com.cfyj.zlk.football.entity.Stage;
import com.cfyj.zlk.football.entity.Stage.StageType;

@Service
public class StageService {
	
	private Logger log = LoggerFactory.getLogger(StageService.class);
	
	@Autowired
	private StageMapper stageMapper;
	
	/**
	 *保存当前期次
	 * @param stagelist
	 * @param stageType
	 * @return
	 */
	public List<Stage> saveCurrent(List<Stage> stagelist, StageType stageType) {
		DateTime dt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(new DateTime().toString("yyyy-MM-dd 11:30:00"));
		stageMapper.updateByCurrentFalse(stageType.ordinal());//根据类型全部期次状态设置为非当前期
		if (stageType.equals(StageType.JC) || stageType.equals(StageType.SF)) {
			if (dt.isAfterNow()) {
				//将stagematch表中时间为大于前一天11.30的所有比赛对应的期次设置为当前期
				stageMapper.updateDefineCurrentTrue(stageType.ordinal(), dt.minusDays(1).toString("yyyy-MM-dd HH:mm:ss"));
			}else{
				stageMapper.updateDefineCurrentTrue(stageType.ordinal(), dt.toString("yyyy-MM-dd HH:mm:ss"));
			}
		}
		List<Stage> stageMatchList = new ArrayList<Stage>();
		for (int i=0;i<stagelist.size();i++) {
			Stage sm=stagelist.get(i);
			if(stageType.equals(StageType.BD)){
				if(i==stagelist.size()-1){
					addStageList(stageType,sm,stageMatchList);
				}
			}else{
				addStageList(stageType,sm,stageMatchList);
			}
		}
		return stageMatchList;
	}
	
	private void addStageList(StageType stageType,Stage stage,List<Stage> stageList){
		try {
			DateTime dt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(new DateTime().toString("yyyy-MM-dd 11:30:00"));
			Stage oldstage=stageMapper.selectByNameAndType(stageType.ordinal(), stage.getName());
			if (oldstage == null) {
				oldstage = new Stage();
				oldstage.setCreateTime(new Date());
				oldstage.setUpdateTime(new Date());
				oldstage.setAvailable(true);
				oldstage.setIsCurrent(true);
				oldstage.setStatus(true);
				oldstage.setName(stage.getName());
				oldstage.setType(stage.getType());
				stageMapper.insert(oldstage);
			}
			else{
				//北单没有多期次，北单当前期只有一期
				if(!stageType.equals(StageType.JC)&&!stageType.equals(StageType.SF)){ 
					oldstage.setIsCurrent(true);
					oldstage.setUpdateTime(new Date());
					stageMapper.updateByPrimaryKey(oldstage);
				}
				//防止历史期次有的比赛时间在11点半之后的情况，该其次还是属于历史期次,只有竞彩期次名称和时间有关系
				if(stageType.ordinal()==4&&!(stage.getName().compareTo( dt.minusDays(2).toString("yyyyMMdd").substring(2))>0)){ 
					oldstage.setIsCurrent(false);
					oldstage.setUpdateTime(new Date());
					stageMapper.updateByPrimaryKey(oldstage);
				}
			}
			stageList.add(oldstage);
			
		} catch (Exception e) {
			log.error("保存期次异常",e);
		}
	}
	
	

	
	/**
	 *	查找当前期次和历史2期的期次
	 * @param stageType
	 * @param size
	 * @return
	 */
	public List<Stage> findSpecialStage(StageType stageType, int size) {
		List<Stage> smlist = stageMapper.getStageByType(stageType.ordinal(), true);
		List<Stage> oldlist = stageMapper.getStageByTypeLimit(stageType.ordinal(), false, size);
		List<Stage> list = new ArrayList<Stage>();
		if (smlist != null && !smlist.isEmpty()) {
			for (int i = 0; i < smlist.size(); i++) {
				list.add(smlist.get(i));
			}
		}
		
		if (oldlist != null && !oldlist.isEmpty()) {
			list.add(oldlist.get(0));
			if (size == 2) {
				list.add(oldlist.get(1));
			}
		}
		return list;
	}
	
	/**
	 * 判断传入的id是否为当前期
	 * @param stageId
	 * @return
	 */
	public boolean isCurrent(Long stageId) {
		if(stageMapper.isCurrent(stageId)==1) {
			return true;
		}
		return false;
	}

	public String getNameById(Long stageId) {
		if(stageId!=null ) {
			return stageMapper.findNameById(stageId);			
		}
		return "";
	}

	public List<Stage> getCurrentName(int type) {
		return stageMapper.findCurrentName(type);

	}



	
}
