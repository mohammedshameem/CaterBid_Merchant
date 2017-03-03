package com.xminds.easyhomefix_merchant.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.HasAccountBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.PaymentHolder;
import com.xminds.easyhomefix_merchant.Holder.TransactionHistoryBaseHolder;
import com.xminds.easyhomefix_merchant.Manager.TransactionManager;
import com.xminds.easyhomefix_merchant.activities.MerchantTransactionHistoryActivity;
import com.xminds.easyhomefix_merchant.adapter.TransactionHistoryAdapter;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;

public class PaymentsTabFragment extends Fragment {
	private TextView accountBalances;
	private Button transferToBank;
	private Button seeAll;
	private ListView transactionHistory;
	private List<PaymentHolder> transactionList = new ArrayList<PaymentHolder>();
	private TransactionHistoryAdapter transactionHistoryAdapter;
	private TextView heading;
	private ImageView imageBack;
	private View viewroot;
	private TransactionHistoryCallBack transactionHistoryCallBack;
	private int requestCode = 0;
	private  List<PaymentHolder> paymentHistory = new ArrayList<PaymentHolder>(); 
	private ProgressDialog progressDialog;
	private HasAccountCallBack hasAccountCallBack;
	private LinearLayout layoutContact;
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			TransactionManager.getInstance().getTransactionHistory(getActivity(),transactionHistoryCallBack,requestCode);
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		viewroot = inflater.inflate(R.layout.merchant_payments, null);
		initViews();
		initManagers();
		setVisibilities();
		setTexts();
		
		/*progressDialog=ProgressDialog.show(getActivity(), null, "Loading transaction...");
		TransactionManager.getInstance().getTransactionHistory(getActivity(),transactionHistoryCallBack,requestCode);*/
		
		seeAll.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(getActivity(),MerchantTransactionHistoryActivity.class);
				startActivity(i);
			}
		});
		
		transferToBank.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TransactionManager.getInstance().hasAccount(getActivity(),hasAccountCallBack, requestCode);
			}
		});
		
		
		layoutContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				
				Utils.callDialog(getActivity(), "6593269075");
				
			}
		});
		
		return viewroot;
	}
	
	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}

	@Override
	public void onResume() {
		
		super.onResume();
		// for auto refresh
		getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("nofication_received"));
		
		progressDialog=ProgressDialog.show(getActivity(), null, "Loading transaction...");
		TransactionManager.getInstance().getTransactionHistory(getActivity(),transactionHistoryCallBack,requestCode);
	}

	private void setTexts() {
		//heading.setText(R.string.payments);		
	}

	private void setVisibilities() {
		//imageBack.setVisibility(View.VISIBLE);
		//heading.setVisibility(View.VISIBLE);
		
	}

	private void initManagers() {
		transactionHistoryCallBack = new TransactionHistoryCallBack();
		hasAccountCallBack = new HasAccountCallBack();
	}

	private void initViews() {
		accountBalances=(TextView)viewroot.findViewById(R.id.textViewAccountBalances);
		transferToBank=(Button)viewroot.findViewById(R.id.buttonTransferToBank);
		seeAll=(Button)viewroot.findViewById(R.id.buttonSeeAll);
		transactionHistory=(ListView)viewroot.findViewById(R.id.listTransationHistory);
		heading=(TextView)viewroot.findViewById(R.id.textview_trackyourfix);
		imageBack=(ImageView)viewroot.findViewById(R.id.image_back);
		layoutContact=(LinearLayout)viewroot.findViewById(R.id.layoutContact);
	}
	
	private void noBankDetailsPopup(){
		LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.merchant_payment_popup, null);
		final PopupWindow popupWindow = new PopupWindow(popupView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);
		popupWindow.update();
		popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
		
		final TextView ok=(TextView)popupView.findViewById(R.id.textViewPopupOk);
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();				
			}
		});
	}
	private void transferToBankPopup(){
		LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.merchant_payment_transfer_popup, null);
		final PopupWindow popupWindow = new PopupWindow(popupView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);
		popupWindow.update();
		popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
		
		final TextView ok=(TextView)popupView.findViewById(R.id.textViewPopupOkTrnsfr);
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();				
			}
		});
	}
	
	private class TransactionHistoryCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			if(requestCode == responseCode){
				if(UtilValidate.isNotNull(result)){
					TransactionHistoryBaseHolder transactionHistoryBaseHolder = (TransactionHistoryBaseHolder)result;
					if(transactionHistoryBaseHolder.isSuccess()){
						if(UtilValidate.isNotNull(transactionHistoryBaseHolder.getData())){
							if(UtilValidate.isNotNull(transactionHistoryBaseHolder.getData().getPaymentHistory())&&
									UtilValidate.isNotEmpty(transactionHistoryBaseHolder.getData().getPaymentHistory())){
								
								paymentHistory = transactionHistoryBaseHolder.getData().getPaymentHistory();
								transactionHistoryAdapter = new TransactionHistoryAdapter(getActivity(),paymentHistory);
								transactionHistory.setAdapter(transactionHistoryAdapter);
							}
							if(UtilValidate.isNotNull(transactionHistoryBaseHolder.getData().getCurrentAccountBalance())){
								accountBalances.setText("$"+transactionHistoryBaseHolder.getData().getCurrentAccountBalance());
							}
						}
						if(progressDialog != null){
							progressDialog.dismiss();
						}
					}
				}else{
					Toast.makeText(getActivity(), "No Transactions", Toast.LENGTH_LONG).show();
					if(progressDialog != null){
						progressDialog.dismiss();
					}
				}
				
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if(progressDialog != null){
				progressDialog.dismiss();
			}
		/*	if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET)){
				Toast.makeText(getActivity(), ApiConstants.NO_INTERNET, Toast.LENGTH_LONG).show();
			}
			if(result.equalsIgnoreCase("Invalid User")){
				Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
			}*/
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(getActivity(), "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(getActivity(), result, "ERROR");
			}

		}
		
	}
	
	private class HasAccountCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if(result !=null){
				HasAccountBaseHolder hasAccountBaseHolder = (HasAccountBaseHolder) result;
				if(hasAccountBaseHolder.isSuccess()){
					if(hasAccountBaseHolder.getData() != null){
							if(hasAccountBaseHolder.getData().getAccountNumber() == null){
								noBankDetailsPopup();
							}else{
								transferToBankPopup();
							}
					}
				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(getActivity(), "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(getActivity(), result, "ERROR");
			}

		}
		
	}
}
