package com.thirdpart.tasktrackerpms.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.widget.EditText;
import android.widget.TextView;

import com.jameschen.framework.base.BaseEditActivity;
import com.thirdpart.model.entity.IssueResult;

public class IssueFeedbackActivity extends BaseEditActivity{
	
	private EditText issueNameEditText;
	
	private EditText  issueDescriptionEditText;
	
	List<File> mFiles = new ArrayList<File>();
	
	IssueResult   issueResult = new IssueResult();
	
	

}
