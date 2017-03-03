package com.xminds.easyhomefix.activities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.Address;
import com.xminds.easyhomefix.Holder.AddressBaseHolder;
import com.xminds.easyhomefix.Holder.AddressListBaseHolder;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.GoogleBaseHolder;
import com.xminds.easyhomefix.Holder.GoogleResultsBaseHolder;
import com.xminds.easyhomefix.Holder.Job;
import com.xminds.easyhomefix.Holder.JobCategories;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.JobSubCategoriesBaseHolder;
import com.xminds.easyhomefix.Managers.CategoriesManager;
import com.xminds.easyhomefix.Managers.GoogleApiManager;
import com.xminds.easyhomefix.Managers.JobCreationManager;
import com.xminds.easyhomefix.Managers.NotificationManager;
import com.xminds.easyhomefix.actionbar.BaseActivity;
import com.xminds.easyhomefix.actionbar.DateTimePicker;
import com.xminds.easyhomefix.adapter.AddressAdapter;
import com.xminds.easyhomefix.adapter.SubcategoriesAdpater;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;


public class EditFixDetailsActivity extends BaseActivity {

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
	private int fullScreenWidth ;
	private int fullScreenWidthMinus ;
	private int fullScreenHeight;
	private int requestcode = 0;
	private EditFixDetailsCallBack editFixDetailsCallBack;
	private String jobId;
	private String catId;
	private  List<JobCategories> jobsubcategories=new ArrayList<JobCategories>();
	private SubcategoriesAdpater subcategoryadapter;
	private JobSubCategoriesCallBack jobSubCategoriesCallBack;
	private ProgressDialog pDialog;
	private String jobTypeId;
	private LogoutReceiver logoutReceiver;
	private LinearLayout linear_righticons;
	private Spinner spinner;
	private String[] time_values = new String[] { "Please select time",
			"08:00 AM-11:00 AM", "11:00 AM-02:00 PM", "02:00 PM-05:00 PM", "05:00 PM-08:00 PM",
			"08:00 PM-08:00 AM" };
	private TimeAdapter timeAdapter;
	private LinearLayout timepicker_linear;
	private String timeString;
	private int spinnerPostion = 0;
	static final int DATE_DIALOG_ID = 999;
	private int myear;
	private int mmonth;
	private int mday;
	private Spinner addrss_spinner;
	private boolean isAddressSelectedFromList = false;
	private String formatedaddress;
	private ProgressDialog progressDialog;
	private GetAddressListCallBack getAddressListCallBack;
	private AddressAdapter addressAdapter;
	private List<Address> addressList = new ArrayList<Address>();
	private JobDetailsCallback jobDetailsCallBack;
	private Job job;
	private boolean istimecorrect;
	private String time;
	private GoogleAddressCallback googleaddresscallback;
	private GoogleAddressstoringcallback googleAddressstoringcallback;
	private GetAddressCallBack getAddressCallBack;
	private boolean isFound;
	private double latitude;
	private double longitude;
	private String postalcode;
	private String floorno;
	private String unitno;
	private String editdetails;
	private String datepicker;
	
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
		setContentView(R.layout.edit_fix_details);
		initViews();
		initManagers();
		setVisibilities();
		setText();
		setCurrentDateOnView();
		
		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
		
		Display display = EditFixDetailsActivity.this.getWindow().getWindowManager().getDefaultDisplay();
		 fullScreenWidth = display.getWidth();
		 fullScreenHeight=display.getHeight();
		 
		 jobId = getIntent().getExtras().getString("jobId");
		 catId = getIntent().getExtras().getString("catId");
		 jobTypeId = getIntent().getExtras().getString("jobTypeId");
		 NotificationManager.getInstance().getNotificationListItemObject(EditFixDetailsActivity.this, jobId, jobDetailsCallBack, requestcode);
		 pDialog=ProgressDialog.show(EditFixDetailsActivity.this, null, null);
		 CategoriesManager.getInstance().getSubCategories(
					EditFixDetailsActivity.this, jobSubCategoriesCallBack,catId,requestcode);
		back_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		spinner_fix_looking_for.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				jobTypeId=jobsubcategories.get(position).getId();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
				
			}
		});
		
		edit_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	
				postalcode=edittext_postalcode.getText().toString();
				floorno=edittext_floorno.getText().toString();
				unitno=edittext_unitno.getText().toString();
				editdetails=edittext_details.getText().toString();
				datepicker=datepicker_edittext.getText().toString();
				
				
				time=timeString.toString().replace("-", " ");
				Log.e("", "$$$$$$$$$$$$$$$$$$$TIMEEEEEEEE$$$$$$$$$$$$$$$$$"+time);
				//time = timeString.toString();
				if(postalcode.length()>0){
					if(postalcode.length()!=6)
					 {
						edittext_postalcode.setError("please enter Valid Postal Code",null);
					 }
				}
				
				if(floorno.length()>0)
				 {
					//floornoValidationFlag = true;
				 }				// TODO Auto-generated method stub
					
				 else
				 {
					 floornoValidationFlag = false;
					 
				 }
                 
				if(unitno.length()>0)
				 {
					unitnoValidationFlag = true;
				 }
				 else
				 {
					 unitnoValidationFlag = false;
					 
				 }
				if(datepicker.length()>0)
				 {
					datepickerValidationFlag = true;
				 }
				 else
				 {
					 datepickerValidationFlag = false;
					 
				 }
				
				
				
				if(editdetails.length()>0)
				 {
					editdetailsValidationFlag = true;
				 }
				 else
				 {
					 editdetailsValidationFlag = false;
					 
				 }
				
				Calendar c = Calendar.getInstance();

				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				String todaysdate = df.format(c.getTime());

				try {
					java.util.Date date1 = df.parse(todaysdate);
					java.util.Date date2 = df.parse(datepicker);
					if (date1.compareTo(date2) < 0) {
						Log.e("", "" + datepicker + " is Greater than my"
								+ todaysdate);
						istimecorrect = true;
					} else {
						istimecorrect = false;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				

				if (istimecorrect == false) {
					int Hr24 = c.get(Calendar.HOUR_OF_DAY);
					if (time.equalsIgnoreCase("08:00 AM 11:00 AM")) {
						if (Hr24 >= 8 && Hr24 <= 11) {
							// ok
							istimecorrect = true;
						} else {

							istimecorrect = false;
						}

					} else if (time.equalsIgnoreCase("11:00 AM 02:00 PM")) {
						if (Hr24 >= 8 && Hr24 <= 14) {
							istimecorrect = true;
							// ok
						} else {

							istimecorrect = false;
						}

					} else if (time.equalsIgnoreCase("02:00 PM 05:00 PM")) {
						if (Hr24 >= 8 && Hr24 <= 17) {
							istimecorrect = true;
							// ok
						} else {
							istimecorrect = false;

						}

					} else if (time.equalsIgnoreCase("05:00 PM 08:00 PM")) {

						if (Hr24 >= 8 && Hr24 <= 20) {
							istimecorrect = true;
							// ok
						} else {

							istimecorrect = false;
						}
					} else if (time.equalsIgnoreCase("08:00 PM 08:00 AM")) {

						if (Hr24 >= 8 && Hr24 <= 24) {
							istimecorrect = true;
							// ok
						} else {
							istimecorrect = false;
							// istimecorrect=false;
						}

					}

				}
				
				
				
					Log.e("","++++++++++++++++++++"+UtilValidate.isEmpty(postalcode)+" "+UtilValidate.isEmpty(floorno)+" "+
					UtilValidate.isEmpty(unitno)+" "+UtilValidate.isEmpty(editdetails)+" "+UtilValidate.isEmpty(datepicker)+" J "+jobTypeId);
					
					final Dialog dialog = new Dialog(context);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.edit_fix_details_pop_up);
					dialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
					Log.e("", "WIDTH>>"+fullScreenWidth);
					fullScreenWidthMinus=fullScreenWidth-40;
					dialog.getWindow().setLayout(fullScreenWidthMinus,LayoutParams.WRAP_CONTENT);
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
							//dialog.dismiss();
							
							Log.e("", "formataddress"+formatedaddress);
							Log.e("", "postalcode"+postalcode);
							if(formatedaddress!=null)
							{
								if(formatedaddress.contains(postalcode)){
									JobCreationManager.getInstance().editFixDetails(EditFixDetailsActivity.this,
											jobId , postalcode, floorno, unitno, editdetails, jobTypeId, datepicker+" "+time,formatedaddress, editFixDetailsCallBack, requestcode);
									dialog.dismiss();
								}else{
									if(CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable()){
										JobCreationManager.getInstance().getAddressFromServer(
												EditFixDetailsActivity.this, postalcode,
												getAddressCallBack, requestcode);
									}else{
										getAddressfromGoogle();
									}
									dialog.dismiss();
								}
							}
							
						}
					});
					dialog.show();
			}
		});
		
		datepicker_edittext.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
			
				//showDateTimeDialog();
				showDialog(DATE_DIALOG_ID);
			}
		});
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				// timeString=spinner.getSelectedItem().toString();

				timeString = null;
				timeString = time_values[position];
				if (!timeString.equals(null)) {
					spinnerPostion = timeAdapter.getPosition(timeString);

					spinner.setSelection(spinnerPostion);

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		
		addrss_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (addressList.get(position).getFloor() !="") {
Log.e("", "####################################"+addressList.get(position)
		.getFloor());
				edittext_floorno.setText(addressList.get(position)
							.getFloor());
					edittext_unitno.setText(addressList.get(position).getUnit());
					edittext_postalcode.setText(addressList.get(position)
							.getPostal_code());
					formatedaddress = addressList.get(position)
							.getBlock_number();
					isAddressSelectedFromList = true;

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});
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

	private void showDateTimeDialog() {
		
		//
		// Create the dialog
		final Dialog mDateTimeDialog = new Dialog(this);
		// Inflate the root layout
		final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater().inflate(R.layout.date_time_dialog, null);
		// Grab widget instance
		final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView.findViewById(R.id.DateTimePicker);
		// Check is system is set to use 24h time (this doesn't seem to work as expected though)
		final String timeS = android.provider.Settings.System.getString(getContentResolver(), android.provider.Settings.System.TIME_12_24);
		final boolean is24h = !(timeS == null || timeS.equals("12"));
		
		// Update demo TextViews when the "OK" button is clicked 
		((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mDateTimePicker.clearFocus();
				// TODO Auto-generated method stub
				
				/*Toast.makeText(EditFixDetailsActivity.this, "date"+mDateTimePicker.get(Calendar.YEAR) + "/" + (mDateTimePicker.get(Calendar.MONTH)+1) + "/"
						+ mDateTimePicker.get(Calendar.DAY_OF_MONTH), Toast.LENGTH_LONG).show();
				
				Toast.makeText(EditFixDetailsActivity.this, "time"+mDateTimePicker.get(Calendar.HOUR) + ":" + mDateTimePicker.get(Calendar.MINUTE) + " "
						+ (mDateTimePicker.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM"), Toast.LENGTH_LONG).show();*/
				
				String time=""+mDateTimePicker.get(Calendar.YEAR) + "/" + (mDateTimePicker.get(Calendar.MONTH)+1) + "/"
						+ mDateTimePicker.get(Calendar.DAY_OF_MONTH)+" "+mDateTimePicker.get(Calendar.HOUR) + ":" + mDateTimePicker.get(Calendar.MINUTE) + " "
								+ (mDateTimePicker.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
				datepicker_edittext.setText(time);
				
				/*if (mDateTimePicker.is24HourView()) {
					((TextView) findViewById(R.id.time)).setText(mDateTimePicker.get(Calendar.HOUR_OF_DAY) + ":" + mDateTimePicker.get(Calendar.MINUTE));
				} else {
					((TextView) findViewById(R.id.time)).setText(mDateTimePicker.get(Calendar.HOUR) + ":" + mDateTimePicker.get(Calendar.MINUTE) + " "
							+ (mDateTimePicker.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM"));
				}*/
				mDateTimeDialog.dismiss();
			}
		});

		// Cancel the dialog when the "Cancel" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDateTimeDialog.cancel();
			}
		});

		// Reset Date and Time pickers when the "Reset" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDateTimePicker.reset();
			}
		});
		
		// Setup TimePicker
		mDateTimePicker.setIs24HourView(is24h);
		// No title on the dialog window
		mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Set the dialog content view
		mDateTimeDialog.setContentView(mDateTimeDialogView);
		// Display the dialog
		mDateTimeDialog.show();
	}

	private void setText() {
		// TODO Auto-generated method stub
		textview_editdetails.setText("Edit Details");
		datepicker_edittext.setTextColor(getResources().getColor(R.color.dark_green));
		//datepicker_edittext.setText("Please select date and time");
		datepicker_edittext.setHint("Please select Date");
		datepicker_edittext.setHintTextColor(getResources().getColor(R.color.dark_green));
	}


	private void initManagers() {
		// TODO Auto-generated method stub
		editFixDetailsCallBack=new EditFixDetailsCallBack();
		jobSubCategoriesCallBack=new JobSubCategoriesCallBack();
		getAddressListCallBack = new GetAddressListCallBack();
		jobDetailsCallBack = new JobDetailsCallback();
		googleaddresscallback = new GoogleAddressCallback();
		googleAddressstoringcallback = new GoogleAddressstoringcallback();
		getAddressCallBack = new GetAddressCallBack();
	}

	private void setVisibilities() {
		// TODO Auto-generated method stub
		avatar.setVisibility(View.GONE);
		back_button.setVisibility(View.VISIBLE);
		textview_editdetails.setVisibility(View.VISIBLE);
		linear_righticons.setVisibility(View.INVISIBLE);
		
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
		linear_righticons = (LinearLayout)findViewById(R.id.linear_righticons);
		spinner = (Spinner) findViewById(R.id.spinner);
		timeAdapter = new TimeAdapter(EditFixDetailsActivity.this,
				R.layout.select_time, time_values);
		spinner.setAdapter(timeAdapter);
		addrss_spinner = (Spinner)findViewById(R.id.addrss_spinner);
	}
	
	private class EditFixDetailsCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (responseCode == requestcode) {
				JobCreationBaseHolder jobCreationBaseHolder = (JobCreationBaseHolder) result;
				if (UtilValidate.isNotNull(jobCreationBaseHolder)) {
					if (jobCreationBaseHolder.isSuccess()) {
						Toast.makeText(EditFixDetailsActivity.this, "Upadated Successfully", Toast.LENGTH_LONG).show();
						NotificationManager.getInstance().getNotificationListItemObject(EditFixDetailsActivity.this, jobId, jobDetailsCallBack, requestcode);
					}else{
						Toast.makeText(EditFixDetailsActivity.this, "Some problem while updating...Try again", Toast.LENGTH_LONG).show();
					}
				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(EditFixDetailsActivity.this, "Please check your internet connection.","ERROR");
			}
			else
			{
				Utils.showDialog(EditFixDetailsActivity.this, "Some problem while updating...Try again","ERROR");
			}
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class JobSubCategoriesCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			
			if (responseCode == requestcode) {
				JobSubCategoriesBaseHolder jobSubCategoriesBaseHolder = (JobSubCategoriesBaseHolder) result;
				if (UtilValidate.isNotNull(jobSubCategoriesBaseHolder)) {
					if (jobSubCategoriesBaseHolder.isSuccess()) {
						EasyHomeFixApp.setJobcategories(jobSubCategoriesBaseHolder.getData());
					 EasyHomeFixApp.setJobsubcategories(null);

						jobsubcategories.clear();
						jobsubcategories=jobSubCategoriesBaseHolder
								.getData();
						 EasyHomeFixApp.setJobsubcategories(jobsubcategories);
						subcategoryadapter = new SubcategoriesAdpater(EditFixDetailsActivity.this, jobsubcategories);
						spinner_fix_looking_for.setAdapter(subcategoryadapter);
						subcategoryadapter.notifyDataSetChanged();
						
						for(int i=0; i<jobsubcategories.size();i++){
							if(jobsubcategories.get(i).getId().equals(jobTypeId)){
								spinner_fix_looking_for.setSelection(i);
							}
						}
						if (pDialog != null) {
							pDialog.dismiss();
						}
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
				Utils.showDialog(EditFixDetailsActivity.this, "Please check your internet connection.", "ERROR");
				//Toast.makeText(EditFixDetailsActivity.this, text, duration)
			}
			else
			{
				Utils.showDialog(EditFixDetailsActivity.this, ""+result, "ERROR");	
			}

		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}

	}
	
	public class TimeAdapter extends ArrayAdapter<String> {

		private String[] objects;

		public TimeAdapter(Context ctx, int txtViewResourceId, String[] objects) {
			super(ctx, txtViewResourceId, objects);
			this.objects = objects;
		}

		@Override
		public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
			return getCustomView(position, cnvtView, prnt);
		}

		@Override
		public View getView(int pos, View cnvtView, ViewGroup prnt) {
			return getCustomView(pos, cnvtView, prnt);
		}

		public View getCustomView(int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();

			View timeSpinner = inflater.inflate(R.layout.select_time, parent,
					false);
			TextView time_text = (TextView) timeSpinner
					.findViewById(R.id.time_text);
			time_text.setText(time_values[position]);

			return timeSpinner;
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:

			// set date picker as current date
			DatePickerDialog _date = new DatePickerDialog(this,
					datePickerListener, myear, mmonth, mday) {

				@Override
				public void onDateChanged(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {

					view.setMinDate(System.currentTimeMillis() - 1000);
					if (year < myear)
						view.updateDate(myear, mmonth, mday);

					if (monthOfYear < mmonth && year == myear)
						view.updateDate(myear, mmonth, mday);

					if (dayOfMonth < mday && year == myear
							&& monthOfYear == mmonth)
						view.updateDate(myear, mmonth, mday);

				}
			};
			return _date;
		}
		return null;
	}

/*	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {

			myear = selectedYear;
			mmonth = selectedMonth;
			mday = selectedDay;

			// set selected date into textview
			datepicker_edittext.setText(new StringBuilder()
					// Month is 0 based, just add 1
					.append(mday).append("-").append(mmonth + 1).append("-")
					.append(myear).append(" "));

			setCurrentDate();

		}
	};*/
	
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {

			myear = selectedYear;
			mmonth = selectedMonth;
			mday = selectedDay;

			// set selected date into textview
			datepicker_edittext.setText(new StringBuilder()
					// Month is 0 based, just add 1
					.append(mday).append("-").append(mmonth + 1).append("-")
					.append(myear).append(" "));

			setCurrentDate();

		}
	};
	public void setCurrentDate() {

		final Calendar c = Calendar.getInstance();
		myear = c.get(Calendar.YEAR);
		mmonth = c.get(Calendar.MONTH);
		mday = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview

	}
	
	public void setCurrentDateOnView() {

		final Calendar c = Calendar.getInstance();
		myear = c.get(Calendar.YEAR);
		mmonth = c.get(Calendar.MONTH);
		mday = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
		datepicker_edittext.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(mday).append("-").append(mmonth + 1).append("-")
				.append(myear).append(" "));
	}
	
	private class GetAddressListCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			if (requestcode == responseCode) {
				if (result != null) {
					AddressListBaseHolder addressListBaseHolder = (AddressListBaseHolder) result;
					if (addressListBaseHolder.isSuccess()) {
						if (addressListBaseHolder != null
								&& UtilValidate
										.isNotEmpty(addressListBaseHolder
												.getData())) {

							addressList.clear();
							addressList.addAll(addressListBaseHolder.getData());
							addressAdapter = new AddressAdapter(
									EditFixDetailsActivity.this, addressList);
							addrss_spinner.setAdapter(addressAdapter);
							for(int i=0;i<addressList.size();i++){
								if(addressList.get(i).getBlock_number().equalsIgnoreCase(job.getAddress().getBlock_number())){
									addrss_spinner.setSelection(i);
								}
							}
							isAddressSelectedFromList = false;
							
							
							if(job!=null)
							{
							if(job.getAddress()!=null)
							{
								if(job.getAddress().getPostal_code()!=null)
							edittext_postalcode.setText(job.getAddress().getPostal_code()+"");
								if(job.getAddress().getFloor()!=null)
							edittext_floorno.setText(job.getAddress().getFloor()+"");
								if(job.getAddress().getUnit()!=null)
							edittext_unitno.setText(job.getAddress().getUnit()+"");
							}	}
							
						
							//progressDialog.dismiss();
						} else {
							Toast.makeText(EditFixDetailsActivity.this,
									"No Data Found!!!", Toast.LENGTH_LONG)
									.show();
							//progressDialog.dismiss();
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
				Utils.showDialog(EditFixDetailsActivity.this, "Please check your internet connection.", "ERROR");
			}
			Toast.makeText(EditFixDetailsActivity.this, result,
					Toast.LENGTH_LONG).show();
			
			
			//progressDialog.dismiss();
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			//progressDialog.dismiss();
		}

	}
	
	private class JobDetailsCallback implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			if(responseCode == requestcode){
				if(result != null){
					EmailSignupBaseHolder jobItemBaseHolder = (EmailSignupBaseHolder) result;
					if(jobItemBaseHolder.isSuccess()){
						if(jobItemBaseHolder.getData() != null){
							if(jobItemBaseHolder.getData().getJob() != null){
								job = jobItemBaseHolder.getData().getJob();
								if(CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable()){
									JobCreationManager.getInstance().getAddressList(
											EditFixDetailsActivity.this,
											CurrentlyLoggedUserDAO.getInstance().getUserId(),
											getAddressListCallBack, requestcode);
									
							
									edittext_postalcode.setText(job.getAddress().getPostal_code()+"");
									edittext_floorno.setText(job.getAddress().getFloor()+"");
									edittext_unitno.setText(job.getAddress().getUnit()+"");
									String datetime[] = job.getJobDateTimeSlot().split("\\s+");
									datepicker_edittext.setText(datetime[0]);
									formatedaddress=job.getAddress().getBlock_number();
								
									for(int i=0;i<time_values.length;i++){
										Log.e("", "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+time_values[i]);
									
										if(time_values[i].equalsIgnoreCase(datetime[1]+" "+datetime[2]+"-"+datetime[3]+" "+datetime[4])){
											
											Log.e("", "{{{{{{{{{{{{{{{}}}}}}}}}}}}}}}}"+"first:"+time_values[i]+" and  second "+datetime[1]);
											spinner.setSelection(i);
										}
									}
									edittext_details.setText(job.getJobDetails());
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
				/*Toast.makeText(EditFixDetailsActivity.this,
						"Please check your internet connection.",
						Toast.LENGTH_SHORT).show();*/
				//Utils.showDialog(EditFixDetailsActivity.this, "Please check your internet connection.", "ERROR");
			}

			
		}

		@Override
		public void onFinish(int requestcode, JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public void getAddressfromGoogle() {
		// TODO Auto-generated method stub

		GoogleApiManager.getInstance().getAddressList(
				EditFixDetailsActivity.this,
				edittext_postalcode.getText().toString(),
				googleaddresscallback, requestcode);
		Toast.makeText(EditFixDetailsActivity.this, "address from google",
				Toast.LENGTH_SHORT).show();
	}
	
	public class GoogleAddressCallback implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			if (responseCode == requestcode) {
				GoogleBaseHolder googleBaseHolder = (GoogleBaseHolder) result;
				if (googleBaseHolder != null) {

					if (googleBaseHolder.getResults() != null) {
						for (GoogleResultsBaseHolder goo : googleBaseHolder
								.getResults()) {

							if (goo.getFormatted_address()
									.contains("Singapore")) {
								if (goo.getGeometry() != null) {
									if (goo.getGeometry().getLocation() != null) {

										latitude = goo.getGeometry()
												.getLocation().getLat();
										longitude = goo.getGeometry()
												.getLocation().getLng();
										isFound = true;

										break;
									}

								}

							}

						}

						if (isFound) {

							Log.e("", "is found");
							// Toast.makeText(ListYourFixesActivity.this,
							// "found lat: "+latitude +"long:"+longitude,
							// Toast.LENGTH_SHORT).show();
							GoogleApiManager.getInstance()
									.getAddressListfromLatlong(
											EditFixDetailsActivity.this,
											String.valueOf(latitude),
											String.valueOf(longitude),
											googleAddressstoringcallback,
											responseCode);
						} else {

							edittext_postalcode.setText("");
							edittext_postalcode.setTextSize(14);
							edittext_postalcode
									.setHintTextColor(EditFixDetailsActivity.this
											.getResources().getColor(
													R.color.Red));
							edittext_postalcode.setError(
									"Postal code not found", null);
							// Toast.makeText(ListYourFixesActivity.this,
							// "notfound ", Toast.LENGTH_SHORT).show();
						}

					}
					Utils.showDialog(EditFixDetailsActivity.this, "Please check your internet connection.", "ERROR");
				}
			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
			
				Utils.showDialog(EditFixDetailsActivity.this, "Please check your internet connection.", "ERROR");
			}

		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub

		}

	}

	public class GoogleAddressstoringcallback implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			if (responseCode == requestcode) {
				GoogleBaseHolder googleBaseHolder = (GoogleBaseHolder) result;
				if (googleBaseHolder != null) {

					if (googleBaseHolder.getResults() != null) {
						for (GoogleResultsBaseHolder goo : googleBaseHolder
								.getResults()) {
							if (goo.getFormatted_address().length() > 0) {
								formatedaddress = goo.getFormatted_address();
								break;
							}

						}
						JobCreationManager.getInstance().editFixDetails(EditFixDetailsActivity.this,
								jobId , postalcode, floorno, unitno, editdetails, jobTypeId, datepicker+" "+timeString,formatedaddress, editFixDetailsCallBack, requestcode);

					}

				}
			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				/*Toast.makeText(EditFixDetailsActivity.this,
						"Please check your internet connection",
						Toast.LENGTH_SHORT).show();*/
				Utils.showDialog(EditFixDetailsActivity.this, "Please check your internet connection.", "ERROR");
			}

		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub

		}

	}

	//

	private class GetAddressCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			if (requestcode == responseCode) {
				if (result != null) {
					AddressBaseHolder addressBaseHolder = (AddressBaseHolder) result;
					if (addressBaseHolder.isSuccess()) {
						if (addressBaseHolder.getData() != null) {
							if (addressBaseHolder.getData().getAddress() != null) {
								
								formatedaddress = addressBaseHolder.getData()
										.getAddress().getBlock_number();
								JobCreationManager.getInstance().editFixDetails(EditFixDetailsActivity.this,
										jobId , postalcode, floorno, unitno, editdetails, jobTypeId, datepicker+" "+timeString,formatedaddress, editFixDetailsCallBack, requestcode);
							} else {
								Toast.makeText(EditFixDetailsActivity.this,
										"No address found!!!",
										Toast.LENGTH_LONG).show();
							}
						}

					}

				}
			} else {

				getAddressfromGoogle();
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			/*Toast.makeText(EditFixDetailsActivity.this, result,
					Toast.LENGTH_LONG).show();*/
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(EditFixDetailsActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(EditFixDetailsActivity.this, ""+result, "ERROR");
			}
			
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub

		}

	}
}
