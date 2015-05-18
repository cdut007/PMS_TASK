package com.jameschen.framework.base;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jameschen.comm.utils.Log;
import com.thirdprt.tasktrackerpms.R;

/**This is the baseFragment is for root frament ,which childFragment must extends it
 * 
 * @author jameschen
 *
 */
public abstract class BaseFragment extends Fragment{
	public  String TAG;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TAG =getTag();
	}
	
	protected BaseActivity getBaseActivity() {
		return (BaseActivity) getActivity();
	}
 	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	Log.i(TAG, "onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);

	}
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG, "onActivityCreatedFagment");
		super.onActivityCreated(savedInstanceState);
	}
	 
	@Override
	public void onResume() {
		Log.i(TAG, "onResumeFagment");
		super.onResume();
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onDestroyView");
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroyFagment");
	}
	
	public void showToast(String string) {
		if (getActivity() == null) {
			Log.i(TAG, "activity is finished");
			return ;
		}
			((BaseActivity) getActivity()).showToast(string);
	}



	public void closeInputMethod() {
		if (getActivity() == null) {
			return ;
		}
		 ((BaseActivity) getActivity()).closeInputMethod();
	}
	@Override
	public void startActivity(Intent intent) {
		// TODO Auto-generated method stub
		super.startActivity(intent);
		getActivity().overridePendingTransition(R.anim.enter_right_anim,
				R.anim.exit_right_anim);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		// TODO Auto-generated method stub
		super.startActivityForResult(intent, requestCode);
		getActivity().overridePendingTransition(R.anim.enter_right_anim,
				R.anim.exit_right_anim);
	}

	
	
}
