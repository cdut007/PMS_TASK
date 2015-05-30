package com.thirdpart.tasktrackerpms.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jameschen.framework.base.BaseCheckItemAdapter;
import com.jameschen.framework.base.MyBaseAdapter;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.tasktrackerpms.R;

public class DeliveryPlanAdapter extends BaseCheckItemAdapter<RollingPlan> {
	private Context context;
	
	
	public DeliveryPlanAdapter(Context context) {
		super(context,R.layout.delivery_plan_item);
		this.context = context;
	}

	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		convertView = super.getView(position, convertView, parent);
		
		return convertView;

	}

	@Override
	public void recycle() {
	
		super.recycle();

	}

	@Override
	protected HoldView<RollingPlan> createHoldView() {
		// TODO Auto-generated method stub
		return new PlanHoldView();
	}

	private final static class PlanHoldView extends CheckItemHoldView<RollingPlan> {
		
		
		TextView noTextView,drawNoTextView;
		CheckBox isChecked;
		BaseCheckItemAdapter<RollingPlan> mCheckItemAdapter;
		@Override
		protected void initChildView(View convertView,
				MyBaseAdapter<RollingPlan> myBaseAdapter) {
			// TODO Auto-generated method stub
			noTextView = (TextView) convertView.findViewById(R.id.task_no);
			drawNoTextView = (TextView) convertView.findViewById(R.id.task_draw_no);
			isChecked = (CheckBox) convertView.findViewById(R.id.task_delivery_check);
			mCheckItemAdapter = (BaseCheckItemAdapter<RollingPlan>) myBaseAdapter;
		
			((View)isChecked.getParent()).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mCheckItemAdapter.setItemChecked((String)isChecked.getTag(), isChecked);
				}
			});
		}
		
		
		@Override
		protected void setInfo(RollingPlan plan) {
			// TODO Auto-generated method stub
			noTextView.setText(plan.getWeldno());
			drawNoTextView.setText(plan.getDrawno());
		
		}
		@Override
		public void setCheckedInfo(BaseCheckItemAdapter<RollingPlan> adapter,int position,RollingPlan item){
			mCheckItemAdapter.markItemCheckStatus(position, isChecked);
			isChecked.setTag(position+"");
		 }
	}

	
	

}
