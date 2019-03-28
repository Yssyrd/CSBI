package com.nt.csbi.entities;

import java.sql.Timestamp;

/**
 * @author 杨润东
 *
 * @date: 2017年4月11日 上午10:49:11
 */
public class OnlineDevice {

	private Integer onLineId;
	private String status;
	private String detectionId;
	private Timestamp realTime;

	public Integer getOnLineId() {
		return onLineId;
	}

	public void setOnLineId(Integer onLineId) {
		this.onLineId = onLineId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDetectionId() {
		return detectionId;
	}

	public void setDetectionId(String detectionId) {
		this.detectionId = detectionId;
	}

	public Timestamp getRealTime() {
		return realTime;
	}

	public void setRealTime(Timestamp realTime) {
		this.realTime = realTime;
	}

}
