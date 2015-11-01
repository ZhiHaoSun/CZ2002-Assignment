package com.moblima.project.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;

import com.moblima.project.model.Cinema;
import com.moblima.project.model.Discount;
import com.moblima.project.model.Model;
import com.moblima.project.model.Movie;
import com.moblima.project.model.Seat;
import com.moblima.project.model.ShowTime;
import com.moblima.project.model.Ticket;

public class TicketManager extends Manager {
	private static final String file = "data/ticket.json";
	
	private ArrayList<Ticket> mTickets;
	
	public TicketManager() throws IOException, JSONException, ParseException {
		super();
	}
	
	public ArrayList<Ticket> getTickets() {
		return mTickets;
	}

	public void setTickets(ArrayList<Ticket> mTickets) {
		this.mTickets = mTickets;
	}

	@Override
	protected void load() throws IOException, JSONException, ParseException {
		JSONArray array = this.getData(file);
		mTickets = new ArrayList<>();
		idCounter = 0;
		Ticket ticket;
		
		for(int i=0;i<array.length();i++){
			ticket = new Ticket(array.getJSONObject(i));
			mTickets.add(ticket);
			idCounter = ticket.getId();
		}
	}

	@Override
	public boolean create(Model instance) throws JSONException {
		if(((Ticket)instance).getClass() != Ticket.class)
			return false;
		
		instance.setId(this.idCounter + 1);
		this.idCounter++;
		
		mTickets.add((Ticket)instance);
		jdata.put(instance.toJSONObject());
		
		this.writeFile(file);
		return true;
	}

	@Override
	public boolean update(Model instance) throws JSONException {
		if(((Ticket)instance).getClass() != Ticket.class)
			return false;
		if(instance.getId() > this.idCounter)
			return false;
		
		Ticket ticket;
		
		for(int i=0;i<this.mTickets.size();i++){
			ticket = this.mTickets.get(i);
			if(ticket.getId() == instance.getId()){
				this.mTickets.set(i, (Ticket) instance);
				jdata.put(i,instance.toJSONObject());
				this.writeFile(file);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean delete(Model instance) {
		if(((Ticket)instance).getClass() != Ticket.class)
			return false;
		if(instance.getId() > this.idCounter)
			return false;
		
		Ticket ticket;
		
		for(int i=0;i<this.mTickets.size();i++){
			ticket = this.mTickets.get(i);
			if(ticket.getId() == instance.getId()){
				this.mTickets.remove(i);
				jdata.remove(i);
				this.writeFile(file);
				
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean deleteById(int id) throws JSONException {
		if(this.idCounter < id)
			return false;
		
		Ticket ticket;
		
		for(int i=0;i<this.mTickets.size();i++){
			ticket = this.mTickets.get(i);
			if(ticket.getId() == id){
				this.mTickets.remove(i);
				jdata.remove(i);
				this.writeFile(file);
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Model getInstanceById(int id) {
		for(Ticket ticket : mTickets){
			if(ticket.getId() == id)
				return ticket;
		}
		return null;
	}
	
	public String getTicketCode(Ticket ticket , ShowTimeManager showTimeManager, MovieManager movieManager, CinemaManager cinemaManager){
		StringBuilder builder = new StringBuilder();
		ShowTime time = ticket.getShowTime();
		Date dateTime = time.getDateTime();
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(dateTime);
		
		Cinema cinema = showTimeManager.getCinema(time, cinemaManager);
		
		builder.append(cinema.getCode());
		builder.append(cal.get(Calendar.YEAR));
		builder.append(cal.get(Calendar.MONTH));
		builder.append(cal.get(Calendar.DAY_OF_MONTH));
		builder.append(cal.get(Calendar.HOUR_OF_DAY));
		builder.append(cal.get(Calendar.MINUTE));
		
		return builder.toString();
	}
	
	public Ticket getTicketBySeat(Seat seat){
		
		for(Ticket ticket : this.mTickets){
			if(ticket.getSeat().equals(seat)){
				return ticket;
			}
		}
		
		return null;
	}
	
	public ArrayList<Ticket> getTicketsByEmail(String email){
		ArrayList<Ticket> tickets = new ArrayList<>();
		
		for(Ticket ticket : this.mTickets){
			if(ticket.getCustomer().getEmail().equals(email))
				tickets.add(ticket);
		}
		
		return tickets;
	}
	
	public ArrayList<Ticket> getTicketsByShowTime(ShowTime time){
		ArrayList<Ticket> tickets = new ArrayList<>();
		
		for(Ticket ticket : this.mTickets){
			if(ticket.getShowTime().equals(time))
				tickets.add(ticket);
		}
		
		return tickets;
	}
	
	public int getPriceAfterDiscount(Ticket ticket, int age, ShowTimeManager showTimeManager, MovieManager movieManager, CinemaManager cinemaManager) throws JSONException{
		Movie movie = showTimeManager.getMovie(ticket.getShowTime(), movieManager);
		Cinema cinema = showTimeManager.getCinema(ticket.getShowTime(), cinemaManager);
		int price = movie.getPrice();
		
		Date date = new Date();
		Locale.setDefault(Locale.CHINA);
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    
	    if(cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
	    	price += Discount.discounts.getJSONObject("holiday").getInt("weekends");
	    }
	    
	    if(age <= 12){
	    	price += Discount.discounts.getJSONObject("age").getInt("below12");
	    } else if(age >= 60){
	    	price += Discount.discounts.getJSONObject("age").getInt("above60");
	    }
	    
	    price += Discount.discounts.getJSONObject("classType").getInt(movie.getClassType().name());
		
	    if(cinema.isPlatinum())
	    	price += Discount.discounts.getJSONObject("cinemaClass").getInt("platinum");
	    
		return price;
	}
}
