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
import com.jameschen.framework.base.BaseEditActivity;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.ManagerService;
import com.thirdpart.model.ManagerService.OnReqHttpCallbackListener;
import com.thirdpart.model.PlanManager;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.DisplayItemView;

public class PlanDetailActivity extends BaseDetailActivity {
	
 
 private PlanManager planManager ;
 RollingPlan rollingPlan;
 boolean scan = false;
 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
      rollingPlan = (RollingPlan) getIntent().getSerializableExtra(Item.PLAN);	
      scan = getIntent().getBooleanExtra("scan", false);
      if (rollingPlan == null) {
    	  scan  = false;
    	  rollingPlan = (RollingPlan) getIntent().getSerializableExtra(Item.TASK);	
      }
      
      planManager = (PlanManager) ManagerService.getNewManagerService(this, PlanManager.class,this);
      
      updateInfo();
      execFetechDetail();
 }
 
	private void execFetechDetail() {
	// TODO Auto-generated method stub
		if (rollingPlan.getId()==null) {
			Log.i(TAG, "plan id is null");
			return;
		}
		planManager.planDetail(rollingPlan.getId());
}

	
	private void updateInfo() {

		final  List<WidgetItemInfo> itemInfos = new ArrayList<WidgetItemInfo>();
		 //R.id.  in array String
		boolean  isHankou = false;
		String type = rollingPlan.getSpeciality();
		if (type==null) {
			 setTitle("明细");
		}else {
			 if (PlanManager.isHankou(type)) {
				  isHankou = true;
				  setTitle("焊口明细"); 
			}else {
				setTitle("支架明细");
			}	
		}
		
		 
		  
		 itemInfos.add(new WidgetItemInfo("0", isHankou?"焊口号：":"支架号：", rollingPlan.getWeldno(), WidgetItemInfo.DISPLAY, false));		
		 itemInfos.add(new WidgetItemInfo("1", "机组号：", rollingPlan.getId(), WidgetItemInfo.DISPLAY, false));
		 itemInfos.add(new WidgetItemInfo("2", "区域号：", rollingPlan.getAreano(), WidgetItemInfo.DISPLAY, false));
		 itemInfos.add(new WidgetItemInfo("3", "图纸号：", rollingPlan.getDrawno(), WidgetItemInfo.DISPLAY, false));
		 itemInfos.add(new WidgetItemInfo("4", isHankou?"焊接控制单号：":"支架控制单号：", rollingPlan.getWeldlistno(), WidgetItemInfo.DISPLAY, false));
		 itemInfos.add(new WidgetItemInfo("5", "RCCM：", rollingPlan.getRccm(), WidgetItemInfo.DISPLAY, false));
		 itemInfos.add(new WidgetItemInfo("6", "质量计划号：", rollingPlan.getQualityplanno(), WidgetItemInfo.DISPLAY, false));
		 itemInfos.add(new WidgetItemInfo("7", "计划施工日期：", rollingPlan.getPlandate(), WidgetItemInfo.DISPLAY, false));
		 itemInfos.add(new WidgetItemInfo("8", "", "", WidgetItemInfo.DEVIDER, false));
		if (isHankou) {
			 itemInfos.add(new WidgetItemInfo("9", "查看工序详情", "", WidgetItemInfo.DISPLAY, true));
					
		}
		 
		 
		 
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
						convertView = new DisplayItemView(PlanDetailActivity.this);
					
					}
						break;
					case WidgetItemInfo.DEVIDER:
						convertView = inflater.inflate(R.layout.item_divider, viewgroup,false);
						
						break;

					default:
						break;
					}
					
					if (widgetItemInfo.bindClick) {
						convertView.setBackgroundResource(R.drawable.item_bg_state);
						convertView.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								if (widgetItemInfo.tag.equals("9")) {
									go2WorkStepDetail();
								}else {
									
								}
							}
						});
					}
				}else {
					
				}
				
				//update
				switch (widgetItemInfo.type) {
				case WidgetItemInfo.DISPLAY:{
					DisplayItemView displayItemView = (DisplayItemView) convertView;
					displayItemView.setNameAndContent(widgetItemInfo.name, widgetItemInfo.content);
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
		
	}
	
	void go2WorkStepDetail(){
		Intent intent = new Intent(this, PlanWorkStepListActivity.class);
		
		intent.putExtra("scan",scan);
		intent.putExtra(ConstValues.ID, Long.parseLong(rollingPlan.getId()));
		startActivity(intent);
	}

	
	@Override
		public void failed(String name, int statusCode, Header[] headers,
				String response) {
			// TODO Auto-generated method stub
			super.failed(name, statusCode, headers, response);
		}

	

	@Override
	public void succ(String name, int statusCode, Header[] headers,
			Object response) {
		// TODO Auto-generated method stub
		if (name.equals(PlanManager.ACTION_PLAN_DETAIL)) {
			rollingPlan = (RollingPlan) response;
			if (rollingPlan==null) {
				Log.i(TAG, "plan is null~~");
				return;
			}
			updateInfo();
		}
		
	}
	

}
