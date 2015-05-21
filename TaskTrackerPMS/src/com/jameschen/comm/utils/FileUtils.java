package com.jameschen.comm.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jameschen.comm.utils.MediaFile.MediaFileType;
import com.jameschen.framework.base.BaseActivity;
import com.thirdpart.tasktrackerpms.R;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

public class FileUtils {

	// 写在/mnt/sdcard/目录下面的文件

	private static String TAG = "FileUtils";

	/**
	 * @param fileName 
	 * @param message
	 */
	public static void writeFileSdcard(String fileName, String message) {

		try {

			FileOutputStream fout = new FileOutputStream(fileName);
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.flush();
			fout.close();
		}

		catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static long getMaxFileSendLimitedSize(Context context) {
		// TODO Auto-generated method stub
		long maxLen = Long.parseLong(context.getString(R.string.max_file_size));
		return maxLen;
		//
	}
	
	
	
	
	
	private static boolean checkSendfileSizeIsOk(final BaseActivity activity, long fileSize,boolean showToast) {
		// check file size
		final long maxImageSize = getMaxFileSendLimitedSize(activity);
		Log.i(TAG, "file size====" + (fileSize / (double) (1024 * 1024))
				+ ("maxfile===" + (maxImageSize / (double) (1024 * 1024))));
		if (maxImageSize <= fileSize) {
			if (showToast) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						activity.showToast(activity.getString(R.string.file_max_limit_info)
								+" "+ (maxImageSize / (double) (1024 * 1024))
								+ activity.getString(R.string.file_unit_m));
									
					}
				});	
			}
			
			return false;
		}else if (0 == fileSize) {
			if (showToast) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						activity.showToast(activity.getString(R.string.file_min_limit_info));	
					}
				});	
			}
			
			return false;
		
		}
		return true;
	}

	
	public static boolean checkSendfileSizeIsOk(Activity activity,
			File sendingFile,boolean showToast) {
		// check file size
		return checkSendfileSizeIsOk(activity, sendingFile.length(),showToast);
	}
	
	public static boolean checkSendfileSizeIsOk(Activity activity,
			File sendingFile) {
		// check file size
		//if file is video type and mp4  size around 20M~40M,we go video compress first.
		if (checkSendVideoFileSize(activity,sendingFile)) {
			
			Intent intent = new Intent(activity, ScanVideoActivity.class);
			intent.setAction(ScanVideoActivity.VIDEO_SCAN);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			intent.putExtra("filePath", sendingFile.getAbsolutePath());
			intent.putExtra("fileName", sendingFile.getName());
			intent.putExtra("extraType", "compress_video");
			activity.startActivityForResult(intent,);
			
			return false;
		}
		return checkSendfileSizeIsOk(activity, sendingFile, true);
	}

	/**file is video type and mp4  size around 20M~40M,we go video compress first.
	 * @param activity
	 * @param sendingFile
	 * @return
	 */
	private static boolean checkSendVideoFileSize(Activity activity,
			File sendingFile) {
	
		  MediaFileType type = MediaFile.getFileType(sendingFile.getAbsolutePath());
	        if(null != type) {
	        	//currently only consider the MP4
	            if (type.fileType == MediaFile.FILE_TYPE_MP4) {
					//round 20M ~ 50M
	            	if (sendingFile.length() >= 20*1024*1024 && sendingFile.length() <= 50*1024*1024) {
	            		return true;
					}
				}
	        }
		
		return false;
	}

	public static void showFileChooser(Activity instance, int requestCode) {

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

		intent.setType("video/*;image/*");

		intent.addCategory(Intent.CATEGORY_OPENABLE);

		try {

			instance.startActivityForResult(
					Intent.createChooser(intent,
							instance.getString(R.string.choose_file_title)),
					requestCode);

		} catch (android.content.ActivityNotFoundException ex) {
			// Potentially direct the user to the Market with a Dialog
		}
	}

	public static String getFileNameByDate() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
		return dateFormat.format(date);
	}
	
	
	public static String getVideoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'VIDEO'_yyyy-MM-dd-HHmmss");
		return dateFormat.format(date) + ".mp4";
	}
	
	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyy-MM-dd-HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	/**
	 * Get a file path from a Uri. This will get the the path for Storage Access
	 * Framework Documents, as well as the _data field for the MediaStore and
	 * other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @author paulburke
	 */
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= 19;// 4.4

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/"
							+ split[1];
				}

				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = MediaStore.MediaColumns.DATA;
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);

				return cursor.getString(column_index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	// get file absolute path.
	public static String Uri2Filename(Application application, Uri uriName) {
		String file = null;

		Log.i("fileURI", "uriName=" + uriName);
		file = getPath(application, uriName);
		// if (null != uriName) {
		// if (0 == uriName.getScheme().toString().compareTo("content")) {
		// Cursor c = application.getContentResolver().query(
		// uriName, null, null, null, null);
		// if (null != c && c.moveToFirst()) {
		// try {
		// int column_index = c
		// .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
		// file = c.getString(column_index);
		// } catch (IllegalArgumentException e) {
		// e.printStackTrace();
		// try {
		// int column_index = c
		// .getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA);
		// file = c.getString(column_index);
		// } catch (Exception e2) {
		// e2.printStackTrace();
		// Toast.makeText(application,application.getString(R.string.choose_image_file_error),
		// Toast.LENGTH_LONG).show();
		// }
		// }
		//
		// }
		// } else if (0 == uriName.getScheme().toString().compareTo("file")) {
		// file = uriName.toString().replace("file://", "");
		// }
		// }
		return file;
	}

	// 文件拷贝
	// 要复制的目录下的所有非子目录(文件夹)文件拷贝
	public static int CopySdcardFile(String fromFile, String toFile) {
		boolean status =copyfile(
				new File(fromFile), new File(toFile), true);
		if (!status) {
			return -1;
		}
		return 0;

	}
	private static void writeToOutFile(File toFile, FileInputStream fosfrom)
			throws IOException {
		FileOutputStream fosto = null;
		fosto = new FileOutputStream(toFile);
		byte bt[] = new byte[1024];
		int c;
		while ((c = fosfrom.read(bt)) > 0) {
			fosto.write(bt, 0, c);
		}
		fosto.close();

	}

	private static boolean copyfile(File fromFile, File toFile, boolean rewrite) {
		FileInputStream fosfrom;
		boolean result;
		if (!fromFile.exists() || !fromFile.isFile() || !fromFile.canRead())
			return false;
		boolean isSucceed = false;
		if (!toFile.getParentFile().exists()) {
			isSucceed = toFile.getParentFile().mkdirs();
			if (!isSucceed)
				Log.e(TAG, "copyfile mkdirs failed");
		}
		if (toFile.exists() && rewrite) {
			isSucceed = toFile.delete();
			if (!isSucceed)
				Log.e(TAG, "copyfile delete failed");
		}
		fosfrom = null;
		result = false;

		try {
			fosfrom = new FileInputStream(fromFile);
			writeToOutFile(toFile, fosfrom);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != fosfrom)
				try {
					fosfrom.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}

		result = true;

		return result;
	}

	public static String getFileName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('/');
			if (dot==-1) {
				dot = filename.lastIndexOf("\\");
			}
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(dot+1);
			}
		}
		return filename;
	}
	
	/*
	 * Java文件操作 获取不带扩展名的文件名
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	/**
	 * Return the extension portion of the file's name .
	 * 
	 * @see #getExtension
	 */
	public static String getExtension(File f) {
		return (f != null) ? getExtension(f.getName()) : "";
	}

	public static String getExtension(String filename) {
		return getExtension(filename, "");
	}
	
	public static String getExtension(String filename, String defExt) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > -1) && (i < (filename.length() - 1))) {
				return filename.substring(i + 1);
			}
		}
		return defExt;
	}

	public static boolean getSdcardAviable() {

		Boolean isSDPresent = android.os.Environment.getExternalStorageState()
				.equals(android.os.Environment.MEDIA_MOUNTED);

		return isSDPresent;
	}

	public static String trimExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');
			if ((i > -1) && (i < (filename.length()))) {
				return filename.substring(0, i);
			}
		}
		return filename;
	}

	
	
	
	public static void scanMediaFileToGallery(Context context, String path) {
		File file = new File(path);
		Uri uri = Uri.fromFile(file);
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
		context.sendBroadcast(intent);
	}

	public interface DownloadFileEndListener {
		void DownloadedFile(File file);
	}


	private static void saveBitmap2TempFile(File file, Bitmap bitmap) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 70 /* ignored for PNG */, bos);
		byte[] bitmapdata = bos.toByteArray();

		// write the bytes in file
		FileOutputStream fos = null;
		try {

			fos = new FileOutputStream(file);

			fos.write(bitmapdata);
			fos.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static boolean isFileExist(String fileName) {
		if (TextUtils.isEmpty(fileName)) {
			return false;
		}
		File file = new File(fileName);
		return file.exists();

	}

	public static void createFileDir(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	
	public static void createFileDirNoMedia(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		File targetFile=new File(dir+".nomedia");
		
	    if(!targetFile.exists()){
	    	try {
	    		File parent=targetFile.getParentFile();
		    	if (parent!=null&&!parent.exists()) {
		    		parent.mkdirs();
				}
		    	targetFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
		
	}

}
