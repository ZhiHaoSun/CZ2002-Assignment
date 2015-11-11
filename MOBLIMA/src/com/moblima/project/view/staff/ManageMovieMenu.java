package com.moblima.project.view.staff;

import java.text.ParseException;

import com.moblima.project.controller.CineplexManager;
import com.moblima.project.model.Movie;
import com.moblima.project.view.BaseMenu;

public class ManageMovieMenu extends BaseMenu {
	
	public ManageMovieMenu(CineplexManager mCineplexManager) {
		super(mCineplexManager);
	}

	private Movie movie;

	@Override 
	public void displayMenu() {
		choice = 0; // each menu manage their own choice integer
				
		do {
			printHeader("Manage Movie Listing");
			println(" 1. Create Movie");
			println(" 2. Update Movie");
			println(" 3. Back");
			println("");
			
			try {
				choice = readChoice(1, 3);
				
				switch (choice) {
					case 1:
						createMovie();
						break;
					case 2:
						updateMovie();
						break;
				}			
			} catch (ExitException e) {
				break;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} while (choice != 4);
	}
	
	private void createMovie() throws ParseException {
		printHeader("Create Movie");
		
		try {
			movie = new Movie();
			
			movie.setTitle(read("Title: "));
			movie.setSynopsis(read("SYNOPSIS: "));
			
			movie.setDirector(read("Director: "));
			movie.setCasts(read("Casts (separate by ','): "));
				
			movie.setLanguage(chooseLanguage());
			movie.setRating(chooseMovieRating());
			movie.setStatus(chooseMovieStatus());
			
			movie.setMovieType(chooseMovieType());
			
			movie.setBlockBuster(confirm("Is this a Blockbuster movie?"));
			movie.setRunTime(read("RunTime (minutes): "));
			movie.setOpening(read("Opening (TBA or dd/MM/yyyy): "));
			
			if (mCineplexManager.create(movie))
				System.out.println("Create Successful");
			else
				System.out.println("Create Unsuccessful");
		} catch (ExitException exit) {}
	}
	
	private void updateMovie() throws ParseException {	
		printHeader("Update Movie");

		try {

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
			
			if (confirm("Change Movie Type?"))
				movie.setMovieType(chooseMovieType());

			if (confirm("Change Movie Rating?"))
				movie.setRating(chooseMovieRating());
			
			if (confirm("Change Movie Status?"))
				movie.setStatus(chooseMovieStatus());
			
			if (confirm("Change RunTime?"))
				movie.setRunTime(read("New RunTime: "));
			
			if (confirm("Change Opening?"))
				movie.setOpening(read("New Opening (dd/MM/yyyy): "));

			if (mCineplexManager.update(movie))
				println("Update Movie Successful");
			else
				println("Update Movie Unsuccessful");
		} catch (ExitException exit) {}
		
	}
}
