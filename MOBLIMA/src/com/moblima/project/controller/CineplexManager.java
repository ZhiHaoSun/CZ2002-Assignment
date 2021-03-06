package com.moblima.project.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;

import com.moblima.project.model.Booking;
import com.moblima.project.model.Cinema;
import com.moblima.project.model.Constant.Cineplex;
import com.moblima.project.model.Constant.Status;
import com.moblima.project.model.Customer;
import com.moblima.project.model.Model;
import com.moblima.project.model.Movie;
import com.moblima.project.model.ShowTime;
import com.moblima.project.model.Staff;
import com.moblima.project.model.Ticket;

/**The manager class to manage the storage of all the models.
 * All the model data is held inside corresponding ArrayLists.
 *Any updates on the data is done on the ArrayLists here and files both.
 */
public class CineplexManager extends Manager {
	/**
	 * 
	 */
	private static final String FILE_STAFF       = "data/staff.json";
	private static final String FILE_MOVIE       = "data/movie.json";
	private static final String FILE_CINEMA      = "data/cinema.json";
	private static final String FILE_SHOWTIME    = "data/showTime.json";
	private static final String FILE_CUSTOMER    = "data/customer.json";
	private static final String FILE_BOOKING	 = "data/booking.json";

	private int midCounter;
	private int sidCounter;
	private int cidCounter;
	
	// to store the original data from json
	private ArrayList<Staff>    mStaffs;
	
	private ArrayList<Movie>    mMovies;
	
	private ArrayList<Cinema>   mCinemas;
	private ArrayList<ShowTime> mShowTimes;
	private ArrayList<Customer> mCustomers;
	private ArrayList<Booking>  mBookingRecords;
	private ArrayList<Ticket>   mTicketPrices;
	
	private Staff staff;
	private Movie movie;
	private Cinema cinema;
	private ShowTime showtime;
	private Customer customer;
	private Booking  booking;
	
	private TicketManager mTicketManager;
	
	/**{@link Manager}
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException
	 * 
	 */
	public CineplexManager() throws IOException, JSONException, ParseException {
		super();
	}
	
	// Read-Only for the list of movies, cinema, showtimes
	// No direct replacement is allow
	/**
	 * @return ArraayList<Movie>
	 */
	public ArrayList<Movie> getMovies() {
		return mMovies;
	}

	/**
	 * @return ArrayList<Cinema>
	 */
	public ArrayList<Cinema> getCinemas() {
		return mCinemas;
	}

	/**
	 * @return ArrayList<ShowTime>
	 */
	public ArrayList<ShowTime> getShowTimes() {
		return mShowTimes;
	}
	
	/**
	 * @return ArrayList<Customer>
	 */
	public ArrayList<Customer> getCustomers() {
		return mCustomers;
	}
	
	/**
	 * @return ArrayList<Booking>
	 */
	public ArrayList<Booking> getBookingRecords() {
		return mBookingRecords;
	}
	
	/**
	 * @return ArrayList<Ticket>
	 */
	public ArrayList<Ticket> getTicketPrices() {
		return mTicketPrices;
	}
	
	/**Search movies based on movie title.
	 * @param String
	 * @return ArrayList<Movie>
	 */
	public ArrayList<Movie> searchMovies(String search) {
		// create new list to prevent from affecting the original copy
		ArrayList<Movie> mSearchResult = new ArrayList<>();
		
		// set to lower case before the search
		search = search.toLowerCase();
		
		for (Movie movie:mMovies) {
			// set the title to lower case also
			String title = movie.getTitle().toLowerCase();
			if (title.contains(search))
				mSearchResult.add(movie);
		}
		
		return mSearchResult;
	}
	
	public ArrayList<Movie> getCurrentMovies() {
		ArrayList<Movie> cMovies = new ArrayList<>();
		
		for (Movie m:mMovies) {
			if (m.getStatus() == Status.PREVIEW || 
				m.getStatus() == Status.NOW_SHOWING) {
				cMovies.add(m);
			}
		}
		
		return cMovies;
	}
		
	/**Get the top five movies.
	 * Two options to be based on:
	 * 1. Overall Rating
	 * 2. Total Sale calculated from booking history
	 * @param boolean byOverallRating
	 * @return ArrayList<Movie> 
	 */
	public ArrayList<Movie> getTopFiveMovies(boolean byOverallRating) {
		// create new list to prevent from affecting the original copy
		ArrayList<Movie> movies = new ArrayList<>(mMovies);

		if (byOverallRating) {
			Collections.sort(movies, new Comparator<Movie>() {
				@Override
				public int compare(Movie m1, Movie m2) {
					// 
					if (m1.getOverallRating() < m2.getOverallRating())
						return 1;
					else if (m1.getOverallRating() > m2.getOverallRating())
						return -1;
					else 
						return m1.getTitle().compareTo(m2.getTitle());
				}
			});
		} else {
			Collections.sort(movies, new Comparator<Movie>() {
				@Override
				public int compare(Movie m1, Movie m2) {
					if (m1.getTicketSales() < m2.getTicketSales())
						return 1;
					else if (m1.getTicketSales() > m2.getTicketSales())
						return -1;
					else 
						return m1.getTitle().compareTo(m2.getTitle());
				}
			});
		}
		return movies;
	}
	
	/**Get ticket price of a ShowTime after discount
	 * @param showtime
	 * @param isStudent
	 * @param isSeniorCitizen
	 * @return Ticket
	 */
	public Ticket getTicketPrice(ShowTime showtime, boolean isStudent, boolean isSeniorCitizen) {
		this.getTopFiveMovies(true);
		return mTicketManager.getTicketPrice(showtime, isStudent, isSeniorCitizen);
	}
	
	/**
	 * Get a list of cinema based on cineplex
	 * @param cineplex
	 * @return list of cinema
	 */
	public ArrayList<Cinema> getCinemas(Cineplex cineplex) {
		// create a new list of cinema
		ArrayList<Cinema> cinemas = new ArrayList<>();
		
		for (Cinema c:mCinemas) {
			if(c.equals(cineplex)) 
				cinemas.add(c);
		}
		
		return cinemas;
	}
	
	public ArrayList<Cinema> getCinemas(boolean platinum) {
		ArrayList<Cinema> cinemas = new ArrayList<>();
		
		for (Cinema c:cinemas) {
			if (c.isPlatinum() == platinum) {
				cinemas.add(c);
			}
		}
		
		return cinemas;
	}

	/**
	 * Initialize all the data.
	 * Give all the data an empty value.
	 */
	private void initialize() {
		// default value for the counter of movie and showtime
		midCounter = 0; 
		sidCounter = 0;
		cidCounter = 0;
		
		// initialize the array list
		mStaffs    = new ArrayList<>();
		mMovies    = new ArrayList<>();
		mCinemas   = new ArrayList<>();
		mShowTimes = new ArrayList<>();
		mCustomers = new ArrayList<>();
		mBookingRecords = new ArrayList<>();
	}
	
	/**Check if a staff is authenticated
	 * @param staff
	 * @return boolean
	 */
	public boolean authenticate(Staff staff){
		return mStaffs.contains(staff);
	}
	
	/**Get current TicketManager
	 * @return TicketManager
	 */
	public TicketManager getTicketManager() {
		return mTicketManager;
	}
	
	
	@Override
	protected void load() throws IOException, JSONException, ParseException {
		initialize();
		
		// initialize ticket manager 
		// retrieve ticket price
		mTicketManager = new TicketManager();
		mTicketPrices  = mTicketManager.getTicketPrices();

		// retrieve staff data
		array = getData(FILE_STAFF);
		
		if (array.length()!=0) {
			for(pos=0;pos<array.length();pos++){
				staff = new Staff(array.getJSONObject(pos));
				mStaffs.add(staff);
			}
		}
				
		// retrieve movie data
		array = getData(FILE_MOVIE);
		
		if (array.length()!=0) {
			for(pos=0;pos<array.length();pos++) {
				movie = new Movie(array.getJSONObject(pos));
				
				mMovies.add(movie);
			}
			
			midCounter = mMovies.get(pos-1).getId();
		}

		// retrieve cinema data
		array = getData(FILE_CINEMA);
		
		if (array.length()!=0) {
			for(pos=0;pos<array.length();pos++){
				cinema = new Cinema(array.getJSONObject(pos));
				mCinemas.add(cinema);
			}
		}
			
		// retrieve show time data
		array = getData(FILE_SHOWTIME);

		if (array.length()!=0) {
			for(pos=0;pos<array.length();pos++){
				showtime = new ShowTime(array.getJSONObject(pos));

				movie = (Movie) getInstance(showtime.getMovie());
				movie.addShowTime(showtime);
				showtime.setMovie(movie);

				cinema = (Cinema) getInstance(showtime.getCinema());
				cinema.addShowTime(showtime);
				showtime.setCinema(cinema);
				
				mShowTimes.add(showtime);
			}
			
			sidCounter = mShowTimes.get(pos-1).getId();
		}
		
		// retrieve customer data
		array = getData(FILE_CUSTOMER);

		if (array.length()!=0) {
			for(pos=0;pos<array.length();pos++){
				customer = new Customer(array.getJSONObject(pos));			
				mCustomers.add(customer);
			}
			
			cidCounter = mCustomers.get(pos-1).getId();
		}
		
		// retrieve customer data
		array = getData(FILE_BOOKING);
		
		if (array.length()!=0) {
			for(pos=0;pos<array.length();pos++){
				booking  = new Booking(array.getJSONObject(pos));		

				customer = (Customer) getInstance(booking.getCustomer()); 
				showtime = (ShowTime) getInstance(booking.getShowtime());
				
				customer.addBookingRecord(booking);
				showtime.addOccupiedSeats(booking.getSeats());

				booking.setCustomer(customer);
				booking.setShowtime(showtime);
								
				movie = showtime.getMovie();
				movie.addTicketSales(booking.getSeats().size());

				mBookingRecords.add(booking);
			}
		}
	}

	@Override
	public boolean create(Model model) {
		if (model instanceof Movie) {
			model.setId(++midCounter);
			mMovies.add((Movie) model);			
			return writeFile(FILE_MOVIE, mMovies.toString());
			
		} else if (model instanceof Cinema) {
			mCinemas.add((Cinema) model);
			return writeFile(FILE_CINEMA, mCinemas.toString());
			
		} else if (model instanceof ShowTime) {
			model.setId(++sidCounter);
			
			ShowTime st = (ShowTime) model;
			st.getMovie().addShowTime(st);
			st.getCinema().addShowTime(st);
			
			mShowTimes.add(st);

			return writeFile(FILE_SHOWTIME, mShowTimes.toString());
		
		} else if (model instanceof Customer) {
			model.setId(++cidCounter);
			mCustomers.add((Customer) model);			
			return writeFile(FILE_CUSTOMER, mCustomers.toString());

		} else if (model instanceof Booking) {	
			Booking record = (Booking) model;
			record.getCustomer().addBookingRecord(record);
			record.getShowtime().addOccupiedSeats(record.getSeats());
			record.getShowtime().getMovie().addTicketSales(record.getSeats().size());
			mBookingRecords.add((Booking) model);			
			
			return writeFile(FILE_BOOKING, mBookingRecords.toString());
		}
		
		return mTicketManager.create(model);
	}

	@Override
	public boolean update(Model model) {
		if (model instanceof Movie) {
			// replace the new copy at the same location of the old copy
			movie = (Movie) model;
			index = mMovies.indexOf(movie);
			mMovies.set(index, movie);
			
			// update all the showtime of the info since
			for (ShowTime showtime: movie.getShowTimes()) {
				showtime.setMovie(movie);
			}
			
			return writeFile(FILE_MOVIE, mMovies.toString());
			
		} else if (model instanceof Cinema) {
			// replace the new copy at the same location of the old copy
			index = mCinemas.indexOf(model);
			mCinemas.set(index, (Cinema) model); 
			return writeFile(FILE_CINEMA, mCinemas.toString());
		} else if (model instanceof ShowTime) {
			ShowTime newCopy = (ShowTime) model;
			ShowTime oldCopy = (ShowTime) getInstance(model);		
			
			System.out.println("old copy:"+oldCopy.getId());
			System.out.println("new copy:"+newCopy.getId());
			// remove the showtime first from movie and cinema 
			oldCopy.getMovie().getShowTimes().remove(oldCopy);
			oldCopy.getCinema().getShowTimes().remove(oldCopy);
			
			// update the new showtime to movie and cinema showlist
			newCopy.getMovie().getShowTimes().add(newCopy);
			newCopy.getCinema().getShowTimes().add(newCopy);
			
			// replace the new copy at the same location of the old copy
			index = mShowTimes.indexOf(model);
			mShowTimes.set(index, (ShowTime) model); 
			return writeFile(FILE_SHOWTIME, mShowTimes.toString());
		} else if (model instanceof Booking) {
			return writeFile(FILE_BOOKING, mShowTimes.toString());

		}
		
		return mTicketManager.update(model);
	}

	@Override
	public boolean delete(Model model) {
		if (model instanceof Movie) {
			mMovies.remove(getInstance(model));
			return writeFile(FILE_MOVIE, mMovies.toString());
			
		} else if (model instanceof ShowTime) {
			showtime = (ShowTime) model;
			
			showtime.getMovie().removeShowTime(showtime);
			showtime.getCinema().removeShowTime(showtime);
			
			mShowTimes.remove(model);
			
			return writeFile(FILE_SHOWTIME, mShowTimes.toString());
		}
		
		return mTicketManager.delete(model);
	}

	@Override
	public Model getInstance(Model model) {
		if (model instanceof Movie) {
			if (mMovies.contains(model)) {
				index = mMovies.indexOf(model);
				return mMovies.get(index);
			}
		} else if (model instanceof Cinema) {
			if (mCinemas.contains(model)) {
				index = mCinemas.indexOf(model);
				return mCinemas.get(index);
			}
		} else if (model instanceof ShowTime) {
			if (mShowTimes.contains(model)) {
				index = mShowTimes.indexOf(model);
				return mShowTimes.get(index);
			}
		} else if (model instanceof Customer) {
			if (mCustomers.contains(model)) {
				index = mCustomers.indexOf(model);
				return mCustomers.get(index);
			}
		} else if (model instanceof Booking) {
			if (mBookingRecords.contains(model)) {
				index = mBookingRecords.indexOf(model);
				return mBookingRecords.get(index);
			}
		} 
		
		return mTicketManager.getInstance(model);
	}

}
