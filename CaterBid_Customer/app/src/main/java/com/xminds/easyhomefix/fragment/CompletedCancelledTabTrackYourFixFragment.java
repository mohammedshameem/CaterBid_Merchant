package com.xminds.easyhomefix.fragment;

import java.io.Serializable;
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
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.JobList;
import com.xminds.easyhomefix.Holder.JobListStatusHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.Holder.Quote;
import com.xminds.easyhomefix.Managers.JobListManager;
import com.xminds.easyhomefix.Managers.UpdateJobStatusManager;
import com.xminds.easyhomefix.activities.CustomerCreateAccountActivity;
import com.xminds.easyhomefix.activities.ReviewServiceActivity;
import com.xminds.easyhomefix.activities.TrackFixFragmentTabhostActivity;
import com.xminds.easyhomefix.adapter.CompletedCancelledAdapter;
import com.xminds.easyhomefix.adapter.OngoingAdapter;
import com.xminds.easyhomefix.adapter.PendingAdapter;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.constants.Constants;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.fragment.OnGoingTabTrackYourFixFragment.OngoingScroll;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class CompletedCancelledTabTrackYourFixFragment extends Fragment {

	private View viewroot;
	private ListView completedCancelledListview;
	private List<JobList> completedCancelledList = new ArrayList<JobList>();
	private CompletedCancelledAdapter completedCancelledAdapter;
	int requestcode = 0;
	private CompletedCallBack completedcallback;
	private LinearLayout empty_completed_page;
	private String quotestatus;
	private String review;
	private String star;
	private List<String> ImageList = new ArrayList<String>();
	UpdateJobStatusCallBack updateJobStatusCallBack;
	private String jobId;
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
    private int mVisibleThreshold=5 ;
    private RelativeLayout loading_completed;
    private int limit=10;
    private int offset=0;
    private SwipeRefreshLayout swipetorefresh;
    private ProgressDialog progressDialog;
	
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			JobListManager.getInstance().getCompletedList(getActivity(),offset,limit,
					completedcallback, requestcode);
		}
	};
    
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
		// for auto refresh
		getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("nofication_received"));
		
		limit=10;
		completedCancelledList.clear();
		offset=completedCancelledList.size();
		
		completedCancelledAdapter = new CompletedCancelledAdapter(getActivity(),CompletedCancelledTabTrackYourFixFragment.this,completedCancelledList);
		completedCancelledListview.setAdapter(completedCancelledAdapter);
		 completedCancelledAdapter.notifyDataSetChanged();
		
		 mPreviousTotal=0;
		if (CurrentlyLoggedUserDAO.getInstance()
				.isAnyUserInCurrentlyLoggedUserTable()) {
			
			progressDialog = ProgressDialog.show(getActivity(), null,
					"Loading...");
			    

			JobListManager.getInstance().getCompletedList(getActivity(),offset,limit,
					completedcallback, requestcode);
		} else {
			Toast.makeText(getActivity(), "please login.........",
					Toast.LENGTH_SHORT).show();
		}

		super.onResume();
	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		viewroot = inflater.inflate(R.layout.completed_cancelled_layout, null);

		initView();
		initManager();
		getIntentValue();
		
		swipetorefresh.setColorScheme(android.R.color.holo_blue_dark,
				android.R.color.holo_blue_light,
				android.R.color.holo_green_light,
				android.R.color.holo_green_dark);
		
		// SWIPE TO REFRESH

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
								JobListManager.getInstance().getCompletedList(getActivity(),offset,limit,
										completedcallback, requestcode);
							}
						}, 3000);
					}
				});
		

		completedCancelledListview
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						
						//gotoFixTab(completedCancelledList.get(position));
						if(UtilValidate.isNotNull(completedCancelledList)){
							for(int i=0;i<completedCancelledList.size();i++){
								if(position==i){
									
									Log.e("", "#######@@@@#######"+completedCancelledList.get(i).getId());
									Log.e("", "#######@@@@#######"+i);
									if(UtilValidate.isNotNull(completedCancelledList.get(i))){
										if(UtilValidate.isNotNull(completedCancelledList.get(i).getJobStatus())){
											
											if(completedCancelledList.get(i).getJobStatus().equalsIgnoreCase("Completed")&&
													UtilValidate.isNotNull(completedCancelledList.get(i).getQuote().get(0).getPaymentStatus())){
												Intent pendingFixIntent = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
												EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
												EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_COMPLETED_PAID);
												Log.e("", "#######@@@@#######"+completedCancelledList.get(i).getId()+"PAID");
												Log.e("", "#######@@@@#######"+i);
												pendingFixIntent.putExtra("job",completedCancelledList.get(i));
												getActivity().startActivity(pendingFixIntent);
											}else if(completedCancelledList.get(i).getJobStatus().equalsIgnoreCase("Completed")&&
													UtilValidate.isNull(completedCancelledList.get(i).getPayment())){
												Intent pendingFixIntent = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
												EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
												EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_COMPLETED_PENDING);
												Log.e("", "#######@@@@#######"+completedCancelledList.get(i).getId()+"PENDING");
												Log.e("", "#######@@@@#######"+i);
												pendingFixIntent.putExtra("job",completedCancelledList.get(i) );
												getActivity().startActivity(pendingFixIntent);
											}else if(completedCancelledList.get(i).getJobStatus().equalsIgnoreCase("Cancelled")){
												Intent pendingFixIntent = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
												EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
												EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_COMPLETED_CANCELLED);
												Log.e("", "#######@@@@#######"+completedCancelledList.get(i).getId()+"CANCELLED");
												Log.e("", "#######@@@@#######"+i);
												pendingFixIntent.putExtra("job", completedCancelledList.get(i));
												getActivity().startActivity(pendingFixIntent);
											}
										}
									}
								}
							}
						}

					}

				});

		return viewroot;
	}

	private void getIntentValue() {
		// TODO Auto-generated method s
		
	}

	public class CompletedCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub

			if (requestcode == responseCode) {
				JobListStatusHolder completedjoblist = (JobListStatusHolder) result;
				if (UtilValidate.isNotNull(completedjoblist)) {
					if (completedjoblist.isSuccess()) {
						if (UtilValidate.isNotNull(completedjoblist.getData()
								.getJobs())) {
							if (UtilValidate.isNotEmpty(completedjoblist
									.getData().getJobs())) {
								
								empty_completed_page.setVisibility(View.GONE);
								completedCancelledListview
										.setVisibility(View.VISIBLE);
								//completedCancelledList.clear();
								
								if(offset==0)
								{
									completedCancelledList.clear();
									completedCancelledList=completedjoblist.getData().getJobs();
									completedCancelledAdapter = new CompletedCancelledAdapter(
											getActivity(),
											CompletedCancelledTabTrackYourFixFragment.this,
											completedCancelledList);
									completedCancelledListview
											.setAdapter(completedCancelledAdapter);
									progressDialog.dismiss();
									
								}
								else
								{
									completedCancelledList.addAll(completedjoblist.getData().getJobs());
									progressDialog.dismiss();
								}
								
								if(completedCancelledAdapter==null)
								{
									completedCancelledAdapter = new CompletedCancelledAdapter(
											getActivity(),
											CompletedCancelledTabTrackYourFixFragment.this,
											completedCancelledList);
									completedCancelledListview
											.setAdapter(completedCancelledAdapter);
									completedCancelledAdapter.notifyDataSetChanged();
								}
								else
								{
									completedCancelledAdapter.notifyDataSetChanged();
								}
								
							}
							

							else {
								Log.e("", "in els>>>>>>>>>>>>>>>>.."+completedCancelledList.size());
								if(completedCancelledList.size()>0)
								{
								
								if(completedCancelledAdapter==null)
								{
									completedCancelledAdapter = new CompletedCancelledAdapter(
											getActivity(),
											CompletedCancelledTabTrackYourFixFragment.this,
											completedCancelledList);
									completedCancelledListview
											.setAdapter(completedCancelledAdapter);
									completedCancelledAdapter.notifyDataSetChanged();
								}
								else
								{
									completedCancelledAdapter.notifyDataSetChanged();
								}
								
								
								}
								else
								{
									empty_completed_page
									.setVisibility(View.VISIBLE);
							completedCancelledListview
									.setVisibility(View.GONE);
									
								}
								
								
							}
							progressDialog.dismiss();
						}
						
						loading_completed.setVisibility(View.GONE);
						completedCancelledListview.setOnScrollListener(new CompletedAndCancelledScroll());
						
					}

					else {

						Toast.makeText(getActivity(),
								completedjoblist.isSuccess() + "",
								Toast.LENGTH_LONG).show();
					}

				} else {

					OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
					if (UtilValidate.isNotNull(onFailureErrorHolder)) {
						if (UtilValidate.isNotNull(onFailureErrorHolder
								.getData())) {

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
				Utils.showDialog(getActivity(), ""+result, "ERROR");
			}
			if(progressDialog!=null)
			{
				progressDialog.dismiss();
			}
			

		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub

		}

	}

	public void initManager() {
		// TODO Auto-generated method stub
		completedcallback = new CompletedCallBack();
		updateJobStatusCallBack = new UpdateJobStatusCallBack();
		completedCancelledAdapter = new CompletedCancelledAdapter(getActivity(),CompletedCancelledTabTrackYourFixFragment.this,completedCancelledList);
		completedCancelledListview.setAdapter(completedCancelledAdapter);
		completedCancelledAdapter.notifyDataSetChanged();
	}

	public void initView() {
		// TODO Auto-generated method stub
		completedCancelledListview = (ListView) viewroot
				.findViewById(R.id.completed_cancelled_list);
		empty_completed_page = (LinearLayout) viewroot
				.findViewById(R.id.empty_completed_page);
		loading_completed=(RelativeLayout) viewroot
		.findViewById(R.id.completed_loading_more);
		swipetorefresh = (SwipeRefreshLayout) viewroot
				.findViewById(R.id.swipetorefresh_completed);
	}

	public void gotoFixTab(JobList completeJobObject) {
		// TODO Auto-generated method stub
/*
		JobList jobList = new JobList();

		try {

			jobList.setAddress(completeJobObject.getAddress());
			jobList.setId(completeJobObject.getId());
			jobList.setImages(completeJobObject.getImages());
			jobList.setJobCategoryId(completeJobObject.getJobCategoryId());
			jobList.setJobDate(completeJobObject.getJobDate());
			jobList.setJobDateTimeSlot(completeJobObject.getJobDateTimeSlot());
			jobList.setJobDetails(completeJobObject.getJobDetails());
			jobList.setJobReview(completeJobObject.getJobReview());
			jobList.setJobStatus(completeJobObject.getJobStatus());
			jobList.setJobTypeId(completeJobObject.getJobTypeId());
			jobList.setPayment(completeJobObject.getPayment());
			jobList.setQuote(completeJobObject.getQuote());
			jobList.setReadStatus(completeJobObject.getReadStatus());
			jobList.setUserId(completeJobObject.getUserId());
		} catch (NullPointerException e) {

			Log.d("TAG", "ERROR in jobList");
		}

		Intent fixIntent = new Intent(getActivity(),
				TrackFixFragmentTabhostActivity.class);
		EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
		EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_COMPLETED);
		fixIntent.putExtra("JOBOBJECT", jobList);
		getActivity().startActivity(fixIntent);*/
		
		

	}

	public void addReview(String joibid) {
		// TODO Auto-generated method stub

		jobId = joibid;

		Intent fixIntent = new Intent(getActivity(),
				ReviewServiceActivity.class);
		fixIntent.putExtra("jobid", jobId);
		startActivity(fixIntent);

	}

	public class UpdateJobStatusCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub

			
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
				Utils.showDialog(getActivity(), ""+result, "ERROR");
			}

		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub

		}

	}
	
	public class CompletedAndCancelledScroll implements OnScrollListener{

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
						offset=completedCancelledList.size();
						Log.e("","offset>>>>>.."+offset);
						if(offset%10==0){
							JobListManager.getInstance().getCompletedList(getActivity(),offset,limit,
									completedcallback, requestcode);
						}
						else
						{
							Log.e("","innnnnnnnnnnnnnnnnn");
							loading_completed.setVisibility(View.GONE);
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

	

}
