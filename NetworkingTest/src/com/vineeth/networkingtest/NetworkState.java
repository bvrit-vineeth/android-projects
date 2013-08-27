package com.vineeth.networkingtest;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class NetworkState extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_network_state);
		
		TextView text = (TextView)findViewById(R.id.network_state);
		text.setText(checkNetworkStatus());
	}
	
	public String checkNetworkStatus(){
		ConnectivityManager conn = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = conn.getActiveNetworkInfo();
		if(info!=null && info.isConnected()){
			return "Network Access Available";
		}
		else{
			return "Network Access Not available";
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.network_state, menu);
		return true;
	}

}
