package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.thirdpart.model.TaskManager;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.WorkStep;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.DisplayItemView;

public class WorkStepDetailActivity extends BaseEditActivity implements OnReqHttpCallbackListener{
	
 
 private TaskManager taskManager ;
 WorkStep workStep;
 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
      workStep = (WorkStep) getIntent().getSerializableExtra(Item.TASK);	
      taskManager = (TaskManager) ManagerService.getNewManagerService(this, TaskManager.class,this);
      setTitle("工序完工信息：");
      updateInfo();
      execFetechDetail();
 }
 
	private void execFetechDetail() {
	// TODO Auto-generated method stub
}

	
	private void updateInfo() {

		final  List<WidgetItemInfo> itemInfos = new ArrayList<WidgetItemInfo>();
		 //R.id.  in array String
//		 itemInfos.add(new WidgetItemInfo("0", "操作者：", workStep.getWeldno(), WidgetItemInfo.CHOOSE, false));		
//		 itemInfos.add(new WidgetItemInfo("1", "完成日期：", rollingPlan.getId(), WidgetItemInfo.CHOOSE, false));
//		 itemInfos.add(new WidgetItemInfo("2", "见证地点：", rollingPlan.getAreano(), WidgetItemInfo.DISPLAY, false));
//		 itemInfos.add(new WidgetItemInfo("3", "见证时间：", rollingPlan.getDrawno(), WidgetItemInfo.CHOOSE, false));
//		 itemInfos.add(new WidgetItemInfo("4", "见证负责人：", rollingPlan.getWeldlistno(), WidgetItemInfo.CHOOSE, false));
//		 
//		 
//		 itemInfos.add(new WidgetItemInfo("8", "", "", WidgetItemInfo.DEVIDER, false));
//
//		 
//		 itemInfos.add(new WidgetItemInfo("5", "A-QC1：", rollingPlan.getRccm(), WidgetItemInfo.DISPLAY, false));
//		 itemInfos.add(new WidgetItemInfo("6", "A-QC2：", rollingPlan.getQualityplanno(), WidgetItemInfo.DISPLAY, false));
//		 itemInfos.add(new WidgetItemInfo("7", "A-QA：", rollingPlan.getPlandate(), WidgetItemInfo.DISPLAY, false));
//		 itemInfos.add(new WidgetItemInfo("8", "B：", "", WidgetItemInfo.DEVIDER, false));
//		 itemInfos.add(new WidgetItemInfo("9", "C：", "", WidgetItemInfo.DISPLAY, true));
		 
		 
		 
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
						convertView = new DisplayItemView(WorkStepDetailActivity.this);
					
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
		Intent intent = new Intent(this, WorkStepListActivity.class);
		
		//intent.putExtra(ConstValues.ID, Long.parseLong(rollingPlan.getId()));
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
		//	rollingPlan = (RollingPlan) response;
			updateInfo();
		}
		
	}
	

}