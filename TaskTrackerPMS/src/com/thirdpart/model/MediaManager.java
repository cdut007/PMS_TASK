package com.thirdpart.model;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
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


	
	


	private static final int REQ_CODE_TAKE_PICTURE = 0x11;
	
	protected static final int REQUEST_CODE_FOR_SELECT_IMAGE = 0x12;


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

	protected ImageView photo;
	protected String picPath;
	private void updateImageUri(String path) {
			photo.setTag(path);
			photo.setImageBitmap(BitmapFactory.decodeFile(path));

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
