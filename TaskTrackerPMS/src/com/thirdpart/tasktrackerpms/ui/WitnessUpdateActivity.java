package com.thirdpart.tasktrackerpms.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import cn.jpush.android.data.w;

import com.google.gson.JsonObject;
import com.jameschen.framework.base.BaseEditActivity;
import com.jameschen.framework.base.UINetworkHandler;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.WitnessDistributed;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.ChooseItemView;
import com.thirdpart.widget.ChooseItemView.onDismissListener;
import com.thirdpart.widget.DisplayItemView;
import com.thirdpart.widget.UserInputItemView;

public class WitnessUpdateActivity extends BaseEditActivity {
	
	private UserInputItemView witnessInputItemView;
	
	private ChooseItemView  chooseTypeView;
	
	


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}
boolean scan;
WitnessDistributed mWitnessDistributed;
boolean isMyevent;
 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 isMyevent = getLogInController().matchUrls("/witness/myevent");
		
	 mWitnessDistributed = (WitnessDistributed)getIntent().getSerializableExtra(Item.WITNESS);
	scan = getIntent().getBooleanExtra("scan", false);
	if (scan) {
		 setTitle("查看见证结果");
	}else {
		 setTitle("填写见证结果");
	}
     
      
      initInfo();
      bindView();
 }
 
	@Override
	public void callCommitBtn(View v) {
		if (TextUtils.isEmpty(witnessInputItemView.getContent())) {
			showToast("填写见证结果");
			return;
		}
		
		String okType ="不合格".equals(chooseTypeView.getContent())?"1":"3";
		
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
					showToast("提交成功");
					WitnessListFragment.CallSucc(WitnessListFragment.callsucc);
					
				}
			};
		if (isMyevent) {
			getPMSManager().wirteMyeventWitnessResult(witnessInputItemView.getContent(),okType, mWitnessDistributed.getId(), handler);
			
		}else {
			getPMSManager().wirteWitnessResult(witnessInputItemView.getContent(),okType, mWitnessDistributed.getWorkStep().getId(), handler);
				
		}
			super.callCommitBtn(v);
	}
	
	private void bindView() {
	// TODO Auto-generated method stub
		witnessInputItemView = (UserInputItemView) findViewById(R.id.witness_result_desc);
//	  if (isMyevent) {
//		 // witnessInputItemView.setVisibility(View.GONE);
//	}
		chooseTypeView = (ChooseItemView) findViewById(R.id.witness_choose_ok);
	String desc = mWitnessDistributed.getWorkStep().noticeresultdesc;   
	   if (scan) {
		   chooseTypeView.setVisibility(View.GONE);
		   DisplayItemView displayItemView  = (DisplayItemView) findViewById(R.id.witness_display_ok);
		   displayItemView.setVisibility(View.VISIBLE);
		   displayItemView.setContent("3".equals(mWitnessDistributed.getWorkStep().noticeresult)?"合格":"不合格");
		   witnessInputItemView.setContent(TextUtils.isEmpty(desc)?"暂无":desc, true);
			
		   findViewById(R.id.commit_layout).setVisibility(View.GONE);
	}else {
		chooseTypeView.setContent("3".equals(mWitnessDistributed.getWorkStep().noticeresult)?"合格":"不合格");
		  
		   witnessInputItemView.setContent(TextUtils.isEmpty(desc)?"无描述":desc, false);
			
	}
		
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
