package com.jameschen.framework.base;

import java.util.ArrayList;

import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.SorcllTextView;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class ListPopupWindowAdapter extends BaseAdapter {
	private ArrayList<String> mArrayList;
	private Context mContext;
	public ListPopupWindowAdapter(ArrayList<String> list, Context context) {
		super();
		this.mArrayList = list;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		if (mArrayList == null) {
			return 0;
		} else {
			return this.mArrayList.size();
		}
	}

	@Override
	public Object getItem(int position) {
		if (mArrayList == null) {
			return null;
		} else {
			return this.mArrayList.get(position);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	private SorcllTextView getButton(Context context) {
		SorcllTextView btn = new SorcllTextView(context);
	
		btn.setTextColor(Color.rgb(0x1a, 0x19, 0x17));
		

		LinearLayout.LayoutParams params = new LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		//btn.setPadding(20, 15, 20, 15);
		btn.setGravity(Gravity.CENTER);
		params.height= context.getResources().getDimensionPixelSize(
				R.dimen.margin_menu_item_height);
		
		// btn.setBackgroundColor(Color.TRANSPARENT);
		btn.setBackgroundResource(R.drawable.list_selector);
	
		btn.setLayoutParams(params);
		return btn;
	}
	private static ImageView getImageView(Context context) {
		ImageView img = new ImageView(context);
		img.setImageResource(R.drawable.light_blue);
		LinearLayout.LayoutParams params = new LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.height =1;
		int marginValue =  context.getResources().getDimensionPixelSize(
				R.dimen.margin_10dp);
		
		// img.setPadding(5, 0, 5, 0);
		img.setLayoutParams(params);
		return img;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();			
			LinearLayout layout = new LinearLayout(mContext);
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(
					AbsListView.LayoutParams.MATCH_PARENT,
					AbsListView.LayoutParams.WRAP_CONTENT);
			layout.setLayoutParams(params);
			layout.setOrientation(LinearLayout.VERTICAL);
			SorcllTextView btn = getButton(mContext);
			ImageView img = getImageView(mContext);
			layout.addView(btn);
			layout.addView(img);
			
			convertView = layout;
 holder.itemTextView = btn;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (this.mArrayList != null) {
			final String itemName = this.mArrayList.get(position);
			if (holder.itemTextView != null) {
				holder.itemTextView.setText(itemName);
			}
		}

		return convertView;

	}

	private class ViewHolder {
		TextView itemTextView;
	}

}