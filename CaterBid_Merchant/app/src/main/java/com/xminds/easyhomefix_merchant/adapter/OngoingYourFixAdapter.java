package com.xminds.easyhomefix_merchant.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Holder.AvailableJobs;
import com.xminds.easyhomefix_merchant.fragment.YourFixesTabFragment;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;

public class OngoingYourFixAdapter extends BaseAdapter {
	private Activity activity;
	private List<AvailableJobs> ongoingList = new ArrayList<AvailableJobs>();
	private LayoutInflater inflater;
	private YourFixesTabFragment yourFixesTabFragment;
	String dateandtime;
	private Date date;
	String actualDate;
	
	public OngoingYourFixAdapter(Activity activity,YourFixesTabFragment yourFixesTabFragment, List<AvailableJobs> ongoingList) {
		this.activity = activity;
		this.ongoingList = ongoingList;
		this.yourFixesTabFragment = yourFixesTabFragment;
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
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		OngoingHolder ongoingholder = new OngoingHolder();
		final InvitationHolder invitationHolder = new InvitationHolder();
			if(convertView==null){
				convertView = inflater.inflate(R.layout.ongoing_list_layout, null);
				
				ongoingholder.pending_fix_cost = (TextView)convertView.findViewById(R.id.pending_fix_cost);
				ongoingholder.pending_fix_date = (TextView)convertView.findViewById(R.id.pending_fix_date);
				ongoingholder.pending_fix_time = (TextView)convertView.findViewById(R.id.pending_fix_time);
				ongoingholder.pending_fix_content = (TextView)convertView.findViewById(R.id.pending_fix_content);
				ongoingholder.pending_fix_right_arrow = (ImageView)convertView.findViewById(R.id.pending_fix_right_arrow);
				ongoingholder.pending_fix_image = (LinearLayout)convertView.findViewById(R.id.pending_fix_image);
				ongoingholder.textViewFixDescription=(TextView)convertView.findViewById(R.id.textViewFixDescription);
				ongoingholder.imageViewCategoryImg=(ImageView)convertView.findViewById(R.id.imageViewCategoryImg);
				ongoingholder.greendot=(ImageView)convertView.findViewById(R.id.ongoinggreendot);
				ongoingholder.booking=(TextView)convertView.findViewById(R.id.ongoingbooking);
				convertView.setTag(ongoingholder);
			}else{
				ongoingholder=(OngoingHolder)convertView.getTag();
			}
				ongoingholder.pending_fix_right_arrow .setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
					}
				});
				
				if(ongoingList.get(position).getReadStatus()!=null)
				{
					if(ongoingList.get(position).getReadStatus().equalsIgnoreCase("unread"))
					{
						ongoingholder.pending_fix_date.setTypeface(null, Typeface.BOLD);
						ongoingholder.greendot.setVisibility(View.VISIBLE);
						ongoingholder.pending_fix_date.setTextColor(Color.parseColor("#000000"));
						ongoingholder.booking.setTextColor(Color.parseColor("#49A582"));
						ongoingholder.booking.setTypeface(null, Typeface.BOLD);
						ongoingholder.pending_fix_time.setTypeface(null, Typeface.BOLD);
						ongoingholder.pending_fix_time.setTextColor(Color.parseColor("#49A582"));
						ongoingholder.pending_fix_content.setTypeface(null, Typeface.BOLD);
						ongoingholder.textViewFixDescription.setTypeface(null, Typeface.BOLD);
					}
					else
					{
						
						ongoingholder.pending_fix_date.setTypeface(null, Typeface.NORMAL);
						ongoingholder.greendot.setVisibility(View.INVISIBLE);
						ongoingholder.pending_fix_date.setTextColor(Color.parseColor("#000000"));
						ongoingholder.booking.setTextColor(Color.parseColor("#000000"));
						ongoingholder.booking.setTypeface(null, Typeface.NORMAL);
						ongoingholder.pending_fix_time.setTypeface(null, Typeface.NORMAL);
						ongoingholder.pending_fix_time.setTextColor(Color.parseColor("#000000"));
						ongoingholder.pending_fix_content.setTypeface(null, Typeface.NORMAL);
						ongoingholder.textViewFixDescription.setTypeface(null, Typeface.NORMAL);
						
						
					}
				}
				
				
				if(UtilValidate.isNotNull(ongoingList.get(position))){
					
					
					if(UtilValidate.isNotNull(ongoingList.get(position).getJobDateTimeSlot())){
						String[] arr = ongoingList.get(position).getJobDateTimeSlot().split(" ", 2);
						
						ongoingholder.pending_fix_time.setText(" "+arr[1]);
					}
					

					if(UtilValidate.isNotNull(ongoingList.get(position).getJobDate())){
						
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					
					
					//date = dateFormat.format(pendingList.get(position).getJob().getJobDate().toString());
					try {
						date = dateFormat.parse(ongoingList.get(position).getJobDate());
						SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
						actualDate = dateFormat2.format(date);
						ongoingholder.pending_fix_date.setText(actualDate.toString());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					}
					
					if(UtilValidate.isNotNull(ongoingList.get(position).getJobDetails())){
						ongoingholder.textViewFixDescription.setText("Description: "+ongoingList.get(position).getJobDetails());
					}
					if(UtilValidate.isNotNull(ongoingList.get(position).getAddress())){
						StringBuilder full_address = new StringBuilder();
						if(ongoingList.get(position).getAddress().getBlock_number() != null && 
								UtilValidate.isNotEmpty(ongoingList.get(position).getAddress().getBlock_number())){
							full_address.append(ongoingList.get(position).getAddress().getBlock_number());
						}
						if(ongoingList.get(position).getAddress().getFloor() != null && 
								UtilValidate.isNotEmpty(ongoingList.get(position).getAddress().getFloor())){
							full_address.append(","+ongoingList.get(position).getAddress().getFloor());
						}
						if(ongoingList.get(position).getAddress().getUnit() != null && 
								UtilValidate.isNotEmpty(ongoingList.get(position).getAddress().getUnit())){
							full_address.append(","+ongoingList.get(position).getAddress().getUnit());
						}
						if(ongoingList.get(position).getAddress().getRoad_building() != null && 
								UtilValidate.isNotEmpty(ongoingList.get(position).getAddress().getRoad_building())){
							full_address.append(","+ongoingList.get(position).getAddress().getRoad_building());
						}
						ongoingholder.pending_fix_content.setText(full_address.toString());
					}
					if(UtilValidate.isNotNull(ongoingList.get(position).getJobCategoryId().getNonSelectedImage())){
						Picasso.with(activity).load(ongoingList.get(position).getJobCategoryId().getNonSelectedImage())
						.into(ongoingholder.imageViewCategoryImg);
					}
					if(UtilValidate.isNotNull(ongoingList.get(position).getQuote())){
						if(UtilValidate.isNotNull(ongoingList.get(position).getQuote().getQuoteAmount())){
							ongoingholder.pending_fix_cost.setText("$"+ongoingList.get(position).getQuote().getQuoteAmount());
						}
					}
				}
		return convertView;
	}
	class OngoingHolder {
		
		private TextView pending_fix_cost;
		private TextView pending_fix_date;
		private TextView pending_fix_time;
		private TextView pending_fix_content;
		private ImageView pending_fix_right_arrow;
		private LinearLayout pending_fix_image;
		private TextView textViewFixDescription;
		private ImageView imageViewCategoryImg;
		private ImageView greendot;
		private TextView booking;
	}
	
	class InvitationHolder {
		
		private TextView pending_invitation_date;
		private TextView pending_invitation_time;
		private TextView pending_invitation_content;
		private ImageView minimize_icon;
		private ImageView maximize_icon;
		private LinearLayout pending_invitation_image;
		private LinearLayout invitation_layout;
		private TextView ongoing_invitation_desc;
		
	}
	
}
