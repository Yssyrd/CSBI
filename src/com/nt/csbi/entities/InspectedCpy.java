package com.nt.csbi.entities;

import java.security.Timestamp;

/**
 * @author 杨润东
 *
 * @date: 2017年4月11日 下午2:39:08
 */
public class InspectedCpy {

	private Integer inspectedCpyId;
	private String inspectedCpyName;
	
	private Timestamp inspectedCpyLoginDate;
	private Timestamp inspectedCpyLogoutDate;
	
	public Integer getInspectedCpyId() {
		return inspectedCpyId;
	}
	public void setInspectedCpyId(Integer inspectedCpyId) {
		this.inspectedCpyId = inspectedCpyId;
	}
	public String getInspectedCpyName() {
		return inspectedCpyName;
	}
	public void setInspectedCpyName(String inspectedCpyName) {
		this.inspectedCpyName = inspectedCpyName;
	}
	public Timestamp getInspectedCpyLoginDate() {
		return inspectedCpyLoginDate;
	}
	public void setInspectedCpyLoginDate(Timestamp inspectedCpyLoginDate) {
		this.inspectedCpyLoginDate = inspectedCpyLoginDate;
	}
	public Timestamp getInspectedCpyLogoutDate() {
		return inspectedCpyLogoutDate;
	}
	public void setInspectedCpyLogoutDate(Timestamp inspectedCpyLogoutDate) {
		this.inspectedCpyLogoutDate = inspectedCpyLogoutDate;
	}
	
	
}
