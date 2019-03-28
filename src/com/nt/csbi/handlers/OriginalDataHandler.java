package com.nt.csbi.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nt.csbi.entities.HistoryInfo;
import com.nt.csbi.entities.OriginalTitleInfo;
import com.nt.csbi.entities.RealTimeInfo;
import com.nt.csbi.services.OriginalDataService;
import com.nt.csbi.services.OriginalTitleInfoService;
import com.nt.csbi.services.RealTimeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 杨润东
 *
 * @date: 2017年4月28日 下午2:14:11
 */
@Controller
@Scope("prototype")
public class OriginalDataHandler {

	@Autowired
	private OriginalDataService service;
	@Autowired
	private OriginalTitleInfoService titleService;
	@Autowired
	private RealTimeService realService;
	
	
	@RequestMapping("/OriginalData")
	public String OriginalData(){
		
		return "originalData";
	}
	
	@RequestMapping(value = "/html2PDF/{dataId}_{pdfFileName}")
	public void html2PDF(@PathVariable("dataId") String dataId,
			@PathVariable("pdfFileName") String pdfFileName
			,HttpServletResponse response,
			HttpServletRequest request) throws Exception{
		
		service.dataHandler(dataId, response,request,pdfFileName);
		
		
	}
	
	@RequestMapping(value = "/html2Excel/{dataId}_{excelFileName}")
	public void html2Excel(@PathVariable("dataId") String dataId,
			@PathVariable("excelFileName") String excelFileName
			,HttpServletResponse response) throws Exception{

		service.html2Excel(dataId, response,excelFileName);
		
	}
	
	@RequestMapping("/originalTableCreate")
	@ResponseBody
	public String originalTableCreate(@RequestParam(value="originalName", required=false) 
			String originalName,HttpServletRequest request){
		
		String originalId = service.originalCreate(originalName,request);
		
		return originalId;
	}
	
	@RequestMapping("/getOriginalTable")
	@ResponseBody
	public String getOriginalTable(@RequestParam(value="originalId", required=true) 
			String originalId,HttpServletRequest request){
		
		service.getOriginalTable(originalId, request);
		return "historyFigure";
	}
	@RequestMapping("/getOriginalTableList")
	@ResponseBody
	public String getOriginalTableList(@RequestParam(value="originalBeginDate", required=false) String originalBeginDate ,
			@RequestParam(value="originalEndDate", required=false) String originalEndDate,
			@RequestParam(value="originalName", required=false) String originalName,
			HttpServletRequest request){
		
		String OriginalHtml = service.getOriginalTableList(originalBeginDate, originalEndDate, 
				originalName);
		
		return OriginalHtml;
	}
	
	@RequestMapping("/updateOraiginalTableTitle")
	@ResponseBody
	public String updateOraiginalTableTitle(@RequestParam(value="originalId", required=false) String originalId ,
			@RequestParam(value="tabletitle", required=false) String tabletitle,
			HttpServletRequest request){
		
		System.out.println("--------------" + service.updateOraiginalTableTitle(originalId, tabletitle, request));
		
		return service.updateOraiginalTableTitle(originalId, tabletitle, request);
	}
	
	@RequestMapping("/updateOraiginalTableOne")
	@ResponseBody
	public String updateOraiginalTableOne(@RequestParam(value="originalId", required=true) String originalId ,
			@RequestParam(value="tableOne", required=true) String tableOne,
			HttpServletRequest request){
		
		return service.updateOraiginalTableOne(originalId, tableOne, request);
	}
	
	@RequestMapping("/updateOraiginalTableTwo")
	@ResponseBody
	public String updateOraiginalTableTwo(@RequestParam(value="originalId", required=true) String originalId ,
			@RequestParam(value="tableTwo", required=true) String tableTwo,
			HttpServletRequest request){
		
		return service.updateOraiginalTableTwo(originalId, tableTwo, request);
	}
	
	@RequestMapping("/updateOraiginalTableThree")
	@ResponseBody
	public String updateOraiginalTableThree(@RequestParam(value="originalId", required=true) String originalId ,
			@RequestParam(value="tableThree", required=true) String tableThree,
			HttpServletRequest request){
		
		return service.updateOraiginalTableThree(originalId, tableThree, request);
	}
	
	@RequestMapping("/updateOraiginalTableFour")
	@ResponseBody
	public String updateOraiginalTableFour(@RequestParam(value="originalId", required=true) String originalId ,
			@RequestParam(value="tableFour", required=true) String tableFour,
			HttpServletRequest request){
		
		return service.updateOraiginalTableFour(originalId, tableFour, request);
	}
	
	@RequestMapping("/updateOraiginalTableFive")
	@ResponseBody
	public String updateOraiginalTableFive(@RequestParam(value="originalId", required=true) String originalId ,
			@RequestParam(value="tableFive", required=true) String tableFive,
			HttpServletRequest request){
		
		return service.updateOraiginalTableFive(originalId, tableFive, request);
	}
	
	@RequestMapping("/updateOraiginalTableSix")
	@ResponseBody
	public String updateOraiginalTableSix(@RequestParam(value="originalId", required=true) String originalId ,
			@RequestParam(value="tableSix", required=true) String tableSix,
			HttpServletRequest request){
		
		return service.updateOraiginalTableSix(originalId, tableSix, request);
	}
	
	@RequestMapping("/updateOraiginalTableSeven")
	@ResponseBody
	public String updateOraiginalTableSeven(@RequestParam(value="originalId", required=true) String originalId ,
			@RequestParam(value="tableSeven", required=true) String tableSeven,
			HttpServletRequest request){
		
		return service.updateOraiginalTableSeven(originalId, tableSeven, request);
	}
	
	@RequestMapping("/updateOraiginalTableEight")
	@ResponseBody
	public String updateOraiginalTableEight(@RequestParam(value="originalId", required=true) String originalId ,
			@RequestParam(value="tableEight", required=true) String tableEight,
			HttpServletRequest request){
		
		return service.updateOraiginalTableEight(originalId, tableEight, request);
	}
	
	@RequestMapping("/updateOraiginalTableNine")
	@ResponseBody
	public String updateOraiginalTableNine(@RequestParam(value="originalId", required=true) String originalId ,
			@RequestParam(value="tableNine", required=true) String tableNine,
			HttpServletRequest request){
		
		return service.updateOraiginalTableNine(originalId, tableNine, request);
	}
	
	@RequestMapping("/getSpecifyHistoryData")
	@ResponseBody
	public String getSpecifyHistoryData(@RequestParam(value="detectionId", required=true) String detectionId ,
			@RequestParam(value="dataSource", required=true) String dataSource ,
			@RequestParam(value="recordTime", required=true) String recordTime){
		String result = "";
		
		if(dataSource.trim().equals("0")){
			HistoryInfo historyInfo = service.getSpecifyOraiginalData(detectionId, recordTime);
			result =  historyInfo.getRecordTime()+"#@"+historyInfo.getAT()+"#@"+historyInfo.getBT()
				+"#@"+historyInfo.getCT()+"#@"+historyInfo.getDT()+"#@"+historyInfo.getET()+"#@"+historyInfo.getAF()
				+"#@"+historyInfo.getAngleX()+"#@"+historyInfo.getInspectedId()+"#@"+historyInfo.getINNOIS()+"#@"
				+historyInfo.getOUTNOI()+"#@"+historyInfo.getNOISE()+"#@" + historyInfo.getDIST();
		}
		
		if(dataSource.trim().equals("1")){
			RealTimeInfo historyInfo = service.getSpecifyOraiginalDataFromRealtime(detectionId, recordTime);
			result =  historyInfo.getRecordTime()+"#@"+historyInfo.getAT()+"#@"+historyInfo.getBT()
				+"#@"+historyInfo.getCT()+"#@"+historyInfo.getDT()+"#@"+historyInfo.getET()+"#@"+historyInfo.getAF()
				+"#@"+historyInfo.getAngleX()+"#@"+(historyInfo.getInspectedId()== null?"0":historyInfo.getInspectedId())
				+"#@"+(historyInfo.getINNOIS()== null?"0":historyInfo.getINNOIS())
				+"#@"+(historyInfo.getOUTNOI()== null?"0":historyInfo.getOUTNOI())
				+"#@"+(historyInfo.getNOISE()== null?"0":historyInfo.getNOISE())
				+"#@"+(historyInfo.getDIST()== null?"0":historyInfo.getDIST())
				+"#@"+(historyInfo.getDetectionIdDetail() == null?" #@ #@ #@ #@ #@ #@ #@ #@ ":historyInfo.getDetectionIdDetail());
			System.out.println("historyInfo.getDetectionIdDetail():"+historyInfo.getDetectionIdDetail());
		}
		
		return result;
		
	}
	
	@RequestMapping("/updateOraiginalUIOptions")
	@ResponseBody
	public void updateOraiginalUIOptions(@RequestParam(value="originalId", required=true) String originalId ,
			@RequestParam(value="options", required=true) String options,
			HttpServletRequest request){
		service.updateOraiginalUIOptions(originalId, options, request);
	}
	
	@RequestMapping("/matchTableHeadInfo")
	@ResponseBody
	public JSONArray matchTableHeadInfo(@RequestParam(value="tableHeadName", required=true) 
			String tableHeadName,@RequestParam(value="modelId", required=true) String modelId){
		JSONArray arr= new JSONArray();
		
		List<OriginalTitleInfo> titleMatch = titleService.getTitleInfoListForSearch(tableHeadName, modelId);
		for(OriginalTitleInfo object : titleMatch){
			String str[] = object.getTitleInfo().split("#@");
			JSONObject obj=new JSONObject();
			obj.put("date",object.getInputDate().toString().substring(0,16));
			obj.put("modelId", str[4]);
			obj.put("detectionId", object.getDetectionId());
			obj.put("cpyInfo", str[0]);
			obj.put("completeInfo", object.getTitleInfo());
			arr.add(obj);
		}
		List<Object[]> tableResult = service.matchTableHeadInfo(tableHeadName,modelId);
		for(Object[] object : tableResult){
			System.out.println(Arrays.asList(object));
			String str[] = object[0].toString().split("#@");
			JSONObject obj=new JSONObject();
			obj.put("date",object[1].toString().substring(0,16));
			obj.put("modelId", str[4]);
			obj.put("detectionId", "");
			obj.put("cpyInfo", str[0]);
			obj.put("completeInfo", object[0]);
			arr.add(obj);
		}
		
		List<Object[]> realTimeResult = realService.matchTableHeadInfo(tableHeadName,modelId);
		for(Object[] object : realTimeResult){
			System.out.println(Arrays.asList(object));
			String str[] = object[0].toString().split("#@");
			JSONObject obj=new JSONObject();
			obj.put("date",object[1].toString().substring(0,16));
			obj.put("modelId", str[4]);
			obj.put("detectionId", "");
			obj.put("cpyInfo", str[0]);
			obj.put("completeInfo", object[0]);
			arr.add(obj);
		}
		
		return arr;
	}
}
