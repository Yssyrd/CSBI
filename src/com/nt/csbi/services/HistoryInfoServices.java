package com.nt.csbi.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nt.csbi.entities.HistoryInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 杨润东
 *
 * @date: 2017年4月20日 下午3:44:17
 */
public interface HistoryInfoServices {
	
	public List<HistoryInfo> HistoryInfoListService(String beginDate,String endDate,String detectionId);
	public List<Object[]> detectionIds(String beginDate,String endDate,String detectionId);
	public List<Object[]> dates(String detectionId);
	public String getFiftyPoints(String timeInterval,String figureBeignTime,String detectionId,
			String options,HttpServletRequest request);
	public String getMaxValue32To36(String Tbegin,String Tend, String detectionId);
	public List<Object[]> previousDetections(String beginDate,String detectionId);
	public List<Object[]> nextDetections(String beginDate,String detectionId);
	public List<HistoryInfo> HistoryInfoListInterval(String inputDate,String intervalTime,String detectionId);
	public List<String> getIntervalData(String detectionId,String lastTime);
	public JSONArray dateAndId(String beginDate,String detectionId,String dataSource);
	public String syncData();
	public String deleteData(JSONArray json);
	public JSONArray getDateIds(JSONObject obj);
}
