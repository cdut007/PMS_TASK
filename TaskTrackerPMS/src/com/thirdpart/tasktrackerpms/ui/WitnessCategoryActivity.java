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
import com.thirdpart.model.entity.IssueMenu;
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

public class WitnessCategoryActivity extends BaseEditActivity {

	private Fragment mFragment;
	protected IssueMenu menu;
	
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
	
	
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		menu= (IssueMenu) intent.getSerializableExtra(Item.WITNESS_CATEGORY);
		setTitle(menu.getContent());
		initInfo();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (isTaskRoot()) {
			startActivity(new Intent(this,MainActivity.class));
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
	
	public void setCommitBtnLayout(boolean isVisiable) {
		// TODO Auto-generated method stub
		if (isVisiable) {
			findViewById(R.id.commit_btn_layout).setVisibility(View.VISIBLE);
				
		} else {
			findViewById(R.id.commit_btn_layout).setVisibility(View.GONE);
			
		}
	}
	
	private void initInfo() {
		setCommitBtnLayout(false);
		
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
		  
		// Make sure fragment is created.
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			mFragment = fm.findFragmentByTag(WitnessCategoryFragment.class.getName());
			if (mFragment == null) {
				Bundle bundle = new Bundle();
				mFragment = WitnessFragment.instantiate(this, WitnessCategoryFragment.class.getName(), bundle);
				ft.add(R.id.fragment_content, mFragment, WitnessCategoryFragment.class.getName());
			}

			ft.commit();
		  
	}
	
	@Override
	public void callCommitBtn(View v) {
		// TODO Auto-generated method stub
		super.callCommitBtn(v);
		DeliveryPlanFragment deliveryPlanFragment =(DeliveryPlanFragment) mFragment;
		deliveryPlanFragment.commit();
	}
}

