package com.vineeth.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent receivedIntent = getIntent();
        String message = receivedIntent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        
        TextView textView = new TextView(this);
        textView.setTextSize(25);
        textView.setText(message);

        // Set the text view as the activity layout
        setContentView(textView);
  
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
