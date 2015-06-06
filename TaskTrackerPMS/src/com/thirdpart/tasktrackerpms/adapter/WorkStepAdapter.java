package com.thirdpart.tasktrackerpms.adapter;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.Intent;
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
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.RollingPlanList;
import com.thirdpart.model.entity.WorkStep;
import com.thirdpart.model.entity.WorkStepList;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.ui.IssueFeedbackActivity;
import com.thirdpart.tasktrackerpms.ui.WorkStepDetailActivity;

public class WorkStepAdapter extends BasePageAdapter<WorkStep> {
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
		TextView workNo,workName;
		View issueFeedback, issueUpdate;
		@Override
		protected void initChildView(View convertView,
				MyBaseAdapter<WorkStep> myBaseAdapter) {
			// TODO Auto-generated method stub
			workNo = (TextView) convertView.findViewById(R.id.workstep_index_item);
			workName = (TextView) convertView.findViewById(R.id.workstep_name);
		   issueFeedback = convertView.findViewById(R.id.issue_feedback);
		   issueUpdate = convertView.findViewById(R.id.issue_update);
		   issueFeedback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Context context = v.getContext();
				
				Intent intent= new Intent(context,IssueFeedbackActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				WorkStep workStep = (WorkStep) v.getTag();
				intent.putExtra("feedback",workStep);
				context.startActivity(intent);
			}
		});
		   issueUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Context context = v.getContext();
				
				Intent intent= new Intent(context,WorkStepDetailActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				WorkStep workStep = (WorkStep) v.getTag();
				intent.putExtra("workstep", workStep);
				context.startActivity(intent);
			}
		});
//		   if (((WorkStepAdapter)myBaseAdapter).scan ){
//			issueFeedback.setVisibility(View.GONE);
//			issueUpdate.setVisibility(View.GONE);
//		}
		}

		@Override
		protected void setInfo(WorkStep workStep) {
			// TODO Auto-generated method stub
			workNo.setText(workStep.getStepno());
			workName.setText(workStep.getStepname());
			issueFeedback.setTag(workStep);
			issueUpdate.setTag(workStep);
			
			if ("PREPARE".equals(workStep.getStepflag())) {
				issueFeedback.setVisibility(View.VISIBLE);
			}else {
				issueFeedback.setVisibility(View.INVISIBLE);	
					
			
			}
		}
		
	}

	boolean scan;
	public void setScanMode(boolean fromPlan) {
		// TODO Auto-generated method stub
		scan = fromPlan;
	}
	

}
