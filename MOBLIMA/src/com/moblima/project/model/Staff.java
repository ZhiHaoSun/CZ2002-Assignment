package com.moblima.project.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Staff {
	private String username;
	private String password;
		
	public Staff() {}
	public Staff(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Staff(JSONObject object) throws JSONException{
		this.username = object.getString("usrname");
		this.password = object.getString("passwd");
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public JSONObject toJSONObject() throws JSONException {
		JSONObject juser = new JSONObject();
		
		juser.put("usrname", username);
		juser.put("passwd", password);
		
		return juser;
	}	
}
