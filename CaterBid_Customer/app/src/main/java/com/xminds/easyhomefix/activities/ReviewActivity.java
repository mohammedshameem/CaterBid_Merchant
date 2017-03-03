package com.xminds.easyhomefix.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
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
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.pkmmte.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.JobReviews;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.Managers.ReviewManager;
import com.xminds.easyhomefix.actionbar.BaseActivity;
import com.xminds.easyhomefix.activities.EditFixDetailsActivity.LogoutReceiver;
import com.xminds.easyhomefix.adapter.ReviewAdapter;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class ReviewActivity extends BaseActivity {

	private TextView review;
	private LinearLayout back;
	private ImageView backImage;
	private ListView reviewList;
	private int requestcode = 0;
	private ReviewCallBack reviewcallback;
	private ReviewAdapter reviewadapter;
	private List<JobReviews> reviewlist = new ArrayList<JobReviews>();
	private TextView textviewCompanyName;
	private RatingBar ratingReview;
	private String companyname;
	private String rating;
	private String fixcompleted;
	private TextView textviewTotalFix;
	private String fixerid;
	private String profilepicture;
	private CircularImageView profile_image;
	private LogoutReceiver logoutReceiver;
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
	private String firstname;
	private String lastname;
	private ProgressDialog progressDialog;
	private String logintype;

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
		getIntentValue();
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
			if (UtilValidate.isNotNull(fixerid)) {
				
				 progressDialog = ProgressDialog.show(ReviewActivity.this, null,
							"Loading...");				

				ReviewManager.getInstance().getAllreviewjoblist(
						ReviewActivity.this, reviewcallback, fixerid,offset,limit,
						requestcode);
			} else {
				Toast.makeText(ReviewActivity.this, "No reviews..",
						Toast.LENGTH_SHORT).show();
			}

		} else {
			Toast.makeText(ReviewActivity.this, "please login.........",
					Toast.LENGTH_SHORT).show();
		}

		// reviewList.setAdapter(new ReviewAdapter(this,reviewlist));
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		if (UtilValidate.isNotNull(profilepicture)) {
			if(logintype.equalsIgnoreCase("facebook"))
			{
				String profileurl=profilepicture;
				if(profileurl.contains("http"))
				{
					profileurl=profileurl.replace("http", "https");
				}
				Picasso.with(ReviewActivity.this).load(profileurl)
				.into(profile_image);
			}
			else
			{
				Picasso.with(ReviewActivity.this).load(profilepicture)
				.into(profile_image);
			}
			
			
		}
		else
		{
			Picasso.with(ReviewActivity.this).load(R.drawable.profile_image_null)
			.into(profile_image);
		}
		}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(EasyHomeFixApp.getCount()!=null)
			getnotificationSize(Integer.parseInt(EasyHomeFixApp.getCount()));
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}

	private void getIntentValue() {
		// TODO Auto-generated method stub
		Intent listyourfix_intent = getIntent();
		companyname = listyourfix_intent.getStringExtra("companyname");
		rating = listyourfix_intent.getStringExtra("rating");
		fixcompleted = listyourfix_intent.getStringExtra("fixescompleted");
		fixerid = listyourfix_intent.getStringExtra("Fixerid");
		profilepicture = listyourfix_intent.getStringExtra("ProfileImage");
		firstname=listyourfix_intent.getStringExtra("firstname");
		lastname=listyourfix_intent.getStringExtra("lastname");
		logintype=listyourfix_intent.getStringExtra("LoginType");
		Log.e("","firstname"+firstname);
		Log.e("","lastname"+lastname);
		
		

	}

	public class ReviewCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (responseCode == requestcode) {
				EmailSignupBaseHolder reviewitems = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(reviewitems)) {
					if (reviewitems.isSuccess()) {
						{
							if (UtilValidate.isNotNull(reviewitems.getData())) {

								if (UtilValidate.isNotNull(reviewitems
										.getData().getFixerReviews())) {

									if (UtilValidate.isNotEmpty(reviewitems
											.getData().getFixerReviews())) {
										// reviewlist.clear();
										
										reviewlist.addAll(reviewitems.getData()
												.getFixerReviews());
										
										if(reviewadapter==null)
										{
											reviewadapter = new ReviewAdapter(
													ReviewActivity.this, reviewlist);
											reviewList.setAdapter(reviewadapter);
											reviewadapter.notifyDataSetChanged();
											progressDialog.dismiss();
										}
										else
										{
											reviewadapter.notifyDataSetChanged();
											progressDialog.dismiss();
										}
										
									}
									else
									{
										if(reviewlist.size()>0)
										{
											if(reviewadapter==null)
											{
												reviewadapter = new ReviewAdapter(
														ReviewActivity.this, reviewlist);
												reviewList.setAdapter(reviewadapter);
												reviewadapter.notifyDataSetChanged();
												progressDialog.dismiss();
											}
											else
											{
												reviewadapter.notifyDataSetChanged();
												progressDialog.dismiss();
											}
										}
									}
									
									loadingmore.setVisibility(View.GONE);
									reviewList.setOnScrollListener(new reviewOnScroll());
									progressDialog.dismiss();
								}
							} else {
								Toast.makeText(ReviewActivity.this,
										"No Reviews.....", Toast.LENGTH_LONG)
										.show();
								progressDialog.dismiss();

							}

						}

					} else {
						Toast.makeText(ReviewActivity.this,
								reviewitems.isSuccess() + "", Toast.LENGTH_LONG)
								.show();
						progressDialog.dismiss();
					}
				}
			} else {

			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();

			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				//Utils.showDialog(ReviewActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(ReviewActivity.this, ""+result, "ERROR");
			}
			
			
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();

		}

	}

	private void setTexts() {
		// TODO Auto-generated method stub
		review.setText(R.string.reviews);
		if ((firstname!="")&&(lastname!="")) {
			textviewCompanyName.setText(firstname+" "+lastname );
		} else if(firstname!=""){
			textviewCompanyName.setText(""+firstname);
		}
		else
		{
			textviewCompanyName.setText("");
		}
		if (UtilValidate.isNotNull(rating)) {
			ratingReview.setRating(Float.parseFloat(rating));
		}

		if (UtilValidate.isNotNull(fixcompleted)) {
			textviewTotalFix.setText("" + fixcompleted);
		}
		else
		{
			textviewTotalFix.setText("0");
		}

	}

	private void setVisibilities() {
		// TODO Auto-generated method stub
		backImage.setVisibility(View.VISIBLE);
		review.setVisibility(View.VISIBLE);

	}

	private void initManagers() {
		// TODO Auto-generated method stub
		reviewcallback = new ReviewCallBack();
		reviewadapter = new ReviewAdapter(
				ReviewActivity.this, reviewlist);
		reviewList.setAdapter(reviewadapter);
		reviewadapter.notifyDataSetChanged();
	}

	private void initViews() {
		review = (TextView) findViewById(R.id.textview_trackyourfix);
		back = (LinearLayout) findViewById(R.id.back_button_layout);
		backImage = (ImageView) findViewById(R.id.image_back);
		textviewCompanyName = (TextView) findViewById(R.id.textviewCompanyName);
		ratingReview = (RatingBar) findViewById(R.id.ratingReview);
		reviewList = (ListView) findViewById(R.id.reviewList);
		textviewTotalFix = (TextView) findViewById(R.id.textviewTotalFix);
		profile_image = (CircularImageView) findViewById(R.id.image_avatar_review);
		loadingmore=(RelativeLayout)findViewById(R.id.review_loading_more);
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

						ReviewManager.getInstance().getAllreviewjoblist(
								ReviewActivity.this, reviewcallback, fixerid,offset,limit,
								requestcode);

					}

					isLoading = true;
				} else {
					loadingmore.setVisibility(View.GONE);
				}

			}

		}

	}

}
