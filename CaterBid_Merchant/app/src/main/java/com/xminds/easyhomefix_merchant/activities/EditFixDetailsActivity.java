package com.xminds.easyhomefix_merchant.activities;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.xminds.easyfix_merchant.datepicker.DatePickerDailog;
import com.easyhomefix.R;

public class EditFixDetailsActivity extends Activity {

	private EditText edittext_postalcode;
	private EditText edittext_floorno;
	private EditText edittext_unitno;
	private EditText datepicker_edittext;
	private Spinner spinner_fix_looking_for;
	private EditText edittext_details;
	private ImageView avatar;
	private ImageView back_button;
	private TextView textview_editdetails;
	private LinearLayout edit_button;
	private Calendar dateandtime;
	boolean  postalcodeValidationFlag = false;
	boolean  floornoValidationFlag = false;
	boolean  unitnoValidationFlag = false;
	boolean  editdetailsValidationFlag = false;
	boolean  datepickerValidationFlag = false;
	
	Context context=this;
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
		setContentView(R.layout.edit_fix_details);
		initViews();
		initManagers();
		setVisibilities();
		setText();
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		back_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		edit_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	
				final String postalcode=edittext_postalcode.getText().toString();
				final String floorno=edittext_floorno.getText().toString();
				final String unitno=edittext_unitno.getText().toString();
				final String editdetails=edittext_details.getText().toString();
				final String datepicker=datepicker_edittext.getText().toString();
				
				if(postalcode.length()==6)
				 {
					postalcodeValidationFlag = true;
				 }
				 else
				 {
					 postalcodeValidationFlag = false;
					 edittext_postalcode.setError("please enter Postal Code");
				 }
				
				if(floorno.length()>0)
				 {
					floornoValidationFlag = true;
				 }				// TODO Auto-generated method stub
					
				 else
				 {
					 floornoValidationFlag = false;
					 edittext_floorno.setError("please enter Floor no");
				 }
                 
				if(unitno.length()>0)
				 {
					unitnoValidationFlag = true;
				 }
				 else
				 {
					 unitnoValidationFlag = false;
					 edittext_unitno.setError("please enter Unit no");
				 }
				if(datepicker.length()>0)
				 {
					datepickerValidationFlag = true;
				 }
				 else
				 {
					 datepickerValidationFlag = false;
					 datepicker_edittext.setError("please select date");
				 }
				
				
				
				if(editdetails.length()>0)
				 {
					editdetailsValidationFlag = true;
				 }
				 else
				 {
					 editdetailsValidationFlag = false;
					 edittext_details.setError("please enter details");
				 }
				
				if(postalcodeValidationFlag&&floornoValidationFlag&&unitnoValidationFlag&&editdetailsValidationFlag&&datepickerValidationFlag)
				{
					

					final Dialog dialog = new Dialog(context);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.edit_fix_details_pop_up);
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
					TextView yes_make_changes_button=(TextView) dialog.findViewById(R.id.button_dialog_yes);
					TextView let_me_do_that_now_button=(TextView) dialog.findViewById(R.id.button_dialog_not_yet);
					let_me_do_that_now_button.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							
						}
					});
					yes_make_changes_button.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
				
				
				
			}
		});
		
datepicker_edittext.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				DatePickerDailog dp = new DatePickerDailog(EditFixDetailsActivity.this,
						dateandtime, new DatePickerDailog.DatePickerListner() {
							@Override
							public void OnDoneButton(Dialog datedialog, Calendar c) {
								datedialog.dismiss();
								dateandtime.set(Calendar.YEAR, c.get(Calendar.YEAR));
								dateandtime.set(Calendar.MONTH,
										c.get(Calendar.MONTH));
								dateandtime.set(Calendar.DAY_OF_MONTH,
										c.get(Calendar.DAY_OF_MONTH));
								datepicker_edittext.setText(new SimpleDateFormat("MMMM dd yyyy")
								.format(c.getTime()));

					
							}

							@Override
							public void OnCancelButton(Dialog datedialog) {
								// TODO Auto-generated method stub
								datedialog.dismiss();
							}
						});
				dp.show();
				
				
				
				
				
			}
		});
	}
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	unregisterReceiver(logoutReceiver);
	super.onDestroy();
}
	
	private void setText() {
		// TODO Auto-generated method stub
		textview_editdetails.setText("Edit Details");
		datepicker_edittext.setTextColor(getResources().getColor(R.color.dark_green));
		//datepicker_edittext.setText("Please select date and time");
		datepicker_edittext.setHintTextColor(getResources().getColor(R.color.dark_green));
	}


	private void initManagers() {
		// TODO Auto-generated method stub
		
	}

	private void setVisibilities() {
		// TODO Auto-generated method stub
		avatar.setVisibility(View.GONE);
		back_button.setVisibility(View.VISIBLE);
		textview_editdetails.setVisibility(View.VISIBLE);
		
		
	}

	private void initViews() {
		// TODO Auto-generated method stub
		
		edittext_postalcode=(EditText) findViewById(R.id.postal_code_edittext);
		edittext_floorno=(EditText) findViewById(R.id.floor_number_edittext);
		edittext_unitno=(EditText) findViewById(R.id.unit_number_edittext);
		datepicker_edittext=(EditText) findViewById(R.id.date_time_selector);
		edittext_details=(EditText) findViewById(R.id.details_edittext);
		spinner_fix_looking_for=(Spinner) findViewById(R.id.fix_looking_for);
		avatar=(ImageView) findViewById(R.id.image_avatar);
		back_button=(ImageView)findViewById(R.id.image_back);
		textview_editdetails=(TextView) findViewById(R.id.textview_trackyourfix);
		edit_button=(LinearLayout) findViewById(R.id.edit_button);
		dateandtime = Calendar.getInstance(Locale.US);
	}
	

}
