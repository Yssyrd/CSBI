package com.nt.csbi.dao.imp;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nt.csbi.dao.OnlineDeviceDao;

/**
 * @author ����
 *
 * @date: 2017��4��12�� ����1:44:09
 */
@Repository
public class OnlineDeviceDaoImp implements OnlineDeviceDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<Object[]> showAllOnlineDevice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> findOnlineDeviceById() {
		// TODO Auto-generated method stub
		return null;
	}

}
