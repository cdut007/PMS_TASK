package com.jameschen.framework.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.base.PageList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public abstract class  BaseCheckItemAdapter<T> extends BasePageAdapter<T> {
	
	


		public BaseCheckItemAdapter(Context context, int layoutId) {
		super(context, layoutId);
		// TODO Auto-generated constructor stub
	}

		@Override
		public void addObjectList(List<T> mList) {
			// TODO Auto-generated method stub
			super.addObjectList(mList);

			initSelectItem(mList.size());
		}

		@Override
		public void addObject(T object) {
			// TODO Auto-generated method stub
			super.addObject(object);
			initSelectItem(1);
		}

		

		@Override
		public void recycle() {
			// TODO Auto-generated method stub
			super.recycle();

		}

		// 用来控制CheckBox的选中状况
		private HashMap<Integer, Boolean> isSelected;

		public List<T> getAllCheckOptions() {
			// TODO Auto-generated method stub
			int count = getCount();
			List<T> mList = new ArrayList<T>();
			for (int i = 0; i < count; i++) {
				if (getIsSelected().get(i)) {
					mList.add(getItem(i));
				}
			}
			return mList;
		}
		@Override
		public void clear(boolean fresh) {
		clearCheck();// TODO Auto-generated method stub
		super.clear(fresh);
		
		}
	
		public void clearCheck() {
			// TODO Auto-generated method stub
			if (isSelected == null) {
				return;
			}
			for (int i = 0 ; i <isSelected.size(); i++) {
				getIsSelected().put(i, false);
			}
		}
		
		// 初始化isSelected的数据
		private void initSelectItem(int size) {
			if (isSelected == null) {
				isSelected = new HashMap<Integer, Boolean>();
			} else {
			}
			int lastLen = isSelected.size();
			for (int i = 0 + lastLen; i < size + lastLen; i++) {
				getIsSelected().put(i + lastLen, false);
			}
		}

		public HashMap<Integer, Boolean> getIsSelected() {
			return isSelected;
		}

		public void setIsSelected(HashMap<Integer, Boolean> selected) {
			isSelected = selected;
		}
		
		
		public void markItemCheckStatus(int position,CheckBox checkBox) {
			// TODO Auto-generated method stub
			Boolean checkBoolean = getIsSelected().get(position);
			if (checkBoolean!=null) {
				checkBox.setChecked(checkBoolean);				
			}else {
				checkBox.setChecked(false);
			}
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view =  super.getView(position, convertView, parent);
		CheckItemHoldView<T> holdView = (CheckItemHoldView<T>) view.getTag();
		 holdView.setCheckedInfo(this, position, null);
		return view;
		}
		
		
		public abstract static class CheckItemHoldView<T> extends HoldView<T>{
			public abstract void  setCheckedInfo(BaseCheckItemAdapter<T> adapter,int position,T item);
		}
		
		public void setItemChecked(String position,CheckBox checkBox) {
			// TODO Auto-generated method stub
			// 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
			// 改变CheckBox的状态
			checkBox.toggle();
			getIsSelected().put(Integer.parseInt(position), checkBox.isChecked());
			notifyDataSetChanged();
		}

		public int getAllCheckOptionsCount() {
			int value = 0;
			int count = getCount();
			if (getIsSelected() == null) {
				return 0;
			}
			for (int i = 0; i < count; i++) {
				if (getIsSelected().get(i)) {
					value += 1;
				}
			}

			return value;
		}

	

}
