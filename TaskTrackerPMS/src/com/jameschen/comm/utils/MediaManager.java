package com.jameschen.comm.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

public class MediaManager {

	// get file absolute path.
	public static String Uri2FilePath(Context context, Uri uri) {
		
		return FileUtils.getPath(context, uri);
	}

	public static void compressImage(Context context,
			String picPathString) {
		// TODO Auto-generated method stub
		return FileUtils.
	}

}
