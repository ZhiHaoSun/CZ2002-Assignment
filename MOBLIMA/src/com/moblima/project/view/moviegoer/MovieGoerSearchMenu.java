package com.moblima.project.view.moviegoer;

import com.moblima.project.controller.CineplexManager;
import com.moblima.project.model.Cinema;
import com.moblima.project.model.Movie;
import com.moblima.project.view.BaseMenu;

public class MovieGoerSearchMenu extends BaseMenu {
	
	private Movie movie;
	private MovieGoerTicketMenu ticketMenu;

	public MovieGoerSearchMenu(CineplexManager mCineplexManager) {
		super(mCineplexManager);
		ticketMenu = new MovieGoerTicketMenu(mCineplexManager);
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
		
//		ArrayList<ShowTime> times = this.mShowTimeManager.getShowTimes();
		
//		for(ShowTime time : times){
//			if(time.getCinema().getId() == cinema.getId()){
//				movie = mShowTimeManager.getMovie(time, mMovieManager);
//				
//				println(movie.getTitle() +"  " + time.getDateTime());
//			}
//		}
	}

}
