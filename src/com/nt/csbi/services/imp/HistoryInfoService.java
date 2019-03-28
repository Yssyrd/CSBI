package com.nt.csbi.services.imp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.csbi.dao.HistoryInfoDao;
import com.nt.csbi.entities.HistoryInfo;
import com.nt.csbi.entities.RealTimeInfo;
import com.nt.csbi.services.HistoryInfoServices;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 杨润东
 *
 * @date: 2017年4月21日 下午3:37:47
 */
@Service
public class HistoryInfoService implements HistoryInfoServices {

	@Autowired
	private HistoryInfoDao historyInfoDao;
	
	@Override
	public List<HistoryInfo> HistoryInfoListService(String beginDate,String endDate,String detectionId){
		// TODO Auto-generated method stub

		if(beginDate==null||beginDate.equals("")){
			beginDate =String.valueOf(new Timestamp(new Date().getTime())).substring(0,10); 
		}
		
		if(endDate.equals("")||endDate==null){
			endDate =String.valueOf(new Timestamp(new Date().getTime())).substring(0,10); 
		}
		
		beginDate += " 00:00:00.0";
		endDate += " 23:59:59.0";
		
		
		List<HistoryInfo> historyInfolist = historyInfoDao.searchHistoryInfoList(Timestamp.valueOf(beginDate),
				Timestamp.valueOf(endDate), detectionId);
		
		return historyInfolist;
		
	}
	
	@Override
	public List<Object[]> dates(String detectionId) {
		
		List<Object[]> historyInfolist = historyInfoDao.getDates(detectionId);
		
		return historyInfolist;
	}
	
	@Override
	public List<Object[]> detectionIds(String beginDate, String endDate, String detectionId) {
		
		
		List<Object[]> historyInfolist = historyInfoDao.getDetectionIds(beginDate,endDate, detectionId);
		
		return historyInfolist;
		
	}

	@Override
	public String getFiftyPoints(String timeInterval, String figureBeignTime, String detectionId,
			String options,HttpServletRequest request) {
		
		String temp = options.substring(0,1);
		Timestamp beginTime = Timestamp.valueOf(figureBeignTime);
		
		Timestamp endTime = new Timestamp(beginTime.getTime()+Long.valueOf(timeInterval)*10000*15);
		
		String inputT32C = "";
		String inputT32L = "";
		String inputT32R = "";
		String inputT36 = "";
		String inputAF = "";
		
		String inputDIST32C = "";
		String inputDIST32L = "";
		String inputDIST32R = "";
		String inputDIST36 = "";
		String inputDISRH = "";
		
		String result = "";
		
		List<Object[]> historyInfolist = historyInfoDao.getFiftyPoints(beginTime, endTime, detectionId, options);
		
		int size = historyInfolist.size();
		
		
		if(size<15){
			
			size = 15-size;
			beginTime = new Timestamp(beginTime.getTime()-Long.valueOf(timeInterval)*10000*size);
			historyInfolist = historyInfoDao.getFiftyPoints(beginTime, endTime, detectionId, options);
		
		}
		size = historyInfolist.size();
		if(size<15){
			return "false";
		}else{
			
			String AFoptions = options.substring(options.length()-1,options.length());
			
			int count = 0;
			int listSize = 0;
			int AFSize = 0;
			
			int getDataT32C = size/15;
			int getDataT32L = size/7;
			int getDataT32R = size/8;
			int getDataT36 = size/15;
			int getDataAF = size/3;
			
			for(Object[] info:historyInfolist){
				
				if(temp.equals("0")){
					
					if((count%getDataT32C==0)&&(listSize<15)){
						listSize++;
						inputT32C += info[0] + "#@" + info[1] + "#@" + info[2] + "#@" + info[3] + "#@"
								+ info[4] + "#@";
					}
					if(info[5].toString().equals("0.00")==false){
						inputDIST32C = info[5].toString();
					}
					
					
				}
				if(temp.equals("1")){
					
					if((count%getDataT32L==0)&&(listSize<7)){
						
						listSize++;
						inputT32L += info[0] + "#@" + info[1] + "#@" + info[2] + "#@" + info[3] + "#@"
								+ info[4] + "#@";
					}
					if(info[5].toString().equals("0.00")==false){
						inputDIST32L = info[5].toString();
					}
					
				}
				if(temp.equals("2")){
					
					if((count%getDataT32R==0)&&(listSize<8)){
						
						listSize++;
						inputT32R += info[0] + "#@" + info[1] + "#@" + info[2] + "#@" + info[3] + "#@"
								+ info[4] + "#@";
					}
					if(info[5].toString().equals("0.00")==false){
						inputDIST32R = info[5].toString();
					}
				}
				if(temp.equals("3")){
					
					if((count%getDataT36==0)&&(listSize<15)){
						
						listSize++;
						inputT36 += info[0] + "#@" + info[1] + "#@" + info[2] + "#@" + info[3] + "#@"
								+ info[4] + "#@";
					}
					if(info[5].toString().equals("0.00")==false){
						inputDIST36 = info[5].toString();
					}
					
				}
				if(AFoptions.equals("4")){
					if(options.length()>1){
						if((count%getDataAF==0)&&(AFSize<3)){
							
							AFSize++;
							inputAF += info[6] + "#@" ;
						}
						if(info[1].toString().equals("0.00")==false){
							inputDISRH = info[7].toString();
						}
					}
					if(options.length()==1){
						if(count%getDataAF==0&&(AFSize<3)){
							
							AFSize++;
							inputAF += info[0] + "#@";
						}
						if(info[1].toString().equals("0.00")==false){
							
							inputDISRH = info[1].toString();
						}
					}
					
				}
				count++;
			}
			
			if(temp.equals("0")){
				request.getSession().setAttribute("inputT32C", inputT32C);
				request.getSession().setAttribute("inputDIST32C", inputDIST32C);
				result += inputDIST32C + "#@" +inputT32C;
			}
			if(temp.equals("1")){
				request.getSession().setAttribute("inputT32L", inputT32L);
				request.getSession().setAttribute("inputDIST32L", inputDIST32L);
				result += inputDIST32L + "#@" +inputT32L;
			}
			if(temp.equals("2")){
				request.getSession().setAttribute("inputT32R", inputT32R);
				request.getSession().setAttribute("inputDIST32R", inputDIST32R);
				result += inputDIST32R + "#@" +inputT32R;
			}
			if(temp.equals("3")){
				request.getSession().setAttribute("inputT36", inputT36);
				request.getSession().setAttribute("inputDIST36", inputDIST36);
				result += inputDIST36 + "#@" +inputT36;
				System.out.println("=====================================:"+result);
			}
			if(AFoptions.equals("4")){
				request.getSession().setAttribute("inputAF", inputAF);
				request.getSession().setAttribute("inputDISRH", inputDISRH);
				result += "&*" + inputDISRH + "#@" +inputAF;
			}
			
		}
		
		return result;
	}

	@Override
	public String getMaxValue32To36(String Tbegin, String Tend, String detectionId) {
		
		return historyInfoDao.getMaxValue32To36(Tbegin, Tend, detectionId);
	}

	@Override
	public List<Object[]> previousDetections(String beginDate, String detectionId) {
		
		beginDate += " 00:00:00.0";
		
		List<Object[]> historyInfolist = historyInfoDao.previousDetections(Timestamp.valueOf(beginDate),detectionId);
		
		return historyInfolist;
	}

	@Override
	public List<Object[]> nextDetections(String beginDate, String detectionId) {
		
		beginDate += " 23:59:59.0";
		
		List<Object[]> historyInfolist = historyInfoDao.nextDetections(Timestamp.valueOf(beginDate),detectionId);
		
		return historyInfolist;
		
	}

	@Override
	public List<HistoryInfo> HistoryInfoListInterval(String inputDate, String intervalTime, String detectionId) {
		String[] str = intervalTime.split("--");
		
		List<HistoryInfo> realtimeInfoList = historyInfoDao.searchHistoryInfoList(Timestamp.valueOf(inputDate.trim()+" "+str[0].trim()),
				Timestamp.valueOf(inputDate.trim()+" "+str[1].trim()), detectionId);
		return realtimeInfoList;
	}

	@Override
	public List<String> getIntervalData(String detectionId, String lastTime) {
		List<String> result = historyInfoDao.getIntervalData(detectionId, lastTime);
		List<String> finalStr = new ArrayList<>();
		String Str = "00:00:00";
 		String last = "";
 		int i = 0;
		for(String tmp : result){
			if(i == 0){
				last = tmp;
			}else{
				if(!tmp.equals(Str)){
					finalStr.add(Str+"--" + tmp);
					Str = last;
					last = tmp;
				}
			}
			i++;
		}
		finalStr.add(Str+"--23:59:59");
		System.out.println("finalStr: "+finalStr.size());
		if(finalStr.size()<2){
			finalStr = new ArrayList<>();
		}
		return finalStr;
	}

	@Override
	public JSONArray dateAndId(String beginDate, String detectionId, String dataSource) {
		
		List<Object[]> detectionIds = historyInfoDao.dateAndId(beginDate, detectionId, dataSource);
		
		JSONArray arr= new JSONArray();
		
		for(Object[] object : detectionIds){
			JSONObject obj=new JSONObject();
			obj.put("date",object[0]);
			obj.put("id", object[1]);
			arr.add(obj);
		}
		
		return arr;
	}

	@Override
	public String syncData() {
		
		List<Object[]> arr= historyInfoDao.syncData();
		String str = "";
		Set set = new TreeSet();
		for (Object[] obj : arr) {
			str = obj[1] + ","  + obj[0] + ","+ obj[2];
			set.add(str);
		}
		
		String[] strArr = (String[]) set.toArray(new String[0]);
		
		historyInfoDao.saveDateSave(strArr);
		return "ok";
	}

	@Override
	public String deleteData(JSONArray json) {
		JSONObject obj = null;
		Integer num = 0;
		Integer [] ids = new Integer[json.size()];
		for (int i = 0; i < json.size(); i++) {
			obj = json.getJSONObject(i);
			if(obj.get("source").equals("0")){
				num += historyInfoDao.deteleDataFromHistory(obj.get("date").toString(),
						obj.get("dId").toString());
				System.out.println("History:"+num);
			}
			if(obj.get("source").equals("1")){
				num += historyInfoDao.deteleDataFromRealTime(obj.get("date").toString(),
						obj.get("dId").toString());
				System.out.println("realTime:"+num);
			}
			ids[i] = Integer.valueOf(obj.getString("id"));
		}
		
		historyInfoDao.deleteSaveDate(ids);
		
		return num.toString();
	}

	@Override
	public JSONArray getDateIds(JSONObject json) {
		
		List<Object[]> arr= historyInfoDao.AllDateAndId(json.getString("date"),
				json.getString("did"),"");
		System.out.println(arr.size());
		JSONArray array = new JSONArray();
		
		for (Object[] obj : arr) {
			JSONObject data = new JSONObject();
			data.put("date", obj[0]);
			data.put("dId", obj[1]);
			data.put("source", obj[2]);
			data.put("id", obj[3]);
			array.add(data);
		}
		
		return array;
	}

}
