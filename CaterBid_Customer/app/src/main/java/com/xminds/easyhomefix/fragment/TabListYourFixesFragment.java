package com.xminds.easyhomefix.fragment;

import android.R.color;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.constants.Constants;
 
public class TabListYourFixesFragment extends Fragment  {

	private ViewGroup container;
	private FragmentTabHost mTabHost;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.container = container;
		return inflater.inflate(R.layout.track_your_fix_fragment_tab_host, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initView();
		initManagers();
		

		// FRAGMENT TAB HOST

		mTabHost = (FragmentTabHost) container
				.findViewById(android.R.id.tabhost);
		mTabHost.setup(getActivity(), getChildFragmentManager(),
				R.id.realtabcontent);

		mTabHost.getTabWidget().setStripEnabled(false);
	
		//mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
		mTabHost.addTab(mTabHost.newTabSpec("Pending").setIndicator("PENDING"),
				PendingTabTrackYourFixFragment.class, null);

		mTabHost.addTab(mTabHost.newTabSpec("OnGoing").setIndicator("ONGOING"),
				OnGoingTabTrackYourFixFragment.class, null);

		mTabHost.addTab(mTabHost.newTabSpec("Completed&Cancelled")
				.setIndicator("COMPLETED & CANCELLED"),
				CompletedCancelledTabTrackYourFixFragment.class, null);
		
	if(EasyHomeFixApp.getCategoryNameDefaultTab()!=null){
		if(EasyHomeFixApp.getCategoryNameDefaultTab().equalsIgnoreCase(Constants.PENDING))
		{
			Log.e("","in pending");
			mTabHost.getTabWidget().setCurrentTab(0);
			mTabHost.getTabWidget().getChildAt(0)
					.setBackgroundResource(R.drawable.pending_selected);
			mTabHost.getTabWidget().setCurrentTab(0);
			mTabHost.getTabWidget().setCurrentTab(1);
			mTabHost.getTabWidget().getChildAt(1)
					.setBackgroundResource(R.drawable.ongoing_unselected);
			mTabHost.getTabWidget().setCurrentTab(2);
			mTabHost.getTabWidget().getChildAt(2)
					.setBackgroundResource(R.drawable.completed_unselected);
			mTabHost.getTabWidget().getChildAt(0).performClick();
			
			TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title); //Unselected Tabs
	        tv.setTextColor(Color.parseColor("#99CA43"));
	        
	        TextView tv1 = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title); //selected Tabs
	        tv1.setTextColor(Color.parseColor("#858585"));
	        
	        TextView tv2 = (TextView) mTabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title); //selected Tabs
	        tv2.setTextColor(Color.parseColor("#858585"));
		
		}
		else if(EasyHomeFixApp.getCategoryNameDefaultTab().equalsIgnoreCase(Constants.ONGOING))
		{
			Log.e("","in ongoing");
			mTabHost.getTabWidget().setCurrentTab(1);
			mTabHost.getTabWidget().getChildAt(0)
					.setBackgroundResource(R.drawable.pending_unselected);
			mTabHost.getTabWidget().setCurrentTab(0);
			mTabHost.getTabWidget().setCurrentTab(1);
			mTabHost.getTabWidget().getChildAt(1)
					.setBackgroundResource(R.drawable.ongoing_selected);
			mTabHost.getTabWidget().setCurrentTab(2);
			mTabHost.getTabWidget().getChildAt(2)
					.setBackgroundResource(R.drawable.completed_unselected);
			mTabHost.getTabWidget().getChildAt(1).performClick();
			TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title); //Unselected Tabs
	        tv.setTextColor(Color.parseColor("#99CA43"));
	        
	        TextView tv1 = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title); //selected Tabs
	        tv1.setTextColor(Color.parseColor("#858585"));
	        
	        TextView tv2 = (TextView) mTabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title); //selected Tabs
	        tv2.setTextColor(Color.parseColor("#858585"));
		
		}
		else if(EasyHomeFixApp.getCategoryNameDefaultTab().equalsIgnoreCase(Constants.COMPLETED))
		{
			Log.e("","in completed");
			mTabHost.getTabWidget().setCurrentTab(2);
			mTabHost.getTabWidget().getChildAt(0)
					.setBackgroundResource(R.drawable.pending_unselected);
			mTabHost.getTabWidget().setCurrentTab(0);
			mTabHost.getTabWidget().setCurrentTab(1);
			mTabHost.getTabWidget().getChildAt(1)
					.setBackgroundResource(R.drawable.ongoing_unselected);
			mTabHost.getTabWidget().setCurrentTab(2);
			mTabHost.getTabWidget().getChildAt(2)
					.setBackgroundResource(R.drawable.completed_selected);
			mTabHost.getTabWidget().getChildAt(2).performClick();
			
            
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title); //Unselected Tabs
	        tv.setTextColor(Color.parseColor("#99CA43"));
	        
	        TextView tv1 = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title); //selected Tabs
	        tv1.setTextColor(Color.parseColor("#858585"));
	        
	        TextView tv2 = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title); //selected Tabs
	        tv2.setTextColor(Color.parseColor("#858585"));
			
		
		}		
		else
		{

			Log.e("","in pending");
			mTabHost.getTabWidget().setCurrentTab(0);
			mTabHost.getTabWidget().getChildAt(0)
					.setBackgroundResource(R.drawable.pending_selected);
			mTabHost.getTabWidget().setCurrentTab(0);
			mTabHost.getTabWidget().setCurrentTab(1);
			mTabHost.getTabWidget().getChildAt(1)
					.setBackgroundResource(R.drawable.ongoing_unselected);
			mTabHost.getTabWidget().setCurrentTab(2);
			mTabHost.getTabWidget().getChildAt(2)
					.setBackgroundResource(R.drawable.completed_unselected);
			mTabHost.getTabWidget().getChildAt(0).performClick();
			
			TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title); //Unselected Tabs
	        tv.setTextColor(Color.parseColor("#99CA43"));
	        
	        TextView tv1 = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title); //selected Tabs
	        tv1.setTextColor(Color.parseColor("#858585"));
	        
	        TextView tv2 = (TextView) mTabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title); //selected Tabs
	        tv2.setTextColor(Color.parseColor("#858585"));
		
		
		}
	}
		
		mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(color.black);
		mTabHost.getTabWidget().getChildAt(1).setBackgroundColor(color.black);
		mTabHost.getTabWidget().getChildAt(2).setBackgroundColor(color.black);
		
		/*TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title); //Unselected Tabs
        tv.setTextColor(Color.parseColor("#99CA43"));
        
        TextView tv1 = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title); //selected Tabs
        tv1.setTextColor(Color.parseColor("#858585"));
        
        TextView tv2 = (TextView) mTabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title); //selected Tabs
        tv2.setTextColor(Color.parseColor("#858585"));*/
		

		// Set TabChangeListener called when tab changed
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				
			
				// TODO Auto-generated method stub
			
				/************ Called when tab changed *************/

				// ********* Check current selected tab and change according
				// images *******/

				for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
					
					mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(color.black);
				
					if ((i == 0) && (i == mTabHost.getCurrentTab())) {
						
						 mTabHost.setPressed(false);
						TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
			            tv.setTextColor(Color.parseColor("#99CA43"));
			            
			            TextView tv1 = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title); //selected Tabs
			            tv1.setTextColor(Color.parseColor("#858585"));
			            
			            TextView tv2 = (TextView) mTabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title); //selected Tabs
			            tv2.setTextColor(Color.parseColor("#858585"));
			            
						
					}
					if ((i == 1) && (i == mTabHost.getCurrentTab())) {
						
						TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
			            tv.setTextColor(Color.parseColor("#99CA43"));
			            
			            TextView tv1 = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title); //selected Tabs
			            tv1.setTextColor(Color.parseColor("#858585"));
			            
			            TextView tv2 = (TextView) mTabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title); //selected Tabs
			            tv2.setTextColor(Color.parseColor("#858585"));
						
						
					}

					if ((i == 2) && (i == mTabHost.getCurrentTab())) {
						
						TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
			            tv.setTextColor(Color.parseColor("#99CA43"));
			            
			            TextView tv1 = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title); //selected Tabs
			            tv1.setTextColor(Color.parseColor("#858585"));
			            
			            TextView tv2 = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title); //selected Tabs
			            tv2.setTextColor(Color.parseColor("#858585"));
						
						
					}
				}
			}
		});

	}

	private void initManagers() {
		// TODO Auto-generated method stub

	}

	public void initView() {
		
	}
	

}
