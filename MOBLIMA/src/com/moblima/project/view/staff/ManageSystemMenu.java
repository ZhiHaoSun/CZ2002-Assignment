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
import com.moblima.project.model.Constant.ClassType;
import com.moblima.project.model.Discount;
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
			println(" 2.Change Discount setting");
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
			} catch (JSONException e) {
				e.printStackTrace();
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
	
	public void configureSetting() throws ExitException, JSONException{
		printHeader("Discount Setting");
		
		println("Which one to change?");
		println(" 1. Age Discount");
		println(" 2. Hoiday Discount");
		println(" 3. Movie Type Discount");
		println(" 4. Cinema Class Discount");
		println(" 5. Back");
		
		int choice = readChoice(1, 5);
		
		switch(choice){
			case 1:
				if(confirm("Change Age Below 12 Discount?"))
					Discount.discounts.getJSONObject("age").put("below12", readInt("New Discount Value: "));
				
				if(confirm("Change Age Above 60 Discount?"))
					Discount.discounts.getJSONObject("age").put("above60", readInt("New Discount Value: "));
				
				break;
			case 2:
				if(confirm("Change weekends Plus?"))
					Discount.discounts.getJSONObject("holiday").put("weekends", readInt("New Discount Value: "));
				break;
			case 3:
				ClassType type = chooseMovieType();
				if(confirm("Change " + type.name() +" Discount?"))
					Discount.discounts.getJSONObject("classType").put(type.name(), readInt("New Discount Value: "));
				
				break;
			case 4:
				if(confirm("Change platinum Discount?"))
					Discount.discounts.getJSONObject("cinemaClass").put("platinum", readInt("New Discount Value: "));
				break;
			case 5:
				return;
		}
		
		if(Discount.save()){
			println("Discount Update Successfully!");
		} else{
			println("Discount Update Unsuccessfully.");
		}
	}
}
