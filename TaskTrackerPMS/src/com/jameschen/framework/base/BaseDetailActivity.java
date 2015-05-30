package com.jameschen.framework.base;

import java.util.List;

import org.apache.http.Header;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jameschen.comm.utils.UtilsUI;
import com.jameschen.framework.base.CommonCallBack.OnRetryLisnter;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.ManagerService.OnReqHttpCallbackListener;
import com.thirdpart.tasktrackerpms.R;

public class BaseDetailActivity extends BaseActivity implements
OnReqHttpCallbackListener{

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	private ProgressBar progressBar;
	protected void showLoadingView(boolean isShown){
		View customView = getSupportActionBar().getCustomView();
		ViewGroup topbar = (ViewGroup) customView.findViewById(R.id.top_bar);
		if (isShown) {
			if (progressBar!=null) {
				topbar.removeView(progressBar);
				progressBar = null;
			}
			 progressBar = new ProgressBar(this);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT, android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			params.addRule(RelativeLayout.CENTER_VERTICAL);
			params.rightMargin = UtilsUI.getPixByDPI(this, 10);
			params.topMargin = UtilsUI.getPixByDPI(this, 5);
			params.bottomMargin = UtilsUI.getPixByDPI(this, 5);
			
			topbar.addView(progressBar, params);  
		}else {
			if (progressBar!=null) {
				topbar.removeView(progressBar);
				progressBar = null;
			}
		}
	}
	
	
	protected void setLoadSucc() {
		showLoadingView(false);
		setTopBarRightBtnVisiable(View.GONE);
	}
	
	protected void showRetryView(final OnRetryLisnter retryLisnter) {
		// TODO Auto-generated method stub

		showLoadingView(false);
		setTopBarRightBtnListener("重试", new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				setTopBarRightBtnVisiable(View.GONE);
				retryLisnter.doRetry();
			}
		});
	}
	
	
	private View getChildViewByTag(ViewGroup viewGroup,String tag) {
		// TODO Auto-generated method stub
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View childView = viewGroup.getChildAt(i);
			WidgetItemInfo sInfo = (WidgetItemInfo) childView.getTag();
				if (sInfo!=null&&tag.equals(sInfo.tag)) {
					return childView;
				}
			
		}
		return null;
	}
	
	
	protected View getViewByWidget(WidgetItemInfo widgetItemInfo){
		ViewGroup viewGroup = (ViewGroup) findViewById(containerId);
		return getChildViewByTag(viewGroup, widgetItemInfo.tag);
	}
	private int containerId=0;
	protected <T extends WidgetItemInfo> void createItemListToUI(List<T> infos,int listId,CreateItemViewListener createItemViewListener,boolean itemLine) {
		containerId = listId;
		ViewGroup viewGroup = (ViewGroup) findViewById(listId);
		int size =infos.size();
		int len=0;
		if (size == 0) {
	
			return;//no more
		}
	//	viewGroup.removeViews(1, viewGroup.getChildCount()-1);

		len =size ;
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		for (int i = 0; i < len; i++) {
			View convertView = getChildViewByTag(viewGroup, infos.get(i).tag); 
			 boolean  isNotExsit = (convertView == null);
			 convertView = createItemViewListener.oncreateItem(i, convertView,viewGroup);
			if (!isNotExsit) {
				continue;
			}

			viewGroup.addView(convertView);
			
			 if (itemLine && i<len-1) {
				 
				 View line =(View) inflater.inflate(R.layout.item_line, viewGroup, false);
				//int padding = UtilsUI.getPixByDPI(this, 10);
				 LayoutParams param = line.getLayoutParams();
				// param.width = UtilsUI.getWidth(getApplication()) - 2*padding;
				 viewGroup.addView(line,param);
			 }
		}
	
	}

public static interface CreateItemViewListener{
	View oncreateItem(int index,View convertView ,ViewGroup viewGroup);
}

@Override
public void start(String name) {
	// TODO Auto-generated method stub
	
}


@Override
public void failed(String name, int statusCode, Header[] headers,
		String response) {
	// TODO Auto-generated method stub
	showToast(response);
}


@Override
public void finish(String name) {
	// TODO Auto-generated method stub
	
}


@Override
public void succ(String name, int statusCode, Header[] headers, Object response) {
	// TODO Auto-generated method stub
	
}	
	
}
