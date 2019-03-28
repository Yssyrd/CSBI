package com.nt.csbi.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nt.csbi.services.DetectionService;


/**
 * @author 杨润东
 *
 * @date: 2017年5月12日 上午11:16:00
 */
@Controller
public class DetectionHandler {

	@Autowired
	private DetectionService detectionService;
	
	@RequestMapping("/getDetectionIdList")
	@ResponseBody
	public String getDetectionIdList(){
		
		String infos = detectionService.getDetectionIdList();
		
		return infos;
	}
	
	@RequestMapping("/getDetectionIdSelectList")
	@ResponseBody
	public String getDetectionIdSelectList(
			@RequestParam(value="startId", required=false) String startId,
			@RequestParam(value="endId", required=false) String endId){
		
		String infos = detectionService.getDetectionIdSelectList(startId,endId);
		
		return infos;
	}
	
	
	@RequestMapping("/detectionIdDel")
	@ResponseBody
	public String detectionIdDel(@RequestParam(value="deviceId", required=false) String deviceId){
		
		String infos = detectionService.detectionIdDel(deviceId);
		
		return infos;
	}
	
	@RequestMapping("/setDetectionStatus")
	@ResponseBody
	public String setDetectionStatus(@RequestParam(value="deviceId", required=true) String deviceId,
			@RequestParam(value="ids", required=true) String ids){
		
		detectionService.setDetectionStatus(deviceId,ids);
		return "";
	}
	
	@RequestMapping("/getCommStatus")
	@ResponseBody
	public String getCommStatus(){
		
		return detectionService.getCommStatus();
	}
	
	@RequestMapping("/restartComm")
	@ResponseBody
	public String restartComm(){
		detectionService.restartComm();
		return "ok";
	}
}
