package com.nt.csbi.dao;

import java.sql.Timestamp;
import java.util.List;

import com.nt.csbi.entities.HistoryInfo;
import com.nt.csbi.entities.RealTimeInfo;

/**
 * @author 杨润东
 *
 * @date: 2017年4月12日 上午9:32:01
 */
public interface HistoryInfoDao {
	
	public List<Object[]> showHistoryInfoByDeviceId(String deviceId);
	public List<Object[]> FindHistoryInfoByInspectedId(String InspectedId);
	public void saveHistoryInfo(List<HistoryInfo> historyInfoList);
	public List<HistoryInfo> searchHistoryInfoList(Timestamp beginDate,Timestamp endDate,String deviceId);
	public List<Object[]> getDates(String deviceId);
	public List<Object[]> getDetectionIds(String beginDate,String endDate,String deviceId);
	public List<Object[]> getFiftyPoints(Timestamp endTime, Timestamp figureBeignTime, String detectionId,String options);
	public String getMaxValue32To36(String Tbegin,String Tend, String detectionId);
	public List<Object[]> previousDetections(Timestamp beginDate,String deviceId);
	public List<Object[]> nextDetections(Timestamp beginDate,String deviceId);
	public List<String> getIntervalData(String detectionId,String lastTime);
	public List<Object[]> dateAndId(String beginDate,String detectionId,String dataSource);
	public void saveDateSave(String[] arr);
	public List<Object[]> syncData();
	public Integer deteleDataFromHistory(String date,String id);
	public Integer deteleDataFromRealTime(String date,String id);
	public void deleteSaveDate(Integer[] ids);
	public List<Object[]> AllDateAndId(String date,String detectionId,String source);
	public void addDateIdStr(String[] arr);
}
