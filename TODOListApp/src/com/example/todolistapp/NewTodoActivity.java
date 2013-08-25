package com.example.todolistapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewTodoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_todo);
		
		//setting the timewidget to display 24 hrs
		TimePicker timeWidget = (TimePicker)findViewById(R.id.time);
		timeWidget.setIs24HourView(true);
		
	}

	public void AddNewToDoItem(View view){
		//Get Title
		EditText titleWidget = (EditText)findViewById(R.id.title);
		String title = titleWidget.getText().toString();
		
		//Get Description
		EditText descriptionWidget = (EditText)findViewById(R.id.description);
		String description = descriptionWidget.getText().toString();
		
		//Get Date
		DatePicker dateWidget = (DatePicker)findViewById(R.id.date);
		String date = new String();
		date = dateWidget.getYear() + "-" + dateWidget.getMonth() + "-" + dateWidget.getDayOfMonth();
		
		//Get Time
		TimePicker timeWidget = (TimePicker)findViewById(R.id.time);
		String time = new String();
		String hours, minutes = new String();
		hours = timeWidget.getCurrentHour().toString();
		minutes = timeWidget.getCurrentMinute().toString();
		if(timeWidget.getCurrentHour() < 10){
			hours = "0" + hours;
		}
		if(timeWidget.getCurrentMinute() < 10){
			minutes = "0" + minutes;
		}
		time = hours + ":" + minutes + ":00";
		
		//creating dboperation object
		DbOperations db = new DbOperations(view.getContext());
		db.openDatabase();
		db.createToDoItem(title, description, date, time);
		db.closeDatabase();
		Toast.makeText(NewTodoActivity.this, "ToDo Item Created", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_todo, menu);
		return true;
	}

}
