package com.xminds.easyhomefix_merchant.fragment;

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

import com.xminds.easyfix_merchant.constants.Constants;
import com.xminds.easyfix_merchant.swipetohide.SwipeDismissTouchListener;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Holder.AvailableJobs;
import com.xminds.easyhomefix_merchant.Holder.Job;
import com.xminds.easyhomefix_merchant.Holder.Quote;
import com.xminds.easyhomefix_merchant.Holder.jobNotifications;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;

public class TabFixTabHostFragment extends Fragment {
	private ViewGroup container;
	private FragmentTabHost mTabHost;
	private ViewGroup dismissableContainer;
	private View topBanner;
	private TextView add_contact_text;
	private ImageView add_icondollar_ImageView;
	private ImageView add_icon;
	private ImageView add_iconavatarwithplus;
	private AvailableJobs availableJobs;
	//private jobNotifications notificationobject;
	private Job notificationobject;
	private Quote quoteObject;
	
	public TabFixTabHostFragment(AvailableJobs availableJobs, Job notificationobject, Quote quoteObject)
	{
		this.availableJobs=availableJobs;
		this.notificationobject = notificationobject;
		this.quoteObject = quoteObject;
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
		b.putSerializable("AVAILABLE_OBJECT", availableJobs);
		//b.putSerializable("QUOTED_JOBS", availableJobs);
		b.putSerializable("job", availableJobs);
		//b.putSerializable("notification", notificationobject);
		b.putSerializable("QUOTE_OBJECT", quoteObject );
		b.putSerializable("QUOTED_OBJECT_FROM_QUOTE", availableJobs );
		b.putSerializable("QUOTED_OBJECT_FROM_ONGOING", availableJobs );
		b.putSerializable("QUOTED_OBJECT_FROM_COMPLETE", availableJobs );
		b.putSerializable("notification_chat", notificationobject);
		b.putSerializable("notification_ongoing", notificationobject);
		b.putSerializable("notification_completed", notificationobject);
		b.putSerializable("notification_cancelled", notificationobject);
		b.putSerializable("From_notification_pending", notificationobject);
	
		//Log.d("","NOTIFICATION USERID"+notificationobject.getUserId());
		mTabHost.addTab(mTabHost.newTabSpec("Chat").setIndicator(""),
				ChatTabFragment.class, b);

		Bundle b1 = new Bundle();
		b1.putSerializable("AVAILABLE_OBJECT", availableJobs);
		//b1.putSerializable("QUOTED_JOBS", availableJobs);
		b1.putSerializable("job", availableJobs);
		//b1.putSerializable("notification", notificationobject);
		b1.putSerializable("QUOTE_OBJECT", quoteObject );
		b1.putSerializable("QUOTED_OBJECT_FROM_QUOTE", availableJobs );
		b1.putSerializable("QUOTED_OBJECT_FROM_ONGOING", availableJobs );
		b1
		.putSerializable("QUOTED_OBJECT_FROM_COMPLETE", availableJobs );
		b1.putSerializable("notification_chat", notificationobject);
		b1.putSerializable("notification_ongoing", notificationobject);
		b1.putSerializable("notification_completed", notificationobject);
		b1.putSerializable("notification_cancelled", notificationobject);
		b1.putSerializable("From_notification_pending", notificationobject);
		
		
		mTabHost.addTab(mTabHost.newTabSpec("Details").setIndicator(""),
				DetailsTabFragment.class, b);

		/*mTabHost.addTab(mTabHost.newTabSpec("Users").setIndicator(""),
				UserTabFragment.class, null);
*/
		mTabHost.getTabWidget().setCurrentTab(0);
		mTabHost.getTabWidget().getChildAt(0)
				.setBackgroundResource(R.drawable.chat_tab_selected);
		mTabHost.getTabWidget().setCurrentTab(0);
		mTabHost.getTabWidget().setCurrentTab(1);
		mTabHost.getTabWidget().getChildAt(1)
				.setBackgroundResource(R.drawable.detail_tab_unselected);
		//mTabHost.getTabWidget().setCurrentTab(2);
		/*mTabHost.getTabWidget().getChildAt(2)
				.setBackgroundResource(R.drawable.users_tab_unselected);*/

		
		if(EasyHomeFixApp.getCategoryNameDefaultTab().equalsIgnoreCase(Constants.CHAT)){

			//Set Chat Tab as Default
			mTabHost.getTabWidget().setCurrentTab(0);
			mTabHost.getTabWidget().getChildAt(0)
					.setBackgroundResource(R.drawable.chat_tab_selected);
			mTabHost.getTabWidget().setCurrentTab(0);
			mTabHost.getTabWidget().setCurrentTab(1);
			mTabHost.getTabWidget().getChildAt(1)
					.setBackgroundResource(R.drawable.detail_tab_unselected);
			//Log.d("","NOTIFICATION SIZE"+notificationobject.getId());
			//mTabHost.getTabWidget().setCurrentTab(2);
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
			//Log.d("","NOTIFICATION SIZE"+notificationobject.getId());
			//mTabHost.getTabWidget().setCurrentTab(2);
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
			topBanner.setVisibility(View.GONE);
			mTabHost.getTabWidget().getChildAt(2).performClick();
		}
		*/
		
		
		
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
		
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(Constants.COMPLETED_CANCELLED) || 
				EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(Constants.COMPLETED_CANCELLED_CANCELLED)||
				EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(Constants.COMPLETED_CANCELLED_PENDING)){
			dismissableContainer.removeView(topBanner);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		 Fragment fragment = getChildFragmentManager().findFragmentById(R.id.realtabcontent_fix);
			fragment.onActivityResult(requestCode, resultCode, data);
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
