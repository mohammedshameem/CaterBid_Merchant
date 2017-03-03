package com.xminds.easyhomefix.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.R.integer;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.Address;
import com.xminds.easyhomefix.Holder.AddressBaseHolder;
import com.xminds.easyhomefix.Holder.AddressListBaseHolder;
import com.xminds.easyhomefix.Holder.GoogleBaseHolder;
import com.xminds.easyhomefix.Holder.GoogleResultsBaseHolder;
import com.xminds.easyhomefix.Holder.JobCategories;
import com.xminds.easyhomefix.Holder.JobCategoriesBaseHolder;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Managers.CategoriesManager;
import com.xminds.easyhomefix.Managers.GoogleApiManager;
import com.xminds.easyhomefix.Managers.JobCreationManager;
import com.xminds.easyhomefix.actionbar.BaseActivity;
import com.xminds.easyhomefix.actionbar.DateTimePicker;
import com.xminds.easyhomefix.activities.SplashScreenActivity.JobCategoriesCallBack;
import com.xminds.easyhomefix.adapter.AddressAdapter;
import com.xminds.easyhomefix.adapter.FixItemsAdapter;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.horizontallistview.HorizontalListView;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class ListYourFixesActivity extends BaseActivity {
	private TextView textview_trackyourfix;
	private ImageView image_back;
	private FixItemsAdapter fixItemsAdapter;
	private HorizontalListView listView;
	private EditText datepicker_edittext;
	private LinearLayout datepicker_linear;
	private Calendar dateandtime;
	private LinearLayout linear_next;
	private EditText edittext_postalcode;
	boolean postalcodeValidationFlag = false;
	boolean datepickerValidationFlag = false;
	boolean timeValidationFlag = false;
	boolean unitnumberValidationFlag=false;
	private String pos;
	private List<JobCategories> jobsubcategories = new ArrayList<JobCategories>();
	private Spinner spinner;
	private ProgressDialog pDialog;
	private String[] time_values = new String[] { "Please select time",
			"08:00 AM-11:00 AM", "11:00 AM-02:00 PM", "02:00 PM-05:00 PM", "05:00 PM-08:00 PM",
			"08:00 PM-08:00 AM" };
	private TimeAdapter timeAdapter;
	private LinearLayout timepicker_linear;
	private String timeString;
	private int spinnerPostion = 0;
	private int myear;
	private int mmonth;
	private int mday;
	private int requestcode = 0;
	private boolean isFound;
	private GoogleAddressCallback googleaddresscallback;
	private GoogleAddressstoringcallback googleAddressstoringcallback;
	private double latitude;
	private double longitude;
	private String formatedaddress;
	private Spinner addrss_spinner;
	static final int DATE_DIALOG_ID = 999;
	private LogoutReceiver logoutReceiver;
	private GetAddressCallBack getAddressCallBack;
	private int requestCode = 0;
	private String postalcode;
	private String datepicker;
	private String unit_no;
	private String time;
	boolean istimecorrect = false;
	private GetAddressListCallBack getAddressListCallBack;
	private AddressAdapter addressAdapter;
	private EditText edt_txt_floorno;
	private EditText edt_txt_unitno;
	private List<Address> addressList = new ArrayList<Address>();
	private ProgressDialog progressDialog;
	private boolean isAddressSelectedFromList = false;
	private List<JobCategories> jobCategoryList = new ArrayList<JobCategories>();
	private JobCategoriesCallBack jobCategoriesCallBack;
	private LinearLayout address_picker_linear;
	private TextView nextbutton;
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
		setContentView(R.layout.listyourfix_page);
		initViews();
		initManagers();
		setVisibilites();
		setText();
		setCurrentDateOnView();
		
		CategoriesManager.getInstance().getCategories(ListYourFixesActivity.this, jobCategoriesCallBack, requestcode);
		if(CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable()){
			progressDialog = ProgressDialog.show(ListYourFixesActivity.this, null,
					"Fetching address List...");
			JobCreationManager.getInstance().getAddressList(
					ListYourFixesActivity.this,
					CurrentlyLoggedUserDAO.getInstance().getUserId(),
					getAddressListCallBack, requestCode);
		}
		else
		{
			address_picker_linear.setVisibility(View.GONE);
		}
		// logout broadcast receiver
		logoutReceiver = new LogoutReceiver();

		// Register the logout receiver
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.package.ACTION_LOGOUT");
		registerReceiver(logoutReceiver, intentFilter);

		Intent i = getIntent();
		String name = i.getStringExtra("name");

		if (name.equals("ac")) {
			pos = "1";
		} else if (name.equals("plumbing")) {
			pos = "2";
		} else if (name.equals("electrician")) {
			pos = "3";
		}

		else if (name.equals("painting")) {
			pos = "4";
		}

		else if (name.equals("carpenter")) {
			pos = "5";
		}

		else if (name.equals("locksmith")) {
			pos = "6";
		} else if (name.equals("pest")) {
			pos = "7";
		}

		else if (name.equals("sanitation")) {
			pos = "8";
		} else if (name.equals("generalworks")) {
			pos = "1000";
		}

/*		SlidingMenu sm = getSlidingMenu();
		sm.setSlidingEnabled(false);
		
	*/
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		image_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		datepicker_edittext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// showDateTimeDialog();
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
		//linear_next.setEnabled(true);
	
		linear_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				postalcode = edittext_postalcode.getText().toString();
				datepicker = datepicker_edittext.getText().toString();
				unit_no = edt_txt_unitno.getText().toString();
				//time = timeString.toString();
				time=timeString.toString().replace("-", " ");
				Log.e("", "$$$$$$$$$$$$$$$$$$$TIMEEEEEEEE$$$$$$$$$$$$$$$$$"+time);

				if (postalcode.length() == 6) {
					postalcodeValidationFlag = true;
					Log.e("","trye valuee>>>>>>>>>>>>>>>>");
				} else {
					postalcodeValidationFlag = false;
					/*edittext_postalcode.setError(
							"please enter 6 digit Postal Code", null);*/
					Utils.showDialog(ListYourFixesActivity.this, "Please enter your 6 digit postal code.", "ERROR");

				}
				if(unit_no.length()>0)
				{
					unitnumberValidationFlag=true;
				}
				else
				{
					unitnumberValidationFlag=false;
					if(postalcodeValidationFlag){
						Utils.showDialog(ListYourFixesActivity.this, "Please enter your unit number.", "ERROR");
					}
				}
				
				if (datepicker.length() > 0) {
					datepickerValidationFlag = true;
					datepicker_edittext.setError(null);
				} 
				else {
					datepickerValidationFlag = false;
					
					if(unitnumberValidationFlag){
						Utils.showDialog(ListYourFixesActivity.this, "Please select a valid date.", "ERROR");
					}
					
				}
				if (time.length() > 0) {
					timeValidationFlag = true;
					// spinner.setTag(null);
				} else {
					timeValidationFlag = false;
					if(datepickerValidationFlag)
					{
						Utils.showDialog(ListYourFixesActivity.this, "Please select a valid time.", "ERROR");
						
					}
					// spinner.setTag("please select time");
				}

				// TODO Auto-generated method stub
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

				if (spinnerPostion == 0) {
					/*Toast.makeText(ListYourFixesActivity.this,
							"Please Select a time", Toast.LENGTH_SHORT).show();*/
					
				}
				if (istimecorrect == false) {
					
					if(postalcodeValidationFlag&&datepickerValidationFlag)
					{
						Utils.showDialog(ListYourFixesActivity.this,
								"Please select a valid time.", "ERROR");
					}
					
				}

				if (postalcodeValidationFlag && datepickerValidationFlag
						&& timeValidationFlag && spinnerPostion != 0
						&& istimecorrect == true) {

					if (isAddressSelectedFromList) {
						
						 pDialog = ProgressDialog.show(ListYourFixesActivity.this,"","Loading..",true);	
						Intent i = new Intent(ListYourFixesActivity.this,
								ConfirmOrderActivity.class);
						i.putExtra("Date", datepicker);
						i.putExtra("Time", time);
						i.putExtra("postalcode", postalcode);
						for(JobCategories jobCategories : jobCategoryList){
							if(jobCategories.isSelected()){
								i.putExtra("catid", jobCategories.getId());
							}
						}
						//i.putExtra("catid", String.valueOf(pos));
						i.putExtra("formatedaddress", formatedaddress);
						i.putExtra("floorNo", edt_txt_floorno.getText()
								.toString());
						i.putExtra("unitNo", edt_txt_unitno.getText()
								.toString());
						Log.e("", "isAddressSelectedFromList");
					
						startActivity(i);
						finish();
						if (pDialog != null) {
							pDialog.dismiss();
						}
					} else {
						if(CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable()){
							JobCreationManager.getInstance().getAddressFromServer(
									ListYourFixesActivity.this, postalcode,
									getAddressCallBack, requestCode);
						}else{
							getAddressfromGoogle();
						}
					}
				}
			}
		});

		addrss_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (addressList.get(position).getFloor() != null) {

					edt_txt_floorno.setText(addressList.get(position)
							.getFloor());
					edt_txt_unitno.setText(addressList.get(position).getUnit());
					edittext_postalcode.setText(addressList.get(position)
							.getPostal_code());
					formatedaddress = addressList.get(position)
							.getBlock_number();
					isAddressSelectedFromList = true;

				}
				addrss_spinner.setSelection(0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});
			
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				for(JobCategories jobCategories : jobCategoryList){
					if(jobCategories.isSelected()){
						jobCategories.setSelected(false);
					}
				}
				jobCategoryList.get(position).setSelected(true);
				fixItemsAdapter.notifyDataSetChanged();
				listView.setSelectionFromLeft(position, 250);
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
	public void getAddressfromGoogle() {
		// TODO Auto-generated method stub

		GoogleApiManager.getInstance().getAddressList(
				ListYourFixesActivity.this,
				edittext_postalcode.getText().toString(),
				googleaddresscallback, requestcode);
		/*Toast.makeText(ListYourFixesActivity.this, "addresssss",
				Toast.LENGTH_SHORT).show();*/
	}

	public void setCurrentDateOnView() {

		final Calendar c = Calendar.getInstance();
		myear = c.get(Calendar.YEAR);
		mmonth = c.get(Calendar.MONTH);
		mday = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
	/*	datepicker_edittext.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(mday).append("-").append(mmonth + 1).append("-")
				.append(myear).append(" "));*/
	}

	public void setCurrentDate() {

		final Calendar c = Calendar.getInstance();
		myear = c.get(Calendar.YEAR);
		mmonth = c.get(Calendar.MONTH);
		mday = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview

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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(logoutReceiver);
		super.onDestroy();
	}

	private void showDateTimeDialog() {
		// Create the dialog
		final Dialog mDateTimeDialog = new Dialog(this);
		// Inflate the root layout
		final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater()
				.inflate(R.layout.date_time_dialog, null);
		// Grab widget instance
		final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView
				.findViewById(R.id.DateTimePicker);

		// Check is system is set to use 24h time (this doesn't seem to work as
		// expected though)
		final String timeS = android.provider.Settings.System.getString(
				getContentResolver(),
				android.provider.Settings.System.TIME_12_24);
		final boolean is24h = !(timeS == null || timeS.equals("12"));

		// Update demo TextViews when the "OK" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {

						mDateTimePicker.clearFocus();

						// TODO Auto-generated method stub

						Log.e("", "mDateTimePicker.get(Calendar.MONTH)+1"
								+ mDateTimePicker.get(Calendar.MONTH) + 1);

						String Dates = mDateTimePicker.get(Calendar.YEAR)
								+ "-"
								+ ((mDateTimePicker.get(Calendar.MONTH) + 1) < 10 ? ("0" + (mDateTimePicker
										.get(Calendar.MONTH) + 1))
										: (mDateTimePicker.get(Calendar.MONTH) + 1))
								+ "-"
								+ (mDateTimePicker.get(Calendar.DAY_OF_MONTH) < 10 ? ("0" + mDateTimePicker
										.get(Calendar.DAY_OF_MONTH))
										: (mDateTimePicker
												.get(Calendar.DAY_OF_MONTH)));

						datepicker_edittext.setText(Dates);

						mDateTimeDialog.dismiss();

					}
				});

		// Cancel the dialog when the "Cancel" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						mDateTimeDialog.cancel();
					}
				});

		// Reset Date and Time pickers when the "Reset" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime))
				.setOnClickListener(new OnClickListener() {

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
		textview_trackyourfix.setText("List your Fix");
		datepicker_edittext.setTextColor(getResources().getColor(
				R.color.dark_green));
		// datepicker_edittext.setText("Please select date and time");
		datepicker_edittext.setHintTextColor(getResources().getColor(
				R.color.dark_green));
		datepicker_edittext.setHint("Please select Date");
	}

	private void setVisibilites() {
		// TODO Auto-generated method stub

		textview_trackyourfix.setVisibility(View.VISIBLE);
		image_back.setVisibility(View.VISIBLE);

	}

	private void initManagers() {
		// TODO Auto-generated method stub
		googleaddresscallback = new GoogleAddressCallback();
		googleAddressstoringcallback = new GoogleAddressstoringcallback();
		getAddressCallBack = new GetAddressCallBack();
		getAddressListCallBack = new GetAddressListCallBack();
		jobCategoriesCallBack = new JobCategoriesCallBack();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		textview_trackyourfix = (TextView) findViewById(R.id.textview_trackyourfix);
		image_back = (ImageView) findViewById(R.id.image_back);
		listView = (HorizontalListView) findViewById(R.id.listview);
		datepicker_edittext = (EditText) findViewById(R.id.datepicker_edittext);
		dateandtime = Calendar.getInstance(Locale.US);
		datepicker_linear = (LinearLayout) findViewById(R.id.datepicker_linear);
		linear_next = (LinearLayout) findViewById(R.id.linear_next);
		edittext_postalcode = (EditText) findViewById(R.id.edittext_postalcode);

		timepicker_linear = (LinearLayout) findViewById(R.id.timepicker_linear);
		spinner = (Spinner) findViewById(R.id.spinner);
		timeAdapter = new TimeAdapter(ListYourFixesActivity.this,
				R.layout.select_time, time_values);
		spinner.setAdapter(timeAdapter);
		addrss_spinner = (Spinner) findViewById(R.id.addrss_spinner);
		edt_txt_floorno = (EditText) findViewById(R.id.edt_txt_floorno);
		nextbutton=(TextView)findViewById(R.id.nextbutton);
		edt_txt_unitno = (EditText) findViewById(R.id.edt_txt_unitno);
		address_picker_linear=(LinearLayout)findViewById(R.id.address_picker_linear);
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
											ListYourFixesActivity.this,
											String.valueOf(latitude),
											String.valueOf(longitude),
											googleAddressstoringcallback,
											responseCode);
						} else {

							edittext_postalcode.setText("");
							edittext_postalcode.setTextSize(14);
							edittext_postalcode
									.setHintTextColor(ListYourFixesActivity.this
											.getResources().getColor(
													R.color.Red));
							edittext_postalcode.setError(
									"Postal code not found", null);
							// Toast.makeText(ListYourFixesActivity.this,
							// "notfound ", Toast.LENGTH_SHORT).show();
						}

					}

				}
			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				/*Toast.makeText(ListYourFixesActivity.this,
						"Please check your internet connection",
						Toast.LENGTH_SHORT).show();*/
				Utils.showDialog(ListYourFixesActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(ListYourFixesActivity.this, ""+result, "ERROR");
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
						Intent i = new Intent(ListYourFixesActivity.this,
								ConfirmOrderActivity.class);
						i.putExtra("Date", datepicker);
						i.putExtra("Time", time);
						i.putExtra("postalcode", postalcode);
						for(JobCategories jobCategories : jobCategoryList){
							if(jobCategories.isSelected()){
								i.putExtra("catid", jobCategories.getId());
							}
						}
						i.putExtra("formatedaddress", formatedaddress);
						i.putExtra("floorNo", edt_txt_floorno.getText()
								.toString());
						i.putExtra("unitNo", edt_txt_unitno.getText()
								.toString());
						Log.e("", "api value from server");
						startActivity(i);
						finish();

					}

				}
			}

		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				/*Toast.makeText(ListYourFixesActivity.this,
						"Please check your internet connection",
						Toast.LENGTH_SHORT).show();*/
				Utils.showDialog(ListYourFixesActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(ListYourFixesActivity.this, ""+result, "ERROR");
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
			if (requestCode == responseCode) {
				if (result != null) {
					AddressBaseHolder addressBaseHolder = (AddressBaseHolder) result;
					if (addressBaseHolder.isSuccess()) {
						if (addressBaseHolder.getData() != null) {
							if (addressBaseHolder.getData().getAddress() != null) {
								Intent i = new Intent(
										ListYourFixesActivity.this,
										ConfirmOrderActivity.class);
								formatedaddress = addressBaseHolder.getData()
										.getAddress().getBlock_number();
								i.putExtra("Date", datepicker);
								i.putExtra("Time", time);
								i.putExtra("postalcode", postalcode);
								for(JobCategories jobCategories : jobCategoryList){
									if(jobCategories.isSelected()){
										i.putExtra("catid", jobCategories.getId());
									}
								}
								i.putExtra("formatedaddress", addressBaseHolder
										.getData().getAddress()
										.getBlock_number());
								Log.e("",
										"api value from server"
												+ String.valueOf(pos));
								i.putExtra("floorNo", edt_txt_floorno.getText()
										.toString());
								i.putExtra("unitNo", edt_txt_unitno.getText()
										.toString());
								startActivity(i);
								finish();
							} else {
								Toast.makeText(ListYourFixesActivity.this,
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
			/*Toast.makeText(ListYourFixesActivity.this, result,
					Toast.LENGTH_LONG).show();*/
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(ListYourFixesActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(ListYourFixesActivity.this, ""+result, "ERROR");
			}
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub

		}

	}

	private class GetAddressListCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			if (requestcode == responseCode) {
				if (result != null) {
					AddressListBaseHolder addressListBaseHolder = (AddressListBaseHolder) result;
					if (addressListBaseHolder.isSuccess()) {
						if (UtilValidate
										.isNotEmpty(addressListBaseHolder
												.getData())) {

							addressList.clear();
							Address address = new Address();
							address.setBlock_number("Please Select an Address");
							addressList.add(address);
							addressList.addAll(addressListBaseHolder.getData());
							addressAdapter = new AddressAdapter(
									ListYourFixesActivity.this, addressList);
							addrss_spinner.setAdapter(addressAdapter);
							progressDialog.dismiss();
						} else {
							
							address_picker_linear.setVisibility(View.GONE);
					/*		Toast.makeText(ListYourFixesActivity.this,
									"No Data Found!!!", Toast.LENGTH_LONG)
									.show();*/
							progressDialog.dismiss();
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
				Utils.showDialog(ListYourFixesActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(ListYourFixesActivity.this, ""+result, "ERROR");
			}
			
			/*Toast.makeText(ListYourFixesActivity.this, result,
					Toast.LENGTH_LONG).show();*/
			progressDialog.dismiss();
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
		}

	}
	
	//Job Categories
	public class JobCategoriesCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			if (responseCode == requestcode) {
				JobCategoriesBaseHolder jobCategoriesBaseHolder = (JobCategoriesBaseHolder) result;
				if (UtilValidate.isNotNull(jobCategoriesBaseHolder)) {
					if (jobCategoriesBaseHolder.isSuccess()) {
						if(UtilValidate.isNotNull(jobCategoriesBaseHolder.getData()))
							Log.e("", "size in ###################>"+jobCategoriesBaseHolder.getData().size());
							jobCategoryList.clear();
							jobCategoryList = jobCategoriesBaseHolder.getData();
							for(JobCategories jobCategories : jobCategoryList){
								if(jobCategories.getId().equalsIgnoreCase(pos)){
									jobCategories.setSelected(true);
								}
							}
							fixItemsAdapter = new FixItemsAdapter(ListYourFixesActivity.this,jobCategoryList);
							listView.setAdapter(fixItemsAdapter);
							int position=Integer.parseInt(pos) ;
							Log.e("","position>>>>>>>>>>>>>>"+position);
							fixItemsAdapter.notifyDataSetChanged();
							//listView.setSelection(position);
							if(position<=8)
							{
								listView.setSelectionFromLeft(position,250);
							}
							else if(position==1000)
							{
								listView.setSelectionFromLeft(8,250);
								
							}
						}
					}
				}

			}

		@Override
		public void onFinish(int responseCode, String result) {
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				//Utils.showDialog(ListYourFixesActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(ListYourFixesActivity.this, "Job Category selection failed", "ERROR");
			}
			
			//Toast.makeText(ListYourFixesActivity.this, "Job Category selection failed", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onFinish(int requestcode, JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}

		}
}
