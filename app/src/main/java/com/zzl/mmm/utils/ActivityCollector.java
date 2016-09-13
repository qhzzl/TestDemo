package com.zzl.mmm.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity管理类
 * @author Administrator
 */
public class ActivityCollector {
	
	public static List<Activity> activities = new ArrayList<Activity>();

	public static void addActivity(Activity activity) {
		if (!activities.contains(activity)) {
			activities.add(activity);
		}
	}

	public static void removeActivity(Activity activity) {
		activities.remove(activity);
	}

	public static void finishAll() {
		for (Activity activity : activities) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
		System.exit(0);
	}
	
	public static void finishAllExcept(Activity lifeactivity) {
		for (Activity activity : activities) {
			if (activity.equals(lifeactivity)) {
				continue;
			}
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}

}
