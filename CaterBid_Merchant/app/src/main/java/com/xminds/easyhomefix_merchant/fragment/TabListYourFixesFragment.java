package com.xminds.easyhomefix_merchant.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost.OnTabChangeListener;

import com.easyhomefix.R;

public class TabListYourFixesFragment extends Fragment {

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

		/*mTabHost.addTab(mTabHost.newTabSpec("Pending").setIndicator(""),
				PendingTabTrackYourFixFragment.class, null);*/

		mTabHost.addTab(mTabHost.newTabSpec("OnGoing").setIndicator(""),
				OnGoingTabTrackYourFixFragment.class, null);

		mTabHost.addTab(mTabHost.newTabSpec("Completed&Cancelled")
				.setIndicator(""),
				CompletedCancelledTabTrackYourFixFragment.class, null);

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
										R.drawable.pending_selected);
						mTabHost.getTabWidget().setCurrentTab(i);
						mTabHost.getTabWidget()
								.getChildAt(1)
								.setBackgroundResource(
										R.drawable.ongoing_unselected);
						mTabHost.getTabWidget().setCurrentTab(1);
						mTabHost.getTabWidget()
								.getChildAt(2)
								.setBackgroundResource(
										R.drawable.completed_unselected);
						mTabHost.getTabWidget().setCurrentTab(2);

					}
					if ((i == 1) && (i == mTabHost.getCurrentTab())) {
						mTabHost.getTabWidget()
								.getChildAt(i)
								.setBackgroundResource(
										R.drawable.ongoing_selected);
						mTabHost.getTabWidget().setCurrentTab(i);
						mTabHost.getTabWidget()
								.getChildAt(2)
								.setBackgroundResource(
										R.drawable.completed_unselected);
						mTabHost.getTabWidget().setCurrentTab(2);
						mTabHost.getTabWidget()
								.getChildAt(0)
								.setBackgroundResource(
										R.drawable.pending_unselected);
						mTabHost.getTabWidget().setCurrentTab(0);
					}

					if ((i == 2) && (i == mTabHost.getCurrentTab())) {
						mTabHost.getTabWidget()
								.getChildAt(i)
								.setBackgroundResource(
										R.drawable.completed_selected);
						mTabHost.getTabWidget().setCurrentTab(i);
						mTabHost.getTabWidget()
								.getChildAt(0)
								.setBackgroundResource(
										R.drawable.pending_unselected);
						mTabHost.getTabWidget().setCurrentTab(0);
						mTabHost.getTabWidget()
								.getChildAt(1)
								.setBackgroundResource(
										R.drawable.ongoing_unselected);
						mTabHost.getTabWidget().setCurrentTab(1);
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
