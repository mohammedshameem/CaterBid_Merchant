package com.xminds.easyhomefix.adapter;

import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Holder.JobList;
import com.xminds.easyhomefix.adapter.FixItemsAdapter.ViewHolder;
import com.xminds.easyhomefix.adapter.PendingAdapter.PendingHolder;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.fragment.PendingTabTrackYourFixFragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageSettingAdapter extends BaseAdapter {
	
	private Activity activity;
	private LayoutInflater inflater;
	private ViewHolder viewholder;
	private List<String>imagelist = new ArrayList<String>();
	

	public ImageSettingAdapter(Activity activity,  List<String> imagelist) {
		// TODO Auto-generated constructor stub
		
		this.activity = activity;
		this.imagelist = imagelist;
		inflater = (LayoutInflater)EasyHomeFixApp.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imagelist.size();
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
		
		View view = convertView; 
		ViewHolder viewholder = new ViewHolder();
		if(view==null)
		{
			view = inflater.inflate(R.layout.detailpage_image_listitem, null);
			viewholder.customerimage=(ImageView)view.findViewById(R.id.imageview_customerimage);
			view.setTag(viewholder);
			
		}
		
		else
		{
			viewholder = (ViewHolder)view.getTag();
		}
		
		if(imagelist!=null)
		{
			if(!imagelist.isEmpty())
			{
				Log.e("","image details>>>>>>>>>>>>>>>>>>>"+imagelist.get(position));
				Picasso.with(activity).load(imagelist.get(position))
				.into(viewholder.customerimage);
				viewholder.customerimage.setScaleType(ScaleType.FIT_XY);
			}
		}
	
		return view;
	}
	
	public class ViewHolder
	{
		private ImageView customerimage;
		
		
	}

}
