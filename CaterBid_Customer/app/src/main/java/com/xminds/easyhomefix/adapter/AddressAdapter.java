package com.xminds.easyhomefix.adapter;

import java.util.ArrayList;
import java.util.List;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Holder.Address;
import com.xminds.easyhomefix.adapter.CompletedCancelledAdapter.PaidHolder;
import com.xminds.easyhomefix.utils.UtilValidate;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AddressAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Address> addressList = new ArrayList<Address>();
	private Activity activity;
	
	public AddressAdapter(Activity activity,List<Address> addressList) {
		this.activity = activity;
		this.inflater =  (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
		this.addressList = addressList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return addressList.size();
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
		AddressHolder addressHolder = new AddressHolder();
		if(convertView == null){
			convertView = inflater.inflate(R.layout.select_time, parent,false);
			addressHolder.address = (TextView)convertView.findViewById(R.id.time_text);
			convertView.setTag(addressHolder);
		}else{
			addressHolder=(AddressHolder)convertView.getTag();
		}
		
		if(addressList.get(position) != null){
			StringBuilder full_address = new StringBuilder();
			if(UtilValidate.isNotEmpty(addressList.get(position).getBlock_number())){
				full_address.append(addressList.get(position).getBlock_number());
			}
			if(UtilValidate.isNotEmpty(addressList.get(position).getFloor())){
				full_address.append(","+addressList.get(position).getFloor());
			}
			if(UtilValidate.isNotEmpty(addressList.get(position).getUnit())){
				full_address.append(","+addressList.get(position).getUnit());
			}
			if(UtilValidate.isNotEmpty(addressList.get(position).getRoad_building())){
				full_address.append(","+addressList.get(position).getRoad_building());
			}
			/*if(UtilValidate.isNotEmpty(addressList.get(position).getPostal_code())){
				full_address.append(","+addressList.get(position).getPostal_code());
			}*/
			addressHolder.address.setText(full_address.toString());
		}
		
		return convertView;
	}
	
	public class AddressHolder {
		
		private TextView address;
	}
}
