package com.xminds.easyhomefix.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.paypal.android.sdk.s;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.JobCategoriesBaseHolder;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.JobNotifications;
import com.xminds.easyhomefix.Holder.JobSubCategoriesBaseHolder;
import com.xminds.easyhomefix.Managers.CategoriesManager;
import com.xminds.easyhomefix.Managers.NotificationManager;
import com.xminds.easyhomefix.adapter.NotificationsAdapter;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class SplashScreenActivity extends Activity {

    private List<String> ListIds = new ArrayList<String>();
    private int requestcode = 0;
    private JobCategoriesCallBack jobCategoriesCallBack;
    private ProgressDialog pDialog;
    private List<JobNotifications> notificationlist = new ArrayList<JobNotifications>();
    private NotificationCallBack notificationCallBack;
    private int limit = 10;
    private int offset = 0;
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

        ListIds = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "1000");
        EasyHomeFixApp.setListIds(null);
        EasyHomeFixApp.setListIds(ListIds);
        EasyHomeFixApp.setCount("0");
        initManagers();


        logoutReceiver = new LogoutReceiver(); //logout broadcast receiver
        IntentFilter intentFilter = new IntentFilter(); // Register the logout receiver
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(logoutReceiver, intentFilter);

        Utils.showHashKey(SplashScreenActivity.this);
        pDialog = ProgressDialog.show(SplashScreenActivity.this, "",
                "Loading..", true);

        CategoriesManager.getInstance().getCategories(
                SplashScreenActivity.this, jobCategoriesCallBack, requestcode);

        if (CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable()) {
            NotificationManager.getInstance().getNotificationList(SplashScreenActivity.this, limit, offset, notificationCallBack, requestcode);
        } else {
            getRegId();
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, LandingPageActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            handler.postDelayed(runnable, 5000);
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
    }


    public void getRegId() {

        final String PROJECT_NUMBER = "367842739261";

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regid = gcm.register(PROJECT_NUMBER);
                } catch (IOException ex) {
                    Log.e(TAG, "Error :" + ex.getMessage());

                }
                return regid;
            }

            @Override
            protected void onPostExecute(String regid) {
                Log.e(TAG, "REGID: " + regid);
                if (regid != null) {
                    EasyHomeFixApp.setGcmRegId(regid);
                }
            }
        }.execute(null, null, null);
    }

    private void initViews() {
        // TODO Auto-generated method stub

    }

    private void initManagers() {
        // TODO Auto-generated method stub
        jobCategoriesCallBack = new JobCategoriesCallBack();
        notificationCallBack = new NotificationCallBack();
    }

    public class JobCategoriesCallBack implements AsyncTaskCallBack {
        @Override
        public void onFinish(int responseCode, Object result) {
            if (pDialog != null) {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }
            }
            if (responseCode == requestcode) {
                JobCategoriesBaseHolder jobCategoriesBaseHolder = (JobCategoriesBaseHolder) result;
                if (UtilValidate.isNotNull(jobCategoriesBaseHolder)) {
                    if (jobCategoriesBaseHolder.isSuccess()) {
                        if (UtilValidate.isNotNull(jobCategoriesBaseHolder.getData()))
                            EasyHomeFixApp.setJobcategories(null);
                        EasyHomeFixApp.setJobcategories(jobCategoriesBaseHolder.getData());
                    } else {
                        Toast.makeText(SplashScreenActivity.this, jobCategoriesBaseHolder.isSuccess() + "", Toast.LENGTH_LONG).show();
                    }
                }
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {
            // TODO Auto-generated method stub
            if (pDialog != null) {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

            }
            if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
                Utils.showDialog(SplashScreenActivity.this, "Please check your internet connection.", "ERROR");
            } else {
                Utils.showDialog(SplashScreenActivity.this, "" + result, "ERROR");
            }

        }

        @Override
        public void onFinish(int requestcode,
                             JobCreationBaseHolder jobCreationBaseHolder, int i) {
            // TODO Auto-generated method stub

        }

    }

    public class NotificationCallBack implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {
            // TODO Auto-generated method stub

            if (responseCode == requestcode) {
                EmailSignupBaseHolder notificationBaseHolder = (EmailSignupBaseHolder) result;
                if (UtilValidate.isNotNull(notificationBaseHolder)) {
                    if (notificationBaseHolder.isSuccess()) {
                        if (UtilValidate.isNotNull(notificationBaseHolder.getData())) {
                            if (UtilValidate.isNotNull(notificationBaseHolder.getData().getJobNotifications())) {
                                if (UtilValidate.isNotEmpty(notificationBaseHolder.getData().getJobNotifications())) {
                                    notificationlist.clear();
                                    notificationlist = notificationBaseHolder.getData().getJobNotifications();

                                    EasyHomeFixApp.setJobNotifications(notificationlist);
                                    EasyHomeFixApp.setNotificationcount(notificationlist.get(0).getRecieverUser().getUnreadNotifications() + "");
                                }
                            }
                        }

                        Handler handler = new Handler();
                        Runnable runnable = new Runnable() {

                            @Override
                            public void run() {
                                // start thread
                                Intent intent = new Intent(SplashScreenActivity.this, LandingPageActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        };
                        handler.postDelayed(runnable, 1000);
                    }
                }
            }
        }

        @Override
        public void onFinish(int responseCode, String result) {
            // TODO Auto-generated method stub

            if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
                Utils.showDialog(SplashScreenActivity.this, "Please check your internet connection.", "ERROR");
            }
        }

        @Override
        public void onFinish(int requestcode,
                             JobCreationBaseHolder jobCreationBaseHolder, int i) {
            // TODO Auto-generated method stub

        }
    }
}
