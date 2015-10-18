package com.moblima.project.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import com.moblima.project.model.Movie;

public class MovieManager extends Manager {
	private static final String JSON_FILE_PATH = "data/movie.json";
	
	private int mCounter; 
	private ArrayList<Movie> mMovies;
	
	public MovieManager() {
		getMovieListing();
	}
	public String generateMovieID() throws JSONException {
		mCounter = jdata.getInt("counter");
		jdata.put("counter", ++mCounter);
		return "M" + String.format("%05d", mCounter);
	}
	
	public boolean create(Movie movie) { 
		try {
			movie.setID(generateMovieID());
			jitems.put(movie.toJSONObject());
			
			// update the json file
			if (writeFile(JSON_FILE_PATH)) {
				mMovies.add(movie);
				return true;
			} else {
				jitems.remove(jitems.length()-1);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return false; 	
	}
	
	public Movie getMovieDetails(String movieID) {
		for (Movie movie:mMovies) 
			if (movie.match(movieID))
				return movie;
				
		return null;
	}
	
	public boolean update(Movie movie) { 
		try {
			String id;
			for (int i=0; i<jitems.length(); i++) {
				id = jitems.getJSONObject(i).getString("id");
				
				if (movie.match(id)) {
					// remove the current movie
					jitems.remove(i);
					
					// add the updated movie
					jitems.put(movie.toJSONObject());
					
					// write to the json file
					if (writeFile(JSON_FILE_PATH)) 
						return true;
					
					break;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false; 
	}
	
	public boolean remove(Movie movie) { 
		try {
			String id;
			for (int i=0; i<jitems.length(); i++) {
				id = jitems.getJSONObject(i).getString("id");
				
				if (movie.match(id)) {
					jitems.remove(i);
					
					if (writeFile(JSON_FILE_PATH)) {
						mMovies.remove(i);
						return true;
					}
					break;
				}
			}
			
			// update the json file
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Movie> getMovieListing() {
		mMovies = new ArrayList<>();

		try {
			jitems = getData(JSON_FILE_PATH, "movies");
			
			for (int i=0; i<jitems.length(); i++) {
				jitem = jitems.getJSONObject(i);
				mMovies.add(new Movie(jitem));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mMovies;
	}
}
