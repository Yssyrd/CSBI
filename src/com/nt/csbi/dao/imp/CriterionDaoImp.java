package com.nt.csbi.dao.imp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nt.csbi.dao.CriterionDao;
import com.nt.csbi.entities.Criterion;

/**
 * @author 杨润东
 *
 * @date: 2017年8月24日 下午3:28:37
 */
@Repository
public class CriterionDaoImp implements CriterionDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public Criterion criterionCreate(Criterion criterion) {
		
		getSession().save(criterion);
	
		return criterion;
	}

	@Override
	public List<Criterion> getCriterionInfoList() {
	
		String hql = "from Criterion order by inputTime desc";
		Query queryObject = getSession().createQuery(hql);
		return queryObject.list();
	}

	@Override
	public Criterion getCriterion(String id) {
		
		Criterion criterion =  (Criterion)getSession().get(Criterion.class, Integer.valueOf(id));
		
		return criterion;
	}

	@Override
	public Criterion criterionUpdate(Criterion criterion) {
		
		getSession().update(criterion);
		
		return criterion;
	}

	@Override
	public void criterionInfoDelete(String id) {
		
		Criterion criterion =  (Criterion)getSession().get(Criterion.class, Integer.valueOf(id));
		getSession().delete(criterion);
		
	}

	@Override
	public List<Object[]> criterionInfoMatch(String ctrId) {
		
		String hql = " select ctrName,ctrFactory,ctrVersion,ctrId,ctrLevel,certificateId,validTime,inputTime from Criterion where ctrId like ?  order by inputTime desc";
		Query queryObject = getSession().createQuery(hql).setString(0, "%" + ctrId + "%");
		
		return queryObject.list();
	}

	@Override
	public List<Object[]> criterionInfoMatchFromOriginal(String ctrId) {
		String hql = " select distinct titleInfo,login  from OriginalData where titleInfo like ? order by login desc";
		
		List list=new ArrayList();
		Query queryObject = null;
		
		queryObject = getSession().createQuery(hql).setString(0, "%" + ctrId + "%");
		
		List<Object[]> result = queryObject.list();
		
		return result;
	}

}
