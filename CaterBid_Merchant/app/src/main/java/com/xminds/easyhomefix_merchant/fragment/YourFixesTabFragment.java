package com.xminds.easyhomefix_merchant.fragment;

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
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.xminds.easyfix_merchant.constants.Constants;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.AvailableJobs;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.EndpointResonseBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.JobBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix_merchant.Manager.ReviewManager;
import com.xminds.easyhomefix_merchant.Manager.UpdateJobStatusManager;
import com.xminds.easyhomefix_merchant.Manager.YourFixesManager;
import com.xminds.easyhomefix_merchant.activities.TrackFixFragmentTabhostActivity;
import com.xminds.easyhomefix_merchant.adapter.CompletedCancelledAdapter;
import com.xminds.easyhomefix_merchant.adapter.OngoingYourFixAdapter;
import com.xminds.easyhomefix_merchant.adapter.filteradapter;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;

public class YourFixesTabFragment extends Fragment {
	private View viewroot;
	private ListView ongoingListview;
	private ListView completedListview;
	private List<Integer> dummyList = new ArrayList<Integer>();
	private List<AvailableJobs> ongoingJobList;
	private List<AvailableJobs> completedCancelledList;
	private OngoingYourFixAdapter ongoingAdapter;
	private CompletedCancelledAdapter completedCancelledAdapter;
	private ImageView ongoing;
	private ImageView completed;
	private ImageView canceled;
	private LinearLayout ongoingLayout;
	private LinearLayout completedLayout;
	private int requestcode = 0;
	private OngoingCallBack ongoingCallBack;
	private CompletedCancelledCallBack completedCancelledCallBack;
	private Spinner filterStatus_spinner;
	private List<String> spinnerValues = new ArrayList<String>();
	private int filterPosition = 0;
	UpdateJobstatusCallBack updateJobstatusCallBack;
	private String jobid;
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
    private int limit=10;
    private int offset=0;
    private RelativeLayout loading_ongoing;
    private RelativeLayout loading_completed;
    private RequestReviewCallBack requestReviewcallback;	
    List<AvailableJobs> completedCancelledFilterList = new ArrayList<AvailableJobs>();
    List<AvailableJobs> AllCompletedCancelledList=new ArrayList<AvailableJobs>();
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
	private ProgressDialog pDialog;
	filteradapter filteradapter;
    
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if(EasyHomeFixApp.getYourfixtabstatus()!=null){
		    	if(EasyHomeFixApp.getYourfixtabstatus().equalsIgnoreCase(Constants.YOURFIX_ONGOING)){
		    		YourFixesManager.getInstance().ongoing(getActivity(),limit,offset, ongoingCallBack, requestcode);	
		    	}else if(EasyHomeFixApp.getYourfixtabstatus().equalsIgnoreCase(Constants.YOURFIX_COMPLETED)){
		    			YourFixesManager.getInstance().completedCancelled(getActivity(),limit,offset, 
		    					completedCancelledCallBack, requestcode);				 
				 }
	    	}else{
	    		YourFixesManager.getInstance().ongoing(getActivity(),limit,offset, ongoingCallBack, requestcode);	
	    	}
			
		}
	};
	
    @Override
    public void onResume() {
    	
    	// for auto refresh
		getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("nofication_received"));
    	
    	spinnerValues.clear();
    	spinnerValues.add("All");
    	spinnerValues.add("Payment Completed");
    	spinnerValues.add("Payment Pending");
    	spinnerValues.add("Payment Cancelled");
  
    	dummyList.clear();
		
		filteradapter=new filteradapter(getActivity(), spinnerValues);
		filterStatus_spinner.setAdapter(filteradapter);
		filteradapter.notifyDataSetChanged();
		
		
	//	filterStatus_spinner.setAdapter(new filteradapter(getActivity(),spinnerValues));
		
		filterStatus_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				limit=10;
				offset=0;
				filterPosition = position;
				
				 /*progressDialog = ProgressDialog.show(getActivity(), null,
							"Loading...");*/
				YourFixesManager.getInstance().completedCancelled(getActivity(),limit,offset, completedCancelledCallBack, requestcode);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		 progressDialog = ProgressDialog.show(getActivity(), null,
					"Loading...");
		
		YourFixesManager.getInstance().ongoing(getActivity(),limit,offset, ongoingCallBack, requestcode);
		
		ongoingListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				
				if(ongoingJobList.get(position).getId()!=null)
				{
					jobid=ongoingJobList.get(position).getId();
				}
				
				if(ongoingJobList.get(position).getReadStatus()!=null)
				{
					if(ongoingJobList.get(position).getReadStatus().equalsIgnoreCase("unread"))
					{
						UpdateJobStatusManager.getInstance().updateJobStatus(getActivity(), updateJobstatusCallBack, jobid, requestcode);
					}
				}
				
				Intent pendingFixIntent = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
				EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
				EasyHomeFixApp.setDetailsTabStatus(Constants.ONGOING);
				if(UtilValidate.isNotNull(ongoingJobList)){
					for(int i=0;i<ongoingJobList.size();i++){
						if(position==i){
							
							if(UtilValidate.isNotNull(ongoingJobList.get(i))){
								//pendingFixIntent.putExtra("job", ongoingJobList.get(i));
								pendingFixIntent.putExtra("QUOTED_OBJECT_FROM_ONGOING", ongoingJobList.get(i));
							}
						}
					}
				}
				pendingFixIntent.putExtra(Constants.ONGOING, "ongoing");
				startActivity(pendingFixIntent);
			}
			
		});

		
		ongoing.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EasyHomeFixApp.setYourfixtabstatus(Constants.YOURFIX_ONGOING);
				
				limit=10;
				ongoingJobList.clear();
				offset=ongoingJobList.size();
				mPreviousTotal=0;
				
				ongoing.setImageResource(R.drawable.ongoing_s);
				completed.setImageResource(R.drawable.completedcancel);
				
				//ongoingAdapter = new OngoingYourFixAdapter(getActivity(),YourFixesTabFragment.this,ongoingJobList);
				//ongoingListview.setAdapter(ongoingAdapter);
				ongoingLayout.setVisibility(View.VISIBLE);
				completedLayout.setVisibility(View.GONE);
				 progressDialog = ProgressDialog.show(getActivity(), null,
							"Loading...");
				
				YourFixesManager.getInstance().ongoing(getActivity(),limit,offset, ongoingCallBack, requestcode);
			}
		});
		
		completed.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EasyHomeFixApp.setYourfixtabstatus(Constants.YOURFIX_COMPLETED);
				

				limit=10;
				completedCancelledList.clear();
				offset=completedCancelledList.size();
				mPreviousTotal=0;
				
				ongoing.setImageResource(R.drawable.ongoingtab);
				completed.setImageResource(R.drawable.completedcancel_s);
				/*completedCancelledAdapter = new CompletedCancelledAdapter(getActivity(),YourFixesTabFragment.this,dummyList);
				completedListview.setAdapter(completedCancelledAdapter);*/
				ongoingLayout.setVisibility(View.GONE);
				completedLayout.setVisibility(View.VISIBLE);
				 progressDialog = ProgressDialog.show(getActivity(), null,
							"Loading...");
				
				YourFixesManager.getInstance().completedCancelled(getActivity(), limit,offset,completedCancelledCallBack, requestcode);
				Log.e("++++++++","Header : "+CurrentlyLoggedUserDAO.getInstance().getHeader());
			}
		});
		
		completedListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {

//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	
				
				if(completedCancelledFilterList.get(position).getId()!=null)
				{
					jobid=completedCancelledFilterList.get(position).getId();
				}
				
				if(completedCancelledFilterList.get(position).getReadStatus()!=null)
				{
					if(completedCancelledFilterList.get(position).getReadStatus().equalsIgnoreCase("unread"))
					{
						UpdateJobStatusManager.getInstance().updateJobStatus(getActivity(), updateJobstatusCallBack, jobid, requestcode);
					}
				}
				if(UtilValidate.isNotNull(completedCancelledFilterList)){
					for(int i=0;i<completedCancelledFilterList.size();i++){
						if(position==i){
							
							Log.e("", "#######@@@@#######"+completedCancelledFilterList.get(i).getId());
							Log.e("", "#######@@@@#######"+i);
							if(UtilValidate.isNotNull(completedCancelledFilterList.get(i))){
								if(UtilValidate.isNotNull(completedCancelledFilterList.get(i).getJobStatus())){
									 if(completedCancelledFilterList.get(i).getJobStatus().equalsIgnoreCase("Pending")||
											completedCancelledFilterList.get(i).getQuote().getStatus().equalsIgnoreCase("3")){
										Intent pendingFixIntent = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
										EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
										EasyHomeFixApp.setDetailsTabStatus(Constants.COMPLETED_CANCELLED_CANCELLED);
										Log.e("", "#######@@@@#######"+completedCancelledFilterList.get(i).getId()+"CANCELLED");
										Log.e("", "#######@@@@#######"+i);
										//pendingFixIntent.putExtra("job", completedCancelledFilterList.get(i));
										pendingFixIntent.putExtra("QUOTED_OBJECT_FROM_COMPLETE",completedCancelledFilterList.get(i));
										getActivity().startActivity(pendingFixIntent);
									}
										
								else if(completedCancelledFilterList.get(i).getJobStatus().equalsIgnoreCase("Completed")&&
											UtilValidate.isNotNull(completedCancelledFilterList.get(i).getPayment())){
										Intent pendingFixIntent = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
										EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
										EasyHomeFixApp.setDetailsTabStatus(Constants.COMPLETED_CANCELLED);
										Log.e("", "#######@@@@#######"+completedCancelledFilterList.get(i).getId()+"PAID");
										Log.e("", "#######@@@@#######"+i);
										//pendingFixIntent.putExtra("job",completedCancelledFilterList.get(i));
										pendingFixIntent.putExtra("QUOTED_OBJECT_FROM_COMPLETE",completedCancelledFilterList.get(i));
										
										getActivity().startActivity(pendingFixIntent);
									}
									
									else if(completedCancelledFilterList.get(i).getJobStatus().equalsIgnoreCase("Completed")&&
											UtilValidate.isNull(completedCancelledFilterList.get(i).getPayment())){
										Intent pendingFixIntent = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
										EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
										EasyHomeFixApp.setDetailsTabStatus(Constants.COMPLETED_CANCELLED_PENDING);
										Log.e("", "#######@@@@#######"+completedCancelledFilterList.get(i).getId()+"PENDING");
										Log.e("", "#######@@@@#######"+i);
										//pendingFixIntent.putExtra("job",completedCancelledFilterList.get(i) );
										pendingFixIntent.putExtra("QUOTED_OBJECT_FROM_COMPLETE",completedCancelledFilterList.get(i));
										getActivity().startActivity(pendingFixIntent);
									}else if(completedCancelledFilterList.get(i).getJobStatus().equalsIgnoreCase("Cancelled")){
										Intent pendingFixIntent = new Intent(getActivity(),TrackFixFragmentTabhostActivity.class);
										EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
										EasyHomeFixApp.setDetailsTabStatus(Constants.COMPLETED_CANCELLED_CANCELLED);
										Log.e("", "#######@@@@#######"+completedCancelledFilterList.get(i).getId()+"CANCELLED");
										Log.e("", "#######@@@@#######"+i);
										//pendingFixIntent.putExtra("job", completedCancelledFilterList.get(i));
										pendingFixIntent.putExtra("QUOTED_OBJECT_FROM_COMPLETE",completedCancelledFilterList.get(i));
										getActivity().startActivity(pendingFixIntent);
									}
								}
							}
						}
					}
				}
				
				
			}
			
		});
		
		
		
		
		
		// SWIPE TO REFRESH
		
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_dark);

		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			             
			            @Override
			            public void onRefresh() {
			                // TODO Auto-generated method stub
			            	swipeRefreshLayout.setRefreshing(true);
			                ( new Handler()).postDelayed(new Runnable() {
			                     
			                    @Override
			                    public void run() {
			                        // TODO Auto-generated method stub
			                    	swipeRefreshLayout.setRefreshing(false);
			                        mPreviousTotal=0;
			                    	
			                    	// pDialog = ProgressDialog.show(EmptyFixQuoteActivity.this,"","Loading..",true);	
			                    		 limit=10;
			                    		 offset=0;
			                        YourFixesManager.getInstance().ongoing(getActivity(),limit,offset, ongoingCallBack, requestcode);
			                    
			                    }
			                }, 3000);
			            }
			        });
		
    	// TODO Auto-generated method stub
    	if(EasyHomeFixApp.getYourfixtabstatus()!=null)
    	{
    		
    	
    	if(EasyHomeFixApp.getYourfixtabstatus().equalsIgnoreCase(Constants.YOURFIX_ONGOING))
    	{
    		YourFixesManager.getInstance().ongoing(getActivity(),limit,offset, ongoingCallBack, requestcode);
    		
    	}
    	else
    		if(EasyHomeFixApp.getYourfixtabstatus().equalsIgnoreCase(Constants.YOURFIX_COMPLETED))
		 {
    			YourFixesManager.getInstance().completedCancelled(getActivity(),limit,offset, completedCancelledCallBack, requestcode);	
	 
		 }
    	}
    	else
    	{
    		YourFixesManager.getInstance().ongoing(getActivity(),limit,offset, ongoingCallBack, requestcode);
    		
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
		//return super.onCreateView(inflater, container, savedInstanceState);
		viewroot = inflater.inflate(R.layout.yourfixes_tab_layout, null);
		initView();
		initManager();
		
		
		return viewroot;
	}
	
	
	
	private void initView() {
		// TODO Auto-generated method stub
		ongoingListview = (ListView)viewroot.findViewById(R.id.ongoing_list);
		completedListview = (ListView)viewroot.findViewById(R.id.completed_list);
		ongoing=(ImageView)viewroot.findViewById(R.id.image_ongoing);
		completed=(ImageView)viewroot.findViewById(R.id.image_completed);
		ongoingLayout=(LinearLayout)viewroot.findViewById(R.id.layout_ongoing_list);
		completedLayout=(LinearLayout)viewroot.findViewById(R.id.layout_completed_list);
		canceled=(ImageView)viewroot.findViewById(R.id.cancelled_right_arrow);
		filterStatus_spinner = (Spinner)viewroot.findViewById(R.id.filterStatus_spinner);
		loading_ongoing=(RelativeLayout)viewroot.findViewById(R.id.ongoing_loading_more);
		loading_completed=(RelativeLayout)viewroot.findViewById(R.id.completed_loading_more);
		swipeRefreshLayout=(SwipeRefreshLayout)viewroot.findViewById(R.id.swipetorefreh_ongoing);
		
	}
	private void initManager() {
		// TODO Auto-generated method stub
		ongoingCallBack=new OngoingCallBack();
		completedCancelledCallBack=new CompletedCancelledCallBack();
		ongoingJobList=new ArrayList<AvailableJobs>();
		completedCancelledList=new ArrayList<AvailableJobs>();
		updateJobstatusCallBack=new UpdateJobstatusCallBack();
		requestReviewcallback = new RequestReviewCallBack();
		
		ongoingAdapter = new OngoingYourFixAdapter(getActivity(),YourFixesTabFragment.this,ongoingJobList);
		ongoingListview.setAdapter(ongoingAdapter);
		ongoingAdapter.notifyDataSetChanged();
		
		completedCancelledAdapter = new CompletedCancelledAdapter(getActivity(),
				YourFixesTabFragment.this,completedCancelledList);
		completedListview.setAdapter(completedCancelledAdapter);
		completedCancelledAdapter.notifyDataSetChanged();
	
	}

	
	public class OngoingCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			JobBaseHolder jobBaseHolder=new JobBaseHolder();
			if(responseCode==requestcode){	
				if(UtilValidate.isNotNull(result)){
						jobBaseHolder=(JobBaseHolder) result;	
							if(jobBaseHolder.isSuccess()){
								if(UtilValidate.isNotNull(jobBaseHolder.getData().getJobs())&&
										UtilValidate.isNotEmpty(jobBaseHolder.getData().getJobs())){
									
									if(offset==0)
									{
										ongoingJobList.clear();
										ongoingJobList=jobBaseHolder.getData().getJobs();
										ongoingAdapter = new OngoingYourFixAdapter(getActivity(),YourFixesTabFragment.this,ongoingJobList);
										ongoingListview.setAdapter(ongoingAdapter);
										progressDialog.dismiss();
									}
									else
									{
										ongoingJobList.addAll(jobBaseHolder.getData().getJobs());
										progressDialog.dismiss();
									}
									
									
									
									if(ongoingAdapter==null)
									{
										ongoingAdapter = new OngoingYourFixAdapter(getActivity(),YourFixesTabFragment.this,ongoingJobList);
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
									if(ongoingJobList.size()>0)
									{
										if(ongoingAdapter==null)
										{
											ongoingAdapter = new OngoingYourFixAdapter(getActivity(),YourFixesTabFragment.this,ongoingJobList);
											ongoingListview.setAdapter(ongoingAdapter);
											ongoingAdapter.notifyDataSetChanged();
										}
										else
										{
											ongoingAdapter.notifyDataSetChanged();
										}
										
									}
								}
								
								if(jobBaseHolder.getData().getJobs().isEmpty()&&offset==0)
								{
									ongoingJobList=jobBaseHolder.getData().getJobs();
									ongoingAdapter = new OngoingYourFixAdapter(getActivity(),YourFixesTabFragment.this,ongoingJobList);
									ongoingListview.setAdapter(ongoingAdapter);
									ongoingAdapter.notifyDataSetChanged();
								}
								
								progressDialog.dismiss();
								loading_ongoing.setVisibility(View.GONE);
								ongoingListview.setOnScrollListener(new OngoingJobsScroll());
						}
						else
						{
							Toast.makeText(getActivity(), jobBaseHolder.isSuccess()+"", Toast.LENGTH_LONG).show();
							progressDialog.dismiss();

						}
					}
			}else{

				OnFailureErrorHolder onFailureErrorHolder =(OnFailureErrorHolder) result;
				if(UtilValidate.isNotNull(onFailureErrorHolder))
				{	
					Toast.makeText(getActivity(), onFailureErrorHolder.getData().get(0).getMessage()+"", Toast.LENGTH_LONG).show();
					progressDialog.dismiss();
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
			progressDialog.dismiss();
		}
		
	}
	
	public class CompletedCancelledCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			JobBaseHolder jobBaseHolder=new JobBaseHolder();
			if(responseCode==requestcode){
					if(UtilValidate.isNotNull(result)){
						jobBaseHolder=(JobBaseHolder) result;
						if(jobBaseHolder.isSuccess()){
							if(UtilValidate.isNotNull(jobBaseHolder.getData().getJobs())&&
									UtilValidate.isNotEmpty(jobBaseHolder.getData().getJobs())){
								
								if(offset==0)
								{
									completedCancelledList.clear();
									completedCancelledList=jobBaseHolder.getData().getJobs();
									completedCancelledAdapter = new CompletedCancelledAdapter(getActivity(),
											YourFixesTabFragment.this,completedCancelledFilterList);
									completedListview.setAdapter(completedCancelledAdapter);
									progressDialog.dismiss();
									
								}
								else
								{
									 completedCancelledList.addAll(jobBaseHolder.getData().getJobs());
									 progressDialog.dismiss();
								}
								  
								
							//Filter List	
								if(filterPosition==0){
									

									completedCancelledFilterList.clear();
									for(AvailableJobs job : completedCancelledList){
										
										completedCancelledFilterList.add(job);
										
										
										if(completedCancelledAdapter==null)
										{
											if(UtilValidate.isNotEmpty(completedCancelledFilterList)){
												Log.e("","on test2");
												completedCancelledAdapter = new CompletedCancelledAdapter(getActivity(),
														YourFixesTabFragment.this,completedCancelledFilterList);
												completedListview.setAdapter(completedCancelledAdapter);
												completedCancelledAdapter.notifyDataSetChanged();
											}else{
												Toast.makeText(getActivity(),"No Data in List", Toast.LENGTH_LONG).show();
											}
										}
										else
										{
											completedCancelledAdapter.notifyDataSetChanged();
											
										}
										
										progressDialog.dismiss();
										
										
									}
									loading_completed.setVisibility(View.GONE);
									completedListview.setOnScrollListener(new CompletedOnScroll());
									
								}else if(filterPosition==1){
									completedCancelledFilterList.clear();
										for(AvailableJobs job : completedCancelledList){
											if(job.getJobStatus().equals("Completed")
													&& UtilValidate.isNotNull(job.getPayment())){
												if(job.getQuote().getStatus().equalsIgnoreCase("2"))
												{
												   completedCancelledFilterList.add(job);
												}
												
												
											}
										}
										if(UtilValidate.isNotEmpty(completedCancelledFilterList)){
											completedCancelledAdapter = new CompletedCancelledAdapter(getActivity(),
													YourFixesTabFragment.this,completedCancelledFilterList);
											completedListview.setAdapter(completedCancelledAdapter);
										}else{
											Toast.makeText(getActivity(),"No Data in List", Toast.LENGTH_LONG).show();
										}
										progressDialog.dismiss();
										
								}else if(filterPosition==2){
									completedCancelledFilterList.clear();
									for(AvailableJobs job : completedCancelledList){
										if(job.getJobStatus().equals("Completed")
												&& UtilValidate.isNull(job.getPayment())){

											completedCancelledFilterList.add(job);
										}
									}
									if(UtilValidate.isNotEmpty(completedCancelledFilterList)){
										completedCancelledAdapter = new CompletedCancelledAdapter(getActivity(),
												YourFixesTabFragment.this,completedCancelledFilterList);
										completedListview.setAdapter(completedCancelledAdapter);
									}else{
										Toast.makeText(getActivity(),"No Data in List", Toast.LENGTH_LONG).show();
									}
									progressDialog.dismiss();
									
								}else if(filterPosition==3){
									completedCancelledFilterList.clear();
									for(AvailableJobs job : completedCancelledList){
										if(job.getJobStatus().equals("Cancelled")||job.getQuote().getStatus().
												equalsIgnoreCase("3")){
											completedCancelledFilterList.add(job);
										}
										
									}
									if(UtilValidate.isNotEmpty(completedCancelledFilterList)){
										completedCancelledAdapter = new CompletedCancelledAdapter(getActivity(),
												YourFixesTabFragment.this,completedCancelledFilterList);
										completedListview.setAdapter(completedCancelledAdapter);
									}else{
										Toast.makeText(getActivity(),"No Data in List", Toast.LENGTH_LONG).show();
									}
									
									progressDialog.dismiss();
								}
								
								
							}else{
								Toast.makeText(getActivity(),"No Data in List", Toast.LENGTH_LONG).show();
								progressDialog.dismiss();
							}
							
							/*loading_completed.setVisibility(View.GONE);
							completedListview.setOnScrollListener(new CompletedOnScroll());*/
							}
						}else{
							Toast.makeText(getActivity(), jobBaseHolder.isSuccess()+"", Toast.LENGTH_LONG).show();
							progressDialog.dismiss();
					}
			}else{

				OnFailureErrorHolder onFailureErrorHolder =(OnFailureErrorHolder) result;
				if(UtilValidate.isNotNull(onFailureErrorHolder))
				{	
					Toast.makeText(getActivity(), onFailureErrorHolder.getData().get(0).getMessage()+"", Toast.LENGTH_LONG).show();
					progressDialog.dismiss();
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
			progressDialog.dismiss();
		}
		
	}
	
/*	public class SpinnerAdapter extends ArrayAdapter<String> {

		private Activity activity;

		public SpinnerAdapter(Activity activity, int resource, String[] objects) {

			// TODO Auto-generated constructor stub
			super(activity, resource, objects);
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			
			// TODO Auto-generated method stub

			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View mySpinner = inflater.inflate(R.layout.spinner_item, parent,
					false);
			TextView main_text = (TextView) mySpinner
					.findViewById(R.id.spinner_item_textview);
			main_text.setText(spinnerValues[position]);
			
			return mySpinner;

		}

	}*/
	
	public class UpdateJobstatusCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			EmailSignupBaseHolder updatejobeHolder=(EmailSignupBaseHolder)result;
			
			
			
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
	
	public class OngoingJobsScroll implements OnScrollListener{

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
				  
				  loading_ongoing.setVisibility(View.VISIBLE);
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
						offset=ongoingJobList.size();
						Log.e("","offset valuein ongoing job"+offset);
						
			if(offset%10==0)
			{
				
				YourFixesManager.getInstance().ongoing(getActivity(),limit,offset, ongoingCallBack, requestcode);
				
			}
			else
			{
				loading_ongoing.setVisibility(View.GONE);
				
			}
			        	isLoading = true;
			        }
			        else
			        {
			        	loading_ongoing.setVisibility(View.GONE);
			        }
			
			
				  
			  }
			
		}
		
	}
	
	
	public class CompletedOnScroll implements OnScrollListener{

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
			Log.e("","in on scrolll");
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
						Log.e("","offset in completed job"+offset);
						
			if(offset%10==0)
			{
				
				YourFixesManager.getInstance().completedCancelled(getActivity(),limit,offset, completedCancelledCallBack, requestcode);
				
			}
			else
			{
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
	
	public void requestReview(String jobId){
		Log.e("JobId","JobId : "+jobId);
	/*	if(		pDialog!=null){
			pDialog.dismiss();*/
		 pDialog=ProgressDialog.show(getActivity(), null, null);
		ReviewManager.getInstance().requestReview(getActivity(), jobId, requestReviewcallback, requestcode);
		/*}*/
	}
	
	private class RequestReviewCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			
				pDialog.dismiss();
			
			if(requestcode == responseCode){
				if(result != null){
					pDialog.dismiss();
					EndpointResonseBaseHolder endpointResonseBaseHolder = (EndpointResonseBaseHolder) result;
					if(endpointResonseBaseHolder.isSuccess()){
						if(endpointResonseBaseHolder.getData() != null){
							if(endpointResonseBaseHolder.getData().getMessage() != null){
								pDialog.dismiss();
								Utils.showDialog(getActivity(), ""+endpointResonseBaseHolder.getData().getMessage(), "EasyHomeFix");
								//Toast.makeText(getActivity(),endpointResonseBaseHolder.getData().getMessage(), Toast.LENGTH_LONG).show();
							}
							else
							{
								pDialog.dismiss();
							}
						}
						else
						{
							pDialog.dismiss();
						}
					}
					else
					{
						pDialog.dismiss();
					}
				}
				else
				{
					pDialog.dismiss();
				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			//Toast.makeText(getActivity(),result, Toast.LENGTH_LONG).show();
		
				pDialog.dismiss();
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
