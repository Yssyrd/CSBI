package com.nt.csbi.dao.imp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nt.csbi.dao.DetectionDeviceDao;
import com.nt.csbi.entities.DetectionDevice;
import com.nt.csbi.entities.OnlineDevice;
import com.nt.csbi.entities.RealTimeInfo;
/**
 * @author 杨润东
 *
 * @date: 2017年4月12日 上午10:48:05
 */

@Repository
public class DetectionDeviceDaoImp implements DetectionDeviceDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void addNewDetectionDevice(DetectionDevice device) {
		
		
		
	}

	@Override
	public void updateDetectionDeviceById(String deviceId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<DetectionDevice> detectionDeviceLists(String startId,String endId) {
		
		List list=new ArrayList();
		Query queryObject = null;
		
		String hql = "from DetectionDevice where status >= '0' ";
		
		if(!startId.equals("")&&startId!=null){
			hql += " and cast(detectionDeviceId as integer) >= ? ";
			list.add(Integer.valueOf(startId));
		}
		if(!endId.equals("")&&endId!=null){
			hql += " and cast(detectionDeviceId as integer) <= ? ";
			list.add(Integer.valueOf(endId));
		}
		
		hql += " order by cast(detectionDeviceId as integer)";
		System.out.println(hql);
		 try{
			 queryObject = getSession().createQuery(hql);
			 for(int i=0;i<list.size();i++){
				 queryObject.setParameter(i, list.get(i));
			 }
		 } catch (Exception e){
			   e.printStackTrace();
	     }
		
		List<DetectionDevice> infoList = queryObject.list();
		list = new ArrayList<>();
		return infoList;
	}


	@Override
	public List<DetectionDevice> findDeviceListById(String deviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findDeviceIdById(String deviceId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<DetectionDevice> detectionIdDel(String deviceId) {
		
		DetectionDevice device = (DetectionDevice) getSession().load(DetectionDevice.class,Integer.valueOf(deviceId));
		getSession().delete(device);

		String hql = "from DetectionDevice where status >= '0' order by cast(detectionDeviceId as integer) ";
		Query queryObject = getSession().createQuery(hql);
		
		List<DetectionDevice> infoList = queryObject.list();
		
		return infoList;
	}

	@Override
	public void setDetectionStatusOnline(String[] deviceId) {
		
		String sql = "truncate table online_device";
		getSession().createSQLQuery(sql).executeUpdate();
		
		
		String hql = "update DetectionDevice set status = '0' where status = ? ";
		Query queryObject  = getSession().createQuery(hql).setString(0, "1");
		queryObject.executeUpdate();
		
		for(String id : deviceId){
			if(!id.trim().equals("")&&id.trim()!=null){
				DetectionDevice device = (DetectionDevice) getSession().load(DetectionDevice.class,Integer.valueOf(id));
				device.setStatus("1");
				getSession().save(device);
			}
		}
	}

	@Override
	public void setOnlineDetectionStatusOnline(String[] ids) {
		
		OnlineDevice device = new OnlineDevice();
		for (String id : ids) {
			device = new OnlineDevice();
			device.setDetectionId(id);
			device.setRealTime(new Timestamp(new Date().getTime()));
			device.setStatus("1");
			getSession().save(device);
		}
		
	}

}
