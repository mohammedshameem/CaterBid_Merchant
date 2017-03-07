package com.xminds.easyhomefix.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix.Holder.JobCategories;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.JobSubCategoriesBaseHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.Managers.CategoriesManager;
import com.xminds.easyhomefix.Managers.DummyUserManager;
import com.xminds.easyhomefix.Managers.ImageUploadManager;
import com.xminds.easyhomefix.Managers.JobCreationManager;
import com.xminds.easyhomefix.actionbar.BaseActivity;
import com.xminds.easyhomefix.adapter.CamImageSetAdapter;
import com.xminds.easyhomefix.adapter.SubcategoriesAdpater;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.constants.Constants;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.dao.UserDAO;
import com.xminds.easyhomefix.horizontallistview.HorizontalListView;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class ConfirmOrderActivity extends BaseActivity {
	private List<JobCategories> jobsubcategories=new ArrayList<JobCategories>();
	private static List<File> imageList = new ArrayList<File>();
	private static final int SELECT_PHOTO = 100;
	private static final int REQUEST_CODE = 10;
	private final int OTP_REQUEST_CODE = 200;
	private ImageView image_back;
	private TextView textview_trackyourfix;
	private LinearLayout linear_insert;
	private LinearLayout linear_findfixers;
	private EditText edittext_datepicker;
	private EditText edittext_postalcode;
	private EditText edittext_jobdetails;
	private Spinner spinner_jobsubcategory;
	private JobCreationCallBack jobCreationCallBack;
	private JobSubCategoriesCallBack jobSubCategoriesCallBack;
	private SubcategoriesAdpater subcategoryadapter;
	private LogoutReceiver logoutReceiver;
	private String[] result=null;
	private ProgressDialog progressDialog;
	private String JOB = "JOB";
	private JobCreationBaseHolder jobCreationBaseHolder;
	private UploadImagesCallBack uploadImagescallback;
	private DummyUserSignUpCallBack dummyUserSignUpCallBack;
	private EmailSignupBaseHolder emailSignupBaseHolder;
	private String listfix_date;
	private String postalcode;
	private String catid;
	private String formatedaddress;
	private String floorNo;
	private String unitNo;
	private String jobid;
	private String timeString;
	private ProgressDialog pDialog;
	private int requestcode = 0;
	private HorizontalListView horizontalListView;
	private Uri fileUri;
	private static final String IMAGE_DIRECTORY_NAME = "EasyHomeFix";
	private static List<String> camImageList = new ArrayList<String>();
	private CamImageSetAdapter imageSettingAdapter;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_order);
		initViews();
		initManagers();
		setVisibilities();
		setTexts();
		
		camImageList.clear();
		imageList.clear();
		//logout broadcast receiver
		
		 logoutReceiver = new LogoutReceiver();
		  
	    // Register the logout receiver
		 
	    IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction("com.package.ACTION_LOGOUT");
	    registerReceiver(logoutReceiver, intentFilter);
		Intent listyourfix_intent = getIntent();
		listfix_date = listyourfix_intent.getStringExtra("Date");
		timeString = listyourfix_intent.getStringExtra("Time");
		postalcode= listyourfix_intent.getStringExtra("postalcode");
		catid=listyourfix_intent.getStringExtra("catid");
		formatedaddress=listyourfix_intent.getStringExtra("formatedaddress");
		floorNo=listyourfix_intent.getStringExtra("floorNo");
		unitNo=listyourfix_intent.getStringExtra("unitNo");
		/*if(listyourfix_intent.hasExtra(floorNo)){
			floorNo = listyourfix_intent.getStringExtra("floorNo");
		}
		
		if(listyourfix_intent.hasExtra(unitNo)){
			unitNo = listyourfix_intent.getStringExtra("unitNo");
		}*/
		
		edittext_datepicker.setText(listfix_date+" "+timeString);
		edittext_datepicker.setEnabled(false);
		edittext_postalcode.setText(formatedaddress);
		edittext_postalcode.setEnabled(false);
		
		
		CategoriesManager.getInstance().getSubCategories(
				ConfirmOrderActivity.this, jobSubCategoriesCallBack,catid,requestcode);
		
		
		imageSettingAdapter = new CamImageSetAdapter(ConfirmOrderActivity.this,camImageList);
        horizontalListView.setAdapter(imageSettingAdapter);
        
		spinner_jobsubcategory.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				 jobid=jobsubcategories.get(position).getId();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		linear_insert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopup();
			}
		});

		image_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		linear_findfixers.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(edittext_jobdetails.getText().toString().length()>0)
				{
					
					if(CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable())
					{
						
						
						JobCreationManager.getInstance().jobCreation(ConfirmOrderActivity.this, postalcode, 
								edittext_jobdetails.getText().toString(),formatedaddress, jobid, listfix_date+" "+timeString,
								floorNo, unitNo, jobCreationCallBack, requestcode);
					}
					else
					{	
						
						String android_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
						DummyUserManager.getInstance().createDummyUser(ConfirmOrderActivity.this,
								android_id, "Customer", "User", dummyUserSignUpCallBack, requestcode);
					}
				}
				else
				{
					Utils.showDialog(ConfirmOrderActivity.this, "Please enter your job details, more information will help our fixers to quote more accurately.", "ERROR");
				}
				
				
				
			}
		});

		horizontalListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
				camImageList.remove(position);
				imageList.remove(position);
				imageSettingAdapter.notifyDataSetChanged();
			}
		});
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}
	
	private void setTexts() {
		// TODO Auto-generated method stub
		textview_trackyourfix.setText("Confirm Order");
	}
	
	public class JobSubCategoriesCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			
			if (pDialog != null) {
				pDialog.dismiss();
			}
			
			if (responseCode == requestcode) {
				
				JobSubCategoriesBaseHolder jobSubCategoriesBaseHolder = (JobSubCategoriesBaseHolder) result;
				
				if (UtilValidate.isNotNull(jobSubCategoriesBaseHolder)) {
					
					if (jobSubCategoriesBaseHolder.isSuccess()) {
						
						EasyHomeFixApp.setJobcategories(jobSubCategoriesBaseHolder.getData());
					    EasyHomeFixApp.setJobsubcategories(null);
						jobsubcategories.clear();
						jobsubcategories=jobSubCategoriesBaseHolder.getData();
						EasyHomeFixApp.setJobsubcategories(jobsubcategories);
						subcategoryadapter = new SubcategoriesAdpater(ConfirmOrderActivity.this, jobsubcategories);
						spinner_jobsubcategory.setAdapter(subcategoryadapter);
						subcategoryadapter.notifyDataSetChanged();

					}

					else {
					}
				}

			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			
			if (pDialog != null) {
				pDialog.dismiss();
			}
			
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				//Utils.showDialog(ConfirmOrderActivity.this, "Please check your internet connection.", "ERROR");
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
	
	private void initManagers() {
		// TODO Auto-generated method stub
		jobCreationCallBack=new JobCreationCallBack();
		jobSubCategoriesCallBack=new JobSubCategoriesCallBack();
		jobCreationBaseHolder=new JobCreationBaseHolder();
		uploadImagescallback=new UploadImagesCallBack();
		dummyUserSignUpCallBack=new DummyUserSignUpCallBack();
	}

	private void setVisibilities() {
		image_back.setVisibility(View.VISIBLE);
		textview_trackyourfix.setVisibility(View.VISIBLE);

	}

	private void initViews() {

		image_back = (ImageView) findViewById(R.id.image_back);
		textview_trackyourfix = (TextView) findViewById(R.id.textview_trackyourfix);
		linear_insert = (LinearLayout) findViewById(R.id.linear_insert);
		edittext_datepicker = (EditText) findViewById(R.id.datepicker);
		edittext_postalcode = (EditText) findViewById(R.id.postalcode);
		linear_findfixers = (LinearLayout) findViewById(R.id.linear_findfixers);
		edittext_jobdetails=(EditText)findViewById(R.id.edittext_jobdetails);
		spinner_jobsubcategory=(Spinner)findViewById(R.id.spinner_jobsubcategory);
		horizontalListView = (HorizontalListView) findViewById(R.id.listview_imageup);
	}

	private void showPopup() {
		
		LayoutInflater layoutInflater = (LayoutInflater) ConfirmOrderActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.insert_popup, null);
		final PopupWindow popupWindow = new PopupWindow(popupView,LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, true);
		popupWindow.update();
		popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
		final LinearLayout linear_photo_lib = (LinearLayout) popupView.findViewById(R.id.linear_photo_lib);
		final LinearLayout linear_cancel = (LinearLayout) popupView.findViewById(R.id.linear_cancel);
		final LinearLayout linear_takephoto = (LinearLayout) popupView.findViewById(R.id.linear_takephoto);
		
		linear_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		
		linear_photo_lib.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(camImageList.size() < 4){
					Intent intent = new Intent(ConfirmOrderActivity.this,
							MutipleImagePickMainActivity.class);
					startActivityForResult(intent, REQUEST_CODE);
					popupWindow.dismiss();
				}else{
					Toast.makeText(ConfirmOrderActivity.this, "Maximum 4 pictures can be uploaded", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		linear_takephoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(camImageList.size() < 4){
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);	
					fileUri = Uri.fromFile(getOutputMediaFile());
					intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
					startActivityForResult(intent, SELECT_PHOTO);
					popupWindow.dismiss();
				}else{
					Toast.makeText(ConfirmOrderActivity.this, "Maximum 4 pictures can be uploaded", Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}

	
	public class JobCreationCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if (responseCode == requestcode) {
				
				jobCreationBaseHolder = (JobCreationBaseHolder) result;
				
				if (UtilValidate.isNotNull(jobCreationBaseHolder)) {
					
					if (jobCreationBaseHolder.isSuccess()) {
						
					if(UtilValidate.isNotNull(jobCreationBaseHolder.getData())){
						
						if(UtilValidate.isNotNull(jobCreationBaseHolder.getData().getJob())){
							
							if(UtilValidate.isNotNull(jobCreationBaseHolder.getData().getJob().getId())){
								 
								 if(UtilValidate.isNotEmpty(imageList)){
									 
									 progressDialog=ProgressDialog.show(ConfirmOrderActivity.this, null, "Creating Job...");
									 
									 for(int i=0;i<imageList.size();i++){
										 
										 ImageUploadManager.getInstance().uploadImages(ConfirmOrderActivity.this,JOB, 
									    		 jobCreationBaseHolder.getData().getJob().getId(), imageList.get(i), uploadImagescallback, requestcode,i);
									  }
							        }
								 
								 else{
									Toast.makeText(ConfirmOrderActivity.this, "Fix created successfully", Toast.LENGTH_LONG).show();
									EasyHomeFixApp.setCategoryNameDefaultTab(Constants.PENDING);
									Intent in=new Intent(ConfirmOrderActivity.this,TrackYourFixFragmentTabHostActivity.class);
									in.putExtra("jobId", jobCreationBaseHolder.getData().getJob());
									in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(in);
									finish();
							 }
					      }
						}
					}
				}

					else 
					{

						Toast.makeText(ConfirmOrderActivity.this,jobCreationBaseHolder.isSuccess() + "",	Toast.LENGTH_LONG).show();

					}
				}

			}
			else {

				OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
				
				if (UtilValidate.isNotNull(onFailureErrorHolder)) {
					
					if (UtilValidate.isNotNull(onFailureErrorHolder.getData())) {
						
						Toast.makeText(ConfirmOrderActivity.this,"?" + onFailureErrorHolder.getData(),Toast.LENGTH_LONG).show();
						
						for (ErrorMessageListHolder error : onFailureErrorHolder.getData()) {

							if (error.getField().equalsIgnoreCase("postalCode")) {
								edittext_postalcode.setText("");
								edittext_postalcode.setTextSize(14);
								edittext_postalcode.setHintTextColor(ConfirmOrderActivity.this
										.getResources().getColor(R.color.Red));
								edittext_postalcode.setHint(error.getMessage());
							}
							
							if (error.getField().equalsIgnoreCase("jobDate")) {
								
								edittext_datepicker.setText("");
								edittext_datepicker.setTextSize(14);
								edittext_datepicker.setHintTextColor(ConfirmOrderActivity.this
										.getResources().getColor(R.color.Red));
								edittext_datepicker.setHint(error.getMessage());
							}
	if (error.getField().equalsIgnoreCase("jobDetails")) {
								
		edittext_jobdetails.setText("");
		edittext_jobdetails.setTextSize(14);
		edittext_jobdetails.setHintTextColor(ConfirmOrderActivity.this
										.getResources().getColor(R.color.Red));
		edittext_jobdetails.setHint(error.getMessage());
							}
							
						}
					}

				}
			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				Utils.showDialog(ConfirmOrderActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(ConfirmOrderActivity.this, ""+result, "ERROR");
			}

		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}

	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {

		Log.e("++++++++++++++++++++++++++", "###### ########  @ Intent Result");
		
		 if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			      if (imageReturnedIntent.hasExtra("all_path")) {
			        result = imageReturnedIntent.getExtras().getStringArray("all_path");
			        if(result != null){
				        for (int i = 0; i < result.length; i++) {
							Log.e("", "###### ######## Image Path of image @ Intent "+i+" is "+result[i]);
							if(camImageList.size() < 4 && imageList.size() < 4){
								File image=new File(Utils.compressImage(result[i],ConfirmOrderActivity.this));
								imageList.add(image);
								camImageList.add(Utils.compressImage(result[i],ConfirmOrderActivity.this));
								imageSettingAdapter.notifyDataSetChanged();
							}else{
								Toast.makeText(ConfirmOrderActivity.this, "Maximum 4 pictures can be uploaded", Toast.LENGTH_LONG).show();
							}
						}
			        }
			      }
		 }
		 
		 if(resultCode == RESULT_OK && requestCode == OTP_REQUEST_CODE){
			 Log.e("OTP_REQUEST_CODE", "OTP_REQUEST_CODE : "+OTP_REQUEST_CODE);
			 if(emailSignupBaseHolder != null){
				 UserDAO.getInstance().InsertUserDetails(
							emailSignupBaseHolder);
					CurrentlyLoggedUserDAO.getInstance()
							.InsertCurrentlyloggedUserDetails(
									emailSignupBaseHolder);
			 }
			 Log.e("++++++++++++++++++++++++++", "From DAO UDID : "+CurrentlyLoggedUserDAO.getInstance().getUdId());
				Log.e("++++++++++++++++++++++++++", "From DAO LOGINTYPE : "+CurrentlyLoggedUserDAO.getInstance().getLoginType());
			 JobCreationManager.getInstance().jobCreation(ConfirmOrderActivity.this, postalcode, 
						edittext_jobdetails.getText().toString(),formatedaddress, jobid, listfix_date+" "+timeString,floorNo,unitNo,
						jobCreationCallBack, requestcode);
		 }
		 
		 if(resultCode == RESULT_OK && requestCode == SELECT_PHOTO){
			 Log.e("!!!!!!!!!!!", "fileUri.toString():"+fileUri.getPath());
			 
			 
			 File image=new File(Utils.compressImage(fileUri.getPath(),ConfirmOrderActivity.this));
			 camImageList.add(Utils.compressImage(fileUri.getPath(),ConfirmOrderActivity.this));
				imageList.add(image);
				imageSettingAdapter.notifyDataSetChanged();
		 }
		    
	}
	
	private class UploadImagesCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			
			if (pDialog != null) {
				pDialog.dismiss();
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if (pDialog != null) {
				pDialog.dismiss();
			}
			
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				Utils.showDialog(ConfirmOrderActivity.this, "Please check your internet connection.", "ERROR");
			}

		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			
			if (pDialog != null) {
				pDialog.dismiss();
			}
			// TODO Auto-generated method stub
			if(UtilValidate.isNotNull(imageList)){
			if(i==imageList.size()-1){
				progressDialog.dismiss();
				Toast.makeText(ConfirmOrderActivity.this, "Fix created successfully", Toast.LENGTH_LONG).show();	
				Intent in=new Intent(ConfirmOrderActivity.this,LandingPageActivity.class);
				in.putExtra("jobId", jobCreationBaseHolder.getData().getJob());
				in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
				progressDialog.dismiss();
				finish();
			     }
			}
		}
		
	}
	
	private class DummyUserSignUpCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			if(result != null){
				emailSignupBaseHolder = (EmailSignupBaseHolder) result;
				if(emailSignupBaseHolder != null){
					if(emailSignupBaseHolder.isSuccess()){
						if(emailSignupBaseHolder.getData() != null){
							UserDAO.getInstance().deleteAllUserRows();
							CurrentlyLoggedUserDAO.getInstance()
									.deleteAllUserRows();
							UserDAO.getInstance().InsertUserDetails(emailSignupBaseHolder);
							CurrentlyLoggedUserDAO.getInstance().InsertDeviceToken(EasyHomeFixApp.getGcmRegId(),
									emailSignupBaseHolder.getData().getUser().getId());
							
							if(emailSignupBaseHolder.getData().getUser() != null){
								if(emailSignupBaseHolder.getData().getUser().getMobileVerified() != null){
									if(emailSignupBaseHolder.getData().getUser().getMobileVerified() == 0){
										Log.e("++++++++++++++++++++++++++", "Mob Varified : "+emailSignupBaseHolder.getData().getUser().getMobileVerified());
										Intent intent = new Intent(ConfirmOrderActivity.this,
												EnterOTPActivity.class);
										intent.putExtra("fromConfirmOrder", "fromConfirmOrder");
										startActivityForResult(intent, OTP_REQUEST_CODE);
									}else{
										Log.e("++++++++++++++++++++++++++", "Mob Varified : "+emailSignupBaseHolder.getData().getUser().getMobileVerified());
										Log.e("++++++++++++++++++++++++++", "UserId : "+CurrentlyLoggedUserDAO.getInstance().getUserId());
										CurrentlyLoggedUserDAO.getInstance().InsertCurrentlyloggedUserDetails(emailSignupBaseHolder);
										JobCreationManager.getInstance().jobCreation(ConfirmOrderActivity.this, postalcode, 
												edittext_jobdetails.getText().toString(),formatedaddress, jobid, listfix_date+" "+timeString,floorNo,unitNo,
												jobCreationCallBack, requestcode);
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
			
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				Utils.showDialog(ConfirmOrderActivity.this, "Please check your internet connection.", "ERROR");
			}

			
		}

		@Override
		public void onFinish(int requestcode, JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private File getOutputMediaFile() {
		 // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
 Log.e("&&&&&&&&&&&&&&&&&", "1111");
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_EHF_" + timeStamp + ".jpg");
		
        return mediaFile;
	}
	
	
	
	

}
