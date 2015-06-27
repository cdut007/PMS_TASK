package com.thirdpart.tasktrackerpms.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.http.Header;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jameschen.comm.utils.Log;
import com.jameschen.comm.utils.MyHttpClient;
import com.jameschen.framework.base.BaseEditActivity;
import com.jameschen.widget.CustomSelectPopupWindow.Category;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.thirdpart.model.IssueManager;
import com.thirdpart.model.ManagerService.OnReqHttpCallbackListener;
import com.thirdpart.model.ManagerService.OnUploadReqHttpCallbackListener;
import com.thirdpart.model.MediaManager;
import com.thirdpart.model.Config.ReqHttpMethodPath;
import com.thirdpart.model.MediaManager.MediaChooseListener;
import com.thirdpart.model.TeamMemberManager;
import com.thirdpart.model.UploadFileManager;
import com.thirdpart.model.TeamMemberManager.LoadUsersListener;
import com.thirdpart.model.WidgetItemInfo;
import com.thirdpart.model.entity.IssueResult;
import com.thirdpart.model.entity.WorkStep;
import com.thirdpart.tasktrackerpms.R;
import com.thirdpart.widget.AddItemView;
import com.thirdpart.widget.AddItemView.AddItem;
import com.thirdpart.widget.ChooseItemView;
import com.thirdpart.widget.EditItemView;
import com.thirdpart.widget.UserInputItemView;

public class IssueFeedbackActivity extends BaseEditActivity implements
		OnUploadReqHttpCallbackListener {

	List<Category> mFiles = new ArrayList<Category>();

	List<Category> mGuanzhuList = new ArrayList<Category>();
	IssueManager sIssueManager;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		mediaManager.onActivityResult(requestCode, resultCode, data);
	}

	UserInputItemView issueDescView;
	EditItemView issueTopic;
	AddItemView addFile, addPerson;
	ChooseItemView solverMan;
	WorkStep workStep;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setTitle("问题反馈");
		workStep = (WorkStep) getIntent().getSerializableExtra("feedback");
		initInfo();
		bindView();
		sIssueManager = (IssueManager) IssueManager.getNewManagerService(this,
				IssueManager.class, this);
		teamMemberManager = new TeamMemberManager(this);
		mediaManager = new MediaManager(this);
	}

	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		getDeliveryList(false, solverMan);
	};

	TeamMemberManager teamMemberManager;
	MediaManager mediaManager;
	UploadFileManager uploadFileManager;

	private void bindView() {
		// TODO Auto-generated method stub

		findViewById(R.id.issue_feedback_container).addOnLayoutChangeListener(
				new OnLayoutChangeListener() {

					@Override
					public void onLayoutChange(View v, int left, int top,
							int right, int bottom, int oldLeft, int oldTop,
							int oldRight, int oldBottom) {
						// TODO Auto-generated method stub
						int addItem = bottom - oldBottom;
						if (addItem > 0 && oldBottom > 0) {
							ScrollView scrollView = (ScrollView) findViewById(R.id.container);
							Log.i(TAG, "deltaHeight=" + addItem + ";bottom="
									+ bottom + ";oldBottom=" + oldBottom);
							scrollView.scrollBy(0, addItem);
						}
					}
				});

		issueDescView = (UserInputItemView) findViewById(R.id.issue_desc);
		issueTopic = (EditItemView) findViewById(R.id.issue_topic);
		addFile = (AddItemView) findViewById(R.id.issue_add_file);
		solverMan = (ChooseItemView) findViewById(R.id.issue_choose_deliver);
		solverMan.setContent("选择解决人");
		addPerson = (AddItemView) findViewById(R.id.issue_add_person);
		solverMan.setChooseItemClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDeliveryList(true, solverMan);
			}
		});

		addFile.setOnCreateItemViewListener(addfileListenr);
		addPerson.setOnCreateItemViewListener(addfoucsPersonCreateListenr);

	}

	AddItemView.CreateItemViewListener addfileListenr = new AddItemView.CreateItemViewListener() {

		@Override
		public void oncreateItem(String tag, View convertView) {
			// TODO Auto-generated method stub
			mFiles.add(new Category(tag));// empty
		}

		@Override
		public void deleteItem(int index) {
			// TODO Auto-generated method stub
			mFiles.remove(index);
		}

		@Override
		public void chooseItem(int index, View convertView) {
			// TODO Auto-generated method stub
			chooseMedia(convertView);

		}

	};

	AddItemView.CreateItemViewListener addfoucsPersonCreateListenr = new AddItemView.CreateItemViewListener() {

		@Override
		public void oncreateItem(String tag, View convertView) {
			// TODO Auto-generated method stub
			mGuanzhuList.add(new Category(tag));// empty
		}

		@Override
		public void deleteItem(int index) {
			// TODO Auto-generated method stub
			mGuanzhuList.remove(index);
		}

		@Override
		public void chooseItem(int index, View convertView) {
			// TODO Auto-generated method stub
			getDeliveryList(true, convertView);
		}

	};

	IssueResult issueResult = new IssueResult();
	Category solverCategory;

	private void getDeliveryList(boolean showWindowView, final View view) {
		// TODO Auto-generated method stub
		// showLoadingView(true);
		teamMemberManager.findDepartmentInfos(showWindowView, view,
				new LoadUsersListener() {

					@Override
					public void onSelcted(Category mParent, Category category) {
						// TODO Auto-generated method stub
						if (view == solverMan) {
							solverCategory = category;
							solverMan.setContent(category.getName());
						} else {// check is foucs choose person
							ChooseItemView chooseItemView = (ChooseItemView) view
									.findViewById(R.id.common_add_item_title);

							for (Category mCategory : mGuanzhuList) {
								if (category.getId().equals(mCategory.getId())) {
									// modify do nothing.
									if (!category.getName().equals(
											chooseItemView.getContent())) {
										showToast("该关注人已经在列表了");// not in
																// current
																// chooseItem,but
																// other already
																// has this
																// name.
									}

									return;
								}
							}
							chooseItemView.setContent(category.getName());

							AddItem addItem = (AddItem) chooseItemView.getTag();
							// 关注人是否已经存在，就只更新
							for (Category mCategory : mGuanzhuList) {
								if (addItem.tag.equals(mCategory.tag)) {
									// modify .
									mCategory.setName(category.getName());
									mCategory.setId(category.getId());
									return;
								}
							}
							Log.i(TAG,
									"can not find the select item from fouc:");
						}

					}

					@Override
					public void loadEndSucc(int type) {
						// TODO Auto-generated method stub

					}

					@Override
					public void loadEndFailed(int type) {
						// TODO Auto-generated method stub

					}

					@Override
					public void beginLoad(int type) {
						// TODO Auto-generated method stub

					}
				});
	}

	protected void chooseMedia(final View convertView) {
		// TODO Auto-gener
		mediaManager.showMediaChooseDialog(new MediaChooseListener() {

			@Override
			public void chooseImage(int reqCodeTakePicture, String filePath) {
				String fileName = filePath;
				View chooseItemView = convertView
						.findViewById(R.id.common_add_item_title);
				View imgItemView = convertView.findViewById(R.id.img_container);

				TextView sTextView = (TextView) convertView
						.findViewById(R.id.common_add_item_content);

				AddItem addItem = (AddItem) chooseItemView.getTag();
				for (Category mCategory : mFiles) {
					if (fileName.equals(mCategory.getName())) {
						// modify do nothing.
						if (!fileName.equals(sTextView.getTag())) {
							showToast("该文件在列表了");// not in current
													// chooseItem,but other
													// already has this name.
						}

						return;
					}
				}
				imgItemView.setVisibility(View.VISIBLE);
				// load image
				ImageView prewImg = (ImageView) convertView
						.findViewById(R.id.common_prew_img);
				if (imageLoader == null) {
					imageLoader = ImageLoader.getInstance();
				}
				imageLoader.displayImage("file://" + filePath, prewImg, options);
				
				sTextView.setText(new File(fileName).getName());
				sTextView.setTag(fileName);

				// 衣衣对应 是否已经存在，就只更新
				for (Category mCategory : mFiles) {

					if (addItem.tag.equals(mCategory.tag)) {
						// modify .

						mCategory.setName(fileName);
						return;
					}
				}
				Log.i(TAG, "can not find the select item from file:");
			}
		});

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (imageLoader != null) {
			imageLoader.clearMemoryCache();
		}
	}

	private ImageLoader imageLoader;
	DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.ic_launcher)
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.showImageOnFail(R.drawable.ic_launcher)
			.considerExifParams(true).cacheInMemory(true).cacheOnDisc(true)
			.build();

	/**
	 * {"witnessdateaqc1":null,"operatedesc":"s","witnessdateaqc2":null,
	 * 
	 * "rollingPlan":{"technologyAsk":null,"remark":null,"qcsign":0,"consteam":
	 * "21", "consdate":1426348800000,"qualityRiskCtl":null,"welder":null,
	 * "updatedBy":null,
	 * "id":28,"assigndate":1432957400000,"drawno":"07060DY-JPS01-JPD-003"
	 * ,"qualitynum":1,
	 * "weldno":"M1","areano":"DY","updatedOn":null,"qcdate":null,"qcman":null,
	 * "qualityplanno"
	 * :"NZPT-TQP-0DY-JPD-P-20519","speciality":"GDHK","plandate":
	 * "2015-05-30至2015-05-31",
	 * "materialtype":"CS","workTool":null,"weldlistno":
	 * "HJKZ-TQP-20519-00518","isend":1,"experienceFeedback":null,
	 * "createdOn":1432957324000
	 * ,"createdBy":"admin","unitno":"0","planfinishdate"
	 * :1432915200000,"consendman":"22",
	 * "doissuedate":null,"enddate":null,"worktime"
	 * :1,"securityRiskCtl":null,"workpoint":0.325,"rccm":"NA"},
	 * 
	 * "operater":"dd","witnesseraqa":null,"updatedBy":"zhangxu","noticed":null,
	 * "witnesserb"
	 * :null,"id":61,"witnesserc":null,"witnesserd":null,"operatedate"
	 * :1432979974000,"noticeaqa":null,
	 * "noticeb":null,"noticec":null,"updatedOn"
	 * :1432979977000,"stepname":"坡口加工","witnessdateaqa":null,
	 * "stepflag":"DONE","noticeainfo":null,"stepno":1,"noticeresul
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * tdesc":null,"createdOn":1432957324000,"" +
	 * "createdBy":"admin","noticeresult"
	 * :null,"witnesseraqc2":null,"witnesseraqc1":null,"witnessdatec":null,
	 * "witnessdated"
	 * :null,"noticeaqc1":null,"noticeaqc2":null,"witnessdateb":null}
	 */

	/*
	 * params.put("workstepid", issue.getWorstepid()); params.put("workstepno",
	 * issue.getStepno()); params.put("stepname", issue.getStepname());
	 * params.put("questionname", issue.getQuestionname());
	 * params.put("describe", issue.getDescribe()); params.put("solverid",
	 * issue.getSolverid()); params.put("concernman", issue.getConcerman());
	 */
	@Override
	public void callCommitBtn(View v) {

		// TODO Auto-generated method stub
		if (TextUtils.isEmpty(issueTopic.getContent())) {
			showToast("请填写问题主题");
			return;
		}
		if (TextUtils.isEmpty(issueDescView.getContent())) {
			showToast("请填写问题描述");
			return;
		}
		if (solverCategory == null) {
			showToast("请选择解决人");
			return;
		}
		issueResult.setSolverid(solverCategory.getId());

		String concerman = getConcerMan();
		issueResult.setConcerman(concerman);
		List<File> mFiles = getFiles();

		issueResult.setQuestionname(issueTopic.getContent().toString());
		issueResult.setDescribe(issueDescView.getContent().toString());

		issueResult.setStepno(workStep.getStepno());
		issueResult.setStepname(workStep.getStepname());
		issueResult.setWorstepid(workStep.getId());
		sIssueManager.createIssue(issueResult, mFiles);
		super.callCommitBtn(v);
	}

	HashSet<File> getHashFileSet(List<File> mList) {
		HashSet<File> mHashSet = new HashSet<File>();
		for (File file : mList) {
			mHashSet.add(file);
		}
		return mHashSet;
	}

	private List<File> getFiles() {
		// TODO Auto-generated method stub
		List<File> mFileLists = new ArrayList<File>();
		for (Category category : mFiles) {
			if (category.getName() == null) {
				Log.i(TAG, "no file seleted=" + category.tag);
				continue;
			}
			mFileLists.add(new File(category.getName()));
		}
		return mFileLists;
	}

	private String getConcerMan() {
		// TODO Auto-generated method stub
		String concerman = "";
		if (mGuanzhuList.size()==1) {
			return mGuanzhuList.get(0).getId();
		}
		if (mGuanzhuList.size()>1) {
			for (int i = 0; i < mGuanzhuList.size(); i++) {
				Category category = mGuanzhuList.get(i);
				if (i==0) {
					concerman +=  category.getId() ;	
				}else {
					concerman += "|"+ category.getId();
				}
			}
		}
		
		return concerman;
	}

	@Override
	public void failed(String name, int statusCode, Header[] headers,
			String response) {
		// TODO Auto-generated method stub
		super.failed(name, statusCode, headers, response);

	}

	@Override
	public void succ(String name, int statusCode, Header[] headers,
			Object response) {
		// TODO Auto-generated method stub
		super.succ(name, statusCode, headers, response);
		showToast("提交成功");
		WorkStepFragment.CallSucc(WorkStepFragment.callsucc);
		setResult(RESULT_OK);
		finish();

	}

	private void initInfo() {

		final List<WidgetItemInfo> itemInfos = new ArrayList<WidgetItemInfo>();
		// R.id. in array String
		itemInfos.add(new WidgetItemInfo(null, null, null, 0, false));

		createItemListToUI(itemInfos, R.id.edit_container,
				new CreateItemViewListener() {

					@Override
					public View oncreateItem(int index, View convertView,
							ViewGroup viewgroup) {
						// TODO Auto-generated method stub
						// if exsit just update , otherwise create it.

						final WidgetItemInfo widgetItemInfo = itemInfos
								.get(index);
						if (convertView == null) {
							// create
							LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

							convertView = inflater.inflate(
									R.layout.issue_feedback_ui, viewgroup,
									false);

						} else {

						}

						// bind tag
						convertView.setTag(widgetItemInfo);
						return convertView;
					}
				}, false);

		// make container

	}

	@Override
	protected void initView() {
		setContentView(R.layout.edit_ui);// TODO Auto-generated method stub
		super.initView();

	}

	@Override
	public void onProgress(String name, int statusCode, int totalSize,
			String response) {
		// TODO Auto-generated method stub

		if (mFiles.size() > 0) {
			Log.i(TAG, "progress content = " + response);
			showProgressDialog("上传图片", response, null);
		}

	}

}
