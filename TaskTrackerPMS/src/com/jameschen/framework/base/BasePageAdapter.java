package com.jameschen.framework.base;

import java.util.List;

import android.content.Context;

import com.thirdpart.model.entity.base.PageList;

public abstract class BasePageAdapter<T, PageType extends PageList<T>> extends MyBaseAdapter<T>{

	
	public BasePageAdapter(Context context,int layoutId) {
		super(context, layoutId);
        }
	
}
