package com.nt.csbi.entities;

import java.sql.Timestamp;

/**
 * @author 杨润东
 *
 * @date: 2017年8月24日 下午3:19:40
 */
public class Criterion {
	
	private Integer criterionId;
	private Timestamp inputTime;
	private String ctrName;
	private String ctrFactory;
	private String ctrVersion;
	private String ctrId;
	private String ctrLevel;
	private String certificateId;
	private String validTime;
	
	public Integer getCriterionId() {
		return criterionId;
	}
	public void setCriterionId(Integer criterionId) {
		this.criterionId = criterionId;
	}
	public Timestamp getInputTime() {
		return inputTime;
	}
	public void setInputTime(Timestamp inputTime) {
		this.inputTime = inputTime;
	}
	public String getCtrName() {
		return ctrName;
	}
	public void setCtrName(String ctrName) {
		this.ctrName = ctrName;
	}
	public String getCtrFactory() {
		return ctrFactory;
	}
	public void setCtrFactory(String ctrFactory) {
		this.ctrFactory = ctrFactory;
	}
	public String getCtrVersion() {
		return ctrVersion;
	}
	public void setCtrVersion(String ctrVersion) {
		this.ctrVersion = ctrVersion;
	}
	public String getCtrId() {
		return ctrId;
	}
	public void setCtrId(String ctrId) {
		this.ctrId = ctrId;
	}
	public String getCtrLevel() {
		return ctrLevel;
	}
	public void setCtrLevel(String ctrLevel) {
		this.ctrLevel = ctrLevel;
	}
	public String getCertificateId() {
		return certificateId;
	}
	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}
	public String getValidTime() {
		return validTime;
	}
	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}
	@Override
	public String toString() {
		return "Criterion [criterionId=" + criterionId + ", inputTime=" + inputTime + ", ctrName=" + ctrName
				+ ", ctrFactory=" + ctrFactory + ", ctrVersion=" + ctrVersion + ", ctrId=" + ctrId + ", ctrLevel="
				+ ctrLevel + ", certificateId=" + certificateId + ", validTime=" + validTime + "]";
	}
}
