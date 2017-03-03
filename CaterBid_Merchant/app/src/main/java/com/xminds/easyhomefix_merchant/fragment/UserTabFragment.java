package com.xminds.easyhomefix_merchant.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easyhomefix.R;

public class UserTabFragment extends Fragment{

	private View viewroot;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		viewroot = inflater.inflate(R.layout.review_service_layout, null);
		/*String value = getArguments().getString("key");
		
		if(value.equalsIgnoreCase("User")){
			
			
		}*/
		return viewroot;
	}
	
}
