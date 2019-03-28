package com.nt.csbi.dao;

import java.util.List;

import com.nt.csbi.entities.DetectionDevice;

/**
 * @author ����
 *
 * @date: 2017��4��12�� ����10:22:25
 */
public interface DetectionDeviceDao {
	
	public void addNewDetectionDevice(DetectionDevice device);
	public void updateDetectionDeviceById(String deviceId);
	public List<DetectionDevice> detectionDeviceLists(String startId,String endId);
	public List<DetectionDevice> findDeviceListById(String deviceId);
	public int findDeviceIdById(String deviceId);
	
	public List<DetectionDevice> detectionIdDel(String deviceId);
	public void setDetectionStatusOnline(String[] deviceId);
	public void setOnlineDetectionStatusOnline(String[] ids);
}
