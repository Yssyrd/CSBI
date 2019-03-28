package com.nt.csbi.services;

/**
 * @author 杨润东
 *
 * @date: 2017年5月12日 上午11:09:48
 */
public interface DetectionService {

	public String addNewDetection(String detcionId);
	public String getDetectionIdList();
	public String detectionIdDel(String deviceId);
	public String getDetectionIdSelectList(String startId,String endId);
	public void setDetectionStatus(String str,String ids);
	public String getCommStatus();
	public void restartComm();
}
