package com.thirdpart.tasktrackerpms.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jameschen.comm.utils.Log;
import com.jameschen.comm.utils.UtilsUI;
import com.jameschen.framework.base.BaseFragment;
import com.jameschen.framework.base.MyBaseAdapter;
import com.jameschen.framework.base.UINetworkHandler;
import com.jameschen.framework.base.MyBaseAdapter.HoldView;
import com.jameschen.widget.BadgeView;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.ManagerService;
import com.thirdpart.model.ManagerService.OnReqHttpCallbackListener;
import com.thirdpart.model.PMSManagerAPI;
import com.thirdpart.model.TaskManager;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.TaskCategory;
import com.thirdpart.model.entity.TaskCategoryInfo;
import com.thirdpart.model.entity.TaskCategoryItem;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.TabItemView;
import com.thirdpart.widget.TabItemView.onItemSelectedLisnter;
import com.thirdpart.widget.TouchImage;

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
		initList(mHankouList,"hk");
		initList(mZhijiaList,"zj");
	final	GridView gridView = (GridView) view.findViewById(R.id.common_list_gv);
		itemAdapter = new ItemAdapter(this, mHankouList);//deault
		gridView.setAdapter(itemAdapter);
	
		
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
	 
	 
	 dateContainer.getChildAt(1).performClick();//today
	 
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
	
	View lastSelectDateView;
	protected void callDateSelectAction(View v) {
		if (lastSelectDateView!=null) {
			lastSelectDateView.setSelected(false);
		}
		v.setSelected(true);
		lastSelectDateView = v;
		
		//do network call action.
		queryDate(v.getTag().toString());
	}
	String category;
	//dateYear, dateWeek, dateMonth, dateAfter, dateCurrent, dateBefore
	private void queryDate(String category) {
		this.category = category;
		// TODO Auto-generated method stub
		getPMSManager().getTaskFinishCountStatus(category, new UINetworkHandler<TaskCategory>(getActivity()) {

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
				showToast(response);
			}

			@Override
			public void callbackSuccess(int statusCode, Header[] headers,
					TaskCategory response) {
				// TODO Auto-generated method stub
				updateInfo(response);
			}
		});
	}
	
	private void updateInfo(TaskCategory response) {
		// TODO Auto-generated method stub
		List<TaskCategoryItem> resuls = response.retuls;
		
		for (TaskCategoryItem taskCategoryItem : resuls) {
				if (taskCategoryItem.type.equals("支架")) {
					updateDataList(taskCategoryItem.result,mZhijiaList);
				}else if (taskCategoryItem.type.equals("焊口")) {
					updateDataList(taskCategoryItem.result,mHankouList);
				}
		}
		itemAdapter.notifyDataSetChanged();
	}
	
	private void updateDataList(List<TaskCategoryInfo> result,
			List<TaskItem> mDataItems) {
		for (TaskItem taskItem : mDataItems) {
				
			for (TaskCategoryInfo categoryInfo : result) {
				if (taskItem.name.equals(categoryInfo.getStatus())) {
					taskItem.count = Integer.parseInt(categoryInfo.getResult());
					taskItem.status = Integer.parseInt(categoryInfo.type);
					continue;
				}
			}
			
		}
		
	}


	void initList(List<TaskItem> mList,String type){
		mList.add(new TaskItem("计划",0x7f0290d3,type));// 计划
		mList.add(new TaskItem("完成",0x7f0090d7,type));// 完成
		mList.add(new TaskItem("未完成",0x7fe56200,type));// 未完成
		mList.add(new TaskItem("施工中",0x7fe78d00,type));// 施工
		mList.add(new TaskItem("处理中",0x7f029d84,type));// 处理
		mList.get(0).count=0;
		mList.get(4).count=0;
	}
	
	static class ItemAdapter extends MyBaseAdapter<TaskItem> {
		TaskFragment sFragment;
		public ItemAdapter(TaskFragment taskFragment, List<TaskItem> mList) {
			super(taskFragment.getActivity(), R.layout.circle_text_item);
			sFragment = taskFragment;
			setObjectList(mList);
			notifyDataSetChanged();
		}

		@Override
		protected HoldView<TaskItem> createHoldView() {
			// TODO Auto-generated method stub
			return new ItemHoldView(sFragment);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			convertView = super.getView(position, convertView, parent);

			return convertView;

		}
		

		@Override
		public boolean isEnabled(int position) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	
	public interface OnItemClickListener{
		public void onItemClick( View view, TaskItem object) ;
	}
	
	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick( View view, TaskItem object) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
			if (object == null) {
				return;
			}
			TaskItem p = (TaskItem) (object);
			p.taskDate=PMSManagerAPI.getdateformat(System.currentTimeMillis());
			p.category=category;
			intent.putExtra(Item.TASK, p);
			startActivity(intent);
		}
	};
	
	
	public static class TaskItem implements Serializable{
		 /**
		 * 
		 */
		private static final long serialVersionUID = -6732693444174428835L;
		public TaskItem(String name, int color,String type) {
			super();
			this.name = name;
			this.color = color;
			this.type = type;
		}
		public String name ,type,category,taskDate;
		 public int  color ,status ;
		public int count;
		 
	}
	
	static class ItemHoldView extends HoldView<TaskItem> {
		TextView contenTextView;
		SimpleDraweeView bgDraweeView;
		View cricleContaner;
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		private TaskFragment sFragment;
		public ItemHoldView(TaskFragment taskFragment) {
			// TODO Auto-generated constructor stub
			sFragment = taskFragment;
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
			convertView.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					//if (event.getAction()!=MotionEvent.ACTION_MOVE) {
						bgDraweeView.postInvalidate();
					//}
					
					return false;
				}
			});
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					sFragment.itemClickListener.onItemClick(v, (TaskItem)contenTextView.getTag());
					
					
				}
			});
		}
		
		@Override
		protected void setInfo( TaskItem object) {
			// TODO Auto-generated method stub
			contenTextView.setTag(object);
			contenTextView.setText(object.name+"90道");
			final int color = object.color;
			final int count = object.count;
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
				
					if (bgDraweeView.isPressed()) {

						paint.setColor(color&0x99999999);
					}else {

						paint.setColor(color);	
					}
					canvas.drawCircle(r, r, r, paint);
					
					if (count>0) {
						paint.setColor(Color.RED);	
						canvas.drawCircle(1.8f*r, 0.4f*r, (float)UtilsUI.getPixByDPI(bgDraweeView.getContext(), 10), paint);
						paint.setColor(Color.WHITE);
						
					if (count>=10 && count<=99) {
						paint.setTextSize(contenTextView.getTextSize());
						canvas.drawText(""+count, 1.67f*r, 0.47f*r, paint);	
					
					}else if(count>99){
						paint.setTextSize(0.6f*contenTextView.getTextSize());
						canvas.drawText("99+", 1.70f*r, 0.45f*r, paint);	
					
						}else {
							paint.setTextSize(contenTextView.getTextSize());
							canvas.drawText(""+count, 1.74f*r, 0.48f*r, paint);	
						
						}
					
					}else {
						
					}
					

				}
			});

			//BadgeView badgeView = (BadgeView) bgDraweeView.getTag();
			//badgeView.setBadgeCount(object.count);
		
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
