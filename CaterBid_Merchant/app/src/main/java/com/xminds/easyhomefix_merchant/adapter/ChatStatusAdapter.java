package com.xminds.easyhomefix_merchant.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pkmmte.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.xminds.easyfix_merchant.Imagezooming.TouchImageView;
import com.xminds.easyfix_merchant.Imagezooming.TouchImageView.OnTouchImageViewListener;
import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.Holder.ChatList;
import com.xminds.easyhomefix_merchant.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix_merchant.dao.UserDAO;
import com.xminds.easyhomefix_merchant.fragment.ChatTabFragment;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;


public class ChatStatusAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;

	private List<ChatList> chatList = new ArrayList<ChatList>();
	private static final String TAG = ChatStatusAdapter.class.getName();
	private final int VIEW_FIXER = 0;
	private final int VIEW_CUSTOMER = 1;
	private final int VIEW_COMMENT = 2;
	private final int CHAT = 1;
	private final int CHAT_IMAGE = 2;
	private final int CHAT_QUOTE_SUBMITTED = 3;
	private final int CHAT_QUOTE_ACCEPTED = 4;
	private final int CHAT_FIX_EDITED = 5;
	private final int CHAT_PAYMENT_REQUESTED = 6;
	private final int CHAT_PAYMENT_MADE = 7;
	private final int CHAT_CALL = 8;
	private final int CHAT_QUOTE_EDITED = 9;
	private final int  CHAT_QUOTE_CREATED = 10;
	private final int CHAT_TYPE_REVIEW_REQUESTED = 11;
	private final int CHAT_TYPE_REVIEW_ADDED = 12;
	private final int CHAT_TYPE_JOB_COMPLETED = 13;
	private final int CHAT_TYPE_CASH_ACCEPTED = 14;
	private final int CHAT_TYPE_JOB_CANCELLED = 15;

	protected static final int REQUEST_CAMERA = 0;
	protected static final int SELECT_FILE = 1;
	private ChatTabFragment chatTabFragment;
	private String FirstName;
	private String LastName;
	private String FixerName;
	/*ViewHolder viewholder;
	ChatHolder chatHolder;
	FixerHolder fixerHolder;*/
	int type;
	//View view;

	public ChatStatusAdapter(Activity activity, ChatTabFragment chatTabFragment, List<ChatList> chatList, String fixerName) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.chatList = chatList;
		this.chatTabFragment = chatTabFragment;
		this.FixerName = fixerName;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d(TAG, "ChatStatusAdapter");
	}

	@Override
	public int getCount() {
		return chatList.size();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 16;
	}

	@Override
	public int getItemViewType(int position) {
		Log.d(TAG, "getItemViewType");

		if (chatList.get(position).getChatTypeId() == 1) {
			type = CHAT;
			return type;

		} if (chatList.get(position).getChatTypeId() == 2) {
			type = CHAT_IMAGE; 
			
			return type;

		}if (chatList.get(position).getChatTypeId() == 3) {
			type = CHAT_QUOTE_SUBMITTED;
			return type;
		}if (chatList.get(position).getChatTypeId() == 4) {
			type = CHAT_QUOTE_ACCEPTED;
			return type;
		}if (chatList.get(position).getChatTypeId() == 5) {
			type = CHAT_FIX_EDITED;
			return type;
		}if (chatList.get(position).getChatTypeId() == 6) {
			type = CHAT_PAYMENT_REQUESTED;
			return type;
		}if (chatList.get(position).getChatTypeId() == 7) {
			type = CHAT_PAYMENT_MADE;
			return type;
		}
		if (chatList.get(position).getChatTypeId() == 8) {
			type = CHAT_CALL;
			return type;
		} 
		
		if (chatList.get(position).getChatTypeId() == 9) {
			type = CHAT_QUOTE_EDITED;
			return type;
		}if (chatList.get(position).getChatTypeId() == 10) {
			type = CHAT_QUOTE_CREATED;
			return type; 
		}if (chatList.get(position).getChatTypeId() == 11) {
			type = CHAT_TYPE_REVIEW_REQUESTED;
			return type; 
		}if (chatList.get(position).getChatTypeId() == 12) {
			type = CHAT_TYPE_REVIEW_ADDED;
			return type;
		}if (chatList.get(position).getChatTypeId() == 13) {
			type = CHAT_TYPE_JOB_COMPLETED;
			return type;
		}if (chatList.get(position).getChatTypeId() == 14) {
			type = CHAT_TYPE_CASH_ACCEPTED;
			return type;
		}
			else {

			type = CHAT_TYPE_JOB_CANCELLED;
			return type;
		}
		
	}

	@SuppressWarnings("unused")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		//View view = convertView;
		ViewHolder viewholder = new ViewHolder();
		ChatHolder chatHolder = new ChatHolder();;
		FixerHolder fixerHolder = new FixerHolder();
		TapHolder tapHolder = new TapHolder();
		int type = getItemViewType(position);
		
			Log.e(TAG, "@@@@@@@@@@@type"+type);
		if(type==1){
			if(UtilValidate.isNotNull(chatList.get(position).getUserId().getUserType())){
			if(chatList.get(position).getUserId().getUserType().equalsIgnoreCase("Customer")){
				
				convertView = inflater.inflate(R.layout.chat_list_item_customer,null);
				viewholder.chat_name = (TextView)convertView.findViewById(R.id.chat_name);
				viewholder.chat_date = (TextView)convertView.findViewById(R.id.chat_date);
				viewholder.chat_content = (TextView)convertView.findViewById(R.id.chat_content);
				viewholder.chat_user_image = (CircularImageView)convertView.findViewById(R.id.chat_user_image);
				viewholder.chat_image = (ImageView) convertView
						.findViewById(R.id.chat_pic);
				
				convertView.setTag(viewholder);
			
/*
			if(fixerHolder.fixer_chat_name == null){}else {
				fixerHolder = (FixerHolder)fixerHolder.fixer_chat_name.getTag();
	        }*/
			if(UtilValidate.isNotNull(chatList.get(position))){
		if ((UtilValidate.isNotNull(chatList.get(position).getUserId()
				.getFirstName()))
				|| (UtilValidate.isNotNull(chatList.get(position)
						.getUserId().getLastName()))) {

			viewholder.chat_name.setText(chatList.get(position)
					.getUserId().getFirstName()
					+ " "
					+ chatList.get(position).getUserId().getLastName());
		}

		if (UtilValidate.isNotNull(chatList.get(position)
				.getChatDateTime())) {

			viewholder.chat_date.setText(chatList.get(position)
					.getChatDateTime());
		}

		if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

			viewholder.chat_content.setVisibility(View.VISIBLE);
			viewholder.chat_image.setVisibility(View.GONE);
			viewholder.chat_content.setText(chatList.get(position)
					.getChatText());
		}
		if (UtilValidate.isNotNull(chatList.get(position).getUserId().getProfileImage())
				&& (UtilValidate.isNotEmpty(chatList.get(position).getUserId().getProfileImage()))) {

			Picasso.with(activity)
					.load(chatList.get(position).getUserId().getProfileImage())
					.into(viewholder.chat_user_image);
		}
		if (UtilValidate.isNotNull(chatList.get(position).getChatImage())
				&& (UtilValidate.isNotEmpty(chatList.get(position).getChatImage()))) {
			
			viewholder.chat_content.setVisibility(View.GONE);
			viewholder.chat_image.setVisibility(View.VISIBLE);

			Picasso.with(activity)
					.load(chatList.get(position).getChatImage())
					.into(viewholder.chat_image);
		}
		}
		}
		
			
			
			else if(chatList.get(position).getUserId().getUserType().equalsIgnoreCase("Fixer")){
				

				/*	
					if(viewholder.chat_name == null){}else {
					viewholder = (ViewHolder)viewholder.chat_name.getTag();
		        }*/
						

						convertView = inflater.inflate(R.layout.chat_list_item_fixer,  null);
						fixerHolder.fixer_chat_name = (TextView)convertView.findViewById(R.id.fix_chat_name);
						fixerHolder.fixer_chat_date = (TextView)convertView.findViewById(R.id.fix_chat_date);
						fixerHolder.fixer_chat_content = (TextView)convertView.findViewById(R.id.fix_chat_content);
						fixerHolder.fixer_chat_user_image = (CircularImageView)convertView.findViewById(R.id.fix_chat_user_image);
						fixerHolder.fixer_chat_image = (ImageView) convertView
								.findViewById(R.id.fix_chat_pic);
						convertView.setTag(fixerHolder);
				
					
				if(UtilValidate.isNotNull(chatList.get(position))){
				if ((UtilValidate.isNotNull(chatList.get(position).getUserId()
						.getFirstName()))
						|| (UtilValidate.isNotNull(chatList.get(position)
								.getUserId().getLastName()))) {

					fixerHolder.fixer_chat_name.setText(chatList.get(position)
							.getUserId().getFirstName()
							+ " "
							+ chatList.get(position).getUserId().getLastName());
				}

				if (UtilValidate.isNotNull(chatList.get(position)
						.getChatDateTime())) {

					fixerHolder.fixer_chat_date.setText(chatList.get(position)
							.getChatDateTime());
				}

				if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

					fixerHolder.fixer_chat_content.setVisibility(View.VISIBLE);
					fixerHolder.fixer_chat_image.setVisibility(View.GONE);
					fixerHolder.fixer_chat_content.setText(chatList.get(position)
							.getChatText());
				}
				if (UtilValidate.isNotNull(chatList.get(position).getUserId().getProfileImage())
						&& (UtilValidate.isNotEmpty(chatList.get(position).getUserId().getProfileImage()))) {

					Picasso.with(activity)
							.load(chatList.get(position).getUserId().getProfileImage())
							.into(fixerHolder.fixer_chat_user_image);
				}
				if (UtilValidate.isNotNull(chatList.get(position).getChatImage())
						&& (UtilValidate.isNotEmpty(chatList.get(position).getChatImage()))) {
					
					fixerHolder.fixer_chat_content.setVisibility(View.GONE);
					fixerHolder.fixer_chat_image.setVisibility(View.VISIBLE);

					Picasso.with(activity)
							.load(chatList.get(position).getChatImage())
							.into(fixerHolder.fixer_chat_image);
				}
				
					}
					
			}
			}	
				
		}
		
		else if(type==2){
			
			
				if(UtilValidate.isNotNull(chatList.get(position).getUserId().getUserType())){
			if(chatList.get(position).getUserId().getUserType().equalsIgnoreCase("Customer")){
				
				convertView = inflater.inflate(R.layout.chat_list_item_customer,null);
				viewholder.chat_name = (TextView)convertView.findViewById(R.id.chat_name);
				viewholder.chat_date = (TextView)convertView.findViewById(R.id.chat_date);
				viewholder.chat_content = (TextView)convertView.findViewById(R.id.chat_content);
				viewholder.chat_user_image = (CircularImageView)convertView.findViewById(R.id.chat_user_image);
				viewholder.chat_image = (ImageView) convertView
							.findViewById(R.id.chat_pic);
					
					convertView.setTag(fixerHolder);
				
			
				if(UtilValidate.isNotNull(chatList.get(position))){
			if ((UtilValidate.isNotNull(chatList.get(position).getUserId()
					.getFirstName()))
					|| (UtilValidate.isNotNull(chatList.get(position)
							.getUserId().getLastName()))) {

				viewholder.chat_name.setText(chatList.get(position)
						.getUserId().getFirstName()
						+ " "
						+ chatList.get(position).getUserId().getLastName());
			}

			if (UtilValidate.isNotNull(chatList.get(position)
					.getChatDateTime())) {

				viewholder.chat_date.setText(chatList.get(position)
						.getChatDateTime());
			}

			if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

				viewholder.chat_content.setVisibility(View.VISIBLE);
				viewholder.chat_image.setVisibility(View.GONE);
				viewholder.chat_content.setText(chatList.get(position)
						.getChatText());
			}
			if (UtilValidate.isNotNull(chatList.get(position).getUserId().getProfileImage())
					&& (UtilValidate.isNotEmpty(chatList.get(position).getUserId().getProfileImage()))) {

				Picasso.with(activity)
						.load(chatList.get(position).getUserId().getProfileImage())
						.into(viewholder.chat_user_image);
			}
			if (UtilValidate.isNotNull(chatList.get(position).getChatImage())
					&& (UtilValidate.isNotEmpty(chatList.get(position).getChatImage()))) {
				
				viewholder.chat_content.setVisibility(View.GONE);
				viewholder.chat_image.setVisibility(View.VISIBLE);

				Picasso.with(activity)
						.load(chatList.get(position).getChatImage())
						.into(viewholder.chat_image);
			}
			
			}
				
				
				viewholder.chat_image.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
					
						
						


				    	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				    
				    	View popupView = inflater.inflate(R.layout.imagepopup, null);
						final PopupWindow popupWindow = new PopupWindow(popupView,android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT,true);
						popupWindow.update();
						
						popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
						final ImageView okbutton=(ImageView)popupView.findViewById(R.id.okbutton);
						final TouchImageView image=(TouchImageView)popupView.findViewById(R.id.image);
						Picasso.with(activity)
						.load(chatList.get(position).getChatImage())
						.into(image);
						
						image.setOnTouchImageViewListener(new OnTouchImageViewListener() {
							
							@Override
							public void onMove() {
								// TODO Auto-generated method stub

								PointF point = image.getScrollPosition();
								RectF rect = image.getZoomedRect();
								float currentZoom = image.getCurrentZoom();
								boolean isZoomed = image.isZoomed();
							
							}
						});
						okbutton.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								popupWindow.dismiss();	
								
							}
						});
						
						
					
					}
				});	
				
				
			}
			
			else if(chatList.get(position).getUserId().getUserType().equalsIgnoreCase("Fixer")){
				
					convertView = inflater.inflate(R.layout.chat_list_item_fixer,  null);
					fixerHolder.fixer_chat_name = (TextView)convertView.findViewById(R.id.fix_chat_name);
					fixerHolder.fixer_chat_date = (TextView)convertView.findViewById(R.id.fix_chat_date);
					fixerHolder.fixer_chat_content = (TextView)convertView.findViewById(R.id.fix_chat_content);
					fixerHolder.fixer_chat_user_image = (CircularImageView)convertView.findViewById(R.id.fix_chat_user_image);
					fixerHolder.fixer_chat_image = (ImageView) convertView
							.findViewById(R.id.fix_chat_pic);
					convertView.setTag(fixerHolder);
			
				if(UtilValidate.isNotNull(chatList.get(position))){
			
			if ((UtilValidate.isNotNull(chatList.get(position).getUserId()
					.getFirstName()))
					|| (UtilValidate.isNotNull(chatList.get(position)
							.getUserId().getLastName()))) {

				fixerHolder.fixer_chat_name.setText(chatList.get(position)
						.getUserId().getFirstName()
						+ " "
						+ chatList.get(position).getUserId().getLastName());
			}

			if (UtilValidate.isNotNull(chatList.get(position)
					.getChatDateTime())) {

				fixerHolder.fixer_chat_date.setText(chatList.get(position)
						.getChatDateTime());
			}

			if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

				fixerHolder.fixer_chat_content.setVisibility(View.VISIBLE);
				fixerHolder.fixer_chat_image.setVisibility(View.GONE);
				fixerHolder.fixer_chat_content.setText(chatList.get(position)
						.getChatText());
			}
			if (UtilValidate.isNotNull(chatList.get(position).getUserId().getProfileImage())
					&& (UtilValidate.isNotEmpty(chatList.get(position).getUserId().getProfileImage()))) {

				Picasso.with(activity)
						.load(chatList.get(position).getUserId().getProfileImage())
						.into(fixerHolder.fixer_chat_user_image);
			}
			if (UtilValidate.isNotNull(chatList.get(position).getChatImage())
					&& (UtilValidate.isNotEmpty(chatList.get(position).getChatImage()))) {
				
				fixerHolder.fixer_chat_content.setVisibility(View.GONE);
				fixerHolder.fixer_chat_image.setVisibility(View.VISIBLE);

				Picasso.with(activity)
						.load(chatList.get(position).getChatImage())
						.into(fixerHolder.fixer_chat_image);
			}
			
			fixerHolder.fixer_chat_image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
					
					


			    	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    
			    	View popupView = inflater.inflate(R.layout.imagepopup, null);
					final PopupWindow popupWindow = new PopupWindow(popupView,android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT,true);
					popupWindow.update();
					
					popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
					final ImageView okbutton=(ImageView)popupView.findViewById(R.id.okbutton);
					final TouchImageView image=(TouchImageView)popupView.findViewById(R.id.image);
					Picasso.with(activity)
					.load(chatList.get(position).getChatImage())
					.into(image);
					
					image.setOnTouchImageViewListener(new OnTouchImageViewListener() {
						
						@Override
						public void onMove() {
							// TODO Auto-generated method stub

							PointF point = image.getScrollPosition();
							RectF rect = image.getZoomedRect();
							float currentZoom = image.getCurrentZoom();
							boolean isZoomed = image.isZoomed();
						
						}
					});
					okbutton.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							popupWindow.dismiss();	
							
						}
					});
					
					
				
				}
			});
			
			}
			}
		
		}
				
				
				
				
				

					
		}
		
		else if(((type > 2)&&(type <10))||((type >10)&&(type <= 15))){
			
			Log.d(TAG,"INSIDE CHAT TYPE ID 3 to 7,9" );
	/*		if(convertView == null){}else {
			chatHolder = (ChatHolder)convertView.getTag();
        }*/
			convertView = inflater.inflate(R.layout.chat_status_listitem, null);
			chatHolder.chat_content_text = (TextView)convertView.findViewById(R.id.chat_status_text);
			convertView.setTag(chatHolder);
			Log.e(TAG,"convertView inside"+convertView );
			
			if(type == 3){
				
			
		
			if (UtilValidate.isNotNull(chatList.get(position))){
				
				if(chatList.get(position).getUserId()!=null){
					
					if(chatList.get(position).getUserId().getFirstName()!=null){
					
					 FirstName = chatList.get(position).getUserId().getFirstName();
					
					}
					if(chatList.get(position).getUserId().getLastName()!=null){
						
					 LastName = chatList.get(position).getUserId().getLastName();
						
					}
				}
				
				if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

					Log.d(TAG, "Inside chat text" + chatList.get(position).getChatText());

					chatHolder.chat_content_text.setText(FirstName+" "+LastName+" "+"submitted a quote for $"+chatList.get(position).getChatText());
				}
						}
			}
			else if(type == 4){
				
			
		
			if (UtilValidate.isNotNull(chatList.get(position))){
				
				if(chatList.get(position).getUserId()!=null){
					
					if(chatList.get(position).getUserId().getFirstName()!=null){
					
					 FirstName = chatList.get(position).getUserId().getFirstName();
					
					}
					if(chatList.get(position).getUserId().getLastName()!=null){
						
					 LastName = chatList.get(position).getUserId().getLastName();
						
					}
				}
				
				if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

					Log.d(TAG, "Inside chat text" + chatList.get(position).getChatText());

					chatHolder.chat_content_text.setText(/*FirstName+" "+LastName+" "+"accepted quote for $"+*/chatList.get(position).getChatText());
				}
						}
			
			}else if(type == 5){

				
		
			if (UtilValidate.isNotNull(chatList.get(position))){
				
				if(chatList.get(position).getUserId()!=null){
					
					if(chatList.get(position).getUserId().getFirstName()!=null){
					
					 FirstName = chatList.get(position).getUserId().getFirstName();
					
					}
					if(chatList.get(position).getUserId().getLastName()!=null){
						
					 LastName = chatList.get(position).getUserId().getLastName();
						
					}
				}
				
				if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

					Log.d(TAG, "Inside chat text" + chatList.get(position).getChatText());
					/*String separated = chatList.get(position).getChatText();
					
				 separated.replace("\\n", "\n");
				 chatHolder.chat_content_text.setText( separated.replace("\\n", "\n") ); */
					
				 String chat=chatList.get(position).getChatText();
				 
				 if(chat.contains("New"))
				 {
					 String newdara = "<b> New</b>";
					 chat=chat.replace("New", newdara);
					 //chat=chat.replaceAll("New", chat);
				 }
				 final String str = chat;
				 final Matcher matcher = Pattern.compile("New").matcher(str);
				 if(matcher.find()){
					 
				   // String boldtext= str.substring(matcher.end()).trim();
				    Log.e("","text>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+str.substring(matcher.end()).trim());
				   
				    String detils="<b>"+str.substring(matcher.end()).trim()+"</b>";
				    
				    Log.e("","text>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+Html.fromHtml(detils));
				    chat=chat.replace(str.substring(matcher.end()).trim(), detils);
				   // String details = "<b> boldtext</b>";
				    //chat=chat.replace("New", details);
				 }
				 
				 
				 if(chat.contains("Fix details have changed."))
				 {
					 String prev="<b> Fix details have changed.</b>";
					 chat=chat.replace("Fix details have changed.", prev);
					 
				 }

				 String separated =chat;
				 separated.replace("\\n", "\n");
				 //chatHolder.chat_content_text.setText( Html.fromHtml(chat) );
				 chatHolder.chat_content_text.setText(Html.fromHtml(separated.replace("\\n","<br />")));
					


				}
						}
			
			
			}else if(type == 6){
				
				
		
			if (UtilValidate.isNotNull(chatList.get(position))){
				
				if(chatList.get(position).getUserId()!=null){
					
					if(chatList.get(position).getUserId().getFirstName()!=null){
					
					 FirstName = chatList.get(position).getUserId().getFirstName();
					
					}
					if(chatList.get(position).getUserId().getLastName()!=null){
						
					 LastName = chatList.get(position).getUserId().getLastName();
						
					}
					
				}
				
				if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

					Log.d(TAG, "Inside chat text" + chatList.get(position).getChatText());

					//chatHolder.chat_content_text.setText(FirstName+" "+LastName+" "+"requested $"+);
					chatHolder.chat_content_text.setText(chatList.get(position).getChatText());
				}
						}
			
			}else if(type == 7){
				
				
		
			if (UtilValidate.isNotNull(chatList.get(position))){
				
				if(chatList.get(position).getUserId()!=null){
					
					if(chatList.get(position).getUserId().getFirstName()!=null){
					
					 FirstName = chatList.get(position).getUserId().getFirstName();
					
					}
					if(chatList.get(position).getUserId().getLastName()!=null){
						
					 LastName = chatList.get(position).getUserId().getLastName();
						
					}
				}
				
				if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

					Log.d(TAG, "Inside chat text" + chatList.get(position).getChatText());

					chatHolder.chat_content_text.setText(FirstName+" "+LastName+" "+"made $"+chatList.get(position).getChatText()+" payment for the job");
				}
						}
			
			}else if(type == 8){
				
				
			if (UtilValidate.isNotNull(chatList.get(position))){
				
				if(chatList.get(position).getUserId()!=null){
					
					if(chatList.get(position).getUserId().getFirstName()!=null){
					
					 FirstName = chatList.get(position).getUserId().getFirstName();
					
					}
					if(chatList.get(position).getUserId().getLastName()!=null){
						
					 LastName = chatList.get(position).getUserId().getLastName();
						
					}
				}
				
				if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

					Log.d(TAG, "Inside chat text" + chatList.get(position).getChatText());
					
					Log.d(TAG, "DB<<<<<<<<<<<" + CurrentlyLoggedUserDAO.getInstance().getUserDetails().getUser().getFirstName());
					
					if((UserDAO.getInstance().getUserDetails().getUser().getFirstName().equalsIgnoreCase(FirstName))&&(UserDAO.getInstance().getUserDetails().getUser().getLastName().equalsIgnoreCase(LastName))){

					chatHolder.chat_content_text.setText(FirstName+" "+LastName+" "+" called "+FixerName);
					
					}else{
						
						chatHolder.chat_content_text.setText(FixerName+" "+" called "+FirstName+" "+LastName);
						
					}
				}
						}
			}
			
			else if(type == 9){
				
				
		
			if (UtilValidate.isNotNull(chatList.get(position))){
				
				if(chatList.get(position).getUserId()!=null){
					
					if(chatList.get(position).getUserId().getFirstName()!=null){
					
					 FirstName = chatList.get(position).getUserId().getFirstName();
					
					}
					if(chatList.get(position).getUserId().getLastName()!=null){
						
					 LastName = chatList.get(position).getUserId().getLastName();
						
					}
				}
				
				if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

					Log.d(TAG, "Inside chat text" + chatList.get(position).getChatText());
					int i=0;
					String[] separated = chatList.get(position).getChatText().split(",");
					chatHolder.chat_content_text.setText(FirstName+" "+LastName+" "+"edited quote from $" + separated[i] +" to $"+separated[i+1]); 
				      /*for (int i = 0; i < separated.length; i++) {
				    	  String  value = separated[i];
				    	  value
				    	  
				           }*/
					
				}
						}
			}else if(type == 11){
				
				
		
			if (UtilValidate.isNotNull(chatList.get(position))){
				
				if(chatList.get(position).getUserId()!=null){
					
					if(chatList.get(position).getUserId().getFirstName()!=null){
					
					 FirstName = chatList.get(position).getUserId().getFirstName();
					
					}
					if(chatList.get(position).getUserId().getLastName()!=null){
						
					 LastName = chatList.get(position).getUserId().getLastName();
						
					}
				}
				
				if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

					Log.d(TAG, "Inside chat text" + chatList.get(position).getChatText());

					chatHolder.chat_content_text.setText(chatList.get(position).getChatText());
				}
						}
			}else if(type == 12){
				
			
		
			if (UtilValidate.isNotNull(chatList.get(position))){
				
				if(chatList.get(position).getUserId()!=null){
					
					if(chatList.get(position).getUserId().getFirstName()!=null){
					
					 FirstName = chatList.get(position).getUserId().getFirstName();
					
					}
					if(chatList.get(position).getUserId().getLastName()!=null){
						
					 LastName = chatList.get(position).getUserId().getLastName();
						
					}
				}
				
				if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

					Log.d(TAG, "Inside chat text" + chatList.get(position).getChatText());

					chatHolder.chat_content_text.setText(chatList.get(position).getChatText());
				}
						}
			}else if(type == 13){
				
				
			if (UtilValidate.isNotNull(chatList.get(position))){
				
				if(chatList.get(position).getUserId()!=null){
					
					if(chatList.get(position).getUserId().getFirstName()!=null){
					
					 FirstName = chatList.get(position).getUserId().getFirstName();
					
					}
					if(chatList.get(position).getUserId().getLastName()!=null){
						
					 LastName = chatList.get(position).getUserId().getLastName();
						
					}
				}
				
				if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

					Log.d(TAG, "Inside chat text" + chatList.get(position).getChatText());

					chatHolder.chat_content_text.setText(FirstName+" "+LastName+" marked as job Completed.");
				}
						}
			}else if(type == 14){
				
			
		
			if (UtilValidate.isNotNull(chatList.get(position))){
				
				if(chatList.get(position).getUserId()!=null){
					
					if(chatList.get(position).getUserId().getFirstName()!=null){
					
					 FirstName = chatList.get(position).getUserId().getFirstName();
					
					}
					if(chatList.get(position).getUserId().getLastName()!=null){
						
					 LastName = chatList.get(position).getUserId().getLastName();
						
					}
				}
				
				if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

					Log.d(TAG, "Inside chat text" + chatList.get(position).getChatText());

					chatHolder.chat_content_text.setText(FirstName+" "+LastName+" accepted payment.");
				}
						}
			}else if(type == 15){
				
				
		
			if (UtilValidate.isNotNull(chatList.get(position))){
				
				/*if(chatList.get(position).getUserId()!=null){
					
					if(chatList.get(position).getUserId().getFirstName()!=null){
					
					 FirstName = chatList.get(position).getUserId().getFirstName();
					
					}
					if(chatList.get(position).getUserId().getLastName()!=null){
						
					 LastName = chatList.get(position).getUserId().getLastName();
						
					}
				}*/
				
				if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

					Log.e(TAG, "Inside chat text" + chatList.get(position).getChatText());

					chatHolder.chat_content_text.setText(chatList.get(position).getChatText());
				}
						}
			}
			
		}else if((type == 10)){

			Log.d(TAG,"INSIDE CHAT TYPE ID 10" );
			if(convertView == null){
			convertView = inflater.inflate(R.layout.taptocall_event_layout, null);
			tapHolder.tap_button = (Button)convertView.findViewById(R.id.btn_tap_to_call);
				convertView.setTag(tapHolder);
				Log.e(TAG,"convertView inside"+convertView );
				tapHolder.tap_button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
					
						chatTabFragment.call(position);
						
					}
				});
		}
				
			/*if (UtilValidate.isNotNull(chatList.get(position))){
				if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {

					Log.d(TAG, "Inside chat text" + chatList.get(position).getChatText());

					tapHolder.tap_button.setText(chatList.get(position).getChatText());
				}
						}*/
			
			
		
			
		}/*else {
			
			
			
		}*/
		
		
		
		
		return convertView;
	}

	public class ViewHolder {

		 TextView chat_name;
		 TextView chat_date;
		 TextView chat_content;
		 CircularImageView chat_user_image;
		 ImageView chat_image;
	}

	public class ChatHolder {

		 TextView chat_content_text;
	}

	public class FixerHolder {

		 TextView fixer_chat_name;
		 TextView fixer_chat_date;
		 TextView fixer_chat_content;
		 CircularImageView fixer_chat_user_image;
		 ImageView fixer_chat_image;
	}
	public class TapHolder {

		 Button tap_button;
	}

}
