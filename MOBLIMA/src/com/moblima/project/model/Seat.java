package com.moblima.project.model;

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
	
	
}