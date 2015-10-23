package com.moblima.project.model;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.model.Constant.ClassType;

public class Cinema extends Model{
	
	private int id;
	private String name;
	private ClassType classType;
	
	private ArrayList<ShowTime> showTimes;
	
	public Cinema(String name, ClassType classType){
		this.name = name;
		this.classType = classType;
		this.showTimes = new ArrayList<>();
	}

	public Cinema(String name, ClassType classType, ArrayList<ShowTime> showTimes) {
		super();
		this.name = name;
		this.classType = classType;
		this.showTimes = showTimes;
	}
	
	public Cinema(JSONObject object) throws JSONException, ParseException{
		this.id = object.getInt("id");
		this.name = object.getString("name");
		this.classType = ClassType.valueOf(object.getString("classType"));
		
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

	public ClassType getClassType() {
		return classType;
	}

	public void setClassType(ClassType classType) {
		this.classType = classType;
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
		object.put("classType",this.classType.toString());
		
		JSONArray array = new JSONArray();
		for(ShowTime showTime: this.showTimes){
			array.put(showTime.toJSONObject());
		}
		object.put("showTimes", array);
		return object;
	}

	@Override
	public String toDisplay() {
		return this.id + "  " + this.name  + "  " + this.classType.toString(); 
	}

}
