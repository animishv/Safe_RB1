package com.example.retailbout;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SliderAdapter extends BaseAdapter {

	Context context;
	 List<Slidermodel> Slidermodel;

	 SliderAdapter(Context context, List<Slidermodel> Slidermodel) {
	  this.context = context;
	  this.Slidermodel = Slidermodel;
	 }


	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {

	if (convertView == null) {
	            LayoutInflater mInflater = (LayoutInflater) context
	                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	            convertView = mInflater.inflate(R.layout.slide_item, null);
	        }

	        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
	        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

	        Slidermodel row_pos = Slidermodel.get(position);
	        // setting the image resource and title
	        imgIcon.setImageResource(row_pos.getIcon());
	        txtTitle.setText(row_pos.getTitle());

	        return convertView;
	 }

	 @Override
	 public int getCount() {
	  return Slidermodel.size();
	 }

	 @Override
	 public Object getItem(int position) {
	  return Slidermodel.get(position);
	 }

	 @Override
	 public long getItemId(int position) {
	  return Slidermodel.indexOf(getItem(position));
	 }

}
