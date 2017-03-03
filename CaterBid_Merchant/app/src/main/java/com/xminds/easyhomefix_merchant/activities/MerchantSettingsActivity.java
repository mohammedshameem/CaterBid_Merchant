package com.xminds.easyhomefix_merchant.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pkmmte.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.AvailableBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.FixerJobCategories;
import com.xminds.easyhomefix_merchant.Holder.UserBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.UserTokenHolder;
import com.xminds.easyhomefix_merchant.Manager.CategoryManager;
import com.xminds.easyhomefix_merchant.Manager.EmailSignUpManager;
import com.xminds.easyhomefix_merchant.Manager.LogoutManager;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.dao.UserDAO;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;

public class MerchantSettingsActivity extends Activity {

	private LinearLayout linear_layout_editdetails;
	private LinearLayout linear_layout_changepassword;
	private TextView textview_mobilenotifications;
	private TextView textview_emailnotifications;
	private Button button_logout;
	private TextView texview_settings;
	private TextView textview_viewall;
	private ImageView back_button;
	private ImageView avatar;
	private boolean remebermecheck = false;
	private boolean remebermecheckemail = false;
	private ToggleButton mobilenotification_toogle_button;
	private ToggleButton emailnotification_toogle_button;
	private ImageView select_fix_category_ac_icon;
	private TextView select_fix_category_ac_text;
	private TextView select_fix_category_plumbing_text;
	private TextView select_fix_category_electrician_text;
	private TextView select_fix_category_painting_text;
	private TextView select_fix_category_carpenter_text;
	private TextView select_fix_category_locksmith_text;
	private TextView select_fix_category_pestCtrl_text;
	private TextView select_fix_category_sanitization_text;
	private TextView general_works_button_text_setting;
	private ImageView select_fix_category_plumbing_icon;
	private ImageView select_fix_category_electrician_icon;
	private ImageView select_fix_category_painting_icon;
	private ImageView select_fix_category_carpenter_icon;
	private ImageView select_fix_category_locksmith_icon;
	private ImageView select_fix_category_pestCtrl_icon;
	private ImageView select_fix_category_sanitization_icon;
	private ImageView general_works_button_icon_setting;
	private LinearLayout select_fix_category_ac_layout;
	private LinearLayout select_fix_category_plumbing_layout;
	private LinearLayout select_fix_category_electrician_layout;
	private LinearLayout select_fix_category_painting_layout;
	private LinearLayout select_fix_category_carpenter_layout;
	private LinearLayout select_fix_category_locksmith_layout;
	private LinearLayout select_fix_category_pestCtrl_layout;
	private LinearLayout select_fix_category_sanitization_layout;
	private LinearLayout general_works_button_setting;
	private String pressed = "false";
	private String plumbingpressed = "false";
	private String electritionpressed = "false";
	private String paintingpressed = "false";
	private String carpenterpressed = "false";
	private String looksmithpressed = "false";
	private String pestcontrolpressed = "false";
	private String sanitizationpressed = "false";
	private String generalworkspressed = "false";
	private UserTokenHolder tokenholder;
	private TextView name;
	private TextView mobile;
	private TextView emailaddress;
	private TextView company_name;
	private ArrayList<String> position = new ArrayList<String>();
	private AddCategoryCallBack addCategoryCallBack;
	private RemoveCategoryCallBack removeCategoryCallBack;
	private List<FixerJobCategories>fixerJobCategoryList = new ArrayList<FixerJobCategories>();
	private ArrayList<String> CategoryId = new ArrayList<String>();
	private int pos;
	private int requestCode = 1;
	private Activity activity;
	private CircularImageView profilepicture;
	private String fixerid;
	private LinearLayout backbutton;
	private LogoutReceiver logoutReceiver;
	private TextView text_edit;
	private ImageView firststar;
	private ImageView secondstar;
	private ImageView thirdstar;
	private ImageView fourthstar;
	private ImageView fifthstar;
	private int requestcode = 0;
	private LogoutCallback logoutCallback;
	private UserDetailsCallBack userDetailsCallBack;
	
	
    public class LogoutReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.package.ACTION_LOGOUT")) {
                finish();
            }
        }
    }
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		texview_settings.setText("Settings");
		tokenholder = new UserTokenHolder();
		tokenholder = UserDAO.getInstance().getUserDetails();
		
		if(CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable())
		{
			EmailSignUpManager.getInstance().getUserDetails(MerchantSettingsActivity.this, userDetailsCallBack, requestCode);
			
		}
		if(UtilValidate.isNotNull(tokenholder.getUser().getFirstName()!=null&&tokenholder.getUser().getLastName()!=null)){
			name.setText(tokenholder.getUser().getFirstName()+" "+tokenholder.getUser().getLastName());
		}
		
		if(UtilValidate.isNotNull(tokenholder.getUser().getEmail()!=null)){
			emailaddress.setText(tokenholder.getUser().getEmail());
		}
		if(UtilValidate.isNotNull(tokenholder.getUser().getCompanyName()!=null)){
			company_name.setText(tokenholder.getUser().getCompanyName());
		}
		if(UtilValidate.isNotNull(tokenholder.getUser().getMobile()!=null)){
			mobile.setText(tokenholder.getUser().getMobile());
		}
		
        if(UtilValidate.isNotNull(tokenholder.getUser().getProfileImage()))
			{
				/*Picasso.with(MerchantSettingsActivity.this).load(tokenholder.getUser().getProfileImage())
				.into(profilepicture);*/
        	if(tokenholder.getUser().getLoginType().equalsIgnoreCase("facebook"))
			{
				String profileurl=tokenholder.getUser()
						.getProfileImage();
				if(profileurl.contains("http"))
				{
					profileurl=profileurl.replace("http", "https");
				}
				Log.e("", "############"+profileurl);
				Picasso.with(MerchantSettingsActivity.this)
						.load(profileurl)
						.into(profilepicture);
			}
			else
			{
				Picasso.with(MerchantSettingsActivity.this)
				.load(tokenholder.getUser().getProfileImage())
				.into(profilepicture);
			}
			}
			else
			{
				Picasso.with(MerchantSettingsActivity.this).load(R.drawable.profile_image_null)
				.into(profilepicture);
			}
		
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(getApplicationContext());
		setContentView(R.layout.merchant_setting);
		initViews();
		initManager();
		setVisibilities();
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
	
		
		texview_settings.setText("Settings");
		tokenholder = new UserTokenHolder();
		tokenholder = UserDAO.getInstance().getUserDetails();
		
		
		// SETTING CATEGORY
		List<String> S=UserDAO.getInstance().getAllFixerCategories();
		for(String a:S)
		{
			Log.e("", "@@@@@first:"+a);
		}
/*		if(EasyHomeFixApp.getCategoryIds()!=null)
		{
			position = EasyHomeFixApp.getCategoryIds();
		}*/
		
		
		Gson gson = new Gson();
		
		Type type = new TypeToken<ArrayList<String>>() {}.getType();
		
		if(gson.fromJson(tokenholder.getUser().getCategoryId(), type)!=null)
		{
			position = gson.fromJson(tokenholder.getUser().getCategoryId(), type);
		}

		for (int j = 0; j < position.size(); j++) {

			if (position.get(j).equalsIgnoreCase("1")) {

				select_fix_category_ac_icon.setImageResource(R.drawable.ac_w);
				select_fix_category_ac_layout.setBackground(getResources()
						.getDrawable(R.drawable.select_fix_category_bg_green));
				select_fix_category_ac_text.setTextColor(getResources()
						.getColor(R.color.white));
			}
			if (position.get(j).equalsIgnoreCase("2")) {

				select_fix_category_plumbing_icon
						.setImageResource(R.drawable.plumbing_w);
				select_fix_category_plumbing_layout
						.setBackground(getResources().getDrawable(
								R.drawable.select_fix_category_bg_green));
				select_fix_category_plumbing_text.setTextColor(getResources()
						.getColor(R.color.white));
			}
			if (position.get(j).equalsIgnoreCase("3")) {

				select_fix_category_electrician_icon
						.setImageResource(R.drawable.wiring_w);
				select_fix_category_electrician_layout
						.setBackground(getResources().getDrawable(
								R.drawable.select_fix_category_bg_green));
				select_fix_category_electrician_text
						.setTextColor(getResources().getColor(R.color.white));
			}
			if (position.get(j).equalsIgnoreCase("4")) {

				select_fix_category_painting_icon
						.setImageResource(R.drawable.painting_w);
				select_fix_category_painting_layout
						.setBackground(getResources().getDrawable(
								R.drawable.select_fix_category_bg_green));
				select_fix_category_painting_text.setTextColor(getResources()
						.getColor(R.color.white));
			}
			if (position.get(j).equalsIgnoreCase("5")) {

				select_fix_category_carpenter_icon
						.setImageResource(R.drawable.carpenter_w);
				select_fix_category_carpenter_layout
						.setBackground(getResources().getDrawable(
								R.drawable.select_fix_category_bg_green));
				select_fix_category_carpenter_text.setTextColor(getResources()
						.getColor(R.color.white));
			}
			if (position.get(j).equalsIgnoreCase("6")) {

				select_fix_category_locksmith_icon
						.setImageResource(R.drawable.lock_smith_w);
				select_fix_category_locksmith_layout
						.setBackground(getResources().getDrawable(
								R.drawable.select_fix_category_bg_green));
				select_fix_category_locksmith_text.setTextColor(getResources()
						.getColor(R.color.white));
			}
			if (position.get(j).equalsIgnoreCase("7")) {

				select_fix_category_pestCtrl_icon
						.setImageResource(R.drawable.pest_control_s);
				select_fix_category_pestCtrl_layout
						.setBackground(getResources().getDrawable(
								R.drawable.select_fix_category_bg_green));
				select_fix_category_pestCtrl_text.setTextColor(getResources()
						.getColor(R.color.white));
			}
			if (position.get(j).equalsIgnoreCase("8")) {

				select_fix_category_sanitization_icon
						.setImageResource(R.drawable.sanitization_w);
				select_fix_category_sanitization_layout
						.setBackground(getResources().getDrawable(
								R.drawable.select_fix_category_bg_green));
				select_fix_category_sanitization_text
						.setTextColor(getResources().getColor(R.color.white));
			}
			if (position.get(j).equalsIgnoreCase("1000")) {

				general_works_button_icon_setting
						.setImageResource(R.drawable.general_works_icon);
				general_works_button_setting.setBackground(getResources()
						.getDrawable(R.drawable.select_fix_category_bg_green));
				general_works_button_text_setting.setTextColor(getResources()
						.getColor(R.color.white));
			}

		}
		
		backbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(MerchantSettingsActivity.this,
						TopBottomFragmentTabHostActivity.class);
				startActivity(in);
				finish();
			}
			
		});
		
		
		if(tokenholder.getUser().getLoginType().equalsIgnoreCase("facebook")||(tokenholder.getUser().getLoginType().equalsIgnoreCase("google")))
		{
			
			text_edit.setTextColor(this.getResources().getColor(R.color.grey_text));
			linear_layout_changepassword.setClickable(false);
			linear_layout_changepassword.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.edit_hidden));
		}
		else
		{
			linear_layout_changepassword.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent in = new Intent(MerchantSettingsActivity.this,
							MerchantChangePasswordActivity.class);
					startActivity(in);

				}
			});
			
		}
		
		
		
		


		linear_layout_editdetails.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent in = new Intent(MerchantSettingsActivity.this,
						MerchantEDitDetailsActivity.class);
				startActivity(in);
			}
		});
		

		

		textview_viewall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent in = new Intent(MerchantSettingsActivity.this,
						ReviewActivity.class);
				startActivity(in);

			}
		});

		button_logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				LogoutManager.getInstance().logout(MerchantSettingsActivity.this, logoutCallback, requestcode);
				

			}
		});

		select_fix_category_ac_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				pos = 1;
				
				if (pressed.equalsIgnoreCase("false")) {

					select_fix_category_ac_icon
							.setImageResource(R.drawable.ac_w);
					select_fix_category_ac_layout.setBackground(getResources()
							.getDrawable(
									R.drawable.select_fix_category_bg_green));
					select_fix_category_ac_text.setTextColor(getResources()
							.getColor(R.color.white));
					pressed = "true";
					
					CategoryManager.getInstance().AddCategory(MerchantSettingsActivity.this,pos,addCategoryCallBack,requestCode);
					
				}

				else if (pressed.equalsIgnoreCase("true")) {
					select_fix_category_ac_icon
							.setImageResource(R.drawable.ac_icon);
					select_fix_category_ac_layout.setBackground(getResources()
							.getDrawable(R.drawable.select_fix_bg));
					select_fix_category_ac_text.setTextColor(getResources()
							.getColor(R.color.black));
					pressed = "false";
					
					CategoryManager.getInstance().RemoveCategory(MerchantSettingsActivity.this,pos,removeCategoryCallBack,requestCode);
					
				}

			}
		});

		select_fix_category_plumbing_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						pos = 2;
						
						if (plumbingpressed.equalsIgnoreCase("false")) {

							select_fix_category_plumbing_icon
									.setImageResource(R.drawable.plumbing_w);
							select_fix_category_plumbing_layout
									.setBackground(getResources()
											.getDrawable(
													R.drawable.select_fix_category_bg_green));
							select_fix_category_plumbing_text
									.setTextColor(getResources().getColor(
											R.color.white));
							plumbingpressed = "true";
							
							CategoryManager.getInstance().AddCategory(MerchantSettingsActivity.this,pos,addCategoryCallBack,requestCode);
							
						} else if (plumbingpressed.equalsIgnoreCase("true")) {
							select_fix_category_plumbing_icon
									.setImageResource(R.drawable.plumbing_icon);
							select_fix_category_plumbing_layout
									.setBackground(getResources().getDrawable(
											R.drawable.select_fix_bg));
							select_fix_category_plumbing_text
									.setTextColor(getResources().getColor(
											R.color.black));
							plumbingpressed = "false";
							
							CategoryManager.getInstance().RemoveCategory(MerchantSettingsActivity.this,pos,removeCategoryCallBack,requestCode);

						}

					}
				});
		select_fix_category_electrician_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						pos = 3;
						
						if (electritionpressed.equalsIgnoreCase("false")) {
							select_fix_category_electrician_icon
									.setImageResource(R.drawable.wiring_w);
							select_fix_category_electrician_layout
									.setBackground(getResources()
											.getDrawable(
													R.drawable.select_fix_category_bg_green));
							select_fix_category_electrician_text
									.setTextColor(getResources().getColor(
											R.color.white));
							electritionpressed = "true";
							
							CategoryManager.getInstance().AddCategory(MerchantSettingsActivity.this,pos,addCategoryCallBack,requestCode);

						} else if (electritionpressed.equalsIgnoreCase("true")) {

							select_fix_category_electrician_icon
									.setImageResource(R.drawable.wiring_icon);
							select_fix_category_electrician_layout
									.setBackground(getResources().getDrawable(
											R.drawable.select_fix_bg));
							select_fix_category_electrician_text
									.setTextColor(getResources().getColor(
											R.color.black));
							electritionpressed = "false";
							
							CategoryManager.getInstance().RemoveCategory(MerchantSettingsActivity.this,pos,removeCategoryCallBack,requestCode);
							
						}

					}
				});
		select_fix_category_painting_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						pos = 4;

						if (paintingpressed.equalsIgnoreCase("false")) {
							select_fix_category_painting_icon
									.setImageResource(R.drawable.painting_w);
							select_fix_category_painting_layout
									.setBackground(getResources()
											.getDrawable(
													R.drawable.select_fix_category_bg_green));
							select_fix_category_painting_text
									.setTextColor(getResources().getColor(
											R.color.white));
							paintingpressed = "true";
							
							CategoryManager.getInstance().AddCategory(MerchantSettingsActivity.this,pos,addCategoryCallBack,requestCode);

						} else if (paintingpressed.equalsIgnoreCase("true")) {

							select_fix_category_painting_icon
									.setImageResource(R.drawable.painting_icon);
							select_fix_category_painting_layout
									.setBackground(getResources().getDrawable(
											R.drawable.select_fix_bg));
							select_fix_category_painting_text
									.setTextColor(getResources().getColor(
											R.color.black));
							paintingpressed = "false";
							
							CategoryManager.getInstance().RemoveCategory(MerchantSettingsActivity.this,pos,removeCategoryCallBack,requestCode);

						}

					}
				});
		select_fix_category_carpenter_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						pos = 5;
						
						if (carpenterpressed.equalsIgnoreCase("false")) {
							select_fix_category_carpenter_icon
									.setImageResource(R.drawable.carpenter_w);
							select_fix_category_carpenter_layout
									.setBackground(getResources()
											.getDrawable(
													R.drawable.select_fix_category_bg_green));
							select_fix_category_carpenter_text
									.setTextColor(getResources().getColor(
											R.color.white));
							carpenterpressed = "true";
							
							CategoryManager.getInstance().AddCategory(MerchantSettingsActivity.this,pos,addCategoryCallBack,requestCode);
							
						} else if (carpenterpressed.equalsIgnoreCase("true")) {
							select_fix_category_carpenter_icon
									.setImageResource(R.drawable.carpenter_icon);
							select_fix_category_carpenter_layout
									.setBackground(getResources().getDrawable(
											R.drawable.select_fix_bg));
							select_fix_category_carpenter_text
									.setTextColor(getResources().getColor(
											R.color.black));
							carpenterpressed = "false";
							
							CategoryManager.getInstance().RemoveCategory(MerchantSettingsActivity.this,pos,removeCategoryCallBack,requestCode);
							
						}

					}
				});
		select_fix_category_locksmith_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						pos = 6;
						
						if (looksmithpressed.equalsIgnoreCase("false")) {

							select_fix_category_locksmith_icon
									.setImageResource(R.drawable.lock_smith_w);
							select_fix_category_locksmith_layout
									.setBackground(getResources()
											.getDrawable(
													R.drawable.select_fix_category_bg_green));
							select_fix_category_locksmith_text
									.setTextColor(getResources().getColor(
											R.color.white));
							looksmithpressed = "true";
							
							CategoryManager.getInstance().AddCategory(MerchantSettingsActivity.this,pos,addCategoryCallBack,requestCode);

						}

						else if (looksmithpressed.equalsIgnoreCase("true")) {
							select_fix_category_locksmith_icon
									.setImageResource(R.drawable.locksmith_icon);
							select_fix_category_locksmith_layout
									.setBackground(getResources().getDrawable(
											R.drawable.select_fix_bg));
							select_fix_category_locksmith_text
									.setTextColor(getResources().getColor(
											R.color.black));
							looksmithpressed = "false";
							
							CategoryManager.getInstance().RemoveCategory(MerchantSettingsActivity.this,pos,removeCategoryCallBack,requestCode);
							
						}

					}
				});
		select_fix_category_pestCtrl_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						pos = 7;

						if (pestcontrolpressed.equalsIgnoreCase("false")) {

							select_fix_category_pestCtrl_icon
									.setImageResource(R.drawable.pest_control_s);
							select_fix_category_pestCtrl_layout
									.setBackground(getResources()
											.getDrawable(
													R.drawable.select_fix_category_bg_green));
							select_fix_category_pestCtrl_text
									.setTextColor(getResources().getColor(
											R.color.white));

							pestcontrolpressed = "true";
							
							CategoryManager.getInstance().AddCategory(MerchantSettingsActivity.this,pos,addCategoryCallBack,requestCode);
							
						} else if (pestcontrolpressed.equalsIgnoreCase("true")) {
							select_fix_category_pestCtrl_icon
									.setImageResource(R.drawable.pest_control_icon);
							select_fix_category_pestCtrl_layout
									.setBackground(getResources().getDrawable(
											R.drawable.select_fix_bg));
							select_fix_category_pestCtrl_text
									.setTextColor(getResources().getColor(
											R.color.black));
							pestcontrolpressed = "false";
							
							CategoryManager.getInstance().RemoveCategory(MerchantSettingsActivity.this,pos,removeCategoryCallBack,requestCode);
							
						}

					}
				});
		select_fix_category_sanitization_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						pos = 8;
						
						if (sanitizationpressed.equalsIgnoreCase("false")) {
							select_fix_category_sanitization_icon
									.setImageResource(R.drawable.sanitization_w);
							select_fix_category_sanitization_layout
									.setBackground(getResources()
											.getDrawable(
													R.drawable.select_fix_category_bg_green));
							select_fix_category_sanitization_text
									.setTextColor(getResources().getColor(
											R.color.white));
							sanitizationpressed = "true";
							
							CategoryManager.getInstance().AddCategory(MerchantSettingsActivity.this,pos,addCategoryCallBack,requestCode);
							
						} else if (sanitizationpressed.equalsIgnoreCase("true")) {
							select_fix_category_sanitization_icon
									.setImageResource(R.drawable.sanitization_icon);
							select_fix_category_sanitization_layout
									.setBackground(getResources().getDrawable(
											R.drawable.select_fix_bg));
							select_fix_category_sanitization_text
									.setTextColor(getResources().getColor(
											R.color.black));
							sanitizationpressed = "false";
							
							CategoryManager.getInstance().RemoveCategory(MerchantSettingsActivity.this,pos,removeCategoryCallBack,requestCode);
							
						}

					}
				});

		general_works_button_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				pos = 1000;

				if (generalworkspressed.equalsIgnoreCase("false")) {
					general_works_button_icon_setting
							.setImageResource(R.drawable.general_works_icon);
					general_works_button_setting.setBackground(getResources()
							.getDrawable(
									R.drawable.select_fix_category_bg_green));
					general_works_button_text_setting
							.setTextColor(getResources()
									.getColor(R.color.white));
					generalworkspressed = "true";
					
					CategoryManager.getInstance().AddCategory(MerchantSettingsActivity.this,pos,addCategoryCallBack,requestCode);

				} else if (generalworkspressed.equalsIgnoreCase("true")) {
					/*
					 * general_works_button_icon_setting
					 * .setImageResource(R.drawable.general_works_icon);
					 */
					general_works_button_setting.setBackground(getResources()
							.getDrawable(R.drawable.select_fix_bg));
					general_works_button_text_setting
							.setTextColor(getResources()
									.getColor(R.color.black));
					generalworkspressed = "false";
					
					CategoryManager.getInstance().RemoveCategory(MerchantSettingsActivity.this,pos,removeCategoryCallBack,requestcode);
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
	private void initViews() {
		// TODO Auto-generated method stub
		linear_layout_editdetails = (LinearLayout) findViewById(R.id.settings_editdetails);
		linear_layout_changepassword = (LinearLayout) findViewById(R.id.settings_changepassword);
		button_logout = (Button) findViewById(R.id.settings_logout);
		back_button = (ImageView) findViewById(R.id.image_back);
		texview_settings = (TextView) findViewById(R.id.textview_trackyourfix);
		textview_viewall = (TextView) findViewById(R.id.viewAll_text);
		avatar = (ImageView) findViewById(R.id.image_avatar);
		select_fix_category_ac_layout = (LinearLayout) findViewById(R.id.select_fix_category_ac_layout_setting);
		select_fix_category_plumbing_layout = (LinearLayout) findViewById(R.id.select_fix_category_plumbing_layout_setting);
		select_fix_category_electrician_layout = (LinearLayout) findViewById(R.id.select_fix_category_electrician_layout_setting);
		select_fix_category_painting_layout = (LinearLayout) findViewById(R.id.select_fix_category_painting_layout_setting);
		select_fix_category_carpenter_layout = (LinearLayout) findViewById(R.id.select_fix_category_carpenter_layout_setting);
		select_fix_category_locksmith_layout = (LinearLayout) findViewById(R.id.select_fix_category_locksmith_layout_setting);
		select_fix_category_pestCtrl_layout = (LinearLayout) findViewById(R.id.select_fix_category_pestCtrl_layout_setting);
		select_fix_category_sanitization_layout = (LinearLayout) findViewById(R.id.select_fix_category_sanitization_layout_setting);
		select_fix_category_ac_icon = (ImageView) findViewById(R.id.select_fix_category_ac_icon_setting);
		select_fix_category_ac_text = (TextView) findViewById(R.id.select_fix_category_ac_text_setting);
		select_fix_category_plumbing_text = (TextView) findViewById(R.id.select_fix_category_plumbing_text_setting);
		select_fix_category_electrician_text = (TextView) findViewById(R.id.select_fix_category_electrician_text_setting);
		select_fix_category_painting_text = (TextView) findViewById(R.id.select_fix_category_painting_text_setting);
		select_fix_category_carpenter_text = (TextView) findViewById(R.id.select_fix_category_carpenter_text_setting);
		select_fix_category_locksmith_text = (TextView) findViewById(R.id.select_fix_category_locksmith_text_setting);
		select_fix_category_pestCtrl_text = (TextView) findViewById(R.id.select_fix_category_pestCtrl_text_setting);
		select_fix_category_sanitization_text = (TextView) findViewById(R.id.select_fix_category_sanitization_text_setting);
		select_fix_category_plumbing_icon = (ImageView) findViewById(R.id.select_fix_category_plumbing_icon_setting);
		select_fix_category_electrician_icon = (ImageView) findViewById(R.id.select_fix_category_electrician_icon_setting);
		select_fix_category_painting_icon = (ImageView) findViewById(R.id.select_fix_category_painting_icon_setting);
		select_fix_category_carpenter_icon = (ImageView) findViewById(R.id.select_fix_category_carpenter_icon_setting);
		select_fix_category_locksmith_icon = (ImageView) findViewById(R.id.select_fix_category_locksmith_icon_setting);
		select_fix_category_pestCtrl_icon = (ImageView) findViewById(R.id.select_fix_category_pestCtrl_icon_setting);
		select_fix_category_sanitization_icon = (ImageView) findViewById(R.id.select_fix_category_sanitization_icon_setting);
		general_works_button_setting = (LinearLayout) findViewById(R.id.general_works_button_setting);
		general_works_button_icon_setting = (ImageView) findViewById(R.id.general_works_button_icon_setting);
		general_works_button_text_setting = (TextView) findViewById(R.id.general_works_button_text_setting);
		name = (TextView) findViewById(R.id.customername);
		mobile = (TextView) findViewById(R.id.mobile);
		emailaddress = (TextView) findViewById(R.id.emailaddress);
		company_name = (TextView) findViewById(R.id.company_name);
		profilepicture=(CircularImageView) findViewById(R.id.profilepicture);
		backbutton=(LinearLayout) findViewById(R.id.back_button_layout);
		text_edit=(TextView)findViewById(R.id.text_edit);
		firststar=(ImageView)findViewById(R.id.first_star);
		secondstar=(ImageView)findViewById(R.id.second_star);
		thirdstar=(ImageView)findViewById(R.id.third_star);
		fourthstar=(ImageView)findViewById(R.id.fourth_star);
		fifthstar=(ImageView)findViewById(R.id.fifth_star);
	}

	private void setVisibilities() {
		// TODO Auto-generated method stub
		texview_settings.setVisibility(View.VISIBLE);
		avatar.setVisibility(View.GONE);
		back_button.setVisibility(View.VISIBLE);

	}

	private void initManager() {
		// TODO Auto-generated method stub

		addCategoryCallBack = new AddCategoryCallBack(); 
		removeCategoryCallBack= new RemoveCategoryCallBack();
		logoutCallback = new LogoutCallback();
		userDetailsCallBack=new UserDetailsCallBack();
	}
	
	public class AddCategoryCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			
			AvailableBaseHolder availableBaseHolder = (AvailableBaseHolder)result;
			
			if(UtilValidate.isNotNull(availableBaseHolder)){
				
				if(UtilValidate.isNotNull(availableBaseHolder.getData())){
					
					if(UtilValidate.isNotNull(availableBaseHolder.getData().getFixer())){
					
						if((UtilValidate.isNotNull(availableBaseHolder.getData().getFixer().getUserId()))){
						
					if((UtilValidate.isNotNull(availableBaseHolder.getData().getFixer().getUserId().getFixerJobCategories()))||(UtilValidate.isNotEmpty(availableBaseHolder.getData().getFixer().getUserId().getFixerJobCategories())));{
						
						fixerJobCategoryList = availableBaseHolder.getData().getFixer().getUserId().getFixerJobCategories();
						CategoryId.clear();
						for(FixerJobCategories fixerJobCategoryObject : fixerJobCategoryList ){
							
							CategoryId.add(fixerJobCategoryObject.getId());
							Log.d("", "ADDCATEGORY"+CategoryId);
						}
					//	EasyHomeFixApp.setCategoryIds(CategoryId);
						UserDAO.getInstance().UpdateUserTable(CategoryId,availableBaseHolder.getData().getFixer().getUserId().getId());
						
					}
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
				Utils.showDialog(MerchantSettingsActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(MerchantSettingsActivity.this, ""+result, "ERROR");
			}
			
		}
		
	}
	
	public class RemoveCategoryCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			
			AvailableBaseHolder availableBaseHolder = (AvailableBaseHolder)result;
			
			if(UtilValidate.isNotNull(availableBaseHolder)){
				
				if(UtilValidate.isNotNull(availableBaseHolder.getData())){
					if(UtilValidate.isNotNull(availableBaseHolder.getData().getFixer())){
						
						if((UtilValidate.isNotNull(availableBaseHolder.getData().getFixer().getUserId()))){
					
					if(UtilValidate.isNotNull(availableBaseHolder.getData().getFixer().getUserId().getFixerJobCategories())||(UtilValidate.isNotEmpty(availableBaseHolder.getData().getFixer().getUserId().getFixerJobCategories())));{
						
						fixerJobCategoryList = availableBaseHolder.getData().getFixer().getUserId().getFixerJobCategories();
						CategoryId.clear();
						for(FixerJobCategories fixerJobCategoryObject : fixerJobCategoryList ){
							
							CategoryId.add(fixerJobCategoryObject.getId());
							Log.d("", "REMOVED ID"+CategoryId);
						}
				//		EasyHomeFixApp.setCategoryIds(CategoryId);
						UserDAO.getInstance().UpdateUserTable(CategoryId,availableBaseHolder.getData().getFixer().getUserId().getId());
						
					}
					
						}
					}
				}
				
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			
			
		}
		
		
	}
	
	private class LogoutCallback implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			Log.e("999999999999999999999999999", "REQC2:"+requestcode+"|"+responseCode);
			if(responseCode == requestcode){
				UserBaseHolder userBaseHolder = (UserBaseHolder) result;
				if(userBaseHolder.isSuccess()){
					Intent broadcastIntent = new Intent();
					broadcastIntent.setAction("com.package.ACTION_LOGOUT");
					sendBroadcast(broadcastIntent);
					
		
					UserDAO.getInstance().deleteAllUserRows();
					CurrentlyLoggedUserDAO.getInstance().deleteAllUserRows();
					EasyHomeFixApp.setCategoryNameDefaultTab(null);
					EasyHomeFixApp.setDetailsTabStatus(null);
					EasyHomeFixApp.setListIds(null);
					EasyHomeFixApp.setNotificationlist(null);
					EasyHomeFixApp.setUserHeader(null);
					AccessToken.setCurrentAccessToken(null);
					EasyHomeFixApp.setNotificationlist(null);
					EasyHomeFixApp.setNotificationcount(null);
					EasyHomeFixApp.setAvailabletabstatus(null);
					Intent in = new Intent(MerchantSettingsActivity.this,
							SplashScreenActivity.class);
					in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(in);
					finish();
				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			//Toast.makeText(MerchantSettingsActivity.this, result, Toast.LENGTH_LONG).show();
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(MerchantSettingsActivity.this, "Please check your internet ", "ERROR");
			}
			else
			{
				Utils.showDialog(MerchantSettingsActivity.this, ""+result, "ERROR");
			}
		}
	
	}
	
	private class UserDetailsCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			UserBaseHolder baseHolder=(UserBaseHolder)result;
			if(baseHolder.isSuccess())
			{
				if((baseHolder.getData().getFirstName()!=null)&&(baseHolder.getData().getLastName()!=null))
				{
					name.setText(baseHolder.getData().getFirstName()+" "+baseHolder.getData().getLastName());
				}
				else if(baseHolder.getData().getFirstName()!=null)
				{
					name.setText(baseHolder.getData().getFirstName());
				}
				else
				{
					name.setText("");
				}
				
				if((baseHolder.getData().getMobile()!=null)&&(baseHolder.getData().getCountryCode()!=null))
				{
					mobile.setText(baseHolder.getData().getCountryCode()+baseHolder.getData().getMobile());
					
				}
				else
				{
					if(baseHolder.getData().getMobile()!=null)
					{
						mobile.setText(baseHolder.getData().getMobile());
					}
					else
					{
						mobile.setText("");
					}
				}
				
				if(baseHolder.getData().getEmail()!=null)
				{
					emailaddress.setText(baseHolder.getData().getEmail());
				}
				else
				{
					emailaddress.setText("");
				}
				
				if(baseHolder.getData().getCompanyName()!=null)
				{
					Log.e("","######################"+baseHolder.getData().getCompanyName());
					company_name.setText(baseHolder.getData().getCompanyName());
				}
				else
				{
					company_name.setText("");
				}
				
				if(baseHolder.getData().getAvrgStarRating()>0)
				{
					
					if(baseHolder.getData().getAvrgStarRating()==1)
					{
						Picasso.with(activity).load(R.drawable.star_full_big)
						.into(firststar);
						Picasso.with(activity).load(R.drawable.star_none)
						.into(secondstar);
						Picasso.with(activity).load(R.drawable.star_none)
						.into(thirdstar);
						Picasso.with(activity).load(R.drawable.star_none)
						.into(fourthstar);
						Picasso.with(activity).load(R.drawable.star_none)
						.into(fifthstar);
						
					}
					if(baseHolder.getData().getAvrgStarRating()==2)
					{
						Picasso.with(activity).load(R.drawable.star_full_big)
						.into(firststar);
						Picasso.with(activity).load(R.drawable.star_full_big)
						.into(secondstar);
						Picasso.with(activity).load(R.drawable.star_none)
						.into(thirdstar);
						Picasso.with(activity).load(R.drawable.star_none)
						.into(fourthstar);
						Picasso.with(activity).load(R.drawable.star_none)
						.into(fifthstar);
						
					}
					if(baseHolder.getData().getAvrgStarRating()==3)
					{
						Picasso.with(activity).load(R.drawable.star_full_big)
						.into(firststar);
						Picasso.with(activity).load(R.drawable.star_full_big)
						.into(secondstar);
						Picasso.with(activity).load(R.drawable.star_full_big)
						.into(thirdstar);
						Picasso.with(activity).load(R.drawable.star_none)
						.into(fourthstar);
						Picasso.with(activity).load(R.drawable.star_none)
						.into(fifthstar);
						
					}
					if(baseHolder.getData().getAvrgStarRating()==4)
					{
						Picasso.with(activity).load(R.drawable.star_full_big)
						.into(firststar);
						Picasso.with(activity).load(R.drawable.star_full_big)
						.into(secondstar);
						Picasso.with(activity).load(R.drawable.star_full_big)
						.into(thirdstar);
						Picasso.with(activity).load(R.drawable.star_full_big)
						.into(fourthstar);
						Picasso.with(activity).load(R.drawable.star_none)
						.into(fifthstar);
						
					}
					if(baseHolder.getData().getAvrgStarRating()>=5)
					{
						Picasso.with(activity).load(R.drawable.star_full_big)
						.into(firststar);
						Picasso.with(activity).load(R.drawable.star_full_big)
						.into(secondstar);
						Picasso.with(activity).load(R.drawable.star_full_big)
						.into(thirdstar);
						Picasso.with(activity).load(R.drawable.star_full_big)
						.into(fourthstar);
						Picasso.with(activity).load(R.drawable.star_full_big)
						.into(fifthstar);
						
					}
					
					
				}
				else if(baseHolder.getData().getAvrgStarRating()==0)
				{
					Picasso.with(activity).load(R.drawable.star_none)
					.into(firststar);
					Picasso.with(activity).load(R.drawable.star_none)
					.into(secondstar);
					Picasso.with(activity).load(R.drawable.star_none)
					.into(thirdstar);
					Picasso.with(activity).load(R.drawable.star_none)
					.into(fourthstar);
					Picasso.with(activity).load(R.drawable.star_none)
					.into(fifthstar);
				}
				
			}
			
			
			
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				//Utils.showDialog(MerchantSettingsActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(MerchantSettingsActivity.this, ""+result, "ERROR");
			}
			
		}
		
	}
	

}
