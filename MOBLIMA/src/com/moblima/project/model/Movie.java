package com.moblima.project.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
	private String id;
	private String title;
	private String synopsis;
	private String director;
	private ArrayList<String> casts;
	
	private Status   status;
	private Rating   rating;
	private Language language;
	private int overallRating; // Overall Reviewer's Rating

	public Movie() {
		casts = new ArrayList<>();
	}
	
	public Movie(JSONObject jObj) throws JSONException {
		casts = new ArrayList<>();

		id 		 = jObj.getString("id");
		title 	 = jObj.getString("title");
		synopsis = jObj.getString("synopsis");
		director = jObj.getString("director");
		
		status 	 = Status.valueOf(jObj.getString("status"));
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

	
	/*
	 * 
	 * */
	public enum Status {
		Coming_Soon, Preview, Now_Showing
	}
	
	public enum Language {
		English, English_with_No_Subtitles, Mandarin, Malay, Tamil, Hindi, Japanese, Thai
	}
	
	public enum Rating {
		G, PG, PG13, NC16, M18, R21
	}
}
