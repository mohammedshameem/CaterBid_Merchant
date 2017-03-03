package com.xminds.easyhomefix.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.JobList;
import com.xminds.easyhomefix.Holder.JobListStatusHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.Managers.JobListManager;
import com.xminds.easyhomefix.Managers.UpdateJobStatusManager;
import com.xminds.easyhomefix.activities.ReviewActivity;
import com.xminds.easyhomefix.activities.TrackFixFragmentTabhostActivity;
import com.xminds.easyhomefix.adapter.OngoingAdapter;
import com.xminds.easyhomefix.adapter.PendingAdapter;
import com.xminds.easyhomefix.adapter.ReviewAdapter;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.constants.Constants;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.fragment.CompletedCancelledTabTrackYourFixFragment.UpdateJobStatusCallBack;
import com.xminds.easyhomefix.fragment.PendingTabTrackYourFixFragment.pendingScroll;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class OnGoingTabTrackYourFixFragment extends Fragment {


	private View viewroot;
	private ListView ongoingListview;
	private List<JobList> ongoingList = new ArrayList<JobList>();
	private OngoingAdapter ongoingAdapter;
	private OnGoingCallBack ongoingCallBack;
	private int requestedCode = 0;
	private LinearLayout empty_ongoing_page;
	private String jobid;
	private List<String> CustomerImageList = new ArrayList<String>();
	private String quoteId;
	UpdateJobStatusCallBack updateJobStatusCallBack;
	private RelativeLayout loadingmore;
    private int currentFirstVisibleItem;
    private int currentVisibleItemCount;
    private int currentScrollState;
    private boolean isLoading = true;
    private int currenttotalItemCount;
    private int mPreviousTotal = 0;
    private boolean mLoading = true;
    private boolean mLastPage = false;
    private int mCurrentPage = 0;
    private int offset = 0;
	private int limit =10;
    private int mVisibleThreshold=5 ;
    private SwipeRefreshLayout swipetorefresh;
    private ProgressDialog progressDialog;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			JobListManager.getInstance().getOngoingList(getActivity(),offset,limit,
					ongoingCallBack, requestedCode);
		}
	};
    
	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}

	@Override
	public void onResume() {
		
		// for auto refresh
				getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("nofication_received"));
		
		limit=10;
		ongoingList.clear();
		offset=ongoingList.size();
	
		ongoingAdapter = new OngoingAdapter(getActivity(),OnGoingTabTrackYourFixFragment.this,ongoingList);
		ongoingListview.setAdapter(ongoingAdapter);
	    ongoingAdapter.notifyDataSetChanged();
		
	    mPreviousTotal=0;
		
		 if (CurrentlyLoggedUserDAO.getInstance()
					.isAnyUserInCurrentlyLoggedUserTable()) {
			 progressDialog = ProgressDialog.show(getActivity(), null,
						"Loading...");
			 
			
			JobListManager.getInstance().getOngoingList(getActivity(),offset,limit,
					ongoingCallBack, requestedCode);
			}
			else {
				Toast.makeText(getActivity(), "please login.........",
						Toast.LENGTH_SHORT).show();
			}
		
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		viewroot = inflater.inflate(R.layout.ongoing_tab_layout, null);

		initView();
		initManager();
		
		// SWIPE TO REFRESH
		
		
		swipetorefresh.setColorScheme(android.R.color.holo_blue_dark,
				android.R.color.holo_blue_light,
				android.R.color.holo_green_light,
				android.R.color.holo_green_dark);

		swipetorefresh
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

					@Override
					public void onRefresh() {
						// TODO Auto-generated method stub
						swipetorefresh.setRefreshing(true);
						(new Handler()).postDelayed(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								swipetorefresh.setRefreshing(false);

								offset = 0;
								limit = 10;
								// pDialog =
								// ProgressDialog.show(EmptyFixQuoteActivity.this,"","Loading..",true);
								Log.e("",
										"swipe to refresh>>>>>>>>>>>>>>>>>>>>>>>");
								 mPreviousTotal=0;
								
								JobListManager.getInstance().getOngoingList(getActivity(),offset,limit,
										ongoingCallBack, requestedCode);
							}
						}, 3000);
					}
				});
		return viewroot;
	}

	public void initManager() {
		// TODO Auto-generated method stub
		ongoingCallBack = new OnGoingCallBack();
		updateJobStatusCallBack=new UpdateJobStatusCallBack();
		ongoingAdapter = new OngoingAdapter(
				getActivity(),
				OnGoingTabTrackYourFixFragment.this,
				ongoingList);
		ongoingListview.setAdapter(ongoingAdapter);
		ongoingAdapter.notifyDataSetChanged();

	}

	public void initView() {
		// TODO Auto-generated method stub
		ongoingListview = (ListView) viewroot.findViewById(R.id.ongoing_list);
		empty_ongoing_page = (LinearLayout) viewroot.findViewById(R.id.empty_ongoing_page);
		loadingmore=(RelativeLayout) viewroot.findViewById(R.id.ongoing_loading_more);
		swipetorefresh = (SwipeRefreshLayout) viewroot
				.findViewById(R.id.swipetorefresh_ongoing);

	}

	public class FixFragmentTabHostFragment extends Fragment {

		private FragmentTabHost mTabHost;
		private View view;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub

			view = inflater.inflate(R.layout.fix_details_fragment_tab_host,
					null);

			return mTabHost;
		}

	}

	public void goToFixTab(JobList ongoingList)
	{
		
		JobList jobList = new JobList();

		try {

			jobList.setAddress(ongoingList.getAddress());
			jobList.setId(ongoingList.getId());
			jobList.setImages(ongoingList.getImages());
			jobList.setJobCategoryId(ongoingList.getJobCategoryId());
			jobList.setJobDate(ongoingList.getJobDate());
			jobList.setJobDateTimeSlot(ongoingList.getJobDateTimeSlot());
			jobList.setJobDetails(ongoingList.getJobDetails());
			jobList.setJobReview(ongoingList.getJobReview());
			jobList.setJobStatus(ongoingList.getJobStatus());
			jobList.setJobTypeId(ongoingList.getJobTypeId());
			jobList.setPayment(ongoingList.getPayment());
			jobList.setQuote(ongoingList.getQuote());
			jobList.setReadStatus(ongoingList.getReadStatus());
			jobList.setUserId(ongoingList.getUserId());
		
		} catch (NullPointerException e) {

			Log.d("TAG", "ERROR in jobList");
		}
		Intent fixIntent = new Intent(getActivity(),
				TrackFixFragmentTabhostActivity.class);
		EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
		EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_ONGOING);
		//fixIntent.putExtra(Constants.ONGOING, "ongoing");
		

		fixIntent.putExtra("ONGOINGOBJECT", jobList);
		fixIntent.putExtra("QuoteObject", ongoingList.getQuote().get(0));
	    getActivity().startActivity(fixIntent);
		
	}
	
	
	public class OnGoingCallBack implements AsyncTaskCallBack {
		
		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub

			if (requestedCode == responseCode) {
				final JobListStatusHolder ongoingjoblist = (JobListStatusHolder) result;
				if (UtilValidate.isNotNull(ongoingjoblist)) {
										if (ongoingjoblist.isSuccess()) {
						if (UtilValidate.isNotNull(ongoingjoblist.getData()
								.getJobs())) {
						
							if (UtilValidate.isNotEmpty(ongoingjoblist
									.getData().getJobs())) {
								empty_ongoing_page.setVisibility(View.GONE);
								ongoingListview.setVisibility(View.VISIBLE);
								//ongoingList.clear();
								
								if(offset==0)
								{
									ongoingList.clear();
									ongoingList=ongoingjoblist.getData()
											.getJobs();
									ongoingAdapter = new OngoingAdapter(
											getActivity(),
											OnGoingTabTrackYourFixFragment.this,
											ongoingList);
									ongoingListview.setAdapter(ongoingAdapter);
									progressDialog.dismiss();
								}
								else
								{
									ongoingList.addAll(ongoingjoblist.getData()
											.getJobs());
									progressDialog.dismiss();
								}
								
								
								if(ongoingAdapter==null)
								{
									ongoingAdapter = new OngoingAdapter(
											getActivity(),
											OnGoingTabTrackYourFixFragment.this,
											ongoingList);
									ongoingListview.setAdapter(ongoingAdapter);
									ongoingAdapter.notifyDataSetChanged();
									
								}
								else
								{
									ongoingAdapter.notifyDataSetChanged();
								}
							}
							else
							{
								if(ongoingList.size()>0)
								{
									 if(ongoingAdapter==null){
											
											ongoingAdapter = new OngoingAdapter(
													getActivity(),
													OnGoingTabTrackYourFixFragment.this,
													ongoingList);
											ongoingListview.setAdapter(ongoingAdapter);
											ongoingAdapter.notifyDataSetChanged();
									  }
									  else{
										  ongoingAdapter.notifyDataSetChanged();
									  }
									
								}
								else{
									
									empty_ongoing_page.setVisibility(View.VISIBLE);
									ongoingListview.setVisibility(View.GONE);
								
									}
							}
							progressDialog.dismiss();
								
								
								ongoingListview.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										// TODO Auto-generated method stub
										
										
										if(UtilValidate.isNotNull(ongoingList.get(position).getId()))
										{
											jobid=ongoingList.get(position).getId();
											
										}
										
										if(UtilValidate.isNotNull(ongoingList.get(position).getReadStatus()))
										{
											
											if(ongoingList.get(position).getReadStatus().equalsIgnoreCase("unread"))
											{
												
											UpdateJobStatusManager.getInstance().updateJobStatus(getActivity(), updateJobStatusCallBack,jobid, requestedCode);
											
											Log.e("","updating job status>>>>>>>>>>>>>>>>******************************************");
											
											}
											
											
										}
										
										goToFixTab(ongoingList.get(position));
										
										
				

									}

									
								});		
								
							
						}
						
				
				
			

		else {

			OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
			if (UtilValidate.isNotNull(onFailureErrorHolder)) {
				if (UtilValidate.isNotNull(onFailureErrorHolder
						.getData())) {
					
					Log.e("error","list emptyyyy");

				}
			}
		}
			
			loadingmore.setVisibility(View.GONE);
			ongoingListview.setOnScrollListener(new OngoingScroll());
			
										
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
				Utils.showDialog(getActivity(), "Please check your internet connection.", "ERROR");
			}
			progressDialog.dismiss();
			 
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}

	}

	public void ongoingFragmentTabHost() {
		// TODO Auto-generated method stub
		Intent fixIntent = new Intent(getActivity(),
				TrackFixFragmentTabhostActivity.class);
		EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
		EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_ONGOING);
		startActivity(fixIntent);

	}
	
	
	public class UpdateJobStatusCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			
			
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
	

	public class OngoingScroll implements OnScrollListener{

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
				  
				  loadingmore.setVisibility(View.VISIBLE);
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
						offset=ongoingList.size();
						
			if(offset%10==0)
			{
				JobListManager.getInstance().getOngoingList(getActivity(),offset,limit,ongoingCallBack,requestedCode);
				
			}
			else
			{
				 loadingmore.setVisibility(View.GONE);
				
			}
			        	
			        	
			        	
			        	isLoading = true;
			        }
			        else
			        {
			        	 loadingmore.setVisibility(View.GONE);
			        }
			
			
				  
			  }
			
		}
		
	}


}
