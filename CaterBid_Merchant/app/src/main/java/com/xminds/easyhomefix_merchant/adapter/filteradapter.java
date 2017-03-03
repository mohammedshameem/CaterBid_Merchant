package com.xminds.easyhomefix_merchant.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easyhomefix.R;

public class filteradapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	List<String> filterList;
	Holder holder;
	public filteradapter(Activity activity, List<String> filterList) {
		// TODO Auto-generated constructor stub
		
		this.activity = activity;
		this.filterList = filterList;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return filterList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {

			// PromotionObject promotion = promotionlist.g;
			 holder = new Holder();
			convertView = inflater.inflate(
					R.layout.spinner_item, null);
			holder.main_text = (TextView) convertView
					.findViewById(R.id.spinner_item_textview);
	
			
	
		


			convertView.setTag(holder);

		} else {

				holder = (Holder) convertView.getTag();

		}
		
		holder.main_text.setText(filterList.get(position));

		return convertView;
	}
			
			
		
		

	
	public class Holder {
		
		private TextView main_text;
		
		
	}
	
	

}
