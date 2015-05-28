package com.jameschen.framework.base;

import java.util.HashMap;
import java.util.List;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public abstract class  BaseCheckItemAdapter<T> extends MyBaseAdapter<T> {
	
	


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

		public class OptionCheckListener implements OnCheckedChangeListener {
			int position;

			public void setCheckOption(int positon) {
				this.position = positon;
			}

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub

				getIsSelected().put(position, isChecked);

			}

		}

		// 用来控制CheckBox的选中状况
		private HashMap<Integer, Boolean> isSelected;

		private void clearCheck() {
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
		
		
		private void markItemCheckStatus(int position,CheckBox checkBox) {
			// TODO Auto-generated method stub
			
			checkBox.setChecked(getIsSelected().get(position));
		}
		
		public void setItemChecked(int position,CheckBox checkBox) {
			// TODO Auto-generated method stub
			// 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
			// 改变CheckBox的状态
			checkBox.toggle();
			getIsSelected().put(position, checkBox.isChecked());
			notifyDataSetChanged();
		}

		public int getAllCheckOptionsCount() {
			int value = 0;
			int count = getCount();
			for (int i = 0; i < count; i++) {
				if (getIsSelected().get(i)) {
					value += 1;
				}
			}

			return value;
		}

	

}
