package com.moblima.project.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.model.Constant.ClassType;

public class Discount {
	
	public static JSONObject discounts;
	
	private static final String file = "data/discount.json";
	
	public static void startData() throws JSONException{
		discounts = new JSONObject();
		JSONObject age = new JSONObject();
		age.put("below12", 0);
		age.put("above60", 0);
		discounts.put("age",age);
		
		JSONObject movieType = new JSONObject();
		movieType.put(ClassType.NORMAL.name(), 0);
		movieType.put(ClassType.BLOCKBUSTER.name(), 0);
		movieType.put(ClassType.THREED.name(), 0);
		discounts.put("classType", movieType);
		
		JSONObject holiday = new JSONObject();
		holiday.put("weekends", 0);
		discounts.put("holiday", holiday);
		
		JSONObject cinemaClass = new JSONObject();
		cinemaClass.put("platinum", 0);
		discounts.put("cinemaClass", cinemaClass);
		
		while(!save());
	}
	
	public static void initialize() throws IOException, JSONException{
		
		String data = new String(Files.readAllBytes(Paths.get(file)));
		discounts = new JSONObject(data);
	}
	
	public static boolean save(){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(discounts.toString());
			bw.flush();
			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
