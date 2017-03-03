package com.xminds.easyhomefix.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Holder.Job;
import com.xminds.easyhomefix.Holder.JobList;
import com.xminds.easyhomefix.Holder.JobNotifications;
import com.xminds.easyhomefix.activities.EmptyFixQuoteActivity;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.constants.Constants;
import com.xminds.easyhomefix.swipetohide.SwipeDismissTouchListener;

public class TabFixTabHostFragment extends Fragment {
	private ViewGroup container;
	private FragmentTabHost mTabHost;
	private ViewGroup dismissableContainer;
	private View topBanner;
	private TextView add_contact_text;
	private ImageView add_icondollar_ImageView;
	private ImageView add_icon;
	private ImageView add_iconavatarwithplus;
	private JobList jobList;
	private Job notificationListObject;

	public TabFixTabHostFragment(JobList jobList, Job notificationListObject) {
		// TODO Auto-generated constructor stub
		
		this.jobList = jobList;
		this.notificationListObject = notificationListObject;
		
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initView();
		initManager();

		mTabHost = (FragmentTabHost) container
				.findViewById(android.R.id.tabhost);
		mTabHost.setup(getActivity(), getChildFragmentManager(),
				R.id.realtabcontent_fix);

		mTabHost.getTabWidget().setStripEnabled(false);

		Bundle b = new Bundle();
		b.putSerializable("PENDINGOBJECT", jobList);
		b.putSerializable("ONGOINGOBJECT", jobList);
		b.putSerializable("job", jobList);
		b.putSerializable("notification_chat", notificationListObject);
		b.putSerializable("notification_ongoing", notificationListObject);
		b.putSerializable("notification_completed", notificationListObject);
		b.putSerializable("From_notification_pending", notificationListObject);
		
		//NOTIFICATION OBJECT FROM CHAT
		
		mTabHost.addTab(mTabHost.newTabSpec("Chat").setIndicator(""),
				ChatTabFragment.class, b);

		Bundle b1 = new Bundle();
		/*b1.putString("key", "jobid");
		b1.putString("key", "quoteid");*/
		b1.putSerializable("PENDINGOBJECT", jobList);
		b1.putSerializable("ONGOINGOBJECT", jobList);
		b1.putSerializable("job", jobList);
		b1.putSerializable("notification_chat", notificationListObject);
		b1.putSerializable("notification_ongoing", notificationListObject);
		b1.putSerializable("notification_completed", notificationListObject);
		b1.putSerializable("From_notification_pending", notificationListObject);
		
		//NOTIFICATION OBJECT FROM  ONGOING
		 //NOTIFICATION OBJECT FROM COMPLETE
		
		mTabHost.addTab(mTabHost.newTabSpec("Details").setIndicator(""),
				DetailsTabFragment.class, b1);

		/*mTabHost.addTab(mTabHost.newTabSpec("Users").setIndicator(""),
				UserTabFragment.class, null);*/

		if(EasyHomeFixApp.getCategoryNameDefaultTab().equalsIgnoreCase(Constants.CHAT)){

			//Set Chat Tab as Default
			mTabHost.getTabWidget().setCurrentTab(0);
			mTabHost.getTabWidget().getChildAt(0)
					.setBackgroundResource(R.drawable.chat_tab_selected);
			mTabHost.getTabWidget().setCurrentTab(0);
			mTabHost.getTabWidget().setCurrentTab(1);
			mTabHost.getTabWidget().getChildAt(1)
					.setBackgroundResource(R.drawable.detail_tab_unselected);
			mTabHost.getTabWidget().setCurrentTab(2);
			/*mTabHost.getTabWidget().getChildAt(2)
					.setBackgroundResource(R.drawable.users_tab_unselected);*/
			topBanner.setVisibility(View.GONE);
			mTabHost.getTabWidget().getChildAt(0).performClick();
		}
		else if(EasyHomeFixApp.getCategoryNameDefaultTab().equalsIgnoreCase(Constants.DETAILS)){

			//Set DETAILS Tab as Default
			mTabHost.getTabWidget().setCurrentTab(1);
			mTabHost.getTabWidget().getChildAt(0)
					.setBackgroundResource(R.drawable.chat_tab_unselected);
			mTabHost.getTabWidget().setCurrentTab(0);
			mTabHost.getTabWidget().setCurrentTab(1);
			mTabHost.getTabWidget().getChildAt(1)
					.setBackgroundResource(R.drawable.detail_tab_selected);
			mTabHost.getTabWidget().setCurrentTab(2);
			/*mTabHost.getTabWidget().getChildAt(2)
					.setBackgroundResource(R.drawable.users_tab_unselected);*/
			topBanner.setVisibility(View.GONE);
			mTabHost.getTabWidget().getChildAt(1).performClick();
			
		
			
			
		}
		/*else if(EasyHomeFixApp.getCategoryNameDefaultTab().equalsIgnoreCase(Constants.USERS)){

			//Set USERS Tab as Default
			mTabHost.getTabWidget().setCurrentTab(2);
			mTabHost.getTabWidget().getChildAt(0)
					.setBackgroundResource(R.drawable.chat_tab_unselected);
			mTabHost.getTabWidget().setCurrentTab(0);
			mTabHost.getTabWidget().setCurrentTab(1);
			mTabHost.getTabWidget().getChildAt(1)
					.setBackgroundResource(R.drawable.detail_tab_unselected);
			mTabHost.getTabWidget().setCurrentTab(2);
			mTabHost.getTabWidget().getChildAt(2)
					.setBackgroundResource(R.drawable.users_tab_selected);
			topBanner.setVisibility(View.VISIBLE);
			mTabHost.getTabWidget().getChildAt(2).performClick();
		}*/
		
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
						/*mTabHost.getTabWidget()
								.getChildAt(2)
								.setBackgroundResource(
										R.drawable.users_tab_unselected);
						mTabHost.getTabWidget().setCurrentTab(2);*/

					}
					if ((i == 1) && (i == mTabHost.getCurrentTab())) {
						mTabHost.getTabWidget()
								.getChildAt(i)
								.setBackgroundResource(
										R.drawable.detail_tab_selected);
						mTabHost.getTabWidget().setCurrentTab(i);
						/*mTabHost.getTabWidget()
								.getChildAt(2)
								.setBackgroundResource(
										R.drawable.users_tab_unselected);
						mTabHost.getTabWidget().setCurrentTab(2);*/
						mTabHost.getTabWidget()
								.getChildAt(0)
								.setBackgroundResource(
										R.drawable.chat_tab_unselected);
						mTabHost.getTabWidget().setCurrentTab(0);
						
						
					}

					/*if ((i == 2) && (i == mTabHost.getCurrentTab())) {
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
					}*/
				}
			}
		});

		
		
		topBanner.setOnTouchListener(new SwipeDismissTouchListener(
				topBanner,
                null,
                new SwipeDismissTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(Object token) {
                        return true;
                    }

                    @Override
                    public void onDismiss(View view, Object token) {
                        dismissableContainer.removeView(topBanner);
                    }
                }));
		dismissableContainer.addView(topBanner);
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(Constants.FROM_COMPLETED_PAID)){
			add_contact_text.setText("Congratulation! Your Fix has been complete. Make payment to \"Merchant name\".");
			add_contact_text.setTextSize(13);
			add_icondollar_ImageView.setVisibility(View.VISIBLE);
			add_icon.setVisibility(View.GONE);
			
			
		}
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(Constants.FROM_COMPLETED_PENDING)){
			add_contact_text.setText("Congratulation! Your Fix has been complete. Make payment to \"Merchant name\".");
			add_contact_text.setTextSize(13);
			add_icondollar_ImageView.setVisibility(View.VISIBLE);
			add_icon.setVisibility(View.GONE);
		}
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(Constants.FROM_COMPLETED_CANCELLED)){
			add_contact_text.setText("Congratulation! Your Fix has been complete. Make payment to \"Merchant name\".");
			add_contact_text.setTextSize(13);
			add_icondollar_ImageView.setVisibility(View.VISIBLE);
			add_icon.setVisibility(View.GONE);
		}
		
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(Constants.FROM_ONGOING)){
			add_iconavatarwithplus.setVisibility(View.VISIBLE);
			add_icon.setVisibility(View.GONE);
			
		}
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.e("","@@@@@@@@@@@@@@@@@@@@@@@inonactivity@@@@@@@@@@@@@");
		 Fragment fragment = getChildFragmentManager().findFragmentById(R.id.realtabcontent_fix);
			fragment.onActivityResult(requestCode, resultCode, data);
/*			
			Intent i = new Intent();
            i.putExtra("data", data);
            startActivity(i);*/
            
		super.onActivityResult(requestCode, resultCode, data);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.container = container;
		return inflater.inflate(R.layout.fix_details_fragment_tab_host, null);
	}

	private void initManager() {
		// TODO Auto-generated method stub

	}

	private void initView() {
		dismissableContainer = (ViewGroup)container. findViewById(R.id.add_contact_layout_id);
		//addContactLayout=(LinearLayout)findViewById(R.id.add_contact_outer_layout);
		topBanner = LayoutInflater.from(getActivity()).inflate(R.layout.add_contact_top_banner, null);
		add_contact_text=(TextView)topBanner.findViewById(R.id.add_contact_text);
		add_icondollar_ImageView=(ImageView)topBanner.findViewById(R.id.add_icondollar);
		add_icon=(ImageView)topBanner.findViewById(R.id.add_icon);
		add_iconavatarwithplus=(ImageView)topBanner.findViewById(R.id.add_iconavatarwithplus);
	}
	

}
