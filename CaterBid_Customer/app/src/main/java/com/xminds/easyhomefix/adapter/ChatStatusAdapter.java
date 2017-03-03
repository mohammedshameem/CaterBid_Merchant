package com.xminds.easyhomefix.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pkmmte.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Holder.ChatList;
import com.xminds.easyhomefix.actionbar.TouchImageView;
import com.xminds.easyhomefix.actionbar.TouchImageView.OnTouchImageViewListener;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.dao.UserDAO;
import com.xminds.easyhomefix.fragment.ChatTabFragment;
import com.xminds.easyhomefix.utils.UtilValidate;

public class ChatStatusAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private String fixerName;
	private List<ChatList> chatList = new ArrayList<ChatList>();
	private static final String TAG = ChatAdapter.class.getName();
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
	private final int CHAT_CALL_IMAGE = 10;
	private final int CHAT_TYPE_REVIEW_REQUESTED = 11;
	private final int CHAT_TYPE_REVIEW_ADDED = 12;
	private final int CHAT_TYPE_JOB_COMPLETED = 13;
	private final int CHAT_TYPE_CASH_ACCEPTED = 14;
	private final int CHAT_TYPE_JOB_CANCELLED = 15;

	protected static final int REQUEST_CAMERA = 0;
	protected static final int SELECT_FILE = 1;
	private ChatTabFragment chatTabFragment;
	
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
		this.fixerName = fixerName;
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

		} else if (chatList.get(position).getChatTypeId() == 2) {
			type = CHAT_IMAGE; 
			
			return type;

		}else if (chatList.get(position).getChatTypeId() == 3) {
			type = CHAT_QUOTE_SUBMITTED;
			return type;
		}else if (chatList.get(position).getChatTypeId() == 4) {
			type = CHAT_QUOTE_ACCEPTED;
			return type;
		}else if (chatList.get(position).getChatTypeId() == 5) {
			type = CHAT_FIX_EDITED;
			return type;
		}else if (chatList.get(position).getChatTypeId() == 6) {
			type = CHAT_PAYMENT_REQUESTED;
			return type;
		}else if (chatList.get(position).getChatTypeId() == 7) {
			type = CHAT_PAYMENT_MADE;
			return type;
		}
		else if (chatList.get(position).getChatTypeId() == 8) {
			type = CHAT_CALL;
			return type;
		} 
		
		else if (chatList.get(position).getChatTypeId() == 9) {
			type = CHAT_QUOTE_EDITED;
			return type;
		} 
		
		else if (chatList.get(position).getChatTypeId() == 10) {
			type = CHAT_CALL_IMAGE;
			return type;
		} 
		
		else if (chatList.get(position).getChatTypeId() == 11) {
			type = CHAT_TYPE_REVIEW_REQUESTED;
			return type;
		} 
		
		else if (chatList.get(position).getChatTypeId() == 12) {
			type = CHAT_TYPE_REVIEW_ADDED;
			return type;
		} 
		
		else if (chatList.get(position).getChatTypeId() == 13) {
			type = CHAT_TYPE_JOB_COMPLETED;
			return type;
		} 
		
		else if (chatList.get(position).getChatTypeId() == 14) {
			type = CHAT_TYPE_CASH_ACCEPTED;
			return type;
		} 
		
		else{
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
		final FixerHolder fixerHolder = new FixerHolder();
		TapHolder tapHolder = new TapHolder();
		int type = getItemViewType(position);
		
			Log.e(TAG, "@@@@@@@@@@@type"+type);
		if(type==1){
			if(UtilValidate.isNotNull(chatList.get(position).getUserId().getUserType())){
			if(chatList.get(position).getUserId().getUserType().equalsIgnoreCase("Customer")){
				
				convertView = inflater.inflate(R.layout.chat_list_item_fixer,null);
				fixerHolder.fixer_chat_name = (TextView)convertView.findViewById(R.id.fix_chat_name);
				fixerHolder.fixer_chat_date = (TextView)convertView.findViewById(R.id.fix_chat_date);
				fixerHolder.fixer_chat_content = (TextView)convertView.findViewById(R.id.fix_chat_content);
				fixerHolder.fixer_chat_user_image = (CircularImageView)convertView.findViewById(R.id.fix_chat_user_image);
				fixerHolder.fixer_chat_image = (ImageView) convertView
						.findViewById(R.id.fix_chat_pic);
				
				convertView.setTag(fixerHolder);
			
/*
			if(fixerHolder.fixer_chat_name == null){}else {
				fixerHolder = (FixerHolder)fixerHolder.fixer_chat_name.getTag();
	        }*/
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
		
			
			
			else if(chatList.get(position).getUserId().getUserType().equalsIgnoreCase("Fixer")){
				

				/*	
					if(viewholder.chat_name == null){}else {
					viewholder = (ViewHolder)viewholder.chat_name.getTag();
		        }*/
						

						convertView = inflater.inflate(R.layout.chat_list_item_customer,  null);
						viewholder.chat_name = (TextView)convertView.findViewById(R.id.chat_name);
						viewholder.chat_date = (TextView)convertView.findViewById(R.id.chat_date);
						viewholder.chat_content = (TextView)convertView.findViewById(R.id.chat_content);
						viewholder.chat_user_image = (CircularImageView)convertView.findViewById(R.id.chat_user_image);
						viewholder.chat_image = (ImageView) convertView
								.findViewById(R.id.chat_pic);
						convertView.setTag(viewholder);
				
					
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
			}	
			
			
	/*		fixerHolder.fixer_chat_image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});*/
			
			
				
		}
		
		else if(type==2){
			
			
				if(UtilValidate.isNotNull(chatList.get(position).getUserId().getUserType())){
			if(chatList.get(position).getUserId().getUserType().equalsIgnoreCase("Customer")){
				
				convertView = inflater.inflate(R.layout.chat_list_item_fixer,null);
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
			
			else if(chatList.get(position).getUserId().getUserType().equalsIgnoreCase("Fixer")){
				
					convertView = inflater.inflate(R.layout.chat_list_item_customer,  null);
					viewholder.chat_name = (TextView)convertView.findViewById(R.id.chat_name);
					viewholder.chat_date = (TextView)convertView.findViewById(R.id.chat_date);
					viewholder.chat_content = (TextView)convertView.findViewById(R.id.chat_content);
					viewholder.chat_user_image = (CircularImageView)convertView.findViewById(R.id.chat_user_image);
					viewholder.chat_image = (ImageView) convertView
							.findViewById(R.id.chat_pic);
					convertView.setTag(viewholder);
			
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
		}
				
				
				
		}
		
		else if(((type > 2)&&(type <10))||((type >10) &&(type <= 15))){
			
			Log.d(TAG,"INSIDE CHAT TYPE ID 3 to 7,9" );
	/*		if(convertView == null){}else {
			chatHolder = (ChatHolder)convertView.getTag();
        }*/
			

			convertView = inflater.inflate(R.layout.chat_status_listitem, null);
				chatHolder.chat_content_text = (TextView)convertView.findViewById(R.id.chat_status_text);
				convertView.setTag(chatHolder);
				Log.e(TAG,"convertView inside");
		
			if(UtilValidate.isNotNull(chatList.get(position))){
				if(type==3){
					if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {
						Log.e(TAG, "Inside chat text" + chatList.get(position).getChatText());
						if(chatList.get(position).getUserId() != null){
							chatHolder.chat_content_text.setText(chatList.get(position).getUserId().getFirstName()+
									" "+chatList.get(position).getUserId().getLastName()+
									" "+"submitted a quote for $"+chatList.get(position).getChatText());
						}
					}
				}else if(type==4){
					if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {
						Log.e(TAG, "Inside chat text" + chatList.get(position).getChatText()+"|ChatType: "+type);
						if(chatList.get(position).getUserId() != null){
							chatHolder.chat_content_text.setText(chatList.get(position).getChatText());
						}
					}
				}else if(type==5){
					Log.e("","in typpeeeee>>>>>>>>>>>>>&&&&&&&&&&&&&&&&&");
					if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {
						Log.e(TAG, "Inside chat text" + chatList.get(position).getChatText()+"|ChatType: "+type);
						if(chatList.get(position).getUserId() != null){
							chatHolder.chat_content_text.setText(chatList.get(position).getChatText().replace("\\n", "\n"));
							String chat=chatList.get(position).getChatText();
							if(chat.contains("New"))
							{
								Log.e("","in new>>>>>>>>>>>>>&&&&&&&&&&&&&&&&&");
							}
							
						}
					}
				}else if(type==6){
					if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {
						Log.e(TAG, "Inside chat text" + chatList.get(position).getChatText()+"|ChatType: "+type);
						if(chatList.get(position).getUserId() != null){
							chatHolder.chat_content_text.setText(chatList.get(position).getChatText());
						}
					}
				}else if(type==7){
					if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {
						Log.e(TAG, "Inside chat text" + chatList.get(position).getChatText()+"|ChatType: "+type);
						if(chatList.get(position).getUserId() != null){
							chatHolder.chat_content_text.setText(chatList.get(position).getChatText());
						}
					}
				}else if(type==8){
					if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {
						Log.e(TAG, "Inside chat text" + chatList.get(position).getChatText()+"|ChatType: "+type+" fixerName: "+fixerName);
						if(chatList.get(position).getUserId() != null){
							if(Integer.parseInt(chatList.get(position).getUserId().getId())==CurrentlyLoggedUserDAO.getInstance().getUserId()){
								chatHolder.chat_content_text.setText(chatList.get(position).getUserId().getFirstName()+
										" "+chatList.get(position).getUserId().getLastName()+
										" called "+fixerName);
							}else{
								chatHolder.chat_content_text.setText(chatList.get(position).getUserId().getFirstName()+
										" "+chatList.get(position).getUserId().getLastName()+
										" called "+UserDAO.getInstance().getUserDetails().getUser().getFirstName()+" "+
										UserDAO.getInstance().getUserDetails().getUser().getLastName());
							}
						}
					}
				}else if(type==9){
					if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {
						Log.e(TAG, "Inside chat text" + chatList.get(position).getChatText()+"|ChatType: "+type);
						int i =0;
						String[] separated = chatList.get(position).getChatText().split(",");
						if(chatList.get(position).getUserId() != null){
							chatHolder.chat_content_text.setText(chatList.get(position).getUserId().getFirstName()+" "+chatList.get(position).getUserId().getLastName()+" "+"edited quote from $" + separated[i] +" to $"+separated[i+1]);
						}
					}
				}else if(type==11){
					if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {
						Log.e(TAG, "Inside chat text" + chatList.get(position).getChatText()+"|ChatType: "+type);
						if(chatList.get(position).getUserId() != null){
							chatHolder.chat_content_text.setText(chatList.get(position).getChatText());
						}
					}
				}else if(type==12){
					if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {
						Log.e(TAG, "Inside chat text" + chatList.get(position).getChatText()+"|ChatType: "+type);
						if(chatList.get(position).getUserId() != null){
							chatHolder.chat_content_text.setText(chatList.get(position).getChatText());
						}
					}
				}else if(type==13){
					if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {
						Log.e(TAG, "Inside chat text" + chatList.get(position).getChatText()+"|ChatType: "+type);
						if(chatList.get(position).getUserId() != null){
							chatHolder.chat_content_text.setText(chatList.get(position).getChatText());
						}
					}
				}else if(type==14){
					if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {
						Log.e(TAG, "Inside chat text" + chatList.get(position).getChatText()+"|ChatType: "+type);
						if(chatList.get(position).getUserId() != null){
							chatHolder.chat_content_text.setText(chatList.get(position).getChatText());
						}
					}
				}else if(type==15){
					if ((UtilValidate.isNotNull(chatList.get(position).getChatText()))) {
						Log.e(TAG, "Inside chat text" + chatList.get(position).getChatText()+"|ChatType: "+type);
						if(chatList.get(position).getUserId() != null){
							chatHolder.chat_content_text.setText(chatList.get(position).getChatText());
						}
					}
				}
			}
			
		}else if((type == 10)){

			Log.e(TAG,"INSIDE CHAT TYPE ID 10" );
			if(convertView == null){
			convertView = inflater.inflate(R.layout.taptocall_event_layout, null);
			tapHolder.tap_button = (Button)convertView.findViewById(R.id.btn_tap_to_call);
				convertView.setTag(tapHolder);
				Log.e(TAG,"convertView inside");
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
