package com.nt.csbi.dao;

import java.util.List;

import com.nt.csbi.entities.InspectedDevice;

/**
 * @author 杨润东
 *
 * @date: 2017年4月12日 上午10:25:21
 */
public interface InspectedDeviceDao {

	public void addNewInspectedDevice(InspectedDevice device);
	public void updateInspectedDeviceById(String deviceId);
	public List<InspectedDevice> inspectedDeviceLists();
	public List<InspectedDevice> findInspectedDeviceById(String deviceId);
	public int findInspectedDeviceIdById(String deviceId);
	
	
}
