package com.moblima.project.view;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONException;

import com.moblima.project.controller.CineplexManager;
import com.moblima.project.model.Movie;
import com.moblima.project.view.moviegoer.MovieGoerMenu;
import com.moblima.project.view.staff.StaffMenu;

public class MainMenu extends BaseMenu {
	
	private StaffMenu mStaffMenu;
	private MovieGoerMenu mMovieGoerMenu;
	private CineplexManager mCineplexManager;

	public MainMenu() {
		try {
			mCineplexManager = new CineplexManager();
			
			mStaffMenu 	   = new StaffMenu(mCineplexManager);		
			mMovieGoerMenu = new MovieGoerMenu(mCineplexManager);
		} catch (IOException | JSONException | ParseException e) {
			println("problem loading the data, please contact the administrator");
			e.printStackTrace();
		}
		
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
						mStaffMenu.displayMenu();
						break;
					case 2:
						mMovieGoerMenu.displayMenu();
						break;
				}			
			} catch (ExitException e) {
				println("Program is exiting.");
				break;
			} 
		} while (choice != 3);
	}
	
	
	
	
}
