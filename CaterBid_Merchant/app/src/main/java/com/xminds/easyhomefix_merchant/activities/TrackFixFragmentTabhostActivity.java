package com.xminds.easyhomefix_merchant.activities;

import android.app.Activity;
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
import com.xminds.easyfix_merchant.constants.Constants;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.AvailableJobs;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.Job;
import com.xminds.easyhomefix_merchant.Holder.JobNotifications;
import com.xminds.easyhomefix_merchant.Holder.Quote;
import com.xminds.easyhomefix_merchant.Holder.jobNotifications;
import com.xminds.easyhomefix_merchant.Manager.NotificationManager;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.dao.PageStatusDAO;
import com.xminds.easyhomefix_merchant.fragment.TabFixTabHostFragment;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;

public class TrackFixFragmentTabhostActivity extends BaseActivity {


	
	private Fragment mContent;
	private ImageView image_back;
	private TextView textview_trackyourfix;	
	private AvailableJobs availableJobs;
	private LogoutReceiver logoutReceiver;
	//private jobNotifications notificationobject;
	private Job notificationobject;
	private Quote quoteObject;
	private String notificationJobId;
	private int requestcode=0;
	JoblistItemCallBack joblistItemCallBack;	
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
		if(EasyHomeFixApp.getDetailsTabStatus() == null){
			if(PageStatusDAO.getInstance().getDetailsTabStatus() != null){
				 EasyHomeFixApp.setDetailsTabStatus(PageStatusDAO.getInstance().getDetailsTabStatus());
				 if(PageStatusDAO.getInstance().getCategoryNameDefaultTab() != null){
					 EasyHomeFixApp.setCategoryNameDefaultTab(PageStatusDAO.getInstance().getCategoryNameDefaultTab());
				}
			}
		}

		
		// set the Above View
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null)
		    mContent = new TabFixTabHostFragment(availableJobs,notificationobject,quoteObject);
		
		SlidingMenu sm = getSlidingMenu();
		sm.setSlidingEnabled(true);
		
		initViews();
		initManagers();
		setVisibilities();
		getIntentValue();
		setTexts();

		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		
		
		   //mContent = new TabFragment();
		image_back.setVisibility(View.VISIBLE);
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
		if(EasyHomeFixApp.getNotificationcount()!=null)
			getnotificationSize(Integer.parseInt(EasyHomeFixApp.getNotificationcount()));
		
		if(notificationJobId!=null)
		{
			NotificationManager.getInstance().jobListItemObject(TrackFixFragmentTabhostActivity.this,notificationJobId,joblistItemCallBack,requestcode);
		}
		
		
		super.onResume();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.e("", "onactivity result in fragment : "+requestCode+"|"+data);
		if (resultCode == Activity.RESULT_OK && null != data) {

			if(mContent != null){
				mContent.onActivityResult(10, resultCode, data);
			}else{
				super.onActivityResult(requestCode, resultCode, data);
			}
		}
		//super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void getIntentValue() {
		// TODO Auto-generated method stub
		
		if(getIntent().hasExtra("AVAILABLE_OBJECT")){
			
			availableJobs = (AvailableJobs)getIntent().getSerializableExtra("AVAILABLE_OBJECT");
			
		}
		
		/*if(getIntent().hasExtra("QUOTED_JOBS")){
			
			availableJobs = (AvailableJobs)getIntent().getSerializableExtra("QUOTED_JOBS");
			
		}*/
		if(getIntent().hasExtra("job")){
			
			availableJobs = (AvailableJobs)getIntent().getSerializableExtra("job");
		
		}
		
		/*if(getIntent().hasExtra("notification")){
			
			notificationobject = (jobNotifications)getIntent().getSerializableExtra("notification");
		
		}*/
		if(getIntent().hasExtra("QUOTE_OBJECT")){
			
			quoteObject = (Quote)getIntent().getSerializableExtra("QUOTE_OBJECT");
		
		}
		if(getIntent().hasExtra("QUOTED_OBJECT_FROM_QUOTE")){
			
			availableJobs = (AvailableJobs)getIntent().getSerializableExtra("QUOTED_OBJECT_FROM_QUOTE");
		
		}
		if(getIntent().hasExtra("QUOTED_OBJECT_FROM_ONGOING")){
			
			availableJobs = (AvailableJobs)getIntent().getSerializableExtra("QUOTED_OBJECT_FROM_ONGOING");
		
		}
		if(getIntent().hasExtra("QUOTED_OBJECT_FROM_COMPLETE")){
			
			availableJobs = (AvailableJobs)getIntent().getSerializableExtra("QUOTED_OBJECT_FROM_COMPLETE");
		
		}
		if(getIntent().hasExtra("notification_jobid")){
			notificationJobId = (String)getIntent().getStringExtra("notification_jobid");
		}
		//NOTIFICATION OBJECT FROM CHAT
		 
		 if (getIntent().hasExtra("notification_chat")) {
			 
			 Log.d("","INSIDE CHAT track" );
			 notificationobject = (Job) getIntent()
						.getSerializableExtra("notification_chat");
			 Log.e("INSIDE CHAT track brf" ,""+notificationobject.getId());
			  //mContent = new TabFixTabHostFragment(availableJobs,notificationobject,quoteObject);
			}
		
		//NOTIFICATION OBJECT FROM  ONGOING
		 
		 if (getIntent().hasExtra("notification_ongoing")) {
			 notificationobject = (Job) getIntent()
						.getSerializableExtra("notification_ongoing");
			}
		 
		 //NOTIFICATION OBJECT FROM COMPLETE
		 
		 
		 if (getIntent().hasExtra("notification_completed")) {
			 notificationobject = (Job) getIntent()
						.getSerializableExtra("notification_completed");
			}
		 
		 
		 if(getIntent().hasExtra("From_notification_pending")){
			 notificationobject = (Job) getIntent()
						.getSerializableExtra("From_notification_pending");

			 
		 }
		 
		 if (getIntent().hasExtra("notification_cancelled")) {
			 notificationobject = (Job) getIntent()
						.getSerializableExtra("notification_cancelled");
			}
		 

	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}
	private void setTexts() {
		// TODO Auto-generated method stub
		if(availableJobs != null){
			if(availableJobs.getJobCategoryId()!=null)
			{
				textview_trackyourfix.setText(availableJobs.getJobCategoryId().getName());
			}
			
		}
		else if(notificationobject != null){
			textview_trackyourfix.setText(notificationobject.getJobCategoryId().getName());
		}
		
	}
	private void initManagers() {
		// TODO Auto-generated method stub
		joblistItemCallBack=new JoblistItemCallBack();
		
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

	public class JoblistItemCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			
			EmailSignupBaseHolder notificationListitemObject = (EmailSignupBaseHolder)result;
			
			if(notificationListitemObject!=null)
			{
				if(notificationListitemObject.getData().getJob().getJobCategoryId().getName()!=null)
				{
					textview_trackyourfix.setText(notificationListitemObject.getData().getJob().getJobCategoryId().getName());
				}
			}
			
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(TrackFixFragmentTabhostActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(TrackFixFragmentTabhostActivity.this, result, "ERROR");
			}
			
		}
		
	}
	
}
