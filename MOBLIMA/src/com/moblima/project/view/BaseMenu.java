package com.moblima.project.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.moblima.project.controller.CinemaManager;
import com.moblima.project.controller.MovieManager;
import com.moblima.project.controller.ReviewManager;
import com.moblima.project.controller.ShowTimeManager;
import com.moblima.project.controller.StaffManager;
import com.moblima.project.controller.TicketManager;
import com.moblima.project.model.Cinema;
import com.moblima.project.model.Constant;
import com.moblima.project.model.Constant.Cineplex;
import com.moblima.project.model.Constant.Language;
import com.moblima.project.model.Constant.Rating;
import com.moblima.project.model.Constant.Status;
import com.moblima.project.model.Movie;
import com.moblima.project.model.Seat;
import com.moblima.project.model.ShowTime;

public abstract class BaseMenu {
	private Scanner sc;
	protected int choice;
	
	public MovieManager mMovieManager = null;
	public CinemaManager mCinemaManager = null;
	public ReviewManager mReviewManager = null;
	public ShowTimeManager mShowTimeManager = null;
	public TicketManager mTicketManager = null;
	public StaffManager mStaffManager = null;
	
	public BaseMenu(Scanner sc) {
		this.sc = sc;
	}
	
	public BaseMenu(Scanner sc, StaffManager mStaffManager) {
		this(sc);
		this.mStaffManager = mStaffManager;
	}
	public BaseMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager, ReviewManager mReviewManager,
			ShowTimeManager mShowTimeManager, TicketManager mTicketManager, StaffManager mStaffManager) {
		this(sc, mStaffManager);
		this.mMovieManager = mMovieManager;
		this.mCinemaManager = mCinemaManager;
		this.mReviewManager = mReviewManager;
		this.mShowTimeManager = mShowTimeManager;
		this.mTicketManager = mTicketManager;
	}

	public abstract void displayMenu();
	
	protected String read(String message) {
		print(message);
		return sc.next();
	}
	
	protected int readInt(String message) {
		print(message);		
		return sc.nextInt();
	}
	
	protected int readInt(String label, int min, int max) throws ExitException {		
		int c = 0;
		
		do {
			try {
				c = readInt(label+" ("+min+"~"+max+"):  ");
			} catch (InputMismatchException ime) {
				sc.nextLine();
			}
		} while(!(c >= min && c <= max));
		
		return c;
	}
	
	protected int readChoice(int min, int max) throws ExitException {
		int c = readInt("Choice ", min, max);
		
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
		println("");
		println("    "+title);
		for (int i=0; i<title.length()+8; i++)
			print("-");
		println("");
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
	
	protected Movie chooseMovie() throws ExitException {
		return chooseMovie("Choose Movie:");
	}
	
	protected Movie chooseMovie(String title) throws ExitException {
		ArrayList<Movie> movies = mMovieManager.getMovies();
		
		println(title);						
		
		for (int i=0, j=1; i<movies.size(); i++,j++)
			println(" "+j+". "+movies.get(i).getTitle());
		
		println(" "+(movies.size()+1)+". Back");
		println("");
		
		int index = readChoice(1, movies.size()+1)-1;
		
		return movies.get(index);
	}
	
	protected Cinema chooseCinema() throws ExitException {
		ArrayList<Cinema> cinemas = this.mCinemaManager.getmCinemas();
		
		println("Choose Cinema:");						
		
		for (int i=0, j=1; i<cinemas.size(); i++,j++)
			println(" "+j+". "+cinemas.get(i).getName());
		
		println(" "+(cinemas.size()+1)+". Back");
		println("");
		
		int index = readChoice(1, cinemas.size()+1)-1;
		
		return cinemas.get(index);
	}
	
	protected ShowTime chooseShowTime() throws ExitException {
		ArrayList<ShowTime> showTimes = this.mShowTimeManager.getmShowTimes();
		ShowTime time;
		
		println("Choose Show Times:");						
		
		for (int i=0, j=1; i<showTimes.size(); i++,j++){
			time = showTimes.get(i);
			println(" "+j+". "+ ((Cinema)mCinemaManager.getInstanceById(time.getCinemaId())).getName() + "  " + ((Movie)mMovieManager.getInstanceById(time.getMovieId())).getTitle() + "  " + time.getDateTimeStr());
		}
		
		println(" "+(showTimes.size()+1)+". Back");
		println("");
		
		int index = readChoice(1, showTimes.size()+1)-1;
		
		return showTimes.get(index);
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
		int length = Status.values().length;
		
		println("Choose Cineplex:");						
		
		for (int i=0, j=1; i<length; i++,j++)
			println(" "+j+". "+Cineplex.values()[i].value());
		
		println(" "+(length+1)+". Back");
		println("");
		
		int index = readChoice(1, length+1) -1;
		return Cineplex.values()[index];
	}
	
	protected void displaySeats(Boolean[][] seats){
		int i,j;
		StringBuilder builder = new StringBuilder("   ");
		
		for(j=1;j<=Constant.colNumber;j++){
			builder.append("" + j +" ");
		}
		builder.append("\n");
		
		for(i=1;i<=Constant.rowNumber;i++){
			builder.append("" + i + "  ");
			for(j=1;j<=Constant.colNumber;j++){
				if(seats[i][j]){
					builder.append("# ");
				}else{
					builder.append("_ ");
				}
			}
			builder.append("\n");
		}
		
		printHeader("Seat Arrangement");
		
		println(builder.toString());
		println("");
	}
	
	protected Seat chooseSeat(ShowTime time) throws ExitException {
		Seat seat;
		int row, col;
		Boolean[][] seats = mShowTimeManager.getSeats(time, mTicketManager);
		
		printHeader("Choose An Empty Seat:");
		println("");
		
		this.displaySeats(seats);
		
		println("Please choose an empty seat. (# means taken. _ means empty)");
		
		do{
			row = readInt("Please select row number: " , 1, Constant.rowNumber + 1);
			col = readInt("Please select col number: " , 1, Constant.colNumber + 1);
		}while(seats[row][col]);
		
		println("Seat (" + row + "," + col + ") is selected.");
		
		seat = new Seat(col , row, time);
		return seat;
	}
	
	// Exception Class
	public class ExitException extends Exception{
		private static final long serialVersionUID = 1L;
	}
}
