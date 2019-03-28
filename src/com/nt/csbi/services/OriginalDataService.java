package com.nt.csbi.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nt.csbi.entities.HistoryInfo;
import com.nt.csbi.entities.OriginalData;
import com.nt.csbi.entities.RealTimeInfo;

/**
 * @author 杨润东
 *
 * @date: 2017年4月28日 下午2:15:00
 */
public interface OriginalDataService {
	
	public void dataHandler(String dataId,HttpServletResponse response,HttpServletRequest request,String excelName) throws Exception;
	
	public void html2Excel(String dataId,HttpServletResponse response,String pdfName) throws Exception;
	
	public String originalCreate(String tableName,HttpServletRequest request);

	public void getOriginalTable(String dataId,HttpServletRequest request);
	
	public String getOriginalTableList(String originalBeginDate,String originalEndDate,String originalName);
	
	public String updateOraiginalTableTitle(String originalDataId,String tableTitle,
												HttpServletRequest request);
	
	public String updateOraiginalTableOne(String originalDataId,String tableOne,
			HttpServletRequest request);
	
	public String updateOraiginalTableTwo(String originalDataId,String tableTwo,
			HttpServletRequest request);
	
	public String updateOraiginalTableThree(String originalDataId,String tableThree,
			HttpServletRequest request);
	
	public String updateOraiginalTableFour(String originalDataId,String tableFour,
			HttpServletRequest request);
	
	public String updateOraiginalTableFive(String originalDataId,String tableFive,
			HttpServletRequest request);
	
	public String updateOraiginalTableSix(String originalDataId,String tableSix,
			HttpServletRequest request);
	
	public String updateOraiginalTableSeven(String originalDataId,String tableSeven,
			HttpServletRequest request);
	
	public String updateOraiginalTableEight(String originalDataId,String tableEight,
			HttpServletRequest request);
	
	public String updateOraiginalTableNine(String originalDataId,String tableNine,
			HttpServletRequest request);
	
	public HistoryInfo getSpecifyOraiginalData(String detectionId ,String recordTime);
	
	public RealTimeInfo getSpecifyOraiginalDataFromRealtime(String detectionId ,String recordTime);
	
	public String updateOraiginalUIOptions(String originalDataId,String options,
			HttpServletRequest request);
	
	public List<Object[]> matchTableHeadInfo(String tableHeadInfo,String modelId);
}
