package com.moblima.project.model;

import org.json.JSONException;
import org.json.JSONObject;

/**The model to hold the info an admin of a cinema
 *
 */
public class Staff {
	private String username;
	private String password;
		
	public Staff() {}
	/**
	 * @param username
	 * @param password
	 */
	public Staff(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/**
	 * @param object
	 * @throws JSONException
	 */
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Staff) {
			Staff staff = (Staff) obj;
			return staff.username.equals(username) && staff.password.equals(password);
		}
		return super.equals(obj);
	}
	
	public JSONObject toJSONObject() throws JSONException {
		JSONObject juser = new JSONObject();
		
		juser.put("usrname", username);
		juser.put("passwd", password);
		
		return juser;
	}	
}
