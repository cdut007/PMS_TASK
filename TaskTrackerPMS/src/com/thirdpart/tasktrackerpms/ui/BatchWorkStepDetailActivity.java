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
import com.thirdpart.model.PlanManager;
import com.thirdpart.model.TaskManager;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.Team;
import com.thirdpart.model.entity.UserInfo;
import com.thirdpart.model.entity.WitnessInfo;
import com.thirdpart.model.entity.Witnesser;
import com.thirdpart.model.entity.WorkStep;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.ChooseItemView;
import com.thirdpart.widget.DisplayItemView;
import com.thirdpart.widget.EditItemView;
import com.thirdpart.widget.EnterItemView;
import com.thirdpart.widget.UserInputItemView;

public class BatchWorkStepDetailActivity extends BaseEditActivity {

	private PlanManager planManager;
	WorkStep workStep = new WorkStep();
	
	private int qcsign=-1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		planManager = (PlanManager) ManagerService.getNewManagerService(this,
				PlanManager.class, this);
		setTitle(TaskFragment.title);
		updateInfo();
	}

	boolean showQCMan(){
		if (workStep == null) {
			return false;
		}
		RollingPlan rollingPlan = workStep.getRollingPlan();
		if (rollingPlan == null) {
			return false;
		}
		if( !TextUtils.isEmpty(rollingPlan.qcman)){
			return true;
		}
		return false;
	}
	


	private void execFetechDetail(String action) {

		if (action.equals(PlanManager.ACTION_PLAN_WORKSTEP_BATCH_COMMIT)) {


			String operater = operaterWidgetItemInfo.content;
			if (TextUtils.isEmpty(operater)) {
				showToast("填写操作者");
				return;
			}

			
			if (qcsign == -1) {
				 showToast("选择QC检查完成");
					return;
			}
			
			String qcman=null;
			if (qcmanWidgetItemInfo!=null) {
				qcman = (String) qcmanWidgetItemInfo.content;
				if (qcman == null) {
					 showToast("填写QC确认人");
						return;
				}
			}
			
			
			String operatedate = PMSManagerAPI.getdateTimeformat(System
					.currentTimeMillis());
			
			

			String operatedesc = operatedescWidgetItemInfo.content;
			
			
			 super.callCommitBtn(null);
			 List<String> eList = new ArrayList<>();
			planManager.commitBatch(operater,operatedesc, operatedate,qcsign,qcman, eList);
		}
	}

	final List<WidgetItemInfo> itemInfos = new ArrayList<WidgetItemInfo>();
	protected ChooseItemView chooseQCView;

	// R.id. in array String

	private void updateInfo() {
		final boolean isDone = "DONE".equals(workStep.getStepflag());
		if (itemInfos.isEmpty()) {
			// R.id. in array String
		
			String defaultOpName = workStep.operater;
			if (TextUtils.isEmpty(defaultOpName) && workStep.getRollingPlan()!=null) {
				defaultOpName = workStep.getRollingPlan().consendmanName;
			}
			
			operaterWidgetItemInfo = new WidgetItemInfo("0",
					"操作者：", defaultOpName, WidgetItemInfo.EDIT, false);

			operatedescWidgetItemInfo = new WidgetItemInfo("25",
					"描述：", workStep.operatedesc==null?"见证合格":workStep.operatedesc, WidgetItemInfo.INPUT, false);

			
			itemInfos.add(operaterWidgetItemInfo);
			itemInfos.add(operatedescWidgetItemInfo);
			itemInfos.add(new WidgetItemInfo("", "", "",
					WidgetItemInfo.DEVIDER, false));

			
			itemInfos.add(qcSignWidgetItemInfo = new WidgetItemInfo("a",
					"QC检查完成：", "选择类别", WidgetItemInfo.CHOOSE, true));
			
			itemInfos.add(qcmanWidgetItemInfo = new WidgetItemInfo("b",
					"QC确认人：", null,WidgetItemInfo.EDIT, true));
								
			
			
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
										BatchWorkStepDetailActivity.this);

							}
								break;
								case WidgetItemInfo.ENTER: {
									convertView = new EnterItemView(
											BatchWorkStepDetailActivity.this);
									convertView.setOnClickListener(new OnClickListener() {
										
										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											WitnessInfo witnessInfo = (WitnessInfo) widgetItemInfo.obj;
											Intent intent = new Intent(BatchWorkStepDetailActivity.this,DetailContentActivity.class);
											intent.putExtra("title", "见证人描述－－"+witnessInfo.witnesserName);
											intent.putExtra("content",witnessInfo.noticeresultdesc);
											startActivity(intent);
										}
									});
								}
									break;
							case WidgetItemInfo.DEVIDER: {
								convertView = new View(
										BatchWorkStepDetailActivity.this);
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
										BatchWorkStepDetailActivity.this);
								EditItemView editItemView = (EditItemView) convertView;
								editItemView.setNameAndContent(
										widgetItemInfo.name,
										widgetItemInfo.content);
								final View tagView = (EditItemView) convertView;
								editItemView.setScan(false);
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
												if (TextUtils.isEmpty(s)|| widgetItemInfo == null) {
													return;
												}
												widgetItemInfo.content = s
														.toString();
											}
										});

							}
								break;
								
							case WidgetItemInfo.INPUT: {
								convertView = new UserInputItemView(
										BatchWorkStepDetailActivity.this);
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
										BatchWorkStepDetailActivity.this);
								final ChooseItemView chooseItemView = (ChooseItemView) convertView;
								if ("a".equals(widgetItemInfo.tag)) {
									chooseQCView = chooseItemView;
								}
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
														showWindow("W",chooseItemView,witnessTeamList);
														
													} else if(widgetItemInfo.tag.equals("22")){//
														showWindow("H",chooseItemView,witnessTeamList);
														
													}else if(widgetItemInfo.tag.equals("23")){//
														showWindow("R",chooseItemView,witnessTeamList);
														
													}else if(widgetItemInfo.tag
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
							
						case WidgetItemInfo.EDIT: {
							EditItemView editItemView = (EditItemView) convertView;
							editItemView
									.setNameAndContent(widgetItemInfo.name,
											widgetItemInfo.content);
						}
							break;
							
							
							case WidgetItemInfo.ENTER: {
								EnterItemView displayItemView = (EnterItemView) convertView;
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

	
	String getName(UserInfo userInfo){
		String realName = userInfo.getRealname();
		
		return realName;
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
						Log.i(TAG, "name==" + item.getName()+"; item.tag="+item.tag);
						// TODO Auto-generated method stub
						widgetItemInfo.content = item.getName();
						qcsign= Integer.parseInt(item.tag);
						if (chooseQCView!=null) {
							chooseQCView.setContent(item.getName());
						}
					}

				});
	
	}
	
	private void showWindow(String type,final ChooseItemView chooseItemView, List<Team> obj) {
		List<String> names = new ArrayList<String>();
		if (obj == null) {
			Log.i(TAG, "item windows is null--" + chooseItemView.getTag());
			return;
		}
		List<UserInfo> mInfos = new ArrayList<UserInfo>();
		for (Team team : obj) {
			if (type.equals(team.type)) {
				mInfos = team.users;
				if (mInfos!=null && mInfos.size()>0) {
					for (UserInfo userInfo : mInfos) {
						names.add(userInfo.getRealname());
					}
				}
				
				
			}
			
		}
		chooseItemView.showMenuItem(mInfos, names,
				new ChooseItemView.onDismissListener<UserInfo>() {

					@Override
					public void onDismiss(UserInfo item) {
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

	protected void updateItem(WidgetItemInfo widgetItemInfo, UserInfo item) {
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
		if (name.equals(PlanManager.ACTION_PLAN_WORKSTEP_BATCH_COMMIT)) {

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
	WidgetItemInfo addressWidgetItemInfo,  witnessW_WidgetItemInfo,
	witnessH_WidgetItemInfo,witnessR_WidgetItemInfo,
			witnessdesWidgetItemInfo, witnessdateWidgetItemInfo,
			operaterWidgetItemInfo, operatedescWidgetItemInfo,
			qcSignWidgetItemInfo,qcmanWidgetItemInfo,qcdateWidgetItemInfo;

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
		if (name.equals(PlanManager.ACTION_PLAN_WORKSTEP_BATCH_COMMIT)) {
			if (response!=null) {
				Log.i(TAG, "JsonObject=="+response.toString());
			}else {
				Log.i(TAG, "JsonObject is null");
			}
			showToast("修改成功");
			WorkStepFragment.CallSucc(WorkStepFragment.callsucc);
			TaskFragment.CallSucc(TaskFragment.callsucc);
		} else {// get witness..
			witnessTeamList = (List<Team>) response;
			Log.i(TAG, "update....");
			updateInfo();
		}

	}

	@Override
	public void callCommitBtn(View v) {

		execFetechDetail(PlanManager.ACTION_PLAN_WORKSTEP_BATCH_COMMIT);

	}

}
