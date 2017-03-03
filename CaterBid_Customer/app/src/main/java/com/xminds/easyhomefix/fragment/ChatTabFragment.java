package com.xminds.easyhomefix.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.ChatBaseHolder;
import com.xminds.easyhomefix.Holder.ChatList;
import com.xminds.easyhomefix.Holder.Job;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.JobList;
import com.xminds.easyhomefix.Holder.JobNotifications;
import com.xminds.easyhomefix.Holder.Quotes;
import com.xminds.easyhomefix.Managers.ChatManager;
import com.xminds.easyhomefix.Managers.ImageUploadManager;
import com.xminds.easyhomefix.activities.ConfirmOrderActivity;
import com.xminds.easyhomefix.activities.TrackFixFragmentTabhostActivity;
import com.xminds.easyhomefix.adapter.ChatAdapter;
import com.xminds.easyhomefix.adapter.ChatStatusAdapter;
import com.xminds.easyhomefix.adapter.PendingAdapter;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.constants.Constants;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

public class ChatTabFragment extends Fragment {
	
	private View viewroot;
	private ListView pendingListview;
	private List<JobList> pendingList = new ArrayList<JobList>();
	private PendingAdapter pendingAdapter;
	private int requestedCode = 1;
	private LinearLayout emptyPendingLayout;
	private String jobid;
	private String quoteid;
	private ChatCallback chatCallBack;
	private ProgressDialog pDialog;
	private ListView lvChat;
	protected static final int REQUEST_CAMERA = 0;
	protected static final int SELECT_FILE = 1;
	private ImageView image_camera;
	String picturePath = null;
	private TextView sendButton;
	private EditText editMessage;
	private AddChatDetailsCallBack addChatDetailsCallBack;
	private  List<ChatList> chatList;
	ChatStatusAdapter chatStatusAdapter;
	private JobList jobList;
	private Job notificationListObject;
	private String QuoteId;
	private UploadImageCallBack uploadChatImage;
	private String fixerName;
	private String firstname;
	private String lastname;
	private SwipeRefreshLayout swipetorefresh;
	private ProgressDialog progressDialog;
	private JobNotifications jobNotifications;
	private Quotes quotes;
	File image;
	private  String mobileNo ;
	private  String CountryCode ;
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.hasExtra("quoteid")) {

				 //QuoteId = intent.getExtras().getString("quoteid");
				if(QuoteId.equalsIgnoreCase(intent.getExtras().getString("quoteid"))){
					 /*progressDialog = ProgressDialog.show(getActivity(), null,
								"Loading...");
					 progressDialog.setCancelable(true);*/
					ChatManager.getInstance().getAllchats(getActivity(), chatCallBack, QuoteId, requestedCode);
					// TODO Auto-generated method stub
				}
			 }
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		viewroot = inflater.inflate(R.layout.chat_tab_layout, null);
		
		initView();
		initManager();
		getIntentValues();
		if (EasyHomeFixApp.getDetailsTabStatus().equalsIgnoreCase(
				Constants.FROM_NOTIFICATION_CHAT)) {
			//setVisibilityFromNotificationChat();

		}
		
		if(quotes != null){
			if(quotes.getId() != null){
				QuoteId = quotes.getId();
			}
			if(quotes.getFixerId() != null){
				fixerName = quotes.getFixerId().getUserId().getFirstName()+" "+
						quotes.getFixerId().getUserId().getLastName();
			}
		}else if(jobList !=null){
			
			if((jobList.getQuote()!= null)&&(!jobList.getQuote().isEmpty())){
				QuoteId = jobList.getQuote().get(0).getId();
				if(jobList.getQuote().get(0).getFixerId() != null){
					if(jobList.getQuote().get(0).getFixerId().getUserId() != null){
						fixerName = jobList.getQuote().get(0).getFixerId().getUserId().getFirstName()+" "+
						jobList.getQuote().get(0).getFixerId().getUserId().getLastName();
					}
				}
			}
		}
		else if(notificationListObject != null){
			if(notificationListObject.getQuote() != null){
				QuoteId = notificationListObject.getQuote().getId();
				if(notificationListObject.getQuote().getFixerId() != null){
					if(notificationListObject.getQuote().getFixerId().getUserId() != null){
						fixerName = notificationListObject.getQuote().getFixerId().getUserId().getFirstName()+" "+
								notificationListObject.getQuote().getFixerId().getUserId().getLastName();
					}
				}
			}
		}
		/* progressDialog = ProgressDialog.show(getActivity(), null,
					"Loading...");
		
		ChatManager.getInstance().getAllchats(getActivity(), chatCallBack, QuoteId, requestedCode);*/
		// TODO Auto-generated method stub
		Log.e("", "QuoteId!!"+QuoteId);
		//Log.e("", "quoteid id!!"+jobList.getQuote().get(0).getId());
		
		/*editMessage.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if(EditorInfo.IME_ACTION_DONE==actionId || EditorInfo.IME_ACTION_UNSPECIFIED==actionId)
				 {
					 return true;
				 }
				return false;
			}
		});
		
*/
		
		image_camera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openGallery(10);
			}
		});
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//chatText
				String chat = editMessage.getText().toString();
				Log.e("TAG", "INSIDE SEND BUTTON"+chat);
				ChatManager.getInstance().AddChatDetails(getActivity(), addChatDetailsCallBack,chat, QuoteId,0, requestedCode);
				editMessage.setText(null);
				
				
			}
		});
	// SWIPE TO REFRESH
		
		swipetorefresh.setColorScheme(android.R.color.holo_blue_dark,
				android.R.color.holo_blue_light,
				android.R.color.holo_green_light,
				android.R.color.holo_green_dark);
		
		

		swipetorefresh
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

					@Override
					public void onRefresh() {
						// TODO Auto-generated method stub
						swipetorefresh.setRefreshing(true);
						(new Handler()).postDelayed(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								swipetorefresh.setRefreshing(false);

								// pDialog =
								// ProgressDialog.show(EmptyFixQuoteActivity.this,"","Loading..",true);
								Log.e("",
										"swipe to refresh>>>>>>>>>>>>>>>>>>>>>>>");
								ChatManager.getInstance().getAllchats(getActivity(), chatCallBack, QuoteId, requestedCode);
							}
						}, 3000);
					}
				});
		
		
		
		return viewroot;
	}
	
	


	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("nofication_received"));
		/*if(progressDialog != null){
			if(progressDialog.isShowing()){
				 progressDialog = ProgressDialog.show(getActivity(), null,
							"Loading...");
				 progressDialog.setCancelable(true);
			}
		}*/
			ChatManager.getInstance().getAllchats(getActivity(), chatCallBack, QuoteId, requestedCode);
		
	}
	
@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
		
	}





@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	
	Log.e("", "UPLOAD IMAGEssqqqqq"+requestCode+" "+resultCode);
    Log.e("", "on acativity created");
	if (requestCode == 10 && resultCode == Activity.RESULT_OK && null != data) {
		Log.e("", "on acativity created inside");
		Uri selectedImage = data.getData();
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = getActivity().getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		picturePath = cursor.getString(columnIndex);
		cursor.close();

		// String picturePath contains the path of selected Image
		Log.e("", "@@@@@@@@@@@picture path@@@@@@@@@@"+picturePath);


		try {

			//File image=new File(Utils.compressImage(result[i],ConfirmOrderActivity.this));
			 image=new File(Utils.compressImage(picturePath,getActivity()));
			Log.e("", "@@@@@@@@@@@image file path@@@@@@@@@@"+image);
			pDialog = ProgressDialog.show(getActivity(), null,
					"Loading...");
			pDialog.setCancelable(true);
			ImageUploadManager.getInstance().uploadChatImage(getActivity(), "CHAT",QuoteId,
            		image, uploadChatImage, requestedCode);
			
			
			
			

		} catch (Exception e) {

			Log.e("", "Exception occured while decodeFile" + e);
		}

	}
	super.onActivityResult(requestCode, resultCode, data);
}
	
	// uploading an image ......
	public void openGallery(int req_code) {
		Log.e("", "in open gallery");
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, req_code);

	}


	public String getPath(Uri uri, Activity activity) {
		Log.d("", "UPLOAD IMAGEssqqqqq");
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = activity
				.managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	

	private void getIntentValues() {
		// TODO Auto-generated method stub
		/* if (getActivity().getIntent().hasExtra("jobId")) {

		jobid = getActivity().getIntent().getExtras().getString("jobId");
			getActivity().getIntent().removeExtra("jobId");
		
		 }*/
		 
		 if (getActivity().getIntent().hasExtra("quoteid")) {

			 QuoteId = getActivity().getIntent().getExtras().getString("quoteid");
					
		 }
		 if (getActivity().getIntent().hasExtra("ONGOINGOBJECT")) {
			 jobList = (JobList) getActivity().getIntent()
						.getSerializableExtra("ONGOINGOBJECT");
			}
		 if (getActivity().getIntent().hasExtra("job")) {
			 jobList = (JobList) getActivity().getIntent()
						.getSerializableExtra("job");
			}
		 if (getActivity().getIntent().hasExtra("PENDINGOBJECT")) {
			 jobList = (JobList) getActivity().getIntent()
						.getSerializableExtra("PENDINGOBJECT");
			}
		 if (getActivity().getIntent().hasExtra("notification_chat")) {
			 notificationListObject = (Job) getActivity().getIntent()
						.getSerializableExtra("notification_chat");
			}
		 if (getActivity().getIntent().hasExtra("notification_ongoing")) {
			 notificationListObject = (Job) getActivity().getIntent()
						.getSerializableExtra("notification_ongoing");
			}
		 if (getActivity().getIntent().hasExtra("notification_completed")) {
			 notificationListObject = (Job) getActivity().getIntent()
						.getSerializableExtra("notification_completed");
			}
		 if (getActivity().getIntent().hasExtra("From_notification_pending")){
			 notificationListObject = (Job) getActivity().getIntent()
						.getSerializableExtra("From_notification_pending");
		 }
		 
		 if(getActivity().getIntent().hasExtra("From_notification_ongoing")){
			 notificationListObject = (Job) getActivity().getIntent()
						.getSerializableExtra("From_notification_ongoing");
		 }
			 
		 if (getActivity().getIntent().hasExtra("QuoteObject")){
			 quotes = (Quotes) getActivity().getIntent()
						.getSerializableExtra("QuoteObject");
		 }
	}


	public void initManager() {
		// TODO Auto-generated method stub
		chatCallBack=new ChatCallback();
		chatList=new ArrayList<ChatList>();
		addChatDetailsCallBack=new AddChatDetailsCallBack();
		uploadChatImage = new UploadImageCallBack();
		
		//adapter.notifyDataSetChanged();
		
		
	}
	

	public void initView() {
		lvChat = (ListView) viewroot
				.findViewById(R.id.lvChat);
		image_camera=(ImageView)viewroot.findViewById(R.id.image_camera);
		sendButton = (TextView)viewroot.findViewById(R.id.btSend);
		editMessage = (EditText)viewroot.findViewById(R.id.editMessage);
		swipetorefresh=(SwipeRefreshLayout)viewroot.findViewById(R.id.swipetorefresh_chat);
	}
	
	
	public class ChatCallback implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if(progressDialog != null)
				progressDialog.dismiss();
			if (responseCode == requestedCode) {
				ChatBaseHolder chatBaseHolder = (ChatBaseHolder) result;
				if (UtilValidate.isNotNull(chatBaseHolder)) {
					if (chatBaseHolder.isSuccess()) {
						Log.e("", ">>>>>>>>>>"
								+ chatBaseHolder.getData().getChats().size());
					
					/*	Toast.makeText(getActivity(),
								chatBaseHolder.isSuccess() + "@@",
								Toast.LENGTH_LONG).show();*/
						//chatList.clear();
						chatList=chatBaseHolder.getData().getChats();
						Collections.reverse(chatList);
						/*ChatStatusAdapter chatStatusAdapter;
						try {
							chatStatusAdapter = new ChatStatusAdapter(getActivity(),ChatTabFragment.this, chatList, fixerName);
					
						ChatAdapter adapter = new ChatAdapter(
								getActivity(), chatBaseHolder.getData().getChats());
						lvChat.setAdapter(chatStatusAdapter);
						chatStatusAdapter.notifyDataSetChanged();
						} catch (Exception e) {
							// TODO Auto-generated catch block
						}*/
						lvChat.post(new Runnable(){
							  public void run() {
								  lvChat.setSelection(lvChat.getCount() - 1);
							  }});
						Log.d("TAG", "INSIDE SEND BUTTON");
						
						ChatStatusAdapter chatStatusAdapter;
						try {
							chatStatusAdapter = new ChatStatusAdapter(getActivity(),ChatTabFragment.this, chatList, fixerName);
					
						ChatAdapter adapter = new ChatAdapter(
								getActivity(), chatBaseHolder.getData().getChats());
						lvChat.setAdapter(chatStatusAdapter);
						chatStatusAdapter.notifyDataSetChanged();
						} catch (Exception e) {
							// TODO Auto-generated catch block
						}
						
						for(ChatList ChatList : chatList){
							if(!ChatList.getUserId().getId().equalsIgnoreCase(String.valueOf(CurrentlyLoggedUserDAO.getInstance().getUserId()))){
								DetailsTabFragment.userHolder = ChatList.getUserId();
								firstname=ChatList.getUserId().getFirstName();
								lastname=ChatList.getUserId().getLastName();
								fixerName=firstname+" "+lastname;	
								mobileNo = ChatList.getUserId().getMobile();
								CountryCode = ChatList.getUserId().getCountryCode();
								break;
							}
						}
						boolean isQuote=false;
						for(ChatList ChatList : chatList){
							if(ChatList.getChatTypeId() == 3){
								DetailsTabFragment.quoteAmount=ChatList.getChatText();
								isQuote = true;
							}
							if(ChatList.getChatTypeId() == 9){
								String[] separated = ChatList.getChatText().split(",");
								DetailsTabFragment.quoteAmount=separated[1];
								isQuote = true;
							}
						}
						if(!isQuote){
							DetailsTabFragment.quoteAmount = "0";
						}
						if(QuoteId != null){
							DetailsTabFragment.QuoteId = QuoteId;
						}
					}

					else {
						progressDialog.dismiss();

						Toast.makeText(getActivity(),
								chatBaseHolder.isSuccess() + "@@",
								Toast.LENGTH_LONG).show();


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
				Utils.showDialog(getActivity(), "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(getActivity(), ""+result, "ERROR");
			}
			if(progressDialog!=null)
			{
				progressDialog.dismiss();
			}
			
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			if(progressDialog != null)
			progressDialog.dismiss();
			
		}

	}
	
	public class AddChatDetailsCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if (responseCode == requestedCode) {
				ChatBaseHolder chatBaseHolder = (ChatBaseHolder) result;
				if (UtilValidate.isNotNull(chatBaseHolder)) {
					if (chatBaseHolder.isSuccess()) {
						Log.e("", ">>>>>>>>>>"
								+ chatBaseHolder.getData().getJobChat());
					
						/*Toast.makeText(getActivity(),
								chatBaseHolder.isSuccess() + "@@",
								Toast.LENGTH_LONG).show();*/
			
			ChatManager.getInstance().getAllchats(getActivity(), chatCallBack, QuoteId, requestedCode);
			// TODO Auto-generated method stub
			/*if (pDialog != null) {
				pDialog.dismiss();
			}
			if (responseCode == requestedCode) {
				ChatBaseHolder chatBaseHolder = (ChatBaseHolder) result;
				if (UtilValidate.isNotNull(chatBaseHolder)) {
					if (chatBaseHolder.isSuccess()) {
						
						//ChatManager.getInstance().getAllchats(getActivity(), chatCallBack, quoteid, requestedCode);
						
					}else{
						
					}
				}
			}*/
					}
				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
		/*	Toast.makeText(getActivity(),result,
					Toast.LENGTH_LONG).show();*/
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(getActivity(), "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(getActivity(), ""+result, "ERROR");
			}

		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	public class UploadImageCallBack implements AsyncTaskCallBack{

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			/*if (pDialog != null) {
				pDialog.dismiss();
			}*/
		
			if (responseCode == requestedCode) {
				ChatBaseHolder chatBaseHolder = (ChatBaseHolder) result;
				if (UtilValidate.isNotNull(chatBaseHolder)) {
					if (chatBaseHolder.isSuccess()) {
						
						ChatManager.getInstance().getAllchats(getActivity(), chatCallBack, QuoteId, requestedCode);
						
					}
				}
			}
			}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(getActivity(), "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(getActivity(), ""+result, "ERROR");
			}
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			
		}
		
	}

	public void call(int position) {
		// TODO Auto-generated method stub
		if(chatList.get(position).getUserId().getCountryCode() != null && chatList.get(position).getUserId().getMobile() != null){
			
			Intent callIntent = new Intent(Intent.ACTION_CALL);          
	        callIntent.setData(Uri.parse("tel:"+CountryCode + mobileNo));   
	        startActivity(callIntent);
	        
	        try {
				 int b=0;
				 if(TelephonyManager.CALL_STATE_IDLE == b){
			        	
					 apicall();
			        }
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				
			}
	       
		} 
	}
	private void apicall() throws InterruptedException {
		ChatManager.getInstance().AddChatDetails(getActivity(), addChatDetailsCallBack,"hai", QuoteId,8,requestedCode);
		Thread.sleep(3000);
		
	}





	@Override
	public void onStop() {
		if(pDialog != null){
			pDialog.dismiss();
		}
		if(progressDialog != null){
			progressDialog.dismiss();
		}
		super.onStop();
	}
	
	
}
