package com.nt.csbi.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nt.csbi.entities.HistoryInfo;
import com.nt.csbi.entities.OriginalData;
import com.nt.csbi.entities.RealTimeInfo;

/**
 * @author 杨润东
 *
 * @date: 2017年5月1日 下午6:43:14
 */
public interface OriginalDataDao {
	
	public OriginalData originalTableCreate(OriginalData originalData);
	
	public OriginalData getOriginalTable(String originalDataId);
	
	public List<OriginalData> getOriginalTableList(String originalBeginDate, String originalEndDate, 
			String originalName);
	
	public OriginalData updateOraiginalTableTitle(String originalDataId,String tableTitle);
	
	public OriginalData updateOraiginalTableOne(String originalDataId,String tableOne);
	
	public OriginalData updateOraiginalTwo(String originalDataId,String tableTwo);

	public OriginalData updateOraiginalTableThree(String originalDataId,String tableThree);

	public OriginalData updateOraiginalTableFour(String originalDataId,String tableFour);
	
	public OriginalData updateOraiginalTableFive(String originalDataId,String tableFive);
	
	public OriginalData updateOraiginalTableSix(String originalDataId,String tableSix);
	
	public OriginalData updateOraiginalTableSeven(String originalDataId,String tableSeven);
	
	public OriginalData updateOraiginalTableEight(String originalDataId,String tableEight);
	
	public OriginalData updateOraiginalTableNine(String originalDataId,String tableNine);
	
	public HistoryInfo getSpecifyOraiginalData(String detectionId ,String recordTime);
	
	public RealTimeInfo getSpecifyOraiginalDataFromRealtime(String detectionId ,String recordTime);
	
	public OriginalData updateOraiginalUIOptions(String originalDataId,String options);
	
	public List<Object[]> matchTableHeadInfo(String tableHeadInfo,String modelId);
}
