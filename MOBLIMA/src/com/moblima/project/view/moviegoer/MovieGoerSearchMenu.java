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
import com.moblima.project.model.ShowTime;
import com.moblima.project.view.BaseMenu;

public class MovieGoerSearchMenu extends BaseMenu {
	
	private Movie movie;
	private MovieGoerTicketMenu ticketMenu;

	public MovieGoerSearchMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager,
			ReviewManager mReviewManager, ShowTimeManager mShowTimeManager, TicketManager mTicketManager,
			StaffManager mStaffManager) {
		super(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
		ticketMenu = new MovieGoerTicketMenu(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
	}

	@Override
	public void displayMenu() {
		int choice = 0; // each menu manage their own choice integer
		
		do {
			printHeader("Search Movies");
			println(" 1. Search In Cinema");
			println(" 2. Ticket Booking");
			println(" 3. Back");
			println("");
			
			try {
				choice = readChoice(1, 3);
				
				switch (choice) {
					case 1:
						searchByCinema();
						break;
					case 2:
						ticketMenu.displayMenu();
						break;
				}			
			} catch (Exception e) {
				break;
			}
		} while (choice != 3);
	}

	private void searchByCinema() throws ExitException {
		Cinema cinema = chooseCinema();
		
		printHeader("Movie in " + cinema.getName());
		
		ArrayList<ShowTime> times = this.mShowTimeManager.getmShowTimes();
		
		for(ShowTime time : times){
			if(time.getCinemaId() == cinema.getId()){
				movie = mShowTimeManager.getMovie(time, mMovieManager);
				
				println(movie.getTitle() +"  " + time.getDateTimeStr());
			}
		}
	}

}
