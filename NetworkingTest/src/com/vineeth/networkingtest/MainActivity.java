package com.vineeth.networkingtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	Intent newIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void invokeNetworkStatus(View view){
		newIntent = new Intent(this, NetworkState.class);
		startActivity(newIntent);
	}
	
	public void invokeDownloadWebPage(View view){
		newIntent = new Intent(this, DownloadWebPage.class);
		startActivity(newIntent);
	}
}
