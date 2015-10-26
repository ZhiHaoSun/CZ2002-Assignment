package com.moblima.project.view.staff;

import java.text.ParseException;
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
import com.moblima.project.model.ShowTime;
import com.moblima.project.view.BaseMenu;

public class ManageShowTimeMenu extends BaseMenu {

	public ManageShowTimeMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager,
			ReviewManager mReviewManager, ShowTimeManager mShowTimeManager, TicketManager mTicketManager,
			StaffManager mStaffManager) {
		super(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);

	}
	
	private ShowTime showTime;
	private Cinema cinema;
	private Movie movie;

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
						this.createShowTime();
						break;
					case 2:
						this.updateShowTime();
						break;
					case 3:
						this.deleteShowTime();
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
	
	public void createShowTime() throws ExitException, ParseException, JSONException{
		showTime = new ShowTime();
		
		printHeader("Create Show Time");
		
		showTime.setCinemaId(chooseCinema().getId());
		showTime.setMovieId(chooseMovie().getId());
		
		showTime.setDateTime(read("Show Time is (dd/MM/yyyy hh:mm PM)"));
		
		if (mShowTimeManager.create(showTime))
			System.out.println("Create Successful");
		else
			System.out.println("Create Unsuccessful");
	}
	
	public void updateShowTime() throws ExitException, ParseException, JSONException{
		printHeader("Update Show Time");
		
		showTime = this.chooseShowTime();
		
		if(confirm("Change Movie?"))
			showTime.setCinemaId(chooseCinema().getId());
		
		if(confirm("Change Cinema?"))
			showTime.setMovieId(chooseMovie().getId());
			
		if(confirm("Change Date and Time?"))
			showTime.setDateTime(read("Show Time is (dd/MM/yyyy hh:mm PM)"));
		
		if (mShowTimeManager.update(showTime))
			System.out.println("Update Successful");
		else
			System.out.println("Update Unsuccessful");
	}
	
	public void deleteShowTime() throws ExitException{
		printHeader("Update Show Time");
		
		showTime = this.chooseShowTime();
		
		if (mShowTimeManager.delete(showTime))
			println("Remove Successful");
		else
			println("Remove Unsuccessful");
	}

}