package com.moblima.project.model;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Holiday extends Model implements Comparable<Holiday> {
	
	private Date   date;
	private String name;
	
	public Holiday() {}
	
	public Holiday(JSONObject object) throws ParseException, JSONException {
		this();
		this.id   = object.getInt("id");
		this.name = object.getString("name");
		this.date = Constant.holidayFormat.parse(object.getString("date"));
	}
	
	public String getDate() {
		return Constant.holidayFormat.format(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Holiday clone() {
		Holiday holiday = new Holiday();
		holiday.id 	 = id;
		holiday.name = name;
		holiday.date = date;
		
		return holiday;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Holiday) {
			Holiday h = (Holiday) obj;
			return h.id == id;
		}
		return super.equals(obj);
	}
	
	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("id", id);
		object.put("name", name);
		object.put("date", getDate());

		return object;
	}

	@Override
	public String toDisplay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Holiday o) {
		if (date.before(o.date))
			return -1;
		else if (date.after(o.date))
			return 1;
		else 
			return name.compareTo(o.name);

	}

}
