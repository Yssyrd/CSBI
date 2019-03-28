package com.nt.csbi.services.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.csbi.dao.CriterionDao;
import com.nt.csbi.entities.Criterion;
import com.nt.csbi.services.CriterionService;

/**
 * @author 杨润东
 *
 * @date: 2017年8月24日 下午3:28:05
 */
@Service
public class CriterionServiceImp implements CriterionService {

	@Autowired
	private CriterionDao criterionDao;
	
	@Override
	public Criterion criterionCreate(Criterion criterion) {
		
		criterion = criterionDao.criterionCreate(criterion);
		
		return criterion;
	}

	@Override
	public List<Criterion> getCriterionInfoList() {
		
		return criterionDao.getCriterionInfoList();
	}

	@Override
	public Criterion getCriterion(String id) {

		return criterionDao.getCriterion(id);
	}

	@Override
	public Criterion criterionUpdate(Criterion criterion) {

		return criterionDao.criterionUpdate(criterion);
	}

	@Override
	public void criterionInfoDelete(String id) {
		
		criterionDao.criterionInfoDelete(id);
	}

	@Override
	public List<Object[]> criterionInfoMatch(String ctrId) {

		List<Object[]> ctrList = criterionDao.criterionInfoMatch(ctrId);
		System.out.println("ctrList:"+ctrList.size());
		for(Object[] object : ctrList){
			System.out.println(Arrays.asList(object));
		}
		List<Object[]> lists = criterionDao.criterionInfoMatchFromOriginal(ctrId);
		System.out.println("lists:"+lists.size());
		List<String> strBool = new ArrayList<>();
		List<Object[]> result = new ArrayList<>(); 
		Object[] objects = new Object[8];
		if(!ctrId.equals("")){
			for(Object[] tmp : lists){
				String [] strArr = tmp[0].toString().split("#@");
				if(strArr!=null){
					if(strArr.length>12){
						if(!strArr[13].trim().equals("")){
							if(!strBool.contains(strArr[13])&&strArr[13].indexOf(ctrId)>-1){
								strBool.add(strArr[13]);
								
								objects =  new Object[8];
								objects[0] = strArr[10];
								objects[1] = strArr[11];
								objects[2] = strArr[12];
								objects[3] = strArr[13];
								objects[4] = strArr[14];
								objects[5] = strArr[15];
								objects[6] = strArr[16];
								objects[7] = tmp[1];
								System.out.println(Arrays.asList(objects));
								result.add(objects);
							}
						}
					}
				}
			}
			lists = new ArrayList<>();
			lists.addAll(result);
		}
		
		lists.addAll(ctrList);
		strBool = new ArrayList<>();
		result = new ArrayList<>();
		ctrList = new ArrayList<>();
		System.out.println("result:"+lists.size());
		return lists;
	}
}
