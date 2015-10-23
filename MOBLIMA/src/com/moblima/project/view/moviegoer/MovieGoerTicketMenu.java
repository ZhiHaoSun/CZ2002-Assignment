package com.moblima.project.view.moviegoer;

import java.util.Scanner;

import com.moblima.project.controller.CinemaManager;
import com.moblima.project.controller.MovieManager;
import com.moblima.project.controller.ReviewManager;
import com.moblima.project.controller.ShowTimeManager;
import com.moblima.project.controller.StaffManager;
import com.moblima.project.controller.TicketManager;
import com.moblima.project.view.BaseMenu;
import com.moblima.project.view.BaseMenu.ExitException;

public class MovieGoerTicketMenu extends BaseMenu {

	public MovieGoerTicketMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager,
			ReviewManager mReviewManager, ShowTimeManager mShowTimeManager, TicketManager mTicketManager, StaffManager mStaffManager) {
		super(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
	}

	@Override
	public void displayMenu() {
		int choice = 0; // each menu manage their own choice integer

		do {
			printHeader("Options for tickets");
			println(" 1. Get tickets");
			println(" 2. View Cinemas");
			println(" 3. View Tickets");
			println(" 4. Quit");
			println("");
			
			try {
				choice = readChoice(1, 3);
				
				switch (choice) {
					case 1:
						String email = this.read("Please enter your email address");
						this.displayTicketsByEmail(email);
						break;
					case 2:
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
	
	public void displayTicketsByEmail(String email){
		
	}
}
