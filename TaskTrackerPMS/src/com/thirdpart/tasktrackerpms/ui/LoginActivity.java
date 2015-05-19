package com.thirdpart.tasktrackerpms.ui;

import org.apache.http.Header;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.jameschen.comm.utils.Log;
import com.jameschen.comm.utils.RegexUtils;
import com.jameschen.framework.base.BaseActivity;
import com.jameschen.framework.base.MyAsyncHttpResponseHandler;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.okhttp.internal.Util;
import com.thirdpart.model.LogInController;
import com.thirdpart.model.entity.UserInfo;
import com.thirdprt.tasktrackerpms.R;

public class LoginActivity extends BaseActivity{

	@InjectView(R.id.login_account)
	private EditText accountInput;
	@InjectView(R.id.login_password)
	private EditText passwordInput;
	@InjectView(R.id.login_btn)
	private Button loginBtn;
	@InjectView(R.id.forget_password)
	private TextView forgetPassword;

	
	

	protected void resetAccountInfo() {
		if (accountInput!=null) {
			accountInput.setText("");
		}
		
		if (passwordInput!=null) {
			passwordInput.setText("");
		}
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Light_NoTitleBar);
		getSupportActionBar().hide();
		setContentView(R.layout.login);
		ButterKnife.inject(this);
	}

	



	public void initEvent() {
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loginInAsyncProcess();
			}
		});
		
		forgetPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
			}
		});
		
	}

	private void loginInAsyncProcess() {
		 //check and process to login

        String id = accountInput.getText().toString();
        String password = passwordInput.getText().toString();
        Log.i(TAG,"userid ==" + id + "; password ==" + password);
        if (RegexUtils.isIdOk(id)) {

            if (RegexUtils.isPasswordOk(password)) {
              executeLoginNetWorkRequest(id,password);

            } else {
                //todo: illegal password
                showToast(mContext, getString(R.string.toast_password_illegal), Style.ALERT);
            }


        } else {
            //todo: illegal ID
            showToast(mContext, getString(R.string.toast_callname_illegal), Style.ALERT);
        }
		
	}

	private void executeLoginNetWorkRequest(String id, String password) {
		// TODO Auto-generated method stub
		if (!NetworkUtil.isInternetAvailable(this)) {

            showToast(mContext, R.string.toast_network_error, Style.ALERT);
            return;
        }
		
		  showProgressDialog(pTitle, pMessage, pCancelClickListener);
		 
	        getPMSManager().login(id, password, new MyAsyncHttpResponseHandler<UserInfo>() {

				@Override
				public void onFail(int statusCode, Header[] headers,
						String response) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSucc(int statusCode, Header[] headers, UserInfo response) {
					// TODO Auto-generated method stub
					//do something
				}
			 
				@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			   cancelProgressDialog();
			 }
	        
	        });
		
	}

	private void fillAccount() {
		String[] accounts = getLogInController().readAccountDataFromPreference();
		if (accounts[0] != null) {
			accountInput.setText(accounts[0]);
		}
	}
	
	

	

	@Override
	public void onResume() {
		super.onResume();
	
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

	}


}
