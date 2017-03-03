package com.xminds.easyhomefix.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

import com.pkmmte.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.Holder.Quotes;
import com.xminds.easyhomefix.activities.ReviewActivity;
import com.xminds.easyhomefix.circularimage.RoundTransform;
import com.xminds.easyhomefix.utils.UtilValidate;

public class FancyCoverFlowSampleAdapter extends FancyCoverFlowAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private int width=0;
    private int height=0;
    List<Quotes>quotes;
    
	
   // private int[] images = {R.drawable.profile_image_null, R.drawable.profile_image_null, R.drawable.profile_image_null, R.drawable.profile_image_null, R.drawable.profile_image_null, R.drawable.profile_image_null,};

    
    public FancyCoverFlowSampleAdapter(
			Activity activity,int width,int height,List<Quotes>quotes) {
		// TODO Auto-generated constructor stub
    	this.activity = activity;
    	this.width=width;
    	this.height=height;
    	this.quotes=quotes;
    	inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
    public int getCount() {
        return quotes.size();
    }

    @Override
    public Integer getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
        CircularImageView imageView = null;

        if (reuseableView != null) {
            imageView = (CircularImageView) reuseableView;
        } else {
            imageView = new CircularImageView(viewGroup.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setLayoutParams(new FancyCoverFlow.LayoutParams(width,height));

        }
        Log.e("", ">>>>"+quotes.get(i).getFixerId().getUserId().getProfileImage());
        if(UtilValidate.isNotNull(quotes.get(i).getFixerId().getUserId().getProfileImage()))
        {
        	if(quotes.get(i).getFixerId().getUserId().getLoginType().equalsIgnoreCase("facebook"))
        	{
        		String profileurl=quotes.get(i).getFixerId().getUserId().getProfileImage();
				if(profileurl.contains("http"))
				{
					profileurl=profileurl.replace("http", "https");
				}
				Picasso.with(activity).load(profileurl)
				.into(imageView);				
        	}
        	else
        	{
        		Picasso.with(activity).load(quotes.get(i).getFixerId().getUserId().getProfileImage()).placeholder(R.drawable.profile_image_null).error(R.drawable.profile_image_null).transform(new RoundTransform()).into(imageView);
        	}
        	
        }
        else
        {
        	Picasso.with(activity).load(R.drawable.profile_image_null).placeholder(R.drawable.profile_image_null).error(R.drawable.profile_image_null).transform(new RoundTransform()).into(imageView);
        }
        
        //imageView.setImageResource(this.getItem(i));
  

        return imageView;
    }

	
}
