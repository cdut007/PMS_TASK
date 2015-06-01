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
import com.thirdpart.model.IssueManager;
import com.thirdpart.model.entity.IssueList;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.tasktrackerpms.R;

public class IssueAdapter extends BasePageAdapter<IssueResult> {
	private Context context;

	public IssueAdapter(Context context) {
		super(context,R.layout.issue_item);
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
	protected HoldView<IssueResult> createHoldView() {
		// TODO Auto-generated method stub
		return new IssueHoldView();
	}

	private final static class IssueHoldView extends HoldView<IssueResult> {
		
		
		TextView noTextView,topicTextView,statusTextView,deliveryTextView;
		@Override
		protected void initChildView(View convertView,
				MyBaseAdapter<IssueResult> myBaseAdapter) {
			// TODO Auto-generated method stub
			noTextView = (TextView) convertView.findViewById(R.id.issue_index_item);
			topicTextView = (TextView) convertView.findViewById(R.id.issue_topic);
			statusTextView = (TextView) convertView.findViewById(R.id.issue_status);
			deliveryTextView = (TextView) convertView.findViewById(R.id.issue_delivery);
		}

		@Override
		protected void setInfo(IssueResult issueResult) {
			// TODO Auto-generated method stub
			noTextView.setText(issueResult.getId());
			topicTextView.setText(issueResult.getStepname());
			statusTextView.setText(IssueManager.getIssueStatus(issueResult.getIsOk()));
			deliveryTextView.setText(issueResult.getCurrentsolver());
		}
		
	}
	

}
