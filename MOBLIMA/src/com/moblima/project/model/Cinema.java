package com.moblima.project.model;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.model.Constant.Cineplex;

public class Cinema extends Model {

	private String name;
	private Cineplex cineplex;

	private String code;
	private boolean isPlatinum;

	private ArrayList<ShowTime> showTimes;

	public Cinema() {
		showTimes = new ArrayList<>();
	}

	public Cinema(String name, Cineplex cineplex) {
		this.name = name;
		this.cineplex = cineplex;
		this.showTimes = new ArrayList<>();
	}

	public Cinema(String name, Cineplex cineplex, ArrayList<ShowTime> showTimes) {
		super();
		this.name = name;
		this.cineplex = cineplex;
		this.showTimes = showTimes;
	}

	public Cinema(JSONObject object) throws JSONException, ParseException {
		this.id = object.getInt("id");
		this.name = object.getString("name");
		this.code = object.getString("code");
		this.isPlatinum = object.getBoolean("platinum suite");
		this.cineplex = Cineplex.valueOf(object.getString("cineplex"));

		ArrayList<ShowTime> shows = new ArrayList<>();
		JSONArray array = object.getJSONArray("show times");

		int len = array.length();
		for (int i = 0; i < len; i++) {
			shows.add(new ShowTime((JSONObject) array.get(i)));
		}

		this.showTimes = shows;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Cineplex getCineplex() {
		return cineplex;
	}

	public void setCineplex(Cineplex cineplex) {
		this.cineplex = cineplex;
	}

	public boolean isPlatinum() {
		return isPlatinum;
	}

	public void setPlatinum(boolean isPlatinum) {
		this.isPlatinum = isPlatinum;
	}

	public ArrayList<ShowTime> getShowTimes() {
		return showTimes;
	}

	public void setShowTimes(ArrayList<ShowTime> showTimes) {
		this.showTimes = showTimes;
	}

	public void addShowTime(ShowTime showTime) {
		this.showTimes.add(showTime);
	}

	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("id", id);
		object.put("code", code);
		object.put("name", name);
		object.put("cineplex", cineplex.name());
		object.put("platinum suite", isPlatinum);

		JSONArray array = new JSONArray();
		for (ShowTime showTime : this.showTimes) {
			array.put(showTime.toJSONObject());
		}
		object.put("show times", array);
		return object;
	}

	@Override
	public String toDisplay() {
		return this.id + "  " + this.name + "  " + this.cineplex.toString();
	}

}
