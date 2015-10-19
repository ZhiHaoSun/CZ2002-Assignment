package com.moblima.project.view.staff;

import java.text.ParseException;
import java.util.Scanner;

import com.moblima.project.model.Movie;
import com.moblima.project.view.BaseMenu;

public class ManageMovieMenu extends BaseMenu {

	private Movie movie;
	
	public ManageMovieMenu(Scanner sc) {
		super(sc);

	}

	@Override 
	public void displayMenu() {
		int choice = 0; // each menu manage their own choice integer
		
		do {
			printHeader("Manage Movie Listing");
			println(" 1. Create Movie");
			println(" 2. Update Movie");
			println(" 3. Remove Movie");
			println(" 4. Back");
			println("");
			
			try {
				choice = readChoice(1, 4);
				
				switch (choice) {
					case 1:
						createMovie();
						break;
					case 2:
						updateMovie();
						break;
					case 3:
						removeMovie();
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
	
	private void createMovie() throws ParseException, ExitException {
		movie = new Movie();
		
		printHeader("Create Movie");
		
		movie.setTitle(read("Title: "));
		movie.setSynopsis(read("SYNOPSIS: "));
		
		movie.setDirector(read("Director: "));
		movie.setCasts(read("Casts (separate by COMMA): "));
		
		movie.setLanguage(chooseLanguage());
		movie.setRating(chooseMovieRating());
		movie.setStatus(chooseMovieStatus());
		
		movie.setRunTime(read("RunTime (minutes): "));
		movie.setOpening(read("Opening (TBA or dd/MM/yyyy): "));
		
		if (mMovieManager.create(movie))
			System.out.println("Create Successful");
		else
			System.out.println("Create Unsuccessful");
	}
	
	private void updateMovie() throws ExitException, ParseException {
		printHeader("Update Movie");
	
		movie = chooseMovie();

		if (confirm("Change Title?"))
			movie.setTitle(read("New Title: "));
		
		if (confirm("Change SYNOPSIS?"))
			movie.setSynopsis(read("New SYNOPSIS: "));
		
		if (confirm("Change Director?"))
			movie.setDirector(read("New Director: "));

		if (confirm("Change Casts"))
			movie.setCasts(read("New Casts: "));
		
		if (confirm("Change Language?"))
			movie.setLanguage(chooseLanguage());
		
		if (confirm("Change Movie Rating?"))
			movie.setRating(chooseMovieRating());
		
		if (confirm("Change Movie Status?"))
			movie.setStatus(chooseMovieStatus());
		
		if (confirm("Change RunTime?"))
			movie.setRunTime(read("New RunTime: "));
		
		if (confirm("Change Opening?"))
			movie.setOpening(read("New Opening (dd/MM/yyyy): "));

		if (mMovieManager.update(movie))
			println("Update Movie Successful");
		else
			println("Update Movie Unsuccessful");
	}
	
	private void removeMovie() throws ExitException {
		printHeader("Remove Movie");
		
		movie = chooseMovie();
		
		if (mMovieManager.remove(movie))
			println("Remove Successful");
		else
			println("Remove Unsuccessful");
	}
}