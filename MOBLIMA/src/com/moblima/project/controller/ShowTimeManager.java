package com.moblima.project.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.moblima.project.model.Cinema;
import com.moblima.project.model.Constant;
import com.moblima.project.model.Model;
import com.moblima.project.model.Movie;
import com.moblima.project.model.Seat;
import com.moblima.project.model.ShowTime;
import com.moblima.project.model.Ticket;

public class ShowTimeManager extends Manager{
	private static final String file = "data/showTime.json";
	
	private ArrayList<ShowTime> mShowTimes;
	
	public ShowTimeManager() throws IOException, JSONException, ParseException {
		super();
	}
	
	@Override
	protected void load() throws IOException, JSONException, ParseException {
		JSONArray array = this.getData(file);
		mShowTimes = new ArrayList<>();
		idCounter = 0;
		ShowTime showTime;
		
		for(int i=0;i<array.length();i++){
			showTime = new ShowTime(array.getJSONObject(i));
			mShowTimes.add(showTime);
			idCounter = showTime.getId();
		}
	}

	public ArrayList<ShowTime> getmShowTimes() {
		return mShowTimes;
	}

	public void setmShowTimes(ArrayList<ShowTime> mShowTimes) {
		this.mShowTimes = mShowTimes;
	}

	@Override
	public boolean create(Model instance) throws JSONException {
		if(((ShowTime)instance).getClass() != ShowTime.class)
			return false;
		
		instance.setId(this.idCounter + 1);
		this.idCounter++;
		mShowTimes.add((ShowTime)instance);
		jdata.put(instance.toJSONObject());
		
		this.writeFile(file);
		return true;
	}

	@Override
	public boolean update(Model instance) throws JSONException {
		if(((ShowTime)instance).getClass() != ShowTime.class)
			return false;
		if(instance.getId() > this.idCounter)
			return false;
		
		ShowTime showTime;
		
		for(int i=0;i<this.mShowTimes.size();i++){
			showTime = this.mShowTimes.get(i);
			if(showTime.getId() == instance.getId()){
				this.mShowTimes.set(i, (ShowTime) instance);
				jdata.put(i,instance.toJSONObject());
				this.writeFile(file);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean delete(Model instance) {
		if(((ShowTime)instance).getClass() != ShowTime.class)
			return false;
		if(instance.getId() > this.idCounter)
			return false;
		
		ShowTime showTime;
		
		for(int i=0;i<this.mShowTimes.size();i++){
			showTime = this.mShowTimes.get(i);
			if(showTime.getId() == instance.getId()){
				this.mShowTimes.remove(i);
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
		
		ShowTime showTime;
		
		for(int i=0;i<this.mShowTimes.size();i++){
			showTime = this.mShowTimes.get(i);
			if(showTime.getId() == id){
				this.mShowTimes.remove(i);
				jdata.remove(i);
				this.writeFile(file);
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Model getInstanceById(int id) {
		for(ShowTime showTime : mShowTimes){
			if(showTime.getId() == id)
				return showTime;
		}
		return null;
	}
	
	//Get the ShowTimes of the particular movie and cinema 
	public ArrayList<ShowTime> getShowTimes(int movieId, int cinemaId){
		ArrayList<ShowTime> showTimes = new ArrayList<ShowTime>();
		
		for(ShowTime showTime : this.mShowTimes){
			if(showTime.getCinemaId() == cinemaId && showTime.getMovieId() == movieId){
				showTimes.add(showTime);
			}
		}
		
		return showTimes;
	}
	
	//Get the movie of the ShowTime. Need the help of MovieManager.
	public Movie getMovie(ShowTime time, MovieManager movieManager){
		return (Movie) movieManager.getInstanceById(time.getMovieId());
	}
	
	//Get the cinema of the ShowTime. Need the help of CinemaManager.
	public Cinema getCinema(ShowTime time, CinemaManager cinemaM){
		return (Cinema) cinemaM.getInstanceById(time.getCinemaId());
	}
	
	//Get the total ticket sale of the ShowTime. Here the sale is after discount.
	public int getTotalSale(ShowTime time, TicketManager ticketManager){
		int total = 0;
		
		for(Ticket ticket: ticketManager.getTickets()){
			if(time.equals(ticket.getShowTime())){
				total += ticket.getPrice();
			}
		}
		
		return total;
	}
	
	//Note the seats row and column both start from 1.
	public Boolean[][] getSeats(ShowTime time, TicketManager ticketManager){
		Boolean[][] seats = new Boolean[Constant.rowNumber+1][Constant.colNumber+1];
		int i,j;
		
		for(i=1;i<=Constant.rowNumber;i++){
			for(j=1;j<=Constant.colNumber;j++)
				seats[i][j] = false;
		}
		
		Seat seat;
		
		for(Ticket ticket : ticketManager.getTickets()){
			if(ticket.getShowTime().equals(time)){
				seat = ticket.getSeat();
				seats[seat.getRow()][seat.getCol()] = true;
			}
		}
		
		return seats;
	}
}
