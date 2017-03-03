package com.xminds.easyhomefix_merchant.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Holder.AvailableJobs;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;

public class QuotedForAdapter extends BaseAdapter {
	private Activity activity;
	private List<AvailableJobs> quotedList = new ArrayList<AvailableJobs>();
	private LayoutInflater inflater;
	String dateandtime;
	private int Quoteamount;

	public QuotedForAdapter(Activity activity,
			
			List<AvailableJobs> quotedList) {
		this.activity = activity;
		this.quotedList = quotedList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return quotedList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		QuotedJobsHolder quotedJobsHolder = new QuotedJobsHolder();

		if (convertView == null) {
			quotedList.size();
			convertView = inflater
					.inflate(R.layout.quotedfor_list_layout, null);

			quotedJobsHolder.quoted_booking = (TextView) convertView
					.findViewById(R.id.quoted_booking);
			quotedJobsHolder.quoted_fix_time = (TextView) convertView
					.findViewById(R.id.quoted_fix_time);
			quotedJobsHolder.quoted_green_dot = (ImageView) convertView
					.findViewById(R.id.quoted_green_dot);
			quotedJobsHolder.pending_fix_content = (TextView) convertView
					.findViewById(R.id.pending_fix_content);
			quotedJobsHolder.textViewFixDescription = (TextView) convertView
					.findViewById(R.id.textViewFixDescription);
			quotedJobsHolder.pending_fix_date = (TextView) convertView
					.findViewById(R.id.pending_fix_date);
			quotedJobsHolder.pending_fix_time = (TextView) convertView
					.findViewById(R.id.newjob_fix_time);
			quotedJobsHolder.quoted_cost=(TextView) convertView
					.findViewById(R.id.quoted_cost);
			quotedJobsHolder.imageViewCAtegoryQuoted=(ImageView) convertView
					.findViewById(R.id.imageViewCAtegoryQuoted);
			
			
			/*
			if (position == 0) {
				quotedJobsHolder.quoted_booking
						.setTextColor(pendingFixesTabFragment.getResources()
								.getColor(R.color.dark_green_contact_us));
				quotedJobsHolder.quoted_fix_time
						.setTextColor(pendingFixesTabFragment.getResources()
								.getColor(R.color.dark_green_contact_us));
				quotedJobsHolder.quoted_green_dot.setVisibility(View.VISIBLE);
				quotedJobsHolder.pending_fix_content
						.setTextColor(pendingFixesTabFragment.getResources()
								.getColor(R.color.black));
				quotedJobsHolder.textViewFixDescription
						.setTextColor(pendingFixesTabFragment.getResources()
								.getColor(R.color.black));
			}*/
			convertView.setTag(quotedJobsHolder);
		} else {
			quotedJobsHolder = (QuotedJobsHolder) convertView.getTag();
		}
		Log.e("","status>>>>>>>"+quotedList.get(position).getReadStatus());
		if(quotedList.get(position).getReadStatus()!=null)
		{
	
		if(quotedList.get(position).getReadStatus().equalsIgnoreCase("unread"))
		{
			
			quotedJobsHolder.quoted_green_dot.setVisibility(View.VISIBLE);
			quotedJobsHolder.pending_fix_date.setTypeface(null, Typeface.BOLD);
			quotedJobsHolder.pending_fix_content.setTypeface(null, Typeface.BOLD);
			quotedJobsHolder.textViewFixDescription.setTypeface(null, Typeface.BOLD);
			quotedJobsHolder.quoted_booking.setTextColor(Color.parseColor("#49A582"));
			quotedJobsHolder.quoted_fix_time.setTextColor(Color.parseColor("#49A582"));
			quotedJobsHolder.quoted_booking.setTypeface(null, Typeface.BOLD);
			quotedJobsHolder.quoted_fix_time.setTypeface(null, Typeface.BOLD);
			
			
		}
		else
		{   	
			quotedJobsHolder.quoted_green_dot.setVisibility(View.INVISIBLE);
			quotedJobsHolder.pending_fix_date.setTypeface(null,Typeface.NORMAL);
			quotedJobsHolder.pending_fix_content.setTypeface(null, Typeface.NORMAL);
			quotedJobsHolder.textViewFixDescription.setTypeface(null, Typeface.NORMAL);
			quotedJobsHolder.quoted_booking.setTextColor(Color.parseColor("#000000"));
			quotedJobsHolder.quoted_fix_time.setTextColor(Color.parseColor("#000000"));
			quotedJobsHolder.quoted_booking.setTypeface(null, Typeface.NORMAL);
			quotedJobsHolder.quoted_fix_time.setTypeface(null, Typeface.NORMAL);
		}
		}
		
		

		if (UtilValidate.isNotNull(quotedList.get(position).getJobDetails()))

		{
			quotedJobsHolder.textViewFixDescription.setText(quotedList.get(
					position).getJobDetails());

		}

		if (UtilValidate.isNotNull(quotedList.get(position).getAddress())) {
			StringBuilder full_address = new StringBuilder();
			if(quotedList.get(position).getAddress().getBlock_number() != null && 
					UtilValidate.isNotEmpty(quotedList.get(position).getAddress().getBlock_number())){
				full_address.append(quotedList.get(position).getAddress().getBlock_number());
			}
			if(quotedList.get(position).getAddress().getFloor() != null && 
					UtilValidate.isNotEmpty(quotedList.get(position).getAddress().getFloor())){
				full_address.append(","+quotedList.get(position).getAddress().getFloor());
			}
			if(quotedList.get(position).getAddress().getUnit() != null && 
					UtilValidate.isNotEmpty(quotedList.get(position).getAddress().getUnit())){
				full_address.append(","+quotedList.get(position).getAddress().getUnit());
			}
			if(quotedList.get(position).getAddress().getRoad_building() != null && 
					UtilValidate.isNotEmpty(quotedList.get(position).getAddress().getRoad_building())){
				full_address.append(","+quotedList.get(position).getAddress().getRoad_building());
			}
			
			quotedJobsHolder.pending_fix_content.setText(full_address.toString());
		}

		if (UtilValidate.isNotNull(quotedList.get(position)
				.getJobDateTimeSlot())) {
			dateandtime = quotedList.get(position).getJobDateTimeSlot();

			if(dateandtime.length()>10){
			
			String[] arr = dateandtime.split(" ", 2);
			
			if(UtilValidate.isNotNull((quotedJobsHolder.pending_fix_date)))
					{
				quotedJobsHolder.pending_fix_date.setText(arr[0]);
					}
			if(UtilValidate.isNotNull(quotedJobsHolder.pending_fix_time))
			{
				quotedJobsHolder.pending_fix_time.setText(arr[1]);
			}
		}
			else
			{
				String[] arr1 = dateandtime.split(" ", 1);
				if(UtilValidate.isNotNull(quotedJobsHolder.pending_fix_date)){
					
					quotedJobsHolder.pending_fix_date.setText(arr1[0]);
				}
			}
		
		if(UtilValidate.isNotNull(quotedList.get(position).getQuote().getQuoteAmount()))
		{
			Quoteamount=quotedList.get(position).getQuote().getQuoteAmount();
			quotedJobsHolder.quoted_cost.setText("$"+String.valueOf(Quoteamount));
			
		}
		
		if(UtilValidate.isNotNull(quotedList.get(position).getJobCategoryId().getNonSelectedImage())){
			Picasso.with(activity).load(quotedList.get(position).getJobCategoryId().getNonSelectedImage())
			.into(quotedJobsHolder.imageViewCAtegoryQuoted);
		}
		
		
		}
		return convertView;
	}
	

	public class QuotedJobsHolder {
		private TextView pending_fix_time;
		private TextView pending_fix_date;
		private ImageView quoted_green_dot;
		private TextView quoted_booking;
		private TextView quoted_fix_time;
		private TextView pending_fix_content;
		private TextView textViewFixDescription;
		private TextView quoted_cost;
		private ImageView imageViewCAtegoryQuoted;
	}

}

