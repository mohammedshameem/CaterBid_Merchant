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
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.JobList;
import com.xminds.easyhomefix.Holder.JobListStatusHolder;
import com.xminds.easyhomefix.Managers.JobListManager;
import com.xminds.easyhomefix.Managers.QuotesManager;
import com.xminds.easyhomefix.Managers.UpdateJobStatusManager;
import com.xminds.easyhomefix.activities.EmptyFixQuoteActivity;
import com.xminds.easyhomefix.activities.ListYourFixesActivity;
import com.xminds.easyhomefix.activities.ReviewActivity;
import com.xminds.easyhomefix.activities.TrackFixFragmentTabhostActivity;
import com.xminds.easyhomefix.adapter.PendingAdapter;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.constants.Constants;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class PendingTabTrackYourFixFragment extends Fragment {

	private View viewroot;
	private ListView pendingListview;
	private List<JobList> pendingList = new ArrayList<JobList>();
	private PendingAdapter pendingAdapter;
	private int requestedCode = 0;
	private PendingCallBackClass pendingCallBack;
	private LinearLayout emptyPendingLayout;
	private String jobId;
	private String catId;
	private String jobTypeId;
	private String jobid;
	private String jobdetails;
	private String block_number;
	private String floor;
	private String unit;
	private String road_building;
	private String postal_code;
	private String jobdateandtime;
	private List<String> CustomerImageList = new ArrayList<String>();
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
	private int limit = 10;
	private int mVisibleThreshold = 5;
	private SwipeRefreshLayout swipetorefresh;
	private ProgressDialog progressDialog;
	
	 private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			JobListManager.getInstance().getPendingList(getActivity(), offset,
					limit, pendingCallBack, requestedCode);
		}
	};

	@Override
	public void onResume() {
		
		// for auto refresh
		getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("nofication_received"));
		// limit=pendingList.size();
		limit = 10;
		pendingList.clear();
		offset = pendingList.size();

		pendingAdapter = new PendingAdapter(getActivity(),
				PendingTabTrackYourFixFragment.this, pendingList);
		pendingListview.setAdapter(pendingAdapter);
		pendingAdapter.notifyDataSetChanged();

		mPreviousTotal = 0;
		if (CurrentlyLoggedUserDAO.getInstance()
				.isAnyUserInCurrentlyLoggedUserTable()) {
			progressDialog = ProgressDialog.show(getActivity(), null,
					"Loading...");
			progressDialog.setCancelable(true);
			JobListManager.getInstance().getPendingList(getActivity(), offset,
					limit, pendingCallBack, requestedCode);
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

		viewroot = inflater.inflate(R.layout.pending_tab_layout, null);

		initView();
		initManager();

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
								mPreviousTotal = 0;
								JobListManager.getInstance().getPendingList(
										getActivity(), offset, limit,
										pendingCallBack, requestedCode);
							}
						}, 3000);
					}
				});

		return viewroot;
	}

	public void initManager() {
		// TODO Auto-generated method stub
		pendingCallBack = new PendingCallBackClass();
		updateJobStatusCallBack = new UpdateJobStatusCallBack();

		pendingAdapter = new PendingAdapter(getActivity(),
				PendingTabTrackYourFixFragment.this, pendingList);
		pendingListview.setAdapter(pendingAdapter);
		pendingAdapter.notifyDataSetChanged();

	}

	public void initView() {
		// TODO Auto-generated method stub
		pendingListview = (ListView) viewroot.findViewById(R.id.pending_list);
		emptyPendingLayout = (LinearLayout) viewroot
				.findViewById(R.id.empty_pending_page);
		loadingmore = (RelativeLayout) viewroot
				.findViewById(R.id.pending_loading_more);
		swipetorefresh = (SwipeRefreshLayout) viewroot
				.findViewById(R.id.swipetorefresh_pending);
	}

	public void goToFixTab(String job_id) {
		// TODO Auto-generated method stub

		/*
		 * Intent pendingFixIntent = new
		 * Intent(getActivity(),EmptyFixQuoteActivity.class);
		 * pendingFixIntent.putExtra("jobId",jobid);
		 * startActivity(pendingFixIntent);
		 */
		// jobId=job_id;

	}

	public class PendingCallBackClass implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			final JobListStatusHolder jobListStatusHolder = (JobListStatusHolder) result;
			if (UtilValidate.isNotNull(jobListStatusHolder)) {

				if (UtilValidate.isNotNull(jobListStatusHolder.getData())) {

					if (UtilValidate.isNotEmpty(jobListStatusHolder.getData()
							.getJobs())) {
						pendingListview.setVisibility(View.VISIBLE);
						emptyPendingLayout.setVisibility(View.GONE);
						Log.e("", "offset value in call back" + offset);

						// pendingList.clear();
						if (offset == 0) 
						{
							pendingList.clear();
							pendingList = jobListStatusHolder.getData()
									.getJobs();
							pendingAdapter = new PendingAdapter(getActivity(),
									PendingTabTrackYourFixFragment.this,
									pendingList);
							pendingListview.setAdapter(pendingAdapter);
							progressDialog.dismiss();
							
						} 
						else 
						{
							pendingList.addAll(jobListStatusHolder.getData()
									.getJobs());
							progressDialog.dismiss();
						}

						if (pendingAdapter == null) {

							pendingAdapter = new PendingAdapter(getActivity(),
									PendingTabTrackYourFixFragment.this,
									pendingList);
							pendingListview.setAdapter(pendingAdapter);
							pendingAdapter.notifyDataSetChanged();
						} 
						else 
						{
							pendingAdapter.notifyDataSetChanged();
							Log.e("","notifyyyyyyyyyyy");
						}
					}

					else {
						if (pendingList.size() > 0) {
							if (pendingAdapter == null) {

								pendingAdapter = new PendingAdapter(
										getActivity(),
										PendingTabTrackYourFixFragment.this,
										pendingList);
								pendingListview.setAdapter(pendingAdapter);
								pendingAdapter.notifyDataSetChanged();
							} 
							else 
							{
								pendingAdapter.notifyDataSetChanged();
							}

						} 
						else 
						{
							pendingListview.setVisibility(View.GONE);
							emptyPendingLayout.setVisibility(View.VISIBLE);

						}

					}
					
					progressDialog.dismiss();

					pendingListview
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									// TODO Auto-generated method stub

									if (UtilValidate.isNotNull(pendingList.get(
											position).getId())) {
										jobid = pendingList.get(position)
												.getId();

									}

									if (UtilValidate.isNotNull(pendingList.get(
											position).getReadStatus())) {

										if (pendingList.get(position)
												.getReadStatus()
												.equalsIgnoreCase("unread")) {

											UpdateJobStatusManager
													.getInstance()
													.updateJobStatus(
															getActivity(),
															updateJobStatusCallBack,
															jobid,
															requestedCode);

										}

									}

									goToTabFix(pendingList.get(position));

								}

							});
				}
				Log.e("","position of scroll");
				loadingmore.setVisibility(View.GONE);
				pendingListview.setOnScrollListener(new pendingScroll());
			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
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
			//Toast.makeText(getActivity(), "please check your internet connection", Toast.LENGTH_LONG).show();

		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub

		}

	}

	public class UpdateJobStatusCallBack implements AsyncTaskCallBack {

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

	public class pendingScroll implements OnScrollListener {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			// int limit=10;

			currentScrollState = scrollState;
			isScrollCompleted(currentFirstVisibleItem, currentVisibleItemCount,
					currenttotalItemCount);
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
			if (currentVisibleItemCount > 0
					&& currentScrollState == SCROLL_STATE_IDLE) {

				loadingmore.setVisibility(View.VISIBLE);
				if (currenttotalItemCount > mPreviousTotal) {

					isLoading = false;
					mPreviousTotal = currenttotalItemCount;
					mCurrentPage++;

					if (mCurrentPage + 1 > 50) {
						mLastPage = true;
					}

				}
				if (!mLastPage
						&& !isLoading
						&& (currenttotalItemCount - currentVisibleItemCount) <= (currentFirstVisibleItem + mVisibleThreshold)) {
					limit = 10;
					offset = pendingList.size();
					if (offset % 10 == 0) {

						JobListManager.getInstance().getPendingList(
								getActivity(), offset, limit, pendingCallBack,
								requestedCode);
					}
					
					 else {
							loadingmore.setVisibility(View.GONE);
						}
					isLoading = true;
					
				} else {
					loadingmore.setVisibility(View.GONE);
				}

			}

		}

	}

	public void goToTabFix(JobList pendingList) {
		// TODO Auto-generated method stub
		Log.d("", "INSIDE TABFIX");
		Intent pendingFixIntent = new Intent(getActivity(),
				EmptyFixQuoteActivity.class);
		EasyHomeFixApp.setCategoryNameDefaultTab(Constants.DETAILS);
		EasyHomeFixApp.setDetailsTabStatus(Constants.FROM_PENDING);
		// fixIntent.putExtra(Constants.ONGOING, "ongoing");

		pendingFixIntent.putExtra("PENDINGOBJECT", pendingList);
		getActivity().startActivity(pendingFixIntent);

	}

}
