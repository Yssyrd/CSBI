package com.nt.csbi.handlers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nt.csbi.entities.OriginalTitleInfo;
import com.nt.csbi.services.OriginalTitleInfoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 杨润东
 *
 * @date: 2017年7月12日 上午11:14:18
 */
@Controller
@Scope("prototype")
public class OriginalTitleInfoHandler {
	
	@Autowired
	private OriginalTitleInfoService titleInfoService;
	
	@RequestMapping("/originalTitleInfoCreate")
	@ResponseBody
	public String originalTitleInfoCreate(@RequestParam(value="originalName", required=false) 
			String originalName,@RequestParam(value="detectionId", required=false) String detectionId){
		
		OriginalTitleInfo titleInfo = new OriginalTitleInfo();
		titleInfo.setDetectionId(detectionId);
		titleInfo.setInputDate(new Timestamp(new Date().getTime()));
		titleInfo.setTitleInfo(originalName);
		
		titleInfoService.saveTitleInfo(titleInfo);
		return "";
		
	}
	
	@RequestMapping("/getTitleInfoList")
	@ResponseBody
	public JSONArray getTitleInfoList(){
	
		JSONArray arr= new JSONArray();
		List<OriginalTitleInfo> titleInfoList = new ArrayList<>();
		titleInfoList = titleInfoService.getTitleInfoList();
		for(OriginalTitleInfo titleInfo : titleInfoList){
			String[] cpyInfo = titleInfo.getTitleInfo().split("#@");
			JSONObject obj=new JSONObject();
			obj.put("date",titleInfo.getInputDate().toString().substring(0, 19));
			obj.put("id", titleInfo.getOtiId());
			obj.put("detectionId", titleInfo.getDetectionId());
			obj.put("cpyInfo", cpyInfo[0]);
			obj.put("completeInfo", titleInfo.getTitleInfo());
			arr.add(obj);
		}
		
		return arr;
	}
	@RequestMapping("/originalTitleInfoDelete")
	@ResponseBody
	public String originalTitleInfoDelete(@RequestParam(value="id", required=false) String id){
		
		titleInfoService.originalTitleInfoDelete(id);
		return "";
		
	}
	
	@RequestMapping("/originalTitleInfoUpdate")
	@ResponseBody
	public String originalTitleInfoUpdate(@RequestParam(value="id", required=false) String id,
			@RequestParam(value="originalName", required=false) String originalName,
			@RequestParam(value="detectionId", required=false) String detectionId){
		
		titleInfoService.originalTitleInfoUpdate(id, originalName, detectionId);
		
		return "";
		
	}
}
