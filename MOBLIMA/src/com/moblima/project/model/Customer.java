package com.moblima.project.model;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

/**Model to hold customer info
 * @author sunzhihao
 *
 */
public class Customer extends Model {
	private String name;
	private String phone;
	private String email;
	
	private ArrayList<Booking> mBookingRecords;
	
	public Customer() {
		mBookingRecords = new ArrayList<>();
	}
	
	/**
	 * @param id
	 */
	public Customer(int id) {
		this();
		this.id = id;
	}
	
	/**
	 * @param name
	 * @param email
	 * @param phone
	 */
	public Customer(String name, String email, String phone) {
		this();
		this.name = name;
		this.email = email;
		this.phone = phone;
	}
	
	/**
	 * @param object
	 * @throws JSONException
	 */
	public Customer(JSONObject object) throws JSONException{
		this(object.getString("name"), 
			 object.getString("email"),
			 object.getString("phone"));
		
		this.id = object.getInt("id");
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
	
	public ArrayList<Booking> getBookingRecords() {
		return mBookingRecords;
	}

	public void addBookingRecord(Booking record) {
		this.mBookingRecords.add(record);
	}
	
	public void setBookingRecords(ArrayList<Booking> mBookingRecords) {
		this.mBookingRecords = mBookingRecords;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Customer) {		
			Customer c = (Customer) obj;
			if (id != 0)
				return c.id == id;
			else
				return (email.equals(c.email) && phone.equals(c.phone) && name.equals(c.name));
		}
		return super.equals(obj);
	}
	
	@Override
	public JSONObject toJSONObject() throws JSONException{
		JSONObject object = new JSONObject();
		object.put("id", id);
		object.put("name", name);
		object.put("phone", phone);
		object.put("email", email);
		
		return object;
	}

	@Override
	public String toDisplay() {
		return null;
	}
}
