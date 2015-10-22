package com.moblima.project.model;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Booking extends Model {
	
	private int id;
	private ShowTime showTime;
	private String email;
	private Date date;
	
	private static String file = "data/booking.json";
	public static ArrayList<Booking> instances;
	public static int curMaxID;
	
	public Booking(ShowTime showTime, String email) {
		super();
		this.id = Booking.curMaxID + 1;
		this.showTime = showTime;
		this.email = email;
		this.date = new Date();
	}
	
	public Booking(ShowTime showTime, String email, Date date) {
		super();
		this.id = Booking.curMaxID + 1;
		this.showTime = showTime;
		this.email = email;
		this.date = date;
	}
	
	public Booking(JSONObject object) throws JSONException, ParseException{
		this.id = object.getInt("id");
		this.showTime = new ShowTime(object.getJSONObject("showTime"));
		this.date = Constant.dateFormat.parse(object.getString("date"));
		this.email = object.getString("email");
	}
	
	public static void load() throws IOException, JSONException, ParseException{
		 instances = new ArrayList<>();
		 ArrayList<JSONObject> objects = readFile(file);
		 int len = 0;
		 
		 if(objects != null)
			 len = objects.size();
		 
		 curMaxID = 0;
		 Booking booking;
		 
		 for(int i=0;i<len;i++){
			 booking = new Booking(objects.get(i));
			 instances.add(booking);
			 
			 if(booking.getId() > curMaxID)
				 curMaxID = booking.getId();
		 }
	}
	 
	public static String getData() throws JSONException{
		if(Booking.instances == null)
			return null;
		else{
			JSONArray array = new JSONArray();
			
			for(int i=0;i<Booking.instances.size();i++){
				array.put(Booking.instances.get(i).toJSONObject());
			}
			
			return array.toString();
		}
	}
	 
	public void save() throws IOException, JSONException{
		if(this.getId() > Booking.curMaxID){
			this.create();
		}else{
			this.update();
		}
	}
	
	public void create() throws IOException, JSONException{
		Booking.instances.add(this);
		Booking.curMaxID = this.getId();
		
		Booking.writeFile(file, Booking.getData());
	}
	
	public void update() throws IOException, JSONException{
		Booking booking;
		int i;
		for(i=0;i<Booking.instances.size();i++){
			booking = Booking.instances.get(i);
			if(booking.getId() == this.getId())
				break;
		}
		
		Booking.instances.set(i, this);
		Booking.writeFile(file, Booking.getData());
	}
	
	public void delete() throws IOException, JSONException{
		for(int i=0;i<Booking.instances.size();i++){
			if(Booking.instances.get(i).getId() == this.id){
				Booking.instances.remove(i);
				break;
			}
		}
		Booking.writeFile(file, Booking.getData());
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ShowTime getShowTime() {
		return showTime;
	}

	public void setShowTime(ShowTime showTime) {
		this.showTime = showTime;
	}
	
	public Movie getMovie(){
		return showTime.getMovie();
	}
	
	public Cinema getCinema(){
		return showTime.getCinema();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	public static ArrayList<Booking> getBookingsByEmail(String email){
		ArrayList<Booking> bookings = new ArrayList<>();
		
		for(Booking booking: Booking.instances){
			if(booking.getEmail().equals(email))
				bookings.add(booking);
		}
		
		return bookings;
	}
	
	public static ArrayList<Booking> getBookingsByMovieId(int movieId){
		ArrayList<Booking> bookings = new ArrayList<>();
		
		for(Booking booking: Booking.instances){
			if(booking.getShowTime().getMovie().getId() == movieId)
				bookings.add(booking);
		}
		
		return bookings;
	}
	
	public static ArrayList<Booking> getBookingsByMovie(Movie movie){
		return Booking.getBookingsByMovieId(movie.getId());
	}

	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("showTime", showTime.toJSONObject());
		object.put("email", email);
		object.put("date", Constant.dateFormat.format(date));
		return object;
	}

	@Override
	public String toDisplay() {
		return showTime.getCinema().getName() + "  " + showTime.getMovie().getTitle() +
				"  " + showTime.getDateTimeStr() + "  " + email + "  " + this.getDateStr();
	}

}
