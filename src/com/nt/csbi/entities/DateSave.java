package com.nt.csbi.entities;

/**
 * @author ����
 *
 * @date: 2018��8��30�� ����10:42:24
 */
public class DateSave {

	private Integer id;
	private String dateStr;								//����
	private String detectionId;							//���
	private String sourceId;							//����Դ 0λ��ʷ���ݣ�1λʵʱ����
	
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
