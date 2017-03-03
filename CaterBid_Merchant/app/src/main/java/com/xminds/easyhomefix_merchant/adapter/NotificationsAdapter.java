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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xminds.easyfix_merchant.actionbar.ActionBarListFragment;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Holder.jobNotifications;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;

public class NotificationsAdapter extends BaseAdapter{
	
	private Activity activity;
	private LayoutInflater inflater;
	private ViewHolder viewholder;
	private List<jobNotifications> FixList=new ArrayList<jobNotifications>();
	private static final String TAG = NotificationsAdapter.class.getName();
	ActionBarListFragment actionBarListFragment;

	public NotificationsAdapter(Activity activity,ActionBarListFragment actionBarListFragment, List<jobNotifications> FixList) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.FixList = FixList;
		this.actionBarListFragment=actionBarListFragment;
		if (activity != null) 
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	 @Override
	    public int getCount() {
	        return FixList.size();
	    }

	    /*
	     * (non-Javadoc)
	     * @see android.widget.Adapter#getItem(int)
	     */
	    @Override
	    public Object getItem(int position) {
	        // TODO Auto-generated method stub
	        return position;
	    }
	    
	    /*
	     * (non-Javadoc)
	     * @see android.widget.Adapter#getItemId(int)
	     */
	    @Override
	    public long getItemId(int position) {
	        // TODO Auto-generated method stub
	        return position;
	    }
	
	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			viewholder = new ViewHolder();

			convertView = inflater.inflate(R.layout.slidebar_row, null);

			viewholder.notification_text = (TextView) convertView
					.findViewById(R.id.notification_text);
			viewholder.notification_date = (TextView) convertView
					.findViewById(R.id.notification_date);
			viewholder.markasrerad=(LinearLayout) convertView
					.findViewById(R.id.markas_read_layout);
			/*viewholder.finallist=(ViewGroup) convertView
			.findViewById(R.id.listcontainer);*/
			viewholder.notification_unread=(ImageView) convertView
			.findViewById(R.id.notification_unread);
			
			
			convertView.setTag(viewholder);
		}else {
			viewholder = (ViewHolder)convertView.getTag();
        }
		
		/*if(FixList.get(position).getNotifyStatus().equalsIgnoreCase("Unread"))
		{	Log.e("","in if>>>>>>>>>>>>>>>>"+FixList.get(position).getNotifyStatus());
			viewholder.notification_unread.setVisibility(convertView.VISIBLE);
			viewholder.notification_text.setTypeface(null, Typeface.BOLD);
			viewholder.notification_date.setTypeface(null, Typeface.BOLD);
			
		}
		else if(FixList.get(position).getNotifyStatus().equalsIgnoreCase("Read"))
		{
			Log.e("","in if else>>>>>>>>>>>>>>>>"+FixList.get(position).getNotifyStatus());
			viewholder.notification_unread.setVisibility(convertView.INVISIBLE);
			viewholder.notification_text.setTypeface(null, Typeface.NORMAL);
			viewholder.notification_date.setTypeface(null, Typeface.NORMAL);
			viewholder.notification_date.setTextColor(Color.parseColor("#000000"));
		}*/
		
		
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
		
		
		/*
		if(UtilValidate.isNotNull(FixList.get(position).getSwipe()))
		{
			if(FixList.get(position).getNotifyStatus().equalsIgnoreCase("Unread"))
			{
				
				if(FixList.get(position).getSwipe().equalsIgnoreCase("true"))
				{
					viewholder.markasrerad.setVisibility(convertView.VISIBLE);
					viewholder.notification_unread.setVisibility(convertView.INVISIBLE);	
					viewholder.notification_date.setTextColor(Color.parseColor("#000000"));
					viewholder.notification_text.setTypeface(null, Typeface.NORMAL);
			
				}
				
				
			}
			
			if(FixList.get(position).getSwipe().equalsIgnoreCase("false"))
			{
				viewholder.markasrerad.setVisibility(convertView.GONE);
				viewholder.notification_unread.setVisibility(convertView.INVISIBLE);	
				viewholder.notification_date.setTextColor(Color.parseColor("#000000"));
				viewholder.notification_text.setTypeface(null, Typeface.NORMAL);
				viewholder.notification_date.setTypeface(null, Typeface.NORMAL);
				
		
			}

			
			
		}*/
		/*else
		{
			
		}*/
	
    viewholder.markasrerad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//ActionBarListFragment.markasRead(FixList.get(position).getId());
				
				if(FixList.get(position).getNotifyStatus().equalsIgnoreCase("Unread"))
				{
					actionBarListFragment.updateNotification(FixList.get(position).getId(),position);
				}
				
				
			}
		});

		
		
		return convertView;
	}

	public class ViewHolder{
	
		private TextView notification_text;
		private TextView notification_date;
		private LinearLayout markasrerad;
		private ImageView notification_unread;
	}
}
