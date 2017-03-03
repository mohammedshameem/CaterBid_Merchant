package com.xminds.easyhomefix_merchant.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.easyandroidanimations.library.ScaleInAnimation;
import com.pkmmte.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.xminds.easyfix_merchant.Imagezooming.TouchImageView;
import com.xminds.easyfix_merchant.circularimage.RoundTransform;
import com.xminds.easyfix_merchant.constants.Constants;
import com.xminds.easyfix_merchant.viewpager.ExtendedViewPager;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.AcceptCashBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.AvailableJobs;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.EndpointResonseBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix_merchant.Holder.FixerReviews;
import com.xminds.easyhomefix_merchant.Holder.Job;
import com.xminds.easyhomefix_merchant.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix_merchant.Holder.PaymentHolder;
import com.xminds.easyhomefix_merchant.Holder.Quote;
import com.xminds.easyhomefix_merchant.Manager.NotificationManager;
import com.xminds.easyhomefix_merchant.Manager.QuoteManager;
import com.xminds.easyhomefix_merchant.Manager.ReviewManager;
import com.xminds.easyhomefix_merchant.Manager.TransactionManager;
import com.xminds.easyhomefix_merchant.activities.EditFixDetailsActivity;
import com.xminds.easyhomefix_merchant.adapter.ImageSettingAdapter;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.horizontallistview.HorizontalListView;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;

public class DetailsTabFragment extends Fragment {
	private View viewroot;

	private TextView fix_completed;
	private LinearLayout edit_details_button_layout;
	private LinearLayout viewAll_layout;
	private LinearLayout enter_or_edit_quote_layout;
	private LinearLayout rating_Review_layout;
	private LinearLayout fix_details_layout;
	private LinearLayout cancelfix_button_layout;
	private LinearLayout linearlayout_review_fixer;
	private LinearLayout fix_detials_item_image_first;
	private LinearLayout layout_request_review;
	private LinearLayout review_fixer_layout;
	private LinearLayout accept_cash_layout;
	private TextView payment_status_text;
	private LinearLayout rating_layout;
	private LinearLayout payment_request_accept_layout;
	private LinearLayout payment_status_layout;
	private TextView accepted_quote_textview;
	private TextView textview_fix_completed;
	private TextView textviewTotalFix;
	private TextView fix_completed_textview;
	private TextView additionaldetails;
	private TextView textviewCompanyName;
	private RatingBar ratingbar_ratingReview_paid;
	private EditText fix_details_item_date_edittext;
	private EditText fix_details_item_address_edittext;
	private CircularImageView image_avatar_review;

	private boolean flagimageExpensive;
	private boolean flagimageWrong;
	private boolean flagimageTrustworthy;
	private boolean flagimageCompetent;
	private String dateandtime;
	private String address;
	private View id_view;
	QuoteCallBack quotecallback;
	int requestcode = 0;
	private EditText edittext_quote;
	private String blocknumber;
	private String floornumber;
	private String unitnumber;
	private String roadbuilding;
	private String postalcode;
	private String Jobid;
	private String jobdetails;
	private String quoteamount;
	private String Jobstatus;
	private TextView textviewfix_completed;
	private List<String> CustomerImageList = new ArrayList<String>();
	private int imageno=1;
	private AvailableJobs availableJobs ;
	//private jobNotifications notificationobject;
	private FixerReviews fixerJobReviews ;
	private LinearLayout request_payment_layout;
	private RequestPaymentCallBack requestPaymentCallBack;
	private PaymentHolder paymentObject ;
	private AcceptCashCallBack acceptCashCallBack;
	private RequestReviewCallBack requestReviewcallback;
	private Quote quoteObject;
	private HorizontalListView HorizondalListview;
	private TextView emptyimages;
	ImageSettingAdapter imageSettingAdapter;
	private String CustomerImageUrl;
	private Job notificationobject;
	PopupWindow popupWindow;
	private ProgressDialog pDialog;
	private String notificationJobId;
	private JobDetailsCallback jobDetailsCallback;
	private String QuoteId;
	ProgressDialog progressDialog;
	private boolean isFromNotfication = false;
	private String cancel="false";
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.hasExtra("notification_jobid")) {
				 if(notificationobject != null){
				if(notificationobject.getId().equalsIgnoreCase(intent.getExtras().getString("notification_jobid"))){
					
					/* progressDialog = ProgressDialog.show(getActivity(), null,
								"Loading...");
*/					
							if(notificationobject.getId() != null){
							
								if(!isFromNotfication){
									/*pDialog = ProgressDialog.show(
											getActivity(), null, "Loading..",
											true);
									pDialog.setCancelable(true);*/
									NotificationManager.getInstance().jobListItemObject(getActivity(),notificationobject.getId(),
										 jobDetailsCallback,requestcode);
								}
							}
						}
					
					 
					 
				}
			 }
		}
	};
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		    super.onActivityCreated(savedInstanceState);
		    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
		}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initManager();
		getIntentValue();
		viewroot = inflater.inflate(R.layout.fix_details_details_tab, null);

		fix_completed = (TextView) viewroot.findViewById(R.id.fix_completed);
		edit_details_button_layout = (LinearLayout) viewroot
				.findViewById(R.id.edit_details_button_layout);
		enter_or_edit_quote_layout = (LinearLayout) viewroot
				.findViewById(R.id.fix_status_layout);
		fix_details_layout = (LinearLayout) viewroot
				.findViewById(R.id.fix_details_item_layout);
		fix_detials_item_image_first = (LinearLayout) viewroot
				.findViewById(R.id.fix_details_item_img);
		layout_request_review = (LinearLayout) viewroot
				.findViewById(R.id.request_review);
		linearlayout_review_fixer = (LinearLayout) viewroot
				.findViewById(R.id.review_fixer_layout);
		cancelfix_button_layout = (LinearLayout) viewroot
				.findViewById(R.id.cancel_fix_button_layout);
		textviewTotalFix = (TextView) viewroot
				.findViewById(R.id.textviewTotalFix);
		accepted_quote_textview = (TextView) viewroot
				.findViewById(R.id.payment_status_text_value);
		textview_fix_completed = (TextView) viewroot
				.findViewById(R.id.textviewfix_completed);
		ratingbar_ratingReview_paid = (RatingBar) viewroot
				.findViewById(R.id.ratingReview_paid);
		id_view = (View) viewroot.findViewById(R.id.view_id);
		accept_cash_layout = (LinearLayout) viewroot
				.findViewById(R.id.accept_cash_layout);
		payment_status_text = (TextView) viewroot
				.findViewById(R.id.payment_status_text);
		rating_layout = (LinearLayout) viewroot
				.findViewById(R.id.rating_layout);
		payment_request_accept_layout = (LinearLayout) viewroot
				.findViewById(R.id.payment_request_accept_layout);
		payment_status_layout = (LinearLayout) viewroot
				.findViewById(R.id.payment_status_layout);
		fix_details_item_address_edittext = (EditText) viewroot
				.findViewById(R.id.fix_details_item_address_edittext);
		fix_details_item_date_edittext = (EditText) viewroot
				.findViewById(R.id.fix_details_item_date_edittext);
		textviewCompanyName = (TextView) viewroot
				.findViewById(R.id.textviewCompanyName);
		image_avatar_review = (CircularImageView) viewroot
				.findViewById(R.id.image_avatar_review);
		additionaldetails = (TextView) viewroot
				.findViewById(R.id.details_tab_fix_item_additional_details_text);
		textviewfix_completed = (TextView) viewroot
				.findViewById(R.id.textviewfix_completed);
		request_payment_layout = (LinearLayout)viewroot.findViewById(R.id.request_payment_layout);
		HorizondalListview=(HorizontalListView)viewroot.findViewById(R.id.Horizondallistview);
		emptyimages=(TextView)viewroot.findViewById(R.id.emptyimages);
		
		
		
		flagimageExpensive = false;
		flagimageWrong = false;
		flagimageTrustworthy = false;
		flagimageCompetent = false;
		// fix_completed.setText("Enter/Edit Quote");
		// accepted_quote_textview.setText("QUOTE:$146");
		// textview_fix_completed.setText("Fix status:Pending");
		textview_fix_completed.setTextSize(18);
		/*textview_fix_completed.setTextColor(color.dark_grey_for_text);

		/*
		 * if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(Constants.
		 * FROM_PENDING)){ setVisibilityofPendingQuote(); }
		 */

//************************************************************************************************************************************
		if (EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.NEW_JOBS)
				|| EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
						Constants.QUOTED_JOBS)) {
			setVisibilityofAvailableFixes();
		}
		if (EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.ONGOING)) {
			Log.e("","2>>>>>>>>>>>>>>");
			setVisibilityofYourFixes();
		}
		if (EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.COMPLETED_CANCELLED)) {
			setVisibilityofYourFixesPaid();
		}
		if (EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.COMPLETED_CANCELLED_PENDING)) {
			setVisibilityofYourFixesPending();
		}
		if (EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.COMPLETED_CANCELLED_CANCELLED)) {
			setVisibilityofYourFixesCancelled();
		}
		
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_QUOTEADDEDNOTIFICATION)) {
			setVisibilityofNotificationQuote();
			
		}
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_REVIEW)){
			setVisibilityofReview();
		}
		

		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_PAYMENT_HISTORY)){
			setVisibilityOfPayment();
		}

		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_NOTIFICATION_ONGOING))
				{
					setVisibilityFromNotificationOngoing();
				}
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_NOTIFICATION_COMPLETE))
				{
					setVisibilityFromNotificationComplete();
				}
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_NOTIFICATION_COMPLETE_PAID))
				{
					setVisibilityFromNotificationCompletePaid();
				}
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_NOTIFICATION_PENDING))
				{
					setVisibilityFromNotificationPending();
				}
		
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_NOTIFICATION_CANCELL)){
			
			setVisibilityFromNotificationCancell();
		}
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_NOTIFICATION)){
			if(notificationJobId != null){
				pDialog = ProgressDialog.show(
						getActivity(), null, "Loading..",
						true);
				pDialog.setCancelable(true);
				NotificationManager.getInstance().jobListItemObject(getActivity(),notificationJobId,
					 jobDetailsCallback,requestcode);
				isFromNotfication = true;
			}
		}
		

		enter_or_edit_quote_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				final Dialog dialog = new Dialog(getActivity());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.fix_details_popup);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
				TextView textview_cancel = (TextView) dialog
						.findViewById(R.id.cancel_textview);
				TextView textview_ok = (TextView) dialog
						.findViewById(R.id.ok_textview);
				edittext_quote = (EditText) dialog
						.findViewById(R.id.quote_text_edittext);
				textview_cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();

					}
				});

				textview_ok.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						// TODO Auto-generated method stub

					

						String quoteAmount = edittext_quote.getText()
								.toString();
						accepted_quote_textview.setText("$ "+quoteAmount);
						 if(availableJobs != null)
							{
								Jobid=availableJobs.getId();
							}
							else if(notificationobject != null)
							{
								Jobid=notificationobject.getId();
							}
						QuoteManager.getInstance().AddQuote(getActivity(),
								quoteAmount, Jobid, quotecallback, requestcode);

						dialog.dismiss();

					}
				});

				dialog.show();

			}
		});

		edit_details_button_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(),
						EditFixDetailsActivity.class);
				startActivity(i);

			}
		});
		
		
		

		accept_cash_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			if(availableJobs!=null){
				if(availableJobs.getQuote()!=null){
					Log.e("","inside11111111>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					Log.e("################", "QuoteId : "+availableJobs.getQuote().getId());
						final Dialog dialog = new Dialog(getActivity());
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.cash_payment_popup);
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
						TextView textview_cancel = (TextView) dialog
								.findViewById(R.id.cash_cancel_textview);
						TextView textview_ok = (TextView) dialog
								.findViewById(R.id.cash_ok_textview);
						TextView cash_amount_disp = (TextView) dialog
								.findViewById(R.id.cash_amount_disp);
						cash_amount_disp.setText("Please collect $"+availableJobs.getQuote().getQuoteAmount()+" in cash");
						textview_cancel.setOnClickListener(new OnClickListener() {
		
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog.dismiss();
		
							}
						});
		
						textview_ok.setOnClickListener(new OnClickListener() {
		
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								TransactionManager.getInstance().acceptCash(getActivity(), availableJobs.getQuote().getId(),
										acceptCashCallBack, requestcode);
								dialog.dismiss();
		
							}
						});
		
						dialog.show();
				}
				
			
			else if(notificationobject!=null)
			{
				if(notificationobject.getQuote()!=null){
						final Dialog dialog = new Dialog(getActivity());
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.cash_payment_popup);
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
						TextView textview_cancel = (TextView) dialog
								.findViewById(R.id.cash_cancel_textview);
						TextView textview_ok = (TextView) dialog
								.findViewById(R.id.cash_ok_textview);
						TextView cash_amount_disp = (TextView) dialog
								.findViewById(R.id.cash_amount_disp);
						cash_amount_disp.setText("Please collect $"+notificationobject.getQuote().getQuoteAmount()+" in cash");
						textview_cancel.setOnClickListener(new OnClickListener() {
		
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog.dismiss();
		
							}
						});
		
						textview_ok.setOnClickListener(new OnClickListener() {
		
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								TransactionManager.getInstance().acceptCash(getActivity(), notificationobject.getQuote().getId(),
										acceptCashCallBack, requestcode);
								dialog.dismiss();
		
							}
						});
		
						dialog.show();
				}
			
			}
			}
			else if(notificationobject!=null)
			{
				if(notificationobject.getQuote()!=null){
						final Dialog dialog = new Dialog(getActivity());
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.cash_payment_popup);
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
						TextView textview_cancel = (TextView) dialog
								.findViewById(R.id.cash_cancel_textview);
						TextView textview_ok = (TextView) dialog
								.findViewById(R.id.cash_ok_textview);
						TextView cash_amount_disp = (TextView) dialog
								.findViewById(R.id.cash_amount_disp);
						cash_amount_disp.setText("Please collect $"+notificationobject.getQuote().getQuoteAmount()+" in cash");
						textview_cancel.setOnClickListener(new OnClickListener() {
		
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog.dismiss();
		
							}
						});
		
						textview_ok.setOnClickListener(new OnClickListener() {
		
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								TransactionManager.getInstance().acceptCash(getActivity(), notificationobject.getQuote().getId(),
										acceptCashCallBack, requestcode);
								dialog.dismiss();
		
							}
						});
		
						dialog.show();
				}
			
			}
			
			
			
			}
		});
		
		request_payment_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(availableJobs!=null)
				{
				TransactionManager.getInstance().requestPayment(getActivity(), requestPaymentCallBack,
						availableJobs.getId(), requestcode);
				}
				else if(notificationobject!=null){
					TransactionManager.getInstance().requestPayment(getActivity(), requestPaymentCallBack,
							notificationobject.getId(), requestcode);
				}
			}
		});
		
		layout_request_review.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(availableJobs != null){
					
					
					pDialog=ProgressDialog.show(getActivity(), null, null);
					ReviewManager.getInstance().requestReview(getActivity(), availableJobs.getId(), 
							requestReviewcallback, requestcode);
				}
				else if(notificationobject!=null)
				{	
					pDialog=ProgressDialog.show(getActivity(), null, null);
					ReviewManager.getInstance().requestReview(getActivity(), notificationobject.getId(), 
						requestReviewcallback, requestcode);	
				}
				
			}
		});

		return viewroot;
	}

	private void setVisibilityFromNotificationCancell() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		if(cancel.equalsIgnoreCase("true"))
		{
			payment_status_text.setText("Job Taken");
		}
		else
		{
			payment_status_text.setText("CANCELLED");
		}
		
		accepted_quote_textview.setVisibility(View.GONE);
		payment_status_layout.setGravity(Gravity.CENTER);
		payment_status_text.setGravity(Gravity.CENTER);
		textview_fix_completed.setText("Fix status: Cancelled");
		enter_or_edit_quote_layout.setVisibility(View.GONE);
		fix_completed.setVisibility(View.GONE);
		payment_status_layout.setBackground(getResources().getDrawable(
				R.drawable.grey_round_corner));
		// payment_status_layout.setBackgroundColor(getResources().getColor(R.color.grey_text));
		cancelfix_button_layout.setVisibility(View.GONE);
		linearlayout_review_fixer.setVisibility(View.GONE);
		
		if(notificationobject!=null){
		if(notificationobject.getImages() != null){
			if (UtilValidate.isNotNull(notificationobject.getImages()) && UtilValidate.isNotEmpty(notificationobject.getImages())) {
				SetFixImages(notificationobject.getImages());
			}
			else
			{
				HorizondalListview.setVisibility(View.GONE);
				emptyimages.setVisibility(View.VISIBLE);
			}
			/*if(availableJobs.getJobStatus()!=null){
				textviewfix_completed.setText("Fix status:" + availableJobs.getJobStatus());
			}*/
			if(notificationobject.getJobDetails() != null){
				additionaldetails.setText(notificationobject.getJobDetails());
			}
			if(notificationobject.getJobDateTimeSlot() != null){
				fix_details_item_date_edittext.setText(notificationobject.getJobDateTimeSlot());
			}
			if ((notificationobject.getUserId().getFirstName()!=null)||(notificationobject.getUserId().getLastName())!=null) {

				textviewCompanyName.setText(notificationobject.getUserId().getFirstName()+" "+notificationobject.getUserId().getLastName());

			} /*else {
				textviewCompanyName.setText(notificationobject.getUserId().getFirstName());
			}*/
			if(UtilValidate.isNotNull(notificationobject.getQuote())){
				if(UtilValidate.isNotNull(notificationobject.getQuote().getQuoteAmount())){
					accepted_quote_textview.setText("$ " +notificationobject.getQuote().getQuoteAmount() );
				}else{
					accepted_quote_textview.setText(" ");
				}
			}
			if(UtilValidate.isNotNull(notificationobject.getAddress())){
				StringBuilder address = new StringBuilder();
				
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getBlock_number())){
					address.append(notificationobject.getAddress().getBlock_number());
				}
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getFloor())){
					address.append(","+notificationobject.getAddress().getFloor());
				}
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getUnit())){
					address.append(","+notificationobject.getAddress().getUnit());
				}
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getRoad_building())){
					address.append(","+notificationobject.getAddress().getRoad_building());
				}
								
				fix_details_item_address_edittext.setText(address.toString());
			}
		}
		if(notificationobject.getUserId().getProfileImage()!=null){
			
			if(notificationobject.getQuote()!=null)
			{
				if(notificationobject.getQuote().getFixerId().getUserId().getLoginType().equalsIgnoreCase("facebook"))
				{
					String profileurl=notificationobject.getUserId().getProfileImage();
					if(profileurl.contains("http"))
					{
						profileurl=profileurl.replace("http", "https");
					}
					CustomerImageUrl=profileurl;
				}
				else
				{
					CustomerImageUrl = notificationobject.getUserId().getProfileImage();
					
				}
				setProfileImage();
			}
			}
			
		if(notificationobject.getJobStatus()!=null)
		{
			textview_fix_completed.setText("Fix Status:"+" "+"Job taken");
		}
		else
		{
			textview_fix_completed.setText("");
		}
		
		}
		
	}

	private void setVisibilityFromNotificationCompletePaid() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		payment_status_text.setText("Paid:");
		enter_or_edit_quote_layout.setVisibility(View.GONE);
		textview_fix_completed.setText("Fix status: Completed");
		fix_completed.setVisibility(View.GONE);
		cancelfix_button_layout.setVisibility(View.GONE);
		linearlayout_review_fixer.setVisibility(View.GONE);
		payment_status_layout.setBackground(getResources().getDrawable(
				R.drawable.dark_green_rounded_corner));
		payment_request_accept_layout.setVisibility(View.GONE);
		layout_request_review.setVisibility(View.GONE);
		
		if(notificationobject.getJobReview() != null){
			rating_layout.setVisibility(View.VISIBLE);
			if(UtilValidate.isNotNull(notificationobject.getJobReview().getStar())){
				ratingbar_ratingReview_paid.setRating(notificationobject.getJobReview().getStar());
			}
		}else{
			rating_layout.setVisibility(View.VISIBLE);
			layout_request_review.setVisibility(View.VISIBLE);
			ratingbar_ratingReview_paid.setRating(0);
		}
		
		//Setting Values
		if(notificationobject.getImages() != null){
			if (UtilValidate.isNotNull(notificationobject.getImages()) && UtilValidate.isNotEmpty(notificationobject.getImages())) {
				SetFixImages(notificationobject.getImages());
			}
			else
			{
				HorizondalListview.setVisibility(View.GONE);
				emptyimages.setVisibility(View.VISIBLE);
			}
		}
			/*if(availableJobs.getJobStatus()!=null){
				textviewfix_completed.setText("Fix status:" + availableJobs.getJobStatus());
			}*/
			if(notificationobject.getJobDetails() != null){
				additionaldetails.setText(notificationobject.getJobDetails());
			}
			if(notificationobject.getJobDateTimeSlot() != null){
				fix_details_item_date_edittext.setText(notificationobject.getJobDateTimeSlot());
			}
			if (UtilValidate.isNotNull(notificationobject.getUserId().getFirstName()+notificationobject.getUserId().getLastName())) {

				textviewCompanyName.setText(notificationobject.getUserId().getFirstName()+" "+notificationobject.getUserId().getLastName());

			} else {
				textviewCompanyName.setText(notificationobject.getUserId().getFirstName());
			}
			if(UtilValidate.isNotNull(notificationobject.getQuote())){
				if(UtilValidate.isNotNull(notificationobject.getQuote().getQuoteAmount())){
					accepted_quote_textview.setText("$ " +notificationobject.getQuote().getQuoteAmount() );
				}else{
					accepted_quote_textview.setText(" ");
				}
			}
			if(UtilValidate.isNotNull(notificationobject.getAddress())){
				StringBuilder address = new StringBuilder();
				
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getBlock_number())){
					address.append(notificationobject.getAddress().getBlock_number());
				}
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getFloor())){
					address.append(","+notificationobject.getAddress().getFloor());
				}
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getUnit())){
					address.append(","+notificationobject.getAddress().getUnit());
				}
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getRoad_building())){
					address.append(","+notificationobject.getAddress().getRoad_building());
				}			
				fix_details_item_address_edittext.setText(address.toString());
			}
			if(notificationobject.getUserId().getProfileImage()!=null){
				
				if(notificationobject.getUserId().getProfileImage()!=null)
				{
					String profileurl= notificationobject.getUserId().getProfileImage();
					if(profileurl.contains("http"))
					{
						profileurl=profileurl.replace("http", "https");
					}
					CustomerImageUrl=profileurl;
				}
				else
				{
					CustomerImageUrl = notificationobject.getUserId().getProfileImage();
				}
				
				
				setProfileImage();
			}
			
			if(notificationobject.getJobStatus()!=null)
			{
				textview_fix_completed.setText("Fix Status:"+" "+notificationobject.getJobStatus());
			}
			else
			{
				textview_fix_completed.setText("");
			}
			
		
	
	}

	private void setVisibilityFromNotificationPending() {
		// TODO Auto-generated method stub
		
		textview_fix_completed.setText("Fix status: Pending");
		cancelfix_button_layout.setVisibility(View.GONE);
		linearlayout_review_fixer.setVisibility(View.GONE);
		fix_completed.setText("Enter/Edit Quote");
		accepted_quote_textview.setText("NA");
		
		
				if(notificationobject!=null)
				{
					if(notificationobject.getImages()!=null)
					{
						if(!notificationobject.getImages().isEmpty())
								{
							CustomerImageList=notificationobject.getImages();
							Log.e("","imagelist<<<<<<<<<<<<<<<<<<<<<<<<,,"+CustomerImageList);
							
							SetFixImages(CustomerImageList);
								}
						else
						{
							HorizondalListview.setVisibility(View.GONE);
							emptyimages.setVisibility(View.VISIBLE);
						}
					}
		

		if (UtilValidate.isNotNull(notificationobject.getJobStatus())) {
			textviewfix_completed.setText("Fix status:" + notificationobject.getJobStatus());
		}

		if (UtilValidate.isNotNull(notificationobject.getJobDetails())) {
			additionaldetails.setText(notificationobject.getJobDetails());
		}

		if (UtilValidate.isNotNull(notificationobject.getQuote())){
		if (UtilValidate.isNotNull(notificationobject.getQuote().getQuoteAmount())) {
			accepted_quote_textview.setText("$ " + notificationobject.getQuote().getQuoteAmount());
		} else {
			accepted_quote_textview.setText("$0");

		}
		}

		if ((UtilValidate.isNotNull(notificationobject.getJobDate()))||(UtilValidate.isNotNull(notificationobject.getJobDateTimeSlot()))) {
			fix_details_item_date_edittext.setText(notificationobject.getJobDateTimeSlot());
		} else {
			fix_details_item_date_edittext.setText(" ");
		}

		if (UtilValidate.isNotNull(notificationobject.getAddress())) {

			StringBuilder address = new StringBuilder();
			
			if(UtilValidate.isNotEmpty(notificationobject.getAddress().getBlock_number())){
				address.append(notificationobject.getAddress().getBlock_number());
			}
			if(UtilValidate.isNotEmpty(notificationobject.getAddress().getFloor())){
				address.append(","+notificationobject.getAddress().getFloor());
			}
			if(UtilValidate.isNotEmpty(notificationobject.getAddress().getUnit())){
				address.append(","+notificationobject.getAddress().getUnit());
			}
			if(UtilValidate.isNotEmpty(notificationobject.getAddress().getRoad_building())){
				address.append(","+notificationobject.getAddress().getRoad_building());
			}			
			fix_details_item_address_edittext.setText(address.toString());
		}
		if (notificationobject.getUserId()!=null){
		if ((notificationobject.getUserId().getFirstName()!=null)||(notificationobject.getUserId().getLastName()!=null)) {

			Log.d("", "CUS NAME"+notificationobject.getUserId().getFirstName());
			textviewCompanyName.setText(notificationobject.getUserId().getFirstName()+" "+notificationobject.getUserId().getLastName());

		} /*else {
			textviewCompanyName.setText(notificationobject.getUserId().getFirstName());
		}*/

		if(notificationobject.getUserId().getProfileImage()!=null){
			
			if(notificationobject.getUserId().getLoginType().equalsIgnoreCase("facebook"))
					{
				String profileurl=notificationobject.getUserId().getProfileImage();
				if(profileurl.contains("http"))
				{
					profileurl=profileurl.replace("http", "https");
				}
				CustomerImageUrl=profileurl;
					}
			else
			{
				CustomerImageUrl = notificationobject.getUserId().getProfileImage();
			}
			
			setProfileImage();
		}
		}
		
		if(notificationobject.getJobStatus()!=null)
		{
			textview_fix_completed.setText("Fix Status:"+" "+notificationobject.getJobStatus());
		}
		else
		{
			textview_fix_completed.setText(notificationobject.getJobStatus());
		}
	}
	}

	private void setVisibilityFromNotificationComplete() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		payment_status_text.setText("PENDING PAYMENT:");
		accepted_quote_textview.setText("$146");
		enter_or_edit_quote_layout.setVisibility(View.GONE);
		textview_fix_completed.setText("Fix status: Completed");
		fix_completed.setVisibility(View.GONE);
		payment_request_accept_layout.setVisibility(View.VISIBLE);
		payment_status_layout.setBackground(getResources().getDrawable(
				R.drawable.yellow_rounded_edge));
		// payment_status_layout.setBackgroundColor(getResources().getColor(R.color.pending_yellow));
		cancelfix_button_layout.setVisibility(View.GONE);
		linearlayout_review_fixer.setVisibility(View.GONE);
		Log.e("", "notificationobject###########################"+notificationobject.getId());
		
	
		//Setting values
		if(notificationobject != null){
			if (UtilValidate.isNotNull(notificationobject.getImages()) && UtilValidate.isNotEmpty(notificationobject.getImages())) {
				SetFixImages(notificationobject.getImages());
			}
			else
			{
				HorizondalListview.setVisibility(View.GONE);
				emptyimages.setVisibility(View.VISIBLE);
			}
			/*if(availableJobs.getJobStatus()!=null){
				textviewfix_completed.setText("Fix status:" + availableJobs.getJobStatus());
			}*/
			if(notificationobject.getJobDetails() != null){
				additionaldetails.setText(notificationobject.getJobDetails());
			}
			if(notificationobject.getJobDateTimeSlot() != null){
				fix_details_item_date_edittext.setText(notificationobject.getJobDateTimeSlot());
			}
			if ((notificationobject.getUserId().getFirstName()!=null)&&(notificationobject.getUserId().getLastName()!=null)) {

				textviewCompanyName.setText(notificationobject.getUserId().getFirstName()+" "+notificationobject.getUserId().getLastName());

			} else {
				textviewCompanyName.setText(notificationobject.getUserId().getFirstName());
			}
			if(UtilValidate.isNotNull(notificationobject.getQuote())){
				if(UtilValidate.isNotNull(notificationobject.getQuote().getQuoteAmount())){
					accepted_quote_textview.setText("$ " +notificationobject.getQuote().getQuoteAmount() );
				}else{
					accepted_quote_textview.setText(" ");
				}
			}
			if(UtilValidate.isNotNull(notificationobject.getAddress())){
				StringBuilder address = new StringBuilder();
				
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getBlock_number())){
					address.append(notificationobject.getAddress().getBlock_number());
				}
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getFloor())){
					address.append(","+notificationobject.getAddress().getFloor());
				}
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getUnit())){
					address.append(","+notificationobject.getAddress().getUnit());
				}
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getRoad_building())){
					address.append(","+notificationobject.getAddress().getRoad_building());
				}
								
				fix_details_item_address_edittext.setText(address.toString());
			}
			if(notificationobject.getUserId().getProfileImage()!=null){
				
				CustomerImageUrl = notificationobject.getUserId().getProfileImage();
				setProfileImage();
			}
			
			if(notificationobject.getJobStatus()!=null)
			{
				textview_fix_completed.setText("Fix Status:"+" "+notificationobject.getJobStatus());
			}
			else
			{
				textview_fix_completed.setText("");
			}
		}
		
	
		
	}

	private void setVisibilityFromNotificationOngoing() {
		// TODO Auto-generated method stub
		accepted_quote_textview.setText("NA");
		textview_fix_completed.setText("Fix status: Assigned");
		cancelfix_button_layout.setVisibility(View.GONE);
		linearlayout_review_fixer.setVisibility(View.GONE);
		fix_completed.setText("Edit Quote");
		fix_completed.setVisibility(View.VISIBLE);
		payment_status_text.setText("QUOTE:");
		payment_status_layout.setBackground(getResources().getDrawable(
				R.drawable.dark_green_rounded_corner));
		enter_or_edit_quote_layout.setVisibility(View.VISIBLE);
		//detailsTabDataHolder = (DetailsTabDataHolder) getActivity().getIntent().getSerializableExtra("detailsTabData");
		
		if(notificationobject!=null){
		if((notificationobject.getImages() != null)&&( UtilValidate.isNotEmpty(notificationobject.getImages()))){
			//if ( UtilValidate.isNotEmpty(notificationobject.getJob().getImages())) {
				SetFixImages(notificationobject.getImages());
			}
			else
			{
				HorizondalListview.setVisibility(View.GONE);
				emptyimages.setVisibility(View.VISIBLE);
			}
		//}
			/*if(availableJobs.getJobStatus()!=null){
				textviewfix_completed.setText("Fix status:" + availableJobs.getJobStatus());
			}*/
			if(notificationobject.getJobDetails() != null){
				additionaldetails.setText(notificationobject.getJobDetails());
			}
			if(notificationobject.getJobDateTimeSlot() != null){
				fix_details_item_date_edittext.setText(notificationobject.getJobDateTimeSlot());
			}
			
			if((notificationobject.getUserId().getFirstName()!=null)||(notificationobject.getUserId().getLastName()!=null))
			{
				Log.d("", "CUS NAME"+notificationobject.getUserId().getFirstName());
				textviewCompanyName.setText(notificationobject.getUserId().getFirstName()+" "+notificationobject.getUserId().getLastName());
				
			}
			/*else
			{
				textviewCompanyName.setText(notificationobject.getJob().getUserId().getFirstName());
			}*/
			if(UtilValidate.isNotNull(notificationobject.getQuote())){
				if(UtilValidate.isNotNull(notificationobject.getQuote().getQuoteAmount())){
					accepted_quote_textview.setText("$ " +notificationobject.getQuote().getQuoteAmount() );
				}else{
					accepted_quote_textview.setText(" ");
				}
			}
			if(UtilValidate.isNotNull(notificationobject.getAddress())){
				StringBuilder address = new StringBuilder();
				
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getBlock_number())){
					address.append(notificationobject.getAddress().getBlock_number());
				}
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getFloor())){
					address.append(","+notificationobject.getAddress().getFloor());
				}
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getUnit())){
					address.append(","+notificationobject.getAddress().getUnit());
				}
				if(UtilValidate.isNotEmpty(notificationobject.getAddress().getRoad_building())){
					address.append(","+notificationobject.getAddress().getRoad_building());
				}
								
				fix_details_item_address_edittext.setText(address.toString());
			}
			
			if(notificationobject.getUserId().getProfileImage()!=null){
				
				if(notificationobject.getUserId().getLoginType().equalsIgnoreCase("facebook"))
				{
					String profileurl=notificationobject.getUserId().getProfileImage();
					if(profileurl.contains("http"))
					{
						profileurl=profileurl.replace("http", "https");
					}
					CustomerImageUrl=profileurl;
				}
				else
				{
					CustomerImageUrl = notificationobject.getUserId().getProfileImage();
				}
				
				setProfileImage();
			}
			if(notificationobject.getJobStatus()!=null)
			{
				textview_fix_completed.setText("Fix Status:"+" "+"Confirmed");
			}
			else
			{
				textview_fix_completed.setText(" ");
			}
			
			
			
		}
	
	}

	private void setVisibilityofReview() {
		// TODO Auto-generated method stub
		
		payment_status_text.setText("Paid:");
		enter_or_edit_quote_layout.setVisibility(View.GONE);
		accepted_quote_textview.setText("$146");
		textview_fix_completed.setText("Fix status: Completed");
		fix_completed.setVisibility(View.GONE);
		cancelfix_button_layout.setVisibility(View.GONE);
		linearlayout_review_fixer.setVisibility(View.GONE);
		
		if(fixerJobReviews.getJobId()!=null)
		{
			if(fixerJobReviews.getJobId().getJobReview()!=null)
			{
				rating_layout.setVisibility(View.VISIBLE);
				if (UtilValidate.isNotNull((fixerJobReviews.getJobId().getJobReview().getStar())));
						{
					ratingbar_ratingReview_paid.setRating(fixerJobReviews.getJobId().getJobReview().getStar());
						}
			}else{
				rating_layout.setVisibility(View.VISIBLE);
				layout_request_review.setVisibility(View.VISIBLE);
				ratingbar_ratingReview_paid.setRating(0);
			}
			
			if(fixerJobReviews.getJobId().getJobDetails() != null){
				additionaldetails.setText(fixerJobReviews.getJobId().getJobDetails());
			}
			if(fixerJobReviews.getJobId().getJobDateTimeSlot() != null){
				fix_details_item_date_edittext.setText(fixerJobReviews.getJobId().getJobDateTimeSlot());
			}
			if(fixerJobReviews.getJobId().getImages() != null){
				if (UtilValidate.isNotEmpty(fixerJobReviews.getJobId().getImages())) {
					SetFixImages(fixerJobReviews.getJobId().getImages());
				}
				else
				{
					HorizondalListview.setVisibility(View.GONE);
					emptyimages.setVisibility(View.VISIBLE);
				}
			}
			
			if(UtilValidate.isNotNull(fixerJobReviews.getJobId().getAddress())){
				StringBuilder address = new StringBuilder();
				if(UtilValidate.isNotEmpty(fixerJobReviews.getJobId().getAddress().getRoad_building())){
					address.append(fixerJobReviews.getJobId().getAddress().getRoad_building()+",");
				}
				if(UtilValidate.isNotEmpty(fixerJobReviews.getJobId().getAddress().getBlock_number())){
					address.append(fixerJobReviews.getJobId().getAddress().getBlock_number()+",");
				}
				if(UtilValidate.isNotEmpty(fixerJobReviews.getJobId().getAddress().getFloor())){
					address.append(fixerJobReviews.getJobId().getAddress().getFloor()+",");
				}
				if(UtilValidate.isNotEmpty(fixerJobReviews.getJobId().getAddress().getUnit())){
					address.append(fixerJobReviews.getJobId().getAddress().getUnit()+",");
				}
				if(UtilValidate.isNotEmpty(fixerJobReviews.getJobId().getAddress().getPostal_code())){
					address.append(fixerJobReviews.getJobId().getAddress().getPostal_code()+".");
				}
				
				fix_details_item_address_edittext.setText(address.toString());
			}
		}
		
		if(fixerJobReviews.getFixerId().getUserId().getFirstName()!=null&&fixerJobReviews.getFixerId().getUserId().getLastName()!=null)
		{
			textviewCompanyName.setText(fixerJobReviews.getFixerId().getUserId().getFirstName()+" "+fixerJobReviews.getFixerId().getUserId().getLastName());
		}
		else
		{
			textviewCompanyName.setText(fixerJobReviews.getFixerId().getUserId().getFirstName());
		}
		
		
		
		}
		
		
		

	private void setVisibilityOfPayment() {

		// TODO Auto-generated method stub
		payment_status_text.setText("Paid:");
		enter_or_edit_quote_layout.setVisibility(View.GONE);
		accepted_quote_textview.setText("$146");
		textview_fix_completed.setText("Fix status: Completed");
		fix_completed.setVisibility(View.GONE);
		cancelfix_button_layout.setVisibility(View.GONE);
		linearlayout_review_fixer.setVisibility(View.GONE);
		
		
		if(paymentObject.getJobId() != null){
			if(paymentObject.getJobId().getJobReview() != null){
				rating_layout.setVisibility(View.VISIBLE);
				if(UtilValidate.isNotNull(paymentObject.getJobId().getJobReview().getStar())){
					ratingbar_ratingReview_paid.setRating(paymentObject.getJobId().getJobReview().getStar());
				}
			}else{
				rating_layout.setVisibility(View.VISIBLE);
				layout_request_review.setVisibility(View.VISIBLE);
				ratingbar_ratingReview_paid.setRating(0);
			}
			
			if(paymentObject.getJobId().getJobDetails() != null){
				additionaldetails.setText(paymentObject.getJobId().getJobDetails());
			}
			if(paymentObject.getJobId().getJobDateTimeSlot() != null){
				fix_details_item_date_edittext.setText(paymentObject.getJobId().getJobDateTimeSlot());
			}
			if(paymentObject.getJobId().getImages() != null){
				if (UtilValidate.isNotEmpty(paymentObject.getJobId().getImages())) {
					SetFixImages(paymentObject.getJobId().getImages());
				}
				else
				{
					HorizondalListview.setVisibility(View.GONE);
					emptyimages.setVisibility(View.VISIBLE);
				}
			}
			
			if(UtilValidate.isNotNull(paymentObject.getJobId().getAddress())){
				StringBuilder address = new StringBuilder();
				if(UtilValidate.isNotEmpty(paymentObject.getJobId().getAddress().getRoad_building())){
					address.append(paymentObject.getJobId().getAddress().getRoad_building()+",");
				}
				if(UtilValidate.isNotEmpty(paymentObject.getJobId().getAddress().getBlock_number())){
					address.append(paymentObject.getJobId().getAddress().getBlock_number()+",");
				}
				if(UtilValidate.isNotEmpty(paymentObject.getJobId().getAddress().getFloor())){
					address.append(paymentObject.getJobId().getAddress().getFloor()+",");
				}
				if(UtilValidate.isNotEmpty(paymentObject.getJobId().getAddress().getUnit())){
					address.append(paymentObject.getJobId().getAddress().getUnit()+",");
				}
				if(UtilValidate.isNotEmpty(paymentObject.getJobId().getAddress().getPostal_code())){
					address.append(paymentObject.getJobId().getAddress().getPostal_code()+".");
				}
				
				fix_details_item_address_edittext.setText(address.toString());
			}
		}
		
		//Setting Values
		
		
			/*if(availableJobs.getJobStatus()!=null){
				textviewfix_completed.setText("Fix status:" + availableJobs.getJobStatus());
			}*/
			
			if (UtilValidate.isNotNull(availableJobs.getUserId().getFirstName()+availableJobs.getUserId().getLastName())) {

				textviewCompanyName.setText(availableJobs.getUserId().getFirstName()+" "+availableJobs.getUserId().getLastName());

			} else {
				textviewCompanyName.setText(availableJobs.getUserId().getFirstName());
			}
			if(UtilValidate.isNotNull(paymentObject.getQuoteId())){
				if(UtilValidate.isNotNull(paymentObject.getQuoteId().getQuoteAmount())){
					accepted_quote_textview.setText("$ " +paymentObject.getQuoteId().getQuoteAmount() );
				}else{
					accepted_quote_textview.setText(" ");
				}
			}
			
	}

	private void setVisibilityofNotificationQuote() {
		// TODO Auto-generated method stub
		
		Log.e("","in notification>>>>>>>>>>>>>.");
		
		
		accepted_quote_textview.setText("$0");
		textview_fix_completed.setText("Fix status: Pending");
		cancelfix_button_layout.setVisibility(View.GONE);
		linearlayout_review_fixer.setVisibility(View.GONE);
		fix_completed.setText("Enter/Edit Quote");
		linearlayout_review_fixer.setVisibility(View.GONE);
		cancelfix_button_layout.setVisibility(View.GONE);
		
		if(notificationobject.getJobStatus()!=null)
		{
		textviewfix_completed.setText("Fix status:"+notificationobject
				.getJobStatus());
		}
		
		else
		{
			textviewfix_completed.setText(" ");
		}
		if(notificationobject.getAddress()!=null)
			
		{
			
			StringBuilder address = new StringBuilder();
			if(UtilValidate.isNotEmpty(notificationobject.getAddress().getRoad_building())){
				address.append(notificationobject.getAddress().getRoad_building()+",");
			}
			if(UtilValidate.isNotEmpty(notificationobject.getAddress().getBlock_number())){
				address.append(notificationobject.getAddress().getBlock_number()+",");
			}
			if(UtilValidate.isNotEmpty(notificationobject.getAddress().getFloor())){
				address.append(notificationobject.getAddress().getFloor()+",");
			}
			if(UtilValidate.isNotEmpty(notificationobject.getAddress().getUnit())){
				address.append(notificationobject.getAddress().getUnit()+",");
			}
			if(UtilValidate.isNotEmpty(notificationobject.getAddress().getPostal_code())){
				address.append(notificationobject.getAddress().getPostal_code()+".");
			}
			
			fix_details_item_address_edittext.setText(address.toString());
			
	  }
		
			if(notificationobject.getJobDateTimeSlot()!=null)
			
			{
			fix_details_item_date_edittext.setText((notificationobject
					.getJobDateTimeSlot()));
			}
			else
			{
			fix_details_item_date_edittext.setText(" ");
			}
		
			if(notificationobject.getId()!=null)
			
			{
			Jobid=notificationobject.getId();
			}
		
			if(notificationobject.getJobDetails()!=null)
			
			{
			additionaldetails.setText(notificationobject.getJobDetails());
			}
		
			else
			{
			additionaldetails.setText(" ");
			}
		
			if(notificationobject.getImages() != null){
			if (UtilValidate.isNotNull(notificationobject.getImages()) && UtilValidate.
					isNotEmpty(notificationobject.getImages())) {
				SetFixImages(notificationobject.getImages());
			}
			else
			{
				HorizondalListview.setVisibility(View.GONE);
				emptyimages.setVisibility(View.VISIBLE);
			}
			}
			
			if((notificationobject.getUserId().getFirstName()!=null)&&(notificationobject.getUserId().getLastName())!=null)
			{
				
			 textviewCompanyName.setText(notificationobject.getUserId().getFirstName()+" "+notificationobject.getUserId().getLastName());
			
			}
			
			else
			{
				textviewCompanyName.setText(notificationobject.getUserId().getFirstName());
			}
			if(notificationobject.getUserId().getProfileImage()!=null){
				
				if(notificationobject.getUserId().getLoginType().equalsIgnoreCase("facebook"))
				{
					String profileurl= notificationobject.getUserId().getProfileImage();
					if(profileurl.contains("http"))
					{
						profileurl=profileurl.replace("http", "https");
						CustomerImageUrl=profileurl;
					}
				}
				else
				{
					CustomerImageUrl = notificationobject.getUserId().getProfileImage();
				}
				setProfileImage();
			}
		
		
		}

	private void getIntentValue() {
		// TODO Auto-generated method stub
		
		if(getActivity().getIntent().hasExtra("AVAILABLE_OBJECT")){
			
			availableJobs = (AvailableJobs)getActivity().getIntent().getSerializableExtra("AVAILABLE_OBJECT");
			
		}
		if(getActivity().getIntent().hasExtra("payment")){
			
			paymentObject = (PaymentHolder)getActivity().getIntent().getSerializableExtra("payment");
			if(paymentObject != null){
				if(paymentObject.getJobId() != null){
					availableJobs = paymentObject.getJobId();
				}
			}
		}
		
		if(getActivity().getIntent().hasExtra("review"))
		{
			fixerJobReviews=(FixerReviews)getActivity().getIntent().getSerializableExtra("review");
			
		}

		if(getActivity().getIntent().hasExtra("QUOTE_OBJECT")){
			
			quoteObject=(Quote)getActivity().getIntent()
					.getSerializableExtra("QUOTE_OBJECT");
			//getActivity().getIntent().removeExtra("QUOTE_OBJECT");
		}
		if(getActivity().getIntent().hasExtra("QUOTED_OBJECT_FROM_QUOTE")){
			
			availableJobs = (AvailableJobs)getActivity().getIntent().getSerializableExtra("QUOTED_OBJECT_FROM_QUOTE");
			//getActivity().getIntent().removeExtra("QUOTED_OBJECT_FROM_QUOTE");
		}
		if(getActivity().getIntent().hasExtra("QUOTED_OBJECT_FROM_ONGOING")){
			
			availableJobs = (AvailableJobs)getActivity().getIntent().getSerializableExtra("QUOTED_OBJECT_FROM_ONGOING");
			//getActivity().getIntent().removeExtra("QUOTED_OBJECT_FROM_ONGOING");
		}
		if(getActivity().getIntent().hasExtra("QUOTED_OBJECT_FROM_COMPLETE")){
			
			availableJobs = (AvailableJobs)getActivity().getIntent().getSerializableExtra("QUOTED_OBJECT_FROM_COMPLETE");
			//getActivity().getIntent().removeExtra("QUOTED_OBJECT_FROM_COMPLETE");
		}
		
		//NOTIFICATION OBJECT FROM  ONGOING
		 
		 if (getActivity().getIntent().hasExtra("notification_ongoing")) {
			 notificationobject = (Job) getActivity().getIntent()
						.getSerializableExtra("notification_ongoing");
			 //getActivity().getIntent().removeExtra("notification_ongoing");
			}
		 
		 //NOTIFICATION OBJECT FROM COMPLETE
		 
		 
		 if (getActivity().getIntent().hasExtra("notification_completed")) {
			 notificationobject = (Job) getActivity().getIntent()
						.getSerializableExtra("notification_completed");
			// getActivity().getIntent().removeExtra("notification_completed");
			}
		
		 if(getActivity().getIntent().hasExtra("From_notification_pending")){
			 notificationobject = (Job) getActivity().getIntent()
						.getSerializableExtra("From_notification_pending");
			 //getActivity().getIntent().removeExtra("From_notification_pending");
			 
		 }
		 if (getActivity().getIntent().hasExtra("notification_cancelled")) {
			 notificationobject = (Job) getActivity().getIntent()
						.getSerializableExtra("notification_cancelled");
			// getActivity().getIntent().removeExtra("notification_cancelled");
			}
		 
		 
		 if (getActivity().getIntent().hasExtra("notification_chat")) {
			 notificationobject = (Job) getActivity().getIntent()
						.getSerializableExtra("notification_chat");
			 Log.e("", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+notificationobject.getId());
			 //getActivity().getIntent().removeExtra("notification_chat");
			}
		 
		 if(getActivity().getIntent().hasExtra("notification_jobid")){
				notificationJobId = (String) getActivity().getIntent().getStringExtra("notification_jobid");
			}
		 if (getActivity().getIntent().hasExtra("quoteid")) {

			 QuoteId = getActivity().getIntent().getExtras().getString("quoteid");
				getActivity().getIntent().removeExtra("quoteid");
		
		 }
	}

	private void initManager() {
		// TODO Auto-generated method stub
		quotecallback = new QuoteCallBack();
		requestPaymentCallBack = new RequestPaymentCallBack();
		acceptCashCallBack = new AcceptCashCallBack();
		requestReviewcallback = new RequestReviewCallBack();
		jobDetailsCallback = new JobDetailsCallback();
	}

	@SuppressLint("NewApi")
	private void setVisibilityofYourFixesCancelled() {
		// TODO Auto-generated method stub
		accepted_quote_textview.setVisibility(View.GONE);
		payment_status_layout.setGravity(Gravity.CENTER);
		payment_status_text.setText("CANCELLED");
		payment_status_text.setGravity(Gravity.CENTER);
		textview_fix_completed.setText("Fix status: Cancelled");
		enter_or_edit_quote_layout.setVisibility(View.GONE);
		fix_completed.setVisibility(View.GONE);
		payment_status_layout.setBackground(getResources().getDrawable(
				R.drawable.grey_round_corner));
		// payment_status_layout.setBackgroundColor(getResources().getColor(R.color.grey_text));
		cancelfix_button_layout.setVisibility(View.GONE);
		linearlayout_review_fixer.setVisibility(View.GONE);
		
		
		if(availableJobs.getImages() != null){
			if (UtilValidate.isNotNull(availableJobs.getImages()) && UtilValidate.isNotEmpty(availableJobs.getImages())) {
				SetFixImages(availableJobs.getImages());
			}
			else
			{
				HorizondalListview.setVisibility(View.GONE);
				emptyimages.setVisibility(View.VISIBLE);
			}
			/*if(availableJobs.getJobStatus()!=null){
				textviewfix_completed.setText("Fix status:" + availableJobs.getJobStatus());
			}*/
			if(availableJobs.getJobDetails() != null){
				additionaldetails.setText(availableJobs.getJobDetails());
			}
			if(availableJobs.getJobDateTimeSlot() != null){
				fix_details_item_date_edittext.setText(availableJobs.getJobDateTimeSlot());
			}
			if ((availableJobs.getUserId().getFirstName()!=null)&&(availableJobs.getUserId().getLastName())!=null) {

				textviewCompanyName.setText(availableJobs.getUserId().getFirstName()+" "+availableJobs.getUserId().getLastName());

			} else {
				textviewCompanyName.setText(availableJobs.getUserId().getFirstName());
			}
			if(UtilValidate.isNotNull(availableJobs.getQuote())){
				if(UtilValidate.isNotNull(availableJobs.getQuote().getQuoteAmount())){
					accepted_quote_textview.setText("$ " +availableJobs.getQuote().getQuoteAmount() );
				}else{
					accepted_quote_textview.setText(" ");
				}
			}
			if(UtilValidate.isNotNull(availableJobs.getAddress())){
				StringBuilder address = new StringBuilder();
				if(UtilValidate.isNotEmpty(availableJobs.getAddress().getRoad_building())){
					address.append(availableJobs.getAddress().getRoad_building()+",");
				}
				if(UtilValidate.isNotEmpty(availableJobs.getAddress().getBlock_number())){
					address.append(availableJobs.getAddress().getBlock_number()+",");
				}
				if(UtilValidate.isNotEmpty(availableJobs.getAddress().getFloor())){
					address.append(availableJobs.getAddress().getFloor()+",");
				}
				if(UtilValidate.isNotEmpty(availableJobs.getAddress().getUnit())){
					address.append(availableJobs.getAddress().getUnit()+",");
				}
				if(UtilValidate.isNotEmpty(availableJobs.getAddress().getPostal_code())){
					address.append(availableJobs.getAddress().getPostal_code()+".");
				}
				
				fix_details_item_address_edittext.setText(address.toString());
			}
		}
		if(availableJobs.getUserId().getProfileImage()!=null){
			if(availableJobs.getUserId().getLoginType().equalsIgnoreCase("facebook"))
			{
				String profileurl=availableJobs.getUserId().getProfileImage();
				if(profileurl.contains("http"))
				{
					profileurl=profileurl.replace("http", "https");
				}
				CustomerImageUrl=profileurl;
			}
			else
			{
				CustomerImageUrl = availableJobs.getUserId().getProfileImage();
			}
			setProfileImage();
		}
	}

	// ***************************************************************************************************
	@SuppressLint("NewApi")
	private void setVisibilityofYourFixesPending() {
		// TODO Auto-generated method stub
		payment_status_text.setText("PENDING PAYMENT:");
		accepted_quote_textview.setText("$146");
		enter_or_edit_quote_layout.setVisibility(View.GONE);
		textview_fix_completed.setText("Fix status: Completed");
		fix_completed.setVisibility(View.GONE);
		payment_request_accept_layout.setVisibility(View.VISIBLE);
		payment_status_layout.setBackground(getResources().getDrawable(
				R.drawable.yellow_rounded_edge));
		// payment_status_layout.setBackgroundColor(getResources().getColor(R.color.pending_yellow));
		cancelfix_button_layout.setVisibility(View.GONE);
		linearlayout_review_fixer.setVisibility(View.GONE);
		
	
		//Setting values
		if(availableJobs.getImages() != null){
			if (UtilValidate.isNotNull(availableJobs.getImages()) && UtilValidate.isNotEmpty(availableJobs.getImages())) {
				SetFixImages(availableJobs.getImages());
			}
			else
			{
				HorizondalListview.setVisibility(View.GONE);
				emptyimages.setVisibility(View.VISIBLE);
			}
			/*if(availableJobs.getJobStatus()!=null){
				textviewfix_completed.setText("Fix status:" + availableJobs.getJobStatus());
			}*/
			if(availableJobs.getJobDetails() != null){
				additionaldetails.setText(availableJobs.getJobDetails());
			}
			if(availableJobs.getJobDateTimeSlot() != null){
				fix_details_item_date_edittext.setText(availableJobs.getJobDateTimeSlot());
			}
			if (UtilValidate.isNotNull(availableJobs.getUserId().getFirstName()+availableJobs.getUserId().getLastName())) {

				textviewCompanyName.setText(availableJobs.getUserId().getFirstName()+" "+availableJobs.getUserId().getLastName());

			} else {
				textviewCompanyName.setText(availableJobs.getUserId().getFirstName());
			}
			if(UtilValidate.isNotNull(availableJobs.getQuote())){
				if(UtilValidate.isNotNull(availableJobs.getQuote().getQuoteAmount())){
					accepted_quote_textview.setText("$ " +availableJobs.getQuote().getQuoteAmount() );
				}else{
					accepted_quote_textview.setText(" ");
				}
			}
			if(UtilValidate.isNotNull(availableJobs.getAddress())){
				StringBuilder address = new StringBuilder();
				if(UtilValidate.isNotEmpty(availableJobs.getAddress().getRoad_building())){
					address.append(availableJobs.getAddress().getRoad_building()+",");
				}
				if(UtilValidate.isNotEmpty(availableJobs.getAddress().getBlock_number())){
					address.append(availableJobs.getAddress().getBlock_number()+",");
				}
				if(UtilValidate.isNotEmpty(availableJobs.getAddress().getFloor())){
					address.append(availableJobs.getAddress().getFloor()+",");
				}
				if(UtilValidate.isNotEmpty(availableJobs.getAddress().getUnit())){
					address.append(availableJobs.getAddress().getUnit()+",");
				}
				if(UtilValidate.isNotEmpty(availableJobs.getAddress().getPostal_code())){
					address.append(availableJobs.getAddress().getPostal_code()+".");
				}
				
				fix_details_item_address_edittext.setText(address.toString());
			}
		}
		if(availableJobs.getUserId().getProfileImage()!=null){
			if(availableJobs.getUserId().getLoginType().equalsIgnoreCase("facebook"))
			{
				String profileurl=availableJobs.getUserId().getProfileImage();
				if(profileurl.contains("http"))
				{
					profileurl=profileurl.replace("http", "https");
				}
				CustomerImageUrl=profileurl;
			}
			else
			{
				CustomerImageUrl = availableJobs.getUserId().getProfileImage();
			}
			
			setProfileImage();
		}
	}

	private void setVisibilityofYourFixesPaid() {
		// TODO Auto-generated method stub
		payment_status_text.setText("Paid:");
		enter_or_edit_quote_layout.setVisibility(View.GONE);
		textview_fix_completed.setText("Fix status: Completed");
		fix_completed.setVisibility(View.GONE);
		cancelfix_button_layout.setVisibility(View.GONE);
		linearlayout_review_fixer.setVisibility(View.GONE);
		payment_status_layout.setBackground(getResources().getDrawable(
				R.drawable.dark_green_rounded_corner));
		payment_request_accept_layout.setVisibility(View.GONE);
		if(availableJobs!=null)
		{
		if(availableJobs.getJobReview() != null){
			rating_layout.setVisibility(View.VISIBLE);
			if(UtilValidate.isNotNull(availableJobs.getJobReview().getStar())){
				ratingbar_ratingReview_paid.setRating(availableJobs.getJobReview().getStar());
			}
		}else{
			rating_layout.setVisibility(View.VISIBLE);
			layout_request_review.setVisibility(View.VISIBLE);
			ratingbar_ratingReview_paid.setRating(0);
		}
		}
		if(availableJobs!=null)
		{
		//Setting Values
		if(availableJobs.getImages() != null){
			if (UtilValidate.isNotNull(availableJobs.getImages()) && UtilValidate.isNotEmpty(availableJobs.getImages())) {
				SetFixImages(availableJobs.getImages());
			}
			else
			{
				HorizondalListview.setVisibility(View.GONE);
				emptyimages.setVisibility(View.VISIBLE);
			}
		}}
			/*if(availableJobs.getJobStatus()!=null){
				textviewfix_completed.setText("Fix status:" + availableJobs.getJobStatus());
			}*/
		if(availableJobs!=null)
		{
			if(availableJobs.getJobDetails() != null){
				additionaldetails.setText(availableJobs.getJobDetails());
			}
		}
		if(availableJobs!=null)
		{
			if(availableJobs.getJobDateTimeSlot() != null){
				fix_details_item_date_edittext.setText(availableJobs.getJobDateTimeSlot());
			}
		}
		if(availableJobs!=null)
		{
			if (UtilValidate.isNotNull(availableJobs.getUserId().getFirstName()+availableJobs.getUserId().getLastName())) {

				textviewCompanyName.setText(availableJobs.getUserId().getFirstName()+" "+availableJobs.getUserId().getLastName());

			} else {
				textviewCompanyName.setText(availableJobs.getUserId().getFirstName());
			}
		}if(availableJobs!=null)
		{
			if(UtilValidate.isNotNull(availableJobs.getQuote())){
				if(UtilValidate.isNotNull(availableJobs.getQuote().getQuoteAmount())){
					accepted_quote_textview.setText("$ " +availableJobs.getQuote().getQuoteAmount() );
				}else{
					accepted_quote_textview.setText(" ");
				}
			}
		}else{
				if(paymentObject != null ){
					if(paymentObject.getQuoteId() != null ){
						if(paymentObject.getQuoteId().getQuoteAmount() != 0 ){
							accepted_quote_textview.setText("$ " +paymentObject.getQuoteId().getQuoteAmount());
						}else{
							accepted_quote_textview.setText(" ");
						}
					}
				}
			}
		if(availableJobs!=null)
		{
			if(UtilValidate.isNotNull(availableJobs.getAddress())){
				StringBuilder address = new StringBuilder();
				if(UtilValidate.isNotEmpty(availableJobs.getAddress().getRoad_building())){
					address.append(availableJobs.getAddress().getRoad_building()+",");
				}
				if(UtilValidate.isNotEmpty(availableJobs.getAddress().getBlock_number())){
					address.append(availableJobs.getAddress().getBlock_number()+",");
				}
				if(UtilValidate.isNotEmpty(availableJobs.getAddress().getFloor())){
					address.append(availableJobs.getAddress().getFloor()+",");
				}
				if(UtilValidate.isNotEmpty(availableJobs.getAddress().getUnit())){
					address.append(availableJobs.getAddress().getUnit()+",");
				}
				if(UtilValidate.isNotEmpty(availableJobs.getAddress().getPostal_code())){
					address.append(availableJobs.getAddress().getPostal_code()+".");
				}
				
				fix_details_item_address_edittext.setText(address.toString());
			}}
		if(availableJobs!=null)
		{
			if(availableJobs.getUserId().getProfileImage()!=null){
				
				if(availableJobs.getUserId().getLoginType().equalsIgnoreCase("facebook"))
				{
					String profileurl=availableJobs.getUserId().getProfileImage();
					if(profileurl.contains("http"))
					{
						profileurl=profileurl.replace("http", "https");
					}
					
					CustomerImageUrl=profileurl;
				}
				else
				{
					CustomerImageUrl = availableJobs.getUserId().getProfileImage();
				}
				
				setProfileImage();
			}
		}
		
	}

	private void setVisibilityofAvailableFixes() {
		// TODO Auto-generated method stub
		accepted_quote_textview.setText("NA");
		textview_fix_completed.setText("Fix status: Pending");
		cancelfix_button_layout.setVisibility(View.GONE);
		linearlayout_review_fixer.setVisibility(View.GONE);
		fix_completed.setText("Enter/Edit Quote");
		
				if(availableJobs!=null)
				{
					if(availableJobs.getImages()!=null)
					{
						if(!availableJobs.getImages().isEmpty())
								{
							CustomerImageList=availableJobs.getImages();
							Log.e("","imagelist<<<<<<<<<<<<<<<<<<<<<<<<,,"+CustomerImageList);
							
							SetFixImages(CustomerImageList);
								}
						else
						{
							HorizondalListview.setVisibility(View.GONE);
							emptyimages.setVisibility(View.VISIBLE);
						}
					}
		

		if (UtilValidate.isNotNull(availableJobs.getJobStatus())) {
			textviewfix_completed.setText("Fix status:" + availableJobs.getJobStatus());
		}

		if (UtilValidate.isNotNull(availableJobs.getJobDetails())) {
			additionaldetails.setText(availableJobs.getJobDetails());
		}

		if (UtilValidate.isNotNull(availableJobs.getQuote())){
		if (UtilValidate.isNotNull(availableJobs.getQuote().getQuoteAmount())) {
			accepted_quote_textview.setText("$ " + availableJobs.getQuote().getQuoteAmount());
		} else {
			accepted_quote_textview.setText("$0");

		}
		}

		if ((UtilValidate.isNotNull(availableJobs.getJobDate()))||(UtilValidate.isNotNull(availableJobs.getJobDateTimeSlot()))) {
			fix_details_item_date_edittext.setText(availableJobs.getJobDateTimeSlot());
		} else {
			fix_details_item_date_edittext.setText(" ");
		}
		
		if (availableJobs.getAddress()!=null){

		if (UtilValidate.isNotNull(availableJobs.getAddress().getPostal_code())) {

			fix_details_item_address_edittext.setText(availableJobs.getAddress().getBlock_number() + availableJobs.getAddress().getFloor()
					+ availableJobs.getAddress().getUnit() + availableJobs.getAddress().getRoad_building() + availableJobs.getAddress().getPostal_code());
		}
		}
		if (availableJobs.getUserId()!=null){
		if ((availableJobs.getUserId().getFirstName()!=null)||(availableJobs.getUserId().getLastName()!=null)) {

			textviewCompanyName.setText(availableJobs.getUserId().getFirstName()+" "+availableJobs.getUserId().getLastName());

		} else {
			textviewCompanyName.setText(availableJobs.getUserId().getFirstName());
		}
		
		if(availableJobs.getUserId().getProfileImage()!=null/*)&&(!availableJobs.getUserId().getProfileImage().isEmpty())*/){
			
			if(availableJobs.getUserId().getProfileImage()!=null)
			{
				String profileurl=availableJobs.getUserId().getProfileImage();
				if(profileurl.contains("http"))
				{
					profileurl=profileurl.replace("http", "https");
				}
				CustomerImageUrl=profileurl;
			}
			else
			{
				CustomerImageUrl = availableJobs.getUserId().getProfileImage();
			}
			setProfileImage();
		}
		}

	}
	}

	private void setVisibilityofYourFixes() {
		// TODO Auto-generated method stub
		accepted_quote_textview.setText("NA");
		textview_fix_completed.setText("Fix status: Assigned");
		cancelfix_button_layout.setVisibility(View.GONE);
		linearlayout_review_fixer.setVisibility(View.GONE);
		fix_completed.setText("Edit Quote");
		payment_status_layout.setBackground(getResources().getDrawable(
				R.drawable.dark_green_rounded_corner));
		payment_status_text.setText("QUOTE:");
		//detailsTabDataHolder = (DetailsTabDataHolder) getActivity().getIntent().getSerializableExtra("detailsTabData");
		
		if(availableJobs != null){
			if(availableJobs.getImages() != null){
				if (UtilValidate.isNotNull(availableJobs.getImages()) && UtilValidate.isNotEmpty(availableJobs.getImages())) {
					SetFixImages(availableJobs.getImages());
				}
				else
				{
					HorizondalListview.setVisibility(View.GONE);
					emptyimages.setVisibility(View.VISIBLE);
				}
			}
				/*if(availableJobs.getJobStatus()!=null){
					textviewfix_completed.setText("Fix status:" + availableJobs.getJobStatus());
				}*/
				if(availableJobs.getJobDetails() != null){
					additionaldetails.setText(availableJobs.getJobDetails());
				}
				if(availableJobs.getJobDateTimeSlot() != null){
					fix_details_item_date_edittext.setText(availableJobs.getJobDateTimeSlot());
				}
				/*if (UtilValidate.isNotNull(CurrentlyLoggedUserDAO.getInstance()
						.getUserDetails().getUser().getCompanyName())) {
	
					textviewCompanyName.setText(CurrentlyLoggedUserDAO.getInstance()
							.getUserDetails().getUser().getCompanyName());
	
				} else {
					textviewCompanyName.setText(" ");
				}*/
				
				if((availableJobs.getUserId().getFirstName()!=null)||(availableJobs.getUserId().getLastName())!=null)
				{
					textviewCompanyName.setText(availableJobs.getUserId().getFirstName()+" "+availableJobs.getUserId().getLastName());
					
				}
				else
				{
					textviewCompanyName.setText(availableJobs.getUserId().getFirstName());
				}
				if(UtilValidate.isNotNull(availableJobs.getQuote())){
					if(UtilValidate.isNotNull(availableJobs.getQuote().getQuoteAmount())){
						accepted_quote_textview.setText("$" +availableJobs.getQuote().getQuoteAmount() );
					}else{
						accepted_quote_textview.setText(" ");
					}
				}
				if(UtilValidate.isNotNull(availableJobs.getAddress())){
					StringBuilder address = new StringBuilder();
					if(UtilValidate.isNotEmpty(availableJobs.getAddress().getRoad_building())){
						address.append(availableJobs.getAddress().getRoad_building()+",");
					}
					if(UtilValidate.isNotEmpty(availableJobs.getAddress().getBlock_number())){
						address.append(availableJobs.getAddress().getBlock_number()+",");
					}
					if(UtilValidate.isNotEmpty(availableJobs.getAddress().getFloor())){
						address.append(availableJobs.getAddress().getFloor()+",");
					}
					if(UtilValidate.isNotEmpty(availableJobs.getAddress().getUnit())){
						address.append(availableJobs.getAddress().getUnit()+",");
					}
					if(UtilValidate.isNotEmpty(availableJobs.getAddress().getPostal_code())){
						address.append(availableJobs.getAddress().getPostal_code()+".");
					}
					
					fix_details_item_address_edittext.setText(address.toString());
				}
				if(availableJobs.getUserId().getProfileImage()!=null){
					
					if(availableJobs.getUserId().getLoginType().equalsIgnoreCase("facebook"))
					{
						String profileurl=availableJobs.getUserId().getProfileImage();
						if(profileurl.contains("http"))
						{
							profileurl=profileurl.replace("http", "https");
						}
						CustomerImageUrl=profileurl;
					}
					else
					{
						CustomerImageUrl = availableJobs.getUserId().getProfileImage();
					}
					
					setProfileImage();
				}
				
		}
		if(availableJobs.getJobStatus()!=null)
		{
			textview_fix_completed.setText("Fix Status: Confirmed");
		}
		else
		{
			textview_fix_completed.setText("");
		}
		
		
	}


	public class QuoteCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (responseCode == requestcode) {
				EmailSignupBaseHolder quotedataHolder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(quotedataHolder)) {
					if (quotedataHolder.isSuccess()) {
						
						  InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
					        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
						
						if(quotedataHolder.getData().getQuote() != null){
							ChatTabFragment.quoteId = quotedataHolder.getData().getQuote().getId();
						}
						Log.e("", "QQQQ: "+quotedataHolder.getData().getQuote().getId());
						/*Toast.makeText(getActivity(),
								quotedataHolder.getData().getMessage(),
								Toast.LENGTH_LONG).show();*/
						if(!EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
								Constants.FROM_NOTIFICATION)){
							Utils.showDialog(getActivity(), 	quotedataHolder.getData().getMessage(),"SUCCESS");
						}

					} else {
					/*	Toast.makeText(getActivity(),
								quotedataHolder.isSuccess() + "",
								Toast.LENGTH_LONG).show();*/
						Utils.showDialog(getActivity(),"Something went wrong,please try again","SUCCESS");
					}

				}
			} else {
				OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
				if (UtilValidate.isNotNull(onFailureErrorHolder)) {
					for (ErrorMessageListHolder error : onFailureErrorHolder
							.getData()) {
						Utils.showDialog(getActivity(), 	error.getMessage(),"ERROR");
					/*	Toast.makeText(getActivity(), "?" + error.getMessage(),
								Toast.LENGTH_LONG).show();*/

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

	
	public void SetFixImages(List<String>CustomerImage)
	{

		CustomerImageList=CustomerImage;
		
		if(CustomerImageList!=null)
		{
			imageSettingAdapter = new ImageSettingAdapter(getActivity(),CustomerImageList);
            HorizondalListview.setAdapter(imageSettingAdapter);
			imageSettingAdapter.notifyDataSetChanged();
			HorizondalListview.setVisibility(View.VISIBLE);
			emptyimages.setVisibility(View.GONE);
		}
		
		
		
		
		HorizondalListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {


		    	LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    	
		    	View popupView = inflater.inflate(R.layout.imagepopuppager, null);
		    	popupWindow = new PopupWindow(popupView,android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT,true);
				popupWindow.update();
				
				popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
				
				popupWindow.setOutsideTouchable(true);
				popupWindow.setFocusable(true);
				new ScaleInAnimation(popupView).animate();
				popupWindow.getContentView().setFocusableInTouchMode(true);
				popupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {

						if (keyCode == KeyEvent.KEYCODE_BACK) {
							popupWindow.dismiss();
							return true;
						}
						return false;
					}
				});
				
				final ImageView okbutton=(ImageView)popupView.findViewById(R.id.okbutton);
				
				ExtendedViewPager mViewPager = (ExtendedViewPager) popupView.findViewById(R.id.view_pager);
			        mViewPager.setAdapter(new TouchImageAdapter(CustomerImageList,getActivity()));
			        mViewPager.setCurrentItem(position);
		
			        okbutton.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							popupWindow.dismiss();
						}
					});
			  
			       
			}
		});
	
}

	 static class TouchImageAdapter  extends PagerAdapter {

			private  List<String>imageList;
			LayoutInflater inflater;
			Activity activity;

			public TouchImageAdapter(
					List<String>imageList,Activity activity) {
				// TODO Auto-generated constructor stub
				this.imageList = imageList;
				this.activity=activity;
				
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return imageList.size();
			}

			@Override
			public boolean isViewFromObject(View view, Object imageView) {
				// TODO Auto-generated method stub
				return view == ((ImageView)imageView );
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				TouchImageView productserviceimage = new TouchImageView(activity);
				//container.addView(productserviceimage, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
				//productserviceimage.setImageResource(imageList.get(position));
				Log.e("", "url:"+imageList.get(position));
				if(UtilValidate.isNotNull(imageList.get(position))){
					productserviceimage.setScaleType(ScaleType.FIT_XY);
					if(UtilValidate.isNotEmpty(imageList.get(position))){
					
						Picasso.with(activity)
						.load(imageList.get(position))
						.placeholder(R.drawable.empty_photo)
						.error(R.drawable.empty_photo).into(productserviceimage);
					/*	Picasso.with(activity).load(imageList.get(position))
						.into(productserviceimage);
						*/
					}
				}
				
				
				
			
				
				
				((ViewPager)container).addView(productserviceimage);
					
				return productserviceimage;
			}
			
			@Override
		    public void destroyItem(View container, int position, Object object) {
		         ((ViewPager) container).removeView((View) object);
		    }
		}

	
	
	private void setProfileImage(){
		if (UtilValidate.isNotNull(CustomerImageUrl)) {
			

			if (!(CustomerImageUrl.equalsIgnoreCase(""))) {

				Picasso.with(getActivity())
						.load((CustomerImageUrl))
						.placeholder(R.drawable.profile_image_null)
						.error(R.drawable.profile_image_null)
						.transform(new RoundTransform())
						.into(image_avatar_review);
			} else {
				Picasso.with(getActivity()).load(R.drawable.profile_image_null)
						.placeholder(R.drawable.profile_image_null)
						.error(R.drawable.profile_image_null)
						.transform(new RoundTransform())
						.into(image_avatar_review);
			}

		} else {
			Picasso.with(getActivity()).load(R.drawable.profile_image_null)
					.placeholder(R.drawable.profile_image_null)
					.error(R.drawable.profile_image_null)
					.transform(new RoundTransform()).into(image_avatar_review);
		}
	}
	
	private class RequestPaymentCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if(UtilValidate.isNotNull(result)){
				EndpointResonseBaseHolder endpointResonseBaseHolder = (EndpointResonseBaseHolder)result;
				if(UtilValidate.isNotNull(endpointResonseBaseHolder)){
					if(endpointResonseBaseHolder.isSuccess()){
						if(UtilValidate.isNotNull(endpointResonseBaseHolder.getData())){
							if(UtilValidate.isNotNull(endpointResonseBaseHolder.getData().getMessage())){
								Toast.makeText(getActivity(), endpointResonseBaseHolder.getData().getMessage(), 
										Toast.LENGTH_LONG).show();
							}
						}
					}else{

						if(UtilValidate.isNotNull(endpointResonseBaseHolder.getData())){
							if(UtilValidate.isNotNull(endpointResonseBaseHolder.getData().getMessage())){
								Toast.makeText(getActivity(), endpointResonseBaseHolder.getData().getMessage(), 
										Toast.LENGTH_LONG).show();
							}
						}
					}
				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			//Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
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
	
	private class AcceptCashCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			if(result != null){
				AcceptCashBaseHolder acceptCashBaseHolder = (AcceptCashBaseHolder) result;
				if(acceptCashBaseHolder.isSuccess()){
					if(acceptCashBaseHolder.getData() != null){
						if(acceptCashBaseHolder.getData().getPayment() != null){
							if(UtilValidate.isNotEmpty(acceptCashBaseHolder.getData().getPayment().getPaymentStatus())){
								if(acceptCashBaseHolder.getData().getPayment().getPaymentStatus().equalsIgnoreCase("Completed")){
									setVisibilityFromNotificationCompletePaid();
									Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
								}
							}
						}
					}
				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			//Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
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
	
	private class RequestReviewCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if(requestcode == responseCode){
				if(result != null){
					EndpointResonseBaseHolder endpointResonseBaseHolder = (EndpointResonseBaseHolder) result;
					if(endpointResonseBaseHolder.isSuccess()){
						if(endpointResonseBaseHolder.getData() != null){
							if(endpointResonseBaseHolder.getData().getMessage() != null){
								Utils.showDialog(getActivity(), ""+endpointResonseBaseHolder.getData().getMessage(), "EasyHomeFix");
								//Toast.makeText(getActivity(),endpointResonseBaseHolder.getData().getMessage(), Toast.LENGTH_LONG).show();
							}
						}
					}
				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			if (pDialog != null) {
				pDialog.dismiss();
			}
			//Toast.makeText(getActivity(),result, Toast.LENGTH_LONG).show();
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
	
	private class JobDetailsCallback implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {

			if(result != null){
				EmailSignupBaseHolder jobDetails = (EmailSignupBaseHolder)result;
				if(jobDetails.isSuccess()){
					if(jobDetails.getData() != null){
						if(jobDetails.getData().getJob() != null){
							notificationobject = jobDetails.getData().getJob();
							if(notificationobject.getQuote() != null){
								if(notificationobject.getJobStatus() != null){
									if(notificationobject.getQuote().getStatus().equalsIgnoreCase("3"))
									{
										 cancel="true";
										setVisibilityFromNotificationCancell();
										
									}
									else if(notificationobject.getJobStatus().equalsIgnoreCase("Cancelled"))
											{
										setVisibilityFromNotificationCancell();
									}else if(notificationobject.getJobStatus().equalsIgnoreCase("Completed") &&
											notificationobject.getPayment() != null){
										setVisibilityFromNotificationCompletePaid();
									}else if(notificationobject.getJobStatus().equalsIgnoreCase("Completed") &&
											notificationobject.getPayment() == null){
										setVisibilityFromNotificationComplete();
									}else if(notificationobject.getJobStatus().equalsIgnoreCase("Ongoing")){
										setVisibilityFromNotificationOngoing();
									}else if(notificationobject.getJobStatus().equalsIgnoreCase("Pending")){
										setVisibilityFromNotificationPending();
									}
								}
								ChatTabFragment.quoteId = notificationobject.getQuote().getId();
							}else{
								EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION);
								QuoteManager.getInstance().AddQuote(getActivity(),
										"0",notificationobject.getId(), quotecallback, requestcode);			
								if(notificationobject.getJobStatus() != null){
									if(notificationobject.getJobStatus().equalsIgnoreCase("Completed") &&
											notificationobject.getPayment() != null){
										setVisibilityFromNotificationCompletePaid();
									}else if(notificationobject.getJobStatus().equalsIgnoreCase("Completed") &&
											notificationobject.getPayment() == null){
										setVisibilityFromNotificationComplete();
									}else if(notificationobject.getJobStatus().equalsIgnoreCase("Ongoing")){
										setVisibilityFromNotificationOngoing();
									}else if(notificationobject.getJobStatus().equalsIgnoreCase("Pending")){
										setVisibilityFromNotificationPending();
									}else if(notificationobject.getJobStatus().equalsIgnoreCase("Cancelled")){
										setVisibilityFromNotificationCancell();
									}
								}
							
							}
						}
					}
				}
				if(pDialog != null){
					pDialog.cancel();
				}
			}
			
		}

		@Override
		public void onFinish(int responseCode, String result) {
			if(pDialog != null){
				pDialog.cancel();
			}
			//Toast.makeText(getActivity(), result , Toast.LENGTH_LONG).show();
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


	@Override
	public void onResume() {
		// TODO Auto-generated method stubCancel
		super.onResume();
		
		getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("nofication_received"));
		/* progressDialog = ProgressDialog.show(getActivity(), null,
					"Loading...");
		 progressDialog.setCancelable(true);*/

		if(availableJobs != null){
			if(availableJobs.getId() != null){
				pDialog = ProgressDialog.show(
						getActivity(), null, "Loading..",
						true);
				pDialog.setCancelable(true);
				NotificationManager.getInstance().jobListItemObject(getActivity(),availableJobs.getId(),
					 jobDetailsCallback,requestcode);
			}
		}else if(paymentObject != null){
			if(paymentObject.getJobId() != null){
				if(paymentObject.getJobId().getId() != null){
					pDialog = ProgressDialog.show(
							getActivity(), null, "Loading..",
							true);
					pDialog.setCancelable(true);
					NotificationManager.getInstance().jobListItemObject(getActivity(),paymentObject.getJobId().getId(),
						 jobDetailsCallback,requestcode);
				}
			}
		}else if(fixerJobReviews != null){
			if(fixerJobReviews.getJobId() != null){
				if(fixerJobReviews.getJobId().getId() != null){
					pDialog = ProgressDialog.show(
							getActivity(), null, "Loading..",
							true);
					pDialog.setCancelable(true);
					NotificationManager.getInstance().jobListItemObject(getActivity(),fixerJobReviews.getJobId().getId(),
						 jobDetailsCallback,requestcode);
				}
			}
		}else if(notificationobject != null){
			if(notificationobject.getId() != null){
				if(pDialog != null){
					if(!pDialog.isShowing()){
						pDialog = ProgressDialog.show(
								getActivity(), null, "Loading..",
								true);
						pDialog.setCancelable(true);
					}
				}
				if(!isFromNotfication){
					NotificationManager.getInstance().jobListItemObject(getActivity(),notificationobject.getId(),
						 jobDetailsCallback,requestcode);
				}
			}
		}else if(notificationJobId != null){
			if(pDialog != null){
				if(!pDialog.isShowing()){
					pDialog = ProgressDialog.show(
							getActivity(), null, "Loading..",
							true);
					pDialog.setCancelable(true);
				}
			}
			if(!isFromNotfication){
				NotificationManager.getInstance().jobListItemObject(getActivity(),notificationJobId,
					 jobDetailsCallback,requestcode);
			}
		}
	}
	
	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
		
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isFromNotfication = false;
		if(pDialog != null){
			pDialog.dismiss();
		}
		if(progressDialog != null){
			progressDialog.dismiss();
		}
	}
	
	
	
}
