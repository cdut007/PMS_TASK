package com.thirdpart.model.upload;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import com.jameschen.comm.utils.FileUtils;

public class UploadManager {

	public static String getPhotoFileName() {
		
		return FileUtils.getPhotoFileName();
	}
	
	private List<String> mFileList;

	public List<String> getFileList() {
		return mFileList;
	}

	public void setFileList(List<String> mFileList) {
		this.mFileList = mFileList;
	}

	public void uploadVideo() {
		// TODO Auto-generated method stub

	}

	public void uploadImage() {
		// TODO Auto-generated method stub

	}
}
