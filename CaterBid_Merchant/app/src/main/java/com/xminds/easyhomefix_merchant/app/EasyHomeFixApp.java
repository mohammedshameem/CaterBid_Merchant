package com.xminds.easyhomefix_merchant.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;

import com.xminds.easyhomefix_merchant.Holder.Quote;
import com.xminds.easyhomefix_merchant.Holder.jobNotifications;
import com.xminds.easyhomefix_merchant.activities.SplashScreenActivity;
public class EasyHomeFixApp extends Application {

	
	private static Context context;
	private static String notificationcount;
	private static List<String>ListIds=new ArrayList<String>();
	private static String categoryNameDefaultTab;
	private static String isfromnotificationChat;
	private static String  userHeader;
	private static String availabletabstatus;
	private static String yourfixtabstatus;
	//private static ArrayList<String>CategoryIds=new ArrayList<String>();
	private static List<jobNotifications> notificationlist = new ArrayList<jobNotifications>();
	private static String gcmRegId;

	

	public static String getNotificationcount() {
		return notificationcount;
	}

	public static void setNotificationcount(String notificationcount) {
		EasyHomeFixApp.notificationcount = notificationcount;
	}

	public static String getGcmRegId() {
		return gcmRegId;
	}

	public static void setGcmRegId(String gcmRegId) {
		EasyHomeFixApp.gcmRegId = gcmRegId;
	}

	public static String getCategoryNameDefaultTab() {
		return categoryNameDefaultTab;
	}
	private static String detailsTabStatus;
	
	@Override
	public void onCreate() {

		EasyHomeFixApp.context = getApplicationContext();
		
	}

	/**
	 * @return the context	private static String categoryNameDefaultTab;
	private static String detailsTabStatus;
	
	 */
	public static Context getContext() {
		return context;
	}

	
	
	public static String getIsfromnotificationChat() {
		return isfromnotificationChat;
	}

	public static void setIsfromnotificationChat(String isfromnotificationChat) {
		EasyHomeFixApp.isfromnotificationChat = isfromnotificationChat;
	}

	/**
	 * @param context
	 *  the context to set
	 */
	public static void setContext(Context context) {
		EasyHomeFixApp.context = context;
	}

	public static List<String> getListIds() {
		return ListIds;
	}

	public static void setListIds(List<String> listIds) {
		ListIds = listIds;
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

	public static String getUserHeader() {
		return userHeader;
	}

	public static void setUserHeader(String userHeader) {
		EasyHomeFixApp.userHeader = userHeader;
	}


	public static List<jobNotifications> getNotificationlist() {
		return notificationlist;
	}

	public static void setNotificationlist(List<jobNotifications> notificationlist) {
		EasyHomeFixApp.notificationlist = notificationlist;
	}

	public static String getAvailabletabstatus() {
		return availabletabstatus;
	}

	public static void setAvailabletabstatus(String availabletabstatus) {
		EasyHomeFixApp.availabletabstatus = availabletabstatus;
	}

	public static String getYourfixtabstatus() {
		return yourfixtabstatus;
	}

	public static void setYourfixtabstatus(String yourfixtabstatus) {
		EasyHomeFixApp.yourfixtabstatus = yourfixtabstatus;
	}
	
}
