package com.nt.csbi.services;

import java.util.List;

import com.nt.csbi.entities.OriginalTitleInfo;

/**
 * @author 杨润东
 *
 * @date: 2017年7月12日 上午11:01:58
 */
public interface OriginalTitleInfoService {

	public void saveTitleInfo(OriginalTitleInfo titleInfo);
	public List<OriginalTitleInfo> getTitleInfoList();
	public void originalTitleInfoDelete(String id);
	public void originalTitleInfoUpdate(String id,String originalName,String detectionId);
	public List<OriginalTitleInfo> getTitleInfoListForSearch(String tableHeadInfo,String modelId);
}
