package com.nt.csbi.dao.imp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nt.csbi.dao.OriginalDataDao;
import com.nt.csbi.entities.HistoryInfo;
import com.nt.csbi.entities.OriginalData;
import com.nt.csbi.entities.RealTimeInfo;

/**
 * @author 杨润东
 *
 * @date: 2017年5月1日 下午6:47:20
 */
@Repository
public class OriginalDataDaoImp implements OriginalDataDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	

	@Override
	public OriginalData originalTableCreate(OriginalData originalData) {
		
		originalData.setTitleInfo(" #@ #@ #@ #@ #@ #@ #@ #@ #@JJF 1260——2010#@婴儿培养箱检测仪#@四川中测辐射科技有限公司#@NT7300#@ #@ #@ #@ ");
		originalData.setTableOne("1#@2#@2#@2#@ #@ #@ #@ ");
		originalData.setTableTwo(" #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ ");
		originalData.setTableThree(" #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ #@ ");
		originalData.setTableFour(" #@ #@ #@ #@ #@ #@ #@ #@ ");
		originalData.setTableFive(" #@ #@ #@ #@ #@ ");
		originalData.setTableSix(" #@ #@ #@ ");
		originalData.setTableSeven(" #@ #@ #@ #@ #@ #@ #@ ");
		originalData.setTableEight("");
		originalData.setAutograph(" #@ #@ ");
		originalData.setShowOptions("1#@1#@1#@1#@1#@1#@1#@1#@1#@");
		
		getSession().save(originalData);
		
		getSession().flush();
		getSession().clear();
		
		return originalData;
	}


	@Override
	public OriginalData getOriginalTable(String originalDataId) {
		// TODO Auto-generated method stub
		
		String hql = "from OriginalData where originalDataId = ? ";
		
		Query queryObject = getSession().createQuery(hql).setInteger(0, Integer.valueOf(originalDataId));
		
		OriginalData originalData = (OriginalData) queryObject.uniqueResult();
		
		return originalData;
	}


	@Override
	public List<OriginalData> getOriginalTableList(String originalBeginDate, String originalEndDate, 
			String originalName) {
		
		String hql = "from OriginalData where  ";
		List<OriginalData> originalDataList = new ArrayList<>();
		List list=new ArrayList();
		Query queryObject = null;
		
		if(!originalBeginDate.equals("")&&originalBeginDate!=null){
			hql += " login> ? and ";
			originalBeginDate = originalBeginDate + " 00:00:00";
			
			list.add(Timestamp.valueOf(originalBeginDate));
		}
		if(!originalEndDate.equals("")&&originalEndDate!=null){
			hql += " login < ? and ";
			list.add(Timestamp.valueOf(originalEndDate + " 23:59:59"));
		}
		
		if(!originalName.equals("")&&originalName!=null){
			hql += " originalDataName like ? and ";
			list.add("%"+originalName+"%");
		}
		hql = hql.substring(0,hql.length()-4);
		
		
		hql += " order by login desc ";
		
		 try{
			 queryObject = getSession().createQuery(hql);
			 for(int i=0;i<list.size();i++){
				 queryObject.setParameter(i, list.get(i));
			 }
		 } catch (Exception e){
			   e.printStackTrace();
	     }
		
		 originalDataList = queryObject.list();
		
		list = new ArrayList<>();
		
		return originalDataList;
		
	}


	@Override
	public OriginalData updateOraiginalTableTitle(String originalDataId, String tableTitle) {
		
		OriginalData originalData = getOriginalTable(originalDataId);
		
		originalData.setTitleInfo(tableTitle);
		
		getSession().update(originalData);
		
		getSession().flush();
		getSession().clear();
		
		return originalData;
	}


	@Override
	public OriginalData updateOraiginalTableOne(String originalDataId, String tableOne) {
		
		OriginalData originalData = getOriginalTable(originalDataId);
		
		originalData.setTableOne(tableOne);
		
		getSession().update(originalData);
		
		getSession().flush();
		getSession().clear();
		
		return originalData;
	}


	@Override
	public OriginalData updateOraiginalTwo(String originalDataId, String tableTwo) {
		
		OriginalData originalData = getOriginalTable(originalDataId);
		
		originalData.setTableTwo(tableTwo);
		
		getSession().update(originalData);
		
		getSession().flush();
		getSession().clear();
		
		return originalData;
	}


	@Override
	public OriginalData updateOraiginalTableThree(String originalDataId, String tableThree) {
		
		OriginalData originalData = getOriginalTable(originalDataId);
		
		originalData.setTableThree(tableThree);
		
		getSession().update(originalData);
		
		getSession().flush();
		getSession().clear();
		
		return originalData;
	}


	@Override
	public OriginalData updateOraiginalTableFour(String originalDataId, String tableFour) {
		
		OriginalData originalData = getOriginalTable(originalDataId);
		
		originalData.setTableFour(tableFour);
		
		getSession().update(originalData);
		
		getSession().flush();
		getSession().clear();
		
		return originalData;
	}


	@Override
	public OriginalData updateOraiginalTableFive(String originalDataId, String tableFive) {
		
		OriginalData originalData = getOriginalTable(originalDataId);
		
		originalData.setTableFive(tableFive);
		
		getSession().update(originalData);
		
		getSession().flush();
		getSession().clear();
		
		return originalData;
	}


	@Override
	public OriginalData updateOraiginalTableSix(String originalDataId, String tableSix) {

		OriginalData originalData = getOriginalTable(originalDataId);
		
		originalData.setTableSix(tableSix);
		
		getSession().update(originalData);
		
		getSession().flush();
		getSession().clear();
		
		return originalData;
	}


	@Override
	public OriginalData updateOraiginalTableSeven(String originalDataId, String tableSeven) {

		OriginalData originalData = getOriginalTable(originalDataId);
		
		originalData.setTableSeven(tableSeven);
		
		getSession().update(originalData);
		
		getSession().flush();
		getSession().clear();
		
		return originalData;
	}


	@Override
	public OriginalData updateOraiginalTableEight(String originalDataId, String tableEight) {

		OriginalData originalData = getOriginalTable(originalDataId);
		
		originalData.setTableEight(tableEight);
		
		getSession().update(originalData);
		
		getSession().flush();
		getSession().clear();
		
		return originalData;
	}

	@Override
	public OriginalData updateOraiginalTableNine(String originalDataId, String tableNine) {

		OriginalData originalData = getOriginalTable(originalDataId);
		
		originalData.setAutograph(tableNine);
		
		getSession().update(originalData);
		
		getSession().flush();
		getSession().clear();
		
		return originalData;
	}

	@Override
	public HistoryInfo getSpecifyOraiginalData(String detectionId, String recordTime) {
		
		String hql = " from HistoryInfo where recordTime = ? and detectionId = ? ";
		Query queryObject = getSession().createQuery(hql).setString(1, detectionId)
								.setTimestamp(0, Timestamp.valueOf(recordTime));
		
		List<HistoryInfo> historyInfoList = queryObject.list();
		
		HistoryInfo historyInfo = new HistoryInfo();
		
		for(HistoryInfo info:historyInfoList){
			historyInfo = info;
		}
		
		return historyInfo;
	}


	@Override
	public RealTimeInfo getSpecifyOraiginalDataFromRealtime(String detectionId, String recordTime) {
		
		String hql = " from RealTimeInfo where recordTime = ? and detectionId = ? ";
		Query queryObject = getSession().createQuery(hql).setString(1, detectionId)
								.setTimestamp(0, Timestamp.valueOf(recordTime));
		
		List<RealTimeInfo> historyInfoList = queryObject.list();
		
		RealTimeInfo historyInfo = new RealTimeInfo();
		
		for(RealTimeInfo info:historyInfoList){
			historyInfo = info;
		}
		
		return historyInfo;
	}


	@Override
	public OriginalData updateOraiginalUIOptions(String originalDataId, String options) {

		OriginalData originalData = getOriginalTable(originalDataId);
		
		originalData.setShowOptions(options);
		
		getSession().update(originalData);
		
		getSession().flush();
		getSession().clear();
		
		return originalData;
	}


	@Override
	public List<Object[]> matchTableHeadInfo(String tableHeadInfo,String modelId) {
		
		String hql = " select distinct titleInfo,login  from OriginalData where ";
		
		List list=new ArrayList();
		Query queryObject = null;
		
		if(!tableHeadInfo.equals("")&&tableHeadInfo!=null){
			hql += " titleInfo like ? and ";
			list.add("%" + tableHeadInfo.trim() + "%");
		}
		if(!modelId.equals("")&&modelId!=null){
			hql += " titleInfo like ? and ";
			list.add("%" + modelId.trim() + "%");
		}
		
		hql = hql.substring(0,hql.length()-4);
		
		hql += " order by login desc ";
		
		 try{
			 queryObject = getSession().createQuery(hql);
			 for(int i=0;i<list.size();i++){
				 queryObject.setParameter(i, list.get(i));
			 }
		 } catch (Exception e){
			   e.printStackTrace();
	     }
		
		List<Object[]> result = queryObject.list();
		
		return result;
	}

}
