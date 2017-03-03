package com.xminds.easyhomefix_merchant.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.pkmmte.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.xminds.easyfix_merchant.constants.Constants;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Holder.FixerReviews;
import com.xminds.easyhomefix_merchant.activities.TrackFixFragmentTabhostActivity;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;


public class ReviewAdapter extends BaseAdapter {

	 	private static LayoutInflater inflater=null;
	 	private List<FixerReviews> reviewlist = new ArrayList<FixerReviews>();
		private Activity activity;
		private Holder holder;
		private SimpleDateFormat fromUser, createddate, createdtime, jobondate,
		jobontime;
		private String finaldate, finaltime, jobdate, jobtime;
		
		
		public ReviewAdapter(Activity activity, List<FixerReviews> reviewlist){
		this.activity=activity;
		this.reviewlist=reviewlist;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return reviewlist.size();
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
		
		
		if(convertView==null)
		{	
		holder = new Holder();
		convertView=inflater.inflate(R.layout.review_list_layout, parent,false);
		holder.ReviewName=(TextView)convertView.findViewById(R.id.textReviewName);
		holder.comment=(TextView)convertView.findViewById(R.id.textReviewComment);
		holder.rating=(RatingBar)convertView.findViewById(R.id.ratingReviewList);
		holder.textview_review_fixer=(TextView)convertView.findViewById(R.id.textview_review_fixer);
		holder.profileImage=(CircularImageView)convertView.findViewById(R.id.image_avatar_list);
		holder.textReviewDate=(TextView)convertView.findViewById(R.id.textReviewDate);
		holder.textReviewTime=(TextView)convertView.findViewById(R.id.textReviewTime);
		
		convertView.setTag(holder);
		
		}
		else
		{
		holder = (Holder) convertView.getTag();
		}
		
		if(reviewlist.get(position).getReview()!=null)
		{
		 holder.comment.setText(reviewlist.get(position).getReview());
		}
		else
		{
		 holder.comment.setText(" ");
		}
		
		if(reviewlist.get(position).getReviewDateTime()!=null)
		{
			fromUser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			jobondate = new SimpleDateFormat("dd/MM/yyyy");
			jobontime = new SimpleDateFormat("hh:mm");
			
			try {

				jobdate = jobondate.format(fromUser.parse(reviewlist
						.get(position).getReviewDateTime()));
				jobtime = jobontime.format(fromUser.parse(reviewlist
						.get(position).getReviewDateTime()));

			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			holder.textReviewDate.setText(jobdate);
			holder.textReviewTime.setText(jobtime);
		
		}
		
		if(reviewlist.get(position).getJobId().getUserId()!=null)
		{
				if((reviewlist.get(position).getJobId().getUserId().getFirstName()!=null)&&
					(reviewlist.get(position).getJobId().getUserId().getLastName()!=null))
					{
				      holder.ReviewName.setText(reviewlist.get(position).getJobId().getUserId().getFirstName()+" "+
				    		  reviewlist.get(position).getJobId().getUserId().getLastName());
					}
					else
					{
						holder.ReviewName.setText(reviewlist.get(position).getJobId().getUserId().getFirstName());
					}
				
		}
		
		if(String.valueOf(reviewlist.get(position).getStar())!=null)
		{
			
			holder.rating.setRating((reviewlist.get(position).getStar()));
		}
		
		if(reviewlist.get(position).getJobId().getUserId().getProfileImage()!=null)
		{
			if(reviewlist.get(position).getJobId().getUserId().getLoginType().equalsIgnoreCase("facebook"))
			{
				String profileurl=(reviewlist.get(position).getJobId().getUserId().getProfileImage());
				if(profileurl.contains("http"))
				{
					profileurl=profileurl.replace("http", "https");
				}
				Picasso.with(activity).load(profileurl)
				.into(holder.profileImage);
			}
			else
			{
				Picasso.with(activity).load(reviewlist.get(position).getJobId().getUserId().getProfileImage())
				.into(holder.profileImage);
			}

		}
		else
		{
			Picasso.with(activity).load(R.drawable.profile_image_null)
			.into(holder.profileImage);
		}
		if(reviewlist.get(position).getJobId().getId()!=null)
		{
			holder.textview_review_fixer.setText("Fix #"+reviewlist.get(position).getJobId().getId());
		}
		else
		{
			holder.textview_review_fixer.setText("   ");
		}
		
		
		holder.textview_review_fixer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(activity,TrackFixFragmentTabhostActivity.class);
				EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_REVIEW);
				EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
				if(reviewlist!=null)
				{
					for(int i=0;i<reviewlist.size();i++)
					{
						if(position==i)
						{
							if(reviewlist.get(i)!=null)
							{
								in.putExtra("review", reviewlist.get(i));
								
							}
						}
					}
				}
				activity.startActivity(in);
			}
		});
		
		return convertView;
		
	}
	
	public class Holder
	{
		private TextView ReviewName;
		private RatingBar rating;
		private TextView comment;
		private TextView textview_review_fixer;
		private CircularImageView profileImage;
		private TextView textReviewDate;
		private TextView textReviewTime;
		
		
	}

}
