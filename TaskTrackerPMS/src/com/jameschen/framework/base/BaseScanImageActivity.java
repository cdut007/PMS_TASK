package com.jameschen.framework.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.facebook.drawee.view.SimpleDraweeView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import com.jameschen.comm.utils.FileUtils;
import com.jameschen.comm.utils.Log;
import com.thirdpart.model.Config;
import com.thirdpart.model.ConstValues.CategoryInfo.Cache;
import com.thirdpart.tasktrackerpms.R;

import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.LinearLayout.LayoutParams;

public class BaseScanImageActivity extends BaseActivity {

	private SimpleDraweeView view;
	private Bitmap imgBitmap;
	private  String imageCachePath=Cache.SCAN_IMAGE_CACHE_DIR;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_scan);
		setTitle(getString(R.string.image_scan));
		initView();
		initEvent();
	}


	private void initEvent() {

		FileUtils.createFileDir(imageCachePath);

		final String imageFile = getIntent().getStringExtra("filePath");
		Log.i(TAG, "image==" + imageFile);
		view.setImageURI(Uri.fromFile(new File(imageFile)));
		String action = getIntent().getAction();
		if (action != null && action.equals(IMAGE_SCAN_RESIZE)) {
			final String name = getIntent().getStringExtra("fileName");
			setTopBarRightBtnListener("确定", new OnClickListener() {
				
				@Override
				public void onClick(View v) {

					String path = imageCachePath + name;
					Log.i("ScanImageActivity", "store path==" + path);
					File file = new File(imageFile);
					if (file.exists()) {
						if (file.length() <= maxImgFileLen) {
							// no need compress.
							// no need compress.
							Bitmap bmp = BitmapFactory.decodeFile(imageFile);
							if (bmp==null) {
								return;
							}
							transImage(imageFile, path, bmp.getWidth(),
									bmp.getHeight(), 100,
									BaseScanImageActivity.this, bmp, false);
							Intent data = new Intent();
							data.putExtra("imagePath", path);
							setResult(RESULT_OK, data);
							finish();
							return;
						}
					}
					boolean imagetransStatus = transImage(imageFile, path,
							1024, 720, 60, BaseScanImageActivity.this, imgBitmap,
							false);
					if (imagetransStatus) {
						Intent data = new Intent();
						data.putExtra("imagePath", path);
						setResult(RESULT_OK, data);
						finish();
					} else {
						Toast.makeText(BaseScanImageActivity.this,"出现错误",
								Toast.LENGTH_SHORT).show();
					}

									
				}
			});
			// show send button
		} else {
		}
		
	}


	// 压缩保存图片
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	@Override
	protected void initView() {
		view = (SimpleDraweeView) findViewById(R.id.image);
		
	}
}
