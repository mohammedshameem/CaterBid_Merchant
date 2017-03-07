package com.xminds.easyhomefix.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.actionbar.BaseActivity;
import com.xminds.easyhomefix.app.EasyHomeFixApp;


public class CollectAccountDetailsActivity extends Activity {
    private Button submit_button;
    private ImageView avatar;
    private ImageView close_button;
    private LogoutReceiver logoutReceiver;

    public class LogoutReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.package.ACTION_LOGOUT")) {
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_account_details);

        initViews();
        initManagers();
        setVisibilities();
        logoutReceiver = new LogoutReceiver();//logout broadcast receiver
        IntentFilter intentFilter = new IntentFilter();  // Register the logout receiver
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(logoutReceiver, intentFilter);


        submit_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(CollectAccountDetailsActivity.this, CustomerPaymentSelectionActivity.class);
                startActivity(i);
            }
        });

        close_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
    }

    private void setVisibilities() {
        // TODO Auto-generated method stub
        avatar.setVisibility(View.GONE);
        close_button.setVisibility(View.VISIBLE);

    }

    private void initManagers() {
        // TODO Auto-generated method stub

    }
    private void initViews() {
        // TODO Auto-generated method stub
        submit_button = (Button) findViewById(R.id.collect_submit_button);
        avatar = (ImageView) findViewById(R.id.image_avatar);
        close_button = (ImageView) findViewById(R.id.image_close);
    }

    /**
     * @param email
     * @return
     */
}
