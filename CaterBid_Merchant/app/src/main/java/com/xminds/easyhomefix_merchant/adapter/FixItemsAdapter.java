package com.xminds.easyhomefix_merchant.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyhomefix.R;

public class FixItemsAdapter extends BaseAdapter{
	
	private Activity activity;
	private LayoutInflater inflater;
	private ViewHolder viewholder;
	private List<String> FixList=new ArrayList<String>();
	private static final String TAG = FixItemsAdapter.class.getName();

	public FixItemsAdapter(Activity activity, List<String> FixList) {
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
	
	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			viewholder = new ViewHolder();

			convertView = inflater.inflate(R.layout.horizontal_list_item, null);
			viewholder.select_fix_category_ac_icon = (ImageView)convertView.findViewById(R.id.select_fix_category_ac_icon);
			viewholder.select_fix_category_ac_text = (TextView)convertView.findViewById(R.id.select_fix_category_ac_text);
			convertView.setTag(viewholder);
		}else {
			viewholder = (ViewHolder)convertView.getTag();
        }

		if(FixList.get(position).equals("1"))
		{
			viewholder.select_fix_category_ac_text.setText("Ac");
			viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.ac_icon));
		}
		if(FixList.get(position).equals("2"))
		{
			viewholder.select_fix_category_ac_text.setText("Plumbing");
			viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.plumbing_icon));
		}

		if(FixList.get(position).equals("3"))
		{
			viewholder.select_fix_category_ac_text.setText("Electritian");
			viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.wiring_icon));
		}
		if(FixList.get(position).equals("4"))
		{
			viewholder.select_fix_category_ac_text.setText("Painting");
			viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.painting_icon));
		}
		if(FixList.get(position).equals("5"))
		{
			viewholder.select_fix_category_ac_text.setText("Carpenter");
			viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.carpenter_icon));
		}
		if(FixList.get(position).equals("6"))
		{
			viewholder.select_fix_category_ac_text.setText("Locksmith");
			viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.locksmith_icon));
		}
		
		if(FixList.get(position).equals("7"))
		{
			viewholder.select_fix_category_ac_text.setText("Pest Control");
			viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.pest_control_icon));
		}
		if(FixList.get(position).equals("8"))
		{
			viewholder.select_fix_category_ac_text.setText("Sanitization");
			viewholder.select_fix_category_ac_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.sanitization_icon));
		}
		
		return convertView;
	}

	public class ViewHolder{
		private ImageView select_fix_category_ac_icon;
		private TextView select_fix_category_ac_text;
		
	}
}
