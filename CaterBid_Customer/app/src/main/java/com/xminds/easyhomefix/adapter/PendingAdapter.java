package com.xminds.easyhomefix.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
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

import com.squareup.picasso.Picasso;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Holder.JobList;
import com.xminds.easyhomefix.Holder.Quote;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.fragment.PendingTabTrackYourFixFragment;
import com.xminds.easyhomefix.utils.UtilValidate;

@SuppressLint("SimpleDateFormat")
public class PendingAdapter extends BaseAdapter {

	private Activity activity;
	private List<JobList>pendingList = new ArrayList<JobList>();
	private LayoutInflater inflater;
	private PendingTabTrackYourFixFragment pendingTabTrackYourFixFragment;
	private String name="notpressed";
	private Date date;
	String actualDate;
	private String dateandtime;
	private int QuoteCount;
	public PendingAdapter(Activity activity, PendingTabTrackYourFixFragment pendingTabTrackYourFixFragment, List<JobList> pendingList) {
		// TODO Auto-generated constructor stub
		
		this.activity = activity;
		this.pendingList = pendingList;
		this.pendingTabTrackYourFixFragment = pendingTabTrackYourFixFragment;
		inflater = (LayoutInflater)EasyHomeFixApp.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pendingList.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView; 
		PendingHolder pendingholder = new PendingHolder();
		final InvitationHolder invitationHolder = new InvitationHolder();
		if(view == null){

				
				view = inflater.inflate(R.layout.pending_fix_posted_list_item, null);
				pendingholder.pending_fix_cost = (TextView)view.findViewById(R.id.pending_fix_cost);
				pendingholder.pending_fix_date = (TextView)view.findViewById(R.id.pending_fix_date);
				pendingholder.pending_fix_time = (TextView)view.findViewById(R.id.pending_fix_time);
				pendingholder.pending_fix_content = (TextView)view.findViewById(R.id.pending_fix_content);
				pendingholder.pending_fix_right_arrow = (ImageView)view.findViewById(R.id.pending_fix_right_arrow);
				pendingholder.pending_fix_image = (LinearLayout)view.findViewById(R.id.pending_fix_image);
				pendingholder.pending_fix_image_icon = (ImageView)view.findViewById(R.id.imageViewCAtegoryPending);
				pendingholder.greendot=(ImageView)view.findViewById(R.id.greendot_pending);
				//pendingholder.pending_fix_image_text = (TextView)view.findViewById(R.id.image_text);
				pendingholder.booking_pending=(TextView)view.findViewById(R.id.booking_pending);
				pendingholder.pending_titile=(TextView)view.findViewById(R.id.pending_titile);
				
				view.setTag(pendingholder);
				
		}else{
			
			 pendingholder = (PendingHolder)view.getTag();
			
		}
				
	
		
		pendingTabTrackYourFixFragment.goToFixTab(pendingList.get(position).getId()+"" );
		
		//CATEGORY IMAGE SETTING
		if(EasyHomeFixApp.getJobcategories()!=null){
		if(EasyHomeFixApp.getJobcategories().size()!=0)
		{
		for(int i=0;i<EasyHomeFixApp.getJobcategories().size();i++){
			
			if(UtilValidate.isNotNull(pendingList.get(position).getJobCategoryId())){
				Log.d("", "DATE"+pendingList.get(position).getJobCategoryId());
				
			if(pendingList.get(position).getJobCategoryId().equals(EasyHomeFixApp.getJobcategories().get(i).getId())){
				Log.d("", "DATE"+EasyHomeFixApp.getJobcategories().get(i).getName());
				
				if(EasyHomeFixApp.getJobcategories().get(i).getName().equalsIgnoreCase("Air Conditioning")){
					
					pendingholder.pending_fix_image_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.ac_icon));
					pendingholder.pending_fix_image_text.setText("Air-Con.");
					pendingholder.pending_fix_image_text.setTextColor(activity.getResources().getColor(R.color.black));
				}
				else if(EasyHomeFixApp.getJobcategories().get(i).getName().equalsIgnoreCase("Plumbing")){
					
					pendingholder.pending_fix_image_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.plumbing_icon));
					pendingholder.pending_fix_image_text.setText("Plumbing");
					pendingholder.pending_fix_image_text.setTextColor(activity.getResources().getColor(R.color.dark_green));
				}
				else if(EasyHomeFixApp.getJobcategories().get(i).getName().equalsIgnoreCase("Electrician")){
					
					pendingholder.pending_fix_image_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.wiring_icon));
					pendingholder.pending_fix_image_text.setText("");
					pendingholder.pending_fix_image_text.setTextColor(activity.getResources().getColor(R.color.black));
				}
				else if(EasyHomeFixApp.getJobcategories().get(i).getName().equalsIgnoreCase("Painting")){
					
					pendingholder.pending_fix_image_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.painting_icon));
					pendingholder.pending_fix_image_text.setText("");
					pendingholder.pending_fix_image_text.setTextColor(activity.getResources().getColor(R.color.black));
				}
				else if(EasyHomeFixApp.getJobcategories().get(i).getName().equalsIgnoreCase("Carpenter")){
					
					pendingholder.pending_fix_image_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.carpenter_icon));
					pendingholder.pending_fix_image_text.setText("");
					pendingholder.pending_fix_image_text.setTextColor(activity.getResources().getColor(R.color.black));
				}
				else if(EasyHomeFixApp.getJobcategories().get(i).getName().equalsIgnoreCase("LockSmith")){
					
					pendingholder.pending_fix_image_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.locksmith_icon));
					pendingholder.pending_fix_image_text.setText("");
					pendingholder.pending_fix_image_text.setTextColor(activity.getResources().getColor(R.color.black));
				}
				else if(EasyHomeFixApp.getJobcategories().get(i).getName().equalsIgnoreCase("Pest Control")){
					
					pendingholder.pending_fix_image_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.pest_control_icon));
					pendingholder.pending_fix_image_text.setText("");
					pendingholder.pending_fix_image_text.setTextColor(activity.getResources().getColor(R.color.black));
				}
				else if(EasyHomeFixApp.getJobcategories().get(i).getName().equalsIgnoreCase("Sanitization")){
					
					pendingholder.pending_fix_image_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.sanitization_icon));
					pendingholder.pending_fix_image_text.setText("");
					pendingholder.pending_fix_image_text.setTextColor(activity.getResources().getColor(R.color.black));
				}
				else{
					
				}
				}
		}
		}
	}
	}
		if(UtilValidate.isNotNull(pendingList.get(position).getJobDate())){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		
		//date = dateFormat.format(pendingList.get(position).getJob().getJobDate().toString());
		try {
			date = dateFormat.parse(pendingList.get(position).getJobDate());
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
			actualDate = dateFormat2.format(date);
			pendingholder.pending_fix_date.setText(actualDate.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		}
		if(UtilValidate.isNotNull(pendingList.get(position).getJobDateTimeSlot()))
		{
			
			dateandtime = pendingList.get(position).getJobDateTimeSlot();

			if(dateandtime.length()>10){
			
			String[] arr = dateandtime.split(" ", 2);

			if(UtilValidate.isNotNull(pendingholder.pending_fix_time)){
				
				pendingholder.pending_fix_time.setText(arr[1]);
			}
			}else{
				
				String[] arr1 = dateandtime.split(" ", 1);
				
				
				
			}
			
		}
		
		if(UtilValidate.isNotNull(pendingList.get(position).getJobDetails())){
			
			pendingholder.pending_fix_content.setText(pendingList.get(position).getJobDetails());
			
		}
		
		if(UtilValidate.isNotNull(pendingList.get(position).getJobCategoryId().getNonSelectedImage())){
			Picasso.with(activity).load(pendingList.get(position).getJobCategoryId().getNonSelectedImage())
			.into(pendingholder.pending_fix_image_icon);
		}
		
		
		if(UtilValidate.isNotNull(pendingList.get(position).getReadStatus()))
		{
			if(pendingList.get(position).getReadStatus().equalsIgnoreCase("Unread"))
			{
				pendingholder.greendot.setVisibility(View.VISIBLE);
				pendingholder.pending_fix_date.setTextColor(Color.parseColor("#000000"));
				pendingholder.pending_fix_time.setTypeface(null, Typeface.BOLD);
				pendingholder.booking_pending.setTypeface(null, Typeface.BOLD);
				pendingholder.booking_pending.setTextColor(Color.parseColor("#49A582"));
				pendingholder.pending_fix_time.setTextColor(Color.parseColor("#49A582"));
				pendingholder.pending_fix_date.setTypeface(null, Typeface.BOLD);
				pendingholder.pending_fix_content.setTypeface(null, Typeface.BOLD);
				
			}
			else
			{
				pendingholder.greendot.setVisibility(View.INVISIBLE);
				pendingholder.pending_fix_date.setTextColor(Color.parseColor("#000000"));
				pendingholder.pending_fix_time.setTypeface(null, Typeface.NORMAL);
				pendingholder.booking_pending.setTypeface(null, Typeface.NORMAL);
				pendingholder.booking_pending.setTextColor(Color.parseColor("#000000"));
				pendingholder.pending_fix_time.setTextColor(Color.parseColor("#000000"));
				pendingholder.pending_fix_date.setTypeface(null, Typeface.NORMAL);
				pendingholder.pending_fix_content.setTypeface(null, Typeface.NORMAL);
			}
		}

		
		
			if(!pendingList.get(position).getQuote().isEmpty())
			{
				pendingholder.pending_titile.setText("Quote Received");
			}
		
		else
		{
			pendingholder.pending_titile.setText("Fix Posted");
		}
		
		
		return view;
	}
	
	public class PendingHolder {
		
		private TextView pending_fix_cost;
		private TextView pending_fix_date;
		private TextView pending_fix_time;
		private TextView pending_fix_content;
		private ImageView pending_fix_right_arrow;
		private LinearLayout pending_fix_image;
		private LinearLayout pending_fixcost_layout;
		private ImageView pending_fix_image_icon;
		private TextView pending_fix_image_text;
		private ImageView greendot;
		private TextView booking_pending;
		private TextView pending_titile;
		
	}
	
	public class InvitationHolder {
		
		private TextView pending_invitation_date;
		private TextView pending_invitation_time;
		private TextView pending_invitation_content;
		private ImageView minimize_icon;
		private ImageView maximize_icon;
		private LinearLayout pending_invitation_image;
		private LinearLayout invitation_layout;
		private LinearLayout pending_invitation_layout;
		
		
	}
	

}
