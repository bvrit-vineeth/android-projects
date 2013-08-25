package com.example.todolistapp;

public class DbObject {
	private long id;
	private String title;
	private String description;
	private String timeStamp;
	
	public long getId() {
	    return id;
	}
	
	public void setId(long id) {
	    this.id = id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setTimeStamp(String timeStamp){
		this.timeStamp = timeStamp;
	}
	
	public String getTimeStamp(){
		return this.timeStamp;
	}
}
