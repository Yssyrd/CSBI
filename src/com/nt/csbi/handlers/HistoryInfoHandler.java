package com.nt.csbi.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nt.csbi.entities.HistoryInfo;
import com.nt.csbi.entities.RealTimeInfo;
import com.nt.csbi.services.HistoryInfoServices;
import com.nt.csbi.services.RealTimeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 杨润东
 *
 * @date: 2017年4月20日 下午3:45:13
 */

@Controller
@Scope("prototype")
public class HistoryInfoHandler {
	
	@Autowired
	private HistoryInfoServices historyInfoService;
	
	@Autowired
	private RealTimeService realtimeService;
	
	@RequestMapping("/getDetectionIds")
	@ResponseBody
	public JSONArray getDetectionIds(@RequestParam(value="beginDate", required=false) String beginDate,
			@RequestParam(value="regionInfo", required=false) String regionInfo,
			@RequestParam(value="dataSource", required=false) String dataSource,
			@RequestParam(value="detectionId", required=false) String detectionId){
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa"+dataSource);
		JSONArray arr= new JSONArray();
		List<Object[]> detectionIds = new ArrayList<>();

		if(dataSource.equals("1")){
			if(!regionInfo.equals("")){
				detectionIds = historyInfoService.detectionIds(beginDate, beginDate, detectionId);
				for(Object[] object : detectionIds){
					JSONObject obj=new JSONObject();
					obj.put("date",object[1]);
					obj.put("id", object[0]);
					arr.add(obj);
				}
			}else{
				arr = historyInfoService.dateAndId(beginDate, detectionId, dataSource);
			}
		}
		
		if(dataSource.equals("0")){
			arr = historyInfoService.dateAndId(beginDate, detectionId, dataSource);
		}
		
		return arr;
	}
	
	@RequestMapping("/historyInfoSearch")
	public String Test(@RequestParam(value="beginDate", required=false) String beginDate ,
			@RequestParam(value="detectionId", required=false) String detectionId,
			@RequestParam(value="regionInfo", required=false) String regionInfo,
			@RequestParam(value="dataSource", required=false) String dataSource,
			@RequestParam(value="intervalTime", required=false) String intervalTime,
			HttpServletRequest request){
		
		System.out.println("=============================================================================");
		if(dataSource.trim().equals("0")){
			if(intervalTime.trim().equals("")){
				List<HistoryInfo> historyInfoList = historyInfoService.HistoryInfoListService(beginDate, 
						beginDate, detectionId);
				request.getSession().setAttribute("historyInfoList",historyInfoList);
			}else{
				List<HistoryInfo> historyInfoList = historyInfoService.HistoryInfoListInterval(beginDate, intervalTime, detectionId);
				request.getSession().setAttribute("historyInfoList",historyInfoList);
				request.getSession().setAttribute("intervalTimeTmp","时间段为："+intervalTime+",");
			
			}
			request.getSession().setAttribute("detectionIds",historyInfoService.detectionIds(beginDate, beginDate, ""));
			List<String> intervalData = historyInfoService.getIntervalData(detectionId, beginDate);
			System.out.println("intervalData:");
			for(String str : intervalData){
				System.out.println(str);
			}
			request.getSession().setAttribute("intervalData",intervalData);
		}
		if(dataSource.trim().equals("1")){
			if(intervalTime.trim().equals("")){
				List<RealTimeInfo> realtimeInfoList = realtimeService.RealTimeInfoList(beginDate, detectionId);
				request.getSession().setAttribute("realtimeInfoList",realtimeInfoList);
			}else{
				List<RealTimeInfo> realtimeInfoList = realtimeService.RealTimeInfoListInterval(beginDate, intervalTime, detectionId);
				request.getSession().setAttribute("realtimeInfoList",realtimeInfoList);
				request.getSession().setAttribute("intervalTimeTmp","时间段为："+intervalTime+",");
			}
			request.getSession().setAttribute("detectionIds",realtimeService.detections(beginDate, regionInfo, ""));
			List<String> intervalData = realtimeService.getIntervalData(detectionId, beginDate);
			request.getSession().setAttribute("intervalData",intervalData);
			
		}
		
		request.getSession().setAttribute("detectionId",detectionId);
		request.getSession().setAttribute("beginDate",beginDate);
		request.getSession().setAttribute("dataSource",dataSource);
		
		return "historyFigure";
	}
	
	@RequestMapping("/SwitchDetection")
	@ResponseBody
	public String SwitchDetection(@RequestParam(value="beginDate", required=false) String beginDate ,
			@RequestParam(value="endDate", required=false) String endDate,
			@RequestParam(value="detectionId", required=false) String detectionId,
			HttpServletRequest request){
		
		request.getSession().invalidate();
		
		return "historyFigure";
	}
	
	@RequestMapping("/getFiftyPoints")
	@ResponseBody
	public String getFiftyPoints(@RequestParam(value="timeInterval", required=false) String timeInterval,
			@RequestParam(value="figureBeignTime", required=true) String figureBeignTime,
			@RequestParam(value="detectionId", required=true) String detectionId,
			@RequestParam(value="options", required=true) String options,
			@RequestParam(value="sourceData", required=true) String sourceData,
			HttpServletRequest request){
		
		String result = "";
		if(sourceData.trim().equals("0")){
			result = historyInfoService.getFiftyPoints(timeInterval, figureBeignTime, detectionId,
					options,request);
		}
		if(sourceData.trim().equals("1")){
			result = realtimeService.getFiftyPoints(timeInterval, figureBeignTime, detectionId,
					options,request);
		}
		
		return result;
	}
	
	@RequestMapping("/getMaxValue32To36")
	@ResponseBody
	public String getMaxValue32To36(@RequestParam(value="Tbegin", required=true) String Tbegin,
			@RequestParam(value="Tend", required=true) String Tend,
			@RequestParam(value="detectionId", required=true) String detectionId,
			@RequestParam(value="sourceData", required=true) String sourceData,
			HttpServletRequest request){
		
		String result = "";
		if(sourceData.trim().equals("0")){
			result =  historyInfoService.getMaxValue32To36(Tbegin, Tend, detectionId);
		}
		if(sourceData.trim().equals("1")){
			result =  realtimeService.getMaxValue32To36(Tbegin, Tend, detectionId);
		}
		
		return result;
	}
	
	@RequestMapping("/previousDetections")
	@ResponseBody
	public JSONArray previousDetections(@RequestParam(value="beginDate", required=false) String beginDate,
			@RequestParam(value="regionInfo", required=false) String regionInfo,
			@RequestParam(value="dataSource", required=false) String dataSource,
			@RequestParam(value="detectionId", required=false) String detectionId){
		
		JSONArray arr= new JSONArray();
		List<Object[]> detectionIds = new ArrayList<>();
		
		if(dataSource.trim().equals("0")){
			detectionIds = historyInfoService.previousDetections(beginDate, detectionId);
			
			for(Object[] object : detectionIds){
				JSONObject obj=new JSONObject();
				obj.put("date",object[0]);
				obj.put("id", object[1]);
				arr.add(obj);
			}
		}
		
		if(dataSource.trim().equals("1")){
			detectionIds = realtimeService.previousDetections(beginDate, detectionId);
			
			for(Object[] object : detectionIds){
				JSONObject obj=new JSONObject();
				obj.put("date",object[0]);
				obj.put("id", object[1]);
				arr.add(obj);
			}
		}
		
		return arr;
	}
	@RequestMapping("/nextDetections")
	@ResponseBody
	public JSONArray nextDetections(@RequestParam(value="beginDate", required=false) String beginDate,
			@RequestParam(value="regionInfo", required=false) String regionInfo,
			@RequestParam(value="dataSource", required=false) String dataSource,
			@RequestParam(value="detectionId", required=false) String detectionId){
		
		JSONArray arr= new JSONArray();
		List<Object[]> detectionIds = new ArrayList<>();
		
		if(dataSource.trim().equals("0")){
			detectionIds = historyInfoService.nextDetections(beginDate, detectionId);
			
			for(Object[] object : detectionIds){
				JSONObject obj=new JSONObject();
				obj.put("date",object[0]);
				obj.put("id", object[1]);
				arr.add(obj);
			}
		}
		
		if(dataSource.trim().equals("1")){
			detectionIds = realtimeService.nextDetections(beginDate, detectionId);
			
			for(Object[] object : detectionIds){
				JSONObject obj=new JSONObject();
				obj.put("date",object[0]);
				obj.put("id", object[1]);
				arr.add(obj);
			}
		}
		
		return arr;
	}
	
	@RequestMapping("/syncData")
	@ResponseBody
	public String syncData(){
		
		return historyInfoService.syncData();
	}
	
	@RequestMapping("/deleteData")
	@ResponseBody
	public String deleteData(@RequestParam(value="ds", required=true) String ds){
		
		JSONArray json=JSONArray.fromObject(ds);
		
		return historyInfoService.deleteData(json);
	}
	
	@RequestMapping("/AllDateAndId")
	@ResponseBody
	public JSONArray AllDateAndId(@RequestParam(value="ds", required=true) String ds){
		
		JSONObject json=JSONObject.fromObject(ds);
		
		return historyInfoService.getDateIds(json);
	}
}
