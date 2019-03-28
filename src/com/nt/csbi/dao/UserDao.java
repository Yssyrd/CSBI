package com.nt.csbi.dao;

import java.util.List;

import com.nt.csbi.entities.User;

/**
 * @author 杨润东
 *
 * @date: 2017年4月12日 上午10:26:15
 */
public interface UserDao {

	public void addNewUser(User user);
	public void updateUserById(String userId);
	public List<User> findUserById(String userId); 
	
}
