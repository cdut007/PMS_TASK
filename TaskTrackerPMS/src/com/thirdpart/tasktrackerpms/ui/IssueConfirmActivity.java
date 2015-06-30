package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.jameschen.framework.base.BaseDetailActivity;
import com.jameschen.framework.base.BaseEditActivity;
import com.jameschen.framework.base.BaseDetailActivity.CreateItemViewListener;
import com.jameschen.framework.base.CommonCallBack.OnRetryLisnter;
import com.jameschen.widget.CustomSelectPopupWindow.Category;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.IssueManager;
import com.thirdpart.model.ManagerService;
import com.thirdpart.model.TeamMemberManager;
import com.thirdpart.model.ManagerService.OnReqHttpCallbackListener;
import com.thirdpart.model.TeamMemberManager.LoadUsersListener;
import com.thirdpart.model.PlanManager;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.IssueMenu;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.ChooseItemView;
import com.thirdpart.widget.DisplayItemView;
import com.thirdpart.widget.UserInputItemView;

public class IssueConfirmActivity extends IssueDetailActivity {
	
	private UserInputItemView issueDesc;
	private CheckBox issueEffectBox;
	
	IssueResult   issueResult ;
	IssueManager issueManager;
	


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}


 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		issueResult = (IssueResult) getIntent().getSerializableExtra(Item.ISSUE);	
      setTitle("问题确认");
      initInfo();
      bindViews();
      issueManager = (IssueManager) ManagerService.getNewManagerService(this, IssueManager.class, this);
      teamMemberManager =new TeamMemberManager(this); 
     
 }
 
 public void onAttachedToWindow() {
	 super.onAttachedToWindow();
 };
TeamMemberManager teamMemberManager;
 
	
	private void bindViews() {
	// TODO Auto-generated method stub
	issueDesc = (UserInputItemView) findViewById(R.id.issue_plan_desc);
	issueEffectBox = (CheckBox) findViewById(R.id.issue_confirm_check);
	issueEffectBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			
		}
	});
	issueDesc.setContent(issueResult.getDescribe(), true);
	}
	
	
	@Override
	public void callCommitBtn(View v) {
		
		
		String isOk  = issueEffectBox.isChecked()?"1":"0";
		
		
		super.callCommitBtn(v);
	    issueManager.confirmIssue(issueResult.getId(),isOk);
	} 
	
	public void failed(String name, int statusCode, Header[] headers, String response) {
		super.failed(name, statusCode, headers, response);
//		if () {
//			
//		}
	};
	
	public void succ(String name, int statusCode, Header[] headers, Object response) {
		super.succ(name, statusCode, headers, response);
		if (name.equals(IssueManager.ACTION_ISSUE_CONFIRM)) {
			showToast("问题确认成功");
			setResult(RESULT_OK);
			finish();
		}
	};
	

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
						
					convertView = inflater.inflate(R.layout.issue_confirm_ui, viewgroup, false);	
					
					
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
