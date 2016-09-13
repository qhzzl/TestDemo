package com.zzl.mmm.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import org.xutils.x;

/**
 * 基础Activity 公共的方法属性
 * @author Administrator
 */
@SuppressLint("Registered") 
public class BaseActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.d("BaseActivity", getClass().getSimpleName());

		ActivityCollector.addActivity(this);
		x.view().inject(this);
		//PushAgent.getInstance(this).onAppStart();
	}

	/**
	 5.0以上
	 全屏模式:

	 Window window = activity.getWindow();
	 //设置透明状态栏,这样才能让 ContentView 向上
	 window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

	 //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
	 window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
	 //设置状态栏颜色
	 window.setStatusBarColor(statusColor);

	 ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
	 View mChildView = mContentView.getChildAt(0);
	 if (mChildView != null) {
	 //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
	 ViewCompat.setFitsSystemWindows(mChildView, false);
	 }
	 着色模式:

	 Window window = activity.getWindow();
	 //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
	 window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

	 //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
	 window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
	 //设置状态栏颜色
	 window.setStatusBarColor(statusColor);

	 ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
	 View mChildView = mContentView.getChildAt(0);
	 if (mChildView != null) {
	 //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
	 ViewCompat.setFitsSystemWindows(mChildView, true);
	 }

	 */
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
	
}
