package com.xminds.easyhomefix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Managers.ReviewManager;
import com.xminds.easyhomefix.activities.EditFixDetailsActivity.LogoutReceiver;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.constants.Constants;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class ReviewServiceActivity extends Activity {

	private TextView review;
	private ImageView closeImage;
	private ImageView avatarImage;
	private int start_rating;
	private String reviewdetails;
	private EditText edittext_review;
	private RatingBar ratingbar_review_rating;
	private Button submit;
	private int requestcode = 0;
	ReviewAddCallBack reviewAddCallBack;
	private boolean ratingvalidationflag = false;
	private boolean reviewvalidationflag = false;
	private String jobid;
	private ProgressDialog pDialog;
	private LogoutReceiver logoutReceiver;
	public class LogoutReceiver extends BroadcastReceiver {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        if (intent.getAction().equals("com.package.ACTION_LOGOUT")) {
	            finish();
	        }
	    }
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review_service_layout);
		initViews();
		initManager();
		getIntentValue();
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		review.setText(R.string.review);
		review.setVisibility(View.VISIBLE);
		closeImage.setVisibility(View.VISIBLE);
		avatarImage.setVisibility(View.GONE);

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (UtilValidate
						.isNotNull(edittext_review.getText().toString())) {
					if(UtilValidate.isNotEmpty(edittext_review.getText().toString()))
					{
						reviewdetails = edittext_review.getText().toString();
						ratingvalidationflag = true;
					}
					else
					{
						Toast.makeText(ReviewServiceActivity.this,
								"Review cannot be blank", Toast.LENGTH_LONG).show();
					}
					}

				else {
					Toast.makeText(ReviewServiceActivity.this,
							"Review cannot be blank", Toast.LENGTH_LONG).show();
				}
				if (((int) ratingbar_review_rating
						.getRating()>0)) {
					start_rating = (int) ratingbar_review_rating.getRating();
					reviewvalidationflag = true;

				} else {
					Toast.makeText(ReviewServiceActivity.this,
							"Star cannot be blank.", Toast.LENGTH_LONG)
							.show();

				}

				if (ratingvalidationflag && reviewvalidationflag){
					
					pDialog = ProgressDialog.show(
							ReviewServiceActivity.this, "",
							"Loading..", true);
					
					ReviewManager.getInstance().addReview(
							ReviewServiceActivity.this, reviewdetails,
							start_rating, reviewAddCallBack, jobid, requestcode);
					
					/*Intent in = new Intent(ReviewServiceActivity.this,
							TrackYourFixFragmentTabHostActivity.class);
					EasyHomeFixApp.setCategoryNameDefaultTab(Constants.COMPLETED);
					startActivity(in);*/
					
				}

			}
		});

		closeImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
	unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}

	private void getIntentValue() {
		// TODO Auto-generated method stub
		
		jobid=getIntent().getStringExtra("jobid");	
		
	}

	private void initManager() {
		// TODO Auto-generated method stub
		reviewAddCallBack = new ReviewAddCallBack();

	}

	private void initViews() {
		review = (TextView) findViewById(R.id.textview_trackyourfix);
		closeImage = (ImageView) findViewById(R.id.image_close);
		avatarImage = (ImageView) findViewById(R.id.image_avatar);
		edittext_review = (EditText) findViewById(R.id.editTextReviewService);
		ratingbar_review_rating = (RatingBar) findViewById(R.id.ratingReviewService);
		submit = (Button) findViewById(R.id.buttonReviewServiceSubmit);
	}

	public class ReviewAddCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if (requestcode == responseCode) {
				EmailSignupBaseHolder addreviewholder = (EmailSignupBaseHolder) result;
				if (addreviewholder.isSuccess()) {
					
					Intent in = new Intent(ReviewServiceActivity.this,
							TrackYourFixFragmentTabHostActivity.class);
					EasyHomeFixApp.setCategoryNameDefaultTab(Constants.COMPLETED);
					in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					startActivity(in);
					finish();
				}
				else{
					Utils.showDialog(ReviewServiceActivity.this, addreviewholder.getData().getMessage(), "ERROR");
				}

			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if (pDialog != null) {
				pDialog.dismiss();
			}
			
			if(result.contains("Sorry , You have already added review for this job."))
			{
				Utils.showDialog(ReviewServiceActivity.this, "Sorry , You have already added review for this job. ", "ERROR");
			}
			
			else if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(ReviewServiceActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(ReviewServiceActivity.this, "Please try again.", "ERROR");
			}
			
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			if (pDialog != null) {
				pDialog.dismiss();
			}
		}

	}
}
