package com.xminds.easyhomefix.actionbar;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pkmmte.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.Job;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.JobNotifications;
import com.xminds.easyhomefix.Holder.UserTokenHolder;
import com.xminds.easyhomefix.Managers.NotificationManager;
import com.xminds.easyhomefix.activities.CustomerSettingsActivity;
import com.xminds.easyhomefix.activities.EmptyFixQuoteActivity;
import com.xminds.easyhomefix.activities.ReviewServiceActivity;
import com.xminds.easyhomefix.activities.SignUpandLoginActivity;
import com.xminds.easyhomefix.activities.TrackFixFragmentTabhostActivity;
import com.xminds.easyhomefix.adapter.NotificationsAdapter;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.constants.Constants;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.dao.UserDAO;
import com.xminds.easyhomefix.swipetohide.SwipeDismissListViewTouchListener;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class ActionBarNotificationListFragment extends Fragment {

	private List<JobNotifications> FixList = new ArrayList<JobNotifications>();
	private ListView listview_notification;
	private ViewGroup container;
	private LinearLayout relative_button_login;
	private CircularImageView profile_pic_circular;
	private LinearLayout relative_button_settings;
	private TextView slider_row_username;
	private LinearLayout markasRead;
	private UserTokenHolder userTokenHolder;
	private String firstname;
	private String lastname;
	int requestcode = 0;
	private String notificationdate;
	private String notificationText;
	private List<JobNotifications> notificationlist = new ArrayList<JobNotifications>();
	private NotificationsAdapter notificationsAdapter;
	private View topBanner;
	private ViewGroup dismissableContainer;
	private String notificationid;
	private int  markasreadpos;
	ActionBarNotificationListFragment actionBarNotificationListFragment;
	UpdateNotificationCallBack updateNotificationCallBack;
	EmailSignupBaseHolder notificationBaseHolder = new EmailSignupBaseHolder();
	private String companyname;
	private float averagestarrating;
	private int fixcompleted;
	private int quoteamount;
	private String block_number;
	private String floor;
	private String unit;
	private String road_building;
	private String postal_code;
	private String jobdateandtime;
	private String jobdetails;
	private String merchantImageUrl;
	private List<String> CustomerImageList = new ArrayList<String>();
	private String jobstatus;
	private String quoteid;
	private TextView notificationcount;
	private int notification;
	private int count;
	NotificationCallBack notificationCallBack;
	private int currentFirstVisibleItem;
	private int currentVisibleItemCount;
	private int currentScrollState;
	private boolean isLoading = true;
	private int currenttotalItemCount;
	private int mPreviousTotal = 0;
	private boolean mLoading = true;
	private boolean mLastPage = false;
	private int mCurrentPage = 0;

	private int mVisibleThreshold = 5;
	private RelativeLayout loading_completed;
	private int limit = 10;
	private int offset = 0;
	private NotificationListItemCallBack notificationListItemCallBack;
	private Job NotificationJobObject ;
	private int position;
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (CurrentlyLoggedUserDAO.getInstance()
					.isAnyUserInCurrentlyLoggedUserTable())
			{
				 limit=10;
				 offset=0;
				
				NotificationManager.getInstance().getNotificationList(
						getActivity(),limit,offset, notificationCallBack, requestcode);
				
			}
		}
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.container = container;
		return inflater.inflate(R.layout.slidebar_list, null);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
		// for auto refresh
		getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("nofication_received"));
		
		
		
		mPreviousTotal = 0;
		initViews();
		initManagers();
		userTokenHolder = new UserTokenHolder();
		userTokenHolder = UserDAO.getInstance().getUserDetails();
		if (UtilValidate.isNotNull(userTokenHolder)) {
			if (UtilValidate.isNotNull(userTokenHolder.getUser())) {
				if (UtilValidate.isNotNull(userTokenHolder.getUser()
						.getFirstName())) {

					firstname = userTokenHolder.getUser().getFirstName();
				}
				if (UtilValidate.isNotNull(userTokenHolder.getUser()
						.getLastName())) {

					lastname = userTokenHolder.getUser().getLastName();
				}
				
				if (UtilValidate.isNotNull(userTokenHolder.getUser()
						.getProfileImage())) {
					if(userTokenHolder.getUser().getLoginType().equalsIgnoreCase("facebook"))
					{
						String profileurl=userTokenHolder.getUser()
								.getProfileImage();
						if(profileurl.contains("http"))
						{
							profileurl=profileurl.replace("http", "https");
						}
						Log.e("", "############"+profileurl);
						Picasso.with(getActivity())
								.load(profileurl)
								.into(profile_pic_circular);
					}
					else
					{
						Picasso.with(getActivity())
						.load(userTokenHolder.getUser().getProfileImage())
						.into(profile_pic_circular);
					}
						
				}
			}
		}
		
		if (CurrentlyLoggedUserDAO.getInstance()
				.isAnyUserInCurrentlyLoggedUserTable())
		{
			 limit=10;
			 offset=0;
			
			NotificationManager.getInstance().getNotificationList(
					getActivity(),limit,offset, notificationCallBack, requestcode);
			
		}

		super.onResume();
	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
		initManagers();

		 final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
		
		if (CurrentlyLoggedUserDAO.getInstance()
				.isAnyUserInCurrentlyLoggedUserTable()) {

			if (EasyHomeFixApp.getJobNotifications() != null) {
				notificationlist = EasyHomeFixApp.getJobNotifications();
				notificationsAdapter = new NotificationsAdapter(
						ActionBarNotificationListFragment.this,
						notificationlist);
				listview_notification.setAdapter(notificationsAdapter);
				
				if(notificationlist.size()>0)
				{
					if (notificationlist.get(0).getRecieverUser() != null) {
						if (notificationlist.get(0).getRecieverUser().getUnreadNotifications() != 0) {
						notificationcount
								.setText(String
										.valueOf(notificationlist.get(0)
												.getRecieverUser()
												.getUnreadNotifications()));
						/*BaseActivity.getnotificationSize(notificationlist.get(0)
								.getRecieverUser().getUnreadNotifications());*/
						 //  BaseActivity baseActivity=new BaseActivity();
			        	   //baseActivity.getnotificationSize(notificationlist.get(0).getRecieverUser().getUnreadNotifications());
						EasyHomeFixApp.setCount(notificationlist.get(0)
								.getRecieverUser().getUnreadNotifications()+"");
						
						count = notificationlist.get(0).getRecieverUser()
								.getUnreadNotifications();
						}
					}
				}
				
				
				notificationsAdapter.notifyDataSetChanged();

			}
			
		if(EasyHomeFixApp.getJobNotifications()!=null)
		{
			notificationlist=EasyHomeFixApp.getJobNotifications();
			notificationsAdapter = new NotificationsAdapter(
			
			ActionBarNotificationListFragment.this,notificationlist
			);
	           listview_notification
			.setAdapter(notificationsAdapter);
	           notificationsAdapter.notifyDataSetChanged();
	           if((notificationlist!=null)&&(!notificationlist.isEmpty())){
	           if(notificationlist.get(0).getRecieverUser() != null){
	        	   if(notificationlist.get(0).getRecieverUser().getUnreadNotifications() != 0){
	        	   notificationcount.setText(String.valueOf(notificationlist.get(0).getRecieverUser().getUnreadNotifications()));
	        	  // BaseActivity baseActivity=new BaseActivity();
	        	   //baseActivity.getnotificationSize(notificationlist.get(0).getRecieverUser().getUnreadNotifications());
	        	   
	       		EasyHomeFixApp.setCount(0+"");
				EasyHomeFixApp.setCount(notificationlist.get(0)
						.getRecieverUser().getUnreadNotifications()+"");
				
	        	   count = notificationlist.get(0).getRecieverUser().getUnreadNotifications();
	        	   Log.d("", "COUNT"+count);
	           }
	           }
		}
		}
		}

		userTokenHolder = new UserTokenHolder();
		userTokenHolder = UserDAO.getInstance().getUserDetails();
		if (UtilValidate.isNotNull(userTokenHolder)) {
			if (UtilValidate.isNotNull(userTokenHolder.getUser())) {
				if (UtilValidate.isNotNull(userTokenHolder.getUser()
						.getFirstName())) {

					firstname = userTokenHolder.getUser().getFirstName();
				}
				if (UtilValidate.isNotNull(userTokenHolder.getUser()
						.getLastName())) {

					lastname = userTokenHolder.getUser().getLastName();
				}

				if (UtilValidate.isNotNull(userTokenHolder.getUser()
						.getProfileImage())) {
					
					if(userTokenHolder.getUser().getLoginType().equalsIgnoreCase("facebook"))
					{
						Log.e("","facebook login>>>>>>>>>>>>>...");
						String profileurl=userTokenHolder.getUser()
								.getProfileImage();
						if(profileurl.contains("http"))
						{
							profileurl=profileurl.replace("http", "https");
						}
						Log.e("", "############"+profileurl);
						Picasso.with(getActivity())
								.load(profileurl)
								.into(profile_pic_circular);
					}
					else
					{
						Log.e("","oterrrrr login>>>>>>>>>>>>>...");
						Picasso.with(getActivity())
						.load(userTokenHolder.getUser().getProfileImage())
						.into(profile_pic_circular);
					}
					
					/*Picasso.with(getActivity())
							.load(userTokenHolder.getUser().getProfileImage())
							.into(profile_pic_circular);
					
					Log.e("","profile image url in oncreateeeee>>>>>>>>>>>>>>>>>>>>>>>>>>>>*********"+userTokenHolder.getUser().getProfileImage());*/
				}
			}
		}

		
		if (CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable() &&
				! CurrentlyLoggedUserDAO.getInstance().getLoginType().equalsIgnoreCase("udId")) {
			slider_row_username.setVisibility(View.VISIBLE);

			slider_row_username.setText(firstname + " " + lastname);
			relative_button_login.setVisibility(View.GONE);
			relative_button_settings.setVisibility(View.VISIBLE);
			listview_notification.setDivider(null);
			listview_notification.setDividerHeight(0);

		} else {
			slider_row_username.setVisibility(View.GONE);
			relative_button_login.setVisibility(View.VISIBLE);
			relative_button_settings.setVisibility(View.GONE);

		}

		listview_notification.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int pos, long id) {
				// TODO Auto-generated method stub
				
				if(notificationlist.get(pos).getNotifyStatus().equalsIgnoreCase("Unread"))
				{
					NotificationManager.getInstance().updateNotification(getActivity(), updateNotificationCallBack, notificationlist.get(pos).getId(), requestcode);
				}
				NotificationManager.getInstance().getNotificationListItemObject(getActivity(),notificationlist.get(pos).getJob().getId(),notificationListItemCallBack,requestcode);
				position=pos;
				//NotificationManager.getInstance().updateNotification(getActivity(), updateNotificationCallBack, notificationlist.get(pos).getJob().getId(), pos);
				
				//CHAT DETAIL PAGE
				
				
				/*if(notificationlist.get(position)!= null){
					
				if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Message recieved"))
						
				{
					Log.d("","INSIDE CHAT" );
					EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CHAT);
					EasyHomeFixApp.setCategoryNameDefaultTab(Constants.CHAT);
					Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
					Qute.putExtra("notification_chat", NotificationJobObject);
					getActivity().startActivity(Qute);
				}
				
				else
					
				{
					//FIX QUOTE PAGE BEFORE ACCEPTING THE QUOTE FROM PENDING
					
					if(notificationlist.get(position).getJob()!=null){
						if(notificationlist.get(position).getJob().getJobStatus().equalsIgnoreCase("Pending")){
					
						
						Intent EmptyQute = new Intent(getActivity(),EmptyFixQuoteActivity.class);
						EmptyQute.putExtra("From_notification_pending", NotificationJobObject);
						getActivity().startActivity(EmptyQute);
							
					}else if(notificationlist.get(position).getJob().getJobStatus().equalsIgnoreCase("Ongoing")){
						
						
						//SHOWING DETAIL PAGE IN  ONGOING
						Log.d("","INSIDE DETAILS" );
						
						if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Quote recieved")){
							Log.d("","INSIDE DETAILS" );
							
							AlertDialog.Builder alt_bld = new AlertDialog.Builder(getActivity());  
							alt_bld.setMessage("You have already accepted a quote for this job. Do you want to view that quote instead?");
							alt_bld.setCancelable(false);
							alt_bld.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, close
									// current activity
									
									//DETAIL PAGE
									notificationlist.get(position).setSwipe("true");
									notificationsAdapter.notifyDataSetChanged();
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_ONGOING);
									EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
									Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
									Qute.putExtra("notification_ongoing", NotificationJobObject);
									getActivity().startActivity(Qute);
									dialog.cancel();
								}
							  });
							alt_bld.setNegativeButton("No",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});
							alt_bld.show();
							
						}else{
							
							Intent FixQute = new Intent(getActivity(),EmptyFixQuoteActivity.class);
							FixQute.putExtra("From_notification_ongoing", NotificationJobObject);
							getActivity().startActivity(FixQute);
							
							
							
							
							
						}
						
						
					}
						else if(notificationlist.get(position).getJob().getJobStatus().equalsIgnoreCase("Completed")){
							
							//SHOWING DETAIL PAGE IN  ONGOING
							Log.d("","INSIDE DETAILS" );
							if((notificationlist.get(position).getNotifyType().equalsIgnoreCase("Quote recieved"))||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Quote Edited")){
								
								Log.d("","INSIDE DETAILS" );
								
								AlertDialog.Builder alt_bld = new AlertDialog.Builder(getActivity());  
								alt_bld.setMessage("You have already accepted a quote for this job. Do you want to view that quote instead?");
								alt_bld.setCancelable(false);
								alt_bld.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// if this button is clicked, close
										// current activity
										
										//DETAIL PAGE
										
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);
										EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
										Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
										Qute.putExtra("notification_completed", NotificationJobObject);
										getActivity().startActivity(Qute);
										dialog.cancel();
										//getActivity().finish();
									}
								  });
								alt_bld.setNegativeButton("No",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dialog.cancel();
									}
								});
								alt_bld.show();
								
							}  else if( notificationlist.get(position).getNotifyType().equalsIgnoreCase("Review Reminder")
									|| notificationlist.get(position).getNotifyType().equalsIgnoreCase("Review Request")||(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Review uncompleted"))){
				               // if( notification.jobDetails.fixerReview == nil ){
								
									Intent reviewIntent = new Intent(getActivity(),ReviewServiceActivity.class);
									getActivity().startActivity(reviewIntent);
				                }
							else if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Payment reminder 1day")||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Payment reminder 2hrs")||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Payment reminder 6hrs")){
								
								EasyHomeFixApp
								.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								Qute.putExtra("notification_completed", NotificationJobObject);
								getActivity().startActivity(Qute);
								
							}
							
							else if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job reminder in an hour")||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job reminder on the day")){
								
								EasyHomeFixApp
								.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								Qute.putExtra("notification_completed", NotificationJobObject);
								getActivity().startActivity(Qute);
							}
							
							else{
								
								Intent FixQute = new Intent(getActivity(),EmptyFixQuoteActivity.class);
								FixQute.putExtra("From_notification_completed", NotificationJobObject);
								getActivity().startActivity(FixQute);
								
							}
							
							
						
					}
						cancelled case
						
						else{
						
						
					}
					}
				}
						
					

				}*/

				
				

			}
		});

		relative_button_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(),
						SignUpandLoginActivity.class);
				startActivity(i);

				getActivity().finish();
			}
		});

		// SWIPE IN LISTVIEW

		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(
				listview_notification,
				new SwipeDismissListViewTouchListener.DismissCallbacks() {
					@Override
					public boolean canDismiss(int position) {
						return true;
					}

					@Override
					public void onDismiss(ListView listview_notification,
							int[] reverseSortedPositions) {
						// TODO Auto-generated method stub
						for (int position : reverseSortedPositions) {

							// notificationlist.remove(position);
							
							notificationlist.get(position).setSwipe("true");

						}
						
						notificationsAdapter.notifyDataSetChanged();
					}

				});
		
		listview_notification.setOnTouchListener(touchListener);

		relative_button_settings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(),
						CustomerSettingsActivity.class);
				startActivity(i);
				getActivity().finish();
			}
		});

	}

	public void markasRead(String notificationId,int pos) {
		notificationid = notificationId;
		markasreadpos = pos;
		
		
		NotificationManager.getInstance().updateNotification(getActivity(),
				updateNotificationCallBack, notificationid, requestcode);
		
	}

	private void initManagers() {
		// TODO Auto-generated method stub
		// notificationCallBack = new NotificationCallBack();
		updateNotificationCallBack = new UpdateNotificationCallBack();
		notificationCallBack= new NotificationCallBack();
		
		notificationsAdapter = new NotificationsAdapter(
			
				ActionBarNotificationListFragment.this,notificationlist
				);
		           listview_notification
				.setAdapter(notificationsAdapter);
		           notificationsAdapter.notifyDataSetChanged();
		           
		           notificationListItemCallBack = new  NotificationListItemCallBack();

	}

	private void initViews() {
		// TODO Auto-generated method stub
		profile_pic_circular = (CircularImageView) container
				.findViewById(R.id.imgAvatar);
		listview_notification = (ListView) container
				.findViewById(R.id.listview_notification);
		relative_button_login = (LinearLayout) container
				.findViewById(R.id.relative_button_login);
		relative_button_settings = (LinearLayout) container
				.findViewById(R.id.relative_button_settings);
		slider_row_username = (TextView) container
				.findViewById(R.id.slider_row_username);
		topBanner = LayoutInflater.from(getActivity()).inflate(
				R.layout.slidebar_row, null);
		dismissableContainer = (ViewGroup) container
				.findViewById(R.id.listcontainer);
		notificationcount = (TextView) container
				.findViewById(R.id.notificationcount);
		loading_completed=(RelativeLayout) container
				.findViewById(R.id.notification_loading_more);

	}

	public class UpdateNotificationCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (responseCode == requestcode) {
				/*BaseActivity.getnotificationSize(--count);
				notificationcount.setText(String.valueOf(count));*/
				/*Toast.makeText(getActivity(),
						"Notification Status Updated Successfully.",
						Toast.LENGTH_LONG).show();*/
				int val=notificationlist.get(0)
						.getRecieverUser().getUnreadNotifications();
				int newVal=val-1;
				notificationlist.get(0)
				.getRecieverUser().setUnreadNotifications(newVal);
				EasyHomeFixApp.setCount(newVal+"");
				notificationcount.setText(String.valueOf(newVal));
				((BaseActivity)getActivity()).getnotificationSize(newVal);
				notificationlist.get(markasreadpos).setSwipe("false");
				notificationsAdapter.notifyDataSetChanged();
			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			

		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub

		}

	}

	public class NotificationCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (responseCode == requestcode) {
				
				Log.e("","in notification call back");
				EmailSignupBaseHolder notificationBaseHolder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(notificationBaseHolder)) {
					if (notificationBaseHolder.isSuccess()) {
						if (UtilValidate.isNotNull(notificationBaseHolder
								.getData())) {
							if (UtilValidate.isNotNull(notificationBaseHolder
									.getData().getJobNotifications())) {
								if (UtilValidate
										.isNotEmpty(notificationBaseHolder
												.getData()
												.getJobNotifications())) {
									
									
									if(offset!=0)
									{
										notificationlist.addAll( notificationBaseHolder
												.getData().getJobNotifications());
									}
									else
									{	notificationlist.clear();
										notificationlist=notificationBaseHolder
												.getData().getJobNotifications();
										Log.e("","notification list size"+notificationlist.size());
									}

								

									if(notificationsAdapter==null)
									{
										
											/*notificationcount.setText(String
													.valueOf(notificationlist.size()));*/
											notificationsAdapter = new NotificationsAdapter(
												
													ActionBarNotificationListFragment.this,
													notificationlist);
											listview_notification
													.setAdapter(notificationsAdapter);
											
											if(notificationlist.size()>0)
											{
												if (notificationlist.get(0).getRecieverUser() != null) {
													if (notificationlist.get(0).getRecieverUser().getUnreadNotifications() != 0) {
													notificationcount
															.setText(String
																	.valueOf(notificationlist.get(0)
																			.getRecieverUser()
																			.getUnreadNotifications()));
													/*BaseActivity.getnotificationSize(notificationlist.get(0)
															.getRecieverUser().getUnreadNotifications());*/
													 //  BaseActivity baseActivity=new BaseActivity();
										        	  // baseActivity.getnotificationSize(notificationlist.get(0).getRecieverUser().getUnreadNotifications());
													
													EasyHomeFixApp.setCount(0+"");
													EasyHomeFixApp.setCount(notificationlist.get(0)
															.getRecieverUser().getUnreadNotifications()+"");
													
													
													//BaseActivity.getnotificationSize(Integer.parseInt(EasyHomeFixApp.getCount()));
											
													count = notificationlist.get(0).getRecieverUser()
															.getUnreadNotifications();
													((BaseActivity)getActivity()).getnotificationSize(notificationlist.get(0).getRecieverUser()
															.getUnreadNotifications());
												}
												}
									}
											notificationsAdapter.notifyDataSetChanged();
									}
									
									else
										if(notificationsAdapter.getCount()<=10)
										{
											notificationcount.setText(String
													.valueOf(notificationlist.size()));
											
											if(notificationlist!=null&&(!notificationlist.isEmpty()))
											{
											notificationsAdapter = new NotificationsAdapter(
												
													ActionBarNotificationListFragment.this,
													notificationlist);
											listview_notification
													.setAdapter(notificationsAdapter);
											}
											if(notificationlist.size()>0)
											{
												if (notificationlist.get(0).getRecieverUser() != null) {
													if (notificationlist.get(0).getRecieverUser().getUnreadNotifications() != 0) {
													notificationcount
															.setText(String
																	.valueOf(notificationlist.get(0)
																			.getRecieverUser()
																			.getUnreadNotifications()));
												/*	BaseActivity.getnotificationSize(notificationlist.get(0)
															.getRecieverUser().getUnreadNotifications());*/
													count = notificationlist.get(0).getRecieverUser()
															.getUnreadNotifications();
													
													EasyHomeFixApp.setCount(0+"");
													EasyHomeFixApp.setCount(notificationlist.get(0)
															.getRecieverUser().getUnreadNotifications()+"");
													try{
													((BaseActivity)getActivity()).getnotificationSize(notificationlist.get(0).getRecieverUser()
															.getUnreadNotifications());
													}catch(Exception e){
														Log.e("ERROR", "Crash",e);
													}
													
												}
												}
												notificationsAdapter.notifyDataSetChanged();
									
									}
										}
									else
									{
										Log.e("","notification list size in else"+notificationlist.size());
									
										if(notificationlist.size()>0)
										{
											if (notificationlist.get(0).getRecieverUser() != null) {
												if (notificationlist.get(0).getRecieverUser().getUnreadNotifications() != 0) {
												notificationcount
														.setText(String
																.valueOf(notificationlist.get(0)
																		.getRecieverUser()
																		.getUnreadNotifications()));
												/*BaseActivity.getnotificationSize(notificationlist.get(0)
														.getRecieverUser().getUnreadNotifications());*/
												count = notificationlist.get(0).getRecieverUser()
														.getUnreadNotifications();
												
												EasyHomeFixApp.setCount(0+"");
												EasyHomeFixApp.setCount(notificationlist.get(0)
														.getRecieverUser().getUnreadNotifications()+"");
												
												((BaseActivity)getActivity()).getnotificationSize(notificationlist.get(0).getRecieverUser()
														.getUnreadNotifications());
												
											}
											}
											notificationsAdapter.notifyDataSetChanged();
								
								}
									}
								
									
									}
									
								
								
								
								else
								{
									if(notificationlist.size()>0)
									{
									
									if(notificationsAdapter==null)
									{
										notificationcount.setText(String
												.valueOf(notificationlist.size()));
										notificationsAdapter = new NotificationsAdapter(
												
												ActionBarNotificationListFragment.this,
												notificationlist);
										listview_notification
												.setAdapter(notificationsAdapter);
										notificationsAdapter.notifyDataSetChanged();
										
									}
									else
									{
										notificationsAdapter.notifyDataSetChanged();
									}
									
									
									if(notificationlist.size()>0)
									{
										if (notificationlist.get(0).getRecieverUser() != null) {
											if (notificationlist.get(0).getRecieverUser().getUnreadNotifications() != 0) {
											notificationcount
													.setText(String
															.valueOf(notificationlist.get(0)
																	.getRecieverUser()
																	.getUnreadNotifications()));
											/*BaseActivity.getnotificationSize(notificationlist.get(0)
													.getRecieverUser().getUnreadNotifications());*/
											
											EasyHomeFixApp.setCount(0+"");
											EasyHomeFixApp.setCount(notificationlist.get(0)
													.getRecieverUser().getUnreadNotifications()+"");
											
											count = notificationlist.get(0).getRecieverUser()
													.getUnreadNotifications();
											}
											
										}
									}
									
									}
									

							}
						}

						else {
							notificationcount.setText("0");
						}

					}
						
						loading_completed.setVisibility(View.GONE);
						listview_notification.setOnScrollListener(new NotificationOnScroll());
						
				}
					
				}
			}
			

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
		/*	if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(getActivity(), "Please check your intenet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(getActivity(), result, "ERROR");
			}*/

		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub

		}

	}
	
	public class NotificationOnScroll implements OnScrollListener{

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			
			currentScrollState = scrollState;
	        isScrollCompleted(currentFirstVisibleItem,currentVisibleItemCount,currenttotalItemCount);
		}

		

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			
			   currentFirstVisibleItem = firstVisibleItem;
		        currentVisibleItemCount = visibleItemCount;
		        currenttotalItemCount = totalItemCount;
			
		}
		private void isScrollCompleted(int currentFirstVisibleItem,
				int currentVisibleItemCount, int currenttotalItemCount) {
			// TODO Auto-generated method stub
			    
			  if (currentVisibleItemCount > 0 && currentScrollState == SCROLL_STATE_IDLE){
				  
				  loading_completed.setVisibility(View.VISIBLE);
				 
				  if (currenttotalItemCount > mPreviousTotal) {
					  
					  isLoading = false;
		                mPreviousTotal = currenttotalItemCount;
		                mCurrentPage++;
		                
		                if (mCurrentPage + 1 > 50) {
		                    mLastPage = true;
		                }
				  
			  }
				    if (!mLastPage && !isLoading && 
			                (currenttotalItemCount - currentVisibleItemCount) <= (currentFirstVisibleItem + mVisibleThreshold)) {
				    	limit=10;
						offset=notificationlist.size();
						Log.e("","offset>>>>>.."+offset);
						if(offset%10==0){
							
							
							
							NotificationManager.getInstance().getNotificationList(
									getActivity(),limit,offset, notificationCallBack, requestcode);
							
						}
			        	
			        	
			        	isLoading = true;
			        }
			        else
			        {
			        	loading_completed.setVisibility(View.GONE);
			        }
			
			
				  
			  }
			
		}
		
	}
	
	public class NotificationListItemCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			EmailSignupBaseHolder notificationListitemObject = (EmailSignupBaseHolder)result;
			if(notificationListitemObject!=null){
				if(notificationListitemObject.getData() != null){
					if(notificationListitemObject.getData().getJob() != null){
						NotificationJobObject = notificationListitemObject.getData().getJob();
						if(NotificationJobObject.getQuote() == null){
							NotificationJobObject.setQuote(notificationlist.get(position).getQuote());
						}
						//Notification Click to appropriate page
						if(notificationlist.get(position)!= null){
							
							if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Message recieved"))
									
							{
								
							
								Log.d("","INSIDE CHAT" );
								if(notificationlist.get(position).getQuote() != null){
									if(notificationlist.get(position).getQuote().getQuoteAmount() != 0){
										Log.e("","innnnnnnnnnnnnnnnnnnnnnnn"+(notificationlist.get(position).getJob().getJobStatus()));
										
										if(notificationlist.get(position).getJob().getJobStatus().equalsIgnoreCase("Pending"))
										{
											EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION_PENDING);
											
										}
										if(notificationlist.get(position).getJob().getJobStatus().equalsIgnoreCase("Ongoing"))
										{
											EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION_ONGOING);
											
										}
										if(notificationlist.get(position).getJob().getJobStatus().equalsIgnoreCase("Completed")&&
												notificationListitemObject.getData().getJob().getPayment()==null)
										{
											EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);
											
										}
										if(notificationlist.get(position).getJob().getJobStatus().equalsIgnoreCase("Completed")&&
												notificationListitemObject.getData().getJob().getPayment()!=null)
										{
											EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE_PAID);
											
										}
										if(notificationlist.get(position).getJob().getJobStatus().equalsIgnoreCase("Cancelled"))
												
										{
											EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELLED);
											
										}
										//EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CHAT);
										EasyHomeFixApp.setCategoryNameDefaultTab(Constants.CHAT);
										Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
										Qute.putExtra("notification_chat", NotificationJobObject);
										getActivity().startActivity(Qute);
									}else{
										/*Intent EmptyQute = new Intent(getActivity(),EmptyFixQuoteActivity.class);
										EmptyQute.putExtra("notification_quote", notificationlist.get(position));
										getActivity().startActivity(EmptyQute);*/
										EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION_PENDING);
										EasyHomeFixApp.setCategoryNameDefaultTab(Constants.CHAT);
										Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
										Qute.putExtra("notification_chat", NotificationJobObject);
										getActivity().startActivity(Qute);
									}
								}else{
									Intent EmptyQute = new Intent(getActivity(),EmptyFixQuoteActivity.class);
									EmptyQute.putExtra("notification_quote", notificationlist.get(position));
									getActivity().startActivity(EmptyQute);
								}
							}
							
							else
								
							{
								//FIX QUOTE PAGE BEFORE ACCEPTING THE QUOTE FROM PENDING
								
								if(notificationlist.get(position).getJob()!=null){
									if(notificationlist.get(position).getJob().getJobStatus().equalsIgnoreCase("Pending")){
										
										if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job time out"))
										{
											 EasyHomeFixApp
												.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELLED);
											     EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
												Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
												Qute.putExtra("From_notification_pending", NotificationJobObject);
												getActivity().startActivity(Qute); 
											
										}
										
										else if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Quote recieved")&&
												notificationlist.get(position).getQuote().getStatus().equalsIgnoreCase("3"))
										{
											 EasyHomeFixApp
												.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELLED);
											     EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
												Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
												Qute.putExtra("From_notification_pending", NotificationJobObject);
												getActivity().startActivity(Qute); 
											
										}
										else
										{
											
											Intent EmptyQute = new Intent(getActivity(),EmptyFixQuoteActivity.class);
											EmptyQute.putExtra("notification_quote", notificationlist.get(position));
											EmptyQute.putExtra("From_notification_pending", NotificationJobObject);
											getActivity().startActivity(EmptyQute);
										}
								
								
										
								}else if(notificationlist.get(position).getJob().getJobStatus().equalsIgnoreCase("Ongoing")){
									
									
									//SHOWING DETAIL PAGE IN  ONGOING
									Log.d("","INSIDE DETAILS" );
									
									if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Quote recieved")){
										Log.d("","INSIDE DETAILS" );
										
										AlertDialog.Builder alt_bld = new AlertDialog.Builder(getActivity());  
										alt_bld.setMessage("You have already accepted a quote for this job. Do you want to view that quote instead?");
										alt_bld.setCancelable(false);
										alt_bld.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,int id) {
												// if this button is clicked, close
												// current activity
												
												//DETAIL PAGE
												notificationlist.get(position).setSwipe("true");
												notificationsAdapter.notifyDataSetChanged();
												EasyHomeFixApp
												.setDetailsTabStatus(Constants.FROM_NOTIFICATION_ONGOING);
												EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
												Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
												Qute.putExtra("From_notification_ongoing", NotificationJobObject);
												getActivity().startActivity(Qute);
												dialog.cancel();
											}
										  });
										alt_bld.setNegativeButton("No",new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,int id) {
												// if this button is clicked, just close
												// the dialog box and do nothing
												dialog.cancel();
											}
										});
										alt_bld.show();
										
									}
									if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Quote Edited"))
									{
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_ONGOING);
										EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
										Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
										Qute.putExtra("From_notification_ongoing", NotificationJobObject);
										getActivity().startActivity(Qute);
										
									}
									
									 if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job reminder in an hour")||notificationlist.get(position).getNotifyType().equalsIgnoreCase
											("Job reminder on the day"))
									{
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_ONGOING);
										EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
										Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
										Qute.putExtra("From_notification_ongoing", NotificationJobObject);
										getActivity().startActivity(Qute);
										
									}
									 if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job time out"))
										{
											 EasyHomeFixApp
												.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELLED);
											     EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
												Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
												Qute.putExtra("From_notification_ongoing", NotificationJobObject);
												getActivity().startActivity(Qute); 
											
										}
									 
									
								}
									else if(notificationlist.get(position).getJob().getJobStatus().equalsIgnoreCase("Completed")){
										Log.e("","insideeeeeeeeeeeeeeeeeee");
										
										if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("2"))
										{
											
										Log.e("","detail>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+NotificationJobObject.getQuote().getStatus());
										//SHOWING DETAIL PAGE IN  ONGOING
										Log.d("","INSIDE DETAILS" );
										if((notificationlist.get(position).getNotifyType().equalsIgnoreCase("Quote recieved"))||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Quote Edited")){
											
											Log.d("","INSIDE DETAILS" );
											
											AlertDialog.Builder alt_bld = new AlertDialog.Builder(getActivity());  
											alt_bld.setMessage("You have already accepted a quote for this job. Do you want to view that quote instead?");
											alt_bld.setCancelable(false);
											alt_bld.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,int id) {
													// if this button is clicked, close
													// current activity
													
													//DETAIL PAGE
													
													
													if(UtilValidate.isNull(NotificationJobObject.getPayment()))
														{
														EasyHomeFixApp
														.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);
														}
													if(NotificationJobObject.getPayment()!=null)
														{
														EasyHomeFixApp
														.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE_PAID);
														
														}
													
													EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
													Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
													Qute.putExtra("notification_completed", NotificationJobObject);
													getActivity().startActivity(Qute);
													dialog.cancel();
													//getActivity().finish();
												}
											  });
											alt_bld.setNegativeButton("No",new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,int id) {
													// if this button is clicked, just close
													// the dialog box and do nothing
													dialog.cancel();
												}
											});
											alt_bld.show();
											
										}  else if( notificationlist.get(position).getNotifyType().equalsIgnoreCase("Review Reminder")
												|| notificationlist.get(position).getNotifyType().equalsIgnoreCase("Review Requested")||(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Review uncompleted"))){
							               // if( notification.jobDetails.fixerReview == nil ){
											if(NotificationJobObject.getJobReview()==null)
											{
												Intent reviewIntent = new Intent(getActivity(),ReviewServiceActivity.class);
												reviewIntent.putExtra("jobid", NotificationJobObject.getId());
												getActivity().startActivity(reviewIntent);
											}
											if(NotificationJobObject.getJobReview()!=null)
											{
												
												EasyHomeFixApp
												.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE_PAID);
												EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
												Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
												Qute.putExtra("notification_completed", NotificationJobObject);
												getActivity().startActivity(Qute);
											}
											
												
							                }
										else if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Payment reminder 1day")||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Payment request reminder 2hrs")||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Payment request reminder 6hrs")
												||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Payment request reminder 1day")||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Payment reminder 2hrs")||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Payment reminder 6hrs")){
											
											/*EasyHomeFixApp
											.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);*/
											if(UtilValidate.isNull(NotificationJobObject.getPayment()))
											{
											EasyHomeFixApp
											.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);
											}
											if(NotificationJobObject.getPayment()!=null)
											{
											EasyHomeFixApp
											.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE_PAID);
											
											
											}
											EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
											Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
											Qute.putExtra("notification_completed", NotificationJobObject);
											getActivity().startActivity(Qute);
											
										}
										
										else if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job reminder in an hour")||notificationlist.get(position).getNotifyType().equalsIgnoreCase
												("Job reminder on the day")||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Payment requested")
										||(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job reminder in an hour"))|(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Cash accepted"))){
											
											/*EasyHomeFixApp
											.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);*/
											if(UtilValidate.isNull(NotificationJobObject.getPayment()))
											{
											EasyHomeFixApp
											.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);
											}
											if(NotificationJobObject.getPayment()!=null)
											{
											EasyHomeFixApp
											.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE_PAID);
											
											}
											EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
											Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
											Qute.putExtra("notification_completed", NotificationJobObject);
											getActivity().startActivity(Qute);
										}
										
										else{
											
											Intent FixQute = new Intent(getActivity(),EmptyFixQuoteActivity.class);
											FixQute.putExtra("From_notification_completed", NotificationJobObject);
											FixQute.putExtra("notification_quote", notificationlist.get(position));
											getActivity().startActivity(FixQute);
											
										}
										
										
									
								}
										else if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("3"))
										{
											Log.e("","insideeeeeeeeeeeeeeeeeeefddddddddddddd");
											 EasyHomeFixApp
												.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELLED);
											     EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
												Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
												Qute.putExtra("notification_completed", NotificationJobObject);
												getActivity().startActivity(Qute); 
										}
								}
									/*cancelled case*/
									
									else
									{
										 if(notificationlist.get(position).getJob().getJobStatus().equalsIgnoreCase("Cancelled"))
										 {
											 if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job timed out")&&notificationlist.get(position).getQuote()==null)
											 {
												 Toast.makeText(getActivity(), "Job cancelled.", Toast.LENGTH_LONG).show();
												 
											 }
											 else
											 {
												 EasyHomeFixApp
													.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELLED);
												     EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
													Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
													Qute.putExtra("notification_completed", NotificationJobObject);
													getActivity().startActivity(Qute); 
											 }
											 
											 
										 }
								}
								}
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
			Utils.showDialog(getActivity(), "Please check your internet connection.", "ERROR");
		}

		@Override
		public void onFinish(int requestcode, JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
}