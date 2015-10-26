package com.moblima.project.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.moblima.project.model.Cinema;
import com.moblima.project.model.Model;

public class CinemaManager extends Manager {
	private static final String file = "data/cinema.json";
	
	private ArrayList<Cinema> mCinemas;
	
	public CinemaManager() throws IOException, JSONException, ParseException {
		super();
	}
	
	@Override
	protected void load() throws IOException, JSONException, ParseException {
		JSONArray array = this.getData(file);
		mCinemas = new ArrayList<>();
		idCounter = 0;
		Cinema cinema;
		
		for(int i=0;i<array.length();i++){
			cinema = new Cinema(array.getJSONObject(i));
			mCinemas.add(cinema);
			idCounter = cinema.getId();
		}
	}

	public ArrayList<Cinema> getmCinemas() {
		return mCinemas;
	}

	public void setmCinemas(ArrayList<Cinema> mCinemas) {
		this.mCinemas = mCinemas;
	}

	@Override
	public boolean create(Model instance) throws JSONException {
		if(((Cinema)instance).getClass() != Cinema.class)
			return false;
		
		instance.setId(this.idCounter + 1);
		this.idCounter++;
		mCinemas.add((Cinema)instance);
		jdata.put(instance.toJSONObject());
		
		this.writeFile(file);
		return true;
	}

	@Override
	public boolean update(Model instance) throws JSONException {
		if(((Cinema)instance).getClass() != Cinema.class)
			return false;
		if(instance.getId() > this.idCounter)
			return false;
		
		Cinema cinema;
		
		for(int i=0;i<this.mCinemas.size();i++){
			cinema = this.mCinemas.get(i);
			if(cinema.getId() == instance.getId()){
				this.mCinemas.set(i, (Cinema) instance);
				jdata.put(i,instance.toJSONObject());
				this.writeFile(file);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean delete(Model instance) {
		if(((Cinema)instance).getClass() != Cinema.class)
			return false;
		if(instance.getId() > this.idCounter)
			return false;
		
		Cinema cinema;
		
		for(int i=0;i<this.mCinemas.size();i++){
			cinema = this.mCinemas.get(i);
			if(cinema.getId() == instance.getId()){
				this.mCinemas.remove(i);
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
		
		Cinema cinema;
		
		for(int i=0;i<this.mCinemas.size();i++){
			cinema = this.mCinemas.get(i);
			if(cinema.getId() == id){
				this.mCinemas.remove(i);
				jdata.remove(i);
				this.writeFile(file);
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Model getInstanceById(int id) {
		for(Cinema cinema : mCinemas){
			if(cinema.getId() == id)
				return cinema;
		}
		return null;
	}
}