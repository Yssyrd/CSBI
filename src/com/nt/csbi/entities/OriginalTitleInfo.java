package com.nt.csbi.entities;

import java.sql.Timestamp;

/**
 * @author 杨润东
 *
 * @date: 2017年7月12日 上午10:50:21
 */
public class OriginalTitleInfo {
	
	private Integer otiId;
	private String titleInfo;
	private String detectionId;
	private Timestamp inputDate;
	public Integer getOtiId() {
		return otiId;
	}
	public void setOtiId(Integer otiId) {
		this.otiId = otiId;
	}
	public String getTitleInfo() {
		return titleInfo;
	}
	public void setTitleInfo(String titleInfo) {
		this.titleInfo = titleInfo;
	}
	public String getDetectionId() {
		return detectionId;
	}
	public void setDetectionId(String detectionId) {
		this.detectionId = detectionId;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	@Override
	public String toString() {
		return "OriginalTitleInfo [titleInfo=" + titleInfo + ", detectionId=" + detectionId + ", inputDate=" + inputDate
				+ "]";
	}
	
}
