package com.xminds.easyhomefix.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.pkmmte.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Holder.JobReviews;
import com.xminds.easyhomefix.utils.UtilValidate;

public class ReviewAdapter extends BaseAdapter {

	private static LayoutInflater inflater;
	private List<JobReviews> reviewlist = new ArrayList<JobReviews>();
	private Activity activity;
	private Holder holder;
	String formattedDate;
	String formattedTime;
	String jobformattedDate;
	String jobformattedTime;
	private SimpleDateFormat fromUser, createddate, createdtime, jobondate,
			jobontime;
	private String finaldate, finaltime, jobdate, jobtime;

	public ReviewAdapter(Activity activity, List<JobReviews> reviewlist) {
		this.activity = activity;
		this.reviewlist = reviewlist;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {

			holder = new Holder();
			Log.e("in adapter", "adapter calll");
			convertView = inflater.inflate(R.layout.review_list_layout, null);
			holder.textReviewDate = (TextView) convertView
					.findViewById(R.id.textReviewDate);
			holder.ratingReviewList = (RatingBar) convertView
					.findViewById(R.id.ratingReviewList);
			holder.textReviewName = (TextView) convertView
					.findViewById(R.id.textReviewName);
			holder.textviewCompanyName = (TextView) convertView
					.findViewById(R.id.textviewCompanyName);
			holder.image_avatar_review = (CircularImageView) convertView
					.findViewById(R.id.image_avatar_review);
			holder.textReviewTime = (TextView) convertView
					.findViewById(R.id.textReviewTime);
			holder.textReviewComment = (TextView) convertView
					.findViewById(R.id.textReviewComment);
			holder.JobonDate = (TextView) convertView
					.findViewById(R.id.JobonDate);
			holder.JobonTime = (TextView) convertView
					.findViewById(R.id.JobonTime);

			convertView.setTag(holder);

		} else {
			holder = (Holder) convertView.getTag();
		}

		if (UtilValidate.isNotNull(reviewlist.get(position).getJobId()
				.getUserId().getFirstName())) {
			holder.textReviewName.setText(reviewlist.get(position).getJobId()
					.getUserId().getFirstName()
					+ " "
					+ reviewlist.get(position).getJobId().getUserId()
							.getLastName());
		}
		if (UtilValidate
				.isNotNull(reviewlist.get(position).getReviewDateTime())) {
			fromUser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			jobondate = new SimpleDateFormat("dd/MM/yyyy");
			jobontime = new SimpleDateFormat("hh:mm");
		
			try {
				String dateStr = reviewlist
						.get(position).getReviewDateTime();
				String timeStr = reviewlist
						.get(position).getReviewDateTime();
				
				fromUser.setTimeZone(TimeZone.getTimeZone("UTC"));
				jobontime.setTimeZone(TimeZone.getTimeZone("UTC"));
				
				Date date = fromUser.parse(dateStr);
				fromUser.setTimeZone(TimeZone.getDefault());
				 formattedDate = fromUser.format(date);
				 
				 Date time = jobontime.parse(timeStr);
				 jobontime.setTimeZone(TimeZone.getDefault());
				 formattedTime = jobontime.format(time);
				


			} catch (ParseException e) {
				e.printStackTrace();
			}

			holder.textReviewDate.setText(formattedDate);
	/*		holder.textReviewTime.setText(formattedTime);*/

		}
		if (UtilValidate.isNotNull(reviewlist.get(position).getJobId()
				.getUserId().getCreatedDate())) {

			fromUser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			createddate = new SimpleDateFormat("dd/MM/yyyy");
			createdtime = new SimpleDateFormat("hh:mm");
			try {
				
				
				String dateStr = reviewlist.get(position)
						.getJobId().getUserId().getCreatedDate();
				String timeStr = reviewlist.get(position)
						.getJobId().getUserId().getCreatedDate();
				
				
				fromUser.setTimeZone(TimeZone.getTimeZone("UTC"));
				createdtime.setTimeZone(TimeZone.getTimeZone("UTC"));
				
				Date date = fromUser.parse(dateStr);
				fromUser.setTimeZone(TimeZone.getDefault());
				jobformattedDate = fromUser.format(date);
				 
				 Date time = createdtime.parse(timeStr);
				 createdtime.setTimeZone(TimeZone.getDefault());
				 jobformattedTime = createdtime.format(time);
				
				

				/*finaldate = createddate
						.format(fromUser.parse(((reviewlist.get(position)
								.getJobId().getUserId().getCreatedDate()))));
				finaltime = createdtime
						.format(fromUser.parse(((reviewlist.get(position)
								.getJobId().getUserId().getCreatedDate()))));*/
			} catch (ParseException e) {
				e.printStackTrace();
			}
			holder.JobonDate.setText(" "+jobformattedDate + " ");
			/*holder.JobonTime.setText(" " + jobformattedTime);*/
		}

		if (UtilValidate.isNotNull(reviewlist.get(position).getStar())) {
			holder.ratingReviewList.setRating(reviewlist.get(position)
					.getStar());
		}
		
		if(UtilValidate.isNotNull(reviewlist.get(position).getReview()))
		{
			holder.textReviewComment.setText(reviewlist.get(position).getReview());
		}
		
		if(reviewlist.get(position).getJobId().getUserId().getProfileImage()!=null)
		{
			Picasso.with(activity).load(reviewlist.get(position).getJobId().getUserId().getProfileImage())
			.into(holder.image_avatar_review);
		}
		
		
		return convertView;

	}

	public class Holder {
		private TextView textReviewDate;
		private RatingBar ratingReviewList;
		private TextView textReviewName;
		private TextView textviewCompanyName;
		private CircularImageView image_avatar_review;
		private TextView textReviewTime;
		private TextView textReviewComment;
		private TextView JobonDate;
		private TextView JobonTime;

	}

}