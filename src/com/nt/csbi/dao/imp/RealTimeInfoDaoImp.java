package com.nt.csbi.dao.imp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.Queryable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nt.csbi.dao.RealTimeInfoDao;
import com.nt.csbi.entities.DetectionDevice;
import com.nt.csbi.entities.HistoryInfo;
import com.nt.csbi.entities.RealTimeInfo;

/**
 * @author 杨润东
 *
 * @date: 2017年4月12日 下午1:44:45
 */
@Repository
public class RealTimeInfoDaoImp implements RealTimeInfoDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<RealTimeInfo> showRealTimeInfoByDeviceId(String deviceId,String lastTime) {
		
		String hql = " from RealTimeInfo where AT>10 and AT <80 and BT>10 and BT<80 and CT>10 and CT<80 "
				+ " and DT>10 and DT<80 and ET>10 and ET<80 and AF >0 and AF<100 and "
				+ " detectionId  = ? and inputDate >? order by inputDate asc ";
		
		lastTime = (new Timestamp(new Date().getTime())).toString().substring(0, 5) + lastTime;
		System.out.println(lastTime);
		
		
		
		Query queryObject = getSession().createQuery(hql).setString(0, deviceId)
				.setTimestamp(1, Timestamp.valueOf(lastTime));
		
		List<RealTimeInfo> infoList = queryObject.list();
		
		return infoList;
		
	}

	@Override
	public List<Object[]> FindRealTimeInfoByInspectedId(String InspectedId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addDetection(String detectionId) {
		
		DetectionDevice device = new DetectionDevice();
		device.setDetectionDeviceLoginDate(new Timestamp(new Date().getTime()));
		device.setDetectionDeviceId(detectionId);
		device.setStatus("0");
		getSession().save(device);
		
	}

	@Override
	public DetectionDevice detectionCharge(String detectionId) {
		
		String hql = "from DetectionDevice where detectionDeviceId = ? ";
		
		Query queryObject = getSession().createQuery(hql).setString(0, detectionId);
		
		DetectionDevice device = (DetectionDevice) queryObject.uniqueResult();
		
		return device;
		
	}

	@Override
	public List<String> detectionInfoFresh(String page) {
		
		String hql = "select detectionId from OnlineDevice "
				+ " order by cast(detectionId as integer) ";
		Query queryObject = getSession().createQuery(hql);
		List<String> devices = queryObject.list();
		
		return devices;
	}

	@Override
	public RealTimeInfo detectionTFInfoFresh(String detectionId) {
		
		String hql = "from RealTimeInfo a where a.inputDate = "
				+ " (select max(b.inputDate) from RealTimeInfo b where b.detectionId = ? ) "
				+ " and a.detectionId = ? ";
		Query queryObject = getSession().createQuery(hql);
		RealTimeInfo device = (RealTimeInfo) queryObject.setString(0, detectionId)
				.setString(1, detectionId).uniqueResult();
		return device;
	}

	@Override
	public void addDetailInfo(RealTimeInfo realTimeInfo) {
		
		getSession().update(realTimeInfo);
		getSession().flush();
		getSession().clear();
		
	}

	@Override
	public List<RealTimeInfo> RealTimeInfoList(String beginDate, String detectionId) {
		
		String hql = "from RealTimeInfo where AT>10 and AT <80 and BT>10 and BT<80 and CT>10 and CT<80 "
				+ " and DT>10 and DT<80 and ET>10 and ET<80 and AF >0 and AF<100 and "
				+ " recordTime > ? and recordTime < ? ";
		List<RealTimeInfo> historyInfoList = new ArrayList<>();
		List list=new ArrayList();
		Query queryObject = null;
		
		
		list.add(Timestamp.valueOf(beginDate+" 00:00:00"));
		list.add(Timestamp.valueOf(beginDate+" 23:59:59"));
		
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
		 } catch (Exception e){
			   e.printStackTrace();
	     }
		
		historyInfoList = queryObject.list();
		list = new ArrayList<>();
		
		return historyInfoList;
	}

	@Override
	public List<Object[]> detections(String beginDate, String regionInfo, String detectionId) {
		String hql = "select distinct detectionId,date_format(recordTime,'%Y-%m-%d') from RealTimeInfo "
				+ " where recordTime is not null ";
		List<Object[]> detectionIds = new ArrayList<>();
		List list=new ArrayList();
		Query queryObject = null;
		
		if(!beginDate.equals("")&&beginDate!=null){
			hql += " and recordTime> ? and recordTime < ? ";
			list.add(Timestamp.valueOf(beginDate+" 00:00:00"));
			list.add(Timestamp.valueOf(beginDate+" 23:59:59"));
		}
		
		if(!detectionId.equals("")&&detectionId!=null){
			hql += " and detectionId like ? ";
			list.add("%"+detectionId+"%");
		}
		
		if(!regionInfo.equals("")&&regionInfo!=null){
			hql += " and regionInfo like ? ";
			list.add("%"+regionInfo+"%");
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
	public List<Object[]> detectionIds(String beginDate, String detectionId, String regionInfo) {
		
		String hql = "select distinct detectionId from RealTimeInfo "
				+ " where  date_format(recordTime,'yyyy-MM-dd') like ? ";
		List<Object[]> detectionIds = new ArrayList<>();
		List list=new ArrayList();
		Query queryObject = null;
		
		list.add("%"+beginDate+"%");
		
		if(!detectionId.equals("")&&detectionId!=null){
			hql += " and detectionId like ? ";
			list.add("%"+detectionId+"%");
		}
		
		if(!regionInfo.equals("")&&regionInfo!=null){
			hql += " and regionInfo like ? ";
			list.add("%"+regionInfo+"%");
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
		
		hql += " from RealTimeInfo where recordTime>= ? and recordTime <= ?  and detectionId = ? order by recordTime asc";
		
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
		
		String hql = " select max(AT) from RealTimeInfo where recordTime>= ? and recordTime <= ? "
				+ "and detectionId = ? ";
		if(time1.getTime()>time2.getTime()){
			queryObject = getSession().createQuery(hql).setTimestamp(0,Timestamp.valueOf(Tend))
					.setTimestamp(1, Timestamp.valueOf(Tbegin)).setString(2, detectionId);
		}else{
			queryObject = getSession().createQuery(hql).setTimestamp(0,Timestamp.valueOf(Tbegin))
					.setTimestamp(1, Timestamp.valueOf(Tend)).setString(2, detectionId);
		}
		return (String) queryObject.uniqueResult();
	}

	@Override
	public List<Object[]> getPointsSustained(Timestamp BeignTime, Timestamp endTime, String detectionId,
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
		
		hql += " from RealTimeInfo where recordTime>= ? and recordTime <= ?  and detectionId = ? order by recordTime asc";
		
		Query queryObject = getSession().createQuery(hql).setTimestamp(0,BeignTime)
				.setTimestamp(1, endTime).setMaxResults(15).setString(2, detectionId);
		
		List<Object[]> historyInfolist = queryObject.list();
		
		return historyInfolist;
	}

	@Override
	public List<Object[]> previousDetections(Timestamp beginDate, String deviceId) {
		
		String hql = "select distinct date_format(recordTime,'%Y-%m-%d'), detectionId from RealTimeInfo "
				+ "where detectionId = ? and recordTime < ? order by recordTime desc ";
		List list=new ArrayList();
		Query queryObject = getSession().createQuery(hql).setString(0, deviceId).setTimestamp(1, beginDate)
				.setMaxResults(1);
		
		List<Object[]> detectionIds = queryObject.list();
		
		return detectionIds;
	}

	@Override
	public List<Object[]> nextDetections(Timestamp beginDate, String deviceId) {
		
		String hql = "select distinct date_format(recordTime,'%Y-%m-%d'), detectionId from RealTimeInfo "
				+ "where detectionId = ? and recordTime > ? order by recordTime asc ";
		Query queryObject = getSession().createQuery(hql).setString(0, deviceId).setTimestamp(1, beginDate)
				.setMaxResults(1);
		
		List<Object[]> detectionIds = queryObject.list();
		
		return detectionIds;
	}

	@Override
	public List<Object[]> matchTableHeadInfo(String tableHeadInfo, String modelId) {
		String hql = " select distinct detectionIdDetail,inputDate  from RealTimeInfo where ";
		
		List list=new ArrayList();
		Query queryObject = null;
		
		if(!tableHeadInfo.equals("")&&tableHeadInfo!=null){
			hql += " detectionIdDetail like ? and ";
			list.add("%" + tableHeadInfo.trim() + "%");
		}
		if(!modelId.equals("")&&modelId!=null){
			hql += " detectionIdDetail like ? and ";
			list.add("%" + modelId.trim() + "%");
		}
		
		hql = hql.substring(0,hql.length()-4);
		
		hql += " order by inputDate desc ";
		
		 try{
			 queryObject = getSession().createQuery(hql);
			 for(int i=0;i<list.size();i++){
				 queryObject.setParameter(i, list.get(i));
			 }
		 } catch (Exception e){
			   e.printStackTrace();
	     }
		
		List<Object[]> result = queryObject.list();
		
		return result;
	}

	@Override
	public List<String> getIntervalData(String detectionId, String lastTime) {
		String hql = "select date_format(recordTime,'%H:%i:%s') from RealTimeInfo "
				+ " where (detectionIdDetail is not null or regionInfo is not null) ";
		List<String> interval = new ArrayList<>();
		List list=new ArrayList();
		Query queryObject = null;
		
		if(!lastTime.equals("")&&lastTime!=null){
			hql += " and recordTime> ? and recordTime < ? ";
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
		System.out.println("lastTime: "+lastTime);
		return interval;
	}

	@Override
	public List<RealTimeInfo> RealTimeInfoListInterval(String beginTime, String endTime, String detectionId) {
		String hql = "from RealTimeInfo where AT>10 and AT <80 and BT>10 and BT<80 and CT>10 and CT<80 "
				+ " and DT>10 and DT<80 and ET>10 and ET<80 and AF >0 and AF<100 and "
				+ " recordTime >= ? and recordTime <= ? ";
		List<RealTimeInfo> historyInfoList = new ArrayList<>();
		List list=new ArrayList();
		Query queryObject = null;
		
		list.add(Timestamp.valueOf(beginTime));
		list.add(Timestamp.valueOf(endTime));
		
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
		 } catch (Exception e){
			   e.printStackTrace();
	     }
		
		historyInfoList = queryObject.list();
		list = new ArrayList<>();
		
		return historyInfoList;
	}

	@Override
	public RealTimeInfo getTableBaseInfo(String detectionId, String lastTime) {
		
		String hql = "from RealTimeInfo where detectionId = ? and inputDate = ? and "
				+ "(detectionIdDetail is not null or regionInfo is not null )";
		System.out.println("lastTime1: "+lastTime);
		lastTime = (new Timestamp(new Date().getTime())).toString().substring(0, 5) + lastTime;
		System.out.println("lastTime2: "+lastTime);
		Query queryObject = getSession().createQuery(hql).setString(0, detectionId)
				.setTimestamp(1, Timestamp.valueOf(lastTime)).setMaxResults(1);
		
		RealTimeInfo info = (RealTimeInfo) queryObject.uniqueResult();
		return info;
	}

	@Override
	public void detailInfoUpdate(String titleDataId, String inspectedIdDetail) {
		
		RealTimeInfo realTimeInfo = (RealTimeInfo) getSession().get(RealTimeInfo.class,Integer.valueOf(titleDataId));
		realTimeInfo.setDetectionIdDetail(inspectedIdDetail);
		
		getSession().update(realTimeInfo);
		getSession().flush();
		getSession().clear();
		
	}

	@Override
	public List<String> detectionInfoFreshPageCount() {
		String hql = "select o.detectionId from OnlineDevice o,DetectionDevice d where o.status = '1' "
				+ " and d.detectionDeviceId = o.detectionId and d.status = '1' order by o.detectionId ";
		Query queryObject = getSession().createQuery(hql);
		List<String> devices = queryObject.list();
		
//		String result = String.valueOf((devices.size()/8)+1);
		
		return devices;
	}

	@Override
	public List<String> getNumHasData() {
		Timestamp date = new Timestamp(new Date().getTime() - 10800000);
		
		String hql = "select detectionId from RealTimeInfo where inputDate > ? group by detectionId ";
		
		return getSession().createQuery(hql).setTimestamp(0, date).list();
	}
	
}
