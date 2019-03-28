package com.nt.csbi.dao;

import java.util.List;

import com.nt.csbi.entities.Criterion;

/**
 * @author 杨润东
 *
 * @date: 2017年8月24日 下午3:28:23
 */
public interface CriterionDao {

	public Criterion criterionCreate(Criterion criterion);
	public List<Criterion> getCriterionInfoList();
	public Criterion getCriterion(String id);
	public Criterion criterionUpdate(Criterion criterion);
	public void criterionInfoDelete(String id);
	public List<Object[]> criterionInfoMatch(String ctrId);
	public List<Object[]> criterionInfoMatchFromOriginal(String ctrId);
}
