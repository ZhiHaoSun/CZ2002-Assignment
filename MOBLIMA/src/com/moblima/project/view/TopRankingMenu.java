package com.moblima.project.view;

import java.util.ArrayList;

import com.moblima.project.controller.CineplexManager;
import com.moblima.project.model.Movie;

public class TopRankingMenu extends BaseMenu {

	public TopRankingMenu(CineplexManager mCineplexManager) {
		super();
	}
	
	@Override
	public void displayMenu() {
		choice = 0;
		
		do {
			printHeader("Top 5 Movies");
			println("Based on:");
			println(" 1. Ticket Sales");
			println(" 2. Overall Rating");
			println(" 3. Back");
			println("");
			
			try {
				choice = readChoice(1, 3);
				
				switch (choice) {
					case 1:
						//** Show Top 5 Movie by Ticket Sales
						showTopFiveMovies(false);
						break;
					case 2:
						//** Show Top 5 Movie by Overall Rating
						showTopFiveMovies(true);
						break;
				}			
			} catch (ExitException e) {
				break;
			}
		} while (choice != 3);
	}
	
	public void showTopFiveMovies(boolean byOverallRating) {
		ArrayList<Movie> movies = mCineplexManager.getTopFiveMovies(byOverallRating);
		
		int counter = 0;
		if (byOverallRating) {
			printHeader("Top 5 Movies by Overall Rating");
			
			for (Movie movie: movies) {
				String orating = String.format("%.1f", movie.getOverallRating());

				print(String.format("%-15s", movie.getTitle()));				
				print(String.format("(%-5s)", orating)); 

				if (counter == 5) break;
			}
		} else {
			printHeader("Top 5 Movies by Ticket Sales");
			
			for (Movie movie: movies) {
				print(String.format("%-15s", movie.getTitle())); 
				print(String.format("(%-5s)", movie.getTicketSales())); 

				if (counter == 5) break;
			}
		}
		
		
	}
}
