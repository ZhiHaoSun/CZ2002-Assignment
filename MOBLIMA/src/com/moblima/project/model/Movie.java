package com.moblima.project.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
	private String id;
	private String title;
	private String synopsis;
	
	private String opening;
	private String runtime;
	
	private String director;
	private ArrayList<String> casts;
	
	private Status   status;
	private Rating   rating;
	private Language language;
	private int overallRating; // Overall Reviewer's Rating
	
	// For opening date
	private SimpleDateFormat sdf, sdp;

	public Movie() {
		casts = new ArrayList<>();
		sdf   = new SimpleDateFormat("dd MMM yyyy");
		sdp   = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	public Movie(JSONObject jObj) throws JSONException {
		casts = new ArrayList<>();

		id 		 = jObj.getString("id");
		title 	 = jObj.getString("title");
		synopsis = jObj.getString("synopsis");
		director = jObj.getString("director");
		
		status 	 = Status.valueOf(jObj.getString("status").toUpperCase());
		rating 	 = Rating.valueOf(jObj.getString("rating"));
		language = Language.valueOf(jObj.getString("language"));
		
		JSONArray jcasts = jObj.getJSONArray("casts");
		
		for (int i=0; i<jcasts.length(); i++)
			casts.add(jcasts.getString(i));
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public ArrayList<String> getCasts() {
		return casts;
	}
	
	public void addCast(String cast) {
		casts.add(cast);
	}
	
	public void removeCast(String cast) {
		casts.remove(cast);
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public int getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(int overallRating) {
		this.overallRating = overallRating;
	}

	public String getOpening() {
		return opening;
	}

	public void setOpening(String opening) throws ParseException {
		if (opening.equals("TBA")) this.opening = opening;
		else {
			Date date = sdp.parse(opening);
			this.opening = sdf.format(date);
		}
	}

	public String getRunTime() {
		return runtime;
	}

	public void setRunTime(String runtime) {
		this.runtime = runtime;
	}
	
	public JSONObject toJSONObject() throws JSONException {
		JSONObject jobj = new JSONObject();
		
		jobj.put("id", id);
		jobj.put("title", title);
		jobj.put("synopsis", synopsis);
		
		jobj.put("casts", casts);
		jobj.put("director", director);
		
		jobj.put("runtime", runtime);
		jobj.put("opening", opening);
	
		jobj.put("status", status.name());
		jobj.put("rating", rating.name());
		jobj.put("language", language.name());
		
		jobj.put("overall rating", 0);
		
		return jobj;
	}	
	
	/*
	 * 
	 * */

	public enum Status {
		COMING_SOON("Coming Soon"), 
		PREVIEW("Preview"), 
		NOW_SHOWING("Now Showing");
		
		private String value;
		Status(String value) { this.value = value; }
		public String value() { return value; }
	}
	
	public enum Language {
		ENGLISH("English"), 
		ENGLISH_WITH_CHINESE_SUBTITLES("English with Chinese subtitles"), 
		ENGLISH_WITH_NO_SUBTITLES("English with no subtitles"), 
		MANDARIN("Mandarin"), 
		MALAY("Malay"), 
		TAMIL("Tamil"), 
		HINDI("Hindi"), 
		JAPANESE("Japanese"), 
		THAI("Thai");
		
		private String value;
		Language(String value) { this.value = value; }
		public String value() { return value; }
	}
	
	public enum Rating {
		G, PG, PG13, NC16, M18, R21
	}
}
