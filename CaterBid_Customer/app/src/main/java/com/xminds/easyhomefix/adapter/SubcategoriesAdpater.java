package com.xminds.easyhomefix.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Holder.JobCategories;

public class SubcategoriesAdpater extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	List<JobCategories> jobsubcategories;
	Holder holder;
	public SubcategoriesAdpater(Activity activity, List<JobCategories> jobsubcategories) {
		// TODO Auto-generated constructor stub
		
		this.activity = activity;
		this.jobsubcategories = jobsubcategories;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jobsubcategories.size();
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
					R.layout.spinner_row_jobsubcategories, null);
			holder.jobsubcat = (TextView) convertView
					.findViewById(R.id.jobsubcat);
	


			convertView.setTag(holder);

		} else {

				holder = (Holder) convertView.getTag();

		}
		
		holder.jobsubcat.setText(jobsubcategories.get(position).getName());

		return convertView;
	}
			
			
		
		

	
	public class Holder {
		
		private TextView jobsubcat;
		
		
	}
	
	

}
