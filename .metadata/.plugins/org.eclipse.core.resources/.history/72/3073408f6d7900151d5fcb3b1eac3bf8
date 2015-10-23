package com.moblima.project.model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.model.Constant.Rating;

public class Review extends Model {
	
	private int id;
	private Rating rating;
	private Date date;
	private String description;
	private int movieId;
	
	public Review(Rating rating, String description, int movieId) {
		super();
		this.rating = rating;
		this.date = new Date();
		this.description = description;
		this.movieId = movieId;
	}
	
	public Review(Rating rating, Date date, String description, int movieId) {
		super();
		this.rating = rating;
		this.date = date;
		this.description = description;
		this.movieId = movieId;
	}
	
	public Review(JSONObject object) throws JSONException, ParseException{
		this.id = object.getInt("id");
		this.description = object.getString("description");
		this.rating = Rating.valueOf(object.getString("rating"));
		this.date = Constant.dateFormat.parse(object.getString("date"));
		this.movieId = object.getInt("movieId");
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public Date getDate() {
		return date;
	}
	
	public String getDateStr() {
		return Constant.dateFormat.format(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("id", id);
		object.put("description", description);
		object.put("rating", rating.toString());
		object.put("date", Constant.dateFormat.format(date));
		object.put("movieId", movieId);
		
		return object;
	}

	@Override
	public String toDisplay() {
		return id + "  " + rating.toString() + "  " + description + "  " + date;
	}

}
