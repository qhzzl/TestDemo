package com.zzl.mmm.application;

import android.app.Application;
import android.content.Context;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.zzl.mmm.db.DBUtil;

import org.xutils.BuildConfig;
import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		x.Ext.init(this);
		x.Ext.setDebug(true);//是否输出debug日志

		// 初始化NoHttp
		NoHttp.initialize(this);
		// 开启调试模式
		Logger.setDebug(true);
		Logger.setTag("NoHttpSample");

		DBUtil.getDbManager();
	}
}