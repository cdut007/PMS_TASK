package com.thirdpart.tasktrackerpms.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jameschen.comm.utils.UtilsUI;
import com.jameschen.framework.base.BaseEditActivity;
import com.jameschen.framework.base.CommonCallBack.OnRetryLisnter;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.ManagerService;
import com.thirdpart.model.ManagerService.OnReqHttpCallbackListener;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.WitnessManager;
import com.thirdpart.model.entity.WitnessDistributed;
import com.thirdpart.model.entity.Witnesser;
import com.thirdpart.model.entity.WitnesserList;
import com.thirdpart.model.entity.WorkStep;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.ChooseItemView;
import com.thirdpart.widget.DisplayItemView;
import com.thirdpart.widget.EditItemView;

public class WitnessChooseActivity extends BaseEditActivity  {

	private WitnessManager witnessManager;
	WitnessDistributed witness;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		witness = (WitnessDistributed) getIntent().getSerializableExtra(
				Item.WITNESS);
		witness.setWitnessdate(getdateformat(Long.parseLong(witness
				.getWitnessdate())));
		witnessManager = (WitnessManager) ManagerService.getNewManagerService(
				this, WitnessManager.class, this);
		setTitle("选择见证人");
		updateInfo();
		execFetechDetail(WitnessManager.ACTION_WITNESS_CHOOSE_WITNESSER);
	}

	String getWitnesserId (WidgetItemInfo widgetItemInfo){

		Witnesser witnesser = getWitnessByType(widgetItemInfo);
		if (witnesser!=null) {
			return witnesser.getId();
		}
		return null;
	}
	
	private void execFetechDetail(String action) {
		
		if (action.equals(WitnessManager.ACTION_WITNESS_CHOOSE_COMMIT)) {
			String witnessid=witness.getWitness();
			String witnesseraqa=getWitnesserId(qaWidgetItemInfo);
			String witnesseraqc2=getWitnesserId(c2qaWidgetItemInfo);
			String witnesseraqc1=getWitnesserId(c1qaWidgetItemInfo);
			String witnesserb=getWitnesserId(notibWidgetItemInfo);
			String witnesserc=getWitnesserId(noticWidgetItemInfo);
			String witnesserd=getWitnesserId(notidWidgetItemInfo);
			witnessManager.commit(witnessid, witnesseraqa, witnesseraqc2, witnesseraqc1, witnesserb, witnesserc, witnesserd);
		} else {
			showLoadingView(true);
			witnessManager.chooseWitnessList(witness.getWitness());	
		}
		
	}
	
	final List<WidgetItemInfo> itemInfos = new ArrayList<WidgetItemInfo>();
	
	private void updateInfo() {
		if (itemInfos.isEmpty()) {
			// R.id. in array String
			itemInfos.add(new WidgetItemInfo("0", "操作工序：", witness.getWorkStep()
					.getStepname(), WidgetItemInfo.DISPLAY, false));
			itemInfos.add(addressWidgetItemInfo=new WidgetItemInfo("1", "见证地点：", witness
					.getWitnessaddress(), WidgetItemInfo.EDIT, false));
			itemInfos.add(timeWidgetItemInfo=new WidgetItemInfo("2", "见证时间：",
					witness.getWitnessdate(), WidgetItemInfo.CHOOSE, true));

			WorkStep workStep = witness.getWorkStep();

			if (!isEmpty(workStep.getNoticeaqc1())) {
				itemInfos.add(c1qaWidgetItemInfo=new WidgetItemInfo("3", "A-QC1：", workStep
						.getNoticeaqc1(), WidgetItemInfo.CHOOSE, true));

			}

			if (!isEmpty(workStep.getNoticeaqc2())) {
				itemInfos.add(c2qaWidgetItemInfo=new WidgetItemInfo("4", "A-QC2：", workStep
						.getNoticeaqc2(), WidgetItemInfo.CHOOSE, true));

			}

			if (!isEmpty(workStep.getNoticeaqa())) {
				itemInfos.add(qaWidgetItemInfo=new WidgetItemInfo("5", "A-QA：", workStep
						.getNoticeaqc1(), WidgetItemInfo.CHOOSE, true));

			}

			if (!isEmpty(workStep.getWitnesserb())) {
				itemInfos.add(notibWidgetItemInfo=new WidgetItemInfo("6", "通知点B：", workStep
						.getWitnesserc(), WidgetItemInfo.CHOOSE, true));

			}

			if (!isEmpty(workStep.getWitnesserc())) {
				itemInfos.add(noticWidgetItemInfo=new WidgetItemInfo("7", "通知点C：", workStep
						.getWitnesserc(), WidgetItemInfo.CHOOSE, true));

			}
			if (!isEmpty(workStep.getWitnesserd())) {
				itemInfos.add(notidWidgetItemInfo=new WidgetItemInfo("8", "通知点D：", workStep
						.getWitnesserd(), WidgetItemInfo.CHOOSE, true));

			}
			
			View view = findViewById(R.id.edit_container);
			FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) view
					.getLayoutParams();
			params.width = UtilsUI.getWidth(getApplication())
					- UtilsUI.getPixByDPI(this, 20);
			params.topMargin = UtilsUI.getPixByDPI(this, 10);
			params.gravity = Gravity.CENTER;
			view.setLayoutParams(params);
	
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
										WitnessChooseActivity.this);

							}
								break;
							case WidgetItemInfo.EDIT: {
								convertView = new EditItemView(
										WitnessChooseActivity.this);
								
							}
								break;
							case WidgetItemInfo.CHOOSE: {
								convertView = new ChooseItemView(
										WitnessChooseActivity.this);
								final ChooseItemView chooseItemView = (ChooseItemView) convertView;
								if (widgetItemInfo.bindClick) {
									convertView.findViewById(
											R.id.common_choose_item_content)
											.setOnClickListener(
													new OnClickListener() {

														@Override
														public void onClick(
																View v) {
															if (widgetItemInfo.tag
																	.equals("2")) {// time
																go2ChooseTime();
															} else {//
																showWindow(chooseItemView,(List<Witnesser>)widgetItemInfo.obj);
																
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
						case WidgetItemInfo.EDIT: {
							EditItemView editItemView = (EditItemView) convertView;
							editItemView.setNameAndContent(widgetItemInfo.name,
									widgetItemInfo.content);
						}
							break;
						default:
							break;
						}
						
						//update window
						
						initOptionItem(
								widgetItemInfo,
								convertView
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
		if (witnessList == null||witnessList.size()==0) {
			Log.i(TAG, "witnessList is null...");
			return;
		}
		
		List<Witnesser> findWitnesser = getWitneessType(widgetItemInfo.content);
		
		if (findWitnesser!=null) {
			widgetItemInfo.obj = findWitnesser;
			//((TextView)btnView).setText(findWitnesser.get(0).getRealname());
			
		}
	}

	
	public CharSequence getAddress() {
		EditItemView editItemView = (EditItemView) getViewByWidget(addressWidgetItemInfo);
		return editItemView.getContent();
	}
	
	private void showWindow(
			final ChooseItemView chooseItemView,
			List<Witnesser> obj) {
		List<String> names = new ArrayList<String>();
		if (obj == null) {
			Log.i(TAG, "item windows is null--"+chooseItemView.getTag());
			return;
		}
		for (Witnesser witnesser : obj) {
			names.add(witnesser.getRealname());
		}
		
		chooseItemView.showMenuItem(obj,names,new ChooseItemView.onDismissListener<Witnesser>(){

			@Override
			public void onDismiss(
					Witnesser item) {
				// TODO Auto-generated method stub
				updateItem((WidgetItemInfo)chooseItemView.getTag(),item);
			}
			
		});	
	}
	
	
	private Witnesser getWitnessByType(WidgetItemInfo widgetItemInfo) {
		// TODO Auto-generated method stub
		if (widgetItemInfo==null) {
			return null;
		}
		View view = getViewByWidget(widgetItemInfo);
		if (view!=null) {
			List<Witnesser> witnessers=(List<Witnesser>) widgetItemInfo.obj;
			if (witnessers!=null) {
				for (Witnesser witnesser : witnessers) {
					if (witnesser.getRealname().equals(widgetItemInfo.content)) {
						return witnesser;
					}
				}
			}
		}
		return null;
	}
	
	protected void updateItem(WidgetItemInfo widgetItemInfo, Witnesser item) {
		// TODO Auto-generated method stub
		widgetItemInfo.content = item.getRealname();
		
		updateInfo();
		
	}

	private List<Witnesser> getWitneessType(String content) {
		// TODO Auto-generated method stub
		for (int i = 0; i < witnessList.size(); i++) {
			 WitnesserList witnessItem = witnessList.get(i);
			if (witnessItem.getWitnesserType().contains(content)||content.equals(witnessItem.getWitnesserType())) {
				return witnessItem.getWitnesser();
			}
		}
		return null;
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

	void go2ChooseTime() {
		Intent intent = new Intent(this, TimeActivity.class);

		// intent.putExtra(ConstValues.ID, Long.parseLong(rollingPlan.getId()));
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
		if (name.equals(WitnessManager.ACTION_WITNESS_CHOOSE_COMMIT)) {
			
		}else {
			showRetryView(new OnRetryLisnter() {
				
				@Override
				public void doRetry() {
					// TODO Auto-generated method stub
					execFetechDetail(name);
				}
			});	
		}
		
	}




	
	private String getdateformat(long times) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = new Date(times);
		return sdf.format(date);
	}

	private List<WitnesserList> witnessList;
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, intent);
		switch (arg0) {
		case TimeActivity.REQUEST_PICK_DATE: {
			if (arg1 == RESULT_OK) {
				int monthVal = intent.getIntExtra("month", 1);
				int dayVal = intent.getIntExtra("day", 1);
				int monthEndVal = intent.getIntExtra("monthEnd", 1);
				int dayEndVal = intent.getIntExtra("dayEnd", 1);
				updateTime(intent.getStringExtra("time"));
			}
		}
			break;

		default:
			break;
		}
	}

	WidgetItemInfo timeWidgetItemInfo,addressWidgetItemInfo,qaWidgetItemInfo,
	c1qaWidgetItemInfo,c2qaWidgetItemInfo,notibWidgetItemInfo,noticWidgetItemInfo,notidWidgetItemInfo;
	
	
	
	private void updateTime(String date) {
		// TODO Auto-generated method stub
		timeWidgetItemInfo.content = date;
		
		updateInfo();
	}

	@Override
	public void succ(String name, int statusCode, Header[] headers,
			Object response) {
		setLoadSucc();
		if (name.equals(WitnessManager.ACTION_WITNESS_CHOOSE_COMMIT)) {
			showToast("修改成功");
		} else {// get witless..
			witnessList = (List<WitnesserList>) response;
			Log.i(TAG, "update....");
			updateInfo();
		}

	}
	@Override
	public void callCommitBtn(View v) {
		// TODO Auto-generated method stub
		super.callCommitBtn(v);
		execFetechDetail(WitnessManager.ACTION_WITNESS_CHOOSE_COMMIT);
	}

}
