package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.google.gson.JsonObject;
import com.jameschen.framework.base.BaseDialogEditActivity;
import com.jameschen.framework.base.BaseEditActivity;
import com.jameschen.framework.base.UINetworkHandler;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.WitnessDistributed;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.ChooseItemView;
import com.thirdpart.widget.ChooseItemView.onDismissListener;
import com.thirdpart.widget.UserInputItemView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class WitnessBatchUpdateActivity extends BaseDialogEditActivity {
	
	private UserInputItemView witnessInputItemView;
	
	private ChooseItemView  chooseTypeView;
	
	private boolean isBatch;


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}
	
WitnessDistributed mWitnessDistributed;
boolean isMyevent;
 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	        Resources res = getResources();
	        Drawable drawable = res.getDrawable(R.drawable.transparent);////注意该nocolor图片是透明的
	        this.getWindow().setBackgroundDrawable(drawable);
	        this.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
	        
		isBatch = getIntent().getBooleanExtra("batch", false);
		
		DisplayMetrics metric = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(metric);  
        int height = metric.heightPixels; // 屏幕高度（像素）  
      
        
		
		 isMyevent = getLogInController().matchUrls("/witness/myevent");
		
	 mWitnessDistributed = (WitnessDistributed)getIntent().getSerializableExtra(Item.WITNESS);
	 

	setTitle("批量填写见证结果");
      
      initInfo();
      bindView();
 }
 
 int count = 0;
	private void executeCommitWitnessResultNetWorkRequest(
			List<WitnessDistributed> mSeletedItems,String content,String okType) {
		// TODO Auto-generated method stub
		if (mSeletedItems == null||mSeletedItems.size() == 0) {
			finish();
			return;
		}
		List<String> ids = new ArrayList<String>();
		
		 count = 0;
		final int size =  mSeletedItems.size();
		 
		for (WitnessDistributed item : mSeletedItems) {
			ids.add(item.getId());

			  UINetworkHandler<JsonObject> handler=new UINetworkHandler<JsonObject>(this) {

					@Override
					public void start() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void finish() {
						// TODO Auto-generated method stub
						cancelProgressDialog();
					}

					@Override
					public void callbackFailure(int statusCode, Header[] headers,
							String response) {
						// TODO Auto-generated method stub
						showToast(response);
					}

					@Override
					public void callbackSuccess(int statusCode, Header[] headers,
							JsonObject response) {
						// TODO Auto-generated method stub
						count++;
						if (count == size) {
							showToast("提交成功");
						}
						WitnessListFragment.CallSucc(WitnessListFragment.callsucc);
						WitnessBatchUpdateActivity.this.finish();
						
					}
				};
			if (isMyevent) {
				getPMSManager().wirteMyeventWitnessResult(content,okType, item.getId(), handler);
				
			}else {
				getPMSManager().wirteWitnessResult(content,okType, item.getWorkStep().getId(), handler);
					
			}
		}
		
		
	}
	
	@Override
	public void callCommitBtn(View v) {
		if (TextUtils.isEmpty(witnessInputItemView.getContent())) {
			showToast("填写见证结果");
			return;
		}
		
		String okType ="不合格".equals(chooseTypeView.getContent())?"1":"3";
		
		executeCommitWitnessResultNetWorkRequest(WitnessListFragment.mSeletedItems, chooseTypeView.getContent(), okType);
		
		  
			super.callCommitBtn(v);
	}
	
	private void bindView() {
	// TODO Auto-generated method stub
		witnessInputItemView = (UserInputItemView) findViewById(R.id.witness_result_desc);
//	  if (isMyevent) {
//		 // witnessInputItemView.setVisibility(View.GONE);
//	}
		chooseTypeView = (ChooseItemView) findViewById(R.id.witness_choose_ok);
	String desc = mWitnessDistributed.noticeresultdesc; 
	 
		
		 if ("1".equals(mWitnessDistributed.getIsok())) {
			 chooseTypeView.setContent("不合格");
		}else {
			chooseTypeView.setContent("合格");
				
		}
		   witnessInputItemView.setContent(TextUtils.isEmpty(desc)?"见证合格":desc, false);
			
	
		
	   chooseTypeView.setChooseItemClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			List<String> items = new ArrayList<String>();
			   items.add("合格");
			   items.add("不合格");
			 chooseTypeView.showMenuItem(items, items, new onDismissListener<String>() {

					@Override
					public void onDismiss(String item) {
						// TODO Auto-generated method stub
						
					}
				});	
		}
	});
	   
	}


	private void initInfo() {

		final  List<WidgetItemInfo> itemInfos = new ArrayList<WidgetItemInfo>();
		 //R.id.  in array String
		 itemInfos.add(new WidgetItemInfo(null, null, null, 0, false));		
		
		  createItemListToUI(itemInfos, R.id.edit_container, new CreateItemViewListener() {

			@Override
			public View oncreateItem(int index, View convertView,
					ViewGroup viewgroup) {
				// TODO Auto-generated method stub
				//if exsit just update , otherwise create it.
				
				final WidgetItemInfo widgetItemInfo = itemInfos.get(index);
				if (convertView ==null) {
					//create
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						
					convertView = inflater.inflate(R.layout.witness_update_ui, viewgroup, false);	
					
					
				}else {
					
				}
				
				//bind tag
				convertView.setTag(widgetItemInfo);
				return convertView;
			}
		}, false);
		  
		  //make container
		 
	}
	
	@Override
	protected void initView() {
		setContentView(R.layout.edit_ui);// TODO Auto-generated method stub
		super.initView();
		
	}
	

}
