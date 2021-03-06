package com.thirdpart.tasktrackerpms.ui;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import cn.jpush.android.data.r;

import com.jameschen.framework.base.BaseActivity;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.entity.RollingPlan;
import com.thirdpart.tasktrackerpms.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RatingBar;

public class PlanWorkStepListActivity extends BaseActivity {

	private Fragment mFragment;
	
	private boolean scan = false;
	RollingPlan mRollingPlan;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("工序信息");
		Intent intent = getIntent();
		mRollingPlan = (RollingPlan) intent.getSerializableExtra(Item.PLAN);
		
		scan = intent.getBooleanExtra("scan", false);
			// Make sure fragment is created.
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		mFragment = fm.findFragmentByTag(WorkStepFragment.class.getName());
		if (mFragment == null) {
			Bundle bundle = new Bundle();
			bundle.putSerializable(Item.PLAN, mRollingPlan);
			bundle.putBoolean("scan", scan);
			mFragment = WorkStepFragment.instantiate(this, WorkStepFragment.class.getName(), bundle);
			ft.add(R.id.fragment_content, mFragment, WorkStepFragment.class.getName());
		}

		ft.commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.fragment_main);
	}
}

