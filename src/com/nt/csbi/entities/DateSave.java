package com.nt.csbi.entities;

/**
 * @author 杨润东
 *
 * @date: 2018年8月30日 上午10:42:24
 */
public class DateSave {

	private Integer id;
	private String dateStr;								//日期
	private String detectionId;							//编号
	private String sourceId;							//数据源 0位历史数据，1位实时数据
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public String getDetectionId() {
		return detectionId;
	}
	public void setDetectionId(String detectionId) {
		this.detectionId = detectionId;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	@Override
	public String toString() {
		return "DateSave [id=" + id + ", dateStr=" + dateStr + ", detectionId=" + detectionId + ", sourceId=" + sourceId
				+ "]";
	}
	
	
}
