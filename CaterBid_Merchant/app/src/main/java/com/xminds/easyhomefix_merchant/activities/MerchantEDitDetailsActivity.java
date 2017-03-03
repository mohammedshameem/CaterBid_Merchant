package com.xminds.easyhomefix_merchant.activities;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.pkmmte.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix_merchant.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix_merchant.Holder.UserTokenHolder;
import com.xminds.easyhomefix_merchant.Manager.EmailSignUpManager;
import com.xminds.easyhomefix_merchant.Manager.ImageUploadManager;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.dao.UserDAO;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;

public class MerchantEDitDetailsActivity extends Activity{
 private ImageView back_button;
 private ImageView avatar;
 private TextView textview_editdetails;
 private EditText edittext_firstname;
 private EditText edittext_lastname;
 private EditText edittext_mobilenumber;
 private EditText edit_text_emailaddress;
 private EditText edit_text_companyname;
 private Button update_details_button;
 private Spinner spinner_number;
 private ImageView plus_button;
 private TextView textview_uploadimage;
 boolean  firstnameValidationFlag = false;
 boolean  lastnameValidationFlag = false;
 boolean  mobilenoValidationFlag = false;
 boolean  emailaddressValidationFlag = false;
 boolean  companynameValidationFlag = false;
 private UpdateUserCallBack updateUserCallBack;
 private int requestcode = 0;
 private ProgressDialog pDialog;
 private UserTokenHolder tokenholder;
 private String customerfirstname;
 private String customerlastname;
 private String mobilenumber;
 private String email;
 private String countrycode;
 private CircularImageView profileimage;
 private LinearLayout linear_upload_image;
 private LogoutReceiver logoutReceiver;
 private static final int REQUEST_CODE = 10;
 private UploadImageCallBack uploadImagecallback;
 private ProgressDialog progressDialog;
 private String[] spinnerValues ;
 private String prefix;
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
		setContentView(R.layout.merchant_edit_details);
		initViews();
		initManager();
		setVisibilities();
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		
		textview_editdetails.setText("Edit Details");
		
		tokenholder = new UserTokenHolder();
		tokenholder = UserDAO.getInstance().getUserDetails();
		customerfirstname = tokenholder.getUser().getFirstName();
		customerlastname = tokenholder.getUser().getLastName();
		mobilenumber = tokenholder.getUser().getMobile();
		countrycode=tokenholder.getUser().getCountryCode();
		email = tokenholder.getUser().getEmail();
		textview_editdetails.setText("Edit Details");
		edittext_firstname.setText(customerfirstname);
		edittext_lastname.setText(customerlastname);
		edittext_mobilenumber.setText(mobilenumber);
		edit_text_emailaddress.setText(email);

		if(tokenholder.getUser().getCompanyName() != null && UtilValidate.isNotEmpty(tokenholder.getUser().getCompanyName())){
			edit_text_companyname.setText(tokenholder.getUser().getCompanyName());
		}
		if(UtilValidate.isNotNull(tokenholder.getUser().getProfileImage()))
		{
		/*	Picasso.with(MerchantEDitDetailsActivity.this).load(tokenholder.getUser().getProfileImage())
			.into(profileimage);*/

			if(tokenholder.getUser().getLoginType().equalsIgnoreCase("facebook"))
			{
				String profileurl=tokenholder.getUser()
						.getProfileImage();
				if(profileurl.contains("http"))
				{
					profileurl=profileurl.replace("http", "https");
				}
				Log.e("", "############"+profileurl);
				Picasso.with(MerchantEDitDetailsActivity.this)
						.load(profileurl)
						.into(profileimage);
			}
			else
			{
				Picasso.with(MerchantEDitDetailsActivity.this)
				.load(tokenholder.getUser().getProfileImage())
				.into(profileimage);
			}
		}
		else if(tokenholder.getUser().getProfileImage()==null)
		{
			Picasso.with(MerchantEDitDetailsActivity.this).load(R.drawable.profile_image_null)
			.into(profileimage);
		}
		
		back_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(MerchantEDitDetailsActivity.this,MerchantSettingsActivity.class);
				startActivity(in);
				finish();
				
			}
		});
		
		update_details_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				final String first_name=edittext_firstname.getText().toString();
				final String last_name=edittext_lastname.getText().toString();
				final String mobile_no=edittext_mobilenumber.getText().toString();
				final String email_address=edit_text_emailaddress.getText().toString();
				final String company_name=edit_text_companyname.getText().toString();
				
				if(first_name.length()>0)
				{
					firstnameValidationFlag= true;
				}
				else
				{
					firstnameValidationFlag= false;
					Utils.showDialog(MerchantEDitDetailsActivity.this, "Please enter your first name.", "ERROR");

				}
				
				if(last_name.length()>0)
				{
					lastnameValidationFlag= true;
				}
				else
				{
					lastnameValidationFlag= false;
					if(firstnameValidationFlag)
					{
						Utils.showDialog(MerchantEDitDetailsActivity.this, "Please enter your last name.", "ERROR");
					}

				}
				
				if((mobile_no.length()>=8)&&mobile_no.length()<=10)
				{
					mobilenoValidationFlag= true;
				}
				else
				{
					mobilenoValidationFlag= false;
					if(lastnameValidationFlag)
					{
						Utils.showDialog(MerchantEDitDetailsActivity.this, "Please enter your valid mobile number.", "ERROR");
					}

				}
				
				if (!isValidEmail(email_address)) {
					
					emailaddressValidationFlag = false;
					if(mobilenoValidationFlag)
					{
						Utils.showDialog(MerchantEDitDetailsActivity.this, "Please enter your valid email address.", "ERROR");
					}
					
				}
				else{
					
					emailaddressValidationFlag = true;
				}
				
				if(company_name.length()>0)
				{
					companynameValidationFlag= true;
				}
				else
				{
					companynameValidationFlag= false;
					if(emailaddressValidationFlag)
					{
						Utils.showDialog(MerchantEDitDetailsActivity.this, "Please enter your company name.", "ERROR");
					}
					

				}
				
				
				if(firstnameValidationFlag&&lastnameValidationFlag&&mobilenoValidationFlag&&emailaddressValidationFlag&&companynameValidationFlag)
				{
					
					
					pDialog = ProgressDialog.show(MerchantEDitDetailsActivity.this,"","Loading..",true);	
					EmailSignUpManager.getInstance().UpdateUser(
							MerchantEDitDetailsActivity.this, email_address,
							first_name, last_name, mobile_no, null, null, null,
							null, null, company_name, updateUserCallBack, requestcode);
					
					/*Toast.makeText(MerchantEDitDetailsActivity.this,
							"Details Updated",
							Toast.LENGTH_SHORT).show();*/
				}
				
				
			}
		});
		
		linear_upload_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Create intent to Open Image applications like Gallery, Google Photos
				Intent galleryIntent = new Intent(Intent.ACTION_PICK,
				        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				// Start the Intent
				startActivityForResult(galleryIntent,REQUEST_CODE);


			}
		});
		
		              // SETTING SPINNER
		
		
		
		spinner_number.setAdapter(new SpinnerAdapter(this,
				R.layout.spinner_item, spinnerValues));
	        spinner_number.setEnabled(false);
		
		
	}
	@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
			super.onDestroy();
		}
	private void initManager() {
		// TODO Auto-generated method stub
		updateUserCallBack=new UpdateUserCallBack();
		uploadImagecallback = new UploadImageCallBack();
		spinnerValues = new String[] { UserDAO.getInstance().getUserDetails().getUser().getCountryCode() };
	}

	private void initViews() {
		// TODO Auto-generated method stub
		back_button=(ImageView) findViewById(R.id.image_back);
		textview_editdetails=(TextView) findViewById(R.id.textview_trackyourfix);
		avatar=(ImageView) findViewById(R.id.image_avatar);
		edittext_firstname=(EditText) findViewById(R.id.edittext_editdetails_firstname);
		edittext_lastname=(EditText) findViewById(R.id.edittext_edit_details_lastname);
		edittext_mobilenumber=(EditText) findViewById(R.id.edittext_edit_details_mobile_number);
		edit_text_emailaddress=(EditText) findViewById(R.id.edittext_editdetails_email_address);
		update_details_button=(Button) findViewById(R.id.button_editdetails_update_details);
		spinner_number=(Spinner) findViewById(R.id.spinner_editdetails_countrycode);
		plus_button=(ImageView) findViewById(R.id.plus_button);
		textview_uploadimage=(TextView) findViewById(R.id.textview_uploadimage);
		edit_text_companyname=(EditText)findViewById(R.id.edittext_editdetails_companyname);
		profileimage=(CircularImageView) findViewById(R.id.profileimage_editdetails);
		linear_upload_image = (LinearLayout)findViewById(R.id.linear_upload_image);
	}

	private void setVisibilities() {
		// TODO Auto-generated method stub
		back_button.setVisibility(View.VISIBLE);
		textview_editdetails.setVisibility(View.VISIBLE);
		avatar.setVisibility(View.GONE);
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 */

	 private boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	 
		 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		 try {
	            // When an Image is picked
	            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK&& null != data) {
	            	progressDialog=ProgressDialog.show(MerchantEDitDetailsActivity.this, null, "Uploading Profile image...");
	                // Get the Image from data
	            	
	                Uri selectedImage = data.getData();
	                String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	                // Get the cursor
	                Cursor cursor = getContentResolver().query(selectedImage,
	                        filePathColumn, null, null, null);
	                // Move to first row
	                cursor.moveToFirst();
	 
	                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	                String imgDecodableString = cursor.getString(columnIndex);
	                cursor.close();
	                Log.e("&&&&&&&&&&&&&", "imgDecodableString : "+imgDecodableString);
	                File image=new File(imgDecodableString);
	                ImageUploadManager.getInstance().uploadProfileImage(MerchantEDitDetailsActivity.this, "PROFILE",
	                		image, uploadImagecallback, requestCode);
	                //ImageView imgView = (ImageView) findViewById(R.id.imgView);
	                // Set the Image in ImageView after decoding the String
	                //imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
	 
	            } else {
	                Toast.makeText(this, "You haven't picked Image",
	                        Toast.LENGTH_LONG).show();
	            }
	        } catch (Exception e) {
	            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
	                    .show();
	        }
	}



		public class UpdateUserCallBack implements AsyncTaskCallBack {

			@Override
			public void onFinish(int responseCode, Object result) {
				if (pDialog != null) {
					pDialog.dismiss();
				}
				if (responseCode == requestcode) {
					EmailSignupBaseHolder emailSignupBaseHolder = (EmailSignupBaseHolder) result;
					if (UtilValidate.isNotNull(emailSignupBaseHolder)) {
						if (emailSignupBaseHolder.isSuccess()) {

							if (UtilValidate.isNotNull(emailSignupBaseHolder)) {
								if (emailSignupBaseHolder.isSuccess()) {

									UserDAO.getInstance().deleteAllUserRows();
									UserDAO.getInstance().InsertUserDetails(
											emailSignupBaseHolder);

									CurrentlyLoggedUserDAO.getInstance()
											.deleteAllUserRows();
									CurrentlyLoggedUserDAO.getInstance()
											.InsertCurrentlyloggedUserDetails(
													emailSignupBaseHolder);
									if (UtilValidate
											.isNotNull(emailSignupBaseHolder
													.getData().getToken())) {
										EasyHomeFixApp.setUserHeader(null);
										EasyHomeFixApp
												.setUserHeader(emailSignupBaseHolder
														.getData().getToken());
									}
									CurrentlyLoggedUserDAO.getInstance()
											.getUserId();

									/*Toast.makeText(
											MerchantEDitDetailsActivity.this,
											emailSignupBaseHolder.isSuccess() + "",
											Toast.LENGTH_LONG).show();*/
						/*			Toast.makeText(
											MerchantEDitDetailsActivity.this,
											"Updated Successfully" + "",
											Toast.LENGTH_LONG).show();*/
									
									Utils.showDialog(MerchantEDitDetailsActivity.this, "Updated Successfully", "SUCCESS");
								}

								else {

									/*Toast.makeText(
											MerchantEDitDetailsActivity.this,
											emailSignupBaseHolder.isSuccess() + "",
											Toast.LENGTH_LONG).show();*/
									
								/*	Toast.makeText(
											MerchantEDitDetailsActivity.this,
											"Not Updated Successfully" + "",
											Toast.LENGTH_LONG).show();*/
									
									Utils.showDialog(MerchantEDitDetailsActivity.this, "something happed,please try again", "ERROR");

								}
							}

						}

						else {
							if(pDialog!=null){
								pDialog.dismiss();
							}
							OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
							if (UtilValidate.isNotNull(onFailureErrorHolder)) {
								for (ErrorMessageListHolder error : onFailureErrorHolder
										.getData()) {

									if (error.getField().equalsIgnoreCase("email")) {
										edit_text_emailaddress.setText("");
										edit_text_emailaddress.setTextSize(15);
										edit_text_emailaddress
												.setHintTextColor(MerchantEDitDetailsActivity.this
														.getResources().getColor(
																R.color.Red));
										edit_text_emailaddress.setHint(error
												.getMessage());
									}

								}

							}
						}
					}

				}

				else {
					OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
					if (UtilValidate.isNotNull(onFailureErrorHolder)) {
						for (ErrorMessageListHolder error : onFailureErrorHolder
								.getData()) {

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
					/*Toast.makeText(MerchantEDitDetailsActivity.this,
							"Please check your internet connection",
							Toast.LENGTH_SHORT).show();*/
					Utils.showDialog(MerchantEDitDetailsActivity.this, "Please check your internet connection", "ERROR");
				}
				else
				{
					Utils.showDialog(MerchantEDitDetailsActivity.this, ""+result, "ERROR");
				}

			}

			/*@Override
			public void onFinish(int requestcode,
					JobCreationBaseHolder jobCreationBaseHolder, int i) {
				// TODO Auto-generated method stub
				
			}*/

		}

	private class UploadImageCallBack implements AsyncTaskCallBack{

			@Override
			public void onFinish(int responseCode, Object result) {
				if(UtilValidate.isNotNull(result)){
					EmailSignupBaseHolder emailSignupBaseHolder = (EmailSignupBaseHolder)result;
					if(UtilValidate.isNotNull(emailSignupBaseHolder)){
						if(emailSignupBaseHolder.isSuccess()){
							if(UtilValidate.isNotNull(emailSignupBaseHolder.getData())){
								if(UtilValidate.isNotNull(emailSignupBaseHolder.getData().getUser())){
									UserDAO.getInstance().deleteAllUserRows();
									UserDAO.getInstance().InsertUserDetails(emailSignupBaseHolder);
									if(UserDAO.getInstance().getUserDetails().getUser().getProfileImage() != null){
										
										
										//ImageView imgView = (ImageView) findViewById(R.id.imgView);
						                // Set the Image in ImageView after decoding the String
						                //imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
						 
										try {
											Picasso.with(MerchantEDitDetailsActivity.this).
											load(emailSignupBaseHolder.getData().getUser().getProfileImage())
											.into(profileimage);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											tokenholder = new UserTokenHolder();
											tokenholder = UserDAO.getInstance().getUserDetails();
											Picasso.with(MerchantEDitDetailsActivity.this).
											load(UserDAO.getInstance().getUserDetails().getUser().getProfileImage())
											.into(profileimage);
										}
										
										
										progressDialog.dismiss();
									}
								}
							}
						}else{
							Toast.makeText(MerchantEDitDetailsActivity.this, "Upload Failed", Toast.LENGTH_LONG).show();
							progressDialog.dismiss();
						}
					}
				}
			}

			@Override
			public void onFinish(int responseCode, String result) {
				
				if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
				{
					Utils.showDialog(MerchantEDitDetailsActivity.this, "Please check your internet connection.", "ERROR");
				}
				else
				{
					Utils.showDialog(MerchantEDitDetailsActivity.this, "Upload Failed", "ERROR");
				}
				//Toast.makeText(MerchantEDitDetailsActivity.this, "Upload Failed", Toast.LENGTH_LONG).show();
				progressDialog.dismiss();
			}

	}
	
	public class SpinnerAdapter extends ArrayAdapter<String> {

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
			LayoutInflater inflater = getLayoutInflater();
			View mySpinner = inflater.inflate(R.layout.spinner_item, parent,
					false);
			TextView main_text = (TextView) mySpinner
					.findViewById(R.id.spinner_item_textview);
			main_text.setText(spinnerValues[position]);
			prefix = spinnerValues[position];
			return mySpinner;

		}

	}
	 
}


