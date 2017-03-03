package com.xminds.easyhomefix.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.ScaleInAnimation;
import com.easyandroidanimations.library.SlideInAnimation;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.JobCategories;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.JobNotifications;
import com.xminds.easyhomefix.Holder.JobReviews;
import com.xminds.easyhomefix.Holder.Quote;
import com.xminds.easyhomefix.Managers.NotificationManager;
import com.xminds.easyhomefix.actionbar.BaseActivity;
import com.xminds.easyhomefix.activities.EditFixDetailsActivity.LogoutReceiver;
import com.xminds.easyhomefix.adapter.NotificationsAdapter;
import com.xminds.easyhomefix.adapter.ReviewAdapter;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.constants.Constants;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class LandingPageActivity extends BaseActivity {

	private ImageView image_back;
	private TextView textview_trackyourfix;
	private LinearLayout list_your_fix_button;
	private LinearLayout keep_track_your_fix_button;
	private ImageView landing_page_icon;
	private LinearLayout select_fix_category_layout;
	private LinearLayout landing_page_icon_layout;

	private LinearLayout select_fix_category_ac_layout;
	private LinearLayout select_fix_category_plumbing_layout;
	private LinearLayout select_fix_category_electrician_layout;
	private LinearLayout select_fix_category_painting_layout;
	private LinearLayout select_fix_category_carpenter_layout;
	private LinearLayout select_fix_category_locksmith_layout;
	private LinearLayout select_fix_category_pestCtrl_layout;
	private LinearLayout select_fix_category_sanitization_layout;

	private TextView need_help_button_text;
	private LinearLayout linear_parent;
	private int fullScreenWidth;
	private int height;
	private int requestcode = 0;
	private LinearLayout general_works_button;
	private String notificationdate;
	private String notificationText;
	private PopupWindow popupWindow;
	private List<JobNotifications> notificationlist = new ArrayList<JobNotifications>();
	private NotificationsAdapter notificationsAdapter;
	private LogoutReceiver logoutReceiver;
	private NotificationCallBack notificationCallBack;
	private int limit=10;
	private int offset=0;
	private static final String TAG = LandingPageActivity.class.getName();
	public class LogoutReceiver extends BroadcastReceiver {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        if (intent.getAction().equals("com.package.ACTION_LOGOUT")) {
	            finish();
	        }
	    }
	}


	/*
	 * public LandingPageActivity() { super(R.string.app_name); }
	 * 
	 * public LandingPageActivity(int titleRes) { super(titleRes); }
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landing_screen);
		
	//NotificationManager.getInstance().getNotificationList(LandingPageActivity.this, notificationCallBack, requestcode);
		initViews();
		initManagers();
		setVisibilities();
		/*if(EasyHomeFixApp.getCount()!=null){
			Log.e("", "on create landing pageee"+Integer.parseInt(EasyHomeFixApp.getCount()));
			getnotificationSize(Integer.parseInt(EasyHomeFixApp.getCount()));
		}*/
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		// Api for Notification

	        NotificationManager.getInstance().getNotificationList(
	        		LandingPageActivity.this,limit,offset, notificationCallBack, requestcode);
		/*NotificationManager.getInstance().getNotificationList(
				LandingPageActivity.this, notificationCallBack, requestcode);*/
	       
	        if(EasyHomeFixApp.getCount()!=null){
				Log.e("", "on create landing pageee"+Integer.parseInt(EasyHomeFixApp.getCount()));
				getnotificationSize(Integer.parseInt(EasyHomeFixApp.getCount()));
			}
		Display display = LandingPageActivity.this.getWindow()
				.getWindowManager().getDefaultDisplay();

		fullScreenWidth = display.getWidth();
		height = display.getHeight();

		if(!CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable())
		{
		keep_track_your_fix_button.setEnabled(false);
		keep_track_your_fix_button.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.grey_button_rounded_corners));
		}
		else
		{
			keep_track_your_fix_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					Intent i = new Intent(LandingPageActivity.this,
							TrackYourFixFragmentTabHostActivity.class);
					EasyHomeFixApp.setCategoryNameDefaultTab(Constants.PENDING);
					
					startActivity(i);
				}
			});
		}
		list_your_fix_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (landing_page_icon.getVisibility() == View.VISIBLE) {
					/*
					 * landing_page_icon.setVisibility(View.GONE);
					 * TranslateAnimation animation=new TranslateAnimation(0, 0,
					 * 0, -(landing_page_icon_layout.getHeight()));
					 * animation.setDuration(1500);
					 * animation.setFillAfter(true);
					 * select_fix_category_layout.setVisibility(View.VISIBLE);
					 * select_fix_category_layout.startAnimation(animation);
					 */

					landing_page_icon.setVisibility(View.GONE);
					new SlideInAnimation(select_fix_category_layout)
							.setDirection(Animation.DIRECTION_DOWN)
							.setDuration(1000).animate();
					select_fix_category_layout.setVisibility(View.VISIBLE);
				} else {

					select_fix_category_layout.setVisibility(View.GONE);
					landing_page_icon.setVisibility(View.VISIBLE);
				}
			}
		});

		need_help_button_text.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				showPopup();
			}
		});
		select_fix_category_ac_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(LandingPageActivity.this,
						ListYourFixesActivity.class);
				i.putExtra("name", "ac");
				startActivity(i);

			}
		});
		select_fix_category_plumbing_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(LandingPageActivity.this,
								ListYourFixesActivity.class);
						i.putExtra("name", "plumbing");
						startActivity(i);

					}
				});
		select_fix_category_electrician_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(LandingPageActivity.this,
								ListYourFixesActivity.class);
						i.putExtra("name", "electrician");
						startActivity(i);

					}
				});
		select_fix_category_painting_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(LandingPageActivity.this,
								ListYourFixesActivity.class);
						i.putExtra("name", "painting");
						startActivity(i);

					}
				});
		select_fix_category_carpenter_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(LandingPageActivity.this,
								ListYourFixesActivity.class);
						i.putExtra("name", "carpenter");
						startActivity(i);

					}
				});
		select_fix_category_locksmith_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(LandingPageActivity.this,
								ListYourFixesActivity.class);
						i.putExtra("name", "locksmith");
						startActivity(i);

					}
				});
		select_fix_category_pestCtrl_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(LandingPageActivity.this,
								ListYourFixesActivity.class);
						i.putExtra("name", "pest");
						startActivity(i);

					}
				});
		select_fix_category_sanitization_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(LandingPageActivity.this,
								ListYourFixesActivity.class);
						i.putExtra("name", "sanitation");
						startActivity(i);

					}
				});

		general_works_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(LandingPageActivity.this,
						ListYourFixesActivity.class);
				i.putExtra("name", "generalworks");
				startActivity(i);

			}
		});
		/*
		 * select_fix_category_layout.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub
		 * 
		 * Intent i=new
		 * Intent(LandingPageActivity.this,ListYourFixesActivity.class);
		 * startActivity(i); } });
		 */

		
	}
	
	@Override
	protected void onResume() {
		
		// TODO Auto-generated method stub
		if(EasyHomeFixApp.getCount()!=null){
			Log.e("", "on resume landing pageee"+Integer.parseInt(EasyHomeFixApp.getCount()));
			getnotificationSize(Integer.parseInt(EasyHomeFixApp.getCount()));
		}
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
	unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}
	
	
	
	
	public class NotificationCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			
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
									
								EasyHomeFixApp.setJobNotifications(notificationlist);
								Log.e("","status>>>>>>>>>"+EasyHomeFixApp.getJobNotifications().get(0).getNotifyStatus());
								Log.e(TAG, "notification count@@@@@@@@@@@@@@@@@"+notificationlist.get(0).getRecieverUser().getUnreadNotifications()+"");
								EasyHomeFixApp.setNotificationcount
								(notificationlist.get(0).getRecieverUser().
										getUnreadNotifications()+"");
								 getnotificationSize(notificationlist.get(0).getRecieverUser().
											getUnreadNotifications());

							}

					}
							
					
				}
					/*
					Handler handler = new Handler();
					Runnable runnable = new Runnable() {

						@Override
						public void run() {
							// start thread
							Intent intent = new Intent(LandingPageActivity.this,
									LandingPageActivity.class);
							startActivity(intent);
							finish();
						}
					};
					handler.postDelayed(runnable, 1000);*/
					}
			}
			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				//Utils.showDialog(LandingPageActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(LandingPageActivity.this, ""+result, "ERROR");
			}
			
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub

		}

	}
	
/*
	public class NotificationCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (responseCode == requestcode) {
				EmailSignupBaseHolder notificationBaseHolder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(notificationBaseHolder)) {
					if (notificationBaseHolder.isSuccess()) {
						for (JobNotifications notification : notificationBaseHolder
								.getData().getJobNotifications()) {
							notificationdate=notification.getNotifyDateTime();
							notificationText=notification.getNotifyText();
							
						}

					
						if(UtilValidate.isNotNull(notificationBaseHolder.getData()))
						{
							if(UtilValidate.isNotNull(notificationBaseHolder.
									getData().getJobNotifications())){
								if(UtilValidate.isNotEmpty(notificationBaseHolder
										.getData().getJobNotifications())){
									notificationlist.clear();
									notificationlist = notificationBaseHolder.getData().getJobNotifications();
									notificationsAdapter = new NotificationsAdapter(
											LandingPageActivity.this, notificationlist);
									
									
									
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

		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}

	}*/

	private void initManagers() {
		// TODO Auto-generated method stub
		notificationCallBack = new NotificationCallBack();
	}

	private void setVisibilities() {
		// TODO Auto-generated method stub
		image_back.setVisibility(View.GONE);
		textview_trackyourfix.setVisibility(View.GONE);
		select_fix_category_layout.setVisibility(View.GONE);
		landing_page_icon.setVisibility(View.VISIBLE);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		image_back = (ImageView) findViewById(R.id.image_back);
		textview_trackyourfix = (TextView) findViewById(R.id.textview_trackyourfix);
		list_your_fix_button = (LinearLayout) findViewById(R.id.list_your_fix_button);
		keep_track_your_fix_button = (LinearLayout) findViewById(R.id.keep_track_your_fix_button);
		landing_page_icon = (ImageView) findViewById(R.id.landing_page_icon);
		select_fix_category_layout = (LinearLayout) findViewById(R.id.select_fix_category_layout);
		need_help_button_text = (TextView) findViewById(R.id.need_help_button_text);
		linear_parent = (LinearLayout) findViewById(R.id.linear_parent);
		select_fix_category_ac_layout = (LinearLayout) findViewById(R.id.select_fix_category_ac_layout);
		select_fix_category_plumbing_layout = (LinearLayout) findViewById(R.id.select_fix_category_plumbing_layout);
		select_fix_category_electrician_layout = (LinearLayout) findViewById(R.id.select_fix_category_electrician_layout);
		select_fix_category_painting_layout = (LinearLayout) findViewById(R.id.select_fix_category_painting_layout);
		select_fix_category_carpenter_layout = (LinearLayout) findViewById(R.id.select_fix_category_carpenter_layout);
		select_fix_category_locksmith_layout = (LinearLayout) findViewById(R.id.select_fix_category_locksmith_layout);
		select_fix_category_pestCtrl_layout = (LinearLayout) findViewById(R.id.select_fix_category_pestCtrl_layout);
		select_fix_category_sanitization_layout = (LinearLayout) findViewById(R.id.select_fix_category_sanitization_layout);
		general_works_button = (LinearLayout) findViewById(R.id.general_works_button);
		landing_page_icon_layout = (LinearLayout) findViewById(R.id.landing_page_icon_layout);
				
	}

	private void showPopup() {
		LayoutInflater layoutInflater = (LayoutInflater) LandingPageActivity.this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.overlay_layout, null);
		popupWindow = new PopupWindow(popupView, fullScreenWidth, height, true);
		popupWindow.update();
		popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		new ScaleInAnimation(popupView).animate();
		popupWindow.getContentView().setFocusableInTouchMode(true);
		popupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_BACK) {
					popupWindow.dismiss();
					return true;
				}
				return false;
			}
		});

		final TextView close = (TextView) popupView
				.findViewById(R.id.textViewClose);
		close.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});

	}

}
