package com.nt.csbi.services;

/**
 * @author ����
 *
 * @date: 2017��5��12�� ����11:09:48
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
