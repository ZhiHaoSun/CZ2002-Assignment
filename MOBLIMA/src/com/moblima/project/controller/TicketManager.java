package com.moblima.project.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.moblima.project.model.Model;
import com.moblima.project.model.Movie;
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
}
