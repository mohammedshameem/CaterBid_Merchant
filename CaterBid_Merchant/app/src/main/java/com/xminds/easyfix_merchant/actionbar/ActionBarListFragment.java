package com.xminds.easyfix_merchant.actionbar;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.xminds.easyfix_merchant.constants.Constants;
import com.xminds.easyfix_merchant.swipetohide.SwipeDismissListViewTouchListener;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.Job;
import com.xminds.easyhomefix_merchant.Holder.UserTokenHolder;
import com.xminds.easyhomefix_merchant.Holder.jobNotifications;
import com.xminds.easyhomefix_merchant.Manager.NotificationManager;
import com.xminds.easyhomefix_merchant.activities.MerchantSettingsActivity;
import com.xminds.easyhomefix_merchant.activities.SignUpandLoginActivity;
import com.xminds.easyhomefix_merchant.activities.TrackFixFragmentTabhostActivity;
import com.xminds.easyhomefix_merchant.adapter.NotificationsAdapter;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.dao.UserDAO;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;

public class ActionBarListFragment extends Fragment {
	
	

	private List<jobNotifications> notificationlist = new ArrayList<jobNotifications>();
	private ListView listview_notification;
	private ViewGroup container;
	private LinearLayout relative_button_login;
	private CircularImageView profile_pic_circular;
	private LinearLayout relative_button_settings;
	private TextView Username;
	private String firstname;
	UserTokenHolder userTokenHolder;
	private String lastname;
	private int requestcode=0;
	private int notification;
	NotificationsAdapter notificationsAdapter;
	updateNotificationCallBack updatenotificationcallback;
	private TextView notificationcount;
	private int count = 0;
	NotificationCallBack notificationCallBack;
	private int limit=10;
	private int offset=0;
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
	private RelativeLayout loading;
	private NotificationListItemCallBack notificationListItemCallBack;
	private Job NotificationJobObject ;
	private int position;
	private int markasreadpos;
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (CurrentlyLoggedUserDAO.getInstance()
					.isAnyUserInCurrentlyLoggedUserTable())
			{
				
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
		
		Log.e("heder","headre"+CurrentlyLoggedUserDAO.getInstance().getHeader());
		
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
				if(UtilValidate.isNotNull(userTokenHolder.getUser().getProfileImage()))
				{
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
				else if(userTokenHolder.getUser().getProfileImage()==null)
				{
					Picasso.with(getActivity()).load(R.drawable.profile_image_null)
					.into(profile_pic_circular);
				}
				
			}
		}
		
		if(CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable())
		{
			limit=10;
			offset=0;
			NotificationManager.getInstance().getNotificationList(getActivity(),limit,offset, notificationCallBack, requestcode);
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
		/*if(CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable())
		{
			NotificationManager.getInstance().getNotificationList(getActivity(),limit,offset, notificationCallBack, requestcode);
			Log.e("","header"+CurrentlyLoggedUserDAO.getInstance().getHeader());
		}*/
		
		if(EasyHomeFixApp.getNotificationlist()!=null)
		{
		
		notificationlist=EasyHomeFixApp.getNotificationlist();
		notificationsAdapter = new NotificationsAdapter(
				getActivity(),
				ActionBarListFragment.this,notificationlist
				);
		 listview_notification
			.setAdapter(notificationsAdapter);
	           notificationsAdapter.notifyDataSetChanged();

	           if(UtilValidate.isNotEmpty(notificationlist))
	           {
	           if(notificationlist.get(0).getRecieverUser() != null){
   	        	   notificationcount.setText(String.valueOf(notificationlist.get(0).getRecieverUser().getUnreadNotifications()));
   	        	  EasyHomeFixApp.setNotificationcount(notificationlist.get(0).getRecieverUser().getUnreadNotifications()+"");
   	        	   //BaseActivity.getnotificationSize(notificationlist.get(0).getRecieverUser().getUnreadNotifications());
   	        	  EasyHomeFixApp.setNotificationcount(notificationlist.get(0).getRecieverUser().getUnreadNotifications()+"");
   	        	   count = notificationlist.get(0).getRecieverUser().getUnreadNotifications();
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
				if(UtilValidate.isNotNull(userTokenHolder.getUser().getProfileImage()))
				{
					/*Picasso.with(getActivity()).load(userTokenHolder.getUser().getProfileImage())
					.into(profile_pic_circular);*/

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
				else if(userTokenHolder.getUser().getProfileImage()==null)
				{
					Picasso.with(getActivity()).load(R.drawable.profile_image_null)
					.into(profile_pic_circular);
				}
			}
		}

		if (CurrentlyLoggedUserDAO.getInstance()
				.isAnyUserInCurrentlyLoggedUserTable()){
			Username.setText(firstname + " " + lastname);
		}
		

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

							notificationlist.get(position).setSwipe("true");

						}

						notificationsAdapter.notifyDataSetChanged();

					}

				});
		
		listview_notification.setOnTouchListener(touchListener);
	
		
		relative_button_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent i=new Intent(getActivity(),SignUpandLoginActivity.class);
			startActivity(i);
			getActivity().finish();
			}
		});
	
		
		relative_button_settings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getActivity(),MerchantSettingsActivity.class);
				startActivity(i);
				
				
				//getActivity().finish();
			
			}
		});
		
		listview_notification.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int pos, long id) {
				// TODO Auto-generated method stub
				
				
				position = pos;
				NotificationManager.getInstance().jobListItemObject(getActivity(),notificationlist.get(position).getJob().getId(),notificationListItemCallBack,requestcode);
				if(notificationlist.get(pos).getNotifyStatus().equalsIgnoreCase("Unread"))
					{
					NotificationManager.getInstance().updateNotification(getActivity(), updatenotificationcallback, notificationlist.get(pos).getId(), requestcode);
					}
		
			
			}
		});
		

		
	}

	private void initManagers() {
		// TODO Auto-generated method stub
		updatenotificationcallback=new updateNotificationCallBack();
		notificationlist=new ArrayList<jobNotifications>();
		notificationCallBack=new NotificationCallBack();
		notificationsAdapter = new NotificationsAdapter(
				getActivity(),
				ActionBarListFragment.this,notificationlist
				);
		 listview_notification
			.setAdapter(notificationsAdapter);
	           notificationsAdapter.notifyDataSetChanged();
	           notificationListItemCallBack = new  NotificationListItemCallBack();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		profile_pic_circular=(CircularImageView)container.findViewById(R.id.imgAvatar);
		listview_notification=(ListView)container.findViewById(R.id.listview_notification);
		relative_button_login=(LinearLayout)container.findViewById(R.id.relative_button_login);
		relative_button_settings=(LinearLayout)container.findViewById(R.id.relative_button_settings);
		Username=(TextView)container.findViewById(R.id.slider_row_username);
		notificationcount = (TextView) container
				.findViewById(R.id.notificationcount);
		loading=(RelativeLayout)container.findViewById(R.id.notification_loading_more);
	}
	
	public  void updateNotification(String notificationid,int pos)
	{
		String nid=notificationid;
		markasreadpos = pos;
		NotificationManager.getInstance().updateNotification(getActivity(), updatenotificationcallback, nid, requestcode);
		
		
	}
	
	
	public class updateNotificationCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			
			// TODO Auto-generated method stub
			if (responseCode == requestcode)
			{
				
				int val=notificationlist.get(0)
						.getRecieverUser().getUnreadNotifications();
				
				int newVal=val-1;
				notificationlist.get(0)
				.getRecieverUser().setUnreadNotifications(newVal);
				EasyHomeFixApp.setNotificationcount(newVal+"");
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
		
	}
	

	public class NotificationCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			
			if (responseCode == requestcode) {
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
										notificationlist.addAll(notificationBaseHolder
												.getData().getJobNotifications());
										
									}
									else
									{	notificationlist.clear();
										notificationlist=notificationBaseHolder
												.getData().getJobNotifications();
									}
									
									if(notificationsAdapter==null)
									{
										notificationsAdapter=new NotificationsAdapter(getActivity(), ActionBarListFragment.this, notificationlist);
										listview_notification.setAdapter(notificationsAdapter);
										
										 if(UtilValidate.isNotEmpty(notificationlist))
								           {
								        	   if(notificationlist.get(0).getRecieverUser() != null){
								   	        	   notificationcount.setText(String.valueOf(notificationlist.get(0).getRecieverUser().getUnreadNotifications()));
								   	        	  EasyHomeFixApp.setNotificationcount(notificationlist.get(0).getRecieverUser().getUnreadNotifications()+"");
								   	        	//   BaseActivity.getnotificationSize(notificationlist.get(0).getRecieverUser().getUnreadNotifications());
								   	        	   
								   	        	   count = notificationlist.get(0).getRecieverUser().getUnreadNotifications();
								   	        	((BaseActivity)getActivity()).getnotificationSize(notificationlist.get(0).getRecieverUser()
														.getUnreadNotifications());
								   	           }
								           }
										 notificationsAdapter.notifyDataSetChanged();
										
									}
									else
										if(notificationsAdapter.getCount()<=10)
										{
											notificationsAdapter=new NotificationsAdapter(getActivity(), ActionBarListFragment.this, notificationlist);
											listview_notification.setAdapter(notificationsAdapter);
											
											 if(UtilValidate.isNotEmpty(notificationlist))
									           {
									        	   if(notificationlist.get(0).getRecieverUser() != null)
									        	   {
									   	        	   notificationcount.setText(String.valueOf(notificationlist.get(0).getRecieverUser().getUnreadNotifications()));
									   	        	//   BaseActivity.getnotificationSize(notificationlist.get(0).getRecieverUser().getUnreadNotifications());
									   	        	  EasyHomeFixApp.setNotificationcount(notificationlist.get(0).getRecieverUser().getUnreadNotifications()+"");
									   	        	   count = notificationlist.get(0).getRecieverUser().getUnreadNotifications();
									   	        	   try
									   	        	   {
									   	        		((BaseActivity)getActivity()).getnotificationSize(notificationlist.get(0).getRecieverUser()
																.getUnreadNotifications());
									   	        	   }
									   	        	   catch(Exception e)
									   	        	   {
									   	        		   Log.e("","error>>>>>>..");
									   	        	   }
									   	        	
									   	           }
									           }
											 notificationsAdapter.notifyDataSetChanged();
										}
									
									else
									{
										notificationsAdapter.notifyDataSetChanged();

							        	   if(UtilValidate.isNotEmpty(notificationlist))
								           {
								        	   if(notificationlist.get(0).getRecieverUser() != null){
								   	        	   notificationcount.setText(String.valueOf(notificationlist.get(0).getRecieverUser().getUnreadNotifications()));
								   	        	  // BaseActivity.getnotificationSize(notificationlist.get(0).getRecieverUser().getUnreadNotifications());
								   	        	  EasyHomeFixApp.setNotificationcount(notificationlist.get(0).getRecieverUser().getUnreadNotifications()+"");
								   	        	   count = notificationlist.get(0).getRecieverUser().getUnreadNotifications();
								   	        	((BaseActivity)getActivity()).getnotificationSize(notificationlist.get(0).getRecieverUser()
														.getUnreadNotifications());
								   	           }
								           }
									}
									
									
								}
								
								if(notificationlist.size()>0)
								{
									
									if(notificationsAdapter==null)
									{
										notificationsAdapter=new NotificationsAdapter(getActivity(), ActionBarListFragment.this, notificationlist);
										listview_notification.setAdapter(notificationsAdapter);
										

							        	   if(UtilValidate.isNotEmpty(notificationlist))
								           {
								        	   notificationsAdapter.notifyDataSetChanged();
									}
									else
									{
									

							        	   if(UtilValidate.isNotEmpty(notificationlist))
								           {
								        	  
								        		notificationsAdapter.notifyDataSetChanged();
									}
								}

						        	   if(notificationlist.get(0).getRecieverUser() != null){
						   	        	   notificationcount.setText(String.valueOf(notificationlist.get(0).getRecieverUser().getUnreadNotifications()));
						   	        	  // BaseActivity.getnotificationSize(notificationlist.get(0).getRecieverUser().getUnreadNotifications());
						   	        	  EasyHomeFixApp.setNotificationcount(notificationlist.get(0).getRecieverUser().getUnreadNotifications()+"");
						   	        	   count = notificationlist.get(0).getRecieverUser().getUnreadNotifications();
						   	        	((BaseActivity)getActivity()).getnotificationSize(notificationlist.get(0).getRecieverUser()
												.getUnreadNotifications());
						   	           }
									}
						           }

							}
						}
						loading.setVisibility(View.GONE);
						listview_notification.setOnScrollListener(new NotificationOnScroll());
					}
				
				}
			}
			
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			
			Toast.makeText(getActivity(), "please check internet connection", Toast.LENGTH_LONG).show();
			
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
				  
				  loading.setVisibility(View.VISIBLE);
				 
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
						Log.e("","offset valyeee>>>>.."+offset);
						if(offset%10==0){
							
							NotificationManager.getInstance().getNotificationList(
									getActivity(),limit,offset, notificationCallBack, requestcode);
							
						}
						else
						{
							 loading.setVisibility(View.GONE);
						}
			        	
			        	
			        	isLoading = true;
			        }
			        else
			        {
			        	loading.setVisibility(View.GONE);
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
				if(notificationListitemObject.getData()!=null){
					if(notificationListitemObject.getData().getJob()!=null){
					
				NotificationJobObject = notificationListitemObject.getData().getJob();
				Log.d("","PENDING NOTIFICATION"+NotificationJobObject.getUserId().getFirstName());
				
				
				
				
				
				//CHAT DETAIL PAGE
				Log.d("","INSIDE NOTIFICATION LIST" );
				
				if(notificationlist.get(position)!= null){
					
					
				if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Message recieved"))
						
				{
					//EasyHomeFixApp.setIsfromnotificationChat(Constants.FROM_NOTIFICATION_CHAT);
					if(NotificationJobObject.getJobStatus().equalsIgnoreCase("pending"))
					{
						EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION_PENDING);
						
					}else if(NotificationJobObject.getJobStatus().equalsIgnoreCase("ongoing"))
					{
						EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION_ONGOING);
					}else if(NotificationJobObject.getJobStatus().equalsIgnoreCase("completed"))
					{
						if(NotificationJobObject.getPayment()==null)
						{
							EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);
						}
						else if(NotificationJobObject.getPayment()!=null)
						{
							EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE_PAID);
							
						}
					}else if(NotificationJobObject.getJobStatus().equalsIgnoreCase("cancelled"))
					{
						
						EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
					}
					
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
							
							if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Fix created")){
								if(NotificationJobObject.getQuote()!=null)
								{
									if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("2"))
									{
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_PENDING);
									}
									else if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("3"))
									{
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
									}
									
								}
								else
								{
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_PENDING);
								}
								
								
								
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent EmptyQute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								EmptyQute.putExtra("From_notification_pending", NotificationJobObject);
								//Log.d("", "PENDING NOTIFICATION"+NotificationJobObject.getUserId().getFirstName());
								getActivity().startActivity(EmptyQute);
					    }
							
							else if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Fix edited"))
							{
								EasyHomeFixApp
								.setDetailsTabStatus(Constants.FROM_NOTIFICATION_PENDING);
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.CHAT);
								Intent EmptyQute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								EmptyQute.putExtra("From_notification_pending", NotificationJobObject);
								getActivity().startActivity(EmptyQute);
								
								
							}
							
							else if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Quote rejected")||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job timed out"))
							{
								EasyHomeFixApp
								.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent EmptyQute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								EmptyQute.putExtra("From_notification_pending", NotificationJobObject);
								getActivity().startActivity(EmptyQute);
								
							}
							
							else{
							EasyHomeFixApp
							.setDetailsTabStatus(Constants.FROM_NOTIFICATION_PENDING);
							EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
							Intent EmptyQute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
							EmptyQute.putExtra("From_notification_pending", NotificationJobObject);
							getActivity().startActivity(EmptyQute);
						}
							
							
							
						
					}
						else if(notificationlist.get(position).getJob().getJobStatus().equalsIgnoreCase("Ongoing"))
						{
							if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Quote accepted")||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Fix created"))
							{
								if(NotificationJobObject.getQuote()!=null)
								{
									Log.e("","is not nullllllll");
									if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("2"))
									{
										Log.e("","is fg,,,,,,,,,,,,,,,,,");
									
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_ONGOING);
									}
									if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("3"))
									{
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
									}
								}
								else
								{
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								}
								
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								Qute.putExtra("notification_ongoing", NotificationJobObject);
								getActivity().startActivity(Qute);
								
							}
							if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job timed out")||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Quote rejected"))
							{
								EasyHomeFixApp
								.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								Qute.putExtra("notification_ongoing", NotificationJobObject);
								getActivity().startActivity(Qute);
								
							}
							if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job reminder in an hour")||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job reminder on the day"))
							{
								if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("2"))
								{
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_ONGOING);
									
								}
								else if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("3"))
								{
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
									
								}
								
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								Qute.putExtra("notification_ongoing", NotificationJobObject);
								getActivity().startActivity(Qute);
								
							}
							if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Fix edited"))
							{
								EasyHomeFixApp
								.setDetailsTabStatus(Constants.FROM_NOTIFICATION_ONGOING);
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.CHAT);
								Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								Qute.putExtra("notification_ongoing", NotificationJobObject);
								getActivity().startActivity(Qute);
							}
							
							
							
						}
						
						else if(notificationlist.get(position).getJob().getJobStatus().equalsIgnoreCase("Completed")){
							
							//SHOWING DETAIL PAGE IN  ONGOING 
							Log.d("","INSIDE DETAILS" );
							if((notificationlist.get(position).getNotifyType().equalsIgnoreCase("Quote recieved"))||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Quote Edited")
									){
								if(NotificationJobObject.getQuote()!=null)
								{
								
								if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("2"))
								{
									if(UtilValidate.isNull(NotificationJobObject.getPayment()))
									{
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);
									}
									if(UtilValidate.isNotNull(NotificationJobObject.getPayment()))
									{
										
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE_PAID);
									}
									
								}
								
								if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("3"))
								{
									
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								}
								}
								else
								{
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								
								}
								
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								Qute.putExtra("notification_completed", NotificationJobObject);
								getActivity().startActivity(Qute);	
								
								
							
							
							}  else if( notificationlist.get(position).getNotifyType().equalsIgnoreCase("Fix completed")){
								
								if(NotificationJobObject.getQuote()!=null)
								{
								
								if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("2"))
								{
									if(UtilValidate.isNull(NotificationJobObject.getPayment()))
									{
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);
									}
									if(UtilValidate.isNotNull(NotificationJobObject.getPayment()))
									{
										
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE_PAID);
									}
								}
								if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("3"))
								{
									
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								}
								}
								else
								{
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								
								}
								
								
								
								
								/*EasyHomeFixApp
								.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);*/
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								Qute.putExtra("notification_completed", NotificationJobObject);
								getActivity().startActivity(Qute);
								
							
						}
							
							else if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job reminder in an hour")||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job reminder on the day")){
								
								if(NotificationJobObject.getQuote()!=null)
								{
								if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("2"))
								{
									if(UtilValidate.isNull(NotificationJobObject.getPayment()))
									{
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);
									}
									if(UtilValidate.isNotNull(NotificationJobObject.getPayment()))
									{
										
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE_PAID);
									}
								}
								if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("3"))
								{
									
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								}
								}
								else
								{
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								
								}
								
								/*EasyHomeFixApp
								.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);*/
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								Qute.putExtra("notification_completed", NotificationJobObject);
								getActivity().startActivity(Qute);
							
							}
							else if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job paid")){
								
								if(NotificationJobObject.getQuote()!=null)
								{								
								if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("2"))
								{
									if(UtilValidate.isNull(NotificationJobObject.getPayment()))
									{
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);
									}
									if(UtilValidate.isNotNull(NotificationJobObject.getPayment()))
									{
										
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE_PAID);
									}
								}
								if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("3")||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Quote rejected"))
								{
									
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								}
								
								}
								else
								{
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								
								}
								
								
							/*	EasyHomeFixApp
								.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE_PAID);*/
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								Qute.putExtra("notification_completed", NotificationJobObject);
								getActivity().startActivity(Qute);
							
						}
							
							else{
								if(NotificationJobObject.getQuote()!=null)
								{
									if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("2"))
									{
										if(UtilValidate.isNull(NotificationJobObject.getPayment()))
										{
											EasyHomeFixApp
											.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);
										}
										if(UtilValidate.isNotNull(NotificationJobObject.getPayment()))
										{
											EasyHomeFixApp
											.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE_PAID);
										}
									}
									if(NotificationJobObject.getQuote().getStatus().equalsIgnoreCase("3"))
									{
										
										EasyHomeFixApp
										.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
									}
								
								}
								else
								{
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								
								}
								
								/*EasyHomeFixApp
								.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);*/
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent FixQute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								FixQute.putExtra("notification_completed", NotificationJobObject);
								getActivity().startActivity(FixQute);
								
							}
							
							if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Fix edited"))
							{
								if(UtilValidate.isNull(NotificationJobObject.getPayment()))
								{
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE);
								}
								if(UtilValidate.isNotNull(NotificationJobObject.getPayment()))
								{
									EasyHomeFixApp
									.setDetailsTabStatus(Constants.FROM_NOTIFICATION_COMPLETE_PAID);
								}
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.CHAT);
								Intent FixQute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								FixQute.putExtra("notification_completed", NotificationJobObject);
								getActivity().startActivity(FixQute);
							
								
							}
							
					}
						//cancelled case
						
						else if(notificationlist.get(position).getJob().getJobStatus().equalsIgnoreCase("Cancelled")){

							
							
							//SHOWING DETAIL PAGE IN  ONGOING
							Log.d("","INSIDE DETAILS" );
							
							if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Quote accepted")){
								
								EasyHomeFixApp
								.setDetailsTabStatus(Constants.FROM_NOTIFICATION_ONGOING);
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								Qute.putExtra("notification_cancelled", NotificationJobObject);
								getActivity().startActivity(Qute);
							
							}
							else if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job timed out"))
							{
								EasyHomeFixApp
								.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								Qute.putExtra("notification_cancelled", NotificationJobObject);
								getActivity().startActivity(Qute);
								
							}
							else if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job reminder in an hour")||notificationlist.get(position).getNotifyType().equalsIgnoreCase("Job reminder on the day")){
								
								EasyHomeFixApp
								.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								Qute.putExtra("notification_cancelled", NotificationJobObject);
								getActivity().startActivity(Qute);
							}else if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Fix created")){
								
								EasyHomeFixApp
								.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								Qute.putExtra("notification_cancelled", NotificationJobObject);
								getActivity().startActivity(Qute);
							}
							else if(notificationlist.get(position).getNotifyType().equalsIgnoreCase("Fix cancelled")){
								
								EasyHomeFixApp
								.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
								EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
								Intent Qute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
								Qute.putExtra("notification_cancelled", NotificationJobObject);
								getActivity().startActivity(Qute);
							}
							
							
							
						else{
							EasyHomeFixApp
							.setDetailsTabStatus(Constants.FROM_NOTIFICATION_CANCELL);
							EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
							Intent FixQute = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
							FixQute.putExtra("notification_cancelled", NotificationJobObject);
							getActivity().startActivity(FixQute);
						}
						}
						
						else{
						
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
