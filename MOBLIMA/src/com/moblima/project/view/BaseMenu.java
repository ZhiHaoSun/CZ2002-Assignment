package com.moblima.project.view;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.json.JSONException;

import com.moblima.project.controller.CinemaManager;
import com.moblima.project.controller.MovieManager;
import com.moblima.project.controller.ReviewManager;
import com.moblima.project.controller.ShowTimeManager;
import com.moblima.project.controller.StaffManager;
import com.moblima.project.controller.TicketManager;
import com.moblima.project.model.Movie;
import com.moblima.project.model.ShowTime;
import com.moblima.project.model.Cinema;
import com.moblima.project.model.Constant.*;

public abstract class BaseMenu {
	private Scanner sc;
	protected int choice;
	
	public MovieManager mMovieManager = null;
	public CinemaManager mCinemaManager = null;
	public ReviewManager mReviewManager = null;
	public ShowTimeManager mShowTimeManager = null;
	public TicketManager mTicketManager = null;
	public StaffManager mStaffManager = null;
	
	public BaseMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager, ReviewManager mReviewManager,
			ShowTimeManager mShowTimeManager, TicketManager mTicketManager, StaffManager mStaffManager) {
		this.sc = sc;
		this.mMovieManager = mMovieManager;
		this.mCinemaManager = mCinemaManager;
		this.mReviewManager = mReviewManager;
		this.mShowTimeManager = mShowTimeManager;
		this.mTicketManager = mTicketManager;
		this.mStaffManager = mStaffManager;
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
	
	//
	protected int readChoice(int min, int max) throws ExitException {		
		int c = 0;
		
		do {
			try {
				c = readInt("Choice ("+min+"~"+max+"): ");
			} catch (InputMismatchException ime) {
				sc.nextLine();
			}
		} while(!(c >= min && c <= max));
		
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
	
	//
	protected Movie chooseMovie() throws ExitException {
		ArrayList<Movie> movies = mMovieManager.getMovies();
		
		println("Choose Movie:");						
		
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
		
		println("Choose Show Times:");						
		
		for (int i=0, j=1; i<showTimes.size(); i++,j++)
			println(" "+j+". "+showTimes.get(i).getDateTimeStr());
		
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
	
	// Exception Class
	public class ExitException extends Exception{
		private static final long serialVersionUID = 1L;
	}
}
