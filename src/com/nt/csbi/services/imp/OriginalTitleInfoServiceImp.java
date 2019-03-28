package com.nt.csbi.services.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.csbi.dao.OriginalTitleInfoDao;
import com.nt.csbi.entities.OriginalTitleInfo;
import com.nt.csbi.services.OriginalTitleInfoService;

/**
 * @author 杨润东
 *
 * @date: 2017年7月12日 上午11:03:01
 */
@Service
public class OriginalTitleInfoServiceImp implements OriginalTitleInfoService {

	@Autowired
	private OriginalTitleInfoDao titleInfoDao;
	
	@Override
	public void saveTitleInfo(OriginalTitleInfo titleInfo) {
		
		titleInfoDao.saveTitleInfo(titleInfo);
		
	}

	@Override
	public List<OriginalTitleInfo> getTitleInfoList() {
		
		return titleInfoDao.getTitleInfoList();
	}

	@Override
	public void originalTitleInfoDelete(String id) {
		titleInfoDao.originalTitleInfoDelete(id);
	}

	@Override
	public void originalTitleInfoUpdate(String id, String originalName, String detectionId) {
		
		titleInfoDao.originalTitleInfoUpdate(id, originalName, detectionId);
		
	}

	@Override
	public List<OriginalTitleInfo> getTitleInfoListForSearch(String tableHeadInfo, String modelId) {
		
		List<OriginalTitleInfo> lists = titleInfoDao.getTitleInfoListForSearch(tableHeadInfo,modelId);
		List<String> strBool = new ArrayList<>();
		List<OriginalTitleInfo> result = new ArrayList<>(); 
		
		if(!tableHeadInfo.trim().equals("")){
			for(OriginalTitleInfo tmp : lists){
				String [] strArr = tmp.getTitleInfo().split("#@");
				if(strArr!=null){
					if(!strArr[0].trim().equals("")){
						String strTmp =  String.valueOf(strArr[0]);
						if(!strBool.contains(strTmp)&&strArr[0].indexOf(tableHeadInfo)>-1){
							strBool.add(strTmp);
							result.add(tmp);
						}
					}
				}
			}
			lists = new ArrayList<>();
			lists.addAll(result);
		}
		if(!modelId.trim().equals("")){
			result = new ArrayList<>(); 
			for(OriginalTitleInfo tmp : lists){
				String [] strArr = tmp.getTitleInfo().split("#@");
				if(strArr!=null){
					if(!strArr[4].trim().equals("")){
						String strTmp = String.valueOf(strArr[4]);
						if(!strBool.contains(strTmp)&&strArr[4].indexOf(modelId.trim())>-1){
							strBool.add(strTmp);
							result.add(tmp);
						}
					}
				}
			}
		}
		return result;
	}

}
