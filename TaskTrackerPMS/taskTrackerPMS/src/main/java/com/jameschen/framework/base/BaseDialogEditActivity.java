package com.jameschen.framework.base;

import java.util.List;

import org.apache.http.Header;

import com.thirdpart.tasktrackerpms.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseDialogEditActivity extends BaseDialogDetailActivity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		initCommit();
	}

public  void callCommitBtn(View v){
	showProgressDialog("正在提交", "请稍候...",null);
};
@Override
public void failed(String name, int statusCode, Header[] headers,
		String response) {
	// TODO Auto-generated method stub
	super.failed(name, statusCode, headers, response);
}
@Override
public void finish(String name) {
	// TODO Auto-generated method stub
	super.finish(name);
    cancelProgressDialog();
}
	

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
