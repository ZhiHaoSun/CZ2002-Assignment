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
	
	private MovieGoerMoviesMenu movieMenu;
	
	public MovieGoerMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager,
			ReviewManager mReviewManager, ShowTimeManager mShowTimeManager, TicketManager mTicketManager, StaffManager mStaffManager) {
		super(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
		movieMenu = new MovieGoerMoviesMenu(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
	}

	public void displayMenu() {
		int choice = 0; // each menu manage their own choice integer

		do {
			printHeader("Customer Options: ");
			println(" 1. View movies");
			println(" 2. View Cinemas");
			println(" 3. View Tickets");
			println(" 4. Go back");
			println("");
			
			try {
				choice = readChoice(1, 3);
				
				switch (choice) {
					case 1:
						this.displayAllMovies();
						this.movieMenu.displayMenu();
						break;
					case 2:
						this.displayCinemas();
						break;
					case 3:
						break;
				}			
			} catch (ExitException e) {
				println("Go back.");
				break;
			}
		} while (choice != 3);
	}
	
	public void displayAllMovies(){
		ArrayList<Movie> movies = this.mMovieManager.getMovies();
		
		println("Id    Title    Opening     Runtime    Director");
		for(int i=0;i<movies.size();i++){
			println(movies.get(i).toDisplay());
		}
	}
	
	public void displayCinemas(){
		ArrayList<Cinema> cinemas = this.mCinemaManager.getmCinemas();
		
		println("Id    Title    Opening     Runtime    Director");
		for(int i=0;i<cinemas.size();i++){
			println(cinemas.get(i).toDisplay());
		}
	}
	
	public void displayAllReviews(){
		ArrayList<Review> reviews = this.mReviewManager.getReviews();
		
		println("Id    Rating    Description            Date");
		for(int i=0;i<reviews.size();i++){
			println(reviews.get(i).toDisplay());
		}
	}
}


