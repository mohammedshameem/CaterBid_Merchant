package com.xminds.easyhomefix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.Job;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.JobList;
import com.xminds.easyhomefix.Holder.JobNotifications;
import com.xminds.easyhomefix.Managers.NotificationManager;
import com.xminds.easyhomefix.actionbar.BaseActivity;
import com.xminds.easyhomefix.activities.EditFixDetailsActivity.LogoutReceiver;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.dao.PageStatusDAO;
import com.xminds.easyhomefix.fragment.DetailsTabFragment;
import com.xminds.easyhomefix.fragment.TabFixTabHostFragment;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;


public class TrackFixFragmentTabhostActivity extends BaseActivity {

	private Fragment mContent;
	private ImageView image_back;
	private TextView textview_trackyourfix;	
	private String 	jobid ;
	private String  quoteid ;
	private JobList jobList;
	private Job notificationListObject;
	private  Job notificationlistObject;
	private LogoutReceiver logoutReceiver;
	private JobDetailsCallback jobDetailsCallback; 
	private String notificationJobId;
	int requestcode = 0;
	private ProgressDialog pDialog;
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
		    mContent = new TabFixTabHostFragment(jobList,notificationListObject);
			//mContent = new TabFixTabHostFragment();
		
		SlidingMenu sm = getSlidingMenu();
		sm.setSlidingEnabled(true);
		initViews();
		initManagers();
		getIntentValue();
		setVisibilities();
		setTexts();
		/*if(notificationJobId!=null){
			
		NotificationManager.getInstance().getNotificationListItemObject(TrackFixFragmentTabhostActivity.this,notificationJobId,
				jobDetailsCallback,requestcode);
		}*/
		
		EasyHomeFixApp.setRejectquoteStatus("false");
	
		
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		
		   //mContent = new TabFragment();
		image_back.setVisibility(View.VISIBLE);
		//textview_trackyourfix.setVisibility(View.VISIBLE);
		
		//textview_trackyourfix.setText(notificationlistObject.getJobCategoryId().getName());
		
		if(jobList!=null)
		{
			if(jobList.getJobCategoryId()!=null)
			{
				
				if(jobList.getJobCategoryId().getName()!=null)
				{
					textview_trackyourfix.setText(jobList.getJobCategoryId().getName());
				}
			}
		}
		else
		{
			
			if(notificationListObject!=null)
			{
				if(notificationListObject !=null)
				{
					
					if(notificationListObject.getJobCategoryId()!=null)
					{
						if(notificationListObject.getJobCategoryId().getName()!=null)
						{
						textview_trackyourfix.setText(notificationListObject.getJobCategoryId().getName());
						}
					}
				}
			}
		}
		setContentView(R.layout.first_tab_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.first_tab_frame, mContent).commit();

		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		
		
		image_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if(EasyHomeFixApp.getAcceptquoteStatus().equalsIgnoreCase("accepted"))
				{
				EasyHomeFixApp.setAcceptquoteStatus("rejected");
				Intent i=new Intent(TrackFixFragmentTabhostActivity.this,TrackYourFixFragmentTabHostActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				finish();
				
				}
				else
				{
					finish();	
				}
				
			}
		});
		
		
			}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(EasyHomeFixApp.getCount()!=null)
			getnotificationSize(Integer.parseInt(EasyHomeFixApp.getCount()));
		super.onResume();
		if(pDialog != null){
			if(!pDialog.isShowing()){
				pDialog = ProgressDialog.show(
						TrackFixFragmentTabhostActivity.this, null, "Loading..",
						true);
				pDialog.setCancelable(true);
			}
		}
		if(notificationJobId!=null){
			
			NotificationManager.getInstance().getNotificationListItemObject(TrackFixFragmentTabhostActivity.this,notificationJobId,
					jobDetailsCallback,requestcode);
			}

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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
	unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}
	private void getIntentValue() {
		// TODO Auto-generated method stub
		/* if (getIntent().hasExtra("jobId")) {
			 
			 jobid = this.getIntent().getExtras().getString("jobId");
			// getIntent().removeExtra("jobId");
		 }
		 if (getIntent().hasExtra("quoteid")) {
			 
			 quoteid = this.getIntent().getExtras().getString("quoteid");
			// getIntent().removeExtra("quoteid");
		 }
		 */
		 if (getIntent().hasExtra("ONGOINGOBJECT")) {
			 jobList = (JobList) getIntent()
						.getSerializableExtra("ONGOINGOBJECT");
			}
		 if (getIntent().hasExtra("job")) {
			 jobList = (JobList) getIntent()
						.getSerializableExtra("job");
			}
		 
		 if (getIntent().hasExtra("PENDINGOBJECT")) {
			 jobList = (JobList) getIntent()
						.getSerializableExtra("PENDINGOBJECT");
			}
		 if(getIntent().hasExtra("notification_jobid")){
				notificationJobId = (String) getIntent().getStringExtra("notification_jobid");
			}
		 		
		 
		 
		//NOTIFICATION OBJECT FROM ACTIONBAR
		 
		 /*if (getIntent().hasExtra("NotificationObject")) {
			 notificationListObject = (JobNotifications) getIntent()
						.getSerializableExtra("NotificationObject");
			}*/
		
		//NOTIFICATION OBJECT FROM CHAT
		 
		 if (getIntent().hasExtra("notification_chat")) {
			 notificationListObject = (Job) getIntent()
						.getSerializableExtra("notification_chat");
			}
		
		//NOTIFICATION OBJECT FROM  ONGOING
		 
		 if (getIntent().hasExtra("notification_ongoing")) {
			 notificationListObject = (Job) getIntent()
						.getSerializableExtra("notification_ongoing");
			}
		 
		 //NOTIFICATION OBJECT FROM COMPLETE
		 
		 
		 if (getIntent().hasExtra("notification_completed")) {
			 notificationListObject = (Job) getIntent()
						.getSerializableExtra("notification_completed");
			}
		 
		 
		 if(getIntent().hasExtra("From_notification_pending")){
			 notificationListObject = (Job) getIntent()
						.getSerializableExtra("From_notification_pending");
			 
		 }
		 

	}
	private void setTexts() {
		// TODO Auto-generated method stub
		//textview_trackyourfix.setText("Track Your Fix");
	}
	private void initManagers() {
		// TODO Auto-generated method stub
		jobDetailsCallback = new JobDetailsCallback();
		
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
	
	
	private class JobDetailsCallback implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			if(result != null){
				EmailSignupBaseHolder jobDetails = (EmailSignupBaseHolder)result;
				if(jobDetails.isSuccess()){
					if(jobDetails.getData() != null){
						if(jobDetails.getData().getJob() != null){
							notificationlistObject = jobDetails.getData().getJob();
							//notificationListObject.getJobCategoryId().getName();
							//( (Object) getActivity()).setActionBarTitle(notificationListObject.getJobCategoryId().getName());
							
							 textview_trackyourfix.setText(notificationlistObject.getJobCategoryId().getName());
													
						}
					}
				}
				if(pDialog != null){
					pDialog.cancel();
				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			if(pDialog != null){
				pDialog.cancel();
			}
			//Toast.makeText(TrackFixFragmentTabhostActivity.this, result , Toast.LENGTH_LONG).show();
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(TrackFixFragmentTabhostActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(TrackFixFragmentTabhostActivity.this, ""+result, "ERROR");
			}
		}

		@Override
		public void onFinish(int requestcode, JobCreationBaseHolder jobCreationBaseHolder, int i) {
			
		}
		
	}
	
	
}
