package com.moblima.project.model;

import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

public class Seat {
	private int col;
	private int row;
	private ShowTime showTime;
	
	public Seat(int col, int row, ShowTime showTime) {
		super();
		this.col = col;
		this.row = row;
		this.showTime = showTime;
	}

	public Seat(JSONObject object) throws JSONException {
		this.row = object.getInt("row");
		this.col = object.getInt("col");
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public ShowTime getShowTime() {
		return showTime;
	}

	public void setShowTime(ShowTime showTime) {
		this.showTime = showTime;
	}

	public JSONObject toJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("row" , row);
		object.put("col", col);
		
		return object;
	}

	public boolean equals(Seat seat) {
		if(seat.getCol() == this.col && seat.getRow() == this.row)
			return true;
		else
			return false;
	}
	
	
}
