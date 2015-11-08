package com.moblima.project.view.moviegoer;

import java.util.ArrayList;

import com.moblima.project.controller.CineplexManager;
import com.moblima.project.model.Cinema;
import com.moblima.project.model.Constant;
import com.moblima.project.model.Customer;
import com.moblima.project.model.Movie;
import com.moblima.project.model.Review;
import com.moblima.project.model.Seat;
import com.moblima.project.model.ShowTime;
import com.moblima.project.model.Booking;
import com.moblima.project.view.BaseMenu;

public class MoviesMenu extends BaseMenu{
	private Movie movie;

	public MoviesMenu(CineplexManager mCineplexManager) {
		super(mCineplexManager);
	}
	
	public void displayMenu(Movie movie) {
		this.movie = movie;
		displayMenu();
	}
	
	@Override
	public void displayMenu() {
		int choice = 0; // each menu manage their own choice integer
		
		do {
			displayMovieDetails();
			println("Options : ");
			println(" 1. Show Times"); 			// show movie showtime and prompt user for movie
			println(" 2. Movie Reviews"); 		// movie reviews
			println(" 3. Back");
			println("");
			
			try {
				choice = readChoice(1, 3);
				
				switch (choice) {
					case 1:
						displayMovieShowTimes();
						break;
					case 2:
						displayReviews();
						break;
				}			
			} catch (ExitException e) {
				break;
			}
		} while (choice != 3);
	}
	
	public void displayMovieDetails(){		
		printHeader(movie.getTitle() +" ("+ movie.getRating().name()+")");
		println("Casts   : " + movie.getCastsStr());
		println("Director: " + movie.getDirector());
		println("Synopsis: " + movie.getSynopsis());
		println("Language: " + movie.getLanguage().value());
		println("Status  : " + movie.getStatus().value());
		println("Opening : " + (movie.getOpening().equals("TBA") ? "To Be Announced" : movie.getOpening()));
		println("Run Time: " + (movie.getRunTime().equals("TBA") ? "To Be Announced" : movie.getRunTime()));
		println("Rating  : " + movie.getOverallStarRating() +"("+ String.format("%.1f", movie.getOverallRating()) +")");
	}
	
	public static final int ASCII_CODE_A = 65;
	
	private int row, col;
	private ShowTime showtime;
	private boolean[][] seats;
	private ArrayList<Seat> selectedSeats;
	
	// Movie Details Option #1: ShowTimes
	public void displayMovieShowTimes() {
		int choice = 0; // each menu manage their own choice integer
		
		try {
			showtime = chooseShowTime(movie);
			
			do {
				initializeSeats(showtime);
				displaySeats();
				
				println("Options : ");
				println(" 1. Select Seats"); 			// show movie showtime and prompt user for movie
				println(" 2. Back to Movie Details");				
				
				choice = readChoice(1, 2);
				
				println("");

				switch (choice) {
					case 1:
						displaySeats(showtime, chooseSeats(showtime));
						
						if (confirm("Do you want to book the selected seats?")) {
							printShowTimeInfo(showtime);
							println("Please fill in the following details: ");
							Customer customer = new Customer();
							customer.setName(read("Your name: "));
							customer.setEmail(read("Your email address: "));
							customer.setPhone(read("Your phone number: "));
							
							if (mCineplexManager.getCustomers().contains(customer)) 
								customer = (Customer) mCineplexManager.getInstance(customer);
							else
								mCineplexManager.create(customer);
						
							Booking booking = new Booking(customer, showtime);
							booking.setSeats(selectedSeats);	
							if (mCineplexManager.create(booking)) {
								println("Successful booked the selected seats");
							} else {
								println("Unable to book the selected seats.");
							}
						}
						break;
				}			
			} while (choice != 3);
		} catch (ExitException e) {}
	}
	
	public void printShowTimeInfo(ShowTime st) {
		Movie  m = st.getMovie();
		Cinema c = st.getCinema();
		
		// Goosebumps (PG)
		// Showing on Sat 7 Nov 9:45PM
		// AMK Hub - SCREEN 1
		println(m.getTitle()+" ("+m.getRating().name()+")");		
		println("Showing on "+st.getDateTime());
		println(c.getCineplex().value() +" - "+ c.getName());
	}
	
	// Show Times Option #1: Select Seats (Book For Seats)
	protected void initializeSeats(ShowTime st) {
		selectedSeats = new ArrayList<>();
		seats = new boolean[Constant.rowNumber][Constant.colNumber];
		
		// set all the occupied seats
		for (Seat s:st.getOccupiedSeats()) 
			seats[s.getRow()][s.getCol()] = true;
	}
	
	protected void displaySeats() {
		displaySeats(showtime, selectedSeats);
	}
	
	protected void displaySeats(ArrayList<Seat> selectedSeats) {
		displaySeats(showtime, selectedSeats);
	}
	
	protected void displaySeats(ShowTime st, ArrayList<Seat> selectedSeats) {
		Movie  movie  = st.getMovie();
		Cinema cinema = st.getCinema();
		
		println("");
		println(movie.getTitle()+" ("+movie.getRating()+")");
		println("Showing on "+st.getDateTime());
		println(cinema.getCineplex().value()+" - "+cinema.getName());
		
		println("");
		println(" Select Seats");
		println("**************");
		println("");
		println("           |      Screen       |");
		println("           ---------------------");
		print("\n        ");
		
		int col, row;
		
		for(col=0; col<Constant.colNumber; col++){
			print(" " + (col+1) +" ");
		}
		
		println("");
		
		for(row=0; row<Constant.rowNumber; row++){
			print("   "+(row+1) +"("+String.valueOf(Character.toChars(ASCII_CODE_A+row))+") ");
			
			for(col=0; col<Constant.colNumber; col++) {
				if(seats[row][col]){
					if (selectedSeats.contains(new Seat(row, col)))
						print("[#]");
					else
						print("[x]");
				}else{
					print("[_]");
				}				
			}
			println("");
		}
		
		println("");
		println("                ----------");
		println("                |Entrance|\n");
		println("([#] Your seat  [_] Avaliable  [x] Sold)");
	}
	
	protected ArrayList<Seat> chooseSeats(ShowTime showtime) throws ExitException {
		println("Please choose your seat(s).");
		
		do {
			do{
				row = readInt("Please input row number" , 1, Constant.rowNumber);
				col = readInt("Please input col number" , 1, Constant.colNumber);
				
				if (seats[--row][--col]) 
					println("Already been taken/selected please choose another seats.");
				else break;
			}while(true);
			
			seats[row][col] = true;
			selectedSeats.add(new Seat(row, col));

			print("Selected Seats: ");
			for (Seat s:selectedSeats) {
				print(s.getSeat()+" ");
			}
			
			println("");

		} while(confirm("continue select seat?"));
		
		return selectedSeats;
	}

	// Movie Details Option #2: Movie Reviews
	public void displayReviews(){
		int choice = 0; // each menu manage their own choice integer
		
		do {
			printHeader("Reviews on " + movie.getTitle());
			if(movie.getReviews().isEmpty()){
				println("No reviews Yet.");
			} else{
				for(Review r : movie.getReviews()){
					println("Name       : " + r.getName());
					println("Rating     : " + r.getStarRating());
					println("Content    : " + r.getDescription());
					println("Posted Date: " + r.getPostedDate());
					println("");
				}
			}
			
			println("Options : ");
			println(" 1. Write Your Review"); 		// show movie showtime and prompt user for movie
			println(" 2. Back");
			println("");
			
			try {
				choice = readChoice(1, 2);
				
				switch (choice) {
					case 1:
						giveReview();
						break;
				}			
			} catch (ExitException e) {
				break;
			}
		} while (choice != 2);
	}
	
	// Review Option #1: Write Your Review
		public void giveReview() throws ExitException {
			Review review = new Review();
			review.setName(read("Your name: "));
			review.setDescription(read("Your content: "));
			review.setRating(readInt("Your rating", 1, 5));
			
			movie.addReview(review);
			
			if (mCineplexManager.update(movie))
				println("Your review has been posted");
			else
				println("Unable to add your review, please contact the admin.");
		}
}
