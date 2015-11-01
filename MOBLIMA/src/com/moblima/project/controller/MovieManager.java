package com.moblima.project.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;

import com.moblima.project.model.Model;
import com.moblima.project.model.Movie;

public class MovieManager extends Manager {
	private static final String file = "data/movie.json";
	
	private ArrayList<Movie> mMovies;
	
	public MovieManager() throws IOException, JSONException, ParseException {
		super();
	}
	
	public ArrayList<Movie> getMovies() {
		return mMovies;
	}

	public void setMovies(ArrayList<Movie> mMovies) {
		this.mMovies = mMovies;
	}

	@Override
	protected void load() throws IOException, JSONException, ParseException {
		JSONArray array = this.getData(file);
		mMovies = new ArrayList<>();
		idCounter = 0;
		
		
		for(int i=0;i<array.length();i++){
			Movie movie = new Movie(array.getJSONObject(i));
			mMovies.add(movie);
			idCounter = movie.getId();
		}
		
	}

	@Override
	public boolean create(Model instance) throws JSONException {
		if(((Movie)instance).getClass() != Movie.class)
			return false;
		
		instance.setId(this.idCounter + 1);
		this.idCounter++;
		mMovies.add((Movie)instance);
		jdata.put(instance.toJSONObject());
		
		this.writeFile(file);
		return true;
	}

	@Override
	public boolean update(Model instance) throws JSONException {
		if(((Movie)instance).getClass() != Movie.class)
			return false;
		if(instance.getId() > this.idCounter)
			return false;
		
		Movie movie;
		
		for(int i=0;i<this.mMovies.size();i++){
			movie = this.mMovies.get(i);
			if(movie.getId() == instance.getId()){
				this.mMovies.set(i, (Movie) instance);
				jdata.put(i,instance.toJSONObject());
				this.writeFile(file);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean delete(Model instance) {
		if(((Movie)instance).getClass() != Movie.class)
			return false;
		if(instance.getId() > this.idCounter)
			return false;
		
		Movie movie;
		
		for(int i=0;i<this.mMovies.size();i++){
			movie = this.mMovies.get(i);
			if(movie.getId() == instance.getId()){
				this.mMovies.remove(i);
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
		
		Movie movie;
		
		for(int i=0;i<this.mMovies.size();i++){
			movie = this.mMovies.get(i);
			if(movie.getId() == id){
				this.mMovies.remove(i);
				jdata.remove(i);
				this.writeFile(file);
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Model getInstanceById(int id) {
		for(Movie movie : mMovies){
			if(movie.getId() == id)
				return movie;
		}
		return null;
	}

	public ArrayList<Movie> getTopMovies() {
		ArrayList<Movie> movies = (ArrayList<Movie>) this.mMovies.clone();
		
		Collections.sort(movies, new Comparator<Movie>(){

			@Override
			public int compare(Movie o1, Movie o2) {
				if(o1.getOverallRating() > o2.getOverallRating())
					return -1;
				else if(o1.getOverallRating() < o2.getOverallRating())
					return 1;
				else
					return 0;
			}
			
		});
		
		return movies;
	}
}
