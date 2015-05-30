package com.jameschen.framework.base;

import java.util.List;

import com.thirdpart.tasktrackerpms.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseEditActivity extends BaseDetailActivity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		initCommit();
	}

public  void callCommitBtn(View v){
	
};
	private void initCommit() {
		// TODO Auto-generated method stub
		findViewById(R.id.commit_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callCommitBtn(v);
			}
		});
	}
	
	
	
}
