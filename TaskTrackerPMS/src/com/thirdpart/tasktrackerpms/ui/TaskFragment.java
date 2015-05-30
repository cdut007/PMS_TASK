package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
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

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.jameschen.comm.utils.UtilsUI;
import com.jameschen.framework.base.BaseFragment;
import com.jameschen.framework.base.MyBaseAdapter;
import com.jameschen.framework.base.MyBaseAdapter.HoldView;
import com.jameschen.widget.BadgeView;
import com.thirdpart.tasktrackerpms.R;

public class TaskFragment extends BaseFragment {
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
	
	
	private static int screenWidth = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.task_ui, container, false);
		screenWidth = UtilsUI.getWidth(getActivity().getApplication());
		initView(view);
		return view;
	}

	private void initView(View view) {
		
		GridView gridView = (GridView) view.findViewById(R.id.common_list_gv);
		gridView.setAdapter(new ItemAdapter(this, new ArrayList<TaskItem>()));
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
//				RollingPlan p = (RollingPlan) (object);
//				intent.putExtra(Item.TASK, p);
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
	
	}
  
	
	View lastSelectDateView;
	protected void callDateSelectAction(View v) {
		if (lastSelectDateView!=null) {
			lastSelectDateView.setSelected(false);
		}
		v.setSelected(true);
		lastSelectDateView = v;
		
		//do network call action.
		
	}

	static class ItemAdapter extends MyBaseAdapter<TaskItem> {

		public ItemAdapter(TaskFragment taskFragment, List<TaskItem> mList) {
			super(taskFragment.getActivity(), R.layout.circle_text_item);

			mList.add(new TaskItem("计划",0x7f0290d3));// 计划
			mList.add(new TaskItem("完成",0x7f0090d7));// 完成
			mList.add(new TaskItem("未完成",0x7fe56200));// 未完成
			mList.add(new TaskItem("施工",0x7fe78d00));// 施工
			mList.add(new TaskItem("处理",0x7f029d84));// 处理
			setObjectList(mList);
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

	
	
	static class TaskItem{
		 public TaskItem(String name, int color) {
			super();
			this.name = name;
			this.color = color;
		}
		public String name ;
		 public int  color ;
		 
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
			badgeView.setBadgeCount(99);
			bgDraweeView.setTag(badgeView);
			badgeView.setTargetView(cricleContaner);
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
			badgeView.setBadgeCount(99);
		
		}
		

	}

}
