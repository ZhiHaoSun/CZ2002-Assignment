package com.moblima.project.model;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.model.Constant.Cineplex;
import com.moblima.project.model.Constant.ClassType;

public class Cinema extends Model{
	
	private int id;
	private String name;
	private Cineplex cineplex;
	
	private ArrayList<ShowTime> showTimes;
	
	public Cinema(){
		showTimes = new ArrayList<>();
	}
	
	public Cinema(String name, Cineplex cineplex){
		this.name = name;
		this.cineplex = cineplex;
		this.showTimes = new ArrayList<>();
	}

	public Cinema(String name, Cineplex cineplex, ArrayList<ShowTime> showTimes) {
		super();
		this.name = name;
		this.cineplex = cineplex;
		this.showTimes = showTimes;
	}
	
	public Cinema(JSONObject object) throws JSONException, ParseException{
		this.id = object.getInt("id");
		this.name = object.getString("name");
		this.cineplex = Cineplex.valueOf(object.getString("cineplex"));
		
		ArrayList<ShowTime> shows = new ArrayList<>();
		JSONArray array = object.getJSONArray("showTimes");
		
		int len = array.length();
		for(int i=0;i<len;i++){
			shows.add(new ShowTime((JSONObject) array.get(i)));
		}
		
		this.showTimes = shows;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Cineplex getCineplex() {
		return cineplex;
	}

	public void setCineplex(Cineplex cineplex) {
		this.cineplex = cineplex;
	}

	public ArrayList<ShowTime> getShowTimes() {
		return showTimes;
	}

	public void setShowTimes(ArrayList<ShowTime> showTimes) {
		this.showTimes = showTimes;
	}
	
	public void addShowTime(ShowTime showTime){
		this.showTimes.add(showTime);
	}

	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("id", this.id);
		object.put("name",this.name);
		object.put("cineplex",this.cineplex.toString());
		
		JSONArray array = new JSONArray();
		for(ShowTime showTime: this.showTimes){
			array.put(showTime.toJSONObject());
		}
		object.put("showTimes", array);
		return object;
	}

	@Override
	public String toDisplay() {
		return this.id + "  " + this.name  + "  " + this.cineplex.toString(); 
	}

}
