package com.xminds.easyhomefix.gcm;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.activities.EmptyFixQuoteActivity;
import com.xminds.easyhomefix.activities.ReviewServiceActivity;
import com.xminds.easyhomefix.activities.SplashScreenActivity;
import com.xminds.easyhomefix.activities.TrackFixFragmentTabhostActivity;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.constants.Constants;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.dao.PageStatusDAO;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;


public class GcmMessageHandler extends IntentService {
	private NotificationManager mNotificationManager;
    private String mes;
    public static final int NOTIFICATION_ID = 1;
    private Handler handler;
    private String jid;
    private String type;
    private String qid;
    private TaskStackBuilder stackBuilder;    
   public GcmMessageHandler() {
       super("GcmMessageHandler");
   }

   @Override
   public void onCreate() {
       // TODO Auto-generated method stub
       super.onCreate();
       handler = new Handler();
       stackBuilder = TaskStackBuilder.create(this);
   }
   @Override
   protected void onHandleIntent(Intent intent) {
       Bundle extras = intent.getExtras();
       mes = extras.getString("message");
       jid = extras.getString("jId");
       type = extras.getString("type");
       if(intent.hasExtra("qId")){
       		qid = extras.getString("qId");
       		Log.e("@@@@@@@@@", "QID: "+qid);
       }

      // GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
       // The getMessageType() intent parameter must be the intent you received
       // in your BroadcastReceiver.
      // String messageType = gcm.getMessageType(intent);
       
       //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, getIntent(type),PendingIntent.FLAG_ONE_SHOT);
       if(CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable()){
	      if(type != null){
		      
		       stackBuilder.addNextIntent(getIntent(type));
		       PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
		      
		      NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
		      .setSmallIcon(R.drawable.app_icon)
		      .setContentTitle("CaterBid")
		      .setContentText(extras.getString("message"))
		      .setAutoCancel(true)
		      .setContentIntent(pendingIntent)
		      .setPriority(Notification.PRIORITY_HIGH)
		      .setDefaults(Notification.DEFAULT_ALL);
		
		      //notificationBuilder.setContentIntent(pendingIntent);
		      NotificationManager notificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		      notificationManager.notify(NOTIFICATION_ID /* ID of notification */, notificationBuilder.build());
		     
		       GcmBroadcastReceiver.completeWakefulIntent(intent);
		       if(checkApp()){
		    	   Intent broadcastIntent = new Intent("nofication_received");
		    	   broadcastIntent.putExtra("quoteid", qid);
		    	   broadcastIntent.putExtra("notification_jobid", jid);
		    	   EasyHomeFixApp.getContext().sendBroadcast(broadcastIntent);
		       }
	      }
       }
   }
   
   
   private boolean isVibrationEnabled(){

       SharedPreferences sp = PreferenceManager
               .getDefaultSharedPreferences(this);

       return sp.getBoolean("notifications_new_message_vibrate",true);
   }
   private long[] getVibrationValue(){

       if(isVibrationEnabled())
           return new long[]{1000L, 1000L};
       else
           return new long[]{01L};
   }

   private Uri getRingTone(){

       SharedPreferences sp = PreferenceManager
               .getDefaultSharedPreferences(this);

       String ringToneValue = sp.getString("notifications_new_message_ringtone","content://settings/system/notification_sound" );
       Uri uri = Uri.parse(ringToneValue);

       return uri;

   }
   public void showToast(){
       handler.post(new Runnable() {
           public void run() {
               Toast.makeText(getApplicationContext(),mes+ "|"+jid+"|"+type , Toast.LENGTH_LONG).show();
           }
        });

   }
   
   private Intent getIntent(String type){
	   if(type != null){
		   if(type.equalsIgnoreCase(Constants.MESSAGE_RECIEVED) || type.equalsIgnoreCase(Constants.QUOTE_EDITED)){
			   EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION);
			   EasyHomeFixApp.setCategoryNameDefaultTab(Constants.CHAT);
			   PageStatusDAO.getInstance().deleteAll();
			   PageStatusDAO.getInstance().insertStatus(Constants.FROM_NOTIFICATION, Constants.CHAT);
			   Intent intents = new Intent(this, TrackFixFragmentTabhostActivity.class);
			   intents.putExtra("quoteid", qid);
			   intents.putExtra("notification_jobid", jid);
			   Log.e("@@@@@@@@@", "QID GTI: "+qid);
			   stackBuilder.addParentStack(TrackFixFragmentTabhostActivity.class);
		       intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		       return intents;
		   }else if(type.equalsIgnoreCase(Constants.REVIEW_REMINDER) || type.equalsIgnoreCase(Constants.REVIEW_REQUESTED) ||
				   type.equalsIgnoreCase(Constants.REVIEW_UNCOMPLETED)){
			   Intent intents = new Intent(this, ReviewServiceActivity.class);
			   intents.putExtra("jobid", jid);
			   Log.e("@@@@@@@@@", "JID: "+jid);
			   stackBuilder.addParentStack(ReviewServiceActivity.class);
		       intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		       return intents;
		   }else if(type.equalsIgnoreCase(Constants.QUOTE_RECIEVED)){
			   Intent intents = new Intent(this, EmptyFixQuoteActivity.class);
			   intents.putExtra("quoteid", qid);
			   intents.putExtra("notification_jobid", jid);
		       intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		       stackBuilder.addParentStack(EmptyFixQuoteActivity.class);
		       return intents;
		   }else{
			   EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION);
			   EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
			   PageStatusDAO.getInstance().deleteAll();
			   PageStatusDAO.getInstance().insertStatus(Constants.FROM_NOTIFICATION, Constants.DETAILS);
			   Intent intents = new Intent(this, TrackFixFragmentTabhostActivity.class);
			   intents.putExtra("quoteid", qid);
			   intents.putExtra("notification_jobid", jid);
		       intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		       stackBuilder.addParentStack(TrackFixFragmentTabhostActivity.class);
		       return intents;
		   }
	   }else{
		   return null;
	   }
   }
   
   public boolean checkApp(){
       ActivityManager am = (ActivityManager) this
                .getSystemService(ACTIVITY_SERVICE);

        // get the info from the currently running task
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        Log.e("@@@@@@@@@@@@@@","EEEE: "+componentInfo.getPackageName());
        if (componentInfo.getPackageName().equalsIgnoreCase("com.caterbid.customer")) {
            return true;
        } else {
           return false;
        }
   } 
   
}