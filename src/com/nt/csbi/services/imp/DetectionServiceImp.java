package com.nt.csbi.services.imp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.csbi.dao.DetectionDeviceDao;
import com.nt.csbi.entities.DetectionDevice;
import com.nt.csbi.services.DetectionService;
import com.nt.csbi.services.HistoryInfoServices;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author 杨润东
 *
 * @date: 2017年5月12日 上午11:10:30
 */
@Service
public class DetectionServiceImp implements DetectionService {

	@Autowired
	private DetectionDeviceDao device;
	
	@Autowired
    private JedisPool jedisPool;//注入JedisPool
	
	
	@Override
	public String addNewDetection(String detectionId) {
		
		DetectionDevice detectionDevice = new DetectionDevice();
		
		detectionDevice.setDetectionDeviceId(detectionId);
		detectionDevice.setDetectionDeviceLoginDate(new Timestamp(new Date().getTime()));
		
		return "";
	}

	@Override
	public String getDetectionIdList() {
		
		List<DetectionDevice> infoList = device.detectionDeviceLists("","");
		
		String result = "";
		
		for(DetectionDevice obj : infoList){
			result += "<tr><td style = 'display:none'>" + obj.getDetectionId().toString() + "</td>";
			result += "<td>" + obj.getDetectionDeviceId() + "</td>";
			result += "<td><a class = 'detectionDel' style='cursor: pointer;'>删除</a></td></tr>";
		}
		return result;
	}

	@Override
	public String detectionIdDel(String deviceId) {
		
		List<DetectionDevice> infoList = device.detectionIdDel(deviceId);
		
		String result = "";
		
		for(DetectionDevice obj : infoList){
			result += "<tr><td style = 'display:none'>" + obj.getDetectionId().toString() + "</td>";
			result += "<td>" + obj.getDetectionDeviceId() + "</td>";
			result += "<td><a class = 'detectionDel' style='cursor: pointer;'>删除</a></td></tr>";
		}
		return result;
	}

	@Override
	public String getDetectionIdSelectList(String startId,String endId) {
		List<DetectionDevice> infoList = device.detectionDeviceLists(startId,endId);
		
		String result = "";
		
		for(DetectionDevice obj : infoList){
			result += "<tr><td>" + obj.getDetectionDeviceId() + "</td>";
			result += "<td style='text-align:center'><input type='checkbox' name='checkboxOption'"
					+ " value=" + obj.getDetectionId().toString() + " style='width:20px;height:20px;z-index:-1;' value='0' >选择</td></tr>";
		}
		
		
		
		return result;
	}

	@Override
	public void setDetectionStatus(String str,String ids) {
		String deviceId[] = str.split("#@");
		device.setDetectionStatusOnline(deviceId);
		
		
		String idArr[] = ids.split("#@");
		Jedis jedis = jedisPool.getResource();
		jedis.del("detectionId");
		for (String id : idArr) {
			jedis.lpush("detectionId", id);
		}
		jedis.set("FreshDID","1");
		device.setOnlineDetectionStatusOnline(idArr);
		jedisPool.returnResourceObject(jedis);
	}

	@Override
	public String getCommStatus() {
		
		Jedis jedis = jedisPool.getResource();
		String status = "0";
		if(jedis.ttl("CommStatus")!=-2){
			
			status = jedis.get("CommStatus");
		}
		
		jedisPool.returnResourceObject(jedis);
		return status;
	}
	
	@Override
	public void restartComm() {
		
		Jedis jedis = jedisPool.getResource();
		jedis.set("StatusMonitor", "1");
		jedisPool.returnResourceObject(jedis);
		
	}

}
