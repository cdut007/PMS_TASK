package com.jameschen.framework.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.protocol.HttpContext;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.jameschen.comm.utils.Log;
import com.jameschen.comm.utils.MediaFile;
import com.jameschen.comm.utils.MediaManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.thirdpart.model.LogInController;
import com.thirdpart.model.ConstValues.CategoryInfo.Cache;
import com.thirdpart.model.upload.UploadManager;
import com.thirdpart.tasktrackerpms.R;

public class BaseUploadActivity extends BaseActivity implements OnClickListener{
	private  String uploadimageCachePath=Cache.SCAN_IMAGE_CACHE_DIR;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_photo);
		setTopBarAllBtnVisiable(View.GONE);
		id =getIntent().getIntExtra("id", -1);
		Log.i(TAG, "id=="+id);
		
		initviews();
	}
	
	private Button upload;
	TextView photoText;
	protected int id;

	private boolean isSucc=false;
	// float money;
	private void initviews() {

		upload = (Button) findViewById(R.id.btn_upload);
		photo = (ImageView) findViewById(R.id.image_choose);
		photoText = (TextView) findViewById(R.id.photo_text);
		//
		
		upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				if (isStart) {
//					showToast("正在上传，请稍候...");
//					return;
//				}
				if (photo.getTag()==null) {
					//showToast("请选择一张照片");
					setResult(RESULT_OK);
					finish();
					return;
				}
				File file  = new File((String)photo.getTag());
				if (!file.exists()) {//file deleted?
					showToast("图片不存在");
					return;
				}
				
				
				 RequestParams params = new RequestParams();  
				 
				 params.put("method", "logo");
				 params.put("id", 0+"");
				 params.put("ssize", 200);
				 params.put("size", 500);
				 try {
					params.put("image_file", file);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			// Upload a File  
				 PersistentCookieStore myCookieStore = new PersistentCookieStore(BaseUploadActivity.this);
				 final AsyncHttpClient client =new AsyncHttpClient();
				 client.setCookieStore(myCookieStore);
				 if (LogInController.getHttpClientInstance()!=null) {
						client.setCookieStore(LogInController.getHttpClientInstance().getCookieStore());
				}
			
				// Map<String, File> fileMap = new HashMap()<String, File>();
//				 fileMap.put("image_file", file);
//				 HttpUtil.get("http://birdboy.cn/upload", params, fileMap);
				 
				client.post("http://birdboy.cn/upload", params, new AsyncHttpResponseHandler(){
					

					


					@Override
					@Deprecated
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						super.onSuccess(content);
						Log.i(TAG, "&&&&&"+content);
						readJsonData(content, uiController, null);
						  HttpContext httpContext = client.getHttpContext();
					        cookies = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
					}
					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1, arg2);
						isSucc  = false;
						isStart=false;
						showToast("上传图片成功");
						debugHeaders(TAG, arg1);
						//setResult(RESULT_OK);
						sendCreateOKIntent();
						//finish();
					}
					 @Override
					public void onProgress(int bytesWritten, int totalSize) {
						// TODO Auto-generated method stub
						super.onProgress(bytesWritten, totalSize);
						int percent = bytesWritten*100/totalSize;
						//photoText.setText("已上传"+percent+"%");
						if (percent==100) {//ok.
							isSucc=true;
						}
						showProgressDialog("上传图片", "已上传"+percent+"%", null);
					}
					 @Override
					public void onStart() {
						super.onStart();
						isStart=true;
						showProgressDialog("上传图片", "已上传"+0+"%", new OnCancelListener() {
							
							@Override
							public void onCancel(DialogInterface arg0) {
								// TODO Auto-generated method stub
								isStart =false;
							}
						});
						//photoText.setText("开始上传。。。");
					}
					
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						super.onFailure(arg0, arg1, arg2, arg3);
						if (isSucc) {
							isSucc=false;
							showToast("上传图片成功");
							debugHeaders(TAG, arg1);
							setResult(RESULT_OK);
							finish();
						}else {
							showToast("上传图片失败!");
						}
						
						
					}
					
				
					 @Override
					public void onFinish() {
						super.onFinish();

						cancelProgressDialog();
						isStart=false;
						//isSucc = false;
						if (isSucc) {
							showToast("上传图片成功");
							setResult(RESULT_OK);
							finish();
							isSucc=false;
						}
					}
				 
				});  
				 
				 
				 
			}
		});

		photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			showAlert();
			}
		});
		
	}

	
	private void sendCreateOKIntent() {
		
		onBackPressed();
		
	}
	

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {

		default:
			break;
		}
	}
	


	private static final int REQ_CODE_TAKE_PICTURE = 0x11;
	
	protected static final int REQUEST_CODE_FOR_SELECT_IMAGE = 0x12;
	
	
	
	
	

	


	public Dialog showAlert() {
		final Dialog dlg = new Dialog(this, R.style.MMTheme_DataSheet);
		LayoutInflater inflater = (LayoutInflater) this
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
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(intent, "Select content"),
						REQUEST_CODE_FOR_SELECT_IMAGE);
				dlg.dismiss();
			}
		});

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				takePhoto(imageCachePath);
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
		lp.x = 0;
		final int cMakeBottom = -1000;
		lp.y = cMakeBottom;
		lp.gravity = Gravity.BOTTOM;
		lp.height = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 160, this.getResources()
						.getDisplayMetrics());
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(true);

		dlg.setContentView(layout);
		dlg.show();

		return dlg;
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
				sendName = UploadManager.getPhotoFileName();
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
			startActivityForResult(intent, REQ_CODE_TAKE_PICTURE);
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == RESULT_OK) {
			
			switch (requestCode) {

			case REQ_CODE_TAKE_PICTURE:
				String picPathString = uploadimageCachePath + sendName;
				Log.i(TAG, "picPathString ==" + picPathString);
				MediaManager.compressImage(this, picPathString);
				updateImageUri(picPathString);
				break;

			case REQUEST_CODE_FOR_SELECT_IMAGE:
				Uri selectedImangeUri = intent.getData();
				if (selectedImangeUri == null) {
					Log.i(TAG, "getData is null");
					showToast(getString(R.string.error_select_photo));
					return;
				}

				String sendingFileName = null;
				sendingFileName = MediaManager.Uri2FilePath(this, selectedImangeUri);

				if (sendingFileName == null) {
					Log.i(TAG, "Uri2FilePath is null");
					showToast(getString(R.string.error_select_photo));
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
						showToast(getString(R.string.error_select_photo));
						return;
					}
				}
				// is image file??
				if (!MediaFile.isImageFileType(sendingFile.getAbsolutePath())) {
					Toast.makeText(this, "请选择图片文件!", Toast.LENGTH_LONG).show();
					return;
				}
			
				break;
			default:
				break;
			}

		}

	}

	String sendName = null;


	





}
