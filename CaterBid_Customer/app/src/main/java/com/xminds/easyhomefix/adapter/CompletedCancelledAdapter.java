package com.xminds.easyhomefix.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Holder.JobList;
import com.xminds.easyhomefix.Holder.Quote;
import com.xminds.easyhomefix.Holder.Quotes;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.fragment.CompletedCancelledTabTrackYourFixFragment;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;


public class CompletedCancelledAdapter extends BaseAdapter {

	private Activity activity;
	private CompletedCancelledTabTrackYourFixFragment completedCancelledTabTrackYourFixFragment;
	private List<JobList>completedCancelledList = new ArrayList<JobList>();
	private LayoutInflater inflater;
	private Date date;
	private String actualDate;
	private String dateandtime;

	
	
	public CompletedCancelledAdapter(Activity activity, CompletedCancelledTabTrackYourFixFragment completedCancelledTabTrackYourFixFragment, List<JobList> pendingList) {
		// TODO Auto-generated constructor stub
		
		this.activity = activity;
		this.completedCancelledList = pendingList;
		this.completedCancelledTabTrackYourFixFragment = completedCancelledTabTrackYourFixFragment;
		Log.e("","1>>>>>>>>"+activity);
		inflater = (LayoutInflater)EasyHomeFixApp.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.e("","2>>>>>>>>"+activity);
		
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView; 
		PaidHolder paidHolder = new PaidHolder();
		
		if(convertView == null){
			
				convertView = inflater.inflate(R.layout.pending_paid_list_item, parent,false);
					
					paidHolder.paid_cost = (TextView)convertView.findViewById(R.id.paid_cost);
					paidHolder.date = (TextView)convertView.findViewById(R.id.date);
					paidHolder.time = (TextView)convertView.findViewById(R.id.time);
					paidHolder.paid_content = (TextView)convertView.findViewById(R.id.paid_content);
					paidHolder.right_arrow = (ImageView)convertView.findViewById(R.id.right_arrow);
					paidHolder.paid_image = (LinearLayout)convertView.findViewById(R.id.paid_image);
					paidHolder.AddReview_button = (LinearLayout)convertView.findViewById(R.id.AddReview_button);
					paidHolder.imageViewTypeImage=(ImageView)convertView.findViewById(R.id.imageViewTypeImage);
					paidHolder.texteViewPaid = (TextView)convertView.findViewById(R.id.texteViewPaid);
					paidHolder.layout_rating_linnear = (LinearLayout)convertView.findViewById(R.id.layout_rating_linnear);
					paidHolder.linear_banner_list = (LinearLayout)convertView.findViewById(R.id.linear_banner_list);
					paidHolder.first_star=(ImageView)convertView.findViewById(R.id.first_star);
					paidHolder.second_star=(ImageView)convertView.findViewById(R.id.second_star);
					paidHolder.third_star=(ImageView)convertView.findViewById(R.id.third_star);
					paidHolder.fourth_star=(ImageView)convertView.findViewById(R.id.fourth_star);
					paidHolder.fifth_star=(ImageView)convertView.findViewById(R.id.fifth_star);
					paidHolder.greendot=(ImageView)convertView.findViewById(R.id.greendot);
					paidHolder.booking_completed=(TextView)convertView.findViewById(R.id.booking_completed);
					
					convertView.setTag(paidHolder);
			}else{
				paidHolder=(PaidHolder)convertView.getTag();
			}
					paidHolder.AddReview_button.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							completedCancelledTabTrackYourFixFragment.addReview(completedCancelledList.get(position).getId());		
						}
					});
					
					
					if(UtilValidate.isNotNull(completedCancelledList.get(position).getJobDate())){
						
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						
						
						//date = dateFormat.format(pendingList.get(position).getJob().getJobDate().toString());
						try {
							date = dateFormat.parse(completedCancelledList.get(position).getJobDate());
							SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
							actualDate = dateFormat2.format(date);
							paidHolder.date.setText(actualDate.toString());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						}
					
					if(UtilValidate.isNotNull(completedCancelledList.get(position).getReadStatus()))
					{
						if(completedCancelledList.get(position).getReadStatus().equalsIgnoreCase("unread"))
						{
							paidHolder.greendot.setVisibility(View.VISIBLE);
							paidHolder.date.setTextColor(Color.parseColor("#000000"));
							paidHolder.time.setTypeface(null, Typeface.BOLD);
							paidHolder.booking_completed.setTypeface(null, Typeface.BOLD);
							paidHolder.booking_completed.setTextColor(Color.parseColor("#49A582"));
							paidHolder.time.setTextColor(Color.parseColor("#49A582"));
							paidHolder.date.setTypeface(null, Typeface.BOLD);
							paidHolder.paid_content.setTypeface(null, Typeface.BOLD);
							
						}
						else
						{
							paidHolder.greendot.setVisibility(View.INVISIBLE);
							paidHolder.date.setTextColor(Color.parseColor("#000000"));
							paidHolder.time.setTypeface(null, Typeface.NORMAL);
							paidHolder.booking_completed.setTypeface(null, Typeface.NORMAL);
							paidHolder.booking_completed.setTextColor(Color.parseColor("#000000"));
							paidHolder.time.setTextColor(Color.parseColor("#000000"));
							paidHolder.date.setTypeface(null, Typeface.NORMAL);
							paidHolder.paid_content.setTypeface(null, Typeface.NORMAL);
						}
					}
					
					if(UtilValidate.isNotNull(completedCancelledList.get(position))){
						
						/*if(UtilValidate.isNotNull(completedCancelledList.get(position).getJobDate())){
							
							paidHolder.date.setText(Utils.toDate(completedCancelledList.get(position).getJobDate()));
							paidHolder.time.setText(" "+Utils.toTime(completedCancelledList.get(position).getJobDate()));
						}*/
						
						if(UtilValidate.isNotNull(completedCancelledList.get(position).getJobDateTimeSlot()))
						{
							//paidHolder.date.setText(completedCancelledList.get(position).getJobDateTimeSlot());
							
							dateandtime = completedCancelledList.get(position).getJobDateTimeSlot();

							if(dateandtime.length()>10){
							
							String[] arr = dateandtime.split(" ", 2);

							
							
							if(UtilValidate.isNotNull(paidHolder.time)){
								
								paidHolder.time.setText(arr[1]);
							}
							}else{
								
								String[] arr1 = dateandtime.split(" ", 1);
								
								
								
							}
							
							
						}

						/*
						if(UtilValidate.isNotNull(completedCancelledList.get(position).getAddress())){
							paidHolder.paid_content.setText(completedCancelledList.get(position).getAddress().getBlock_number()+","
									+completedCancelledList.get(position).getAddress().getFloor()+","+completedCancelledList.get(position).getAddress().getUnit()+","
									+completedCancelledList.get(position).getAddress().getRoad_building()+","+completedCancelledList.get(position).getAddress().getPostal_code());
						}*/
						
						if(completedCancelledList.get(position).getAddress()!=null)
							
						{
							StringBuilder address = new StringBuilder();
							if(UtilValidate.isNotEmpty(completedCancelledList.get(position).getAddress().getRoad_building())){
								address.append(completedCancelledList.get(position).getAddress().getRoad_building()+",");
							}
							if(UtilValidate.isNotEmpty(completedCancelledList.get(position).getAddress().getBlock_number())){
								address.append(completedCancelledList.get(position).getAddress().getBlock_number()+",");
							}
							if(UtilValidate.isNotEmpty(completedCancelledList.get(position).getAddress().getFloor())){
								address.append(completedCancelledList.get(position).getAddress().getFloor()+",");
							}
							if(UtilValidate.isNotEmpty(completedCancelledList.get(position).getAddress().getUnit())){
								address.append(completedCancelledList.get(position).getAddress().getUnit()+",");
							}
							if(UtilValidate.isNotEmpty(completedCancelledList.get(position).getAddress().getPostal_code())){
								address.append(completedCancelledList.get(position).getAddress().getPostal_code()+".");
							}
							
							paidHolder.paid_content.setText(completedCancelledList.get(position).getJobDetails());
							
						}
						
						
						
						if(UtilValidate.isNotNull(completedCancelledList.get(position).getJobCategoryId().getNonSelectedImage())){
							Picasso.with(activity).load(completedCancelledList.get(position).getJobCategoryId().getNonSelectedImage())
							.into(paidHolder.imageViewTypeImage);
						}
						
						
						
						if(UtilValidate.isNotNull(completedCancelledList.get(position).getJobStatus())){
							
							if(completedCancelledList.get(position).getJobStatus().equalsIgnoreCase("Completed"))
								
							{
								if(UtilValidate.isNotNull(completedCancelledList.get(position).getQuote()))
								{

								for(Quotes quote:completedCancelledList.get(position).getQuote())
								{
									
									if(UtilValidate.isNotNull(quote.getPaymentStatus()))
									{

										paidHolder.texteViewPaid.setText(activity.getResources().getString(R.string.paid));
										paidHolder.linear_banner_list.setBackgroundColor(activity.getResources().getColor(R.color.light_green));
										paidHolder.paid_cost.setVisibility(View.VISIBLE);
										paidHolder.layout_rating_linnear.setVisibility(View.VISIBLE);
										
									
										
										if(UtilValidate.isNotNull(quote.getQuoteAmount()))
										{
											paidHolder.paid_cost.setText("$"+quote.getQuoteAmount());
										}
										else
										{
											paidHolder.paid_cost.setText("$0");
										}
										
										
											if(UtilValidate.isNotNull(completedCancelledList.get(position).getJobReview()))
											{
												if(UtilValidate.isNotNull(completedCancelledList.get(position).getJobReview().getReview()))
												{
													paidHolder.AddReview_button.setVisibility(View.GONE);
													
													int numstar=completedCancelledList.get(position).getJobReview().getStar();
													
													if(numstar==0)
													{
														paidHolder.first_star.setImageResource(R.drawable.star_none);
														paidHolder.second_star.setImageResource(R.drawable.star_none);
														paidHolder.third_star.setImageResource(R.drawable.star_none);
														paidHolder.fourth_star.setImageResource(R.drawable.star_none);
														paidHolder.fifth_star.setImageResource(R.drawable.star_none);
													}
													else if(numstar==1)
													{
														paidHolder.first_star.setImageResource(R.drawable.star_filled);
														paidHolder.second_star.setImageResource(R.drawable.star_none);
														paidHolder.third_star.setImageResource(R.drawable.star_none);
														paidHolder.fourth_star.setImageResource(R.drawable.star_none);
														paidHolder.fifth_star.setImageResource(R.drawable.star_none);
													}
													else if(numstar==2)
													{
														paidHolder.first_star.setImageResource(R.drawable.star_filled);
														paidHolder.second_star.setImageResource(R.drawable.star_filled);
														paidHolder.third_star.setImageResource(R.drawable.star_none);
														paidHolder.fourth_star.setImageResource(R.drawable.star_none);
														paidHolder.fifth_star.setImageResource(R.drawable.star_none);
													}
													else if(numstar==3)
													{
														paidHolder.first_star.setImageResource(R.drawable.star_filled);
														paidHolder.second_star.setImageResource(R.drawable.star_filled);
														paidHolder.third_star.setImageResource(R.drawable.star_filled);
														paidHolder.fourth_star.setImageResource(R.drawable.star_none);
														paidHolder.fifth_star.setImageResource(R.drawable.star_none);
														
													}
													else if(numstar==4)
													{
														paidHolder.first_star.setImageResource(R.drawable.star_filled);
														paidHolder.second_star.setImageResource(R.drawable.star_filled);
														paidHolder.third_star.setImageResource(R.drawable.star_filled);
														paidHolder.fourth_star.setImageResource(R.drawable.star_filled);
														paidHolder.fifth_star.setImageResource(R.drawable.star_none);
														
													}
													else if(numstar>=5)
													{
														paidHolder.first_star.setImageResource(R.drawable.star_filled);
														paidHolder.second_star.setImageResource(R.drawable.star_filled);
														paidHolder.third_star.setImageResource(R.drawable.star_filled);
														paidHolder.fourth_star.setImageResource(R.drawable.star_filled);
														paidHolder.fifth_star.setImageResource(R.drawable.star_filled);
													}
													
														         
												}
												
											}
											else
											{
												paidHolder.AddReview_button.setVisibility(View.VISIBLE);
												paidHolder.first_star.setImageResource(R.drawable.star_none);
												paidHolder.second_star.setImageResource(R.drawable.star_none);
												paidHolder.third_star.setImageResource(R.drawable.star_none);
												paidHolder.fourth_star.setImageResource(R.drawable.star_none);
												paidHolder.fifth_star.setImageResource(R.drawable.star_none);
												
											}
									}
									
									else if(UtilValidate.isNull(quote.getPaymentStatus()))
									{
										paidHolder.texteViewPaid.setText(activity.getResources().getString(R.string.pending_payment));
										paidHolder.linear_banner_list.setBackgroundColor(activity.getResources().getColor(R.color.pending_yellow));
										paidHolder.layout_rating_linnear.setVisibility(View.GONE);
										paidHolder.AddReview_button.setVisibility(View.GONE);
										paidHolder.paid_cost.setVisibility(View.VISIBLE);
										

										if(UtilValidate.isNotNull(quote.getQuoteAmount()))
										{
											paidHolder.paid_cost.setText("$"+quote.getQuoteAmount());
										}
										else
										{
											paidHolder.paid_cost.setText("$0");
										}

									}
								}
							}
							
							}
	
							else if(completedCancelledList.get(position).getJobStatus().equals("Cancelled")){
								paidHolder.texteViewPaid.setText(activity.getResources().getString(R.string.cancelled));
								paidHolder.linear_banner_list.setBackgroundColor(activity.getResources().getColor(R.color.grey_text));
								paidHolder.paid_cost.setVisibility(View.GONE);
								paidHolder.layout_rating_linnear.setVisibility(View.GONE);
								paidHolder.AddReview_button.setVisibility(View.GONE);
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
		private ImageView imageViewTypeImage;
		private TextView texteViewPaid;
		private LinearLayout layout_rating_linnear;
		private LinearLayout linear_banner_list;
		private ImageView greendot;
		private TextView booking_completed;
		
	}
	}
