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
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.fragment.AvailableTabFragment;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;

public class NewJobsAdapter extends BaseAdapter {

	private Activity activity;
	private List<AvailableJobs> newJobsList = new ArrayList<AvailableJobs>();
	private LayoutInflater inflater;
	private AvailableTabFragment availableTabFragment;
	String dateandtime;
	private Date date;
	String actualDate;

	public NewJobsAdapter(Activity activity,
			AvailableTabFragment availableTabFragment,
			List<AvailableJobs> newJobsList) {
		// TODO Auto-generated constructor stub

		this.activity = activity;
		this.newJobsList = newJobsList;
		inflater = (LayoutInflater) EasyHomeFixApp.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/*
	 * public NewJobsAdapter(Activity activity,AvailableTabFragment
	 * pendingFixesTabFragment,List<Integer> pendingList) { //super();
	 * this.activity = activity; this.pendingList = pendingList;
	 * this.pendingFixesTabFragment = pendingFixesTabFragment; inflater =
	 * (LayoutInflater
	 * )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); }
	 */

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newJobsList.size();
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
		View view = convertView;
		NewJobsHolder newjobsholder = new NewJobsHolder();
		final InvitationHolder invitationHolder = new InvitationHolder();

		if (view == null) {
			newJobsList.size();
			view = inflater
					.inflate(R.layout.pending_fix_posted_list_item, null);

			// pendingholder.pending_fix_cost =
			// (TextView)view.findViewById(R.id.pending_fix_cost);
			newjobsholder.pending_fix_date = (TextView) view
					.findViewById(R.id.pending_fix_date);
			newjobsholder.pending_fix_time = (TextView) view
					.findViewById(R.id.newjob_fix_time);
			newjobsholder.pending_fix_content = (TextView) view
					.findViewById(R.id.pending_fix_content);
			newjobsholder.pending_fix_right_arrow = (ImageView) view
					.findViewById(R.id.pending_fix_right_arrow);
			newjobsholder.pending_fix_image = (LinearLayout) view
					.findViewById(R.id.pending_fix_image);
			newjobsholder.image_avl_greendot = (ImageView) view
					.findViewById(R.id.image_avl_greendot);
			newjobsholder.newjob_fix_booking = (TextView) view
					.findViewById(R.id.newjob_fix_booking);
			newjobsholder.textViewFixDescription = (TextView) view
					.findViewById(R.id.textViewFixDescription);
			newjobsholder.imageViewCAtegorynewjobs=(ImageView) view
					.findViewById(R.id.imageViewCAtegorynewjobs);

			newjobsholder.pending_fix_right_arrow
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

						}
					});

			view.setTag(newjobsholder);

		} else {

			newjobsholder = (NewJobsHolder) view.getTag();

		}
		
		if(newJobsList.get(position).getReadStatus().equalsIgnoreCase("unread"))
		{
			newjobsholder.image_avl_greendot.setVisibility(View.VISIBLE);
			newjobsholder.pending_fix_date.setTypeface(null, Typeface.BOLD);
			newjobsholder.pending_fix_content.setTypeface(null, Typeface.BOLD);
			newjobsholder.textViewFixDescription.setTypeface(null, Typeface.BOLD);
			newjobsholder.newjob_fix_booking.setTextColor(Color.parseColor("#49A582"));
			newjobsholder.pending_fix_time.setTextColor(Color.parseColor("#49A582"));
			newjobsholder.newjob_fix_booking.setTypeface(null, Typeface.BOLD);
			newjobsholder.pending_fix_time.setTypeface(null, Typeface.BOLD);
			
		}
		else
		{
			newjobsholder.image_avl_greendot.setVisibility(View.INVISIBLE);
			newjobsholder.pending_fix_date.setTypeface(null, Typeface.NORMAL);
			newjobsholder.pending_fix_content.setTypeface(null, Typeface.NORMAL);
			newjobsholder.textViewFixDescription.setTypeface(null, Typeface.NORMAL);
			newjobsholder.newjob_fix_booking.setTextColor(Color.parseColor("#000000"));
			newjobsholder.pending_fix_time.setTextColor(Color.parseColor("#000000"));
			newjobsholder.newjob_fix_booking.setTypeface(null, Typeface.NORMAL);
			newjobsholder.pending_fix_time.setTypeface(null, Typeface.NORMAL);
		}
		

		
		
		if (UtilValidate.isNotNull(newJobsList.get(position).getJobDetails()))

		{
			newjobsholder.textViewFixDescription.setText(newJobsList.get(
					position).getJobDetails());

		}

		if (UtilValidate.isNotNull(newJobsList.get(position).getAddress())) {
			StringBuilder full_address = new StringBuilder();
			if(newJobsList.get(position).getAddress().getBlock_number() != null && 
					UtilValidate.isNotEmpty(newJobsList.get(position).getAddress().getBlock_number())){
				full_address.append(newJobsList.get(position).getAddress().getBlock_number());
			}
			if(newJobsList.get(position).getAddress().getFloor() != null && 
					UtilValidate.isNotEmpty(newJobsList.get(position).getAddress().getFloor())){
				full_address.append(","+newJobsList.get(position).getAddress().getFloor());
			}
			if(newJobsList.get(position).getAddress().getUnit() != null && 
					UtilValidate.isNotEmpty(newJobsList.get(position).getAddress().getUnit())){
				full_address.append(","+newJobsList.get(position).getAddress().getUnit());
			}
			if(newJobsList.get(position).getAddress().getRoad_building() != null && 
					UtilValidate.isNotEmpty(newJobsList.get(position).getAddress().getRoad_building())){
				full_address.append(","+newJobsList.get(position).getAddress().getRoad_building());
			}

			newjobsholder.pending_fix_content.setText(full_address.toString());
		}

		if (UtilValidate.isNotNull(newJobsList.get(position)
				.getJobDateTimeSlot())) {
			dateandtime = newJobsList.get(position).getJobDateTimeSlot();

			if(dateandtime.length()>10){
			
			String[] arr = dateandtime.split(" ", 2);

			if(UtilValidate.isNotNull(newjobsholder.pending_fix_time)){
				
				newjobsholder.pending_fix_time.setText(arr[1]);
			}
			}else{
				
				String[] arr1 = dateandtime.split(" ", 1);
				
				
			}
			

		}
		
		
		if(UtilValidate.isNotNull(newJobsList.get(position).getJobDate())){
			
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		
		//date = dateFormat.format(pendingList.get(position).getJob().getJobDate().toString());
		try {
			date = dateFormat.parse(newJobsList.get(position).getJobDate());
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
			actualDate = dateFormat2.format(date);
			newjobsholder.pending_fix_date.setText(actualDate.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		}
		
		if(UtilValidate.isNotNull(newJobsList.get(position).getJobCategoryId().getNonSelectedImage())){
			Picasso.with(activity).load(newJobsList.get(position).getJobCategoryId().getNonSelectedImage())
			.into(newjobsholder.imageViewCAtegorynewjobs);
		}
		
	
		return view;
	}

	public class NewJobsHolder {

		private TextView pending_fix_cost;
		private TextView pending_fix_date;
		private TextView pending_fix_time;
		private TextView pending_fix_content;
		private ImageView pending_fix_right_arrow;
		private LinearLayout pending_fix_image;
		private ImageView image_avl_greendot;
		private TextView newjob_fix_booking;
		private TextView textViewFixDescription;
		private ImageView imageViewCAtegorynewjobs;
	}

	public class InvitationHolder {

		private TextView pending_invitation_date;
		private TextView pending_invitation_time;
		private TextView pending_invitation_content;
		private ImageView minimize_icon;
		private ImageView maximize_icon;
		private LinearLayout pending_invitation_image;
		private LinearLayout invitation_layout;

	}

}
