package com.moblima.project.view.staff;

import java.text.ParseException;

import org.json.JSONException;

import com.moblima.project.controller.CineplexManager;
import com.moblima.project.model.Cinema;
import com.moblima.project.model.Movie;
import com.moblima.project.model.ShowTime;
import com.moblima.project.view.BaseMenu;

public class ManageShowTimeMenu extends BaseMenu {
	
	private ShowTime showtime;

	public ManageShowTimeMenu (CineplexManager mCineplexManager) {
		super(mCineplexManager);
	}

	@Override
	public void displayMenu() {
		choice = 0; // each menu manage their own choice integer
		
		do {
			printHeader("Manage Cinema Show Time Listing");
			println(" 1. Create Show Time");
			println(" 2. Update Show Time");
			println(" 3. Remove Show Time");
			println(" 4. Back");
			println("");
			
			try {
				choice = readChoice(1, 4);
				
				switch (choice) {
					case 1:
						createShowTime();
						break;
					case 2:
						updateShowTime();
						break;
					case 3:
						deleteShowTime();
						break;
				}			
			} catch (Exception e) {
				break;
			}
		} while (choice != 4);
	}
	
	public void createShowTime() throws ExitException, ParseException, JSONException{
		showtime = new ShowTime();
		
		printHeader("Create Show Time");
		
		showtime.setMovie(chooseMovie());
		showtime.setCinema(chooseCinema(chooseCineplex()));
		showtime.setDate(readDate("Please input the DATE of the ShowTime"));
		showtime.setTime(readTime("Please input the TIME of the ShowTime"));
		
		if (mCineplexManager.create(showtime))
			System.out.println("Create Successful");
		else
			System.out.println("Create Unsuccessful");
	}
	
	public void updateShowTime() throws ParseException, JSONException{
		printHeader("Update Show Time");
		try {
			showtime = chooseShowTime();
					
			if(confirm("Change Movie?")) 
				showtime.setMovie(chooseMovie());
			
			if(confirm("Change Cinema?"))
				showtime.setCinema(chooseCinema(chooseCineplex()));
			
			if(confirm("Change Date of the ShowTime?"))
				showtime.setDate(readDate("New Date"));
			
			if(confirm("Change Time of the ShowTime?"))
				showtime.setTime(readTime("New Time"));

			if (mCineplexManager.update(showtime)) 
				System.out.println("Update Successful");
			else 
				System.out.println("Update Unsuccessful");
		} catch (ExitException exit) {}
	}
	
	public void deleteShowTime() {
		printHeader("Remove Show Time");
		
		try {
			showtime = chooseShowTime();
			
			if (mCineplexManager.delete(showtime))
				println("Remove Successful");
			else
				println("Remove Unsuccessful");
		} catch (ExitException exit) {}
	}

}
