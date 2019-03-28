package com.nt.csbi.dao.imp;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nt.csbi.dao.InspectedDeviceDao;
import com.nt.csbi.entities.InspectedDevice;

/**
 * @author 杨润东
 *
 * @date: 2017年4月12日 下午1:42:49
 */
@Repository
public class InspectedDeviceDaoImp implements InspectedDeviceDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void addNewInspectedDevice(InspectedDevice device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateInspectedDeviceById(String deviceId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<InspectedDevice> inspectedDeviceLists() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InspectedDevice> findInspectedDeviceById(String deviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findInspectedDeviceIdById(String deviceId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
