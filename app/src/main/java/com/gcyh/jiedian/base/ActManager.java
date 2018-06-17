package com.gcyh.jiedian.base;

import android.app.Activity;
import android.content.Context;

import java.util.LinkedList;


/**
 * Activity Manager
 * @author caizhiguang
 */
public class ActManager {
	
	private static final String TAG = "ActManager";

	private LinkedList<Activity> activityStack;
	private static ActManager instance;

	private ActManager(){
		if (activityStack == null) {
			activityStack = new LinkedList<>();
		}
	}

	public static ActManager getAppManager() {
		if (instance == null) {
			instance = new ActManager();
		}
		return instance;
	}

	/**
	 * 压栈
	 */
	public void addActivity(Activity activity) {
		activityStack.push(activity);
	}

	/**
	 * 当前Activity
	 */
	public Activity currentActivity() {
		Activity activity = null;
		
		if(activityStack.size() > 0){
			activity = activityStack.getFirst();
		}
	
		return activity;
	}

	/**
	 * 判断是否包含这个activity
	 * @param cls
	 * @return
	 */
	public boolean containActivity(Class<?> cls){
		if(activityStack == null){
			return false;
		}

		for(Activity act : activityStack){
			if(act.getClass().equals(cls)){
				return true;
			}
		}

		return false;
	}

	/**
	 * 结束当前Activity
	 */
	public void finishActivity() {
		Activity activity = activityStack.getLast();
		if (null != activity)
			finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		int index = activityStack.indexOf(activity);
		if (index != -1) {
			activityStack.remove(index).finish();
		}
	}

	/**
	 * 依据类名退出
	 * 
	 * @param cls
	 *            Activity的类名
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		if (activityStack != null) {
			while (!activityStack.isEmpty()) {
				activityStack.remove(activityStack.size() - 1).finish();
			}
		}
	}
	
	/**
	 * 结束所有Activity，但保留最后一个
	 */
/*	public static void finishActivitiesAndKeepLastOne() {
		for (int i = 0, size = activityStack.size()-1; i < size; i++) {
			activityStack.get(0).finish();
			activityStack.remove(0);
		}
	}*/
	
	public void printActStack(){
		for (int i = 0; i < activityStack.size(); i++) {
			System.out.println(activityStack.get(i).getClass().getSimpleName());
		}
	}
	
	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
		} catch (Exception e) {
//			LogUtil.error(ActManager.class, e.getMessage());
		}
	}

}
