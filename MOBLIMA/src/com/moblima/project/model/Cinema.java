package com.moblima.project.model;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.model.Constant.Cineplex;

/**The class to hold cinema info and all the showtimes inside the cinema
 * @author sunzhihao
 *
 */
public class Cinema extends Model {

	private String name;
	private Cineplex cineplex;

	private String code;
	private boolean isPlatinum;

	private ArrayList<ShowTime> mShowTimes;

	public Cinema() {
		mShowTimes = new ArrayList<>();
	}
	
	/**
	 * @param code
	 */
	public Cinema(String code) {
		this.code = code;
	}

	/**
	 * @param name
	 * @param cineplex
	 */
	public Cinema(String name, Cineplex cineplex) {
		this.name = name;
		this.cineplex = cineplex;
	}

	/**
	 * @param name
	 * @param cineplex
	 * @param showTimes
	 */
	public Cinema(String name, Cineplex cineplex, ArrayList<ShowTime> showTimes) {
		super();
		this.name = name;
		this.cineplex = cineplex;
		this.mShowTimes = showTimes;
	}

	/**
	 * @param object
	 * @throws JSONException
	 * @throws ParseException
	 */
	public Cinema(JSONObject object) throws JSONException, ParseException {
		this();
		this.name = object.getString("name");
		this.code = object.getString("code");
		this.isPlatinum = object.getBoolean("platinum suite");
		this.cineplex = Cineplex.valueOf(object.getString("cineplex"));
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
		return mShowTimes;
	}

	public void setShowTimes(ArrayList<ShowTime> showTimes) {
		this.mShowTimes = showTimes;
	}

	public void addShowTime(ShowTime showTime) {
		this.mShowTimes.add(showTime);
	}
	
	public void removeShowTime(ShowTime showTime) {
		this.mShowTimes.remove(showTime);
	}

/**Update the cinema info from a cinema instance.
 * @param copyInstance
 */
	public void copy(Cinema copyInstance) {
		id 	 = copyInstance.id;
		name = copyInstance.name;
		code = copyInstance.code;
		
		cineplex   = copyInstance.cineplex;
		mShowTimes = copyInstance.mShowTimes;
		isPlatinum = copyInstance.isPlatinum;
	}
	
	public Cinema clone() {
		Cinema cloned = new Cinema();
		cloned.id   = id;
		cloned.name = name;
		cloned.code = code;
		
		cloned.cineplex   = Cineplex.valueOf(cineplex.name());
		cloned.isPlatinum = isPlatinum;
		cloned.mShowTimes = mShowTimes;
		return cloned;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Cinema) {
			Cinema c = (Cinema) obj;
			return c.getCode().equals(code);
		} else if (obj instanceof Cineplex) {
			return cineplex.equals(obj);
		} else if (obj instanceof String) {
			return code.equals(obj);
		}
		
		return super.equals(obj);
	}

	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("code", code);
		object.put("name", name);
		object.put("cineplex", cineplex.name());
		object.put("platinum suite", isPlatinum);

//		JSONArray array = new JSONArray();
//		for (ShowTime showTime : this.mShowTimes) {
//			array.put(showTime.toJSONObject());
//		}
//		object.put("show times", array);
		return object;
	}

	@Override
	public String toDisplay() {
		return this.id + "  " + this.name + "  " + this.cineplex.toString();
	}

}
