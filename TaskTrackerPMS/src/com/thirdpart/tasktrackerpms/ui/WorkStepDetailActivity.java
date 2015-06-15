package com.thirdpart.tasktrackerpms.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jameschen.comm.utils.UtilsUI;
import com.jameschen.framework.base.BaseDetailActivity;
import com.jameschen.framework.base.BaseEditActivity;
import com.jameschen.framework.base.BaseDetailActivity.CreateItemViewListener;
import com.jameschen.framework.base.CommonCallBack.OnRetryLisnter;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.ManagerService;
import com.thirdpart.model.ManagerService.OnReqHttpCallbackListener;
import com.thirdpart.model.PMSManagerAPI;
import com.thirdpart.model.PlanManager;
import com.thirdpart.model.TaskManager;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.WitnessManager;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.Team;
import com.thirdpart.model.entity.WitnessDistributed;
import com.thirdpart.model.entity.Witnesser;
import com.thirdpart.model.entity.WitnesserList;
import com.thirdpart.model.entity.WorkStep;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.ChooseItemView;
import com.thirdpart.widget.DisplayItemView;
import com.thirdpart.widget.EditItemView;
import com.thirdpart.widget.UserInputItemView;

public class WorkStepDetailActivity extends BaseEditActivity {

	private TaskManager taskManager;
	WorkStep workStep;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		workStep = (WorkStep) getIntent().getSerializableExtra("workstep");
		taskManager = (TaskManager) ManagerService.getNewManagerService(this,
				TaskManager.class, this);
		setTitle("" + workStep.getStepname());
		updateInfo();
		execFetechDetail(TaskManager.ACTION_WITNESS_CHOOSE_TEAM);
	}

	boolean showWitness() {
		if (workStep == null) {
			return false;
		}
		if (workStep.getWitnesserb() != null
				|| workStep.getWitnesserc() != null
				|| workStep.getWitnesserd() != null
				|| workStep.getNoticeaqa() != null
				|| workStep.getNoticeaqc1() != null
				|| workStep.getNoticeaqc2() != null) {
			return true;
		}
		return false;
	}

	String getWitnesserId(WidgetItemInfo widgetItemInfo) {

		Witnesser witnesser = getWitnessByType(widgetItemInfo);
		if (witnesser != null) {
			return witnesser.getId();
		}
		return null;
	}

	private void execFetechDetail(String action) {

		if (action.equals(TaskManager.ACTION_TASK_COMMIT)) {

			String witnessdes = null;

			String operater = operaterWidgetItemInfo.content;
			if (TextUtils.isEmpty(operater)) {
				showToast("填写操作者");
				return;
			}

			String operatedate = PMSManagerAPI.getdateTimeformat(System
					.currentTimeMillis());

			String witnesseaddress = addressWidgetItemInfo.content;
			if (TextUtils.isEmpty(witnesseaddress)) {
				showToast("填写见证地点");
				return;
			}

			String operatedesc = null;

			String witnessdate = (String) witnessdateWidgetItemInfo.obj;
			if (TextUtils.isEmpty(witnessdate)) {
				showToast("填写见证时间");
				return;
			}

			 Team witness=(Team) witnessWidgetItemInfo.obj;
			 if (witness == null) {
				 showToast("选择见证负责人");
				return;
			}
			 super.callCommitBtn(null);
			taskManager.commit(workStep.getId(), witness.getId(), witnessdes, witnesseaddress, witnessdate, operater, operatedate, operatedesc);
		} else {
			showLoadingView(true);
			taskManager.chooseWitnessHeadList();
		}

	}

	final List<WidgetItemInfo> itemInfos = new ArrayList<WidgetItemInfo>();

	// R.id. in array String

	private void updateInfo() {
		final boolean isDone = "DONE".equals(workStep.getStepflag());
		if (itemInfos.isEmpty()) {
			// R.id. in array String
			itemInfos.add(operaterWidgetItemInfo = new WidgetItemInfo("0",
					"操作者：", workStep.operater, WidgetItemInfo.EDIT, isDone));

			itemInfos.add(operatedescWidgetItemInfo = new WidgetItemInfo("25",
					"描述：", workStep.operatedesc, WidgetItemInfo.INPUT, isDone));
			
			if (showWitness()) {
				itemInfos.add(addressWidgetItemInfo = new WidgetItemInfo("1",
						"见证地点：", null, WidgetItemInfo.EDIT, false));
				itemInfos.add(witnessdateWidgetItemInfo = new WidgetItemInfo("2",
						"见证时间：", "选择见证时间", WidgetItemInfo.CHOOSE, true));
				itemInfos.add(witnessWidgetItemInfo = new WidgetItemInfo("21",
						"负责人：", "选择见证负责人", WidgetItemInfo.CHOOSE, true));

			}
			
			
			
			if (isDone) {
				
				   findViewById(R.id.commit_layout).setVisibility(View.GONE);
			}
			
			itemInfos.add(new WidgetItemInfo("", "", "",
					WidgetItemInfo.DEVIDER, false));

			if (!isEmpty(workStep.getNoticeaqc1())) {
				itemInfos.add(new WidgetItemInfo("3", "A-QC1：", workStep
						.getNoticeaqc1(), WidgetItemInfo.DISPLAY, false));

			}

			if (!isEmpty(workStep.getNoticeaqc2())) {
				itemInfos.add(new WidgetItemInfo("4", "A-QC2：", workStep
						.getNoticeaqc2(), WidgetItemInfo.DISPLAY, false));

			}

			if (!isEmpty(workStep.getNoticeaqa())) {
				itemInfos.add(new WidgetItemInfo("5", "A-QA：", workStep
						.getNoticeaqc1(), WidgetItemInfo.DISPLAY, false));

			}

			if (!isEmpty(workStep.noticeb)) {
				itemInfos.add(new WidgetItemInfo("6", "通知点B：", workStep
						.getWitnesserc(), WidgetItemInfo.DISPLAY, false));

			}

			if (!isEmpty(workStep.noticec)) {
				itemInfos.add(new WidgetItemInfo("7", "通知点C：", workStep
						.getWitnesserc(), WidgetItemInfo.DISPLAY, false));

			}
			if (!isEmpty(workStep.noticed)) {
				itemInfos.add(new WidgetItemInfo("8", "通知点D：", workStep
						.getWitnesserd(), WidgetItemInfo.DISPLAY, false));

			}
			
			if (isDone) {
				
			}
		}

		createItemListToUI(itemInfos, R.id.edit_container,
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
							switch (widgetItemInfo.type) {
							case WidgetItemInfo.DISPLAY: {
								convertView = new DisplayItemView(
										WorkStepDetailActivity.this);

							}
								break;
							case WidgetItemInfo.DEVIDER: {
								convertView = new View(
										WorkStepDetailActivity.this);
								LayoutParams params = new LayoutParams(
										LayoutParams.WRAP_CONTENT,
										LayoutParams.WRAP_CONTENT);
								params.height = UtilsUI.getPixByDPI(
										getApplicationContext(), 8);
								convertView.setLayoutParams(params);

							}
								break;
							case WidgetItemInfo.EDIT: {
								convertView = new EditItemView(
										WorkStepDetailActivity.this);
								EditItemView editItemView = (EditItemView) convertView;
								editItemView.setNameAndContent(
										widgetItemInfo.name,
										widgetItemInfo.content);
								final View tagView = (EditItemView) convertView;
								editItemView.setScan(isDone);
								editItemView
										.addTextChangedListener(new TextWatcher() {

											@Override
											public void onTextChanged(
													CharSequence s, int start,
													int before, int count) {
												// TODO Auto-generated method
												// stub

											}

											@Override
											public void beforeTextChanged(
													CharSequence s, int start,
													int count, int after) {
												// TODO Auto-generated method
												// stub

											}

											@Override
											public void afterTextChanged(
													Editable s) {
												// TODO Auto-generated method
												// stub
												WidgetItemInfo widgetItemInfo = (WidgetItemInfo) tagView
														.getTag();
												widgetItemInfo.content = s
														.toString();
											}
										});

							}
								break;
								
							case WidgetItemInfo.INPUT: {
								convertView = new UserInputItemView(
										WorkStepDetailActivity.this);
								UserInputItemView editItemView = (UserInputItemView) convertView;
								editItemView.setNameAndContent(
										widgetItemInfo.name,
										widgetItemInfo.content);
								final View tagView = (UserInputItemView) convertView;
								editItemView.setScan(isDone);
								editItemView
										.addTextChangedListener(new TextWatcher() {

											@Override
											public void onTextChanged(
													CharSequence s, int start,
													int before, int count) {
												// TODO Auto-generated method
												// stub

											}

											@Override
											public void beforeTextChanged(
													CharSequence s, int start,
													int count, int after) {
												// TODO Auto-generated method
												// stub

											}

											@Override
											public void afterTextChanged(
													Editable s) {
												// TODO Auto-generated method
												// stub
												WidgetItemInfo widgetItemInfo = (WidgetItemInfo) tagView
														.getTag();
												widgetItemInfo.content = s
														.toString();
											}
										});

							}
								break;
								
							case WidgetItemInfo.CHOOSE: {

								convertView = new ChooseItemView(
										WorkStepDetailActivity.this);
								final ChooseItemView chooseItemView = (ChooseItemView) convertView;
								if (widgetItemInfo.bindClick) {
									convertView.findViewById(
											R.id.common_choose_item_content)
											.setOnClickListener(new OnClickListener() {
												
												@Override
												public void onClick(View v) {
													// TODO Auto-generated method stub

													if (widgetItemInfo.tag
															.equals("2")) {// time
														go2ChooseTime(widgetItemInfo);
													}
														else if(widgetItemInfo.tag.equals("21")){//
														showWindow(chooseItemView,witnessTeamList);
														
													} else if(widgetItemInfo.tag
															.equals("20")){//
														go2ChooseTime(widgetItemInfo);
													}
												

											
												}
											});
												

								}
								
								
							}
								break;

							default:
								break;
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
						case WidgetItemInfo.CHOOSE:
							ChooseItemView chooseItemView = (ChooseItemView) convertView;
							chooseItemView
									.setNameAndContent(widgetItemInfo.name,
											widgetItemInfo.content);

							break;

						default:
							break;
						}

						// update window

						initOptionItem(widgetItemInfo, convertView
								.findViewById(R.id.common_choose_item_content));

						// bind tag
						convertView.setTag(widgetItemInfo);
						return convertView;
					}
				}, true);
	}

	protected void initOptionItem(WidgetItemInfo widgetItemInfo, View btnView) {
		// TODO Auto-generated method stub
		if (widgetItemInfo.type != WidgetItemInfo.CHOOSE) {
			return;
		}
		if (witnessTeamList == null || witnessTeamList.size() == 0) {
			Log.i(TAG, "witnessTeam List is null...");
			return;
		}

	
	}

	public CharSequence getAddress() {
		EditItemView editItemView = (EditItemView) getViewByWidget(addressWidgetItemInfo);
		return editItemView.getContent();
	}

	private void showWindow(final ChooseItemView chooseItemView, List<Team> obj) {
		List<String> names = new ArrayList<String>();
		if (obj == null) {
			Log.i(TAG, "item windows is null--" + chooseItemView.getTag());
			return;
		}
		for (Team team : obj) {
			names.add(team.getName());
		}

		chooseItemView.showMenuItem(obj, names,
				new ChooseItemView.onDismissListener<Team>() {

					@Override
					public void onDismiss(Team item) {
						Log.i(TAG, "name==" + item.getName());
						// TODO Auto-generated method stub
						updateItem((WidgetItemInfo) chooseItemView.getTag(),
								item);
					}

				});
	}

	private Witnesser getWitnessByType(WidgetItemInfo widgetItemInfo) {
		// TODO Auto-generated method stub
		if (widgetItemInfo == null) {
			return null;
		}
		View view = getViewByWidget(widgetItemInfo);
		if (view != null) {
			List<Witnesser> witnessers = (List<Witnesser>) widgetItemInfo.obj;
			if (witnessers != null) {
				for (Witnesser witnesser : witnessers) {
					if (witnesser.getRealname().equals(widgetItemInfo.content)) {
						return witnesser;
					}
				}
			}
		}
		return null;
	}

	protected void updateItem(WidgetItemInfo widgetItemInfo, Team item) {
		// TODO Auto-generated method stub
		widgetItemInfo.content = item.getName();
		widgetItemInfo.obj = item;
		updateInfo();

	}

	private boolean isEmpty(String noticeaqc1) {
		// TODO Auto-generated method stub
		return TextUtils.isEmpty(noticeaqc1);
	}

	@Override
	protected void initView() {
		setContentView(R.layout.edit_ui);// TODO Auto-generated method stub
		super.initView();

	}

	void go2ChooseTime(WidgetItemInfo widgetItemInfo) {
		Intent intent = new Intent(this, TimeActivity.class);
		startActivityForResult(intent, TimeActivity.REQUEST_PICK_DATE);

	}

	@Override
	public void start(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void failed(final String name, int statusCode, Header[] headers,
			String response) {
		super.failed(name, statusCode, headers, response);
		if (name.equals(TaskManager.ACTION_TASK_COMMIT)) {

		} else {
			showRetryView(new OnRetryLisnter() {

				@Override
				public void doRetry() {
					// TODO Auto-generated method stub
					execFetechDetail(name);
				}
			});
		}

	}

	private List<Team> witnessTeamList;

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, intent);
		switch (arg0) {
		case TimeActivity.REQUEST_PICK_DATE: {
			if (arg1 == RESULT_OK) {

				updateTime(intent.getStringExtra("time"),intent.getStringExtra("format"));
			}
		}
			break;

		default:
			break;
		}

	}

	// id 工序步骤ID
	// witness 见证组组长ID
	// witnessdes N 见证描述
	// witnessaddress 见证地点
	// witnessdate 见证时间
	// operater 完成者
	// operatedate 完成时间（格式2015-05-24 22:22:45）
	// operatedesc N 完成信息描述
	WidgetItemInfo addressWidgetItemInfo, witnessWidgetItemInfo,
			witnessdesWidgetItemInfo, witnessdateWidgetItemInfo,
			operaterWidgetItemInfo, operatedescWidgetItemInfo;

	private void updateTime(String date, String formart) {
		// TODO Auto-generated method stub
		witnessdateWidgetItemInfo.content = date;
		witnessdateWidgetItemInfo.obj = formart;
		updateInfo();
	}

	@Override
	public void succ(String name, int statusCode, Header[] headers,
			Object response) {
		setLoadSucc();
		if (name.equals(TaskManager.ACTION_TASK_COMMIT)) {
			showToast("修改成功");
		} else {// get witless..
			witnessTeamList = (List<Team>) response;
			Log.i(TAG, "update....");
			updateInfo();
		}

	}

	@Override
	public void callCommitBtn(View v) {

		execFetechDetail(TaskManager.ACTION_TASK_COMMIT);

	}

}
