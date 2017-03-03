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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.Holder.UserTokenHolder;
import com.xminds.easyhomefix.Managers.UserManager;
import com.xminds.easyhomefix.actionbar.BaseActivity;
import com.xminds.easyhomefix.activities.ConfirmOrderActivity.LogoutReceiver;
import com.xminds.easyhomefix.activities.CustomerEditDetailsActivity.UpdateUserCallBack;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.dao.UserDAO;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;


public class CustomerChangePasswordActivity extends Activity{

private  EditText edittext_oldpassword;
private  EditText edittext_newpassword;
private  EditText edittext_repeatpassword;
private  Button Updatepassword_button;
private  ImageView back_button;
private  TextView textview_changepassword;
private  ImageView avatar;
private  ImageView imageview_userimage;
private  TextView textview_username;
boolean  oldpasswordValidationFlag = false;
boolean  newpasswordValidationFlag = false;
boolean  repeatpasswordValidationFlag = false;
private UpdateUserCallBack updateUserCallBack;
private int requestcode=0;
private ProgressDialog pDialog;
private UserTokenHolder tokenholder;
private String customerfirstname;
private String customerlastname;
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
		setContentView(R.layout.customer_change_password);
		initViews();
		initManager();
		setVisibilities();
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		textview_changepassword.setText("Change Password");
		
		tokenholder = new UserTokenHolder();
		tokenholder = UserDAO.getInstance().getUserDetails();
		customerfirstname = tokenholder.getUser().getFirstName();
		customerlastname = tokenholder.getUser().getLastName();
		textview_username.setText(customerfirstname+" "+customerlastname);
		
		if(UtilValidate.isNotNull(tokenholder.getUser().getProfileImage()))
		{
			/*Picasso.with(CustomerChangePasswordActivity.this).load(tokenholder.getUser().getProfileImage())
			.into(imageview_userimage);*/
			
			if(tokenholder.getUser().getLoginType().equalsIgnoreCase("facebook"))
			{
				String profileurl=tokenholder.getUser()
						.getProfileImage();
				if(profileurl.contains("http"))
				{
					profileurl=profileurl.replace("http", "https");
				}
				Log.e("", "############"+profileurl);
				Picasso.with(CustomerChangePasswordActivity.this)
						.load(profileurl)
						.into(imageview_userimage);
			}
			else
			{
				Picasso.with(CustomerChangePasswordActivity.this).load(tokenholder.getUser().getProfileImage())
				.into(imageview_userimage);
				
			}
		}
		else
		{
			Picasso.with(CustomerChangePasswordActivity.this).load(R.drawable.profile_image_null)
			.into(imageview_userimage);
		}
		
		
		back_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		Updatepassword_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 final String oldpassword=edittext_oldpassword.getText().toString();
				 final String newpassword=edittext_newpassword.getText().toString();
				 final String repeatpassword=edittext_repeatpassword.getText().toString();
				 
				 if(oldpassword.length()>0)
				 {
					 oldpasswordValidationFlag = true;
				 }
				 else
				 {
					 oldpasswordValidationFlag = false;
					 Utils.showDialog(CustomerChangePasswordActivity.this, "Please enter your old password.", "ERROR");
				 }
				 
				if(newpassword.length()>0) 
				{
					newpasswordValidationFlag = true;
				}
				else 
				{
			    newpasswordValidationFlag = false;
			    
			    if(oldpasswordValidationFlag)
			    {
			    	Utils.showDialog(CustomerChangePasswordActivity.this, "Please enter your new password.", "ERROR");
			    }
				}
				
				if(repeatpassword.length()>0)
				{
				repeatpasswordValidationFlag = true;
					
				}
				else
				{
					repeatpasswordValidationFlag = false;
					if(newpasswordValidationFlag)
					{
						Utils.showDialog(CustomerChangePasswordActivity.this, "please re-enter your new password.", "ERROR");
					}
				}
				
				if(oldpasswordValidationFlag&&newpasswordValidationFlag&&repeatpasswordValidationFlag)
				{
					
					
					
					pDialog = ProgressDialog.show(CustomerChangePasswordActivity.this,"","Loading..",true);	
					UserManager.getInstance().UpdateUser(CustomerChangePasswordActivity.this, null, null,
							null, null,
							
							null, null, oldpassword, 
							newpassword, repeatpassword, null, updateUserCallBack, requestcode);
				}
			}
			
			
			
		});
		
	}	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}

		private void initViews()
		{
			edittext_oldpassword=(EditText) findViewById(R.id.edittext_oldpassword);
			edittext_newpassword=(EditText)findViewById(R.id.edittext_newpassword);
			edittext_repeatpassword=(EditText) findViewById(R.id.edittext_repeatpassword);
			Updatepassword_button=(Button) findViewById(R.id.button_updatepassword);
			back_button=(ImageView) findViewById(R.id.image_back);
			textview_changepassword=(TextView) findViewById(R.id.textview_trackyourfix);
		    avatar=(ImageView) findViewById(R.id.image_avatar);
		    imageview_userimage=(ImageView) findViewById(R.id.imageview_editprofile_user);
		    textview_username=(TextView) findViewById(R.id.textview_editprofile_username);
		}
		
		private void setVisibilities() {
		// TODO Auto-generated method stub
			textview_changepassword.setVisibility(View.VISIBLE);
			avatar.setVisibility(View.GONE);
			back_button.setVisibility(View.VISIBLE);
	}

	
		private void initManager() {
			// TODO Auto-generated method stub
			updateUserCallBack=new UpdateUserCallBack();
		}


		public class UpdateUserCallBack implements AsyncTaskCallBack{

			@Override
			public void onFinish(int responseCode, Object result) {
				if(pDialog!=null){
					pDialog.dismiss();
				}
				if (responseCode == requestcode) {
					EmailSignupBaseHolder emailSignupBaseHolder = (EmailSignupBaseHolder) result;
					if (UtilValidate.isNotNull(emailSignupBaseHolder)) {
						if (emailSignupBaseHolder.isSuccess()) {
							
							if (UtilValidate.isNotNull(emailSignupBaseHolder)) {
								if (emailSignupBaseHolder.isSuccess()) {
									
									
									
									 UserDAO.getInstance().deleteAllUserRows();
									 UserDAO.getInstance().InsertUserDetails(emailSignupBaseHolder);
									 
									 CurrentlyLoggedUserDAO.getInstance().deleteAllUserRows();
									 CurrentlyLoggedUserDAO.getInstance().InsertCurrentlyloggedUserDetails(emailSignupBaseHolder);
									finish();
									Toast.makeText(CustomerChangePasswordActivity.this,"password Updated Sucessfully.", Toast.LENGTH_LONG).show();
								}
								
								else
								{

									Toast.makeText(CustomerChangePasswordActivity.this, "Please try again later", Toast.LENGTH_LONG).show();
									
								
								}
							}
							
							
							
						
							
						}
						
					
					}
					

			}
				
				else
				{
					OnFailureErrorHolder onFailureErrorHolder =(OnFailureErrorHolder) result;
					if(UtilValidate.isNotNull(onFailureErrorHolder))
					{
					for (ErrorMessageListHolder error : onFailureErrorHolder
							.getData()) {
				

						if (error.getField().equalsIgnoreCase("newPassword")) {
							/*edittext_newpassword.setText("");
							edittext_newpassword.setTextSize(12);
							edittext_newpassword.setHintTextColor(CustomerChangePasswordActivity.this
									.getResources().getColor(R.color.Red));
							edittext_newpassword.setHint(error.getMessage());*/
							Utils.showDialog(CustomerChangePasswordActivity.this, ""+error.getMessage(), "ERROR");
						}
						if (error.getField().equalsIgnoreCase("oldPassword")) {
							/*edittext_oldpassword.setText("");
							edittext_oldpassword.setTextSize(12);
							edittext_oldpassword.setHintTextColor(CustomerChangePasswordActivity.this
									.getResources().getColor(R.color.Red));
							edittext_oldpassword.setHint(error.getMessage());*/
							Utils.showDialog(CustomerChangePasswordActivity.this, ""+error.getMessage(), "ERROR");
						}
						if (error.getField().equalsIgnoreCase("repeatePassword")) {
							/*edittext_repeatpassword.setText("");
							edittext_repeatpassword.setTextSize(12);
							edittext_repeatpassword.setHintTextColor(CustomerChangePasswordActivity.this
									.getResources().getColor(R.color.Red));
							edittext_repeatpassword.setHint(error.getMessage());*/
							Utils.showDialog(CustomerChangePasswordActivity.this, ""+error.getMessage(), "ERROR");
						}
						
						
						
					}
				}

				}
			}
			@Override
			public void onFinish(int responseCode, String result) {
				// TODO Auto-generated method stub
				if(pDialog!=null){
					pDialog.dismiss();
				}
				if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET)){
					Utils.showDialog(CustomerChangePasswordActivity.this, "Please check your internet connection.", "ERROR");
				}
				
			}
			@Override
			public void onFinish(int requestcode,
					JobCreationBaseHolder jobCreationBaseHolder, int i) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
}
