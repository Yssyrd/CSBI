package com.nt.csbi.entities;

import java.sql.Timestamp;

/**
 * @author 杨润东
 *
 * @date: 2017年4月11日 上午10:36:27
 */
public class HistoryInfo {
	
	private Integer historyId;
	private Timestamp recordTime;
	private String AT;
	private String BT;
	private String CT;
	private String DT;
	private String ET;
	private String AF;
	private String AG;
	private String O2;
	private String angleX;
	private String angleY;
	private String angleZ;
	private String DIST;
	private String CONT;
	private String DISRH;
	private String NOISE;
	private String INNOIS;
	private String OUTNOI;
	private String FLOW;
	private Timestamp inputDate;
	private String inspectedId;
	private String detectionId; 
	
	public Integer getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
	public Timestamp getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}
	public String getAT() {
		return AT;
	}
	public void setAT(String aT) {
		AT = aT;
	}
	public String getBT() {
		return BT;
	}
	public void setBT(String bT) {
		BT = bT;
	}
	public String getCT() {
		return CT;
	}
	public void setCT(String cT) {
		CT = cT;
	}
	public String getDT() {
		return DT;
	}
	public void setDT(String dT) {
		DT = dT;
	}
	public String getET() {
		return ET;
	}
	public void setET(String eT) {
		ET = eT;
	}
	public String getAF() {
		return AF;
	}
	public void setAF(String aF) {
		AF = aF;
	}
	public String getAG() {
		return AG;
	}
	public void setAG(String aG) {
		AG = aG;
	}
	public String getO2() {
		return O2;
	}
	public void setO2(String o2) {
		O2 = o2;
	}
	public String getAngleX() {
		return angleX;
	}
	public void setAngleX(String angleX) {
		this.angleX = angleX;
	}
	public String getAngleY() {
		return angleY;
	}
	public void setAngleY(String angleY) {
		this.angleY = angleY;
	}
	public String getAngleZ() {
		return angleZ;
	}
	public void setAngleZ(String angleZ) {
		this.angleZ = angleZ;
	}
	public String getDIST() {
		return DIST;
	}
	public void setDIST(String dIST) {
		DIST = dIST;
	}
	public String getCONT() {
		return CONT;
	}
	public void setCONT(String cONT) {
		CONT = cONT;
	}
	public String getDISRH() {
		return DISRH;
	}
	public void setDISRH(String dISRH) {
		DISRH = dISRH;
	}
	public String getNOISE() {
		return NOISE;
	}
	public void setNOISE(String nOISE) {
		NOISE = nOISE;
	}
	public String getINNOIS() {
		return INNOIS;
	}
	public void setINNOIS(String iNNOIS) {
		INNOIS = iNNOIS;
	}
	public String getOUTNOI() {
		return OUTNOI;
	}
	public void setOUTNOI(String oUTNOI) {
		OUTNOI = oUTNOI;
	}
	public String getFLOW() {
		return FLOW;
	}
	public void setFLOW(String fLOW) {
		FLOW = fLOW;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	public String getDetectionId() {
		return detectionId;
	}
	public void setDetectionId(String detectionId) {
		this.detectionId = detectionId;
	}
	public String getInspectedId() {
		return inspectedId;
	}
	public void setInspectedId(String inspectedId) {
		this.inspectedId = inspectedId;
	}
	@Override
	public String toString() {
		return "HistoryInfo [recordTime=" + recordTime + ", AT=" + AT + ", BT=" + BT + ", CT=" + CT + ", DT=" + DT
				+ ", ET=" + ET + ", AF=" + AF + ", AG=" + AG + ", O2=" + O2 + ", angleX=" + angleX + ", angleY="
				+ angleY + ", angleZ=" + angleZ + ", DIST=" + DIST + ", CONT=" + CONT + ", DISRH=" + DISRH + ", NOISE="
				+ NOISE + ", INNOIS=" + INNOIS + ", OUTNOI=" + OUTNOI + ", FLOW=" + FLOW + ", inputDate=" + inputDate
				+ ", inspectedId=" + inspectedId + ", detectionId=" + detectionId + "]";
	}
	
	
	
}
