package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jameschen.framework.base.BaseFragment;
import com.jameschen.framework.base.MyBaseAdapter;
import com.jameschen.framework.base.MyBaseAdapter.HoldView;
import com.jameschen.widget.BadgeView;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.tasktrackerpms.R;

public class TaskFragment extends BaseFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.task_ui, container, false);
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
				RollingPlan p = (RollingPlan) (object);
				intent.putExtra(Item.TASK, p);
				startActivity(intent);
			}
		});
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
		@Override
		protected void initChildView(View convertView,
				MyBaseAdapter<TaskItem> myBaseAdapter) {
			// TODO Auto-generated method stub
			cricleContaner = convertView.findViewById(R.id.cricle_contaner);
			contenTextView = (TextView) convertView.findViewById(R.id.common_circle_item_content);
			bgDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.common_circle_item_img);
		}
		
		BadgeView badgeView;
		@Override
		protected void setInfo(TaskItem object) {
			// TODO Auto-generated method stub
			badgeView = new BadgeView(bgDraweeView.getContext());
			badgeView.setBadgeCount(99);
			contenTextView.setText(object.name+"90道");
			RoundingParams roundingParams = 
					bgDraweeView.getHierarchy().getRoundingParams();
				roundingParams.setBorder(object.color, 1.0f);
				roundingParams.setRoundAsCircle(true);
				bgDraweeView.getHierarchy().setRoundingParams(roundingParams);
			badgeView.setTargetView(cricleContaner);
		
		}
		

	}

}
