package com.moblima.project.view.moviegoer;

import java.util.Scanner;

import com.moblima.project.controller.MovieManager;
import com.moblima.project.view.BaseMenu;

public class MovieGoerMenu extends BaseMenu {

	public MovieGoerMenu(Scanner sc) {
		super(sc);
		
		mMovieManager = new MovieManager();
	}
	
	public void displayMenu() {
		choice = 0; // each menu manage their own choice integer
		
		do {
			printHeader("Welcome to MOBLIMA");
			println(" 1. Movie Listing");
			println(" 2. Search");
			println(" 3. Remove Movie");
			println(" 4. Back");
			println("");
			
			try {
				choice = readChoice(1, 4);
				
				switch (choice) {
					case 1:

						break;
					case 2:

						break;
					case 3:

						break;
					case 4: 
						return; // end this method and go back to previous menu
					default:
						println("Invalid choice! Please select again!!!");
						break;
				}			
			} catch (Exception e) {
				break;
			}
		} while (choice != 4);
	}
}
