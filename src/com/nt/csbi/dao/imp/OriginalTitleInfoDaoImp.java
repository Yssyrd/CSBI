package com.nt.csbi.dao.imp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nt.csbi.dao.OriginalTitleInfoDao;
import com.nt.csbi.entities.OriginalTitleInfo;

/**
 * @author 杨润东
 *
 * @date: 2017年7月12日 上午11:03:31
 */
@Repository
public class OriginalTitleInfoDaoImp implements OriginalTitleInfoDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void saveTitleInfo(OriginalTitleInfo titleInfo) {
		
		getSession().save(titleInfo);
		getSession().flush();
		getSession().clear();
		
	}

	@Override
	public List<OriginalTitleInfo> getTitleInfoList() {
		
		String hql = "from OriginalTitleInfo order by inputDate desc";
		Query queryObject = getSession().createQuery(hql);
		return queryObject.list();
	}

	@Override
	public void originalTitleInfoDelete(String id) {

		OriginalTitleInfo titleInfo =  (OriginalTitleInfo)getSession().get(OriginalTitleInfo.class, Integer.valueOf(id));
		getSession().delete(titleInfo);
		getSession().flush();
		getSession().clear();
	}

	@Override
	public void originalTitleInfoUpdate(String id, String originalName, String detectionId) {
		
		OriginalTitleInfo titleInfo =  (OriginalTitleInfo)getSession().get(OriginalTitleInfo.class, Integer.valueOf(id));
		titleInfo.setDetectionId(detectionId);
		titleInfo.setInputDate(new Timestamp(new Date().getTime()));
		titleInfo.setTitleInfo(originalName);
		getSession().update(titleInfo);
		getSession().flush();
		getSession().clear();
	}

	@Override
	public List<OriginalTitleInfo> getTitleInfoListForSearch(String tableHeadInfo, String modelId) {
		
		String hql = " from OriginalTitleInfo where ";
		
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
		
		hql += " order by inputDate desc ";
		
		 try{
			 queryObject = getSession().createQuery(hql);
			 for(int i=0;i<list.size();i++){
				 queryObject.setParameter(i, list.get(i));
			 }
		 } catch (Exception e){
			   e.printStackTrace();
	     }
		
		List<OriginalTitleInfo> result = queryObject.list();
		
		return result;
	}

}
