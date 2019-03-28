package com.nt.csbi.handlers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nt.csbi.entities.Criterion;
import com.nt.csbi.entities.OriginalTitleInfo;
import com.nt.csbi.services.CriterionService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 杨润东
 *
 * @date: 2017年8月24日 下午3:31:01
 */
@Controller
public class CriterionHandler {

	@Autowired
	private CriterionService criterionService;
	
	@RequestMapping("/criterionCreate")
	@ResponseBody
	public String criterionCreate(@RequestParam(value="ctrName", required=true) String ctrName,
			@RequestParam(value="ctrFactory", required=true) String ctrFactory,
			@RequestParam(value="ctrVersion", required=true) String ctrVersion,
			@RequestParam(value="ctrId", required=true) String ctrId,
			@RequestParam(value="ctrLevel", required=true) String ctrLevel,
			@RequestParam(value="certificateId", required=true) String certificateId,
			@RequestParam(value="validTimeInfo", required=true) String validTime){
		
		Criterion criterion = new Criterion();
		criterion.setCtrName(ctrName);
		criterion.setCtrFactory(ctrFactory);
		criterion.setCtrVersion(ctrVersion);
		criterion.setCtrId(ctrId);
		criterion.setCtrLevel(ctrLevel);
		criterion.setCertificateId(certificateId);
		criterion.setValidTime(validTime);
		criterion.setInputTime(new Timestamp(new Date().getTime()));
		
		criterion = criterionService.criterionCreate(criterion);
		
		return "insert";
	}
	
	@RequestMapping("/getCriterionInfoList")
	@ResponseBody
	public JSONArray getCriterionInfoList(){
	
		JSONArray arr= new JSONArray();
		List<Criterion> criterion = new ArrayList<>();
		criterion = criterionService.getCriterionInfoList();
		for(Criterion ctr : criterion){
			String str = "";
			JSONObject obj=new JSONObject();
			obj.put("date",ctr.getInputTime().toString().substring(0, 19));
			obj.put("name", ctr.getCtrName());
			obj.put("id", ctr.getCriterionId());
			obj.put("certificateId", ctr.getCtrId());
			str = (ctr.getCtrName().trim().equals("")?" ":ctr.getCtrName()) + "#@" +
				  (ctr.getCtrFactory().trim().equals("")?" ":ctr.getCtrFactory()) + "#@" +
				  (ctr.getCtrVersion().trim().equals("")?" ":ctr.getCtrVersion()) + "#@" +
				  (ctr.getCtrId().trim().equals("")?" ":ctr.getCtrId()) + "#@" +
				  (ctr.getCtrLevel().trim().equals("")?" ":ctr.getCtrLevel()) + "#@" +
				  (ctr.getCertificateId().trim().equals("")?" ":ctr.getCertificateId()) + "#@"+
				  (ctr.getValidTime().trim().equals("")?" ":ctr.getValidTime()) + "#@" +
				  ctr.getCriterionId() + "#@";
			obj.put("completeInfo", str);
			arr.add(obj);
		}

		return arr;
	}
	
	@RequestMapping("/criterionUpdate")
	@ResponseBody
	public String criterionUpdate(@RequestParam(value="id", required=true) String id,
			@RequestParam(value="ctrName", required=true) String ctrName,
			@RequestParam(value="ctrFactory", required=true) String ctrFactory,
			@RequestParam(value="ctrVersion", required=true) String ctrVersion,
			@RequestParam(value="ctrId", required=true) String ctrId,
			@RequestParam(value="ctrLevel", required=true) String ctrLevel,
			@RequestParam(value="certificateId", required=true) String certificateId,
			@RequestParam(value="validTimeInfo", required=true) String validTime){
		
		Criterion criterion = null;
		String result = "";
		if(id!=null){
			criterion = criterionService.getCriterion(id);
			criterion.setCtrName(ctrName);
			criterion.setCtrFactory(ctrFactory);
			criterion.setCtrVersion(ctrVersion);
			criterion.setCtrId(ctrId);
			criterion.setCtrLevel(ctrLevel);
			criterion.setCertificateId(certificateId);
			criterion.setValidTime(validTime);
			
			criterion = criterionService.criterionUpdate(criterion);
			result = "0";
		}else{
			
			result = "1";
		}
		
		return result;
	}
	
	@RequestMapping("/criterionInfoDelete")
	@ResponseBody
	public String criterionInfoDelete(@RequestParam(value="id", required=false) String id){
		
		criterionService.criterionInfoDelete(id);

		return "1";
		
	}
	
	@RequestMapping("/criterionInfoMatch")
	@ResponseBody
	public JSONArray criterionInfoMatch(@RequestParam(value="ctrId", required=false) String ctrId){
		
		JSONArray arr= new JSONArray();
		List<Object[]> criterionList = criterionService.criterionInfoMatch(ctrId);
		for(Object[] object : criterionList){
			System.out.println(Arrays.asList(object));
			JSONObject obj=new JSONObject();
			obj.put("ctrName",object[0]);
			obj.put("ctrId",object[3]);
			obj.put("date", object[7].toString().substring(0,16));
			String str = "";
			for(int i = 0; i<8; i++){
				str += (!object[i].toString().trim().equals("")?object[i].toString():" ") + "#@";
			}
			obj.put("completeInfo",str);
			
			arr.add(obj);
		}

		return arr;
		
	}
}
