package com.xminds.easyhomefix.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.Camera.PictureCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Holder.JobCategories;


public class FixItemsAdapter extends BaseAdapter{
	
	private Activity activity;
	private LayoutInflater inflater;
	private ViewHolder viewholder;
	private List<JobCategories> FixList=new ArrayList<JobCategories>();
	//private int po=0;
	private static final String TAG = FixItemsAdapter.class.getName();

	public FixItemsAdapter(Activity activity, List<JobCategories> FixList) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.FixList = FixList;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	 @Override
	    public int getCount() {
	        return FixList.size();
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
	
	@SuppressLint("NewApi")
	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			viewholder = new ViewHolder();

			convertView = inflater.inflate(R.layout.horizontal_list_item, null);
			viewholder.select_fix_category_ac_icon = (ImageView)convertView.findViewById(R.id.select_fix_category_ac_icon);
			viewholder.select_fix_category_ac_text = (TextView)convertView.findViewById(R.id.select_fix_category_ac_text);
			viewholder.select_fix_category_ac_layout= (LinearLayout)convertView.findViewById(R.id.select_fix_category_ac_layout);
			convertView.setTag(viewholder);
		}else {
			viewholder = (ViewHolder)convertView.getTag();
        }
		//Log.e("#######",""+po);
		if(position == 0)
		{
			viewholder.select_fix_category_ac_text.setText("Air-con");
			if(FixList.get(position).isSelected()){
				//Log.e("#######","@1 "+po);
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.ac_w));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg_green));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.white));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}else{
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.ac_icon));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.black));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}
		}
		if(position == 1)
		{
			viewholder.select_fix_category_ac_text.setText(FixList.get(position).getName());
			if(FixList.get(position).isSelected()){
				//Log.e("#######","@2 "+po);
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.plumbing_w));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg_green));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.white));
			}else{
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.plumbing_icon));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.black));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}
		}

		if(position == 2)
		{
			viewholder.select_fix_category_ac_text.setText(FixList.get(position).getName());
			if(FixList.get(position).isSelected()){
				//Log.e("#######","@3 "+po);
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.wiring_w));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg_green));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.white));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}else{
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.wiring_icon));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.black));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}
		}
		if(position == 3)
		{
			viewholder.select_fix_category_ac_text.setText(FixList.get(position).getName());
			if(FixList.get(position).isSelected()){
				//Log.e("#######","@4 "+po);
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.painting_w));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg_green));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.white));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}else{
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.painting_icon));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.black));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}
		}
		if(position == 4)
		{
			viewholder.select_fix_category_ac_text.setText(FixList.get(position).getName());
			if(FixList.get(position).isSelected()){
				//Log.e("#######","@5 "+po);
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.carpenter_w));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg_green));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.white));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}else{
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.carpenter_icon));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.black));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}
		}
		if(position == 5)
		{
			viewholder.select_fix_category_ac_text.setText(FixList.get(position).getName());
			if(FixList.get(position).isSelected()){
				//Log.e("#######","@6 "+po);
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.lock_smith_w));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg_green));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.white));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}else{
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.locksmith_icon));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.black));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}
		}
		
		if(position == 6)
		{
			viewholder.select_fix_category_ac_text.setText(FixList.get(position).getName());
			if(FixList.get(position).isSelected()){
				//Log.e("#######","@7 "+po);
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.pest_control_s));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg_green));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.white));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}else{
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.pest_control_icon));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.black));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}
		}
		if(position == 7)
		{
			viewholder.select_fix_category_ac_text.setText(FixList.get(position).getName());
			if(FixList.get(position).isSelected()){
				//Log.e("#######","@8 "+po);
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.sanitization_w));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg_green));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.white));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}else{
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.sanitization_icon));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.black));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}
		}
		if(position == 8)
		{
			viewholder.select_fix_category_ac_text.setText("Gen Works");
			if(FixList.get(position).isSelected()){
				//Log.e("#######","@8 "+po);
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.general_works_icon_s));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg_green));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.white));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}else{
				viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.general_works_icon));
				viewholder.select_fix_category_ac_layout.setBackground(activity.getResources().getDrawable(R.drawable.select_fix_bg));
				viewholder.select_fix_category_ac_text.setTextColor(activity.getResources().getColor(R.color.black));
				viewholder.select_fix_category_ac_icon.getLayoutParams().height=100;
			}
		}
	
		viewholder.select_fix_category_ac_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		return convertView;
	}

	public class ViewHolder{
		private ImageView select_fix_category_ac_icon;
		private TextView select_fix_category_ac_text;
		private LinearLayout select_fix_category_ac_layout;
		
	}
}
