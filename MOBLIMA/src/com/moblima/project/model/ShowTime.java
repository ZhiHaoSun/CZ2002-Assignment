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
	
	private static String file = "data/showTime.json";
	public static ArrayList<ShowTime> instances;
	
	public ShowTime(int movieId, int cinemaId, Date dateTime) {
		super();
		this.movieId = movieId;
		this.cinemaId = cinemaId;
		this.dateTime = dateTime;
	}

	public ShowTime(JSONObject object) throws JSONException, ParseException {
		this.movieId = object.getInt("movieId");
		this.cinemaId = object.getInt("cinemaId");
		
		this.dateTime = Constant.dateTimeFormat.parse(object.getString("dateTime"));
	}
	
	public static void load() throws IOException, JSONException, ParseException{
		 instances = new ArrayList<>();
		 ArrayList<JSONObject> objects = readFile(file);
		 int len = 0;
		 
		 if(objects != null)
			 len = objects.size();
		 
		 ShowTime showTime;
		 
		 for(int i=0;i<len;i++){
			 showTime = new ShowTime(objects.get(i));
		 }
	}
	 
	public static String getData() throws JSONException{
		if(ShowTime.instances == null)
			return null;
		else{
			JSONArray array = new JSONArray();
			
			for(int i=0;i<ShowTime.instances.size();i++){
				array.put(ShowTime.instances.get(i).toJSONObject());
			}
			
			return array.toString();
		}
	}
	 
	public void save() throws IOException, JSONException{
		this.create();
	}
	
	public void create() throws IOException, JSONException{
		ShowTime.instances.add(this);
		
		ShowTime.writeFile(file, Review.getData());
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

	public Movie getMovie(){
		for(Movie m: Movie.instances){
			if(m.getId() == this.movieId)
				return m;
		}
		
		return null;
	}
	
	public Cinema getCinema(){
		for(Cinema cinema: Cinema.instances){
			if(cinema.getId() == this.cinemaId)
				return cinema;
		}
		
		return null;
	}

	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("movieId", movieId);
		object.put("cinemaId",cinemaId);
		object.put("dateTime" , Constant.dateTimeFormat.format(dateTime));
		
		return object;
	}

	@Override
	public String toDisplay() {
		return this.getCinema().getName() + "  " + this.getMovie().getTitle() + "  " + this.getDateTimeStr();
	}

}
