package com.jameschen.framework.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

public class BaseImageActivity extends BaseActivity {

	private ImageView view;
	private Bitmap imgBitmap;
	private  String imageCachePath=Cache.SCAN_IMAGE_CACHE_DIR;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}



	// 压缩保存图片
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	@Override
	protected void initView() {
		view = (ImageView) findViewById(R.id.image);
		
	}
}
