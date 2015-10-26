package com.moblima.project.view.staff;

import java.util.Scanner;

import org.json.JSONException;

import com.moblima.project.controller.CinemaManager;
import com.moblima.project.controller.MovieManager;
import com.moblima.project.controller.ReviewManager;
import com.moblima.project.controller.ShowTimeManager;
import com.moblima.project.controller.StaffManager;
import com.moblima.project.controller.TicketManager;
import com.moblima.project.model.Cinema;
import com.moblima.project.view.BaseMenu;

public class ManageCinemaMenu extends BaseMenu {
	
	private ManageShowTimeMenu mManageShowTimeMenu;

	public ManageCinemaMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager,
			ReviewManager mReviewManager, ShowTimeManager mShowTimeManager, TicketManager mTicketManager,
			StaffManager mStaffManager) {
		super(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
		mManageShowTimeMenu = new ManageShowTimeMenu(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
	}
	
	private Cinema cinema;

	@Override
	public void displayMenu() {
		choice = 0; // each menu manage their own choice integer
		
		do {
			printHeader("Manage Cinema Listing");
			println(" 1. Create Cinema");
			println(" 2. Manage Cinema Show Times");
			println(" 3. Update Cinema");
			println(" 4. Back");
			println("");
			
			try {
				choice = readChoice(1, 4);
				
				switch (choice) {
					case 1:
						this.createCinema();
						break;
					case 2:
						this.mManageShowTimeMenu.displayMenu();
						break;
					case 3:
						this.updateCinema();
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

	public void createCinema() throws ExitException, JSONException{
		cinema = new Cinema();
		
		printHeader("Create Cinema");
		
		cinema.setName(read("Cinema Name: "));
		cinema.setCineplex(this.chooseCineplex());
		
		if (this.mCinemaManager.create(cinema))
			System.out.println("Create Successful");
		else
			System.out.println("Create Unsuccessful");
	}
	
	public void updateCinema() throws ExitException, JSONException{
		printHeader("Update Movie");
		
		cinema = chooseCinema();
		
		if(confirm("Change Name ?"))
			cinema.setName(read("New Name: "));
		if(confirm("Change Cineplex?"))
			cinema.setCineplex(chooseCineplex());
		
		if(this.mCinemaManager.update(cinema))
			println("Update Cinema Successful");
		else
			println("Update Cinema Unsuccessful");
	}
}
