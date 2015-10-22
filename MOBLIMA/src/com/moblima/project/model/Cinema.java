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
	
	public static ArrayList<Cinema> instances;
	public static int curMaxID;
	private static String file = "data/cinema.json";
	
	public Cinema(String name, ClassType classType){
		this.id = Cinema.curMaxID+1;
		this.name = name;
		this.classType = classType;
		this.showTimes = new ArrayList<>();
	}

	public Cinema(String name, ClassType classType, ArrayList<ShowTime> showTimes) {
		super();
		this.id = Cinema.curMaxID + 1;
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
	
	public static void load() throws IOException, JSONException, ParseException{
		 instances = new ArrayList<>();
		 ArrayList<JSONObject> objects = readFile(file);
		 int len = 0;
			
		 if(objects != null)
			 len = objects.size();
		 
		 curMaxID = 0;
		 Cinema cinema;
		 
		 for(int i=0;i<len;i++){
			 cinema = new Cinema(objects.get(i));
			 instances.add(cinema);
			 
			 if(cinema.getId() > curMaxID)
				 curMaxID = cinema.getId();
		 }
	}
	 
	public static String getData() throws JSONException{
		if(Cinema.instances == null)
			return null;
		else{
			JSONArray array = new JSONArray();
			
			for(int i=0;i<Cinema.instances.size();i++){
				array.put(Cinema.instances.get(i).toJSONObject());
			}
			
			return array.toString();
		}
	}
	 
	public void save() throws IOException, JSONException{
		if(this.getId() > Cinema.curMaxID){
			this.create();
		}else{
			this.update();
		}
	}
	
	public void create() throws IOException, JSONException{
		Cinema.instances.add(this);
		Cinema.curMaxID = this.getId();
		
		Cinema.writeFile(file, Cinema.getData());
	}
	
	public void update() throws IOException, JSONException{
		Cinema cinema;
		int i;
		for(i=0;i<Cinema.instances.size();i++){
			cinema = Cinema.instances.get(i);
			if(cinema.getId() == this.getId())
				break;
		}
		
		Cinema.instances.set(i, this);
		Cinema.writeFile(file, Cinema.getData());
	}
	
	public void delete() throws IOException, JSONException{
		for(int i=0;i<Cinema.instances.size();i++){
			if(Cinema.instances.get(i).getId() == this.id){
				Cinema.instances.remove(i);
				break;
			}
		}
		Cinema.writeFile(file, Cinema.getData());
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
