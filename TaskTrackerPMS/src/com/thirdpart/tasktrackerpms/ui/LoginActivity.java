package com.thirdpart.tasktrackerpms.ui;

import org.apache.http.Header;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jameschen.comm.utils.Log;
import com.jameschen.comm.utils.NetworkUtil;
import com.jameschen.comm.utils.RegexUtils;
import com.jameschen.framework.base.BaseActivity;
import com.jameschen.framework.base.UINetworkHandler;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.okhttp.internal.Util;
import com.thirdpart.model.LogInController;
import com.thirdpart.model.entity.UserInfo;
import com.thirdpart.tasktrackerpms.R;

public class LoginActivity extends BaseActivity{

	private EditText accountInput;
	private EditText passwordInput;
	private Button loginBtn;
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
	protected void initView() {
		setContentView(R.layout.login);
		accountInput = (EditText) findViewById(R.id.login_account);
		passwordInput = (EditText) findViewById(R.id.login_password);
		loginBtn = (Button) findViewById(R.id.login_btn);
		forgetPassword = (TextView) findViewById(R.id.forget_password);
		initEvent();
	    fillAccount();
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Light_NoTitleBar);
		
		getSupportActionBar().hide();
	}

	

	private void go2main() {
		
		Intent intentEx = new Intent();
		intentEx.setClass(this, MainActivity.class);
		intentEx.setFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		overridePendingTransition(R.anim.welcome_begin_anim,
				R.anim.welcome_end_anim);
		startActivity(intentEx);
		finish();
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
              showToast(getString(R.string.error_password_regex));
            }


        } else {
            //todo: illegal ID
        	 showToast(getString(R.string.error_account_regex));
        }
		
	}

	
	
	
	
	boolean isLogining = false;

	private void executeLoginNetWorkRequest(final String id,final String password) {
		// TODO Auto-generated method stub
		if (isLogining) {
			return;
		}
	        getPMSManager().login(id, password, new UINetworkHandler<UserInfo>(this) {

	        
				@Override
				public void start() {
					// TODO Auto-generated method stub
					isLogining = true;
					closeInputMethod();
					showProgressDialog("登录", "正在登录...", null);
				}
				
				
				@Override
				public void callbackFailure(int statusCode, Header[] headers,
						String response) {
					if (isFinishing()) {
						return;
					}
					// TODO Auto-generated method stub
					 if (statusCode == -1002||
							 statusCode==-1001) {
							showToast(getString(R.string.error_password));
						}else {
							showToast(response);
						}
				}

				@Override
				public void callbackSuccess(int statusCode, Header[] headers,
						UserInfo response) {
					if (isFinishing()) {
						return;
					}
					getLogInController().registerPush(response.getId());
					// TODO Auto-generated method stub
					getLogInController().saveUserToPreference(LoginActivity.this, id, password,response);
					cancelProgressDialog();
					go2main();
				}


				@Override
				public void finish() {
					isLogining = false;
					if (isFinishing()) {
						return;
					}
					// TODO Auto-generated method stub
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
