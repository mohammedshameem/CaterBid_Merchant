package com.xminds.easyhomefix_merchant.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.squareup.picasso.Picasso;
import com.xminds.easyfix_merchant.actionbar.BaseActivity;
import com.xminds.easyfix_merchant.circularimage.RoundTransform;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.FixerReviews;
import com.xminds.easyhomefix_merchant.Manager.ReviewManager;
import com.xminds.easyhomefix_merchant.adapter.ReviewAdapter;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.dao.UserDAO;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;

public class ReviewActivity extends BaseActivity {

	private TextView review;
	private LinearLayout back;
	private ImageView backImage;
	private ListView reviewList;
	private TextView textview_trackyourfix;
	private int requestCode = 0;
	ReviewCallBack reviewCallBack;
	private String userid;
	private List<FixerReviews> reviewlist = new ArrayList<FixerReviews>();
	ReviewAdapter reviewAdapter;
	private TextView textviewCompanyName;
	private RatingBar ratingReview;
	private TextView no_of_fixcompleted;
	private LogoutReceiver logoutReceiver;
	private ImageView image_avatar_review;
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
	private int limit = 10;
	private int offset = 0;
	private RelativeLayout loadingmore;

	public class LogoutReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("com.package.ACTION_LOGOUT")) {
				finish();
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review_page_layout);
		initViews();
		initManagers();
		setVisibilities();
		setTexts();

		// logout broadcast receiver
		logoutReceiver = new LogoutReceiver();

		// Register the logout receiver
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.package.ACTION_LOGOUT");
		registerReceiver(logoutReceiver, intentFilter);

		if (CurrentlyLoggedUserDAO.getInstance()
				.isAnyUserInCurrentlyLoggedUserTable()) {
			userid = UserDAO.getInstance().getUserDetails().getUser().getId();
			ReviewManager.getInstance().getfixerreview(ReviewActivity.this,
					reviewCallBack, userid, offset, limit, requestCode);
		}

		Picasso.with(ReviewActivity.this)
				.load((UserDAO.getInstance().getUserDetails().getUser()
						.getProfileImage()))
				.placeholder(R.drawable.profile_image_null)
				.error(R.drawable.profile_image_null)
				.transform(new RoundTransform()).into(image_avatar_review);
		// reviewList.setAdapter(new ReviewAdapter(this));
		
		if(UserDAO.getInstance().getUserDetails().getUser().getFirstName()!=null&&UserDAO.getInstance().getUserDetails().getUser().getLastName()!=null)
		{
			textviewCompanyName.setText(UserDAO.getInstance().getUserDetails().getUser().getFirstName()+" "+UserDAO.getInstance().getUserDetails().getUser().getLastName());
			
		}
		else
		{
			textviewCompanyName.setText(UserDAO.getInstance().getUserDetails().getUser().getFirstName());
		}
		
		if(UserDAO.getInstance().getUserDetails().getUser().getAvrgStarRating()>0)
		{
			ratingReview.setRating((UserDAO.getInstance().getUserDetails().getUser().getAvrgStarRating()));
			
		}
		else
		{
			ratingReview.setRating(0);
		}
		if(UserDAO.getInstance().getUserDetails().getUser().getNumberOfFixesCompleted()>0)
		{
			no_of_fixcompleted.setText(String.valueOf(UserDAO.getInstance().getUserDetails().getUser().getNumberOfFixesCompleted()));
		}
		else
		{
			no_of_fixcompleted.setText("0");
			
		}
		
			
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(EasyHomeFixApp.getNotificationcount()!=null)
			getnotificationSize(Integer.parseInt(EasyHomeFixApp.getNotificationcount()));
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}

	private void setTexts() {
		// TODO Auto-generated method stub
		textview_trackyourfix.setText(R.string.reviews);
	}

	private void setVisibilities() {
		// TODO Auto-generated method stub
		backImage.setVisibility(View.VISIBLE);
		textview_trackyourfix.setVisibility(View.VISIBLE);

	}

	private void initManagers() {
		// TODO Auto-generated method stub
		reviewCallBack = new ReviewCallBack();
		reviewAdapter = new ReviewAdapter(
				ReviewActivity.this, reviewlist);
		reviewList.setAdapter(reviewAdapter);
		reviewAdapter.notifyDataSetChanged();

	}

	private void initViews() {
		review = (TextView) findViewById(R.id.textview_trackyourfix);
		back = (LinearLayout) findViewById(R.id.back_button_layout);
		backImage = (ImageView) findViewById(R.id.image_back);
		reviewList = (ListView) findViewById(R.id.reviewList);
		textview_trackyourfix = (TextView) findViewById(R.id.textview_trackyourfix);
		no_of_fixcompleted = (TextView) findViewById(R.id.textviewTotalFix);
		textviewCompanyName = (TextView) findViewById(R.id.textviewCompanyName);
		ratingReview = (RatingBar) findViewById(R.id.ratingReview);
		image_avatar_review = (ImageView) findViewById(R.id.image_avatar_review);
		loadingmore=(RelativeLayout)findViewById(R.id.review_loading_more);
	}

	public class ReviewCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (requestCode == responseCode) {
				EmailSignupBaseHolder reviewholder = (EmailSignupBaseHolder) result;
				if (reviewholder != null) {
					if (reviewholder.isSuccess()) {
						if (reviewholder.getData() != null) {
							if (reviewholder.getData().getFixerReviews() != null) {
								//reviewlist.clear();
								reviewlist.addAll(reviewholder.getData()
										.getFixerReviews());
								if(reviewAdapter==null)
								{
									reviewAdapter = new ReviewAdapter(
											ReviewActivity.this, reviewlist);
									reviewList.setAdapter(reviewAdapter);
									reviewAdapter.notifyDataSetChanged();
								}
								else
								{
									reviewAdapter.notifyDataSetChanged();
								}
								
								if(reviewlist.size()>0)
								{
									if(reviewAdapter==null)
									{
										reviewAdapter = new ReviewAdapter(
												ReviewActivity.this, reviewlist);
										reviewList.setAdapter(reviewAdapter);
										reviewAdapter.notifyDataSetChanged();
									}
									else
									{
										reviewAdapter.notifyDataSetChanged();
									}
									
								}
								

								for (FixerReviews reviews : reviewholder
										.getData().getFixerReviews()) {
									if (reviews.getFixerId() != null ) {
										if(reviews.getFixerId().getUserId() != null ){
											textviewCompanyName.setText(reviews.getFixerId().getUserId().getFirstName()+" "+
													reviews.getFixerId().getUserId().getLastName());
										}
									} 
									if (String.valueOf(reviews.getFixerId()
											.getNumberOfFixesCompleted()) != null) {
										no_of_fixcompleted
												.setText(String
														.valueOf(reviews
																.getFixerId()
																.getNumberOfFixesCompleted()));
									} else {
										no_of_fixcompleted.setText("0");
									}
									if (String.valueOf(reviews.getFixerId()
											.getAvrgStarRating()) != null) {
										ratingReview.setRating(reviews
												.getFixerId()
												.getAvrgStarRating());
									}

								}

							}
							
							loadingmore.setVisibility(View.GONE);
							reviewList.setOnScrollListener(new reviewOnScroll());
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
				Utils.showDialog(ReviewActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(ReviewActivity.this, result, "ERROR");
			}

		}

	}

	public class reviewOnScroll implements OnScrollListener {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub

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
			Log.e("", "in on scrolll");
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
					offset = reviewlist.size();
					Log.e("", "offset in completed job" + offset);

					if (offset % 10 == 0) {

						ReviewManager.getInstance().getfixerreview(
								ReviewActivity.this, reviewCallBack, userid,
								offset, limit, requestCode);

					}
					else
					{
						loadingmore.setVisibility(View.GONE);
					}

					isLoading = true;
				} else {
					loadingmore.setVisibility(View.GONE);
				}

			}

		}

	}

}
