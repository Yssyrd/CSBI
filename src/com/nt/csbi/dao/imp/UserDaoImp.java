package com.nt.csbi.dao.imp;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nt.csbi.dao.UserDao;
import com.nt.csbi.entities.User;

/**
 * @author 杨润东
 *
 * @date: 2017年4月12日 下午1:45:56
 */
@Repository
public class UserDaoImp implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void addNewUser(User user) {
		
		getSession().save(user);
		
	}

	@Override
	public void updateUserById(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> findUserById(String userId) {
		// TODO Auto-generated method stub
		
		
		
		return null;
	}

}
