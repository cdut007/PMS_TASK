package com.thirdpart.tasktrackerpms.ui;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jameschen.framework.base.BaseActivity;
import com.jameschen.framework.base.BaseEditActivity;
import com.jameschen.framework.base.BaseDetailActivity.CreateItemViewListener;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.entity.DepartmentInfo;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.tasktrackerpms.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RatingBar;

public class DeliveryPlanListActivity extends BaseEditActivity {

	private Fragment mFragment;
	DepartmentInfo departmentInfo;
	private String title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		departmentInfo = (DepartmentInfo) getIntent().getSerializableExtra(Item.PLAN);			    
		 title = departmentInfo.getDepartment().getName();
		setTitle(title);
			// Make sure fragment is created.
		initInfo();
	}

	private void initInfo() {

		final  List<WidgetItemInfo> itemInfos = new ArrayList<WidgetItemInfo>();
		 //R.id.  in array String
		 itemInfos.add(new WidgetItemInfo("0", null, null, 0, false));		
		
		  createItemListToUI(itemInfos, R.id.edit_container, new CreateItemViewListener() {

			@Override
			public View oncreateItem(int index, View convertView,
					ViewGroup viewgroup) {
				// TODO Auto-generated method stub
				//if exsit just update , otherwise create it.
				
				final WidgetItemInfo widgetItemInfo = itemInfos.get(index);
				if (convertView ==null) {
					//create
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						
					convertView = inflater.inflate(R.layout.fragment_main, viewgroup, false);	
					
					
				}else {
					
				}
				
				//bind tag
				convertView.setTag(widgetItemInfo);
				return convertView;
			}
		}, false);
		  
		  //make container
		  FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			mFragment = fm.findFragmentByTag(DeliveryPlanFragment.class.getName());
			if (mFragment == null) {
				Bundle bundle = new Bundle();
				bundle.putString(Item.PLAN, ""+title);
				bundle.putString("teamId", departmentInfo.getDepartment().getId());
				mFragment = DeliveryPlanFragment.instantiate(this, DeliveryPlanFragment.class.getName(), bundle);
				ft.add(R.id.fragment_content, mFragment, DeliveryPlanFragment.class.getName());
			}

			ft.commit();
		  
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.list_edit_ui);
	}
	
	@Override
	public void callCommitBtn(View v) {
		// TODO Auto-generated method stub
		super.callCommitBtn(v);
		DeliveryPlanFragment deliveryPlanFragment =(DeliveryPlanFragment) mFragment;
		deliveryPlanFragment.commit();
	}
}

