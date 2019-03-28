package com.nt.csbi.dao.imp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nt.csbi.dao.InspectedCpyDao;
import com.nt.csbi.entities.InspectedCpy;

/**
 * @author 杨润东
 *
 * @date: 2017年4月12日 下午1:41:59
 */
@Repository
public class InspectedCpyDaoImp implements InspectedCpyDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	
	@Override
	public void addNewInspectedCpyDao(InspectedCpy inspectedCpy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateInspectedCpyDaoById(String inspectedCpyId) {
		// TODO Auto-generated method stub
		
	}

}
