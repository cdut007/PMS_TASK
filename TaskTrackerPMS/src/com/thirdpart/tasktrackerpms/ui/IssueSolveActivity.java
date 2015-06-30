package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.jameschen.framework.base.BaseEditActivity;
import com.jameschen.widget.CustomSelectPopupWindow.Category;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.IssueManager;
import com.thirdpart.model.ManagerService;
import com.thirdpart.model.TeamMemberManager;
import com.thirdpart.model.TeamMemberManager.LoadUsersListener;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.ChooseItemView;
import com.thirdpart.widget.UserInputItemView;

public class IssueSolveActivity extends IssueDetailActivity {
	
	private UserInputItemView issueDesc;
	private CheckBox issueCheckBox;
	private ChooseItemView deliveryChooseItemView;
	
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
      setTitle("问题解决");
      initInfo();
      bindViews();
      issueManager = (IssueManager) ManagerService.getNewManagerService(this, IssueManager.class, this);
      teamMemberManager =new TeamMemberManager(this); 
     
 }
 
 public void onAttachedToWindow() {
	 super.onAttachedToWindow();
	 getDeliveryList(false);
 };
TeamMemberManager teamMemberManager;
 
	
	private void bindViews() {
	// TODO Auto-generated method stub
	issueDesc = (UserInputItemView) findViewById(R.id.issue_plan_desc);
	issueCheckBox = (CheckBox) findViewById(R.id.issue_solved_check);
	deliveryChooseItemView = (ChooseItemView) findViewById(R.id.issue_choose_deliver);
	deliveryChooseItemView.setContent("选择指派人");
	issueCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			if (isChecked) {
			deliveryChooseItemView.setVisibility(View.GONE);	
			}else {
			deliveryChooseItemView.setVisibility(View.VISIBLE);
			}
		}
	});
	
	deliveryChooseItemView.setChooseItemClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				getDeliveryList(true);	
			
			
		}
	});
	}
	
	Category mCategory;
	
	private void getDeliveryList(boolean showWindowView) {
		// TODO Auto-generated method stub
	//	showLoadingView(true);
		teamMemberManager.findDepartmentInfos(showWindowView,deliveryChooseItemView, new LoadUsersListener() {
			
			@Override
			public void onSelcted(Category mParent, Category category) {
				// TODO Auto-generated method stub
				mCategory = category;
				solverid = mCategory.getId();
				solvedman = mCategory.getName();
				deliveryChooseItemView.setContent(category.getName());
			}
			
			@Override
			public void loadEndSucc(int type) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void loadEndFailed(int type) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beginLoad(int type) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public void callCommitBtn(View v) {
		if (TextUtils.isEmpty(issueDesc.getContent())) {
			showToast("请填写问题描述");
			return;
		}
		
		if (!issueCheckBox.isChecked()&&TextUtils.isEmpty(solvedman)) {
			showToast("请选择指派人");
			return;
		}
		
		
		
		String isSolve = issueCheckBox.isChecked()?"1":"0";
		
		if (issueCheckBox.isChecked()) {
			solvedman=getLogInController().getInfo().getRealname();
			solverid=getLogInController().getInfo().getId();
		}else {
			if (mCategory!=null) {
				solverid = mCategory.getId();
				solvedman = mCategory.getName();
			}
		}
		
		super.callCommitBtn(v);
	    issueManager.handleIssue(issueResult.getId(),issueDesc.getContent().toString(), issueResult.getWorstepid(), solvedman, isSolve, solverid);
	} 
	
	public void failed(String name, int statusCode, Header[] headers, String response) {
		super.failed(name, statusCode, headers, response);
//		if () {
//			
//		}
	};
	
	public void succ(String name, int statusCode, Header[] headers, Object response) {
		super.succ(name, statusCode, headers, response);
		if (name.equals(IssueManager.ACTION_ISSUE_HANDLE)) {
			showToast("问题提交成功");
			setResult(RESULT_OK);
			finish();
		}
	};
	
String  solvedman,solverid;

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
						
					convertView = inflater.inflate(R.layout.issue_solve_ui, viewgroup, false);	
					
					
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
