package com.moblima.project.view.moviegoer;

import com.moblima.project.controller.CineplexManager;
import com.moblima.project.model.Cinema;
import com.moblima.project.model.Constant.Rating;
import com.moblima.project.model.Movie;
import com.moblima.project.view.BaseMenu;

public class SearchMenu extends BaseMenu{

	public SearchMenu(CineplexManager mCineplexManager) {
		super(mCineplexManager);
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
//		ArrayList<ShowTime> showTimes = cinema.getShowTimes();
//		Movie movie;
//		
//		for(ShowTime time : showTimes){
//			movie = (Movie)this.mMovieManager.getInstanceById(time.getMovie().getId());
//			println(movie.getTitle() + "  " + time.getDateTime());
//		}
	}
	
	public void displayMoviesByRating(Rating rating){
		
		println("Movies with rating " + rating.toString() + " :");
		
		for(Movie movie : this.mCineplexManager.getMovies()){
			if(movie.getRating().equals(rating)){
				println(movie.getTitle());
			}
		}
	}
}
