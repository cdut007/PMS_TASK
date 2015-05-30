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
import com.jameschen.framework.base.MyBaseAdapter;
import com.jameschen.framework.base.MyBaseAdapter.HoldView;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.WitnessDistributed;
import com.thirdpart.model.entity.WorkStep;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.ui.IssueFeedbackActivity;
import com.thirdpart.tasktrackerpms.ui.WitnessChooseActivity;
import com.thirdpart.tasktrackerpms.ui.WitnessUpdateActivity;
import com.thirdpart.tasktrackerpms.ui.WorkStepDetailActivity;
import com.thirdpart.widget.TouchImage;

public class WitnesserAdapter extends MyBaseAdapter<WitnessDistributed> {
	private Context context;

	public WitnesserAdapter(Context context) {
		super(context,R.layout.witness_item);
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
	protected HoldView<WitnessDistributed> createHoldView() {
		// TODO Auto-generated method stub
		return new ItemHoldView();
	}

	private final static class ItemHoldView extends HoldView<WitnessDistributed> {

		TextView noTextView,adressTextView;
		TouchImage chooseWitenss;
	
		@Override
		protected void initChildView(View convertView,
				MyBaseAdapter<WitnessDistributed> myBaseAdapter) {
			// TODO Auto-generated method stub
			noTextView = (TextView) convertView.findViewById(R.id.witness_index_item);
			adressTextView = (TextView) convertView.findViewById(R.id.witnenss_address);
			chooseWitenss = (TouchImage) convertView.findViewById(R.id.witness_choose);
			
			chooseWitenss.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Context context = v.getContext();
					
					Intent intent= new Intent(context,WitnessChooseActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					WitnessDistributed workStep = (WitnessDistributed) v.getTag();
					intent.putExtra(Item.WITNESS, workStep);
					context.startActivity(intent);
				}
			});
			
		
		}

		@Override
		protected void setInfo(WitnessDistributed object) {
			// TODO Auto-generated method stub
			noTextView.setText(object.getId());
			adressTextView.setText(object.getWitnessaddress());
			chooseWitenss.setTag(object);
		}
		
	}
	

}
