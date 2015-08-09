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
import com.thirdpart.widget.EnterItemView;
import com.thirdpart.widget.UserInputItemView;

public class WorkStepDetailActivity extends BaseEditActivity {

	private TaskManager taskManager;
	WorkStep workStep;
	boolean lastIndex;
	private int qcsign=-1;
	String witnessAdrress;
	boolean eidtWitness;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		eidtWitness = getIntent().getBooleanExtra("editWitness", false);
		
		workStep = (WorkStep) getIntent().getSerializableExtra("workstep");
		witnessAdrress = getIntent().getStringExtra("witnessAdress");
		lastIndex = getIntent().getBooleanExtra("lastIndex", false);
		Log.i(TAG, "isLastIndex="+lastIndex);
		taskManager = (TaskManager) ManagerService.getNewManagerService(this,
				TaskManager.class, this);
		setTitle("" + workStep.getStepname());
		updateInfo();
		fetchWorkStepDetail();
		execFetechDetail(TaskManager.ACTION_WITNESS_CHOOSE_TEAM);
	}

	private void fetchWorkStepDetail() {
		// TODO Auto-generated method stub
		getPMSManager().getWorkStepDetail(workStep.getId(), new UINetworkHandler<WorkStep>(this) {
			@Override
			public void callbackFailure(int statusCode, Header[] headers,
					String response) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void start() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void finish() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void callbackSuccess(int statusCode, Header[] headers,
					WorkStep response) {
				// TODO Auto-generated method stub
				workStep = response;
				final boolean isDone = "DONE".equals(workStep.getStepflag());
				if (isDone) {
					itemInfos.clear();
					updateInfo();
				}
				
			}
		});
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
	
	public static boolean showWitness(WorkStep workStep) {
		if (workStep == null) {
			return false;
		}
		if (workStep.getWitnesserb() != null
				|| workStep.getWitnesserc() != null
				|| workStep.getWitnesserd() != null
				|| !TextUtils.isEmpty(workStep.getNoticeaqa())
				|| !TextUtils.isEmpty(workStep.getNoticeaqc1())
				|| !TextUtils.isEmpty(workStep.getNoticeaqc2())) {
			
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
			if (!eidtWitness&&TextUtils.isEmpty(operater)) {
				showToast("填写操作者");
				return;
			}

			String operatedate = PMSManagerAPI.getdateTimeformat(System
					.currentTimeMillis());
			String witnesseaddress=null;
			if (eidtWitness&&addressWidgetItemInfo!=null) {
				 witnesseaddress = addressWidgetItemInfo.content;
				if (TextUtils.isEmpty(witnesseaddress)) {
					showToast("填写见证地点");
					return;
				}
			}
			

			String operatedesc = operatedescWidgetItemInfo.content;
			
			String witnessdate=null;
			if (eidtWitness&&witnessdateWidgetItemInfo!=null) {
				 witnessdate = (String) witnessdateWidgetItemInfo.obj;
				if (TextUtils.isEmpty(witnessdate)) {
					showToast("填写见证时间");
					return;
				}
			}
			 Team witness=null;
			if (eidtWitness&&witnessWidgetItemInfo!=null) {
				  witness=(Team) witnessWidgetItemInfo.obj;
				 if (witness == null) {
					 showToast("选择见证负责人");
					return;
				}
			}

			String qcman=null;
			if (!eidtWitness&&lastIndex) {
				
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
				
				
			}
			
			 super.callCommitBtn(null);
			taskManager.commit(workStep.getId(),witness!=null? witness.getId():null, witnessdes, witnesseaddress, witnessdate, operater, operatedate, operatedesc,qcsign,qcman);
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
		if (eidtWitness) {
			operaterWidgetItemInfo = new WidgetItemInfo("0",
					"操作者：", workStep.operater, WidgetItemInfo.EDIT, false);

			operatedescWidgetItemInfo = new WidgetItemInfo("25",
					"描述：", workStep.operatedesc==null?"合格":workStep.operatedesc, WidgetItemInfo.INPUT, false);

			
//			itemInfos.add(operaterWidgetItemInfo = new WidgetItemInfo("0",
//					"操作者：", workStep.operater, WidgetItemInfo.EDIT, false));
//
//			itemInfos.add(operatedescWidgetItemInfo = new WidgetItemInfo("25",
//					"描述：", workStep.operatedesc==null?"合格":workStep.operatedesc, WidgetItemInfo.INPUT, false));
//		
		}else {
			itemInfos.add(operaterWidgetItemInfo = new WidgetItemInfo("0",
					"操作者：", workStep.operater, WidgetItemInfo.EDIT, isDone));

			itemInfos.add(operatedescWidgetItemInfo = new WidgetItemInfo("25",
					"描述：", workStep.operatedesc==null?"合格":workStep.operatedesc, WidgetItemInfo.INPUT, isDone));
		
		}	
			if (showWitness(workStep)) {
				
				if (isDone&&!eidtWitness) {
					if (workStep.witnessInfo!=null&&workStep.witnessInfo.size()>0) {
						WitnessInfo sInfo =workStep.witnessInfo.get(0);
						itemInfos.add(addressWidgetItemInfo = new WidgetItemInfo("1",
								"见证地点：", sInfo.witnessaddress, WidgetItemInfo.DISPLAY, false));
						
						itemInfos.add(witnessdateWidgetItemInfo = new WidgetItemInfo("2",
								"见证时间：", PMSManagerAPI.getdateTimeformat(sInfo.witnessdate),WidgetItemInfo.DISPLAY, false));
						itemInfos.add(witnessWidgetItemInfo = new WidgetItemInfo("21",
								"负责人：", sInfo.witnessName, WidgetItemInfo.DISPLAY, false));
						if (workStep.witnessesAssign!=null&&workStep.witnessesAssign.size()>0) {
							for (int i = 0; i < workStep.witnessesAssign.size(); i++) {
								WitnessInfo witnessInfo =  workStep.witnessesAssign.get(i);
								String okType ="1".equals(witnessInfo.isok)?"不合格":"合格";
								if (witnessInfo.isok==null||witnessInfo.isok==0) {
									 okType =" 未见证";
								}else {
									okType = " 见证"+okType;
								}
								WidgetItemInfo sItemInfo = new WidgetItemInfo("w"+i,
										""+witnessInfo.witnesserName+okType, "", WidgetItemInfo.ENTER, true);
								sItemInfo.obj = witnessInfo;
								itemInfos.add(sItemInfo);
							
							}
						}
					}
				
					
				}else {
					if (eidtWitness) {
						if (witnessAdrress!=null) {
							itemInfos.add(addressWidgetItemInfo = new WidgetItemInfo("1",
									"见证地点：", witnessAdrress, WidgetItemInfo.EDIT, true));
							
						}else {
							itemInfos.add(addressWidgetItemInfo = new WidgetItemInfo("1",
									"见证地点：", null, WidgetItemInfo.EDIT, true));
							
						}
					}else {
						if (!"PREPARE".equals(workStep.getStepflag())) {
							itemInfos.add(witnessdateWidgetItemInfo = new WidgetItemInfo("2",
									"见证时间：", "选择见证时间",WidgetItemInfo.CHOOSE, true));
							itemInfos.add(witnessWidgetItemInfo = new WidgetItemInfo("21",
									"负责人：", "选择见证负责人", WidgetItemInfo.CHOOSE, true));
						
						}
						
					}
					
				}

			}
			
			if (showQCMan()&&isDone&&!eidtWitness) {
				
				
				if (lastIndex) {
					RollingPlan rollingPlan = workStep.getRollingPlan();
					
					itemInfos.add(qcSignWidgetItemInfo = new WidgetItemInfo("a",
							"QC检查完成：", RollingPlan.QCFinifh(rollingPlan.getQcsign()), WidgetItemInfo.DISPLAY, false));
					
					itemInfos.add(qcmanWidgetItemInfo = new WidgetItemInfo("b",
							"QC确认人：", rollingPlan.qcman,WidgetItemInfo.DISPLAY, false));
					
					if (!TextUtils.isEmpty(rollingPlan.qcdate)) {
						itemInfos.add(qcdateWidgetItemInfo = new WidgetItemInfo("c",
								"QC确认日期：", PMSManagerAPI.getdateTimeformat(Long.parseLong(rollingPlan.qcdate)), WidgetItemInfo.DISPLAY, false));
					
					}
					
					
				}else {
					

				}
			}
			
			if (isDone&&!eidtWitness) {
				
				   findViewById(R.id.commit_layout).setVisibility(View.GONE);
			}else {
				if (lastIndex&&!eidtWitness) {
					itemInfos.add(qcSignWidgetItemInfo = new WidgetItemInfo("a",
							"QC检查完成：", "选择类别", WidgetItemInfo.CHOOSE, true));
					
					itemInfos.add(qcmanWidgetItemInfo = new WidgetItemInfo("b",
							"QC确认人：", null,WidgetItemInfo.EDIT, true));
										
				}
			}
			
			itemInfos.add(new WidgetItemInfo("", "", "",
					WidgetItemInfo.DEVIDER, false));

//			if (!isEmpty(workStep.getNoticeaqc1())) {
//				itemInfos.add(new WidgetItemInfo("s3", "A-QC1：", workStep
//						.getNoticeaqc1(), WidgetItemInfo.DISPLAY, false));
//
//			}
//
//			if (!isEmpty(workStep.getNoticeaqc2())) {
//				itemInfos.add(new WidgetItemInfo("s4", "A-QC2：", workStep
//						.getNoticeaqc2(), WidgetItemInfo.DISPLAY, false));
//
//			}
//
//			if (!isEmpty(workStep.getNoticeaqa())) {
//				itemInfos.add(new WidgetItemInfo("s5", "A-QA：", workStep
//						.getNoticeaqa(), WidgetItemInfo.DISPLAY, false));
//
//			}
//
//			if (!isEmpty(workStep.noticeb)) {
//				itemInfos.add(new WidgetItemInfo("s6", "通知点B：", workStep
//						.noticeb, WidgetItemInfo.DISPLAY, false));
//
//			}
//
//			if (!isEmpty(workStep.noticec)) {
//				itemInfos.add(new WidgetItemInfo("s7", "通知点C：", workStep
//						.noticec, WidgetItemInfo.DISPLAY, false));
//
//			}
//			if (!isEmpty(workStep.noticed)) {
//				itemInfos.add(new WidgetItemInfo("s8", "通知点D：", workStep
//						.noticed, WidgetItemInfo.DISPLAY, false));
//
//			}
//			
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
								case WidgetItemInfo.ENTER: {
									convertView = new EnterItemView(
											WorkStepDetailActivity.this);
									convertView.setOnClickListener(new OnClickListener() {
										
										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											WitnessInfo witnessInfo = (WitnessInfo) widgetItemInfo.obj;
											Intent intent = new Intent(WorkStepDetailActivity.this,DetailContentActivity.class);
											intent.putExtra("title", "见证人描述－－"+witnessInfo.witnesserName);
											intent.putExtra("content",witnessInfo.noticeresultdesc);
											startActivity(intent);
										}
									});
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
	WidgetItemInfo addressWidgetItemInfo, witnessWidgetItemInfo,
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
		if (name.equals(TaskManager.ACTION_TASK_COMMIT)) {
			if (response!=null) {
				Log.i(TAG, "JsonObject=="+response.toString());
			}else {
				Log.i(TAG, "JsonObject is null");
			}
			showToast("修改成功");
			WorkStepFragment.CallSucc(WorkStepFragment.callsucc);
			
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
