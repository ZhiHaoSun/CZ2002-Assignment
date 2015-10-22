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
	
	public static ArrayList<Review> instances;
	public static int curMaxID;
	private static String file = "data/review.json";
	
	public Review(Rating rating, String description, int movieId) {
		super();
		this.id = Review.curMaxID + 1;
		this.rating = rating;
		this.date = new Date();
		this.description = description;
		this.movieId = movieId;
	}
	
	public Review(Rating rating, Date date, String description, int movieId) {
		super();
		this.id = Review.curMaxID + 1;
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
	
	public static void load() throws IOException, JSONException, ParseException{
		 instances = new ArrayList<>();
		 ArrayList<JSONObject> objects = readFile(file);
		 int len = 0;
		 
		 if(objects != null)
			 len = objects.size();
		 curMaxID = 0;
		 Review review;
		 
		 for(int i=0;i<len;i++){
			 review = new Review(objects.get(i));
			 instances.add(review);
			 
			 if(review.getId() > curMaxID)
				 curMaxID = review.getId();
		 }
	}
	 
	public static String getData() throws JSONException{
		if(Review.instances == null)
			return null;
		else{
			JSONArray array = new JSONArray();
			
			for(int i=0;i<Review.instances.size();i++){
				array.put(Review.instances.get(i).toJSONObject());
			}
			
			return array.toString();
		}
	}
	 
	public void save() throws IOException, JSONException{
		if(this.getId() > Review.curMaxID){
			this.create();
		}else{
			this.update();
		}
	}
	
	public void create() throws IOException, JSONException{
		Review.instances.add(this);
		Review.curMaxID = this.getId();
		
		Review.writeFile(file, Review.getData());
	}
	
	public void update() throws IOException, JSONException{
		Review review;
		int i;
		for(i=0;i<Review.instances.size();i++){
			review = Review.instances.get(i);
			if(review.getId() == this.getId())
				break;
		}
		
		Review.instances.set(i, this);
		Review.writeFile(file, Review.getData());
	}
	
	public void delete() throws IOException, JSONException{
		for(int i=0;i<Review.instances.size();i++){
			if(Review.instances.get(i).getId() == this.id){
				Review.instances.remove(i);
				break;
			}
		}
		Review.writeFile(file, Review.getData());
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public Movie getMovie(){
		for(Movie movie: Movie.instances){
			if(movie.getId() == this.movieId)
				return movie;
		}
		
		return null;
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
