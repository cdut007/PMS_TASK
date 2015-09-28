package com.thirdpart.tasktrackerpms.adapter;

import java.text.DecimalFormat;

import javax.crypto.spec.PSource;

import android.content.Context;
import android.graphics.Color;
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
import com.thirdpart.widget.DisplayItemView;
import com.thirdpart.widget.DisplayMultiLineItemView;

public class IssueAdapter extends BasePageAdapter<IssueResult> {
	private Context context;

	public IssueAdapter(Context context,OnStatusItemListener onStatusItemListener) {
		super(context,R.layout.issue_item);
		this.context = context;
		this.onStatusItemListener = onStatusItemListener;
	}

	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		convertView = super.getView(position, convertView, parent);
		IssueHoldView sHoldView = (IssueHoldView) convertView.getTag();
		sHoldView.setInfo(position);
		return convertView;

	}

	@Override
	public void recycle() {
	
		super.recycle();

	}

	@Override
	protected HoldView<IssueResult> createHoldView() {
		// TODO Auto-generated method stub
		return new IssueHoldView(onStatusItemListener);
	}
OnStatusItemListener onStatusItemListener;
public interface OnStatusItemListener{
	void onItemClicked(View convertView,IssueResult issueResult);
}
	private final static class IssueHoldView extends HoldView<IssueResult> {
		
		
		TextView noTextView,topicTextView,statusTextView,deliveryTextView,tuzhiTextView,hankouTextView;
		OnStatusItemListener statusItemListener;
		public IssueHoldView(OnStatusItemListener onStatusItemListener) {
			// TODO Auto-generated constructor stub
			statusItemListener = onStatusItemListener;
		}

		@Override
		protected void initChildView(final View convertView,
				MyBaseAdapter<IssueResult> myBaseAdapter) {
			
			// TODO Auto-generated method stub
			noTextView = (TextView) convertView.findViewById(R.id.issue_index_item);
			
			DisplayMultiLineItemView issueDisplayItemView = (DisplayMultiLineItemView) convertView.findViewById(R.id.issue_topic);
			 topicTextView  = issueDisplayItemView.getContentView();
			 hankouTextView = (TextView) convertView.findViewById(R.id.issue_hankou);
			 
			//issueDisplayItemView.setNameBgColor(Color.rgb(0xBF,0xef, 0xff));
			 DisplayItemView issueDisplay2ItemView = (DisplayItemView) convertView.findViewById(R.id.issue_tuzhi);
			// issueDisplayItemView.setNameBgColor(Color.rgb(0xBF,0xef, 0xff));
			 tuzhiTextView  = issueDisplay2ItemView.getContentView();
			
			statusTextView = (TextView) convertView.findViewById(R.id.issue_status);
			deliveryTextView = (TextView) convertView.findViewById(R.id.issue_delivery);
			statusTextView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					statusItemListener.onItemClicked(convertView,(IssueResult)statusTextView.getTag());
				}
			});
		}

		public void setInfo( int position) {
			// TODO Auto-generated method stub
			noTextView.setText(""+(position+1));
		}

		@Override
		protected void setInfo(IssueResult issueResult) {
			// TODO Auto-generated method stub
			topicTextView.setText(issueResult.getQuestionname());
			tuzhiTextView.setText(issueResult.drawno);
			hankouTextView.setText(issueResult.weldno);
			statusTextView.setText(IssueManager.getIssueStatus(issueResult.getIsOk()));
			statusTextView.setTextColor(IssueManager.getIssueStatusColor(issueResult.getIsOk()));
			deliveryTextView.setText(issueResult.getCurrentsolver());
			statusTextView.setTag(issueResult);
		}
		
	}
	

}
