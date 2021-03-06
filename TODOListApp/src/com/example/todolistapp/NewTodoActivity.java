package com.example.todolistapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewTodoActivity extends Activity {

	public String KEY_SUCCESS = "Success";
	public String KEY_ERROR = "Error";
	private String fetch_url = "http://sampletodolist.herokuapp.com/addTodoListitem/";
	private String toast_msg;
	public String TITLE_IDENTIFIER = "title";
	public String DESCRIPTION_IDENTIFIER = "description";
	public String DATE_IDENTIFIER = "date";
	public String STATUS_IDENTIFIER = "status";
	private String title, description, date;
	//private boolean status;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_todo);
		
		//setting the timewidget to display 24 hrs
		TimePicker timeWidget = (TimePicker)findViewById(R.id.time);
		timeWidget.setIs24HourView(true);
		
	}

	public void AddNewToDoItem(View view) throws UnsupportedEncodingException{
		//Get Title
		EditText titleWidget = (EditText)findViewById(R.id.title);
		title = titleWidget.getText().toString();
		
		//Get Description
		EditText descriptionWidget = (EditText)findViewById(R.id.description);
		description = descriptionWidget.getText().toString();
		
		//Get Date
		DatePicker dateWidget = (DatePicker)findViewById(R.id.date);
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
		date += ' ' + time;
		String postData = "title=" + URLEncoder.encode(title, "UTF-8") + "&description=" +
							URLEncoder.encode(description, "UTF-8") + "&date=" +
							URLEncoder.encode(date, "UTF-8");
		AddItemRestCall addobj = new AddItemRestCall();
		addobj.execute(fetch_url, postData);
		this.finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_todo, menu);
		return true;
	}
	
	private class AddItemRestCall extends AsyncTask<String, Void, JSONObject>{
		@Override
		protected JSONObject doInBackground(String... params) {
			try{
				//call function to send HTTP request
				return downloadWebPage(params[0], params[1]);
			}
			catch(IOException e){
				return null;
			}
		} 
		
		@Override
		protected void onPostExecute(JSONObject result) {
			if(result!=null){
				parseJSON(result);
				/*Intent resultIntent = new Intent();
				resultIntent.putExtra(TITLE_IDENTIFIER, title);
				resultIntent.putExtra(DESCRIPTION_IDENTIFIER, description);
				resultIntent.putExtra(DATE_IDENTIFIER, date);
				resultIntent.putExtra(STATUS_IDENTIFIER, status);
				setResult(RESULT_OK, resultIntent);*/
				Toast.makeText(NewTodoActivity.this, toast_msg, Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(NewTodoActivity.this, toast_msg, Toast.LENGTH_LONG).show();
			}
			
		}
		
		private JSONObject downloadWebPage(String inputurl, String urlEncodedData) throws IOException{
			InputStream result = null;
			try{
				URL url = new URL(inputurl);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setReadTimeout(30000);
		        conn.setConnectTimeout(30000);
		        conn.setRequestMethod("POST");
		        conn.setDoInput(true);
		        
		        //Set post data
		        conn.setDoOutput(true);
		        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		        wr.writeBytes(urlEncodedData);
		        wr.flush();
		        wr.close();
		        
		        conn.connect();
		        result = conn.getInputStream();
		        return convertInputStreamToJSON(result);
			}
			catch(Exception e){
				toast_msg = "Network Error Occured";
				return null;
			}
			finally{
				if(result != null){
					result.close();
				}
			}
		}
		
		public JSONObject convertInputStreamToJSON(InputStream input) throws IOException, UnsupportedEncodingException{
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuilder sb = new StringBuilder();
			String line = null;
			JSONObject jsonFormat = null;
			while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
			input.close();
			String jsonString = sb.toString();
			try {
				jsonFormat = new JSONObject(jsonString);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return jsonFormat;
		}
		
		public void parseJSON(JSONObject input){
			try {
				String msg = input.getString(KEY_SUCCESS);
				if(msg!=null){
					toast_msg = msg;
					//status = true;
				}
				else{
					toast_msg = input.getString(KEY_ERROR);
					//status = false;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}
