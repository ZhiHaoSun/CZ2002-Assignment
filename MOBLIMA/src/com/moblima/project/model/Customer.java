package com.moblima.project.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Customer {
	private String name;
	private String phone;
	private String email;
	
	public Customer(String name, String phone, String email) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
	
	public Customer(JSONObject object) throws JSONException{
		this.name = object.getString("name");
		this.phone = object.getString("phone");
		this.email = object.getString("email");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public JSONObject toJSONObject() throws JSONException{
		JSONObject object = new JSONObject();
		object.put("name", name);
		object.put("phone", phone);
		object.put("email", email);
		
		return object;
	}
}
