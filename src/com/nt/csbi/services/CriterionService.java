package com.nt.csbi.services;

import java.util.List;

import com.nt.csbi.entities.Criterion;

/**
 * @author 杨润东
 *
 * @date: 2017年8月24日 下午3:27:46
 */
public interface CriterionService {

	public Criterion criterionCreate(Criterion criterion);
	public List<Criterion> getCriterionInfoList();
	public Criterion getCriterion(String id);
	public Criterion criterionUpdate(Criterion criterion);
	public void criterionInfoDelete(String id);
	public List<Object[]> criterionInfoMatch(String ctrId);
	
}
