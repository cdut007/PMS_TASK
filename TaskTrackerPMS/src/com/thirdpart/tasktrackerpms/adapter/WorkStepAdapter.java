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
import com.thirdpart.model.entity.WorkStep;
import com.thirdpart.model.entity.WorkStepList;
import com.thirdpart.tasktrackerpms.R;

public class WorkStepAdapter extends BasePageAdapter<WorkStep,WorkStepList> {
	private Context context;
	
	
	public WorkStepAdapter(Context context) {
		super(context,R.layout.workstep_item);
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
	protected HoldView<WorkStep> createHoldView() {
		// TODO Auto-generated method stub
		return new WrokStepView();
	}

	private final static class WrokStepView extends HoldView<WorkStep> {

		@Override
		protected void initChildView(View convertView,
				MyBaseAdapter<WorkStep> myBaseAdapter) {
			// TODO Auto-generated method stub
		}

		@Override
		protected void setInfo(WorkStep object) {
			// TODO Auto-generated method stub
			
		}
		
	}
	

}
