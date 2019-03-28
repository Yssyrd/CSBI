package com.nt.csbi.dao;

import java.util.List;

import com.nt.csbi.entities.User;

/**
 * @author ����
 *
 * @date: 2017��4��12�� ����10:26:15
 */
public interface UserDao {

	public void addNewUser(User user);
	public void updateUserById(String userId);
	public List<User> findUserById(String userId); 
	
}
