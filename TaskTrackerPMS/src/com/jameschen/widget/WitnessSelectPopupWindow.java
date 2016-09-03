package com.jameschen.widget;

import java.util.List;

import android.R.integer;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jameschen.comm.utils.Util;
import com.jameschen.comm.utils.UtilsUI;
import com.jameschen.framework.base.BaseActivity;
import com.thirdpart.model.entity.Department;
import com.thirdpart.model.entity.Team;
import com.thirdpart.tasktrackerpms.R;

public class WitnessSelectPopupWindow   {
	

	PopupWindow popupWindow;
	TextView mView;
	Object selectItem;
	List<Team> mList;

	public WitnessSelectPopupWindow() {
	}

	public WitnessSelectPopupWindow(TextView view) {
		mView = view;
	}

	public WitnessSelectPopupWindow(TextView view, Object selectItem) {
		mView = view;
		context = view.getContext();
		this.selectItem = selectItem;
	}

	public void setItemOnClickListener(OnItemClickListener itemClickListener) {
		// TODO Auto-generated method stub
		onItemClickListener = itemClickListener;
	}

	public void setOnDismissListener(OnDismissListener dismissListener) {
		// TODO Auto-generated method stub
		this.onDismissListener = dismissListener;
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			
			@Override
			public void onDismiss() {
				onDismissListener.onDismiss();
			}
		});
	}
	
	OnItemClickListener onItemClickListener;
	OnDismissListener  onDismissListener;
	public interface OnItemClickListener {
		void onItemClick(int index);
	}
	public interface OnDismissListener {
		void onDismiss();
	}

	int width = 0;
	Context context;
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void showActionWindow(View parent, BaseActivity context, List<Team> list,
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
	
	public PopupWindow getPopupWindow(){
		return popupWindow;
	}
	public boolean isShown() {
		// TODO Auto-generated method stub
		return popupWindow.isShowing();
	}
	
	public void showActionWindow(View parent, Context context, List<Team> list) throws Exception{
		// final RingtoneclipModel currentData = model;
		// final int res_id = currentData.getId();
		mList = list;
		int[] location = new int[2];
		parent.getLocationOnScreen(location);
		View view = getView(context, list);
		if (width>0) {
			popupWindow = new PopupWindow(view, width, UtilsUI.getPixByDPI(context, 320));
		
		}else {
			popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, UtilsUI.getPixByDPI(context, 320));// new
			// PopupWindow(view,
			// popWidth,
			// LayoutParams.WRAP_CONTENT);
		}
	
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAsDropDown(parent, 0, 0);
	}
	
	private ListView childListView;
	public ListView getChildListView() {
		// TODO Auto-generated method stub
		return childListView;
	}
	public void dismiss() {
		popupWindow.dismiss();
	}
	private ProgressBar loadingbar;
	private TextView loadingNoData;
	private ListView areaGridView;
	private CategoryAdapter adapter;
	
	
	
	private View getView(Context context, List<Team> list) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		ViewGroup layout  = (ViewGroup) inflater.inflate(R.layout.category_select_ui, null);
			 areaGridView = (ListView) layout.findViewById(R.id.area_label_listView);
			childListView  = (ListView) layout.findViewById(R.id.child_area_label_listView);
			loadingbar =  (ProgressBar) layout.findViewById(R.id.child_area_label_loading);
			loadingNoData =  (TextView) layout.findViewById(R.id.child_area_label_tv);
			
			TextView  parant = (TextView)layout.findViewById(R.id.category);
			TextView  child = (TextView)layout.findViewById(R.id.child_area_title);
			parant.setText("见证类别");
			child.setText("见证人员");
		 adapter = new CategoryAdapter(context,list);
			areaGridView.setAdapter(adapter);
			areaGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					selectPosition=position;
					adapter.setCurrentSelection(position);
					beginLoadingData();
					if (onItemClickListener != null) {
						onItemClickListener.onItemClick(position);
					}
				}
			});
			
			//reselected
//			adapter.setCurrentSelection(selectPosition);
//			areaGridView.setSelection(selectPosition);
//			beginLoadingData();
//			if (onItemClickListener != null&&selectPosition>0) {//not select all
//				onItemClickListener.onItemClick(selectPosition);
//			}else {
//				showLoadingFinished();
//			}
		return layout;
	}
	
	

	private int selectPosition=-1,childSelectPosition=0;
	
	private void beginLoadingData() {
		loadingbar.setVisibility(View.VISIBLE);
		loadingNoData.setVisibility(View.GONE);
		childListView.setVisibility(View.GONE);
	}
	
	private void showLoadingFinished() {
		childListView.setVisibility(View.VISIBLE);
		loadingbar.setVisibility(View.GONE);
		loadingNoData.setVisibility(View.GONE);
	}
	private void showNodata() {
		loadingNoData.setVisibility(View.VISIBLE);
		loadingbar.setVisibility(View.GONE);
	}
	
	public void updateParentData(List<Team> mCategories) {
		// TODO Auto-generated method stub
		if (mCategories.size()>0) {
			adapter.clear();
			adapter.addAll(mCategories);
			adapter.notifyDataSetChanged();
		}
		
	}
	
	public void showChildArea() {
		if (childListView.getAdapter()==null) {
			beginLoadingData();
			return;
		}
		if (childListView.getAdapter().getCount()>0) {
			showLoadingFinished();
		}else {
			showNodata();
		}
		
		
	}
	boolean showChildArea=true;
	public void setChildAreaShow(boolean childAreaShow) {
		this.showChildArea = childAreaShow;
	}

	public static class CategoryAdapter extends ArrayAdapter<Team> {
		private Context context;

		public CategoryAdapter(Context context, final List<Team> objects) {
			super(context, -1, objects);
			this.context = context;
		}
		int pos;
		public void setCurrentSelection(int position) {
			// TODO Auto-generated method stub
			pos = position;
			notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView != null) {
				holder = (ViewHolder) convertView.getTag();
				if (pos == position) {
					convertView.setBackgroundColor(context.getResources().getColor(
							R.color.area_item_select));
				}else {
					convertView.setBackgroundResource(R.drawable.menu_selector);
				}
				attchReourceToView(holder.item, getItem(position));
				return convertView;
			}
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = (ViewGroup) inflater.inflate(R.layout.category_item,
					parent, false);
			holder = new ViewHolder();
			holder.item = (TextView) convertView.findViewById(R.id.item_text1);
			convertView.setTag(holder);
			if (pos == position) {
				convertView.setBackgroundColor(context.getResources().getColor(
						R.color.area_item_select));
			}else {
				convertView.setBackgroundResource(R.drawable.menu_selector);
			}
			attchReourceToView(holder.item, getItem(position));
			
			return convertView;
		}

		public static void attchReourceToView(TextView view, Team item) {
			view.setText(item.getName());
			view.setCompoundDrawablePadding(UtilsUI.getPixByDPI(view.getContext(), 10));
			if (true) {
				return;
			}
		}

		public static void attchReourceToView(TextView view, Team item,int rightRes) {
			view.setText(item.getName());
			view.setCompoundDrawablePadding(UtilsUI.getPixByDPI(view.getContext(), 1));
			if (true) {
				return;
			}
		}
		
		
		final class ViewHolder {
			TextView item;
		}

	}



}
