package com.xminds.easyhomefix.adapter;

import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.adapter.ImageSettingAdapter.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class CamImageSetAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private ViewHolder viewholder;
	private List<String>imagelist = new ArrayList<String>();
	
	public CamImageSetAdapter(Activity activity,  List<String> imagelist) {
		this.activity = activity;
		this.imagelist = imagelist;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			viewholder.imageview_delete_image = (ImageView)view.findViewById(R.id.imageview_delete_image);
			view.setTag(viewholder);
			
		}
		
		else
		{
			viewholder = (ViewHolder)view.getTag();
		}
		
		viewholder.imageview_delete_image .setVisibility(View.VISIBLE);
		if(imagelist!=null)
		{
			if(!imagelist.isEmpty())
			{
				Log.e("","image details>>>>>>>>>>>>>>>>>>>"+imagelist.get(position));
				/*Picasso.with(activity).load(imagelist.get(position))
				.into(viewholder.customerimage);
				viewholder.customerimage.setScaleType(ScaleType.FIT_XY);*/
				 BitmapFactory.Options options = new BitmapFactory.Options();
				 
		            // downsizing image as it throws OutOfMemory Exception for larger
		            // images
		            options.inSampleSize = 8;
		 
		            final Bitmap bitmap = BitmapFactory.decodeFile(imagelist.get(position),options);
		 
		            viewholder.customerimage.setImageBitmap(bitmap);
			}
		}
	
		return view;
	
	}
	
	public class ViewHolder
	{
		private ImageView customerimage;
		private ImageView imageview_delete_image;
		
	}

}
