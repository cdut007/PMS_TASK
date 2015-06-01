package com.thirdpart.tasktrackerpms.ui;

import java.io.File;
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
import android.widget.RelativeLayout;

import com.jameschen.comm.utils.Log;
import com.jameschen.framework.base.BaseEditActivity;
import com.jameschen.widget.CustomSelectPopupWindow.Category;
import com.thirdpart.model.IssueManager;
import com.thirdpart.model.TeamMemberManager;
import com.thirdpart.model.TeamMemberManager.LoadUsersListener;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.AddItemView;
import com.thirdpart.widget.AddItemView.AddItem;
import com.thirdpart.widget.ChooseItemView;
import com.thirdpart.widget.EditItemView;
import com.thirdpart.widget.UserInputItemView;

public class IssueFeedbackActivity extends BaseEditActivity {
	
	
	List<String> mFiles = new ArrayList<String>();
	
	List<Category> mGuanzhuList = new ArrayList<Category>();
	IssueManager sIssueManager;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}

	UserInputItemView issueDescView;
	EditItemView  issueTopic;
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
      teamMemberManager =new TeamMemberManager(this); 
 }
 
 public void onAttachedToWindow() {
	 super.onAttachedToWindow();
	 getDeliveryList(false,solverMan);
 };
 TeamMemberManager teamMemberManager;
 
	private void bindView() {
	// TODO Auto-generated method stub
		View container = findViewById(R.id.sroll_container);
		RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) container.getLayoutParams();
		param.addRule(RelativeLayout.ABOVE, R.id.commit_layout);
		container.setLayoutParams(param);
		//
		issueDescView = (UserInputItemView) findViewById(R.id.issue_desc);		
		issueTopic = (EditItemView) findViewById(R.id.issue_topic);
		addFile = (AddItemView) findViewById(R.id.issue_add_file);
		solverMan = (ChooseItemView) findViewById(R.id.issue_choose_deliver);
		solverMan.setContent("选择解决人");
		addPerson = (AddItemView) findViewById(R.id.issue_add_person);
		solverMan.setChooseItemClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDeliveryList(true,solverMan);	
			}
		});
		
		addFile.setOnCreateItemViewListener(addfileListenr);
		addPerson.setOnCreateItemViewListener(addfoucsPersonCreateListenr);
	
		
}
	
	AddItemView.CreateItemViewListener	addfileListenr=new AddItemView.CreateItemViewListener() {
		
		@Override
		public void oncreateItem(String tag, View convertView) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteItem(int index) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void chooseItem(int index, View convertView) {
			// TODO Auto-generated method stub
			
		}


	};
	
	
AddItemView.CreateItemViewListener	addfoucsPersonCreateListenr=new AddItemView.CreateItemViewListener() {
		
		@Override
		public void oncreateItem(String tag, View convertView) {
			// TODO Auto-generated method stub
			mGuanzhuList.add(new Category(tag));//empty
		}
		
		@Override
		public void deleteItem(int index) {
			// TODO Auto-generated method stub
			mGuanzhuList.remove(index);
		}
		
		@Override
		public void chooseItem(int index, View convertView) {
			// TODO Auto-generated method stub
			getDeliveryList(true,convertView);
		}


	};

	
	
	IssueResult issueResult = new IssueResult();
	Category solverCategory;
	private void getDeliveryList(boolean showWindowView,final View view) {
		// TODO Auto-generated method stub
	//	showLoadingView(true);
		teamMemberManager.findDepartmentInfos(showWindowView,view, new LoadUsersListener() {
			
			@Override
			public void onSelcted(Category mParent, Category category) {
				// TODO Auto-generated method stub
				if (view== solverMan) {
					solverCategory = category;
					solverMan.setContent(category.getName());	
				}else {//check is foucs choose person
					ChooseItemView chooseItemView = (ChooseItemView) view.findViewById(R.id.common_add_item_title);
					
					for (Category mCategory : mGuanzhuList) {
						if (category.getId().equals(mCategory.getId())) {
							//modify  do nothing.
							if (!category.getName().equals(chooseItemView.getContent())) {
								showToast("该关注人已经在列表了");//not in current chooseItem,but other already has this name.
							    }
							
							return;
						}
					}
					chooseItemView.setContent(category.getName());
					
					
					AddItem addItem =(AddItem) chooseItemView.getTag();
					//关注人是否已经存在，就只更新
					for (Category mCategory : mGuanzhuList) {
						if (addItem.tag.equals(mCategory.tag)) {
							//modify .
							mCategory = category;
							return;
						}
					}
					mGuanzhuList.add(category);
				}
				
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
		
		if (TextUtils.isEmpty(issueDescView.getContent())) {
			showToast("请填写问题描述");
			return;
		}
		if (solverCategory == null) {
			showToast("请选择解决人");
			return;
		}
		sIssueManager.createIssue(issueResult);
		super.callCommitBtn(v);
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
