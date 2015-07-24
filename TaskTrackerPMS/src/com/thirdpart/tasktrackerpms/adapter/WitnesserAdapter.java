package com.thirdpart.tasktrackerpms.adapter;

import java.text.DecimalFormat;
import java.util.Scanner;

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
	public boolean deliveryWitness,sanChooseWitness;
	public WitnesserAdapter(Context context, boolean deliveryWitness,boolean scanChoose) {
		super(context,R.layout.witness_item);
		this.context = context;
		this.deliveryWitness = deliveryWitness;
		sanChooseWitness = scanChoose;
	}

	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		convertView = super.getView(position, convertView, parent);
		ItemHoldView sHoldView = (ItemHoldView) convertView.getTag();
		sHoldView.setInfo(position);
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
	
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return super.isEnabled(position);
	}
	



	private final static class ItemHoldView extends HoldView<WitnessDistributed> {

		TextView noTextView,adressTextView;
		TextView chooseWitenss;
	boolean Scanner = false,chooseWitness=false;
		@Override
		protected void initChildView(View convertView,
				final MyBaseAdapter<WitnessDistributed> myBaseAdapter) {
			// TODO Auto-generated method stub
			noTextView = (TextView) convertView.findViewById(R.id.witness_index_item);
			adressTextView = (TextView) convertView.findViewById(R.id.witnenss_address);
			chooseWitenss = (TextView) convertView.findViewById(R.id.witness_choose);
		Scanner = ((WitnesserAdapter)myBaseAdapter).deliveryWitness;	
		chooseWitness = 	 ((WitnesserAdapter)myBaseAdapter).sanChooseWitness;
		chooseWitenss.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Context context = v.getContext();
					
					Intent intent= new Intent(context,WitnessChooseActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					WitnessDistributed workStep = (WitnessDistributed) v.getTag();
					intent.putExtra(Item.WITNESS, workStep);
					if (Scanner) {
						intent.putExtra("scan", true);
					}
					
					if ( ((WitnesserAdapter)myBaseAdapter).sanChooseWitness) {
						intent.putExtra("scan", true);
					}
					
					context.startActivity(intent);
				}
			});
			
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Context context = v.getContext();
				if (Scanner) {
					return;
				}
				Intent intent= new Intent(context,WitnessUpdateActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				WitnessDistributed workStep = (WitnessDistributed)chooseWitenss.getTag();
				intent.putExtra("scan", Scanner);
				intent.putExtra(Item.WITNESS, workStep);
				context.startActivity(intent);	
			}
		});
		}
		public void setInfo( int position) {
			// TODO Auto-generated method stub
			noTextView.setText(""+(position+1));
		}
		@Override
		protected void setInfo(WitnessDistributed object) {
			// TODO Auto-generated method stub
			adressTextView.setText(object.getWitnessaddress());
			chooseWitenss.setTag(object);
			chooseWitenss.setText(Scanner?"查看见证":"选择见证人");
			if (chooseWitness) {
				chooseWitenss.setText("查看见证");
			}
		}
		
	}
	

}
