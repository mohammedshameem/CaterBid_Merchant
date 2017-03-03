package com.xminds.easyhomefix.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.easyhomefix.customer.R;


public class customerUserAdapter extends BaseAdapter {
	 
	Context mcontext;
	 private static LayoutInflater inflater=null;
	 
	public customerUserAdapter(Context  mcontext)
	{ 
		super();
		this.mcontext = mcontext;
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) mcontext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView=inflater.inflate(R.layout.customer_user_listitem, parent,false);
		return convertView;
	}

}
