package com.moblima.project.model;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Review extends Model implements Comparable<Review> {
		
	private Date date;

	private int rating;
	private String name;
	private String description;
	
	public Review() {}
	
	public Review(int rating, String description, int movieId) {
		super();
		this.rating = rating;
		this.date = new Date();
		this.description = description;
	}
	
	public Review(int rating, Date date, String description, int movieId) {
		super();
		this.rating = rating;
		this.date = date;
		this.description = description;
	}
	
	public Review(JSONObject object) throws JSONException, ParseException{
		name 		= object.getString("name");
		description = object.getString("description");
		rating 		= object.getInt("rating");
		date 		= Constant.datetimeFormat.parse(object.getString("date posted"));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public String getPostedDate() {
		if (date == null) date = Calendar.getInstance().getTime();

		return Constant.datetimeFormat.format(date);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		
		object.put("name", name);
		object.put("rating", rating);
		object.put("description", description);
		object.put("date posted", getPostedDate());
		
		return object;
	}
	
	@Override
	public String toDisplay() {
		return getPostedDate() + " \t "+ name + "  " + description + "  "+ rating;
	}

	@Override
	public int compareTo(Review o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
