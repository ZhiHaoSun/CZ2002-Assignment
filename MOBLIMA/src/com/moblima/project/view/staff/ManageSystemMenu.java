package com.moblima.project.view.staff;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import org.json.JSONException;

import com.moblima.project.controller.CinemaManager;
import com.moblima.project.controller.Manager;
import com.moblima.project.controller.MovieManager;
import com.moblima.project.controller.ReviewManager;
import com.moblima.project.controller.ShowTimeManager;
import com.moblima.project.controller.StaffManager;
import com.moblima.project.controller.TicketManager;
import com.moblima.project.model.Model;
import com.moblima.project.model.ShowTime;
import com.moblima.project.model.Ticket;
import com.moblima.project.view.BaseMenu;
import com.moblima.project.view.BaseMenu.ExitException;

public class ManageSystemMenu extends BaseMenu {
	
	private Ticket ticket;
	private ShowTime showTime;

	public ManageSystemMenu(Scanner sc, MovieManager mMovieManager, CinemaManager mCinemaManager,
			ReviewManager mReviewManager, ShowTimeManager mShowTimeManager, TicketManager mTicketManager,
			StaffManager mStaffManager) {
		super(sc, mMovieManager, mCinemaManager, mReviewManager, mShowTimeManager, mTicketManager, mStaffManager);
		
	}

	@Override
	public void displayMenu() {
		choice = 0; // each menu manage their own choice integer
		
		do {
			printHeader("Manage Tickets Sale");
			println(" 1. View Tickets Sale");
			println(" 2. Change ticket price and discount setting");
			println(" 3. Back");
			println("");
			
			try {
				choice = readChoice(1, 3);
				
				switch (choice) {
					case 1:
						viewTicketSale();
						break;
					case 2:
						configureSetting();
						break;
					case 3:
						return;
					default:
						println("Invalid choice! Please select again!!!");
						break;
				}			
			} catch (ExitException e) {
				break;
			}
		} while (choice != 3);
	}
	
	public void viewTicketSale() throws ExitException{
		printHeader("View Ticket Sale");
		int i,j;
		
		showTime = this.chooseShowTime();
		Boolean[][] seats = this.mShowTimeManager.getSeats(showTime, mTicketManager);
		
		this.displaySeats(seats);
		
	}
	
	public void configureSetting(){
		
	}
}
