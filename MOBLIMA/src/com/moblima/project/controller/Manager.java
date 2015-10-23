package com.moblima.project.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.model.Model;

public abstract class Manager {
	protected int idCounter;
	protected JSONArray  jdata;
	
	public Manager() throws IOException, JSONException, ParseException{
		load();
	}
	
	protected JSONArray getData(String filepath) throws IOException, JSONException {
		String data = new String(Files.readAllBytes(Paths.get(filepath)));
		
		jdata = new JSONArray(data);
		return jdata;
	}
	
	protected boolean writeFile(String file) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(jdata.toString());
			bw.flush();
			bw.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	protected abstract void load() throws IOException, JSONException, ParseException ;
	
	public boolean save(Model instance) throws JSONException{
		if(instance.getId() > this.idCounter)
			return create(instance);
		else
			return update(instance);
	}
	public abstract boolean create(Model instance) throws JSONException;
	public abstract boolean update(Model instance) throws JSONException;
	public abstract boolean delete(Model instance) throws JSONException;
	public abstract boolean deleteById(int id) throws JSONException;
	
	public abstract Model getInstanceById(int id);
	
	public String toString(){
		return jdata.toString();
	}
}
