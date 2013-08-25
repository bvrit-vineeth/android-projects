package com.example.todolistapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity{
	private DbOperations dbOperations = new DbOperations(this);
	private List<DbObject> allItems;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	    dbOperations.openDatabase();
		allItems = dbOperations.getAllToDoItems();
		ListView todolistView = (ListView)findViewById(R.id.ToDoListView);
		CustomArrayAdapter adapter = new CustomArrayAdapter(this, (ArrayList<DbObject>)allItems);
		todolistView.setAdapter(adapter);
		todolistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				String clickedItemTitle = allItems.get(position).getTitle();
				String clickedItemDescription = allItems.get(position).getDescription();
				long Itemid = allItems.get(position).getId();
				Intent showClickedItemData = new Intent(MainActivity.this,TodoListDescriptionActivity.class);
				showClickedItemData.putExtra("title", clickedItemTitle);
				showClickedItemData.putExtra("description", clickedItemDescription);
				showClickedItemData.putExtra("id", Itemid);
				startActivity(showClickedItemData);
				finish();
			}
			
		});
	}
	
	
	public void onClick(View view){
		Intent newIntent = new Intent(this, NewTodoActivity.class);
		startActivity(newIntent);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
