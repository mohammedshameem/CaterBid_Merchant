package com.xminds.easyhomefix_merchant.activities;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.xminds.easyfix_merchant.actionbar.ActionBarListFragment.updateNotificationCallBack;
import com.easyhomefix.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.jobNotifications;
import com.xminds.easyhomefix_merchant.Manager.NotificationManager;
import com.xminds.easyhomefix_merchant.adapter.NotificationsAdapter;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;

public class SplashScreenActivity extends Activity {
	
	private  List<String>ListIds=new ArrayList<String>();

	NotificationCallBack notificationCallBack;
	private int notification;
	NotificationsAdapter notificationsAdapter;
	updateNotificationCallBack updatenotificationcallback;
	private TextView notificationcount;
	private int count = 0;
	private List<jobNotifications> notificationlist = new ArrayList<jobNotifications>();
	private int limit=10;
	private  int requestcode=0;
	private int offset=0;
	private LogoutReceiver logoutReceiver;
	private GoogleCloudMessaging gcm;
	private String regid;
    public class LogoutReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.package.ACTION_LOGOUT")) {
                finish();
            }
        }
    }
	private static final String TAG = SplashScreenActivity.class.getName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		initViews();
		initManager();
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		
		
		Log.e("","header"+CurrentlyLoggedUserDAO.getInstance().getHeader());
		ListIds= Arrays.asList("1", "2", "3","4","5","6","7","8","1000");
		EasyHomeFixApp.setListIds(null);
		EasyHomeFixApp.setListIds(ListIds);
		Utils.showHashKey(SplashScreenActivity.this);
		if(!CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable()){
			getRegId();
		}
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {		
					// start thread
            	if (CurrentlyLoggedUserDAO.getInstance()
        				.isAnyUserInCurrentlyLoggedUserTable()) {
            		
                   NotificationManager.getInstance().getNotificationList(SplashScreenActivity.this,limit,offset, notificationCallBack, requestcode);
            		
            		
            		
            	}else{
            		
            		Intent intent = new Intent(SplashScreenActivity.this,SignUpandLoginActivity.class);
					startActivity(intent);
					finish();
            	}
					/* Intent in=new Intent(SplashScreenActivity.this,MerchantSettingsActivity.class);
					    startActivity(in);*/
			}
		};
		handler.postDelayed(runnable, 3000);
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}
	
	
	private void initManager() {
		// TODO Auto-generated method stub
		
		notificationCallBack=new NotificationCallBack();
		
	}


	private void initViews() {
		// TODO Auto-generated method stub
		
		
	}


	public class NotificationCallBack implements AsyncTaskCallBack{

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
									
							
									EasyHomeFixApp.setNotificationlist(null);
								EasyHomeFixApp.setNotificationlist(notificationlist);
								if(UtilValidate.isNotEmpty(notificationlist))
								{
									if(notificationlist.get(0).getRecieverUser()!=null)
									{
										if(notificationlist.get(0).getRecieverUser().getUnreadNotifications()+""!=null)
										{
											Log.e(TAG, "notification count@@@@@@@@@@@@@@@@@"+notificationlist.get(0).getRecieverUser().getUnreadNotifications()+"");
								EasyHomeFixApp.setNotificationcount
								(notificationlist.get(0).getRecieverUser().
										getUnreadNotifications()+"");
									}
									}
									}
									
								}

							}
						}

					}
					
					Intent intent = new Intent(SplashScreenActivity.this,TopBottomFragmentTabHostActivity.class);
					startActivity(intent);
					finish();
				}
			}
			
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(SplashScreenActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(SplashScreenActivity.this, result, "ERROR");
			}
			
		}
		
	}
	
	public void getRegId(){
		 
		 final   String PROJECT_NUMBER = "327089025637";
		//962441763799
       new AsyncTask<Void, Void, String>() {
           @Override
           protected String doInBackground(Void... params) {
               
               try {
                   if (gcm == null) {
                       gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                   }
                   regid = gcm.register(PROJECT_NUMBER);
                   

               } catch (IOException ex) {
                   Log.e(TAG,"Error :" + ex.getMessage());

               }
               return regid;
           }

           @Override
           protected void onPostExecute(String regid) {
           	Log.e(TAG, "REGID: "+regid);
           	if(regid != null){
           		EasyHomeFixApp.setGcmRegId(regid);
           	}
           }
       }.execute(null, null, null);
   }
	
}
