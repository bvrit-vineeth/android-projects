package com.example.todolistapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DbOperations {
	private SQLiteDatabase database;
	private MySQLiteHelper dbhelper;
	private String[] table_columns = new String[]{MySQLiteHelper.COLUMN_ID, 
			MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_DESCRIPTION, MySQLiteHelper.COLUMN_TIME};
	
	public DbOperations(Context context){
		dbhelper = new MySQLiteHelper(context);
	}
	
	public void openDatabase() throws SQLException{
		database = dbhelper.getWritableDatabase();
	}
	
	public void closeDatabase(){
		dbhelper.close();
	}
	
	public DbObject createToDoItem(String title, String description, String date, String time){
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_TITLE, title);
		values.put(MySQLiteHelper.COLUMN_DESCRIPTION, description);
		values.put(MySQLiteHelper.COLUMN_TIME, date + " " + time);
		long insertId = database.insert(MySQLiteHelper.TABLE_NAME, null, values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME, table_columns,  MySQLiteHelper.COLUMN_ID + " = " + insertId , null, null, null, null);
		cursor.moveToFirst();
		DbObject newData = cursorToDbObject(cursor);
		cursor.close();
		return newData;
	}
	
	public void deleteToDoItem(long deletableItemID){
		database.delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID + "=" + deletableItemID, null);
	}
	
	public List<DbObject> getAllToDoItems() {
	    List<DbObject> listItems = new ArrayList<DbObject>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME,
	        table_columns, null, null, null, null, null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      DbObject item = cursorToDbObject(cursor);
	      listItems.add(item);
	      cursor.moveToNext();
	    }
	    cursor.close();
	    return listItems;
	}
	
	private DbObject cursorToDbObject(Cursor cursor){
		DbObject newDb = new DbObject();
		newDb.setId(cursor.getLong(0));
		newDb.setTitle(cursor.getString(1));
		newDb.setDescription(cursor.getString(2));
		newDb.setTimeStamp(cursor.getString(3));
		return newDb;
	}
}
