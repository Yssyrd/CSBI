package com.nt.csbi.dao;

import java.util.List;

/**
 * @author ����
 *
 * @date: 2017��4��12�� ����10:25:42
 */
public interface OnlineDeviceDao {
	
	public List<Object[]> showAllOnlineDevice();
	public List<Object[]> findOnlineDeviceById();
	
	
	
}
