package com.nt.csbi.entities;

import java.security.Timestamp;

/**
 * @author 杨润东
 *
 * @date: 2017年4月11日 下午2:24:35
 */
public class InspectedDevice {

	private Integer inspectedId;
	private String inspectedDeviceId;
	private String Manufacturer;
	private String manuDate;
	private String expiryDate;
	private String testCpy;
	private Timestamp inspectedDeviceloginDate;
	private Timestamp inspectedDevicelogoutDate;
	
	private InspectedCpy inspectedCpyId;

	public Integer getInspectedId() {
		return inspectedId;
	}

	public void setInspectedId(Integer inspectedId) {
		this.inspectedId = inspectedId;
	}

	public String getInspectedDeviceId() {
		return inspectedDeviceId;
	}

	public void setInspectedDeviceId(String inspectedDeviceId) {
		this.inspectedDeviceId = inspectedDeviceId;
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

	public Timestamp getInspectedDeviceloginDate() {
		return inspectedDeviceloginDate;
	}

	public void setInspectedDeviceloginDate(Timestamp inspectedDeviceloginDate) {
		this.inspectedDeviceloginDate = inspectedDeviceloginDate;
	}

	public Timestamp getInspectedDevicelogoutDate() {
		return inspectedDevicelogoutDate;
	}

	public void setInspectedDevicelogoutDate(Timestamp inspectedDevicelogoutDate) {
		this.inspectedDevicelogoutDate = inspectedDevicelogoutDate;
	}

	public InspectedCpy getInspectedCpyId() {
		return inspectedCpyId;
	}

	public void setInspectedCpyId(InspectedCpy inspectedCpyId) {
		this.inspectedCpyId = inspectedCpyId;
	}
	
	
}
