
package com.thirdpart.tasktrackerpms.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jameschen.framework.base.BaseFragment;
import com.jameschen.widget.image.gesture.GestureImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.thirdpart.tasktrackerpms.R;


/**
 * This fragment will populate the children of the ViewPager from {@link ImageDetailActivity}.
 */
public class ImageDetailFragment extends BaseFragment {
    private static final String IMAGE_DATA_EXTRA = "extra_image_data";
    private String mImageUrl;
    private ImageView mImageView;
	private ImageLoader mImageLoader;
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
    
   static class Photo{
    	Bitmap loadBitmap;
    	String url;
    }
	protected static Photo photo = new Photo();
	public static void saveImageToGallery(Context context, String url) {
		if (!url.equals(photo.url)) {
			Log.i("bmp", "url not the same..");
			return;
		}
		Bitmap bmp = photo.loadBitmap;
		if (bmp == null) {
			Log.i("bmp", "saveImageToGallery bmp is null");
		return;	
		}
	    // 首先保存图片
	    File appDir = new File(Environment.getExternalStorageDirectory(), "PMSTasker");
	    if (!appDir.exists()) {
	        appDir.mkdir();
	    }
	    String fileName = System.currentTimeMillis() + ".jpg";
	    File file = new File(appDir, fileName);
	    String path = file.getAbsolutePath();
	    try {
	        FileOutputStream fos = new FileOutputStream(file);
	        bmp.compress(CompressFormat.JPEG, 100, fos);
	        fos.flush();
	        fos.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
		}
	    
	    // 其次把文件插入到系统图库
	    try {
	        MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), fileName, null);
	        Toast.makeText(context, "保存成功!", Toast.LENGTH_SHORT).show();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    // 最后通知图库更新
	    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
	}
	
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
            mImageLoader = ((ImageDetailActivity) getActivity()).getImageLoader();
          
            
            mImageLoader.displayImage(mImageUrl, mImageView, ((ImageDetailActivity) getActivity()).getImageOptions(),new ImageLoadingListener() {
				
				@Override
				public void onLoadingStarted(String arg0, View arg1) {
					loadingBar.setVisibility(View.VISIBLE);
				}
				
				@Override
				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					loadingBar.setVisibility(View.GONE);
				}
				
				@Override
				public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
					loadingBar.setVisibility(View.GONE);
					GestureImageView gestureImageView = new GestureImageView(getActivity());
					gestureImageView.setLayoutParams(mImageView.getLayoutParams());
					ViewGroup viewGroup =(ViewGroup) mImageView.getParent();
					mImageView.setVisibility(View.GONE);
					gestureImageView.setImageBitmap(arg2);
					photo.loadBitmap = arg2;
					photo.url = mImageUrl;
					viewGroup.addView(gestureImageView);
				}
				
				@Override
				public void onLoadingCancelled(String arg0, View arg1) {
					// TODO Auto-generated method stub
					
				}
			});
        }

        // Pass clicks on the ImageView to the parent activity to handle
        if (OnClickListener.class.isInstance(getActivity()) ) {
            mImageView.setOnClickListener((OnClickListener) getActivity());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImageView != null) {
        	mImageLoader.cancelDisplayTask(mImageView);
            mImageView.setImageDrawable(null);
        }
    }

}
