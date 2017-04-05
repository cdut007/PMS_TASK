package com.thirdpart.tasktrackerpms.ui;


import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.apache.log4j.Level;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import cn.jpush.android.api.JPushInterface;

import com.jameschen.comm.utils.Log;
import com.jameschen.comm.utils.OttoBusHelper;
import com.jameschen.comm.utils.StorageUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.otto.Bus;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.thirdpart.model.TestReq;
import com.thirdpart.tasktrackerpms.R;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Created by yongjun on 05/16/2015.
 */
@ReportsCrashes(mailTo = "316458704@qq.com", customReportContent = {
		ReportField.APP_VERSION_NAME, ReportField.APP_VERSION_CODE,
		ReportField.APPLICATION_LOG, ReportField.ANDROID_VERSION,
		ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA,
		ReportField.STACK_TRACE, ReportField.LOGCAT }, mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text,formKey = "", logcatArguments = {
		"-t", "3000", "-v", "long", "ActivityManager:I", "MyApp:D", "*:S" })
public class MyApplication extends Application {

	private static final String TAG = "Mypplication";
	private Bus mBus = OttoBusHelper.getBus(this);
	private static Application mApplication;
	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;
		ACRA.init(this);
		initializeImageLoader(this);
		initLogConfig();
		mBus.register(this); // listen for "global" events
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        TestReq.init(this);
        initUpgrade(this);
	}

	
	private void initUpgrade(MyApplication myApplication) {
	    /**** Beta高级设置*****/
        /**
         * true表示app启动自动初始化升级模块；
         * false不好自动初始化
         * 开发者如果担心sdk初始化影响app启动速度，可以设置为false
         * 在后面某个时刻手动调用
         */
        Beta.autoInit = true;

        /**
         * true表示初始化时自动检查升级
         * false表示不会自动检查升级，需要手动调用Beta.checkUpgrade()方法
         */
        Beta.autoCheckUpgrade = true;

        /**
         * 设置升级周期为60s（默认检查周期为0s），60s内SDK不重复向后天请求策略
         */
        Beta.initDelay = 1 * 1000;

        /**
         * 设置通知栏大图标，largeIconId为项目中的图片资源；
         */
        Beta.largeIconId = R.drawable.logo;

        /**
         * 设置状态栏小图标，smallIconId为项目中的图片资源id;
         */
        Beta.smallIconId = R.drawable.logo;


        /**
         * 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
         * 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
         */
        Beta.defaultBannerId = R.drawable.logo;

        /**
         * 设置sd卡的Download为更新资源保存目录;
         * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
         */
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        /**
         * 点击过确认的弹窗在APP下次启动自动检查更新时会再次显示;
         */
        Beta.showInterruptedStrategy = true;

        /**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗;
         * 不设置会默认所有activity都可以显示弹窗;
         */
        Beta.canShowUpgradeActs.add(MainActivity.class);

        /**
         * 已经接入Bugly用户改用上面的初始化方法,不影响原有的crash上报功能;
         * init方法会自动检测更新，不需要再手动调用Beta.checkUpdate(),如需增加自动检查时机可以使用Beta.checkUpdate(false,false);
         * 参数1： applicationContext
         * 参数2：appId
         * 参数3：是否开启debug
         */
        Bugly.init(getApplicationContext(),"8627b7cc8e", true);
		
	}


	private void initializeImageLoader(MyApplication myApplication) {
		// TODO Auto-generated method stub
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(myApplication).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove
																					// for
																					// release
																					// app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}


	public void fulshLog() {
		// TODO Auto-generated method stub
		if (logConfigurator!=null) {
			logConfigurator.setImmediateFlush(true);
			logConfigurator.setImmediateFlush(false);
			logConfigurator.configure();
		}
	}
	
	LogConfigurator logConfigurator ;
	private void initLogConfig() {
		// TODO Auto-generated method stub
		// Create log Configurator
		 logConfigurator = new LogConfigurator();
		// Setting append log coudn't cover by a new log.
		logConfigurator.setUseFileAppender(true);
		// Define a file path for output log.
		String filename = StorageUtils.getLogFile();
		Log.i("info", filename);
		// Setting log output
		logConfigurator.setFileName(filename);
		// Setting log's level
		logConfigurator.setRootLevel(Level.DEBUG);
		logConfigurator.setLevel("org.apache", Level.ERROR);
		logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
		logConfigurator.setMaxFileSize(1024 * 1024 * 5);
		// Set up to use the cache first and then output to a file for a period
		// of time
		logConfigurator.setImmediateFlush(false);
		logConfigurator.setUseLogCatAppender(true);
		// logConfigurator.setResetConfiguration(true);
		logConfigurator.configure();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		mApplication=null;
		mBus.unregister(this);
	}

	
	static final String product_url = "http://106.37.241.216:4445/easycms-website";

	static final String product_url_five_company = "http://106.37.241.216:5555/easycms-website";

	static final String stage_url = "http://106.37.241.216:5555/easycms-website";

	
	public static String getBaseUrl() {
		// TODO Auto-generated method stub
		if (mApplication == null ) {
			return product_url_five_company;
		}
		SharedPreferences sharedPreferences = mApplication.getSharedPreferences("staging",Context.MODE_PRIVATE);
		if (sharedPreferences.getBoolean("staging", false)) {
			return sharedPreferences.getString("staging_url", stage_url);
		}
		
		return product_url_five_company;
	
	}

}