package com.moblima.project.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.moblima.project.model.Cinema;
import com.moblima.project.model.Movie;
import com.moblima.project.view.staff.LoginMenu;
import com.moblima.project.view.staff.StaffMenu;

public class MainMenu extends BaseMenu {
	private LoginMenu mLoginMenu;
	
	public MainMenu(Scanner sc) {
		super(sc);
		
		mLoginMenu = new LoginMenu(sc);
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
						break;
				}			
			} catch (ExitException e) {
				println("Program is exiting.");
				break;
			}
		} while (choice != 3);
	}
	
	public void displayMovies(){
		ArrayList<Movie> movies = Movie.instances;
		
		println("Id    Title    Opening     Runtime    Director");
		for(int i=0;i<movies.size();i++){
			println(movies.get(i).toDisplay());
		}
	}
	
	public void displayCinemas(){
		ArrayList<Cinema> cinemas = Cinema.instances;
		
		println("Id    Title    Opening     Runtime    Director");
		for(int i=0;i<cinemas.size();i++){
			println(cinemas.get(i).toDisplay());
		}
	}
}
