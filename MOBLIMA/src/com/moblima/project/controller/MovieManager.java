package com.moblima.project.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.model.Movie;

public class MovieManager extends Manager {
	private static final String JSON_FILE_PATH = "data/movie.json";
	
	private ArrayList<Movie> mMovies;
	
	public boolean create(Movie movie) { 
		
		return false; 	
	}
	public boolean update(Movie movie) { return false; }
	public boolean remove(Movie movie) { return false; }
	
	public ArrayList<Movie> retrieveMovieListing() {
		mMovies = new ArrayList<>();

		try {
			String data = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
			jdata  = new JSONObject(data);
			jitems = jdata.getJSONArray("movies");

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
