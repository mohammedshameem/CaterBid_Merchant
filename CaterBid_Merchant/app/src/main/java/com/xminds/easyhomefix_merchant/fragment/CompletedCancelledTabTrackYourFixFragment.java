package com.xminds.easyhomefix_merchant.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.xminds.easyfix_merchant.constants.Constants;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.activities.ReviewServiceActivity;
import com.xminds.easyhomefix_merchant.activities.TrackFixFragmentTabhostActivity;
import com.xminds.easyhomefix_merchant.adapter.CompletedCancelledAdapter;


public class CompletedCancelledTabTrackYourFixFragment extends Fragment{
	
	private View viewroot;
	private ListView completedCancelledListview;
	private List<Integer> completedCancelledList = new ArrayList<Integer>();
	private CompletedCancelledAdapter completedCancelledAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		viewroot = inflater.inflate(R.layout.completed_cancelled_layout, null);
		
		initView();
		initManager();
		completedCancelledList.clear();
		
		for(int i=0;i<5;i++){
			
			completedCancelledList.add(i);
		   //completedCancelledAdapter = new CompletedCancelledAdapter(getActivity(),CompletedCancelledTabTrackYourFixFragment.this,completedCancelledList);
			completedCancelledListview.setAdapter(completedCancelledAdapter);
		}
		
		completedCancelledListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent fixIntent = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
				fixIntent.putExtra(Constants.COMPLETED, "completed");
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
		completedCancelledListview = (ListView)viewroot.findViewById(R.id.completed_cancelled_list);
		
	}

	public void gotoFixTab() {
		// TODO Auto-generated method stub
		
		Intent fixIntent = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
		startActivity(fixIntent);
		
	}

	public void addReview() {
		// TODO Auto-generated method stub
		
		Intent fixIntent = new Intent(getActivity(),ReviewServiceActivity.class);
		startActivity(fixIntent);
		
	}
	
	

}
