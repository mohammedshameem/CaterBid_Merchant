package com.xminds.easyhomefix_merchant.activities;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xminds.easyfix_merchant.actionbar.BaseActivity;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;

public class ConfirmOrderActivity extends BaseActivity {
	private ImageView image_back;
	private TextView textview_trackyourfix;
	private LinearLayout linear_insert;
	private LinearLayout linear_findfixers;
	private static final int SELECT_PHOTO = 100;
	private EditText edittext_datepicker;
	private EditText edittext_postalcode;
	private int mValue=0;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_order);
		initViews();
		initManagers();
		setVisibilities();
		setTexts();
		
		

		//logout broadcast receiver
		  logoutReceiver = new LogoutReceiver();
		  
	        // Register the logout receiver
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("com.package.ACTION_LOGOUT");
	        registerReceiver(logoutReceiver, intentFilter);
			
		
		
		Intent listyourfix_intent = getIntent();
		String listfix_date = listyourfix_intent.getStringExtra("DateandTime");
		edittext_datepicker.setText(listfix_date);
		
		
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
				Intent i=new Intent(ConfirmOrderActivity.this,TrackYourFixFragmentTabHostActivity.class);
				startActivity(i);
			}
		});

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
	private void setTexts() {
		// TODO Auto-generated method stub
		textview_trackyourfix.setText("Confirm Order");
	}

	private void initManagers() {
		// TODO Auto-generated method stub

	}

	private void setVisibilities() {
		image_back.setVisibility(View.VISIBLE);
		textview_trackyourfix.setVisibility(View.VISIBLE);

	}

	private void initViews() {

		image_back = (ImageView) findViewById(R.id.image_back);
		textview_trackyourfix = (TextView) findViewById(R.id.textview_trackyourfix);
		linear_insert=(LinearLayout)findViewById(R.id.linear_insert);
		edittext_datepicker=(EditText) findViewById(R.id.datepicker);
		edittext_postalcode=(EditText)findViewById(R.id.postalcode);
		linear_findfixers=(LinearLayout)findViewById(R.id.linear_findfixers);
	}
	
	private void showPopup(){
		LayoutInflater layoutInflater = (LayoutInflater) ConfirmOrderActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.insert_popup, null);
		final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,true);
		popupWindow.update();
		popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
		final LinearLayout linear_photo_lib=(LinearLayout)popupView.findViewById(R.id.linear_photo_lib);
		final LinearLayout linear_cancel=(LinearLayout)popupView.findViewById(R.id.linear_cancel);
		linear_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();				
			}
		});
		linear_photo_lib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			     Intent intent = new Intent(ConfirmOrderActivity.this,MutipleImagePickMainActivity.class);
			     startActivity(intent);
				/*intent.setType("image/*");*/
				/*intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent,"Select Picture"),SELECT_PHOTO);*/
				
			}
		});
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

        switch(requestCode) { 
        case SELECT_PHOTO:
            if(resultCode == RESULT_OK){
				try {
					final Uri imageUri = imageReturnedIntent.getData();
					final InputStream imageStream = getContentResolver().openInputStream(imageUri);
					final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
					Log.e("", "<<<<<<imageStream>>>>"+imageStream);
					Log.e("", "<<<<<<imageUri>>>>"+imageUri);
					//imageView.setImageBitmap(selectedImage);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

            }
        }
    }

}
