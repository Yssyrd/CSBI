package com.nt.csbi.entities;

import java.security.Timestamp;

/**
 * @author ����
 *
 * @date: 2017��4��11�� ����2:39:08
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
