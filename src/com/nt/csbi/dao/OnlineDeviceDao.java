package com.nt.csbi.dao;

import java.util.List;

/**
 * @author 杨润东
 *
 * @date: 2017年4月12日 上午10:25:42
 */
public interface OnlineDeviceDao {
	
	public List<Object[]> showAllOnlineDevice();
	public List<Object[]> findOnlineDeviceById();
	
	
	
}
