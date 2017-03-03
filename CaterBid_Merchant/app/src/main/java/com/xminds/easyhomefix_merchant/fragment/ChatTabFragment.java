package com.xminds.easyhomefix_merchant.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.xminds.easyfix_merchant.constants.Constants;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Apiconstants.ApiConstants;
import com.xminds.easyhomefix_merchant.Holder.AvailableJobs;
import com.xminds.easyhomefix_merchant.Holder.ChatBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.ChatList;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.ErrorMessageListHolder;
import com.xminds.easyhomefix_merchant.Holder.Job;
import com.xminds.easyhomefix_merchant.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.JobList;
import com.xminds.easyhomefix_merchant.Holder.JobNotifications;
import com.xminds.easyhomefix_merchant.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix_merchant.Holder.PaymentHolder;
import com.xminds.easyhomefix_merchant.Holder.Quote;
import com.xminds.easyhomefix_merchant.Holder.jobNotifications;
import com.xminds.easyhomefix_merchant.Manager.AvailableManager;
import com.xminds.easyhomefix_merchant.Manager.ChatManager;
import com.xminds.easyhomefix_merchant.Manager.ImageUploadManager;
import com.xminds.easyhomefix_merchant.Manager.QuoteManager;
import com.xminds.easyhomefix_merchant.adapter.ChatStatusAdapter;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;
import com.xminds.easyhomefix_merchant.fragment.AvailableTabFragment.QuoteCallBack;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;
import com.xminds.easyhomefix_merchant.utils.Utils;
import com.xminds.easyhomefix_merchant.webservice.AsyncTaskCallBack;


public class ChatTabFragment extends Fragment {
	
	private View viewroot;
	private ListView pendingListview;
	private List<JobList> pendingList = new ArrayList<JobList>();
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
	private JobNotifications notificationListObject;
	private String QuoteId;
	private UploadImageCallBack uploadChatImage;
	private AvailableJobs availableJobs;
	//private jobNotifications  notificationobject;
	private  Quote quoteObject;
	private Quote QuoteObject;
	private QuoteCallBack quotecallback;
	private int requestcode = 0;
	private Job  notificationobject;
	private String FixerFirstName;
	private String FixerLastName;
	private String FixerName;
	private SwipeRefreshLayout swipeRefreshLayout;
	private PaymentHolder paymentObject;
	public static String quoteId;
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.hasExtra("quoteid")) {

				 //QuoteId = intent.getExtras().getString("quoteid");
				if(QuoteId.equalsIgnoreCase(intent.getExtras().getString("quoteid"))){
					/* pDialog = ProgressDialog.show(getActivity(), null,
								"Loading...");
					 pDialog.setCancelable(true);*/
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
		if(quoteObject != null){
			QuoteId = quoteObject.getId();
			Log.e("", "Quote id2:"+QuoteId);
			FixerFirstName = quoteObject.getFixerId().getUserId().getFirstName().toString();
			FixerLastName = quoteObject.getFixerId().getUserId().getLastName().toString();
			FixerName = FixerFirstName+" "+FixerLastName;
		}else if(availableJobs !=null){
			//Log.e("", "job id!!"+availableJobs.getQuote().getQuoteAmount());
			if(availableJobs.getQuote()!=null){
				
			QuoteId = availableJobs.getQuote().getId();
			Log.e("", "Quote id1:"+QuoteId);
			
			FixerFirstName = availableJobs.getQuote().getFixerId().getUserId().getFirstName().toString();
			FixerLastName = availableJobs.getQuote().getFixerId().getUserId().getLastName().toString();
			
			FixerName = FixerFirstName+" "+FixerLastName;
			
			}
		}else if(notificationobject != null){
			if(notificationobject.getQuote()!=null){
			QuoteId = notificationobject.getQuote().getId();
			Log.e("", "INSIDE NOTIFICATION"+QuoteId+"|"+notificationobject.getId());
			FixerFirstName = notificationobject.getQuote().getFixerId().getUserId().getFirstName().toString();
			FixerLastName = notificationobject.getQuote().getFixerId().getUserId().getLastName().toString();
			FixerName = FixerFirstName+" "+FixerLastName;
			}/*else{
				
				QuoteManager.getInstance().AddQuote(getActivity(),
						"0", notificationobject.getId(), quotecallback, requestcode);
				if(QuoteObject != null){
					
				QuoteId = QuoteObject.getId();
				Log.d("", "INSIDE QUOTE"+QuoteId);
				}
			}*/
		} else if(paymentObject != null){
			if(paymentObject.getQuoteId() != null){
				QuoteId = paymentObject.getQuoteId().getId();
				if(paymentObject.getQuoteId().getFixerId() != null){
					FixerName = paymentObject.getQuoteId().getFixerId().getUserId().getFirstName()+" "+
							paymentObject.getQuoteId().getFixerId().getUserId().getLastName();
				}
			}
		}
		if(QuoteId == null){
			if(ChatTabFragment.quoteId!=null){
				QuoteId = quoteId;
			}
		}
	
		
		
		//ChatManager.getInstance().getAllchats(getActivity(), chatCallBack, QuoteId, requestedCode);
		// TODO Auto-generated method stub
		Log.e("", "Quote id3:"+QuoteId);
		//Log.e("", "quoteid id!!"+jobList.getQuote().get(0).getId());
		

		
		image_camera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openGallery(1);
			}
		});
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//chatText
				String chat = editMessage.getText().toString();
				Log.d("TAG", "INSIDE SEND BUTTON"+chat);
				ChatManager.getInstance().AddChatDetails(getActivity(), addChatDetailsCallBack,chat,0, QuoteId, requestedCode);
				editMessage.setText(null);
				
				
			}
		});
		
	/*	pendingListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent pendingFixIntent = new Intent(getActivity(),EmptyFixQuoteActivity.class);
				pendingFixIntent.putExtra("jobId",jobid);
				Log.e("", "job id in adapter"+jobid);
				startActivity(pendingFixIntent);	
			}
			
		});*/
		
		

		// SWIPE TO REFRESH
		
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_dark);

		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
	             
	            @Override
	            public void onRefresh() {
	                // TODO Auto-generated method stub
	            	swipeRefreshLayout.setRefreshing(true);
	                ( new Handler()).postDelayed(new Runnable() {
	                     
	                    @Override
	                    public void run() {
	                        // TODO Auto-generated method stub
	                    	swipeRefreshLayout.setRefreshing(false);
	                    	
	                    	// pDialog = ProgressDialog.show(EmptyFixQuoteActivity.this,"","Loading..",true);	
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
		 pDialog = ProgressDialog.show(getActivity(), null,
					"Loading...");
		 pDialog.setCancelable(true);
		ChatManager.getInstance().getAllchats(getActivity(), chatCallBack, QuoteId, requestedCode);
	}
	
	
@Override
	public void onDestroy() {
	getActivity().unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	
	Log.d("", "UPLOAD IMAGEssqqqqq"+requestCode+" "+resultCode);
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

			File image=new File(picturePath);
			
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
				getActivity().getIntent().removeExtra("quoteid");
		
		 }
		
		if(getActivity().getIntent().hasExtra("AVAILABLE_OBJECT")){
			
			availableJobs = (AvailableJobs)getActivity().getIntent().getSerializableExtra("AVAILABLE_OBJECT");
			//getActivity().getIntent().removeExtra("AVAILABLE_OBJECT");
		}
		
		if(getActivity().getIntent().hasExtra("notification")){
			
			notificationobject = (Job)getActivity().getIntent().getSerializableExtra("notification");
			//getActivity().getIntent().removeExtra("notification");
		}
		if(getActivity().getIntent().hasExtra("QUOTE_OBJECT")){
			
			quoteObject=(Quote)getActivity().getIntent()
					.getSerializableExtra("QUOTE_OBJECT");
			//getActivity().getIntent().removeExtra("QUOTE_OBJECT");
		}
		if(getActivity().getIntent().hasExtra("QUOTED_OBJECT_FROM_QUOTE")){
			
			availableJobs = (AvailableJobs)getActivity().getIntent().getSerializableExtra("QUOTED_OBJECT_FROM_QUOTE");
			//getActivity().getIntent().removeExtra("QUOTED_OBJECT_FROM_QUOTE");
		}
		if(getActivity().getIntent().hasExtra("QUOTED_OBJECT_FROM_ONGOING")){
			
			availableJobs = (AvailableJobs)getActivity().getIntent().getSerializableExtra("QUOTED_OBJECT_FROM_ONGOING");
			//getActivity().getIntent().removeExtra("QUOTED_OBJECT_FROM_ONGOING");
		}
		if(getActivity().getIntent().hasExtra("QUOTED_OBJECT_FROM_COMPLETE")){
			
			availableJobs = (AvailableJobs)getActivity().getIntent().getSerializableExtra("QUOTED_OBJECT_FROM_COMPLETE");
			//getActivity().getIntent().removeExtra("QUOTED_OBJECT_FROM_COMPLETE");
		}
		//NOTIFICATION OBJECT FROM CHAT
		 
		 if (getActivity().getIntent().hasExtra("notification_chat")) {
			 notificationobject = (Job) getActivity().getIntent()
						.getSerializableExtra("notification_chat");
			 //getActivity().getIntent().removeExtra("notification_chat");
			}
		
		//NOTIFICATION OBJECT FROM  ONGOING
		 
		 if (getActivity().getIntent().hasExtra("notification_ongoing")) {
			 notificationobject = (Job) getActivity().getIntent()
						.getSerializableExtra("notification_ongoing");
			 //getActivity().getIntent().removeExtra("notification_ongoing");
			}
		 
		 //NOTIFICATION OBJECT FROM COMPLETE
		 
		 
		 if (getActivity().getIntent().hasExtra("notification_completed")) {
			 notificationobject = (Job) getActivity().getIntent()
						.getSerializableExtra("notification_completed");
			// getActivity().getIntent().removeExtra("notification_completed");
			}
		 
		 //NOTIFICATION OBJECT FROM PENDING
		 
		 if (getActivity().getIntent().hasExtra("From_notification_pending")) {
			 notificationobject = (Job) getActivity().getIntent()
						.getSerializableExtra("From_notification_pending");
			 //getActivity().getIntent().removeExtra("From_notification_pending");
			}
		 
		 if (getActivity().getIntent().hasExtra("notification_cancelled")) {
			 notificationobject = (Job) getActivity().getIntent()
						.getSerializableExtra("notification_cancelled");
			 //getActivity().getIntent().removeExtra("notification_cancelled");
			}
		 if(getActivity().getIntent().hasExtra("payment")){
				
				paymentObject = (PaymentHolder)getActivity().getIntent().getSerializableExtra("payment");
			}
	}


	public void initManager() {
		// TODO Auto-generated method stub
		chatCallBack=new ChatCallback();
		chatList=new ArrayList<ChatList>();
		addChatDetailsCallBack=new AddChatDetailsCallBack();
		uploadChatImage = new UploadImageCallBack();
		quotecallback = new QuoteCallBack();
		
		//adapter.notifyDataSetChanged();
		
		
	}
	

	public void initView() {
		lvChat = (ListView) viewroot
				.findViewById(R.id.lvChat);
		image_camera=(ImageView)viewroot.findViewById(R.id.image_camera);
		sendButton = (TextView)viewroot.findViewById(R.id.btSend);
		editMessage = (EditText)viewroot.findViewById(R.id.editMessage);
		swipeRefreshLayout=(SwipeRefreshLayout)viewroot.findViewById(R.id.swipetorefresh_chat);
	}
	
	
	public class ChatCallback implements AsyncTaskCallBack {

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
								+ chatBaseHolder.getData().getChats().size());
					
						//chatList.clear();
						chatList=chatBaseHolder.getData().getChats();
						Collections.reverse(chatList);
						ChatStatusAdapter chatStatusAdapter;
						try {
							chatStatusAdapter = new ChatStatusAdapter(getActivity(),ChatTabFragment.this, chatList,FixerName);
							lvChat.setAdapter(chatStatusAdapter);
							chatStatusAdapter.notifyDataSetChanged();
						} catch (Exception e) {
							// TODO Auto-generated catch block
						}
						/*ChatAdapter adapter = new ChatAdapter(
								getActivity(), chatBaseHolder.getData().getChats());*/
					
						lvChat.post(new Runnable(){
							  public void run() {
								  lvChat.setSelection(lvChat.getCount() - 1);
							  }});
						Log.d("TAG", "INSIDE SEND BUTTON");
					}

					else {


						Toast.makeText(getActivity(),
								 "Please try agian later",
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
			/*if (result.equalsIgnoreCase(ApiConstants.NO_INTERNET)) {
				Toast.makeText(getActivity(),
						"Please check your internet connection",
						Toast.LENGTH_SHORT).show();
			}*/
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(getActivity(), "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(getActivity(), result, "ERROR");
			}

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
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(getActivity(), "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(getActivity(), result, "ERROR");
			}

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
			if (pDialog != null) {
				pDialog.dismiss();
			}
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(getActivity(), "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(getActivity(), result, "ERROR");
			}

		}

		
		
	}

	public void call(int position) {
		// TODO Auto-generated method stub
		
		
		if(chatList.get(position).getUserId().getCountryCode() != null && chatList.get(position).getUserId().getMobile() != null){
			
			Intent callIntent = new Intent(Intent.ACTION_CALL);  
	        callIntent.setData(Uri.parse("tel:"+chatList.get(position).getUserId().getCountryCode()+chatList.get(position).getUserId().getMobile()));
			

	        startActivity(callIntent);
	        
	        try {
				 int b=0;
				 if(TelephonyManager.CALL_STATE_OFFHOOK == b){
			        	
					 apicall();
			        }
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				
			}
	       
		}
	}
        

	private void apicall()throws InterruptedException {
		// TODO Auto-generated method stub
		ChatManager.getInstance().AddChatDetails(getActivity(), addChatDetailsCallBack,"hai",8, QuoteId, requestedCode);
		Thread.sleep(3000);
	}


	public class QuoteCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub
			if (responseCode == requestcode) {
				EmailSignupBaseHolder quotedataHolder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(quotedataHolder)) {
					if (quotedataHolder.isSuccess()) {

						/*Toast.makeText(getActivity(),
								quotedataHolder.getData().getMessage(),
								Toast.LENGTH_LONG).show();*/
						QuoteObject = quotedataHolder.getData().getQuote();
						Log.d("", "QUOTE ID"+QuoteObject.getId());
						
						//Utils.showDialog(getActivity(), 	quotedataHolder.getData().getMessage(),"SUCCESS");

					} else {

					/*	Toast.makeText(getActivity(),
								quotedataHolder.isSuccess() + "",
								Toast.LENGTH_LONG).show();*/
						//Utils.showDialog(getActivity(), 	"Something went wrong,please try again","SUCCESS");
					}

				}
			} else {
				OnFailureErrorHolder onFailureErrorHolder = (OnFailureErrorHolder) result;
				if (UtilValidate.isNotNull(onFailureErrorHolder)) {
					for (ErrorMessageListHolder error : onFailureErrorHolder
							.getData()) {
						Utils.showDialog(getActivity(), 	error.getMessage(),"ERROR");
					/*	Toast.makeText(getActivity(), "?" + error.getMessage(),
								Toast.LENGTH_LONG).show();*/

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
				Utils.showDialog(getActivity(), result, "ERROR");
			}

		}

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(pDialog != null){
			pDialog.dismiss();
		}
	}
	
	
}
