package com.xminds.easyhomefix_merchant.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.xminds.easyfix_merchant.constants.Constants;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.activities.TrackFixFragmentTabhostActivity;
import com.xminds.easyhomefix_merchant.adapter.OngoingAdapter;

public class OnGoingTabTrackYourFixFragment extends Fragment{
	
private View viewroot;
private ListView ongoingListview;
private List<Integer> ongoingList = new ArrayList<Integer>();
private OngoingAdapter ongoingAdapter;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		viewroot = inflater.inflate(R.layout.ongoing_tab_layout, null);
		
		initView();
		initManager();
		ongoingList.clear();
		
		for(int i=0;i<5;i++){
			
			ongoingList.add(i);
			ongoingAdapter = new OngoingAdapter(getActivity(),OnGoingTabTrackYourFixFragment.this,ongoingList);
			ongoingListview.setAdapter(ongoingAdapter);
		}
		
		ongoingListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent fixIntent = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
				fixIntent.putExtra(Constants.ONGOING, "ongoing");
				startActivity(fixIntent);
			}
		});
		
				
		return viewroot;
	}

	public void initManager() {
		// TODO Auto-generated method stub
		
		
	}

	public void initView() {
		// TODO Auto-generated method stub
		ongoingListview = (ListView)viewroot.findViewById(R.id.ongoing_list);
		
	}
	
	public class FixFragmentTabHostFragment extends Fragment{
		
		private FragmentTabHost mTabHost;
		private View view;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			
			view = inflater.inflate(R.layout.fix_details_fragment_tab_host, null);
			
			
			
			
			return mTabHost;
		}
		
	}

	public void ongoingFragmentTabHost() {
		// TODO Auto-generated method stub
		Intent fixIntent = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
		startActivity(fixIntent);
	}
	

}
