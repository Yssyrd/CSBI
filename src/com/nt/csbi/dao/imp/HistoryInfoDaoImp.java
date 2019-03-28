package com.nt.csbi.dao.imp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nt.csbi.dao.HistoryInfoDao;
import com.nt.csbi.entities.DateSave;
import com.nt.csbi.entities.HistoryInfo;
import com.nt.csbi.entities.RealTimeInfo;

/**
 * @author 杨润东
 *
 * @date: 2017年4月12日 上午9:33:10
 */
@Repository
public class HistoryInfoDaoImp implements HistoryInfoDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	
	@Override
	public List<Object[]> showHistoryInfoByDeviceId(String deviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> FindHistoryInfoByInspectedId(String InspectedId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void saveHistoryInfo(List<HistoryInfo> historyInfoList) {
		
		Set set = new TreeSet();
		String str = "";
		
		int i = 0;
		for(HistoryInfo historyInfo:historyInfoList){
			
			str = historyInfo.getDetectionId() + "," 
					+ historyInfo.getRecordTime().toString().substring(0, 10) + ",0";
			set.add(str);
			getSession().save(historyInfo);
			i++; 	
			if(i%20==0){
				getSession().flush();
				getSession().clear();
				i=0;
			}
			
		}
		addDateIdStr((String[]) set.toArray(new String[0]));
		
		getSession().flush();
		getSession().clear();
	}


	@Override
	public List<HistoryInfo> searchHistoryInfoList(Timestamp beginDate, Timestamp endDate, String deviceId) {

		String hql = "from HistoryInfo where AT>10 and AT <80 and BT>10 and BT<80 and CT>10 and CT<80 "
				+ " and DT>10 and DT<80 and ET>10 and ET<80 and AF >0 and AF<100 and recordTime >= ? and recordTime <= ? ";
		List<HistoryInfo> historyInfoList = new ArrayList<>();
		List list=new ArrayList();
		Query queryObject = null;
		
		list.add(beginDate);
		list.add(endDate);
		
		if(!deviceId.equals("")&&deviceId!=null){
			hql += " and detectionId like ? ";
			list.add("%"+deviceId+"%");
		}
		
		hql += " order by recordTime asc ";
		
		 try{
			 queryObject = getSession().createQuery(hql);
			 for(int i=0;i<list.size();i++){
				 queryObject.setParameter(i, list.get(i));
			 }
		 } catch (Exception e){
			   e.printStackTrace();
	     }
		
		historyInfoList = queryObject.list();
		list = new ArrayList<>();
		 
		return historyInfoList;
	}
	
	@Override
	public List<Object[]> getDates(String deviceId) {
		
		String hql = "select distinct date_format(recordTime,'%Y-%m-%d'), detectionId from HistoryInfo "
				+ "where detectionId like ? order by recordTime desc ";
		List list = new ArrayList();
		Query queryObject = getSession().createQuery(hql).setString(0, "%"+deviceId+"%");
		
		List<Object[]> detectionIds = queryObject.list();
		
		return detectionIds;
	}

	@Override
	public List<Object[]> getDetectionIds(String beginDate, String endDate, String deviceId) {
		
		String hql = "select distinct detectionId,date_format(recordTime,'%Y-%m-%d') from HistoryInfo where recordTime is not null ";
		List<Object[]> detectionIds = new ArrayList<>();
		List list=new ArrayList();
		Query queryObject = null;
		
		if(!beginDate.equals("")&&beginDate!=null){
			hql += " and recordTime>= ? and recordTime <= ? ";
			list.add(Timestamp.valueOf(beginDate+" 00:00:00"));
			list.add(Timestamp.valueOf(beginDate+" 23:59:59"));
		}
		
		if(!deviceId.equals("")&&deviceId!=null){
			hql += " and detectionId like ? ";
			list.add("%"+deviceId+"%");
		}
		
		hql += " order by recordTime desc ";
		
		try{
			 queryObject = getSession().createQuery(hql);
			 for(int i=0;i<list.size();i++){
				 queryObject.setParameter(i, list.get(i));
			 }
		 } catch (Exception e){
			   e.printStackTrace();
	     }
		
		detectionIds = queryObject.list();
		
		list = new ArrayList<>();
		
		return detectionIds;
	}


	@Override
	public List<Object[]> getFiftyPoints(Timestamp BeignTime, Timestamp endTime, String detectionId,
			String options) {
		
		String hql = "select";
		
		for(int i =0;i<options.length();i++){
		
			if(Integer.valueOf(options.substring(i,i+1))<4){
				hql	+= " AT,BT,CT,DT,ET,DIST";
			}	
				
			if(options.substring(i,i+1).equals("4")){
				
				if(options.length()>1){
					hql += " ,AF,DISRH";
				}else{
					hql += " AF,DISRH";
				}
				
			}
		}
		
		hql += " from HistoryInfo where recordTime>= ? and recordTime <= ?  and detectionId = ? order by recordTime asc";
		
		Query queryObject = getSession().createQuery(hql).setTimestamp(0,BeignTime)
				.setTimestamp(1, endTime).setString(2, detectionId);
		
		List<Object[]> historyInfolist = queryObject.list();
		
		return historyInfolist;
	}


	@Override
	public String getMaxValue32To36(String Tbegin, String Tend, String detectionId) {
		
		Timestamp time1= Timestamp.valueOf(Tbegin);
		Timestamp time2= Timestamp.valueOf(Tend);
		Query queryObject = null;
		
		String hql = " select max(AT) from HistoryInfo where recordTime>= ? and recordTime <= ? "
				+ "and detectionId = ? ";
		if(time1.getTime()>time2.getTime()){
			queryObject = getSession().createQuery(hql).setTimestamp(0,Timestamp.valueOf(Tend))
					.setTimestamp(1, Timestamp.valueOf(Tbegin)).setString(2, detectionId);
		}else{
			queryObject = getSession().createQuery(hql).setTimestamp(0,Timestamp.valueOf(Tbegin))
					.setTimestamp(1, Timestamp.valueOf(Tend)).setString(2, detectionId);
		}
		System.out.println((String) queryObject.uniqueResult());
		return (String) queryObject.uniqueResult();
	}


	@Override
	public List<Object[]> previousDetections(Timestamp beginDate, String deviceId) {
	
		String hql = "select distinct date_format(recordTime,'%Y-%m-%d'), detectionId from HistoryInfo "
				+ "where detectionId = ? and recordTime < ? order by recordTime desc ";
		List list=new ArrayList();
		Query queryObject = getSession().createQuery(hql).setString(0, deviceId).setTimestamp(1, beginDate)
				.setMaxResults(1);
		
		List<Object[]> detectionIds = queryObject.list();
		
		return detectionIds;
	}

	@Override
	public List<Object[]> nextDetections(Timestamp beginDate, String deviceId) {
		
		String hql = "select distinct date_format(recordTime,'%Y-%m-%d'), detectionId from HistoryInfo "
				+ "where detectionId = ? and recordTime > ? order by recordTime asc ";
		List list=new ArrayList();
		Query queryObject = getSession().createQuery(hql).setString(0, deviceId).setTimestamp(1, beginDate)
				.setMaxResults(1);
		
		List<Object[]> detectionIds = queryObject.list();
		
		return detectionIds;
	}

	@Override
	public List<String> getIntervalData(String detectionId, String lastTime) {
		String hql = "select date_format(recordTime,'%H:%i:%s') from HistoryInfo "
				+ " where inspectedId != '0' ";
		List<String> interval = new ArrayList<>();
		List list=new ArrayList();
		Query queryObject = null;
		
		if(!lastTime.equals("")&&lastTime!=null){
			hql += " and recordTime>= ? and recordTime <= ? ";
			list.add(Timestamp.valueOf(lastTime+" 00:00:00"));
			list.add(Timestamp.valueOf(lastTime+" 23:59:59"));
		}
		
		if(!detectionId.equals("")&&detectionId!=null){
			hql += " and detectionId like ? ";
			list.add("%"+detectionId+"%");
		}
		
		hql += " order by recordTime asc ";
		
		try{
			 queryObject = getSession().createQuery(hql);
			 for(int i=0;i<list.size();i++){
				 queryObject.setParameter(i, list.get(i));
			 }
		}catch (Exception e){
			   e.printStackTrace();
	    }
		
		interval = queryObject.list();
		
		list = new ArrayList<>();
		return interval;
	}


	@Override
	public List<Object[]> dateAndId(String beginDate,String detectionId, String dataSource) {
		
		String hql = "select dateStr,detectionId from DateSave"
				+ "  where sourceId = ? ";
		List<Object[]> detectionIds = new ArrayList<>();
		List list=new ArrayList();
		Query queryObject = null;
		list.add(dataSource);
		if(!beginDate.equals("")&&beginDate!=null){
			hql += " and dateStr = ? ";
			list.add(beginDate);
		}
		
		if(!detectionId.equals("")&&detectionId!=null){
			hql += " and detectionId like ? ";
			list.add("%"+detectionId+"%");
		}
		
		hql += " order by  dateStr desc ";
		
		try{
			 queryObject = getSession().createQuery(hql);
			 for(int i=0;i<list.size();i++){
				 queryObject.setParameter(i, list.get(i));
			 }
		}catch (Exception e){
			   e.printStackTrace();
	    }
		
		return queryObject.list() ;
	}


	@Override
	public void saveDateSave(String[] arr) {
		
		String hql = "TRUNCATE TABLE DateSave";
		getSession().createSQLQuery(hql).executeUpdate();
		getSession().flush();
		getSession().clear();
		
		String[] dateId = null;
		for (String date : arr) {
			System.out.println(date);
			dateId = date.split(",");
			DateSave save = new DateSave();
			save.setDateStr(dateId[1]);
			save.setDetectionId(dateId[0]);
			save.setSourceId(dateId[2]);
			getSession().save(save);
		}
	}


	@Override
	public List<Object[]> syncData() {
		
		String hql = "select distinct date_format(recordTime,'%Y-%m-%d'),detectionId,0 "
				+ " from HistoryInfo where recordTime is not null order by recordTime desc ";
		Query queryObject = getSession().createQuery(hql);
		
		List<Object[]> detectionIds = queryObject.list();
		
		hql = "select distinct date_format(recordTime,'%Y-%m-%d'),detectionId,1 "
				+ " from RealTimeInfo where recordTime is not null order by recordTime desc ";
		
		detectionIds.addAll(getSession().createQuery(hql).list());
		
		return detectionIds;
		
	}


	@Override
	public Integer deteleDataFromHistory(String date, String id) {
		
		String hql = " delete from HistoryInfo where recordTime >=? and "
				+ " recordTime<=? and detectionId =? ";
		Query queryObject =getSession().createQuery(hql);
		queryObject.setTimestamp(0, Timestamp.valueOf(date.trim()+" 00:00:00"));
		queryObject.setTimestamp(1, Timestamp.valueOf(date.trim()+" 23:59:59"));
		queryObject.setString(2, id);
		Integer num = queryObject.executeUpdate();
		
		if((int)(1+Math.random()*10)>7){
			getSession().flush();
			getSession().clear();
		}
		return num;
	}


	@Override
	public Integer deteleDataFromRealTime(String date, String id) {
		
		String hql = " delete from RealTimeInfo where recordTime >=? and "
				+ " recordTime<=? and detectionId =? ";
		Query queryObject =getSession().createQuery(hql);
		queryObject.setTimestamp(0, Timestamp.valueOf(date.trim()+" 00:00:00"));
		queryObject.setTimestamp(1, Timestamp.valueOf(date.trim()+" 23:59:59"));
		queryObject.setString(2, id);
		Integer num = queryObject.executeUpdate();
		
		if((int)(1+Math.random()*10)>7){
			getSession().flush();
			getSession().clear();
		}
		return num;
		
	}


	@Override
	public List<Object[]> AllDateAndId(String date,String detectionId,String source) {
		String hql = "select dateStr,detectionId,sourceId,id from DateSave "
				+ " where detectionId is not null ";
		
		List<Object[]> detectionIds = new ArrayList<>();
		List list=new ArrayList();
		Query queryObject = null;
		if(!date.equals("")&&date!=null){
			hql += " and dateStr = ? ";
			list.add(date);
		}
		
		if(!detectionId.equals("")&&detectionId!=null){
			hql += " and detectionId = ? ";
			list.add(detectionId);
		}
		
		if(!source.equals("")&&source!=null){
			hql += " and sourceId = ? ";
			list.add(detectionId);
		}
		
		hql += " order by dateStr asc ";
		
		try{
			 queryObject = getSession().createQuery(hql);
			 for(int i=0;i<list.size();i++){
				 queryObject.setParameter(i, list.get(i));
			 }
		}catch (Exception e){
			   e.printStackTrace();
	    }
		
		return queryObject.list();
	}


	@Override
	public void deleteSaveDate(Integer[] ids) {
		
		String hql = " delete from DateSave where id in :ids ";
		Query queryObject =getSession().createQuery(hql);
		queryObject.setParameterList("ids", ids);
		queryObject.executeUpdate();
		
		getSession().flush();
		getSession().clear();
	}


	@Override
	public void addDateIdStr(String[] arr) {
		
		String[] dateId = null;
		boolean flag = true;
		for (String str : arr) {
			flag = true;
			dateId = str.split(",");
			List<Object[]> allDateAndId = AllDateAndId(dateId[1], dateId[0],"0");
			if(allDateAndId.size()>0){
				flag = false;
			}
			if(flag){
				DateSave save = new DateSave();
				save.setDateStr(dateId[1]);
				save.setDetectionId(dateId[0]);
				save.setSourceId(dateId[2]);
				getSession().save(save);
			}
		}
		getSession().flush();
		getSession().clear();
	}

}
