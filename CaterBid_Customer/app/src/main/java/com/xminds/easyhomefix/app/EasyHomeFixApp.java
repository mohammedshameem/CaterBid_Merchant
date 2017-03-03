package com.xminds.easyhomefix.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.xminds.easyhomefix.Holder.JobCategories;
import com.xminds.easyhomefix.Holder.JobNotifications;
import com.xminds.easyhomefix.Holder.JobReviews;
import com.xminds.easyhomefix.activities.SplashScreenActivity;

public class EasyHomeFixApp extends Application {


	
	private static String count;
	
	private static String acceptquoteStatus="rejected";

	private static String rejectquoteStatus="false";
	

	public static String getRejectquoteStatus() {
		return rejectquoteStatus;
	}

	public static void setRejectquoteStatus(String rejectquoteStatus) {
		EasyHomeFixApp.rejectquoteStatus = rejectquoteStatus;
	}

	public static String getAcceptquoteStatus() {
		return acceptquoteStatus;
	}

	public static void setAcceptquoteStatus(String acceptquoteStatus) {
		EasyHomeFixApp.acceptquoteStatus = acceptquoteStatus;
	}

	public static String getCount() {
		return count;
	}

	public static void setCount(String count) {
		EasyHomeFixApp.count = count;
	}

	public static String getOngoingstatus() {
		return ongoingstatus;
	}

	public static void setOngoingstatus(String ongoingstatus) {
		EasyHomeFixApp.ongoingstatus = ongoingstatus;
	}

	public static List<String> getListIds() {
		return ListIds;
	}

	public static void setListIds(List<String> listIds) {
		ListIds = listIds;
	}

	public int getClickedPosJob() {
		return clickedPosJob;
	}

	public void setClickedPosJob(int clickedPosJob) {
		this.clickedPosJob = clickedPosJob;
	}

	private static Context context;
	private static List<String>ListIds=new ArrayList<String>();
	private static String categoryNameDefaultTab;
	private static String detailsTabStatus;
	private static List<JobCategories> jobsubcategories;
	private int clickedPosJob;
	private static  List<JobCategories> jobcategories;
	private static List<JobNotifications>jobNotifications;
	private static List<JobReviews>jobNotificationReview;
	private static String gcmRegId;
	private static String ongoingstatus;
	private static String notificationcount;
	@Override
	public void onCreate() {

		EasyHomeFixApp.context = getApplicationContext();
	        String android_id = Settings.Secure.getString(EasyHomeFixApp.context.getContentResolver(), Settings.Secure.ANDROID_ID);
	        Log.e("", "android unique device id"+android_id);
	}

	/**
	 * @return the context
	 */
	public static Context getContext() {
		return context;
	}

	/**
	 * @param context
	 *  the context to set
	 */
	
	
	
	public static void setContext(Context context) {
		EasyHomeFixApp.context = context;
	}

	public static List<JobCategories> getJobcategories() {
		return jobcategories;
	}

	public static void setJobcategories(List<JobCategories> jobcategories) {
		EasyHomeFixApp.jobcategories = jobcategories;
	}

	public static List<JobCategories> getJobsubcategories() {
		return jobsubcategories;
	}

	public static void setJobsubcategories(List<JobCategories> jobsubcategories) {
		EasyHomeFixApp.jobsubcategories = jobsubcategories;
	}


	public static String getCategoryNameDefaultTab() {
		return categoryNameDefaultTab;
	}

	public static void setCategoryNameDefaultTab(String categoryNameDefaultTab) {
		EasyHomeFixApp.categoryNameDefaultTab = categoryNameDefaultTab;
	}

	public static String getDetailsTabStatus() {
		return detailsTabStatus;
	}

	public static void setDetailsTabStatus(String detailsTabStatus) {
		EasyHomeFixApp.detailsTabStatus = detailsTabStatus;
	}


	public static List<JobNotifications> getJobNotifications() {
		return jobNotifications;
	}

	public static void setJobNotifications(List<JobNotifications> jobNotifications) {
		EasyHomeFixApp.jobNotifications = jobNotifications;
	}

	public static List<JobReviews> getJobNotificationReview() {
		return jobNotificationReview;
	}

	public static void setJobNotificationReview(List<JobReviews> jobNotificationReview) {
		EasyHomeFixApp.jobNotificationReview = jobNotificationReview;
	}


	public static String getGcmRegId() {
		return gcmRegId;
	}

	public static void setGcmRegId(String gcmRegId) {
		EasyHomeFixApp.gcmRegId = gcmRegId;
	}

	public static String getNotificationcount() {
		return notificationcount;
	}

	public static void setNotificationcount(String notificationcount) {
		EasyHomeFixApp.notificationcount = notificationcount;
	}
	


}
