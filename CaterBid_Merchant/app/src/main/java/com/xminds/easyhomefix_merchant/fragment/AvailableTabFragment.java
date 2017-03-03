package com.xminds.easyhomefix_merchant.fragment;

import java.lang.reflect.Type;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xminds.easyfix_merchant.constants.Constants;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.AvailableBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.AvailableJobs;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix_merchant.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix_merchant.Holder.Quote;
import com.xminds.easyhomefix_merchant.Holder.UserTokenHolder;
import com.xminds.easyhomefix_merchant.Manager.AvailableManager;
import com.xminds.easyhomefix_merchant.Manager.QuoteManager;
import com.xminds.easyhomefix_merchant.Manager.UpdateJobStatusManager;
import com.xminds.easyhomefix_merchant.activities.EmptyFixQuoteActivity;
import com.xminds.easyhomefix_merchant.activities.TrackFixFragmentTabhostActivity;
import com.xminds.easyhomefix_merchant.adapter.NewJobsAdapter;
import com.xminds.easyhomefix_merchant.adapter.QuotedForAdapter;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.dao.UserDAO;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;

public class AvailableTabFragment extends Fragment {

	private View viewroot;
	private ListView newJobsListview;
	private ListView quotedListview;
	private NewJobsAdapter newJobsAdapter;
	private QuotedForAdapter quotedForAdapter;
	private ImageView newJobs;
	private ImageView quoted;
	private LinearLayout newJobsLayout;
	private LinearLayout quotedLayout;
	private SwipeRefreshLayout swipetorefresh_newjob;
	private SwipeRefreshLayout swipetorefresh_quoted;
	private NewJobCallBack newJobCallBack;
	private QuotedjobcallBack quotedjobcallBack;
	private int requestcode = 0;
	private List<AvailableJobs> pendingNewJobsList = new ArrayList<AvailableJobs>();
	private List<AvailableJobs> quotedList = new ArrayList<AvailableJobs>();
	private AvailableJobs NewJoblist =new AvailableJobs();
	private List<String>CustomerImageList=new ArrayList<String>();
	private ArrayList<String> CategoryId;
	private ArrayList<String> categoryId;
	private ArrayList<String>  catId;
	private int offset = 0;
	private int limit = 10;
	private String jobid;;
	private UserTokenHolder tokenholder;
	UpdateJobStatusCallBack updateJobStatusCallBack;
	private RelativeLayout loadingmore;
	private RelativeLayout loadingmorequoted;
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
    private QuoteCallBack quotecallback;
	private Quote quoteObject;
	private ProgressDialog progressDialog;
	Intent pendingFixIntent;
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			 if(EasyHomeFixApp.getAvailabletabstatus()!=null){
				if(EasyHomeFixApp.getAvailabletabstatus().equalsIgnoreCase(Constants.AVAILABLE_NEWJOB)){
						AvailableManager.getInstance().getAllNewJobs(getActivity(), CategoryId,
								offset, limit, newJobCallBack, requestcode);
				 }else if(EasyHomeFixApp.getAvailabletabstatus().equalsIgnoreCase(Constants.AVAILABLE_QUOTEDJOB)){
						AvailableManager.getInstance().getAllQuotedJobs(getActivity(), CategoryId, offset, 
								limit, quotedjobcallBack, requestcode);	 
				 }
			 }
			 else
			 {
				 AvailableManager.getInstance().getAllNewJobs(getActivity(), CategoryId,
							offset, limit, newJobCallBack, requestcode);
			 }
		}
	};
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub

		// for auto refresh
				getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("nofication_received"));
		
		initView();
		initManager();
		// Bundle b = getActivity().getIntent().getExtras();
		//CategoryId.clear();
		Bundle b = this.getArguments();
		if (b != null) {
			//CategoryId.clear();
			categoryId = (ArrayList<String>) b
					.getStringArrayList("CATEGORY_ID");
			Log.d("", "CATEGORY ID IN AVAILABLE" + categoryId);
		}
		
			
			Gson gson = new Gson();
			tokenholder = new UserTokenHolder();
			tokenholder = UserDAO.getInstance().getUserDetails();
			//Log.d("", "CATEGORY ID FROM DB"+tokenholder.getUser().getCategoryId());
			
			Type type = new TypeToken<ArrayList<String>>() {}.getType();
			//CategoryId.clear();
			catId = gson.fromJson(tokenholder.getUser().getCategoryId(), type);
			
			
			
			if(categoryId!=null)
			{
				if(!categoryId.isEmpty())
				{
					CategoryId=categoryId;
				}
			}
			
			if(catId!=null)
			{
				if(!catId.isEmpty())
				{
					CategoryId=catId;
				}
			}
			
			/* progressDialog = ProgressDialog.show(getActivity(), null,
						"Loading...");*/
			 Log.e("","status>>>>>>>>>>>>>>>>>>>>>>>"+EasyHomeFixApp.getAvailabletabstatus());
			 
			 if(EasyHomeFixApp.getAvailabletabstatus()!=null)
			 {
			 
			if(EasyHomeFixApp.getAvailabletabstatus().equalsIgnoreCase(Constants.AVAILABLE_NEWJOB))
			 {
				newJobs.setImageResource(R.drawable.new_job_s);
				quoted.setImageResource(R.drawable.quoted_unselected);
				EasyHomeFixApp.setDetailsTabStatus(Constants.NEW_JOBS);
					AvailableManager.getInstance().getAllNewJobs(getActivity(), CategoryId,
							offset, limit, newJobCallBack, requestcode);
			 }
			 else if(EasyHomeFixApp.getAvailabletabstatus().equalsIgnoreCase(Constants.AVAILABLE_QUOTEDJOB))
			 {
				 newJobs.setImageResource(R.drawable.new_job);
				quoted.setImageResource(R.drawable.quoted_selected);
				EasyHomeFixApp.setDetailsTabStatus(Constants.QUOTED_JOBS);
				newJobsLayout.setVisibility(View.GONE);
				quotedLayout.setVisibility(View.VISIBLE);
					AvailableManager.getInstance().getAllQuotedJobs(getActivity(), CategoryId, offset, limit, quotedjobcallBack, requestcode);
				 
			 }
			 }
			 else
			 {
				 progressDialog = ProgressDialog.show(getActivity(), null,
							"Loading...");
				 AvailableManager.getInstance().getAllNewJobs(getActivity(), CategoryId,
							offset, limit, newJobCallBack, requestcode);
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

		viewroot = inflater.inflate(R.layout.available_tab_layout, null);

		initView();
		initManager();
		

		newJobs.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				EasyHomeFixApp.setAvailabletabstatus(Constants.AVAILABLE_NEWJOB);
				limit=10;
				pendingNewJobsList.clear();
				offset=pendingNewJobsList.size();
				mPreviousTotal=0;
				newJobsLayout.setVisibility(View.VISIBLE);
				quotedLayout.setVisibility(View.GONE);
				/*newJobsAdapter = new NewJobsAdapter(getActivity(),AvailableTabFragment.this,pendingNewJobsList);
				newJobsListview.setAdapter(newJobsAdapter);
				newJobsAdapter.notifyDataSetChanged();
				*/
			
				newJobs.setImageResource(R.drawable.new_job_s);
				quoted.setImageResource(R.drawable.quoted_unselected);
				EasyHomeFixApp.setDetailsTabStatus(Constants.NEW_JOBS);
				
				progressDialog = ProgressDialog.show(getActivity(), null,
							"Loading...");
				AvailableManager.getInstance().getAllNewJobs(getActivity(),
						CategoryId, offset, limit, newJobCallBack, requestcode);
			
			}
		});

		quoted.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EasyHomeFixApp.setAvailabletabstatus(Constants.AVAILABLE_QUOTEDJOB);
				limit=10;
				quotedList.clear();
				offset=quotedList.size();
				mPreviousTotal=0;
				
				/*quotedForAdapter = new QuotedForAdapter(getActivity(),quotedList);
				newJobsListview.setAdapter(quotedForAdapter);
				quotedForAdapter.notifyDataSetChanged();*/
				
				newJobs.setImageResource(R.drawable.new_job);
				quoted.setImageResource(R.drawable.quoted_selected);
				EasyHomeFixApp.setDetailsTabStatus(Constants.QUOTED_JOBS);
				progressDialog = ProgressDialog.show(getActivity(), null,
							"Loading...");
				AvailableManager.getInstance().getAllQuotedJobs(getActivity(), CategoryId, offset, limit, quotedjobcallBack, requestcode);

				newJobsLayout.setVisibility(View.GONE);
				quotedLayout.setVisibility(View.VISIBLE);
			}
		});
		
		// SWIPE TO REFRESH
		
		swipetorefresh_newjob.setColorScheme(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_dark);

		swipetorefresh_newjob.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
	             
	            @Override
	            public void onRefresh() {
	                // TODO Auto-generated method stub
	            	swipetorefresh_newjob.setRefreshing(true);
	                ( new Handler()).postDelayed(new Runnable() {
	                     
	                    @Override
	                    public void run() {
	                        // TODO Auto-generated method stub
	                    	swipetorefresh_newjob.setRefreshing(false);
	                        mPreviousTotal=0;
	                    	
	                    	// pDialog = ProgressDialog.show(EmptyFixQuoteActivity.this,"","Loading..",true);	
	                		
	                         limit=10;
                    		 offset=0;
	                    		AvailableManager.getInstance().getAllNewJobs(getActivity(),
	            						CategoryId, offset, limit, newJobCallBack, requestcode);
	                    
	                    }
	                }, 3000);
	            }
	        });
		swipetorefresh_quoted.setColorScheme(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_dark);

		swipetorefresh_quoted.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
	             
	            @Override
	            public void onRefresh() {
	                // TODO Auto-generated method stub
	            	swipetorefresh_quoted.setRefreshing(true);
	                ( new Handler()).postDelayed(new Runnable() {
	                     
	                    @Override
	                    public void run() {
	                        // TODO Auto-generated method stub
	                    	swipetorefresh_quoted.setRefreshing(false);
	                        mPreviousTotal=0;
	                    	
	                    	// pDialog = ProgressDialog.show(EmptyFixQuoteActivity.this,"","Loading..",true);	
	                         limit=10;
                    		 offset=0;
	                        AvailableManager.getInstance().getAllQuotedJobs(getActivity(), CategoryId, offset, limit, quotedjobcallBack, requestcode);
	                    
	                    }
	                }, 3000);
	            }
	        });
		
		
		

		return viewroot;
	}

	public void initManager() {
		// TODO Auto-generated method stub
		newJobCallBack = new NewJobCallBack();
		quotedjobcallBack=new QuotedjobcallBack();
		updateJobStatusCallBack=new UpdateJobStatusCallBack();
		
		newJobsAdapter = new NewJobsAdapter(getActivity(),AvailableTabFragment.this,pendingNewJobsList);
		newJobsListview.setAdapter(newJobsAdapter);
		newJobsAdapter.notifyDataSetChanged();
		
		
		quotedForAdapter = new QuotedForAdapter(getActivity(),quotedList);
		quotedListview.setAdapter(quotedForAdapter);
		quotedForAdapter.notifyDataSetChanged();
		
		 quotecallback = new QuoteCallBack();

	}

	public void initView() {
		// TODO Auto-generated method stub
		newJobsListview = (ListView) viewroot.findViewById(R.id.pending_list);
		quotedListview = (ListView) viewroot.findViewById(R.id.quoted_list);
		newJobs = (ImageView) viewroot.findViewById(R.id.image_pending);
		quoted = (ImageView) viewroot.findViewById(R.id.image_quoted);
		newJobsLayout = (LinearLayout) viewroot
				.findViewById(R.id.layout_pending_list);
		quotedLayout = (LinearLayout) viewroot
				.findViewById(R.id.layout_quoted_list);
		loadingmore=(RelativeLayout) viewroot
				.findViewById(R.id.newjob_loading_more);
		loadingmorequoted=(RelativeLayout) viewroot
				.findViewById(R.id.quotedjob_loading_more);
		swipetorefresh_newjob=(SwipeRefreshLayout)viewroot.findViewById(R.id.swipetorefresh_newjob);
		swipetorefresh_quoted=(SwipeRefreshLayout)viewroot.findViewById(R.id.swipetorefresh_quoted);
	}

	public void goToFixTab() {
		// TODO Auto-generated method stub

		Intent pendingFixIntent = new Intent(getActivity(),
				EmptyFixQuoteActivity.class);
		startActivity(pendingFixIntent);

	}

	public class NewJobCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			Log.e("", "IN ONFINISH SUCCESS");
			// TODO Auto-generated method stub
			
			final AvailableBaseHolder availableBaseHolder = (AvailableBaseHolder) result;
			if (responseCode == requestcode) {
				if (UtilValidate.isNotNull(result)) {
					if (availableBaseHolder.isSuccess()) {
						if(availableBaseHolder.getData().getJobs()!=null)
						{
							if(!availableBaseHolder.getData().getJobs().isEmpty())
							{
								
								if(offset==0)
								{
									pendingNewJobsList.clear();
									pendingNewJobsList=availableBaseHolder.getData()
											.getJobs();
									newJobsAdapter = new NewJobsAdapter(getActivity(),
											AvailableTabFragment.this, pendingNewJobsList);
									newJobsListview.setAdapter(newJobsAdapter);
									if(progressDialog!=null)
									progressDialog.dismiss();
								}
								else
								{
									pendingNewJobsList.addAll(availableBaseHolder.getData()
											.getJobs());
									if(progressDialog!=null)
								progressDialog.dismiss();
								}
								
								
								if(newJobsAdapter==null)
								{
									newJobsAdapter = new NewJobsAdapter(getActivity(),
											AvailableTabFragment.this, pendingNewJobsList);
									newJobsListview.setAdapter(newJobsAdapter);
									newJobsAdapter.notifyDataSetChanged();
								}
								else
								{
									newJobsAdapter.notifyDataSetChanged();
								}
							}
							else
							{
								if(pendingNewJobsList.size()>0)
								{
									 if(newJobsAdapter==null){
											
											newJobsAdapter = new NewJobsAdapter(getActivity(),
													AvailableTabFragment.this, pendingNewJobsList);
											newJobsListview.setAdapter(newJobsAdapter);
											newJobsAdapter.notifyDataSetChanged();
									  }
									  else{
											newJobsAdapter.notifyDataSetChanged();
									  }
							}
							}
							
							if(availableBaseHolder.getData().getJobs().isEmpty()&&offset==0){
								
								pendingNewJobsList=availableBaseHolder.getData().getJobs();
								newJobsAdapter = new NewJobsAdapter(getActivity(),
										AvailableTabFragment.this, pendingNewJobsList);
								newJobsListview.setAdapter(newJobsAdapter);
								newJobsAdapter.notifyDataSetChanged();
							}
						}
						
						//pendingNewJobsList.clear();
						if(progressDialog!=null)
						progressDialog.dismiss();
						loadingmore.setVisibility(View.GONE);
						newJobsListview.setOnScrollListener(new newJobsScroll());
						
					} else {
						Toast.makeText(getActivity(),
								availableBaseHolder.isSuccess() + "",
								Toast.LENGTH_LONG).show();
						if(progressDialog!=null)
						progressDialog.dismiss();

					}
				}
			} else {

				OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
				if (UtilValidate.isNotNull(onFailureErrorHolder)) {
					Toast.makeText(
							getActivity(),
							onFailureErrorHolder.getData().get(0).getMessage()
									+ "", Toast.LENGTH_LONG).show();
					if(progressDialog!=null)
					progressDialog.dismiss();
				}

			}

			newJobsListview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					
					
					
					if(pendingNewJobsList.get(position).getId()!=null)
					{
						jobid=pendingNewJobsList.get(position).getId();
					}
					
					if(pendingNewJobsList.get(position).getReadStatus()!=null)
					{
						if(pendingNewJobsList.get(position).getReadStatus().equalsIgnoreCase("unread"))
						{
						     UpdateJobStatusManager.getInstance().updateJobStatus(getActivity(), updateJobStatusCallBack, jobid, requestcode);
						}
					}
					
					
					QuoteManager.getInstance().AddQuote(getActivity(),
							"0", jobid, quotecallback, requestcode);
					
					 pendingFixIntent = new Intent(getActivity(),
							TrackFixFragmentTabhostActivity.class);
					pendingFixIntent.putExtra("AVAILABLE_OBJECT", pendingNewJobsList.get(position));
					EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
					/*pendingFixIntent.putExtra("QUOTE_OBJECT", quoteObject);
					Log.e("","passsingggggggggggggggggggg");
					EasyHomeFixApp.setDetailsTabStatus(Constants.NEW_JOBS);
					startActivity(pendingFixIntent);*/

				}

			});
			

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

	public class QuotedjobcallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {

			// TODO Auto-generated method stub
			final AvailableBaseHolder availableBaseHolder = (AvailableBaseHolder) result;
			if (responseCode == requestcode) {
				if (UtilValidate.isNotNull(result)) {
					if (availableBaseHolder.isSuccess()) {
						//quotedList.clear();
						
						if(availableBaseHolder.getData().getJobs()!=null)
						{
							if(!availableBaseHolder.getData().getJobs().isEmpty())
								
							{
								if(offset==0)
								{
									quotedList.clear();
									quotedList=availableBaseHolder.getData().getJobs();
									quotedForAdapter = new QuotedForAdapter(getActivity(),
											 quotedList);
									quotedListview.setAdapter(quotedForAdapter);
									if(progressDialog!=null)
									progressDialog.dismiss();
								}
								else
								{
									quotedList.addAll(availableBaseHolder.getData().getJobs());
									if(progressDialog!=null)
									progressDialog.dismiss();
								}
								
								
								if(quotedForAdapter==null)
								{
									quotedForAdapter = new QuotedForAdapter(getActivity(),
											 quotedList);
									quotedListview.setAdapter(quotedForAdapter);
									quotedForAdapter.notifyDataSetChanged();
								}
								else
								{
									quotedForAdapter.notifyDataSetChanged();
								}
							}
							
							
							else if(quotedList.size()>0)
								{
									if(quotedForAdapter!=null)
									{
										quotedForAdapter = new QuotedForAdapter(getActivity(),
												 quotedList);
										quotedListview.setAdapter(quotedForAdapter);
									}
									else
									{
										quotedListview.setAdapter(quotedForAdapter);
									}
									
								}
							
							
							if(availableBaseHolder.getData().getJobs().isEmpty()&&offset==0)
							{
								quotedList=availableBaseHolder.getData().getJobs();
								quotedForAdapter = new QuotedForAdapter(getActivity(),
										 quotedList);
								quotedListview.setAdapter(quotedForAdapter);
								quotedForAdapter.notifyDataSetChanged();
							}
							
							
							if(progressDialog!=null)
							progressDialog.dismiss();
							loadingmorequoted.setVisibility(View.GONE);
							quotedListview.setOnScrollListener(new QuotedJobsScroll());
						}

					} else {
						Toast.makeText(getActivity(),
								availableBaseHolder.isSuccess() + "",
								Toast.LENGTH_LONG).show();
						if(progressDialog!=null)
						progressDialog.dismiss();

					}
				}
			} else {

				OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
				if (UtilValidate.isNotNull(onFailureErrorHolder)) {
					Toast.makeText(
							getActivity(),
							onFailureErrorHolder.getData().get(0).getMessage()
									+ "", Toast.LENGTH_LONG).show();
					if(progressDialog!=null)
					progressDialog.dismiss();
				}

			}

			quotedListview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
				
					
					
					if(UtilValidate.isNotNull(quotedList.get(position).getId()!=null))
					{
						jobid=quotedList.get(position).getId();
					}
					
					if(quotedList.get(position).getReadStatus().equalsIgnoreCase("unread"))
					{
						UpdateJobStatusManager.getInstance().updateJobStatus(getActivity(), updateJobStatusCallBack, jobid, requestcode);
					}
					
					
					Intent quotedIntent = new Intent(getActivity(),
							TrackFixFragmentTabhostActivity.class);

					EasyHomeFixApp.setDetailsTabStatus(Constants.QUOTED_JOBS);
					EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
					//quotedIntent.putExtra("QUOTED_JOBS", quotedList.get(position));
					quotedIntent.putExtra("QUOTED_OBJECT_FROM_QUOTE", quotedList.get(position));
					startActivity(quotedIntent);
				}

			});

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

	
	public class UpdateJobStatusCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			EmailSignupBaseHolder updatereadstatus=(EmailSignupBaseHolder)result;
			
			
			
			
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
	
	

	public class newJobsScroll implements OnScrollListener{

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
						offset=pendingNewJobsList.size();
						Log.e("","offset value"+offset);
						Log.e("","in available newjobscroll fragment");
						
			if(offset%10==0)
			{
				AvailableManager.getInstance().getAllNewJobs(getActivity(), CategoryId,
						offset, limit, newJobCallBack, requestcode);
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
	
	
	public class QuotedJobsScroll implements OnScrollListener{

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
				  
				  loadingmorequoted.setVisibility(View.VISIBLE);
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
				    	  Log.e("","in quoted scroll6");
						offset=quotedList.size();
					
						
			if(offset%10==0)
			{
				AvailableManager.getInstance().getAllQuotedJobs(getActivity(), CategoryId, offset,
						limit, quotedjobcallBack, requestcode);
			}
			else
			{
				loadingmorequoted.setVisibility(View.GONE);
			}
			        	isLoading = true;
			        }
			        else
			        {
			        	loadingmorequoted.setVisibility(View.GONE);
			        }
			
			
				  
			  }
			
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

						/*Toast.makeText(getActivity(),
								quotedataHolder.getData().getMessage(),
								Toast.LENGTH_LONG).show();*/
						quoteObject = quotedataHolder.getData().getQuote();
						Log.d("", "QUOTE ID"+quoteObject.getId());
						/*Intent pendingFixIntent = new Intent(getActivity(),
								TrackFixFragmentTabhostActivity.class);
						pendingFixIntent.putExtra("AVAILABLE_OBJECT", NewJoblist);
						EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);*/
						pendingFixIntent.putExtra("QUOTE_OBJECT", quoteObject);
						Log.e("","passsingggggggggggggggggggg");
						EasyHomeFixApp.setDetailsTabStatus(Constants.NEW_JOBS);
						startActivity(pendingFixIntent);
						//Utils.showDialog(getActivity(), 	quotedataHolder.getData().getMessage(),"SUCCESS");

					} else {

					/*	Toast.makeText(getActivity(),
								quotedataHolder.isSuccess() + "",
								Toast.LENGTH_LONG).show();*/
						//Utils.showDialog(getActivity(), 	"Something went wrong,please try again","SUCCESS");
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

		}

	}
	
	
}
