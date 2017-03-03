package com.xminds.easyhomefix.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.squareup.picasso.Picasso;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Holder.JobList;
import com.xminds.easyhomefix.Holder.Quote;
import com.xminds.easyhomefix.Holder.Quotes;
import com.xminds.easyhomefix.fragment.OnGoingTabTrackYourFixFragment;
import com.xminds.easyhomefix.fragment.PendingTabTrackYourFixFragment;
import com.xminds.easyhomefix.utils.UtilValidate;

public class OngoingAdapter extends BaseAdapter {
	
	private Activity activity;
	private List<JobList> ongoingList = new ArrayList<JobList>();
	private LayoutInflater inflater;
	private OnGoingTabTrackYourFixFragment onGoingTabTrackYourFixFragment;
	AcceptedHolder acceptedHolder;
	private Date date;
	private String actualDate;
	

	public OngoingAdapter(Activity activity, OnGoingTabTrackYourFixFragment onGoingTabTrackYourFixFragment, List<JobList> ongoingList) {
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
		// TODO Auto-generated method stubOngoing
	
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated meth	
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
	
		if(convertView == null){
			
			acceptedHolder = new AcceptedHolder();
			convertView = inflater.inflate(R.layout.ongoing_accepted_list_item, null);
			acceptedHolder.accepted_cost = (TextView)convertView.findViewById(R.id.accepted_cost);
			acceptedHolder.accepted_date = (TextView)convertView.findViewById(R.id.accepted_date);
			acceptedHolder.accepted_time = (TextView)convertView.findViewById(R.id.accepted_time);
			acceptedHolder.accepted_content = (TextView)convertView.findViewById(R.id.accepted_content);
			acceptedHolder.accepted_right_arrow = (ImageView)convertView.findViewById(R.id.accepted_right_arrow);
			acceptedHolder.greendot=(ImageView)convertView.findViewById(R.id.greendot_ongoing);
			acceptedHolder.imageViewCAtegoryOngoing = (ImageView)convertView.findViewById(R.id.imageViewCAtegoryOngoing);
			acceptedHolder.booking_ongoing=(TextView)convertView.findViewById(R.id.booking_ongoing);
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
		
		if(UtilValidate.isNotNull(ongoingList.get(position).getReadStatus()))
		{
			if(ongoingList.get(position).getReadStatus().equalsIgnoreCase("unread"))
			{
				acceptedHolder.greendot.setVisibility(View.VISIBLE);
				
			}
			else
			{
				acceptedHolder.greendot.setVisibility(View.INVISIBLE);
				acceptedHolder.accepted_date.setTextColor(Color.parseColor("#000000"));
				acceptedHolder.accepted_time.setTypeface(null, Typeface.NORMAL);
				acceptedHolder.booking_ongoing.setTypeface(null, Typeface.NORMAL);
				acceptedHolder.booking_ongoing.setTextColor(Color.parseColor("#000000"));
				acceptedHolder.accepted_time.setTextColor(Color.parseColor("#000000"));
				acceptedHolder.accepted_date.setTypeface(null, Typeface.NORMAL);
				acceptedHolder.accepted_content.setTypeface(null, Typeface.NORMAL);
			}
		}
		
		
		if(UtilValidate.isNotNull(ongoingList.get(position).getJobDate())){
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
			
			//date = dateFormat.format(pendingList.get(position).getJob().getJobDate().toString());
			try {
				date = dateFormat.parse(ongoingList.get(position).getJobDate());
				SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
				actualDate = dateFormat2.format(date);
				acceptedHolder.accepted_date.setText(actualDate.toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			}
			
			
			if(UtilValidate.isNotNull(ongoingList.get(position).getJobDetails())){
				
				acceptedHolder.accepted_content.setText(ongoingList.get(position).getJobDetails());
				
			}
			for(Quotes quoteamount:ongoingList.get(position).getQuote())
			{
				Log.e("","quoteeeeeeeeeeeee"+ongoingList.get(position).getQuote());
				{
					acceptedHolder.accepted_cost.setText("$"+quoteamount.getQuoteAmount());
				}
				
			}
			
			if(UtilValidate.isNotNull(ongoingList.get(position).getJobCategoryId().getNonSelectedImage())){
				Picasso.with(activity).load(ongoingList.get(position).getJobCategoryId().getNonSelectedImage())
				.into(acceptedHolder.imageViewCAtegoryOngoing);
			}
			
			
	/* if(UtilValidate.isNotNull(ongoingList.get(position).getQuote().get(position).getQuoteAmount()))
	 {
		 acceptedHolder.accepted_cost.setText(String.valueOf(ongoingList.get(position).getQuote().get(position).getQuoteAmount()));
	 }*/
		
		return convertView;
	}
	
	public class AcceptedHolder{
		
		private TextView accepted_cost;
		private TextView accepted_date;
		private TextView accepted_time;
		private TextView accepted_content;
		private ImageView accepted_right_arrow;
		private LinearLayout accepted_image;
		private ImageView imageViewCAtegoryOngoing;
		private ImageView greendot;
		private TextView booking_ongoing;
	}

}
