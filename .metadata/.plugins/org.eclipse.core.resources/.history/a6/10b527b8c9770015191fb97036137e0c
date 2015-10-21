package com.moblima.project.model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.model.Constant.*;

public class Movie extends Model{
	private int id;
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
	private static String file = "data/movie.json";

	public static ArrayList<Movie> instances;
	public static int curMaxID;

	public Movie() {
		id = Movie.curMaxID + 1;
		casts = new ArrayList<>();
		sdf   = new SimpleDateFormat("dd MMM yyyy");
		sdp   = new SimpleDateFormat("dd/MM/yyyy");
		status = Status.valueOf("NONE");
		rating = Rating.valueOf("NO");
		language = Language.valueOf("ENGLISH");
	}
	
	public Movie(JSONObject jObj) throws JSONException {
		casts = new ArrayList<>();

		id 		 = jObj.getInt("id");
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
	
	 public static void load() throws IOException, JSONException{
		 instances = new ArrayList<>();
		 ArrayList<JSONObject> objects = readFile(file);
		 
		 int len = objects.size();
		 curMaxID = 0;
		 Movie movie;
		 
		 for(int i=0;i<len;i++){
			 movie = new Movie(objects.get(i));
			 instances.add(movie);
			 
			 if(movie.getId() > curMaxID)
				 curMaxID = movie.getId();
		 }
	}
	 
	public static String getData() throws JSONException{
		if(Movie.instances == null)
			return null;
		else{
			JSONArray array = new JSONArray();
			
			for(int i=0;i<Movie.instances.size();i++){
				array.put(Movie.instances.get(i).toJSONObject());
			}
			
			return array.toString();
		}
	}
	 
	public void save() throws IOException, JSONException{
		if(this.getId() > Movie.curMaxID){
			this.create();
		}else{
			this.update();
		}
	}
	
	public void create() throws IOException, JSONException{
		Movie.instances.add(this);
		Movie.curMaxID = this.getId();
		
		Movie.writeFile(file, Movie.getData());
	}
	
	public void update() throws IOException, JSONException{
		Movie movie;
		int i;
		for(i=0;i<Movie.instances.size();i++){
			movie = Movie.instances.get(i);
			if(movie.getId() == this.getId())
				break;
		}
		
		Movie.instances.set(i, this);
		Movie.writeFile(file, Movie.getData());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
	
	public void setCasts(String casts) {
		String[] names = casts.split(",");
		
		this.casts.clear();
		
		for (String name: names)
			addCast(name.trim());
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
	
	public boolean match(String id) {
		return id.equals(this.id);
	}
	
	@Override
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

	@Override
	public String toDisplay() {
		return this.id + "  " + this.title + "  " + this.opening + "  " +this.runtime + "  " + this.director;
	}	
}
