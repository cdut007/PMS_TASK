package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jameschen.framework.base.BaseDetailActivity;
import com.jameschen.framework.base.BaseEditActivity;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.ManagerService;
import com.thirdpart.model.ManagerService.OnReqHttpCallbackListener;
import com.thirdpart.model.PlanManager;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.IssueMenu;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.DisplayItemView;

public class IssueDetailActivity extends BaseDetailActivity {
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}


 IssueResult issueResult;
 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		issueResult = (IssueResult) getIntent().getSerializableExtra(Item.ISSUE);	
      setTitle("问题详情");
      initInfo();
 }
 
	
	
	private void initInfo() {

		final  List<WidgetItemInfo> itemInfos = new ArrayList<WidgetItemInfo>();
		 //R.id.  in array String
		 itemInfos.add(new WidgetItemInfo("0", null, null, 0, false));		
		
		  createItemListToUI(itemInfos, R.id.detail_container, new CreateItemViewListener() {

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
		  
		  
	}
	
	@Override
	protected void initView() {
		setContentView(R.layout.detail_ui);// TODO Auto-generated method stub
		super.initView();
		
	}
	


	

}
