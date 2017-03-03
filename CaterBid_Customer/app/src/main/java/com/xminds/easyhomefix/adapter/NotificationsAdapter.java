package com.xminds.easyhomefix.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Holder.JobNotifications;
import com.xminds.easyhomefix.actionbar.ActionBarNotificationListFragment;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.swipetohide.SwipeDismissTouchListener;
import com.xminds.easyhomefix.utils.UtilValidate;

public class NotificationsAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ViewHolder viewholder;
	private List<JobNotifications> FixList = new ArrayList<JobNotifications>();
	private static final String TAG = NotificationsAdapter.class.getName();
	private ViewGroup container;
	ActionBarNotificationListFragment actionBarNotificationListFragment;

	public NotificationsAdapter(ActionBarNotificationListFragment actionBarNotificationListFragment,
			List<JobNotifications> FixList) {
		// TODO Auto-generated constructor stub
		this.FixList = FixList;
		this.actionBarNotificationListFragment=actionBarNotificationListFragment;
		inflater = (LayoutInflater) EasyHomeFixApp.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return FixList.size();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position,  View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			viewholder = new ViewHolder();
			convertView = inflater.inflate(R.layout.slidebar_row,null);

			viewholder.notification_text = (TextView) convertView
					.findViewById(R.id.notification_text);
			viewholder.notification_date = (TextView) convertView
					.findViewById(R.id.notification_date);
			viewholder.markasrerad=(LinearLayout) convertView
					.findViewById(R.id.markas_read_layout);
			viewholder.finallist=(ViewGroup) convertView
			.findViewById(R.id.listcontainer);
			viewholder.notification_unread=(ImageView) convertView
			.findViewById(R.id.notification_unread);
			
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		
		
	/*	int markasRead(convertView convertView)
		{
		}
		*/
		
		
		
		
		
	viewholder.markasrerad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				actionBarNotificationListFragment.markasRead(FixList.get(position).getId(),position);
				
			}
		});
		


		if (UtilValidate.isNotNull(FixList.get(position).getNotifyDateTime())) {

			viewholder.notification_date.setText(FixList.get(position)
					.getNotifyDateTime());
			
		}
		if (UtilValidate.isNotNull(FixList.get(position).getNotifyText())) {
			

			viewholder.notification_text.setText(FixList.get(position)
					.getNotifyText());
			
			
		}
		
	
		if(UtilValidate.isNotNull((FixList.get(position).getSwipe())))
				{
			
			
			if(FixList.get(position).getSwipe().equalsIgnoreCase("true"))
			{
				viewholder.markasrerad.setVisibility(convertView.VISIBLE);
				/*viewholder.notification_unread.setVisibility(convertView.INVISIBLE);	
				viewholder.notification_date.setTextColor(Color.parseColor("#000000"));
				viewholder.notification_text.setTypeface(null, Typeface.NORMAL);*/
		
			}
			if(FixList.get(position).getSwipe().equalsIgnoreCase("false"))
			{
				FixList.get(position).setNotifyStatus("Read");
				viewholder.markasrerad.setVisibility(convertView.GONE);
				viewholder.notification_unread.setVisibility(convertView.INVISIBLE);	
				viewholder.notification_date.setTextColor(Color.parseColor("#000000"));
				viewholder.notification_text.setTypeface(null, Typeface.NORMAL);
				viewholder.notification_date.setTypeface(null, Typeface.NORMAL);
		
			}
			
			
				
				}

		else
		{
			viewholder.markasrerad.setVisibility(convertView.GONE);
			viewholder.notification_unread.setVisibility(convertView.VISIBLE);	
			viewholder.notification_date.setTextColor(Color.parseColor("#49A582"));
			viewholder.notification_text.setTypeface(null, Typeface.BOLD);
			
		}
		
		if(UtilValidate.isNotNull(FixList.get(position).getNotifyStatus()))
		{
			if(FixList.get(position).getNotifyStatus().equalsIgnoreCase("Unread"))
			{
				viewholder.notification_unread.setVisibility(convertView.VISIBLE);
				
				
			}
			else if(FixList.get(position).getNotifyStatus().equalsIgnoreCase("Read"))
			{
				viewholder.notification_unread.setVisibility(convertView.INVISIBLE);
				viewholder.notification_date.setTextColor(Color.parseColor("#000000"));
				viewholder.notification_text.setTypeface(null, Typeface.NORMAL);
				viewholder.markasrerad.setVisibility(convertView.GONE);
				viewholder.notification_date.setTypeface(null, Typeface.NORMAL);
				
			}
		}
		return convertView;
	}
	
	
	
	

	public class ViewHolder {

		private TextView notification_text;
		private TextView notification_date;
		private LinearLayout markasrerad;
		private ViewGroup finallist;
		private ImageView notification_unread;
		
	}
	
	
}
