package com.nt.csbi.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nt.csbi.entities.RealTimeInfo;

import net.sf.json.JSONArray;

/**
 * @author 杨润东
 *
 * @date: 2017年5月12日 上午9:56:45
 */
public interface RealTimeService {

	public List<RealTimeInfo> RealTimeInfoList(String beginDate,String detectionId);
	public List<RealTimeInfo> RealTimeInfoListInterval(String inputDate,String intervalTime,String detectionId);
	public List<Object[]> detections(String beginDate,String regionInfo,String detectionId);
	public List<Object[]> detectionIds(String beginDate,String detectionId,String regionInfo);
	public List<RealTimeInfo> getRealTimeInfo(String detectionId,String lastTime);
	public void addDetection (String detectionId);
	public String detectionCharge(String detectionId);
	public JSONArray detectionInfoFresh(String page);
	public String detectionInfoFreshPageCount();
	public RealTimeInfo detectionTFInfoFresh(String detectionId);
	public void addDetailInfo(RealTimeInfo realTimeInfo);
	public String getFiftyPoints(String timeInterval,String figureBeignTime,String detectionId,
			String options,HttpServletRequest request);
	public String getMaxValue32To36(String Tbegin,String Tend, String detectionId);
	public List<Object[]> previousDetections(String beginDate,String detectionId);
	public List<Object[]> nextDetections(String beginDate,String detectionId);
	public List<Object[]> matchTableHeadInfo(String tableHeadInfo,String modelId);
	public List<String> getIntervalData(String detectionId,String lastTime);
	public RealTimeInfo getTableBaseInfo(String detectionId,String lastTime);
	public void detailInfoUpdate(String titleDataId,String inspectedIdDetail);
	
}
