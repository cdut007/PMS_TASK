package com.thirdpart.model.entity;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;

public class IssueCategoryStatistic {

	private List<IssueCategoryItem> mCategoryItems;
	
	public static List<IssueCategoryItem> getWitnessCategoryItem(IssueCategoryStatistic statistic) {
		if (statistic.mCategoryItems==null) {
			statistic.mCategoryItems = new ArrayList<IssueCategoryItem>();
		
			statistic.assigned.key="3";
			statistic.mCategoryItems.add(statistic.myeventComplete);
			
			statistic.assigned.key="2";
			statistic.mCategoryItems.add(statistic.assigned);
			
			statistic.myevent.key="1";
			statistic.mCategoryItems.add(statistic.myevent);
			statistic.assign.key="0";
			statistic.mCategoryItems.add(statistic.assign);
			
			
		
				}
		return statistic.mCategoryItems;
	}
	
	public static List<IssueCategoryItem> getCategoryItem(IssueCategoryStatistic statistic) {
		if (statistic.mCategoryItems==null) {
			statistic.mCategoryItems = new ArrayList<IssueCategoryItem>();
			statistic.mCategoryItems.add(statistic.needConfirm);
			statistic.mCategoryItems.add(statistic.needToSolve);
			statistic.mCategoryItems.add(statistic.concernedNotSolved);
			statistic.mCategoryItems.add(statistic.notSolved);
			statistic.mCategoryItems.add(statistic.created);
			statistic.mCategoryItems.add(statistic.solved);
				}
		return statistic.mCategoryItems;
	}
	public IssueCategoryItem myeventComplete,myevent,assigned,assign,needToSolve,notSolved,created,needConfirm,solved,concernedNotSolved;
}
