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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jameschen.framework.base.BaseDetailActivity;
import com.jameschen.framework.base.BaseEditActivity;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.ManagerService;
import com.thirdpart.model.ManagerService.OnReqHttpCallbackListener;
import com.thirdpart.model.PlanManager;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.IssueMenu;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.tasktrackerpms.ui.TaskFragment.TaskItem;
import com.thirdpart.widget.DisplayItemView;

public class TaskDetailActivity extends BaseEditActivity implements OnReqHttpCallbackListener{
	


	private Fragment mFragment;


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}


 TaskItem taskItem;
 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		taskItem = (TaskItem) getIntent().getSerializableExtra(Item.TASK);	
      setTitle(""+taskItem.name+"-"+(taskItem.type==0?"焊口":"支架"));
      initInfo();
 }
 //
 
	
	
	private void initInfo() {

		final  List<WidgetItemInfo> itemInfos = new ArrayList<WidgetItemInfo>();
		 //R.id.  in array String
		 itemInfos.add(new WidgetItemInfo("0", null, null, 0, false));		
		
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
						
					convertView = inflater.inflate(R.layout.fragment_main, viewgroup, false);	
					
					
				}else {
					
				}
				
				//bind tag
				convertView.setTag(widgetItemInfo);
				return convertView;
			}
		}, false);
		  
		  //make container
		  
		  FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			mFragment = fm.findFragmentByTag(WorkStepFragment.class.getName());
			if (mFragment == null) {
				Bundle bundle = new Bundle();
			//	bundle.putLong(ConstValues.ID, Long.parseLong(issueMenu.getId()));
				mFragment = WorkStepFragment.instantiate(this, WorkStepFragment.class.getName(), bundle);
				ft.add(R.id.fragment_content, mFragment, WorkStepFragment.class.getName());
			}

			ft.commit();
		  
	}
	
	@Override
	protected void initView() {
		setContentView(R.layout.edit_ui);// TODO Auto-generated method stub
		super.initView();
		
	}
	

	@Override
	public void start(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void failed(String name, int statusCode, Header[] headers,
			String response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finish(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void succ(String name, int statusCode, Header[] headers,
			Object response) {
		// TODO Auto-generated method stub
		
		
	}
	

}
