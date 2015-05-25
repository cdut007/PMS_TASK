package com.thirdpart.tasktrackerpms.adapter;

import java.text.DecimalFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jameschen.framework.base.BaseActivity;
import com.jameschen.framework.base.BasePageAdapter;
import com.jameschen.framework.base.MyBaseAdapter;
import com.jameschen.framework.base.MyBaseAdapter.HoldView;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.RollingPlanList;
import com.thirdpart.tasktrackerpms.R;

public class PlanAdapter extends BasePageAdapter<RollingPlan,RollingPlanList> {
	private Context context;
	
	
	public PlanAdapter(Context context) {
		super(context,R.layout.plan_item);
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

	private final static class PlanHoldView extends HoldView<RollingPlan> {
		
		
		TextView classTextView,deliveryNumTextView,finishNumTextView,currentNumTextView;
		@Override
		protected void initChildView(View convertView,
				MyBaseAdapter<RollingPlan> myBaseAdapter) {
			// TODO Auto-generated method stub
			classTextView = (TextView) convertView.findViewById(R.id.plan_class_item);
			currentNumTextView = (TextView) convertView.findViewById(R.id.plan_current_num_item);
			finishNumTextView = (TextView) convertView.findViewById(R.id.plan_finish_num_item);
			deliveryNumTextView = (TextView) convertView.findViewById(R.id.plan_total_num_item);
		}

		@Override
		protected void setInfo(RollingPlan plan) {
			// TODO Auto-generated method stub
			//classTextView.setText(plan.getClass());
			//currentNumTextView.setText(plan.get);
		}
		
	}
	

}
