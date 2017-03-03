package com.xminds.easyhomefix_merchant.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.fragment.OnGoingTabTrackYourFixFragment;

public class OngoingAdapter extends BaseAdapter {
	
	private Activity activity;
	private List<Integer> ongoingList = new ArrayList<Integer>();
	private LayoutInflater inflater;
	private OnGoingTabTrackYourFixFragment onGoingTabTrackYourFixFragment;

	public OngoingAdapter(Activity activity, OnGoingTabTrackYourFixFragment onGoingTabTrackYourFixFragment, List<Integer> ongoingList) {
		// TODO Auto-generated constructor stub
		
		this.activity = activity;
		this.ongoingList = ongoingList;
		this.onGoingTabTrackYourFixFragment = onGoingTabTrackYourFixFragment;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ongoingList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		AcceptedHolder acceptedHolder = null;
		
		if(convertView == null){
			
			acceptedHolder = new AcceptedHolder();
			convertView = inflater.inflate(R.layout.ongoing_accepted_list_item, null);
			acceptedHolder.accepted_cost = (TextView)convertView.findViewById(R.id.accepted_cost);
			acceptedHolder.accepted_date = (TextView)convertView.findViewById(R.id.accepted_date);
			acceptedHolder.accepted_time = (TextView)convertView.findViewById(R.id.accepted_time);
			acceptedHolder.accepted_content = (TextView)convertView.findViewById(R.id.accepted_content);
			acceptedHolder.accepted_right_arrow = (ImageView)convertView.findViewById(R.id.accepted_right_arrow);
			acceptedHolder.accepted_image = (LinearLayout)convertView.findViewById(R.id.accepted_image);
			
			convertView.setTag(acceptedHolder);
			
		}else{
			
			acceptedHolder = (AcceptedHolder)convertView.getTag();
			
		}
		
		acceptedHolder.accepted_right_arrow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				onGoingTabTrackYourFixFragment.ongoingFragmentTabHost();
				
			}
		});
		
		return convertView;
	}
	
	public class AcceptedHolder{
		
		private TextView accepted_cost;
		private TextView accepted_date;
		private TextView accepted_time;
		private TextView accepted_content;
		private ImageView accepted_right_arrow;
		private LinearLayout accepted_image;
	}

}
