package com.moblima.project.view.moviegoer;

import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;

import com.moblima.project.controller.CinemaManager;
import com.moblima.project.controller.MovieManager;
import com.moblima.project.controller.ReviewManager;
import com.moblima.project.controller.ShowTimeManager;
import com.moblima.project.controller.StaffManager;
import com.moblima.project.controller.TicketManager;
import com.moblima.project.model.Movie;
import com.moblima.project.model.Review;
import com.moblima.project.view.BaseMenu;

public class MoviesMenu extends BaseMenu{
	private Movie movie;
	
	public MoviesMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager,
			ReviewManager mReviewManager, ShowTimeManager mShowTimeManager, TicketManager mTicketManager, StaffManager mStaffManager) {
		super(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
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
			println("Options: ");
			println(" 1. Show Times"); 		// show movie showtime and prompt user for movie
			println(" 2. Give a Review");	// 
			println(" 3. Back");
			println("");
			
			try {
				choice = readChoice(1, 3);
				
				switch (choice) {
					case 1:
						break;
					case 2:
						giveReview();
						break;
				}			
			} catch (ExitException e) {
				break;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (choice != 3);
	}
	
	public void displayMovieDetails(){		
		printHeader(movie.getTitle() +" "+ movie.getRating().name());
		println("Casts   :  " + movie.getCastsStr());
		println("Director:  " + movie.getDirector());
		println("Synopsis:  " + movie.getSynopsis());
		println("Language:  " + movie.getLanguage().value());
		println("Runtime :  " + movie.getRunTime());
		println("Opening :  " + movie.getOpening());
		println("Rating  :  " + movie.getOverallRating() + "★");
		println("Reviews :  " + (movie.getReviews().isEmpty()?"Currently No Review":""));
		for (Review r: movie.getReviews()) 
			println(r.toDisplay());
		
		println("");
	}
	
	public void giveReview() throws ExitException, JSONException {
		Review review = new Review();
		review.setName(read("Name  :  "));
		review.setDescription(read("Review:  "));
		review.setRating(readInt("Rating", 1, 5));
		movie.addReview(review);
		
		if (mMovieManager.update(movie))
			println("Your review has been posted");
		else
			println("System error, please contact admin.");
	}

	public void displayReviews(Movie movie){
		printHeader("Movie Reviews and Ratings");
//		ArrayList<Review> reviews = this.mReviewManager.getMovieReviews(movie);
//		
//		for(Review r : reviews){
//			println(r.toDisplay());
//		}
	}
}
