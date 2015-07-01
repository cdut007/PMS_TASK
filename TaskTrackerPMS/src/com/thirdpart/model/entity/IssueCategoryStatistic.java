package com.thirdpart.model.entity;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;

public class IssueCategoryStatistic {

	private List<IssueCategoryItem> mCategoryItems;
	
	public static List<IssueCategoryItem> getCategoryItem(IssueCategoryStatistic statistic) {
		if (statistic.mCategoryItems==null) {
			statistic.mCategoryItems = new ArrayList<IssueCategoryItem>();
			statistic.mCategoryItems.add(statistic.needConfirm);
			statistic.mCategoryItems.add(statistic.needToSolve);
			statistic.mCategoryItems.add(statistic.concernedNotSolved);
		}
		return statistic.mCategoryItems;
	}
	public IssueCategoryItem needToSolve,notSolved,created,needConfirm,solved,concernedNotSolved;
}
