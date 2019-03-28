package com.nt.csbi.dao;

import java.sql.Timestamp;
import java.util.List;

import com.nt.csbi.entities.DetectionDevice;
import com.nt.csbi.entities.RealTimeInfo;

/**
 * @author 杨润东
 *
 * @date: 2017年4月12日 上午10:26:01
 */
public interface RealTimeInfoDao {
	
	public List<RealTimeInfo> RealTimeInfoList(String beginDate,String detectionId);
	public List<RealTimeInfo> RealTimeInfoListInterval(String beginTime,String endTime,String detectionId);
	public List<RealTimeInfo> showRealTimeInfoByDeviceId(String deviceId,String lastTime);
	public List<Object[]> detectionIds(String beginDate,String detectionId,String regionInfo);
	public List<Object[]> detections(String beginDate,String regionInfo,String detectionId);
	public List<Object[]> FindRealTimeInfoByInspectedId(String InspectedId);
	public void addDetection(String detectionId);
	public DetectionDevice detectionCharge(String detectionId);
	public List<String> detectionInfoFresh(String page);
	public List<String> detectionInfoFreshPageCount();
	public RealTimeInfo detectionTFInfoFresh(String detectionId);
	public void addDetailInfo(RealTimeInfo realTimeInfo);
	public List<Object[]> getFiftyPoints(Timestamp endTime, Timestamp figureBeignTime, String detectionId,String options);
	public List<Object[]> getPointsSustained(Timestamp endTime, Timestamp figureBeignTime, String detectionId,String options);
	public String getMaxValue32To36(String Tbegin,String Tend, String detectionId);	
	public List<Object[]> previousDetections(Timestamp beginDate,String deviceId);
	public List<Object[]> nextDetections(Timestamp beginDate,String deviceId);
	public List<Object[]> matchTableHeadInfo(String tableHeadInfo,String modelId);
	public List<String> getIntervalData(String detectionId,String lastTime);
	public RealTimeInfo getTableBaseInfo(String detectionId,String lastTime);
	public void detailInfoUpdate(String titleDataId,String inspectedIdDetail);
	public List<String> getNumHasData();
}
