package com.thirdpart.widget;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.tasktrackerpms.R;

public class PopupWindowUtil implements OnClickListener {
	PopupWindow popupWindow;
	TextView mView;
	Object selectItem;
	List<String> mList;

	public PopupWindowUtil() {
	}

	public PopupWindowUtil(TextView view) {
		mView = view;
	}

	public PopupWindowUtil(TextView view, Object selectItem) {
		mView = view;
		context = view.getContext();
		this.selectItem = selectItem;
	}

	public void setItemOnClickListener(OnItemClickListener itemClickListener) {
		// TODO Auto-generated method stub
		onItemClickListener = itemClickListener;
	}

	OnItemClickListener onItemClickListener;

	public interface OnItemClickListener {
		void onItemClick(int index);
	}

	int width = 0;
	Context context;
	
	public void showActionWindow(View parent, Context context, List<String> list,
			int  width) {
		this.context = context;
		// final RingtoneclipModel currentData = model;
		// final int res_id = currentData.getId();
		mList = list;
		int[] location = new int[2];
		int popWidth = width;
		parent.getLocationOnScreen(location);
		View view = getView(context, list);
		popupWindow = new PopupWindow(view, popWidth, LayoutParams.WRAP_CONTENT);// new
																					// PopupWindow(view,
																					// popWidth,
																					// LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAsDropDown(parent, 0, 0);
	}
	
	public void showActionWindow(View parent, Context context, List<String> list) {
		// final RingtoneclipModel currentData = model;
		// final int res_id = currentData.getId();
		mList = list;
		int[] location = new int[2];
		int popWidth = context.getResources().getDimensionPixelOffset(
				R.dimen.popupWindow_width);
		parent.getLocationOnScreen(location);
		View view = getView(context, list);
		popupWindow = new PopupWindow(view, popWidth, LayoutParams.WRAP_CONTENT);// new
																					// PopupWindow(view,
																					// popWidth,
																					// LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAsDropDown(parent, 0, 0);
	}

	private View getView(Context context, List<String> list) {
		LinearLayout layout = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundResource(R.drawable.dialogimg);
		int size = list.size();
		for (int i = 0; i < size; i++) {
			if (i != size - 1) {
				String name = "";
				name = list.get(i);
				
				Button btn = getButton(context, name, i, size);
				ImageView img = getImageView(context);
				layout.addView(btn);
				layout.addView(img);
			} else {
				String name = "";
				name = list.get(i);
				Button btn = getButton(context, name, i, size);
				layout.addView(btn);
			}
		}

		return layout;
	}

	private Button getButton(Context context, String text, int i, int size) {
		Button btn = new Button(context);
		btn.setText(text);
		btn.setTextColor(Color.rgb(0x1a, 0x19, 0x17));
		btn.setTag(i);

		LinearLayout.LayoutParams params = new LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		//btn.setPadding(20, 15, 20, 15);
		btn.setGravity(Gravity.CENTER);
		params.height= context.getResources().getDimensionPixelSize(
				R.dimen.margin_menu_item_height);
		int marginValue =  context.getResources().getDimensionPixelSize(
				R.dimen.margin_10dp);
		if (i == 0) {
			params.topMargin=marginValue;
		
		} else if (i == size - 1) {
			params.bottomMargin = marginValue;

		} else {

		}
		
		params.leftMargin = marginValue;
		params.rightMargin = marginValue;
		// btn.setBackgroundColor(Color.TRANSPARENT);
		btn.setBackgroundResource(R.drawable.list_selector);
		btn.setOnClickListener(this);
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
		params.leftMargin =marginValue;
		params.rightMargin = marginValue;
		// img.setPadding(5, 0, 5, 0);
		img.setLayoutParams(params);
		return img;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Integer numInteger = Integer.valueOf(v.getTag().toString());
		String text = null;
		
		text = mList.get(numInteger);
		
		if (mView != null) {
			mView.setText(text);
		}
		popupWindow.dismiss();
		
		if (onItemClickListener != null) {
			onItemClickListener.onItemClick(numInteger);
		}

	}
}
