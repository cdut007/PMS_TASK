package com.thirdpart.tasktrackerpms.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jameschen.comm.utils.Util;
import com.jameschen.framework.base.BaseActivity;
import com.jameschen.framework.base.BasePageAdapter;
import com.jameschen.framework.base.MyBaseAdapter;
import com.jameschen.framework.base.MyBaseAdapter.HoldView;
import com.thirdpart.model.entity.Department;
import com.thirdpart.model.entity.DepartmentInfo;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.model.entity.RollingPlanList;
import com.thirdpart.model.entity.Task;
import com.thirdpart.tasktrackerpms.R;

public class PlanAdapter extends BasePageAdapter<DepartmentInfo> {
	private Context context;
	
	
	public PlanAdapter(Context context) {
		super(context,R.layout.plan_item);
		this.context = context;
	}

	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		convertView = super.getView(position, convertView, parent);
		
		return convertView;

	}

	@Override
	public void recycle() {
	
		super.recycle();

	}

	@Override
	protected HoldView<DepartmentInfo> createHoldView() {
		// TODO Auto-generated method stub
		return new DepartmentInfoHoldView();
	}

	private final static class DepartmentInfoHoldView extends HoldView<DepartmentInfo> {
		
		
		TextView classTextView,deliveryNumTextView,finishNumTextView,currentNumTextView;
		@Override
		protected void initChildView(View convertView,
				MyBaseAdapter<DepartmentInfo> myBaseAdapter) {
			// TODO Auto-generated method stub
			classTextView = (TextView) convertView.findViewById(R.id.plan_class_item);
			currentNumTextView = (TextView) convertView.findViewById(R.id.plan_current_num_item);
			finishNumTextView = (TextView) convertView.findViewById(R.id.plan_finish_num_item);
			deliveryNumTextView = (TextView) convertView.findViewById(R.id.plan_total_num_item);
		}

		@Override
		protected void setInfo(DepartmentInfo departmentInfo) {
			// TODO Auto-generated method stub
			Department department =departmentInfo.getDepartment();
			if (department!=null) {
				classTextView.setText(department.getName());	
			}else {
				classTextView.setText("Unknow");
			}
			List<Task> mList = departmentInfo.getTasks();
			
			if (mList!=null) {
				currentNumTextView.setText(""+getCurrentTask(mList));
				finishNumTextView.setText(""+getComplateTask(mList));
				deliveryNumTextView.setText(""+getTotalTask(mList));
			}else {
				currentNumTextView.setText("0");
				finishNumTextView.setText("0");
				deliveryNumTextView.setText("0");
			}
			
		}
		
		boolean isHanKouType(Task task){
			
//			if ("焊口".equals(task.getType())) {
//				return true;
//			}
			return true;
		}
		
		private int getComplateTask(List<Task> mList) {
			// TODO Auto-generated method stub
			int count =0;
			for (Task task : mList) {
				if (Util.isJsonNull(task.getComplate())) {
					continue;
				}
				int value = Integer.parseInt(task.getComplate());
				if (value>0 &&isHanKouType(task)) {
					count+=value;
				}
			}
			return count;
		}
//		private float getCurrentTask(int complete,) {
//			// TODO Auto-generated method stub
//			float count =0;
//			for (Task task : mList) {
//				float value = Float.parseFloat(task.getPercent());
//				if (value>0) {
//					count+=value;
//				}
//			}
//			return count;
//		}
		private int getCurrentTask(List<Task> mList) {
			// TODO Auto-generated method stub
			int count =0;
			for (Task task : mList) {
				if (Util.isJsonNull(task.getSurplus())) {
					continue;
				}
				int value = Integer.parseInt(task.getSurplus());
				if (value>0 &&isHanKouType(task)) {
					count+=value;
				}
			}
			return count;
		}
		private int getTotalTask(List<Task> mList) {
			// TODO Auto-generated method stub
			int count =0;
			for (Task task : mList) {
				if (Util.isJsonNull(task.getTotal())) {
					continue;
				}
				int value = Integer.parseInt(task.getTotal());
				if (value>0 &&isHanKouType(task)) {
					count+=value;
				}
			}
			return count;
		}
	}
	
	
	
	

}
