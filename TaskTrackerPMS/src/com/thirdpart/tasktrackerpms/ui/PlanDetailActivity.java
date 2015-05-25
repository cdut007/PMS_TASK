package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jameschen.framework.base.BaseDetailActivity;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.ManagerService;
import com.thirdpart.model.ManagerService.OnReqHttpCallbackListener;
import com.thirdpart.model.PlanManager;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.RollingPlanDetail;
import com.thirdpart.tasktrackerpms.R;

public class PlanDetailActivity extends BaseDetailActivity implements OnReqHttpCallbackListener{
	
 private RollingPlanDetail mRollingPlanDetail;	
 
 private PlanManager planManager ;
 RollingPlan rollingPlan;
 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
      rollingPlan = (RollingPlan) getIntent().getSerializableExtra(Item.PLAN);	
      planManager = (PlanManager) ManagerService.getNewManagerService(this, PlanManager.class,this);
      
      execFetechDetail();
 }
 
	private void execFetechDetail() {
	// TODO Auto-generated method stub
		planManager.planDetail(rollingPlan.getId());
}

	
	private void updateInfo() {

		final  List<WidgetItemInfo> itemInfos = new ArrayList<WidgetItemInfo>();
		 //R.id.  in array String
		 itemInfos.add(new WidgetItemInfo("0", "焊口号：", "test", WidgetItemInfo.DISPLAY, false));		
		 itemInfos.add(new WidgetItemInfo("1", "机组号：", "test", WidgetItemInfo.DISPLAY, false));
		 itemInfos.add(new WidgetItemInfo("2", "区域号：", "test", WidgetItemInfo.DISPLAY, false));
		 itemInfos.add(new WidgetItemInfo("3", "图纸号：", "test", WidgetItemInfo.DISPLAY, false));
		 itemInfos.add(new WidgetItemInfo("4", "焊接控制单号：", "test", WidgetItemInfo.DISPLAY, false));
		 itemInfos.add(new WidgetItemInfo("5", "RCCM：", "test", WidgetItemInfo.DISPLAY, false));
		 itemInfos.add(new WidgetItemInfo("6", "质量计划号：", "test", WidgetItemInfo.DISPLAY, false));
		 itemInfos.add(new WidgetItemInfo("7", "计划施工日期：", "test", WidgetItemInfo.DISPLAY, false));
		 itemInfos.add(new WidgetItemInfo("8", "", "", WidgetItemInfo.DEVIDER, false));
		 itemInfos.add(new WidgetItemInfo("9", "焊查看工序日期：", "test", WidgetItemInfo.DISPLAY, true));
		 
		 
		 
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
					switch (widgetItemInfo.type) {
					case WidgetItemInfo.DISPLAY:{
						convertView = inflater.inflate(R.layout.common_display_item, viewgroup,false);
					
					}
						break;
					case WidgetItemInfo.DEVIDER:
						convertView = inflater.inflate(R.layout.item_divider, viewgroup,false);
						
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
				}else {
					
				}
				Log.i(TAG, "type="+widgetItemInfo.type);
				//update
				switch (widgetItemInfo.type) {
				case WidgetItemInfo.DISPLAY:{
					TextView textView = (TextView) convertView.findViewById(R.id.common_display_item_name);
					Log.i(TAG, "name="+widgetItemInfo.name);
					textView.setText(widgetItemInfo.name);
				}
					break;
				case WidgetItemInfo.DEVIDER:
					
					break;

				default:
					break;
				}
				//bind tag
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

	@Override
	public void start(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void failed(String name, int statusCode, Header[] headers,
			String response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finish(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void succ(String name, int statusCode, Header[] headers,
			Object response) {
		// TODO Auto-generated method stub
		if (name.equals(PlanManager.ACTION_PLAN_DETAIL)) {
			updateInfo();
		}
		
	}
	

}
