package com.jameschen.framework.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jameschen.framework.base.Worker.MyWorkerable;

public abstract class MyBaseAdapter<T> extends BaseAdapter implements
		MyWorkerable {
	private Worker mObjectWorker;
	/**
	 * Lock used to modify the content of mObjects. Any write operation
	 * performed on the array should be synchronized on this lock. This lock is
	 * also used by the filter (see getFilter() to make a synchronized copy of
	 * the original array of data.
	 * */
	private final Object mLock = new Object();

	private ArrayList<T> mObjectInfos = new ArrayList<T>();

	public List<T> getObjectInfos() {
		return mObjectInfos;
	}

	protected boolean showDistance=true;
	
	public boolean isShowDistance() {
		return showDistance;
	}
	public void setShowDistance(boolean showDistance) {
		this.showDistance = showDistance;
	}

	
	boolean isRecycle = false;

	public void recycle() {
		isRecycle = true;
		// exit the activity.....
		mObjectInfos.clear();

	}
	

	
	public interface  ImageLoad{
		public static final int NONE=-1;
	}

	
	protected Context context;
	
	private  int layoutId=-1;
	
	
	/**
	 * @param context
	 * 
	 *            for ObjectmangeMent
	 */
	public MyBaseAdapter(Context context) {
		
		this(context, -1);
	}
 
	public MyBaseAdapter(Context context,int layoutId) {
			this.layoutId=layoutId;	
			this.context = context;
	}

    protected int defaultImageResId=ImageLoad.NONE;
	
	
	public MyBaseAdapter() {

	}

	
	public void onResume() {
		notifyDataSetChanged();
	}

	public void onPause() {

	}

	public void clear() {
		clear(false);
	}

	public void clear(boolean fresh) {
		synchronized (mLock) {
			mObjectInfos.clear();
		}
		if (fresh) {
			notifyDataSetInvalidated();
		}

	}

	/**
	 * remove the adapter index object from arraylist.
	 * 
	 * @param index
	 */
	public void remove(int index) {
		synchronized (mLock) {
			mObjectInfos.remove(index);
		}
	}

	public void remove(T object) {
		synchronized (mLock) {
			mObjectInfos.remove(object);
		}
	}

	public void addObject(T object) {
		synchronized (mLock) {
			mObjectInfos.add(object);
		}

	}

	
	  public void insert(T object, int index) {
	        synchronized (mLock) {
	        	mObjectInfos.add(index, object);
	        }
	    }
	  
	String searchStr = "";
	
    public String getSearchStr() {
		return searchStr;
	}
	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}
	
	boolean isSearchMode = false;
	
	public boolean isSearchMode() {
		return isSearchMode;
	}
	public void setSearchMode(boolean searchMode) {
		this.isSearchMode = searchMode;
		if (!searchMode) {
			setSearchStr("");
		}
	}
	public void setObjectList(List<T> mList,boolean search) {
			synchronized (mLock) {
				mObjectInfos = (ArrayList<T>) mList;
				setSearchMode(search);
			}

		}
	  
	  
	public void setObjectList(List<T> mList) {
		setObjectList(mList, false);

	}

	public void addObjectList(List<T> mList) {
		synchronized (mLock) {
			mObjectInfos.addAll(mList);
		}

	}

	@Override
	public int getCount() {
		synchronized (mLock) {
			return mObjectInfos.size();
		}
	}

	@Override
	public T getItem(int paramInt) {
		// TODO Auto-generated method stub
		synchronized (mLock) {
			return mObjectInfos.get(paramInt);
		}
	}

	public int getItemIndex(T item) {
		// TODO Auto-generated method stub
		synchronized (mLock) {
			return mObjectInfos.indexOf(item);
		}
	}
	@Override
	public long getItemId(int paramInt) {
		// TODO Auto-generated method stub
		return paramInt;
	}

	@Override
	public Worker getWorker() {
	
		return mObjectWorker;
	}

	protected  abstract HoldView<T> createHoldView();
     
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HoldView<T> item = null; // TODO Auto-generated method stub
		if (convertView == null) {
			item = createHoldView();
			//check the item is create
			if (item == null) {
				throw new NullPointerException("you must return a new holdView from the createHoldView method!");
			}
			//check the layoutId is ok
			if (-1 == layoutId) {
				throw new RuntimeException("you must define the layoutId from the xml !");
			}
			
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = (ViewGroup) inflater.inflate(layoutId,
					parent, false);
			
			item.initChildView(convertView,this);
			convertView.setTag(item);
		} else {
			item = (HoldView<T>) convertView.getTag();
		}

		T obj = getItem(position);
		item.setInfo(obj);
		
		return convertView;

	}


	/**
	 * Observer to get notified when the content observer's data load
	 * finished
	 */

	public abstract static class HoldView<T> {
    
		protected abstract void initChildView(View convertView,MyBaseAdapter<T> myBaseAdapter);

		protected abstract void setInfo(T object);
		

	}



}
