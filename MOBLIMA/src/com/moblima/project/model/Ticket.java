package com.moblima.project.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.model.Constant.MovieType;
import com.moblima.project.model.Constant.TicketType;

/**The Ticket class to hold all the discount info and the total price of the ticket after discount.
 * @author sunzhihao
 *
 */
public class Ticket extends Model {
	
	private boolean platinum;
	private MovieType movieType;
	private TicketType ticketType;
	private double price;
	
	public Ticket(){}
	/**
	 * @param platinum
	 * @param movieType
	 */
	public Ticket(boolean platinum, MovieType movieType){
		this.platinum  = platinum;
		this.movieType = movieType;
	}
	
	public TicketType getTicketType() {
		return ticketType;
	}
	
	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public boolean isPlatinum() {
		return platinum;
	}
	
	public void setPlatinum(boolean platinum) {
		this.platinum = platinum;
	}
	
	public MovieType getMovieType() {
		return movieType;
	}
	
	public void setMovieType(MovieType movieType) {
		this.movieType = movieType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Ticket) {
			Ticket t = (Ticket) obj;
			return (t.platinum == platinum && t.movieType ==movieType && t.ticketType == ticketType);
		}
		return super.equals(obj);
	}
	
	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("id",id);
		object.put("price" , price);
		return object;
	}

	@Override
	public String toDisplay() {
		return "";
	}
}
