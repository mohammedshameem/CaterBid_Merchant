package com.xminds.easyhomefix.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Holder.ChatList;
import com.xminds.easyhomefix.utils.UtilValidate;


public class ChatAdapter extends BaseAdapter{
	
	
	private Activity activity;
	private LayoutInflater inflater;
	private ViewHolder viewholder;
	private List<ChatList> chatList=new ArrayList<ChatList>();
	private static final String TAG = ChatAdapter.class.getName();
	private final int VIEW_FIXER 		= 0;
	private final int VIEW_CUSTOMER 		= 1;
	private final int VIEW_COMMENT 	= 2;
	protected static final int REQUEST_CAMERA = 0;
	protected static final int SELECT_FILE = 1;
	
	
	public ChatAdapter(Activity activity, List<ChatList> chatList) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.chatList = chatList;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d(TAG,"ChatAdapter" );
	}

	 @Override
	    public int getCount() {
	        return chatList.size();
		
	    }

	    /*
	     * (non-Javadoc)
	     * @see android.widget.Adapter#getItem(int)
	     */
	    @Override
	    public Object getItem(int position) {
	        // TODO Auto-generated method stub
	        return position;
	    }
	    
	    /*
	     * (non-Javadoc)
	     * @see android.widget.Adapter#getItemId(int)
	     */
	    @Override
	    public long getItemId(int position) {
	        // TODO Auto-generated method stub
	        return position;
	    }
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 3;
	}
	@Override
	public int getItemViewType(int position) {
		Log.d(TAG,"getItemViewType" );

		if(chatList.get(position).getUserId().getUserType().equalsIgnoreCase("fixer")){
			return VIEW_FIXER;

		}else if(chatList.get(position).getUserId().getUserType().equalsIgnoreCase("Customer")){
			return VIEW_CUSTOMER;

		}else{
			return VIEW_COMMENT;
		}
	}
	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		int type = getItemViewType(position);
		Log.d(TAG,"type"+type );
		/*if((UtilValidate.isNotNull(chatList.get(position).getChatTypeId()))&&(UtilValidate.isEmpty(chatList.get(position).getChatTypeId()))){
		if(chatList.get(position).getChatTypeId() == 1){*/
			Log.d(TAG,"INSIDE CHAT TYPE ID 1" );
		if(type == VIEW_FIXER){
			Log.d(TAG,"VIEW_FIXER" );
			if(convertView == null){
				viewholder = new ViewHolder();
				convertView = inflater.inflate(R.layout.chat_list_item_fixer, null);
				viewholder.chat_name = (TextView)convertView.findViewById(R.id.chat_name);
				viewholder.chat_date = (TextView)convertView.findViewById(R.id.chat_date);
				viewholder.chat_content = (TextView)convertView.findViewById(R.id.chat_content);
				viewholder.chat_user_image = (ImageView)convertView.findViewById(R.id.chat_user_image);
				convertView.setTag(viewholder);
			}else {
				viewholder = (ViewHolder)convertView.getTag();
	        }
			viewholder.chat_name .setText(chatList.get(position).getUserId().getFirstName()+" "+chatList.get(position).getUserId().getLastName());
			viewholder.chat_date.setText(chatList.get(position).getChatDateTime());
			viewholder.chat_content.setText(chatList.get(position).getChatText());

		}
		else if(type == VIEW_CUSTOMER){
			Log.d(TAG,"VIEW_CUSTOMER" );
			if(convertView == null){
				viewholder = new ViewHolder();
				convertView = inflater.inflate(R.layout.chat_list_item_customer, null);
				viewholder.chat_name = (TextView)convertView.findViewById(R.id.chat_name);
				viewholder.chat_date = (TextView)convertView.findViewById(R.id.chat_date);
				viewholder.chat_content = (TextView)convertView.findViewById(R.id.chat_content);
				viewholder.chat_user_image = (ImageView)convertView.findViewById(R.id.chat_user_image);
				convertView.setTag(viewholder);
			}else {
				viewholder = (ViewHolder)convertView.getTag();
	        }
			
			viewholder.chat_name .setText(chatList.get(position).getUserId().getFirstName()+" "+chatList.get(position).getUserId().getLastName());
			viewholder.chat_date.setText(chatList.get(position).getChatDateTime());
			viewholder.chat_content.setText(chatList.get(position).getChatText());
			
			
		}else{
		//}
		if(chatList.get(position).getChatTypeId() > 2){
			Log.d(TAG,"INSIDE CHAT TYPE ID 2" );
			if(convertView == null){
				viewholder = new ViewHolder();
				convertView = inflater.inflate(R.layout.chat_status_listitem, null);
				viewholder.chat_content = (TextView)convertView.findViewById(R.id.chat_content);
				convertView.setTag(viewholder);
			}else {
				viewholder = (ViewHolder)convertView.getTag();
	        }
			viewholder.chat_content.setText(chatList.get(position).getChatText());
			
			
		}
		}
		
		
		return convertView;
	}

	public class ViewHolder{
	
		private TextView chat_name;
		private TextView chat_date;
		private TextView chat_content;
		private ImageView chat_user_image;
	}

}
