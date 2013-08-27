package com.vineeth.networkingtest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DownloadWebPage extends Activity {
	private TextView replyData;
	private EditText urlField;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_web_page);
		urlField = (EditText)findViewById(R.id.url_field);
		replyData = (TextView)findViewById(R.id.reply_data);
	}
	
	public void fetchWebPage(View view){
		ConnectivityManager conn = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = conn.getActiveNetworkInfo();
		String url = urlField.getText().toString();
		if(info!=null && info.isConnected()){
			new showWebPageData().execute(url); 
		}
		else{
			replyData.setText("Network Access Not available");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.download_web_page, menu);
		return true;
	}
	
	private class showWebPageData extends AsyncTask<String, Void, String>{
		@Override
		protected String doInBackground(String... params) {
			try{
				//call function to send HTTP request
				return downloadWebPage(params[0]);
			}
			catch(IOException e){
				return "Error Retrieving Webpage";
			}
		} 
		
		@Override
		protected void onPostExecute(String result) {
			replyData.setText(result);
		}
		
		private String downloadWebPage(String inputurl) throws IOException{
			InputStream result = null;
			try{
				URL url = new URL(inputurl);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setReadTimeout(10000);
		        conn.setConnectTimeout(15000);
		        conn.setRequestMethod("GET");
		        conn.setDoInput(true);
		        conn.connect();
		        result = conn.getInputStream();
		        return convertInputStreamToString(result);
			}
			finally{
				if(result != null){
					result.close();
				}
			}
		}
		
		public String convertInputStreamToString(InputStream input) throws IOException, UnsupportedEncodingException{
			char[] buffer = new char[512];
			String returnString = new String();
			returnString = "";
			Reader reader = null;
			reader = new InputStreamReader(input, "UTF-8");
			while(reader.read(buffer)!=-1){
				returnString += new String(buffer);
			}
			return returnString;
		}
	}

}


