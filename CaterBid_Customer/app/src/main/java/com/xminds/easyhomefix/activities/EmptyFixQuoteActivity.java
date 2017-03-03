package com.xminds.easyhomefix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix.Holder.Job;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.JobHolder;
import com.xminds.easyhomefix.Holder.JobList;
import com.xminds.easyhomefix.Holder.JobNotifications;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.Holder.Quotes;
import com.xminds.easyhomefix.Holder.QuotesBaseHolder;
import com.xminds.easyhomefix.Managers.NotificationManager;
import com.xminds.easyhomefix.Managers.QuotesManager;
import com.xminds.easyhomefix.actionbar.BaseActivity;
import com.xminds.easyhomefix.adapter.FancyCoverFlowSampleAdapter;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.constants.Constants;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class EmptyFixQuoteActivity extends BaseActivity {
	
	private LinearLayout negotiate_chat_id;
	private ImageView image_back;
	private LinearLayout viewAll_layout;
	private TextView textview_trackyourfix;
	private FancyCoverFlow fancyCoverFlow;
	private RelativeLayout quote_layout;
	private TextView fixer_name;
	private int fullScreenWidth=0 ;
	private int fullscreenHeight=0;
	private int imageHeight=0;
	private int imageWidth=0;
	private ProgressDialog pDialog;
	private String jobId ;
	private int requestcode=0;
	private RatingBar ratingReview;
	private QuotesCallback quotesCallback;
	private TextView completed_fixes_id;
	private TextView quote_cost_id;
	private Button accept_quote_id;
	private String quoteid;
	private int quoteamount;
	private String merchantImageUrl="";
	private String fixerid;
	private String Quoteamount;
	private String catId;
	private String jobTypeId;
	private SwipeRefreshLayout swipetorefresh;
	private String block_number;
	private String floor;
	private String unit;
	private String road_building;
	private String postal_code;
	private String jobdateandtime;
	private String jobdetails;
	private JobList jobList;
	private JobHolder jobholder;
	private Job notificationListObject;
	private String profileimage;
	private AcceptQuoteCallBack acceptQuoteCallBack; 
	private LogoutReceiver logoutReceiver;
	private int selectedPosition;
	private QuotesBaseHolder quotesBaseHolder;
	private JobNotifications jobNotifications;
	private String notificationJobId;
	private JobDetailsCallback jobDetailsCallback;
	private String firstname;
	private String lastname;
	private String logintype;
	public class LogoutReceiver extends BroadcastReceiver {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        if (intent.getAction().equals("com.package.ACTION_LOGOUT")) {
	            finish();
	        }
	    }
	}
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if(jobId != null){
				QuotesManager.getInstance().getAllQuotes(EmptyFixQuoteActivity.this, quotesCallback, jobId, requestcode);
			}
		}
	};	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.fix_quotes_pending_tab);
		
		initView();
		initManager();
		getIntentValues();
		setVisibilites();
		setTexts();
		
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		 pDialog = ProgressDialog.show(EmptyFixQuoteActivity.this,"","Loading..",true);	
		 
		 if(notificationListObject != null){
			 
			 jobId = notificationListObject.getId();
			 QuotesManager.getInstance().getAllQuotes(EmptyFixQuoteActivity.this, quotesCallback, jobId, requestcode);
		 }else if(jobList != null){
			 
			 jobId = jobList.getId();
			 QuotesManager.getInstance().getAllQuotes(EmptyFixQuoteActivity.this, quotesCallback, jobId, requestcode);
		 }else if(jobNotifications != null){
			 if(jobNotifications.getJob() != null){
				 jobId = jobNotifications.getJob().getId();
				 QuotesManager.getInstance().getAllQuotes(EmptyFixQuoteActivity.this, quotesCallback, jobId, requestcode);
			 }
		 }else{
			 jobId = notificationJobId;
			 NotificationManager.getInstance().getNotificationListItemObject(EmptyFixQuoteActivity.this,notificationJobId,
					 jobDetailsCallback,requestcode);
		 }
		 Log.e("%%%%%%%%%%%%%%%%%%%%%%%%%", ",HDR: "+CurrentlyLoggedUserDAO.getInstance().getHeader());
		 
		
		 Display display = EmptyFixQuoteActivity.this.getWindow().getWindowManager().getDefaultDisplay();
		 fullScreenWidth = display.getWidth();
		 fullscreenHeight=display.getHeight();
		 Log.e("widthhhfdgbdh","widthhh"+fullScreenWidth);
		 if((fullScreenWidth<750)&&(fullScreenWidth>700))
		 {
			 //xhdpi
			 imageHeight=200;
			 imageWidth=200;
		 }
		 else if((fullScreenWidth<1100)&&(fullScreenWidth>1050))
		 {
			 //xxhdpi
			 imageHeight=300;
			 imageWidth=300;
		 }
		 else
		 {
			
			 imageHeight=150;
			 imageWidth=150;
		 }
		

		 
		 swipetorefresh.setColorScheme(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_dark);

		 swipetorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
	             
	            @Override
	            public void onRefresh() {
	                // TODO Auto-generated method stub
	            	swipetorefresh.setRefreshing(true);
	                ( new Handler()).postDelayed(new Runnable() {
	                     
	                    @Override
	                    public void run() {
	                        // TODO Auto-generated method stub
	                    	swipetorefresh.setRefreshing(false);
	                        
	                    	
	                    	// pDialog = ProgressDialog.show(EmptyFixQuoteActivity.this,"","Loading..",true);	
	                		 QuotesManager.getInstance().getAllQuotes(EmptyFixQuoteActivity.this, quotesCallback, jobId, requestcode);
	                    }
	                }, 3000);
	            }
	        });


		image_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		negotiate_chat_id.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent pendingFixIntent = new Intent(EmptyFixQuoteActivity.this,TrackFixFragmentTabhostActivity.class);
				EasyHomeFixApp.setCategoryNameDefaultTab(Constants.CHAT);
				EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_PENDING);
				pendingFixIntent.putExtra("From_notification_pending", notificationListObject);
				pendingFixIntent.putExtra("PENDINGOBJECT", jobList);
				if(quotesBaseHolder.getData().getQuotes() != null &&
						UtilValidate.isNotEmpty(quotesBaseHolder.getData().getQuotes())){
					pendingFixIntent.putExtra("QuoteObject", quotesBaseHolder.getData().getQuotes().get(selectedPosition));
				}
				startActivity(pendingFixIntent);
				finish();
			
			}
		});
		
		
		viewAll_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent pendingFixIntent = new Intent(EmptyFixQuoteActivity.this,ReviewActivity.class);
				pendingFixIntent.putExtra(Constants.PENDING, "pending");
				pendingFixIntent.putExtra("companyname", fixer_name.getText().toString());
				pendingFixIntent.putExtra("firstname",firstname);
				pendingFixIntent.putExtra("lastname",lastname);
				pendingFixIntent.putExtra("rating", String.valueOf(ratingReview.getRating()));
				pendingFixIntent.putExtra("fixescompleted", String.valueOf(completed_fixes_id.getText().toString()));
				pendingFixIntent.putExtra("Fixerid",fixerid);
				pendingFixIntent.putExtra("ProfileImage", profileimage);
				pendingFixIntent.putExtra("LoginType", logintype);
				
				startActivity(pendingFixIntent);
			}
		});
		
		quote_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Intent collectNumberintent = new Intent(EmptyFixQuoteActivity.this,CollectFloorNoandUnitNoActivity.class);
				startActivity(collectNumberintent);*/
			}
		});
		
		accept_quote_id.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Log.e("$$$$$$$$$$$$$$$$", "selectedPosition"+selectedPosition);
				/*if(jobList!=null)
				{
					if(jobList.getQuote()!=null)
					{
						quoteid=jobList.getQuote().get(selectedPosition).getId();
						Log.e("","quote id direct"+quoteid);
					}
					
					
				}
				else
				if(notificationListObject!=null)
				{
					//quoteid=notificationListObject.getQuote().getId();
					if(quotesBaseHolder!=null){
						quoteid = quotesBaseHolder.getData().getQuotes().get(selectedPosition).getId();
					}
					Log.e("","quote id from direct"+quoteid);
				}*/
				quoteid = quotesBaseHolder.getData().getQuotes().get(selectedPosition).getId();
				
				if(quotesBaseHolder.getData().getQuotes().get(selectedPosition).getQuoteAmount()>0)
				{
					acceptQuoteDialog(EmptyFixQuoteActivity.this, "Are you sure you want to accept the quote", "Accept Quote");
				}
				else
				{
					Utils.showDialog(EmptyFixQuoteActivity.this,"Unable to continue with zero quote.", "ERROR");
					
				}
				
				
			
		
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// for auto refresh
		EmptyFixQuoteActivity.this.registerReceiver(mBroadcastReceiver, new IntentFilter("nofication_received"));
				
		if(EasyHomeFixApp.getCount()!=null)
			getnotificationSize(Integer.parseInt(EasyHomeFixApp.getCount()));
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(mBroadcastReceiver);
	unregisterReceiver(logoutReceiver);
		super.onDestroy();
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
					
					pDialog = ProgressDialog.show(
							EmptyFixQuoteActivity.this, "", "Loading..",
							true);
					QuotesManager.getInstance().AcceptQuote(EmptyFixQuoteActivity.this, acceptQuoteCallBack, quoteid, requestcode);
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
	
	private void getIntentValues() {
		// TODO Auto-generated method stub
		
		if (EmptyFixQuoteActivity.this.getIntent().hasExtra("PENDINGOBJECT")) {
			 jobList = (JobList) getIntent()
						.getSerializableExtra("PENDINGOBJECT");
			 Log.d("", "OBJECT+++++++++++++++++++++"+jobList.getJobStatus());
			}
		
		// JOBLIST FROM CONFORM ORDER PAGE
		
		if(EmptyFixQuoteActivity.this.getIntent().hasExtra("jobId"))
		{
			jobList = (JobList) getIntent()
					.getSerializableExtra("jobId");
		}
		if(EmptyFixQuoteActivity.this.getIntent().hasExtra("From_notification_pending"))
		{
			notificationListObject = (Job) getIntent()
					.getSerializableExtra("From_notification_pending");
		}
		if(EmptyFixQuoteActivity.this.getIntent().hasExtra("From_notification_ongoing"))
		{
			notificationListObject= (Job) getIntent()
					.getSerializableExtra("From_notification_ongoing");
		}
		if(EmptyFixQuoteActivity.this.getIntent().hasExtra("From_notification_completed"))
		{
			notificationListObject= (Job) getIntent()
					.getSerializableExtra("From_notification_completed");
		}
		if (EmptyFixQuoteActivity.this.getIntent().hasExtra("notification_chat")) {
			notificationListObject = (Job) EmptyFixQuoteActivity.this.getIntent()
						.getSerializableExtra("notification_chat");
			}
		if(EmptyFixQuoteActivity.this.getIntent().hasExtra("notification_quote")){
			jobNotifications = (JobNotifications) EmptyFixQuoteActivity.this.getIntent()
					.getSerializableExtra("notification_quote");
		}
		if(EmptyFixQuoteActivity.this.getIntent().hasExtra("notification_jobid")){
			notificationJobId = (String) EmptyFixQuoteActivity.this.getIntent().getStringExtra("notification_jobid");
		}
		}
		
	
		
	

	private void setTexts() {
		// TODO Auto-generated method stub
		textview_trackyourfix.setText("Fix Quotes");
	}

	private void setVisibilites() {
		// TODO Auto-generated method stub

		image_back.setVisibility(View.VISIBLE);
		textview_trackyourfix.setVisibility(View.VISIBLE);
	}

	private void initManager() {
		// TODO Auto-generated method stub
		quotesCallback=new QuotesCallback();
		acceptQuoteCallBack=new AcceptQuoteCallBack();
		jobDetailsCallback = new JobDetailsCallback();
	}

	private void initView() {
		// TODO Auto-generated method stub
		negotiate_chat_id = (LinearLayout)findViewById(R.id.negotiate_chat_id);
		image_back = (ImageView)findViewById(R.id.image_back);
		textview_trackyourfix = (TextView)findViewById(R.id.textview_trackyourfix);
		viewAll_layout=(LinearLayout)findViewById(R.id.viewAll_layout);
		fixer_name = (TextView)findViewById(R.id.fixer_name);
		quote_layout=(RelativeLayout)findViewById(R.id.quote_layout);
		ratingReview=(RatingBar)findViewById(R.id.ratingReview);
		completed_fixes_id=(TextView)findViewById(R.id.completed_fixes_id);
		quote_cost_id=(TextView)findViewById(R.id.quote_cost_id);
		accept_quote_id=(Button)findViewById(R.id.accept_quote_id);
		swipetorefresh=(SwipeRefreshLayout)findViewById(R.id.swipetorefresh);
		 fancyCoverFlow = (FancyCoverFlow)findViewById(R.id.fancyCoverFlow);
	}
	
	public class QuotesCallback implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if (responseCode == requestcode) {
				quotesBaseHolder = (QuotesBaseHolder) result;
				if(quotesBaseHolder.getData()!=null){
					if(quotesBaseHolder.getData().getQuotes()!=null&&quotesBaseHolder.getData().getQuotes().size()>0)
					{
						if(jobList!=null){
							jobList.setQuote(quotesBaseHolder.getData().getQuotes());
						}
					}
				}
				if (UtilValidate.isNotNull(quotesBaseHolder)) {
			if(UtilValidate.isNotNull(	quotesBaseHolder.getData()))
			{
				if(UtilValidate.isNotEmpty(	quotesBaseHolder.getData().getQuotes()))
					
				{
					for(Quotes quotes : quotesBaseHolder.getData().getQuotes()){
						if(quotes.getStatus().equalsIgnoreCase("2")){
							Intent pendingFixIntent = new Intent(EmptyFixQuoteActivity.this,TrackFixFragmentTabhostActivity.class);
							EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
						
							EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION);
							//jobList.setQuote(quotesBaseHolder.getData().getQuotes());
							pendingFixIntent.putExtra("notification_jobid", jobId);
							pendingFixIntent.putExtra("QuoteObject", quotesBaseHolder.getData().getQuotes().get(selectedPosition));
							startActivity(pendingFixIntent);
							finish();
						}
					}
						negotiate_chat_id.setEnabled(true);
						accept_quote_id.setEnabled(true);
					   viewAll_layout.setVisibility(View.VISIBLE);
					   fancyCoverFlow.setAdapter(new FancyCoverFlowSampleAdapter(EmptyFixQuoteActivity.this,imageWidth,imageHeight,	quotesBaseHolder.getData().getQuotes()));
				        fancyCoverFlow.setUnselectedAlpha(1.0f);
				        fancyCoverFlow.setUnselectedSaturation(0.0f);
				        fancyCoverFlow.setUnselectedScale(0.5f);
				        fancyCoverFlow.setSpacing(0);
				        fancyCoverFlow.setMaxRotation(0);
				        fancyCoverFlow.setScaleDownGravity(0.5f);
				        fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
				        Log.e("9999999999999999999999", "|QID2:"+UtilValidate.isNotNull(jobNotifications));
				        if(jobNotifications != null){
					        for(int i=0; i<quotesBaseHolder.getData().getQuotes().size();i++){
					        	Log.e("9999999999999999999999", "QID:"+quotesBaseHolder.getData().getQuotes().get(i).getId()
					        			+"|QID2:"+jobNotifications.getQuote().getId());
					        	if(quotesBaseHolder.getData().getQuotes().get(i).getId().equalsIgnoreCase(jobNotifications.getQuote().getId())){
					        		fancyCoverFlow.setSelection(i);
					        	}
					        }
				        }
				        
				        fancyCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1,
									int position, long arg3) {
								// TODO Auto-generated method stub
								Log.e("#########", "POSITION: "+position);
								selectedPosition = position;
								for(int i=0;i<quotesBaseHolder.getData().getQuotes().size();i++)
								{
									
								
								if(position == i){
						    		//fixer_name.setText("FIXER NAME1");
									//fixer_name.setText("Hang on! We're gathering quotes...");
									quote_cost_id.setText("$"+quotesBaseHolder.getData().getQuotes().get(position).getQuoteAmount()+" ");
									completed_fixes_id.setText(quotesBaseHolder.getData().getQuotes().get(position).getFixerId().getNumberOfFixesCompleted()+" ");
									ratingReview.setRating(Float.parseFloat(quotesBaseHolder.getData().getQuotes().get(position).getFixerId().getAvrgStarRating()));
									fixer_name.setText(	quotesBaseHolder.getData().getQuotes().get(position).getFixerId().getUserId().getFirstName()
											+" "+quotesBaseHolder.getData().getQuotes().get(position).getFixerId().getUserId().getLastName());
									quoteid=quotesBaseHolder.getData().getQuotes().get(position).getId();
									merchantImageUrl=quotesBaseHolder.getData().getQuotes().get(i).getFixerId().getUserId().getProfileImage();
									quoteamount=quotesBaseHolder.getData().getQuotes().get(position).getQuoteAmount();
									fixerid=quotesBaseHolder.getData().getQuotes().get(position).getFixerId().getUserId().getId();
									profileimage=quotesBaseHolder.getData().getQuotes().get(position).getFixerId().getUserId().getProfileImage();
									firstname=quotesBaseHolder.getData().getQuotes().get(position).getFixerId().getUserId().getFirstName();
									lastname=quotesBaseHolder.getData().getQuotes().get(position).getFixerId().getUserId().getLastName();
									logintype=quotesBaseHolder.getData().getQuotes().get(position).getFixerId().getUserId().getLoginType();
									
						    	}
							}}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub
								
							}
						});
				        
					
				}
				else
				{
					Toast.makeText(EmptyFixQuoteActivity.this, "No quotes", Toast.LENGTH_SHORT).show();
					negotiate_chat_id.setEnabled(false);
					accept_quote_id.setEnabled(false);
					
					//finish();
				}
			}
			       
				}

			} else {

				OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				/*Toast.makeText(EmptyFixQuoteActivity.this,
						"Please check your internet connection",
						Toast.LENGTH_SHORT).show();*/
				Utils.showDialog(EmptyFixQuoteActivity.this, "Please check your internet connection.", "ERROR");
			}

		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}

	}
	
	
	public class AcceptQuoteCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub

			if (responseCode == requestcode) {

				QuotesBaseHolder AcceptquotesBaseHolder = (QuotesBaseHolder) result;
				if (UtilValidate.isNotNull(AcceptquotesBaseHolder)) {
					if (AcceptquotesBaseHolder.isSuccess()) {
						if(pDialog!=null)
						{
							pDialog.dismiss();
						}
						Toast.makeText(EmptyFixQuoteActivity.this,
								AcceptquotesBaseHolder.getData().getMessage() + "",
								Toast.LENGTH_LONG).show();
						
						Intent pendingFixIntent = new Intent(EmptyFixQuoteActivity.this,TrackFixFragmentTabhostActivity.class);
						EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
						
						if(jobList!=null)
						{
							EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_ONGOING);
						}
						else if(notificationListObject!=null)
							{
								EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION_ONGOING);
							}
						
						//jobList.setQuote(quotesBaseHolder.getData().getQuotes());
						pendingFixIntent.putExtra("PENDINGOBJECT", jobList);
						pendingFixIntent.putExtra("From_notification_pending", notificationListObject);
						pendingFixIntent.putExtra("QuoteObject", quotesBaseHolder.getData().getQuotes().get(selectedPosition));
						startActivity(pendingFixIntent);
						finish();
					} else {

						Toast.makeText(EmptyFixQuoteActivity.this,
								AcceptquotesBaseHolder.isSuccess() + "",
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
			
			if(pDialog!=null)
			{
				pDialog.dismiss();
			}
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				Utils.showDialog(EmptyFixQuoteActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(EmptyFixQuoteActivity.this, ""+result, "ERROR");
			}


		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub

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
							QuotesManager.getInstance().getAllQuotes(EmptyFixQuoteActivity.this, quotesCallback, jobId, requestcode);
						}
					}
				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			if(pDialog!=null)
			{
				 pDialog.dismiss();
			}
			
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(EmptyFixQuoteActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(EmptyFixQuoteActivity.this, ""+result, "ERROR");
			}
			
		}

		@Override
		public void onFinish(int requestcode, JobCreationBaseHolder jobCreationBaseHolder, int i) {
			
		}
		

	
}
}
