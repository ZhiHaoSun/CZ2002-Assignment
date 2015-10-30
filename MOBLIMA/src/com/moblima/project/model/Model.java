package com.moblima.project.model;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Model {
	protected int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public abstract JSONObject toJSONObject() throws JSONException;
	
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
