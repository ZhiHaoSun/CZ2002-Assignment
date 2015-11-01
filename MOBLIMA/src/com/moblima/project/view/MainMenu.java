package com.moblima.project.view;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;

import com.moblima.project.controller.CinemaManager;
import com.moblima.project.controller.MovieManager;
import com.moblima.project.controller.ReviewManager;
import com.moblima.project.controller.ShowTimeManager;
import com.moblima.project.controller.StaffManager;
import com.moblima.project.controller.TicketManager;
import com.moblima.project.model.Cinema;
import com.moblima.project.model.Movie;
import com.moblima.project.model.Ticket;
import com.moblima.project.view.moviegoer.MovieGoerMenu;
import com.moblima.project.view.staff.LoginMenu;
import com.moblima.project.view.staff.StaffMenu;

public class MainMenu extends BaseMenu {
	private MovieGoerMenu movieGoerMenu;
	private LoginMenu mLoginMenu;
	
	public MainMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager, ReviewManager mReviewManager,
			ShowTimeManager mShowTimeManager, TicketManager mTicketManager, StaffManager mStaffManager) {
		super(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
		
		movieGoerMenu = new MovieGoerMenu(sc,mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
		mLoginMenu = new LoginMenu(sc,mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
	}

	public void displayMenu() {
		int choice = 0; // each menu manage their own choice integer

		do {
			printHeader("MOvie Booking and LIsting Management Application (MOBLIMA)");
			println(" 1. Staff Login");
			println(" 2. Customer");
			println(" 3. Quit");
			println("");
			
			try {
				
				choice = readChoice(1, 3);
				
				
				switch (choice) {
					case 1:
						mLoginMenu.displayMenu();
						break;
					case 2:
						movieGoerMenu.displayMenu();
						break;
				}			
			} catch (ExitException e) {
				println("Program is exiting.");
				break;
			}
		} while (choice != 3);
	}
	
	
	
	
}
