package com.moblima.project.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Model {
	public abstract JSONObject toJSONObject() throws JSONException;
	
	public static ArrayList<JSONObject> readFile(String file) throws IOException, JSONException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		StringBuilder builder = new StringBuilder();
		while((line = reader.readLine()) != null){
			builder.append(line);
		}
		reader.close();
		return load(builder.toString());
	}
	
	protected static void writeFile(String file, String data) throws IOException, JSONException{
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		
		writer.write(data);
		writer.flush();
		writer.close();
	}
	
	public static ArrayList<JSONObject> load(String content) throws JSONException {
		JSONArray json = new JSONArray(content);
		int len = json.length();
		ArrayList<JSONObject> objects = new ArrayList<>();
		
		if(json != null && len >0){
			for(int i=0;i<len;i++){
				objects.add(json.getJSONObject(i));
			}
			return objects;
		}
		
		return null;
	}
	
	public String toString(){
		try {
			return this.toJSONObject().toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public abstract String toDisplay();
}
