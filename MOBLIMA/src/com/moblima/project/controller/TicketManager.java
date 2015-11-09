package com.moblima.project.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.moblima.project.model.Constant;
import com.moblima.project.model.Constant.MovieType;
import com.moblima.project.model.Constant.TicketType;
import com.moblima.project.model.Holiday;
import com.moblima.project.model.Model;
import com.moblima.project.model.ShowTime;
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
	
	public Ticket getTicketPrice (ShowTime showtime, boolean isStudent, boolean isSeniorCitizen) {
		Calendar cal = Calendar.getInstance();
	    //cal.setTime(showtime.getDate());
	    try {
			Date testdate = Constant.dateFormatShort.parse("1/1/2015");
		    cal.setTime(testdate);
		    
		    
			Date six = Constant.clockFormat.parse("1800");
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

			TicketType type = null;
	
			if (showtime.getCinema().isPlatinum()) {
				// Monday to Thursday
				if (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.THURSDAY) {
					type = TicketType.MON_TO_THU;
				// Friday to Sun
				} else {
					type = TicketType.FRI_TO_SUN;
				}
			} else {
				// Sat, Sun, Eve of PH or PH
				if (mHolidays.contains(showtime) || 
					(dayOfWeek >= Calendar.SATURDAY && dayOfWeek <= Calendar.SUNDAY)) {
					type = TicketType.SAT_AND_SUN;
				} else {
					// Mon to Fri
					if (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY) {
						if (showtime.getTime().before(six)) {
							if (isStudent) {
								type = TicketType.STUDENTS;
							} else if (isSeniorCitizen) {	
								type = TicketType.SENIOR_CITIZENS;
							} else if (dayOfWeek == Calendar.FRIDAY) {
								type = TicketType.FRI_BEFORE_SIX_PM;
							}
						// Fri after 6pm
						System.out.println("after 6pm");
						} else if (dayOfWeek == Calendar.FRIDAY) {
							type = TicketType.FRI_AFTER_SIX_PM;
						} 
						
						// Mon to Wed
						if (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.WEDNESDAY) {
							type = TicketType.MON_WED;
						// Thu
						} else if (dayOfWeek == Calendar.THURSDAY) {
							type = TicketType.THU;
						}
						
					}
				}
			}
			
			Ticket tprice = new Ticket();
			tprice.setPlatinum(showtime.getCinema().isPlatinum());
			tprice.setMovieType(showtime.getMovie().getMovieType());
			tprice.setTicketType(type);
			tprice = (Ticket) getInstance(tprice);
			return tprice;
	    } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return null;
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
		if (model instanceof Ticket) {
			if (mTicketPrices.contains(model)) {
				index = mTicketPrices.indexOf(model);
				return mTicketPrices.get(index);
			}
		}
		return null;
	}
}
