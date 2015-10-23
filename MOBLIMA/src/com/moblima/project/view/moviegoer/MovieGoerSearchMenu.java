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
import com.moblima.project.model.Constant.Rating;
import com.moblima.project.model.Movie;
import com.moblima.project.model.ShowTime;
import com.moblima.project.view.BaseMenu;
import com.moblima.project.view.BaseMenu.ExitException;

public class MovieGoerSearchMenu extends BaseMenu{

	public MovieGoerSearchMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager,
			ReviewManager mReviewManager, ShowTimeManager mShowTimeManager, TicketManager mTicketManager, StaffManager mStaffManager) {
		super(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
	}
	
	@Override
	public void displayMenu() {
		int choice = 0; // each menu manage their own choice integer

		do {
			printHeader("Options for searching");
			println(" 1. Search Movies by Cinema");
			println(" 2. View Cinemas");
			println(" 3. Search Movie by Rating");
			println(" 4. Quit");
			println("");
			
			try {
				choice = readChoice(1, 4);
				
				switch (choice) {
					case 1:
						Cinema cinema = this.chooseCinema();
						this.displayMoviesByCinema(cinema);
						break;
					case 2:
						break;
					case 3:
						Rating rating = this.chooseMovieRating();
						this.displayMoviesByRating(rating);
						break;
				}			
			} catch (ExitException e) {
				println("Go back.");
				break;
			}
		} while (choice != 4);
	}
	
	public void displayMoviesByCinema(Cinema cinema){
		ArrayList<ShowTime> showTimes = cinema.getShowTimes();
		Movie movie;
		
		for(ShowTime time : showTimes){
			movie = (Movie)this.mMovieManager.getInstanceById(time.getMovieId());
			println(movie.getTitle() + "  " + time.getDateTimeStr());
		}
	}
	
	public void displayMoviesByRating(Rating rating){
		
		println("Movies with rating " + rating.toString() + " :");
		
		for(Movie movie : this.mMovieManager.getMovies()){
			if(movie.getRating().equals(rating)){
				println(movie.getTitle());
			}
		}
	}
}
