package com.thirdpart.model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

import com.google.gson.reflect.TypeToken;
import com.jameschen.comm.utils.Util;
import com.jameschen.comm.utils.UtilsUI;
import com.jameschen.framework.base.UINetworkHandler;
import com.jameschen.widget.CustomSelectPopupWindow;
import com.jameschen.widget.CustomSelectPopupWindow.Category;
import com.jameschen.widget.CustomSelectPopupWindow.CategoryAdapter;
import com.jameschen.widget.WitnessSelectPopupWindow;
import com.thirdpart.model.ManagerService.ManagerNetworkHandler;
import com.thirdpart.model.entity.Department;
import com.thirdpart.model.entity.RetationshipDepartmentInfo;
import com.thirdpart.model.entity.Team;
import com.thirdpart.model.entity.UserInfo;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.ChooseItemView;

public class WitnessTeamManager {
private static final String TAG = "WitnessTeamManager";
Context context;


public WitnessTeamManager(Context context){
	this.context = context;
}
	



 static List<Team> mCategories = new ArrayList<Team>();
List<UserInfo> mChildCategories = new ArrayList<UserInfo>();
	
public interface LoadUsersListener{

	void beginLoad(int type);

	void loadEndFailed(int type);

	void loadEndSucc(int type);

	void onSelcted(Team mParent, UserInfo category);
	
}	
private LoadUsersListener listener;	
boolean showWindow;
public void findWitnessTeamInfos( boolean show,final View view,LoadUsersListener loadUsersListener) {
	
	listener = loadUsersListener;
	showWindow = show;
	if (mCategories.size()>0) {
		if (show) {
			showCategory(view, mCategories);	
		}
		
	}else {
		listener.beginLoad(0);
	}
	

	UINetworkHandler<List<Team>> hander = new UINetworkHandler<List<Team>>(context){

		@Override
		public void start() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void finish() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void callbackFailure(int statusCode, Header[] headers,
				String response) {
			// TODO Auto-generated method stub
			listener.loadEndFailed(0);	
		}

		@Override
		public void callbackSuccess(int statusCode, Header[] headers,
				List<Team> response) {
			// TODO Auto-generated method stub
			if (response!=null&&response.size()>0) {
				mCategories.clear();
				mCategories = response;
				update(view);	
			}else {
				Log.i("member Error", "empty return");
			}
			listener.loadEndSucc(0);	
		}
	};
	
	Type sToken = new TypeToken<List<Team>>() {
	}.getType();
	hander.setType(sToken);

	PMSManagerAPI.getInstance(context).witnessTeamList(hander);

	

}


protected void update(View view ) {
	if (!showWindow) {
		return;
	}
	if (Util.isActivyFinish(context)) {
		return;
	}
	if (customSelectPopupWindow.isShown()) {
		customSelectPopupWindow.updateParentData(mCategories);	
	}else {
		showCategory(view, mCategories);
	}
	
}
WitnessSelectPopupWindow customSelectPopupWindow = new WitnessSelectPopupWindow();

	 private  void showCategory(View view, List<Team> categoryItems) {
		 DisplayMetrics displaymetrics =context. getResources().getDisplayMetrics();
		Log.i("screenWidth", "w="+displaymetrics.widthPixels);
		 final List<Team> sCategories =new ArrayList<Team>(categoryItems);	 
		 
		 try {
			customSelectPopupWindow.showActionWindow(view, context, sCategories);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("window error", "window eero.r...");
			return;
		}
			int width =displaymetrics.widthPixels-UtilsUI.getPixByDPI(context, 20);
			customSelectPopupWindow.getPopupWindow().update(width, LayoutParams.WRAP_CONTENT);
			customSelectPopupWindow.setItemOnClickListener(new WitnessSelectPopupWindow.OnItemClickListener() {
				

				@Override
				public void onItemClick(int index) {
					
					loadChild(sCategories.get(index));
					
				}
			});
			
			customSelectPopupWindow.setOnDismissListener(new WitnessSelectPopupWindow.OnDismissListener() {

				@Override
				public void onDismiss() {
					showWindow = false;
				}
			});
		}
	 
	 private void showChildArea(final List<UserInfo> childAreas,final Team mParent) {
		 customSelectPopupWindow.getChildListView().setAdapter(new ChildAdapter(context, childAreas));
		 customSelectPopupWindow.showChildArea();
			customSelectPopupWindow.getChildListView().setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					customSelectPopupWindow.dismiss();
					listener.onSelcted(mParent,childAreas.get(arg2));
				
				}
			});
			
		}
	 
	 String lastParentId ;
	 private void loadChild(final Team parent) {
		
		if (parent.getId().equals(lastParentId)) {
			return;
		}
		updateChild(parent);
		
	 }
	 private void updateChild(Team parent) {
			// TODO Auto-generated method stub
		 if (Util.isActivyFinish(context)) {
				return;
			}
			showChildArea(mChildCategories, parent);
		}
	 
	 
		public static class ChildAdapter extends ArrayAdapter<UserInfo> {
			private Context context;

			public ChildAdapter(Context context, final List<UserInfo> objects) {
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

			public static void attchReourceToView(TextView view, UserInfo item) {
				view.setText(item.getName());
				view.setCompoundDrawablePadding(UtilsUI.getPixByDPI(view.getContext(), 10));
				if (true) {
					return;
				}
			}

			public static void attchReourceToView(TextView view, UserInfo item,int rightRes) {
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
