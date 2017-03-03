package com.xminds.easyhomefix.actionbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.readystatesoftware.viewbadger.BadgeView;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.activities.TrackFixFragmentTabhostActivity;
import com.xminds.easyhomefix.activities.TrackYourFixFragmentTabHostActivity;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.utils.UtilValidate;

public class BaseActivity extends SlidingFragmentActivity {

	private int mTitleRes;
	protected Fragment mFrag;
	private ImageView menuSelectionIcon;
	private ActionBar actionBar;
	private TextView menu_heading;
	private static final String TAG = BaseActivity.class.getName();
	private static BaseActivity ins;
	private LinearLayout header_layout;
	private View linear_righticons;
	String picturePath = null;
	private  BadgeView badge;
	private View image_badge;
	/**
	 * defaultsssss
	 */
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		
		if(EasyHomeFixApp.getRejectquoteStatus().equalsIgnoreCase("false"))
		{
			EasyHomeFixApp.setRejectquoteStatus("true");
			Intent i=new Intent(BaseActivity.this,TrackYourFixFragmentTabHostActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
			finish();
		}else
		{
			finish();
		}
		
		if(EasyHomeFixApp.getAcceptquoteStatus().equalsIgnoreCase("accepted"))
		{
			EasyHomeFixApp.setAcceptquoteStatus("rejected");
		Intent i=new Intent(BaseActivity.this,TrackYourFixFragmentTabHostActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		finish();
		
		}
		else
		{
			finish();	
		}
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
Log.e(TAG, "in oncreateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
	//	setTitle(mTitleRes);

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager()
					.beginTransaction();
			mFrag = new ActionBarNotificationListFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (ListFragment) this.getSupportFragmentManager()
					.findFragmentById(R.id.menu_frame);

		}

		/*
		 * customize the SlidingMenu
		 */

		SlidingMenu sm = getSlidingMenu();
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		//sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setMode(SlidingMenu.RIGHT);
		getSupportActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
		getSupportActionBar().setCustomView(R.layout.custom_action_bar);
		menuSelectionIcon = (ImageView) findViewById(R.id.image_avatar);
		linear_righticons = findViewById(R.id.linear_righticons);
		image_badge = findViewById(R.id.image_badge);
		//menu_heading = (TextView) findViewById(R.id.menu_heading);
		//menu_heading.setText(EasyHomeFixApp.getCategoryTitle());
		badge = new BadgeView(this, image_badge);
		getSupportActionBar().setHomeButtonEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		//sm.setSlidingEnabled(false);
		// setting the current action bar for further use...
		this.setMyActionBar(getSupportActionBar());

		menuSelectionIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(getCurrentFocus()!=null) {
					  InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
					}
				toggle();

			}
		});
	/*	if(EasyHomeFixApp.getCount()!=null){
		badge.setText(Integer.parseInt(EasyHomeFixApp.getCount()));
		badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		badge.setTextSize(12);
		badge.show();}*/

	}
	
	public  void getnotificationSize(int count)
	{
		
		if(UtilValidate.isNotNull(count)){
			if(count>0){
				badge.setText(0+"");
				badge.setText(count+"");
				badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
				badge.setTextSize(12);
				badge.show();
				
			
			}else{
				badge.hide();
			}
		}
	}
	
	
	
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode,resultCode,data);
	
}
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {

		Log.e(TAG,
				"################# BaseActivity onResume() ##################");
		
		super.onResume();
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	/**
	 * @return
	 */
	public ActionBar getMyActionBar() {
		return actionBar;
	}

	/**
	 * @param actionBar
	 */
	public void setMyActionBar(ActionBar actionBar) {
		this.actionBar = actionBar;
	}

	/**
	 * @param titleFromFragment
	 */
	public void setHeadingText(int titleFromFragment) {
		this.mTitleRes = titleFromFragment;
		menu_heading.setText(mTitleRes);
	}

	/**
	 * 
	 * @return ins
	 */
	public static BaseActivity getInstace() {
		return ins;
	}

	

}
