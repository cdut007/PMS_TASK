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
import com.jameschen.framework.base.MyBaseAdapter;
import com.jameschen.framework.base.MyBaseAdapter.HoldView;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.tasktrackerpms.R;

public class WitnesserAdapter extends MyBaseAdapter<IssueResult> {
	private Context context;

	public WitnesserAdapter(Context context) {
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

		@Override
		protected void initChildView(View convertView,
				MyBaseAdapter<IssueResult> myBaseAdapter) {
			// TODO Auto-generated method stub
		}

		@Override
		protected void setInfo(IssueResult object) {
			// TODO Auto-generated method stub
			
		}
		
	}
	

}
