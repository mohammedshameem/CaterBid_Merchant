package com.xminds.easyhomefix.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.easyandroidanimations.library.ScaleInAnimation;
import com.pkmmte.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.EndpointResonseBaseHolder;
import com.xminds.easyhomefix.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix.Holder.Job;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.JobList;
import com.xminds.easyhomefix.Holder.JobNotifications;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.Holder.Quotes;
import com.xminds.easyhomefix.Holder.QuotesBaseHolder;
import com.xminds.easyhomefix.Holder.UserHolder;
import com.xminds.easyhomefix.Managers.ChatManager;
import com.xminds.easyhomefix.Managers.JobCreationManager;
import com.xminds.easyhomefix.Managers.NotificationManager;
import com.xminds.easyhomefix.Managers.QuotesManager;
import com.xminds.easyhomefix.actionbar.TouchImageView;
import com.xminds.easyhomefix.activities.CustomerPaymentSelectionActivity;
import com.xminds.easyhomefix.activities.EditFixDetailsActivity;
import com.xminds.easyhomefix.activities.EmptyFixQuoteActivity;
import com.xminds.easyhomefix.activities.LandingPageActivity;
import com.xminds.easyhomefix.activities.ReviewActivity;
import com.xminds.easyhomefix.activities.ReviewServiceActivity;
import com.xminds.easyhomefix.activities.TrackFixFragmentTabhostActivity;
import com.xminds.easyhomefix.adapter.ImageSettingAdapter;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.circularimage.RoundTransform;
import com.xminds.easyhomefix.constants.Constants;
import com.xminds.easyhomefix.horizontallistview.HorizontalListView;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.viewpager.ExtendedViewPager;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class DetailsTabFragment extends Fragment {
	private View viewroot;
	private ProgressDialog pDialog;
	private String payment_Status = "payment_pending";
	private LinearLayout fix_completed_layout;
	private LinearLayout edit_details_button_layout;
	private LinearLayout viewAll_layout;
	private LinearLayout reject_quote_layout;
	private TextView textviewCompanyName;
	private RatingBar categoryNameRatingbar;
	private TextView viewAll_text;
	private TextView textviewTotalFix;
	private LinearLayout payment_status_layout;
	private TextView payment_status_text;
	private TextView payment_status_text_value;
	private TextView fix_completed;
	private LinearLayout pay_now_layout;
	private LinearLayout reject_accept_quote_layout;
	private EditText fix_details_item_address_edittext;
	private EditText fix_details_item_date_edittext;
	private LinearLayout review_fixer_layout;
	private LinearLayout cancel_fix_button_layout;
	private LinearLayout payment_layout;
	private LinearLayout detailtab_fixitem_layout;
	private TextView additionaldetails;
	private EditText categoryname;
	private ImageView categoryimage;
	private View view_id;
	private LinearLayout accept_quote_layout;
	private CircularImageView image_avatar_review;
	private String quoteid;
	private String companyname;
	private String rating;
	private String fixescompleted;
	private String quoteamount;
	private String MerchantImageUrl;
	private String jobId;
	private String catId;
	private String jobTypeId;
	String emailaddress;
	AcceptQuoteCallBack acceptquotesCallback;
	RejectQuotecallBack rejectQuotecallBack;
	int requestcode = 0;
	private String block_number;
	private String floor;
	private String unit;
	private String road_building;
	private String postal_code;
	private String jobdateandtime;
	private String jobdetails;
	private String fixerid;
	private String rejectCategory = "";
	private String Jobstatus;
	private String firstname;
	private String lastname;
	private TextView textviewfix_completed;
	private List<String> CustomerImageList = new ArrayList<String>();
	

	private boolean flagimageExpensive;
	private boolean flagimageWrong;
	private boolean flagimageTrustworthy;
	private boolean flagimageCompetent;
	private String quoteId;
	private Date date;
	private String actualDate;
	private CompleteFixCallBack completeFixCallBack;
	private CancelFixCallBack cancelFixCallBack;
	private String star;
	private String review;
	private JobList jobList;
	private String jobid;
	private Job notificationListObject;
	ImageSettingAdapter imageSettingAdapter;
	private HorizontalListView horizondallistview;
	private TextView emptyimage;
	PopupWindow popupWindow;
	private Quotes quotes;
	private String jobidreview;;
	private JobDetailsCallback jobDetailsCallback; 
	private String notificationJobId;
	public static UserHolder userHolder;
	public static String quoteAmount;
	public static String QuoteId;
	private boolean isFromNotfication = false;
	private String logintype;
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.e("","in on receive>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			if (intent.hasExtra("notification_jobid")) {
				if(notificationListObject != null){
					if(notificationListObject.getId() != null){
						if(notificationListObject.getId().equalsIgnoreCase(intent.getExtras().getString("notification_jobid"))){
							if(pDialog != null){
								if(!pDialog.isShowing()){
									pDialog = ProgressDialog.show(
											getActivity(), null, "Loading..",
											true);
									pDialog.setCancelable(true);
								}
							}
							Log.e("","in detaiil >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
							NotificationManager.getInstance().getNotificationListItemObject(getActivity(),notificationListObject.getId(),
									jobDetailsCallback,requestcode);
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

		getIntentValues();
		initManager();
		viewroot = inflater.inflate(R.layout.fix_details_details_tab, null);

		fix_completed_layout = (LinearLayout) viewroot
				.findViewById(R.id.fix_completed_layout);
		edit_details_button_layout = (LinearLayout) viewroot
				.findViewById(R.id.edit_details_button_layout);
		viewAll_layout = (LinearLayout) viewroot
				.findViewById(R.id.viewAll_layout);
		reject_quote_layout = (LinearLayout) viewroot
				.findViewById(R.id.reject_quote_layout);
		textviewCompanyName = (TextView) viewroot
				.findViewById(R.id.textviewCompanyName);
		categoryNameRatingbar = (RatingBar) viewroot
				.findViewById(R.id.categoryNameRatingbar);
		viewAll_text = (TextView) viewroot.findViewById(R.id.viewAll_text);
		textviewTotalFix = (TextView) viewroot
				.findViewById(R.id.textviewTotalFix);
		payment_status_layout = (LinearLayout) viewroot
				.findViewById(R.id.payment_status_layout);
		payment_status_text = (TextView) viewroot
				.findViewById(R.id.payment_status_text);
		payment_status_text_value = (TextView) viewroot
				.findViewById(R.id.payment_status_text_value);
		pay_now_layout = (LinearLayout) viewroot
				.findViewById(R.id.pay_now_layout);
		reject_accept_quote_layout = (LinearLayout) viewroot
				.findViewById(R.id.reject_accept_quote_layout);
		fix_details_item_address_edittext = (EditText) viewroot
				.findViewById(R.id.fix_details_item_address_edittext);
		fix_details_item_date_edittext = (EditText) viewroot
				.findViewById(R.id.fix_details_item_date_edittext);
		review_fixer_layout = (LinearLayout) viewroot
				.findViewById(R.id.review_fixer_layout);
		cancel_fix_button_layout = (LinearLayout) viewroot
				.findViewById(R.id.cancel_fix_button_layout);
		payment_layout = (LinearLayout) viewroot
				.findViewById(R.id.payment_status_layout);
		detailtab_fixitem_layout = (LinearLayout) viewroot
				.findViewById(R.id.details_tab_fix_item_layout);
		view_id = (View) viewroot.findViewById(R.id.view_id);
		accept_quote_layout = (LinearLayout) viewroot
				.findViewById(R.id.accept_quote_layout);
		image_avatar_review = (CircularImageView) viewroot
				.findViewById(R.id.image_avatar_review);
		additionaldetails = (TextView) viewroot
				.findViewById(R.id.details_tab_fix_item_additional_details_text);
		fix_completed=(TextView) viewroot
				.findViewById(R.id.fix_completed);
		textviewfix_completed=(TextView) viewroot
				.findViewById(R.id.textview_fixcompleted);
		horizondallistview=(HorizontalListView) viewroot.findViewById(R.id.listview);
		emptyimage=(TextView) viewroot
				.findViewById(R.id.emptyimages);
		categoryname=(EditText)viewroot.findViewById(R.id.fix_details_item_edittext);
		categoryimage=(ImageView)viewroot.findViewById(R.id.details_tab_fix_item_icon);
	

		flagimageExpensive = false;
		flagimageWrong = false;
		flagimageTrustworthy = false;
		flagimageCompetent = false;

		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_NOTIFICATION_PENDING))
				{
					//setVisibilityFromNotificationPaymentDetail();
					setVisibilityFromNotificationPending();
				}
		
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_NOTIFICATION_ONGOING))
				{
					//setVisibilityFromNotificationPaymentDetail();
					setVisibilityFromNotificationOngoing();
				}
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_NOTIFICATION_COMPLETE))
				{
					//setVisibilityFromNotificationPaymentDetail();
					setVisibilityFromNotificationComplete();
				}
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_NOTIFICATION_COMPLETE_PAID))
				{
					setVisibilityFromNotificationCompletePaid();
			
				}
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_NOTIFICATION_CANCELLED))
				{
					setVisibilityFromNotificationCancelled();
			
				}
		
		if (EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_PENDING)) 
			{
				setVisibilityofQuote();
			}

		if (EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_ONGOING)) {
			setVisibilityofOngoing();
		}
		if (EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_COMPLETED_PENDING)) {
			setVisibilityofPayment();
		}
		if (EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_COMPLETED_PAID)) {
			setVisibilityofPaymentPaid();
		}
		
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_COMPLETED_CANCELLED)){
			setVisibilityCancel();
		}
		
		if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_NOTIFICATION)){
			if(notificationJobId != null){
				pDialog = ProgressDialog.show(
						getActivity(), null, "Loading..",
						true);
				pDialog.setCancelable(true);
				NotificationManager.getInstance().getNotificationListItemObject(getActivity(),notificationJobId,
					 jobDetailsCallback,requestcode);
				isFromNotfication = true;
			}
		}
		
		if(EasyHomeFixApp.getRejectquoteStatus().equalsIgnoreCase(
				"true")){
			setVisibilityFromNotificationCancelled();
		}

		//setting visibility of cancelled view
		
		pay_now_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(jobList!=null)
				{
					jobId=jobList.getId();
					quoteamount=String.valueOf(jobList.getQuote().get(0).getQuoteAmount());
					quoteId=jobList.getQuote().get(0).getId();
					
				}
				else if(notificationListObject!=null)
				{
					jobId=notificationListObject.getId();
					quoteamount=String.valueOf(notificationListObject.getQuote().getQuoteAmount());
					quoteId=notificationListObject.getQuote().getId();
					
				}
				
				Intent i = new Intent(getActivity(),
						CustomerPaymentSelectionActivity.class);
				i.putExtra("jobId", jobId);
				i.putExtra("Ongoingquoteamount", quoteamount);
				i.putExtra("quoteId", quoteId);
				startActivity(i);
				//getActivity().finish();
			}
		});

		edit_details_button_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(jobList!=null)
				{
					jobId=jobList.getId();
					catId=jobList.getJobCategoryId().getId();
					jobTypeId=jobList.getJobTypeId().getId();
				}
				else if(notificationListObject!=null)
				{
					jobId=notificationListObject.getId();
					catId=notificationListObject.getJobCategoryId().getId();
					jobTypeId=notificationListObject.getJobTypeId().getId();
				}
				
				Intent i = new Intent(getActivity(),
						EditFixDetailsActivity.class);
				i.putExtra("jobId", jobId);
				i.putExtra("catId", catId);
				i.putExtra("jobTypeId", jobTypeId);
				Log.e("", "@@@@jobid"+jobId);
				Log.e("", "@@@@catId"+catId);
				Log.e("", "@@@@jobTypeId"+jobTypeId);
				startActivity(i);

			}
		});
		fix_completed_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(jobList!=null)
				{
					jobid=jobList.getId();
				}
				else if(notificationListObject.getId()!=null)
				{
					jobid=notificationListObject.getId();
				}
				

				pDialog = ProgressDialog.show(
						getActivity(), "", "Loading..",
						true);
				JobCreationManager.getInstance().completeFix(getActivity(),jobid, completeFixCallBack, requestcode);
			}
		});

		accept_quote_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(quotes != null)
				{
					if(quotes.getQuoteAmount()>0){
						acceptQuoteDialog(getActivity(), "Are you sure you want to accept the quote", "Accept Quote");
					}else
					{
						Utils.showDialog(getActivity(),"Unable to continue with zero quote.", "ERROR");
					}
				}else if(!quoteAmount.equalsIgnoreCase("0")){
					acceptQuoteDialog(getActivity(), "Are you sure you want to accept the quote", "Accept Quote");
				}
				else
				{
					Utils.showDialog(getActivity(),"Unable to continue with zero quote.", "ERROR");
				}
				

			}
		});

		reject_quote_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopup();

			}
		});
	
		viewAll_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if(jobList!=null)
				{
					if(quotes!=null)
					{
						fixerid=quotes.getFixerId().getUserId().getId();
						companyname=quotes.getFixerId().getCompanyName();
						rating=String.valueOf(quotes.getFixerId().getAvrgStarRating());
						fixescompleted=String.valueOf(quotes.getFixerId().getNumberOfFixesCompleted());
						MerchantImageUrl=quotes.getFixerId().getUserId().getProfileImage();
						firstname=quotes.getFixerId().getUserId().getFirstName();
						lastname=quotes.getFixerId().getUserId().getLastName();
						logintype=quotes.getFixerId().getUserId().getLoginType();
						
					}
					else
					{
						fixerid=jobList.getQuote().get(0).getFixerId().getUserId().getId();
						companyname=jobList.getQuote().get(0).getFixerId().getCompanyName();
						rating=String.valueOf(jobList.getQuote().get(0).getFixerId().getAvrgStarRating());
						fixescompleted=String.valueOf(jobList.getQuote().get(0).getFixerId().getNumberOfFixesCompleted());
						MerchantImageUrl=jobList.getQuote().get(0).getFixerId().getUserId().getProfileImage();
						firstname=jobList.getQuote().get(0).getFixerId().getUserId().getFirstName();
						lastname=jobList.getQuote().get(0).getFixerId().getUserId().getLastName();
						logintype=jobList.getQuote().get(0).getFixerId().getUserId().getLoginType();
					}
					
					
				}
				else if(notificationListObject!=null)
				{
					
					if(quotes!=null)
					{
						fixerid=quotes.getFixerId().getUserId().getId();
						companyname=quotes.getFixerId().getCompanyName();
						rating=String.valueOf(quotes.getFixerId().getAvrgStarRating());
						fixescompleted=String.valueOf(quotes.getFixerId().getNumberOfFixesCompleted());
						MerchantImageUrl=quotes.getFixerId().getUserId().getProfileImage();
						firstname=quotes.getFixerId().getUserId().getFirstName();
						lastname=quotes.getFixerId().getUserId().getLastName();
						logintype=quotes.getFixerId().getUserId().getLoginType();
						
					}else if(notificationListObject.getQuote() != null)
					{
						fixerid=notificationListObject.getQuote().getFixerId().getUserId().getId();
						companyname=notificationListObject.getQuote().getFixerId().getCompanyName();
						rating=String.valueOf(notificationListObject.getQuote().getFixerId().getAvrgStarRating());
						fixescompleted=String.valueOf(notificationListObject.getQuote().getFixerId().getNumberOfFixesCompleted());
						MerchantImageUrl=notificationListObject.getQuote().getFixerId().getUserId().getProfileImage();
						firstname=notificationListObject.getQuote().getFixerId().getUserId().getFirstName();
						lastname=notificationListObject.getQuote().getFixerId().getUserId().getLastName();
						logintype=notificationListObject.getQuote().getFixerId().getUserId().getLoginType();
					}else if(userHolder != null){
						fixerid=userHolder.getId();
						companyname=userHolder.getCompanyName();
						rating=String.valueOf(userHolder.getAvrgStarRating());
						fixescompleted=String.valueOf(userHolder.getNumberOfFixesCompleted());
						MerchantImageUrl=userHolder.getProfileImage();
						firstname=userHolder.getFirstName();
						lastname=userHolder.getLastName();
						logintype=userHolder.getLoginType();
					}
					
				}
				
					Intent i = new Intent(getActivity(), ReviewActivity.class);
					if(UtilValidate.isNotNull(fixerid))
					{i.putExtra("Fixerid", fixerid);
					}
					if(UtilValidate.isNotNull(companyname))
					{
						i.putExtra("companyname", companyname);
					}
					if(UtilValidate.isNotNull(rating))
					{
						i.putExtra("rating", rating);
					}
					if(UtilValidate.isNotNull(fixescompleted))
					{
						i.putExtra("fixescompleted",fixescompleted);
					}
					if(UtilValidate.isNotNull(MerchantImageUrl))
					{
						i.putExtra("ProfileImage",MerchantImageUrl);
					}
					if(UtilValidate.isNotNull(logintype))
					{
						i.putExtra("LoginType", logintype);
					}
					i.putExtra("firstname", firstname);
					i.putExtra("lastname", lastname);

						startActivity(i);

			}
		});

		cancel_fix_button_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pDialog = ProgressDialog.show(
						getActivity(), "", "Loading..",
						true);
				if(jobList!=null)
				{
					jobid=jobList.getId();
					
				}
				else if(notificationListObject!=null)
				{
					jobid=notificationListObject.getId();
					
				}
				
				JobCreationManager.getInstance().cancelFix(getActivity(), jobid, cancelFixCallBack, requestcode);
			}
		});
		
		payment_status_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(Constants.FROM_COMPLETED_CANCELLED))
				{
					Toast.makeText(getActivity(), "Job Cancelled.", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		review_fixer_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub\
				
				
				Intent in=new Intent(getActivity(),ReviewServiceActivity.class);
				if(jobList!=null)
				{
					
					jobidreview= jobList.getId();
				}
				if(notificationListObject!=null)
				{
					jobidreview= notificationListObject.getId();
					
				}
				in.putExtra("jobid", jobidreview);
				startActivity(in);
				
			}
		});
		
		
		return viewroot;
		}

		private void setVisibilityFromNotificationPending() {
		// TODO Auto-generated method stub
			payment_status_text.setText("QUOTE: ");
			fix_completed_layout.setVisibility(View.GONE);
			pay_now_layout.setVisibility(View.GONE);
			reject_accept_quote_layout.setVisibility(View.VISIBLE);
			review_fixer_layout.setVisibility(View.GONE);
			cancel_fix_button_layout.setVisibility(View.VISIBLE);
			
			if(notificationListObject != null){

				if(notificationListObject.getImages()!=null)
				{
					if(!notificationListObject.getImages().isEmpty())
					{
						CustomerImageList=notificationListObject.getImages();
						SetFixImages(CustomerImageList);
					}
					
					else
					{
						Log.e("","in else>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						horizondallistview.setVisibility(View.GONE);
						emptyimage.setVisibility(View.VISIBLE);
						
					}
				}
			
				if (UtilValidate.isNotNull(notificationListObject.getJobDetails())) {
					additionaldetails.setText(notificationListObject.getJobDetails());
				} else {
					additionaldetails.setText(" ");
				}
				/*if (UtilValidate.isNotNull(notificationListObject.getAddress().getPostal_code())) {
					fix_details_item_address_edittext.setText(notificationListObject.getAddress().getBlock_number() + " "
							+ notificationListObject.getAddress().getFloor() + " " + notificationListObject.getAddress().getUnit() + " "
							+ notificationListObject.getAddress().getRoad_building() + " "
							+ notificationListObject.getAddress().getPostal_code() + " ");

				}*/

				if (UtilValidate.isNotNull(notificationListObject.getAddress().getPostal_code())) {
					fix_details_item_address_edittext.setText(notificationListObject.getAddress().getBlock_number() + " "
							+ notificationListObject.getAddress().getFloor() + " " + notificationListObject.getAddress().getUnit() + " " + notificationListObject.getAddress().getRoad_building());
				}
				
				if (UtilValidate.isNotNull(notificationListObject.getJobDateTimeSlot())) {
					fix_details_item_date_edittext.setText(
							notificationListObject.getJobDateTimeSlot());
				}
				if(notificationListObject.getQuote().getFixerId().getUserId().getFirstName()!=null&&
						notificationListObject.getQuote().getFixerId().getUserId().getLastName()!=null)
						{
					textviewCompanyName.setText(notificationListObject.getQuote().getFixerId().getUserId().getFirstName()+" "+
						notificationListObject.getQuote().getFixerId().getUserId().getLastName());
					
						}
				else
				{
					textviewCompanyName.setText(notificationListObject.getQuote().getFixerId().getUserId().getFirstName());
					
				}
				
				if(notificationListObject.getQuote().getFixerId().getUserId().getNumberOfFixesCompleted()>0)
				{
					
					textviewTotalFix.setText(String.valueOf(notificationListObject.getQuote().getFixerId().getUserId().getNumberOfFixesCompleted()));
				}
				else
				{
					textviewTotalFix.setText("0");
					
				}
				
				if(notificationListObject.getJobReview()!=null)
				{
					categoryNameRatingbar.setRating(notificationListObject.getJobReview().getStar());
					
				}else{
					categoryNameRatingbar.setRating(0);
				}
				if(notificationListObject.getQuote().getQuoteAmount()>=0)
				{
					payment_status_text_value.setText("$"+notificationListObject.getQuote().getQuoteAmount());
					
				}
				
				
			}
			
		
	}

		private void setVisibilityFromNotificationCancelled() {
		// TODO Auto-generated method stub
			
			payment_status_text.setText("CANCELLED");
			fix_completed_layout.setVisibility(View.GONE);
			pay_now_layout.setVisibility(View.GONE);
			reject_accept_quote_layout.setVisibility(View.GONE);
			review_fixer_layout.setVisibility(View.GONE);
			cancel_fix_button_layout.setVisibility(View.VISIBLE);
			textviewfix_completed.setText("Fix status: Cancelled");
			textviewfix_completed.setVisibility(View.VISIBLE);
			payment_status_layout.setBackground(getResources().getDrawable(
					R.drawable.greycolor_with_roundcorner));
			payment_status_text_value.setVisibility(View.GONE);
			textviewTotalFix.setVisibility(View.GONE);
			detailtab_fixitem_layout.setVisibility(View.VISIBLE);
			
			if(notificationListObject != null){
				
				 if(notificationListObject.getJobStatus().equalsIgnoreCase("cancelled"))
				 {
					 viewAll_layout.setVisibility(View.INVISIBLE);
					 edit_details_button_layout.setVisibility(View.GONE);
					 cancel_fix_button_layout.setVisibility(View.GONE);
				 }
				
			if (UtilValidate.isNotNull(notificationListObject.getJobStatus())) {
				textviewfix_completed.setText("Fix status:"+" " + notificationListObject.getJobStatus());
				
			}
			

			if (UtilValidate.isNotNull(notificationListObject.getJobDetails())) {
				additionaldetails.setText(notificationListObject.getJobDetails());
			}
			else
			{
				additionaldetails.setText("");
				
			}
			if(notificationListObject.getQuote()!=null)
			{
				if ((notificationListObject.getQuote().getFixerId().getUserId().getFirstName()!=null)&&(notificationListObject.getQuote().getFixerId().getUserId().getLastName())!=null) {
					textviewCompanyName.setText(notificationListObject.getQuote().getFixerId().getUserId().getFirstName()+" "+notificationListObject.getQuote().getFixerId().getUserId().getLastName());
				} else {
					textviewCompanyName.setText((notificationListObject.getQuote().getFixerId().getUserId().getFirstName()));
				}

				if (UtilValidate.isNotNull(notificationListObject.getQuote().getFixerId().getAvrgStarRating())) {
					categoryNameRatingbar.setRating(Float.parseFloat(notificationListObject.getQuote().getFixerId().getAvrgStarRating()+""));
				}

				if ((notificationListObject.getQuote() != null)) {
					if (notificationListObject.getQuote().getQuoteAmount() != 0)
					payment_status_text_value.setText("$" + notificationListObject.getQuote().getQuoteAmount());
					Log.e("", "jobdetail" + notificationListObject.getJobDetails());
				}
				else
				{
					payment_status_text_value.setText("$0");

				}
				
			}
			

			

			if (UtilValidate.isNotNull(notificationListObject.getAddress().getPostal_code())) {
				fix_details_item_address_edittext.setText(notificationListObject.getAddress().getBlock_number() + " "
						+ notificationListObject.getAddress().getFloor() + " " + notificationListObject.getAddress().getUnit() + " " + notificationListObject.getAddress().getRoad_building() + " "
						);

			}
			if (UtilValidate.isNotNull(notificationListObject.getJobDate())) {
				fix_details_item_date_edittext.setText(notificationListObject.getJobDateTimeSlot());
			}
			
			}
			

			if(notificationListObject!=null)
			{	

				if(notificationListObject.getImages()!=null)
				{
					if(!notificationListObject.getImages().isEmpty())
					{
						CustomerImageList=notificationListObject.getImages();
						SetFixImages(CustomerImageList);
					}
					
					else
					{
						horizondallistview.setVisibility(View.GONE);
						emptyimage.setVisibility(View.VISIBLE);
						
					}
				}
			}
			
			
		
	}

		private void setVisibilityFromNotificationCompletePaid() {
		// TODO Auto-generated method stub
			
			payment_status_text.setText("PAID:");
			payment_status_text_value.setText(" $124");
			payment_status_text.setTextSize(22);
			payment_status_text_value.setTextSize(22);
			fix_completed_layout.setVisibility(View.GONE);
			pay_now_layout.setVisibility(View.GONE);
			reject_accept_quote_layout.setVisibility(View.GONE);
			review_fixer_layout.setVisibility(View.VISIBLE);
			cancel_fix_button_layout.setVisibility(View.GONE);
			detailtab_fixitem_layout.setVisibility(View.GONE);
			edit_details_button_layout.setVisibility(View.GONE);
			view_id.setVisibility(View.GONE);
			payment_layout
			.setBackgroundResource(R.drawable.dark_green_rounded_corner);
			
			
			if(UtilValidate.isNotNull(notificationListObject.getJobReview()))
			{
				review_fixer_layout.setVisibility(View.GONE);
			}
			
			if(notificationListObject.getQuote()!=null)
			{
				
			
			if(notificationListObject.getQuote().getFixerId().getUserId().getFirstName()!=null&&
					notificationListObject.getQuote().getFixerId().getUserId().getLastName()!=null)
			{
				textviewCompanyName.setText(notificationListObject.getQuote().getFixerId().getUserId().getFirstName()+" "+
						notificationListObject.getQuote().getFixerId().getUserId().getLastName());
				
			}
			else
			{
				textviewCompanyName.setText(notificationListObject.getQuote().getFixerId().getUserId().getFirstName());
				
			}
			if(notificationListObject.getQuote()!=null)
			{
				if(notificationListObject.getQuote().getFixerId().getAvrgStarRating()>0)
				{
					categoryNameRatingbar.setRating(notificationListObject.getQuote().getFixerId().getAvrgStarRating());
				}
				else
				{
					categoryNameRatingbar.setRating(0);
				}
			}
			
			
			if (UtilValidate.isNotNull(notificationListObject.getQuote())) {
				if(notificationListObject.getQuote().getFixerId() != null){
					textviewTotalFix.setText(notificationListObject.getQuote().getFixerId().getNumberOfFixesCompleted()+"");
				}
			}
			
			if(notificationListObject.getQuote().getQuoteAmount()>=0)
			{
				payment_status_text_value.setText("$"+notificationListObject.getQuote().getQuoteAmount());
				
			}
			
			if(notificationListObject.getQuote().getFixerId().getUserId().getProfileImage()!=null)
			{
				if(notificationListObject.getQuote().getFixerId().getUserId().getLoginType().equalsIgnoreCase("facebook"))
				{
					String profileurl=notificationListObject.getQuote().getFixerId().getUserId().getProfileImage();
					if(profileurl.contains("http"))
					{
						profileurl=profileurl.replace("http", "https");
					}
					MerchantImageUrl=profileurl;
				}
				else
				{
					MerchantImageUrl = notificationListObject.getQuote().getFixerId().getUserId().getProfileImage();
				}
				setCustomerImages();
			}
			
			if (UtilValidate.isNotNull(notificationListObject.getAddress().getPostal_code())) {
				fix_details_item_address_edittext.setText(notificationListObject.getAddress().getBlock_number() + " "
						+ notificationListObject.getAddress().getFloor() + " " + notificationListObject.getAddress().getUnit() + " " + notificationListObject.getAddress().getRoad_building() + " "
						);

			}
			if (UtilValidate.isNotNull(notificationListObject.getJobDate()+""+notificationListObject.getJobDateTimeSlot())) {
				fix_details_item_date_edittext.setText(notificationListObject.getJobDate()+""+notificationListObject.getJobDateTimeSlot());
			}
			

			if (UtilValidate.isNotNull(notificationListObject.getJobDetails())) {
				additionaldetails.setText(notificationListObject.getJobDetails());
			} else {
				additionaldetails.setText(" ");
			}
			

			if(notificationListObject.getImages()!=null)
			{
				if(!notificationListObject.getImages().isEmpty())
					{
						CustomerImageList=notificationListObject.getImages();
						Log.e("","imagelist<<<<<<<<<<<<<<<<<<<<<<<<,,"+CustomerImageList);
						SetFixImages(CustomerImageList);
							}
					else
					{
						Log.e("","in else>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						horizondallistview.setVisibility(View.GONE);
						emptyimage.setVisibility(View.VISIBLE);
					}
				}
			}
			
		
	}

		public void setVisibilityFromNotificationComplete() {
		// TODO Auto-generated method stub
			
			payment_status_text.setText("PENDING PAYMENT:");
			payment_status_text_value.setText(quoteamount);
			payment_status_text.setTextSize(22);
			payment_status_text_value.setTextSize(22);
			payment_layout
					.setBackgroundResource(R.drawable.yellowcolor_with_round_corner);
			fix_completed_layout.setVisibility(View.GONE);
			pay_now_layout.setVisibility(View.VISIBLE);
			reject_accept_quote_layout.setVisibility(View.GONE);
			review_fixer_layout.setVisibility(View.VISIBLE);
			cancel_fix_button_layout.setVisibility(View.GONE);
			detailtab_fixitem_layout.setVisibility(View.GONE);
			edit_details_button_layout.setVisibility(View.GONE);
			view_id.setVisibility(View.GONE);
			review_fixer_layout.setVisibility(View.GONE);
			
			
			
			if(notificationListObject.getQuote().getFixerId().getUserId().getProfileImage()!=null)
			{
				if(notificationListObject.getQuote()!=null)
				{
					if(notificationListObject.getQuote().getFixerId().getUserId().getLoginType().equalsIgnoreCase("facebook"))
					{
						String profileurl=notificationListObject.getQuote().getFixerId().getUserId().getProfileImage();
						if(profileurl.contains("http"))
						{
							profileurl=profileurl.replace("http", "https");
						}
						MerchantImageUrl=profileurl;
					}
					else
					{
						MerchantImageUrl = notificationListObject.getQuote().getFixerId().getUserId().getProfileImage();
					}
					
					setCustomerImages();
				}
				
			}
			
			if (UtilValidate.isNotNull(notificationListObject.getAddress().getPostal_code())) {
				fix_details_item_address_edittext.setText(notificationListObject.getAddress().getBlock_number() + " "
						+ notificationListObject.getAddress().getFloor() + " " + notificationListObject.getAddress().getUnit() + " " + notificationListObject.getAddress().getRoad_building() + " "
						);

			}
			if (UtilValidate.isNotNull(notificationListObject.getJobDateTimeSlot())) {
				fix_details_item_date_edittext.setText(notificationListObject.getJobDateTimeSlot());
			}
			

			if (UtilValidate.isNotNull(notificationListObject.getJobDetails())) {
				additionaldetails.setText(notificationListObject.getJobDetails());
			} else {
				additionaldetails.setText(" ");
			}

			if ((notificationListObject.getQuote().getFixerId().getUserId().getFirstName()!=null)&&(notificationListObject.getQuote().getFixerId().getUserId().getLastName())!=null) {
				textviewCompanyName.setText(notificationListObject.getQuote().getFixerId().getUserId().getFirstName()+" "+notificationListObject.getQuote().getFixerId().getUserId().getLastName());
			} else {
				textviewCompanyName.setText(" ");
			}

			if (UtilValidate.isNotNull(notificationListObject.getQuote())) {
				if(notificationListObject.getQuote().getFixerId() != null){
					categoryNameRatingbar.setRating(Float.parseFloat(notificationListObject.getQuote().getFixerId().getAvrgStarRating()+""));
				}
			}

			if (UtilValidate.isNotNull(notificationListObject.getQuote())) {
				if(notificationListObject.getQuote().getFixerId() != null){
					textviewTotalFix.setText(notificationListObject.getQuote().getFixerId().getNumberOfFixesCompleted()+"");
				}
			}
			if ((notificationListObject.getQuote() != null)) {
				if (notificationListObject.getQuote().getQuoteAmount() != 0)
				payment_status_text_value.setText("$" + notificationListObject.getQuote().getQuoteAmount());
			}

			else {
				payment_status_text_value.setText("$0");

			}
			
			if(notificationListObject.getImages()!=null)
			{
				if(!notificationListObject.getImages().isEmpty())
					{
						CustomerImageList=notificationListObject.getImages();
						Log.e("","imagelist<<<<<<<<<<<<<<<<<<<<<<<<,,"+CustomerImageList);
						SetFixImages(CustomerImageList);
							}
					else
					{
						Log.e("","in else>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						horizondallistview.setVisibility(View.GONE);
						emptyimage.setVisibility(View.VISIBLE);
					}
				}
			
		
	}

		public void setVisibilityFromNotificationOngoing() {
		// TODO Auto-generated method stub
			
			payment_status_text.setText("ACCEPTED QUOTE: ");
			detailtab_fixitem_layout.setVisibility(View.GONE);
			fix_completed_layout.setVisibility(View.VISIBLE);
			pay_now_layout.setVisibility(View.GONE);
			reject_accept_quote_layout.setVisibility(View.GONE);
			review_fixer_layout.setVisibility(View.GONE);
			cancel_fix_button_layout.setVisibility(View.VISIBLE);
			fix_completed.setText(" Mark Fix as Completed");
			
			textviewTotalFix.setVisibility(View.GONE);
			
			if(notificationListObject!=null)
			{
				if(notificationListObject.getImages()!=null)
				{
					if(!notificationListObject.getImages().isEmpty())
							{
						CustomerImageList=notificationListObject.getImages();
						Log.e("","imagelist<<<<<<<<<<<<<<<<<<<<<<<<,,"+CustomerImageList);
						SetFixImages(CustomerImageList);
							}
					else
					{
						horizondallistview.setVisibility(View.GONE);
						emptyimage.setVisibility(View.VISIBLE);
					}
				}
				
			
			

			if (UtilValidate.isNotNull(notificationListObject.getJobStatus())) {
				textviewfix_completed.setText("Fix status:"+ " "+ notificationListObject.getJobStatus());
			}
			

			if (UtilValidate.isNotNull(notificationListObject.getJobDetails())) {
				additionaldetails.setText(notificationListObject.getJobDetails());
			}
			else
			{
				additionaldetails.setText("");
				
			}
			
			if (UtilValidate.isNotNull(notificationListObject.getQuote())) {
				if(notificationListObject.getQuote().getFixerId() != null){
					if(notificationListObject.getQuote().getFixerId().getUserId() != null){
						textviewCompanyName.setText(notificationListObject.getQuote().getFixerId().getUserId().getFirstName()+" "+
					notificationListObject.getQuote().getFixerId().getUserId().getLastName());
					}
				}
			} else if(quotes != null){
				if(quotes.getFixerId() != null){
					if(quotes.getFixerId().getUserId() != null){
						textviewCompanyName.setText(quotes.getFixerId().getUserId().getFirstName()+" "+
								quotes.getFixerId().getUserId().getLastName());
						categoryNameRatingbar.setRating(Float.parseFloat(quotes.getFixerId().getAvrgStarRating()+""));
						textviewTotalFix.setText(quotes.getFixerId().getNumberOfFixesCompleted()+"");
						payment_status_text_value.setText("$" + quotes.getQuoteAmount());
						if(quotes.getFixerId().getUserId().getProfileImage()!=null)
						{
							if(quotes.getFixerId().getUserId().getLoginType().equalsIgnoreCase("facebook"))
							{
								String profileurl=quotes.getFixerId().getUserId().getProfileImage();
								if(profileurl.contains("http"))
								{
									profileurl=profileurl.replace("http", "https");
								}
								MerchantImageUrl=profileurl;
							}
							else
							{
								MerchantImageUrl = quotes.getFixerId().getUserId().getProfileImage();
							}
							
							setCustomerImages();
						}
					}
				}
			}
			
			if(notificationListObject.getQuote()!=null)
			{
				if(notificationListObject.getQuote().getQuoteAmount()>0)
				{
					payment_status_text_value.setText("$"+notificationListObject.getQuote().getQuoteAmount());
				}
				else
				{
					payment_status_text_value.setText("0");
				}
				
			}

			
			if(notificationListObject.getQuote()!=null)
			{
				if(notificationListObject.getQuote().getFixerId().getAvrgStarRating()>0)
				{
					categoryNameRatingbar.setRating(notificationListObject.getQuote().getFixerId().getAvrgStarRating());
				}
				else
				{
					categoryNameRatingbar.setRating(0);
				}
			}

			

			if (UtilValidate.isNotNull(notificationListObject.getAddress().getPostal_code())) {
				fix_details_item_address_edittext.setText(notificationListObject.getAddress().getBlock_number() + " "
						+ notificationListObject.getAddress().getFloor() + " " + notificationListObject.getAddress().getUnit() + " " + notificationListObject.getAddress().getRoad_building());
			}
			if (UtilValidate.isNotNull(notificationListObject.getJobDate()+""+notificationListObject.getJobDateTimeSlot())) {
				fix_details_item_date_edittext.setText(notificationListObject.getJobDateTimeSlot());
			}
			
			/*if(notificationListObject.getQuote().getFixerId().getUserId().getProfileImage()!=null)
			{
				MerchantImageUrl = notificationListObject.getQuote().getFixerId().getUserId().getProfileImage();
				setCustomerImages();
			}*/
			
		}
			
			
	}

		public void setVisibilityFromNotificationChat() {
		// TODO Auto-generated method stub
		
	}

		private void setVisibilityCancel() {
		// TODO Auto-generated method stub
			
			payment_status_text.setText("CANCELLED");
			fix_completed_layout.setVisibility(View.GONE);
			pay_now_layout.setVisibility(View.GONE);
			reject_accept_quote_layout.setVisibility(View.GONE);
			review_fixer_layout.setVisibility(View.GONE);
			cancel_fix_button_layout.setVisibility(View.VISIBLE);
			textviewfix_completed.setText("Fix status: Cancelled");
			textviewfix_completed.setVisibility(View.VISIBLE);
			payment_status_layout.setBackground(getResources().getDrawable(
					R.drawable.greycolor_with_roundcorner));
			payment_status_text_value.setVisibility(View.GONE);
			textviewTotalFix.setVisibility(View.GONE);
			detailtab_fixitem_layout.setVisibility(View.VISIBLE);
			
			if(jobList != null){
				
				if(jobList.getJobStatus().equalsIgnoreCase("cancelled"))
				{
						 viewAll_layout.setVisibility(View.INVISIBLE);
						 edit_details_button_layout.setVisibility(View.GONE);
						 cancel_fix_button_layout.setVisibility(View.GONE);
				}
					
				if (UtilValidate.isNotNull(jobList.getJobStatus())) {
					textviewfix_completed.setText("Fix status:" + jobList.getJobStatus());
					
				}
				
	
				if (UtilValidate.isNotNull(jobList.getJobDetails())) {
					additionaldetails.setText(jobList.getJobDetails());
				}
				else
				{
					additionaldetails.setText("");
					
				}
				
				if ((jobList.getUserId().getFirstName()!=null)&&(jobList.getUserId().getLastName())!=null) {
					textviewCompanyName.setText(jobList.getUserId().getFirstName()+" "+jobList.getUserId().getLastName());
				} else {
					textviewCompanyName.setText((jobList.getUserId().getFirstName()));
				}
	
				if (UtilValidate.isNotNull(jobList.getUserId().getAvrgStarRating())) {
					categoryNameRatingbar.setRating(Float.parseFloat(jobList.getUserId().getAvrgStarRating()+""));
				}
	
				if ((jobList.getQuote() != null)&&(!jobList.getQuote().isEmpty())) {
					if (jobList.getQuote().get(0).getQuoteAmount() != 0)
					payment_status_text_value.setText("$" + jobList.getQuote().get(0).getQuoteAmount());
					Log.e("", "jobdetail" + jobList.getJobDetails());
				}
				else
				{
					payment_status_text_value.setText("$0");
	
				}
	
				
	
				if (UtilValidate.isNotNull(jobList.getAddress().getPostal_code())) {
					fix_details_item_address_edittext.setText(jobList.getAddress().getBlock_number() + " "
							+ jobList.getAddress().getFloor() + " " + jobList.getAddress().getUnit() + " " + jobList.getAddress().getRoad_building() + " "
							);
	
				}
				if (UtilValidate.isNotNull(jobList.getJobDate())) {
					fix_details_item_date_edittext.setText(jobList.getJobDateTimeSlot());
				}
				
				if(jobList.getImages()!=null)
				{
					if(!jobList.getImages().isEmpty())
					{
						CustomerImageList=jobList.getImages();
						SetFixImages(CustomerImageList);
					}
					
					else
					{
						horizondallistview.setVisibility(View.GONE);
						emptyimage.setVisibility(View.VISIBLE);
						
					}
				}
				
				if(jobList.getJobCategoryId().getName()!=null)
				{
					categoryname.setText(jobList.getJobCategoryId().getName());
	
				if(jobList.getJobCategoryId().getName().equalsIgnoreCase("Air Conditioning"))
				{
					Picasso.with(getActivity()).load(R.drawable.ac_icon)
					.into(categoryimage);
				}
				else if(jobList.getJobCategoryId().getName().equalsIgnoreCase("Electrician"))
				{
					Picasso.with(getActivity()).load(R.drawable.wiring_icon)
					.into(categoryimage);
				}
				else if(jobList.getJobCategoryId().getName().equalsIgnoreCase("Plumbing"))
				{
					Picasso.with(getActivity()).load(R.drawable.plumbing_icon)
					.into(categoryimage);
				}
				else if(jobList.getJobCategoryId().getName().equalsIgnoreCase("Painting"))
				{
					Picasso.with(getActivity()).load(R.drawable.painting_icon)
					.into(categoryimage);
				}
				else if(jobList.getJobCategoryId().getName().equalsIgnoreCase("Carpenter"))
				{
					Picasso.with(getActivity()).load(R.drawable.carpenter_icon)
					.into(categoryimage);
				}
				else if(jobList.getJobCategoryId().getName().equalsIgnoreCase("LockSmith"))
				{
					Picasso.with(getActivity()).load(R.drawable.locksmith_icon)
					.into(categoryimage);
				}
				else if(jobList.getJobCategoryId().getName().equalsIgnoreCase("Pest Control"))
				{
					Picasso.with(getActivity()).load(R.drawable.pest_control_icon)
					.into(categoryimage);
				}
				else if(jobList.getJobCategoryId().getName().equalsIgnoreCase("Sanitization"))
				{
					Picasso.with(getActivity()).load(R.drawable.sanitization_icon)
					.into(categoryimage);
				}
				}
			}else if(notificationListObject != null){
				

				if(notificationListObject.getImages()!=null)
				{
					if(!notificationListObject.getImages().isEmpty())
					{
						CustomerImageList=notificationListObject.getImages();
						SetFixImages(CustomerImageList);
					}
					
					else
					{
						Log.e("","in else>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						horizondallistview.setVisibility(View.GONE);
						emptyimage.setVisibility(View.VISIBLE);
						
					}
				}
			
				if (UtilValidate.isNotNull(notificationListObject.getJobDetails())) {
					additionaldetails.setText(notificationListObject.getJobDetails());
				} else {
					additionaldetails.setText(" ");
				}
				if (UtilValidate.isNotNull(notificationListObject.getAddress().getPostal_code())) {
					fix_details_item_address_edittext.setText(notificationListObject.getAddress().getBlock_number() + " "
							+ notificationListObject.getAddress().getFloor() + " " + notificationListObject.getAddress().getUnit() + " "
							+ notificationListObject.getAddress().getRoad_building() + " "
							);

				}
				
				if (UtilValidate.isNotNull(notificationListObject.getJobDateTimeSlot())) {
					fix_details_item_date_edittext.setText(
							notificationListObject.getJobDateTimeSlot());
				}
			}
				if(quotes != null){
					if ((quotes.getFixerId().getUserId().getFirstName()!=null)&&
							(quotes.getFixerId().getUserId().getLastName()!=null)) {
						textviewCompanyName.setText(quotes.getFixerId().getUserId().getFirstName()+" "+
								quotes.getFixerId().getUserId().getLastName());
					} else {
						textviewCompanyName.setText(quotes.getFixerId().getUserId().getFirstName());
					}
		
					if (quotes.getFixerId().getUserId().getAvrgStarRating()!=null) {
						categoryNameRatingbar.setRating(Float.parseFloat(quotes.getFixerId().getUserId().getAvrgStarRating()+""));
					}
					else
					{
						categoryNameRatingbar.setRating(0);
					}
		
					if (UtilValidate.isNotNull(quotes.getFixerId().getUserId().getNumberOfFixesCompleted())) {
						textviewTotalFix.setText(quotes.getFixerId().getUserId().getNumberOfFixesCompleted()+"");
					}else
					{
						textviewTotalFix.setText("0");
					}
					if (quotes.getQuoteAmount() != 0){
						payment_status_text_value.setText("$" + quotes.getQuoteAmount());
					}else {
						payment_status_text_value.setText("$0");
		
					}
					
					if(quotes.getFixerId().getUserId().getProfileImage()!=null)
					{
						if(quotes.getFixerId().getUserId().getLoginType().equalsIgnoreCase("facebook"))
						{
							String profileurl=quotes.getFixerId().getUserId().getProfileImage();
							if(profileurl.contains("http"))
							{
								profileurl=profileurl.replace("http", "https");
							}
							MerchantImageUrl=profileurl;
						}
						else
						{
							MerchantImageUrl=quotes.getFixerId().getUserId().getProfileImage();
						}
						
						
						setCustomerImages();
					}
				}else if(userHolder != null){

					if ((userHolder.getFirstName()!=null&&
							userHolder.getLastName()!=null)) {
						textviewCompanyName.setText(userHolder.getFirstName()+" "+
								userHolder.getLastName());
					} else {
						textviewCompanyName.setText(userHolder.getFirstName());
					}
		
					if (userHolder.getAvrgStarRating()!=null) {
						categoryNameRatingbar.setRating(Float.parseFloat(userHolder.getAvrgStarRating()+""));
					}
					else
					{
						categoryNameRatingbar.setRating(0);
					}
		
					if (UtilValidate.isNotNull(userHolder.getNumberOfFixesCompleted())) {
						textviewTotalFix.setText(userHolder.getNumberOfFixesCompleted()+"");
					}else
					{
						textviewTotalFix.setText("0");
					}
					if (quoteAmount != null){
						payment_status_text_value.setText("$" + quoteAmount);
					}else {
						payment_status_text_value.setText("$0");
		
					}
					
					if(userHolder.getProfileImage()!=null)
					{
						if(userHolder.getLoginType().equalsIgnoreCase("facebook"))
						{
							String profileurl=userHolder.getProfileImage();
							if(profileurl.contains("http"))
							{
								profileurl=profileurl.replace("http", "https");
							}
							MerchantImageUrl=profileurl;
						}
						else
						{
							MerchantImageUrl=userHolder.getProfileImage();
						}
						
						setCustomerImages();
					}
				
				}
			
	}

		private void setVisibilityFromNotificationPaymentDetail() {
		// TODO Auto-generated method stub
			
			payment_status_text.setText("");
			detailtab_fixitem_layout.setVisibility(View.GONE);
			fix_completed_layout.setVisibility(View.VISIBLE);
			pay_now_layout.setVisibility(View.GONE);
			reject_accept_quote_layout.setVisibility(View.GONE);
			review_fixer_layout.setVisibility(View.GONE);
			cancel_fix_button_layout.setVisibility(View.VISIBLE);
			fix_completed.setText("Mark Fix as Completed");
			if(notificationListObject.getQuote().getFixerId().getUserId().getProfileImage()!=null)
			{
				if(notificationListObject.getQuote().getFixerId().getUserId().getLoginType().equalsIgnoreCase("facebook"))
				{
					String profileurl=notificationListObject.getQuote().getFixerId().getUserId().getProfileImage();
					if(profileurl.contains("http"))
					{
						profileurl=profileurl.replace("http", "https");
					}
					MerchantImageUrl=profileurl;
				}
				else
				{
					MerchantImageUrl=jobList.getQuote().get(0).getFixerId().getUserId().getProfileImage();
				}
				
				setCustomerImages();
			}
			
		
	}

		private void initManager() {
		// TODO Auto-generated method stub
		rejectQuotecallBack = new RejectQuotecallBack();
		acceptquotesCallback = new AcceptQuoteCallBack();
		completeFixCallBack = new CompleteFixCallBack();
		cancelFixCallBack = new CancelFixCallBack();
		jobDetailsCallback = new JobDetailsCallback();
		}

		private void getIntentValues() {
		// TODO Auto-generated method stub
		
				 if (getActivity().getIntent().hasExtra("ONGOINGOBJECT")) {
					 jobList = (JobList) getActivity().getIntent()
								.getSerializableExtra("ONGOINGOBJECT");
					 Log.d("", "OBJECT+++++++++++++++++++++"+jobList.getJobStatus());
					}
				 if (getActivity().getIntent().hasExtra("job")) {
					 jobList = (JobList) getActivity().getIntent()
								.getSerializableExtra("job");
					
					 Log.d("", "OBJECTC+++++++++++++++++++++"+jobList.getJobStatus());
					}
				 if (getActivity().getIntent().hasExtra("PENDINGOBJECT")) {
					 jobList = (JobList) getActivity().getIntent()
								.getSerializableExtra("PENDINGOBJECT");
					 //Log.d("", "OBJECT+++++++++++++++++++++"+jobList.getJobStatus());
					}
				/* if (getActivity().getIntent().hasExtra("NotificationObject")) {
					 notificationListObject = (JobNotifications) getActivity().getIntent()
								.getSerializableExtra("NotificationObject");
					 Log.d("", "OBJECT+++++++++++++++++++++"+jobList.getJobStatus());
					}*/
				//NOTIFICATION OBJECT FROM CHAT
				
				 if (getActivity().getIntent().hasExtra("notification_chat")) {
					 notificationListObject = (Job) getActivity().getIntent()
								.getSerializableExtra("notification_chat");
					}
				
				//NOTIFICATION OBJECT FROM  ONGOING
				 
				 if (getActivity().getIntent().hasExtra("notification_ongoing")) {
					 notificationListObject = (Job) getActivity().getIntent()
								.getSerializableExtra("notification_ongoing");
					}
				 
				 //NOTIFICATION OBJECT FROM COMPLETE
				 
				 
				 if (getActivity().getIntent().hasExtra("notification_completed")) {
					 notificationListObject = (Job) getActivity().getIntent()
								.getSerializableExtra("notification_completed");
					}
				 
				 //NOTIFICATION OBJECT FROM PENDING
				 
				 if (getActivity().getIntent().hasExtra("From_notification_pending")) {
					 notificationListObject = (Job) getActivity().getIntent()
								.getSerializableExtra("From_notification_pending");
					}
				 
				 if (getActivity().getIntent().hasExtra("QuoteObject")){
					 quotes = (Quotes) getActivity().getIntent()
								.getSerializableExtra("QuoteObject");
				 }
				 
				 if(getActivity().getIntent().hasExtra("From_notification_ongoing"))
				 {
					 notificationListObject = (Job) getActivity().getIntent()
								.getSerializableExtra("From_notification_ongoing");
				 }
				 if(getActivity().getIntent().hasExtra("notification_jobid")){
						notificationJobId = (String) getActivity().getIntent().getStringExtra("notification_jobid");
					}
				 		
	}

	

	private void setVisibilityofPaymentPaid() {
		// TODO Auto-generated method stub
		Log.e("","paid");
		payment_status_text.setText("PAID:");
		payment_status_text_value.setText(" $124");
		payment_status_text.setTextSize(22);
		payment_status_text_value.setTextSize(22);
		fix_completed_layout.setVisibility(View.GONE);
		pay_now_layout.setVisibility(View.GONE);
		reject_accept_quote_layout.setVisibility(View.GONE);
		review_fixer_layout.setVisibility(View.VISIBLE);
		cancel_fix_button_layout.setVisibility(View.GONE);
		detailtab_fixitem_layout.setVisibility(View.GONE);
		edit_details_button_layout.setVisibility(View.GONE);
		view_id.setVisibility(View.GONE);
		payment_layout
		.setBackgroundResource(R.drawable.dark_green_rounded_corner);
		
		
		if(UtilValidate.isNotNull(jobList.getJobReview()))
		{
			review_fixer_layout.setVisibility(View.GONE);
		}
		
		if (UtilValidate.isNotNull(jobList.getAddress().getPostal_code())) {
			fix_details_item_address_edittext.setText(jobList.getAddress().getBlock_number() + " "
					+ jobList.getAddress().getFloor() + " " + jobList.getAddress().getUnit() + " " + jobList.getAddress().getRoad_building() + " "
					);

		}
		if (UtilValidate.isNotNull(jobList.getJobDateTimeSlot())) {
			fix_details_item_date_edittext.setText(jobList.getJobDateTimeSlot());
		}
		

		if (UtilValidate.isNotNull(jobList.getJobDetails())) {
			additionaldetails.setText(jobList.getJobDetails());
		} else {
			additionaldetails.setText(" ");
		}

		if ((jobList.getQuote().get(0).getFixerId().getUserId().getFirstName()!=null&&jobList.getQuote().get(0).getFixerId().getUserId().getLastName()!=null)) {
			textviewCompanyName.setText(jobList.getQuote().get(0).getFixerId().getUserId().getFirstName()+" "+jobList.getQuote().get(0).getFixerId().getUserId().getLastName());
		} else {
			textviewCompanyName.setText(jobList.getQuote().get(0).getFixerId().getUserId().getFirstName()+"");
		} 
if(jobList.getJobReview()!=null){
		if (UtilValidate.isNotNull(jobList.getJobReview().getStar())) {
			
			categoryNameRatingbar.setRating(Float.parseFloat(jobList.getJobReview().getStar()+""));
		}
}
		if (UtilValidate.isNotNull(jobList.getQuote().get(0).getFixerId().getUserId().getNumberOfFixesCompleted())) {
			textviewTotalFix.setText(jobList.getQuote().get(0).getFixerId().getUserId().getNumberOfFixesCompleted()+"");
		}
		if ((jobList.getQuote() != null)&&(!jobList.getQuote().isEmpty())) {
			if (jobList.getQuote().get(0).getQuoteAmount() != 0)
			payment_status_text_value.setText("$" + jobList.getQuote().get(0).getQuoteAmount());
		}

		else {
			payment_status_text_value.setText("$0");

		}
		
		

		
		if(jobList.getQuote().get(0).getFixerId().getUserId().getProfileImage()!=null)
		{
			if(jobList.getQuote().get(0).getFixerId().getUserId().getLoginType().equalsIgnoreCase("facebook"))
			{
				String profileurl=jobList.getQuote().get(0).getFixerId().getUserId().getProfileImage();
				if(profileurl.contains("http"))
				{
					profileurl=profileurl.replace("http", "https");
				}
				MerchantImageUrl=profileurl;
			}
			else
			{
				MerchantImageUrl=jobList.getQuote().get(0).getFixerId().getUserId().getProfileImage();
			}
			
			
			setCustomerImages();
		}
		
			
		if(jobList!=null)
		{	

			if(jobList.getImages()!=null)
			{
				if(!jobList.getImages().isEmpty())
				{
					CustomerImageList=jobList.getImages();
					SetFixImages(CustomerImageList);
				}
				
				else
				{
					Log.e("","in else>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					horizondallistview.setVisibility(View.GONE);
					emptyimage.setVisibility(View.VISIBLE);
					
				}
			}
		}
		
		
		

	}

	private void setVisibilityofPayment() {
		// TODO Auto-generated method stub
		payment_status_text.setText("PENDING PAYMENT:");
		payment_status_text_value.setText(quoteamount);
		payment_status_text.setTextSize(22);
		payment_status_text_value.setTextSize(22);
		payment_layout
				.setBackgroundResource(R.drawable.yellowcolor_with_round_corner);
		fix_completed_layout.setVisibility(View.GONE);
		pay_now_layout.setVisibility(View.VISIBLE);
		reject_accept_quote_layout.setVisibility(View.GONE);
		review_fixer_layout.setVisibility(View.VISIBLE);
		cancel_fix_button_layout.setVisibility(View.GONE);
		detailtab_fixitem_layout.setVisibility(View.GONE);
		edit_details_button_layout.setVisibility(View.GONE);
		view_id.setVisibility(View.GONE);
		review_fixer_layout.setVisibility(View.GONE);
		
		if(jobList!=null)
		{
			if(jobList.getQuote()!=null&&jobList.getQuote().size()>0)
			{
			if(jobList.getQuote().get(0).getFixerId().getUserId().getProfileImage()!=null)
			{
				if(jobList.getQuote().get(0).getFixerId().getUserId().getLoginType().equalsIgnoreCase("facebook"))
				 {
					String profileurl=jobList.getQuote().get(0).getFixerId().getUserId().getProfileImage();
					if(profileurl.contains("http"))
					{
						profileurl=profileurl.replace("http", "https");
					}
					MerchantImageUrl=profileurl;
					}
				else
				{
					MerchantImageUrl=jobList.getQuote().get(0).getFixerId().getUserId().getProfileImage();
				}
				
				setCustomerImages();
			}
			if(jobList!=null)
			{
				
		
			if (UtilValidate.isNotNull(jobList.getAddress().getPostal_code())) {
				fix_details_item_address_edittext.setText(jobList.getAddress().getBlock_number() + " "
						+ jobList.getAddress().getFloor() + " " + jobList.getAddress().getUnit() + " " + jobList.getAddress().getRoad_building() + " "
						);

			}
			if (UtilValidate.isNotNull(jobList.getJobDateTimeSlot())) {
				fix_details_item_date_edittext.setText(jobList.getJobDateTimeSlot());
			}
			

			if (UtilValidate.isNotNull(jobList.getJobDetails())) {
				additionaldetails.setText(jobList.getJobDetails());
			} else {
				additionaldetails.setText(" ");
			}

			if (jobList.getQuote().get(0).getFixerId().getUserId().getFirstName()!=null&&jobList.getQuote().get(0).getFixerId().getUserId().getLastName()!=null) {
				textviewCompanyName.setText(jobList.getQuote().get(0).getFixerId().getUserId().getFirstName()+" "+jobList.getQuote().get(0).getFixerId().getUserId().getLastName());
			} else {
				textviewCompanyName.setText(jobList.getQuote().get(0).getFixerId().getUserId().getFirstName());
			}

			if (UtilValidate.isNotNull(jobList.getQuote().get(0).getFixerId().getUserId().getAvrgStarRating())) {
				categoryNameRatingbar.setRating(Float.parseFloat(jobList.getQuote().get(0).getFixerId().getUserId().getAvrgStarRating()+""));
			}

			if (UtilValidate.isNotNull(jobList.getQuote().get(0).getFixerId().getUserId().getNumberOfFixesCompleted())) {
				textviewTotalFix.setText(jobList.getQuote().get(0).getFixerId().getUserId().getNumberOfFixesCompleted()+"");
			}
			if ((jobList.getQuote() != null)&&(!jobList.getQuote().isEmpty())) {
				if (jobList.getQuote().get(0).getQuoteAmount() != 0)
				payment_status_text_value.setText("$" + jobList.getQuote().get(0).getQuoteAmount());
			}

			else {
				payment_status_text_value.setText("$0");

			}
			}
			
			
			if(jobList!=null)
			{	

				if(jobList.getImages()!=null)
				{
					if(!jobList.getImages().isEmpty())
					{
						CustomerImageList=jobList.getImages();
						SetFixImages(CustomerImageList);
					}
					
					else
					{
						Log.e("","in else>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						horizondallistview.setVisibility(View.GONE);
						emptyimage.setVisibility(View.VISIBLE);
						
					}
				}
			}
			
			
			
		}
		}

	}

	private void setVisibilityofOngoing() {
		// TODO Auto-generated method stub
		payment_status_text.setText("ACCEPTED QUOTE: ");
		detailtab_fixitem_layout.setVisibility(View.GONE);
		fix_completed_layout.setVisibility(View.VISIBLE);
		pay_now_layout.setVisibility(View.GONE);
		reject_accept_quote_layout.setVisibility(View.GONE);
		review_fixer_layout.setVisibility(View.GONE);
		cancel_fix_button_layout.setVisibility(View.VISIBLE);
		fix_completed.setText(" Mark Fix as Completed");
		
		
		
		if(jobList!=null){
			if(jobList.getImages()!=null){
				if(!jobList.getImages().isEmpty())
				{
					CustomerImageList=jobList.getImages();
					SetFixImages(CustomerImageList);
				}else{
					Log.e("","in else>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					horizondallistview.setVisibility(View.GONE);
					emptyimage.setVisibility(View.VISIBLE);
				}
			}
			if (UtilValidate.isNotNull(jobList.getJobDetails())) {
				additionaldetails.setText(jobList.getJobDetails());
			}else{
				additionaldetails.setText("");
			}
			if (UtilValidate.isNotNull(jobList.getAddress().getPostal_code())) {
				fix_details_item_address_edittext.setText(jobList.getAddress().getBlock_number() + " "
						+ jobList.getAddress().getFloor() + " " + jobList.getAddress().getUnit() + " " + jobList.getAddress().getRoad_building() + " "
						);
	
			}
			if (UtilValidate.isNotNull(jobList.getJobDateTimeSlot())) {
				fix_details_item_date_edittext.setText(
						jobList.getJobDateTimeSlot());
			}
		}else if(notificationListObject != null){

			if(notificationListObject.getImages()!=null){
				if(!notificationListObject.getImages().isEmpty())
				{
					CustomerImageList=notificationListObject.getImages();
					SetFixImages(CustomerImageList);
				}else{
					Log.e("","in else>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					horizondallistview.setVisibility(View.GONE);
					emptyimage.setVisibility(View.VISIBLE);
				}
			}
			if (UtilValidate.isNotNull(notificationListObject.getJobDetails())) {
				additionaldetails.setText(notificationListObject.getJobDetails());
			}else{
				additionaldetails.setText("");
			}
			if (UtilValidate.isNotNull(notificationListObject.getAddress().getPostal_code())) {
				fix_details_item_address_edittext.setText(notificationListObject.getAddress().getBlock_number() + " "
						+ notificationListObject.getAddress().getFloor() + " " 
						+ notificationListObject.getAddress().getUnit() + " " + notificationListObject.getAddress().getRoad_building() + " "
						);
	
			}
			if (UtilValidate.isNotNull(notificationListObject.getJobDateTimeSlot())) {
				fix_details_item_date_edittext.setText(
						notificationListObject.getJobDateTimeSlot());
			}
		
		}
		if(jobList != null){	
			if(jobList.getQuote()!=null&&jobList.getQuote().size()>0)
			{
			if(jobList.getQuote().get(0).getFixerId().equals(quotes)){
					if ((jobList.getQuote().get(0).getFixerId().getUserId().getFirstName())!=null&&(jobList.getQuote().get(0).getFixerId().getUserId().getLastName())!=null) {
						textviewCompanyName.setText(jobList.getQuote().get(0).getFixerId().getUserId().getFirstName()+" "+jobList.getQuote().get(0).getFixerId().getUserId().getLastName());
					} else {
						textviewCompanyName.setText(jobList.getUserId().getFirstName());
					}
			
					if (UtilValidate.isNotNull(jobList.getQuote().get(0).getFixerId().getUserId().getAvrgStarRating())) {
						categoryNameRatingbar.setRating(Float.parseFloat(jobList.getQuote().get(0).getFixerId().getUserId().getAvrgStarRating()+""));
			
			
					}
			
					if (jobList.getQuote().get(0).getFixerId().getNumberOfFixesCompleted()!=0) {
						textviewTotalFix.setText(jobList.getQuote().get(0).getFixerId().getNumberOfFixesCompleted()+"");
					}
					else
					{
						textviewTotalFix.setText("0");
					}
			
			
					if ((jobList.getQuote() != null)&&(!jobList.getQuote().isEmpty())) {
						
						payment_status_text_value.setText("$" + jobList.getQuote().get(0).getQuoteAmount());
						Log.e("", "jobdetail" + jobdetails);
					}
					else
					{
						payment_status_text_value.setText("$0");
			
					}
					if(jobList.getQuote().get(0).getFixerId().getUserId().getProfileImage()!=null)
					{
						if(jobList.getQuote().get(0).getFixerId().getUserId().getLoginType().equalsIgnoreCase("facebook"))
						{
							String profileurl=jobList.getQuote().get(0).getFixerId().getUserId().getProfileImage();
							if(profileurl.contains("http"))
							{
								profileurl=profileurl.replace("http", "https");
							}
							MerchantImageUrl=profileurl;
						}
						else
						{
							MerchantImageUrl=jobList.getQuote().get(0).getFixerId().getUserId().getProfileImage();
						}
						
						setCustomerImages();
					}
			}
		}
		
			   if(quotes != null){	
					if ((quotes.getFixerId().getUserId().getFirstName()!=null)&&(quotes.getFixerId().getUserId().getLastName())!=null) {
						textviewCompanyName.setText(quotes.getFixerId().getUserId().getFirstName()+" "+quotes.getFixerId().getUserId().getLastName());
					} 
			
					if (UtilValidate.isNotNull(quotes.getFixerId().getUserId().getAvrgStarRating())) {
						categoryNameRatingbar.setRating(Float.parseFloat(quotes.getFixerId().getUserId().getAvrgStarRating()+""));
					}
			
					if (quotes.getFixerId().getNumberOfFixesCompleted()!=0) {
						textviewTotalFix.setText(quotes.getFixerId().getNumberOfFixesCompleted()+"");
					}
					else
					{
						textviewTotalFix.setText("0");
					}
						
						payment_status_text_value.setText("$" + quotes.getQuoteAmount());
	
					if(quotes.getFixerId().getUserId().getProfileImage()!=null)
					{
						if(quotes.getFixerId().getUserId().getLoginType().equalsIgnoreCase("facebook"))
						{
							String profileurl=quotes.getFixerId().getUserId().getProfileImage();
							if(profileurl.contains("http"))
							{
								profileurl=profileurl.replace("http", "https");
							}
							MerchantImageUrl=profileurl;
						}
						else
						{
							MerchantImageUrl=quotes.getFixerId().getUserId().getProfileImage();
						}
						
						setCustomerImages();
					}
				}
		}
			
	}
	private void setVisibilityofQuote() {
		// TODO Auto-generated method stub
		payment_status_text.setText("QUOTE: ");
		fix_completed_layout.setVisibility(View.GONE);
		pay_now_layout.setVisibility(View.GONE);
		reject_accept_quote_layout.setVisibility(View.VISIBLE);
		review_fixer_layout.setVisibility(View.GONE);
		cancel_fix_button_layout.setVisibility(View.VISIBLE);

		if(jobList!=null)
		{	

			if(jobList.getImages()!=null)
			{
				if(!jobList.getImages().isEmpty())
				{
					CustomerImageList=jobList.getImages();
					SetFixImages(CustomerImageList);
				}
				
				else
				{
					horizondallistview.setVisibility(View.GONE);
					emptyimage.setVisibility(View.VISIBLE);
					
				}
			}
		
			if (UtilValidate.isNotNull(jobList.getJobDetails())) {
				additionaldetails.setText(jobList.getJobDetails());
			} else {
				additionaldetails.setText(" ");
			}
			if(jobList.getAddress()!=null){
				if (UtilValidate.isNotNull(jobList.getAddress().getPostal_code())) {
					fix_details_item_address_edittext.setText(jobList.getAddress().getBlock_number() + " "
							+ jobList.getAddress().getFloor() + " " + jobList.getAddress().getUnit() + " " + jobList.getAddress().getRoad_building() + " ");
				}
			}
			
			if (UtilValidate.isNotNull(jobList.getJobDateTimeSlot())) {
				fix_details_item_date_edittext.setText(
						jobList.getJobDateTimeSlot());
			}
			
			
			
		}else if(notificationListObject != null){
			

			if(notificationListObject.getImages()!=null)
			{
				if(!notificationListObject.getImages().isEmpty())
				{
					CustomerImageList=notificationListObject.getImages();
					SetFixImages(CustomerImageList);
				}
				
				else
				{
					horizondallistview.setVisibility(View.GONE);
					emptyimage.setVisibility(View.VISIBLE);
					
				}
			}
		
			if (UtilValidate.isNotNull(notificationListObject.getJobDetails())) {
				additionaldetails.setText(notificationListObject.getJobDetails());
			} else {
				additionaldetails.setText(" ");
			}
			if (UtilValidate.isNotNull(notificationListObject.getAddress().getPostal_code())) {
				fix_details_item_address_edittext.setText(notificationListObject.getAddress().getBlock_number() + " "
						+ notificationListObject.getAddress().getFloor() + " " + notificationListObject.getAddress().getUnit() + " "
						+ notificationListObject.getAddress().getRoad_building() + " "
						);

			}
			
			if (UtilValidate.isNotNull(notificationListObject.getJobDateTimeSlot())) {
				fix_details_item_date_edittext.setText(
						notificationListObject.getJobDateTimeSlot());
			}
			if(notificationListObject.getQuote()!=null){
				if(notificationListObject.getQuote().getQuoteAmount()>0){
					payment_status_text_value.setText(notificationListObject.getQuote().getQuoteAmount());
				}
			}
			
		}
			if(quotes != null){
				if ((quotes.getFixerId().getUserId().getFirstName()!=null)&&
						(quotes.getFixerId().getUserId().getLastName())!=null) {
					textviewCompanyName.setText(quotes.getFixerId().getUserId().getFirstName()+" "+
							quotes.getFixerId().getUserId().getLastName());
				} else {
					textviewCompanyName.setText(quotes.getFixerId().getUserId().getFirstName());
				}
	
				if (quotes.getFixerId().getUserId().getAvrgStarRating()!=null) {
					categoryNameRatingbar.setRating(Float.parseFloat(quotes.getFixerId().getUserId().getAvrgStarRating()+""));
				}
				else
				{
					categoryNameRatingbar.setRating(0);
				}
	
				if (UtilValidate.isNotNull(quotes.getFixerId().getUserId().getNumberOfFixesCompleted())) {
					textviewTotalFix.setText(quotes.getFixerId().getUserId().getNumberOfFixesCompleted()+"");
				}else
				{
					textviewTotalFix.setText("0");
				}
				if (quotes.getQuoteAmount() != 0){
					payment_status_text_value.setText("$" + quotes.getQuoteAmount());
				}else {
					payment_status_text_value.setText("$0");
	
				}
				
				if(quotes.getFixerId().getUserId().getProfileImage()!=null)
				{
					if(quotes.getFixerId().getUserId().getLoginType().equalsIgnoreCase("facebook"))
					{
						String profileurl=quotes.getFixerId().getUserId().getProfileImage();
						if(profileurl.contains("http"))
						{
							profileurl=profileurl.replace("http", "https");
						}
						MerchantImageUrl=profileurl;
					}
					else
					{
						MerchantImageUrl=quotes.getFixerId().getUserId().getProfileImage();
					}
					
					setCustomerImages();
				}
				
				if(quotes.getQuoteAmount()>0){
					
					payment_status_text_value.setText(quotes.getQuoteAmount()+"");
					
				}
			}else if(userHolder != null){

				if ((userHolder.getFirstName()!=null)&&
						userHolder.getLastName()!=null) {
					textviewCompanyName.setText(userHolder.getFirstName()+" "+
							userHolder.getLastName());
				} else {
					textviewCompanyName.setText(userHolder.getFirstName());
				}
	
				if (userHolder.getAvrgStarRating()!=null) {
					categoryNameRatingbar.setRating(Float.parseFloat(userHolder.getAvrgStarRating()+""));
				}
				else
				{
					categoryNameRatingbar.setRating(0);
				}
	
				if (UtilValidate.isNotNull(userHolder.getNumberOfFixesCompleted())) {
					textviewTotalFix.setText(userHolder.getNumberOfFixesCompleted()+"");
				}else
				{
					textviewTotalFix.setText("0");
				}
				if (quoteAmount != null){
					payment_status_text_value.setText("$" + quoteAmount);
				}else {
					payment_status_text_value.setText("$0");
	
				}
				
				if(userHolder.getProfileImage()!=null)
				{
					if(userHolder.getLoginType().equalsIgnoreCase("facebook"))
					{
						String profileurl=userHolder.getProfileImage();
						if(profileurl.contains("http"))
						{
							profileurl=profileurl.replace("http", "https");
						}
						MerchantImageUrl=profileurl;
					}
					else
					{
						MerchantImageUrl=userHolder.getProfileImage();
					}
					
					
					
					setCustomerImages();
				}
			
			}

		
	}
	
	public void setCustomerImages()
	{
		if (UtilValidate.isNotNull(MerchantImageUrl))

		{

			if (!MerchantImageUrl.equalsIgnoreCase("")) {
				Picasso.with(getActivity()).load(MerchantImageUrl)
						.placeholder(R.drawable.profile_image_null)
						.error(R.drawable.profile_image_null)
						.transform(new RoundTransform())
						.into(image_avatar_review);
			} else {
				Picasso.with(getActivity())
						.load(R.drawable.profile_image_null)
						.placeholder(R.drawable.profile_image_null)
						.error(R.drawable.profile_image_null)
						.transform(new RoundTransform())
						.into(image_avatar_review);
			}

		} else {
			Picasso.with(getActivity()).load(R.drawable.profile_image_null)
					.placeholder(R.drawable.profile_image_null)
					.error(R.drawable.profile_image_null)
					.transform(new RoundTransform())
					.into(image_avatar_review);
		}

	}
	
	
	
	

	private void showPopup() {

		LayoutInflater layoutInflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(
				R.layout.reject_quote_popup_layout, null);
		final PopupWindow popupWindow = new PopupWindow(popupView,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		popupWindow.update();
		popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

		// final TextView
		// close=(TextView)popupView.findViewById(R.id.textViewClose);
		final ImageView close = (ImageView) popupView
				.findViewById(R.id.image_popup_close);
		close.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});

		final ImageView imageExpensive = (ImageView) popupView
				.findViewById(R.id.imageExpensive);

		final ImageView imageWrong = (ImageView) popupView
				.findViewById(R.id.imageWrong);

		final ImageView imageTrustworthy = (ImageView) popupView
				.findViewById(R.id.imageTrustworthy);

		final ImageView imageCompetent = (ImageView) popupView
				.findViewById(R.id.imageCompetent);
		final Button button_reviewSubmit = (Button) popupView
				.findViewById(R.id.button_reviewSubmit);

		button_reviewSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(quotes != null)
				{
					quoteid=quotes.getId();
				}
				else if(jobList!=null)
				{
					
					quoteId=jobList.getQuote().get(0).getId();
					
				}
				if(notificationListObject!=null)
				{
					if(notificationListObject.getQuote()!=null){
					quoteid=notificationListObject.getQuote().getId();
					}else{
						if(QuoteId != null){
							quoteid = QuoteId;
							Log.e("**********", "QID :"+quoteid);
						}
					}
				}
				
				if(quoteid != null){
					pDialog = ProgressDialog.show(
							getActivity(), "", "Loading..",
							true);
	
					QuotesManager.getInstance().RejectQuote(getActivity(),
							rejectCategory, rejectQuotecallBack, quoteid,
							requestcode);
				}
				popupWindow.dismiss();
			}
		});

		imageExpensive.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flagimageExpensive) {
					imageExpensive.setImageResource(R.drawable.feedback_png);
					flagimageExpensive = false;
				} else {
					imageExpensive.setImageResource(R.drawable.feedback1_s);
					flagimageExpensive = true;
					rejectCategory = ApiConstants.TOOEXPENSIVE;
					imageWrong.setImageResource(R.drawable.feedback2);
					imageTrustworthy.setImageResource(R.drawable.feedback3);
					imageCompetent.setImageResource(R.drawable.feedback4);
				}
			}
		});

		imageWrong.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flagimageWrong) {
					imageWrong.setImageResource(R.drawable.feedback2);
					flagimageWrong = false;
				} else {
					imageExpensive.setImageResource(R.drawable.feedback_png);
					flagimageWrong = true;
					rejectCategory = ApiConstants.WRONGCATEGORY;
					imageWrong.setImageResource(R.drawable.feedback2_s);
					imageTrustworthy.setImageResource(R.drawable.feedback3);
					imageCompetent.setImageResource(R.drawable.feedback4);
				}
			}
		});

		imageTrustworthy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flagimageTrustworthy) {
					imageTrustworthy.setImageResource(R.drawable.feedback3);
					flagimageTrustworthy = false;
				} else {
					imageExpensive.setImageResource(R.drawable.feedback_png);
					flagimageTrustworthy = true;
					rejectCategory = ApiConstants.NOTTHRUSTWORTHY;
					imageWrong.setImageResource(R.drawable.feedback2);
					imageTrustworthy.setImageResource(R.drawable.feedback3_s);
					imageCompetent.setImageResource(R.drawable.feedback4);
				}
			}
		});

		imageCompetent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flagimageCompetent) {
					imageCompetent.setImageResource(R.drawable.feedback4);
					flagimageCompetent = false;
				} else {
					imageExpensive.setImageResource(R.drawable.feedback_png);
					flagimageCompetent = true;
					rejectCategory = ApiConstants.NOTCOMPETENT;
					imageWrong.setImageResource(R.drawable.feedback2);
					imageTrustworthy.setImageResource(R.drawable.feedback3);
					imageCompetent.setImageResource(R.drawable.feedback4_s);
				}
			}
		});

	}

	public class AcceptQuoteCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if (responseCode == requestcode) {

				QuotesBaseHolder quotesBaseHolder = (QuotesBaseHolder) result;
				if (UtilValidate.isNotNull(quotesBaseHolder)) {
					if (quotesBaseHolder.isSuccess()) {

						Toast.makeText(getActivity(),
								quotesBaseHolder.getData().getMessage() + "",
								Toast.LENGTH_LONG).show();
						setVisibilityofOngoing();
						EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_ONGOING);
						
						EasyHomeFixApp.setAcceptquoteStatus("accepted");
						/* Intent intent = new Intent(getActivity(), LandingPageActivity.class);
					        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					        Intent broadcastIntent = new Intent();
							broadcastIntent.setAction("com.package.ACTION_LOGOUT");
							getActivity().sendBroadcast(broadcastIntent);
					        startActivity(intent);
					        getActivity().finish();*/
					} else {

						Toast.makeText(getActivity(),
								quotesBaseHolder.isSuccess() + "",
								Toast.LENGTH_LONG).show();

					}
				}

			} else {
				OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
				if (UtilValidate.isNotNull(onFailureErrorHolder)) {
					for (ErrorMessageListHolder error : onFailureErrorHolder
							.getData()) {

					}
				}
			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(getActivity(), "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(getActivity(), ""+result, "ERROR");
			}
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			if (pDialog != null) {
				pDialog.dismiss();
			}
		}

	}

	public class RejectQuotecallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if (responseCode == requestcode) {

				QuotesBaseHolder quotesBaseHolder = (QuotesBaseHolder) result;
				if (UtilValidate.isNotNull(quotesBaseHolder)) {
					if (quotesBaseHolder.isSuccess()) {

						/*Toast.makeText(getActivity(),
								quotesBaseHolder.getData().getMessage() + "",
								Toast.LENGTH_LONG).show();*/
						
						EasyHomeFixApp.setRejectquoteStatus("true");
						setVisibilityFromNotificationCancelled();
					}
				}
			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(getActivity(), "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(getActivity(), ""+result, "ERROR");
			}
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			if (pDialog != null) {
				pDialog.dismiss();
			}
		}

	}
	
	public class CompleteFixCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if (responseCode == requestcode) {
				if(UtilValidate.isNotNull(result)){
					EndpointResonseBaseHolder endpointResonseBaseHolder = (EndpointResonseBaseHolder) result;
					if(endpointResonseBaseHolder.isSuccess()){
						
						if(jobList!=null)
						{
							Toast.makeText(getActivity(), endpointResonseBaseHolder.getData().getMessage(), Toast.LENGTH_LONG).show();
							setVisibilityofPayment();
							EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_COMPLETED_PENDING);
						}
						
						if(notificationListObject!=null)
						{
							Toast.makeText(getActivity(), endpointResonseBaseHolder.getData().getMessage(), Toast.LENGTH_LONG).show();
							if(notificationListObject.getQuote() != null){
								setVisibilityFromNotificationComplete();
								EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);
							}else{
								if(notificationListObject.getId() != null){
									if(pDialog != null){
										if(!pDialog.isShowing()){
											pDialog = ProgressDialog.show(
													getActivity(), null, "Loading..",
													true);
											pDialog.setCancelable(true);
										}
									}
									
										NotificationManager.getInstance().getNotificationListItemObject(getActivity(),notificationListObject.getId(),
												 jobDetailsCallback,requestcode);
									
								}
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
			// TODO Auto-generated method stub
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(getActivity(), "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(getActivity(), ""+result, "ERROR");
			}
			//Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			if (pDialog != null) {
				pDialog.dismiss();
			}
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class CancelFixCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if (responseCode == requestcode) {
				if(UtilValidate.isNotNull(result)){
					EndpointResonseBaseHolder endpointResonseBaseHolder = (EndpointResonseBaseHolder) result;
					if(endpointResonseBaseHolder.isSuccess()){
						if(jobList!=null)
						{
							jobList.setJobStatus("cancelled");
							setVisibilityCancel();
						}
						if(notificationListObject!=null)
						{
							notificationListObject.setJobStatus("cancelled");
							setVisibilityFromNotificationCancelled();
						}
						
						
						Toast.makeText(getActivity(), endpointResonseBaseHolder.getData().getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			if (pDialog != null) {
				pDialog.dismiss();
			}
			// TODO Auto-generated method stub
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(getActivity(), "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(getActivity(), result, "ERROR");
			}
			//Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			if (pDialog != null) {
				pDialog.dismiss();
			}
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	public void SetFixImages(List<String>CustomerImage)
	{

		CustomerImageList=CustomerImage;
		
		if(CustomerImageList!=null)
		{
			imageSettingAdapter = new ImageSettingAdapter(getActivity(),CustomerImageList);
            horizondallistview.setAdapter(imageSettingAdapter);
			imageSettingAdapter.notifyDataSetChanged();
			horizondallistview.setVisibility(View.VISIBLE);
			emptyimage.setVisibility(View.GONE);
		}
		horizondallistview.setOnItemClickListener(new OnItemClickListener() {

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
	 public  void acceptQuoteDialog(Activity activity,String textString,String success){

	    	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	View popupView = inflater.inflate(R.layout.acceptquotepopup, null);
			final PopupWindow popupWindow = new PopupWindow(popupView,android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT,true);
			popupWindow.update();
			popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
			final TextView sucess=(TextView)popupView.findViewById(R.id.sucess);
			final TextView quotestring=(TextView)popupView.findViewById(R.id.quotestring);
			final LinearLayout ok=(LinearLayout)popupView.findViewById(R.id.ok);
			final LinearLayout cancel=(LinearLayout)popupView.findViewById(R.id.cancel);
			
			sucess.setText(success+"");
			quotestring.setText(textString+"");
			
			ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popupWindow.dismiss();	
					
					
					if(quotes != null)
					{
						quoteid=quotes.getId();
					}
					else if(jobList!=null)
					{
						
						quoteId=jobList.getQuote().get(0).getId();
						
					}
					if(notificationListObject!=null)
					{
						if(notificationListObject.getQuote()!=null){
						quoteid=notificationListObject.getQuote().getId();
						}else{
							if(QuoteId != null){
								quoteid = QuoteId;
								Log.e("**********", "QID :"+quoteid);
							}
						}
					}
					if(quoteid != null){
						pDialog = ProgressDialog.show(
								getActivity(), "", "Loading..",
								true);
						QuotesManager.getInstance().AcceptQuote(getActivity(),
							acceptquotesCallback, quoteid, requestcode);
					}else{
						Log.e("&&&&&&&&&&&&&&&&&&&&&&&&&", "Quote NULL!!!!!");
					}
					
				}
			});
			cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popupWindow.dismiss();	
					
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


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("nofication_received"));
		if(jobList != null){
			if(jobList.getId() != null){
				pDialog = ProgressDialog.show(
						getActivity(), null, "Loading..",
						true);
				pDialog.setCancelable(true);
				
				NotificationManager.getInstance().getNotificationListItemObject(getActivity(),jobList.getId(),
						 jobDetailsCallback,requestcode);
			}
		}else if(notificationListObject != null){
			if(notificationListObject.getId() != null){
				if(pDialog != null){
					if(!pDialog.isShowing()){
						pDialog = ProgressDialog.show(
								getActivity(), null, "Loading..",
								true);
						pDialog.setCancelable(true);
					}
				}
				if(!isFromNotfication){
					NotificationManager.getInstance().getNotificationListItemObject(getActivity(),notificationListObject.getId(),
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
				NotificationManager.getInstance().getNotificationListItemObject(getActivity(),notificationJobId,
						jobDetailsCallback,requestcode);
			}
		}
	}
	
	@Override
	public void onDetach() {
		
		// TODO Auto-generated method stub
	    super.onDetach();
	   if(notificationListObject!=null)
	   {
		   if(notificationListObject.getJobCategoryId()!=null)
		   {
			   getActivity().setTitle(notificationListObject.getJobCategoryId().getName());
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
							
							notificationListObject = jobDetails.getData().getJob();
							if(notificationListObject.getJobStatus() != null){
								/*if(EasyHomeFixApp.getRejectquoteStatus().equalsIgnoreCase(
										"true")){
									setVisibilityFromNotificationCancelled();
								}*/
								Log.e("","inside settiing>>>>>>>>>>>>>>>>>>>>");
								
								if(notificationListObject.getJobStatus().equalsIgnoreCase("Completed") &&
										notificationListObject.getPayment() != null){
									setVisibilityFromNotificationCompletePaid();
								}else if(notificationListObject.getJobStatus().equalsIgnoreCase("Completed") &&
										notificationListObject.getPayment() == null){
									setVisibilityFromNotificationComplete();
								}else if(notificationListObject.getJobStatus().equalsIgnoreCase("Cancelled")){
									setVisibilityFromNotificationCancelled();
								}else if(notificationListObject.getJobStatus().equalsIgnoreCase("Ongoing")){
									setVisibilityFromNotificationOngoing();
								}else if(notificationListObject.getJobStatus().equalsIgnoreCase("Pending")){
									setVisibilityofQuote();
									if(quotes != null){
										userHolder=null;
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

		@Override
		public void onFinish(int requestcode, JobCreationBaseHolder jobCreationBaseHolder, int i) {
			
		}
		
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isFromNotfication = false;
	}
	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}
	@Override
	public void onStop() {
		if(pDialog != null){
			pDialog.dismiss();
		}
		super.onStop();
	}
	 
}
