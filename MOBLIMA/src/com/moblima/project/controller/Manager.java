package com.moblima.project.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Manager {
	protected JSONArray  jitems;
	protected JSONObject jdata, jitem;
	
	protected JSONArray getData(String filepath, String key) throws IOException, JSONException {
		return getData(filepath).getJSONArray(key);
	}
	
	protected JSONObject getData(String filepath) throws IOException, JSONException {
		String data = new String(Files.readAllBytes(Paths.get(filepath)));
		
		jdata = new JSONObject(data);
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
}