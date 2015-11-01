package com.moblima.project.view.moviegoer;

import java.util.ArrayList;
import java.util.Scanner;

import com.moblima.project.controller.CinemaManager;
import com.moblima.project.controller.MovieManager;
import com.moblima.project.controller.ReviewManager;
import com.moblima.project.controller.ShowTimeManager;
import com.moblima.project.controller.StaffManager;
import com.moblima.project.controller.TicketManager;
import com.moblima.project.model.Cinema;
import com.moblima.project.model.Movie;
import com.moblima.project.model.Review;
import com.moblima.project.view.BaseMenu;
import com.moblima.project.view.BaseMenu.ExitException;

public class MovieGoerMenu extends BaseMenu {
	
	private MovieGoerMovieMenu movieMenu;
//	private SearchMenu searchMenu;
	private MovieGoerTicketMenu ticketMenu;
	
	public MovieGoerMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager,
			ReviewManager mReviewManager, ShowTimeManager mShowTimeManager, TicketManager mTicketManager, StaffManager mStaffManager) {
		super(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
		movieMenu = new MovieGoerMovieMenu(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
//		searchMenu = new SearchMenu(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
		ticketMenu = new MovieGoerTicketMenu(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
	}

	public void displayMenu() {
		choice = 0; // each menu manage their own choice integer
		
		do {
			printHeader("Welcome to MOBLIMA");
			println(" 1. Movie Listing");
			println(" 2. Search Movie");
			println(" 3. Ticket Booking");
			println(" 4. Back");
			println("");
			
			try {
				choice = readChoice(1, 4);
				
				switch (choice) {
					case 1:
						movieMenu.displayMenu();
						break;
					case 2:
//						this.searchMenu.displayMenu();
						break;
					case 3:
						//** View Book History 
						this.ticketMenu.displayMenu();
						break;
				}			
			} catch (Exception e) {
				break;
			}
		} while (choice != 4);
	}
	
	public void displayMovieListing() throws ExitException {
		printHeader("Movies");
//		movieMenu.displayMenu(chooseMovie(""));
	}
	
	public void displayAllMovies(){
		printHeader("All Movies");
		
		ArrayList<Movie> movies = this.mMovieManager.getMovies();
		
		println("Movies List.");
		
		for(Movie movie : movies){
			println(movie.getTitle());
		}
	}
	
	public void displayCinemas(){
		ArrayList<Cinema> cinemas = this.mCinemaManager.getmCinemas();
		
		println("Id    Title    Opening     Runtime    Director");
		for(int i=0;i<cinemas.size();i++){
			println(cinemas.get(i).toDisplay());
		}
	}
}


