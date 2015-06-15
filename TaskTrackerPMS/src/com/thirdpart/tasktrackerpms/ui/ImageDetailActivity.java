

package com.thirdpart.tasktrackerpms.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.jameschen.comm.utils.Log;
import com.jameschen.framework.base.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.thirdpart.tasktrackerpms.R;

public class ImageDetailActivity extends BaseActivity implements OnClickListener {
    public static final String EXTRA_IMAGE = "extra_image";

    private ImagePagerAdapter mAdapter;
    private ViewPager mPager;
    private List<String> phoneList = new ArrayList<String>();

	private DisplayImageOptions options;
	
	protected ImageLoader imageLoader;

	public ImageLoader getImageLoader() {
		if (imageLoader == null) {
			imageLoader = ImageLoader.getInstance();
		}
		return imageLoader;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
      
        super.onCreate(savedInstanceState);
        phoneList = getIntent().getStringArrayListExtra("photos");
        //int position =getIntent().getIntExtra("position", 0);
        Log.i(TAG, "phoneList  size = "+phoneList.size());
        // Set up ViewPager and backing adapter
        mAdapter = new ImagePagerAdapter(getSupportFragmentManager(),phoneList);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setPageMargin((int) getResources().getDimension(R.dimen.image_detail_pager_margin));
        mPager.setOffscreenPageLimit(2);
        setTitle("照片"+phoneList.size()+"张");
        options = new DisplayImageOptions.Builder()
   		.showImageOnLoading(R.drawable.logo).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
   		.showImageForEmptyUri(R.drawable.logo)
   		.showImageOnFail(R.drawable.logo).considerExifParams(true).cacheInMemory(true) 
   		.cacheOnDisc(true).build();
        // Set the current item based on the extra passed in to this activity
        final int extraCurrentItem = getIntent().getIntExtra(EXTRA_IMAGE, -1);
        if (extraCurrentItem != -1) {
            mPager.setCurrentItem(extraCurrentItem);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * The main adapter that backs the ViewPager. A subclass of FragmentStatePagerAdapter as there
     * could be a large number of items in the ViewPager and we don't want to retain them all in
     * memory at once but create/destroy them on the fly.
     */
    private class ImagePagerAdapter extends FragmentStatePagerAdapter {
        private final int mSize;
        private List<String> mList = new ArrayList<String>();
        public ImagePagerAdapter(FragmentManager fm, List<String>photos) {
            super(fm);
            mSize = photos.size();
            mList =photos;
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public Fragment getItem(int position) {
       
            return ImageDetailFragment.newInstance(mList.get(position));
        }
    }

    /**
     * Set on the ImageView in the ViewPager children fragments, to enable/disable low profile mode
     * when the ImageView is touched.
     */
    @TargetApi(VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
    	super.onClick(v);
    	
//        final int vis = mPager.getSystemUiVisibility();
//        if ((vis & View.SYSTEM_UI_FLAG_LOW_PROFILE) != 0) {
//            mPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//        } else {
//            mPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
//        }
   }

	public DisplayImageOptions getImageOptions() {
		// TODO Auto-generated method stub
		return options;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.image_detail_pager);
	}
}
