package com.nt.csbi.handlers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nt.csbi.entities.RealTimeInfo;
import com.nt.csbi.services.RealTimeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
 * @author 杨润东
 *
 * @date: 2017年5月12日 上午9:55:31
 */
@Controller
public class RealTimeHandler {

	@Autowired
	private RealTimeService realTimeService;
	
	@ResponseBody
	@RequestMapping("/getRealTimeInfo")
	public JSONArray getRealTimeInfo(@RequestParam("detectionId") String detectionId,
			@RequestParam(value="lastTime", required=false) String lastTime) throws IOException{
		
		List<RealTimeInfo> infos = realTimeService.getRealTimeInfo(detectionId,lastTime);
		JSONArray arr= new JSONArray();
		
		for(RealTimeInfo realTimeInfo : infos){
			JSONObject obj=new JSONObject();
			obj.put("time", realTimeInfo.getRecordTime().toString().substring(5, 19));
			obj.put("AT", realTimeInfo.getAT());
			obj.put("BT", realTimeInfo.getBT());
			obj.put("CT", realTimeInfo.getCT());
			obj.put("DT", realTimeInfo.getDT());
			obj.put("ET", realTimeInfo.getET());
			obj.put("AF", realTimeInfo.getAF());
			obj.put("id", realTimeInfo.getInspectedId());
			obj.put("DIST", realTimeInfo.getDIST());
			obj.put("CONT", realTimeInfo.getCONT());
			obj.put("DISRH", realTimeInfo.getDISRH());
			obj.put("NOISE", realTimeInfo.getNOISE());
			obj.put("INNOIS", realTimeInfo.getINNOIS());
			obj.put("OUTNOI", realTimeInfo.getOUTNOI());
			obj.put("FLOW", realTimeInfo.getFLOW());
			
			if (Integer.valueOf(realTimeInfo.getAngleX().toString().trim()) > 1950
					&& Integer.valueOf(realTimeInfo.getAngleX().toString().trim()) < 2088) {
				obj.put("AngleX","0");
			} else if (Integer.valueOf(realTimeInfo.getAngleX().toString().trim()) > 1648
					&& Integer.valueOf(realTimeInfo.getAngleX().toString().trim()) <= 1950) {
				obj.put("AngleX", "15");
			} else if (Integer.valueOf(realTimeInfo.getAngleX().toString().trim()) >= 2088
					&& Integer.valueOf(realTimeInfo.getAngleX().toString().trim()) < 2448) {
				obj.put("AngleX","-15");
			} else {
				obj.put("AngleX","0");
			}
			
			if(realTimeInfo.getDetectionIdDetail()!=null||realTimeInfo.getRegionInfo()!=null){
				if(!realTimeInfo.getDetectionIdDetail().trim().equals("")||
						!realTimeInfo.getRegionInfo().trim().equals("")){
					obj.put("tableInfo","10");
				}else{
					obj.put("tableInfo","");
				}
			}else{
				obj.put("tableInfo","");
			}
			
			arr.add(obj);
		}
		
		return arr;
	}
	@ResponseBody
	@RequestMapping("/addDetection")
	public String addDetection(@RequestParam("detectionId") String detectionId) throws IOException{
		
		String result = realTimeService.detectionCharge(detectionId);
		if(result.length()>0){
			result = "false";
		}else{
			realTimeService.addDetection(detectionId);
			result = "";
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/detectionInfoFresh")
	public JSONArray detectionInfoFresh(@RequestParam("page") String page,
			HttpServletRequest request) throws IOException{
		
		return realTimeService.detectionInfoFresh(page);
	}
	
	@ResponseBody
	@RequestMapping("/detectionInfoFreshPageCount")
	public String detectionInfoFreshPageCount() throws IOException{
		
		return realTimeService.detectionInfoFreshPageCount();
	}
	
	@ResponseBody
	@RequestMapping("/addDetailInfo")
	public String addDetailInfo(@RequestParam("detectionId") String detectionId,
			@RequestParam(value="inspectedId", required=false) String inspectedId,
			@RequestParam(value="DIST", required=false) String DIST,
			@RequestParam(value="CONT", required=false) String CONT,
			@RequestParam(value="FLOW", required=false) String FLOW,
			@RequestParam(value="DISRH", required=false) String DISRH,
			@RequestParam(value="OUTNOI", required=false) String OUTNOI,
			@RequestParam(value="INNOIS", required=false) String INNOIS,
			@RequestParam(value="NOISE", required=false) String NOISE,
			@RequestParam(value="inputTime", required=false) String inputTime,
			@RequestParam(value="titleDataId", required=false) String titleDataId
			) throws IOException{
		
			RealTimeInfo realTimeInfo = realTimeService.detectionTFInfoFresh(detectionId);
			
			realTimeInfo.setCONT(CONT);
			realTimeInfo.setOUTNOI(OUTNOI);
			realTimeInfo.setDISRH(DISRH);
			realTimeInfo.setDIST(DIST);
			realTimeInfo.setFLOW(FLOW);
			realTimeInfo.setINNOIS(INNOIS);
			realTimeInfo.setNOISE(NOISE);
			realTimeInfo.setInspectedId(inspectedId);
			realTimeService.addDetailInfo(realTimeInfo);
			
		String result = "";
		if(inputTime.length()==14){
			Timestamp tmp = Timestamp.valueOf((new Timestamp(new Date().getTime())).toString().substring(0, 4) + "-" + inputTime);
			Timestamp time = new Timestamp(tmp.getTime()-1000);
			result = time.toString().substring(5, 19);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/addTitleInfo")
	public String addTitleInfo(@RequestParam("detectionId") String detectionId,
			@RequestParam(value="inspectedIdDetail", required=false) String inspectedIdDetail,
			@RequestParam(value="regionInfo", required=false) String regionInfo,
			@RequestParam(value="inputTime", required=false) String inputTime,
			@RequestParam(value="titleDataId", required=false) String titleDataId
			) throws IOException{
		
		if(titleDataId.trim().equals("")){
			RealTimeInfo realTimeInfo = realTimeService.detectionTFInfoFresh(detectionId);
			if(!inspectedIdDetail.trim().equals("")){
				realTimeInfo.setDetectionIdDetail(inspectedIdDetail);
			}
			if(!regionInfo.trim().equals("")){
				realTimeInfo.setRegionInfo(regionInfo);
			}
			realTimeService.addDetailInfo(realTimeInfo);
			
		}else{
			realTimeService.detailInfoUpdate(titleDataId, inspectedIdDetail);
		}
		String result = "";
		if(inputTime.length()==14){
			Timestamp tmp = Timestamp.valueOf((new Timestamp(new Date().getTime())).toString().substring(0, 4) + "-" + inputTime);
			Timestamp time = new Timestamp(tmp.getTime()-1000);
			result = time.toString().substring(5, 19);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getTableBaseInfo")
	public JSONObject getTableBaseInfo(@RequestParam("detectionId") String detectionId,
			@RequestParam(value="recordTime", required=false) String recordTime) throws IOException{
		
		System.out.println("getTableBaseInfo------------------------------------");
		RealTimeInfo info = realTimeService.getTableBaseInfo(detectionId, recordTime);
		
		JSONObject obj=new JSONObject();
		
		obj.put("id",info.getRealTimeId());
		obj.put("info",info.getDetectionIdDetail());
		
		return obj;
	}
}
