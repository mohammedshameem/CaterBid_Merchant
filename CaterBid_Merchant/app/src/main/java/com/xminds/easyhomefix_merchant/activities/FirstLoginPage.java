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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xminds.easyfix_merchant.actionbar.BaseActivity;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.AvailableBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.FixerJobCategories;
import com.xminds.easyhomefix_merchant.Manager.CategoryManager;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.dao.UserDAO;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;

public class FirstLoginPage extends BaseActivity {

	private Button submit_button;
	private LinearLayout select_fix_category_ac_layout;
	private LinearLayout select_fix_category_plumbing_layout;
	private LinearLayout select_fix_category_electrician_layout;
	private LinearLayout select_fix_category_painting_layout;
	private LinearLayout select_fix_category_carpenter_layout;
	private LinearLayout select_fix_category_locksmith_layout;
	private LinearLayout select_fix_category_pestCtrl_layout;
	private LinearLayout select_fix_category_sanitization_layout;
	private LinearLayout general_works_button;
	private int pos;
	private ImageView select_fix_category_ac_icon;
	private TextView select_fix_category_ac_text;
	private TextView select_fix_category_plumbing_text;
	private TextView select_fix_category_electrician_text;
	private TextView select_fix_category_painting_text;
	private TextView select_fix_category_carpenter_text;
	private TextView select_fix_category_locksmith_text;
	private TextView select_fix_category_pestCtrl_text;
	private TextView select_fix_category_sanitization_text;
	private TextView general_works_button_text;
	private ImageView select_fix_category_plumbing_icon;
	private ImageView select_fix_category_electrician_icon;
	private ImageView select_fix_category_painting_icon;
	private ImageView select_fix_category_carpenter_icon;
	private ImageView select_fix_category_locksmith_icon;
	private ImageView select_fix_category_pestCtrl_icon;
	private ImageView select_fix_category_sanitization_icon;
	private ImageView general_works_button_icon;
	private String pressed = "false";
	private String plumbingpressed = "false";
	private String electritionpressed = "false";
	private String paintingpressed = "false";
	private String carpenterpressed = "false";
	private String looksmithpressed = "false";
	private String pestcontrolpressed = "false";
	private String sanitizationpressed = "false";
	private String generalworkspressed = "false";
	private AddCategoryCallBack addCategoryCallBack;
	private int requestCode = 1;
	private RemoveCategoryCallBack removeCategoryCallBack;
	private List<FixerJobCategories>fixerJobCategoryList = new ArrayList<FixerJobCategories>();
	private ArrayList<String> CategoryId = new ArrayList<String>();
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
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_login_page);
		
		initViews();
		initmanager();
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		
		
		submit_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(CategoryId.isEmpty()){
					
					Toast.makeText(FirstLoginPage.this, "Please Select Category", Toast.LENGTH_SHORT).show();
					
				}else{
					
					Intent in = new Intent(FirstLoginPage.this,
							TopBottomFragmentTabHostActivity.class);
					in.putStringArrayListExtra("CATEGORY_ID", CategoryId);
					Log.d("", "CATEGORYID IN FIRST LOGIN PAGE"+CategoryId);
					startActivity(in);
					finish();
				}
				

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
					Log.e("", "add");
					
					CategoryManager.getInstance().AddCategory(FirstLoginPage.this,pos,addCategoryCallBack,requestCode);
				}

				else if (pressed.equalsIgnoreCase("true")) {
					select_fix_category_ac_icon
							.setImageResource(R.drawable.ac_icon);
					select_fix_category_ac_layout.setBackground(getResources()
							.getDrawable(R.drawable.select_fix_bg));
					select_fix_category_ac_text.setTextColor(getResources()
							.getColor(R.color.black));
					pressed = "false";
					Log.e("", "remove");
					CategoryManager.getInstance().RemoveCategory(FirstLoginPage.this,pos,removeCategoryCallBack,requestCode);
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
							
							CategoryManager.getInstance().AddCategory(FirstLoginPage.this,pos,addCategoryCallBack,requestCode);
							
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
							CategoryManager.getInstance().RemoveCategory(FirstLoginPage.this,pos,removeCategoryCallBack,requestCode);
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
							
							CategoryManager.getInstance().AddCategory(FirstLoginPage.this,pos,addCategoryCallBack,requestCode);

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
							CategoryManager.getInstance().RemoveCategory(FirstLoginPage.this,pos,removeCategoryCallBack,requestCode);
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
							
							CategoryManager.getInstance().AddCategory(FirstLoginPage.this,pos,addCategoryCallBack,requestCode);

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
							CategoryManager.getInstance().RemoveCategory(FirstLoginPage.this,pos,removeCategoryCallBack,requestCode);
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
							
							CategoryManager.getInstance().AddCategory(FirstLoginPage.this,pos,addCategoryCallBack,requestCode);
							
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
							CategoryManager.getInstance().RemoveCategory(FirstLoginPage.this,pos,removeCategoryCallBack,requestCode);
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

							CategoryManager.getInstance().AddCategory(FirstLoginPage.this,pos,addCategoryCallBack,requestCode);
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
							CategoryManager.getInstance().RemoveCategory(FirstLoginPage.this,pos,removeCategoryCallBack,requestCode);
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
							
							CategoryManager.getInstance().AddCategory(FirstLoginPage.this,pos,addCategoryCallBack,requestCode);
							
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
							CategoryManager.getInstance().RemoveCategory(FirstLoginPage.this,pos,removeCategoryCallBack,requestCode);
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
							
							CategoryManager.getInstance().AddCategory(FirstLoginPage.this,pos,addCategoryCallBack,requestCode);
							
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
							CategoryManager.getInstance().RemoveCategory(FirstLoginPage.this,pos,removeCategoryCallBack,requestCode);
						}

					}
				});

		general_works_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pos = 1000;
				
				if (generalworkspressed.equalsIgnoreCase("false")) {
					general_works_button_icon
							.setImageResource(R.drawable.general_works_icon);
					general_works_button.setBackground(getResources()
							.getDrawable(
									R.drawable.select_fix_category_bg_green));
					general_works_button_text.setTextColor(getResources()
							.getColor(R.color.white));
					generalworkspressed = "true";
					
					CategoryManager.getInstance().AddCategory(FirstLoginPage.this,pos,addCategoryCallBack,requestCode);

				} else if (generalworkspressed.equalsIgnoreCase("true")) {
					/*
					 * general_works_button_icon
					 * .setImageResource(R.drawable.general_works_icon);
					 */
					general_works_button.setBackground(getResources()
							.getDrawable(R.drawable.select_fix_bg));
					general_works_button_text.setTextColor(getResources()
							.getColor(R.color.black));
					generalworkspressed = "false";
					CategoryManager.getInstance().RemoveCategory(FirstLoginPage.this,pos,removeCategoryCallBack,requestCode);
				}

			}
		});
		
		//CHECKING FOR GETTING THE CATEGORY ID TO PASS IN AVAILABLE URL FOR NEWJOBS
		

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
	private void initmanager() {
		// TODO Auto-generated method stub
		addCategoryCallBack = new AddCategoryCallBack(); 
		removeCategoryCallBack= new RemoveCategoryCallBack();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		submit_button = (Button) findViewById(R.id.login_submit_button);
		select_fix_category_ac_layout = (LinearLayout) findViewById(R.id.select_fix_category_ac_layout);
		select_fix_category_plumbing_layout = (LinearLayout) findViewById(R.id.select_fix_category_plumbing_layout);
		select_fix_category_electrician_layout = (LinearLayout) findViewById(R.id.select_fix_category_electrician_layout);
		select_fix_category_painting_layout = (LinearLayout) findViewById(R.id.select_fix_category_painting_layout);
		select_fix_category_carpenter_layout = (LinearLayout) findViewById(R.id.select_fix_category_carpenter_layout);
		select_fix_category_locksmith_layout = (LinearLayout) findViewById(R.id.select_fix_category_locksmith_layout);
		select_fix_category_pestCtrl_layout = (LinearLayout) findViewById(R.id.select_fix_category_pestCtrl_layout);
		select_fix_category_sanitization_layout = (LinearLayout) findViewById(R.id.select_fix_category_sanitization_layout);
		select_fix_category_ac_icon = (ImageView) findViewById(R.id.select_fix_category_ac_icon);
		select_fix_category_ac_text = (TextView) findViewById(R.id.select_fix_category_ac_text);
		select_fix_category_plumbing_text = (TextView) findViewById(R.id.select_fix_category_plumbing_text);
		select_fix_category_electrician_text = (TextView) findViewById(R.id.select_fix_category_electrician_text);
		select_fix_category_painting_text = (TextView) findViewById(R.id.select_fix_category_painting_text);
		select_fix_category_carpenter_text = (TextView) findViewById(R.id.select_fix_category_carpenter_text);
		select_fix_category_locksmith_text = (TextView) findViewById(R.id.select_fix_category_locksmith_text);
		select_fix_category_pestCtrl_text = (TextView) findViewById(R.id.select_fix_category_pestCtrl_text);
		select_fix_category_sanitization_text = (TextView) findViewById(R.id.select_fix_category_sanitization_text);
		select_fix_category_plumbing_icon = (ImageView) findViewById(R.id.select_fix_category_plumbing_icon);
		select_fix_category_electrician_icon = (ImageView) findViewById(R.id.select_fix_category_electrician_icon);
		select_fix_category_painting_icon = (ImageView) findViewById(R.id.select_fix_category_painting_icon);
		select_fix_category_carpenter_icon = (ImageView) findViewById(R.id.select_fix_category_carpenter_icon);
		select_fix_category_locksmith_icon = (ImageView) findViewById(R.id.select_fix_category_locksmith_icon);
		select_fix_category_pestCtrl_icon = (ImageView) findViewById(R.id.select_fix_category_pestCtrl_icon);
		select_fix_category_sanitization_icon = (ImageView) findViewById(R.id.select_fix_category_sanitization_icon);
		general_works_button_icon = (ImageView) findViewById(R.id.general_works_button_icon);
		general_works_button_text = (TextView) findViewById(R.id.general_works_button_text);
		general_works_button = (LinearLayout) findViewById(R.id.general_works_button);

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
						//EasyHomeFixApp.setCategoryIds(CategoryId);
					//	EmailSignupBaseHolder userObject=new EmailSignupBaseHolder();
					//	userObject.setData(availableBaseHolder.getData()));
				//	UserDAO.getInstance().getUserDetails()
					//	UserDAO.getInstance().getUserDetails().getUser().setFixerJobCategories(fixerJobCategoryList);
						
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
				Utils.showDialog(FirstLoginPage.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(FirstLoginPage.this, ""+result, "ERROR");
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
					//	EasyHomeFixApp.setCategoryIds(CategoryId);
						
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
				Utils.showDialog(FirstLoginPage.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(FirstLoginPage.this, ""+result, "ERROR");
			}
		}
		
		
	}

}
