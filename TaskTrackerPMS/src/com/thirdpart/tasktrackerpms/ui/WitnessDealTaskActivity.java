package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.jameschen.comm.utils.Log;
import com.jameschen.comm.utils.UtilsUI;
import com.jameschen.framework.base.BaseEditActivity;
import com.jameschen.framework.base.CommonCallBack.OnRetryLisnter;
import com.jameschen.framework.base.UINetworkHandler;
import com.thirdpart.model.ManagerService;
import com.thirdpart.model.PMSManagerAPI;
import com.thirdpart.model.PlanManager;
import com.thirdpart.model.TaskManager;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.Team;
import com.thirdpart.model.entity.WitnessInfo;
import com.thirdpart.model.entity.Witnesser;
import com.thirdpart.model.entity.WorkStep;
import com.thirdpart.model.entity.WorkStepList;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.ChooseBelowItemView;
import com.thirdpart.widget.ChooseItemView;
import com.thirdpart.widget.DisplayItemView;
import com.thirdpart.widget.EditItemView;
import com.thirdpart.widget.EnterItemView;
import com.thirdpart.widget.UserInputItemView;

public class WitnessDealTaskActivity extends BaseEditActivity {

	RollingPlan rollingPlan;

	
	String getType(){
		String type = rollingPlan.getSpeciality();
		if (type == null) {
			return "焊口";
		} else {
			if (PlanManager.isHankou(type)) {
				return "焊口";
			} else {
				return "支架";
			}
		}
	}
	

	private TaskManager taskManager;
	WorkStep workStep;
	WorkStepList workStepList ;
	boolean lastIndex;
	String witnessAdrress;
	boolean eidtWitness;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		rollingPlan = (RollingPlan) getIntent().getSerializableExtra("dealtask");

		setTitle(rollingPlan.getWeldno()+getType()+"见证信息");
		
		workStep = (WorkStep) getIntent().getSerializableExtra("workstep");
		
		if (workStep == null) {
			workStep = new WorkStep();
		}
		
		witnessAdrress = getIntent().getStringExtra("witnessAdress");
		lastIndex = getIntent().getBooleanExtra("lastIndex", false);
		Log.i(TAG, "isLastIndex="+lastIndex);
		taskManager = (TaskManager) ManagerService.getNewManagerService(this,
				TaskManager.class, this);
		updateInfo();
		fetchWorkStepListDetail();
		execFetechDetail(TaskManager.ACTION_WITNESS_CHOOSE_TEAM);
		
	}

	private void fetchWorkStepListDetail() {
		
		

		// TODO Auto-generated method stub
			
	        getPMSManager().getWorkStepList(rollingPlan.getId()+"",10+"", 1+"",new UINetworkHandler<WorkStepList>(this){

	    	

				@Override
				public void start() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void finish() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void callbackFailure(int statusCode, Header[] headers,
						String response) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void callbackSuccess(int statusCode, Header[] headers,
						WorkStepList response) {
					// TODO Auto-generated method stub
					workStepList = response;

					itemInfos.clear();
					witnessDateLists.clear();
					Log.i(TAG, "update.work step list detail...");
						updateInfo();
				}
	    	});
		
	
		
//		// TODO Auto-generated method stub
//		getPMSManager().getWorkStepDetail(workStep.getId(), new UINetworkHandler<WorkStep>(this) {
//			@Override
//			public void callbackFailure(int statusCode, Header[] headers,
//					String response) {
//				// TODO Auto-generated method stub
//				
//			}
//			@Override
//			public void start() {
//				// TODO Auto-generated method stub
//				
//			}
//			@Override
//			public void finish() {
//				// TODO Auto-generated method stub
//				
//			}
//			@Override
//			public void callbackSuccess(int statusCode, Header[] headers,
//					WorkStep response) {
//				// TODO Auto-generated method stub
//				workStep = response;
//				
//				final boolean isDone = "DONE".equals(workStep.getStepflag());
//
//				itemInfos.clear();
//				witnessDateLists.clear();
//				Log.i(TAG, "update.detail...");
//					updateInfo();
//				
//			}
//		});
	}

	

	String getWitnesserId(WidgetItemInfo widgetItemInfo) {

		Witnesser witnesser = getWitnessByType(widgetItemInfo);
		if (witnesser != null) {
			return witnesser.getId();
		}
		return null;
	}

	private void execFetechDetail(String action) {

		if (action.equals(TaskManager.ACTION_TASK_BATCH_COMMIT)) {

			String witnessdes = null;

			String witnesseaddress=null;
			if (addressWidgetItemInfo!=null) {
				 witnesseaddress = addressWidgetItemInfo.content;
				if (TextUtils.isEmpty(witnesseaddress)) {
					showToast("填写见证地点");
					return;
				}
			}
			if (witnessdesWidgetItemInfo!=null) {
				 witnessdes = witnessdesWidgetItemInfo.content;
				
			}

			
			if (!chooseTime) {
				showToast("填写见证时间");
				return;
			}
			 Team witness=null;
			if (witnessWidgetItemInfo!=null) {
				  witness=(Team) witnessWidgetItemInfo.obj;
				 if (witness == null) {
					 showToast("选择见证负责人");
					return;
				}
			}

			
			 super.callCommitBtn(null);
			 
			taskManager.commitBatch(rollingPlan.getId(), witness.getId(), witnessdes, witnesseaddress, witnessDateLists);
		} else {
			showLoadingView(true);
			taskManager.chooseWitnessHeadList();
		}

	}
	
	List<String>witnessDateLists = new ArrayList<String>();
	
	final List<WidgetItemInfo> itemInfos = new ArrayList<WidgetItemInfo>();

	// R.id. in array String

	
	private void createWorkStepItemInfo(WorkStep workStep) {
		if (WorkStepDetailActivity.showWitness(workStep)) {
			
			if ("DONE".equals(workStep.getStepflag())) {
				//
				Log.i(TAG, "work step is done");
				return;
			}
			
			boolean chooseMode = false;
			String time = "";
			if (workStep.witnessInfo!=null&&workStep.witnessInfo.size()>0) {
				
				if (true) {
					chooseTime = true;
					Log.i(TAG, "witnessInfo  is ok");
					return ;
				}
				
				WitnessInfo sInfo =workStep.witnessInfo.get(0);
			
				if (workStep.witnessInfo.get(0).witnessdate!=0) {
					time = PMSManagerAPI.getdateTimeformat(sInfo.witnessdate);
					chooseMode = true;
					itemInfos.add( new WidgetItemInfo("workTime_"+workStep.getId(),
							workStep.getStepname()+"见证时间：",time,WidgetItemInfo.CHOOSE_BELOW, true));
						
				}else {
					chooseMode = true;
					itemInfos.add(new WidgetItemInfo("workTime_"+workStep.getId(),
							workStep.getStepname()+"见证时间：","选择见证时间",WidgetItemInfo.CHOOSE_BELOW, true));
					
				}
				String stepTime = PMSManagerAPI.combineWorkIdTime(workStep.getId(), time);
				if (!witnessDateLists.contains(stepTime)) {
					if (chooseMode) {
						eidtWitness = true;
					}
					
					witnessDateLists.add(stepTime);
				}
					
			}else {
				Log.i(TAG, "not witness started ");
				chooseMode = true;
				String stepTime = PMSManagerAPI.combineWorkIdTime(workStep.getId(), time);
				if (!witnessDateLists.contains(stepTime)) {
					if (chooseMode) {
						eidtWitness = true;
					}
					
					witnessDateLists.add(stepTime);
				 itemInfos.add(new WidgetItemInfo("workTime_"+workStep.getId(),
						workStep.getStepname()+"见证时间：","选择见证时间",WidgetItemInfo.CHOOSE_BELOW, true));
			
				}else {
					Log.i(TAG, "witnessDateLists already contain this");
				}
			}
		
			
		
	
		}else {
			Log.i(TAG, "no witness info");
		}

	}
	
	
	
	public Team  getWitness(){
		
		if (workStepList == null) {
			return null;
		}
		List<WorkStep> mList = workStepList.getDatas();
		if (mList ==null) {
			return null;
		}
		for (int i = 0; i < mList.size(); i++) {
			WorkStep lastworkStep = mList.get(i);
			if (lastworkStep.witnessInfo!=null&&lastworkStep.witnessInfo.size()>0 && lastworkStep.witnessInfo.get(0).witnessName!=null) {
				WitnessInfo sInfo =lastworkStep.witnessInfo.get(0);
				Team team = new Team();
				team.setId(sInfo.witness);
				team.setName(sInfo.witnessName);
				return team;
				
			}
			
		}
		
		return null;
	}
	
	
	public String  getWitnessDsc(){
		
		
		if (workStepList == null) {
			return null;
		}
		List<WorkStep> mList = workStepList.getDatas();
		if (mList ==null) {
			return null;
		}
		for (int i = 0; i < mList.size(); i++) {
			WorkStep lastworkStep = mList.get(i);
			if (!TextUtils.isEmpty(lastworkStep.operatedesc)) {
				
				return lastworkStep.operatedesc;
				
			}
			
		}

		return null;
	}
	
	public String  getWitnessAddress(){
		
		if (workStepList == null) {
			return null;
		}
		List<WorkStep> mList = workStepList.getDatas();
		if (mList ==null) {
			return null;
		}
		for (int i = 0; i < mList.size(); i++) {
			WorkStep lastworkStep = mList.get(i);
			if (lastworkStep.witnessInfo!=null&&lastworkStep.witnessInfo.size()>0 ) {
				WitnessInfo sInfo =lastworkStep.witnessInfo.get(0);
				if (!TextUtils.isEmpty(sInfo.witnessaddress)) {
					return sInfo.witnessaddress;
				}
				
			}
			
		}
		
		return null;
		
	}
	
	
	private void updateInfo() {
		
		if (itemInfos.isEmpty()) {
			// R.id. in array String
		
		boolean hasDatas = false;
		if (workStepList!=null&&workStepList.getDatas()!=null) {
			
			List<WorkStep> batchTaskList = workStepList.getDatas();
			hasDatas = batchTaskList.size()>0;
			for (WorkStep workStep : batchTaskList) {
				createWorkStepItemInfo(workStep);
			}
			
		}else {
			eidtWitness = false;
		}
		
		if (eidtWitness) {
			
//			String witnessDesc = getWitnessDsc();
//			
//			if (witnessDesc!=null) {
//				witnessdesWidgetItemInfo = new WidgetItemInfo("25",
//						"见证描述：", witnessDesc==null?"见证合格":witnessDesc, WidgetItemInfo.INPUT, false);
//			}else {
//				witnessdesWidgetItemInfo = new WidgetItemInfo("25",
//						"见证描述：", witnessDesc==null?"见证合格":witnessDesc, WidgetItemInfo.INPUT, true);
//			}
//			
//			itemInfos.add(witnessdesWidgetItemInfo);
		
			String witnessaddress = getWitnessAddress();
			if (witnessaddress!=null) {
				itemInfos.add(addressWidgetItemInfo = new WidgetItemInfo("1",
						"见证地点：", witnessaddress, WidgetItemInfo.EDIT, true));
			}else {
				itemInfos.add(addressWidgetItemInfo = new WidgetItemInfo("1",
						"见证地点：", witnessaddress, WidgetItemInfo.EDIT, true));
			}
		
			
			Team witness = getWitness();
			if (witness!=null) {
				itemInfos.add(witnessWidgetItemInfo = new WidgetItemInfo("21",
						"负责人：",witness.getName(), WidgetItemInfo.CHOOSE, true));
				 witnessWidgetItemInfo.obj = witness;
			}else {
				itemInfos.add(witnessWidgetItemInfo = new WidgetItemInfo("21",
						"负责人：", "选择见证负责人", WidgetItemInfo.CHOOSE, true));
			}
			
			
		}
		
		
			
			if (!eidtWitness) {
				if (hasDatas) {
					itemInfos.add(new WidgetItemInfo("all_complete", "见证结果：", "所有工序已经发起了见证",
							WidgetItemInfo.DISPLAY, false));	
				}
				
				   findViewById(R.id.commit_layout).setVisibility(View.GONE);
			}else {
				 findViewById(R.id.commit_layout).setVisibility(View.VISIBLE);
			}
			
			itemInfos.add(new WidgetItemInfo("", "", "",
					WidgetItemInfo.DEVIDER, false));

			
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
										WitnessDealTaskActivity.this);

							}
								break;
								
							case WidgetItemInfo.DEVIDER: {
								convertView = new View(
										WitnessDealTaskActivity.this);
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
										WitnessDealTaskActivity.this);
								EditItemView editItemView = (EditItemView) convertView;
								editItemView.setNameAndContent(
										widgetItemInfo.name,
										widgetItemInfo.content);
								final View tagView = (EditItemView) convertView;
								editItemView.setScan(!eidtWitness);
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
										WitnessDealTaskActivity.this);
								UserInputItemView editItemView = (UserInputItemView) convertView;
								editItemView.setNameAndContent(
										widgetItemInfo.name,
										widgetItemInfo.content);
								final View tagView = (UserInputItemView) convertView;
								editItemView.setScan(!eidtWitness);
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
								
							case WidgetItemInfo.CHOOSE_BELOW: {

								convertView = new ChooseBelowItemView(
										WitnessDealTaskActivity.this);
								if (widgetItemInfo.bindClick) {
									convertView.findViewById(
											R.id.common_choose_item_content)
											.setOnClickListener(new OnClickListener() {
												
												@Override
												public void onClick(View v) {
													// TODO Auto-generated method stub

													if (widgetItemInfo.tag
															.startsWith("workTime")) {// time
														chooseTime = true;
														go2ChooseTime(widgetItemInfo);
													}
											
												}

												
											});
												

								}
								
								
							}
								break;
								
								
							case WidgetItemInfo.CHOOSE: {

								convertView = new ChooseItemView(
										WitnessDealTaskActivity.this);
								final ChooseItemView chooseItemView = (ChooseItemView) convertView;
								if (widgetItemInfo.bindClick) {
									convertView.findViewById(
											R.id.common_choose_item_content)
											.setOnClickListener(new OnClickListener() {
												
												@Override
												public void onClick(View v) {
													// TODO Auto-generated method stub

													if (widgetItemInfo.tag
															.startsWith("workTime")) {// time
														
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

						case WidgetItemInfo.CHOOSE_BELOW:
							ChooseBelowItemView chooseBelowItemView = (ChooseBelowItemView) convertView;
							chooseBelowItemView
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
	
	
	private void showWindow(final ChooseItemView chooseItemView, List<Team> obj) {
		List<String> names = new ArrayList<String>();
		if (obj == null) {
			Log.i(TAG, "item windows is null--" + chooseItemView.getTag());
			return;
		}
		
		for (Team team : obj) {
			
			names.add(getName(team));
		}
		List<Team> iTeams = new ArrayList<Team>();
		if (getUserCount()>0) {
			
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


	@Override
	protected void initView() {
		setContentView(R.layout.edit_ui);// TODO Auto-generated method stub
		super.initView();

	}
    WidgetItemInfo currentItemInfo ;
	void go2ChooseTime(WidgetItemInfo widgetItemInfo) {
		currentItemInfo = widgetItemInfo;
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
		if (name.equals(TaskManager.ACTION_TASK_BATCH_COMMIT)) {

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
				chooseTime = true;
				updateTime(intent.getStringExtra("time"),intent.getStringExtra("format"));
			}
		}
			break;

		default:
			break;
		}

	}
boolean chooseTime = false;
	// id 工序步骤ID
	// witness 见证组组长ID
	// witnessdes N 见证描述
	// witnessaddress 见证地点
	// witnessdate 见证时间
	// operater 完成者
	// operatedate 完成时间（格式2015-05-24 22:22:45）
	// operatedesc N 完成信息描述
	WidgetItemInfo addressWidgetItemInfo, witnessWidgetItemInfo,
			witnessdesWidgetItemInfo ;

	private void updateTime(String date, String formart) {
		// TODO Auto-generated method stub
		if (currentItemInfo!=null) {
			currentItemInfo.content = date;
			String tag = currentItemInfo.tag ;
			 List<String> dateList = witnessDateLists;
			 String id = tag.replaceFirst("workTime_", "");
			for (int i = 0; i < dateList.size(); i++) {
				if (dateList.get(i).startsWith(id)) {//find
					Log.i(TAG, "find set time~~");
					dateList.set(i, PMSManagerAPI.combineWorkIdTime(id, formart));
					break;
				}
			}
		}
		
		updateInfo();
	}

	@Override
	public void succ(String name, int statusCode, Header[] headers,
			Object response) {
		setLoadSucc();
	  if (name.equals(TaskManager.ACTION_TASK_BATCH_COMMIT)) {
			if (response!=null) {
				Log.i(TAG, "BATCH JsonObject=="+response.toString());
			}else {
				Log.i(TAG, "BATCH JsonObject is null");
			}
			showToast("修改成功");
			WorkStepFragment.CallSucc(WorkStepFragment.callsucc);
			finish();
			
		} else {// get witness..
			witnessTeamList = (List<Team>) response;
			Log.i(TAG, "update....");
			getUserCount();
			updateInfo();
		}

	}

	int getUserCount(){
		if (witnessTeamList == null) {
			Log.i(TAG, "update....but get null witnessTeamList");
			return 0 ;
		}else{
			if (witnessTeamList.size()>0 && witnessTeamList.get(0).users!=null &&witnessTeamList.get(0).users.size()>0) {
				int count = witnessTeamList.get(0).users.size();
				Log.i(TAG, "update....get witnessTeamList count:"+count);
				return count ;
			}else{
				Log.i(TAG, "update....get witnessTeamList count is 0");
				return 0 ;
			}
			}
	}
	
	@Override
	public void callCommitBtn(View v) {

		execFetechDetail(TaskManager.ACTION_TASK_BATCH_COMMIT);

	}


}
