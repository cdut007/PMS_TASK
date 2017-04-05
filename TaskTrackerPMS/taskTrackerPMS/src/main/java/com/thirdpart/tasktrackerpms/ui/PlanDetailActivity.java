package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.thirdpart.model.PMSManagerAPI;
import com.thirdpart.model.PlanManager;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.WorkStep;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.adapter.WorkStepAdapter;
import com.thirdpart.widget.DisplayItemView;
import com.thirdpart.widget.EnterItemView;

public class PlanDetailActivity extends BaseDetailActivity {

	private PlanManager planManager;
	RollingPlan rollingPlan;
	boolean scan = false;
	private boolean isTaskConfirm;

	
	
	
	private void witnessDealBatTask() {
		Context context = this;
		Intent intent = new Intent(context, WitnessDealTaskActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("dealtask", rollingPlan);
		context.startActivity(intent);
		
	}
	
	private void issueFeedBack() {
		Context context = this;
		Intent intent = new Intent(context, IssueFeedbackActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("feedback", rollingPlan);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		rollingPlan = (RollingPlan) getIntent().getSerializableExtra(Item.PLAN);
		scan = getIntent().getBooleanExtra("scan", true);
		if (rollingPlan == null) {
			rollingPlan = (RollingPlan) getIntent().getSerializableExtra(
					Item.TASK);
			isTaskConfirm = true;
		}

		planManager = (PlanManager) ManagerService.getNewManagerService(this,
				PlanManager.class, this);

		updateInfo();
		execFetechDetail();
	}

	private void execFetechDetail() {

		if (rollingPlan.getId() == null) {
			Log.i(TAG, "plan id is null");
			return;
		}
		planManager.planDetail(rollingPlan.getId());
	}

	private void updateInfo() {

		final List<WidgetItemInfo> itemInfos = new ArrayList<WidgetItemInfo>();
		// R.id. in array String
		boolean isHankou = false;
		String type = rollingPlan.getSpeciality();
		if (type == null) {
			setTitle("明细");
		} else {
			if (PlanManager.isHankou(type)) {
				isHankou = true;
				setTitle("焊口明细");
			} else {
				setTitle("支架明细");
			}
		}

		itemInfos.add(new WidgetItemInfo("0", isHankou ? "焊口号：" : "支架号：",
				rollingPlan.getWeldno(), WidgetItemInfo.DISPLAY, false));
		itemInfos.add(new WidgetItemInfo("1", "机组号：", rollingPlan.getUnitno(),
				WidgetItemInfo.DISPLAY, false));
		itemInfos.add(new WidgetItemInfo("2", "区域号：", rollingPlan.getAreano(),
				WidgetItemInfo.DISPLAY, false));
		itemInfos.add(new WidgetItemInfo("3", "图纸号：", rollingPlan.getDrawno(),
				WidgetItemInfo.DISPLAY, false));
		if (isHankou) {
			itemInfos.add(new WidgetItemInfo("4", isHankou ? "焊接控制单号："
					: "支架控制单号：", rollingPlan.getWeldlistno(),
					WidgetItemInfo.DISPLAY, false));

		}

		itemInfos.add(new WidgetItemInfo("5", "RCCM：", rollingPlan.getRccm(),
				WidgetItemInfo.DISPLAY, false));
		itemInfos.add(new WidgetItemInfo("b1", "施工班组",
				rollingPlan.consteamName, WidgetItemInfo.DISPLAY, false));
		itemInfos.add(new WidgetItemInfo("b2", "施工组长",
				rollingPlan.consendmanName, WidgetItemInfo.DISPLAY, false));
		itemInfos.add(new WidgetItemInfo("b3", "材质类型", rollingPlan
				.getMaterialtype(), WidgetItemInfo.DISPLAY, false));
		itemInfos.add(new WidgetItemInfo("b4", "点值",
				rollingPlan.getWorkpoint(), WidgetItemInfo.DISPLAY, false));
		itemInfos.add(new WidgetItemInfo("b5", "工时", rollingPlan.getWorktime(),
				WidgetItemInfo.DISPLAY, false));
		itemInfos.add(new WidgetItemInfo("b6", "工程量", rollingPlan
				.getQualitynum(), WidgetItemInfo.DISPLAY, false));

		itemInfos.add(new WidgetItemInfo("6", "质量计划号：", rollingPlan
				.getQualityplanno(), WidgetItemInfo.DISPLAY, false));
		itemInfos.add(new WidgetItemInfo("7", "计划施工日期：", rollingPlan
				.getPlandate(), WidgetItemInfo.DISPLAY, false));
		itemInfos.add(new WidgetItemInfo("8", "", "", WidgetItemInfo.DEVIDER,
				false));
		if (isHankou) {
			itemInfos.add(itemInfos.size() - 1, new WidgetItemInfo("9",
					"查看工序详情", "", WidgetItemInfo.DISPLAY, true));

		} else {
			if (type != null) {
				itemInfos.add(itemInfos.size() - 1, new WidgetItemInfo("9a",
						"支架更新", "", WidgetItemInfo.DISPLAY, true));
			}

		}

		itemInfos.add(new WidgetItemInfo("-1", "问题详情", "",
				WidgetItemInfo.DISPLAY, true));

		if (isTaskConfirm & !scan) {
			itemInfos.add(new WidgetItemInfo("10", "问题反馈", "",
					WidgetItemInfo.DISPLAY, true));
			
			itemInfos.add(new WidgetItemInfo("e9", "批量见证", "",
					WidgetItemInfo.DISPLAY, true));
			
		} else {

		}
		
		
		
		
		
		if (!TextUtils.isEmpty(rollingPlan.welder)) {
			itemInfos.add(new WidgetItemInfo("e1", "焊工：", rollingPlan.welder,
					WidgetItemInfo.DISPLAY, false));
			
		}
		if (rollingPlan.welddate!=0) {
			itemInfos.add(new WidgetItemInfo("e2", "焊接完成日期：", PMSManagerAPI
					.getdateTimeformat(rollingPlan.welddate), WidgetItemInfo.DISPLAY,
					false));
		}
		if (!TextUtils.isEmpty(rollingPlan.qcman)) {
			itemInfos.add(new WidgetItemInfo("e3", "QC检查人员：", rollingPlan.qcman,
					WidgetItemInfo.DISPLAY, false));
		}
		if (!TextUtils.isEmpty(rollingPlan.getQcsign())) {
			itemInfos.add(new WidgetItemInfo("e4", "检查状态：", RollingPlan
					.QCFinifh(rollingPlan.getQcsign()), WidgetItemInfo.DISPLAY,
					false));
		}
		
		if (!TextUtils.isEmpty(rollingPlan.qcdate)) {
			itemInfos.add(new WidgetItemInfo("e5", "检查日期：", PMSManagerAPI
					.getdateTimeformat(Long.parseLong(rollingPlan.qcdate)),
					WidgetItemInfo.DISPLAY, false));
		}
		
		
		
		

		itemInfos.add(new WidgetItemInfo("11", "技术要求",
				rollingPlan.technologyAsk, WidgetItemInfo.ENTER, true));
		itemInfos.add(new WidgetItemInfo("12", "质量风险及控制措施",
				rollingPlan.qualityRiskCtl, WidgetItemInfo.ENTER, true));
		itemInfos.add(new WidgetItemInfo("13", "安全风险及控制措施",
				rollingPlan.securityRiskCtl, WidgetItemInfo.ENTER, true));
		itemInfos.add(new WidgetItemInfo("14", "经验反馈",
				rollingPlan.experienceFeedback, WidgetItemInfo.ENTER, true));
		itemInfos.add(new WidgetItemInfo("15", "施工工具", rollingPlan.workTool,
				WidgetItemInfo.ENTER, true));
		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.detail_container);
		viewGroup.removeAllViews();
		createItemListToUI(itemInfos, R.id.detail_container,
				new CreateItemViewListener() {

					@Override
					public View oncreateItem(int index, View convertView,
							ViewGroup viewgroup) {
						// TODO Auto-generated method stub
						// if exsit just update , otherwise create it.

						final WidgetItemInfo widgetItemInfo = itemInfos
								.get(index);
						if (convertView == null) {
							// create
							LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							switch (widgetItemInfo.type) {
							case WidgetItemInfo.DISPLAY: {
								convertView = new DisplayItemView(
										PlanDetailActivity.this);

							}
								break;
							case WidgetItemInfo.DEVIDER:
								convertView = inflater
										.inflate(R.layout.item_divider,
												viewgroup, false);

								break;
							case WidgetItemInfo.ENTER:
								convertView = new EnterItemView(
										PlanDetailActivity.this);

								break;
							default:
								break;
							}

							if (widgetItemInfo.bindClick) {
								convertView
										.setBackgroundResource(R.drawable.item_bg_state);
								convertView
										.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View v) {
												// stub
												if (widgetItemInfo.tag
														.equals("9")) {
													go2WorkStepDetail();
												} else if (widgetItemInfo.tag
														.equals("9a")) {
													go2ZhijiaUpdate();
												} else if (widgetItemInfo.tag
														.equals("10")) {
													issueFeedBack();
												} else if (widgetItemInfo.tag
														.equals("-1")) {
													issueDetail();
												} else if (widgetItemInfo.tag.equals("e9")) {
													witnessDealBatTask();
												}
												
												else {
													String tag = widgetItemInfo.tag;
													try {
														int index = Integer
																.parseInt(tag);
														if (index >= 11
																&& index <= 15) {
															go2ItemDetail(widgetItemInfo);
														}
													} catch (NumberFormatException e) {
														// TODO: handle
														// exception
														e.printStackTrace();
													}

												}
											}

										

										});
							}
						} else {

						}

						// update
						switch (widgetItemInfo.type) {
						case WidgetItemInfo.DISPLAY: {
							DisplayItemView displayItemView = (DisplayItemView) convertView;
							displayItemView
									.setNameAndContent(widgetItemInfo.name,
											widgetItemInfo.content);
						}
							break;

						case WidgetItemInfo.ENTER: {
							EnterItemView enterItemView = (EnterItemView) convertView;
							enterItemView
									.setNameAndContent(widgetItemInfo.name,
											widgetItemInfo.content);
						}
							break;
						case WidgetItemInfo.DEVIDER:

							break;

						default:
							break;
						}
						// bind tag
						convertView.setTag(widgetItemInfo);
						return convertView;
					}
				}, true);
	}

	private void issueDetail() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, IssuePlanActivity.class);
		intent.putExtra(Item.PLAN, rollingPlan);
		startActivity(intent);
	}

	private void go2ItemDetail(WidgetItemInfo widgetItemInfo) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, DetailContentActivity.class);
		intent.putExtra("title", widgetItemInfo.name);
		intent.putExtra("content", widgetItemInfo.content);
		startActivity(intent);
	}

	@Override
	protected void initView() {
		setContentView(R.layout.detail_ui);
		super.initView();

	}

	private void go2ZhijiaUpdate() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, ZhiJiaDetailActivity.class);

		intent.putExtra("plan", rollingPlan);
		startActivityForResult(intent, 0x21);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		switch (arg0) {
		case 0x21: {
			if (arg1 == RESULT_OK) {
				execFetechDetail();
			}
		}
			break;

		default:
			break;
		}
	}

	void go2WorkStepDetail() {
		Intent intent = new Intent(this, PlanWorkStepListActivity.class);

		if (isTaskConfirm&!scan) {
			intent.putExtra("scan", false);
		} else {
			intent.putExtra("scan", true);
		}
		intent.putExtra(Item.PLAN, rollingPlan);
		startActivity(intent);
	}

	@Override
	public void failed(String name, int statusCode, Header[] headers,
			String response) {
		super.failed(name, statusCode, headers, response);
	}

	@Override
	public void succ(String name, int statusCode, Header[] headers,
			Object response) {
		if (name.equals(PlanManager.ACTION_PLAN_DETAIL)) {
			rollingPlan = (RollingPlan) response;
			if (rollingPlan == null) {
				Log.i(TAG, "～plan is null~~");
				return;
			}
			updateInfo();
		}

	}

}
