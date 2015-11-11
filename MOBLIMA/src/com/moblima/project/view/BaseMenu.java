package com.moblima.project.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

import com.moblima.project.controller.CineplexManager;
import com.moblima.project.controller.TicketManager;
import com.moblima.project.model.Cinema;
import com.moblima.project.model.Constant;
import com.moblima.project.model.Constant.Cineplex;
import com.moblima.project.model.Constant.Language;
import com.moblima.project.model.Constant.MovieType;
import com.moblima.project.model.Constant.Rating;
import com.moblima.project.model.Constant.Status;
import com.moblima.project.model.Movie;
import com.moblima.project.model.ShowTime;

public abstract class BaseMenu {
	private Scanner sc;
	protected int choice;
	
	public TicketManager mTicketManager = null;
	
	protected CineplexManager mCineplexManager;
	
	public BaseMenu() {
		sc = new Scanner(System.in);
		sc.useDelimiter("\\n");
	}
	
	public BaseMenu(Scanner sc) {
		this.sc = sc;
	}
	
	public BaseMenu(CineplexManager mCineplexManager) {
		this();
		this.mCineplexManager = mCineplexManager;
	}

	public abstract void displayMenu();
	
	/**
	 * This method will read a String input with label
	 * @param label	is the message to be printed when asking for input
	 * @return the entered String input
	 */
	protected String read(String label) {
		String input = "";
		
		do{
			print(label);
			input = sc.nextLine();
		}while(input.trim().equals(""));
		
		return input;
	}
	
	public void readNextLine() {
		println("Press ENTER key to continue...");
		sc.nextLine();
	}
	
	/**
	 * This method will read a Double value with label
	 * @param label	is the message to be printed when asking for input
	 * @return the entered Integer input
	 */
	protected double readDouble(String label) {
		double c = 0;
		
		do {
			try {
				c = Double.parseDouble(read(label));
				break;
			} catch (NumberFormatException ime) {
				println("Please input a decimal number.");
			}
		} while(true);
		
		return c;
	}
	
	/**
	 * This method will read an Integer value with label
	 * @param label	is the message to be printed when asking for input
	 * @return the entered Integer input
	 */
	protected int readInt(String label) {
		return Integer.parseInt(read(label));
	}

	/**
	 * This method will only read in a range of Integer value with label
	 * @param label is the message to be printed when asking for input
	 * @param min	is the minimum range to be read in
	 * @param max	is the maximum range to be read in
	 * @return an Integer value between min and max
	 */
	protected int readInt(String label, int min, int max) {		
		int c = 0;
		
		do {
			try {
				c = readInt(label+" ("+min+"~"+max+"): ");
				
			} catch (NumberFormatException ime) {
				println("Please input an Integer value.");
			}
		} while(!(c >= min && c <= max));
		
		return c;
	}
	
	/**
	 * This method will only read in a range of Integer value with back/exit function
	 * @param min	is the minimum range to be read in
	 * @param max	is the maximum range to be read in
	 * @return an Integer value between min and (max-1)
	 * @throws ExitException when the max value is selected
	 */
	protected int readChoice(int min, int max) throws ExitException {
		return readChoice("Choice", min, max);
	}
	
	/**
	 * This method will only read in a range of Integer value with 
	 * customize label and back/exit function
	 * @param label is the message to be printed when asking for input
	 * @param min	is the minimum range to be read in
	 * @param max	is the maximum range to be read in
	 * @return an Integer value between min and (max-1)
	 * @throws ExitException when the max value is selected
	 */
	protected int readChoice(String label, int min, int max) throws ExitException {
		int c = readInt(label, min, max);
		
		if (c == max) throw new ExitException();
		
		return c;
	}
	
	/**
	 * Confirmation Message
	 * --------------------
	 * This method only accept 'y' or 'n' as input
	 * return 	 true  if input is 'y' or
	 * return 	 false if input is 'n' 
	 * Otherwise it will repeatedly prompt user for input
	 * */
	protected boolean confirm(String message) {
		while (true) {
			String in = read(message+ " (Y/N): ").toLowerCase();
			if (in.equals("y"))
				return true;
			else if (in.equals("n"))
				return false;
		}
	}
	
	/**
	 * This method will only read in a format of the date with label
	 * @param label is the message to be printed when asking for input
	 * @return Date when a correct format if entered, Otherwise keep prompting
	 */
	protected Date readDate(String label) {
		return readDate(label, "");
	}

	protected Date readDate(String label, String format) {
		SimpleDateFormat sdf;
		
		if (format.isEmpty()) { // default value
			sdf = Constant.dateFormatShort;
			format = Constant.FORMAT_DATE_SHORT;
		} else {
			sdf = new SimpleDateFormat(format);
		}
		
		do {
			try {
				String date = read(label+" ("+format+"): ");
				return sdf.parse(date);
			} catch (ParseException ime) {
				println("Please enter a correct date format");
				sc.nextLine();
			}
		} while(true);
	}
	
	/**
	 * This method will only read in a format of the time with label
	 * @param label is the message to be printed when asking for input
	 * @return Date when a correct format if entered, Otherwise keep prompting
	 */
	protected Date readTime(String label) {		
		do {
			try {
				String time = read(label+" ("+Constant.FORMAT_TIME_CLOCK+"): ");
				return Constant.clockFormat.parse(time);
			} catch (ParseException ime) {
				println("Please enter a correct time format");
				sc.nextLine();
			}
		} while(true);
	}
	
	protected Movie chooseMovie() throws ExitException {
		return chooseMovie("Choose Movie:");
	}
	
	protected Movie chooseMovie(String label) throws ExitException {
		return chooseMovie(label, mCineplexManager.getMovies());
	}
	
	protected Movie chooseMovie(String label, ArrayList<Movie> movies) throws ExitException {
		if (!label.isEmpty())
			println(label);						
		
		for (int i=0, j=1; i<movies.size(); i++,j++)
			println(" "+j+". "+movies.get(i).getTitle()+" ("+movies.get(i).getMovieType().value()+")");
		
		println(" "+(movies.size()+1)+". Back");
		println("");
		
		int index = readChoice(1, movies.size()+1)-1;
		
		return movies.get(index).clone();
	}
	
	protected Language chooseLanguage() throws ExitException {
		int length = Language.values().length;
		
		println("Choose Language:");						
		
		for (int i=0, j=1; i<length; i++,j++)
			println(" "+j+". "+Language.values()[i].value());
		
		println(" "+(length+1)+". Back");
		println("");

		int index = readChoice(1, length+1) -1;
		return Language.values()[index];
	}
	
	protected Rating chooseMovieRating() throws ExitException {
		int length = Rating.values().length;
		
		println("Choose Movie Rating:");						
		
		for (int i=0, j=1; i<length; i++,j++)
			println(" "+j+". "+Rating.values()[i]);
		
		println(" "+(length+1)+". Back");
		println("");
		
		int index = readChoice(1, length+1) -1;
		return Rating.values()[index];
	}
	
	protected MovieType chooseMovieType() throws ExitException {
		return chooseMovieType("Choose Movie Type:");
	}
	
	protected MovieType chooseMovieType(String label) throws ExitException {
		int length = MovieType.values().length;
		
		println(label);						
		
		for (int i=0, j=1; i<length; i++,j++)
			println(" "+j+". "+MovieType.values()[i].value());
		
		println(" "+(length+1)+". Back");
		println("");
		
		int index = readChoice(1, length+1) -1;
		return MovieType.values()[index];
	}
	
	protected Status chooseMovieStatus() throws ExitException {
		int length = Status.values().length;
		
		println("Choose Movie Status:");						
		
		for (int i=0, j=1; i<length; i++,j++)
			println(" "+j+". "+Status.values()[i].value());
		
		println(" "+(length+1)+". Back");
		println("");
		
		int index = readChoice(1, length+1) -1;
		return Status.values()[index];
	}
	
	protected Cineplex chooseCineplex() throws ExitException {
		int length = Cineplex.values().length;
		
		println("Choose Cineplex:");						
		
		for (int i=0, j=1; i<length; i++,j++)
			println(" "+j+". "+Cineplex.values()[i].value());
		
		println(" "+(length+1)+". Back");
		println("");
		
		int index = readChoice(1, length+1) -1;
		return Cineplex.values()[index];
	}
	
	protected Cinema chooseCinema() throws ExitException {
		return chooseCinema(mCineplexManager.getCinemas());
	}
	
	protected Cinema chooseCinema(Cineplex cineplex) throws ExitException {
		return chooseCinema(mCineplexManager.getCinemas(cineplex));
	}
	
	protected Cinema chooseCinema(ArrayList<Cinema> cinemas) throws ExitException {		
		println("Choose Cinema:");						
		
		for (int i=0, j=1; i<cinemas.size(); i++,j++) {
			Cinema c = cinemas.get(i);
			print(" "+j+". "+c.getName());
			print(c.isPlatinum()?" (Platinum)\n":"\n");
		}
		
		println(" "+(cinemas.size()+1)+". Back");
		println("");
		
		int index = readChoice(1, cinemas.size()+1)-1;
		
		return cinemas.get(index).clone();
	}
	
	///** Start: For Show Time **//**/*	
	protected ShowTime chooseShowTime() throws ExitException {
		return chooseShowTime(chooseMovie());
	}
	
	protected ShowTime chooseShowTime(Movie movie) throws ExitException {
		// select the movie ShowTime to be shown
		ArrayList<ShowTime> mMovieShowTimes = new ArrayList<>(movie.getShowTimes());
		
		printHeader("ShowTime of "+movie.getTitle());

		if (!mMovieShowTimes.isEmpty()) {
			// Once the filter is done
			// Display the ShowTime according to date & time		
			// initialize the variables going to be used
			String prevDate = "";		// keep track of the previous ShowTime's date been printed
			boolean platinum = true;
			Cineplex cineplex = null;
			int pos, row = 1, col = 0;
			
			// sort the ShowTime according to the date then time
			Collections.sort(mMovieShowTimes);
//			for (ShowTime st: mMovieShowTimes) 
//				println(st.getCinema().getCineplex().name()+" "+st.getCinema().isPlatinum()+" "+st.getCinema().getName()+" "+st.getDateTime());

			// start doing the printing of the showtime
			for (pos=0; pos<mMovieShowTimes.size(); pos++,row++) {
				
				// retrieve ShowTime from list
				ShowTime showtime = mMovieShowTimes.get(pos);
				
				if (cineplex == null || showtime.getCinema().isPlatinum() != platinum || cineplex != showtime.getCinema().getCineplex()) {
					cineplex = showtime.getCinema().getCineplex();
					
					if (pos != 0) println("");
					
					if (showtime.getCinema().isPlatinum()) {
						printSubHeader(cineplex.value() + " (Platinum Suite)");
					} else {
						printSubHeader(cineplex.value());
					}					
					
					col = 0;
					platinum = showtime.getCinema().isPlatinum();
					prevDate = "";
				} else {
					if (col == 4) {
						println(""); 
						col = 0;
					} else if (!prevDate.equals(showtime.getFormattedDate())) {
						println("");
					}
				}
				
				// print the ShowTime Date
				// when prevDate is empty (no date had been printed yet)
				// or 	prevDate is not equal to the current ShowTime's date 
				// (which mean the current showtime is another date)
				if (prevDate.isEmpty() || !prevDate.equals(showtime.getFormattedDate())) {
					// store the showtime date
					prevDate = showtime.getFormattedDate();
					
					
					// display the date of the showtime
					println("\n "+prevDate);
					
					// reset the col to 0 when the date change
					col = 0;
				}
				
				// display time of the movie
				print("    ("+row+") "+showtime.getFormattedTime());
				
				// pre-increment the col counter 
				// and reset the col counter
				// when col counter is equal to 5
				col++;
			}
			
			// ask user to input the choice of the showtime
			println("\n\n ("+(mMovieShowTimes.size()+1)+") Cancel");
			println("");
			
			pos = readChoice(1, mMovieShowTimes.size()+1) -1;

			// return the selected showtime back
			return mMovieShowTimes.get(pos).clone();
		} else {
			println("");
			println("  No Show Time in this Cineplex");
			throw new ExitException();
		}
	}
	
	///** End: For Show Time **//**/*
	
	/**
	 * shortcut for 
	 * - System.out.print(); 
	 * - System.out.println(); 
	 */
	// Method to print header with default style
	protected void printHeader(String title){
		println("");
		println("    "+title);
		for (int i=0; i<title.length()+8; i++)
			print("-");
		println("");
	}

	// Method to print header with default style
	protected void printSubHeader(String title){
		print("\n");
		for (int i=0; i<title.length()+8; i++)
			print("=");
		println("");
		println("    "+title);
		for (int i=0; i<title.length()+8; i++)
			print("=");
		print("\n");
	}
		
	// This method was created to replace System.out.print
	// for Better Readability
	protected void print(String message) {
		System.out.print(message);
	}
	
	// This method was created to replace System.out.println
	// for Better Readability
	protected void println(String message) {
		System.out.println(message);
	}
	
	// Exception Class
	public class ExitException extends Exception{
		private static final long serialVersionUID = 1L;
	}
}
