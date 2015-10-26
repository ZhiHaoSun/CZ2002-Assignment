package com.moblima.project.model;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowTime extends Model {
	
	private int movieId;
	private int cinemaId;
	
	private Date dateTime;
	
	public ShowTime(int movieId, int cinemaId, Date dateTime) {
		super();
		this.movieId = movieId;
		this.cinemaId = cinemaId;
		this.dateTime = dateTime;
	}

	public ShowTime(JSONObject object) throws JSONException, ParseException {
		this.id = object.getInt("id");
		this.movieId = object.getInt("movieId");
		this.cinemaId = object.getInt("cinemaId");
		
		this.dateTime = Constant.dateTimeFormat.parse(object.getString("dateTime"));
	}
	
	public ShowTime() {}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public int getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(int cinemaId) {
		this.cinemaId = cinemaId;
	}

	public Date getDateTime() {
		return dateTime;
	}
	
	public String getDateTimeStr() {
		return Constant.dateTimeFormat.format(dateTime);
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	public void setDateTime(String time) throws ParseException{
		this.dateTime = Constant.dateTimeFormat.parse(time);
	}

	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("id",id);
		object.put("movieId", movieId);
		object.put("cinemaId",cinemaId);
		object.put("dateTime" , Constant.dateTimeFormat.format(dateTime));
		
		return object;
	}

	@Override
	public String toDisplay() {
		return "";
	}

	public boolean equals(ShowTime time) {
		if(this.id == time.getId() && this.movieId == time.getMovieId() && this.cinemaId == time.getCinemaId())
			return true;
		else
			return false;
	}

}