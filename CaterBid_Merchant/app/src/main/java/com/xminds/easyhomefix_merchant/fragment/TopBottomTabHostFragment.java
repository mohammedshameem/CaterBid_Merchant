package com.xminds.easyhomefix_merchant.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost.OnTabChangeListener;

import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.activities.TopBottomFragmentTabHostActivity;

public class TopBottomTabHostFragment extends Fragment {

	private ViewGroup container;
	static FragmentTabHost mTabHost;
	private FragmentTabHost mTabHost1;
	private ArrayList<String>categoryId = new ArrayList<String>();
	private ArrayList<String>categoryID = new ArrayList<String>();
	private TopBottomFragmentTabHostActivity topBottomFragmentTabHostActivity;
	
	public TopBottomTabHostFragment() {
	}
	
	public TopBottomTabHostFragment(ArrayList<String> category_ID,TopBottomFragmentTabHostActivity topBottomFragmentTabHostActivity) {
		// TODO Auto-generated constructor stub
		categoryID.clear();
		this.categoryID = category_ID;
		this.topBottomFragmentTabHostActivity = topBottomFragmentTabHostActivity;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.container = container;
		return inflater.inflate(R.layout.top_bottom_tabhost, null);
	}
	

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		
		mTabHost1 = (FragmentTabHost) container.findViewById(android.R.id.tabhost);
		mTabHost1.setup(getActivity(),
				getFragmentManager(), R.id.realtabcontent);
		
		mTabHost1.getTabWidget().setStripEnabled(false);
		//mTabHost1.getTabWidget().getLayoutParams().height=50;
		AvailableTabFragment availableTabFragment = new AvailableTabFragment();
		Bundle b = new Bundle();
		b.putStringArrayList("CATEGORY_ID", categoryID);
		b.putString("key", "available_fixes");
		mTabHost1.addTab(mTabHost1.newTabSpec("available_fixes").setIndicator(""),
				AvailableTabFragment.class, b);
		availableTabFragment.setArguments(b);

		Bundle b1 = new Bundle();
		b1.putString("key", "your_fixes");
		mTabHost1.addTab(mTabHost1.newTabSpec("your_fixes").setIndicator(""),
				YourFixesTabFragment.class, b1);

		Bundle b2 = new Bundle();
		b2.putString("key", "payments");
		mTabHost1.addTab(mTabHost1.newTabSpec("payments").setIndicator(""),
				PaymentsTabFragment.class, b2);
		
		mTabHost1.getTabWidget().setCurrentTab(0);
		mTabHost1.getTabWidget().getChildAt(0)
				.setBackgroundResource(R.drawable.available_selected);
		// mTabHost1.getTabWidget().setCurrentTab(0);
		mTabHost1.getTabWidget().setCurrentTab(1);
		mTabHost1.getTabWidget().getChildAt(1)
				.setBackgroundResource(R.drawable.fix_unselected);
		mTabHost1.getTabWidget().setCurrentTab(2);
		mTabHost1.getTabWidget().getChildAt(2)
				.setBackgroundResource(R.drawable.payment_unselected);

	
		mTabHost1.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				if(tabId=="available_fixes"){
					//heading.setText(R.string.available_fixes);	
					mTabHost1.getTabWidget().setCurrentTab(0);
					mTabHost1.getTabWidget().getChildAt(0)
							.setBackgroundResource(R.drawable.available_selected);
					mTabHost1.getTabWidget().setCurrentTab(1);
					mTabHost1.getTabWidget().getChildAt(1)
							.setBackgroundResource(R.drawable.fix_unselected);
					mTabHost1.getTabWidget().setCurrentTab(2);
					mTabHost1.getTabWidget().getChildAt(2)
							.setBackgroundResource(R.drawable.payment_unselected);
					if(topBottomFragmentTabHostActivity != null){
						topBottomFragmentTabHostActivity.setPageTitle("Available Fixes");
					}
				}
				if(tabId=="your_fixes"){
					//heading.setText(R.string.your_fixes);	
					mTabHost1.getTabWidget().setCurrentTab(0);
					mTabHost1.getTabWidget().getChildAt(0)
							.setBackgroundResource(R.drawable.available_unselected);
					mTabHost1.getTabWidget().setCurrentTab(1);
					mTabHost1.getTabWidget().getChildAt(1)
							.setBackgroundResource(R.drawable.fix_selected);
					mTabHost1.getTabWidget().setCurrentTab(2);
					mTabHost1.getTabWidget().getChildAt(2)
							.setBackgroundResource(R.drawable.payment_unselected);
					if(topBottomFragmentTabHostActivity != null){
						topBottomFragmentTabHostActivity.setPageTitle("Your Fixes");
					}
				}
				if(tabId=="payments"){
					//heading.setText(R.string.payments);	
					mTabHost1.getTabWidget().setCurrentTab(0);
					mTabHost1.getTabWidget().getChildAt(0)
							.setBackgroundResource(R.drawable.available_unselected);
					mTabHost1.getTabWidget().setCurrentTab(1);
					mTabHost1.getTabWidget().getChildAt(1)
							.setBackgroundResource(R.drawable.fix_unselected);
					mTabHost1.getTabWidget().setCurrentTab(2);
					mTabHost1.getTabWidget().getChildAt(2)
							.setBackgroundResource(R.drawable.payment_selected);
					if(topBottomFragmentTabHostActivity != null){
						topBottomFragmentTabHostActivity.setPageTitle("Payments");
					}
				}
			}
		});
		
	
	
	}


}
