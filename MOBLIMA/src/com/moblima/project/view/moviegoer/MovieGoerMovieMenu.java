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

public class MovieGoerMovieMenu extends BaseMenu {
	
	private Movie movie;
	
	private MovieGoerSearchMenu searchMenu;

	public MovieGoerMovieMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager,
			ReviewManager mReviewManager, ShowTimeManager mShowTimeManager, TicketManager mTicketManager,
			StaffManager mStaffManager) {
		super(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
		searchMenu = new MovieGoerSearchMenu(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
	}

	@Override
	public void displayMenu() {
		choice = 0; // each menu manage their own choice integer
		
		do {
			printHeader("Movie Listing");
			println(" 1. List all Movies");
			println(" 2. List Top 5 Movies");
			println(" 3. View Movie Details");
			println(" 4. Add Review");
			println(" 5. Search Movies");
			println(" 6. Back");
			println("");
			
			try {
				choice = readChoice(1, 6);
				
				switch (choice) {
					case 1:
						displayAllMovies();
						break;
					case 2:
						displayTopMovies();
						break;
					case 3:
						movie = chooseMovie();
						
						displayMovieDetails(movie);
						break;
					case 4:
						addReview();
						break;
					case 5:
						searchMenu.displayMenu();
						break;
				}			
			} catch (Exception e) {
				break;
			}
		} while (choice != 6);
	}

	public void displayAllMovies(){
		printHeader("All Movies");
		
		ArrayList<Movie> movies = this.mMovieManager.getMovies();
		
		for(Movie movie : movies){
			println(movie.getTitle());
		}
	}
	
	public void displayTopMovies() {
		printHeader("Top 5 Movies");
		
		ArrayList<Movie> movies = this.mMovieManager.getTopMovies();
		
		int size = movies.size() < 5 ? movies.size() : 5;
		
		for(int i=0;i<size;i++){
			println(movies.get(i).getTitle());
		}
	}
	
	public void displayMovieDetails(Movie movie){
		printHeader("Details of " + movie.getTitle());
		
		println("Movie Title: " + movie.getTitle());
		println("Movie Synopsis: " + movie.getSynopsis());
		println("Movie Director: " + movie.getDirector());
		println("Movie Casts: " + movie.getCastsStr());
		println("Movie Opening: " + (movie.getOpening().equals("TBA") ? "To Be Announced" : movie.getOpening()));
		println("Movie Run Time: " + (movie.getRunTime().equals("TBA") ? "To Be Announced" : movie.getRunTime()));
		println("Movie Overall Rating: " + movie.getOverallRating());
		println("Movie Normal Price" + movie.getPrice());
		println("Movie Status: " + movie.getStatus());
		println("Movie Level: " + movie.getRating());
		println("Movie Language: " + movie.getLanguage());
		
		printHeader("Reviews on " + movie.getTitle());
		if(movie.getReviews().size() == 0){
			println("No reviews Yet.");
		} else{
			for(Review r : movie.getReviews()){
				println("Name: " + r.getName());
				println("Content: " + r.getDescription());
				println("Rating: " + r.getRating());
				println("Posted Date: " + r.getPostedDate());
				println("");
			}
		}
		
	}
	
	public void addReview() throws ExitException, JSONException{
		Movie movie = chooseMovie();
		
		Review review = new Review();
		review.setName(read("Your name: "));
		review.setDescription(read("Your content: "));
		review.setRating(readInt("Your rating(From 0 to 5): "));
		
		movie.addReview(review);
		if(mMovieManager.update(movie)){
			println("Review added successfully!");	
		} else{
			println("Review added failed.");
		}
	}
}