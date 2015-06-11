
package com.thirdpart.tasktrackerpms.ui;


import com.jameschen.framework.base.BaseFragment;
import com.thirdpart.tasktrackerpms.R;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;


/**
 * This fragment will populate the children of the ViewPager from {@link ImageDetailActivity}.
 */
public class ImageDetailFragment extends BaseFragment {
    private static final String IMAGE_DATA_EXTRA = "extra_image_data";
    private String mImageUrl;
    private ImageView mImageView;
    /**
     * Factory method to generate a new instance of the fragment given an image number.
     *
     * @param imageUrl The image url to load
     * @return A new instance of ImageDetailFragment with imageNum extras
     */
    public static ImageDetailFragment newInstance(String imageUrl) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString(IMAGE_DATA_EXTRA, imageUrl);
        f.setArguments(args);

        return f;
    }

    /**
     * Empty constructor as per the Fragment documentation
     */
    public ImageDetailFragment() {}

    /**
     * Populate image using a url from extras, use the convenience factory method
     * {@link ImageDetailFragment#newInstance(String)} to create this fragment.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString(IMAGE_DATA_EXTRA) : null;
    }

    ProgressBar loadingBar ;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate and locate the main ImageView
        final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        mImageView = (ImageView) v.findViewById(R.id.imageView);
        loadingBar = (ProgressBar)v.findViewById(R.id.loading_img);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Use the parent activity to load the image asynchronously into the ImageView (so a single
        // cache can be used over all pages in the ViewPager
        if (ImageDetailActivity.class.isInstance(getActivity())) {
            
			
//            mImageLoader.displayImage(mImageUrl, mImageView, ((ImageDetailActivity) getActivity()).getImageOptions(),new ImageLoadingListener() {
//				
//				@Override
//				public void onLoadingStarted(String arg0, View arg1) {
//					loadingBar.setVisibility(View.VISIBLE);
//				}
//				
//				@Override
//				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
//					loadingBar.setVisibility(View.GONE);
//				}
//				
//				@Override
//				public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
//					loadingBar.setVisibility(View.GONE);
//				}
//				
//				@Override
//				public void onLoadingCancelled(String arg0, View arg1) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
        }

        // Pass clicks on the ImageView to the parent activity to handle
        if (OnClickListener.class.isInstance(getActivity()) ) {
            mImageView.setOnClickListener((OnClickListener) getActivity());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       
    }
}
