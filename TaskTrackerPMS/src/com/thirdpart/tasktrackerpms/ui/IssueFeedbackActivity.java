package com.thirdpart.tasktrackerpms.ui;

import java.io.File;
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
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jameschen.framework.base.BaseEditActivity;
import com.jameschen.framework.base.BaseDetailActivity.CreateItemViewListener;
import com.jameschen.widget.CustomSelectPopupWindow.Category;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.IssueManager;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.ManagerService.OnReqHttpCallbackListener;
import com.thirdpart.model.entity.IssueMenu;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.AddItemView;
import com.thirdpart.widget.ChooseItemView;
import com.thirdpart.widget.DisplayItemView;
import com.thirdpart.widget.UserInputItemView;

public class IssueFeedbackActivity extends BaseEditActivity {
	
	
	List<File> mFiles = new ArrayList<File>();
	
	List<Category> mGuanzhuList = new ArrayList<Category>();
	IssueManager sIssueManager;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}

	UserInputItemView issueDescView,issueTopic;
	AddItemView addFile,addPerson;
	ChooseItemView solverMan;
 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
      setTitle("问题反馈");
      initInfo();
      bindView();
      sIssueManager = (IssueManager) IssueManager.getNewManagerService(this, IssueManager.class, this);
 }
 
	
	
	private void bindView() {
	// TODO Auto-generated method stub
		issueDescView = (UserInputItemView) findViewById(R.id.issue_desc);		
		issueTopic = (UserInputItemView) findViewById(R.id.issue_topic);
		addFile = (AddItemView) findViewById(R.id.issue_add_file);
		solverMan = (ChooseItemView) findViewById(R.id.issue_choose_deliver);
		solverMan.setContent("选择解决人");
		addPerson = (AddItemView) findViewById(R.id.issue_add_person);
}
	IssueResult issueResult = new IssueResult();
	Category solverCategory;
	
	/*params.put("workstepid", issue.getWorstepid());
	params.put("workstepno", issue.getStepno());
	params.put("stepname", issue.getStepname());
	params.put("questionname", issue.getQuestionname());
	params.put("describe", issue.getDescribe());
	params.put("solverid", issue.getSolverid());
	params.put("concernman", issue.getConcerman());*/
	@Override
	public void callCommitBtn(View v) {
		// TODO Auto-generated method stub
		super.callCommitBtn(v);
		if (TextUtils.isEmpty(issueDescView.getContent())) {
			showToast("请填写问题描述");
			return;
		}
		if (solverCategory == null) {
			showToast("请选择解决人");
			return;
		}
		sIssueManager.createIssue(issueResult);
	}

	
	 @Override
	 public void failed(String name, int statusCode, Header[] headers,
	 		String response) {
	 	// TODO Auto-generated method stub
	 	super.failed(name, statusCode, headers, response);
	 
	 }
	   
	   @Override
	 public void succ(String name, int statusCode, Header[] headers,
	 		Object response) {
	 	// TODO Auto-generated method stub
	 	super.succ(name, statusCode, headers, response);
	 	showToast("提交成功");
	 	
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
						
					convertView = inflater.inflate(R.layout.issue_feedback_ui, viewgroup, false);	
					
					
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
