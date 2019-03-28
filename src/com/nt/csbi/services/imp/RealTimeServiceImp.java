package com.nt.csbi.services.imp;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.csbi.dao.RealTimeInfoDao;
import com.nt.csbi.entities.DetectionDevice;
import com.nt.csbi.entities.RealTimeInfo;
import com.nt.csbi.services.RealTimeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author 杨润东
 *
 * @date: 2017年5月12日 上午9:58:27
 */

@Service
public class RealTimeServiceImp implements RealTimeService {

	@Autowired
	private RealTimeInfoDao realTimeDao;
	
	@Autowired
    private JedisPool jedisPool;//注入JedisPool
	
	@Override
	public List<RealTimeInfo> getRealTimeInfo(String detectionId,String lastTime) {

		
		if(lastTime.equals("")){
			
			lastTime = new Timestamp(new Date().getTime() - 43200000).toString().substring(5, 19);
			
		}
		
		List<RealTimeInfo> infoList = realTimeDao.showRealTimeInfoByDeviceId(detectionId,lastTime);
		
		
		return infoList;
	}

	@Override
	public void addDetection(String detectionId) {
		
		realTimeDao.addDetection(detectionId);
		
	}

	@Override
	public String detectionCharge(String detectionId) {

		DetectionDevice device = realTimeDao.detectionCharge(detectionId);
		
		String result = "";
		
		if(device != null){
			result = "false";
		}
		return result;
		
	}

	@Override
	public JSONArray detectionInfoFresh(String page) {
		
		Jedis jedis = jedisPool.getResource();
		
		JSONArray arr= new JSONArray();
		List<String> devices = jedis.lrange("detectionId", 0, -1);
		List<Integer> nums = new ArrayList<>();
		
		for (String str : devices) {
			nums.add(Integer.parseInt(str));
		}
		long time1 = new Date().getTime();
		List<String> hasData = realTimeDao.getNumHasData();
		System.out.println("size():"+hasData.size());
		Collections.sort(nums);
		for(Integer device : nums){
			JSONObject obj=new JSONObject();
			if(jedis.ttl(device.toString())>=0){
				obj.put("devices", device.toString());
				obj.put("status", "1");
				arr.add(obj);
			}else{
				if(hasData.contains(device.toString())){
					obj.put("devices", device.toString());
					obj.put("status", "2");
					arr.add(obj);
				}else{
					obj.put("devices", device.toString());
					obj.put("status", "0");
					arr.add(obj);
				}
			}
			
		}
		time1 = new Date().getTime() - time1;
		System.out.println("查询用时:"+time1);
		jedisPool.returnResourceObject(jedis);
		return arr;
	}
	


	@Override
	public RealTimeInfo detectionTFInfoFresh(String detectionId) {

		RealTimeInfo infoList = realTimeDao.detectionTFInfoFresh(detectionId);
		
		return infoList;
	}

	@Override
	public void addDetailInfo(RealTimeInfo realTimeInfo) {
		
		realTimeDao.addDetailInfo(realTimeInfo);
		
	}

	@Override
	public List<RealTimeInfo> RealTimeInfoList(String beginDate, String detectionId) {
		List<RealTimeInfo> realtimeInfoList = realTimeDao.RealTimeInfoList(beginDate, detectionId);
		return realtimeInfoList;
	}

	@Override
	public List<Object[]> detections(String beginDate, String regionInfo, String detectionId) {
		
		List<Object[]> detectionIds = realTimeDao.detections(beginDate, regionInfo, detectionId);
		
		return detectionIds;
	}

	@Override
	public List<Object[]> detectionIds(String beginDate, String detectionId, String regionInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFiftyPoints(String timeInterval, String figureBeignTime, String detectionId, String options,
			HttpServletRequest request) {
		String temp = options.substring(0,1);
		
		Timestamp beginTime = Timestamp.valueOf(figureBeignTime);
		Timestamp endTime = null;
		
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
		
		
		List<Object[]> historyInfolist = null;
		
		if(timeInterval.trim().equals("1")){
			
			endTime = new Timestamp(beginTime.getTime()+Long.valueOf(12)*10000*15);
			historyInfolist = realTimeDao.getPointsSustained(beginTime, endTime, detectionId, options);
			
		}else{
			
			endTime = new Timestamp(beginTime.getTime()+Long.valueOf(timeInterval)*10000*15);
			historyInfolist = realTimeDao.getFiftyPoints(beginTime, endTime, detectionId, options);
			
		}
		int size = historyInfolist.size();
		System.out.println("RealTimeSize : "+ size);
		if(size<15){
			
			size = 15-size;
			beginTime = new Timestamp(beginTime.getTime()-Long.valueOf(timeInterval)*10000*size);
			historyInfolist = realTimeDao.getFiftyPoints(beginTime, endTime, detectionId, options);
		
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
					if(info[5]!= null){
						if(!info[5].equals("")){
							inputDIST32C = info[5].toString();
						}
					}
					
				}
				if(temp.equals("1")){
					
					if((count%getDataT32L==0)&&(listSize<7)){
						
						listSize++;
						inputT32L += info[0] + "#@" + info[1] + "#@" + info[2] + "#@" + info[3] + "#@"
								+ info[4] + "#@";
					}
					if(info[5]!= null){
						if(!info[5].equals("")){
							inputDIST32L = info[5].toString();
						}
					}
					
				}
				if(temp.equals("2")){
					
					if((count%getDataT32R==0)&&(listSize<8)){
						
						listSize++;
						inputT32R += info[0] + "#@" + info[1] + "#@" + info[2] + "#@" + info[3] + "#@"
								+ info[4] + "#@";
					}
					if(info[5]!= null){
						if(!info[5].equals("")){
							inputDIST32R = info[5].toString();
						}
					}
				}
				if(temp.equals("3")){
					
					if((count%getDataT36==0)&&(listSize<15)){
						
						listSize++;
						inputT36 += info[0] + "#@" + info[1] + "#@" + info[2] + "#@" + info[3] + "#@"
								+ info[4] + "#@";
					}
					if(info[5]!= null){
						if(!info[5].equals("")){
							inputDIST36 = info[5].toString();
						}
					}
					
				}
				if(AFoptions.equals("4")){
					if(options.length()>1){
						if((count%getDataAF==0)&&(AFSize<3)){
							
							AFSize++;
							inputAF += info[6] + "#@" ;
						}
						if(info[1]!= null){
							if(!info[1].equals("")){
								inputDISRH = info[7].toString();
							}
						}
					}
					if(options.length()==1){
						if(count%getDataAF==0&&(AFSize<3)){
							
							AFSize++;
							inputAF += info[0] + "#@";
						}
						if(info[1]!= null){
							if(!info[1].equals("")){
								inputDISRH = info[1].toString();
							}
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
		
		return realTimeDao.getMaxValue32To36(Tbegin, Tend, detectionId);
	}

	@Override
	public List<Object[]> previousDetections(String beginDate, String detectionId) {
		
		beginDate += " 00:00:00.0";
		
		List<Object[]> historyInfolist = realTimeDao.previousDetections(Timestamp.valueOf(beginDate),detectionId);
		
		return historyInfolist;
	}

	@Override
	public List<Object[]> nextDetections(String beginDate, String detectionId) {
		beginDate += " 23:59:59.0";
		
		List<Object[]> historyInfolist = realTimeDao.nextDetections(Timestamp.valueOf(beginDate),detectionId);
		
		return historyInfolist;
	}

	@Override
	public List<Object[]> matchTableHeadInfo(String tableHeadInfo, String modelId) {
		List<Object[]> lists = realTimeDao.matchTableHeadInfo(tableHeadInfo,modelId);
		List<String> strBool = new ArrayList<>();
		List<Object[]> result = new ArrayList<>(); 
		
		if(!tableHeadInfo.trim().equals("")){
			for(Object[] tmp : lists){
				String [] strArr = tmp[0].toString().split("#@");
				if(strArr!=null){
					if(!strArr[0].trim().equals("")){
						String strTmp =  String.valueOf(strArr[0]);
						if(!strBool.contains(strTmp)&&strArr[0].indexOf(tableHeadInfo)>-1){
							strBool.add(strTmp);
							result.add(tmp);
						}
					}
				}
			}
			lists = new ArrayList<>();
			lists.addAll(result);
		}
		if(!modelId.trim().equals("")){
			System.out.println("modelId:" + modelId);
			result = new ArrayList<>(); 
			for(Object[] tmp : lists){
				String [] strArr = tmp[0].toString().split("#@");
				if(strArr!=null){
					if(!strArr[4].trim().equals("")){
						String strTmp = String.valueOf(strArr[4]);
						if(!strBool.contains(strTmp)&&strArr[4].indexOf(modelId.trim())>-1){
							strBool.add(strTmp);
							result.add(tmp);
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public List<String> getIntervalData(String detectionId, String lastTime) {
		
		List<String> result = realTimeDao.getIntervalData(detectionId, lastTime);
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
	public List<RealTimeInfo> RealTimeInfoListInterval(String inputDate,String intervalTime, String detectionId) {
		
		String[] str = intervalTime.split("--");
		
		List<RealTimeInfo> realtimeInfoList = realTimeDao.RealTimeInfoListInterval(inputDate.trim()+" "+str[0].trim(),
				inputDate.trim()+" "+str[1].trim(), detectionId);
		return realtimeInfoList;
	}

	@Override
	public RealTimeInfo getTableBaseInfo(String detectionId, String lastTime) {
		
		return realTimeDao.getTableBaseInfo(detectionId, lastTime);
	}

	@Override
	public void detailInfoUpdate(String titleDataId, String inspectedIdDetail) {
		
		realTimeDao.detailInfoUpdate(titleDataId, inspectedIdDetail);
	}

	@Override
	public String detectionInfoFreshPageCount() {
		
		Jedis jedis = jedisPool.getResource();
		
		List<String> devices = realTimeDao.detectionInfoFreshPageCount();
		
		Integer count = 0;
		
		for (String str : devices) {
			if(jedis.ttl(str)>=0){
				count++;
			}
		}
		jedisPool.returnResourceObject(jedis);
		
		String result = "";
		
		if(count%8>0){
			result = String.valueOf((count/8)+1);
		}else{
			result = String.valueOf(count/8);
		}
		System.out.println("pageResult:"+result);
		return result;
	}

}
