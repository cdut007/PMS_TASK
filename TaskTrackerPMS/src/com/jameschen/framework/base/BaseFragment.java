package com.jameschen.framework.base;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jameschen.comm.utils.Log;
import com.thirdpart.model.EventCallbackListener;
import com.thirdpart.model.LogInController;
import com.thirdpart.model.PMSManagerAPI;
import com.thirdpart.tasktrackerpms.R;

/**This is the baseFragment is for root frament ,which childFragment must extends it
 * 
 * @author jameschen
 *
 */
public abstract class BaseFragment extends Fragment{
	public  String TAG;

	static List<EventCallbackListener> succListeners = new ArrayList<EventCallbackListener>();
	
	private String callbackTag;
	protected void registerCallBack(EventCallbackListener eventCallbackListener) {
		// TODO Auto-generated method stub
		this.callbackTag = eventCallbackListener.getTag();
		
		succListeners.add(eventCallbackListener);
		
	}
	
	public static void CallSucc(String callbackTag) {
		
		EventCallbackListener eventCallbackListener = getCallback(callbackTag);
		if (eventCallbackListener!=null) {
			eventCallbackListener.commitSucc();
		}
		return ;
		// TODO Auto-generated method stub
		
	}
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
	
	
	public static EventCallbackListener getCallback(String callbackTag) {
		for (EventCallbackListener eventCallbackListener : succListeners) {
			if (callbackTag!=null&&callbackTag.equals(eventCallbackListener.getTag())) {
				return eventCallbackListener;
			}
		}
		return null;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		 succListeners.remove(getCallback(callbackTag));
		
		
		Log.i(TAG, "onDestroyFagment");
	}
	
	public void showToast(String string) {
		if (getActivity() == null) {
			Log.i(TAG, "activity is finished");
			return ;
		}
			(getBaseActivity()).showToast(string);
	}

	protected boolean isActiviyFinishing() {
		// TODO Auto-generated method stub
       return  getBaseActivity().isFinishing();
	}
	
	public LogInController getLogInController(){
		return getBaseActivity().getLogInController();
	}
	
	public PMSManagerAPI getPMSManager(){
	
			return (getBaseActivity()).getPMSManager();
	}

	public void closeInputMethod() {
		if (getActivity() == null) {
			return ;
		}
		 (getBaseActivity()).closeInputMethod();
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
