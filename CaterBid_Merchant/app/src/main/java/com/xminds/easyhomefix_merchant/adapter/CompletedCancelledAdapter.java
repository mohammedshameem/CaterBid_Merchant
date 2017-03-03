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

import com.squareup.picasso.Picasso;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Holder.AvailableJobs;
import com.xminds.easyhomefix_merchant.fragment.YourFixesTabFragment;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;

public class CompletedCancelledAdapter extends BaseAdapter {

	private Activity activity;
	private List<AvailableJobs>completedCancelledList = new ArrayList<AvailableJobs>();
	private YourFixesTabFragment yourFixesTabFragment;
	private LayoutInflater inflater;
	   private boolean clickable = true;

	
	
	
	public CompletedCancelledAdapter(Activity activity, YourFixesTabFragment yourFixesTabFragment ,List<AvailableJobs> pendingList) {
		this.activity = activity;
		this.completedCancelledList = pendingList;
		this.yourFixesTabFragment = yourFixesTabFragment;
		if(activity!=null)
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return completedCancelledList.size();
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
		PaidHolder paidHolder = new PaidHolder();
			
		if(convertView==null){	
			convertView = inflater.inflate(R.layout.pending_paid_list_item, null);
				
				paidHolder.paid_cost = (TextView)convertView.findViewById(R.id.paid_cost);
				paidHolder.date = (TextView)convertView.findViewById(R.id.date);
				paidHolder.time = (TextView)convertView.findViewById(R.id.time);
				paidHolder.paid_content = (TextView)convertView.findViewById(R.id.paid_content);
				paidHolder.right_arrow = (ImageView)convertView.findViewById(R.id.right_arrow);
				paidHolder.paid_image = (LinearLayout)convertView.findViewById(R.id.paid_image);
				paidHolder.AddReview_button = (LinearLayout)convertView.findViewById(R.id.AddReview_button);
				paidHolder.textview_campm_cancel_desc=(TextView)convertView.findViewById(R.id.textview_campm_cancel_desc);
				paidHolder.imageViewTypeImage=(ImageView)convertView.findViewById(R.id.imageViewTypeImage);
				paidHolder.texteViewPaid = (TextView)convertView.findViewById(R.id.texteViewPaid);
				paidHolder.layout_rating_linnear = (LinearLayout)convertView.findViewById(R.id.layout_rating_linnear);
				paidHolder.linear_banner_list = (LinearLayout)convertView.findViewById(R.id.linear_banner_list);
				paidHolder.first_star = (ImageView)convertView.findViewById(R.id.first_star);
				paidHolder.second_star = (ImageView)convertView.findViewById(R.id.second_star);
				paidHolder.third_star = (ImageView)convertView.findViewById(R.id.third_star);
				paidHolder.fourth_star = (ImageView)convertView.findViewById(R.id.fourth_star);
				paidHolder.fifth_star = (ImageView)convertView.findViewById(R.id.fifth_star);
				paidHolder.booking= (TextView)convertView.findViewById(R.id.bookingcompleted);
				paidHolder.greendot= (ImageView)convertView.findViewById(R.id.completedgreendot);
				convertView.setTag(paidHolder);
		}else{
			paidHolder=(PaidHolder)convertView.getTag();
		}	
		final int pos = position;
				paidHolder.AddReview_button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						 if (clickable) {
							 clickable = false;
							 Log.e("", "heeeeeeeeeeeeeeeeerrrrrrrrrrrrrrrrre");
						yourFixesTabFragment.requestReview(completedCancelledList.get(pos).getId());
						 clickable = true;
						 }
					}
				});
				
				
				if(completedCancelledList.get(position).getReadStatus()!=null)
				{
					if(completedCancelledList.get(position).getReadStatus().equalsIgnoreCase("unread"))
					{
						paidHolder.greendot.setVisibility(View.VISIBLE);
						paidHolder.date.setTypeface(null, Typeface.BOLD);
						paidHolder.booking.setTypeface(null, Typeface.BOLD);
						paidHolder.booking.setTextColor(Color.parseColor("#49A582"));
						paidHolder.time.setTextColor(Color.parseColor("#49A582"));
						paidHolder.paid_content.setTypeface(null, Typeface.BOLD);
						paidHolder.textview_campm_cancel_desc.setTypeface(null, Typeface.BOLD);
						
					}
					else
					{    
						paidHolder.greendot.setVisibility(View.INVISIBLE);
						paidHolder.date.setTypeface(null, Typeface.NORMAL);
						paidHolder.booking.setTypeface(null, Typeface.NORMAL);
						paidHolder.booking.setTextColor(Color.parseColor("#000000"));
						paidHolder.time.setTextColor(Color.parseColor("#000000"));
						paidHolder.paid_content.setTypeface(null, Typeface.NORMAL);
						paidHolder.textview_campm_cancel_desc.setTypeface(null, Typeface.NORMAL);
					}
				}
				
				
				if(UtilValidate.isNotNull(completedCancelledList.get(position))){
					
					if(UtilValidate.isNotNull(completedCancelledList.get(position).getJobDateTimeSlot())){
						String[] arr = completedCancelledList.get(position).getJobDateTimeSlot().split(" ", 2);
						
						paidHolder.date.setText(" "+arr[0]);
						paidHolder.time.setText(" "+arr[1]);
					}
					
					if(UtilValidate.isNotNull(completedCancelledList.get(position).getJobDetails())){
						paidHolder.textview_campm_cancel_desc.setText("Description: "+completedCancelledList.get(position).getJobDetails());
					}
					if(UtilValidate.isNotNull(completedCancelledList.get(position).getAddress())){
						
						StringBuilder full_address = new StringBuilder();
						if(completedCancelledList.get(position).getAddress().getBlock_number() != null && 
								UtilValidate.isNotEmpty(completedCancelledList.get(position).getAddress().getBlock_number())){
							full_address.append(completedCancelledList.get(position).getAddress().getBlock_number());
						}
						if(completedCancelledList.get(position).getAddress().getFloor() != null && 
								UtilValidate.isNotEmpty(completedCancelledList.get(position).getAddress().getFloor())){
							full_address.append(","+completedCancelledList.get(position).getAddress().getFloor());
						}
						if(completedCancelledList.get(position).getAddress().getUnit() != null && 
								UtilValidate.isNotEmpty(completedCancelledList.get(position).getAddress().getUnit())){
							full_address.append(","+completedCancelledList.get(position).getAddress().getUnit());
						}
						if(completedCancelledList.get(position).getAddress().getRoad_building() != null && 
								UtilValidate.isNotEmpty(completedCancelledList.get(position).getAddress().getRoad_building())){
							full_address.append(","+completedCancelledList.get(position).getAddress().getRoad_building());
						}
						paidHolder.paid_content.setText(full_address.toString());
					}
					if(UtilValidate.isNotNull(completedCancelledList.get(position).getJobCategoryId().getNonSelectedImage())){
						Picasso.with(activity).load(completedCancelledList.get(position).getJobCategoryId().getNonSelectedImage())
						.into(paidHolder.imageViewTypeImage);
					}
					
					if(UtilValidate.isNotNull(completedCancelledList.get(position).getJobStatus())){
						
						
						 if(completedCancelledList.get(position).getJobStatus().equals("Pending") ||
								completedCancelledList.get(position).getQuote().getStatus().equalsIgnoreCase("3")){
							
							
						//	Log.e("", "$$$$$$$$$$$$$$$$$$$$$"+completedCancelledList.get(position).getQuote().getStatus());
							paidHolder.texteViewPaid.setText(activity.getResources().getString(R.string.cancelled));
							paidHolder.linear_banner_list.setBackgroundColor(activity.getResources().getColor(R.color.grey_text));
							paidHolder.paid_cost.setVisibility(View.GONE);
							paidHolder.layout_rating_linnear.setVisibility(View.GONE);
							paidHolder.AddReview_button.setVisibility(View.GONE);
						
						}
						else if(completedCancelledList.get(position).getJobStatus().equals("Completed")
								&& UtilValidate.isNotNull(completedCancelledList.get(position).getPayment())){
							
							if(completedCancelledList.get(position).getQuote().getStatus().equalsIgnoreCase("2"))
							{
							paidHolder.texteViewPaid.setText(activity.getResources().getString(R.string.paid));
							paidHolder.linear_banner_list.setBackgroundColor(activity.getResources().getColor(R.color.light_green));
							
							if(UtilValidate.isNotNull(completedCancelledList.get(position).getJobReview())){
								paidHolder.layout_rating_linnear.setVisibility(View.VISIBLE);
								if(UtilValidate.isNotNull(completedCancelledList.get(position).getJobReview().getStar())){
									switch (completedCancelledList.get(position).getJobReview().getStar()){
										case 1:
											paidHolder.first_star.setImageResource(R.drawable.star_full);
											paidHolder.second_star.setImageResource(R.drawable.star_none);
											paidHolder.third_star.setImageResource(R.drawable.star_none);
											paidHolder.fourth_star.setImageResource(R.drawable.star_none);
											paidHolder.fifth_star.setImageResource(R.drawable.star_none);
											break;
										case 2:
											paidHolder.first_star.setImageResource(R.drawable.star_full);
											paidHolder.second_star.setImageResource(R.drawable.star_full);
											paidHolder.third_star.setImageResource(R.drawable.star_none);
											paidHolder.fourth_star.setImageResource(R.drawable.star_none);
											paidHolder.fifth_star.setImageResource(R.drawable.star_none);
											break;
										case 3:
											paidHolder.first_star.setImageResource(R.drawable.star_full);
											paidHolder.second_star.setImageResource(R.drawable.star_full);
											paidHolder.third_star.setImageResource(R.drawable.star_full);
											paidHolder.fourth_star.setImageResource(R.drawable.star_none);
											paidHolder.fifth_star.setImageResource(R.drawable.star_none);
											break;
										case 4:
											paidHolder.first_star.setImageResource(R.drawable.star_full);
											paidHolder.second_star.setImageResource(R.drawable.star_full);
											paidHolder.third_star.setImageResource(R.drawable.star_full);
											paidHolder.fourth_star.setImageResource(R.drawable.star_full);
											paidHolder.fifth_star.setImageResource(R.drawable.star_none);
											break;
										case 5:
											paidHolder.first_star.setImageResource(R.drawable.star_full);
											paidHolder.second_star.setImageResource(R.drawable.star_full);
											paidHolder.third_star.setImageResource(R.drawable.star_full);
											paidHolder.fourth_star.setImageResource(R.drawable.star_full);
											paidHolder.fifth_star.setImageResource(R.drawable.star_full);
											break;
									}
								}
								paidHolder.AddReview_button.setVisibility(View.GONE);
							}else{
								paidHolder.layout_rating_linnear.setVisibility(View.VISIBLE);
								paidHolder.first_star.setImageResource(R.drawable.star_none);
								paidHolder.second_star.setImageResource(R.drawable.star_none);
								paidHolder.third_star.setImageResource(R.drawable.star_none);
								paidHolder.fourth_star.setImageResource(R.drawable.star_none);
								paidHolder.fifth_star.setImageResource(R.drawable.star_none);
								paidHolder.AddReview_button.setVisibility(View.VISIBLE);
							}
							
							paidHolder.paid_cost.setVisibility(View.VISIBLE);
							if(UtilValidate.isNotNull(completedCancelledList.get(position).getQuote())){
								if(UtilValidate.isNotNull(completedCancelledList.get(position).getQuote().getQuoteAmount())){
									paidHolder.paid_cost.setText("$"+completedCancelledList.get(position).getQuote().getQuoteAmount());
								}
							}
						}
							else if(completedCancelledList.get(position).getQuote().getStatus().equalsIgnoreCase("3"))
							{
								paidHolder.texteViewPaid.setText(activity.getResources().getString(R.string.cancelled));
								paidHolder.linear_banner_list.setBackgroundColor(activity.getResources().getColor(R.color.grey_text));
								paidHolder.paid_cost.setVisibility(View.GONE);
								paidHolder.layout_rating_linnear.setVisibility(View.GONE);
								paidHolder.AddReview_button.setVisibility(View.GONE);
							}
					}
						
						else if(completedCancelledList.get(position).getJobStatus().equals("Cancelled")){
							paidHolder.texteViewPaid.setText(activity.getResources().getString(R.string.cancelled));
							paidHolder.linear_banner_list.setBackgroundColor(activity.getResources().getColor(R.color.grey_text));
							paidHolder.paid_cost.setVisibility(View.GONE);
							paidHolder.layout_rating_linnear.setVisibility(View.GONE);
							paidHolder.AddReview_button.setVisibility(View.GONE);
						}
						
						
						else if(completedCancelledList.get(position).getJobStatus().equals("Completed")
								&& UtilValidate.isNull(completedCancelledList.get(position).getPayment())){
							paidHolder.texteViewPaid.setText(activity.getResources().getString(R.string.pending_payment));
							paidHolder.linear_banner_list.setBackgroundColor(activity.getResources().getColor(R.color.pending_yellow));
							paidHolder.layout_rating_linnear.setVisibility(View.GONE);
							paidHolder.AddReview_button.setVisibility(View.GONE);
							if(UtilValidate.isNotNull(completedCancelledList.get(position).getQuote())){
								if(UtilValidate.isNotNull(completedCancelledList.get(position).getQuote().getQuoteAmount())){
									paidHolder.paid_cost.setText("$"+completedCancelledList.get(position).getQuote().getQuoteAmount());
								}
							}
						}
						
					}
									
				}	
					
		return convertView;
	}
	
	public class PaidHolder {
		
		private TextView paid_cost;
		private TextView date;
		private TextView time;
		private TextView paid_content;
		private ImageView right_arrow;
		private LinearLayout paid_image;
		private ImageView first_star;
		private ImageView second_star;
		private ImageView third_star;
		private ImageView fourth_star;
		private ImageView fifth_star;
		private LinearLayout AddReview_button;
		private TextView textview_campm_cancel_desc;
		private ImageView imageViewTypeImage;
		private TextView texteViewPaid;
		private LinearLayout layout_rating_linnear;
		private LinearLayout linear_banner_list;
		private TextView booking;
		private ImageView greendot;
	}
	
	
}
