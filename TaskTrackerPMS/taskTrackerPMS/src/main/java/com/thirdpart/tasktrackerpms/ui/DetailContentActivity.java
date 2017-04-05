package com.thirdpart.tasktrackerpms.ui;

import java.lang.reflect.Type;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.jameschen.framework.base.BaseActivity;
import com.thirdpart.tasktrackerpms.R;


public class DetailContentActivity extends BaseActivity{
	TextView contentText;
	private String content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		setTitle(intent.getStringExtra("title"));
		
		content = intent.getStringExtra("content");
		
		initViews();
		
	}
	
	
	
	
	private void initViews() {
		 contentText = (TextView) findViewById(R.id.detail_content);
		 if (TextUtils.isEmpty(content)) {
			return;
		}
		 contentText.setText(Html.fromHtml(content));
	}




	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.detail_content_ui);
	}

	
}
