package com.example.todolistapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomArrayAdapter extends ArrayAdapter<DbObject>{
	private final Context context;
	private final List<DbObject> values;
	
	public CustomArrayAdapter(Context context, ArrayList<DbObject> values){
		super(context, R.layout.list_row, values);
		this.context = context;
		this.values = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View listRowView = inflater.inflate(R.layout.list_row, parent, false);
		TextView title = (TextView)listRowView.findViewById(R.id.list_row_title);
		TextView date = (TextView)listRowView.findViewById(R.id.list_row_date);
		TextView time = (TextView)listRowView.findViewById(R.id.list_row_time);
		String [] splitTimeStamp = values.get(position).getTimeStamp().split(" ");
		title.setText(values.get(position).getTitle());
		date.setText(splitTimeStamp[0]);
		time.setText(splitTimeStamp[1]);
		return listRowView;
	}
}
