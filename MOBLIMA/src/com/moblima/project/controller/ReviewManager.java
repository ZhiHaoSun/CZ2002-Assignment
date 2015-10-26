package com.moblima.project.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.moblima.project.model.Model;
import com.moblima.project.model.Movie;
import com.moblima.project.model.Review;

public class ReviewManager extends Manager {
private static final String file = "data/review.json";
	
	private ArrayList<Review> mReviews;

	public ReviewManager() throws IOException, JSONException, ParseException {
		super();
	}
	
	public ArrayList<Review> getReviews() {
		return mReviews;
	}


	public void setmReviews(ArrayList<Review> mReviews) {
		this.mReviews = mReviews;
	}

	@Override
	protected void load() throws IOException, JSONException, ParseException {
		JSONArray array = this.getData(file);
		mReviews = new ArrayList<>();
		idCounter = 0;
		Review review;
		
		for(int i=0;i<array.length();i++){
			review = new Review(array.getJSONObject(i));
			mReviews.add(review);
			idCounter = review.getId();
		}
	}

	@Override
	public boolean create(Model instance) throws JSONException {
		if(((Review)instance).getClass() != Review.class)
			return false;
		
		instance.setId(this.idCounter + 1);
		this.idCounter++;
		mReviews.add((Review)instance);
		jdata.put(instance.toJSONObject());
		
		this.writeFile(file);
		return true;
	}

	@Override
	public boolean update(Model instance) throws JSONException {
		if(((Review)instance).getClass() != Review.class)
			return false;
		if(instance.getId() > this.idCounter)
			return false;
		
		Review review;
		
		for(int i=0;i<this.mReviews.size();i++){
			review = this.mReviews.get(i);
			if(review.getId() == instance.getId()){
				this.mReviews.set(i, (Review) instance);
				jdata.put(i,instance.toJSONObject());
				this.writeFile(file);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean delete(Model instance) {
		if(((Review)instance).getClass() != Review.class)
			return false;
		if(instance.getId() > this.idCounter)
			return false;
		
		Review review;
		
		for(int i=0;i<this.mReviews.size();i++){
			review = this.mReviews.get(i);
			if(review.getId() == instance.getId()){
				this.mReviews.remove(i);
				jdata.remove(i);
				this.writeFile(file);
				
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean deleteById(int id) throws JSONException {
		if(this.idCounter < id)
			return false;
		
		Review review;
		
		for(int i=0;i<this.mReviews.size();i++){
			review = this.mReviews.get(i);
			if(review.getId() == id){
				this.mReviews.remove(i);
				jdata.remove(i);
				this.writeFile(file);
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Model getInstanceById(int id) {
		for(Review review : mReviews){
			if(review.getId() == id)
				return review;
		}
		return null;
	}
	
	public ArrayList<Review> getMovieReviews(Movie movie){
		ArrayList<Review> reviews = new ArrayList<>();
		
		for(Review review : mReviews){
			if(review.getMovieId() == movie.getId())
				reviews.add(review);
		}
		return reviews;
	}
}