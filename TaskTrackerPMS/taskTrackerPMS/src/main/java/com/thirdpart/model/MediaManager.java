package com.thirdpart.model;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jameschen.comm.utils.FileUtils;
import com.jameschen.comm.utils.Log;
import com.jameschen.comm.utils.MediaFile;
import com.jameschen.framework.base.BaseActivity;
import com.thirdpart.model.ConstValues.CategoryInfo.Cache;
import com.thirdpart.tasktrackerpms.R;

/**
 * for choose file & upload file
 * */
public class MediaManager {
	
BaseActivity context;
public static interface MediaChooseListener{

	void chooseImage(int reqCodeTakePicture, String filePath);
	
}

MediaChooseListener mediaChooseListener ;
public Dialog showMediaChooseDialog(MediaChooseListener mediaChooseListener) {
	this.mediaChooseListener = mediaChooseListener;
	final Dialog dlg = new Dialog(context, R.style.MMTheme_DataSheet);
	LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	LinearLayout layout = (LinearLayout) inflater.inflate(
			R.layout.choose_image_dialog_layout, null);
	final int cFullFillWidth = 10000;
	layout.setMinimumWidth(cFullFillWidth);

	// only 3 button .
	TextView btn0 = (TextView) layout
			.findViewById(R.id.menu_dialog_take_by_camera);
	TextView btn1 = (TextView) layout
			.findViewById(R.id.menu_dialog_choose_from_exist);
	TextView btn2 = (TextView) layout
			.findViewById(R.id.menu_dialog_cancel);
	btn0.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");
			context.startActivityForResult(
					Intent.createChooser(intent, "Select content"),
					REQUEST_CODE_FOR_SELECT_IMAGE);
			dlg.dismiss();
		}
	});

	btn1.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			takePhoto(uploadimageCachePath);
			dlg.dismiss();
		}
	});
	btn2.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			dlg.dismiss();
		}
	});
	// set a large value put it in bottom
	Window w = dlg.getWindow();
	WindowManager.LayoutParams lp = w.getAttributes();
//	lp.x = 0;
//	final int cMakeBottom = -1000;
//	lp.y = cMakeBottom;
//	lp.gravity = Gravity.BOTTOM;
	lp.height = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP, 160, context.getResources()
					.getDisplayMetrics());
	dlg.onWindowAttributesChanged(lp);
	dlg.setCanceledOnTouchOutside(true);

	dlg.setContentView(layout);
	dlg.show();

	return dlg;
}


	public MediaManager(BaseActivity baseActivity) {
	// TODO Auto-generated constructor stub
		context = baseActivity;
}

	// get file absolute path.
	public static String Uri2FilePath(Context context, Uri uri) {
		
		return FileUtils.getPath(context, uri);
	}

	public static void compressImage(Context context,
			String picPathString) {
		// TODO Auto-generated method stub
		return ;
	}


	private  String uploadimageCachePath=Cache.SCAN_IMAGE_CACHE_DIR;

	
	protected int id;


	
	


	public static final int REQ_CODE_TAKE_PICTURE = 0x11;
	
	public static final int REQUEST_CODE_FOR_SELECT_IMAGE = 0x12;


	private static final String TAG = "mediaManager";
	
	
	
	
	public void chooseMedia(){
		
	}

	
	
	public String takePhoto(String path) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			String directoryPath = path;
			com.jameschen.comm.utils.FileUtils.createFileDir(directoryPath);
			
			File mPhotoFile = null;
			try {
				// define sending file name
				sendName = UploadFileManager.getPhotoFileName();
				Log.i(TAG, "sendName==="+sendName);
				String mPhotoPath = directoryPath + "/" + sendName;
				mPhotoFile = new File(mPhotoPath);
				if (!mPhotoFile.exists()) {
					mPhotoFile.createNewFile();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			Log.i(TAG, "sendName=after=="+sendName);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
			context.startActivityForResult(intent, REQ_CODE_TAKE_PICTURE);
			return sendName;
		} else {

		}
		return null;
	}

	protected String picPath;
	private void updateImageUri(String path) {

      }
	
	public void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK) {
			
			switch (requestCode) {

			case REQ_CODE_TAKE_PICTURE:
				String picPathString = uploadimageCachePath + sendName;
				Log.i(TAG, "picPathString ==" + picPathString);
				MediaManager.compressImage(context, picPathString);
				updateImageUri(picPathString);
				mediaChooseListener.chooseImage(REQ_CODE_TAKE_PICTURE,picPathString);
				break;

			case REQUEST_CODE_FOR_SELECT_IMAGE:
				Uri selectedImangeUri = intent.getData();
				if (selectedImangeUri == null) {
					Log.i(TAG, "getData is null");
					showToast(context.getString(R.string.error_select_photo));
					return;
				}

				String sendingFileName = null;
				sendingFileName = MediaManager.Uri2FilePath(context, selectedImangeUri);

				if (sendingFileName == null) {
					Log.i(TAG, "Uri2FilePath is null");
					showToast(context.getString(R.string.error_select_photo));
					return;
				}
				// android bug on some device
				File sendingFile = new File(sendingFileName);
				Log.i(TAG, "file path&&&&&&" + sendingFile);
				if (!sendingFile.exists()) {// maybe need to decode ..
					try {
						sendingFileName = URLDecoder.decode(sendingFileName,
								"utf-8");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					sendingFile = new File(sendingFileName);
					// double check file is exist or not
					if (!sendingFile.exists()) {
						showToast(context.getString(R.string.error_select_photo));
						return;
					}
				}
				// is image file??
				if (!MediaFile.isImageFileType(sendingFile.getAbsolutePath())) {
					showToast("请选择图片文件!");
					return;
				}
				mediaChooseListener.chooseImage(REQUEST_CODE_FOR_SELECT_IMAGE,sendingFile.getAbsolutePath());
				break;
			default:
				break;
			}

		}

	}

	private void showToast(String content) {
		// TODO Auto-generated method stub
		context.showToast(content);

	}


	String sendName = null;
	


	





}
