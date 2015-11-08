package com.moblima.project.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.model.Booking;
import com.moblima.project.model.Constant.MovieType;
import com.moblima.project.model.Constant.TicketType;
import com.moblima.project.model.Holiday;
import com.moblima.project.model.Model;
import com.moblima.project.model.Ticket;

public class TicketManager extends Manager {
	private static final String FILE_TICKET  = "data/ticket.json";
	private static final String FILE_HOLIDAY = "data/holiday.json";

	private JSONArray  array;
	private JSONObject object;
	
	private Holiday holiday;
	
	private ArrayList<Holiday> mHolidays;
	private ArrayList<Ticket> mTicketPrices;
	
	public TicketManager() throws IOException, JSONException, ParseException {
		super();
	}
	
	public ArrayList<Holiday> getHolidays() {
		Collections.sort(mHolidays);
		return mHolidays;
	}
	
	public ArrayList<Ticket> getTicketPrices() {
		return mTicketPrices;
	}

	@Override
	protected void load() throws IOException, JSONException, ParseException {
		idCounter     = 0;
		mHolidays	  = new ArrayList<>();
		mTicketPrices = new ArrayList<>();
		
		// retrieve cinema data
		array = getData(FILE_HOLIDAY);
		
		if (array.length()!=0) {
			for(pos=0;pos<array.length();pos++){
				holiday = new Holiday(array.getJSONObject(pos));
				mHolidays.add(holiday);
			}

			idCounter = mHolidays.get(pos-1).getId();
		}
		
		// retrieve ticket data
		array = getData(FILE_TICKET);
		if (array.length()!=0) {
			Ticket ticket;
			MovieType [] mtypes = MovieType.values();
			TicketType[] ttypes = TicketType.values();
			
			for (int i=0; i<mtypes.length; i++) {
				object = array.getJSONObject(0);
				object = object.getJSONObject("PLATINUM");
				object = object.getJSONObject(mtypes[i].name());

				for (int j=ttypes.length-2; j<ttypes.length; j++) {
					if (object.has(ttypes[j].name())) {
						ticket = new Ticket(true, mtypes[i]);
						ticket.setPrice(object.getDouble(ttypes[j].name()));
						ticket.setTicketType(ttypes[j]);
						
						mTicketPrices.add(ticket);
					}
				}
			}
			
			for (int i=0; i<mtypes.length; i++) {
				object = array.getJSONObject(0);
				object = object.getJSONObject(mtypes[i].name());

				for (int j=0; j<ttypes.length-2; j++) {
					if (object.has(ttypes[j].name())) {
						ticket = new Ticket(false, mtypes[i]);
						ticket.setPrice(object.getDouble(ttypes[j].name()));
						ticket.setTicketType(ttypes[j]);
						
						mTicketPrices.add(ticket);
					}
				}
			}
		}
	}
	
	public String generateTicketPriceTable(boolean platinum) {
		String price;
		StringBuilder sb = new StringBuilder();
		MovieType [] mtypes = MovieType.values();
		TicketType[] ttypes = TicketType.values();

		try {
			array = getData(FILE_TICKET);
			
			if (array.length()!=0) {
				int index, length;
				object = array.getJSONObject(0);
	
				index  = 0;
				length = ttypes.length;
				
				if (platinum) {
					object = object.getJSONObject("PLATINUM");
	
					sb.append("\n Platinum Ticket Price\n");
					sb.append("-----------------------\n");
					
					index  = length - 2;				
				} else {
					sb.append("\n Normal Ticket Price\n");
					sb.append("---------------------\n");
					
					length = length - 2;
				}
					
				JSONObject digital, threed;
	
				sb.append(String.format(" %-15s", "Ticket Type") +"|");
				sb.append(String.format(" %-7s", "2D") +"|");
				sb.append(String.format(" %-7s", "3D") +"\n");
				sb.append("***********************************\n");
	
				for (; index<length; index++) {
					sb.append(String.format(" %-15s", ttypes[index].value()));
					sb.append("|");
	
					digital = object.getJSONObject(mtypes[0].name());
					
					if (digital.has(ttypes[index].name())) {
						price = "$"+String.format("%.2f", digital.getDouble(ttypes[index].name()));
						sb.append(String.format(" %-7s", price));
					} else {
						sb.append(String.format(" %-7s", "NA"));
					}
					
					sb.append("|");
	
					threed = object.getJSONObject(mtypes[1].name());
					
					if (threed.has(ttypes[index].name())) {
						price = "$"+String.format("%.2f", threed.getDouble(ttypes[index].name()));
						sb.append(String.format(" %-7s", price));
					} else {
						sb.append(String.format(" %-7s", "NA"));
					}
					
					sb.append("\n");
				}			
			}
		
		} catch (IOException | JSONException e) {
			return "Wrong json file? or invalid json format for ticket?";
		}
		
		return sb.toString();
	}
	
	public void calculateTicketPrice(Booking record) {
		
	}


	@Override
	public boolean create(Model model) {
		if (model instanceof Holiday) {
			model.setId(++idCounter);
			mHolidays.add((Holiday) model);			
			return writeFile(FILE_HOLIDAY, mHolidays.toString());
		}
		return false;
	}

	@Override
	public boolean update(Model model) {
		if (model instanceof Ticket) {
			Ticket ticket = (Ticket) model;
			try {
				array  = getData(FILE_TICKET);
				object = array.getJSONObject(0); 
				
				if (ticket.isPlatinum())
					object = object.getJSONObject("PLATINUM");
				
				object = object.getJSONObject(ticket.getMovieType().name());
				object.put(ticket.getTicketType().name(), ticket.getPrice());
				
				return writeFile(FILE_TICKET, array.toString());
			} catch (IOException | JSONException e) {
				e.printStackTrace();
			}
		} else if (model instanceof Holiday) {
			index = mHolidays.indexOf(model);
			mHolidays.set(index, (Holiday) model); 
			return writeFile(FILE_HOLIDAY, mHolidays.toString());
		}
		
		return false;
	}

	@Override
	public boolean delete(Model model) {
		if (model instanceof Holiday) {
			mHolidays.remove(model);
			return writeFile(FILE_HOLIDAY, mHolidays.toString());
		}
		return false;
	}

	@Override
	public Model getInstance(Model model) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	protected void load() throws IOException, JSONException, ParseException {
//		JSONArray array = this.getData(file);
//		mTickets = new ArrayList<>();
//		idCounter = 0;
//		Ticket ticket;
//		
//		for(int i=0;i<array.length();i++){
//			ticket = new Ticket(array.getJSONObject(i));
//			mTickets.add(ticket);
//			idCounter = ticket.getId();
//		}
//	}
//
//	@Override
//	public boolean create(Model instance) throws JSONException {
//		if(((Ticket)instance).getClass() != Ticket.class)
//			return false;
//		
//		instance.setId(this.idCounter + 1);
//		this.idCounter++;
//		
//		mTickets.add((Ticket)instance);
//		jdata.put(instance.toJSONObject());
//		
//		this.writeFile(file);
//		return true;
//	}
//
//	@Override
//	public boolean update(Model instance) throws JSONException {
//		if(((Ticket)instance).getClass() != Ticket.class)
//			return false;
//		if(instance.getId() > this.idCounter)
//			return false;
//		
//		Ticket ticket;
//		
//		for(int i=0;i<this.mTickets.size();i++){
//			ticket = this.mTickets.get(i);
//			if(ticket.getId() == instance.getId()){
//				this.mTickets.set(i, (Ticket) instance);
//				jdata.put(i,instance.toJSONObject());
//				this.writeFile(file);
//				return true;
//			}
//		}
//		return false;
//	}
//
//	@Override
//	public boolean delete(Model instance) {
//		if(((Ticket)instance).getClass() != Ticket.class)
//			return false;
//		if(instance.getId() > this.idCounter)
//			return false;
//		
//		Ticket ticket;
//		
//		for(int i=0;i<this.mTickets.size();i++){
//			ticket = this.mTickets.get(i);
//			if(ticket.getId() == instance.getId()){
//				this.mTickets.remove(i);
//				jdata.remove(i);
//				this.writeFile(file);
//				
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	@Override
//	public boolean deleteById(int id) throws JSONException {
//		if(this.idCounter < id)
//			return false;
//		
//		Ticket ticket;
//		
//		for(int i=0;i<this.mTickets.size();i++){
//			ticket = this.mTickets.get(i);
//			if(ticket.getId() == id){
//				this.mTickets.remove(i);
//				jdata.remove(i);
//				this.writeFile(file);
//				
//				return true;
//			}
//		}
//		
//		return false;
//	}
//
//	@Override
//	public Model getInstanceById(int id) {
//		for(Ticket ticket : mTickets){
//			if(ticket.getId() == id)
//				return ticket;
//		}
//		return null;
//	}
//	
//	public String getTicketCode(Ticket ticket , ShowTimeManager showTimeManager, MovieManager movieManager, CinemaManager cinemaManager){
//		StringBuilder builder = new StringBuilder();
//		ShowTime time = ticket.getShowTime();
//		Date dateTime = ticket.getDateTime();
//		
//		Calendar cal = Calendar.getInstance();
//	    cal.setTime(dateTime);
//		
//		Cinema cinema = showTimeManager.getCinema(time, cinemaManager);
//		
//		builder.append(cinema.getCode());
//		builder.append(cal.get(Calendar.YEAR));
//		builder.append(cal.get(Calendar.MONTH));
//		builder.append(cal.get(Calendar.DAY_OF_MONTH));
//		builder.append(cal.get(Calendar.HOUR_OF_DAY));
//		builder.append(cal.get(Calendar.MINUTE));
//		
//		return builder.toString();
//	}
//	
//	public Ticket getTicketBySeat(Seat seat){
//		
//		for(Ticket ticket : this.mTickets){
//			if(ticket.getSeat().equals(seat)){
//				return ticket;
//			}
//		}
//		
//		return null;
//	}
//	
//	public ArrayList<Ticket> getTicketsByEmail(String email){
//		ArrayList<Ticket> tickets = new ArrayList<>();
//		
//		for(Ticket ticket : this.mTickets){
//			if(ticket.getCustomer().getEmail().equals(email))
//				tickets.add(ticket);
//		}
//		
//		return tickets;
//	}
//	
//	public ArrayList<Ticket> getTicketsByShowTime(ShowTime time){
//		ArrayList<Ticket> tickets = new ArrayList<>();
//		
//		for(Ticket ticket : this.mTickets){
//			if(ticket.getShowTime().equals(time))
//				tickets.add(ticket);
//		}
//		
//		return tickets;
//	}
//	
//	public int getPriceAfterDiscount(Ticket ticket, int age, ShowTimeManager showTimeManager, MovieManager movieManager, CinemaManager cinemaManager) throws JSONException{
//		Movie movie = showTimeManager.getMovie(ticket.getShowTime(), movieManager);
//		Cinema cinema = showTimeManager.getCinema(ticket.getShowTime(), cinemaManager);
//		int price = movie.getPrice();
//		
//		Date date = new Date();
//		Locale.setDefault(Locale.CHINA);
//		Calendar cal = Calendar.getInstance();
//	    cal.setTime(date);
//	    
//	    if(cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
//	    	price += Discount.discounts.getJSONObject("holiday").getInt("weekends");
//	    } else {
//	    	// price change before 6 and after 6
//	    	if (cal.get(Calendar.HOUR_OF_DAY) < 18); 		
//	    }
//	    
//	    if(age <= 12){
//	    	price += Discount.discounts.getJSONObject("age").getInt("below12");
//	    } else if(age >= 60){
//	    	price += Discount.discounts.getJSONObject("age").getInt("above60");
//	    }
//	    
//	    price += Discount.discounts.getJSONObject("classType").getInt(movie.getClassType().name());
//		
//	    if(cinema.isPlatinum())
//	    	price += Discount.discounts.getJSONObject("cinemaClass").getInt("platinum");
//	    
//		return price;
//	}
//
//	@Override
//	public Model getInstance(Model model) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
