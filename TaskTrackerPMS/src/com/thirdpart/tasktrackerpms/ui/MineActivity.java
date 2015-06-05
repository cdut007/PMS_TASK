package com.thirdpart.tasktrackerpms.ui;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.jameschen.framework.base.BaseActivity;
import com.thirdpart.model.ConstValues;
import com.thirdpart.model.ConstValues.Item;
import com.thirdpart.model.entity.IssueMenu;
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

public class MineActivity extends BaseActivity {

	private Fragment mFragment;
	IssueMenu menu;
	
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
	
	
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		menu= (IssueMenu) intent.getSerializableExtra(Item.MINE);
		setTitle(menu.getContent());
		// Make sure fragment is created.
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		switch (menu.getId()) {
		case "0"://my issue
		{
			mFragment = fm.findFragmentByTag(IssueFragment.class.getName());
			if (mFragment == null) {
				Bundle bundle = new Bundle();
				mFragment = IssueFragment.instantiate(this, IssueFragment.class.getName(), bundle);
				ft.add(R.id.fragment_content, mFragment, IssueFragment.class.getName());
			}

			ft.commit();
		}
			break;
		case "1"://my delivery plan
		{
		  mFragment = fm.findFragmentByTag(DeliveryPlanFragment.class.getName());
			if (mFragment == null) {
				Bundle bundle = new Bundle();
				bundle.putBoolean("scan", true);
				mFragment = DeliveryPlanFragment.instantiate(this, DeliveryPlanFragment.class.getName(), bundle);
				ft.add(R.id.fragment_content, mFragment, DeliveryPlanFragment.class.getName());
			}

			ft.commit();
		}
			break;
			case "2"://my witness
			{
				mFragment = fm.findFragmentByTag(WitnessListFragment.class.getName());
				if (mFragment == null) {
					Bundle bundle = new Bundle();
					mFragment = IssueFragment.instantiate(this, WitnessListFragment.class.getName(), bundle);
					ft.add(R.id.fragment_content, mFragment, WitnessListFragment.class.getName());
				}

				ft.commit();
			}
				break;

		default:
			break;
		}
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (isTaskRoot()) {
			startActivity(new Intent(this,MainActivity.class));
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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

