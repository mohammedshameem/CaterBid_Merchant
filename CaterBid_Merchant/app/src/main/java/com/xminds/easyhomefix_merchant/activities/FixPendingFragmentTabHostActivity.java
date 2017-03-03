package com.xminds.easyhomefix_merchant.activities;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.xminds.easyfix_merchant.constants.Constants;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.fragment.ChatTabFragment;
import com.xminds.easyhomefix_merchant.fragment.DetailsTabFragment;
import com.xminds.easyhomefix_merchant.fragment.UserTabFragment;

public class FixPendingFragmentTabHostActivity extends FragmentActivity {
	private LinearLayout add_contact;
	private FragmentTabHost mTabHost;
	private ImageView image_back;
	private TextView textview_trackyourfix;
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
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.fix_details_fragment_tab_host);

		initView();
		initManager();

		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		
		
		image_back.setVisibility(View.VISIBLE);
		textview_trackyourfix.setVisibility(View.VISIBLE);

		textview_trackyourfix.setText("Category Name");

		image_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();
			}
		});

		add_contact = (LinearLayout) findViewById(R.id.add_contact_layout_id);

		if (getIntent().hasExtra(Constants.PENDING)) {

			add_contact.setVisibility(View.GONE);
			
		}
		else if(getIntent().hasExtra(Constants.COMPLETED)){
			
			add_contact.setVisibility(View.VISIBLE);
			
		}else if(getIntent().hasExtra(Constants.ONGOING)){
			
			add_contact.setVisibility(View.VISIBLE);
			
		}

		// FRAGMENT TAB HOST

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(FixPendingFragmentTabHostActivity.this,
				getSupportFragmentManager(), R.id.realtabcontent);

		mTabHost.getTabWidget().setStripEnabled(false);
		mTabHost.getTabWidget().getLayoutParams().height = 100;

		mTabHost.addTab(mTabHost.newTabSpec("Chat").setIndicator(""),
				ChatTabFragment.class, null);

		mTabHost.addTab(mTabHost.newTabSpec("Details").setIndicator(""),
				DetailsTabFragment.class, null);

		mTabHost.addTab(mTabHost.newTabSpec("Users").setIndicator(""),
				UserTabFragment.class, null);

		mTabHost.getTabWidget().setCurrentTab(0);
		mTabHost.getTabWidget().getChildAt(0)
				.setBackgroundResource(R.drawable.chat_tab_selected);
		mTabHost.getTabWidget().setCurrentTab(0);
		mTabHost.getTabWidget().setCurrentTab(1);
		mTabHost.getTabWidget().getChildAt(1)
				.setBackgroundResource(R.drawable.detail_tab_unselected);
		mTabHost.getTabWidget().setCurrentTab(2);
		mTabHost.getTabWidget().getChildAt(2)
				.setBackgroundResource(R.drawable.users_tab_unselected);

		// Set TabChangeListener called when tab changed
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub

				/************ Called when tab changed *************/

				// ********* Check current selected tab and change according
				// images *******/

				for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {

					if ((i == 0) && (i == mTabHost.getCurrentTab())) {
						mTabHost.getTabWidget()
								.getChildAt(i)
								.setBackgroundResource(
										R.drawable.chat_tab_selected);
						mTabHost.getTabWidget().setCurrentTab(i);
						mTabHost.getTabWidget()
								.getChildAt(1)
								.setBackgroundResource(
										R.drawable.detail_tab_unselected);
						mTabHost.getTabWidget().setCurrentTab(1);
						mTabHost.getTabWidget()
								.getChildAt(2)
								.setBackgroundResource(
										R.drawable.users_tab_unselected);
						mTabHost.getTabWidget().setCurrentTab(2);

					}
					if ((i == 1) && (i == mTabHost.getCurrentTab())) {
						mTabHost.getTabWidget()
								.getChildAt(i)
								.setBackgroundResource(
										R.drawable.detail_tab_selected);
						mTabHost.getTabWidget().setCurrentTab(i);
						mTabHost.getTabWidget()
								.getChildAt(2)
								.setBackgroundResource(
										R.drawable.users_tab_unselected);
						mTabHost.getTabWidget().setCurrentTab(2);
						mTabHost.getTabWidget()
								.getChildAt(0)
								.setBackgroundResource(
										R.drawable.chat_tab_unselected);
						mTabHost.getTabWidget().setCurrentTab(0);
					}

					if ((i == 2) && (i == mTabHost.getCurrentTab())) {
						mTabHost.getTabWidget()
								.getChildAt(i)
								.setBackgroundResource(
										R.drawable.users_tab_selected);
						mTabHost.getTabWidget().setCurrentTab(i);
						mTabHost.getTabWidget()
								.getChildAt(0)
								.setBackgroundResource(
										R.drawable.chat_tab_unselected);
						mTabHost.getTabWidget().setCurrentTab(0);
						mTabHost.getTabWidget()
								.getChildAt(1)
								.setBackgroundResource(
										R.drawable.detail_tab_unselected);
						mTabHost.getTabWidget().setCurrentTab(1);
					}
				}
			}
		});

	}
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	unregisterReceiver(logoutReceiver);
	super.onDestroy();
}
	private void initManager() {
		// TODO Auto-generated method stub

	}

	private void initView() {
		// TODO Auto-generated method stub

		image_back = (ImageView) findViewById(R.id.image_back);
		textview_trackyourfix = (TextView) findViewById(R.id.textview_trackyourfix);
	}

}
