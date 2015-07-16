package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.jameschen.comm.utils.UtilsUI;
import com.jameschen.framework.base.BaseEditActivity;
import com.jameschen.framework.base.CommonCallBack.OnRetryLisnter;
import com.jameschen.framework.base.UINetworkHandler;
import com.jameschen.widget.CustomSelectPopupWindow.Category;
import com.thirdpart.model.ManagerService;
import com.thirdpart.model.PMSManagerAPI;
import com.thirdpart.model.TaskManager;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.Team;
import com.thirdpart.model.entity.WitnessInfo;
import com.thirdpart.model.entity.Witnesser;
import com.thirdpart.model.entity.WorkStep;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.ChooseItemView;
import com.thirdpart.widget.DisplayItemView;
import com.thirdpart.widget.EditItemView;
import com.thirdpart.widget.UserInputItemView;

public class ZhiJiaDetailActivity extends BaseEditActivity {

	private TaskManager taskManager;
	RollingPlan rollingPlan;
	private int qcsign=-1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		rollingPlan = (RollingPlan) getIntent().getSerializableExtra("plan");
	taskManager = (TaskManager) ManagerService.getNewManagerService(this,
				TaskManager.class, this);
		if (isScan()) {

			setTitle("查看支架回填信息");
		} else {
			setTitle("回填支架信息");
		}
		updateInfo();
		execFetechDetail(TaskManager.ACTION_WITNESS_CHOOSE_TEAM);
	}

	private boolean isScan() {
		// TODO Auto-generated method stub
		return !TextUtils.isEmpty(rollingPlan.enddate);
	}


	boolean showQCMan(){
		
		
		if( !TextUtils.isEmpty(rollingPlan.qcman)){
			return true;
		}
		return false;
	}
	
	boolean showWitness() {
		
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


			String operater = operaterWidgetItemInfo.content;
			if (TextUtils.isEmpty(operater)) {
				showToast("填写完成者");
				return;
			}

			String operatedate = PMSManagerAPI.getdateTimeformat(System
					.currentTimeMillis());
			
			
			String witnessdate=null;
			if (operatedateWidgetItemInfo!=null) {
				 witnessdate = (String) operatedateWidgetItemInfo.obj;
				if (TextUtils.isEmpty(witnessdate)) {
					showToast("填写完成时间");
					return;
				}
			}
			 
			String qcman=null;

			
			if (qcsign == -1) {
				 showToast("选择QC检查完成");
					return;
			}
			
			if (qcmanWidgetItemInfo!=null) {
				qcman = (String) qcmanWidgetItemInfo.content;
				if (qcman == null) {
					 showToast("填写QC确认人");
						return;
				}
			}
			
			
		
			
			 super.callCommitBtn(null);
			taskManager.confirmMyPlanFinish(rollingPlan.getId(), operater, operatedate,qcman,qcsign+"");
		} else {
			showLoadingView(true);
			taskManager.chooseWitnessHeadList();
		}

	}

	final List<WidgetItemInfo> itemInfos = new ArrayList<WidgetItemInfo>();

	// R.id. in array String

	private void updateInfo() {
		final boolean isDone = isScan();
		if (itemInfos.isEmpty()) {
			// R.id. in array String
		if (isDone) {
			itemInfos.add(operaterWidgetItemInfo = new WidgetItemInfo("0",
					"完成者：", rollingPlan.welder, WidgetItemInfo.DISPLAY, false));

			itemInfos.add(operatedateWidgetItemInfo = new WidgetItemInfo("20",
					"完成时间：", PMSManagerAPI.getdateTimeformat(Long.parseLong(rollingPlan.enddate)), WidgetItemInfo.DISPLAY, false));
			
		} else {
			itemInfos.add(operaterWidgetItemInfo = new WidgetItemInfo("0",
					"完成者：", null, WidgetItemInfo.EDIT, true));

			itemInfos.add(operatedateWidgetItemInfo = new WidgetItemInfo("20",
					"完成时间：", "请选择完成时间", WidgetItemInfo.CHOOSE, true));
			
		}
			
			if (showQCMan()&&isDone) {
				
				itemInfos.add(qcSignWidgetItemInfo = new WidgetItemInfo("a",
						"QC检查完成：", RollingPlan.QCFinifh(rollingPlan.getQcsign()), WidgetItemInfo.DISPLAY, false));
				
				itemInfos.add(qcmanWidgetItemInfo = new WidgetItemInfo("b",
						"QC确认人：", rollingPlan.qcman,WidgetItemInfo.DISPLAY, false));
				
				if (!TextUtils.isEmpty(rollingPlan.qcdate)) {
					itemInfos.add(qcdateWidgetItemInfo = new WidgetItemInfo("c",
							"QC确认日期：", PMSManagerAPI.getdateTimeformat(Long.parseLong(rollingPlan.qcdate)), WidgetItemInfo.DISPLAY, false));
				
				}
				
				
			
			}
			
			if (isDone) {
				
				   findViewById(R.id.commit_layout).setVisibility(View.GONE);
			}else {

				itemInfos.add(qcSignWidgetItemInfo = new WidgetItemInfo("a",
						"QC检查完成：", "选择类别", WidgetItemInfo.CHOOSE, true));
				
				itemInfos.add(qcmanWidgetItemInfo = new WidgetItemInfo("b",
						"QC确认人：", null,WidgetItemInfo.EDIT, true));
									
			
			}
			
			itemInfos.add(new WidgetItemInfo("", "", "",
					WidgetItemInfo.DEVIDER, false));
		
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
										ZhiJiaDetailActivity.this);

							}
								break;
							case WidgetItemInfo.DEVIDER: {
								convertView = new View(
										ZhiJiaDetailActivity.this);
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
										ZhiJiaDetailActivity.this);
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
										ZhiJiaDetailActivity.this);
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
										ZhiJiaDetailActivity.this);
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
													}else if(widgetItemInfo.tag
															.equals("a")){//
														go2ChooseQCSign(chooseItemView,widgetItemInfo);
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

	String getName(Team team){
		String realName = "";
		if (team.users!=null&&team.users.size()>0) {
			realName = " - "+team.users.get(0).getRealname();
		}
		return team.getName()+realName;
	}
	
	private void go2ChooseQCSign(
			ChooseItemView chooseItemView, final WidgetItemInfo widgetItemInfo) {
		// TODO Auto-generated method stub

		List<String> names = new ArrayList<String>();
		List<Category> sCategories = new ArrayList<Category>();
		Category category = new Category("0");
		category.setName("未确认");
		sCategories.add(category);
		
		category = new Category("1");
		category.setName("确认");
		sCategories.add(category);
		
		category = new Category("2");
		category.setName("退回");
		sCategories.add(category);
		
		for (Category scategory : sCategories) {
			
			names.add(scategory.getName());
		}

		chooseItemView.showMenuItem(sCategories, names,
				new ChooseItemView.onDismissListener<Category>() {

					@Override
					public void onDismiss(Category item) {
						Log.i(TAG, "name==" + item.getName());
						// TODO Auto-generated method stub
						widgetItemInfo.content = item.getName();
						qcsign= Integer.parseInt(item.tag);
						updateInfo();
					}

				});
	
	}
	
	private void showWindow(final ChooseItemView chooseItemView, List<Team> obj) {
		List<String> names = new ArrayList<String>();
		if (obj == null) {
			Log.i(TAG, "item windows is null--" + chooseItemView.getTag());
			return;
		}
		for (Team team : obj) {
			
			names.add(getName(team));
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
		widgetItemInfo.content = getName(item);
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
	WidgetItemInfo addressWidgetItemInfo,
			witnessdesWidgetItemInfo, operatedateWidgetItemInfo,
			operaterWidgetItemInfo,
			qcSignWidgetItemInfo,qcmanWidgetItemInfo,qcdateWidgetItemInfo;

	private void updateTime(String date, String formart) {
		// TODO Auto-generated method stub
		operatedateWidgetItemInfo.content = date;
		operatedateWidgetItemInfo.obj = formart;
		updateInfo();
	}

	@Override
	public void succ(String name, int statusCode, Header[] headers,
			Object response) {
		setLoadSucc();
		if (name.equals(TaskManager.ACTION_ZHIJIA_COMMIT)) {
			if (response!=null) {
				Log.i(TAG, "JsonObject=="+response.toString());
			}else {
				Log.i(TAG, "JsonObject is null");
			}
			showToast("修改成功");
			setResult(RESULT_OK);
			finish();
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
