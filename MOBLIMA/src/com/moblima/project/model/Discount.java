package com.moblima.project.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class Discount {
	
	public static Map<String , Integer> ageDiscount;
	public static Map<String , Integer> cinemaClassDiscount;
	public static Map<String , Integer> movieTypeDiscount;
	public static Map<String , Integer> holidayDiscount;
	
	private static final String file = "data/discount.json";
	
	public static void initialize() throws IOException, JSONException{
		ageDiscount = new HashMap<>();
		cinemaClassDiscount = new HashMap<>();
		movieTypeDiscount = new HashMap<>();
		holidayDiscount = new HashMap<>();
		
		String data = new String(Files.readAllBytes(Paths.get(file)));
		
		JSONObject object = new JSONObject(data);
		JSONObject branch;
		
		if(object.has("age")){
			branch = object.getJSONObject("age");
			Iterator<String> iterator = branch.keys();
			
			while(iterator.hasNext()){
				String key = iterator.next();
				ageDiscount.put(key, new Integer(branch.getInt(key)));
			}
		}
		
		if(object.has("cinemaClass")){
			branch = object.getJSONObject("cinemaClass");
			Iterator<String> iterator = branch.keys();
			
			while(iterator.hasNext()){
				String key = iterator.next();
				ageDiscount.put(key, new Integer(branch.getInt(key)));
			}
		}
		
		if(object.has("movieType")){
			branch = object.getJSONObject("age");
			Iterator<String> iterator = branch.keys();
			
			while(iterator.hasNext()){
				String key = iterator.next();
				ageDiscount.put(key, new Integer(branch.getInt(key)));
			}
		}
	}
}
