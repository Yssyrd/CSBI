package com.nt.csbi.entities;

import java.sql.Timestamp;

/**
 * @author 杨润东
 *
 * @date: 2017年4月11日 上午11:14:05
 */
public class DetectionDevice {
	
	private Integer detectionId;
	private String detectionDeviceId;
	private String Manufacturer;
	private String manuDate;
	private String expiryDate;
	private String testCpy;
	private String status;
	
	private Timestamp detectionDeviceLoginDate;
	private Timestamp detectionDeviceLogoutDate;
	
	private User userId;

	public Integer getDetectionId() {
		return detectionId;
	}

	public void setDetectionId(Integer detectionId) {
		this.detectionId = detectionId;
	}

	public String getDetectionDeviceId() {
		return detectionDeviceId;
	}

	public void setDetectionDeviceId(String detectionDeviceId) {
		this.detectionDeviceId = detectionDeviceId;
	}

	public String getManufacturer() {
		return Manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		Manufacturer = manufacturer;
	}

	public String getManuDate() {
		return manuDate;
	}

	public void setManuDate(String manuDate) {
		this.manuDate = manuDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getTestCpy() {
		return testCpy;
	}

	public void setTestCpy(String testCpy) {
		this.testCpy = testCpy;
	}

	public Timestamp getDetectionDeviceLoginDate() {
		return detectionDeviceLoginDate;
	}

	public void setDetectionDeviceLoginDate(Timestamp detectionDeviceLoginDate) {
		this.detectionDeviceLoginDate = detectionDeviceLoginDate;
	}

	public Timestamp getDetectionDeviceLogoutDate() {
		return detectionDeviceLogoutDate;
	}

	public void setDetectionDeviceLogoutDate(Timestamp detectionDeviceLogoutDate) {
		this.detectionDeviceLogoutDate = detectionDeviceLogoutDate;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
