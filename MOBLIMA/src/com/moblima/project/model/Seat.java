package com.moblima.project.model;

import org.json.JSONException;
import org.json.JSONObject;

/**The model to hold a seat location and its ShowTime
 *
 */
public class Seat extends Model implements Comparable<Seat> {
	private int col;
	private int row;
	private ShowTime showTime;
	
	/**
	 * @param row
	 * @param col
	 */
	public Seat(int row, int col) {
		this.col = col;
		this.row = row;
	}
	
	/**
	 * @param col
	 * @param row
	 * @param showTime
	 */
	public Seat(int col, int row, ShowTime showTime) {
		super();
		this.col = col;
		this.row = row;
		this.showTime = showTime;
	}

	/**
	 * @param object
	 * @throws JSONException
	 */
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
	
	/**Get the seat in format A5
	 * @return String
	 */
	public String getSeat() {
		return String.valueOf(Character.toChars(65+row))+(col+1);
	}

	public void setShowTime(ShowTime showTime) {
		this.showTime = showTime;
	}

	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		
		object.put("col", col);
		object.put("row" , row);
		
		return object;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Seat) {
			Seat s = (Seat) obj;
			return this.col == s.col && this.row == s.row;
		}
		
		return false;
	}

	@Override
	public String toDisplay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Seat s) {
		int compare = 0;
		
		if (row < s.row) {
			compare = -1;
		} else if (row > s.row) {
			compare = 1;
		} else if (col < s.col) {
			compare = -1;
		} else if (col > s.col) {
			compare = 1;
		}
		
		return compare;
	}
	
	
}
