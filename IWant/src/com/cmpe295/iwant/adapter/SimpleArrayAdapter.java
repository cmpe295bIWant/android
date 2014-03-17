package com.cmpe295.iwant.adapter;

import com.cmpe295.iwant.CategoryActivity;
import com.cmpe295.iwant.ComputersActivity;
import com.cmpe295.iwant.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
 
public class SimpleArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	ImageButton imageViewDetails;
	View rowView;
 
	public SimpleArrayAdapter(Context context, String[] values) {
		super(context, R.layout.computers, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		rowView = inflater.inflate(R.layout.computers, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);
		
		// Change icon based on name
		String s = values[position];
 
		System.out.println(s);
 
		if (s.equals("WindowsMobile")) {
			imageView.setImageResource(R.drawable.computers);
		} else if (s.equals("iOS")) {
			imageView.setImageResource(R.drawable.computers);
		} else if (s.equals("Blackberry")) {
			imageView.setImageResource(R.drawable.computers);
		} else {
			imageView.setImageResource(R.drawable.computers);
		}
 
		return rowView;
	}
	

}