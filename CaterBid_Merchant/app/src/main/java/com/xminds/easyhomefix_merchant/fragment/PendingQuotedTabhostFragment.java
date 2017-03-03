package com.xminds.easyhomefix_merchant.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easyhomefix.R;

public class PendingQuotedTabhostFragment extends Fragment {

	private FragmentTabHost mTabHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		mTabHost = new FragmentTabHost(getActivity());

	//	mTabHost = (FragmentTabHost)container. findViewById(R.id.tabcontent);
		 //View rootView = inflater.inflate(R.layout.pending_quote_tab_layout,container,false);
		 //mTabHost = (FragmentTabHost)rootView.findViewById(R.id.tabhostPendingQuote);
		 mTabHost.setup(getActivity(), getChildFragmentManager(),android.R.id.tabhost);
		 mTabHost.getTabWidget().setStripEnabled(false);
		 mTabHost.addTab(mTabHost.newTabSpec("pending").setIndicator(""),AvailableTabFragment.class, null);
	     mTabHost.addTab(mTabHost.newTabSpec("quoted").setIndicator(""),OnGoingTabTrackYourFixFragment.class, null);
	     
	     mTabHost.getTabWidget().setCurrentTab(0);
	     mTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.newpending_selected);
	     mTabHost.getTabWidget().setCurrentTab(0);
	     mTabHost.getTabWidget().setCurrentTab(1);
	     mTabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.quoted_unselected);
	     
		return mTabHost;
	}
	
}
