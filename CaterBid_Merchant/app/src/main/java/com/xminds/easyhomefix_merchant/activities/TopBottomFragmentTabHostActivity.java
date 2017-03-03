package com.xminds.easyhomefix_merchant.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.xminds.easyfix_merchant.actionbar.BaseActivity;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.jobNotifications;
import com.xminds.easyhomefix_merchant.Manager.NotificationManager;
import com.xminds.easyhomefix_merchant.activities.SplashScreenActivity.NotificationCallBack;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.fragment.TopBottomTabHostFragment;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;

public class TopBottomFragmentTabHostActivity extends BaseActivity {

	private Fragment mContent;
	private ImageView image_back;
	private TextView textview_trackyourfix;
	private ArrayList<String> category_ID = new ArrayList<String>();
	private LogoutReceiver logoutReceiver;
	private int limit = 10;
	private int requestcode = 0;
	private int offset = 0;
	private List<jobNotifications> notificationlist = new ArrayList<jobNotifications>();
	NotificationCallBack notificationCallBack;

	public class LogoutReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("com.package.ACTION_LOGOUT")) {
				finish();
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the Above View
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		category_ID.clear();
		category_ID = getIntent().getStringArrayListExtra("CATEGORY_ID");

		if (mContent == null)
			mContent = new TopBottomTabHostFragment(category_ID,
					TopBottomFragmentTabHostActivity.this);

		initViews();
		initManagers();
		setVisibilities();
		setTexts();

		// logout broadcast receiver
		logoutReceiver = new LogoutReceiver();

		// Register the logout receiver
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.package.ACTION_LOGOUT");
		registerReceiver(logoutReceiver, intentFilter);

		image_back.setVisibility(View.GONE);
		textview_trackyourfix.setVisibility(View.VISIBLE);

		setContentView(R.layout.first_tab_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.first_tab_frame, mContent).commit();

		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		image_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		NotificationManager.getInstance().getNotificationList(
				TopBottomFragmentTabHostActivity.this, limit, offset,
				notificationCallBack, requestcode);

		if (EasyHomeFixApp.getNotificationcount() != null)
			getnotificationSize(Integer.parseInt(EasyHomeFixApp
					.getNotificationcount()));
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}

	private void setTexts() {
		// TODO Auto-generated method stub
		textview_trackyourfix.setText("Available fixes");
	}

	private void initManagers() {
		// TODO Auto-generated method stub
		notificationCallBack = new NotificationCallBack();
	}

	private void setVisibilities() {
		image_back.setVisibility(View.VISIBLE);
		textview_trackyourfix.setVisibility(View.VISIBLE);

	}

	private void initViews() {
		// TODO Auto-generated method stub
		image_back = (ImageView) findViewById(R.id.image_back);
		textview_trackyourfix = (TextView) findViewById(R.id.textview_trackyourfix);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, fragment).commit();
		getSlidingMenu().showContent();
	}

	public void setPageTitle(String title) {
		textview_trackyourfix.setText(title);
	}

	public class NotificationCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			if (responseCode == requestcode) {
				EmailSignupBaseHolder notificationBaseHolder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(notificationBaseHolder)) {
					if (notificationBaseHolder.isSuccess()) {
						if (UtilValidate.isNotNull(notificationBaseHolder
								.getData())) {
							if (UtilValidate.isNotNull(notificationBaseHolder
									.getData().getJobNotifications())) {
								if (UtilValidate
										.isNotEmpty(notificationBaseHolder
												.getData()
												.getJobNotifications())) {
									notificationlist.clear();
									notificationlist = notificationBaseHolder
											.getData().getJobNotifications();

									EasyHomeFixApp.setNotificationlist(null);
									EasyHomeFixApp
											.setNotificationlist(notificationlist);
									if (UtilValidate
											.isNotEmpty(notificationlist)) {
										if (notificationlist.get(0)
												.getRecieverUser() != null) {
											if (notificationlist.get(0)
													.getRecieverUser()
													.getUnreadNotifications()
													+ "" != null) {
												EasyHomeFixApp
														.setNotificationcount(notificationlist
																.get(0)
																.getRecieverUser()
																.getUnreadNotifications()
																+ "");

												getnotificationSize(Integer
														.parseInt(EasyHomeFixApp
																.getNotificationcount()));
											}
										}
									}

								}

							}
						}

					}

				}
			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				//Utils.showDialog(TopBottomFragmentTabHostActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(TopBottomFragmentTabHostActivity.this, result, "ERROR");
			}

		}

	}

}
