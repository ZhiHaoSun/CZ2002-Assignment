package com.moblima.project.model;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Ticket extends Model {
	
	private ShowTime showTime;
	private Customer customer;
	private Seat seat;
	private Date date;
	
	public Ticket(ShowTime showTime, Customer customer) {
		super();
		this.showTime = showTime;
		this.customer = customer;
		this.date = new Date();
	}
	
	public Ticket(ShowTime showTime, Customer customer, Date date) {
		super();
		this.showTime = showTime;
		this.customer = customer;
		this.date = date;
	}
	
	public Ticket(JSONObject object) throws JSONException, ParseException{
		this.id = object.getInt("id");
		this.showTime = new ShowTime(object.getJSONObject("showTime"));
		this.date = Constant.dateFormat.parse(object.getString("date"));
		this.customer = new Customer(object.getJSONObject("customer"));
	}

	public ShowTime getShowTime() {
		return showTime;
	}

	public void setShowTime(ShowTime showTime) {
		this.showTime = showTime;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public Date getDate() {
		return date;
	}
	
	public String getDateStr(){
		return Constant.dateFormat.format(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("id",id);
		object.put("showTime", showTime.toJSONObject());
		object.put("customer", customer.toJSONObject());
		object.put("date", Constant.dateFormat.format(date));
		return object;
	}

	@Override
	public String toDisplay() {
//		return showTime.getCinema().getName() + "  " + showTime.getMovie().getTitle() +
//				"  " + showTime.getDateTimeStr() + "  " + email + "  " + this.getDateStr();
		return "";
	}
}
