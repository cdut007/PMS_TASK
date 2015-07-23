package com.thirdpart.model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

import com.google.gson.reflect.TypeToken;
import com.jameschen.comm.utils.Util;
import com.jameschen.comm.utils.UtilsUI;
import com.jameschen.framework.base.UINetworkHandler;
import com.jameschen.widget.CustomSelectPopupWindow;
import com.jameschen.widget.CustomSelectPopupWindow.Category;
import com.jameschen.widget.CustomSelectPopupWindow.CategoryAdapter;
import com.thirdpart.model.entity.RetationshipDepartmentInfo;
import com.thirdpart.model.entity.Team;
import com.thirdpart.widget.ChooseItemView;

public class TeamMemberManager {
private static final String TAG = "TeamMemberManager";
Context context;


public TeamMemberManager(Context context){
	this.context = context;
}
	



 static List<Category> mCategories = new ArrayList<Category>();
List<Category> mChildCategories = new ArrayList<Category>();
	
public interface LoadUsersListener{

	void beginLoad(int type);

	void loadEndFailed(int type);

	void loadEndSucc(int type);

	void onSelcted(Category mParent, Category category);
	
}	
private LoadUsersListener listener;	
boolean showWindow;
public static   boolean filterDepart =true;
public void findDepartmentInfos( boolean show,final View view,LoadUsersListener loadUsersListener) {
	
	listener = loadUsersListener;
	showWindow = show;
	if (filterDepart) {

		
		if (mCategories.size()>0) {
			if (show) {
				showCategory(view, mCategories);	
			}
			
		}else {
			listener.beginLoad(0);
		}
		Type sToken = new TypeToken<List<Category>>() {
		}.getType();
		listener.beginLoad(1);
	PMSManagerAPI.getInstance(context).getSovers(new UINetworkHandler<List<Category>>(context,sToken) {

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
			listener.loadEndFailed(1);	
		}

		@Override
		public void callbackSuccess(int statusCode, Header[] headers,
				List<Category> response) {
			// TODO Auto-generated method stub
			if (response!=null) {
				mCategories = response;

				if (showWindow&&mCategories.size()==0) {
					showCategory(view, mCategories);	
				}
				listener.loadEndSucc(0);	
			
			}else {
				Log.i("member Error", "empty child return");
			}
			listener.loadEndSucc(1);	
		}

		
	});
		
		return;
	}
	if (mCategories.size()>0) {
		if (show) {
			showCategory(view, mCategories);	
		}
		
	}else {
		listener.beginLoad(0);
	}
	PMSManagerAPI.getInstance(context).getDepartment(new UINetworkHandler<RetationshipDepartmentInfo>(context) {

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
				RetationshipDepartmentInfo response) {
			// TODO Auto-generated method stub
			if (response!=null&&response.getDepartments().size()>0) {
				mCategories = response.getDepartments();
				update(view);	
			}else {
				Log.i("member Error", "empty return");
			}
			listener.loadEndSucc(0);	
		}
	});
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
CustomSelectPopupWindow customSelectPopupWindow = new CustomSelectPopupWindow();

	 private  void showCategory(View view, List<Category> categoryItems) {
		 DisplayMetrics displaymetrics =context. getResources().getDisplayMetrics();
		Log.i("screenWidth", "w="+displaymetrics.widthPixels);
		 final List<Category> sCategories =new ArrayList<Category>(categoryItems);
		
		 if (filterDepart) {
			 List<String> names = new ArrayList<String>();
				
				for (Category category : categoryItems) {
					
					names.add(category.getName());
				}

				((ChooseItemView)view).showMenuItem(categoryItems, names,
						new ChooseItemView.onDismissListener<Category>() {

							@Override
							public void onDismiss(Category item) {
								Log.i(TAG, "name==" + item.getName());
								// TODO Auto-generated method stub
								listener.onSelcted(item,item);
							}

						});
			
			 return;
		}
		 
		 
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
			customSelectPopupWindow.setItemOnClickListener(new CustomSelectPopupWindow.OnItemClickListener() {
				

				@Override
				public void onItemClick(int index) {
					
					loadChild(sCategories.get(index));
					
				}
			});
			
			customSelectPopupWindow.setOnDismissListener(new CustomSelectPopupWindow.OnDismissListener() {

				@Override
				public void onDismiss() {
					showWindow = false;
				}
			});
		}
	 
	 private void showChildArea(final List<Category> childAreas,final Category mParent) {
		 customSelectPopupWindow.getChildListView().setAdapter(new CategoryAdapter(context, childAreas));
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
	 private void loadChild(final Category parent) {
		
		if (parent.getId().equals(lastParentId)) {
			return;
		}
		updateChild(parent);
		
		 Type sToken = new TypeToken<List<Category>>() {
			}.getType();
			listener.beginLoad(1);
		PMSManagerAPI.getInstance(context).getDepartmentUser(parent.getId(),new UINetworkHandler<List<Category>>(context,sToken) {

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
				listener.loadEndFailed(1);	
			}

			@Override
			public void callbackSuccess(int statusCode, Header[] headers,
					List<Category> response) {
				// TODO Auto-generated method stub
				if (response!=null) {
					mChildCategories = response;
					
					updateChild(parent);	
				}else {
					Log.i("member Error", "empty child return");
				}
				listener.loadEndSucc(1);	
			}

			
		});
	}
	 private void updateChild(Category parent) {
			// TODO Auto-generated method stub
		 if (Util.isActivyFinish(context)) {
				return;
			}
			showChildArea(mChildCategories, parent);
		}
}
