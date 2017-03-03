package com.xminds.easyhomefix_merchant.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xminds.easyfix_merchant.constants.Constants;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Holder.PaymentHolder;
import com.xminds.easyhomefix_merchant.activities.TrackFixFragmentTabhostActivity;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;

public class TransactionHistoryAdapter extends BaseAdapter {
	private Activity activity;
	private List<PaymentHolder> transactionList = new ArrayList<PaymentHolder>();
	private static LayoutInflater inflater=null;
	 
	public TransactionHistoryAdapter(Activity activity,List<PaymentHolder> transactionList) {
		this.activity = activity;
		this.transactionList = transactionList;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return transactionList.size();
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
		Holder holder;
		if(convertView==null){
			holder=new Holder();
			convertView=inflater.inflate(R.layout.transaction_history_list_small_layout, null);
			holder.transactionDate=(TextView)convertView.findViewById(R.id.textViewTransactionDate);
			holder.transactionFixnumber=(TextView)convertView.findViewById(R.id.textViewTransactionFixnumber);
			holder.transactionAmount=(TextView)convertView.findViewById(R.id.textViewTransactionAmount);
			holder.transactionType=(TextView)convertView.findViewById(R.id.textViewTransactionType);
			holder.transactionStatus=(TextView)convertView.findViewById(R.id.textViewTransactionStatus);
			holder.linear_TransactionFixnumber = (LinearLayout)convertView.findViewById(R.id.linear_TransactionFixnumber);
			convertView.setTag(holder);
		}else{
			holder=(Holder)convertView.getTag();
		}
		final int pos = position;
		holder.linear_TransactionFixnumber.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent pendingFixIntent = new Intent(activity,TrackFixFragmentTabhostActivity.class);
				EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
				EasyHomeFixApp.setDetailsTabStatus(Constants.COMPLETED_CANCELLED);
				pendingFixIntent.putExtra("payment",transactionList.get(pos));
				activity.startActivity(pendingFixIntent);
			}
		});
		
		if(UtilValidate.isNotNull(transactionList)){
			if(UtilValidate.isNotNull(transactionList.get(position))){
				if(UtilValidate.isNotNull(transactionList.get(position).getPaymentStatus())){
					if(transactionList.get(position).getPaymentStatus().equalsIgnoreCase("Completed")){
						holder.transactionStatus.setText("Paid");
					}
				}
				if(UtilValidate.isNotNull(transactionList.get(position).getPaymentType())){
					if(transactionList.get(position).getPaymentType().equalsIgnoreCase("paypal")){
						holder.transactionType.setText("(Paypal)");
					}else{
						holder.transactionType.setText("(Cash)");
					}
				}
				if(UtilValidate.isNotNull(transactionList.get(position).getJobPaymentDate())){
					SimpleDateFormat dateTime, date;
					dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					date = new SimpleDateFormat("dd/MM/yyyy");
					String dateString = "";
					try {
						dateString = date.format(dateTime.parse(transactionList.get(position).getJobPaymentDate()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					holder.transactionDate.setText(dateString);
				}
				if(UtilValidate.isNotNull(transactionList.get(position).getJobId())){
					if(UtilValidate.isNotNull(transactionList.get(position).getJobId().getId())){
						holder.transactionFixnumber.setText("Fix #"+transactionList.get(position).getJobId().getId());
					}
				}
				if(UtilValidate.isNotNull(transactionList.get(position).getQuoteId())){
					if(UtilValidate.isNotNull(transactionList.get(position).getQuoteId().getQuoteAmount())){
						holder.transactionAmount.setText("$"+transactionList.get(position).getQuoteId().getQuoteAmount());
					}
				}
				
			}
		}
		
		
		return convertView;
	}
	
	private class Holder{
		TextView transactionDate;
		TextView transactionFixnumber;
		TextView transactionAmount;
		TextView transactionType;
		TextView transactionStatus;
		LinearLayout linear_TransactionFixnumber;
	}
}
