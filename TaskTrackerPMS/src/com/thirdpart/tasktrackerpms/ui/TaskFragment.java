package com.thirdpart.tasktrackerpms.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jameschen.comm.utils.UtilsUI;
import com.jameschen.framework.base.BaseFragment;
import com.jameschen.framework.base.MyBaseAdapter;
import com.jameschen.framework.base.MyBaseAdapter.HoldView;
import com.jameschen.widget.BadgeView;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.ManagerService;
import com.thirdpart.model.ManagerService.OnReqHttpCallbackListener;
import com.thirdpart.model.TaskManager;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.TabItemView;
import com.thirdpart.widget.TabItemView.onItemSelectedLisnter;

public class TaskFragment extends BaseFragment implements OnReqHttpCallbackListener{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	TaskManager taskManager;
	private static int screenWidth = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.task_ui, container, false);
		screenWidth = UtilsUI.getWidth(getActivity().getApplication());
		initView(view);
		taskManager = (TaskManager) ManagerService.getNewManagerService(getBaseActivity(), TaskManager.class, this);
		aquireCount();
		return view;
	}
	
private void aquireCount() {
		// TODO Auto-generated method stub
		
	}

ItemAdapter itemAdapter;
List<TaskItem> mHankouList = new ArrayList<TaskItem>();
List<TaskItem> mZhijiaList = new ArrayList<TaskItem>();
static final int HANKOU=TaskManager.TYPE_HANKOU,ZHIJIA=TaskManager.TYPE_ZHIJIA;
private void initView(View view) {
		initList(mHankouList,HANKOU);
		initList(mZhijiaList,ZHIJIA);
	final	GridView gridView = (GridView) view.findViewById(R.id.common_list_gv);
		itemAdapter = new ItemAdapter(this, mHankouList);//deault
		gridView.setAdapter(itemAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
				Object object = parent.getAdapter().getItem(position);
				if (object == null) {
					return;
				}
				TaskItem p = (TaskItem) (object);
				intent.putExtra(Item.TASK, p);
				startActivity(intent);
			}
		});
		
	ViewGroup dateContainer = (ViewGroup) view.findViewById(R.id.task_indicator_date_container);	
	 int size = dateContainer.getChildCount();
	 for (int i = 0; i < size; i++) {
		dateContainer.getChildAt(i).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callDateSelectAction(v);
			}
		});
	}
	 dateContainer.getChildAt(0).performClick();
	 
	TabItemView tabItemView = (TabItemView) view.findViewById(R.id.task_type);
		tabItemView.setItemSelectedLisnter(new onItemSelectedLisnter() {
			
			@Override
			public void onTabSelected(int pos, WidgetItemInfo tag) {
				// TODO Auto-generated method stub
				if (pos == 0) {//hankou
					itemAdapter.setObjectList(mHankouList);
				}else {
					itemAdapter.setObjectList(mZhijiaList);
				}
				itemAdapter.notifyDataSetChanged();
				gridView.setAdapter(itemAdapter);
				title = tag.content;
				updateTitle();
			}
		});
	}
String title ;
public void updateTitle() {
	if (title ==null) {
		title="焊口";
	}
	
	(getBaseActivity()).changeTitle(title);
	
	
}
@Override
public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	super.onAttach(activity);
	updateTitle();
}
@Override
public void onHiddenChanged(boolean hidden) {
	// TODO Auto-generated method stub
	super.onHiddenChanged(hidden);
	if (hidden) {
		(getBaseActivity()).changeTitle("");
	}else {
		updateTitle();
	}
}
	int type ,date;
	View lastSelectDateView;
	protected void callDateSelectAction(View v) {
		if (lastSelectDateView!=null) {
			lastSelectDateView.setSelected(false);
		}
		v.setSelected(true);
		lastSelectDateView = v;
		
		//do network call action.
		
	}

	
	void initList(List<TaskItem> mList,int type){
		mList.add(new TaskItem("计划",0x7f0290d3,type));// 计划
		mList.add(new TaskItem("完成",0x7f0090d7,type));// 完成
		mList.add(new TaskItem("未完成",0x7fe56200,type));// 未完成
		mList.add(new TaskItem("施工",0x7fe78d00,type));// 施工
		mList.add(new TaskItem("处理",0x7f029d84,type));// 处理
		mList.get(0).count=99;
		mList.get(4).count=99;
	}
	
	static class ItemAdapter extends MyBaseAdapter<TaskItem> {

		public ItemAdapter(TaskFragment taskFragment, List<TaskItem> mList) {
			super(taskFragment.getActivity(), R.layout.circle_text_item);
			setObjectList(mList);
			notifyDataSetChanged();
		}

		@Override
		protected HoldView<TaskItem> createHoldView() {
			// TODO Auto-generated method stub
			return new ItemHoldView();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			convertView = super.getView(position, convertView, parent);

			return convertView;

		}
	}

	
	
	public static class TaskItem implements Serializable{
		 /**
		 * 
		 */
		private static final long serialVersionUID = -6732693444174428835L;
		public TaskItem(String name, int color,int type) {
			super();
			this.name = name;
			this.color = color;
			this.type = type;
		}
		public String name ;
		 public int  color ,type ;
		public int count;
		 
	}
	
	static class ItemHoldView extends HoldView<TaskItem> {
		TextView contenTextView;
		SimpleDraweeView bgDraweeView;
		View cricleContaner;
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		public ItemHoldView() {
			// TODO Auto-generated constructor stub
			
			paint.setStyle(Style.FILL);
		}
		@Override
		protected void initChildView(View convertView,
				MyBaseAdapter<TaskItem> myBaseAdapter) {
			// TODO Auto-generated method stub
			cricleContaner = convertView.findViewById(R.id.cricle_contaner);
			contenTextView = (TextView) convertView.findViewById(R.id.common_circle_item_content);
			bgDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.common_circle_item_img);
		
			LayoutParams param = bgDraweeView.getLayoutParams();
			
			if (screenWidth>0) {
				param.width = screenWidth/3;
				param.height =screenWidth/3;
			}
			BadgeView badgeView = new BadgeView(bgDraweeView.getContext());
			badgeView.setBadgeCount(0);
			bgDraweeView.setTag(badgeView);
			badgeView.setTargetView(bgDraweeView);
		}
		
		@Override
		protected void setInfo(TaskItem object) {
			// TODO Auto-generated method stub
			
			contenTextView.setText(object.name+"90道");
			final int color = object.color;
			bgDraweeView.getHierarchy().setPlaceholderImage(new Drawable() {
				
				@Override
				public void setColorFilter(ColorFilter cf) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void setAlpha(int alpha) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public int getOpacity() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public void draw(Canvas canvas) {
					// TODO Auto-generated method stub
					int r = bgDraweeView.getWidth()/2;
					paint.setColor(color);
					canvas.drawCircle(r, r, r, paint);
					
				}
			});

			BadgeView badgeView = (BadgeView) bgDraweeView.getTag();
			badgeView.setBadgeCount(object.count);
		
		}
		

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
