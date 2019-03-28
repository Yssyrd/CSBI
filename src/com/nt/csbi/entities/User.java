package com.nt.csbi.entities;

import java.security.Timestamp;

/**
 * @author 杨润东
 *
 * @date: 2017年4月11日 下午2:42:57
 */
public class User {

	private Integer userId;
	private String userName;
	private String password;
	private String detectionDeviceCpy;
	private String permissions;
	private Timestamp userLoginDate;
	private Timestamp userLogoutDate;
	
	private Integer cpyAlias;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDetectionDeviceCpy() {
		return detectionDeviceCpy;
	}

	public void setDetectionDeviceCpy(String detectionDeviceCpy) {
		this.detectionDeviceCpy = detectionDeviceCpy;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public Timestamp getUserLoginDate() {
		return userLoginDate;
	}

	public void setUserLoginDate(Timestamp userLoginDate) {
		this.userLoginDate = userLoginDate;
	}

	public Timestamp getUserLogoutDate() {
		return userLogoutDate;
	}

	public void setUserLogoutDate(Timestamp userLogoutDate) {
		this.userLogoutDate = userLogoutDate;
	}

	public Integer getCpyAlias() {
		return cpyAlias;
	}

	public void setCpyAlias(Integer cpyAlias) {
		this.cpyAlias = cpyAlias;
	}
	
	
}
