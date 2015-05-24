package com.thirdpart.tasktrackerpms.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.jameschen.framework.base.BaseDetailActivity;
import com.jameschen.framework.base.BaseEditActivity;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.ManagerService;
import com.thirdpart.model.PlanManager;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.RollingPlanDetail;
import com.thirdpart.model.entity.WorkStepDetail;
import com.thirdpart.tasktrackerpms.R;

public class PlanDetailActivity extends BaseDetailActivity{
	
 private RollingPlanDetail mRollingPlanDetail;	
 
 private PlanManager planManager ;
 RollingPlan rollingPlan;
 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
      rollingPlan = (RollingPlan) getIntent().getSerializableExtra(Item.PLAN);	
      planManager = (PlanManager) ManagerService.getNewManagerService(this, PlanManager.class);
      execFetechDetail();
 }
 
	private void execFetechDetail() {
	// TODO Auto-generated method stub
	
}

	
	private void updateInfo() {

		final  List<WidgetItemInfo> itemInfos = new ArrayList<WidgetItemInfo>();
		  WidgetItemInfo info = new WidgetItemInfo();
		  
		  info.tag="";
		  info.name="焊口号：";
		 itemInfos.add(info);
		
		 info.tag="";
		  info.name="机组号：";
		 itemInfos.add(info);
		 
		 info.tag="";
		  info.name="区域号：";
		 itemInfos.add(info);
		 
		 info.tag="";
		  info.name="图纸号：";
		 itemInfos.add(info);
		 
		 info.tag="";
		  info.name="焊接控制单号：";
		 itemInfos.add(info);
		 
		 info.tag="";
		  info.name="RCCM：";
		 itemInfos.add(info);
		 
		 info.tag="";
		  info.name="质量计划号：";
		 itemInfos.add(info);
		 
		 info.tag="";
		  info.name="计划施工日期：";
		 itemInfos.add(info);
		 
		 
		 info.tag="";
		  info.name="";
		  info.type=WidgetItemInfo.DEVIDER;
		 itemInfos.add(info);
		 
		 info.tag="";
		  info.name="焊查看工序日期：";
		  info.bindClick=true;
		 itemInfos.add(info);
		 
		 
		  createItemListToUI(itemInfos, R.id.detail_container, new CreateItemViewListener() {

			@Override
			public View oncreateItem(int index, View convertView,
					LayoutInflater layoutInflater) {
				// TODO Auto-generated method stub
				//if exsit just update , otherwise create it.
				final WidgetItemInfo widgetItemInfo = itemInfos.get(index);
				
				if (convertView ==null) {
					
					switch (widgetItemInfo.type) {
					case WidgetItemInfo.DISPLAY:
						convertView = layoutInflater.inflate(R.layout.common_display_item, null);
						
						break;
					case WidgetItemInfo.DEVIDER:
						convertView = layoutInflater.inflate(R.layout.item_divider, null);
						
						break;

					default:
						break;
					}
					
					if (widgetItemInfo.bindClick) {
						convertView.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								if (widgetItemInfo.tag.equals("xx")) {
									go2WorkStepDetail();
								}else {
									
								}
							}
						});
					}
				}
				
				//update
				convertView.setTag(widgetItemInfo);
				return convertView;
			}
		}, true);
	}
	
	@Override
	protected void initView() {
		setContentView(R.layout.detail_ui);// TODO Auto-generated method stub
		super.initView();
		
	   updateInfo();
	}
	
	void go2WorkStepDetail(){
		Intent intent = new Intent(this, WorkStepListActivity.class);
		
		intent.putExtra(ConstValues.ID, Long.parseLong(rollingPlan.getId()));
		startActivity(intent);
	}
	

}
